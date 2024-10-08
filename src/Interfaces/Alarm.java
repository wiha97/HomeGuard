package Interfaces;

public interface Alarm {
    void trigger();
    void detect();
    void reset();
    boolean isActive();
}
