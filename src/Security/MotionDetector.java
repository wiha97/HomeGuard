package Security;

import Interfaces.Alarm;
import Managers.CentralUnit;
import Models.Room;

public class MotionDetector extends AreaDetector implements Alarm {
    public MotionDetector(Room room){
        super(room);
        CentralUnit.getDetectors().add(this);
        name = room.getName() + " motion detector";
    }

    public void trigger() {
        room.setStatus(2);
        isTriggered = true;
        CentralUnit.sirens("Motion detected in " + room.getName());
    }

    @Override
    public void detect() {
        if(room.isHasMovement()) {
            trigger();
        }
        else if(isTriggered)
            reset();

    }

    @Override
    public void reset() {
        isTriggered = false;
        room.setStatus(1);
    }
}
