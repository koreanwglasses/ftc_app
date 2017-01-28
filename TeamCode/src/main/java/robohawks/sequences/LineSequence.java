package robohawks.sequences;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.Sequencer;
import robohawks.async.WaitOperation;
import robohawks.async.error.ComplexOperation;
import robohawks.modules.base.ColorModule;
import robohawks.modules.base.DriveModule;

/**
 * Created by fchoi on 1/14/2017.
 */
public class LineSequence extends ComplexOperation{

    private ColorModule colorModule;
    private DriveModule driveModule;

    private boolean foundLine;

    private Sequence mainSequence;

    public LineSequence(Sequencer sequencer, ColorModule colorModule, DriveModule driveModule) {
        super(sequencer);
        this.colorModule = colorModule;
        this.driveModule = driveModule;
    }

    @Override
    public void start(Sequence.Callback callback) {
        mainSequence = sequencer
            .begin(driveModule.setHeadingXPOp(0, 1))
            .then(new WaitOperation() {
                @Override
                public void loop(Sequence.Callback callback) {

                }
            });
    }

    @Override
    public void loop(Sequence.Callback callback) {
        if(mainSequence.isFinished()) {
            callback.next();
        }
    }

    @Override
    public void stop(Sequence.Callback callback) {
        mainSequence.terminate();
        callback.next();
    }
}
