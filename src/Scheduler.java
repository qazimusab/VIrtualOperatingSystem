/**
 * Created by qazimusab on 2/19/16.
 */
public class Scheduler {
    private static int pc = 0;
    public static Boolean LongTermScheduler(Disk d, RAM r, PCB p){
        if (pc < d.TotalProcess() && r.remaining_size >= d.GetProcess(pc).Opcode_Size()){
            while(r.changing);
            while(p.changing);
            //Add to the RAM
            r.Add_Process(d.GetProcess(pc));
            MMU q = new MMU(d.GetProcess(pc), r.Total_Process() - 1);
            //Then add the queue to PCB
            p.AddQueue(q);
            System.out.println("Added job to RAM: " + d.GetProcess(pc++).JobNumber);
            r.changing = false;
            p.changing = false;
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
