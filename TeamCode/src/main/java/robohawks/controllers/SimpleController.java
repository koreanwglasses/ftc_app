package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import robohawks.async.Sequencer;
import robohawks.modules.SimpleModule;
import robohawks.modules.base.DriveModule;

/**
 * Created by fchoi on 9/26/2016.
 */
@Autonomous(name="Simple", group ="Sample")
public class SimpleController extends Controller{
    @Override
    public void init() {
        DriveModule driveModule = new DriveModule(hardwareMap);
        sequencer.begin(driveModule.drive(2, 1, 1)).then(driveModule.drive(2, 1, .5));
    }
}
