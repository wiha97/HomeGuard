package JWutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogModel {
    private String time;
    private String message;
    private int logLevel;

    public LogModel(String msg, int logLevel){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        time = "["+sdf.format(cal.getTime())+"]";
        message = msg;
        this.logLevel = logLevel;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("%s  %s", time, message);
    }

    public int getLogLevel() {
        return logLevel;
    }
}
