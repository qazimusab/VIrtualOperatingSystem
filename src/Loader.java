/**
 * Created by Patrick on 2/19/2016.
 */
public class Loader {
    public static int HextoDecimal(String Hex){
        return Integer.parseInt(Hex, 16);
    }
    public static String HextoBinary(String Hex){
        Integer input = Integer.parseInt(Hex, 16);
        return Integer.toBinaryString(input);
    }
    public static int BinarytoDecimal(String Binary){
        return Integer.parseInt(Binary, 2);
    }
    public static String DecimaltoBinary(String Decimal){
        Integer input = Integer.parseInt(Decimal);
        return Integer.toBinaryString(input);

    }
}
