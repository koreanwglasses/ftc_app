package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import robohawks.async.Module;
import robohawks.async.Sequence;
import robohawks.async.error.DeviceLockedException;

/**
 * Created by fchoi on 9/25/2016.
 */
public class Drive {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private boolean locked;

    public Drive(HardwareMap hwMap) {
    }

    public void setPowerLeft(double power) {
        leftMotor.setPower(power);
    }

    public void setPowerRight(double power) {
        rightMotor.setPower(power);
    }

    public Module leftTime(double seconds) {
        return null;
    }

    public void rightTime(double seconds) {

    }

    public void driveForwardTime(double seconds) {

    }

    private class LeftTime implements Module {
        private Drive drive;
        private double seconds;

        public LeftTime(Drive drive, double seconds) {
            this.drive = drive;
        }

        @Override
        public void start(Sequence.Callback callback) {
            drive.setPowerLeft(1);
        }

        @Override
        public void loop(Sequence.Callback callback) {
            drive.setPowerLeft(0);
            callback.next();
        }
    }
}
