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

    public void setActuator1(boolean extended){
        actuator1Extended = extended;

        if(extended){
            actuator1.setPosition(0);
        } else {
            actuator1.setPosition(1);
        }
    }

    public void setActuator2(boolean extended) {
        actuator2Extended = extended;

        if (extended){
            actuator2.setPosition(0);
        } else {
            actuator2.setPosition(1);
        }
    }

    public void setActuator1(){ setActuator1(!isActuator1Extended());}

    public void setActuator2(){ setActuator2(!isActuator2Extended());}
}
