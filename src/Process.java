import java.util.ArrayList;
import java.util.List;

/**
 * Created by qazimusab on 2/19/16.
 */
public class Process {

    public enum STATE {
        ready,
        waiting,
        executing,
        terminated
    }

    private List<String> jobInstructions;
    private List<String> dataInstructions;
    private List<Page> pages;
    private int jobNumber;
    private int numberOfInstructions;
    private int priority;
    private int inputBufferSize;
    private int outputBufferSize;
    private int tempBufferSize;
    private int dataAddress;

    public Process() {
        jobNumber = 0;
        numberOfInstructions = 0;
        priority = 0;
        inputBufferSize = 0;
        outputBufferSize = 0;
        tempBufferSize = 0;
        dataAddress = 0;
        jobInstructions = new ArrayList<>();
        dataInstructions = new ArrayList<>();
        pages = new ArrayList<>();
    }

    public void setJobInformation(int jobNumber, int numberOfInstructions, int priority) {
        this.jobNumber = jobNumber;
        this.numberOfInstructions = numberOfInstructions;
        this.priority = priority;
    }

    public void setDataInformation(int inputBufferSize, int outputBufferSize, int tempBufferSize) {
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.tempBufferSize = tempBufferSize;
    }

    public void addJobInstruction(String jobInstruction) {
        jobInstructions.add(jobInstruction);
        int pageNumber = (jobInstructions.size() - 1) / 4;
        if (pages.size() == pageNumber) {
            pages.add(new Page(pages.size()));
            pages.get(pages.size() - 1).setWord(new Data((jobInstructions.size() - 1) / 4,
                    (jobInstructions.size() - 1) % 4,
                    jobInstruction));
            System.out.println("Creating new page in page table");
            System.out.println("Just added this job instruction to page table: " + jobInstruction);

        } else {
            pages.get(pages.size() - 1).setWord(new Data((jobInstructions.size() - 1) / 4,
                    (jobInstructions.size() - 1) % 4,
                    jobInstruction));
            System.out.println("Just added this job instruction to page table: " + jobInstruction);

        }
    }

    public void addDataInstruction(String dataInstruction) {
        dataAddress = dataInstructions.size();
        dataInstructions.add(dataInstruction);

        if (pages.size() == ((jobInstructions.size() - 1 + dataInstructions.size()) / 4)) { //check if newest page is full
            pages.add(new Page(pages.size()));
            pages.get(pages.size() - 1).setWord(new Data((jobInstructions.size() - 1 + dataInstructions.size()) / 4,
                    (jobInstructions.size() - 1 + dataInstructions.size()) % 4,
                    dataInstruction));
            System.out.println("Creating new page in page table");
            System.out.println("Just added this data instruction to page table:  " + dataInstruction);

        } else { //page in list has space
            pages.get(pages.size() - 1).setWord(new Data((jobInstructions.size() - 1 + dataInstructions.size()) / 4,
                    (jobInstructions.size() - 1 + dataInstructions.size()) % 4,
                    dataInstruction));
            System.out.println("Just added this data instruction to page table:  " + dataInstruction);
        }
    }

    public int getTotalOperationCodeSize() {
        int totalSize = jobInstructions.size() + dataInstructions.size();
        return totalSize;
    }

    public String getOperationCodeValue(int pc) {
        return (pc <= jobInstructions.size() - 1 ? jobInstructions.get(pc) : dataInstructions.get(pc - jobInstructions.size()));
    }

    public void write(int pos, String NewValue) {
        if (pos < dataInstructions.size()) {
            dataInstructions.set(pos, NewValue);
        } else {
            dataInstructions.set(pos - dataInstructions.size(), NewValue);
        }
    }

    public void pageWriteBack(Page p) {
        pages.set(p.getPageNumber(), p);
    }

    public void writeBackRAM(Page swaped) {
        pages.add(swaped.getPageNumber(), swaped);
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public int getDataAddress() {
        return dataAddress;
    }

    public void setDataAddress(int dataAddress) {
        this.dataAddress = dataAddress;
    }

    public int getInputBufferSize() {
        return inputBufferSize;
    }

    public void setInputBufferSize(int inputBufferSize) {
        this.inputBufferSize = inputBufferSize;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public int getNumberOfInstructions() {
        return numberOfInstructions;
    }

    public void setNumberOfInstructions(int numberOfInstructions) {
        this.numberOfInstructions = numberOfInstructions;
    }

    public int getOutputBufferSize() {
        return outputBufferSize;
    }

    public void setOutputBufferSize(int outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getTempBufferSize() {
        return tempBufferSize;
    }

    public void setTempBufferSize(int tempBufferSize) {
        this.tempBufferSize = tempBufferSize;
    }

    public List<String> getDataInstructions() {
        return dataInstructions;
    }

    public void setDataInstructions(List<String> dataInstructions) {
        this.dataInstructions = dataInstructions;
    }

    public void setJobInstructions(List<String> jobInstructions) {
        this.jobInstructions = jobInstructions;
    }

    public List<String> getJobInstructions() {
        return jobInstructions;
    }
}