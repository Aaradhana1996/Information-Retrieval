import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SkipBigram {
	File dataFile;
    BufferedReader br;
    String lineInput;
    Date inputTime;
    Date inputDate;
    
    public static void main(String[] args) {
    	new SkipBigram();
    }

    public SkipBigram() {

        dataFile = new File("C:\\Users\\bharila\\Desktop\\Spring 2017"
        		+ "\\CSSE413-02\\Information Retrieval\\Presidents\\Adams.txt");
        String line;

        try {
            InputStream fis = new FileInputStream(dataFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String[] words;
            ArrayList<String> wordsArr = new ArrayList<String>();
            HashMap<String, String> bigram = new HashMap<String, String>();
            int i = 0;
            while ((line = br.readLine()) != null) {
            	words = line.split(" ");
            	wordsArr.add(words[i]);
            	i+=i;
            }
            for(int j = 0; j < wordsArr.size(); j++) {
            	if (wordsArr.size() > j+2)
            		bigram.put(wordsArr.get(j), wordsArr.get(j+2));
            }
            System.out.println(bigram.toString());
        }
         catch (IOException ioe) {

            System.out.println("\n An error with the Data.txt file occured.");
        }
    }
}
