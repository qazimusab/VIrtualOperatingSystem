import java.util.List;

/**
 * Created by qazimusab on 2/19/16.
 */
public class Process {
    List<String> job_opcode;
    List<String> data_opcode;
    //public List<page> total_page;
    public int jobNumber;
    public int numberofInstructions;
    public int priority;
    public int inputBufferSize;
    public int outputBufferSize;
    public int tempBufferSize;
    public int dataAddress;

    public Process(){
        jobNumber = 0;
        numberofInstructions = 0;
        priority = 0;
        inputBufferSize = 0;
        outputBufferSize = 0;
        tempBufferSize = 0;
        dataAddress = 0;
    }
    public void setJobInfo(int jobnum, int instrtotal, int pr){
        jobNumber = jobnum;
        numberofInstructions = instrtotal;
        priority = pr;
    }
    public void setDataInfo(int input, int output, int temp){
        inputBufferSize = input;
        outputBufferSize = output;
        tempBufferSize = temp;
    }
    public void addJobOpcode(String op){
        job_opcode.add(op);
        int pagenum = (job_opcode.size() - 1) / 4;
        /*if(total_page.size() == pagenum){
            total_page.add(new page(total_page.size()));
        }*/
    }
    public void addDataOpcode(String op){
        dataAddress = data_opcode.size();
        data_opcode.add(op);
    }
    public int Opcode_Size(){
        int Size = job_opcode.size() + data_opcode.size();
        return Size;
    }
    public void write(int pos, String newValue){
        if (pos < data_opcode.size()){
            data_opcode.set(pos, newValue);
        }
        else{
            data_opcode.set(pos - data_opcode.size(), newValue);
        }
    }
    public String opcodeValue(int pc){
        return (pc <= job_opcode.size() - 1 ? job_opcode.get(pc) : data_opcode.get(pc - job_opcode.size()));
    }
}
