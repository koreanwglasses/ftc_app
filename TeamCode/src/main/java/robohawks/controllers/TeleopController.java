package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import robohawks.MathX;
import robohawks.modules.base.DriveModule;

/**
 * Created by fchoi on 10/13/2016.
 */
@TeleOp(name="Basic", group ="Teleop")
public class TeleopController extends Controller{
    DriveModule driveModule;

    float threshold = .1f;

    @Override
    public void init() {
        driveModule = new DriveModule(hardwareMap);
    }

    @Override
    public void loop() {
        super.loop();

        double x, z;
        if(gamepad1.left_stick_x > threshold) {
            x = MathX.expScale(gamepad1.left_stick_x, 2);
        } else {
            x = 0;
        }

        if(gamepad1.left_stick_y > threshold) {
            z = MathX.expScale(gamepad1.left_stick_y, 2);
        } else {
            z = 0;
        }
        driveModule.setHeading(x, z);

        telemetry.addData("Heading", x + ", " + z);
    }
}
