import JWutil.*;
import Managers.CentralUnit;

public class HomeGuard {
    public static void main(String[] args) {
        Print.clear();
        splash();
        Print.clear();
        Print.line("Welcome!");
        App.sleep(200);
        Print.clear();
        CentralUnit.start();
    }

    static void splash(){
        for(int i = 0; i < 32; i++){
            Print.clear();
            String load = loading(i);
            Print.line(load);
            Print.line("""
                    |    ___      ___                |
                    |   |   |    |   |    HOME       |
                    |   |   |    |   | _______       |
                    |   |   |    |   |/       \\      |
                    |   |   |____|   |    ___  \\     |
                    |   |    ____    |   |   \\_/     |
                    |   |   |    |   |   |           |
                    |   |   |    |   |   |   ____    |
                    |   |___|    |___|   |  |__  |   |
                    |               \\     \\____| |   |
                    |        GUARD   \\           |   |
                    |                 \\_________/    |
                    |                                |""");
            Print.line(load);
            Print.line("Loading...");
            App.sleep(33);
        }
    }

    private static String loading(int idx){
        int spaces = 32 - idx;
        String bar = fillSpace(spaces/2 , ' ');
        bar += fillSpace(idx, '=');
        bar+= fillSpace(spaces/2, ' ');
        return bar;
    }

    private static String fillSpace(int idx, char filler){
        String space = "";
        for(int i = 0; i <= idx; i++){
            space += filler;
        }
        return space;
    }
}
