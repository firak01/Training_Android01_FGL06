package de.fgl.tryout.android.training001;

import java.util.ArrayList;

import android.app.Activity;
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

//FGL06 - Mache hieraus eine ActionBarActivity
//.... extends Activity
//Damit eine Menüleiste angezeigt wird. Aber ActionBarActivty ist deprecated
//Lösung:
//AppCompatActivity wird über die SupportBibiothek (V7) eingebunden.
public class DisplayMessageActivityForResult<T> extends AppCompatActivity {	
	private MyMessageStoreFGL<T> objStore=null;
	private String sMessageCurrent=null;
	
	/**
	 * @param message
	 * 15.07.2016 08:26:09 Fritz Lindhauer
	 */
	private void setMessageCurrent(String message) {
		this.sMessageCurrent= message;
		Log.d("FGLSTATE", this.getClass().getSimpleName()+". setMessageCurrent() für '" + message + "'");
		
	}
	private String getMessageCurrent(){		
		if(this.sMessageCurrent==null) this.sMessageCurrent=new String("");
		
		return this.sMessageCurrent;		
	}
	
	private void setMessageStore(MyMessageStoreFGL<T> objStore){
		this.objStore = objStore;
	}
	private MyMessageStoreFGL<T> getMessageStore(){
		if(this.objStore==null){
			this.objStore = new MyMessageStoreFGL<T>();
		}
		return this.objStore;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message_activity_for_result);
		
		
		//###############################################################
		//++++++++++++++++++++++++++++++++++++++++++++++
				// Get the message from the intent
				Intent intent = getIntent();
				
				//FGL 20161125: Statt den String direkt zu übernehmen jetzt ein StoreObjekt verwenden, in dem auch Werte enthalten sind,
				//                     die ggfs. nur zwischengespeichert wurden, um sie an eine andere Activity weiter/wieder zurückzugeben.
				//String message = intent.getStringExtra(MyMessageHandler.EXTRA_MESSAGE);
											
				// Get the Message from the StoreObject, stored in the intent.
				MyMessageStoreFGL objStore = (MyMessageStoreFGL) intent.getSerializableExtra(MyMessageHandler.EXTRA_STORE);
				if(objStore==null){
					Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - StoreObject IS NULL.");
				}else{
					Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - StoreObject FOUND.");
					this.setMessageStore(objStore);
					String sMessage = this.getMessageStore().getString(MyMessageHandler.EXTRA_MESSAGE);
					Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - String from StoreObject = '"+sMessage + "'");
										
					this.setMessageCurrent(sMessage);
				}
				
				
			
				//++++++++++++++++++++++++++++++++++++++++++++++
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
						//TODO 20160818: Warum ist Action Bar NULL?
						Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - action bar IS NULL.");
						
					}else{
						Log.d("FGLTEST", "Methode sDisplayActivity.onCreate(..) - action bar not null.");
					
						actionBar.setDisplayHomeAsUpEnabled(true);
					
						//Style den Hintergrund	
						//TODO 20161103: Funktioniert nicht
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
//					getSupportFragmentManager().beginTransaction()
//							.add(R.id.container, new PlaceholderFragment()).commit();
				}
		
		
	}
	
	//FGL für neue Buttons in der Action Bar notwendig
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		    // Inflate the menu items for use in the action bar
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.display_message, menu);
		    return super.onCreateOptionsMenu(menu);
		}
	
	
	//FGL: Training/Adding the Action Bar / Adding Action Buttons
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			Log.d("FGLSTATE", "DisplayMessageActivityForResult.onOptionsItemSelected()");
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
//			if (id == R.id.action_settings) {
//				return true;
//			}
//			return super.onOptionsItemSelected(item);
			
			
			//### Fang den "Zurück" Button der ActivityActionBar ab. #####
			//Ziel: Übergib an die Ausgangsaktivität wieder den Wert. 
			//a) Wenn über den Zurück-Button des Geräts gearbeitet wird, ist es nicht notwendig. 
			//   Dann bleibt der Wert als Variable in der Activity vorhanden.
			//b) Wenn über den Zurück-Button in der ActionBar gearbeitet wird, sind die Werte in onResume() nur entgegenzunehmen,
			//   durch einen Intent, in dem man den Wert speichert.
			//c) IDEE: Versuche aus der Hauptaktivität mal die DisplayMessageActivity02 aufzurufen mit
			//         startActivityForResult(). Vielleicht geht es dann einfacher.
			//
			if(id==16908332){
				//If Abfrage, weil in der Switch-Case Anweisung der Vergleich nicht zu klappen scheint.
				Log.d("FGLSTATE", "DisplayMessageActivityForResult.DisplayMessageActivityForResult.onOptionsItemSelected() für speziell definierte actionBarId gefunden.");
				
				
				//TODO GOON FGL 20161202: Packe den neuen Wert in das StoreObjekt zurück.
				//                                        Packe Testweise die Dummy-ArrayListe in das StoreObjekt.
				//                                        Packe das StoreObjekt in das Bundle
				//                                        Hole in der MainActivity die Werte zurück.....
				
				//Versuch X: Gib an die aufgerufene Funktion den Wert zurück
	    		Bundle bundle = new Bundle();
	            bundle.putString(MyMessageHandler.RESUME_MESSAGE_BUNDLE, this.getMessageCurrent());
	            //natürlich nicht in den Intent Packen, der dieser Activity beim Start mitgegeben worden ist getIntent().putExtras(bundle);
	            
	            //TODO GOON: Packe testweise eine ArrayListe hier herein, versuche diese dann entgegenzunehmen.
	            //                    Das Ziel ist es so die ArrayListe im ListenFragment auch zu füllen.
	            //Versuch3b: Arraylist (für Suchelementliste)
	            Bundle bundle03 = new Bundle();
	            ArrayList<String>listaTemp02=new ArrayList<String>();
	            listaTemp02.add("TEST02");
	            bundle03.putStringArrayList(MyMessageHandler.KEY_ELEMENTS_TO_SEARCH_CURRENT, listaTemp02);	               			         
	            
	            //Start an intent mit dem Ziel diesen in der onResume Methpde entgegenzunehmen.
	    		Intent intent = new Intent(this, MainActivity.class);	    		
	    		intent.putExtras(bundle);
	    		intent.putExtras(bundle03);
	      		startActivity(intent); //Merke: Nachteil ist, das jeder Activity-Start quasi in eine History kommt. 
	      		                       //       Das bedeutet, dass der Zurück-Button des Geräts erst einmal alle Activities aus der Historie durchläuft,
	      		                       //       wenn man ihn in der Hauptmaske benötigt.
	    		
				
			}else{
				Log.d("FGLSTATE", "DisplayMessageActivityForResult.onOptionsItemSelected() für speziell definierte actionBarId NICHT gefunden.");
				
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
			        	Log.d("FGLSTATE", "DisplayMessageActivityForResult.onOptionsItemSelected() für HOME item.id= '" + id + "'");
			        case R.id.homeAsUp:
			        	Log.d("FGLSTATE", "DisplayMessageActivityForResult.onOptionsItemSelected() für HOMEASUP item.id= '" + id + "'");
			        case R.id.up:
			        	Log.d("FGLSTATE", "DisplayMessageActivityForResult.onOptionsItemSelected() für HUP item.id= '" + id + "'");
			        case 16908332:
			        	//DAS WIRD AUS iregendeinem Grund nicht ausgeführt. Darum in den if-Abfrage vorneweg verlagert.
			        	Log.d("FGLSTATE", "DisplayMessageActivityForResult.onOptionsItemSelected() für speziell definierte actionBarId ohne in R-Klasse vohranden zu sein: item.id= '" + id + "'");	        		        
			        default:
			        	Log.d("FGLSTATE", "DisplayMessageActivityForResult.onOptionsItemSelected() für default item.id= '" + id + "'");
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
	
	
	public void finish() {
//	    if (mParent == null) {
//	        int resultCode;
//	        Intent resultData;
//	        synchronized (this) {
//	            resultCode = mResultCode;
//	            resultData = mResultData;
//	        }
//	        if (Config.LOGV) Log.v(TAG, "Finishing self: token=" + mToken);
//	        try {
//	            if (ActivityManagerNative.getDefault()
//	                .finishActivity(mToken, resultCode, resultData)) {
//	                mFinished = true;
//	            }
//	        } catch (RemoteException e) {
//	            // Empty
//	        }
//	    } else {
//	        mParent.finishFromChild(this);
//	    }
		
		Log.d("FGLSTATE", this.getClass().getSimpleName()+". im finish()");
		
		String message = this.getMessageCurrent() + " (als Result)";
		Intent data = new Intent();
		data.putExtra(MyMessageHandler.EXTRA_MESSAGE, message);
		setResult(Activity.RESULT_OK, data);
		
		//Wichtig: Erst den Intent bauen und dann erst finish() der Elternklasse aufrufen.
		super.finish();
	}
		
	
}
