import java.io.File;
import java.util.ArrayList;

public class DocumentExtracter {
	public ArrayList<File> documentsList;
	
	public DocumentExtracter(final File folder) {
		documentsList = listFilesForFolder(folder);
	}
	
	public ArrayList<File> listFilesForFolder(final File folder) {
		ArrayList<File> files = new ArrayList<File>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            files.add(fileEntry);
	        }
	    }
	    return files;
	}
}
