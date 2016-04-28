package operating_system.interfaces;

/**
 * Created by qazimusab on 4/28/16.
 */
public interface IRegister {
    void insert(int value, int index);
    int retrieve(int registerIndex);
}
