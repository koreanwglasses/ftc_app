package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import robohawks.async.Sequence;
import robohawks.modules.base.*;
import robohawks.sequences.ButtonSequence;
import robohawks.sequences.LineSequence;
import robohawks.sequences.LineSequencev2;
import robohawks.sequences.ShootSequence;

/**
 * Created by paarth on 1/28/17.
 */

@Autonomous(name = "BranchAutonomousControllerv2")
public class BranchAutonomousControllerv2 extends Controller {

    Sequence sequence;
    ColorModule colorModule;

    @Override
    public void init() {
        ActuatorModule actuatorModule = new ActuatorModule(hardwareMap);
        DriveModule driveModule = new DriveModule(hardwareMap);
        RangeModule rangeModule = new RangeModule(hardwareMap);
        colorModule = new ColorModule(hardwareMap);
        LaunchModule launchModule = new LaunchModule(hardwareMap);

        actuatorModule.initialize();
        colorModule.initialize();

        LineSequencev2 lineSequence = new LineSequencev2(sequencer, colorModule, driveModule, rangeModule);
        ButtonSequence buttonSequence = new ButtonSequence(sequencer, actuatorModule, colorModule, true);
        ShootSequence shootSequence = new ShootSequence(sequencer, driveModule, launchModule);

        sequence = sequencer
                .begin(lineSequence)
                .then(buttonSequence)
                .then(shootSequence);
    }

    @Override
    public void loop() {
        super.loop();

        telemetry.addData("White", colorModule.isLeftWhitenotBlack() || colorModule.isRightWhitenotBlack());
    }
}
