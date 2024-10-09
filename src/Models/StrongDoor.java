package Models;

import Security.DoorDetector;
import Security.EntryDetector;
import Security.PinPad;

public class StrongDoor extends Door {
    private PinPad pinPad;

    public StrongDoor(Room room){
        super(room);
        name = "secure door";
        pinPad = new PinPad(this);
    }

    @Override
    public void open() {
        if(detector.isActive()) {
            if (!pinPad.enterPin()) {
                isOpen = true;
            }
            else
                isOpen = false;
        }
    }
}
