import java.util.List;
/**
 * Created by qazimusab on 2/19/16.
 */
class Driver {
    Disk disk;
    RAM ram;
    Loader load;
    CPU cpu;
    Dispatcher dispatch;
    PCB pcb;
    public List<CPU> totalCPU;
    private boolean complete = false;

    public Driver(String text){
        String input = "";
    }

    public void CPUStart(){
        /*cpu.startCPU();
        while(!Complete){
            Complete = Scheduler.longTerm(disk, ram, pcb);
            Complete = Scheduler.shortTerm(pcb, totalCPU[min].readyQueue) && Complete;
        }*/
    }
    //end loop
}
