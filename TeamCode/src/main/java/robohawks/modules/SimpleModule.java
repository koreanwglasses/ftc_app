package robohawks.modules;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.Sequencer;
import robohawks.async.error.ErrorArgs;
import robohawks.async.error.ErrorHandler;
import robohawks.modules.base.DriveModule;

/**
 * Created by fchoi on 9/26/2016.
 */
public class SimpleModule {
    private DriveModule driveModule;
    private Sequencer sequencer;

    public SimpleModule(Sequencer sequencer, DriveModule driveModule) {
        this.sequencer = sequencer;
        this.driveModule = driveModule;
    }

    public Operation simple() {
        return new SimpleOperation(this);
    }

    private class SimpleOperation implements Operation, ErrorHandler {
        private SimpleModule module;
        private Sequence inflectionSequence;

        private SimpleOperation(SimpleModule module) {
            this.module = module;
        }

        @Override
        public void start(Sequence.Callback callback) {
            inflectionSequence = module.sequencer.begin(module.driveModule.arc(1,.5, 1)).then(module.driveModule.arc(1, 1, .5));
            inflectionSequence.setErrorHandler(this);
        }

        @Override
        public void loop(Sequence.Callback callback) {
            if(inflectionSequence.isFinished()) callback.next();
        }

        @Override
        public void handleError(Sequence sequence, ErrorArgs error) {

        }
    }
}
