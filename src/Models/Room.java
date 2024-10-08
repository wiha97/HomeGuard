package Models;

import JWutil.Print;
import JWutil.Validate;
import Security.*;

import java.util.Arrays;

public class Room extends Area {
    protected String name;
    protected EntryPoint[] entryPoints;
    protected Detector[] detectors;
    private Area linkedArea;
    private boolean isOnFire;
    private boolean hasMovement;

    public Room(){
    }

    public Room(String bluePrint, int level){
        super(bluePrint,level);
    }

    public Room(String bluePrint, int level, String name){
        super(bluePrint,level);
        this.name = name;
    }

    public void menu(){
        boolean loop = true;
        while (loop){
            Print.line(String.format("""
                    %s
                    %s
                        %s
                        [1] [E]nter room
                        [2] Set on [F]ire""", Print.title(name), statColor(bluePrint.replace("\n\n", "")), Print.back()));

            String option = Validate.option("1e2f");
            switch (option){
                case "1":
                    //  Very temp
                    if(entryPoints != null) {
                        EntryPoint door = Arrays.stream(entryPoints).filter(a -> a instanceof Door).toList().getFirst();
                        if (door != null)
                            door.open();
                    }
                    else
                        Print.line(Print.warning("No entrypoints"));
                    break;
                case "2":
                    Print.clear();
                    isOnFire = true;
                    break;
                case "q":
                    loop = false;
                    break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public EntryPoint[] getEntryPoints() {
        return entryPoints;
    }

    public void setEntryPoints(EntryPoint[] entryPoints){
        this.entryPoints = entryPoints;
    }

    public void setDetectors(Detector[] detectors){
        this.detectors = detectors;
    }

    public void setStatus(int status) {
        this.status = status;
        if(linkedArea != null)
            linkedArea.status = status;
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

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    public void setHasMovement(boolean hasMovement) {
        this.hasMovement = hasMovement;
    }
}
