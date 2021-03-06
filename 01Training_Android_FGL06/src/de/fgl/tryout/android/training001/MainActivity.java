package de.fgl.tryout.android.training001;


import java.util.ArrayList;

import basic.zBasic.util.datatype.string.StringZZZ;
import biz.tekeye.abouttest.AboutBox;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Aus developer.android.com Training/Building Your First App
 * @Date   2014-05-06
 * @author Fritz Lindhauer
 * @param <T>
 *
 */
public class MainActivity<T> extends  AppCompatActivity{ // ActionBarActivity { //Merke: ActionBarActivity is deprecated
	//Fragments wegsichern, damit man sie wiederherstellen kann (theoretisch)
	private PlaceholderFragmentList  fragmentList;
	private PlaceholderFragmentMain fragmentMain;
	
	/* MERKE: objStore Objekt, muss auf Activity-Ebene sein. Ansonsten geht es beim Start einer anderen Activity verloren*/
	private MyMessageStoreFGL<T> objStore=null;
	public void setMessageStore(MyMessageStoreFGL<T> objStore){
		this.objStore = objStore;
	}
	public MyMessageStoreFGL<T> getMessageStore(){
		if(this.objStore==null){
			this.objStore = new MyMessageStoreFGL<T>();
		}
		return this.objStore;
	}
	
	private MyVersionBox versionBox = null;
	public MyVersionBox getVersionBox() {
		if(this.versionBox==null){
			MyVersionBox a = new MyVersionBox();
			this.versionBox = a;
		}
		return this.versionBox;
	}
	public void setVersionBox(MyVersionBox versionBox) {
		this.versionBox = versionBox;
	}	
	
	private MyVersionHtmlBox versionHtmlBox = null;
	public MyVersionHtmlBox getVersionHtmlBox() {
		if(this.versionHtmlBox==null){
			MyVersionHtmlBox a = new MyVersionHtmlBox();
			this.versionHtmlBox = a;
		}
		return this.versionHtmlBox;
	}
	public void setVersionHtmlBox(MyVersionHtmlBox versionHtmlBox) {
		this.versionHtmlBox = versionHtmlBox;
	}	
	
	private MyAboutBox aboutBox = null;
	public MyAboutBox getAboutBox() {
		if(this.aboutBox==null){
			MyAboutBox a = new MyAboutBox();
			this.aboutBox = a;
		}
		return this.aboutBox;
	}
	public void setAboutBox(MyAboutBox aboutBox) {
		this.aboutBox = aboutBox;
	}
	
//############################################################################################
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() : START");
		// Set the user interface layout for this Activity
	    // The layout file is defined in the project res/layout/main_activity.xml file
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			//FGL06: füge die Fragments in eine ScrollView, so dass Sie auch als Gesamtheit scrollbar sind
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() wurde aktiviert. KEIN SAVEDINSTANCESTATE vorhanden");
			this.fragmentMain =  new PlaceholderFragmentMain();
			this.fragmentMain.setRetainInstance(true);
			getSupportFragmentManager().beginTransaction()
				.add(R.id.containerScrollViewActivityMain, this.fragmentMain,"FRAGMENT_MAIN").commit();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() FRAGMENT_MAIN erzeugt.");
			
			//FGL06: füge eine Liste der Suchbegriffe hinzu, und die Möglichkeit daraus auswählend (ggfs. kombiniert) zu suchen
			this.fragmentList = new PlaceholderFragmentList();
			this.fragmentList.setRetainInstance(true);
			getSupportFragmentManager().beginTransaction()
				.add(R.id.containerScrollViewActivityMain, this.fragmentList,"FRAGMENT_MAIN_LIST").commit();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() FRAGMENT_MAIN_LIST erzeugt.");
			
		 } else {
			//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
			//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
			//            und Eclipse neu starten muss.
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() wurde aktiviert. MIT SAVEDINSTANCESTATE vorhanden");
			 	
	         this.fragmentList = (PlaceholderFragmentList) getSupportFragmentManager().getFragment(savedInstanceState,"fragmentList");
	         this.fragmentMain = (PlaceholderFragmentMain) getSupportFragmentManager().getFragment(savedInstanceState,"fragmentMain");
        }//end 	if (savedInstanceState == null) {
	}//END MainActivity.onCreate(...)
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Log.d("FGLSTATE", "onOptionsItemSelected() id='"+id+"'.");
		
		if (id == R.id.action_end) {
			finishIt();			
		}
		
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.action_about) {
			Log.d("FGLSTATE", "onOptionsItemSelected(): ABOUT BOX");
			//Original (s.05_TryOut_AndroidCookbook_ .... ) aus einem Button einer Main-Activity-Klasse: AboutBox.Show(Main.this);
			//AboutBox.Show(this);
			
			//20160906: Hole nun eine von mir überarbeitet Version
			//MyVersionHandler versionHandler = new MyVersionHandler();
			//MyVersionAboutBox versionAboutBox = new MyVersionAboutBox(versionHandler);
			//this.getVersionAboutBox().setVersionHandler(versionHandler);
			this.getAboutBox().Show(MainActivity.this);
			//versionAboutBox.Show(this);			
		}
		if(id== R.id.action_version_txt){
			//Darstellung der Version Box, basierend auf einem TextString
			this.getVersionBox().Show(MainActivity.this);			
		}
		if(id==R.id.action_version_html){
			//Darstellung der Version, als eigene Activity mit einer WebView, nur um das lauff�hig zu bekommen.
			//Vgl buch Android 4.4, S. 118
			//final Intent intent = new Intent(this, DisplayWebviewActivityForVersion.class);
			//startActivity(intent);
			this.getVersionHtmlBox().Show(MainActivity.this);
		}		
		return super.onOptionsItemSelected(item);
	}
	
	private void finishIt() {	
		finishAffinity(); //Beendet auch alle "Parent Activities", Ab Android 4.1.
	}
		
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 * 
	 * SHIFT+ALT+J ist die Tastenkombination für automatische Kommentare.
	 * Nun muss das entsprechene Template angepasst werden.
	 *  
	 */
	@Override
	public void onPause(){
		super.onPause();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
		//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
		//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onPause() wurde aktiviert");
		
	    if (getSupportFragmentManager().findFragmentByTag("FRAGMENT_MAIN") != null)
		        getSupportFragmentManager().findFragmentByTag("FRAGMENT_MAIN").setRetainInstance(true);
	    if (getSupportFragmentManager().findFragmentByTag("FRAGMENT_MAIN_LIST") != null)
	        getSupportFragmentManager().findFragmentByTag("FRAGMENT_MAIN_LIST").setRetainInstance(true);
	}
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onNewIntent(android.content.Intent)
	 * 18.07.2016 08:46:20 Fritz Lindhauer
	 * Versuch dadurch den neuen Intent zu verwenden und nicht einen vorher an die activity iergendwie erzeugten Intent
	 * http://stackoverflow.com/questions/6352281/getintent-extras-always-null
	 */
	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    Log.d("FGLSTATE", this.getClass().getSimpleName()+".onNewIntent() wurde aktiviert");
	    setIntent(intent);
	}
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 * 14.07.2016 08:06:14 Fritz Lindhauer
	 */
	@SuppressWarnings("unchecked")
	public void onResume(){						
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
		//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
		//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume() wurde aktiviert");
		
		Intent intent = getIntent();
		if(intent==null){ //Verhindere einen Fehler, wenn die Activity ohne Intent gestartet wird. Z.B. von der Oberfläche
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - Intent IS NULL.");
		}else{
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - Intent UNGLEICH NULL.");
		
			//FGL 20161125: Statt den String direkt zu übernehmen jetzt ein StoreObjekt verwenden, in dem auch Werte enthalten sind,
			//                		die ggfs. nur zwischengespeichert wurden, um sie an eine andere Activity weiter/wieder zurückzugeben.
								
			// Get the Message from the StoreObject, stored in the intent.
			MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);			
			if(objStore==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - StoreObject IS NULL.");
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - StoreObject FOUND.");
				this.setMessageStore(objStore);															
			}
		}//getIntent==null
			
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		super.onResume();
	}
	
	public void onStop(){
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onStop() wurde aktiviert");

		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.		
        super.onStop();
	}
	
	public void onRestart(){				
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onRestart() wurde aktiviert");
		
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		super.onRestart();
	}
	
	public void onStart(){
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onStart() wurde aktiviert");
		
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		super.onStart();
	}
	
	public void onSaveInstanceState(Bundle outState){
		//NOTWENDIG ZUM ZWISCHENSPEICHERN DER DATEN IM BUNDLE

		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onSaveInstanceState() wurde aktiviert");
				
		//20161128: FGL06 - nun auch die Fragments speichern, funktioniert das überhaupt???
		//Save the fragment's instance
	    getSupportFragmentManager().putFragment(outState, "fragmentMain", this.fragmentMain);
	    getSupportFragmentManager().putFragment(outState, "fragmentList", this.fragmentList);
	    
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle inState){
		//NOTWENDIG ZUM ZURUECKHOLEN DER ZWISCHENGESPEICHERTEN DATEN AUS DEM BUNDEL. 
		//WIRD ABER NUR BEI onCreate() aufgerufen
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onRestoreInstanceState() wurde aktiviert");
		
		//20161128: FGL06 - nun auch die Fragments speichern, bzw. hier zurückholen
		//load the fragment's instance
		//TODO 20161211: Klappt das irgendwie???
		this.fragmentMain = (PlaceholderFragmentMain) getSupportFragmentManager().getFragment(inState, "fragmentMain");
		this.fragmentList = (PlaceholderFragmentList) getSupportFragmentManager().getFragment(inState, "fragmentList");
		
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		super.onRestoreInstanceState(inState);
	}
	
	
	

	/**
	 * A placeholder fragment containing a simple view.
	 * ACHTUNG: Der Code für die Buttons soll hier herein. 
	 *          Daher muss der Button Event-Listener extra hier gesetzt werden.
	 *          Damit dann die onClick Methode vorhanden ist, muss View.OnClickListern implementiert werden.
	 */
	public static class PlaceholderFragmentMain<T> extends Fragment implements OnClickListener{
		/* MERKE: objStore Objekt, darf nur auf Activity-Ebene sein. Ansonsten geht es beim Start einer anderen Activity verloren */		
		private void setMessageStore(MyMessageStoreFGL<T> objStore){
			MainActivity<T> objActivityParent = (MainActivity<T>)this.getActivity();
			objActivityParent.setMessageStore(objStore);
		}
		private MyMessageStoreFGL<T> getMessageStore(){
			MainActivity<T> objActivityParent = (MainActivity<T>)this.getActivity();
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

		
				
		//##########################################################
		public PlaceholderFragmentMain() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			//z.B. registriere künstlich die onClick-Events
			initButtons();
												
		   return rootView;
		}
		
			
		//Damit die Events der Buttons HIER im Fragment und nicht in der Activity stehen, den Event hier abfangen und die ID auswerten.
		//Daher muss das Fragment View.onClickListener implementieren.
		//Jeder Button muss aber in onCreateView(...) gesucht werden und explizit mit button.setOnClickListener(this); den Listener zugewiesen bekommen.
		@Override
		public void onClick(View v) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onClick(v) wurde aktiviert");
		    switch (v.getId()) {
		    case R.id.button_send:
		    	sendMessage(v);
	            break;
		    case R.id.button_send_for_result:
	        	sendMessageForResult(v);
	            break;
	        case R.id.button_search_web:
	        	sendMessageToSearchWeb(v);
	            break;
	        case R.id.button_add_search_list:
	        	addElementToSearchList(v);
	            break;
	        default: 
	        	Log.d("FGLSTATE", this.getClass().getSimpleName()+".onClick(v) noch nicht implementiert für Id v.getId() = '" + v.getId() + "'");
	        	break;
		    }
		}
		
			
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);	
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle) wurde aktiviert");
			
			//Damit die Events an den Buttons erhalten bleiben, wie im onCreate();			
			initButtons();
			
			//Arbeite nun ggfs. mit den erhaltenen Daten weiter
			if(savedInstanceState==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle)SAVEDINSTANCESTATE IST NULL");
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle)SAVEDINSTANCESTATE IST NICHT NULL");
				
				//Greife auf den objectStore der activity zu.
				MyMessageStoreFGL<T> objStore = ((MainActivity<T>)getActivity()).getMessageStore();
				if(objStore==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): objStore ist null");
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): objStore ist NICHT null");
					
					//Blende nun ggfs. die verborgenen Buttons wieder ein.
					initValueDriven(objStore);	
				}
			}
		}
		
		public void onSaveInstanceState(Bundle outState){
			super.onSaveInstanceState(outState);			
		}
		
		public void onResume(){
			super.onResume();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): START");	
			
			//Damit die künstlich geschaffenen Events an den Buttons bleiben
			initButtons();
								
			String sMessageCurrent = this.getMessageCurrent();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per Variable sMessageCurrent = " + sMessageCurrent);
			
			if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
				//Wiederherstellen als Variable aus dem StoreObjekt.
				EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
				editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_VARIABLE);			
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

	Button button_send_for_result = (Button) getActivity().findViewById(R.id.button_send_for_result);
    Button button_send = (Button) getActivity().findViewById(R.id.button_send);
    Button button_search = (Button) getActivity().findViewById(R.id.button_search_web);
    Button button_add = (Button) getActivity().findViewById(R.id.button_add_search_list);
    
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
			if(button_send_for_result!=null) button_send_for_result.setOnClickListener(this);
			if(button_send!=null) button_send.setOnClickListener(this);
			if(button_search!=null) button_search.setOnClickListener(this);
			if(button_add!=null)button_add.setOnClickListener(this);
		}

private void initValueDriven(MyMessageStoreFGL objStore){
	Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): START.");
	if(objStore==null){
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): Kein objStore.");
		
	}else{
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDrive(): ObjStore vorhanden.");
		
	}//end if objStore == null
}
		
		
		/* (non-Javadoc)
		 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
		 * 27.07.2016 09:09:40 Fritz Lindhauer
		 */
		//Merke wird nur aufgerufen, wenn in der Activity ... DisplayMessageActivityForResult() der Zurück-Button am Gerät gedrückt wird.
		@SuppressWarnings("unchecked")
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityResult(): START mit requestCode='" + requestCode + "' | resultCode='"+resultCode+"'");	    
		    if (requestCode == 1) {
		    	if(intent==null){
		    		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityResult(..) - Intent Object IS NULL.");
		    	}else{
			    	// Get the Message from the StoreObject, stored in the intent.
					MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);
					if(objStore==null){
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityResult(..) - StoreObject IS NULL.");
					}else{
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityResult(..) - StoreObject FOUND.");
						this.setMessageStore(objStore);
					}		
		    	}//intent==null
		    }
		    super.onActivityResult(requestCode, resultCode, intent); //setzt das etwa den intent auf NULL?
		} 
		
		
		/**Starte eine Activity mit einer WebView. Dabei werfe den eingegebenen Text als Suchstring in die URL.
		 * @param view
		 * 13.10.2016 10:17:34 Fritz Lindhauer
		 */
		public void sendMessageToSearchWeb(View view){
			//Start an intent
			Intent intent = new Intent(getActivity(), DisplayWebviewActivityForSearch.class);
			EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
			String message = editText.getText().toString();
			
			//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse		
			message = MyMessageHandler.createNormedMessage(message);								
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".sendMessageToSearchWeb(): message nach der Normierung = " + message);
			
			//Speichere die message in eine lokale Variable. Grund: So kann man sie dann wegsichern wenn sich der State des Geräts ändert.
			this.setMessageCurrent(message);
					
				//-----------------------------
				//Mache eine Hilfsklasse, für das Wegsichern und auch wieder Zurückholen von den Intent-Informationen zwischen den Activities.
				MyMessageStoreFGL<T> objStore = this.getMessageStore();
					
				//Fülle den Store mit dem Eintrag
				objStore.put(MyMessageHandler.MESSAGE_RESUME, message); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
				objStore.put(MyMessageHandler.MESSAGE_CHOOSEN, message);
				
					//Übergib den Store als ganzes an den Intent
				intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);//objStore muss serializable sein
				//------------------------------
			
			//intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);//Das wäre nach meiner Konvention einen Message auf oberster Ebene, ausserhalb des ObjektMessageStore.
			startActivity(intent);			
		}
		
		
		/** Called when the user clicks the "Add" button */
		@SuppressWarnings("unchecked")
		public void addElementToSearchList(View view){
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElementToSearchList()");
			   
			EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
			if(editText!=null){
				String message = editText.getText().toString();		
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElementToSearchList(): message = " + message);
				
				//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse
				message = MyMessageHandler.createNormedMessage(message);							
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElementToSearchList() message nach der Normierung = " + message);
				
				if(!StringZZZ.isEmpty(message)){
					//Das ist dann die Kommunikation mit einem anderen Fragment aus einem Fragment heraus !!!
					PlaceholderFragmentList<T> frgList = (PlaceholderFragmentList<T>)getActivity().getSupportFragmentManager().findFragmentByTag("FRAGMENT_MAIN_LIST");
					
					//Füge die Message dem objStore hinzu.
					//Füge die Elemente der Liste (bzw. dem Adapter) hinzu.
					//Setze markierte Elemente wieder markiert, etc.
		            frgList.addElement(message);		//hier passiert also richtig viel...									
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElementToSearchList(): message hinzugefügt");		
										
					//Lösche nun den übergebenen Text aus dem Eingabefeld UND aus dem StoreObjekt
					this.clearMessageCurrent();				
				}
			}//end if editText!=null
		}
		
		private void clearMessageCurrent(){
			EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
			if(editText!=null){
				editText.setText("");					
			}
			
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				objStore.put(MyMessageHandler.MESSAGE_RESUME, "");
			}
			
		}
		
		/** Called when the user clicks the Send button */
		public void sendMessage(View view) {
		    //Start an intent
			Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
			EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
			String message = editText.getText().toString();
			
			//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse
			message = MyMessageHandler.createNormedMessage(message);		
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".sendMessage(): message nach der Normierung = " + message);
			
			//Speichere die message in eine lokale Variable. Grund: So kann man sie dann wegsichern wenn sich der State des Ger�ts �ndert.
			this.setMessageCurrent(message);
					
			//-----------------------------
			//Mache eine Hilfsklasse, für das Wegsichern und auch wieder Zurückholen von den Intent-Informationen zwischen den Activities.
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
				
				//Fülle den Store mit dem Eintrag
			objStore.put(MyMessageHandler.MESSAGE_RESUME, message); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
			
				//Übergib den Store als ganzes an den Intent
			intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);//objStore muss serializable sein
			//------------------------------
			
			startActivity(intent);
		}
		
		
		/** Called when the user clicks the SendForResult button */
		public void sendMessageForResult(View view) {
		    //Start an intent
			Intent intent = new Intent(getActivity(), DisplayMessageActivityForResult.class);
			EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
			String message = editText.getText().toString();
			
			//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse		
			message = MyMessageHandler.createNormedMessage(message);				
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".sendessageForResult(): message nach der Normierung = " + message);
			
			//Speichere die message in eine lokale Variable. Grund: So kann man sie dann wegsichern wenn sich der State des Geräts ändert.
			this.setMessageCurrent(message);
			
			//-----------------------------
			//Mache eine Hilfsklasse, für das Wegsichern und auch wieder Zurückholen von den Intent-Informationen zwischen den Activities.
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
				
			//Fülle den Store mit dem Eintrag
			objStore.put(MyMessageHandler.MESSAGE_RESUME, message); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
			
				//Übergib den Store als ganzes an den Intent
			intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);//objStore muss serializable sein
			//------------------------------

			startActivityForResult(intent,1);
		}
		
	}//END PlaceholderFragmentmain
		
	//#########################################################################
	/**
	 * A placeholder fragment containing a simple view.
	 * ACHTUNG: Der Code für die Buttons soll hier herein. 
	 *          Daher muss der Button Event-Listener extra hier gesetzt werden.
	 *          Damit dann die onClick Methode vorhanden ist, muss View.OnClickListern implementiert werden.
	 */
	public static class PlaceholderFragmentList<T> extends Fragment implements OnClickListener{		
//		private MyMessageStoreFGL<T> objStore=null;		//Iregendwie geht der Store in den Fragments immer verloren, darum nur in der Activity halten.
//		private void setMessageStore(MyMessageStoreFGL<T> objStore){
//			this.objStore = objStore;
//		}
//		private MyMessageStoreFGL<T> getMessageStore(){
//			if(this.objStore==null){
//				this.objStore = new MyMessageStoreFGL<T>();
//			}
//			return this.objStore;
//		}
		private void setMessageStore(MyMessageStoreFGL<T> objStore){
			MainActivity objActivityParent = (MainActivity)this.getActivity();
			objActivityParent.setMessageStore(objStore);
		}
		private MyMessageStoreFGL<T> getMessageStore(){
			MainActivity objActivityParent = (MainActivity)this.getActivity();
			return objActivityParent.getMessageStore();
		}
		
		//Vgl. Buch Andorid 4.4. Ein Listener, der die Auswahl in der ListView bemerkt.
		//Die Methode on NothingSelected muss dann auch automatisch implementiert werden.
		//ABER: onItemSelected wird nie aufgerufen
		private AdapterView.OnItemSelectedListener objListViewItemSelectedListener = new AdapterView.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onItemSelected() Item ausgewählt an Position: '"+position+"'");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		};//end new OnItemSelectedListener
		
		//Versuche analog zu AdapterView.OnItemSelected onItemClick zu ermöglichen
		//Die Methode onItemClick muss dann automatisch implementiert werden.
				private AdapterView.OnItemClickListener objListViewItemClickedListener = new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onItemClick() Item geclickt an Position: '"+position+"'");
						
						//TODO GOON: 20161213 Hier eine ArrayList der geclickten Werte verwalten.
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onItemClick() parent hat die Klasse: '"+parent.getClass().getName()+"'");
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onItemClick() parent hat als parent die Klasse: '"+parent.getParent().getClass().getName()+"'");
						
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onItemClick() view.getContext() hat die Klasse: '"+view.getContext().getClass().getName()+"'");
						PlaceholderFragmentList<T> fragment = (PlaceholderFragmentList<T>) ((FragmentActivity) view.getContext()).getSupportFragmentManager().findFragmentByTag("FRAGMENT_MAIN_LIST");
						if(fragment==null){
							Log.d("FGLSTATE", this.getClass().getSimpleName()+".onItemClick() : Fragment nicht gefunden.");
						}else{
							Log.d("FGLSTATE", this.getClass().getSimpleName()+".onItemClick() : Zugriff auf das Fragment.");
							fragment.handleSearchElementsClicked(position);
						}
						

					}
				};//end new OnItemClickListener
		
		private ArrayList<String>findSearchElementsRelevant(){
			ArrayList<String> listaReturn = new ArrayList<String>();
			main:{
			
				//ABER: Die ListView weiss irgendwie nicht welche Items geclickt sind.
				//Lösung: Füge einen onClick-Listener hinzu und verwalte in einer ArrayList die geclickten Werte selbst.
				ArrayList<String> listaElement = this.getSearchElementsFromStrore();					
				ArrayList<Integer>lista = this.getSearchElementsIndexClicked();
				
				//Entferne die Indices aus der Gesamtliste
				if(lista.size()==0){
					//A) Wenn nichts gewählt wurde, alle
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".findSearchElementsRelevant(): Übernimm alle aus der Liste.");
					listaReturn = (ArrayList<String>) listaElement.clone();					
				}else{
					//B) Wenn etwas gewählt wurde nur die gewählten
					//Verwende dies, da es bei einer einfachen For-Schleife Probleme gibt mit dem "Nächstenermitteln", wenn man den Vorgänger löscht.(index out of bounds)				
					for(Integer intObject : lista){
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".findSearchElementsRelevant(): Übernimm aus Position '" + intObject.toString() + "'");
						listaReturn.add(listaElement.get(intObject.intValue()));						
					}
				}				
			}//end main:
			return listaReturn;
		}
				
		@SuppressWarnings({ "unchecked"})
		private ArrayList<String>getSearchElementsFromStrore(){
			//Für die Liste der Suchwerte, sie wird gefüllt. Wenn sie leer ist, wird ein spezieller "Leereintrag" angezeigt.
			//Die Daten nur noch im objectStore der MainActivity halten.
			
			ArrayList<String> listaReturn = null;
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				listaReturn = (ArrayList<String>) objStore.getArrayList(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT);
			}else{
				listaReturn = new ArrayList<String>();
			}
			return listaReturn;
		}
		private void setSearchElementsToStore(ArrayList<String> listaSearchString){
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				objStore.put(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT, (ArrayList<T>)listaSearchString);
			}
		}
		
		
		//#### Führe eine Liste der geclickten Elemente
		@SuppressWarnings({ "unchecked"})
		private ArrayList<Integer>getSearchElementsIndexClicked(){
			//Für die Liste der Suchwerte, sie wird gefüllt. Wenn sie leer ist, wird ein spezieller "Leereintrag" angezeigt.
			//Die Daten nur noch im objectStore der MainActivity halten.
			
			ArrayList<Integer> listaReturn = null;
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				listaReturn = (ArrayList<Integer>) objStore.getArrayList(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CLICKED);
			}else{
				listaReturn = new ArrayList<Integer>();
			}
			return listaReturn;
		}
		private void setSearchElementsIndexClicked(ArrayList<Integer> listaSearchInteger){
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				objStore.put(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CLICKED, (ArrayList<T>)listaSearchInteger);
			}
		}
		private void handleSearchElementsClicked(int iPosition){
			Integer intPosition=new Integer(iPosition);
			
			//Hole die ArrayListe
			ArrayList<Integer> lista = this.getSearchElementsIndexClicked();
			if(lista.contains(intPosition)){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".handleSearchElementsClicked() : Index schon geclickt. Entferne ihn: '" + intPosition.toString()+"'");
				lista.remove(intPosition);
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".handleSearchElementsClicked() : Index noch nicht geclickt. Füge ihn hinzu: '" + intPosition.toString()+"'");				
				lista.add(intPosition);
			}
			this.setSearchElementsIndexClicked(lista);		
			
			//Ggfs. ändert sich auch wieder die Button Bechriftung, etc. , darum aufrufen: 
			initValueDriven();
		}
		
		
		
		
		private ArrayAdapter<String> arrayAdapter;
		private void setArrayAdapter(ArrayAdapter<String> adapter){
			this.arrayAdapter=adapter;
		}
		private ArrayAdapter<String> getArrayAdapter(){
			return this.arrayAdapter;
		}
				
		public PlaceholderFragmentList() {
		}
		
		public void addElement(String sToAddIn){
			ArrayList<String>listatemp = this.getSearchElementsFromStrore();
			main:{
			if(StringZZZ.isEmpty(sToAddIn)) break main;			
			String sToAdd = sToAddIn.trim(); //nur mit getrimmten Strings arbeiten.
			if(StringZZZ.isEmpty(sToAdd)) break main;
			
			//Eingangscheck, gibt es den Eintrag schon in der Liste.
			if(listatemp.contains(sToAdd)){
				Context context = this.getContext();
				//String stext = R.string.message_element_in_list; //liefert nur einen IntegerWert zurück
				String stext =getResources().getString(R.string.message_element_in_list);
				int duration = Toast.LENGTH_SHORT;

				CharSequence text = stext;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
			}else{
			
			
				//!!! FGL06: o.k. wenn man die ganze Activity Scrollbar gemacht hat, dann kann man die Listview nicht scrollen.
				//             Dies später versuchen zu lösen.
				//Jetzt erst ein Toast UND trotzdem (testweise) einen Begriff hinzufügen. Damit kann man mal sehen, ob es ggfs. doch irgendwann 
				//scrollbar ist.
				if(listatemp.size()>=4){
					//mache den Warnhinweis Toast
					//Context context = getApplicationContext();
					Context context = this.getContext();
					CharSequence text = MyMessageHandler.MESSAGE_IMPLEMENT_SCROLLING;
					int duration = Toast.LENGTH_SHORT;
	
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
					//hänge einen Hinweis an, das wird nur beim letzten Eintrag in die Liste übernommen.
					sToAdd = sToAdd + MyMessageHandler.MESSAGE_ADDITION_IMPLEMENT_SCROLLING;
				}
				
				//Übernimm den Eintrag mit dem Warnhinweis, nur, um ggfs. das Scrollen mal zu testen.
				if(listatemp.size()<=4){
					listatemp.add(sToAdd);
					this.setSearchElementsToStore(listatemp);//!! Das Zurückschreiben ist notwendig ist. Es wird ansonsten nur der aktuellste Wert in die Liste gesetzt.
					
					//1. Versuch: Cast Fehler. man man nicht Object[] in String[] casten  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, (String[])listaSearchString.toArray());//Haken werden hinter den Elementen angezeigt.
					//2. Versuch: NullPointer Exception: Attempt to get length of null Array.
					//The toArray() method without passing any argument returns Object[]. So you have to pass an array as an argument, which will be filled with the data from the list, and returned. You can pass an empty array as well, but you can also pass an array with the desired size.
					//String[] saTemp = listaSearchString.toArray(new String[listaSearchString.size()]);														
					String[] saTemp = listatemp.toArray(new String[listatemp.size()]);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElement() saTemp erzeugt.");
								
					//Füge hier den Adapter hinzu UND sorge dafür, dass die markierten Einträge auch markiert bleiben.
					initViewItemWebSearchValueDriven(); //Merke: Das initialiesieren der Events an die ListViiew spare ich mir hier. Es muss schon vorher passiert sein in onCreateView().
					
					 //Steuere nun wieder die Sichtbarkeit, etc. basierend auf den Werten.
			        initValueDriven();	//Merke: Das initialiesieren der Events an die ListViiew spare ich mir hier. Es muss schon vorher passiert sein in onCreateView().
				}//end if listaTemp.size()<=5
			}//end if listaTemp.contains(sToAdd) 
			}//end main:
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView(): START.");
			View rootView = inflater.inflate(R.layout.fragment_main_list, container, false);
			
			//Binde künstlich hergeholte Events an die Buttons
			initButtons();
			
			//Hier, die Events an die ListView binden
			initViewItemWebSearch();
			
		
			
			//#####################################################
			if(savedInstanceState==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() mit Bundle 'savedInstanceState' ist NULL.");
				
				//Teste auf gefüllte Liste
				//initialisiereListTestElemente();
			}else{

				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() mit Bundle 'savedInstanceState' gefunden.");
				
				//Stelle die Liste mit Suchelementen wieder her. 
				//TODO FGL 20161203: Hohle die Werte aus dem ObjectMessageStore.
				
				//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast		
				ArrayList<String> listaTemp = (ArrayList<String>) savedInstanceState.get(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT);
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() mit Bundle '" + listaTemp.size() + "' ArrayList SuchListen-Elementen.");
				this.setSearchElementsToStore(listaTemp);						
			}
								

			//TODO: Prüfe, ob hier die Buttons hinsichtlich der Beschriftung und des Aktivierens/Deaktivierens/Anzeigens verarbeit werden müssen.
			
			//TODO: Prüfe, ob hier die Liste auch gefüllt werden muss.
						
			return rootView;
		}
		
		//Damit die Events der Buttons HIER im Fragment und nicht in der Activity stehen, den Event hier abfangen und die ID auswerten.
		//!!! Daher muss das Fragment View.onClickListener implementieren !!!
		//Jeder Button muss aber in onCreateView(...) gesucht werden und explizit mit button.setOnClickListener(this); den Listener zugewiesen bekommen.
		@Override
		public void onClick(View v) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onClick(v) wurde aktiviert");
		    switch (v.getId()) {
		    case R.id.button_remove_from_list:
		    	removeFromList(v);
	            break;
		    case R.id.button_search_web_from_list:
		    	sendMessageToSearchWebFromList(v);
	            break;
	        default: 
	        	Log.d("FGLSTATE", this.getClass().getSimpleName()+".onClick(v) noch nicht implementiert für Id v.getId() = '" + v.getId() + "'");
	        	break;
		    }
		}
		
		@Override
		public void onStart() {
		    super.onStart();
		    Log.d("FGLSTATE", this.getClass().getSimpleName()+".onStart()");
		   
		    //Entferne ggfs. schon vorhandene Views, sonst bekommt man z.B. den "keine einträge" Eintrag mehrfach.
		    //ListView vwList = (ListView) ((AppCompatActivity) getContext()).findViewById(R.id.list_search_web);
		    //vwList.removeAllViews(); //darf hier nicht ausgeführt werden.
		    //darf wohl ausgeführt werden, aber bewirkt nichts.   vwList.removeAllViewsInLayout();
		    
		    /* weil der Eintrag "keine einträge" verdoppelt wird beim Benutzen des Return Buttons diesen Code nach "onCreate" verschieben.
			//Remember to place the emptyView after binding the adapter to listview.Mine was not working for first time and after I moved the setEmptyView after the setAdapter it is now working.
			TextView txtNoItems = noItems(vwList, getResources().getString(R.string.element_search_web_from_list_empty));
			Log.d("FGLSTATE", "PlaceholderFragementList.onStart() Leerlisteneintrag txtNoItems erzeugt.");
			
			vwList.setEmptyView(txtNoItems);
			Log.d("FGLSTATE", "PlaceholderFragementList.onStart() Leerlisteneintrag txtNoItems gesetzt.");
			*/
		}
		
		public void onSaveInstanceState(Bundle outState){			
			//NOTWENDIG ZUM ERHALTEN DER DATEN IM BUNDLE, WENN DAS GERÄT GEDREHT WIRD
			//Merke: Bei Fragments gibt es keine onRestoreInstanceState()-Methode. Hole das Bundle in onActivityCreated ab.
						
			//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
					//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
					//            und Eclipse neu starten muss.
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onSaveInstanceState() wurde aktiviert");
						
			//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast
			//TODO FGL 20161203: Ändere das auf den MessageObjectStore ab
			ArrayList<String> listaTemp = this.getSearchElementsFromStrore();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onSaveInstanceState(): Es sind '" + listaTemp.size() + "' Elemente in der Liste.");
			outState.putSerializable(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT, listaTemp); //liste darf nicht das Interface sein, sondern muss explizit die Klasse ArrayList sein.
			
			//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.					
			super.onSaveInstanceState(outState);								
		}
		
		@SuppressWarnings("unchecked")
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);	
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle) wurde aktiviert");
			
			//Damit die Events an den Buttons erhalten bleiben, wie im onCreate();			
			initButtons();
			
			//Hier, die Events an die ListView binden
			initViewItemWebSearch();
			
			if(savedInstanceState==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() savedInstanceState==null.");
			}else{				
				MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) savedInstanceState.getSerializable(MyMessageHandler.EXTRA_STORE);
				if(objStore==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): objStore ist null");
				}else{
					String sMessageCurrent = objStore.getString(MyMessageHandler.MESSAGE_RESUME);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): sMessageCurrent = " + sMessageCurrent);				
				
					//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast		
					ArrayList<String> listaTemp = (ArrayList<String>) objStore.get(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() mit Bundle '" + listaTemp.size() + "' ArrayList SuchListen-Elementen.");
					this.setSearchElementsToStore(listaTemp);			
				
				if(listaTemp.isEmpty()){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() ArrayList für Elemente ist leer.");
					//Merke: Im onStart wird ein Element erstellt, dass angezeigt werden soll, wenn die Liste leer ist.
				}else{
					Log.d("FGLSTATE",this.getClass().getSimpleName()+".onActivityCreated() ArrayList mit Elementen ist gefüllt. Anzahl Elemente: " + listaTemp.size());
					
					//#############################################################
					//Hier, die Events an die ListView binden
					initViewItemWebSearch();
					
					//Hier, versuche die ListView zu füllen
					initViewItemWebSearchValueDriven();																									
				}		//end if(this.listaSearchString.isEmpty()){		
				}//end if (objStore == null)			
			}//end if(savedInstanceState!=null){							
		}
		
		public void onResume(){
			super.onResume();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): START");
			
			//Versuche die gespeicherten Liste wiederherszustellen.
			//Merke: Beim einfachen Wechseln zurück (Gerätebutton) wird dann nicht onCreate() aufgerufen, sondern onResume(), 
			//       darum gehört der Code hierher, ABER: savedInstanceState ist hier nicht vorhanden.
			//       Wenn man auf den objStore zugreift, dann ist das alles einfacher.
			
			MyMessageStoreFGL<T> objStore = this.getMessageStore(); 												
			if(objStore!=null){
				initValueDriven(objStore);	
				
				initViewItemWebSearchValueDriven();
			}	
		}
		
		 /** Called when the user clicks the RemoveFromList button */
		public void removeFromList(View view) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".removerFromList(): START");
						
			//-----------------------------
			//Mache eine Hilfsklasse, für das Wegsichern und auch wieder Zurückholen von den Intent-Informationen zwischen den Activities.
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".removerFromList(): KEIN STORE OBJEKT.");
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".removerFromList(): STORE OBJEKT gefunden.");		
				
				//Hole das Listen-Designelement
				ListView objList = (ListView) getActivity().findViewById(R.id.list_search_web);
				if(objList==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".removeFromList(): KEIN ListView Desing-OBJEKT gefunden.");
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".removeFromList(): ListView Desing-OBJEKT gefunden.");	
					
					//Das liefert immer NULL zurück String sSelected = (String) objList.getSelectedItem();
					//Versuch über den ListAdapter zu gehen. Diesen ListViewAdapter müssen wir aber erst einmal in einer neuen Klasse implementieren.
					//OnItemSelectedListener des Objekts objListViewItemSelectedListener.
					//Dieses Objekt muss der ListView mit lv.setOnItemSelectedListener(obListViewItemdSelectedListener) hinzugefügt werden.
					
					//Dann kann man aus der so ermittelten Postion heraus den Wert ermitteln
					///....
					//Log.d("FGLSTATE", this.getClass().getSimpleName()+".removerFromList(): Selektiertes Objekt='"+sSelected+"'");
					
					//ABER: Die ListView weiss irgendwie nicht welche Items geclickt sind.
					//Lösung: Füge einen onClick-Listener hinzu und verwalte in einer ArrayList die geclickten Werte selbst.
					ArrayList<String> listaElement = this.getSearchElementsFromStrore();					
					ArrayList<Integer>lista = this.getSearchElementsIndexClicked();
					
					//Entferne die Indices aus der Gesamtliste
					if(lista.size()==0){
						//A) Wenn nichts gewählt wurde, alle
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".removeFromList(): Entferne alle aus der Liste.");
						ArrayList<String>listaNix = new ArrayList<String>(); //Mache ein dummy, um alles zu entfernen. 
						listaElement.retainAll(listaNix); //Verwende dies, da es bei einer For-Schleife Probleme gibt mit dem "Nächstenermitteln", wenn man den Vorgänger löscht.
												
					}else{
						//B) Wenn etwas gewählt wurde nur die gewählten
						//Verwende dies, da es bei einer einfachen For-Schleife Probleme gibt mit dem "Nächstenermitteln", wenn man den Vorgänger löscht.(index out of bounds)
						ArrayList<String>listaRemove = new ArrayList<String>();
						for(Integer intObject : lista){
							Log.d("FGLSTATE", this.getClass().getSimpleName()+".removeFromList(): Entferne an Position '" + intObject.toString() + "'");
							listaRemove.add(listaElement.get(intObject.intValue()));						
						}
						listaElement.removeAll(listaRemove);
												
						//Entferne diese Indexeinträge auch aus der Click-Liste					
						lista.clear();
					}
					this.setSearchElementsToStore(listaElement);
					this.setSearchElementsIndexClicked(lista);
					
					
					
					//PROBLEM: Wie das Entfernen des Elements wieder in die Ansicht bringen.
					//Zeige die Werte an, ... das Initialisieren der Events an die View spare ich mir hier
					initViewItemWebSearchValueDriven();
			        			       
			      //Steuere die Buttons, etc.
			       initValueDriven(objStore);
				}
			}		
		}
		
		 /** Called when the user clicks the SearchWebFromList button */
		public void sendMessageToSearchWebFromList(View view) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".sendMessageToSearchWebFromList(): START");
				
			//-----------------------------
			//Mache eine Hilfsklasse, für das Wegsichern und auch wieder Zurückholen von den Intent-Informationen zwischen den Activities.
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".sendMessageToSearchWebFromList(): KEIN STORE OBJEKT.");
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".sendMessageToSearchWebFromList(): STORE OBJEKT.");
				
				//Start an intent
				Intent intent = new Intent(getActivity(), DisplayWebviewActivityForSearch.class);
				
				//Hohle alle oder nur alle ausgewählten Suchstrings aus der Liste und fülle den Store mit dem Eintrag (20161222: der wird momentan noch nicht verwendet)
				ArrayList<String>listaMessage=this.findSearchElementsRelevant();
				objStore.putArrayListString(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CHOOSEN, listaMessage); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
								
				//Durchlaufe alle messages
				String sMessageTotal = new String("");
				for(String sMessage : listaMessage){
					//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse		
					sMessage = MyMessageHandler.createNormedMessage(sMessage);								
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".sendMessageToSearchWeb(): message nach der Normierung = " + sMessage);
					
					if(sMessageTotal.equals("")){
						sMessageTotal = sMessage;
					}else{
						sMessageTotal = sMessageTotal + "+" + sMessage;
					}
					
				}
								
				//Speichere die message in eine lokale Variable. Grund: So kann man sie dann wegsichern wenn sich der State des Geräts ändert.
				//Nein, das ist nicht notwendig, sondern das wäre die im EditFeld eingegebene Messagethis.setMessageCurrent(sMessageTotal);
						
				//Fülle den Store mit dem Eintrag
				objStore.put(MyMessageHandler.MESSAGE_CHOOSEN, sMessageTotal); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
					
				//Übergib den Store als ganzes an den Intent
				intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);//objStore muss serializable sein
				//------------------------------
				
				//intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);//Das wäre nach meiner Konvention einen Message auf oberster Ebene, ausserhalb des ObjektMessageStore.
				startActivity(intent);		
				
			}
		}
		
		private TextView noItems(ListView listView, String text) {
			//Merke1: 
			//ListView als Argument aufgenommen. Abgewandelt von einem Beispiel für ListFragments.
			//Siehe: http://stackoverflow.com/questions/14082303/how-to-use-setemptyview-with-custom-list-layout-in-listfragment/15990955#15990955
			
			//Merke 2:
			//Beispiel für ListActivity in Buch Android 4.4 Seite 130. Hier kann man das durch Konfiguration im Layout - XML erreichen:  geokontakte_auflisten.xml.... id/android:empty...
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		    TextView emptyView = new TextView(getActivity());
		    //Make sure you import android.widget.LinearLayout.LayoutParams;
		    emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		    //Instead of passing resource id here I passed resolved color 
		    //That is, getResources().getColor((R.color.gray_dark))
		   // emptyView.setTextColor(getResources().getColor(R.color.material_grey_100)); //.gray_dark)); //es wird nix angezeigt.
		    
		    //FGL: Ich übergeb hier einfach die ID.
		    emptyView.setTextColor(Color.GRAY);
		    emptyView.setText(text);
		    emptyView.setTextSize(18);
		    //emptyView.setVisibility(View.GONE); //FGL: Das darf nicht gesetzt sein..., sonst erscheint der Eintrag nicht.
		    emptyView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		    Log.d("FGLSTATE", this.getClass().getSimpleName()+".noItems() ... TextView erzeugt");
		    
		    //Beispiel aus dem Web: Geht wohl nur für ListFragments ((ViewGroup) getListView().getParent()).addView(emptyView, 0);
		    //Add the view to the list view. This might be what you are missing		    
		    //((ViewGroup) getView().getParent()).addView(emptyView, 0);
		  		    		    		    
		    //Funktioniert, auch mit meiner ListView!!!
		    //Fehler: ((ViewGroup)listView).addView(emptyView, 0);//not supported in AdapterView
		    ((ViewGroup)listView.getParent()).addView(emptyView, 0);
		    return emptyView;
		}
		
		//###########################################################################
		private void initButtons(){
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".initButtons(): START");
					
					//FGL 20161204: Wenn man die Events im Fragment selbst haben will,
					//              muss man den Event-Handler am Button hier einbauen.
					//              sonst gibt es Fehlermeldungen der Art:
					//12-04 06:02:41.664: E/AndroidRuntime(1778): java.lang.IllegalStateException: Could not find method sendMessage(View) in a parent or ancestor Context for android:onClick attribute defined on view class android.support.v7.widget.AppCompatButton with id 'button_send'
					//              Alternativ dazu die Methode in der Activity belassen, was meiner Meinung nach eine unschöne Lösung ist, wg. mangelnder Kapselung.
					
			Button button_removeFromList = (Button) getActivity().findViewById(R.id.button_remove_from_list);
		    Button button_searchFromList = (Button) getActivity().findViewById(R.id.button_search_web_from_list);
		    
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
			if(button_removeFromList!=null) button_removeFromList.setOnClickListener(this);		
			if(button_searchFromList!=null) button_searchFromList.setOnClickListener(this);

		}
		
		private void initValueDriven(){
			this.initValueDriven(this.getMessageStore());
		}
			
		private void initValueDriven(MyMessageStoreFGL objStore){
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): START.");
			if(objStore==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): Kein ObjStore.");
				
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): ObjStore vorhanden.");
				
				//Blende nun ggfs. die verborgenen Buttons wieder ein.
				ArrayList<String> listaTemp = this.getSearchElementsFromStrore(); //objStore.getArrayList(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT);
				ArrayList<Integer>listaClicked= this.getSearchElementsIndexClicked();
				
				//######################################
				//Gib erst einmal den aktuellen Listenstatus im Log aus. Diese Stati steuern die Sichtbarkeit der Buttons, Texte, etc.
				if(listaTemp==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): objStore hat keine Arrayliste für die Suchelement gespeichert.");
				}else{
					if(listaTemp.size()==0){
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): Arrayliste aus objStore ist leer.");
					}else{
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): Arrayliste aus objStore, Anzahl Elemente: '" + listaTemp.size() + "'");
					}
				}
				
				if(listaClicked==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): objStore hat keine Arrayliste für die ausgewählten Suchelement gespeichert.");
				}else{
					if(listaTemp.size()==0){
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): Arrayliste der ausgewählten Suchelemente aus objStore ist leer.");
					}else{
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): Arrayliste der ausgewählten Suchelement aus objStore, Anzahl Elemente: '" + listaClicked.size() + "'");
					}
				}
				
					
				//##############################		
				//Steuere den Button "Suche per Liste":
				Button buttonSearchFromList = (Button)((AppCompatActivity)getContext()).findViewById(R.id.button_search_web_from_list);
				if(buttonSearchFromList==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): Button '" + R.id.button_search_web_from_list + "' NICHT gefunden.");
				}else{
					if(listaTemp==null){
						buttonSearchFromList.setEnabled(false);
						buttonSearchFromList.setClickable(false);
						buttonSearchFromList.setVisibility(View.INVISIBLE);//besser als gone ist invisible--- das macht transparent
					}else{
						if(listaTemp.size()==0){
							buttonSearchFromList.setEnabled(false);
							buttonSearchFromList.setClickable(false);
							buttonSearchFromList.setVisibility(View.VISIBLE);
						}else if(listaTemp.size()>=1 && (listaClicked.size()!=listaTemp.size() && listaClicked.size()>=1)){
							//ausgewählte suchen
				        	buttonSearchFromList.setEnabled(true);
				        	buttonSearchFromList.setClickable(true);
				        	buttonSearchFromList.setVisibility(View.VISIBLE);
				        	buttonSearchFromList.setText(R.string.button_search_web_selected);															
						}else if(listaTemp.size()>=1 && (listaClicked.size()==0 || listaClicked.size()==listaTemp.size())){
							//alle aus der Liste suchen, wenn entwder nix ausgewählt wurde oder alles ausgewählt wurde.
				        	buttonSearchFromList.setEnabled(true);
				        	buttonSearchFromList.setClickable(true);
				        	buttonSearchFromList.setVisibility(View.VISIBLE);
				        	buttonSearchFromList.setText(R.string.button_search_web_from_list);	
						}						
					}
				}//buttonSearchFromList!=null
						
				//Steuere den Button "Entferne aus Liste":
		        Button buttonRemoveFromList = (Button)((AppCompatActivity)getContext()).findViewById(R.id.button_remove_from_list);
				if(buttonRemoveFromList==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initValueDriven(): Button '" + R.id.button_remove_from_list + "' NICHT gefunden.");
				}else{
					if(listaTemp==null){
						buttonRemoveFromList.setEnabled(false);
						buttonRemoveFromList.setClickable(false);
						buttonRemoveFromList.setVisibility(View.INVISIBLE);//besser als gone ist invisible--- das macht transparent
					}else{
						if(listaTemp.size()==0){
							buttonRemoveFromList.setEnabled(false);
							buttonRemoveFromList.setClickable(false);
							buttonRemoveFromList.setVisibility(View.VISIBLE);					
						}else if(listaTemp.size()>=1 && (listaClicked.size()!=listaTemp.size() && listaClicked.size()>=1)){
							//ausgewählte entfernen
							buttonRemoveFromList.setEnabled(true);
							buttonRemoveFromList.setClickable(true);
				        	buttonRemoveFromList.setVisibility(View.VISIBLE);
				        	buttonRemoveFromList.setText(R.string.button_remove_selected_from_list);															
						}else if(listaTemp.size()>=1 && (listaClicked.size()==0 || listaClicked.size()==listaTemp.size())){
							//alle aus der Liste entfernen, wenn entwder nix ausgewählt wurde oder alles ausgewählt wurde.
							buttonRemoveFromList.setEnabled(true);
							buttonRemoveFromList.setClickable(true);
				        	buttonRemoveFromList.setVisibility(View.VISIBLE);
				        	buttonRemoveFromList.setText(R.string.button_remove_from_list);	
						}						
					}
				}//buttonRemoveFromList!=null
				
				
			}//end if objStore == null
		}
		
		private void initViewItemWebSearch(){
			this.initViewItemWebSearch(null);
		}
		private void initViewItemWebSearch(View rootView){

			ListView vwList=null;
			if(rootView==null){
				vwList = (ListView) ((AppCompatActivity) getContext()).findViewById(R.id.list_search_web);
			}else{
				vwList = (ListView) rootView.findViewById(R.id.list_search_web);
			}
			if(vwList==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearch() vwList ist NULL.");
				
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearch() vwList gefunden.");

				//Damit die ListView auf die Selection von Einträgen reagieren kann.
				vwList.setOnItemSelectedListener(this.objListViewItemSelectedListener);//ABER: Scheinbar wird in der View das Auswählen so nicht gemerkt....
				
				//Damit die ListView auf das Anclicken von Einträgen reagieren kann.
				vwList.setOnItemClickListener(this.objListViewItemClickedListener);
								
				//Versuche einen Eintrag für "keine Elemente" bereitzustellen. Mache dies nur im "onCreate" (und nicht im start()), da ansonsten beim Betätigen des "Return" Buttons mehrer dieser Einträge passieren.
				//Remember to place the emptyView after binding the adapter to listview.Mine was not working for first time and after I moved the setEmptyView after the setAdapter it is now working.
				TextView txtNoItems = noItems(vwList, getResources().getString(R.string.element_search_web_from_list_empty));
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearch() Leerlisteneintrag txtNoItems erzeugt.");
			
				vwList.setEmptyView(txtNoItems);
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearch() Leerlisteneintrag txtNoItems gesetzt.");
				
			}
		}
		
		private void initViewItemWebSearchValueDriven(){
			this.initViewItemWebSearchValueDriven(null);
		}
		private void initViewItemWebSearchValueDriven(View rootView){
			ListView vwList=null;
			if(rootView==null){
				vwList = (ListView) ((AppCompatActivity) getContext()).findViewById(R.id.list_search_web);
			}else{
				vwList = (ListView) rootView.findViewById(R.id.list_search_web);
			}
			if(vwList==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() vwList ist NULL.");
				
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() vwList gefunden.");
			
				//Zeige die Liste mit Suchelementen an.	
				ArrayList<String> listaTemp = (ArrayList<String>) this.getSearchElementsFromStrore();
				
				if(listaTemp.isEmpty()){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() ArrayList für Elemente ist leer.");
					//Merke: Im onStart wird ein Element erstellt, dass angezeigt werden soll, wenn die Liste leer ist.
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() ArrayList mit Elementen ist gefüllt. Anzahl Elemente: " + getSearchElementsFromStrore().size());
				}
										
					//1. Versuch: Cast Fehler. man man nicht Object[] in String[] casten  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, (String[])listaSearchString.toArray());//Haken werden hinter den Elementen angezeigt.
					//2. Versuch: NullPointer Exception: Attempt to get length of null Array.
					//The toArray() method without passing any argument returns Object[]. So you have to pass an array as an argument, which will be filled with the data from the list, and returned. You can pass an empty array as well, but you can also pass an array with the desired size.
					String[] saTemp = listaTemp.toArray(new String[listaTemp.size()]);
																
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() saTemp erzeugt.");
					ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, saTemp);//Haken werden hinter den Elementen angezeigt.											
					vwList.setAdapter(myArrayAdapter);	
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() Arrayadapter gesetzt.");

					
					//### 20161215: PROBLEM: Wie wieder einmal selektierte Einträge mit einem Haken versehen?
					//Versuch 1) Kann man ggfs. hier auch die "grünen Haken" wieder setzen?
					//myArrayAdapter.notifyDataSetChanged();	
									
					//Versuch 2) Versuche irgendwie den Haken in die Zeile zu setzen.
//					Object obj = vwList.getItemAtPosition(0);
//					if(obj!=null){
//					Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() kein Item bei 0.");
//					}else{
//						Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() Item bei 0 mit Klasse: '" + Item.class.getSimpleName());
//					}					
					//Liefert ein Item der Liste zurück, das kann aber im Default nix. IDEE: Verwende irgendwann einmal einen CustomAdapter.
//					Item item = (Item) vwList.getItemAtPosition(0);
					
					//Versuch 3) 
					//Wenn das klappt wäre das zu einfach... toll, es klappt.
					//Log.d("FGLSTATE", this.getClass().getSimpleName()+".initViewItemWebSearchValueDriven() ######### SETZE CHECKED ####");	
					//vwList.setItemChecked(0, true);
					
					//Setze die Items, die ausgewählt waren an der Postition.
					ArrayList<Integer>listaIndex=this.getSearchElementsIndexClicked();
					for(Integer intIndex : listaIndex){
						vwList.setItemChecked(intIndex.intValue(), true);
					}
					
					//Das ist notwendig, um die View neu anzuzeigen
					 vwList.refreshDrawableState();
					
				
			}//end if vwList==null
		}
				
		private void initialisiereListTestElemente(){
			String[] saTest = {"eins","zwei","drei","vier","fünf"};
			for(int icount=0; icount <= saTest.length-1; icount++){
				this.getSearchElementsFromStrore().add(saTest[icount]);
			}			
		}				
	}
}

