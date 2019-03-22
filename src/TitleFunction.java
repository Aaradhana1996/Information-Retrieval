import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class TitleFunction {
	
	ArrayList<String> queryWords;
	ArrayList<String> titles;
	ArrayList<File> files;
	String title;
	public TitleFunction(Query query, ArrayList<File> files){		
		queryWords = new ArrayList<String>();
		ArrayList<String> qWords = query.wordsList;
		for(int i = 0; i < qWords.size(); i++){
			queryWords.add(qWords.get(i).toLowerCase());
		}
		this.titles = new ArrayList<String>();
		for(int j = 0; j < files.size(); j++){
			Parser parse = new Parser(files.get(j).getName());
			parse.read();
			titles.add(parse.getTag("<title>", "</title>"));
		}
		this.files = files;
	}
	
	public TitleFunction(Query query, String title){
		this.title = title.toLowerCase();
		queryWords = new ArrayList<String>();
		ArrayList<String> qWords = query.wordsList;
		for(int i = 0; i < qWords.size(); i++){
			queryWords.add(qWords.get(i).toLowerCase());
		}
	}
	
	public int titleIndivScore(){
		String[] titleWords = title.split(" ");
		int score = 0;
			for(int i = 0; i < titleWords.length; i++){
				String word = titleWords[i];
				if(queryWords.contains(word)){
					for(int j = 0; j < queryWords.size(); j++){
						if(queryWords.get(j).toLowerCase().equals(word.toLowerCase())){
							score++;
						}
					}
				}
			}
		return score;
	}
	
	public File score(){
		String[] titleWords;
		int max = 0;
		File res = null;
		for(int p = 0; p < titles.size(); p++){
			titleWords = titles.get(p).toLowerCase().split(" ");
			int score = 0;
				for(int i = 0; i < titleWords.length; i++){
					String word = titleWords[i];
					if(queryWords.contains(word)){
						for(int j = 0; j < queryWords.size(); j++){
							if(queryWords.get(j).toLowerCase().equals(word.toLowerCase())){
								score++;
							}
						}
					}
				}
			if(score > max){
				res = files.get(p);
				max = score;
			}
		}
		
		return res;
	}
}
