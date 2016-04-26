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
        System.out.println("Enter '1' for only 1 CPU or 'n' for n CPUs");
        String input = scanner.nextLine();

        shortTermScheduler = new ShortTermScheduler();
        longTermScheduler = new LongTermScheduler();
        disk = new Disk();
        ram = new RAM();
        pcb = new PCB();
        dispatcher = new Dispatcher();
        dma = new DMA();

        if (input.equals("1")) {
            totalCores = 1;
            singleCPU = new CPU();
            Loader.load(); //loads the jobs and their data sections into disk
            startProcessors();
        }

        else if (input.equals("n")) {
            System.out.println("Enter how many CPU's you want.");
            totalCores = scanner.nextInt();
            for (int i = 0; i < totalCores; i++) {
                quadCore.add(new CPU());
            }
            Loader.load(); //loads the jobs and their data sections into disk
            startProcessors();
        }
        else {
            System.out.println("You entered an invalid number of CPUs");
        }

        System.out.println("Finished Simulation");
    }

    public void startProcessors() {
        if(totalCores == 1) {
            System.out.println("--------------------------------------------------");
            System.out.println("Starting Single Processor Operating System");
            System.out.println("--------------------------------------------------");
            singleCPU.startCPU();
        }
        else {
            System.out.println("--------------------------------------------------");
            System.out.println("Starting Quad Core Processor Operating System");
            System.out.println("--------------------------------------------------");
            quadCore.forEach(CPU::startCPU);
        }
        while (totalCPUsThatHaveFinishedExecuting != totalCores) {
            isExecutionComplete = longTermScheduler.scheduleLongTerm();
            isExecutionComplete = shortTermScheduler.scheduleShortTerm() && isExecutionComplete;
        }
    }
}
