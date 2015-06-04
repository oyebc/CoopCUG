/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import org.json.me.JSONArray;
import org.json.me.JSONException;

/**
 *
 * @author Oyebc
 */
public class HttpAPI {
    private static HttpConnection httpConn;
    private static InputStream inStream;
    
    public static Contact authenticate(String ccPin) {
        StringBuffer sb = new StringBuffer();
        try {
       //     doc = new Document();
            //Security bug here must be fix. 
            //Sending authenication parameters without SSL/TSL socket enabled. -seyi 
//            String queryString = "http://209.59.223.18:8080/MobileApp/Mobile?action=Auth&uname=" + username + "&pwd=" + password;
            String queryString ="http://coopnetwork.coop/ccpinapp/login.php?pin="+ccPin;
            httpConn = (HttpConnection) Connector.open(queryString);
            httpConn.setRequestMethod(HttpConnection.GET);
            //MIDP 2.0 should be considered for use -seyi
            httpConn.setRequestProperty("User-Agent", "Profile/MIDP-1.0 Confirguration/CLDC-1.0");
            inStream = httpConn.openInputStream();   
                       
            if(inStream!=null){
            
                      int ch = 0;
                      while(true){ // get the title.
                        ch = inStream.read();
                        if (ch == -1){
                          break;
                        }
                        sb.append((char)ch);
                      }
        }
                    }
                    catch (IOException x){
                            x.printStackTrace();
                    }
                    finally{
                         try     {
                             if(inStream!=null)
                             {   inStream.close();
                              httpConn.close();}
                         } catch (IOException x){
                              x.printStackTrace();
                         }
        
                    }
  
                    System.out.println(sb.toString());
    
                    Contact contact= null;
                    if(sb.toString().equals("false") || inStream==null)
                    {}   
                    else
                        {
                        contact = new Contact();
                            try {
                                JSONArray jArray = new JSONArray(sb.toString());
                                 contact.fromLoginJSON(jArray.getJSONObject(0).toString());
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                       
                    }
                    
        
        return contact;
    }
    
    public static boolean search(final String surname, final String otherNames,final String city, final String gender){
    
          StringBuffer sb = new StringBuffer();
          boolean isSystemOk = true;
        try {
       //     doc = new Document();
            //Security bug here must be fix. 
            //Sending authenication parameters without SSL/TSL socket enabled. -seyi 
//            String queryString = "http://209.59.223.18:8080/MobileApp/Mobile?action=Auth&uname=" + username + "&pwd=" + password;
           
            String queryString ="http://coopnetwork.coop/ccpinapp/search.php?sname="+surname+"&oname="+otherNames+ "&city="+city+"&sex="+gender;
            httpConn = (HttpConnection) Connector.open(queryString);
            httpConn.setRequestMethod(HttpConnection.GET);
            //MIDP 2.0 should be considered for use -seyi
            httpConn.setRequestProperty("User-Agent", "Profile/MIDP-1.0 Confirguration/CLDC-1.0");
            inStream = httpConn.openInputStream();   
                       
            
 
                      int ch = 0;
                      while(true){ // get the title.
                        ch = inStream.read();
                        if (ch == -1){
                          break;
                        }
                        sb.append((char)ch);
                      }
                    }
                    catch (IOException x){
//                            x.printStackTrace();
                            isSystemOk = false;
                            System.out.println(isSystemOk);
                    }
               // catch(ConnectionNotFoundException ex){}
                    finally{
                         try     {
                             if(inStream!=null)
                              inStream.close();
                              httpConn.close();
                         } catch (IOException x){
                              x.printStackTrace();
                         }
                    }
                    System.out.println(sb.toString());
                        Response response = new Response();
                        if(!sb.toString().equals(""))
                         response.fromJSON(sb.toString());
                       
                         return isSystemOk;
                    
    }
    
}
