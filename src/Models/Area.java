package Models;

import JWutil.Print;

public class Area {
    protected String bluePrint;
    protected int status = 0;

    public Area(){

    }

    public Area(String bluePrint, int level){
        this.bluePrint = bluePrint;
        setOffset(level);
    }

    private void setOffset(int level) {
        for (int i = 0; i < level; i++) {
            bluePrint = "\n\n\n\n" + bluePrint;
        }
    }

    protected String statColor(String txt){
        return switch (status) {
            case 1 -> Print.good(txt);
            case 2 -> Print.warning(txt);
            case 3 -> Print.alert(txt);
            default -> txt;
        };
    }

    public int getStatus() {
        return status;
    }

    public String getBluePrint() {
        return bluePrint;
    }

    public void setBluePrint(String bluePrint) {
        this.bluePrint = bluePrint;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
