import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by qazimusab on 3/17/16.
 */
public class Loader {

    public static void load() {
        String relativePathToFile = "src/Program-File.txt";
        String line;
        boolean isDataOpcode = false;
        Process process = new Process();
        File file = new File(relativePathToFile);
        FileReader fileReader;
        BufferedReader bufferedReader;
        if (!file.exists()) {
            System.out.println("This file does not exist.");
        } else {
            try {
                System.out.println("Loading file into disk: " + file.getAbsolutePath());
                fileReader = new FileReader(relativePathToFile);
                bufferedReader = new BufferedReader(fileReader);
                while ((line = bufferedReader.readLine()) != null) {
                    if(!line.equals("// END") && !line.equals("//END"))
                        System.out.println(line);
                    if (line.charAt(0) == '/') { //either a new job, data section, or end of job
                        if (line.contains("JOB")) {
                            process = new Process();
                            isDataOpcode = false;
                            String[] jobInformation = line.split(" ");
                            int jobNumber = Converter.hexToDecimal(jobInformation[2]);
                            int numberOfInstructions = Converter.hexToDecimal(jobInformation[3]);
                            int priorityNumber = Converter.hexToDecimal(jobInformation[4]);
                            process.setJobInformation(jobNumber, numberOfInstructions, priorityNumber);
                            System.out.println("---------------------------------------------");
                            System.out.println("Reading new Job into Disk");
                            System.out.println("---------------------------------------------");
                            System.out.println("Job number: " + jobNumber + "\nNumber of Instructions: " + numberOfInstructions +
                                    "\nPriority number: " + priorityNumber);
                        } else if (line.contains("Data")) {
                            isDataOpcode = true;
                            String[] dataInformation = line.split(" ");
                            int inputBufferSize = Converter.hexToDecimal(dataInformation[2]);
                            int outputBufferSize = Converter.hexToDecimal(dataInformation[3]);
                            int tempBufferSize = Converter.hexToDecimal(dataInformation[4]);
                            process.setDataInformation(inputBufferSize, outputBufferSize, tempBufferSize);
                            System.out.println("---------------------------------------------");
                            System.out.println("Entering Data Section");
                            System.out.println("---------------------------------------------");
                            System.out.println("Reading reading data section for job #" + process.getJobNumber() + "\nInput buffer size: " +
                                    inputBufferSize + "\nOutput buffer size: " + outputBufferSize + "\nTemp buffer size: " + tempBufferSize);
                        } else if (line.equals("// END") || line.equals("//END")) {
                            Driver.disk.addProcess(process);
                            System.out.println("Added Job Number " + process.getJobNumber() + " to Disk. It had " + process.getJobInstructions().size() +
                                    " job instructions and " + process.getDataInstructions().size() + " instructions.");
                            System.out.println("---------------------------------------------");
                            System.out.println("End of loading Job #" + process.getJobNumber() + " into disk.");
                            System.out.println("---------------------------------------------");
                            System.out.println(line);
                        }
                    } else { //is an instruction
                        String[] instruction;
                        if (isDataOpcode == false) {
                            instruction = line.split("x");
                            process.addJobInstruction(Converter.hexToBinary(instruction[1]));
                        } else {
                            instruction = line.split("x");
                            process.addDataInstruction(Converter.hexToBinary(instruction[1]));
                        }

                    }
                }
                bufferedReader.close();
                fileReader.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}