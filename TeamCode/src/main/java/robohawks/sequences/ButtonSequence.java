package robohawks.sequences;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.Sequencer;
import robohawks.async.SimpleOperation;
import robohawks.async.error.ComplexOperation;
import robohawks.modules.base.*;

/**
 * Created by fchoi on 12/14/2016.
 */
public class ButtonSequence extends ComplexOperation {
    ActuatorModule actuatorModule;
    ColorModule colorModule;

    boolean rednotBlue;

    Sequence sequence;

    Sequence leftSequence;
    Sequence rightSequence;

    public ButtonSequence(Sequencer sequencer, ActuatorModule actuatorModule, ColorModule colorModule, boolean rednotBlue) {
        super(sequencer);

        this.actuatorModule = actuatorModule;
        this.colorModule = colorModule;

        this.rednotBlue = rednotBlue;
    }

    @Override
    public void start(Sequence.Callback callback) {
        leftSequence = sequencer
                .create(actuatorModule.setActuatorLeftOp(true))
                .then(new WaitModule(5500))
                .then(actuatorModule.setActuatorLeftOp(false));
        rightSequence = sequencer
                .create(actuatorModule.setActuatorRightOp(true))
                .then(new WaitModule(5500))
                .then(actuatorModule.setActuatorRightOp(false));
        sequence = sequencer
                .begin(colorModule.isRednotBlueOp())
                .thenStartIf(leftSequence, rightSequence);
    }

    @Override
    public void loop(Sequence.Callback callback) {
        if (leftSequence.isFinished() || rightSequence.isFinished()) {
            stop(callback);
        }
    }

    @Override
    public void stop(Sequence.Callback callback) {
        if (!sequence.isFinished()) {
            sequence.terminate();
        }
        callback.next();
    }
}

