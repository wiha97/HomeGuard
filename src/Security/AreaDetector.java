package Security;

import Managers.CentralUnit;
import Models.Room;

public class AreaDetector extends Detector {
    protected Room room;

    public AreaDetector(Room room){
        this.room = room;
    }
}
