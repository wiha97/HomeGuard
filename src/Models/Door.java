package Models;

import JWutil.Print;
import Security.*;

public class Door extends EntryPoint{

    public Door(Room room) {
        super(room);
        name = "door";
        detector = new DoorDetector(this);
    }
}
