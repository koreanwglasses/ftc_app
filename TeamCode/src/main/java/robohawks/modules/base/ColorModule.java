package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.I2cAddr;
import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.SimpleOperation;
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
//        buttonSensor.setI2cAddress(I2cAddr.create8bit(0x40));
//        buttonSensor.setI2cAddress(I2cAddr.create7bit(0x20));

        lineSensorLeft = hardwareMap.colorSensor.get("lineSensorLeft");
//        lineSensorLeft.setI2cAddress(I2cAddr.create8bit(0x3e));
//        lineSensorLeft.setI2cAddress(I2cAddr.create7bit(0x1f));

        lineSensorRight = hardwareMap.colorSensor.get("lineSensorRight");
//        lineSensorRight.setI2cAddress(I2cAddr.create8bit(0x3c));
//        lineSensorRight.setI2cAddress(I2cAddr.create7bit(0x1e));
    }

    public void initialize() {
        buttonSensor.enableLed(false);
        lineSensorLeft.enableLed(true);
        lineSensorRight.enableLed(true);
    }

    public Color getButtonColor() {
        return Color.fromArgb(buttonSensor.argb());
    }

    public int getColorArgb() {
        return buttonSensor.argb();
    }

    public Color getLeftColor() {
        return Color.fromArgb(lineSensorLeft.argb());
    }

    public Color getRightColor() {
        return Color.fromArgb(lineSensorRight.argb());
    }

    public void setLight(boolean lightOn) {
        buttonSensor.enableLed(lightOn);
    }

    public boolean isRednotBlue() {
        Color color = getButtonColor();
        return color.r > color.b;
    }

    /**
     * Creates an operation that pushes the color (as a boolean) to the sequence
     * @return the operation
     */
    public Operation isRednotBlueOp() {
        return new SimpleOperation() {
            @Override
            public void start(Sequence.Callback callback) {
                callback.next(isRednotBlue());
            }
        };
    }

    public Operation isRednotBlueOp(final boolean flip) {
        return new SimpleOperation() {
            @Override
            public void start(Sequence.Callback callback) {
                callback.next(isRednotBlue() ^ flip);
            }
        };
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
