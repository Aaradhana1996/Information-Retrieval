import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Parser {
	
	int numLinks = 0;
	String inputFilePath;
	String htmlRaw;
	ArrayList<String> subsections;
	HashMap<String,String> tags;
	
	public Parser(String inputFilePath){
        this.inputFilePath = inputFilePath;
        htmlRaw = "";
        tags = new HashMap<String,String>();
    }
	public void read(){
	     String line = null;
	        try {
	       //read text file into a string
	            FileReader fileReader = 
	                new FileReader(inputFilePath);

	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	            	htmlRaw+=line;
	            }   

	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file.");                
	        }
	        catch(IOException ex) {
	        	ex.printStackTrace();
	        }
	   
	}
	public String getInfoBox(){
		
		String rawBox = this.getTag("<table class=" + '"' + "infobox", "</table>");
		String data = "";
		int firstChk = rawBox.indexOf('>');
		rawBox = rawBox.substring(firstChk + 1);
		while(rawBox.indexOf("<") >= 0 && rawBox.indexOf(">") >= 0){
			int endBrac1 = rawBox.indexOf(">");
			int startBrac = rawBox.indexOf("<",endBrac1);
			int endBrac2 = rawBox.indexOf(">", startBrac);
			String str = rawBox.substring(endBrac1+1,startBrac);
			String fill = "&#160;";
			int textCheck = str.indexOf(fill);
			while (textCheck >= 0){
				str = str.substring(0, textCheck) + " "
						+ str.substring(textCheck + fill.length(), str.length());
				textCheck = str.indexOf(fill);
			}
			rawBox = rawBox.substring(endBrac2);
			if(!str.equals(""))
				data = data + str + " ";
		}
		return data.trim().replaceAll("\\s+", " ");
		
	}
	public int countLinks(){
		if(numLinks != 0){
			return numLinks;
		}else{
			this.getLinks();
			return numLinks;
		}
	}
	public String getLinks(){
		try{
			String rawText = this.getTag("<ul id=" + "'" + "whatlinkshere", "</ul>");
			String data = "";
			int firstChk = rawText.indexOf('>');
			rawText = rawText.substring(firstChk + 1);
			while(rawText.indexOf("<") >= 0 && rawText.indexOf(">") >= 0){
				int endBrac1 = rawText.indexOf(">");
				int startBrac = rawText.indexOf("<",endBrac1);
				int endBrac2 = rawText.indexOf(">", startBrac);
				String str = rawText.substring(endBrac1+1,startBrac);
				String fill = "&#160;";
				int textCheck = str.indexOf(fill);
				while (textCheck >= 0){
					str = str.substring(0, textCheck) + " "
							+ str.substring(textCheck + fill.length(), str.length());
					textCheck = str.indexOf(fill);
				}
				
				rawText = rawText.substring(endBrac2);
				if(!str.equals("")){
					data = data + str + " ";
					numLinks++;
				}
			}
			
			return data.trim().replaceAll("\\s+", " ");
		}catch(StringIndexOutOfBoundsException e){
			return "Not a link page";
		}
		
	}
	public String getPlainText(){
		//only use this with plaintext files
		String fill = "&#160;";
		int textCheck = htmlRaw.indexOf(fill);
		String str = htmlRaw;
		while (textCheck >= 0){
			str = str.substring(0, textCheck) + " "
					+ str.substring(textCheck + fill.length(), str.length());
			textCheck = str.indexOf(fill);
		}
		return str.trim().replaceAll("\\s+", " ");
	}
	
	public String getMainText(){
		String rawText = "";
			rawText = this.getTag("</table>", "<table class=" + '"' + "navbox");
		
		String data = "";
		int firstChk = rawText.indexOf("<p>");
		rawText = rawText.substring(firstChk);
		while(rawText.indexOf("<") >= 0 && rawText.indexOf(">") >= 0){
			int endBrac1 = rawText.indexOf(">");
			int startBrac = rawText.indexOf("<",endBrac1);
			int endBrac2 = rawText.indexOf(">", startBrac);
			String str = rawText.substring(endBrac1+1,startBrac);
			String fill = "&#160;";
			int textCheck = str.indexOf(fill);
			while (textCheck >= 0){
				str = str.substring(0, textCheck) + " "
						+ str.substring(textCheck + fill.length(), str.length());
				textCheck = str.indexOf(fill);
			}
			rawText = rawText.substring(endBrac2);
			if(!str.equals(""))
				data = data + str + " ";
		}
		return data.trim().replaceAll("\\s+", " ");
	}
	public String getTag(String tagStart, String tagEnd){
		
		if(tags.containsKey(tagStart)){
			return tags.get(tagStart);
		}
		String res = "";	
		int start = htmlRaw.indexOf(tagStart)+tagStart.length();
		int end = htmlRaw.indexOf(tagEnd, start);
		res = htmlRaw.substring(start, end);
		return res;
	}
	
}

