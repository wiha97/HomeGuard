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
        name = room.getName() + " smoke detector";
        sprinkler = new Sprinkler(room);
        CentralUnit.getDetectors().add(this);
    }

    public void trigger() {
        room.setStatus(3);
        CentralUnit.sirens("Smoke detected in " + room.toString());
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
        room.setStatus(0);
    }
}