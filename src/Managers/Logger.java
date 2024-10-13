package Managers;

import JWutil.Print;
import JWutil.Validate;
import Models.LogModel;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static List<LogModel> logs = new ArrayList<>();
    private static int verbosity = 3;

    public static void menu(){
        Print.clear();
        Print.line(String.format("""
                %s
                    %s
                    [1] [C]lear logs
                    [2] [V]erbosity (%s)""",
        Print.title("Log settings"), Print.back(), verbosity));

        String opt = Validate.option("1c2v");
        switch (opt){
            case "1":
                clearLogs();
                break;
            case "c":
                clearLogs();
                break;
            case "2":
                setVerbosity();
                break;
            case "v":
                setVerbosity();
                break;
        }
    }

    private static void clearLogs(){
        Print.clear();
        logs.clear();
    }

    private static void setVerbosity(){
        verbosity = Validate.number();
        Print.clear();
    }

    public static void addLog(String log, int logLevel){
        logs.add(new LogModel(log, logLevel));
    }

    public static List<LogModel> getLogs() {
        return logs.stream().filter(l -> l.getLogLevel() <= verbosity).toList().reversed();
    }

    public static String printLogs(){
        String list = "";
        for(int i = 0; i < logs.size(); i++){
            LogModel log = logs.get(i);
            list += String.format("[%s] %s", log.getTime(), log.getMessage());
            if(i < logs.size()-1)
                list += "\n";
        }
        return list;
    }
}
