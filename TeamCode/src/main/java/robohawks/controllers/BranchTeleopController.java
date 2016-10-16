package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import robohawks.MathX;
import robohawks.async.Sequence;
import robohawks.modules.base.DriveModule;
import robohawks.modules.base.LaunchModule;

/**
 * Created by fchoi on 10/13/2016.
 */
@TeleOp(name="Branch", group ="Teleop")
public class BranchTeleopController extends Controller{
    DriveModule driveModule;
    LaunchModule launchModule;

    boolean launchButtonState;
    Sequence launchSequence;

    float threshold = .2f;

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
            x = MathX.expScale(gamepad1.left_stick_x, 2);
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

        if(launchSequence != null && launchSequence.isFinished()) {
            launchSequence = null;
        }
        if(gamepad1.a != launchButtonState && gamepad1.a && launchSequence == null) {
            launchSequence = sequencer.begin(launchModule.launch());
        }
        launchButtonState = gamepad1.a;

        telemetry.addData("Heading", x + ", " + p);
    }
}
