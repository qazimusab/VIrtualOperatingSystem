/**
 * Created by qazimusab on 2/16/16.
 */
public class CPU {

    private ReadyQueue readyQueue;
    private Register register;
    private int opCode;
    private int instructionType;
    private int s1Register;
    private int s2Register;
    private int dRegister;
    private int bRegister;
    private int register1;
    private int register2;
    private long dataSection;
    private long address;
    private int programCounter = 0;
    private String inputBuffer, outputBuffer;
    private boolean working = false;
    private Thread cpuThread;
    private String opcodeString;
    private int totalIOCount = 0;
    private Cache cache;

    public CPU() {
        readyQueue = new ReadyQueue(this);
        register = new Register();
        cache = new Cache();
    }

    /**
     * Starts a new thread for the CPU to run on
     */
    public void startCPU() {
        cpuThread = new Thread(() -> {
            while (Driver.totalCPUsThatHaveFinishedExecuting != Driver.totalCores) {
                fetch();
                if (working == true) {
                    int decodedOpString = decode(opcodeString);
                    System.out.println("............Decoded opcode: " + decodedOpString + "............");
                    execute(decodedOpString);
                }
            }
            System.exit(0);
        });
        cpuThread.start();
    }

    /**
     * Returns true if the CPU thread is still running
     *
     * @return
     */
    public boolean isRunning() {
        return cpuThread.isAlive();
    }

    public int getEffectiveAddress(int bRegister, long address) {
        return register.get(bRegister) + (int) address;
    }


    /**
     * Gets all the jobs on the ready queue for this CPU and executes their instructions
     *
     * @param operationCode
     */
    private void execute(int operationCode) {
        int pcbPosition;
        readyQueue.calculateWaitTime(programCounter);
        System.out.println("............Execution Starting............");
        switch (operationCode) {
            case 0:
                System.out.println("............Operation Code: " + operationCode + " = RD............");
                System.out.println("Reads Content of Input buffer into a accumulator.");
                if (address > 0) {
                    pcbPosition = readyQueue.getPCBAtFront();
                    inputBuffer = Driver.dma.GetOpcode(pcbPosition, getWordAddress((int) address));
                    register.assign(Converter.binaryToDecimal(inputBuffer), register1);
                    totalIOCount++;
                } else {
                    register.assign(register.get(register2), register1);
                    totalIOCount++;
                }
                break;
            case 1:
                System.out.println("............Operation Code: " + operationCode + " = WR............");
                System.out.println("Writes the content of accumulator into Output buffer.");
                outputBuffer = Converter.decimalToBinary(register.get(register1));
                pcbPosition = readyQueue.getPCBAtFront();
                Driver.dma.writeData(pcbPosition, getWordAddress((int) address), outputBuffer);
                totalIOCount++;
                break;
            case 2:
                System.out.println("............Operation Code: " + operationCode + " = ST............");
                System.out.println("Stores content of Register " + dRegister + " into an address");
                address = register.get(dRegister);
                break;
            case 3:
                System.out.println("............Operation Code: " + operationCode + " = LW............");
                System.out.println("Loads the content of an address into a register " + dRegister);
                register.assign(register.get((int) address), dRegister);
                System.out.println("Content at Register " + dRegister + " now has value: " + register.get(dRegister));
                break;
            case 4:
                System.out.println("............Operation Code: " + operationCode + " = MOV............");
                register.assign(register.get(register1), register2);
                break;
            case 5:
                System.out.println("............Operation Code: " + operationCode + " = ADD............");
                System.out.println("Adds content of two S-regs into D-reg");
                register.assign(register.get(s1Register) + register.get(s2Register), dRegister);
                System.out.println("Value of Register " + s1Register + ": " + register.get(s1Register));
                System.out.println("Value of Register " + s2Register + ": " + register.get(s2Register));
                System.out.println("Adding value...");
                System.out.println("Value of Register " + dRegister + ": " + register.get(dRegister));
                break;
            case 6:
                System.out.println("............Operation Code: " + operationCode + " = SUB............");
                System.out.println("Subtracts content of two S-regs into D-reg");
                register.assign(register.get(register1) - register.get(register2), dRegister);
                System.out.println("Value of Register " + s1Register + ": " + register.get(s1Register));
                System.out.println("Value of Register " + s2Register + ": " + register.get(s2Register));
                System.out.println("Subtracting value...");
                System.out.println("Value of Register " + dRegister + ": " + register.get(dRegister));
                break;
            case 7:
                System.out.println("............Operation Code: " + operationCode + " = MUL............");
                System.out.println("Multiplies content of two S-regs into D-reg");
                register.assign(register.get(register1) * register.get(register2), dRegister);
                System.out.println("Value of Register " + s1Register + ": " + register.get(s1Register));
                System.out.println("Value of Register " + s2Register + ": " + register.get(s2Register));
                System.out.println("Multiplying value...");
                System.out.println("Value of Register " + dRegister + ": " + register.get(dRegister));
                break;
            case 8:
                System.out.println("............Operation Code: " + operationCode + " = DIV............");
                System.out.println("Divides content of two S-regs into D-reg");
                if (register.get(s2Register) == 0) {
                    System.out.println("Cannot divide by 0!");
                } else {
                    register.assign(register.get(register1) / register.get(register2), dRegister);
                    System.out.println("Value of Register " + s1Register + ": " + register.get(s1Register));
                    System.out.println("Value of Register " + s2Register + ": " + register.get(s2Register));
                    System.out.println("Dividing value...");
                    System.out.println("Value of Register " + dRegister + ": " + register.get(dRegister));
                }
                break;
            case 9:
                System.out.println("............Operation Code: " + operationCode + " = AND............");
                System.out.println("Logical AND of two S-regs into D-reg");
                register.assign(register.get(register1) & register.get(register2), dRegister);
                System.out.println("Value of Register " + s1Register + ": " + register.get(s1Register));
                System.out.println("Value of Register " + s2Register + ": " + register.get(s2Register));
                System.out.println("Performing logical AND...");
                System.out.println("Value of Register " + dRegister + ": " + register.get(dRegister));
                break;
            case 10:
                System.out.println("............Operation Code: " + operationCode + " = OR............");
                System.out.println("Logical OR of two S-regs into D-reg");
                register.assign(register.get(register1) | register.get(register2), dRegister);
                System.out.println("Value of Register " + s1Register + ": " + register.get(s1Register));
                System.out.println("Value of Register " + s2Register + ": " + register.get(s2Register));
                System.out.println("Performing logical OR...");
                System.out.println("Value of Register " + dRegister + ": " + register.get(dRegister));
                break;
            case 11:
                System.out.println("............Operation Code: " + operationCode + " = MOVI............");
                System.out.println("Transfers address/Data directly into a register");
                register.assign(getWordAddress((int) address), dRegister);
                System.out.println("Register " + dRegister + " now has value: " + register.get(dRegister));
                break;
            case 12:
                System.out.println("............Operation Code: " + operationCode + " = ADDI............");
                System.out.println("Adds a Data directly to the content of a register");
                register.assign((int) address + register.get(dRegister), dRegister);
                System.out.println("Register " + dRegister + " now has value: " + register.get(dRegister));
                break;
            case 13:
                System.out.println("............Operation Code: " + operationCode + " = MULI............");
                System.out.println("Multiplies a Data directly to the content of a register");
                register.assign((int) address * register.get(dRegister), dRegister);
                System.out.println("Register " + dRegister + " now has value: " + register.get(dRegister));
                break;
            case 14:
                System.out.println("............Operation Code: " + operationCode + " = DIVI............");
                if (register.get(dRegister) == 0) {
                    System.out.println("Cannot divide by 0!");
                } else {
                    System.out.println("Divides a Data directly to the content of a register.");
                    register.assign((int) address / register.get(dRegister), dRegister);
                    System.out.println("Register " + dRegister + " now has value: " + register.get(dRegister));
                }
                break;

            case 15:
                System.out.println("............Operation Code: " + operationCode + " = LDI............");
                System.out.println("Loads a data/address directly to the content of a register");
                register.assign((int) address, dRegister);
                System.out.println("Register " + dRegister + " now has value: " + register.get(dRegister));
                break;

            case 16:
                System.out.println("............Operation Code: " + operationCode + " = SLT............");
                System.out.println("Sets the D-reg to 1 if  first S-reg is less than second B-reg, and 0 otherwise");
                if (register.get(s1Register) < register.get(s2Register)) {
                    register.assign(1, dRegister);
                    System.out.println("Value at Register " + dRegister + " = " + register.get(dRegister));
                } else {
                    register.assign(0, dRegister);
                    System.out.println("Value at Register " + dRegister + " = " + register.get(dRegister));
                }
                break;
            case 17:
                System.out.println("............Operation Code: " + operationCode + " = SLTI............");
                System.out.println("Sets the D-reg to 1 if  first S-reg is less than a Data, and 0 otherwise");
                if (register.get(s1Register) < (int) address) {
                    register.assign(1, dRegister);
                    System.out.println("Value at Register " + dRegister + " = " + register.get(dRegister));
                } else {
                    register.assign(0, dRegister);
                    System.out.println("Value at Register " + dRegister + " = " + register.get(dRegister));
                }
                break;
            case 18:
                System.out.println("............Operation Code: " + operationCode + " = HLT............");
                System.out.println("Logical end of program");
                readyQueue.removeFrontPCB();
                programCounter = 0;
                System.out.println("Total # of I/O for this job was: " + totalIOCount);
                totalIOCount = 0;
                break;
            case 19:
                System.out.println("............Operation Code: " + operationCode + " = NOP............");
                System.out.println("Does nothing and moves to next instruction");
                break;
            case 20:
                System.out.println("............Operation Code: " + operationCode + " = JMP............");
                System.out.println("Jumps to a specified location");
                programCounter = (int) address / 4;
                System.out.println("\nProgram counter set to " + programCounter);
                break;
            case 21:
                System.out.println("............Operation Code: " + operationCode + " = BEQ............");
                System.out.println("Branches to an address when content of B-reg = D-reg");
                if (register.get(dRegister) == register.get(bRegister)) {
                    programCounter = (int) address / 4;
                    System.out.println("Program counter set to " + programCounter);
                } else {
                    System.out.println("Program counter set to " + programCounter);
                }
                break;
            case 22:
                System.out.println("............Operation Code: " + operationCode + " = BNE............");
                System.out.println("Branches to an address when content of B-reg <> D-reg");
                if (register.get(dRegister) != register.get(bRegister)) {
                    programCounter = (int) address / 4;
                    System.out.println("Program counter set to " + programCounter);
                } else {
                    System.out.println("Program counter set to " + programCounter);
                }
                break;
            case 23:
                System.out.println("............Operation Code: " + operationCode + " = BEZ............");
                System.out.println("Branches to an address when content of B-reg = 0");
                if (register.get(bRegister) == 0) {
                    programCounter = (int) address / 4;
                    System.out.println("Program counter set to " + programCounter);
                } else {
                    System.out.println("Program counter set to " + programCounter);
                }
                break;
            case 24:
                System.out.println("............Operation Code: " + operationCode + " = BNZ............");
                System.out.println("Branches to an address when content of B-reg <> 0");
                if (register.get(bRegister) != 0) {
                    programCounter = (int) address / 4;
                    System.out.println("Program counter set to " + programCounter);
                } else {
                    System.out.println("Program counter set to " + programCounter);
                }
                break;
            case 25:
                System.out.println("............Operation Code: " + operationCode + " = BGZ............");
                System.out.println("Branches to an address when content of B-reg > 0");
                if (register.get(bRegister) > 0) {
                    programCounter = (int) address / 4;
                    System.out.println("Program counter set to " + programCounter);
                } else {
                    System.out.println("Program counter set to " + programCounter);
                }
                break;
            case 26:
                System.out.println("............Operation Code: " + operationCode + " = BLZ............");
                System.out.println("Branches to an address when content of B-reg < 0");
                if (register.get(bRegister) < 0) {
                    programCounter = (int) address / 4;
                    System.out.println("Program counter set to " + programCounter);
                } else {
                    System.out.println("Program counter set to " + programCounter);
                }
                break;
            default:
                System.out.println("............The operation code: " + operationCode + " was invalid............");
                break;
        }
    }

    private int getWordAddress(int address) {
        return address / 4;
    }

    private int decode(String binaryInstruction) {
        System.out.println("................Decoding Started................");
        System.out.println("Decoding: " + binaryInstruction);
        instructionType = Converter.binaryToDecimal(binaryInstruction.substring(0, 2));
        opCode = Converter.binaryToDecimal(binaryInstruction.substring(2, 8));
        System.out.println("Type of instruction: " + instructionType);
        switch (instructionType) {
            case 0:
                System.out.println("Arithmetic Instruction Format");
                s1Register = Converter.binaryToDecimal(binaryInstruction.substring(8, 12));
                s2Register = Converter.binaryToDecimal(binaryInstruction.substring(12, 16));
                dRegister = Converter.binaryToDecimal(binaryInstruction.substring(16, 20));
                dataSection = Converter.binaryToDecimal(binaryInstruction.substring(20));
                System.out.println("Operation code: " + opCode);
                System.out.println("Source Register: " + register1 + ": " + register.get(s1Register));
                System.out.println("Source Register: " + register2 + ": " + register.get(s2Register));
                System.out.println("Destination Register: " + dRegister + ": " + register.get(dRegister));
                if (dataSection > 0) {
                    System.out.println("Address value: " + dataSection);
                }
                break;
            case 1:
                System.out.println("Conditional Branch and Immediate format");
                bRegister = Converter.binaryToDecimal(binaryInstruction.substring(8, 12));
                dRegister = Converter.binaryToDecimal(binaryInstruction.substring(12, 16));
                address = Converter.binaryToDecimal(binaryInstruction.substring(16));

                System.out.println("Operation code: " + opCode);
                System.out.println("Branch Register: " + register.get(bRegister));
                System.out.println("Destination Register: " + register.get(dRegister));
                System.out.println("Address Value: " + address);
                break;
            case 2:
                System.out.println("Unconditional Jump format");
                address = Converter.binaryToDecimal(binaryInstruction.substring(8));
                System.out.println("Operation code value: " + opCode);
                System.out.println("Address Value: " + address);
                break;
            case 3:
                System.out.println("Input and Output instruction format");
                register1 = Converter.binaryToDecimal(binaryInstruction.substring(8, 12));
                register2 = Converter.binaryToDecimal(binaryInstruction.substring(12, 16));
                address = Converter.binaryToDecimal(binaryInstruction.substring(16));
                System.out.println("Operation code value: " + opCode);
                System.out.println("Register " + register1 + ": " + register.get(register1));
                System.out.println("Register " + register2 + ": " + register.get(register2));
                System.out.println("Address Value: " + address);
                break;
            default:
                System.out.println("............Invalid Instruction Type............");
                break;
        }
        return opCode;
    }

    /**
     * Fetches the next instruction from the ready queue
     */
    private void fetch() {
        working = false;
        if (readyQueue.getTotalPCBs() > 0) { //check if any processes are in queue
            int pcbAtFront = readyQueue.getPCBAtFront();
            System.out.println("................Fetching................");
            opcodeString = Driver.dma.GetOpcode(pcbAtFront, programCounter);
            programCounter++;
            working = true;
        }
    }

    public void setReadyQueue(ReadyQueue readyQueue) {
        this.readyQueue = readyQueue;
    }

    public ReadyQueue getReadyQueue() {
        return readyQueue;
    }

    public boolean isWorking() {
        return working;
    }
}