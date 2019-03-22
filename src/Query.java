import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Query {
	public ArrayList<String> wordsList;
	public Query() throws IOException {
		wordsList = getInput();
	}
	
	public ArrayList<String> getInput() throws IOException {
		System.out.println("Enter a query: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		ArrayList<String> wordsArr = getQueryWordsList(line);
		return wordsArr;
	}
	
	private ArrayList<String> getQueryWordsList(String line) {
		String[] words = line.split(" ");
        ArrayList<String> wordsArr = new ArrayList<String>();
		for (int i = 0; i < words.length; i++) {
        	wordsArr.add(words[i]);
        }
		return wordsArr;
	}
}
