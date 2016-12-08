package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.error.DeviceLockedException;

/**
 * Created by paarth on 12/5/16.
 */
public class ButtonModule {
    private Servo servo1;
    private Servo servo2;

    private boolean locked;

    public ButtonModule(HardwareMap hardwareMap){
        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");
    }

    public Operation turn(Servo servo, double turnTime){return new Turn(this, servo, turnTime);}

    private class Turn implements Operation{
        private ButtonModule buttonModule;
        private ElapsedTime elapsedTime;

        private double initialTime;
        private double turnTime;
        private Servo servo;

        public Turn(ButtonModule buttonModule, Servo servo, double turnTime){

            this.buttonModule = buttonModule;
            this.turnTime = turnTime;

            this.elapsedTime = new ElapsedTime();

        }

        @Override
        public void start(Sequence.Callback callback) {
            if (buttonModule.locked == true){
                callback.err(new DeviceLockedException(this));
            } else {
                buttonModule.locked = true;
                servo.setPosition(servo.getPosition() + 180.0);

                initialTime = elapsedTime.milliseconds();
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            if (elapsedTime.milliseconds() > initialTime + turnTime){
                stop(callback);
            }
        }

        @Override
        public void stop(Sequence.Callback callback) {
            servo.setPosition(servo.getPosition() - 180.0);
            buttonModule.locked = false;
            callback.next();
        }
    }

}
