import java.util.ArrayList;
import java.util.List;

/**
 * Created by qazimusab on 3/17/16.
 */
public class ReadyQueue {
    private List<Integer> PCBs;
    private CPU cpu;
    private long completionTime = 0;
    private long waitTime = 0;

    public ReadyQueue(CPU cpu) {
        this.cpu = cpu;
        PCBs = new ArrayList<>();
    }

    public int getTotalPCBs() {
        return PCBs.size();
    }

    public int getPCBAtFront() {
        return PCBs.size() == 0 ? -1 : PCBs.get(0);
    }

    public void addPCB(int pcb) {
        PCBs.add(pcb);
        completionTime = System.currentTimeMillis();
        waitTime = System.currentTimeMillis();
    }

    public void removeFrontPCB() {
        System.out.println("Removing Job #" + Driver.pcb.getQueue(PCBs.get(0)).process.getJobNumber());

        System.out.println("Job #" + Driver.pcb.getQueue(PCBs.get(0)).process.getJobNumber() + " took: " + (System.currentTimeMillis() - completionTime) + " milliseconds to complete");

        PCBs.remove(0);
        Driver.totalJobsFinishedExecuting++;


        if (PCBs.size() == 0) {
            if(Driver.totalJobsFinishedExecuting == 30) {
                System.out.println("No PCBs remaining. Execution completed.");
                Driver.totalCPUsThatHaveFinishedExecuting++;
            }
            else {
                int remainingSize = Driver.ram.getRemainingSize();
                int sizeFreedUp = 0;
                for(int i = 0; i < Driver.totalJobsFinishedExecuting; i++) {
                    sizeFreedUp += Driver.disk.getProcess(i).getTotalOperationCodeSize();
                }
                Driver.ram.setRemainingSize(remainingSize + sizeFreedUp);
                Driver.longTermScheduler.scheduleLongTerm();
                Driver.shortTermScheduler.scheduleShortTerm();

            }
        }
    }

    public void calculateWaitTime(int programCounter) {
        if (programCounter == 1) {
            if (waitTime != 0) {
                if(Driver.totalCores == 1) {
                    System.out.println("Job #" + Driver.pcb.getQueue(PCBs.get(0)).process.getJobNumber() + " waited: " + (System.currentTimeMillis() - waitTime) + " milliseconds");
                }
                else {
                    System.out.println("Job #" + Driver.pcb.getQueue(PCBs.get(0)).process.getJobNumber() + " waited: " + ((System.currentTimeMillis() - waitTime) / 2) + " milliseconds");
                }
            }
        } else {
            return;
        }
    }

    private void priorityOrganize() {
        while (cpu.isWorking()) ;
        for (int i = 0; i < PCBs.size(); i++) {
            for (int j = 1; j < PCBs.size(); j++) {
                if (Driver.pcb.getQueue(PCBs.get(i)).process.getPriority() < Driver.pcb.getQueue(PCBs.get(j)).process.getPriority()) {
                    int temp = PCBs.get(i);
                    PCBs.set(i, PCBs.get(j));
                    PCBs.set(j, temp);
                }
            }
        }
    }

    private void shortestJobFirst() {
        while (cpu.isWorking()) ;
        for (int i = 0; i < PCBs.size(); i++) {
            for (int j = 1; j < PCBs.size(); j++) {
                if (Driver.pcb.getQueue(PCBs.get(i)).process.getNumberOfInstructions() > Driver.pcb.getQueue(PCBs.get(j)).process.getNumberOfInstructions()) {
                    int temp = PCBs.get(i);
                    PCBs.set(i, PCBs.get(j));
                    PCBs.set(j, temp);
                }
            }
        }
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public List<Integer> getPCBs() {
        return PCBs;
    }

    public void setPCBs(List<Integer> PCBs) {
        this.PCBs = PCBs;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}