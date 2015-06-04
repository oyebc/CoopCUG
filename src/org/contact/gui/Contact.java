/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.contact.gui;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author Oyebc
 */
public class Contact implements JSONable {
    
    private String title="";
    private String surname="";
    private String otherNames="";
    private String gender="";
    private String city="";
    private String ccPin="";
    private String numbers="";
    private String cugNumber="";

    public Contact(final String aTitle, final String aSurname, final String theOtherNames, final String aGender, final String aCity, final String aCCPin, final String theNumbers, final String cugNumber)
    {
        title = aTitle;
        surname= aSurname;
        otherNames = theOtherNames;
        gender = aGender;
        city = aCity;
        ccPin = aCCPin;
        numbers = theNumbers;
        this.cugNumber=cugNumber; 
    }
    
    public Contact()
    {}
    public String getCcPin() {
        return ccPin;
    }

    public void setCcPin(String ccPin) {
        this.ccPin = ccPin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(!city.equals(""))
            this.city = city;
        else
            this.city = "Unknown";
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public void setCugNumber(String cugnumber){
        this.cugNumber= cugnumber;
    }
    
    public String getCugNumber(){
        return cugNumber;
    }
    
    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toJSON() {
        
                JSONObject inner = new JSONObject();
		try {
			inner.put("title", getTitle());
			inner.put("surname", getSurname());
			inner.put("other_names", getOtherNames());
                        inner.put("gender", getGender());
                        inner.put("city", getCity());
                        inner.put("cc_pin", getCcPin());
                        inner.put("numbers", getNumbers());
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return inner.toString();
    }

    public void fromJSON(String JSONstring) {
        
        try {
			JSONObject json = new JSONObject(JSONstring);
			setTitle(json.getString("title"));
                        setSurname(json.getString("surname"));
                        setOtherNames(json.getString("other_names"));
                        setGender(json.getString("gender"));
                        setCity(json.getString("city"));
                        setCcPin(json.getString("cc_pin"));
                        setNumbers(json.getString("numbers"));
                        setCugNumber(json.getString("cug_phone"));
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
    }
    
    public void fromLoginJSON(String JSONstring) {
        
        try {
			JSONObject json = new JSONObject(JSONstring);
			setTitle(json.getString("title"));
                        setSurname(json.getString("surname"));
                        setOtherNames(json.getString("other_names"));
                        setGender(json.getString("gender"));
                        setCity(json.getString("city"));
                        setCcPin(json.getString("cc_pin"));
                        setCugNumber(json.getString("cug_phone"));
//                        setNumbers(json.getString("numbers"));
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
    }
    public String toString(){
    
        return getTitle()+"-"+getSurname() +"-"+ getOtherNames()+"-"+getGender()+"-"+getCity()+"-"+getNumbers()+"-"+getCugNumber();
    }

}
