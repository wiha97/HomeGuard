package Security;

import Interfaces.Alarm;
import Managers.CentralUnit;
import Models.Room;

public class MotionDetector extends AreaDetector implements Alarm {
    public MotionDetector(Room room){
        super(room);
        CentralUnit.getDetectors().add(this);
        name = "motion detector";
    }

    public void trigger() {
        isTriggered = true;
//        CentralUnit.sirens("Motion detected in " + room.getName());
        CentralUnit.setNotification("Motion detected in " + room.getName());
    }

    @Override
    public void detect() {
        if(room.isHasMovement() && !isTriggered) {
            trigger();
        }
        else if(isTriggered && !room.isHasMovement())
            reset();
    }

    @Override
    public void reset() {
        room.setHasMovement(false);
        isTriggered = false;
    }
}
