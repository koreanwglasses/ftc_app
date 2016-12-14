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

    private boolean servo1Extended;
    private boolean servo2Extended;

    private boolean locked;

    public ButtonModule(HardwareMap hardwareMap){
        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");
    }

    public boolean isServo1Extended() {
        return servo1Extended;
    }

    public boolean isServo2Extended() {
        return servo2Extended;
    }

    public void setServo1(boolean extended) {
        servo1Extended = extended;
        if(extended) {
            servo1.setPosition(1);
        } else {
            servo1.setPosition(0);
        }
    }

    public void setServo2(boolean extended) {
        servo2Extended = extended;
        if(extended) {
            servo2.setPosition(1);
        } else {
            servo2.setPosition(0);
        }
    }

    public void toggleServo1() {
        setServo1(!isServo1Extended());
    }

    public void toggleServo2() {
        setServo2(!isServo2Extended());
    }
}
