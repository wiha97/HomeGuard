package Models;

import JWutil.Print;
import Security.*;

public class Door extends EntryPoint{

    private boolean isOpen;

    public Door(Room room) {
        super(room);
        name = room.toString() + " door";
        detector = new DoorDetector(this);
    }

    @Override
    public void open(){
        isOpen = true;
        Print.line(Print.warning(name + " opened"));
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
