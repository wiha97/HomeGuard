package Models;

import Managers.CentralUnit;
import Security.EntryDetector;

public class EntryPoint {
    protected String name;
    protected Room room;
    protected EntryDetector detector;
    protected boolean isOpen;
    protected boolean isBroken;

    public EntryPoint(Room room){
        this.room = room;
    }

    public void open(){
        isOpen = true;
    }

    public void close(){
        CentralUnit.setNotification(room.getName() + " closed");
        isOpen = false;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isOpen(){
        return isOpen;
    }
    public boolean isBroken(){
        return isBroken;
    }

    @Override
    public String toString() {
        return name;
    }
}
