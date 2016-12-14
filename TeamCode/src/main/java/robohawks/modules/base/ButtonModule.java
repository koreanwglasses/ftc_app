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

    public Operation turn(int servox){return new Turn(this, servox);}

    private class Turn implements Operation{
        private ButtonModule buttonModule;
        private int servox;

        public Turn(ButtonModule buttonModule, int servox){

            this.buttonModule = buttonModule;
            this.servox = servox;
        }

        @Override
        public void start(Sequence.Callback callback) {
            if (buttonModule.locked == true){
                callback.err(new DeviceLockedException(this));
            } else {
                buttonModule.locked = true;
                if(servox == 1){
                    servo1.setPosition(1);
                } else {
                    servo2.setPosition(1);
                }

            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            stop(callback);
        }

        @Override
        public void stop(Sequence.Callback callback) {
            if (servox == 1){
                servo1.setPosition(0);
            } else {
                servo2.setPosition(0);
            }

            buttonModule.locked = false;
            callback.next();
        }
    }

}
