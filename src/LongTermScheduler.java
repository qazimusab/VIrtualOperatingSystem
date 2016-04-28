/**
 * Created by qazimusab on 4/20/16.
 */
public class LongTermScheduler implements ILongTermScheduler {

    public static int programCounter = 0;

    @Override
    public boolean canScheduleLongTerm() {
        return programCounter < Driver.disk.getTotalProcesses() && Driver.ram.getRemainingSize() >= Driver.disk.getProcess(programCounter).getTotalOperationCodeSize();
    }

    @Override
    public boolean scheduleLongTerm() {
        if (canScheduleLongTerm()) {
            System.out.println("-------------------------------------------");
            System.out.println("Adding Job #" + Driver.disk.getProcess(programCounter).getJobNumber() + " to RAM");
            System.out.println("-------------------------------------------");

            while (Driver.ram.isChanging()) {} // purpose of this is synchronization
            while (Driver.pcb.isChanging()) {} //purpose of this is synchronization

            System.out.println("Total number of space remaining in RAM: " + Driver.ram.getRemainingSize() + " words.");
            Driver.ram.addProcess(Driver.disk.getProcess(programCounter));
            MMU mmu = new MMU(Driver.disk.getProcess(programCounter));
            Driver.pcb.addQueue(mmu);
            System.out.println("Just added Job #" + Driver.disk.getProcess(programCounter).getJobNumber() +
                    " to RAM.\nWords used: " + Driver.disk.getProcess(programCounter).getTotalOperationCodeSize() +
                    "\nRemaining word space in ram: " + Driver.ram.getRemainingSize());
            programCounter++;
            Driver.ram.setChanging(false);
            Driver.pcb.setChanging(false);
            return false;
        }
        return false;
    }

}
