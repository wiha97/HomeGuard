package Security;

import JWutil.Print;
import Models.Room;

public class Sprinkler {
    Room room;
    public Sprinkler(Room room){
        this.room = room;
    }
    public void activate(){
        Print.line(room.getName() + " sprinkler: " + Print.NAVY + "FSSSSSSSSSSSSHHHHHHH" + Print.RESET);
        room.setOnFire(false);
    }
}
