package de.fgl.tryout.android.training001;

import java.util.ArrayList;

import de.fgl.tryout.android.training001.MainActivity.PlaceholderFragmentMain;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;
import android.print.PrintManager;

//public class DisplaySearchWebActivity extends Activity {

//Damit eine Menüleiste angezeigt wird. Aber ActionBarActivity ist deprecated
//public class DisplaySearchWebActivity extends ActionBarActivity {

//AppCompatActivity wird wohl über die SupportBibiotheken eingebunden.
public class DisplayWebviewActivityForSearch<T> extends AppCompatActivity {
	private MyMessageStoreFGL<T> objStore=null; //Merke: Der objStore muss auf Activity-Ebenen bleiben. Wird es auf Fragment-Ebene gespeichert geht das Objekt beim Start einer Activity daraus verloren. 
	public void setMessageStore(MyMessageStoreFGL<T> objStore){
		this.objStore = objStore;
	}
	public MyMessageStoreFGL<T> getMessageStore(){
		if(this.objStore==null){
			this.objStore = new MyMessageStoreFGL<T>();
		}
		return this.objStore;
	}
	
	//Weil wir das Fragment später noch ansteuern wollen: Hier als private Variable deklarieren
	PlaceholderFragmentSearch fragmentWebView = null;
		
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_search_web);
		
		//++++++++++++++++++++++++++++++++++++++++++++++
		// Get the message from the intent
		Intent intent = getIntent();
		if(intent==null){
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - Intent ist NULL ");						
		}else{
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - Intent gefunden.");
			
			//FGL 20161125: Statt den String direkt zu übernehmen jetzt ein StoreObjekt verwenden, in dem auch Werte enthalten sind,
			//                     die ggfs. nur zwischengespeichert wurden, um sie an eine andere Activity weiter/wieder zurückzugeben.
			//String message = intent.getStringExtra(MyMessageHandler.EXTRA_MESSAGE);
										
			//Dieser Store wird auch von den Fragments genutzt.
			MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);
			if(objStore==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - ObjectStore ist NULL.");			
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - ObjectStore gefunden.");			
				this.setMessageStore(objStore);		
			}
		}
		
		//++++++++++++++++++++++++++++++++++++++++++++++		
		//SO WIRD DANN DAS FRAGMENT EINGEBUNDEN, WELCHES ALS INTERNE KLASSE HIER AUCH DEFINIERT IST.
		//WENN DAS FRAGMENT ENTSPRECHEND DEFINIERT IST tools:context="de.fgl.tryout.android.training001.DisplaySearchWebActivity$PlaceholderFragment"
		//MAN MAN MUSS DIE GEWUENSCHTEN LAYOUT-ELEMENTE AUS DER ACTIVITY IN DAS FRAGMENT VERSCHIEBEN UND DABEI BEACHTEN, DASS SIE IM FRAGEMENT in ANDEREN METHODEN DEFINIERT WERDEN.
		if (savedInstanceState == null) {		
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - savedInstanceState Bundle ist NULL ");
						
			//Nun erst das Fragment erstellen und in einer private Variablen speichern. Wir brauchen es späer noch.
			//Merke: Das PlaceholderFragment ist hier ein interne Klasse. Code dafür siehe unten.
			fragmentWebView = new PlaceholderFragmentSearch();		
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - Fragment erstellt: '" + fragmentWebView.getClass().getSimpleName() + "'");
			fragmentWebView.setRetainInstance(true);//FGL Das soll verhindern, dass sich die WebView neu lädt, wenn das Gerät gedreht wird mit (Strg + F12). Technisch gesehen wird es - wie ich es verstanden habe - beim Refresh der Activity aus dem Baum genommen und wieder angehängt.
			//funktioniert an dieser Stelle nicht. mal woanders ausprobieren. Nur wo?
			
			//Das Fragment dem FragmentManager hinzufügen
			getFragmentManager().beginTransaction()
				.add(R.id.container, fragmentWebView)
				.commit();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - Fragment hinzugefügt: '" + fragmentWebView.getClass().getSimpleName() + "'");
						
			//Merke: Nicht Löschen, schönes Beispiel!
			//hier werden Informationen an das Fragment übergeben.
//			DetailsFragment details = new DetailsFragment();
//            details.setArguments(getIntent().getExtras());
//            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();

		}else{
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - savedInstanceState Bundle ungleich NULL ");
			if(objStore==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - StoreObject IS NULL.");
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - StoreObject FOUND.");				
				initValueDriven(objStore);
				
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
	//						android.support.v7.app.ActionBar actionBar = getSupportActionBar();  //funktioniert nur in einer ActionBarActivity
	//						actionBar.setDisplayHomeAsUpEnabled(true);
	//						
	//						//Style den Hintergrund		
	//						actionBar.setBackgroundDrawable(new ColorDrawable(iColor)); // set your desired color
				}else{
					Log.d("FGLSTATE",this.getClass().getSimpleName()+".onCreate(..) - minSdkVersion is 11 or higher.");
					
					// If your minSdkVersion is 11 or higher, instead use:
					android.app.ActionBar actionBar = getActionBar();
					if(actionBar==null){
						//TODO GOO 20160818: Warum ist Action Bar NULL?
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - action bar IS NULL.");
						
					}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - action bar not null.");
					
					actionBar.setDisplayHomeAsUpEnabled(true);
					
					//Style den Hintergrund			
					actionBar.setBackgroundDrawable(new ColorDrawable(iColor)); // set your desired color
					}
				}			
			}//end if objStore == null
		}//end if savedInstanceState==null	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateOptionsMenu() für DisplayWebViewActity aufgerufen.");
		// Inflate the menu; this adds items to the action bar if it is present.
		//Aber nur, wenn der WebView-Client meldet, dass die Seite schon geladen wurde.
		//FGL Cool: Hier wird direkt auf die private Variable einer internen Klasse zugegriffen. .... Oder doch nicht... irgendeinen Grund wird der Fehler schon haben...
		if(fragmentWebView==null){
			//DAS passiert beispielsweise, wenn die Orientierung des Geräts geändert wird (STRG + F12)
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateOptionsMenu() fragmentWebView ist NULL.");
			
			//Lösungsansatz: Hole fragmentWebView nun per FragmentManager. Es soltle da sein und nicht neu gemacht werden müssen.
			fragmentWebView = (PlaceholderFragmentSearch) getFragmentManager().findFragmentById(R.id.container);
			if(fragmentWebView==null){				
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateOptionsMenu() fragmentWebView bleibt auch nach Suche über FragmentManager NULL.");
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateOptionsMenu() fragmentWebView erfolgreich gefunden per Suche über FragmentManager.");				
			}
		}
		
		if(fragmentWebView!=null){
			MyWebViewClient objWebViewClient = fragmentWebView.getWebViewClient();
			if(objWebViewClient!=null){
			    if (objWebViewClient.isDataLoaded()) {
			    	Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateOptionsMenu() webViewClient meldet webSeite geladen.");
			    	getMenuInflater().inflate(R.menu.display_search_web, menu);
		        }else{
		        	Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateOptionsMenu() webViewClient meldet webSeite (noch) nicht geladen.");
		        }
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateOptionsMenu() webViewClient ist NULL.");
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
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onOptionsItemSelected() -geclickte id ='"+ id +"'.");
		
		switch(id) {
		case 16908332:
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onOptionsItemSelected() -geclickt wurde auf den 'Zurück-Link' der Optionsleiste.");
			Intent intent = new Intent(this, MainActivity.class);	
			
		//Packe den neuen Wert in das StoreObjekt zurück.
		PlaceholderFragmentSearch<T> frgmain = (PlaceholderFragmentSearch<T>) getFragmentManager().findFragmentById(R.id.container);
			
		//Packe das StoreObjekt in das Bundle
		//Hole in der MainActivity die Werte zurück.....				
		MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) this.getMessageStore();//getIntent().getSerializableExtra(MyMessageHandler.EXTRA_STORE);
		if(objStore==null){
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onOptionsItemSelected() - Kein objStore aus dem Fragment='"+ frgmain.getClass().getSimpleName()+"' zu bekommen.");
		}else{
			//! Wenn das gleiche zurückgegeben wir, was reinkommt, braucht man das nicht zu holen und zurückzuschreiben.
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onOptionsItemSelected() - MessageCurrent aus dem Fragment '"+ frgmain.getClass().getSimpleName()+"' - objStore ='"+ frgmain.getMessageCurrent()+"'.");				
			objStore.put(MyMessageHandler.MESSAGE_RESUME, frgmain.getMessageCurrent());

			//MERKE: Erzeuge kein neuse bundle-Objekt. Das geht verloren.
			//intent.putExtra(MyMessageHandler.EXTRA_STORE, bundle); //So wird nix entgegengenommen	
			intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore); 
			startActivity(intent); //Merke: Nachteil ist, das jeder Activity-Start quasi in eine History kommt. 
	  		                       //       Das bedeutet, dass der Zurück-Button des Geräts erst einmal alle Activities aus der Historie durchläuft,
	  		                       //       wenn man ihn in der Hauptmaske benötigt.
			}//end if objStore == null
		//}//end if id == zurück-Button
										
			return true;
		case R.id.action_settings:
			return true;
		case R.id.action_end:
			finishIt();
			return true;
		case R.id.action_print:
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onOptionsItemSelected(..) - PRINT.");						 
			print();//Idee aus Android SDK Legacy - API Demos - Bereich: API/APPS/printHTMLfromScreen.java 
            return true;
       default:
    	   Log.d("FGLSTATE", this.getClass().getSimpleName()+".onOptionsItemSelected(..) - DEFAULT.");
    	   return super.onOptionsItemSelected(item); //WICHTIG: Wenn das ausgeführt wird, habe ich es nie geschaft etwas über intent.extras zurückzugeben!
		}
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
	 
	 private void finishIt() {	
		//	finish(); //Aber: Beendet nur diese Activity, nicht aber die Start Activity
			finishAffinity(); //Beendet auch alle "Parent Activities", Ab Android 4.1.
		}
			
		private void initValueDriven(MyMessageStoreFGL<T> objStore){
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): START.");
			if(objStore==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): Kein ObjStore.");
				
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): ObjStore vorhanden.");
				
//				 //FGL: Wenn man die View im Layout-Editor erstellt hätte, kann man eine ID vergeben, die hier benutzt werden könnte.
//				// Create the text view
//			    TextView textView = new TextView(this);
//			    textView.setTextSize(40);
//			    textView.setText(this.getMessageCurrent());
//
//			    // Set the text view as the activity layout
//			    setContentView(textView);

			}//end if objStore == null
		}
	 
	 
	

//################ INNERE FRAGMENT KLASSE(N) AB HIER 
	/**
	 * A placeholder fragment containing the SearchView
	 * FGL: Dieser Code basiert auf "DisplayWebviewActivityForVersion", der Lösung für Dialogbox & Webview,
	 *      welche Actity erweitert. Allerdings sind die im Fragment zu verwendenen Methoden andere, da das Fragement in einer Activity eingebunden ist.
	 */
	public static class PlaceholderFragmentSearch<T> extends Fragment {
		private MyWebViewClient objWebViewClient=null;
		/* MERKE: objStore Objekt, muss auf Activity-Ebene sein. Ansonsten geht es beim Start einer anderen Activity verloren
		private MyMessageStoreFGL<T> objStore=null;
		private void setMessageStore(MyMessageStoreFGL<T> objStore){
			this.objStore = objStore;
		}
		private MyMessageStoreFGL<T> getMessageStore(){
			if(this.objStore==null){
				this.objStore = new MyMessageStoreFGL<T>();
			}
			return this.objStore;
		}
		*/
		private void setMessageStore(MyMessageStoreFGL<T> objStore){
			DisplayWebviewActivityForSearch objActivityParent = (DisplayWebviewActivityForSearch)this.getActivity();
			objActivityParent.setMessageStore(objStore);
		}
		private MyMessageStoreFGL<T> getMessageStore(){
			DisplayWebviewActivityForSearch objActivityParent = (DisplayWebviewActivityForSearch)this.getActivity();
			return objActivityParent.getMessageStore();
		}

		private void setMessageCurrent(String message) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageCurrent() für '" + message + "'");
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				objStore.put(message, MyMessageHandler.MESSAGE_RESUME);
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageCurrent() findet kein Store Objekt.");
			}					
		}
		private String getMessageCurrent(){					
			Log.d("FGLSTATE", this.getClass().getSimpleName()+". getMessageCurrent() gestartet.");
			String sReturn = new String("");
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				sReturn = objStore.getString(MyMessageHandler.MESSAGE_RESUME);
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+". getMessageCurrent() findet kein Store Objekt.");
			}
			return sReturn;
		}
		
		private void setMessageChoosen(String message) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageChoosen() für '" + message + "'");
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				objStore.put(message, MyMessageHandler.MESSAGE_CHOOSEN);
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageChoosen() findet kein Store Objekt.");
			}					
		}
		private String getMessageChoosen(){					
			Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageChoosen() gestartet.");
			String sReturn = new String("");
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				sReturn = objStore.getString(MyMessageHandler.MESSAGE_CHOOSEN);
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageChoosen() findet kein Store Objekt.");
			}
			return sReturn;
		}
				
		public PlaceholderFragmentSearch() {
		}

		
		// this method is only called once for this fragment
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // retain this fragment //FGL Versuche die WebView nicht neu zu laden, wenn man die Orientierung des Geräts ändert.
	        setRetainInstance(true);	        	        	        
	    }//end onCreate()
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			this.getWebViewClient();
						
			View rootView = inflater.inflate(
					R.layout.fragment_display_search_web, container, false);
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() start.");
			
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
		
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);	
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle) wurde aktiviert");
			
			//Damit die Events an den Buttons erhalten bleiben, wie im onCreate();			
			initButtons();
			
			//Arbeite nun ggfs. mit den erhaltenen Daten weiter
			if(savedInstanceState!=null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle)SAVEDINSTANCESTATE IST NICHT NULL");
				
				//Greife auf den objectStore der activity zu.
				//TODO: FGL 20161211: Die Klasse, der Activity, die hier benötigt wird irgendwo herholen.
				//                    oder es so gestalten, dass hier ein Interface z.B. useStoreableZZZ verwendet wird.
				MyMessageStoreFGL<T> objStore = ((DisplayWebviewActivityForSearch<T>)getActivity()).getMessageStore();
				if(objStore==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): objStore ist null");
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): objStore ist NICHT null");
					
					//Blende nun ggfs. die verborgenen Buttons wieder ein.
					initValueDriven(objStore);	
				}
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle)SAVEDINSTANCESTATE IST NULL");							
			}
		}
		 
		//############################################################
		private void initButtons(){
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".initButtons(): START");
			
					//FGL 20161204: Wenn man die Events im Fragment selbst haben will,
					//              muss man den Event-Handler am Button hier einbauen.
					//              sonst gibt es Fehlermeldungen der Art:
					//12-04 06:02:41.664: E/AndroidRuntime(1778): java.lang.IllegalStateException: Could not find method sendMessage(View) in a parent or ancestor Context for android:onClick attribute defined on view class android.support.v7.widget.AppCompatButton with id 'button_send'
					//              Alternativ dazu die Methode in der Activity belassen, was meiner Meinung nach eine unschöne Lösung ist, wg. mangelnder Kapselung.
		
		//	Button button_send_for_result = (Button) getActivity().findViewById(R.id.button_send_for_result);
		//    Button button_send = (Button) getActivity().findViewById(R.id.button_send);
		//    Button button_search = (Button) getActivity().findViewById(R.id.button_search_web);
		//    Button button_add = (Button) getActivity().findViewById(R.id.button_add_search_list);
		//    
				//FGL 20161204: Das geht so nicht... zumindest in Fragments geht das nicht.
				   /*button.setOnClickListener(new OnClickListener()
				   {
				             @Override
				             public void onClick(View v)
				             {
				                // do something
				             } 
				   }); */
					
					//FGL 20161204: Alternativer Weg für den Listener, wenn das Fragment view.OnClickListener implementiert
		//			if(button_send_for_result!=null) button_send_for_result.setOnClickListener(this);
		//			if(button_send!=null) button_send.setOnClickListener(this);
		//			if(button_search!=null) button_search.setOnClickListener(this);
		//			if(button_add!=null)button_add.setOnClickListener(this);
				}

		private void initValueDriven(MyMessageStoreFGL<T> objStore){
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): START.");
			if(objStore==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): Kein ObjStore.");
				
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): ObjStore vorhanden.");
				
//					 //FGL: Wenn man die View im Layout-Editor erstellt hätte, kann man eine ID vergeben, die hier benutzt werden könnte.
//					// Create the text view
//				    TextView textView = new TextView(this);
//				    textView.setTextSize(40);
//				    textView.setText(this.getMessageCurrent());
//
//				    // Set the text view as the activity layout
//				    setContentView(textView);

			}//end if objStore == null
		}
		 
		 private void initialisiereWebKit(WebView view, String sSearch){				
				view.loadUrl("https://www.google.de/search?q="+sSearch);				
			}
		 
		 private void initialisiereWebKit(WebView view){							
				//hole den Suchstring 
				// Activity ac = (Activity) view.getContext();
				DisplayWebviewActivityForSearch ac = (DisplayWebviewActivityForSearch) view.getContext();
				//cast geht nicht PlaceholderFragmentMain<T>myFragment=(PlaceholderFragmentMain<T>) ac.getFragmentManager().findFragmentById(R.id.container);
				PlaceholderFragmentSearch<T>myFragment=this;
				//String sSearch=myFragment.getMessageCurrent();//Das ist der String für das Suchwerteingabefeld
				String sSearch=myFragment.getMessageChoosen(); //Das ist der String für die eigentliche Suche
				
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initialisiereWebKit() mit Suchstring: " + sSearch);
				
				//ACHTUNG: Nicht Löschen... gute Beispiele...
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
						//Merke: WebView hat aus Sicherheitsgründen strenge Einschränkungen, was darin funktioniert. (z.B. kein Zugriff auf Cookies oder BrowserCache)
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
				 Log.d("FGLSTATE", this.getClass().getSimpleName()+".getWebViewClient() erstelle neuen WebViewClient.");
				 this.objWebViewClient = new MyWebViewClient();
			 }else{
				 Log.d("FGLSTATE", this.getClass().getSimpleName()+".getWebViewClient() Zugriff auf bestehenden WebViewClient.");
			 }
			 return this.objWebViewClient;
		 }
	}//END CLASS PLACEHOLDERFRAGMENT
}//END CLASS DisplayWebviewActivityForSearch
