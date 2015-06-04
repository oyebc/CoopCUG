/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import com.futurice.tantalum2.util.StringUtils;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Oyebc
 */
public class ContactDetailsView extends View{
    
    private final Command backCommand = new Command("Back",Command.BACK,0);
    private final Command saveCommand = new Command ("Save","Save this Contact to Phone Memory", Command.SCREEN, 1);
    private final Command deleteCommand = new Command ("Delete","Delete this Contact from Phone Memory", Command.SCREEN, 1);
    
    private final Contact currentContact;
    private int contentHeight;
    private View parentView;
    public final static int SEARCHMODE=0;
    public final static int CONTACTMODE=1;
    private final int mode;

    public ContactDetailsView(final ContactCanvas canvas, Contact contact, View view, final int viewMode){
        super(canvas);
        currentContact = contact;
        parentView = view;
        mode = viewMode;
    }
    
    public ContactDetailsView(final ContactCanvas canvas, final Contact contact, final int viewMode)  {
        super(canvas);
        currentContact = contact;
        mode=viewMode;
//        parentView = null;
    }
    
    public void keyPressed(int keyCode) {
      if (keyCode == -1 || keyCode == Canvas.KEY_NUM2 || keyCode==Canvas.UP) 
            {
                renderY+=ContactCanvas.MARGIN;
                canvas.queueRepaint();
            }
        else if (keyCode == -2 || keyCode == Canvas.KEY_NUM8||keyCode==Canvas.DOWN)
        {
                renderY-=ContactCanvas.MARGIN;
                canvas.queueRepaint();
        } 
    }
    
    public void render(Graphics g, int width, int height) {

        canvas.setFullScreenMode(false);
        if (contentHeight < canvas.getHeight()) {
            this.renderY = 0;
        } else if (this.renderY < -contentHeight + canvas.getHeight()) {
            this.renderY = -contentHeight + canvas.getHeight();
        } else if (this.renderY > 0) {
            this.renderY = 0;
        }
        
        g.setColor(ContactCanvas.GREEN);
        g.fillRect(0, 0, width, height);
        
        //int curY = ContactCanvas.MARGIN;
        int curY = ContactCanvas.MARGIN + renderY;        
        int offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Title"))/2;
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.setFont(ContactCanvas.FONT_TITLE);
        g.drawString("Title", offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        g.setColor(ContactCanvas.WHITE);
        g.setFont(ContactCanvas.FONT_DESCRIPTION);
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, currentContact.getTitle()))/2;
        g.drawString(currentContact.getTitle(), offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        curY+=ContactCanvas.MARGIN/2;
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, curY, width, curY);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, curY+1, width, curY+1);
        curY+=ContactCanvas.MARGIN/2;
        
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Surname"))/2; 
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.setFont(ContactCanvas.FONT_TITLE);
        g.drawString("Surname", offSetX, curY, Graphics.TOP|Graphics.LEFT);
//        g.drawString("Company Name", width/2, curY, Graphics.VCENTER|Graphics.HCENTER);
        curY+= ContactCanvas.FONT_TITLE.getHeight();        
        g.setColor(ContactCanvas.WHITE);
        g.setFont(ContactCanvas.FONT_DESCRIPTION);
//        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, currentJob.getCompanyName()))/2;
          offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, currentContact.getSurname()))/2;
        g.drawString(currentContact.getSurname(), offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
       // g.drawString("Flour Mill", width/2, curY, Graphics.HCENTER);
        curY+=ContactCanvas.MARGIN/2;
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, curY, width, curY);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, curY+1, width, curY+1);
        curY+=ContactCanvas.MARGIN/2;
        
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Other Names"))/2;
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.setFont(ContactCanvas.FONT_TITLE);
        g.drawString("Other Names", offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        g.setColor(ContactCanvas.WHITE);
        g.setFont(ContactCanvas.FONT_DESCRIPTION);
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, currentContact.getOtherNames()))/2;
        g.drawString(currentContact.getOtherNames(), offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        
         curY+=ContactCanvas.FONT_TITLE.getHeight()/2;
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, curY, width, curY);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, curY+1, width, curY+1);
        curY+=ContactCanvas.MARGIN/2;
        
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Location"))/2;
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.setFont(ContactCanvas.FONT_TITLE);
        g.drawString("Location", offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        g.setColor(ContactCanvas.WHITE);
        g.setFont(ContactCanvas.FONT_DESCRIPTION);
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, currentContact.getCity()))/2;
        g.drawString(currentContact.getCity(), offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();        
                
         curY+=ContactCanvas.MARGIN/2;
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, curY, width, curY);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, curY+1, width, curY+1);
        curY+=ContactCanvas.MARGIN/2;
        
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Gender"))/2;
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.setFont(ContactCanvas.FONT_TITLE);
        g.drawString("Gender", offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        g.setColor(ContactCanvas.WHITE);
        g.setFont(ContactCanvas.FONT_DESCRIPTION);
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, currentContact.getGender()))/2;
        g.drawString(currentContact.getGender(), offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        
                
         curY+=ContactCanvas.MARGIN/2;
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, curY, width, curY);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, curY+1, width, curY+1);
        curY+=ContactCanvas.MARGIN/2;
        
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "CC Pin"))/2;
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.setFont(ContactCanvas.FONT_TITLE);
        g.drawString("CC Pin", offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        g.setColor(ContactCanvas.WHITE);
        g.setFont(ContactCanvas.FONT_DESCRIPTION);
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, currentContact.getCcPin()))/2;
        g.drawString(currentContact.getCcPin(), offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        curY+=ContactCanvas.MARGIN/2;
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, curY, width, curY);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, curY+1, width, curY+1);
        curY+=ContactCanvas.MARGIN/2;
        
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "CUG Phone Number"))/2;
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.setFont(ContactCanvas.FONT_TITLE);
        g.drawString("CUG Phone Number", offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_TITLE.getHeight();
        g.setColor(ContactCanvas.WHITE);
        g.setFont(ContactCanvas.FONT_DESCRIPTION);
//        for (int i=0;i<2;i++)
//        {
//            offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, "07038123584"))/2;
//            g.drawString("07038123584", offSetX, curY, Graphics.TOP|Graphics.LEFT);
//            curY+= ContactCanvas.FONT_DESCRIPTION.getHeight();
//        }
        offSetX = ContactCanvas.MARGIN + ((width-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_DESCRIPTION, currentContact.getCugNumber()))/2;
        g.drawString(currentContact.getCugNumber(), offSetX, curY, Graphics.TOP|Graphics.LEFT);
        curY+= ContactCanvas.FONT_DESCRIPTION.getHeight();
        curY+=ContactCanvas.MARGIN/2;
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, curY, width, curY);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, curY+1, width, curY+1);
        curY+=ContactCanvas.MARGIN/2;
       
        contentHeight = curY - renderY;
        
        renderScrollBar(g, contentHeight);
    }

    public Command[] getCommands() {
        if (mode == SEARCHMODE )
          return new Command []{backCommand, saveCommand};
        else 
            return new Command []{backCommand, deleteCommand};
    }

    public void commandAction(Command c, Displayable d) {
        if (c==backCommand)
        {
            if(mode == CONTACTMODE)
                canvas.getContactMIDlet().getDisplay().setCurrent(canvas.getCustomForm(canvas.getSavedContacts(), CONTACTMODE));
            else if (mode == SEARCHMODE)
//                canvas.setCurrentView(parentView);
                canvas.getContactMIDlet().getDisplay().setCurrent(canvas.getCustomForm(canvas.getSearchContacts(), SEARCHMODE));
        }
        else if (c==saveCommand)
        {
            canvas.getSavedContacts().addElement(currentContact);            
            Alert infoAlert = new Alert("Status", "Contact Saved", null, AlertType.INFO);
            infoAlert.setTimeout(3000);   
            //canvas.setCurrentView(parentView);
//            canvas.getCustomForm(canvas.getSavedContacts()).append(new ContactDisplayCustomItem(currentContact, canvas.getWidth()));
            canvas.getContactMIDlet().getDisplay().setCurrent(infoAlert,canvas.getCustomForm(canvas.getSearchContacts(), SEARCHMODE));
            
        }
        else if (c==deleteCommand)
        {
            //ContactView.savedContacts.removeElement(currentContact);
            canvas.getSavedContacts().removeElement(currentContact);                       
            Alert infoAlert = new Alert("Status", "Contact Deleted", null, AlertType.INFO);
            infoAlert.setTimeout(3000);   
//            canvas.setCurrentView(parentView);
//            canvas.getContactMIDlet().getDisplay().setCurrent(infoAlert,canvas);
            canvas.getContactMIDlet().getDisplay().setCurrent(infoAlert,canvas.getCustomForm(canvas.getSavedContacts(), CONTACTMODE));
        }
    }
    
}
