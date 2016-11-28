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
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Aus developer.android.com Training/Building Your First App
 * @Date   2014-05-06
 * @author Fritz Lindhauer
 *
 */
public class MainActivity extends  AppCompatActivity{ // ActionBarActivity { //Merke: ActionBarActivity is deprecated
	private String sMessageCurrent=null;
	private MyVersionBox versionBox = null;
	private MyVersionHtmlBox versionHtmlBox = null;
	private MyAboutBox aboutBox = null;
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


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the user interface layout for this Activity
	    // The layout file is defined in the project res/layout/main_activity.xml file
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new PlaceholderFragment(),"FRAGMENT_MAIN").commit();
			
					//FGL06: füge eine Liste der Suchbegriffe hinzu, und die Möglichkeit daraus auswählend (ggfs. kombiniert) zu suchen
			getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new PlaceholderFragmentList(),"FRAGMENT_MAIN_LIST").commit();
		 } else {
			//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
			//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
			//            und Eclipse neu starten muss.
			Log.d("FGLSTATE", "MainActivity.onCreate() wurde aktiviert. MIT SAVEDINSTANCESTATE vorhanden");
			 
        	//Notwendiger Zweig um Persistierung zurückzuholen. Siehe auch onResume().
        	String sMessageCurrent = (String) savedInstanceState.getSerializable(MyMessageHandler.KEY_MESSAGE_CURRENT);
        	Log.d("FGLSTATE", "MainActivity.onCreate(): sMessageCurrent = " + sMessageCurrent);
			
        	if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
	        	this.setMessageCurrent(sMessageCurrent);
	        	
	        	//Sollte man nun irgendwie den String zurück-/einsetzen?
	        	EditText editText = (EditText) findViewById(R.id.edit_message);
	        	if(editText==null){
	        		Log.d("FGLSTATE", "MainActivity.onCreate(): EditText - Element im UI nicht gefunden.");	    			
	        	}else{
	        		editText.setText(sMessageCurrent + " (wiederhergestellt)");
	        	}
        	}        	
        }
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
	
	public void addElementToSearchList(View view){
		Log.d("FGLSTATE", "MainActivity.addElementToSearchList()");
		   
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();		
		Log.d("FGLSTATE", "MainActivity.addElementToSearchList(): message = " + message);
		
		if(!StringZZZ.isEmpty(message)){
			
			//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse
			message = MyMessageHandler.createNormedMessage(message);							
			Log.d("FGLSTATE", "sendessageForResult(): message nach der Normierung = " + message);
								
			PlaceholderFragmentList frgList = (PlaceholderFragmentList)getSupportFragmentManager().findFragmentByTag("FRAGMENT_MAIN_LIST");
            frgList.addElement(message);			
			Log.d("FGLSTATE", "MainActivity.addElementToSearchList(): message hinzugefügt");			
		}
	}
	
	
	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
	    //Start an intent
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		
		//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse
		message = MyMessageHandler.createNormedMessage(message);		
		Log.d("FGLSTATE", "sendessage(): message nach der Normierung = " + message);
		
		//Speichere die message in eine lokale Variable. Grund: So kann man sie dann wegsichern wenn sich der State des Ger�ts �ndert.
		this.setMessageCurrent(message);
				
		intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	
	/** Called when the user clicks the SendForResult button */
	public void sendMessageForResult(View view) {
	    //Start an intent
		Intent intent = new Intent(this, DisplayMessageActivityForResult.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		
		//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse		
		message = MyMessageHandler.createNormedMessage(message);				
		Log.d("FGLSTATE", "sendessageForResult(): message nach der Normierung = " + message);
		
		//Speichere die message in eine lokale Variable. Grund: So kann man sie dann wegsichern wenn sich der State des Ger�ts �ndert.
		this.setMessageCurrent(message);
				
		intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);
		startActivityForResult(intent,1);
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 * 27.07.2016 09:09:40 Fritz Lindhauer
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("FGLSTATE", "onActivityResult(): START mit requestCode='" + requestCode + "' | resultCode='"+resultCode+"'");
	    super.onActivityResult(requestCode, resultCode, data);	    
	    if (requestCode == 1) {
	    		String stredittext=data.getStringExtra(MyMessageHandler.EXTRA_MESSAGE);
	         if(resultCode == RESULT_OK){	             
	             Log.d("FGLSTATE", "onActivityResult(): result OK. message  empfangen = " + stredittext);
	             this.setMessageCurrent(stredittext);
	         }else{	        	
	        	 Log.d("FGLSTATE", "onActivityResult(): result nicht OK. message  empfangen = " + stredittext);
	         }
	    }
	} 
	
	
	/**Starete eine Activity mit einer WebView. Dabei werfe den eingegebenen Text als Suchstring in die URL.
	 * @param view
	 * 13.10.2016 10:17:34 Fritz Lindhauer
	 */
	public void sendMessageToSearchWeb(View view){
		//Start an intent
				Intent intent = new Intent(this, DisplayWebviewActivityForSearch.class);
				EditText editText = (EditText) findViewById(R.id.edit_message);
				String message = editText.getText().toString();
				
				//Besser als das Standard String.replace und Pattern zu verwenden ist hier die JAZKernel-Hilfsklasse		
				message = MyMessageHandler.createNormedMessage(message);								
				Log.d("FGLSTATE", "searchWeb(): message nach der Normierung = " + message);
				
				//Speichere die message in eine lokale Variable. Grund: So kann man sie dann wegsichern wenn sich der State des Ger�ts �ndert.
				this.setMessageCurrent(message);
						
				intent.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);
				startActivity(intent);			
	}
	
	/**
	 * @param message
	 * 15.07.2016 08:26:09 Fritz Lindhauer
	 */
	private void setMessageCurrent(String message) {
		this.sMessageCurrent= message;
		Log.d("FGLSTATE", "MainActivity.setMessageCurrent() für '" + message + "'");
		
	}
	private String getMessageCurrent(){
		Log.d("FGLSTATE", "MainActivity.getMessageCurrent() für '" + this.sMessageCurrent + "'");
		return this.sMessageCurrent;		
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
		Log.d("FGLSTATE", "onPause() wurde aktiviert");
		
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
	    Log.d("FGLSTATE", "onNewIntent() wurde aktiviert");
	    setIntent(intent);
	}
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 * 14.07.2016 08:06:14 Fritz Lindhauer
	 */
	public void onResume(){
		//super.onResume();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
		//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
		//            und Eclipse neu starten muss.
		Log.d("FGLSTATE", "onResume() wurde aktiviert");
				
		//Versuche einen gespeicherten Text wiederherszustellen.
		//Merke: Beim einfachen Wechseln zurück wird dann nicht onCreate() aufgerufen, sondern onResume(), 
		//       darum gehört der Code hierher, ABER: savedInstanceState ist hier nicht vorhanden.
    	//String sMessageCurrent = (String) savedInstanceState.getSerializable(KEY_MESSAGE_CURRENT);
    	//this.setMessageCurrent(sMessageCurrent);
    	
		//Damit das funktioniert muss onRestoreInstanceState() ausgeführt werden und es muss die lokale Property wieder gefüllt worden sein.
		String sMessageCurrent = this.getMessageCurrent();
		Log.d("FGLSTATE", "onResume(): Wert per Variable sMessageCurrent = " + sMessageCurrent);
		
		if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
			//1. Variante: Als Variable
			EditText editText = (EditText) findViewById(R.id.edit_message);
			editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_VARIABLE);			
		}else{
			//2. Variante als "StringExtra"			
			//Das ist der Normalefall: Die Variable ist nämlich weg.
			//Nun Versuch sie in inStop() über einen Intent.getExtras zu sichern und hier wiederherzustellen
			sMessageCurrent=getIntent().getStringExtra(MyMessageHandler.RESUME_MESSAGE);
			Log.d("FGLSTATE", "onResume(): Wert per intent sMessageCurrent = " + sMessageCurrent);			
					
			if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
				//DAS FUNKTIONIERT GGFS. AUCH NICHT!!!
				EditText editText = (EditText) findViewById(R.id.edit_message);
				editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_INTENT);
			}else{
				//3. Variante: Als Bundle aus getExras()
				Bundle bundle = getIntent().getExtras();
				if(bundle!=null){
					sMessageCurrent = bundle.getString(MyMessageHandler.RESUME_MESSAGE_BUNDLE);
					Log.d("FGLSTATE", "onResume(): Wert per intent und bundle sMessageCurrent = " + sMessageCurrent);
					
					if(!StringZZZ.isEmptyNull(sMessageCurrent)&& !StringZZZ.isBlank(sMessageCurrent) & !StringZZZ.isWhitespace(sMessageCurrent)){
						EditText editText = (EditText) findViewById(R.id.edit_message);
						editText.setText(sMessageCurrent + MyMessageHandler.MESSAGE_ADDITION_BUNDLE);
					}
				}else{
					Log.d("FGLSTATE", "onResume(): Bundle ist auch im neuen intent leer");				
				}			
			}
		}
		super.onResume();
	}
	
	public void onStop(){
		//super.onStop();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
				Log.d("FGLSTATE", "onStop() wurde aktiviert");
				
				
		//Versuche für onResume() den Stringwert zu retten, der dann in onResume() ausgelesen werden soll.
				//ABER TODO GOON 20160715: Das klappt nicht!!!
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();			
		Log.d("FGLSTATE", "onStop() - Sicher Message in intent weg: " + message);
		getIntent().putExtra(MyMessageHandler.RESUME_MESSAGE, message);
		
		//ABER: So kann der Wert nicht in der gleichen Activity wiedergeholte werden.

		//Versuch 2:
		Bundle bundle = new Bundle();
        bundle.putString(MyMessageHandler.RESUME_MESSAGE_BUNDLE, message);
        getIntent().putExtras(bundle);
        
        //Versuch 3: Mit neuem Intent UND der überschriebenenen Methode onNewIntent()
        Log.d("FGLSTATE", "onStop() - Sicher Message in NEUEM intent weg: " + message);
        //Intent intent = new Intent(this, MainActivity.class);
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle02 = new Bundle();
        bundle02.putString(MyMessageHandler.RESUME_MESSAGE_BUNDLE, message);
        intent.putExtras(bundle02);
		
        super.onStop();
	}
	
	public void onRestart(){
		super.onRestart();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
				Log.d("FGLSTATE", "onRestart() wurde aktiviert");
	}
	
	public void onStart(){
		super.onStart();
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
				Log.d("FGLSTATE", "onStart() wurde aktiviert");
	}
	
	public void onSaveInstanceState(Bundle outState){
		
		//NOTWENDIG ZUM PERSISTIERN DER DATEN IM BUNDLE
				//super.onSaveInstanceState(outState);
				//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
				
				//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
						//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
						//            und Eclipse neu starten muss.
						Log.d("FGLSTATE", "onSaveInstanceState() wurde aktiviert");
				
				
				//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast
				String sMessage = this.getMessageCurrent();
				outState.putSerializable(MyMessageHandler.KEY_MESSAGE_CURRENT, sMessage); //liste darf nicht das Interface sein, sondern muss explizit die Klasse ArrayList sein.
				super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle inState){
		//NOTWENDIG ZUM ZURUECKHOLEN DER PERSISTIERTEN DATEN AUS DEM BUNDEL. 
		//WIRD ABER NUR BEI onCreate() aufgerufen
		
		//Merke onResume() oder onStart() haben kein Bundle als Parameter
		//Bei onResume() ... WIE KANN MAN DA DIE DATEN ZURUECKHOLEN?????
		//super.onRestoreInstanceState(inState);
		//FGL: Rufe beim Überschreiben dieser Event-Methoden IMMER die Methode der Elternklasse auf.
		
		//FGL: Versuch etwas in LogCat auszugeben. Dazu muss der Emulator/das Gerät verbunden sein.
				//     Merke: Hatte man ggfs. mehrere Emulatoren am Laufen, kann es sein, dass man alle beenden muss
				//            und Eclipse neu starten muss.
				Log.d("FGLSTATE", "onRestoreInstanceState() wurde aktiviert");
		
		
		//GRUND: SERIALIZIERUNG geht nur mit expliziter Klasse, ggf. reicht auch hier ein "dreckiger" Typecast		
		String sMessage = (String) inState.get(MyMessageHandler.KEY_MESSAGE_CURRENT);
		Log.d("FGLSTATE", "onRestoreInstanceState() mit sMessage="+sMessage);
		this.setMessageCurrent(sMessage);	
		
		//klappst so nicht, versuche die Methode der Elternklasse danach aufzurufen.
		super.onRestoreInstanceState(inState);
	}
	
	
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragmentList extends Fragment {
		private ArrayList<String> listaSearchString = new ArrayList<String>();//Für die Liste der Suchwerte, sie wird gefüllt. Wenn sie leer ist, wird ein spezieller "Leereintrag" angezeigt.
		private ArrayList<String>getSearchElements(){
			return listaSearchString;
		}
		
		private ArrayAdapter arrayAdapter;
		private void setArrayAdapter(ArrayAdapter adapter){
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
			
			//1. Versuch: Cast Fehler. man man nicht Object[] in String[] casten  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, (String[])listaSearchString.toArray());//Haken werden hinter den Elementen angezeigt.
			//2. Versuch: NullPointer Exception: Attempt to get length of null Array.
			//The toArray() method without passing any argument returns Object[]. So you have to pass an array as an argument, which will be filled with the data from the list, and returned. You can pass an empty array as well, but you can also pass an array with the desired size.
			String[] saTemp = listaSearchString.toArray(new String[listaSearchString.size()]);														
			Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() saTemp erzeugt.");
			
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
			ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, saTemp);//Haken werden hinter den Elementen angezeigt.
			this.setArrayAdapter(myArrayAdapter);//20161128: das funktioniert... ist aber eine nicht so toll, immer korrekt angepasste Breite der Einträge.

			Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() Arrayadapter neu gefüllt.");
			vwList.setAdapter(myArrayAdapter);	
			//vwList.invalidateViews();//20161128: Dies nicht machen, sonst wird jedesmal diese View 'zusätzlich' geladen (zumindest in meinen Tests)
	        vwList.refreshDrawableState();				
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_list, container, false);
			
			//Hier, versuche die ListView zu füllen
			ListView vwList;
			vwList = (ListView) rootView.findViewById(R.id.list_search_web);
			if(vwList==null){
				Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() vwList ist NULL.");
				
			}else{
				Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() vwList gefunden.");

				//Teste auf gefüllte Liste
				//initialisiereListTestElemente();
							
				if(this.listaSearchString.isEmpty()){
					Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() ArrayList für Elemente ist leer.");
					//Merke: Im onStart wird ein Element erstellt, dass angezeigt werden soll, wenn die Liste leer ist.
				}else{
					Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() ArrayList mit Elementen ist gefüllt. Anzahl Elemente: " + listaSearchString.size());
				}
										
					//1. Versuch: Cast Fehler. man man nicht Object[] in String[] casten  ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, (String[])listaSearchString.toArray());//Haken werden hinter den Elementen angezeigt.
					//2. Versuch: NullPointer Exception: Attempt to get length of null Array.
					//The toArray() method without passing any argument returns Object[]. So you have to pass an array as an argument, which will be filled with the data from the list, and returned. You can pass an empty array as well, but you can also pass an array with the desired size.
					String[] saTemp = listaSearchString.toArray(new String[listaSearchString.size()]);
																
					Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() saTemp erzeugt.");
					ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(vwList.getContext(), android.R.layout.simple_list_item_checked, saTemp);//Haken werden hinter den Elementen angezeigt.
					this.setArrayAdapter(myArrayAdapter);
					Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() Arrayadapter erzeugt.");
					vwList.setAdapter(myArrayAdapter);	
					Log.d("FGLSTATE", "PlaceholderFragementList.onCreateView() Arrayadapter gesetzt.");									
			}
			return rootView;
		}
		
		@Override
		public void onStart() {
		    super.onStart();
		    Log.d("FGLSTATE", "PlaceholderFragementList.onStart()");
		    ListView vwList = (ListView) ((AppCompatActivity) getContext()).findViewById(R.id.list_search_web);
		    
			//Remember to place the emptyView after binding the adapter to listview.Mine was not working for first time and after I moved the setEmptyView after the setAdapter it is now working.
			TextView txtNoItems = noItems(vwList, getResources().getString(R.string.element_search_web_from_list_empty));
			Log.d("FGLSTATE", "PlaceholderFragementList.onStart() Leerlisteneintrag txtNoItems erzeugt.");
			
			vwList.setEmptyView(txtNoItems);
			Log.d("FGLSTATE", "PlaceholderFragementList.onStart() Leerlisteneintrag txtNoItems gesetzt.");
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
		    Log.d("FGLSTATE", "PlaceholderFragementList.noItems() ... TextView erzeugt");
		    
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
				this.listaSearchString.add(saTest[icount]);
			}
			
		}
		
		
	}

}
