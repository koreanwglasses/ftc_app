package robohawks.controllers.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Sequence;
import robohawks.controllers.Controller;
import robohawks.modules.base.ColorModule;
import robohawks.modules.base.DriveModule;
import robohawks.sequences.LineSequence;

/**
 * Created by paarth on 1/28/17.
 */
@Autonomous(name = "LineControllerLeft", group = "Test")
public class LineController extends Controller {
    LineSequence lineSequence;
    Sequence mainSequence;

    @Override
    public void init() {
        final ColorModule colorModule = new ColorModule(hardwareMap);
        final DriveModule driveModule = new DriveModule(hardwareMap);

        lineSequence = new LineSequence(sequencer, colorModule, driveModule);
        mainSequence = sequencer
                .begin(lineSequence);
    }

    @Override
    public void loop() {
        super.loop();
    }
}
