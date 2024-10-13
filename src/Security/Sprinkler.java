package Security;

import JWutil.App;
import JWutil.Print;
import Managers.CentralUnit;
import Models.Room;

public class Sprinkler {
    Room room;
    public Sprinkler(Room room){
        this.room = room;
    }
    public void activate(){
        App.sleep(500);
        CentralUnit.setNotification(room.getName() + " sprinkler: " + Print.NAVY + "FSSSSSSSSSSSSHHHHHHH" + Print.RESET, 1);
        App.sleep(500);
        room.extinguishFire();
        CentralUnit.setNotification(Print.good(room.getName() + " fire extinguished!"),2);
    }
}
