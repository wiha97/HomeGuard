package Security;

import Interfaces.Alarm;
import JWutil.Print;
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
        CentralUnit.setNotification(Print.warning("Motion detected in " + room.getName()), 1);
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
        CentralUnit.setNotification(Print.good(this + " reset"), 3);
    }
}
