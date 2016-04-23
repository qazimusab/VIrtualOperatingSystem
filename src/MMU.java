/**
 * Created by Patrick on 3/12/2016.
 */
public class MMU {

    Process process;
    public int positionInRam;
    public Process.STATE state;
    private PageTable pageTable;

    public MMU(Process process) {
        positionInRam = Driver.ram.totalProcesses() - 1;
        state = Process.STATE.waiting;
        this.process = process;
        pageTable = new PageTable();
    }

    public void changeState(Process.STATE state) {
        this.state = state;
    }

    public String GetPageOpcode(int pc) {
        System.out.println("Require opcode at pos " + pc + " in RAM");
        System.out.println("Opcode is currently at page number " + pc / 4 + " and position " + pc % 4 + " in RAM");

        if (pageTable.quotientExists(pc / 4)) {
            return pageTable.getData(pc / 4, pc % 4).getWord();
        }

        if (pageTable.isFull()) {
            Page leastUsed = pageTable.getLeastUsedPage();
            process.writeBackRAM(leastUsed);
        }
        pageTable.swapPage(process.getPages().get(pc / 4));


        return pageTable.getData(pc / 4, pc % 4).getWord();
    }

    public void writePage(int pageNumber, int remainder, int operationCode) {

        if (!pageTable.doesPageExist(pageNumber) && !pageTable.isFull()) {
            pageTable.swapPage(process.getPages().get(pageNumber));
            pageTable.writePage(pageNumber, remainder, operationCode);
        } else if (!pageTable.doesPageExist(pageNumber) && pageTable.isFull()) {
            pageTable.writePage(pageNumber, remainder, operationCode);
            pageTable.swapPage(process.getPages().get(pageNumber));
            pageTable.writePage(pageNumber, remainder, operationCode);
        } else {
            pageTable.writePage(pageNumber, remainder, operationCode);
        }
    }
}