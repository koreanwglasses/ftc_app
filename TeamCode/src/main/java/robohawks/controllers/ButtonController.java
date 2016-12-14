package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.modules.base.ButtonModule;
import robohawks.modules.base.ColorModule;
import robohawks.sequences.ButtonSequence;
import robohawks.utils.Color;

/**
 * Created by paarth on 12/14/16.
 */

@Autonomous(name = "button", group = "Sample")
public class ButtonController extends Controller{
    ButtonModule buttonModule;
    ColorModule colorModule;

    Operation lineSequence;
    ButtonSequence buttonSequence;

    Sequence sequence;

    @Override
    public void init() {
        buttonModule = new ButtonModule(hardwareMap);
        colorModule = new ColorModule(hardwareMap);

        buttonSequence = new ButtonSequence(sequencer, buttonModule, colorModule, true);

        sequence = sequencer.begin(lineSequence).then(buttonSequence);
    }

    @Override
    public void loop() {
        super.loop();
    }
}
