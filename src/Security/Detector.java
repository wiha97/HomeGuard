package Security;

import JWutil.Print;

public abstract class Detector {
    protected String name;
    protected boolean isTriggered;
    protected boolean isActive;

    public boolean isActive(){
        return isActive;
    }

    public void setActive(boolean active){
        isActive = active;
    }

    public void toggle(){
        isActive = !isActive;
    }

    @Override
    public String toString(){
        if(isActive)
            return Print.good(name);
        return Print.warning(name);
    }

    public boolean isTriggered() {
        return isTriggered;
    }
}
