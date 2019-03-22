import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Main {
	public static void main(String args[]) throws IOException{
		Query query = new Query();
		ArrayList<String> queryWords = query.wordsList;		
		final File folder = new File("C:/EclipseWorkspaces/InformationRetreival/USAPresidents/Presidents");
		DocumentExtracter documents = new DocumentExtracter(folder);
		
		ArrayList<File> docs = documents.documentsList;
		HashMap<String,  ArrayList<String>> docsWordsList = new HashMap<String, ArrayList<String>>();
		for (File doc: docs) {
			Parser test = new Parser(doc.getName());
			test.read();
			String check = test.getPlainText();
			DocumentReader readDoc = new DocumentReader(check);
			ArrayList<String> docWords = readDoc.wordsList;
			docsWordsList.put(doc.getName(),docWords);
		}
		BM25Variables bmvar = new BM25Variables(docsWordsList, queryWords);
		BM25ScoringFunction bmscoring = new BM25ScoringFunction(1.2, 0.75, bmvar.totalNumDocsInCollectionN, 
				bmvar.allDocsLengthInWords, bmvar.avgNumWordsDocs,bmvar.numberDocsContainingQin, bmvar.queryTerminDocFrequencies);
		HashMap<String, Double> bm25scores = bmscoring.getBM25scores(docs, queryWords);
		
		//test	
		Map<String, Double> bm25scoresSorted = sortFucntion(bm25scores);
		Iterator<String> iter = bm25scoresSorted.keySet().iterator();
		String next;
		String maxDoc="didn't find document";
		Double max = new Double(Double.MIN_VALUE);
		//int tiMax = 0;
		String tiMaxDoc = "Not Found.";
		System.out.println("Top 10 search results");
		int i = 0;
		boolean boo = false;
		ArrayList<String> print = new ArrayList<String>();
		ArrayList<String> titlePrint = new ArrayList<String>();

		while(iter.hasNext()) {
			next = iter.next();
			String presidentName = next.substring(0, next.length()-4);
			String[] titleWords = presidentName.split(" ");
			for(int index = 0; index < titleWords.length; index++) {
				if((queryWords.contains(titleWords[index].toLowerCase()) || queryWords.contains(titleWords[index]))&&!titlePrint.contains(presidentName)) {
					titlePrint.add(presidentName);
				}
			}
			if(i < 10)
				print.add(next.substring(0, next.length()-4));
			i++;
		}
//		if(!tiMaxDoc.equals("Not Found.")){
//			System.out.println("answerTitle"+tiMaxDoc);
//		}
		int a = 0;
		if(!titlePrint.isEmpty()){
			for(a = 0; a < titlePrint.size(); a++){
				System.out.println(a+1+". "+titlePrint.get(a));
			}
		}
		int num = a;
		for(int b = 0; b < print.size(); b++) {
			if(num+b+1 < 11)
				if(titlePrint.contains(print.get(b))) num++;
				else System.out.println(num+b+1+". "+print.get(b));
		}
	}
	
	public static Map<String, Double> sortFucntion(Map<String, Double> bm25scores) {
	
		List<Map.Entry<String, Double>> list =
	            new LinkedList<Map.Entry<String, Double>>( bm25scores.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<String, Double>>()
	    {
	        public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
	        {
	            return (o2.getValue()).compareTo( o1.getValue() );
	        }
	    } );
		Map<String, Double> result = new LinkedHashMap<String, Double>();
	    for (Map.Entry<String, Double> entry : list)
	    {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    return result;
	}
}
