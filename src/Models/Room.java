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
            String opts = "sf";
            String str = "";
            for(int i = 0; i < detectors.length; i++){
                str += String.format("        %s (%s)", detectors[i], detectors[i].isActive() ? Print.good("enabled"):Print.warning("disabled"));
                str += "\n";
            }
            for (int i = 1; i <= entryPoints.length; i++) {
                opts += i;
                int idx = i-1;
                str += String.format("    [%s] %s (%s) %s", i, entryPoints[idx].getName(), entryPoints[idx].isOpen() ? Print.warning("open") : Print.good("closed"),
                        (entryPoints[idx] instanceof StrongDoor) ? String.format("(%s)", (entryPoints[idx]).detector.isActive() ? Print.good("enabled"):Print.warning("disabled")):"");
                if (i != entryPoints.length)
                    str += "\n";
            }

            Print.clear();
            Print.line(String.format("""
                    %s
                    %s
                        %s
                    %s
                        [S]et room on [F]ire""", Print.title(name, CentralUnit.getNotification()), statColor(bluePrint.replace("\n\n", "")), Print.back(), str));

            String option = Validate.option(opts);
            switch (option) {
                case "s":
                    isOnFire = true;
                    break;
                case "f":
                    isOnFire = true;
                    break;
                case "q":
                    loop = false;
                    break;
                default:
                    entryPoints[Integer.parseInt(option)-1].menu();
                    break;
            }
        }
    }

    private void setLinkedStat(int stat) {
        if (linkedArea != null)
            linkedArea.setStatus(stat);
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        status = 0;
        for (Detector det : detectors) {
            if (det.isActive()) {
                if (det.isTriggered()) {
                    if (det instanceof SmokeDetector) {
                        setLinkedStat(3);
                        return 3;
                    }
                    status = 2;
                } else if (det instanceof MotionDetector)
                    status = 1;
            }
        }
        for (EntryPoint ep : entryPoints) {
            if (ep.detector.isTriggered()) {
                status = 3;
                break;
            }
            else if(ep.detector.isSoftTrigger()){
                status = 2;
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
    }

    public void setHasMovement(boolean hasMovement) {
        this.hasMovement = hasMovement;
    }
}
