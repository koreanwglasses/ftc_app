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
public class Drive {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private boolean locked;

    // This initializes the bindings
    public Drive(HardwareMap hwMap) {
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
    public Operation driveForward(double seconds) {
        return new TimeForward(this, seconds);
    }

    // This is a module
    private class TimeForward implements Operation {
        private Drive drive;
        private double targetTime;
        private ElapsedTime runtime;

        private boolean running;

        public TimeForward(Drive drive, double seconds) {
            this.drive = drive;
            this.runtime = new ElapsedTime();
            this.targetTime = runtime.time() + seconds;
        }

        @Override
        public void start(Sequence.Callback callback) {
            if(drive.locked) {
                callback.err(new DeviceLockedException(this));
            } else {
                drive.locked = true;
                running = true;

                drive.setPowerLeft(1);
                drive.setPowerRight(1);
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            if(runtime.time() > targetTime) {
                stop(callback);
            }
        }

        public void stop(Sequence.Callback callback) {
            if(running) {
                drive.locked = false;
                drive.setPowerLeft(0);
                callback.next();
            } else {
                callback.err(new OperationNotRunningException(this));
            }
        }
    }
}
