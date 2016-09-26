package robohawks.modules;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.modules.base.DriveModule;

/**
 * Created by fchoi on 9/26/2016.
 */
public class SimpleModule {
    private DriveModule driveModule;

    public SimpleModule(DriveModule driveModule) {
        this.driveModule = driveModule;
    }

    public Operation simple() {
        return new SimpleOperation(this);
    }

    private class SimpleOperation implements Operation {
        private SimpleModule module;
        private SimpleOperation(SimpleModule module) {
            this.module = module;
        }

        @Override
        public void start(Sequence.Callback callback) {

        }

        @Override
        public void loop(Sequence.Callback callback) {

        }
    }
}
