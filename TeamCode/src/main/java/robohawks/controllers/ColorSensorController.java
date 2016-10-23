package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Sequence;
import robohawks.modules.base.ColorModule;

/**
 * Created by paarth on 10/19/16.
 */

@Autonomous(name="color", group="Sample")
public class ColorSensorController extends Controller {
    ColorModule colorModule;

    @Override
    public void init() {
        this.colorModule = new ColorModule(hardwareMap);
        colorModule.setLight(false);
    }

    @Override
    public void loop() {
        super.loop();

        telemetry.addData("Color", colorModule.getColor());
    }

}
