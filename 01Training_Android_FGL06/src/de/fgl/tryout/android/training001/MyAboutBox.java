/**
 * 
 */
package de.fgl.tryout.android.training001;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//import biz.tekeye.abouttest.AboutBox;
/**
 * @author Fritz Lindhauer
 *
 */
public class MyAboutBox extends DialogBoxAlertOK{
	 private MyContentHandler contentHandler=null;
	 
	public MyContentHandler getContentHandler() {
		if(this.contentHandler==null){
			this.contentHandler=new MyContentHandler();
		}
		return this.contentHandler;
	}

	public void setContentHandler(MyContentHandler contentHandler) {
		this.contentHandler = contentHandler;
	}

	

	public MyAboutBox(){
		super();
	}
	public MyAboutBox(MyContentHandler objContentHandler){
		this();
		this.setContentHandler(objContentHandler);
	 }
	 
	 //nicht mehr static machen
	 public void Show(Activity callingActivity) {
			  this.setCallingActivity(callingActivity);
			  
			  String sAboutText = this.computeAboutString();
			  Log.d("FGLTEST", "Methode Show() aboutText = " + sAboutText);
		      //SpannableString aboutTextSpannable = this.computeSpannableVersionString();
			  
		    //Generate views to pass to AlertDialog.Builder and to set the text
		    View about;
		    TextView tvAbout;
		    LayoutInflater inflater = null;
		    try {
		      //Inflate the custom view
		      inflater = this.getCallingActivity().getLayoutInflater();
		     // about = inflater.inflate(R.layout.aboutbox, (ViewGroup) callingActivity.findViewById(R.id.aboutView));
		      about = inflater.inflate(R.layout.dialogbox_alert_ok, (ViewGroup) this.getCallingActivity().findViewById(R.id.myAboutBox));
		      //tvAbout = (TextView) about.findViewById(R.id.myAboutText);		
		      tvAbout = (TextView) about.findViewById(R.id.textView1);
		    } catch(InflateException e) {
		      //Inflater can throw exception, unlikely but default to TextView if it occurs
		      about = tvAbout = new TextView(callingActivity);		      
		    }
		    
		    //ALSO LÖSUNG: Ziehe eine neue Textview in das layout. Mit der alten gab es Probleme.
		    //TODO Goon 20160907: Lösung schön machen.
		    
		    //Set the about text
		    //CharSequence cs = "<html><body>AAAAAS</body></html>"; //CharSequence is an interface, and the String class implements CharSequence.		    
		    //tvAbout.setText(cs);
		    
		     tvAbout.setText(sAboutText);
		    
		    //tvAbout.setText("hardcoded Text");
		    // Now Linkify the text
		    // Das entfernt irgendwie den normalen Text, oder ?       
		    //Linkify.addLinks(tvAbout, Linkify.ALL);
		    //CharSequence test = tvAbout.getText();
		    //Log.d("FGLTEST", "Methode Show() aboutText = " + test);
		    
		    //Build and show the dialog	
		    super.show(about);		   
		  }
		  
		  private String computeAboutString(){
			  String sReturn="";
			  main:{
				  Activity callingActivity = this.getCallingActivity();
				  String sText = new String("");
				  String sAbout = new String("");
				  if(callingActivity!=null){				  
					  sText = sText + callingActivity.getString(R.string.about);
							  					  
  					  
  					  MyContentHandler contentHandler = this.getContentHandler();  					  
  					  if(contentHandler!=null){
  						  sAbout = "\n" + sAbout + contentHandler.readFileAboutFromRawFGL(callingActivity);					
  					  }					  					    					 
				  }
				  sReturn = sText + sAbout;
				
			
			  }//end main:
			  return sReturn;			
		  }
		  
		  private SpannableString computeSpannableAboutString(){
			//Use a Spannable to allow for links highlighting
			  String sText = this.computeAboutString();
			  SpannableString objReturn=new SpannableString(sText);
			  return objReturn;
		  }

	
	
}
