/**
 * Created by qazimusab on 4/27/16.
 */
public interface ICPU {

    void fetch();

    int decode(String binaryInstruction);

    void execute(int operationCode);


}
