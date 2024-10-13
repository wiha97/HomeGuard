package Models;

import JWutil.Print;
import JWutil.Validate;
import Managers.CentralUnit;
import Security.EntryDetector;

public class EntryPoint {
    protected String name;
    protected Room room;
    protected EntryDetector detector;
    protected boolean isOpen;
    protected boolean isBroken;

    public EntryPoint(Room room) {
        this.room = room;
    }

    public void menu() {
        boolean loop = true;
        while (loop) {
            Print.clear();
            Print.line(String.format("""
                    %s
                        %s
                        [1] %s
                        [2] %s
                        [3] %s""", Print.title(name, CentralUnit.getNotification()), Print.back(),
                    !isOpen ? "[O]pen" : "[C]lose",
                    !isBroken ? "[D]estroy" : "[F]ix",
                    detector.isActive() ? "De[A]ctivate" : "[A]ctivate"));
            String option = Validate.option("1oc2df3a");
            switch (option) {
                case "1":
                    open();
                    break;
                case "o":
                    open();
                    break;
                case "c":
                    open();
                    break;
                case "2":
                    breach();
                    break;
                case "d":
                    breach();
                    break;
                case "f":
                    breach();
                    break;
                case "3":
                    detector.toggle();
                    break;
                case "a":
                    detector.toggle();
                    break;
                case "q":
                    loop = false;
                    break;
            }
        }
    }

    public void open() {
        isOpen = !isOpen;
    }

    public void breach() {
        isBroken = !isBroken;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return room.getName() + " " + name;
    }
}
