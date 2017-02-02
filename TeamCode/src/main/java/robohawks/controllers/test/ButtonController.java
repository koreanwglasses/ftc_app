package robohawks.controllers.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.controllers.Controller;
import robohawks.modules.base.ButtonModule;
import robohawks.modules.base.ColorModule;
import robohawks.modules.base.DriveModule;
import robohawks.sequences.ButtonSequence;

/**
 * Created by paarth on 12/14/16.
 */

@Autonomous(name = "button", group = "Test")
public class ButtonController extends Controller {
    ButtonModule buttonModule;
    ColorModule colorModule;
    DriveModule driveModule;

    Operation lineSequence;
    ButtonSequence buttonSequence;

    Sequence sequence;

    @Override
    public void init() {
        buttonModule = new ButtonModule(hardwareMap);
        colorModule = new ColorModule(hardwareMap);
        driveModule = new DriveModule(hardwareMap);

        colorModule.setLight(true);
        buttonModule.setServo1(false);
        buttonModule.setServo2(false);

//        buttonSequence = new ButtonSequence(sequencer, driveModule, buttonModule, colorModule, true);

        sequence = sequencer.begin(buttonSequence);
    }

    @Override
    public void loop() {

        telemetry.addData("ColorArgb", colorModule.getColorArgb());
        telemetry.addData("Color", colorModule.getButtonColor());
        super.loop();
    }
}
