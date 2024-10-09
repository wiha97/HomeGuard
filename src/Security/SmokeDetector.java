package Security;

import Interfaces.Alarm;
import JWutil.Print;
import Managers.CentralUnit;
import Models.Room;

public class SmokeDetector extends AreaDetector implements Alarm {
    Sprinkler sprinkler;

    public SmokeDetector(Room room){
        super(room);
        isActive = true;
        name = "smoke detector";
        sprinkler = new Sprinkler(room);
        CentralUnit.getDetectors().add(this);
    }

    public void trigger() {
        isTriggered = true;
        room.setOnFire();
        CentralUnit.setNotification("Smoke detected in " + room.toString());
        sprinkler.activate();
    }

    public void detect() {
        if(room.isOnFire()) {
            trigger();
        }
        else if(!room.isOnFire() && isTriggered)
            reset();
    }

    public void reset() {
        isTriggered = false;
    }
}
