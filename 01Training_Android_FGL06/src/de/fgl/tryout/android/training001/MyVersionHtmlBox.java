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
import android.webkit.WebView;
import android.widget.TextView;
//import biz.tekeye.abouttest.AboutBox;
/**
 * @author Fritz Lindhauer
 *
 */
public class MyVersionHtmlBox extends DialogBoxAlertOK{
	 private MyVersionHtmlHandler versionHtmlHandler=null;
	 
	public MyVersionHtmlHandler getVersionHtmlHandler() {
		if(this.versionHtmlHandler==null){
			this.versionHtmlHandler=new MyVersionHtmlHandler();
		}
		return this.versionHtmlHandler;
	}

	public void setVersionHtmlHandler(MyVersionHtmlHandler versionHtmlHandler) {
		this.versionHtmlHandler = versionHtmlHandler;
	}

	

	public MyVersionHtmlBox(){
		super();
	}
	public MyVersionHtmlBox(MyVersionHtmlHandler objVersionHtmlHandler){
		this();
		this.setVersionHtmlHandler(objVersionHtmlHandler);
	 }
	 
	  public void Show(Activity callingActivity) {
			  this.setCallingActivity(callingActivity);
			  
			//Anders als bei den Txt-Dateien wird die HTML Datei nicht ausgelesen, sondern 1:1 angezeigt.
			  
		    //Generate views to pass to AlertDialog.Builder and to set the text
		    View about;
		    WebView wvAbout;
		    LayoutInflater inflater = null;
		    try {
		      //Inflate the custom view
		      inflater = this.getCallingActivity().getLayoutInflater();
		      about = inflater.inflate(R.layout.dialogbox_alert_html_ok, (ViewGroup) this.getCallingActivity().findViewById(R.id.myAboutBox));
		      //tvAbout = (TextView) about.findViewById(R.id.myAboutText);		
		      wvAbout = (WebView) about.findViewById(R.id.webView1);
		      
		      //Versuch die Scrollbar permanent zu machen.
		      wvAbout.setScrollbarFadingEnabled(false);
		      wvAbout.setScrollBarFadeDuration(0);
		    } catch(InflateException e) {
		      //Inflater can throw exception, unlikely but default to TextView if it occurs
		      about = wvAbout = new WebView(callingActivity);		      
		    }
		    
		    //Hier den Text nicht setzen, sondern die WebView per WebKit initialisieren
		    MyVersionHtmlHandler handler = this.getVersionHtmlHandler();
		    handler.initialisiereWebKit(wvAbout);
		    
		    //Build and show the dialog	
		    super.show(about);		   
		  }
		  
		
	
}
