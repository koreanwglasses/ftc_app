package robohawks.async;

import robohawks.async.error.ErrorArgs;
import robohawks.async.error.ErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fchoi on 9/25/2016.
 */
public class Sequence {
    private Sequencer parent;
    private List<Operation> actionQueue;

    private Operation currentAction;
    private boolean actionInProgress;

    private boolean paused;
    private boolean finished;

    private Callback callback;
    private ErrorHandler errorHandler;

    @Deprecated
    public Sequence() {
        this.actionQueue = new ArrayList<>();
        this.actionInProgress = false;
        this.callback = new Callback(this);
    }

    public Sequence(Sequencer parent) {
        this.parent = parent;
        this.actionQueue = new ArrayList<>();
        this.actionInProgress = false;
        this.callback = new Callback(this);
    }

    public Sequence then(Operation action) {
        actionQueue.add(action);
        return this;
    }

    public Operation start(final Sequence sequence) {
        return new SimpleOperation() {
            @Override
            public void start(Callback callback) {
                sequence.unpause();
                callback.next();
            }
        };
    }

    public Operation join(final Sequence sequence) {
        return new Operation() {
            @Override
            public void start(Callback callback) {

            }

            @Override
            public void loop(Callback callback) {
                if(sequence.isFinished()) callback.next();
            }

            @Override
            public void stop(Callback callback) {
                callback.next();
            }
        };
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void loop() {
        if (!finished && !paused) {
            if (actionInProgress) {
                currentAction.loop(callback);
            } else if (actionQueue.size() == 0) {
                finished = true;
            } else {
                currentAction = actionQueue.get(0);
                actionQueue.remove(0);

                actionInProgress = true;
                currentAction.start(callback);
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }

    public void terminate() {
        finished = true;
        currentAction.stop(callback);
    }

    public boolean isFinished() {
        return finished;
    }

    public class Callback {
        private Sequence parent;
        private Callback(Sequence parent) {
            this.parent = parent;
        }

        public void next() {
            parent.actionInProgress = false;
        }

        public void err(ErrorArgs error) {
            if(parent.errorHandler != null) {
                parent.errorHandler.handleError(parent, error);
            }
        }

        public Sequence getSequence() {
            return parent;
        }
    }
}
