package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import robohawks.async.Operation;
import robohawks.async.Sequence;

/**
 * Created by fchoi on 10/13/2016.
 */
public class LaunchModule {
    private DcMotor wheel;

    public LaunchModule(HardwareMap hwMap) {
        wheel = hwMap.dcMotor.get("launchWheel");
    }

    public void setWheelPower(double power) {
        wheel.setPower(power);
    }

    private class Launch implements Operation {

        private LaunchModule launchModule;

        public Launch(LaunchModule launchModule) {
            this.launchModule = launchModule;
        }

        @Override
        public void start(Sequence.Callback callback) {

        }

        @Override
        public void loop(Sequence.Callback callback) {

        }

        @Override
        public void stop(Sequence.Callback callback) {

        }
    }
}
