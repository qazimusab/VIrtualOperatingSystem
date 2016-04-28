package operating_system.cpu_related;

import operating_system.main.Driver;

/**
 * Created by qazimusab on 3/17/16.
 */
public class DMA {

    public String GetOpcode(int pcbIndex, int pc) {
        String opcode = Driver.pcb.getQueue(pcbIndex).GetPageOpcode(pc);
        return opcode;
    }

    /**
     * Writes data instructions to process.
     * @param pcbIndex
     * @param pc
     * @param value
     */
    public void writeData(int pcbIndex, int pc, String value) {
        Driver.ram.writeData(Driver.pcb.getQueue(pcbIndex).positionInRam, pc, value);
    }

}
