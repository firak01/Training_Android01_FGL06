/**
 * 
 */
package de.fgl.tryout.android.training001;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.abstractList.CollectionAndroidUtils;

/** Idee: Übergib nur noch diesen einen Wert an den Intent.
 *          Dann muss man auch nur noch diesen einen Wert vom Intent holen.
 *          Die anderen Werte sind dann hierin (... irgendwie in einer Map ... ) abgelegt.
 * @author Fritz Lindhauer
 *Muss serializable sein, damit es mit intent.putExtra(MyMessageHanlder.EXTRA_STORE, objStore) an den Intent übergeben werden kann.
 * @param <T>
 */
public class MyMessageStoreFGL<T> extends ObjectZZZ implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4868128170326164471L;
	//private Bundle objBundleAll=null; //Versuch das mal statt eine Hashmap zu verwenden, weil es Android spezifisch ist. //ABER: Bundle ist nicht serialisierbar!!! Darum Fehler!!!
	private HashMap<String,T> objHmAll=null;
	private void setHashMapAll(HashMap<String,T>hm){				
		this.objHmAll=hm;
	}
	private HashMap<String,T>getHashMapAll(){
		if(this.objHmAll==null){
			this.objHmAll = new HashMap<String,T>();
		}
		return this.objHmAll;
	}
	
	//####################################################
	public MyMessageStoreFGL(){		
	}
	
	public MyMessageStoreFGL(Intent intent){
		Bundle objBundle = intent.getExtras();
		HashMap<String,T> objhm = this.bundleToHashMap(objBundle);
		this.setHashMapAll(objhm);
	}
	
	@SuppressWarnings("unchecked")
	public void put(String sKey, String sValue){
		this.getHashMapAll().put(sKey, (T) sValue);		
	}
	public void put(String sKey, T sValue){
		this.getHashMapAll().put(sKey, sValue);		
	}
	@SuppressWarnings("unchecked")
	public void put(String sKey, ArrayList<String> listaTemp) {
		this.getHashMapAll().put(sKey, (T) listaTemp);
	}
	
	public T get(String sKey){
		return this.getHashMapAll().get(sKey);
	}
	public String getString(String sKey){
		return (String) this.getHashMapAll().get(sKey);
	}
	@SuppressWarnings("unchecked")
	private HashMap<String,T>bundleToHashMap(Bundle objBundle){
		//Man muss ein Pacable Objekt haben/selbst erstellen. ggfs. wie in
		//https://guides.codepath.com/android/using-parcelable
		//Parcelable c = new Parcelable();
		//HashMap hmReturn = CollectionAndroidUtils.fromBundle(objBundle, c);//hierfür braucht man ein Parcelable() Objekt
		
	
		//Da ich erst einmal den "primitiven" Weg realisieren möchte:
		HashMap<String,T> hmReturn = new HashMap<String,T>();
				
		Set<String> objSet = objBundle.keySet();
		for (String s : objSet) {
		    System.out.println(s);
		    hmReturn.put(s, (T) objBundle.get(s));
		}		
		return hmReturn;
	}
	
	
	
	
}
