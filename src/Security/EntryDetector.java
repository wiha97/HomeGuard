package Security;

import Models.EntryPoint;

public class EntryDetector extends Detector{
    protected EntryPoint entryPoint;

    public EntryDetector(EntryPoint entryPoint){
        this.entryPoint = entryPoint;

    }
}
