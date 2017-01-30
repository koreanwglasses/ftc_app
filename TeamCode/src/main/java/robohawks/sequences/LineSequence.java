package robohawks.sequences;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.Sequencer;
import robohawks.async.WaitOperation;
import robohawks.async.error.ComplexOperation;
import robohawks.async.error.ErrorArgs;
import robohawks.async.error.ErrorHandler;
import robohawks.modules.base.ColorModule;
import robohawks.modules.base.DriveModule;
import robohawks.modules.base.RangeModule;

/**
 * Created by fchoi on 1/14/2017.
 */
public class LineSequence extends ComplexOperation implements ErrorHandler{

    private ColorModule colorModule;
    private DriveModule driveModule;
    private RangeModule rangeModule;

    private boolean foundLine;

    private double abortDistance = 0;

    private Sequence mainSequence;

    public LineSequence(Sequencer sequencer, ColorModule colorModule, DriveModule driveModule, RangeModule rangeModule) {
        super(sequencer);
        this.colorModule = colorModule;
        this.driveModule = driveModule;
        this.rangeModule = rangeModule;
    }

    @Override
    public void start(Sequence.Callback callback) {
        mainSequence = sequencer
            .begin(driveModule.setHeadingXPOp(0, 1))
            .then(new WaitOperation() {
                @Override
                public void loop(Sequence.Callback callback) {
                    if(colorModule.isLeftWhitenotBlack() || colorModule.isRightWhitenotBlack()) {
                        callback.next(colorModule.isLeftWhitenotBlack());
                    } else if (rangeModule.getDistance() < abortDistance) {
                        callback.err(new ErrorArgs(this));
                    }
                }
            })
            .then();
        mainSequence.setErrorHandler(this);
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

    @Override
    public void handleError(Sequence sequence, ErrorArgs error) {
        if(sequence == mainSequence) {
            mainSequence.terminate();
        }
    }
}
