package operating_system.main;

import java.math.BigInteger;

/**
 * Created by qazimusab on 4/19/16.
 */
public class Converter {

    public static int hexToDecimal(String hex) {
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        int val = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    public static String hexToBinary(String hex) {
        String preBin = new BigInteger(hex, 16).toString(2);
        Integer length = preBin.length();
        if (length < 32) {
            for (int i = 0; i < 32 - length; i++) {
                preBin = "0" + preBin;
            }
        }
        return preBin;
    }

    public static int binaryToDecimal(String binary) {
        int decimalValue = Integer.parseInt(binary, 2);
        return decimalValue;
    }

    public static String decimalToBinary(int decimal) {
        return Integer.toBinaryString(decimal);
    }
}
