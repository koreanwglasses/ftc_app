package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by paarth on 1/28/17.
 */
public class RangeModule {

    private OpticalDistanceSensor opticalDistanceSensor;
    private UltrasonicSensor ultrasonicSensor;

    public RangeModule(HardwareMap hardwareMap){
        this.opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("optic");
        this.ultrasonicSensor = hardwareMap.ultrasonicSensor.get("ultra");
    }

    public float

}
