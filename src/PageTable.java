/**
 * Created by qazimusab on 3/17/16.
 */
public class PageTable {

    private Page[] table; //use array because its more efficient

    public PageTable() {
        table = new Page[4];
    }

    public Page getPage(int pos) {
        return table[pos];
    }

    public void writePage(int pageNumber, int remainder, int operationCode) {
        int i;

        for (i = 0; i < 4; i++) {
            if (table[i].getPageNumber() == pageNumber) {
                break;
            }
        }
        table[i].getWords()[remainder] = new Data(operationCode / 4, operationCode % 4, Converter.decimalToBinary(operationCode));
    }

    public boolean doesPageExist(int position) {
        for (int i = 0; i < 4; i++) {
            if (table[i].getPageNumber() == position) {
                return true;
            }
        }
        return false;
    }

    public Data getData(int quotient, int remainder) {
        int i;
        for (i = 0; i < 4; i++) {
            if (table[i].getPageNumber() == quotient) {
                break;
            }
        }

        table[i].setFrequency(table[i].getFrequency() + 1);
        return table[i].getWord(remainder);
    }


    public boolean quotientExists(int quotient) {

        for (int i = 0; i < 4; i++) {
            if (table[i] != null && table[i].getPageNumber() == quotient) {
                return true;
            }
        }
        return false;
    }

    public void swapPage(Page pageToSwap) {
        System.out.println("............Swapping Opcode............");
        for (int i = 0; i < 4; i++) {
            if (table[i] == null) {
                table[i] = pageToSwap;
                return;
            }
        }
        int smallest = table[0].getFrequency();
        int opcodeToSwap = 0;
        for (int i = 0; i < 4; i++) {
            if (smallest > table[i].getFrequency()) {
                smallest = table[i].getFrequency();
                opcodeToSwap = i;
            }
        }
        table[opcodeToSwap] = pageToSwap;

    }

    public Page getLeastUsedPage() {
        int smallest = table[0].getFrequency();
        int leastUsed = 0;
        for (int i = 0; i < 4; i++) {
            if (smallest > table[i].getFrequency()) {
                smallest = table[i].getFrequency();
                leastUsed = i;
            }
        }
        return table[leastUsed];
    }

    public boolean isFull() {

        for (int i = 0; i < 4; i++) {
            if (table[i] == null) {
                return false;
            }
        }
        return false;
    }

}
