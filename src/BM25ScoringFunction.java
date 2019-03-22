import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BM25ScoringFunction {
	private double k1;
	private double b;
	private int totalNumDocsInCollectionN;
	private HashMap<String, Integer> allDocsLengthInWords;
	private double avgNumWordsDocs; 
	private HashMap<String, Integer> numberDocsContainingQin;
	private ArrayList<DocQueryWordTermFrequency> queryTerminDocFrequencies;
	
	public BM25ScoringFunction(double k1, double b,int totalNumDocsInCollectionN,
			HashMap<String, Integer> allDocsLengthInWords, double avgNumWordsDocs, HashMap<String, Integer> numberDocsContainingQin, 
			ArrayList<DocQueryWordTermFrequency> queryTerminDocFrequencies) {
		this.k1 = k1;
		this.b = b;
		this.totalNumDocsInCollectionN = totalNumDocsInCollectionN;
		this.allDocsLengthInWords = allDocsLengthInWords;
		this.avgNumWordsDocs = avgNumWordsDocs;
		this.numberDocsContainingQin =numberDocsContainingQin;
		this.queryTerminDocFrequencies = queryTerminDocFrequencies;
	}
	
	public HashMap<String, Double> getBM25scores(ArrayList<File> docs, ArrayList<String> queryWords) {
		HashMap<String, Double> bm25csores = new HashMap<String, Double>();
		Iterator<File> docsIter = docs.iterator();
		while (docsIter.hasNext()) {
			File next = docsIter.next();
			bm25csores.put(next.getName(), score(next, queryWords));
		}
		return bm25csores;
	}
	
	public double score(File documentD, ArrayList<String> queryWords) {
		double score = 0;
		Iterator<String> iter = queryWords.iterator();
		while(iter.hasNext()) {
			score += queryWordScore(documentD, iter.next());
		}
		return score;
	}
	
	private HashMap<String, Integer> getQueryWordsSingleDocFrequenciesList(String documentName) {
		HashMap<String, Integer> queryWordsFrequenciesMap = new HashMap<String, Integer>();
		Iterator <DocQueryWordTermFrequency> iter = queryTerminDocFrequencies.iterator();
		DocQueryWordTermFrequency next;
		while(iter.hasNext()) {
			next = iter.next();
			if(next.documentName.equals(documentName)) {
				queryWordsFrequenciesMap.put(next.queryWord, next.frequency);
			}
		}
		return queryWordsFrequenciesMap;
	}
	
	private double queryWordScore(File documentD, String queryWord) {
		int frequency;
		double IDF = calcIDF(numberDocsContainingQin.get(queryWord));
		HashMap<String, Integer> queryWordsFrequenciesMap = getQueryWordsSingleDocFrequenciesList(documentD.getName());
		int docLengthInWords = allDocsLengthInWords.get(documentD.getName());
		frequency = queryWordsFrequenciesMap.get(queryWord);
		if(frequency==0) frequency = 1; 
		double numerator = IDF*frequency*(k1+1);
		double denominator = frequency+(k1*(1-b+(b*(docLengthInWords/avgNumWordsDocs))));
		return numerator/denominator;
	}

	private double calcIDF(int numberDocsContainingONEQin) {
		return Math.log((totalNumDocsInCollectionN - numberDocsContainingONEQin + 0.5)/(numberDocsContainingONEQin + 0.5));
	}
}

//private double queryWordScore(File documentD, String queryWord) {
//	int frequency;
////	System.out.println("documentD"+documentD+"---------------------------------------------------------------------------------");
////	System.out.println("queryWord:"+ queryWord+"----------------------------------------");
////	System.out.println("numberDocsContainingQin"+numberDocsContainingQin);
//	double IDF = calcIDF(numberDocsContainingQin.get(queryWord));
////	System.out.println("idf"+IDF);
//	HashMap<String, Integer> queryWordsFrequenciesMap = getQueryWordsSingleDocFrequenciesList(documentD.getName());
//	int docLengthInWords = allDocsLengthInWords.get(documentD.getName());
//	frequency = queryWordsFrequenciesMap.get(queryWord);
//	if(frequency==0) frequency = 1; //TODO temporary fix
//	//System.out.println("frequency"+frequency);
//	double numerator = IDF*frequency*(k1+1);
//	//System.out.println("numerator"+numerator);
//	//System.out.println("docLengthInWords"+docLengthInWords);
//	double denominator = frequency+(k1*(1-b+(b*(docLengthInWords/avgNumWordsDocs))));
//	//System.out.println(denominator+"denominator");
////	System.out.println("----------------------------------------------------------------");
//	return numerator/denominator;
//}
//
//private double calcIDF(int numberDocsContainingONEQin) {
//	//System.out.println("calcIDFtotalNumDocsInCollectionN"+totalNumDocsInCollectionN);
//	//System.out.println("calcIDFnumberDocsContainingONEQin"+numberDocsContainingONEQin);
//	return Math.log((totalNumDocsInCollectionN - numberDocsContainingONEQin + 0.5)/(numberDocsContainingONEQin + 0.5));
//}
