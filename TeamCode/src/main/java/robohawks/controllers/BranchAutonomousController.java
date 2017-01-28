package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.modules.base.ActuatorModule;
import robohawks.sequences.LineSequence;

/**
 * Created by paarth on 1/28/17.
 */

@Autonomous(name = "BranchAutonomousController")
public class BranchAutonomousController extends Controller {

    ActuatorModule actuatorModule;
    LineSequence lineSequence;


    @Override
    public void init() {

    }

    @Override
    public void loop() {
        super.loop();
    }
}
