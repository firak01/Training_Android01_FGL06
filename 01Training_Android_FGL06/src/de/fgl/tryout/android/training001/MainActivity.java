package de.fgl.tryout.android.training001;


import java.util.ArrayList;

import basic.zBasic.util.datatype.string.StringZZZ;
import biz.tekeye.abouttest.AboutBox;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
	
	//#############################################
	private void setMessageCurrent(String message) {
		Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageCurrent() für '" + message + "'");
		MyMessageStoreFGL<T> objStore = this.getMessageStore();
		if(objStore!=null){
			objStore.put(message, MyMessageHandler.RESUME_MESSAGE);
		}else{
			Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageCurrent() findet kein Store Objekt.");
		}					
	}
	private String getMessageCurrent(){					
		Log.d("FGLSTATE", this.getClass().getSimpleName()+". getMessageCurrent() gestartet.");
		String sReturn = new String("");
		MyMessageStoreFGL<T> objStore = this.getMessageStore();
		if(objStore!=null){
			sReturn = objStore.getString(MyMessageHandler.RESUME_MESSAGE);
		}else{
			Log.d("FGLSTATE", this.getClass().getSimpleName()+". getMessageCurrent() findet kein Store Objekt.");
		}
		return sReturn;
	}
	

	
	
	
//############################################################################################
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() : START");
		// Set the user interface layout for this Activity
	    // The layout file is defined in the project res/layout/main_activity.xml file
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() wurde aktiviert. KEIN SAVEDINSTANCESTATE vorhanden");
			this.fragmentMain =  new PlaceholderFragmentMain();
			this.fragmentMain.setRetainInstance(true);
			getSupportFragmentManager().beginTransaction()
				.add(R.id.container, this.fragmentMain,"FRAGMENT_MAIN").commit();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() FRAGMENT_MAIN erzeugt.");
			
			//FGL06: füge eine Liste der Suchbegriffe hinzu, und die Möglichkeit daraus auswählend (ggfs. kombiniert) zu suchen
			this.fragmentList = new PlaceholderFragmentList();
			this.fragmentList.setRetainInstance(true);
			getSupportFragmentManager().beginTransaction()
				.add(R.id.container, this.fragmentList,"FRAGMENT_MAIN_LIST").commit();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate() FRAGMENT_MAIN_LIST erzeugt.");
			
			//++++++++++++++++++++++++++++++++++++++++++++++
			// Get the message from the intent
//			Intent intent = getIntent();	
//			if(intent==null){ //Verhindere einen Fehler, wenn die Activity ohne Intent gestartet wird. Z.B. von der Oberfläche
//				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - Intent IS NULL.");
//			}else{
//				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - Intent UNGLEICH NULL.");
//				
//				//FGL 20161125: Statt den String direkt zu übernehmen jetzt ein StoreObjekt verwenden, in dem auch Werte enthalten sind,
//				//                     die ggfs. nur zwischengespeichert wurden, um sie an eine andere Activity weiter/wieder zurückzugeben.
//														
//				// Get the Message from the StoreObject, stored in the intent.
//				//	MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);
//				Bundle objBundle = intent.getExtras();
//				if(objBundle==null){
//					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - BundleObject IS NULL.");
//				}else{
//					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - BundleObject UNGLEICH NULL.");
//				
//					MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) objBundle.getSerializable(MyMessageHandler.EXTRA_STORE);
//					if(objStore==null){
//						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - StoreObject IS NULL.");
//					}else{
//						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - StoreObject FOUND.");
//						this.setMessageStore(objStore);
//						String sMessage = this.getMessageStore().getString(MyMessageHandler.RESUME_MESSAGE);
//						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - String from StoreObject = '"+sMessage + "'");
//											
//					}
//				}//end if objBundle==null
//			}//end if intent!=null
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
			finish();			
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
	
	@SuppressWarnings("unchecked")
	public void addElementToSearchList(View view){
		Log.d("FGLSTATE", "MainActivity.addElementToSearchList()");
		   
		EditText editText = (EditText) findViewById(R.id.edit_message);
		if(editText!=null){
			String message = editText.getText().toString();		
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElementToSearchList(): message = " + message);
			
			//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse
			message = MyMessageHandler.createNormedMessage(message);							
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElementToSearchList() message nach der Normierung = " + message);
			
			if(!StringZZZ.isEmpty(message)){													
				PlaceholderFragmentList<T> frgList = (PlaceholderFragmentList<T>)getSupportFragmentManager().findFragmentByTag("FRAGMENT_MAIN_LIST");
	            frgList.addElement(message);		//hier passiert richtig viel...	
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElementToSearchList(): message hinzugefügt");		
				
				//Lösche nun den übergebenen Text aus dem Eingabefeld
				editText.setText("");
			}
		}//end if editText!=null
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
		//super.onResume();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
				
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
		//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
		//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume() wurde aktiviert");
		String sMessage=new String("");
				
//		//Versuche einen gespeicherten Text wiederherszustellen.
//		//Merke: Beim einfachen Wechseln zurück wird dann nicht onCreate() aufgerufen, sondern onResume(), 
//		//       darum gehört der Code hierher, ABER: savedInstanceState ist hier nicht vorhanden.
//		
//		//Damit das funktioniert muss onRestoreInstanceState() ausgeführt werden und es muss die lokale Property wieder gefüllt worden sein.
		String sMessageCurrent = this.getMessageCurrent();
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per Variable sMessageCurrent = " + sMessageCurrent);
//		
		if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
//			//1. Variante: Als Variable
			sMessage = sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_VARIABLE;			
		}else{
//			//2. Variante: Hole den Wert aus dem MessageStore, der zwischen den Activities ausgetauscht wird.
//			//             Das ist der Normalefall: Die Variable ist nämlich normalerweise weg.
			Intent intent = getIntent();
			if(intent==null){ //Verhindere einen Fehler, wenn die Activity ohne Intent gestartet wird. Z.B. von der Oberfläche
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - Intent IS NULL.");
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - Intent UNGLEICH NULL.");
			
				//FGL 20161125: Statt den String direkt zu übernehmen jetzt ein StoreObjekt verwenden, in dem auch Werte enthalten sind,
				//                		die ggfs. nur zwischengespeichert wurden, um sie an eine andere Activity weiter/wieder zurückzugeben.
									
				// Get the Message from the StoreObject, stored in the intent.
				MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);
				Bundle objBundle=null;
				//Bundle objBundle = intent.getExtras();
				//Bundle objBundle = intent.getBundleExtra(MyMessageHandler.EXTRA_STORE);			
				if(objBundle==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - BundleObject IS NULL.");
					objStore = (MyMessageStoreFGL<T>) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - BundleObject UNGLEICH NULL.");
					objStore = (MyMessageStoreFGL<T>) objBundle.getSerializable(MyMessageHandler.EXTRA_STORE);
				} //objBundle==null
				
				String sMessageTest = intent.getStringExtra(MyMessageHandler.RESUME_MESSAGE);
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - String from intent.getStringExtra = '"+sMessageTest + "'");
				sMessage=sMessageTest;
				
				
			
				if(objStore==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - StoreObject IS NULL.");
					
					
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - StoreObject FOUND.");
					this.setMessageStore(objStore);
					sMessage = this.getMessageStore().getString(MyMessageHandler.RESUME_MESSAGE);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(..) - String from StoreObject = '"+sMessage + "'");															
				}
		//	}//end if objBundle==null
		}//getIntent==null
			
			
			
//			MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) getIntent().getSerializableExtra(MyMessageHandler.EXTRA_STORE);
//			if(objStore!=null){
//				this.setMessageStore(objStore);
//											
//				//Nun Versuch sie in inStop() über einen Intent.getExtras zu sichern und hier wiederherzustellen
//				sMessageCurrent = objStore.getString(MyMessageHandler.RESUME_MESSAGE);
//				Log.d("FGLSTATE", "onResume(): Wert per intent (aus MessageStore) sMessageCurrent = " + sMessageCurrent);			
//					
//				if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
//					//DAS FUNKTIONIERT GGFS. AUCH NICHT!!!
//					EditText editText = (EditText) findViewById(R.id.edit_message);
//					editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_INTENT);
//				}else{
//					Log.d("FGLSTATE", "onResume(): Wert per intent (aus MessageStore) ist leer oder NULL.");			
//					
//					EditText editText = (EditText) findViewById(R.id.edit_message);
//					editText.setText("");
//				}
//			}
//		}
		}//sMessageCurrent istempty
		
		
		EditText editText = (EditText) findViewById(R.id.edit_message);
		editText.setText(sMessage);	
		super.onResume();
	}
	
	public void onStop(){
		//super.onStop();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onStop() wurde aktiviert");
				
		//Merke: objStore nur in den Fragments	
		//Versuche den Stringwert zu retten, der dann in onResume() ausgelesen werden soll.
		//Nutze dafür den objektMessageStore
//		EditText editText = (EditText) findViewById(R.id.edit_message);
//		String message = editText.getText().toString();			
//		Log.d("FGLSTATE", "onStop() - Sicher Message in intent weg: " + message);
//		
//		//Suchstring
//		MyMessageStoreFGL<T> objStore = this.getMessageStore();
//		objStore.put(MyMessageHandler.RESUME_MESSAGE, message);
//		
//		//Arraylist (für Suchelementliste)
//        ArrayList<String>listaTemp=new ArrayList<String>();
//        listaTemp.add("TEST01");
//		objStore.put(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT, listaTemp);
//		
//		//Das Message Store Objekt wieder in den Intent schreiben.
//		getIntent().putExtra(MyMessageHandler.EXTRA_STORE, objStore);
//		//intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	
        super.onStop();
	}
	
	public void onRestart(){
		super.onRestart();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onRestart() wurde aktiviert");
	}
	
	public void onStart(){
		super.onStart();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onStart() wurde aktiviert");
	}
	
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		//NOTWENDIG ZUM PERSISTIERN DER DATEN IM BUNDLE
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onSaveInstanceState() wurde aktiviert");
		
		//TODO FGL GOON 20161203 Das ebenfalls auf den MessageObjectStore abändern.
		//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast
		//String sMessage = this.getMessageCurrent();
		//outState.putSerializable(MyMessageHandler.KEY_MESSAGE_CURRENT, sMessage); //liste darf nicht das Interface sein, sondern muss explizit die Klasse ArrayList sein.
		
		//20161128: FGL06 - nun auch die Fragments speichern
		//Save the fragment's instance
	    getSupportFragmentManager().putFragment(outState, "fragmentMain", this.fragmentMain);
	    getSupportFragmentManager().putFragment(outState, "fragmentList", this.fragmentList);							
	}
	
	@Override
	public void onRestoreInstanceState(Bundle inState){
		super.onRestoreInstanceState(inState);
		//NOTWENDIG ZUM ZURUECKHOLEN DER PERSISTIERTEN DATEN AUS DEM BUNDEL. 
		//WIRD ABER NUR BEI onCreate() aufgerufen
		
		//Merke onResume() oder onStart() haben kein Bundle als Parameter
		//Bei onResume() ... WIE KANN MAN DA DIE DATEN ZURUECKHOLEN?????
		//super.onRestoreInstanceState(inState);
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onRestoreInstanceState() wurde aktiviert");
		
		
		//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast		
//		String sMessage = (String) inState.get(MyMessageHandler.KEY_MESSAGE_CURRENT);
//		Log.d("FGLSTATE", "onRestoreInstanceState() mit sMessage="+sMessage);
//		this.setMessageCurrent(sMessage);	
		
		//20161128: FGL06 - nun auch die Fragments speichern
		//load the fragment's instance
		this.fragmentMain = (PlaceholderFragmentMain) getSupportFragmentManager().getFragment(inState, "fragmentMain");
		this.fragmentList = (PlaceholderFragmentList) getSupportFragmentManager().getFragment(inState, "fragmentList");
				
		
		//klappst so nicht, versuche die Methode der Elternklasse danach aufzurufen.
		//super.onRestoreInstanceState(inState);
	}
	
	
	

	/**
	 * A placeholder fragment containing a simple view.
	 * ACHTUNG: Der Code für die Buttons soll hier herein. 
	 *          Daher muss der Button Event-Listener extra hier gesetzt werden.
	 *          Damit dann die onClick Methode vorhanden ist, muss View.OnClickListern implementiert werden.
	 */
	public static class PlaceholderFragmentMain<T> extends Fragment implements OnClickListener{
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
			MainActivity objActivityParent = (MainActivity)this.getActivity();
			objActivityParent.setMessageStore(objStore);
		}
		private MyMessageStoreFGL<T> getMessageStore(){
			MainActivity objActivityParent = (MainActivity)this.getActivity();
			return objActivityParent.getMessageStore();
		}

		private void setMessageCurrent(String message) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageCurrent() für '" + message + "'");
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				objStore.put(message, MyMessageHandler.RESUME_MESSAGE);
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageCurrent() findet kein Store Objekt.");
			}					
		}
		private String getMessageCurrent(){					
			Log.d("FGLSTATE", this.getClass().getSimpleName()+". getMessageCurrent() gestartet.");
			String sReturn = new String("");
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				sReturn = objStore.getString(MyMessageHandler.RESUME_MESSAGE);
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
			
			//FGL 20161204: Wenn man die Events im Fragment selbst haben will,
			//              muss man den Event-Handler am Button hier einbauen.
			//              sonst gibt es Fehlermeldungen der Art:
			//12-04 06:02:41.664: E/AndroidRuntime(1778): java.lang.IllegalStateException: Could not find method sendMessage(View) in a parent or ancestor Context for android:onClick attribute defined on view class android.support.v7.widget.AppCompatButton with id 'button_send'
			//              Alternativ dazu die Methode in der Activity belassen, was meiner Meinung nach eine unschöne Lösung ist, wg. mangelnder Kapselung.
			Button button_send_for_result = (Button) rootView.findViewById(R.id.button_send_for_result);
		    Button button_send = (Button) rootView.findViewById(R.id.button_send);
		    Button button_search = (Button) rootView.findViewById(R.id.button_search_web);
		    
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
			button_send_for_result.setOnClickListener(this);
			button_send.setOnClickListener(this);
			button_search.setOnClickListener(this);
												
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
	        default: 
	        	Log.d("FGLSTATE", this.getClass().getSimpleName()+".onClick(v) noch nicht implementiert für Id v.getId() = '" + v.getId() + "'");
	        	break;
		    }
		}
		
		// this method is only called once for this fragment
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // retain this fragment //FGL Versuche die WebView nicht neu zu laden, wenn man die Orientierung des Geräts ändert.
	        setRetainInstance(true);
	        
	        
	      //###############################################################
	        //### Hier die richtige Stelle, den in onCreateView() wird der WebViewClient mit dem Suchstring initialisiert
			//++++++++++++++++++++++++++++++++++++++++++++++
					// Get the message from the intent
					Intent intent = getActivity().getIntent();
					
					//FGL 20161125: Statt den String direkt zu übernehmen jetzt ein StoreObjekt verwenden, in dem auch Werte enthalten sind,
					//                     die ggfs. nur zwischengespeichert wurden, um sie an eine andere Activity weiter/wieder zurückzugeben.
					//String message = intent.getStringExtra(MyMessageHandler.EXTRA_MESSAGE);
												
					// Get the Message from the StoreObject, stored in the intent.
					MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);					
					if(objStore==null){
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - StoreObject IS NULL.");
					}else{
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - StoreObject FOUND.");
						this.setMessageStore(objStore);
						String sMessage = this.getMessageStore().getString(MyMessageHandler.RESUME_MESSAGE);
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreate(..) - String from StoreObject = '"+sMessage + "'");
											
						this.setMessageCurrent(sMessage);
					}
	        
	    }//end onCreate()

		
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);	
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle) wurde aktiviert");
			if(savedInstanceState!=null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle)SAVEDINSTANCESTATE IST NICHT NULL");
				
				//TODO: 20161204 Ändere das auf den Objekt MessageStore ab. 				
				//Notwendiger Zweig um Persistierung zurückzuholen. Siehe auch onResume().
	        	//String sMessageCurrent = (String) savedInstanceState.getSerializable(MyMessageHandler.KEY_MESSAGE_CURRENT);
				MyMessageStoreFGL objStore = (MyMessageStoreFGL) savedInstanceState.getSerializable(MyMessageHandler.EXTRA_STORE);
				if(objStore==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): objStore ist null");
				}else{
					String sMessageCurrent = objStore.getString(MyMessageHandler.RESUME_MESSAGE);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): sMessageCurrent = " + sMessageCurrent);
				
	        	if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
		        	this.setMessageCurrent(sMessageCurrent);
		        	
		        	//Sollte man nun irgendwie den String zurück-/einsetzen?
		        	EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
		        	if(editText==null){
		        		Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): EditText - Element im UI nicht gefunden.");	    			
		        	}else{
		        		editText.setText(sMessageCurrent + " (wiederhergestellt)");
		        	}
	        	}   
				}//if objStore!=null
			
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle)SAVEDINSTANCESTATE IST NULL");
				
				//Versuche einen gespeicherten Text wiederherszustellen.
//				//Merke: Beim einfachen Wechseln zurück wird dann nicht onCreate() aufgerufen, sondern onResume(), 
//				//       darum gehört der Code hierher, ABER: savedInstanceState ist hier nicht vorhanden.
//				
//				//Damit das funktioniert muss onRestoreInstanceState() ausgeführt werden und es muss die lokale Property wieder gefüllt worden sein.
				String sMessageCurrent = this.getMessageCurrent();
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per Variable sMessageCurrent = " + sMessageCurrent);
			
				if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
					//1. Variante: Als Variable
					EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
					editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_VARIABLE);			
				}else{
					//2. Variante: Hole den Wert aus dem MessageStore, der zwischen den Activities ausgetauscht wird.
					//             Das ist der Normalefall: Die Variable ist nämlich normalerweise weg.
					MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) getActivity().getIntent().getSerializableExtra(MyMessageHandler.EXTRA_STORE);
					if(objStore!=null){
						this.setMessageStore(objStore);
													
						//Nun Versuch sie in inStop() über einen Intent.getExtras zu sichern und hier wiederherzustellen
						sMessageCurrent = objStore.getString(MyMessageHandler.RESUME_MESSAGE);
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per intent (aus MessageStore) sMessageCurrent = " + sMessageCurrent);			
							
						if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
							//DAS FUNKTIONIERT GGFS. AUCH NICHT!!!
							EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
							editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_INTENT);
						}else{
							Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per intent (aus MessageStore) ist leer oder NULL.");			
							
							EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
							editText.setText("");
						}
					}
				}
				super.onResume();	
			}
		}
		
		public void onSaveInstanceState(Bundle outState){
			super.onSaveInstanceState(outState);			
		}
		
		public void onResume(){
			super.onResume();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): START");			
								
			String sMessageCurrent = this.getMessageCurrent();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per Variable sMessageCurrent = " + sMessageCurrent);
			
			if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
				//1. Variante: Als Variable
				EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
				editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_VARIABLE);			
			}else{
				//2. Variante: Hole den Wert aus dem MessageStore, der zwischen den Activities ausgetauscht wird.
				//             Das ist der Normalefall: Die Variable ist nämlich normalerweise weg.
				MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) getActivity().getIntent().getSerializableExtra(MyMessageHandler.EXTRA_STORE);
				if(objStore==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): KEIN ObjektStore aus dem Intent der Activity erhalten.");						
				}else{
					this.setMessageStore(objStore);
												
					//Nun Versuch sie in inStop() über einen Intent.getExtras zu sichern und hier wiederherzustellen
					sMessageCurrent = objStore.getString(MyMessageHandler.RESUME_MESSAGE);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per intent (aus MessageStore) sMessageCurrent = " + sMessageCurrent);			
						
					if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per intent (aus MessageStore) ist gefüllt");	
						
						//DAS FUNKTIONIERT GGFS. AUCH NICHT!!!
						EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
						editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_INTENT);
					}else{
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per intent (aus MessageStore) ist leer oder NULL.");			
						
						EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
						editText.setText("");
					}
				}
			}
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
					objStore.put(MyMessageHandler.RESUME_MESSAGE, message); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
					
						//Übergib den Store als ganzes an den Intent
					intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);//objStore muss serializable sein
					//------------------------------
					
					
					//intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);//Das wäre nach meiner Konvention einen Message auf oberster Ebene, ausserhalb des ObjektMessageStore.
					startActivity(intent);			
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
			objStore.put(MyMessageHandler.RESUME_MESSAGE, message); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
			
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
			objStore.put(MyMessageHandler.RESUME_MESSAGE, message); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
			
				//Übergib den Store als ganzes an den Intent
			intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);//objStore muss serializable sein
			//------------------------------
			
			//FGL 20161203: Schalte diesen direkten Weg ab...
			//intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);
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
		
		//Die Daten nur noch im objectStore der MainActivity halten.
		//private ArrayList<String> listaSearchString = new ArrayList<String>();//Für die Liste der Suchwerte, sie wird gefüllt. Wenn sie leer ist, wird ein spezieller "Leereintrag" angezeigt.
		@SuppressWarnings({ "unchecked"})
		private ArrayList<String>getSearchElements(){
			ArrayList<String> listaReturn = new ArrayList<String>();
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				listaReturn = (ArrayList<String>) objStore.getArrayList(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT);
			}
			return listaReturn;
		}
		private void setSearchElements(ArrayList<String> listaSearchString){
			MyMessageStoreFGL<T> objStore = this.getMessageStore();
			if(objStore!=null){
				objStore.put(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT, listaSearchString);
			}
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
		
		public void addElement(String sToAdd){
			ArrayList<String>listatemp = this.getSearchElements();
			listatemp.add(sToAdd);
			this.setSearchElements(listatemp);//!! Das Zurückschreiben ist notwendig ist. Es wird ansonsten nur der aktuellste Wert in die Liste gesetzt.
			
			//1. Versuch: Cast Fehler. man man nicht Object[] in String[] casten  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, (String[])listaSearchString.toArray());//Haken werden hinter den Elementen angezeigt.
			//2. Versuch: NullPointer Exception: Attempt to get length of null Array.
			//The toArray() method without passing any argument returns Object[]. So you have to pass an array as an argument, which will be filled with the data from the list, and returned. You can pass an empty array as well, but you can also pass an array with the desired size.
			//String[] saTemp = listaSearchString.toArray(new String[listaSearchString.size()]);														
			String[] saTemp = listatemp.toArray(new String[listatemp.size()]);
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElement() saTemp erzeugt.");
			
			//das Adapter Benachrichtigen reicht nicht
//			this.getArrayAdapter().notifyDataSetChanged();			
//			ListView vwList = (ListView) ((AppCompatActivity) getContext()).findViewById(R.id.list_search_web);
//			if(vwList==null){
//				Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() vwList ist NULL.");
//				
//			}else{
//				Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() vwList gefunden.");
//
//				vwList.invalidateViews();
//		        vwList.refreshDrawableState();
//			}
			
			ListView vwList = (ListView) ((AppCompatActivity) getContext()).findViewById(R.id.list_search_web);
			if(vwList!=null){
				ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, saTemp);//Haken werden hinter den Elementen angezeigt.
				this.setArrayAdapter(myArrayAdapter);//20161128: das funktioniert... ist aber eine nicht so toll, immer korrekt angepasste Breite der Einträge.
	
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".addElement() Arrayadapter neu gefüllt.");
				vwList.setAdapter(myArrayAdapter);	
				//vwList.invalidateViews();//20161128: Dies nicht machen, sonst wird jedesmal diese View 'zusätzlich' geladen (zumindest in meinen Tests)
		        vwList.refreshDrawableState();	
		        
		        //Mache den Button "Suche per Liste" aktiviert.
		        Button buttonSearchFromList = (Button)((AppCompatActivity)getContext()).findViewById(R.id.button_search_web_from_list);
		        if(buttonSearchFromList!=null){
		        	buttonSearchFromList.setEnabled(true);
		        	buttonSearchFromList.setClickable(true);//TODO: Bei einem Element der Liste Clickbar, ab 2 Elementen erst Clickbar, wenn mindestens 1 Element der Liste ausgewählt wurde.
		        }//buttonSearchFromList != null
		        
		        //Mache den Button "Entferne aus Liste" aktiviert.
		        Button buttonRemoveFromList = (Button)((AppCompatActivity)getContext()).findViewById(R.id.button_remove_from_list);
		        if(buttonRemoveFromList!=null){
		        	buttonRemoveFromList.setEnabled(true);
		        	buttonRemoveFromList.setClickable(true);//TODO: Bei einem Element der Liste Clickbar, ab 2 Elementen erst Clickbar, wenn mindestens 1 Element der Liste ausgewählt wurde.
		        }//buttonRemoveFromList != null
		        
		        
			}//vwList!=null
	    
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView(): START.");
			View rootView = inflater.inflate(R.layout.fragment_main_list, container, false);
			
			//###########################################################
			//FGL 20161204: Wenn man die Events im Fragment selbst haben will,
			//              muss man den Event-Handler am Button hier einbauen.
			//              sonst gibt es Fehlermeldungen der Art:
			//12-04 06:02:41.664: E/AndroidRuntime(1778): java.lang.IllegalStateException: Could not find method sendMessage(View) in a parent or ancestor Context for android:onClick attribute defined on view class android.support.v7.widget.AppCompatButton with id 'button_send'
			//              Alternativ dazu die Methode in der Activity belassen, was meiner Meinung nach eine unschöne Lösung ist, wg. mangelnder Kapselung.
			Button button_removeFromList = (Button) rootView.findViewById(R.id.button_remove_from_list);
		    Button button_searchFromList = (Button) rootView.findViewById(R.id.button_search_web_from_list);
		    
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
			button_removeFromList.setOnClickListener(this);		
			button_searchFromList.setOnClickListener(this);
			
			
			//#####################################################
			//Hier, versuche die ListView zu füllen
			ListView vwList;
			vwList = (ListView) rootView.findViewById(R.id.list_search_web);
			if(vwList==null){
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() vwList ist NULL.");
				
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() vwList gefunden.");

				
				//Versuche einen Eintrag für "keine Elemente" bereitzustellen. Mache dies nur im "onCreate" (und nicht im start()), da ansonsten beim Betätigen des "Return" Buttons mehrer dieser Einträge passieren.
				//Remember to place the emptyView after binding the adapter to listview.Mine was not working for first time and after I moved the setEmptyView after the setAdapter it is now working.
				TextView txtNoItems = noItems(vwList, getResources().getString(R.string.element_search_web_from_list_empty));
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() Leerlisteneintrag txtNoItems erzeugt.");
				
				vwList.setEmptyView(txtNoItems);
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() Leerlisteneintrag txtNoItems gesetzt.");
				
				if(savedInstanceState==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() mit Bundle 'savedInstanceState' ist NULL.");
					
					//Teste auf gefüllte Liste
					//initialisiereListTestElemente();
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() mit Bundle 'savedInstanceState' gefunden.");
					
					//Stelle die Liste mit Suchelementen wieder her
					//TODO FGL 20161203: Hohle die Werte aus dem ObjectMessageStore.
					
					//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast		
					ArrayList<String> listaTemp = (ArrayList<String>) savedInstanceState.get(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() mit Bundle '" + listaTemp.size() + "' ArrayList SuchListen-Elementen.");
					this.setSearchElements(listaTemp);						
				}
				
			
				//Zeige die Liste mit Suchelementen an.	
				ArrayList<String> listaTemp = (ArrayList<String>) this.getSearchElements();
				
				if(listaTemp.isEmpty()){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() ArrayList für Elemente ist leer.");
					//Merke: Im onStart wird ein Element erstellt, dass angezeigt werden soll, wenn die Liste leer ist.
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() ArrayList mit Elementen ist gefüllt. Anzahl Elemente: " + getSearchElements().size());
				}
										
					//1. Versuch: Cast Fehler. man man nicht Object[] in String[] casten  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, (String[])listaSearchString.toArray());//Haken werden hinter den Elementen angezeigt.
					//2. Versuch: NullPointer Exception: Attempt to get length of null Array.
					//The toArray() method without passing any argument returns Object[]. So you have to pass an array as an argument, which will be filled with the data from the list, and returned. You can pass an empty array as well, but you can also pass an array with the desired size.
					String[] saTemp = listaTemp.toArray(new String[listaTemp.size()]);
																
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() saTemp erzeugt.");
					ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, saTemp);//Haken werden hinter den Elementen angezeigt.
					this.setArrayAdapter(myArrayAdapter);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() Arrayadapter erzeugt.");
					vwList.setAdapter(myArrayAdapter);	
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onCreateView() Arrayadapter gesetzt.");									
			}
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
			
			//NOTWENDIG ZUM PERSISTIERN DER DATEN IM BUNDLE
					//super.onSaveInstanceState(outState);
					//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
					
					//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
							//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
							//            und Eclipse neu starten muss.
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onSaveInstanceState() wurde aktiviert");
					
					
					//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast
					//TODO FGL 20161203: Ändere das auf den MessageObjectStore ab
					ArrayList<String> listaTemp = this.getSearchElements();
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onSaveInstanceState(): Es sind '" + listaTemp.size() + "' Elemente in der Liste.");
					outState.putSerializable(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT, listaTemp); //liste darf nicht das Interface sein, sondern muss explizit die Klasse ArrayList sein.
					super.onSaveInstanceState(outState);
					
					//Merke: Bei Fragments gibt es keine onRestoreInstanceState()-Methode. Hole das Bundle in onActivityCreated ab.
		}
		
		@SuppressWarnings("unchecked")
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);	
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(bundle) wurde aktiviert");
			if(savedInstanceState!=null){
				MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) savedInstanceState.getSerializable(MyMessageHandler.EXTRA_STORE);
				if(objStore==null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): objStore ist null");
				}else{
					String sMessageCurrent = objStore.getString(MyMessageHandler.RESUME_MESSAGE);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated(): sMessageCurrent = " + sMessageCurrent);				
				
					//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast		
					ArrayList<String> listaTemp = (ArrayList<String>) objStore.get(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT);
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() mit Bundle '" + listaTemp.size() + "' ArrayList SuchListen-Elementen.");
					this.setSearchElements(listaTemp);			
				
				if(listaTemp.isEmpty()){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() ArrayList für Elemente ist leer.");
					//Merke: Im onStart wird ein Element erstellt, dass angezeigt werden soll, wenn die Liste leer ist.
				}else{
					Log.d("FGLSTATE",this.getClass().getSimpleName()+".onActivityCreated() ArrayList mit Elementen ist gefüllt. Anzahl Elemente: " + listaTemp.size());
					
					//Hier, versuche die ListView zu füllen
					ListView vwList;					
					vwList = (ListView) getActivity().findViewById(R.id.list_search_web);
					if(vwList==null){
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() vwList ist NULL.");
						
					}else{
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() vwList gefunden.");

						//1. Versuch: Cast Fehler. man man nicht Object[] in String[] casten  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, (String[])listaSearchString.toArray());//Haken werden hinter den Elementen angezeigt.
						//2. Versuch: NullPointer Exception: Attempt to get length of null Array.
						//The toArray() method without passing any argument returns Object[]. So you have to pass an array as an argument, which will be filled with the data from the list, and returned. You can pass an empty array as well, but you can also pass an array with the desired size.
						String[] saTemp = listaTemp.toArray(new String[listaTemp.size()]);
																	
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() saTemp erzeugt.");
						ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, saTemp);//Haken werden hinter den Elementen angezeigt.
						this.setArrayAdapter(myArrayAdapter);
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() Arrayadapter erzeugt.");
						vwList.setAdapter(myArrayAdapter);	
						Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() Arrayadapter gesetzt.");	
					}																					
				}		//end if(this.listaSearchString.isEmpty()){		
				}//end if (objStore == null)
			}else{
				Log.d("FGLSTATE", this.getClass().getSimpleName()+".onActivityCreated() savedInstanceState==null.");
			}//end if(savedInstanceState!=null){							
		}
		
		public void onResume(){
			super.onResume();
			Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): START");
			
			//Versuche die gespeicherten Liste wiederherszustellen.
			//Merke: Beim einfachen Wechseln zurück wird dann nicht onCreate() aufgerufen, sondern onResume(), 
			//       darum gehört der Code hierher, ABER: savedInstanceState ist hier nicht vorhanden.
			
			//Damit das funktioniert muss onRestoreInstanceState() ausgeführt werden und es muss die lokale Property wieder gefüllt worden sein.
			MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) getActivity().getIntent().getSerializableExtra(MyMessageHandler.EXTRA_STORE);
			if(objStore!=null){
				this.setMessageStore(objStore);
												
				//Nun Versuch sie in inStop() über einen Intent.getExtras zu sichern und hier wiederherzustellen
				ArrayList<String> listaCurrent = (ArrayList<String>) objStore.get(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT);
				if(listaCurrent!=null){
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): Wert per intent (aus MessageStore) ArrrayList mit = '" + listaCurrent.size() + "' Elementen.");
					this.setSearchElements(listaCurrent);
				}else{
					Log.d("FGLSTATE", this.getClass().getSimpleName()+".onResume(): KEIN Wert per intent (aus MessageStore) ArrrayList.");
				}							
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
				//Hole aus dem Store die ArrayList
				//objStore.put(MyMessageHandler.RESUME_MESSAGE, message); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
				
				//Hole die markierte Position aus der Designelement-Liste
				//Übergib den Store als ganzes an den Intent
				//intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);//objStore muss serializable sein
				//------------------------------				
			}
		
		
			//intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);
			//startActivityForResult(intent,1);
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
				//Hole aus dem Store die ArrayList
				//objStore.put(MyMessageHandler.RESUME_MESSAGE, message); //IDEE: das sollte der Wert der UI-Komponente sein, also R.id.edit_message
				
				//Hole die markierte Position aus der Designelement-Liste
				//Übergib den Store als ganzes an den Intent
				//intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);//objStore muss serializable sein
				//------------------------------				
			}
		
		
			//intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);
			//startActivityForResult(intent,1);
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
				
		private void initialisiereListTestElemente(){
			String[] saTest = {"eins","zwei","drei","vier","fünf"};
			for(int icount=0; icount <= saTest.length-1; icount++){
				this.getSearchElements().add(saTest[icount]);
			}			
		}				
	}
}

