import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BM25Variables {
	
	public HashMap<String,ArrayList<String>> docsWordsList;
	public ArrayList<String> queryWords;
	public int totalNumDocsInCollectionN;
	public HashMap<String, Integer> allDocsLengthInWords;
	public double avgNumWordsDocs; //gets initialised in calcAllDocsLengthInWords() function called in constructor
	public HashMap<String, Integer> numberDocsContainingQin;
	public ArrayList<DocQueryWordTermFrequency> queryTerminDocFrequencies;
	
	public BM25Variables(HashMap<String,ArrayList<String>> docsWordsList, ArrayList<String> queryWords) {
		this.docsWordsList = docsWordsList;
		this.queryWords = queryWords;
		this.totalNumDocsInCollectionN = this.docsWordsList.size();
		this.allDocsLengthInWords = calcAllDocsLengthInWords();
		this.queryTerminDocFrequencies = new ArrayList<DocQueryWordTermFrequency>();
		this.numberDocsContainingQin = calcNumberDocsContainingQin();
	}
	
	private HashMap<String, Integer> calcNumberDocsContainingQin() {
		HashMap<String, Integer> NumOfDocsWithQueryWord = new HashMap<String, Integer>();
		for(int queryWordInd = 0; queryWordInd < queryWords.size(); queryWordInd++) {
			String queryWord = queryWords.get(queryWordInd);
			NumOfDocsWithQueryWord.put(queryWord, new Integer(0));
			Iterator<String> docsWordsListIter = docsWordsList.keySet().iterator();
			int docIndex = 0;
			while(docsWordsListIter.hasNext()) {
				String docName = docsWordsListIter.next();
				ArrayList<String> doc = docsWordsList.get(docName);
				addDocQueryWordTermFrequency(docName, queryWord);
				NumOfDocsWithQueryWord = iterateDocumentWords(doc, queryWord, docIndex,NumOfDocsWithQueryWord);
				docIndex++;
			}
		}
		return NumOfDocsWithQueryWord;
	}
	
	public void addDocQueryWordTermFrequency(String docName, String queryWord) {
		DocQueryWordTermFrequency docQueryWord = new DocQueryWordTermFrequency(docName, queryWord);
		queryTerminDocFrequencies.add(docQueryWord);
	}

	
	public HashMap<String, Integer> iterateDocumentWords(ArrayList<String> doc, String queryWord, int docIndex, 
			HashMap<String, Integer> NumOfDocsWithQueryWord) {
		boolean docAlreadyAdded = false;
		for(int docWordsIndex = 0; docWordsIndex < doc.size(); docWordsIndex++) {
			String docWord = doc.get(docWordsIndex);
			if(docWord.toLowerCase().equals(queryWord)) {
				if(!docAlreadyAdded) {
					NumOfDocsWithQueryWord = incrementNumOfDocsForQueryWord(NumOfDocsWithQueryWord, queryWord);
					docAlreadyAdded = true;
				}
				incrementQueryTerminDocFrequency(docIndex);
			}
		}
		return NumOfDocsWithQueryWord;
	}
	
	public void incrementQueryTerminDocFrequency(int docIndex) {
		DocQueryWordTermFrequency dqwtf = queryTerminDocFrequencies.get(docIndex);
		dqwtf.frequency = dqwtf.frequency+1;
		queryTerminDocFrequencies.set(docIndex, dqwtf);
	}
	
	public HashMap<String, Integer> incrementNumOfDocsForQueryWord(HashMap<String, Integer> NumOfDocsWithQueryWord, 
			String queryWord) {
		Integer newValue = NumOfDocsWithQueryWord.get(queryWord) + 1;
		NumOfDocsWithQueryWord.replace(queryWord, newValue);
		return NumOfDocsWithQueryWord;
	}
	
	private HashMap<String, Integer> calcAllDocsLengthInWords() {
		int totalNumWordsInAllDocs = 0;
		HashMap<String, Integer> docNameLengthInWords = new HashMap<String, Integer>();
		Set<String> docs = docsWordsList.keySet();
		Iterator<String> docsIter = docs.iterator();
		while(docsIter.hasNext()) {
			String doc = docsIter.next();
			ArrayList<String> docWords = docsWordsList.get(doc);
			int docLengthInWords = docWords.size();
			docNameLengthInWords.put(doc, docLengthInWords);
			totalNumWordsInAllDocs = totalNumWordsInAllDocs + docLengthInWords;
		}
		this.avgNumWordsDocs = totalNumWordsInAllDocs/this.totalNumDocsInCollectionN;
		return docNameLengthInWords;
	}
}
//System.out.println("lists print "+ docsWordsList.toString() +"\n" +
//queryWords.toString() +"\n" +
//totalNumDocsInCollectionN +"\n" +
//allDocsLengthInWords.toString() +"\n" +
//avgNumWordsDocs +"\n" +
//numberDocsContainingQin.toString() +"\n" +
//queryTerminDocFrequencies.toString());
