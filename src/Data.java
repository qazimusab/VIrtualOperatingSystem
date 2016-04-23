/**
 * Created by qazimusab on 3/17/16.
 */
public class Data {

    private int quotient;
    private int remainder;
    private String word;

    public Data() {
        quotient = 0;
        remainder = 0;
        word = "";
    }

    public Data(int quotient, int remainder, String word) {
        this.quotient = quotient;
        this.remainder = remainder;
        this.word = word;
    }

    public int getQuotient() {
        return quotient;
    }

    public void setQuotient(int quotient) {
        this.quotient = quotient;
    }

    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}