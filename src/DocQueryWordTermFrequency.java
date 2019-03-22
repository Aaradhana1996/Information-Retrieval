
public class DocQueryWordTermFrequency {
	public String documentName;
	public String queryWord;
	public int frequency;
	
	public DocQueryWordTermFrequency(String documentName, String queryWord) {
		this.documentName = documentName;
		this.queryWord = queryWord;
		this.frequency = 0;
	}
	
	public String toString() {
		return "documentName:"+documentName+", queryWord:"+queryWord+", frequency:"+frequency;
	}
}
