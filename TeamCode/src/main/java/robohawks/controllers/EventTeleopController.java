package robohawks.controllers;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import robohawks.async.error.ErrorArgs;
import robohawks.async.error.ErrorHandler;
import robohawks.async.io.Buttons;
import robohawks.modules.base.ActuatorModule;
import robohawks.modules.base.LiftModule;
import robohawks.utils.MathX;
import robohawks.async.Sequence;
import robohawks.modules.base.DriveModule;
import robohawks.modules.base.LaunchModule;

/**
 * Created by fchoi on 10/13/2016.
 */
@TeleOp(name="Event", group ="Teleop")
public class EventTeleopController extends TeleopController implements ErrorHandler{
    DriveModule driveModule;
    LaunchModule launchModule;
    ActuatorModule actuatorModule;
    LiftModule liftModule;

    boolean launchTriggerState;
    double launchPower = 0;
    Sequence launchSequence;

    Sequence loadDecelSequence;

    float threshold = .1f;

    @Override
    public void init() {
        driveModule = new DriveModule(hardwareMap);
        launchModule = new LaunchModule(hardwareMap);
        actuatorModule = new ActuatorModule(hardwareMap);
        liftModule = new LiftModule(hardwareMap);

        actuatorModule.initialize();
    }

    @Override
    public void loop() {
        super.loop();

        // Drive
        double x, p;
        if(Math.abs(gamepad1.left_stick_x) > threshold) {
            x = MathX.expScale(gamepad1.left_stick_x, .8);
        } else {
            x = 0;
        }

        if(Math.abs(gamepad1.right_trigger) > threshold) {
            p = MathX.expScale(gamepad1.right_trigger, 2);
        } else {
            p = 0;
        }
        if(Math.abs(gamepad1.left_trigger) > threshold) {
            p -= MathX.expScale(gamepad1.left_trigger, 2);
        }
        driveModule.setHeadingXP(x, p);
        // End Drive

        // LaunchRev
        if(gamepad2.right_trigger > threshold) {
            launchPower = 1;
        } else {
            launchPower = 0;
        }
        if(launchPower > threshold && !launchTriggerState) {
            if(launchSequence != null) {
                launchSequence.terminate();
            }
            launchSequence = sequencer.begin(launchModule.launchRev(launchPower));
            launchSequence.setErrorHandler(this);
        }
        if (launchPower <= threshold && launchTriggerState) {
            if(launchSequence != null) {
                launchSequence.terminate();
            }
            launchSequence = sequencer.begin(launchModule.launchDecel());
            launchSequence.setErrorHandler(this);
        }
        launchTriggerState = launchPower > threshold;
        // End LaunchRev



        telemetry.addData("Heading", x + ", " + p);
        telemetry.addData("Power", launchModule.getLaunchPower());
        if(launchPower <= threshold && launchModule.getLaunchPower() > 0) {
            telemetry.addData("Info", "Decelerating...");
        }
    }

    @Override
    public void handleError(Sequence sequence, ErrorArgs error) {
        telemetry.addData("Error", sequence.toString() + " - " + error);
    }

    @Override
    public boolean buttonDown(int controller, int buttonCode) {
        if(controller == 1) {
            switch (buttonCode) {
                case Buttons.dpad_up:
                    launchModule.setFeedPower(0.3);
                    break;
                case Buttons.dpad_down:
                    launchModule.setFeedPower(-0.3);
                    break;
                default:
                    return false;
            }
        } else {
            switch (buttonCode) {
                case Buttons.x:
                    if(loadDecelSequence != null) {
                        loadDecelSequence.terminate();
                        loadDecelSequence = null;
                    }
                    launchModule.setLoadPower(-1);
                    break;
                case Buttons.y:
                    if(loadDecelSequence != null) {
                        loadDecelSequence.terminate();
                        loadDecelSequence = null;
                    }
                    launchModule.setLoadPower(1);
                    break;
                case Buttons.dpad_up:
                    liftModule.setPower(.5);
                case Buttons.dpad_down:
                    liftModule.setPower(-.5);
                default:
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean buttonUp(int controller, int buttonCode) {
        if(controller == 1) {
            switch (buttonCode) {
                case Buttons.dpad_up:
                    if(!gamepad1.dpad_down) {
                        launchModule.setFeedPower(0);
                    }
                    break;
                case Buttons.dpad_down:
                    if(!gamepad1.dpad_up) {
                        launchModule.setFeedPower(0);
                    }
                    break;
                case Buttons.y:
                    actuatorModule.toggleActuatorLeft();
                    break;
                case Buttons.x:
                    actuatorModule.toggleActuatorRight();
                    break;
                default:
                    return false;
            }
        } else {
            switch (buttonCode) {
                case Buttons.x:
                case Buttons.y:
                    if(!gamepad2.y && !gamepad2.x) {
                        if (loadDecelSequence != null) {
                            loadDecelSequence.terminate();
                        }
                        loadDecelSequence = sequencer.begin(launchModule.loadDecel());
                        loadDecelSequence.setErrorHandler(this);
                        break;
                    }
                case Buttons.dpad_up:
                    if(!gamepad2.dpad_down) {
                        liftModule.setPower(0);
                    }
                    break;
                case Buttons.dpad_down:
                    if(!gamepad2.dpad_up) {
                        liftModule.setPower(0);
                    }
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean joystickMoved(int controller, int joystickCode, float x, float y) {
        return false;
    }

    @Override
    public boolean triggerPressed(int controller, int triggerCode, float value) {
        return false;
    }
}
