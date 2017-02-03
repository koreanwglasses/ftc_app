package robohawks.controllers.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robohawks.async.Sequence;
import robohawks.controllers.Controller;
import robohawks.modules.base.LiftModule;

/**
 * Created by paarth on 2/2/17.
 */

@TeleOp(name = "LiftController", group = "Teleop")
public class LiftController extends Controller{
    LiftModule liftModule;

    boolean liftButtonState;
    Sequence liftSequence;

    @Override
    public void init(){
        liftModule = new LiftModule(hardwareMap);
    }

    @Override
    public void loop() {
        super.loop();

        if(liftSequence != null && liftSequence.isFinished()) {
            liftSequence = null;
        }
        if(gamepad1.a != liftButtonState && gamepad1.a && liftSequence == null) {
            liftSequence = sequencer.begin(liftModule.lift());
        }
        liftButtonState = gamepad1.a;
    }
}
