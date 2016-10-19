package robohawks.modules.base;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.error.DeviceLockedException;

/**
 * Attempt to make a module myself --Paarth Tandon
 */
public class ColorModule {
    private ColorSensor colorSensor1;

    private boolean locked;

    public ColorModule(HardwareMap hardwareMap){
        colorSensor1 = hardwareMap.colorSensor.get("colorSensor1");
    }

    public Operation getColor() {return new GetColor(this);}

    private class GetColor implements Operation{
        private ColorModule colorModule;
        private ArrayList color = new ArrayList();

        public GetColor(ColorModule colorModule){
            this.colorModule = colorModule;
        }

        @Override
        public void start(Sequence.Callback callback) {
            if (colorModule.locked){
                callback.err(new DeviceLockedException(this));
            } else {
                colorModule.locked = true;
                color.add(colorSensor1.red());
                color.add(colorSensor1.green());
                color.add(colorSensor1.blue());
                DbgLog.msg(color.toString());
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {

        }

        @Override
        public void stop(Sequence.Callback callback) {
            colorModule.locked = false;
            callback.next();
        }
    }

}
