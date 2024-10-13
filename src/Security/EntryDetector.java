package Security;

import Interfaces.Alarm;
import JWutil.Print;
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
        CentralUnit.setNotification(Print.warning(entryPoint + " was opened"),2);
    }

    @Override
    public void trigger() {
        isTriggered = true;
        CentralUnit.setNotification(Print.alert(entryPoint + " was broken"),1);
    }

    @Override
    public void detect() {
        if(isTriggered && !entryPoint.isBroken())
            reset();
        else if(entryPoint.isBroken() && !isTriggered)
            trigger();
        else if(softTrigger && !entryPoint.isOpen())
            reset();
        else if(entryPoint.isOpen() && !softTrigger)
            softTrigger();
    }

    @Override
    public void reset() {
        softTrigger = false;
        isTriggered = false;
        CentralUnit.setNotification(Print.good(this + " reset"), 3);
    }

    public boolean isSoftTrigger(){
        return softTrigger;
    }
}
