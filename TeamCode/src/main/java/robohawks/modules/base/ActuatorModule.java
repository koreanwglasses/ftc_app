package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by paarth on 1/23/17.
 */
public class ActuatorModule {
    private Servo actuator1;
    private Servo actuator2;

    public boolean actuator1Extended;
    public boolean actuator2Extended;

    private boolean locked;

    public ActuatorModule(HardwareMap hardwareMap){
        actuator1 = hardwareMap.servo.get("actLeft");
        actuator2 = hardwareMap.servo.get("actRight");
    }

    public boolean isActuator1Extended() {
        return actuator1Extended;
    }

    public boolean isActuator2Extended() {
        return actuator2Extended;
    }

    public void toggleActuator1(boolean extended){
        actuator1Extended = extended;

        if(extended){
            actuator1.setPosition(1);
        } else {
            actuator1.setPosition(0);
        }
    }

    public void toggleActuator2(boolean extended) {
        actuator2Extended = extended;

        if (extended){
            actuator2.setPosition(1);
        } else {
            actuator2.setPosition(0);
        }
    }

    public void toggleActuator1(){ toggleActuator1(!isActuator1Extended());}

    public void toggleActuator2(){ toggleActuator2(!isActuator2Extended());}
}
