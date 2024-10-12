package Security;

import JWutil.*;

import java.util.concurrent.atomic.*;

public class PinPad {
    private String name;
    private final String CODE = "0451";

    public PinPad(Object obj) {
        this.name = obj.toString();
    }

    public boolean enterPin() {
        Print.clear();
        Print.line(Print.title(" "+CODE+" "));
        Print.line("""
                ||        |        |        ||
                ||    1   |    2   |    3   ||
                ||________|________|________||
                ||        |        |        ||
                ||    4   |    5   |    6   ||
                ||________|________|________||
                ||        |        |        ||
                ||    7   |    8   |    9   ||
                ||________|________|________||
                ||        |        |        ||
                ||    *   |    0   |    #   ||
                ||________|________|________||
                \\|==========================|/""");
        Print.line(name);
        final AtomicBoolean loop = new AtomicBoolean(true);
        final AtomicReference<String> pass = new AtomicReference<>("");
        Thread t1 = new Thread(() -> {
            int i = 5;
            while (!pass.get().equals(CODE) && i > 0) {
                App.sleep(1000);
                i--;
            }
            if(i < 1 && !pass.get().equals(CODE)){
                Print.clear();
                Print.line(Print.warning("Timed out"));
                Print.line(Print.warning("Press enter to continue"));
            }
            loop.set(false);
        });
        t1.start();
        while (true) {
            Print.same("> ");
            pass.setPlain(Print.newScan());
            if(!loop.get()) {
                Print.clear();
                return false;
            }

            if(pass.get().equals(CODE)) {
                Print.line(Print.good("Welcome!"));
                t1.interrupt();
                Print.clear();
                return true;
            }
            else
                Print.line(Print.warning("Incorrect pass"));
        }
    }
}
