package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Sequence;
import robohawks.modules.base.ButtonModule;

/**
 * Created by paarth on 12/14/16.
 */

@Autonomous(name = "button", group = "Sample")
public class ButtonController extends Controller{
    ButtonModule buttonModule;

    Sequence buttonSequence;

    @Override
    public void init() {
        buttonModule = new ButtonModule(hardwareMap);
    }

    @Override
    public void loop() {
        super.loop();

        if(buttonSequence == null) {
            buttonSequence = sequencer.begin(buttonModule.toggle(1));
        }
    }
}
