package de.fgl.tryout.android.training001;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Build;
import android.print.PrintManager;

//public class DisplaySearchWebActivity extends Activity {

//Damit eine Menüleiste angezeigt wird. Aber ActionBarActivity ist deprecated
//public class DisplaySearchWebActivity extends ActionBarActivity {

//AppCompatActivity wird wohl über die SupportBibiotheken eingebunden.
public class DisplayWebviewActivityForSearch extends AppCompatActivity {
	//Weil wir das Fragment später noch ansteuern wollen: Hier als private Variable deklarieren
	PlaceholderFragment fragmentWebView = null;
	private String sMessageCurrent;
	
	/**
	 * @param message
	 * 15.07.2016 08:26:09 Fritz Lindhauer
	 * Test auf Änderungen
	 */
	private void setMessageCurrent(String message) {
		this.sMessageCurrent= message;
		Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageCurrent() f�r '" + message + "'");
		
	}
	private String getMessageCurrent(){
		Log.d("FGLSTATE", this.getClass().getSimpleName()+". getMessageCurrent() f�r '" + this.sMessageCurrent + "'");
		return this.sMessageCurrent;		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_search_web);
		
		//++++++++++++++++++++++++++++++++++++++++++++++
		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(MyMessageHandler.EXTRA_MESSAGE);
		Log.d("FGLTEST", this.getClass().getSimpleName()+". getMessageCurrent() für die Suche '" + message + "'");
		this.setMessageCurrent(message);
		//++++++++++++++++++++++++++++++++++++++++++++++
		
		
		//SO WIRD DANN DAS FRAGMENT EINGEBUNDEN, WELCHES ALS INTERNE KLASSE HIER AUCH DEFINIERT IST.
		//WENN DAS FRAGMENT ENTSPRECHEND DEFINIERT IST tools:context="de.fgl.tryout.android.training001.DisplaySearchWebActivity$PlaceholderFragment"
		//MAN MAN MUSS DIE GEWUENSCHTEN LAYOUT-ELEMENTE AUS DER ACTIVITY IN DAS FRAGMENT VERSCHIEBEN UND DABEI BEACHTEN, DASS SIE IM FRAGEMENT in ANDEREN METHODEN DEFINIERT WERDEN.
		if (savedInstanceState == null) {
			//Merke: Das PlaceholderFragment ist hier ein interne Klasse. Code dafür siehe unten.
			//getFragmentManager().beginTransaction()
			//		.add(R.id.container, new PlaceholderFragment()).commit();
			
			
			//Nun erst das Fragment erstellen und in einer private Variablen speichern. Wir brauchen es späer noch.
			fragmentWebView = new PlaceholderFragment();					
			fragmentWebView.setRetainInstance(true);//FGL Das soll verhindern, dass sich die WebView neu lädt, wenn das Gerät gedreht wird mit (Strg + F12). Technisch gesehen wird es - wie ich es verstanden habe - beim Refresh der Activity aus dem Baum genommen und wieder angehängt.
			//funktioniert an dieser Stelle nicht. mal woanders ausprobieren. Nur wo?
			
			getFragmentManager().beginTransaction()
				.add(R.id.container, fragmentWebView)
				.commit();
						
			//hier werden Informationen an das Fragment übergeben.
//			DetailsFragment details = new DetailsFragment();
//            details.setArguments(getIntent().getExtras());
//            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();

		}
		
		//++++++++++++++++++++++++++++++++++++++++++++++
		//Den Hintergrund der Menüleiste steuern
		// TODO: Das klappt noch nicht
		//++++++++++++++++++++++++++++++++++++++++++++++
				int iColor;
				//String alarmMessagePrefix = "Alarm";
				//if(message.startsWith(alarmMessagePrefix)){
					iColor = Color.RED;
				//}else{
				//	iColor = Color.GRAY;
				//}
				
		//FGL: Check System Version at Runtime
		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
			//FGL: Aktiviere den Home / UP Button 
//					android.support.v7.app.ActionBar actionBar = getSupportActionBar();  //funktioniert nur in einer ActionBarActivity
//					actionBar.setDisplayHomeAsUpEnabled(true);
//					
//					//Style den Hintergrund		
//					actionBar.setBackgroundDrawable(new ColorDrawable(iColor)); // set your desired color
		}else{
			Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - minSdkVersion is 11 or higher.");
			
			// If your minSdkVersion is 11 or higher, instead use:
			android.app.ActionBar actionBar = getActionBar();
			if(actionBar==null){
				//TODO GOO 20160818: Warum ist Action Bar NULL?
				Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - action bar IS NULL.");
				
			}else{
			Log.d("FGLTEST", "Methode DisplayActivity.onCreate(..) - action bar not null.");
			
			actionBar.setDisplayHomeAsUpEnabled(true);
			
			//Style den Hintergrund			
			actionBar.setBackgroundDrawable(new ColorDrawable(iColor)); // set your desired color
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("FGLTEST", "onCreateOptionsMenu() für DisplayWebViewActity aufgerufen.");
		// Inflate the menu; this adds items to the action bar if it is present.
		//Aber nur, wenn der WebView-Client meldet, dass die Seite schon geladen wurde.
		//FGL Cool: Hier wird direkt auf die private Variable einer internen Klasse zugegriffen. .... Oder doch nicht... irgendeinen Grund wird der Fehler schon haben...
		if(fragmentWebView==null){
			//DAS passiert beispielsweise, wenn die Orientierung des Geräts geändert wird (STRG + F12)
			Log.d("FGLTEST", "onCreateOptionsMenu() fragmentWebView ist NULL.");
			
			//Lösungsansatz: Hole fragmentWebView nun per FragmentManager. Es soltle da sein und nicht neu gemacht werden müssen.
			fragmentWebView = (PlaceholderFragment) getFragmentManager().findFragmentById(R.id.container);
			if(fragmentWebView==null){				
				Log.d("FGLTEST", "onCreateOptionsMenu() fragmentWebView bleibt auch nach Suche über FragmentManager NULL.");
			}else{
				Log.d("FGLTEST", "onCreateOptionsMenu() fragmentWebView erfolgreich gefunden per Suche über FragmentManager.");				
			}
		}
		
		if(fragmentWebView!=null){
			MyWebViewClient objWebViewClient = fragmentWebView.getWebViewClient();
			if(objWebViewClient!=null){
			    if (objWebViewClient.isDataLoaded()) {
			    	Log.d("FGLTEST", "onCreateOptionsMenu() webViewClient meldet webSeite geladen.");
			    	getMenuInflater().inflate(R.menu.display_search_web, menu);
		        }else{
		        	Log.d("FGLTEST", "onCreateOptionsMenu() webViewClient meldet webSeite (noch) nicht geladen.");
		        }
			}else{
				Log.d("FGLTEST", "onCreateOptionsMenu() webViewClient ist NULL.");
			}
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		if (id == R.id.action_end) {
			//finish(); //Aber: Beendet nur diese Activity, nicht aber die Start Activity
			finishAffinity(); //Beendet auch alle "Parent Activities", Ab Android 4.1.
		}
		
		if (id == R.id.action_print){
			Log.d("FGLTEST", "Methode DisplayActivityWebViewActivityForSearch.onOptionsItemSelected(..) - PRINT.");
			
			//Idee aus Android SDK Legacy - API Demos - Bereich: API/APPS/printHTMLfromScreen.java  
			print();
            return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 private void print() {
	        // Get the print manager.
	        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
	        
	        //Hole aus dem gespeicherten Fragment die WebView.
	        //getView holt quasi die Root des Fragments.
	        //darauf aufbauend kann man dann die webView suchen.
	        //View mWebView = fragmentWebView.getView().findViewById(R.id.webView1);
	        WebView mWebView = (WebView) fragmentWebView.getView().findViewById(R.id.webView1);
	        
	        // Pass in the ViewView's document adapter.
	        //createPrintDocumentAdapter ist deprecated: printManager.print("MotoGP stats", mWebView.createPrintDocumentAdapter(), null);
	        //Nun wird der Benutzer gezwungen einen String für den Namen des Dokuments zu übergeben
	        String sDocTitle = new String("mein_WebView_Ausdruck");
	        printManager.print("MotoGP stats", mWebView.createPrintDocumentAdapter(sDocTitle), null);
	    }

	/**
	 * A placeholder fragment containing the SearchView
	 * FGL: Dieser Code basiert auf "DisplayWebviewActivityForVersion", der Lösung für Dialogbox & Webview,
	 *      welche Actity erweitert. Allerdings sind die im Fragment zu verwendenen Methoden andere, da das Fragement in einer Activity eingebunden ist.
	 */
	public static class PlaceholderFragment extends Fragment {
		private MyWebViewClient objWebViewClient=null;
		
		public PlaceholderFragment() {
		}

		
		// this method is only called once for this fragment
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // retain this fragment //FGL Versuche die WebView nicht neu zu laden, wenn man die Orientierung des Geräts ändert.
	        setRetainInstance(true);
	    }

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			this.getWebViewClient();
						
			View rootView = inflater.inflate(
					R.layout.fragment_display_search_web, container, false);
			Log.d("FGLTEST", "PlaceholderFrament.onCreateView() start.");
			
			//Hier, versuche die WebView zu füllen
			WebView wvSearch;
			wvSearch = (WebView) rootView.findViewById(R.id.webView1);
			
			if(wvSearch!=null){
				
				//Für Seiten mit JavaScript und CSS
				wvSearch.getSettings().setJavaScriptEnabled(true);
				
				 //Versuch die Scrollbar permanent zu machen.
			      wvSearch.setScrollbarFadingEnabled(false);
			      wvSearch.setScrollBarFadeDuration(0);
				
			      if(objWebViewClient!=null){
			        	wvSearch.setWebViewClient(this.objWebViewClient);
				    }else{
				    	
				    };
			      
			      
				initialisiereWebKit(wvSearch);
				wvSearch.bringToFront();
				
				 
			      
			      //TODO: Den Inhalt der Suchanfrage per Bundle übergeben.
			      //TODO: Dafür sorgen, dass dies in der gleichen WebView gestartet wird und nicht in einem neuen Browserfenster.
			      //Das passiert nun in "initalisiere Webkit"
			      //wvSearch.loadUrl("https://www.google.de");
			      //wvSearch.loadUrl("https://www.google.de/search?q=android");
				
			}	
			
			return rootView;
		}
		
		 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			//Das wird ausgeführt. 
			Log.d("FGLTEST", "PlaceholderFrament.onActivityCreated() start.");
	    }
		 
		 
		 private void initialisiereWebKit(WebView view, String sSearch){				
				view.loadUrl("https://www.google.de/search?q="+sSearch);				
			}
		 
		 private void initialisiereWebKit(WebView view){							
				//hole den Suchstring 
				// Activity ac = (Activity) view.getContext();
				DisplayWebviewActivityForSearch ac = (DisplayWebviewActivityForSearch) view.getContext();
				String sSearch = ac.getMessageCurrent();
				Log.d("FGLTEST", "initialisiereWebKit mit Suchstring: " + sSearch);
								
				//+++ Variante 1)
				//Anders als bei den Textdateien, kann so die WebViewer Datei nicht angezeigt werden.
				//int contextMenueId = R.raw.version_html_fgl;		
				//InputStream is = context.getResources().openRawResource(contextMenueId);
								
				//+++ Variante 2)
				//final String mimetype = "text/html";
				//final String encoding = "UTF-8";
				//String htmldata;				
				//try{
				//	if (is != null && is.available() > 0) {
				//		final byte[] bytes = new byte[is.available()];
				//		is.read(bytes);
				//		htmldata = new String(bytes);
						//anders als .loadData kann die Methode loadDataWithBaseURL auch statische Webseiten verwenden.
						//Merke: WebView hat aus Sicherheitsgr�nden strenge Einschr�nkungen, was darin funktioniert. (z.B. kein Zugriff auf Cookies oder BrowserCache)
						//       
				//		view.loadDataWithBaseURL(null, htmldata, mimetype, encoding, null);						
				//	}
				//} catch(IOException e){									
				//}
				
				//+++ Variante 3)
				// Load the URL of the HTML file
		        //view.loadUrl("file:///android_asset/version_html_fgl.html");
				//view.loadUrl("https://www.google.de/search?q=android");
				
				initialisiereWebKit(view, sSearch);							
			}
		 
		 public MyWebViewClient getWebViewClient(){
			 if(this.objWebViewClient == null){
				 Log.d("FGLTEST", "PlaceholderFrament.getWebViewClient() erstelle neuen WebViewClient.");
				 this.objWebViewClient = new MyWebViewClient();
			 }else{
				 Log.d("FGLTEST", "PlaceholderFrament.getWebViewClient() zugriff auf bestehenden WebViewClient.");
			 }
			 return this.objWebViewClient;
		 }
	}//END CLASS PLACEHOLDERFRAGMENT
}
