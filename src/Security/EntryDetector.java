package Security;

import Interfaces.Alarm;
import Managers.CentralUnit;
import Models.EntryPoint;

public class EntryDetector extends Detector implements Alarm {
    protected EntryPoint entryPoint;
    private boolean softTrigger;

    public EntryDetector(EntryPoint entryPoint){
        this.entryPoint = entryPoint;
    }

    public void softTrigger(){
        softTrigger = true;
        CentralUnit.setNotification(entryPoint + " was opened");
    }

    @Override
    public void trigger() {
        isTriggered = true;
        CentralUnit.setNotification(entryPoint + " was broken");
    }

    @Override
    public void detect() {
        if((isTriggered || softTrigger) && !entryPoint.isOpen() && !entryPoint.isBroken())
            reset();
        if(entryPoint.isBroken() && !isTriggered)
            trigger();
        else if(entryPoint.isOpen() && !isTriggered)
            softTrigger();
    }

    @Override
    public void reset() {
        softTrigger = false;
        isTriggered = false;
    }

    public boolean isSoftTrigger(){
        return softTrigger;
    }
}
