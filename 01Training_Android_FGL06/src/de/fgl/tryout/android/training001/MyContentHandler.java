/**
 * 
 */
package de.fgl.tryout.android.training001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
//import android.widget.TextView;
import android.util.Log;


/**
 * @author Fritz Lindhauer
 *
 */
public class MyContentHandler {
	
	
	public String readFileAboutFromRawFGL(Activity callingActivity){
		InputStreamReader is = new InputStreamReader(callingActivity.getResources().openRawResource(R.raw.about_fgl));
    	BufferedReader reader = new BufferedReader(is);
    	StringBuilder finalText = new StringBuilder();
    	String line;
    	try {
			while ((line = reader.readLine()) != null) {
			    finalText.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Log.d("FGLTEST", "Methode MyContentHandler.readFileAboutFromRawFGL(). finalText = " + finalText.toString());
    	return finalText.toString();
	}

}
