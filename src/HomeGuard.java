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
        String logo =String.format("""
                    %s
                    |    ___      ___               |
                    |   |   |    |   |    HOME      |
                    |   |   |    |   |_________     |
                    |   |   |    |   |         \\    |
                    |   |   |____|   |    __    \\   |
                    |   |            |   /  \\___/   |
                    |   |    ____    |  |   ____    |
                    |   |   |    |   |  |  |_   |   |
                    |   |___|    |___|   \\___|  |   |
                    |               \\           |   |
                    |        GUARD   \\__________/   |
                    |                         %s|""", line("", false), Print.info(App.getVersion()));
        String[] rows = logo.split("\n");
        for(String row : rows){
            Print.line(row);
            App.sleep(50);
        }
        line(logo, true);
    }

    static String line(String ln, boolean rev){
        String bar = "";
        if(!rev) {
            for (int i = 0; i < 31; i++) {
                Print.clear();
                bar = loading(i, 31, '=', ' ');
                Print.line(bar);
                App.sleep(10);
            }
        }
        else {
            for (int i = 29; i > 0; i--) {
                Print.clear();
                bar = loading(i, 29, ' ', '=');
                Print.line(ln);
                Print.line(" "+bar+" ");
                App.sleep(33);
            }
        }
        ln += bar;
        Print.clear();
        return ln;
    }

    private static String loading(int idx, int width, char cb, char fill){
        int spaces = width - idx;
        if(spaces % 2 == 0)
            idx--;
        String bar = fillSpace(spaces/2 , fill);
        bar += fillSpace(idx, cb);
        bar+= fillSpace(spaces/2, fill);
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
