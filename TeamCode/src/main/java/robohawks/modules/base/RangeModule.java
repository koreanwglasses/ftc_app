package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by paarth on 1/28/17.
 */
public class RangeModule {

    private UltrasonicSensor ultrasonicSensor;

    public RangeModule(HardwareMap hardwareMap){
        this.ultrasonicSensor = hardwareMap.ultrasonicSensor.get("ultra");
    }

    public double getUltrasonicSensor(){
        return ultrasonicSensor.getUltrasonicLevel();
    }

}
