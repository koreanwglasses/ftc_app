package robohawks.sequences;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.Sequencer;
import robohawks.async.SimpleOperation;
import robohawks.async.error.ComplexOperation;
import robohawks.modules.base.ButtonModule;
import robohawks.modules.base.ColorModule;
import robohawks.modules.base.DriveModule;

/**
 * Created by fchoi on 12/14/2016.
 */
public class ButtonSequence extends ComplexOperation {
    ButtonModule buttonModule;
    ColorModule colorModule;
    DriveModule driveModule;

    boolean rednotBlue;
    boolean seesRednotBlue;

    Sequence sequence;

    public ButtonSequence(Sequencer sequencer, DriveModule driveModule, ButtonModule buttonModule, ColorModule colorModule, boolean rednotBlue) {
        super(sequencer);

        this.driveModule = driveModule;
        this.buttonModule = buttonModule;
        this.colorModule = colorModule;

        this.rednotBlue = rednotBlue;
    }

    @Override
    public void start(Sequence.Callback callback) {
        seesRednotBlue = colorModule.isRednotBlue();

        sequence = sequencer
                .begin(driveModule.drive(1, -0.5, -0.5))
                .then(new SimpleOperation() {
                    @Override
                    public void start(Sequence.Callback callback) {
                        if (seesRednotBlue == rednotBlue) {
                            buttonModule.setServo1(true);
                            buttonModule.setServo2(false);
                        } else {
                            buttonModule.setServo2(true);
                            buttonModule.setServo1(false);
                        }
                    }
                })
                .then(driveModule.drive(1, 0.0, 0.0))
                .then(driveModule.drive(1, 0.5, 0.5))
                .then(driveModule.drive(1, 0.0, 0.0))
                .then(driveModule.drive(1, -0.5, -0.5))
                .then(new SimpleOperation() {
                          @Override
                          public void start(Sequence.Callback callback) {
                              buttonModule.setServo1(false);
                              buttonModule.setServo2(false);
                          }
                      }
                );
    }

    @Override
    public void loop(Sequence.Callback callback) {
        if (sequence.isFinished()) {
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

