import java.util.List;

/**
 * Created by qazimusab on 2/19/16.
 */
public class PCB {
    List<MMU> Device_Queues;
    RAM ram;
    public Boolean changing = false;
    /*public PCB(){
        Device_Queues = new List<MMU>();
    }*/
    public PCB(RAM r){
       ram = r;
       //Device_Queues = new List<MMU>();
    }
    public void AddQueue(MMU c){
        changing = true;
        //Device_Queues.add(c);
    }
    public MMU GetQueueAt(int pos){
        return Device_Queues.get(pos);
    }
    public int TotalQueues(){
        return Device_Queues.size();
    }
}
