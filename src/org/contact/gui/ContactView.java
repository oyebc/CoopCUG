/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import com.futurice.tantalum2.Workable;
import com.futurice.tantalum2.Worker;
import com.futurice.tantalum2.rms.RMSUtils;
import com.futurice.tantalum2.util.StringUtils;
import com.futurice.tantalum2.util.UniqueItemVector;
import javax.microedition.lcdui.*;

/**
 *
 * @author Oyebc
 */
public class ContactView extends View {

    private final Command exitCommand = new Command("Exit", Command.EXIT, 5);
    private final Command viewChildrenCommand = new Command("Details", Command.ITEM, 0);
    private final Command searchCommand = new Command ("Find Contacts", Command.SCREEN, 1);
//    String [] tempContacts = {"Cyhi da Prince","2Chainz", "Kanye West","Big Sean","John Legend", "Teyana Taylor", "Kid Cudi", "Pusha T", "Motherfucker Jones", "Keyser Soze", "Marsha Ambrosius"};
    private Image contactBuffer;
    private int selectedIndex=0;
    private int onScreenStartIndex=0;
    //private int visibleRows = canvas.getHeight()/ContactCanvas.boxHeight;
    private int visibleRows;
//    private int onScreenStopIndex = onScreenStartIndex+visibleRows-1;
    private int onScreenStopIndex;
    final int stringX=ContactCanvas.boxHeight/2  + 2*ContactCanvas.MARGIN;
    private int contentHeight;
    public static UniqueItemVector savedContacts;
    private Contact currentContact;
    
    public ContactView(final ContactCanvas canvas)  {
        super(canvas);
        
        String savedJSON = "";
        savedJSON = RMSUtils.readString("savedJSON");
        if (!savedJSON.equals(""))
        {
            savedContacts = Response.fromSavedJSON(savedJSON);
        }
        else
            savedContacts = new UniqueItemVector();
        
//        System.out.println("CanvasHeight: "+ canvas.getHeight());
//        System.out.println("BoxHeight: "+ ContactCanvas.boxHeight);
//        System.out.println("Visible Rows: "+visibleRows);
    }
    
    public void keyPressed(int keyCode)  {
        
        if (keyCode == -1 || keyCode == Canvas.KEY_NUM2 || keyCode==Canvas.UP) 
            {
               selectedIndex--;
               if (selectedIndex < onScreenStartIndex)
               {
                   onScreenStartIndex = selectedIndex;
                   onScreenStopIndex = onScreenStartIndex+visibleRows-1;
                   setRenderY(renderY + ContactCanvas.boxHeight);
               }
               if (selectedIndex<0)
               {
//                   selectedIndex=tempContacts.length-1;
                   selectedIndex=savedContacts.size()-1;
                   onScreenStopIndex = selectedIndex;
                   onScreenStartIndex = onScreenStopIndex-visibleRows+1;
                   setRenderY(-1*ContactCanvas.boxHeight *(onScreenStartIndex+1));
               }
               
               canvas.queueRepaint(); 
            }
        else if (keyCode == -2 || keyCode == Canvas.KEY_NUM8||keyCode==Canvas.DOWN){
        
                selectedIndex++;
                if (selectedIndex > onScreenStopIndex)
                {
                    onScreenStopIndex = selectedIndex;
                    onScreenStartIndex = onScreenStopIndex-visibleRows+1;
                    setRenderY(renderY - ContactCanvas.boxHeight);
                }
//                if (selectedIndex>=tempContacts.length)
                if (selectedIndex>=savedContacts.size())
                {
                    selectedIndex=0;
                    onScreenStartIndex = selectedIndex;
                    onScreenStopIndex = visibleRows-1;
                    setRenderY(0);
                }
                canvas.queueRepaint();
        }
        else if (keyCode == -5 || keyCode == Canvas.KEY_NUM5 || keyCode ==-8) 
        {
//            canvas.setCurrentView(new ContactDetailsView(canvas, con, this));
            if (!savedContacts.isEmpty())
                canvas.setCurrentView(new ContactDetailsView(canvas, currentContact, this, ContactDetailsView.CONTACTMODE));
        }
        System.out.println("startIndex: "+onScreenStartIndex);
        System.out.println("stopIndex: "+onScreenStopIndex);
        System.out.println("selectedIndex: "+selectedIndex);
    }
    
    public void render(Graphics g, int width, int height) {
        canvas.setFullScreenMode(false);
        int contactOffset = 0;      
        if (contentHeight < canvas.getHeight()-contactOffset) {
            this.renderY = contactOffset;
        } else if (this.renderY < -contentHeight + canvas.getHeight()+contactOffset) {
            this.renderY = -contentHeight + canvas.getHeight();
        } else if (this.renderY > contactOffset) {
            this.renderY = contactOffset;
        } 
            g.setColor(ContactCanvas.GREEN);
            g.fillRect(0, 0, width, height);
            if(!savedContacts.isEmpty())
            {
                    visibleRows = height/ContactCanvas.boxHeight;
                    onScreenStopIndex= onScreenStartIndex+visibleRows-1;
                    drawContactBuffer();
                    g.drawImage(contactBuffer, 0, this.renderY, Graphics.TOP|Graphics.LEFT);
                    System.out.println("Height: "+height);

                    contentHeight = contactBuffer.getHeight();   
                    renderScrollBar(g, contentHeight);
        //            renderScrollBar(g, contentHeight, visibleRows*ContactCanvas.boxHeight);
                    //rendercontactScroll(g, contentHeight);
                System.out.println("CanvasHeight: "+ canvas.getHeight());
                System.out.println("BoxHeight: "+ ContactCanvas.boxHeight);
                System.out.println("Visible Rows: "+visibleRows);
            }
            else                
                {   g.setColor(ContactCanvas.WHITE);
                    g.setFont(ContactCanvas.FONT_DESCRIPTION);
                    g.drawString("No Stored Contact", ContactCanvas.MARGIN, ContactCanvas.MARGIN, Graphics.TOP|Graphics.LEFT);
                }
    }

    public Command[] getCommands() {
        return new Command[]{searchCommand, exitCommand, viewChildrenCommand};
    }

    public void commandAction(Command c, Displayable d) {

        if (c==viewChildrenCommand)
        {
            if (!savedContacts.isEmpty())
                canvas.setCurrentView(new ContactDetailsView(canvas, currentContact, this, ContactDetailsView.CONTACTMODE));
        }
        else if (c == exitCommand)
        {       
            Worker.queue(new Workable() {

                public boolean work() {
                    Contact []contactArray= new Contact[ContactView.savedContacts.size()];
                    for (int i=0;i< ContactView.savedContacts.size();i++)
                    {
                        contactArray[i]=(Contact)ContactView.savedContacts.elementAt(i);
                    }
                    RMSUtils.write("savedJSON", Response.toJSON(contactArray));

                    return false;
                }
            });
            canvas.getContactMIDlet().exitMIDlet(false);
        }
        else if (c==searchCommand)
        {
            canvas.setCurrentView(canvas.getSearchView());
        }
    }
    
    public final void drawContactBuffer()   {
        
        contactBuffer = Image.createImage(canvas.getWidth(), savedContacts.size()*ContactCanvas.boxHeight);
        Graphics g = contactBuffer.getGraphics();
        g.setColor(ContactCanvas.GREEN);
        g.fillRect(0, 0, contactBuffer.getWidth(), contactBuffer.getHeight());
        int cursorpointer = 0;
        
        for (int i=0;i<savedContacts.size();i++)
        {
            g.setColor(ContactCanvas.DARK_HILITE);
            g.drawLine(0, cursorpointer, contactBuffer.getWidth(), cursorpointer);
            g.setColor(ContactCanvas.LIGHT_HILITE);
            g.drawLine(0, cursorpointer+1, contactBuffer.getWidth(), cursorpointer+1);
            
            if(i==selectedIndex)
                {
                    g.setColor(ContactCanvas.DARK_HILITE);
                    g.fillRect(0, cursorpointer+1, contactBuffer.getWidth(), ContactCanvas.boxHeight-1);
                    g.setColor(ContactCanvas.LIGHT_HILITE);
                }
            g.setFont(ContactCanvas.FONT_DESCRIPTION);
//            g.drawString(StringUtils.truncate(tempContacts[i],ContactCanvas.FONT_DESCRIPTION,contactBuffer.getWidth()-stringX-ContactCanvas.MARGIN),stringX , cursorpointer + ContactCanvas.MARGIN, Graphics.TOP|Graphics.LEFT);
            currentContact = (Contact)savedContacts.elementAt(i);
            g.drawString(StringUtils.truncate(currentContact.getSurname()+" "+currentContact.getOtherNames(),ContactCanvas.FONT_DESCRIPTION,contactBuffer.getWidth()-stringX-ContactCanvas.MARGIN),stringX , cursorpointer + ContactCanvas.MARGIN, Graphics.TOP|Graphics.LEFT);
            
            cursorpointer += ContactCanvas.boxHeight;
        }
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, cursorpointer-2, contactBuffer.getWidth(), cursorpointer-2);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, cursorpointer-1, contactBuffer.getWidth(), cursorpointer-1);
    }
    
}
