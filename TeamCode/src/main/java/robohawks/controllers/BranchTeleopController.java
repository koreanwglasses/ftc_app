package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import robohawks.async.error.ErrorArgs;
import robohawks.async.error.ErrorHandler;
import robohawks.modules.base.ActuatorModule;
import robohawks.modules.base.LiftModule;
import robohawks.utils.MathX;
import robohawks.async.Sequence;
import robohawks.modules.base.DriveModule;
import robohawks.modules.base.LaunchModule;

/**
 * Created by fchoi on 10/13/2016.
 */
@TeleOp(name="Branch", group ="Teleop")
public class BranchTeleopController extends Controller implements ErrorHandler{
    DriveModule driveModule;
    LaunchModule launchModule;
    ActuatorModule actuatorModule;
    LiftModule liftModule;
//    ButtonModule buttonModule;

    boolean launchTriggerState;
//    boolean lockLaunchState;
//    boolean lockLaunchPower;
    double launchPower = 0;
    Sequence launchSequence;

    boolean loadButtonState;
    Sequence loadDecelSequence;

    boolean lActState;
    boolean rActState;

    boolean liftTriggerState;

    float threshold = .1f;

    @Override
    public void init() {
        driveModule = new DriveModule(hardwareMap);
        launchModule = new LaunchModule(hardwareMap);
        actuatorModule = new ActuatorModule(hardwareMap);
        liftModule = new LiftModule(hardwareMap);

        actuatorModule.initialize();
    }

    @Override
    public void loop() {
        super.loop();

        // Drive
        double x, p;
        if(Math.abs(gamepad1.left_stick_x) > threshold) {
            x = MathX.expScale(gamepad1.left_stick_x, .8);
        } else {
            x = 0;
        }

        if(Math.abs(gamepad1.right_trigger) > threshold) {
            p = MathX.expScale(gamepad1.right_trigger, 2);
        } else {
            p = 0;
        }
        if(Math.abs(gamepad1.left_trigger) > threshold) {
            p -= MathX.expScale(gamepad1.left_trigger, 2);
        }
        driveModule.setHeadingXP(x, p);
        // End Drive

        // LaunchRev
//        if(!lockLaunchPower) {
            if(gamepad2.right_trigger > threshold) {
//                launchPower = gamepad2.right_trigger;
                launchPower = 1;
            } else {
                launchPower = 0;
            }
//        }
        if(launchPower > threshold && !launchTriggerState) {
            if(launchSequence != null) {
                launchSequence.terminate();
            }
            launchSequence = sequencer.begin(launchModule.launchRev(launchPower));
            launchSequence.setErrorHandler(this);
        }
        if (launchPower <= threshold && launchTriggerState) {
            if(launchSequence != null) {
                launchSequence.terminate();
            }
            launchSequence = sequencer.begin(launchModule.launchDecel());
            launchSequence.setErrorHandler(this);
        }
        launchTriggerState = launchPower > threshold;
        // End LaunchRev

        // LaunchRev Lock
//        if(gamepad2.a && !lockLaunchState) {
//            lockLaunchPower = !lockLaunchPower;
//        }
//        lockLaunchState = gamepad2.a;
        // End LaunchRev Lock

        // Feed
        if(gamepad2.dpad_up) {
            launchModule.setFeedPower(0.3);
        } else {
            if(!gamepad2.dpad_down) {
                launchModule.setFeedPower(0);
            }
        }

        if(gamepad2.dpad_down) {
            launchModule.setFeedPower(-0.3);
        } else {
            if(!gamepad2.dpad_up) {
                launchModule.setFeedPower(0);
            }
        }
        // End Feed

        // Load
        if((gamepad2.y || gamepad2.x) && !loadButtonState) {
            if(loadDecelSequence != null) {
                loadDecelSequence.terminate();
                loadDecelSequence = null;
            }

            launchModule.setLoadPower(gamepad2.y ? 1 : -1);
        }
        if (!(gamepad2.y || gamepad2.x) && loadButtonState) {
            if(loadDecelSequence != null) {
                loadDecelSequence.terminate();
            }
            loadDecelSequence = sequencer.begin(launchModule.loadDecel());
            loadDecelSequence.setErrorHandler(this);
        }
        loadButtonState = (gamepad2.y || gamepad2.x);
        // End Load

        if (gamepad1.x && !rActState){
            actuatorModule.toggleActuatorRight();
        }

        rActState = gamepad1.x;

        if (gamepad1.y && !lActState){
            actuatorModule.toggleActuatorLeft();
        }

        lActState = gamepad1.y;

        // Buttons
//        if(gamepad1.y && !rButtonState) {
//            buttonModule.toggleServo2();
//        }
//        rButtonState = gamepad1.y;
//
//        if(gamepad1.x && !lButtonState) {
//            buttonModule.toggleServo1();
//        }
//        lButtonState = gamepad1.x;
        // End Buttons

        telemetry.addData("Heading", x + ", " + p);
        telemetry.addData("Power", launchModule.getLaunchPower());
//        if(lockLaunchPower) {
//            telemetry.addData("Info", "Power locked at" + launchPower);
//        }
        if(launchPower <= threshold && launchModule.getLaunchPower() > 0) {
            telemetry.addData("Info", "Decelerating...");
        }
    }

    @Override
    public void handleError(Sequence sequence, ErrorArgs error) {
        telemetry.addData("Error", sequence.toString() + " - " + error);
    }
}
