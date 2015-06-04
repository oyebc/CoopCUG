/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import com.futurice.tantalum2.util.UniqueItemVector;
import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author Oyebc
 */
class Response implements JSONable {
 
	public static Contact[] contacts= {}; 
        
	
	public Contact[] getContact() {
		return contacts;
	}
	
        public void setThreads(Contact[] contact) {
		this.contacts = contact;
	} 
 
	public void fromJSON(final String jsonString) {
		try {
//			JSONObject json = new JSONObject(jsonString);
//			JSONArray jsonArray = json.getJSONArray("threads");
                        JSONArray jsonArray = new JSONArray(jsonString);
			int total = jsonArray.length();
			Contact[] threads = new Contact[total];
			for (int i=0;i<total;i++) {
				String threadsJSON = jsonArray.getString(i);
				threads[i] = new Contact();
				threads[i].fromJSON(threadsJSON);
			}
			setThreads(threads);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}
	
        public static UniqueItemVector fromSavedJSON(final String jsonString) {
	
            UniqueItemVector threads = new UniqueItemVector();
            try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray jsonArray = json.getJSONArray("threads");
//                        JSONArray jsonArray = new JSONArray(jsonString);
			int total = jsonArray.length();
//			Contact[] threads = new Contact[total];
                        
                        Contact tempContact;
			for (int i=0;i<total;i++) {
				String threadsJSON = jsonArray.getString(i);				
                                tempContact = new Contact();
				tempContact.fromJSON(threadsJSON);
                                threads.addElement(tempContact);
			}
//			setThreads(threads);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
            return threads;
	}
        
        public static String toJSON( Contact[] contactArray) {
            
            
		JSONObject inner = new JSONObject();
		try {
			JSONArray jsonArray = new JSONArray();
//			Contact[] threads = getContact();
                        Contact[] threads = contactArray;
			for (int i=0;i<threads.length;i++) {
				jsonArray.put(threads[i].toJSON());
			}
			inner.put("threads", jsonArray);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return inner.toString();
	}
 
	public String toString() {
		
                String response= "";
                Contact[] threads = getContact();
		for (int i=0;i<threads.length;i++) {
			response += "[" + threads[i].toString() + "]";
		}
		return response; 
	}

    public String toJSON() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
}
