package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import robohawks.MathX;
import robohawks.modules.base.DriveModule;
import robohawks.modules.base.LaunchModule;

/**
 * Created by fchoi on 10/13/2016.
 */
@TeleOp(name="Basic", group ="Teleop")
public class TeleopController extends Controller{
    DriveModule driveModule;
    LaunchModule launchModule;

    double launchPower;
    boolean locked;

    boolean lockedButtonState;

    float threshold = .2f;

    @Override
    public void init() {
        driveModule = new DriveModule(hardwareMap);
        launchModule = new LaunchModule(hardwareMap);
    }

    @Override
    public void loop() {
        super.loop();

        double x, z;
        if(Math.abs(gamepad1.left_stick_x) > threshold) {
            x = MathX.expScale(gamepad1.left_stick_x, 2);
        } else {
            x = 0;
        }

        if(Math.abs(gamepad1.left_stick_y) > threshold) {
            z = MathX.expScale(gamepad1.left_stick_y, 2);
        } else {
            z = 0;
        }
        driveModule.setHeading(x, z);

        if(gamepad1.a != lockedButtonState && gamepad1.a)
            locked = !locked;
        lockedButtonState = gamepad1.a;

        if(!locked)
            launchPower = gamepad1.right_stick_y;

        launchModule.setWheelPower(launchPower);

        telemetry.addData("Heading", x + ", " + z);
        telemetry.addData("Power", launchPower + (locked ? "*": ""));
    }
}
