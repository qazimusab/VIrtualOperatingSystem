package operating_system.memory;

import operating_system.interfaces.IDisk;
import operating_system.cpu_related.Process;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 2/26/2016.
 */
public class Disk implements IDisk {

    private int totalSize;
    private int remainingSize;
    private List<Process> processes;

    public Disk() {
        totalSize = 2048;
        remainingSize = 2048;
        processes = new ArrayList<>();
    }

    void recalculateRemainderSize()
    {
        remainingSize--;
    }

    public void addProcess(Process process) {
        recalculateRemainderSize();
        processes.add(process);
    }

    public int getTotalProcesses() {
        return processes.size();
    }

    public Process getProcess(int pc) {
        return processes.get(pc);
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public int getRemainingSize() {
        return remainingSize;
    }

    public void setRemainingSize(int remainingSize) {
        this.remainingSize = remainingSize;
    }
}