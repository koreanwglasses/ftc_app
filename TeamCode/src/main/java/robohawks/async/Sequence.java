package robohawks.async;

import robohawks.async.error.ErrorArgs;
import robohawks.async.error.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fchoi on 9/25/2016.
 */
public class Sequence {
    private List<Operation> actionQueue;

    private Operation currentAction;
    private boolean actionInProgress;

    private boolean paused;
    private boolean finished;

    private Callback callback;
    private ErrorHandler errorHandler;

    public Sequence() {
        actionQueue = new ArrayList<>();

        actionInProgress = false;

        callback = new Callback(this);
    }

    public Sequence then(Operation action) {
        actionQueue.add(action);
        return this;
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
