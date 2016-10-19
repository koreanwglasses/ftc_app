package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Sequence;
import robohawks.modules.base.ColorModule;

/**
 * Created by paarth on 10/19/16.
 */

@Autonomous(name="color", group="Sample")
public class ColorSensorController extends Controller {
    Sequence colorSequence;

    @Override
    public void init() {
        ColorModule colorModule = new ColorModule(hardwareMap);
        colorSequence = sequencer.begin(colorModule.getColor());
    }

    @Override
    public void loop() {
        super.loop();

        if (colorSequence.isFinished()) requestOpModeStop();
    }

}
