package operating_system.memory;

import operating_system.interfaces.IRegister;

/**
 * Created by qazimusab on 3/17/16.
 */
public class Register implements IRegister {
    private int[] registers;

    public Register() {
        registers = new int[16];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = 0;
        }
    }

    public void insert(int value, int index) {
        if (index == 1) {
            return;
        } else if (index < registers.length) {
            registers[index] = value;
        } else {
            System.out.println("The register is invalid.");
        }
    }

    public int retrieve(int registerIndex) {
        return registers[registerIndex];
    }
}