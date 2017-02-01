package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Sequence;
import robohawks.modules.base.*;
import robohawks.sequences.ButtonSequence;
import robohawks.sequences.LineSequence;
import robohawks.sequences.ShootSequence;

/**
 * Created by paarth on 1/28/17.
 */

@Autonomous(name = "BranchAutonomousController")
public class BranchAutonomousController extends Controller {

    Sequence sequence;

    @Override
    public void init() {
        ActuatorModule actuatorModule = new ActuatorModule(hardwareMap);
        DriveModule driveModule = new DriveModule(hardwareMap);
        RangeModule rangeModule = new RangeModule(hardwareMap);
        ColorModule colorModule = new ColorModule(hardwareMap);
        LaunchModule launchModule = new LaunchModule(hardwareMap);

        LineSequence lineSequence = new LineSequence(sequencer, colorModule, driveModule, rangeModule);
        ButtonSequence buttonSequence = new ButtonSequence(sequencer, actuatorModule, colorModule, true);
        ShootSequence shootSequence = new ShootSequence(sequencer, driveModule, launchModule);

        sequence = sequencer
            .begin(lineSequence)
            .then(buttonSequence)
            .then(shootSequence);
    }
}
