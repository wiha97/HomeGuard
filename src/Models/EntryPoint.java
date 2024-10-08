package Models;

import Security.EntryDetector;

public class EntryPoint {
    protected String name;
    protected Room room;
    protected EntryDetector detector;
    protected boolean isOpen;

    public EntryPoint(Room room){
        this.room = room;
    }

    public void open(){
        isOpen = true;
    }

    public void close(){
        isOpen = false;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isOpen(){
        return isOpen;
    }

    @Override
    public String toString() {
        return name;
    }
}
