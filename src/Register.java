/**
 * Created by qazimusab on 3/17/16.
 */
public class Register {
    private int[] registers;

    public Register() {
        registers = new int[16];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = 0;
        }
    }

    public void assign(int valueToAssign, int index) {
        if (index == 1) {
            return;
        } else if (index < registers.length) {
            registers[index] = valueToAssign;
        } else {
            System.out.println("The register is invalid.");
        }
    }

    public int get(int index) {
        return registers[index];
    }
}