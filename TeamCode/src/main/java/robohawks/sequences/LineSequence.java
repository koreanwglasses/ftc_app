package robohawks.sequences;

import robohawks.async.Operation;
import robohawks.async.Sequence;
import robohawks.modules.base.ColorModule;
import robohawks.modules.base.DriveModule;
import robohawks.utils.Color;

/**
 * Created by fchoi on 1/14/2017.
 */
public class LineSequence implements Operation{

    private ColorModule colorModule;
    private DriveModule driveModule;

    private boolean left;
    private boolean foundLine;

    public LineSequence(ColorModule colorModule, DriveModule driveModule, boolean left) {
        this.colorModule = colorModule;
        this.driveModule = driveModule;

        this.left = left;
    }

    @Override
    public void start(Sequence.Callback callback) {
        driveModule.setHeadingXP(0, 0.7);
        foundLine = false;
    }

    @Override
    public void loop(Sequence.Callback callback) {
        if(!foundLine) {
            if(colorModule.isWhitenotBlack()) {
                foundLine = true;
            }
        } else {
            if(!colorModule.isWhitenotBlack()) {
                if(left) {
                    driveModule.setHeadingXP(-0.5, 0.7);
                } else {
                    driveModule.setHeadingXP(0.5, 0.7);
                }
            } else {
                driveModule.setHeadingXP(0, 0.7);
            }
        }
    }

    @Override
    public void stop(Sequence.Callback callback) {

    }
}
