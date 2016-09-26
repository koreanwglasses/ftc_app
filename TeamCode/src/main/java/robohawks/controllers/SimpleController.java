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
        SimpleModule simpleModule = new SimpleModule();
        DriveModule driveModuleModule = new DriveModule(null);
        sequencer.begin(driveModuleModule.driveForward(2));
    }
}
