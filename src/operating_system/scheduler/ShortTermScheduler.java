package operating_system.scheduler;

import operating_system.main.Driver;
import operating_system.interfaces.IShortTermScheduler;
import operating_system.cpu_related.Process;

import java.lang.*;

/**
 * Created by qazimusab on 4/20/16.
 */
public class ShortTermScheduler implements IShortTermScheduler {

    @Override
    public boolean scheduleShortTerm() {
        ReadyQueue readyQueue;
        if(Driver.totalCores > 1) {
            int cpuWithShortestReadyQueue = 0;
            for (int i = 1; i < Driver.totalCores; ++i) {
                if (Driver.quadCore.get(cpuWithShortestReadyQueue).getReadyQueue().getTotalPCBs() > Driver.quadCore.get(i).getReadyQueue().getTotalPCBs())
                    cpuWithShortestReadyQueue = i;
            }
            readyQueue = Driver.quadCore.get(cpuWithShortestReadyQueue).getReadyQueue();
        }
        else {
            readyQueue = Driver.singleCPU.getReadyQueue();
        }
        if (Driver.pcb.getTotalProcesses() > 0) {
            int i;
            for (i = 0; i < Driver.pcb.getTotalProcesses(); i++) {
                if (Driver.pcb.getQueue(i).state == Process.STATE.waiting) {
                    break;
                } else if (i + 1 == Driver.pcb.getTotalProcesses()) {
                    return true;
                }
            }
            Driver.pcb.getQueue(i).changeState(Process.STATE.ready);
            System.out.println("Moving Job #" + Driver.pcb.getQueue(i).process.getJobNumber() + " from waiting queue to ready queue");
            readyQueue.addPCB(i);
            return false;
        }
        return false;
    }

}
