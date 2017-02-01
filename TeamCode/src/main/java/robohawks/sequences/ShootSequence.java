package robohawks.sequences;

import robohawks.async.Sequence;
import robohawks.async.Sequencer;
import robohawks.async.error.ComplexOperation;
import robohawks.modules.base.DriveModule;
import robohawks.modules.base.LaunchModule;

/**
 * Created by fchoi on 2/1/2017.
 */
public class ShootSequence extends ComplexOperation {

    DriveModule driveModule;
    LaunchModule launchModule;

    Sequence sequence;

    public ShootSequence(Sequencer sequencer, DriveModule driveModule, LaunchModule launchModule) {
        super(sequencer);

        this.driveModule = driveModule;
        this.launchModule = launchModule;
    }

    @Override
    public void start(Sequence.Callback callback) {
        sequence = sequencer
                .begin(driveModule.drive(1, 1, 1))
                .then(launchModule.launchRev(1))
                .then(launchModule.launch(sequencer));
    }

    @Override
    public void loop(Sequence.Callback callback) {
        if(sequence.isFinished()) {
            callback.next();
        }
    }

    @Override
    public void stop(Sequence.Callback callback) {
        sequence.terminate();
        callback.next();
    }
}
