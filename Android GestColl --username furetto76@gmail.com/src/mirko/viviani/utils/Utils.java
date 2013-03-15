package mirko.viviani.utils;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.net.Uri;

public class Utils {
	
	public static Intent getViewFileIntent(File file) throws Exception {
		Intent intent = null;
    	if (file.exists())
    	{
    		boolean isPdf = false;
    		try 
    		{
    			/* se esiste lo apre, scegliendo fra pdf e immagine */
    			intent = new Intent(Intent.ACTION_VIEW);
    			Uri itemFilenameUri = Uri.fromFile(file);
    			/* sceglie il mime */
    			if (file.getName().endsWith(".pdf")) {
    				isPdf = true;
    				intent.setDataAndType(itemFilenameUri, "application/pdf");
    			} else {
    				isPdf = false;
    				intent.setDataAndType(itemFilenameUri, "image/*");
    			}
    		}
    		catch (android.content.ActivityNotFoundException ex) {
    			// Potentially direct the user to the Market with a Dialog
    			String msg = "Please install a PDF Manager.";
    			if (isPdf == false)
    				msg = "Please install an image viewer.";
    			
    			throw new Exception(msg);
    		}
    	}
    	return intent;
		
	}

	
	
	public static String concatStringsWSep(List<String> strings, String separator) {
		if (strings == null)
			return "";
		
	    StringBuilder sb = new StringBuilder();
	    String sep = "";
	    for(String s: strings) {
	        sb.append(sep).append(s);
	        sep = separator;
	    }
	    return sb.toString();                           
	}	
}


