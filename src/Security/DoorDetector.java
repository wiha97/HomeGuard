package Security;

import Interfaces.Alarm;
import Managers.CentralUnit;
import Models.Door;

public class DoorDetector extends EntryDetector implements Alarm {
    private boolean isTriggered;

    public DoorDetector(Door door){
        super(door);
        name = door.toString() + " door detector";
        CentralUnit.getDetectors().add(this);
    }

    public void trigger() {
        isTriggered = true;
        entryPoint.getRoom().setStatus(2);
    }

    public void detect() {
        if(entryPoint.isOpen() && !isTriggered)
            trigger();
        else if(isTriggered)
            reset();
    }

    public void reset() {
        isTriggered = false;
        entryPoint.getRoom().setStatus(1);
    }
}
