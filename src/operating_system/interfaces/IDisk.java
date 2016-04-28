package operating_system.interfaces;

import operating_system.cpu_related.Process;

/**
 * Created by qazimusab on 4/28/16.
 */
public interface IDisk {
    void addProcess(Process process);
    Process getProcess(int pc);
    int getTotalSize();
    int getRemainingSize();
}
