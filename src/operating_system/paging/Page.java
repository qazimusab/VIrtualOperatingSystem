package operating_system.paging;

/**
 * Created by qazimusab on 3/17/16.
 */
public class Page
{
    private int pageNumber;
    private Data[] words;
    private int frequency;

    public Page()
    {
        pageNumber = 0;
        words = new Data[4];
        frequency = 0;
    }

    public Page(int pageNumber)
    {
        this.pageNumber = pageNumber;
        words = new Data[4];
        frequency = 0;
    }

    public void setWord(Data word)
    {
        words[word.getRemainder()] = word;
    }

    public Data getWord(int remainder)
    {
        return words[remainder];
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Data[] getWords() {
        return words;
    }

    public void setWords(Data[] words) {
        this.words = words;
    }
}