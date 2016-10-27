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
import android.webkit.WebView;


/**
 * @author Fritz Lindhauer
 *
 */
public class MyVersionHtmlHandler {
	
	
	//Hinter der WebView steckt die WebKit Browserimplementierung für die Darstellung von Webseiten.
	public void initialisiereWebKit(WebView view){			
		
		 // Load the URL of the HTML file
        view.loadUrl("file:///android_asset/version_html_fgl.html");
	}

}
