package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import robohawks.async.Sequence;
import robohawks.modules.base.DriveModule;

/**
 * Created by fchoi on 9/26/2016.
 */
@Autonomous(name="Park In Middle", group ="Competition")
public class ParkInMiddleController extends Controller{
    Sequence mainSequence;

    @Override
    public void init() {
        DriveModule driveModule = new DriveModule(hardwareMap);
        mainSequence = sequencer.begin(driveModule.drive(4, 1, 0.4));
    }

    @Override
    public void loop() {
        super.loop();

        if(mainSequence.isFinished()) requestOpModeStop();
    }
}
