import java.util.List;
/**
 * Created by qazimusab on 2/19/16.
 */
public class RAM {
    public int total_size;
    public int remaining_size;
    List<Process> tempProcess;
    List<String> pageOpCode;
    public boolean changing = false;
    public RAM(){
        total_size = 1024;
        remaining_size = 1024;
    }
    private void recalculateRemaining_Size(int prsize){
        remaining_size -= prsize;
    }
    public void addProcess(Process p){
        changing = true;
        //RecalculateRemaining_Size(p.Opcode_Size());
    }
    public int totalProcess(){
        return tempProcess.size();
    }
    public Process getProcess(int pos){
        return tempProcess.get(pos);
    }
    public String Get_OpCode(int processloc, int opcodeloc){
        changing = true;
        Process p = tempProcess.get(processloc);
        return p.opcodeValue(opcodeloc);
    }
    public void removeProcess(int pos){
        changing = true;
        remaining_size += tempProcess.get(pos).Opcode_Size();
        int jobnumber = tempProcess.get(pos).jobNumber;
        tempProcess.remove(pos);
        System.out.println("Remove Job Number "
                + jobnumber + " out of RAM");
    }
    public void WriteData(int pos, int pc, String Value){
        tempProcess.get(pos).write(pc, Value);
    }
    public int getPercent(){
        return ((total_size - remaining_size) / total_size) * 100;
    }
}
