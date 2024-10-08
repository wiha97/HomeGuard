package Models;

import Security.PinPad;

public class StrongDoor extends Door {
    private PinPad pinPad;

    public StrongDoor(Room room){
        super(room);
        name = room.getName() + " strong door";
        pinPad = new PinPad();
    }

    @Override
    public void open() {
        if(!pinPad.enterPin()) {
            room.setStatus(3);
        }
    }
}
