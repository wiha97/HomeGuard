package JWutil;

public class App {

    public static String getVersion() {
        return "v1.3.0";
    }

    public static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception ignored) {
        }
    }
}
