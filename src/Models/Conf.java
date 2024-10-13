package Models;

public class Conf {
    private static boolean compactMode = false;

    public static boolean isCompactMode() {
        return compactMode;
    }

    public static void setCompactMode(){
        compactMode = !compactMode;
    }
}
