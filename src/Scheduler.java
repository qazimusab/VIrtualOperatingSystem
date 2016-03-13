/**
 * Created by qazimusab on 2/19/16.
 */
public class Scheduler {
    private static int pc = 0;
    public static Boolean LongtermScheduler(Disk d, RAM r, PCB p){
        if (pc < d.TotalProcess() && r.remaining_size >= d.GetProcess(pc).Opcode_Size()){
            while(r.changing);
            while(p.changing);
            //Add to the RAM
            r.Add_Process(d.GetProcess(pc));
            MMU q = new MMU(d.GetProcess(pc), r.Total_Process() - 1);
            //Then add the queue to PCB
            p.AddQueue(q);
        }
    }
}
