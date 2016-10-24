package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robohawks.async.Sequence;
import robohawks.modules.base.ElevatorModule;

/**
 * Created by paarth on 10/24/16.
 */

@TeleOp(name="elevate", group = "Teleop")
public class ElevatorController extends Controller {
    ElevatorModule elevatorModule;

    boolean elevateButtonState;
    Sequence elevateSequence;

    @Override
    public void init() {
        elevatorModule = new ElevatorModule(hardwareMap);
    }

    @Override
    public void loop() {
        super.loop();

        if(elevateSequence != null && elevateSequence.isFinished()) {
            elevateSequence = null;
        }
        if(gamepad1.a != elevateButtonState && gamepad1.a && elevateSequence == null) {
            elevateSequence = sequencer.begin(elevatorModule.elevate());
        }
        elevateButtonState = gamepad1.a;
    }
}
