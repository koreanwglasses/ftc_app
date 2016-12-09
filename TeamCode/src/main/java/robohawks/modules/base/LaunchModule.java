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
    private DcMotor feedMotor;
    private DcMotor loadMotor;

    private boolean locked;

    public LaunchModule(HardwareMap hwMap) {
        motor1 = hwMap.dcMotor.get("launchMotorWheel1");
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor2 = hwMap.dcMotor.get("launchMotorWheel2");
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        feedMotor = hwMap.dcMotor.get("launchFeedMotor");
        feedMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        loadMotor = hwMap.dcMotor.get("loadMotor");
        loadMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setFeedPower(double power) {
        feedMotor.setPower(power);
    }

    public void setWheelPower(double power) {
        motor1.setPower(power);
        motor2.setPower(-power);
    }

    public void setLoadPower(double power) {
        loadMotor.setPower(power);
    }

    public double getLoadPower() {
        return loadMotor.getPower();
    }

    public Operation load(double loadTime) {
        return new Load(this, loadTime);
    }

    public Operation loadDecel() {
        return new LoadDecel(this);
    }

    public Operation launch(double feedTime) {
        return new Launch(this, feedTime);
    }

    private class Load implements Operation {
        private LaunchModule launchModule;
        private ElapsedTime time;

        private double initialTime;
        private double loadTime;

        public Load(LaunchModule launchModule, double loadTime) {
            this.launchModule = launchModule;
            this.loadTime = loadTime;

            this.time = new ElapsedTime();
        }

        @Override
        public void start(Sequence.Callback callback) {
            if(launchModule.locked == true){
                callback.err(new DeviceLockedException(this));
            } else {
                launchModule.locked = true;
                launchModule.setLoadPower(1);

                initialTime = time.milliseconds();
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            if(time.milliseconds() > initialTime + loadTime){
                stop(callback);
            }
        }

        @Override
        public void stop(Sequence.Callback callback) {
            launchModule.setLoadPower(0);
            launchModule.locked = false;
            callback.next();
        }
    }

    private class LoadDecel implements Operation {
        private LaunchModule launchModule;
        private ElapsedTime time;

        private double targetTime;

        public LoadDecel(LaunchModule launchModule) {
            this.launchModule = launchModule;
            this.time = new ElapsedTime();
        }

        @Override
        public void start(Sequence.Callback callback) {
            if(launchModule.locked) {
                callback.err(new DeviceLockedException(this));
            } else {
                launchModule.locked = true;

                targetTime = time.milliseconds() + 3000 * launchModule.getLoadPower();
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            if(time.milliseconds() > targetTime) {
                stop(callback);
            } else {
                launchModule.setLoadPower((targetTime - time.milliseconds()) / 3000.0);
            }
        }

        @Override
        public void stop(Sequence.Callback callback) {
            launchModule.locked = false;
            launchModule.setLoadPower(0);
            callback.next();
        }
    }

    private class Launch implements Operation {
        private LaunchModule launchModule;
        private ElapsedTime time;

        private double initialTime;
        private double feedTime;

        public Launch(LaunchModule launchModule, double feedTime) {
            this.launchModule = launchModule;
            this.feedTime = feedTime;
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
            if(dtime > 6000 + feedTime) {
                stop(callback);
            } else if(dtime > 2000 + feedTime) {
                double pow = 1 - MathX.expScale((dtime - 2000 - feedTime) / 4000, .4);
                launchModule.setWheelPower(pow);
            } else if(dtime > 1000 + feedTime) {
                launchModule.setFeedPower(0);
            } else if(dtime > 1000) {
                launchModule.setFeedPower(.2);
            }
        }

        @Override
        public void stop(Sequence.Callback callback) {
            launchModule.locked = false;
            launchModule.setWheelPower(0);
            launchModule.setFeedPower(0);
            callback.next();
        }
    }
}
