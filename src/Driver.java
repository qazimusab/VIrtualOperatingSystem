import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by qazimusab on 2/19/16.
 */
public class Driver {

    public static Disk disk;
    public static RAM ram;
    public static PCB pcb;
    public static Dispatcher dispatcher;
    public static DMA dma;
    public static CPU singleCPU;
    public static ShortTermScheduler shortTermScheduler;
    public static LongTermScheduler longTermScheduler;
    public static List<CPU> quadCore;
    private boolean isExecutionComplete = false;
    public static int totalCPUsThatHaveFinishedExecuting = 0;
    public static int totalCores = 0;
    public static int totalJobsFinishedExecuting = 0;

    public void startOperatingSystemSimulation() {
        Scanner scanner = new Scanner(System.in);
        quadCore = new ArrayList<>();
        System.out.println("Enter '1' for only 1 CPU or '4' for 4 CPUs");
        String input = scanner.nextLine();

        shortTermScheduler = new ShortTermScheduler();
        longTermScheduler = new LongTermScheduler();
        disk = new Disk();
        ram = new RAM();
        pcb = new PCB();
        dispatcher = new Dispatcher();
        dma = new DMA();
        Loader.load(); //loads the jobs and their data sections into disk

        if (input.equals("1")) {
            totalCores = 1;
            singleCPU = new CPU();
            startProcessors();
        }
        else if (input.equals("4")) {
            totalCores = 4;
            for (int i = 0; i < 4; i++) {
                quadCore.add(new CPU());
            }

            startProcessors();
        }
        else {
            System.out.println("You entered an invalid number of CPUs");
        }

        System.out.println("Finished Simulation");
    }

    public void startProcessors() {
        if(totalCores == 1) {
            singleCPU.startCPU();
        }
        else {
            quadCore.forEach(CPU::startCPU);
        }
        while (!isExecutionComplete) {
            isExecutionComplete = longTermScheduler.scheduleLongTerm();
            isExecutionComplete = shortTermScheduler.scheduleShortTerm() && isExecutionComplete;
        }
    }
}
