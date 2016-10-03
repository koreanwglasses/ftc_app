package robohawks.controllers;

import robohawks.async.Sequencer;
import robohawks.modules.SimpleModule;
import robohawks.modules.base.DriveModule;

/**
 * Created by fchoi on 9/26/2016.
 */
public class SimpleController extends Controller{

    @Override
    protected void init(Sequencer sequencer) {
        DriveModule driveModule = new DriveModule(null);
        SimpleModule simpleModule = new SimpleModule(sequencer, driveModule);
        sequencer.begin(driveModule.drive(2, 1, 1));
    }
}
