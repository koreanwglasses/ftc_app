package robohawks.modules.base;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.error.DeviceLockedException;
import robohawks.utils.Color;

/**
 * Attempt to make a module myself --Paarth Tandon
 */
public class ColorModule {
    private ColorSensor colorSensor1;

    public ColorModule(HardwareMap hardwareMap){
        colorSensor1 = hardwareMap.colorSensor.get("colorSensor1");
    }

// This "Operation" is just a method—it's instant, and it returns a value. There is no point in making it an asynchronous operation
//    public Operation getColor() {return new GetColor(this);}

    public Color getColor() {
        return Color.fromArgb(colorSensor1.argb());
    }

}
