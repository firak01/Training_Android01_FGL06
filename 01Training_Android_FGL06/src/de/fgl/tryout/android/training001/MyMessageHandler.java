/**
 * 
 */
package de.fgl.tryout.android.training001;

import basic.zBasic.util.datatype.string.StringZZZ;

/**
 * @author Fritz Lindhauer
 *
 */
public class MyMessageHandler {
	public final static String EXTRA_STORE = "de.fgl.tryout.android.training001.MyMessageStoreFGL"; //Darunter sollen dann alle anderen Werte (ereichbar über die anderen Stringkonstanten) abgelegt sein.	
	//public final static String EXTRA_MESSAGE = "de.fgl.tryout.android.training001.MainAcitvity.MESSAGE"; //Konvention: Das wäre eine Message auf oberster Ebene und nicht im ObjectMessageStore.
	
	public final static String RESUME_MESSAGE = "de.fgl.tryout.android.training001.MainAcitvity.RESUMEMESSAGE";
	//Das ist dann beim Verwenden des MessageStore nicht mehr notwendig.
	//Alles wird nämlich auf die gleiche Messageposition bezogen public final static String RESUME_MESSAGE_BUNDLE = "de.fgl.tryout.android.training001.MainAcitvity.RESUMEMESSAGEBUNDLE";
	
	
	public final static String KEY_MESSAGE_CURRENT="currentMessage";
	public final static String KEY_ELEMENTS_TO_SEARCH_CURRENT="currentListToSearchElements";
	public final static String KEY_ELEMENTS_TO_SEARCH_CLICKED="currentListToSearchElementsClicked";
	
	//Folgende Texte werden beim Zurückkehren aus der "DisplayMessageActivity" an den Sende String gehängt. Je nachdem welcher Weg gewählt wurde.
	public final static String MESSAGE_ADDITION_VARIABLE=" (wiederhergestellt per Variable)";
	public final static String MESSAGE_ADDITION_BUNDLE=" (wiederhergestellt per Intent und Bundle)";
	public final static String MESSAGE_ADDITION_INTENT=" (wiederhergestellt per Intent)";
	public final static String MESSAGE_ADDITION_RESULT=" (als Result)";
	
	//Für den überzähligen Listeneintrag
	public final static String MESSAGE_ADDITION_IMPLEMENT_SCROLLING = " (letzer Eintrag, per Scrollen nicht erreichbar)";
	public final static String MESSAGE_IMPLEMENT_SCROLLING = "Maximale Anzahl für eine nicht scrollbare Liste erreicht. (Alternativlösung muss implementiert werden)";
	
	/**
	 * @param message
	 * @return
	 * 01.08.2016 11:24:43 Fritz Lindhauer
	 */
	public static String createNormedMessage(String message) {
		message = StringZZZ.replace(message, MyMessageHandler.MESSAGE_ADDITION_VARIABLE, "");
		message = StringZZZ.replace(message, MyMessageHandler.MESSAGE_ADDITION_INTENT, "");
		message = StringZZZ.replace(message, MyMessageHandler.MESSAGE_ADDITION_BUNDLE, "");		
		message = StringZZZ.replace(message, MyMessageHandler.MESSAGE_ADDITION_RESULT,"");
		return message;
	}
}
