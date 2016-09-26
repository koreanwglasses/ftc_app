package robohawks.modules;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.modules.base.Drive;

/**
 * Created by fchoi on 9/26/2016.
 */
public class SimpleModule {
    public SimpleModule() {

    }

    public Operation simple() {
        return new SimpleOperation(this);
    }

    public class SimpleOperation implements Operation {
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
