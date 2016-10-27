package de.fgl.tryout.android.training001;

import android.app.Activity;
import android.util.Log;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**Mit dem eigenene WebViewClient sicherstellen:
 * - dass auch Links innerhalb der WebView geöffnet werden und nicht im Standardbrowser.
 * @author Fritz Lindhauer
 *
 */
public class MyWebViewClient extends WebViewClient{
	private boolean mDataLoaded=false;
	
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String sUrl) {
            	//Merke: WebView hat aus Sicherheitsgründen strenge Einschränkungen, was darin funktioniert. (z.B. kein Zugriff auf Cookies oder BrowserCache)
            	//FGL: Diese View speichern, damit wir auf deren Activity, etc. zugreifen können.
            	//this.setWebView(view);
                view.loadUrl(sUrl);
                return true;
            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
                        
            //Idee aus: 09Example_AndroidSDK_Legacy-ApiDemos01\src\com\example\android\apis\app\PrintHtmlFromScreen.java
            @Override
            public void onPageFinished(WebView view, String url) {
            	Log.d("FGLTEST", "onPageFinished() für MyWebviewClient aufgerufen.");
    			
                // Data loaded, so now we want to show the print option.
                mDataLoaded = true;
                
             // Important: Only enable the print option after the page is loaded.
             // FGL: Weil das im Beispiel in einer inneren Klasse innerhalb einer activity-Klasse ist, kann man das so schreiben:
                //invalidateOptionsMenu();
                //Aber hier sind wir in einer eigenen Klasse. Darum:
                ViewParent vp01 = view.getParent();
                Log.d("FGLTEST", "onPageFinished(): ViewParent ist von der Klasse: " + vp01.getClass().getName());
                

                Activity ac = (Activity) view.getContext(); 
                //view.isFocused()

                //Hole das Fragment host.getFragmentManager().findFragmentById(R.id.mySearchWebView);
                
                //Activity ac = ((DisplayWebviewActivityForSearch)getActivity());
                ac.invalidateOptionsMenu();
    			
            }

            //Darüber wird gesteuert, ob im Menü der "Print" Button angezeigt werden kann.
            public boolean isDataLoaded(){
            	return this.mDataLoaded;
            }
}
