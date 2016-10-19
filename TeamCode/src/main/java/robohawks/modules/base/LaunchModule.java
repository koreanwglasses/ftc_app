package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import robohawks.utils.MathX;
import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.error.DeviceLockedException;

/**
 * This module launches stuff --Fred Choi 2016
 */
public class LaunchModule {
    private DcMotor motor1;
    private DcMotor motor2;

    private boolean locked;

    public LaunchModule(HardwareMap hwMap) {
        motor1 = hwMap.dcMotor.get("launchMotorWheel1");
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor2 = hwMap.dcMotor.get("launchMotorWheel2");
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setWheelPower(double power) {
        motor1.setPower(power);
        motor2.setPower(-power);
    }

    public Operation launch() {
        return new Launch(this);
    }

    private class Launch implements Operation {
        private LaunchModule launchModule;
        private ElapsedTime time;

        private double initialTime;

        public Launch(LaunchModule launchModule) {
            this.launchModule = launchModule;
            this.time = new ElapsedTime();
        }

        @Override
        public void start(Sequence.Callback callback) {
            if(launchModule.locked) {
                callback.err(new DeviceLockedException(this));
            } else {
                launchModule.locked = true;
                launchModule.setWheelPower(1);

                initialTime = time.milliseconds();
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            double dtime = time.milliseconds() - initialTime;
            if(dtime > 8000) {
                stop(callback);
            } else if(dtime > 4000) {
                double pow = 1 - MathX.expScale((dtime - 4000) / 4000, .4);
                launchModule.setWheelPower(pow);
            }
        }

        @Override
        public void stop(Sequence.Callback callback) {
            launchModule.locked = false;
            launchModule.setWheelPower(0);
            callback.next();
        }
    }
}
