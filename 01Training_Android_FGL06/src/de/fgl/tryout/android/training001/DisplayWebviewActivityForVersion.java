package de.fgl.tryout.android.training001;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

/** Anlaog zu Buch Android 4.4 S. 118 "Hilfe Anzeigen"
 *  @author Fritz Lindhauer
 */
public class DisplayWebviewActivityForVersion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_version);
		
		//WebView aus dem Layout holen
		final WebView view = (WebView) findViewById(R.id.webView1);
		
		//Für Seiten mit JavaScript und CSS
		view.getSettings().setJavaScriptEnabled(true);
		initialisiereWebKit(view, this);
		view.bringToFront();
	}
	
	//Hinter der WebView steckt die WebKit Browserimplementierung für die Darstellung von Webseiten.
	private void initialisiereWebKit(WebView view, Context context){
		final String mimetype = "text/html";
		final String encoding = "UTF-8";
		String htmldata;
		
		//Anders als bei den Textdateien, kann so die WebViewer Datei nicht angezeigt werden.
		//int contextMenueId = R.raw.version_html_fgl;		
		//InputStream is = context.getResources().openRawResource(contextMenueId);

		//Hier sollen die Links in der HTML Datei in einem externen default Browser geöffnet werden. Darum nuten wir zum Anzeigen nicht den WebView-Client.
		//Load the URL of the HTML file
		//Merke: WebView hat aus Sicherheitsgründen strenge Einschränkungen, was darin funktioniert. (z.B. kein Zugriff auf Cookies oder BrowserCache)
        view.loadUrl("file:///android_asset/version_html_fgl.html");
        
		//Merke: so würde eine selbst generierte HTML Seite geladen.
		//try{
		//	if (is != null && is.available() > 0) {
		//		final byte[] bytes = new byte[is.available()];
		//		is.read(bytes);
		//		htmldata = new String(bytes);
				//anders als .loadData kann die Methode loadDataWithBaseURL auch statische Webseiten verwenden.
				//Merke: WebView hat aus Sicherheitsgründen strenge Einschränkungen, was darin funktioniert. (z.B. kein Zugriff auf Cookies oder BrowserCache)
				//       
		//		view.loadDataWithBaseURL(null, htmldata, mimetype, encoding, null);						
		//	}
		//} catch(IOException e){
			
			
		//}
		
	}
	
}
