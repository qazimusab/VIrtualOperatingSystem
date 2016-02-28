import java.util.List;
/**
 * Created by qazimusab on 2/19/16.
 */
public class RAM {
    public int total_size;
    public int remaining_size;
    List<Process> temp_Process;
    List<String> pageOpCode;
    public boolean changing = false;
    public RAM(){
        total_size = 1024;
        remaining_size = 1024;
    }
    private void RecalculateRemaining_Size(int prsize){
        remaining_size -= prsize;
    }
    public void Add_Process(Process p){
        changing = true;
        //RecalculateRemaining_Size(p.Opcode_Size());
    }
    public int Total_Process(){
        return temp_Process.size();
    }
    public Process GetProcess(int pos){
        return temp_Process.get(pos);
    }
    public String Get_OpCode(int processloc, int opcodeloc){
        changing = true;
        Process p = temp_Process.get(processloc);
        return p.Opcode_Value(opcodeloc);
    }
    public void Remove_Process(int pos){
        changing = true;
        remaining_size += temp_Process.get(pos).Opcode_Size();
        int jobnumber = temp_Process.get(pos).JobNumber;
        temp_Process.RemoveAt(pos);
        System.out.println("Remove Job Number "
                + jobnumber + " out of RAM");
    }
    public void WriteData(int pos, int pc, String Value){
        temp_Process.get(pos).Write(pc, Value);
    }
    public int getPercent(){
        return ((total_size - remaining_size) / total_size) * 100;
    }
}
