/**
 * Created by qazimusab on 3/17/16.
 */
class Cache
{
    int[] registerCopy;
    Process process;

    public Cache()
    {
        //initialize registerCopy
        registerCopy = new int[16];
        process = new Process();

    }
    public void copy(Register reg)
    {
        for (int i = 0; i < registerCopy.length; i++)
        {
            registerCopy[i] = reg.get(i);
        }
    }
    public Register resume()
    {
        Register reg = new Register();
        for (int i = 0; i < 16; i++)
        {
            reg.assign(registerCopy[i], i);
        }

        return reg;
    }

}