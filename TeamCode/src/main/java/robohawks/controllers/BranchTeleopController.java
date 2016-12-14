package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import robohawks.async.error.ErrorArgs;
import robohawks.async.error.ErrorHandler;
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

    boolean launchButtonState;
    Sequence launchSequence;

    boolean loadButtonState;
    Sequence loadDecelSequence;

    float threshold = .1f;

    @Override
    public void init() {
        driveModule = new DriveModule(hardwareMap);
        launchModule = new LaunchModule(hardwareMap);
    }

    @Override
    public void loop() {
        super.loop();

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

        // Launch

        if(gamepad1.a && !launchButtonState) {
            if(launchSequence != null) {
                launchSequence.terminate();
                launchSequence = null;
            }

            launchModule.setWheelPower(1);
        }
        if (!gamepad1.a && launchButtonState) {
            if(launchSequence != null) {
                launchSequence.terminate();
            }
            launchSequence = sequencer.begin(launchModule.launchDecel());
            launchSequence.setErrorHandler(this);
        }
        launchButtonState = gamepad1.a;

        // Un-Feed
        if(gamepad1.y) {
            launchModule.setFeedPower(0.2);
        } else {
            if(!gamepad1.x) {
                launchModule.setFeedPower(0);
            }
        }

        if(gamepad1.x) {
            launchModule.setFeedPower(-0.2);
        } else {
            if(!gamepad1.y) {
                launchModule.setFeedPower(0);
            }
        }

        // Feed

        if(gamepad1.b && !loadButtonState) {
            if(loadDecelSequence != null) {
                loadDecelSequence.terminate();
                loadDecelSequence = null;
            }

            launchModule.setLoadPower(1);
        }
        if (!gamepad1.b && loadButtonState) {
            if(loadDecelSequence != null) {
                loadDecelSequence.terminate();
            }
            loadDecelSequence = sequencer.begin(launchModule.loadDecel());
            loadDecelSequence.setErrorHandler(this);
        }
        loadButtonState = gamepad1.b;

        telemetry.addData("Heading", x + ", " + p);
        telemetry.addData("Locked", launchModule.isLocked());
        telemetry.addData("test", loadDecelSequence == null);
        if (loadDecelSequence != null) {
            telemetry.addData("test1", loadDecelSequence.isFinished());
        }
    }

    @Override
    public void handleError(Sequence sequence, ErrorArgs error) {
        telemetry.addData("Error", sequence.toString() + " - " + error);
    }
}
