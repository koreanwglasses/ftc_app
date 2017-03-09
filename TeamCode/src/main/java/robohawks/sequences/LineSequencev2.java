package robohawks.sequences;

import com.qualcomm.robotcore.util.ElapsedTime;
import robohawks.async.*;
import robohawks.async.error.ComplexOperation;
import robohawks.async.error.ErrorArgs;
import robohawks.async.error.ErrorHandler;
import robohawks.modules.base.ColorModule;
import robohawks.modules.base.DriveModule;
import robohawks.modules.base.RangeModule;

/**
 * Created by fchoi on 1/14/2017.
 */
public class LineSequencev2 extends ComplexOperation implements ErrorHandler{

    private ColorModule colorModule;
    private DriveModule driveModule;
    private RangeModule rangeModule;

    private boolean foundLine;

    private double abortDistance = 0.20;
    private double stopDistance = 0.03;

    private Sequence mainSequence;
    private Sequence secSequence;

    private ElapsedTime time;
    private double nextCheckTime;

    public LineSequencev2(Sequencer sequencer, ColorModule colorModule, DriveModule driveModule, RangeModule rangeModule) {
        super(sequencer);
        this.colorModule = colorModule;
        this.driveModule = driveModule;
        this.rangeModule = rangeModule;

        time = new ElapsedTime();
    }

    @Override
    public void start(final Sequence.Callback callback1) {
        mainSequence = sequencer
                .begin(driveModule.drive(1, -1, -1))
                .then(driveModule.setHeadingXPOp(0, -0.25))
                .then(new LoopOperation() {
                    @Override
                    public void loop(Sequence.Callback callback) {
                        if(colorModule.isLeftWhitenotBlack() || colorModule.isRightWhitenotBlack() || rangeModule.getDistance() < abortDistance) {
                            callback.next(colorModule.isLeftWhitenotBlack());
                        }
//                } else if (rangeModule.getDistance() < abortDistance) {
//                    callback.err(new ErrorArgs(this));
//                }
                    }
                })
//            .then(driveModule.drive(0.5, 0.2, 0.5))
                .then(new LoopOperation() {
                    @Override
                    public void loop(Sequence.Callback callback) {
                        if(colorModule.isLeftWhitenotBlack() || colorModule.isRightWhitenotBlack()) {
                            driveModule.setPowerLeft(0);
                            driveModule.setPowerRight(0);
                            callback.next();
                        } else {
                            driveModule.setPowerLeft(0.3);
                            driveModule.setPowerRight(-0.3);
                        }
                    }
                })
                .then(new LoopOperation() {
                    @Override
                    public void loop(Sequence.Callback callback) {
                        if(rangeModule.getDistance() < stopDistance) {
//                    callback1.next();
                            callback.next();
                        } else if (colorModule.isLeftWhitenotBlack()) {
                            driveModule.setPowerLeft(0.3);
                            driveModule.setPowerRight(-0.3);
                        } else if (colorModule.isRightWhitenotBlack()) {
                            driveModule.setPowerLeft(-0.3);
                            driveModule.setPowerRight(0.3);
                        } else {
                            driveModule.setPowerLeft(-0.25);
                            driveModule.setPowerRight(-0.25);
                        }
                    }
                });
        mainSequence.setErrorHandler(this);

        secSequence = sequencer
                .create(driveModule.setHeadingXPOp(0, 1))
                .then(new LoopOperation() {
                    @Override
                    public void loop(Sequence.Callback callback) {
                        if(rangeModule.getDistance() < stopDistance) {
                            callback1.next();
                            callback.next();
                        }
                    }
                });

        nextCheckTime = time.milliseconds() + 10000;
    }

    private double prevDistance = 0;
    @Override
    public void loop(Sequence.Callback callback) {
        if(mainSequence.isFinished()) callback.next();
        if(time.milliseconds() >= nextCheckTime) {
//            double distance = rangeModule.getDistance();
//            if (Math.abs(distance - prevDistance) < 0.01) {
//                mainSequence.terminate();
//                secSequence.unpause();
//            }
//            prevDistance = distance;

            nextCheckTime = time.milliseconds() + 2000;
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
