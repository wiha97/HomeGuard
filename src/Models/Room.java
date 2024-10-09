package Models;

import JWutil.Print;
import JWutil.Validate;
import Managers.CentralUnit;
import Security.*;

import java.util.Arrays;

public class Room extends Area {
    protected String name;
    protected EntryPoint[] entryPoints = new EntryPoint[]{};
    protected Detector[] detectors = new Detector[]{};
    private Area linkedArea;
    private boolean isOnFire;
    private boolean isBreakIn;
    private boolean hasMovement;

    public Room() {
    }

    public Room(String bluePrint, int level) {
        super(bluePrint, level);
    }

    public Room(String bluePrint, int level, String name) {
        super(bluePrint, level);
        this.name = name;
    }

    public void menu() {
        boolean loop = true;
        while (loop) {
            Print.clear();
            Print.line(String.format("""
                    %s
                    %s
                        %s
                        [1] [O]pen door
                        [2] [C]lose door
                        [3] set room on [F]ire""", Print.title(name, CentralUnit.getNotification()), statColor(bluePrint.replace("\n\n", "")), Print.back()));

            String option = Validate.option("1o2cfr");
            switch (option) {
                case "1":
                    enterRoom();
                    break;
                case "o":
                    enterRoom();
                    break;
                case "2":
                    exitRoom();
                    break;
                case "c":
                    exitRoom();
                    break;
                case "3":
                    isOnFire = true;
                    break;
                case "f":
                    isOnFire = true;
                    break;
                case "q":
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void enterRoom() {
        Print.clear();
        Door door = getDoor();
        if (door != null) {
            door.open();
        } else
            Print.line(Print.warning("No entrypoints"));
    }

    private void exitRoom() {
        Print.clear();
        Door door = getDoor();
        if (door != null) {
            door.close();
        } else
            Print.line(Print.warning("No exitpoints"));
    }

    private Door getDoor() {
        return ((Door) Arrays.stream(entryPoints).filter(a -> a instanceof Door).toList().getFirst());
    }

    private void setLinkedStat(int stat) {
        if (linkedArea != null)
            linkedArea.setStatus(stat);
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        for (Detector det : detectors) {
            if (det.isActive()) {
                if (det.isTriggered()) {
                    if (det instanceof SmokeDetector) {
                        setLinkedStat(3);
                        return 3;
                    }
                    setLinkedStat(2);
                    return 2;
                } else if (det instanceof MotionDetector)
                    status = 1;
            }
        }
        for (EntryPoint ep : entryPoints) {
            if (ep.detector.isTriggered()) {
                setLinkedStat(2);
                return 2;
            }
        }
        setLinkedStat(status);
        return status;
    }

    public EntryPoint[] getEntryPoints() {
        return entryPoints;
    }

    public void setEntryPoints(EntryPoint[] entryPoints) {
        this.entryPoints = entryPoints;
    }

    public void setDetectors(Detector[] detectors) {
        this.detectors = detectors;
    }

//    public void setStatus(int status) {
//        this.status = status;
//        if(linkedArea != null)
//            linkedArea.status = status;
//    }

    public void setLinkedArea(Area linkedArea) {
        this.linkedArea = linkedArea;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public boolean isHasMovement() {
        return hasMovement;
    }

    public void setOnFire() {
        isOnFire = true;
    }

    public void extinguishFire() {
        isOnFire = false;
//        setStatus(1);
    }

    public void setHasMovement(boolean hasMovement) {
        this.hasMovement = hasMovement;
    }

    public boolean isBreakIn() {
        return isBreakIn;
    }

    public void setBreakIn(boolean breakIn) {
        isBreakIn = breakIn;
    }
}
