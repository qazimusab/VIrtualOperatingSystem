import java.util.ArrayList;
import java.util.List;

/**
 * Created by qazimusab on 2/19/16.
 */
public class PCB {

    private List<MMU> processes;
    private boolean changing = false;

    public PCB() {
        processes = new ArrayList<>();
    }

    public void addQueue(MMU mmu) {
        changing = true;
        processes.add(mmu);
    }

    public MMU getQueue(int pos) {
        return processes.get(pos);
    }

    public int getTotalProcesses() {
        return processes.size();
    }

    public boolean isChanging() {
        return changing;
    }

    public void setChanging(boolean changing) {
        this.changing = changing;
    }

    public List<MMU> getProcesses() {
        return processes;
    }

    public void setProcesses(List<MMU> processes) {
        this.processes = processes;
    }
}