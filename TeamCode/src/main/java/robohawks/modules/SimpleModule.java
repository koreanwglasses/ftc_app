package robohawks.modules;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.modules.base.Drive;

/**
 * Created by fchoi on 9/26/2016.
 */
public class SimpleModule implements Operation {
    // Required modules / base classes
    private Drive drive;

    public SimpleModule(Drive drive) {

    }

    @Override
    public void start(Sequence.Callback callback) {

    }

    @Override
    public void loop(Sequence.Callback callback) {

    }
}
