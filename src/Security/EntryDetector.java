package Security;

import Interfaces.Alarm;
import Managers.CentralUnit;
import Models.EntryPoint;

public class EntryDetector extends Detector implements Alarm {
    protected EntryPoint entryPoint;

    public EntryDetector(EntryPoint entryPoint){
        this.entryPoint = entryPoint;
    }

    @Override
    public void trigger() {
        isTriggered = true;
        CentralUnit.setNotification(entryPoint.toString() + " opened");
    }

    @Override
    public void detect() {
        if(entryPoint.isOpen() && !isTriggered)
            trigger();
        else if(isTriggered && !entryPoint.isOpen())
            reset();
    }

    @Override
    public void reset() {
        entryPoint.close();
        isTriggered = false;
    }
}
