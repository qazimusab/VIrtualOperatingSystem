import java.util.List;
import java.util.ArrayList;
/**
 * Created by qazimusab on 2/19/16.
 */
public class Process {
    List<String> job_opcode;
    List<String> data_opcode;
    public ArrayList total_page;
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
}
