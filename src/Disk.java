import java.util.List;
/**
 * Created by Patrick on 2/26/2016.
 */
public class Disk {
    int totalSize;
    int remainingSize;
    List<Process> Processes;

    public Disk(){
        totalSize = 2048;
        remainingSize = 2048;
    }
    void RecalculateRemainSize(){
        remainingSize--;
    }
    public void AddProcess(Process p){
        RecalculateRemainSize();
        Processes.add(p);
    }
    public int TotalProcess(){
        return Processes.size();
    }
    public Process GetProcess(int pc){
        return Processes.get(pc);
    }
}
