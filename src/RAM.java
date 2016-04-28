import java.util.ArrayList;
import java.util.List;

/**
 * Created by qazimusab on 2/19/16.
 */
public class RAM implements IRAM {

    private int totalSize;
    private int remainingSize;
    private List<Process> processes;
    private List<String> pageOperationCodes;
    private boolean changing = false;

    public RAM() {
        totalSize = 1024;
        remainingSize = 1024;
        processes = new ArrayList<>();
        pageOperationCodes = new ArrayList<>();
    }

    private void subtractRemainingSize(int wordsUsedByProcess) {
        remainingSize -= wordsUsedByProcess;
    }

    public void addProcess(Process process) {
        changing = true;
        subtractRemainingSize(process.getTotalOperationCodeSize());
        processes.add(process);
        changing = false;
    }

    public int totalProcesses() {
        return processes.size();
    }

    public Process getProcess(int pos) {
        return processes.get(pos);
    }

    public String getOperationCode(int processloc, int opcodeloc) {
        changing = true;
        Process p = processes.get(processloc);
        return p.getOperationCodeValue(opcodeloc);
    }

    public void removeProcess(int pos) {
        changing = true;
        remainingSize += processes.get(pos).getTotalOperationCodeSize();
        int jobNumber = processes.get(pos).getJobNumber();
        processes.remove(pos);
        System.out.println("Remove Job Number " + jobNumber + " out of RAM");
    }

    public void writeData(int pos, int pc, String Value) {
        processes.get(pos).write(pc, Value);
    }

    public boolean isChanging() {
        return changing;
    }

    public void setChanging(boolean changing) {
        this.changing = changing;
    }

    public int getRemainingSize() {
        return remainingSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public List<String> getPageOperationCodes() {
        return pageOperationCodes;
    }

    public void setPageOperationCodes(List<String> pageOperationCodes) {
        this.pageOperationCodes = pageOperationCodes;
    }

    public void setRemainingSize(int remainingSize) {
        changing = true;
        this.remainingSize = remainingSize;
        changing = false;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getPercentage() {
        return ((totalSize - remainingSize) / totalSize) * 100;
    }
}