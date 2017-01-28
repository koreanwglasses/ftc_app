package robohawks.controllers.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import robohawks.async.Sequence;
import robohawks.controllers.Controller;
import robohawks.modules.base.DriveModule;
import robohawks.modules.base.LaunchModule;

/**
 * Created by fchoi on 1/14/2017.
 */
@Autonomous(name="Red Auto Shoot", group ="Competition")
public class RedAutoShootController extends Controller {
    Sequence mainSequence;

    @Override
    public void init() {
        DriveModule driveModule = new DriveModule(hardwareMap);
        LaunchModule launchModule = new LaunchModule(hardwareMap);
        mainSequence = sequencer
            .begin(driveModule.drive(1.5, 0.7, 0.7))
            .then(launchModule.launch(sequencer))
            .then(driveModule.drive(1.5, 0.7, 0.7))
            .then(driveModule.drive(1.5, 0, 0.7))
            .then(driveModule.drive(1, -0.7, -0.7))
            .then(driveModule.drive(1.5, 0.7, 0.7));
    }

    @Override
    public void loop() {
        super.loop();

        if(mainSequence.isFinished()) requestOpModeStop();
    }
}
