/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import com.futurice.tantalum2.TantalumMIDlet;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

/**
 * @author Oyebc
 */
public class ContactSearch extends TantalumMIDlet implements CommandListener {
    
    private Displayable currentDisplayable;
    private ContactCanvas canvas;
    private Form resultForm;
    private Command addJob;
    private Command exit;
    private Command about;
    
    public void startApp() {
        switchDisplayable(null, getCanvas());
        currentDisplayable = canvas;
//        currentDisplayable = getResultForm(new Contact[]{});
//        switchDisplayable(null, currentDisplayable);
//          String sample = "{\"api_status\":\"SAMPLE\",\"threads\":[\"{\\\"tid\\\":10,\\\"title\\\":\\\"3rdTitle\\\"}\",\"{\\\"tid\\\":15,\\\"title\\\":\\\"this is 4th Title\\\"}\"]}";
//          sample = "{\"threads\":[\"{\\\"title\\\":\\\"Mr.\\\",\\\"surname\\\":\\\"Oladokun\\\",\\\"other_names\\\":\\\"Stephen A.\\\",\\\"gender\\\":\\\"Male\\\",\\\"city\\\":\\\"Ibadan\\\",\\\"cc_pin\\\":\\\"D06D10D8\\\",\\\"numbers\\\":\\\"\\\"}\",\"{\\\"title\\\":\\\"Mrs.\\\",\\\"surname\\\":\\\"Oladokun\\\",\\\"other_names\\\":\\\"Stephanie A.\\\",\\\"gender\\\":\\\"Female\\\",\\\"city\\\":\\\"Ibadan\\\",\\\"cc_pin\\\":\\\"28F5FA8\\\",\\\"numbers\\\":\\\"08077161125: GLO, 08038336171: MTN,\\\"}\" ]}";
//          Response response = new Response();
//	  response.fromJSON(sample);
//	  System.out.println("Convert from JSON : "+ response);
    }
    
    public void pauseApp() {
        switchDisplayable(null, currentDisplayable);
    }
    
     public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
        final Display display = getDisplay();

        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }
    }
     
     public Display getDisplay() {
        return Display.getDisplay(this);
    }
     
     public ContactCanvas getCanvas()
     {
         if(canvas == null)
             canvas = new ContactCanvas(this);
         
         return canvas;
     }
     
     private Displayable getResultForm(Contact[] jobs) {
        
        if(resultForm == null)
        {
            resultForm = new Form("Contact");
            for(int i=0;i<jobs.length;i++)
            {
                resultForm.append(new ContactDisplayCustomItem(jobs[i], resultForm.getWidth()));
            }
            exit = new Command("Exit", Command.EXIT, 0);
            addJob = new Command("Add",Command.SCREEN, 1);
            about = new Command("About", Command.HELP, 1);
            resultForm.addCommand(exit);
            resultForm.addCommand(addJob);           
            resultForm.addCommand(about);
            resultForm.setCommandListener(this);
        }
        return resultForm;
    }
    
     public void commandAction(Command c, Displayable d) {
        if (c==exit)
        {
            exitMIDlet(false);
        }
        else if(c==addJob)
        {
            resultForm.append(new ContactDisplayCustomItem(new Contact("Mr", "Bitches", "Be Trippin", "Male", "Rack City", "69", "1-800-FU", "1-800-FU"), resultForm.getWidth()));
            resultForm.append(new ContactDisplayCustomItem(new Contact("Ms", "Niggas", "Be Thirsty", "Female", "Rack City", "69", "1-800-FU", "1-800-FU"), resultForm.getWidth()));
//            resultForm.append(new JobCustomItem("Pistis Ambassadorial Event Consultant", "Pistis Events Planning Limited", null, resultForm.getWidth()));
        }
        else if(c==about)
        {
            String aboutString = "CoopCUG as a word was derived from the words Cooperative and Closed User Group. It was designed as an on-net voice telecom platform that allows members who are subscribed to the COOpCUG network to make calls for any length of time, 24 hours a day and seven days a week for the whole  month after the payment of the prescribed monthly rental fee for the four GSM providers i.e. MTN, Airtel, Glo and Etisalat."+

                                "What this translates to is that if a member subscribes to CoopCUG, that member will pay a prescribed monthly fee, which is renewable every month, and this allows such member to make voice calls to other members on the CoopCUG platform without any charges as long as such calls terminate on the same network e.g. calls made from an MTN subscriber to another MTN subscriber both on the CoopCUG Service. When a subscriber makes a call from his network to another network e.g. from MTN to Airtel, there is a charge made on such calls. But these charges are usually lower than what one would ordinarily get were such calls made by those who are not subscribed to the CoopCUG."+

                                "A subscriber to the network is issued a CoopCUG PIN known as \"CC PIN\" which is a unique identification number only obtainable to the subscriber after the completion of the registration process. This enables the subscriber after the completion of the registration process. This enables the subscriber to store and share telephone numbers with other subscribers on the CoopCUG network, in the same way that a Blackberry subscriber has a BB PIN."+

                                "To subscribe, logon to www.coopcug.com and follow the instructions or visit any of our dealers/agents within your area. Payments for the CoopCUG service can be made to CoopCUG accounts at First Bank, Zenith Bank, Stanbic IBTC, Skye Bank and mobile payment through e-wallet accounts on MTN MObile Money, First Monie, Glo Cash, Teasly Mobile, Fortis Mobile e.t.c.";
            
            Alert aboutAlert = new Alert("About", aboutString, null, AlertType.INFO);
            aboutAlert.setTimeout(Alert.FOREVER);
        }
     }
}
