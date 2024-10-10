package Models;

import JWutil.Print;
import Security.*;

import java.util.ArrayList;
import java.util.List;

public class House {
    private List<Area> areas;
    private List<Room> rooms;
    private int level = 0;

    public House() {
        fillRooms();
    }

    public void printHouse() {
        int i = 0;
        for (int x = 0; x < 5 * level; x++) {
            for (Area room : areas) {
                String[] rows = room.getBluePrint().split("\n");
                if (rows.length > i) {
                    switch (room.getStatus()) {
                        case 1:
                            Print.same(Print.good(rows[i]));
                            break;
                        case 2:
                            Print.same(Print.warning(rows[i]));
                            break;
                        case 3:
                            Print.same(Print.alert(rows[i]));
                            break;
                        default:
                            Print.same(rows[i]);
                            break;
                    }
                }
            }
            i++;
            Print.line("");
        }
    }

    private void fillRooms() {
        areas = new ArrayList<>();
        Room backYard = new Room("""
                _________________________________________________________________________
                |                    _______________________________                    |
                |                   |   ~~~~    ~~~    ~~~    ~~~~  |                   |
                |      |====|       | ~~    ~~    ~~~     ~~~~~     |                   |
                |      |====|       |   ~~~     Backyard    ~~~~~   |                   |
                |                   |   ~~  ~~~~     ~~~~~~     ~~~ |                   |
                |                   |_______________________________|                   |
                |                                                                       |""", level++, "backyard");
        backYard.setDetectors(new Detector[]{new MotionDetector(backYard)});

        level++;
        Room dinesh = new Room("""
                |_____---______
                |             |
                |    Dinesh   |
                |___________==|""", level, "dinesh");
        dinesh.setDetectors(new Detector[]{new SmokeDetector(dinesh)});
        dinesh.setEntryPoints(new EntryPoint[]{new Window(dinesh), new Door(dinesh)});

        Room erlich = new Room("""
                ___---___---___
                |             |
                |   Erlich    |
                |_==__________|""", level, "erlich");
        erlich.setDetectors(new Detector[]{new SmokeDetector(erlich)});
        erlich.setEntryPoints(new EntryPoint[]{new Window(erlich), new Window(erlich), new Door(erlich)});

        Room kitchen = new Room("""
                ______---______
                |             |
                |   Kitchen   _
                |__         __|""", level, "kitchen");
        kitchen.setDetectors(new Detector[]{new SmokeDetector(kitchen)});
        kitchen.setEntryPoints(new EntryPoint[]{new Window(kitchen)});

        Area livExt = new Area("""
                __-===:===-__
                |           |
                _   Living  I
                |__        _|""", level);

        Room richard = new Room("""
                _____----_____|
                |             |
                I   Richard   |
                |_____________|""", level, "richard");
        richard.setDetectors(new Detector[]{new SmokeDetector(richard)});
        richard.setEntryPoints(new EntryPoint[]{new Door(richard), new Window(richard)});
        level++;
        Room yang = new Room("""
                |________
                |       |
                [  JY   I
                |_______|""", level, "jian-yang");
        yang.setDetectors(new Detector[]{new SmokeDetector(yang), new MotionDetector(yang)});
        yang.setEntryPoints(new EntryPoint[]{new Window(yang), new Door(yang)});
        Room hall = new Room("""
                ___==___==______________|       |______|      |______
                |                                                   |
                I                 Hall                              I
                |__==_       ..                                   __|""",level,"hall");
        hall.setDetectors(new Detector[]{new SmokeDetector(hall), new MotionDetector(hall)});
        hall.setEntryPoints(new EntryPoint[]{new StrongDoor(hall), new Window(hall)});

        Room jared = new Room("""
                __________|
                |         |
                I  Jared  ]
                |         |""", level, "jared");
        jared.setDetectors(new Detector[]{new SmokeDetector(jared), new MotionDetector(jared)});
        jared.setEntryPoints(new EntryPoint[]{new StrongDoor(jared), new Door(jared), new Window(jared)});
        level++;

        Room gilfoyle = new Room("""
                |___________==|
                |             |
                [   Gilfoyle  |
                |-----___-----|""", level, "gilfoyle");
        gilfoyle.setDetectors(new Detector[]{new SmokeDetector(gilfoyle)});
        gilfoyle.setEntryPoints(new EntryPoint[]{new Window(gilfoyle), new Window(gilfoyle), new Door(gilfoyle)});

        Area entrance = new Area("""
                |      |
                |--==--|
                |      |
                |      |""", level);
        hall.setLinkedArea(entrance);

        Room living = new Room("""
                |           |----------------|     |__|
                |           |_____Living_____|        |
                |                                     |
                |------________---------------_____---|""", level, "living room");
        living.setEntryPoints(new EntryPoint[]{new Window(living), new Window(living), new StrongDoor(living)});
        living.setDetectors(new Detector[]{new SmokeDetector(living), new MotionDetector(living)});
        living.setLinkedArea(livExt);

        Area garage = new Area("""
                |         |
                |  Garage |
                |         |
                 \\_______/""", level++);
        jared.setLinkedArea(garage);

        Area lawn = new Area("""
                |                                                             |         |
                |                                                             |         |
                |                           Lawn                              |         |
                |_____________________________________________________________|_________|""",level);
        backYard.setLinkedArea(lawn);

        areas.add(backYard);
        areas.add(dinesh);
        areas.add(erlich);
        areas.add(kitchen);
        areas.add(livExt);
        areas.add(richard);
        areas.add(yang);
        areas.add(hall);
        areas.add(jared);
        areas.add(gilfoyle);
        areas.add(entrance);
        areas.add(living);
        areas.add(garage);
        areas.add(lawn);

        rooms = areas.stream().filter(a -> a instanceof Room).map(Room.class::cast).toList();
    }

    public Room getRoomByName(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name))
                return room;
        }
        Print.line(Print.warning(String.format("No room with name \"%s\" found!", name)));
        return null;
    }

    public List<Room> getRooms(){
        return rooms;
    }
}
