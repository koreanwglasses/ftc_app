package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Sequence;
import robohawks.async.SimpleOperation;
import robohawks.modules.base.ActuatorModule;
import robohawks.modules.base.WaitModule;

/**
 * Created by paarth on 1/23/17.
 */
@Autonomous(name="Actuator Controller", group="Competition")
public class ActuatorController extends Controller{
    Sequence mainSequence;

    @Override
    public void init() {
        final ActuatorModule actuatorModule = new ActuatorModule(hardwareMap);
        mainSequence = sequencer
            .begin(actuatorModule.setActuator1Op(true))
            .then(new WaitModule(2000))
            .then(actuatorModule.setActuator1Op(false))
            .then(actuatorModule.setActuator2Op(true))
            .then(new WaitModule(2000))
            .then(actuatorModule.setActuator2Op(false));
    }

    @Override
    public void loop() {
        super.loop();
    }
}
