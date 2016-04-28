package operating_system.interfaces;

import operating_system.cpu_related.Process;

/**
 * Created by qazimusab on 4/28/16.
 */
public interface IRAM {
    void addProcess(Process process);
    int getRemainingSize();
    int getTotalSize();
}
