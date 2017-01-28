package robohawks.controllers.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robohawks.async.Sequence;
import robohawks.controllers.Controller;
import robohawks.modules.base.RangeModule;

/**
 * Created by paarth on 1/28/17.
 */
@Autonomous(name = "RangeSensorController", group = "Test")
public class RangeSensorController extends Controller {

    Sequence mainSequence;

    @Override
    public void init() {
        RangeModule rangeModule = new RangeModule(hardwareMap);
        double data = rangeModule.getUltrasonicSensor();

        telemetry.addData("Range:\t", Double.toString(data));
    }

    @Override
    public void loop() {
        super.loop();
    }

}
