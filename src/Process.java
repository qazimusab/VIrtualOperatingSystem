import java.util.List;
import java.util.ArrayList;
/**
 * Created by qazimusab on 2/19/16.
 */
public class Process {
    List<String> job_opcode;
    List<String> data_opcode;
    //public List<page> total_page;
    public int JobNumber;
    public int NumberofInstructions;
    public int Priority;
    public int inputBufferSize;
    public int outputBufferSize;
    public int tempBufferSize;
    public int DataAddress;

    public Process(){
        JobNumber = 0;
        NumberofInstructions = 0;
        Priority = 0;
        inputBufferSize = 0;
        outputBufferSize = 0;
        tempBufferSize = 0;
        DataAddress = 0;
    }
    public void Set_Job_Info(int jobnum, int instrtotal, int pr){
        JobNumber = jobnum;
        NumberofInstructions = instrtotal;
        Priority = pr;
    }
    public void Set_Data_Info(int input, int output, int temp){
        inputBufferSize = input;
        outputBufferSize = output;
        tempBufferSize = temp;
    }
    public void Add_Job_opcode(String op){
        job_opcode.add(op);
        int pagenum = (job_opcode.size() - 1) / 4;
        /*if(total_page.size() == pagenum){
            total_page.add(new page(total_page.size()));
        }*/
    }
    public void Add_Data_opcode(String op){
        DataAddress = data_opcode.size();
        data_opcode.add(op);
    }
    public int Opcode_Size(){
        int Size = job_opcode.size() + data_opcode.size();
        return Size;
    }
}
