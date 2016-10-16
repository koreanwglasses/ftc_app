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
public class MasterTeleopController extends Controller{
    DriveModule driveModule;

    float threshold = .1f;

    @Override
    public void init() {
        driveModule = new DriveModule(hardwareMap);
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

        telemetry.addData("Heading", x + ", " + p);
    }
}
