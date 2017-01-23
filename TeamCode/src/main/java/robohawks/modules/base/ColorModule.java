package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import robohawks.utils.Color;

/**
 * Attempt to make a module myself --Paarth Tandon
 */
public class ColorModule {
    private ColorSensor buttonSensor;
    private ColorSensor lineSensorLeft;
    private ColorSensor lineSensorRight;

    public ColorModule(HardwareMap hardwareMap){
        buttonSensor = hardwareMap.colorSensor.get("buttonSensor");
        lineSensorLeft = hardwareMap.colorSensor.get("lineSensorLeft");
        lineSensorRight= hardwareMap.colorSensor.get("lineSensorRight");
    }

// This "Operation" is just a method—it's instant, and it returns a value. There is no point in making it an asynchronous operation
//    public Operation getButtonColor() {return new GetColor(this);}

    public Color getButtonColor() {
        return Color.fromArgb(buttonSensor.argb());
    }

    public int getColorArgb() {
        return buttonSensor.argb();
    }

    public void setLight(boolean lightOn) {
        buttonSensor.enableLed(lightOn);
    }

    public boolean isRednotBlue() {
        Color color = getButtonColor();
        return color.r > color.b;
    }

    public boolean isLeftWhitenotBlack() {
        // todo
        return false;
    }

    public boolean isRightWhitenotBlack() {
        // todo
        return false;
    }
}
