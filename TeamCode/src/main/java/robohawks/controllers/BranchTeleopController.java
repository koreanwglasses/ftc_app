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

        if(gamepad2.a && !launchButtonState) {
            if(launchSequence != null) {
                launchSequence.terminate();
                launchSequence = null;
            }

            launchModule.setWheelPower(1);
        }
        if (!gamepad2.a && launchButtonState) {
            if(launchSequence != null) {
                launchSequence.terminate();
            }
            launchSequence = sequencer.begin(launchModule.launchDecel());
            launchSequence.setErrorHandler(this);
        }
        launchButtonState = gamepad2.a;

        // Un-Feed
        if(gamepad2.left_trigger > 0.5) {
            launchModule.setFeedPower(0.2);
        } else {
            if(gamepad2.right_trigger <= 0.5) {
                launchModule.setFeedPower(0);
            }
        }

        if(gamepad2.right_trigger > 0.5) {
            launchModule.setFeedPower(-0.2);
        } else {
            if(gamepad2.left_trigger <= 0.5) {
                launchModule.setFeedPower(0);
            }
        }

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

        telemetry.addData("Heading", x + ", " + p);
        telemetry.addData("Locked", launchModule.isLocked());
    }

    @Override
    public void handleError(Sequence sequence, ErrorArgs error) {
        telemetry.addData("Error", sequence.toString() + " - " + error);
    }
}
