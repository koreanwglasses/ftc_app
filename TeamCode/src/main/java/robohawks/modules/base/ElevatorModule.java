package robohawks.modules.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.async.error.DeviceLockedException;
import robohawks.utils.MathX;

/**
 * Created by paarth on 10/24/16.
 */
public class ElevatorModule {
    private DcMotor elevator;

    private boolean locked;

    public ElevatorModule(HardwareMap hardwareMap){
        elevator = hardwareMap.dcMotor.get("elevator");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    private void setPower(double power){
        elevator.setPower(power);
    }

    public Operation elevate(){ return new Elevate(this); }

    private class Elevate implements Operation{
        private ElevatorModule elevatorModule;
        private ElapsedTime elapsedTime;

        private double initialTime;

        public Elevate(ElevatorModule elevatorModule){
            this.elevatorModule = elevatorModule;
            this.elapsedTime = new ElapsedTime();
        }

        @Override
        public void start(Sequence.Callback callback) {
            if(elevatorModule.locked) {
                callback.err(new DeviceLockedException(this));
            } else {
                elevatorModule.locked = true;
                elevatorModule.setPower(1);

                initialTime = elapsedTime.milliseconds();
            }
        }

        @Override
        public void loop(Sequence.Callback callback) {
            double dtime = elapsedTime.milliseconds() - initialTime;
            if (dtime > 8000){
                stop(callback);
            }else if(dtime > 4000){
                double pow = 1 - MathX.expScale((dtime - 4000) / 4000, .4);
                elevatorModule.setPower(pow);
            }
        }

        @Override
        public void stop(Sequence.Callback callback) {
            elevatorModule.locked = false;
            elevatorModule.setPower(0);
            callback.next();
        }
    }
}
