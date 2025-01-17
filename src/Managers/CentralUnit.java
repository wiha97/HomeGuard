package Managers;

import Interfaces.Alarm;
import JWutil.*;
import Models.*;
import Security.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CentralUnit {
    private static PinPad pinPad = new PinPad("Central Unit");
    private static final List<Alarm> DETECTORS = new ArrayList<>();
    private static List<Alarm> toggleAbleAlarms;
    private static boolean runLoop;
    private static boolean isEnabled;
    private static House house;
    private static String notification = "";

    public static void start() {
        house = new House();
        toggleAbleAlarms = DETECTORS.stream().filter(a -> !(a instanceof SmokeDetector)).toList();
        middleOut();
        overview();
    }

    public static void menu() {
        boolean loop = true;
        while (loop) {
            Print.clear();
            Print.line(String.format("""
                            %s
                                %s
                                [1] [O]verview
                                [2] [L]ist alarms (%s active)
                                [3] [T]oggle system (%s)
                                [4] [R]eset system
                                [5] [S]imulate""",
                    Print.title("HomeGuard " + App.getVersion(), Logger.getLogs()), Print.back(),
                    DETECTORS.stream().filter(Alarm::isActive).toList().size(),
                    isEnabled ? Print.good("Enabled") : Print.warning("Disabled")));
            String option = Validate.option("1o2l3t4r5s");
            switch (option) {
                case "1":
                    overview();
                    break;
                case "o":
                    overview();
                    break;
                case "2":
                    listDetectors();
                    break;
                case "l":
                    listDetectors();
                    break;
                case "3":
                    toggleAlarm();
                    break;
                case "t":
                    toggleAlarm();
                    break;
                case "4":
                    reset();
                    break;
                case "r":
                    reset();
                    break;
                case "5":

                    simulate();
                    break;
                case "s":

                    simulate();
                    break;
                case "q":

                    loop = false;
                    runLoop = false;
                    break;
            }
        }
    }

    private static void overview() {
        boolean loop = true;
        while (loop) {
            Print.clear();
            if(Conf.isCompactMode())
                Print.line(Print.title("HomeGuard " + App.getVersion(), Logger.getLogs()));
            else
                Print.line(Print.title("HomeGuard " + App.getVersion()));
            house.printHouse();

            int size = house.getRooms().size() + 8;
            String[] options = new String[size];
            int idx = 0;
            for (Room room : house.getRooms()) {
                options[idx++] = room.getName();
            }
            options[idx++] = "log settings";
            options[idx++] = "simulate";
            options[idx++] = "toggle";
            options[idx++] = "reset";
            options[idx++] = "exit";
            options[idx++] = "simple";
            options[idx++] = "compact mode";
            options[idx] = "quit";

            String option = Validate.optString(options);

            if (option.equals("exit") || option.equals("quit")) {
                runLoop = false;
                break;
            }

            switch (option) {
                case "simulate":
                    simulate();
                    break;
                case "log settings":
                    Logger.menu();
                    break;
                case "toggle":
                    toggleAlarm();
                    break;
                case "reset":
                    reset();
                    break;
                case "simple":
                    menu();
                    break;
                case "compact mode":
                    Conf.setCompactMode();
                    break;
                default:
                    house.getRoomByName(option).menu();
                    break;
            }
        }
    }

    public static void middleOut() {
        Thread thread = new Thread(() -> {
            runLoop = true;
            while (runLoop) {
                App.sleep(10);
                for (Alarm alarm : DETECTORS.stream().filter(Alarm::isActive).toList()) {
                    alarm.detect();
                }
            }

        });
        thread.start();
    }

    private static void simulate() {
        int passes = 10;
        int chance = 20;
        int delay = 100;
        boolean loop = true;
        while (loop) {
            Print.clear();
            Print.line(String.format("""
                    %s
                        %s
                        [1] [P]asses (%s)
                        [2] [C]hance (%s)
                        [3] [D]elay (%s)
                        [4] [R]un""", Print.title("Simulation"), Print.back(), passes, chance, delay));
            String option = Validate.option("1p2c3d4r");
            switch (option) {
                case "1":
                    passes = Validate.number(0);
                    break;
                case "p":
                    passes = Validate.number(0);
                    break;
                case "2":
                    chance = Validate.number(0);
                    break;
                case "c":
                    chance = Validate.number(0);
                    break;
                case "3":
                    delay = Validate.number(0);
                    break;
                case "d":
                    delay = Validate.number(0);
                    break;
                case "4":
                    runSimulation(passes, chance, delay);
                    break;
                case "r":
                    runSimulation(passes, chance, delay);
                    break;
                case "q":
                    return;
            }
        }
    }

    private static void runSimulation(int passes, int chance, int delay) {
        for (int i = 0; i < passes; i++) {
            for (Room room : house.getRooms()) {
                switch (new Random().nextInt(chance)) {
                    case 1:
                        room.setOnFire();
                        break;
                    case 2:
                        room.setHasMovement(!room.isHasMovement());
                        break;
                    case 3:
                        if (room.getEntryPoints().length > 0) {
                            EntryPoint ep = room.getEntryPoints()[new Random().nextInt(room.getEntryPoints().length)];
                            ep.open();
                        }
                        break;
                    case 4:
                        if (room.getEntryPoints().length > 0) {
                            EntryPoint ep = room.getEntryPoints()[new Random().nextInt(room.getEntryPoints().length)];
                            ep.breach();
                        }
                        break;
                    default:
                        break;
                }
                Print.clear();
                if(Conf.isCompactMode())
                    Print.line(Print.title("Simulation", Logger.getLogs()));
                else
                    Print.line(Print.title("Simulation"));
                house.printHouse();
                Print.line(Print.info(String.format("Pass: %s/%s", i+1, passes)));
                App.sleep(delay);
            }
        }
        Print.line("Press enter to continue...");
        setNotification("Simulation complete", 1);
        Print.newScan();
    }

    private static void listDetectors() {
        Print.clear();
        Print.line(Print.title("Alarms"));
        for (Alarm alarm : toggleAbleAlarms) {
            Print.line(alarm);
        }
        Print.line("Press enter to continue...");
        Print.newScan();
    }

    private static void toggleAlarm() {
        Print.clear();
        if (pinPad.enterPin()) {
            isEnabled = !isEnabled;
            for (Alarm alarm : toggleAbleAlarms) {
                ((Detector) alarm).setActive(isEnabled);
            }
            setNotification(Print.info("Alarms toggled"), 2);
        }
    }

    private static void reset() {
        Print.clear();
        if (pinPad.enterPin()) {
            for (Alarm alarm : DETECTORS) {
                alarm.reset();
            }
        }
        setNotification("Reset alarm system", 2);
    }

    public static void sirens(String message) {
        Print.line(message);
    }


    public static List<Alarm> getDetectors() {
        return DETECTORS;
    }

    public static String getNotification() {
        return notification;
    }

    public static void setRunLoop(boolean run) {
        runLoop = run;
    }

    public static void setNotification(String notification, int logLevel) {
        CentralUnit.notification = notification;
        Logger.addLog(notification, logLevel);
    }
}
