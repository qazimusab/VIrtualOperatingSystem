package operating_system.scheduler;

import operating_system.main.Driver;

/**
 * Created by qazimusab on 2/19/16.
 */
public class Dispatcher {

    private boolean working = false;

    public String getOperationCode(int pcbIndex, int pc) {
        setWorking(true);
        while (Driver.ram.isChanging()) ;
        String operationCode = Driver.ram.getOperationCode(Driver.pcb.getQueue(pcbIndex).positionInRam, pc);
        Driver.ram.setChanging(false);
        setWorking(false);
        return operationCode;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
}