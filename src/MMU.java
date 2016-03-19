/**
 * Created by Patrick on 3/12/2016.
 */
public class MMU {
    public enum STATE{
        ready,
        waiting,
        executing,
        terminated
    }
    Process process;
    public int posinRAM;
    public int Jobnum;
    public int totalinstruction;
    public int priority;
    public int IBsize;
    public int OBsize;
    public int TBsize;
    public int totalopcode;
    public STATE state;
    public int pc;
    public MMU(Process p, int pos){
        posinRAM = pos;
        Jobnum = p.jobNumber;
        totalinstruction = p.numberofInstructions;
        totalopcode = p.Opcode_Size();
        priority = p.priority;
        IBsize = p.inputBufferSize;
        OBsize = p.outputBufferSize;
        TBsize = p.tempBufferSize;
        state = STATE.waiting;
        process = p;
        //page = new PageTable();
    }
    public void ChangeState(STATE t){
        state = t;
    }
}
