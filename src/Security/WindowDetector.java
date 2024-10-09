package Security;

import Interfaces.Alarm;
import Managers.CentralUnit;
import Models.Window;

public class WindowDetector extends EntryDetector implements Alarm {

    public WindowDetector(Window window) {
        super(window);
        name = "window detector";
        CentralUnit.getDetectors().add(this);
    }

//    @Override
//    public void trigger() {
//        isTriggered = true;
//    }
//
//    @Override
//    public void detect() {
//        if (((Window)entryPoint).isShattered())
//            trigger();
//        else if (isTriggered)
//            reset();
//    }
//
//    @Override
//    public void reset() {
//        isTriggered = false;
//        entryPoint.getRoom().setStatus(1);
//    }
}
