package Managers;

import Interfaces.Alarm;
import JWutil.App;
import JWutil.Print;
import JWutil.Validate;
import Models.House;
import Models.Room;
import Security.*;

import java.util.ArrayList;
import java.util.List;

public class CentralUnit {
    private static final List<Alarm> DETECTORS = new ArrayList<>();
    private static List<Alarm> toggleAbleAlarms;
    private static boolean runLoop;
    private static boolean isEnabled;
    private static House house;

    public static void start() {
        house = new House();
        toggleAbleAlarms = DETECTORS.stream().filter(a -> !(a instanceof SmokeDetector)).toList();
        middleOut();
        menu();
    }

    public static void menu() {
        boolean loop = true;
        while (loop) {
            Print.line(String.format("""
                            %s
                                %s
                                [1] [O]verview
                                [2] [L]ist alarms (%s active)
                                [3] [T]oggle system (%s)
                                [4] [R]eset system
                                [5] [S]imulate""",
                    Print.title("HomeGuard " + App.getVersion()), Print.back(), DETECTORS.stream().filter(Alarm::isActive).toList().size(), isEnabled ? Print.good("Enabled") : Print.warning("Disabled")));
            String option = Validate.option("1o2l3t4r5s");
            switch (option) {
                case "1":
                    Print.clear();
                    overview();
                    break;
                case "o":
                    Print.clear();
                    overview();
                    break;
                case "2":
                    Print.clear();
                    listDetectors();
                    break;
                case "l":
                    Print.clear();
                    listDetectors();
                    break;
                case "3":
                    Print.clear();
                    toggleAlarm();
                    break;
                case "t":
                    Print.clear();
                    toggleAlarm();
                    break;
                case "4":
                    Print.clear();
                    reset();
                    break;
                case "r":
                    Print.clear();
                    reset();
                    break;
                case "5":
                    Print.clear();
                    simulate();
                    break;
                case "s":
                    Print.clear();
                    simulate();
                    break;
                case "q":
                    Print.clear();
                    loop = false;
                    runLoop = false;
                    break;
            }
        }
    }

    private static void overview() {
        boolean loop = true;
        while (loop) {
            Print.line(Print.title("Overview"));
            house.printHouse();

            int size = house.getRooms().size() + 1;
            String[] options = new String[size];
            int idx = 0;
            for (Room room : house.getRooms()) {
                options[idx++] = room.getName();
            }
            options[idx] = "exit";

            String option = Validate.optString(options);
            Print.clear();
            if (option.equals("exit"))
                break;
            house.getRoomByName(option).menu();
        }
    }

    public static void middleOut() {
        Thread thread = new Thread(() -> {
            runLoop = true;
            while (runLoop) {
                App.sleep(100);
                for (Alarm alarm : DETECTORS.stream().filter(Alarm::isActive).toList()) {
                    alarm.detect();
                }
            }
            Print.clear();
        });
        thread.start();
    }

    private static void simulate() {
        //TODO
    }

    private static void listDetectors() {
        Print.clear();
        for (Alarm alarm : toggleAbleAlarms) {
            Print.line(alarm);
        }
    }

    private static void toggleAlarm() {
        if (new PinPad().enterPin()) {
            isEnabled = !isEnabled;
            for (Alarm alarm : toggleAbleAlarms) {
                ((Detector) alarm).setActive(isEnabled);
            }
        }
    }

    private static void reset() {
        if (new PinPad().enterPin()) {
            for (Alarm alarm : DETECTORS) {
                alarm.reset();
            }
        }
    }

    public static void sirens(String message) {
        Print.line(message);
    }


    public static List<Alarm> getDetectors() {
        return DETECTORS;
    }

    public static void setRunLoop(boolean run) {
        runLoop = run;
    }
}
