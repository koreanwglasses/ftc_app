package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.error.DeviceLockedException;
import robohawks.async.error.OperationNotRunningException;

/**
 * Created by fchoi on 9/25/2016.
 */
// This is a "base" class, which is a combination of bindings and modules
public class DriveModule {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private boolean locked;

    // This initializes the bindings
    public DriveModule(HardwareMap hwMap) {
        // Not implemented yet
    }

    // This is a binding
    public void setPowerLeft(double power) {
        leftMotor.setPower(power);
    }

    public void setPowerRight(double power) {
        rightMotor.setPower(power);
    }

    // This encapsulates the module

    /**
     * Creates an operation that drives the robot forward for 'second' seconds
     * @param seconds the amount of time to drive forward in seconds
     * @return an operation
     */
    public Operation driveForward(double seconds) {
        return new TimeForward(this, seconds);
    }

    /**
     * Creates an operation that drives the robot in an arc for 'second' seconds
     * @param seconds the amount of time to drive in an arc in seconds
     * @param leftPower the amount of power given to the left side
     * @param rightPower the amount of power given to the right side
     * @return an operation
     */
    public Operation arc(double seconds, double leftPower, double rightPower) {
        return new Arc(this, seconds, leftPower, rightPower);
    }

    private class Arc implements Operation {
        private DriveModule driveModule;
        private double seconds;
        private double leftPower;
        private double rightPower;
        private ElapsedTime time;
        private double targetTime;
        private boolean running;

        public Arc (DriveModule driveModule, double seconds, double leftPower, double rightPower){
            this.driveModule = driveModule;
            this.seconds = seconds;
            this.leftPower = leftPower;
            this.rightPower = rightPower;
            this.time = new ElapsedTime();
        }

        @Override
        public void start(Sequence.Callback callback) {
            if (driveModule.locked){
                callback.err(new DeviceLockedException(this));
            }else{
                driveModule.locked = true;
                running = true;
                targetTime = time.time() + seconds;
                driveModule.setPowerLeft(leftPower);
                driveModule.setPowerRight(rightPower);
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            if (time.time() > targetTime) {
                stop(callback);
            }
        }

        @Override
        public void stop(Sequence.Callback callback) {
            if(running) {
                driveModule.locked = false;
                driveModule.setPowerLeft(0);
                driveModule.setPowerRight(0);
                callback.next();
            } else {
                callback.err(new OperationNotRunningException(this));
            }
        }
    }

    // This is a module
    private class TimeForward implements Operation {
        private DriveModule driveModule;
        private double seconds;
        private double targetTime;
        private ElapsedTime runtime;

        private boolean running;

        public TimeForward(DriveModule driveModule, double seconds) {
            this.driveModule = driveModule;
            this.runtime = new ElapsedTime();
            this.seconds = seconds;
        }

        @Override
        public void start(Sequence.Callback callback) {
            if(driveModule.locked) {
                callback.err(new DeviceLockedException(this));
            } else {
                driveModule.locked = true;
                running = true;

                this.targetTime = runtime.time() + seconds;

                driveModule.setPowerLeft(1);
                driveModule.setPowerRight(1);
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            if(runtime.time() > targetTime) {
                stop(callback);
            }
        }

        @Override
        public void stop(Sequence.Callback callback) {
            if(running) {
                driveModule.locked = false;
                driveModule.setPowerLeft(0);
                callback.next();
            } else {
                callback.err(new OperationNotRunningException(this));
            }
        }
    }
}
