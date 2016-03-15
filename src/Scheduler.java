/**
 * Created by qazimusab on 2/19/16.
 */
public class Scheduler {
    private static int programCounter = 0;
    public static Boolean LongTermScheduler(Disk d, RAM r, PCB pcb){
        if (programCounter < d.TotalProcess() && r.remaining_size >= d.GetProcess(programCounter).Opcode_Size()){
            while(r.changing);
            while(pcb.changing);
            //Add to the RAM
            r.Add_Process(d.GetProcess(programCounter));
            MMU mmu = new MMU(d.GetProcess(programCounter), r.Total_Process() - 1);
            //Then add the queue to PCB
            pcb.AddQueue(mmu);
            System.out.println("Added job to RAM: " + d.GetProcess(programCounter++).JobNumber);
            r.changing = false;
            pcb.changing = false;
            return false;
        }
        else
        {
            return true;
        }
    }
    public static Boolean ShortTermScheduler (PCB p){
        if (p.TotalQueues() > 0){
            int i = 0;
            for (i=0; i < p.TotalQueues(); i++){
                if (p.GetQueueAt(i).state == MMU.STATE.waiting){
                    break;
                }
                else if (i + 1 == p.TotalQueues()){
                    return true;
                }
            }
            p.GetQueueAt(i).ChangeState(MMU.STATE.ready);
            System.out.println("Placing job " + p.GetQueueAt(i).Jobnum + " from device queue to ready queue");
            //u.AddPCB(i);
            return false;
        }
        return false;
    }
}
