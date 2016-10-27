/**
 * 
 */
package de.fgl.tryout.android.training001;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

/**
 * @author Fritz Lindhauer
 *
 */
public class DialogBoxAlertOK {
	private Activity callingActivity = null;
	private String titlePrefix = null;
	private String title = null;
	 
	public String getTitle() {
		String sTitle = new String("");
		if(this.title==null){
			sTitle = this.getCallingActivity().getString(R.string.app_name);
		}else{
			sTitle = this.title;
		}
		return sTitle;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitlePrefix() {
		return titlePrefix;
	}
	public void setTitlePrefix(String titlePrefix) {
		this.titlePrefix = titlePrefix;
	}

	public Activity getCallingActivity() {
		return callingActivity;
	}

	public void setCallingActivity(Activity callingActivity) {
		this.callingActivity = callingActivity;
	}
	
	
	
	public DialogBoxAlertOK(){
		
	}
	public String computeTitleTotal(){
		String sReturn = new String("");
		if(this.getTitlePrefix()!=null) sReturn = sReturn + this.getTitlePrefix();
		if(this.getTitle()!=null) sReturn = sReturn + this.getTitle();
		return sReturn;
	}
	public void show(View viewContent){
		//Build and show the dialog		    
	    new AlertDialog.Builder(this.getCallingActivity())
	      .setTitle(this.computeTitleTotal())
	      .setCancelable(true)
	      .setIcon(R.drawable.ic_launcher)
	      .setPositiveButton("OK", null)
	      .setView(viewContent)
	      .show();    //Builder method returns allow for method chaining
	}
	
	
}
