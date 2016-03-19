import java.util.List;

/**
 * Created by qazimusab on 2/16/16.
 */
public class CPU {
    private Scheduler scheduler;
    private Dispatcher dispatcher;
    private PCB pcb;
    private List<Process> readyQueue;

    public int pc = 0;
    private String inputBuffer, outputBuffer, tempBuffer;
    public Boolean working = false;
    Thread t;
    String opCodeString;
    public int ioCount = 0;

    public CPU(Dispatcher d, PCB p){
        pcb = p;
        //readyQueue.contains(pcb);

    }
}
