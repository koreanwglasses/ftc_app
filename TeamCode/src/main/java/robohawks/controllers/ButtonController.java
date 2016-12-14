package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Sequence;
import robohawks.modules.base.ButtonModule;
import robohawks.modules.base.ColorModule;
import robohawks.utils.Color;

/**
 * Created by paarth on 12/14/16.
 */

@Autonomous(name = "button", group = "Sample")
public class ButtonController extends Controller{
    ButtonModule buttonModule;
    ColorModule colorModule;

    Sequence buttonSequence;

    @Override
    public void init() {

        buttonModule = new ButtonModule(hardwareMap);
        colorModule = new ColorModule(hardwareMap);
    }

    @Override
    public void loop() {
        super.loop();

        if(buttonSequence == null) {

            buttonSequence = sequencer.begin(buttonModule.toggle(1));
        }
    }
}
