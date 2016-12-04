package de.fgl.tryout.android.training001;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


// .... extends Activity
//Damit eine Menüleiste angezeigt wird. Aber ActionBarActivity ist deprecated
//public class DisplayMessageActivity extends ActionBarActivity {
//Lösung:
//AppCompatActivity wird wohl über die SupportBibiothek (V7) eingebunden.
public class DisplayMessageActivity<T> extends AppCompatActivity {
	private MyMessageStoreFGL<T> objStore=null;
	private String sMessageCurrent;
	
	private void setMessageStore(MyMessageStoreFGL<T> objStore){
		this.objStore = objStore;
	}
	private MyMessageStoreFGL<T> getMessageStore(){
		if(this.objStore==null){
			this.objStore = new MyMessageStoreFGL<T>();
		}
		return this.objStore;
	}
	
	/**
	 * @param message
	 * 15.07.2016 08:26:09 Fritz Lindhauer
	 * Test auf Änderungen
	 */
	private void setMessageCurrent(String message) {
		this.sMessageCurrent= message;
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".setMessageCurrent() für '" + message + "'");
		
	}
	private String getMessageCurrent(){
		Log.d("FGLSTATE", this.getClass().getSimpleName()+".getMessageCurrent() für '" + this.sMessageCurrent + "'");
		if(this.sMessageCurrent==null){
			this.sMessageCurrent=new String("");
		}
		return this.sMessageCurrent;		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//++++++++++++++++++++++++++++++++++++++++++++++
		// Get the message from the intent
		Intent intent = getIntent();

		MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);
		if(objStore==null){
			Log.d("FGLSTATE", "DisplayMessageActivity.onCreate(..) - Store ist NULL.");
		}else{
			this.setMessageStore(objStore);
			String sMessage = objStore.getString(MyMessageHandler.EXTRA_MESSAGE);	
			Log.d("FGLSTATE", "DisplayMessageActivity.onCreate(..) - Message aus dem Store = '" + sMessage +"'.");
			this.setMessageCurrent(sMessage);
		
		
		//++++++++++++++++++++++++++++++++++++++++++++++
		
		//FGL: TODO - Das mit dem Ändern der Farbe funktioniert nicht
		int iColor;
		String alarmMessagePrefix = "Alarm";
		if(this.getMessageCurrent().startsWith(alarmMessagePrefix)){
			iColor = Color.RED;
		}else{
			iColor = Color.GRAY;
		}
		
		//FGL: Check System Version at Runtime
		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
			//FGL: Aktiviere den Home / UP Button 
//			android.support.v7.app.ActionBar actionBar = getSupportActionBar();  //funktioniert nur in einer ActionBarActivity
//			actionBar.setDisplayHomeAsUpEnabled(true);
//			
//			//Style den Hintergrund		
//			actionBar.setBackgroundDrawable(new ColorDrawable(iColor)); // set your desired color
		}else{
			Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - minSdkVersion is 11 or higher.");
			
			// If your minSdkVersion is 11 or higher, instead use:
			android.app.ActionBar actionBar = getActionBar();
			if(actionBar==null){
				//TODO 20160818: Warum ist Action Bar NULL?
				Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - action bar IS NULL.");
				
			}else{
				Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - action bar not null.");
			
				actionBar.setDisplayHomeAsUpEnabled(true);
			
				//Style den Hintergrund
				//TODO: 20161103 Funktioniert nicht
				actionBar.setBackgroundDrawable(new ColorDrawable(iColor)); // set your desired color
			}		
		}
			
		
		// Create the text view
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText(this.getMessageCurrent());

	    // Set the text view as the activity layout
	    setContentView(textView);

	    //FGL: Wenn man die View im Layout-Editor erstellt, kann man eine ID vergeben, die hier benutzt werden kann. 
	    //Initialize member TextView so we can manipulate it later
	    //mTextView = (TextView) findViewById(R.id.text_message);
		//setContentView(R.layout.activity_display_message);

		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		}//objStore!=null
	}

	//Wird für diese App nicht benötigt
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.display_message, menu);
//		return true;
//	}

	//FGL für neue Buttons in der Action Bar notwendig
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		    // Inflate the menu items for use in the action bar
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.display_message, menu);
		    return super.onCreateOptionsMenu(menu);
		}
		
		//FGL für die Reaktion auf neue Buttons in der ActionBar notwendig
	//FGL: Training/Adding the Action Bar / Adding Action Buttons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("FGLSTATE", "onOptionsItemSelected()");
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
		
		
		//### Fang den "Zurück" Button der ActivityActionBar ab. #####
		//Ziel: Übergib an die Ausgangsaktivität wieder den Wert. 
		//a) Wenn über den Zurück-Button des Geräts gearbeitet wird, ist es nicht notwendig. 
		//   Dann bleibt der Wert als Variable in der Activity vorhanden.
		//b) Wenn über den Zurück-Button in der ActionBar gearbeitet wird, sind die Werte in onResume() nur entgegenzunehmen,
		//   durch einen Intent, in dem man den Wert speichert.
		if(id==16908332){
			Intent intent = new Intent(this, MainActivity.class);
			
			//If Abfrage, weil in der Switch-Case Anweisung der Vergleich nicht zu klappen scheint.
			Log.d("FGLSTATE", "onOptionsItemSelected() für speziell definierte actionBarId gefunden.");
			
			//Verwende den Message Store zur Rückgabe von Werten
			MyMessageStoreFGL<T> objStore = (MyMessageStoreFGL<T>) this.getMessageStore();//getIntent().getSerializableExtra(MyMessageHandler.EXTRA_STORE);
			if(objStore!=null){														
				sMessageCurrent = objStore.getString(MyMessageHandler.RESUME_MESSAGE);
				Log.d("FGLSTATE", "onResume(): Wert per intent (aus MessageStore) sMessageCurrent = " + sMessageCurrent);			
			
			    intent.putExtra(MyMessageHandler.EXTRA_STORE, objStore);						
			}
			
      		startActivity(intent); //Merke: Nachteil ist, das jeder Activity-Start quasi in eine History kommt. 
      		                       //       Das bedeutet, dass der Zurück-Button des Geräts erst einmal alle Activities aus der Historie durchl�uft,
      		                       //       wenn man ihn in der Hauptmaske betätigt.
    		
			
		}else{
			Log.d("FGLSTATE", "onOptionsItemSelected() für speziell definierte actionBarId NICHT gefunden.");
			
			// Handle presses on the action bar items
		    switch (id) {
		    	case R.id.action_end:
					//finish(); //Aber: Beendet nur diese Activity, nicht aber die Start Activity
					finishAffinity(); //Beendet auch alle "Parent Activities", Ab Android 4.1.		
		        case R.id.action_search:
		            openSearch();
		            return true;
		        case R.id.action_settings:
		            openSettings();
		            return true;
		        case R.id.home:
		        	Log.d("FGLSTATE", "onOptionsItemSelected() für HOME item.id= '" + id + "'");
		        case R.id.homeAsUp:
		        	Log.d("FGLSTATE", "onOptionsItemSelected() für HOMEASUP item.id= '" + id + "'");
		        case R.id.up:
		        	Log.d("FGLSTATE", "onOptionsItemSelected() für HUP item.id= '" + id + "'");
		        case 16908332:
		        	//DAS WIRD AUS iregendeinem Grund nicht ausgef�hrt. Darum in den if-Abfrage vorneweg verlagert.
		        	Log.d("FGLSTATE", "onOptionsItemSelected() für speziell definierte actionBarId ohne in R-Klasse vohranden zu sein: item.id= '" + id + "'");	        		        
		        default:
		        	Log.d("FGLSTATE", "onOptionsItemSelected() für default item.id= '" + id + "'");
		            return super.onOptionsItemSelected(item);
		    }
		}
		
		return true;
	}

	
	private void openSettings() {
		// TODO Auto-generated method stub
		
	}

	private void openSearch() {
		// TODO Auto-generated method stub
		
	}	
}
