package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import robohawks.utils.Color;

/**
 * Attempt to make a module myself --Paarth Tandon
 */
public class ColorModule {
    private ColorSensor buttonSensor1;

    public ColorModule(HardwareMap hardwareMap){
        buttonSensor1 = hardwareMap.colorSensor.get("buttonSensor");
    }

// This "Operation" is just a method—it's instant, and it returns a value. There is no point in making it an asynchronous operation
//    public Operation getColor() {return new GetColor(this);}

    public Color getColor() {
        return Color.fromArgb(buttonSensor1.argb());
    }

    public void setLight(boolean lightOn) {
        buttonSensor1.enableLed(lightOn);
    }

    public boolean isRedorBlue(){
        //TODO
    }
}
