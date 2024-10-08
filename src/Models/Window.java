package Models;

import Security.WindowDetector;

public class Window extends EntryPoint {
    private boolean isShattered;

    public Window(Room room) {
        super(room);
        name = room.getName() + " window";
        detector = new WindowDetector(this);
    }

    public void shatter(){
        isShattered = true;
    }


    public boolean isShattered() {
        return isShattered;
    }
}
