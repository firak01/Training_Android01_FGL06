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
	public final static String EXTRA_MESSAGE = "de.fgl.tryout.android.training001.MainAcitvity.MESSAGE";
	public final static String RESUME_MESSAGE = "de.fgl.tryout.android.training001.MainAcitvity.RESUMEMESSAGE";
	public final static String RESUME_MESSAGE_BUNDLE = "de.fgl.tryout.android.training001.MainAcitvity.RESUMEMESSAGEBUNDLE";
	
	
	public final static String KEY_MESSAGE_CURRENT="currentMessage";
	
	//Folgende Texte werden beim Zurückkehren aus der "DisplayMessageActivity" an den Sende String gehängt. Je nachdem welcher Weg gewählt wurde.
	public final static String MESSAGE_ADDITION_VARIABLE=" (wiederhergestellt per Variable)";
	public final static String MESSAGE_ADDITION_BUNDLE=" (wiederhergestellt per Intent und Bundle)";
	public final static String MESSAGE_ADDITION_INTENT=" (wiederhergestellt per Intent)";
	public final static String MESSAGE_ADDITION_RESULT=" (als Result)";
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
