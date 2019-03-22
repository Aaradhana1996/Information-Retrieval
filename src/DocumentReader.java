import java.util.ArrayList;
import java.util.Arrays;

public class DocumentReader {
	
	public ArrayList<String> wordsList;
	
	public DocumentReader(String document)  {
		wordsList = new ArrayList<String>();	      
	    String[] docWords = document.split(" ");
	           
       for(int i = 0; i < docWords.length; i++) {
    	   String word = docWords[i];
       		wordsList.add(word);
       }
	}
}
