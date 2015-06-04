/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import com.futurice.tantalum2.Workable;
import com.futurice.tantalum2.Worker;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import com.futurice.tantalum2.util.StringUtils;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

/**
 *
 * @author Oyebc
 */
public class SearchView extends View{
    
    private int selectedIndex=0;
    private Command backCommand = new Command("Contacts",Command.BACK , 2);
    private Command searchCommand = new Command ("Search", Command.SCREEN, 5);
    private Command editCommand = new Command ("Edit",Command.ITEM,0);
    private final Command about = new Command("About", Command.HELP, 1);
    private final String [] labels = {"Surname", "Other Names", "City", "Sex"};
    private TextBox tbox;
    private List tList;
    private Command tCommand;
    private String surname = "";
    private String otherNames ="";
    private String city="";
    private String sex= "Male";    
    private int visibleRows;
    private int onScreenStartIndex=0;
    private int onScreenStopIndex;
    private int searchBoxHeight = 9*ContactCanvas.MARGIN;
    private Image searchBuffer;
    private int contentHeight;
    
    public SearchView(final ContactCanvas canvas) {
        super(canvas);
    }

    public void keyPressed(int keyCode)   {
        if (keyCode == -1 || keyCode == Canvas.KEY_NUM2 || keyCode==Canvas.UP) 
            {
               selectedIndex--;
               if (selectedIndex < onScreenStartIndex)
               {
                   onScreenStartIndex = selectedIndex;
                   onScreenStopIndex = onScreenStartIndex+visibleRows-1;
                   setRenderY(renderY + searchBoxHeight);
               }
               if (selectedIndex<0)
               {
//                   selectedIndex=tempContacts.length-1;
                   selectedIndex=3;
                   onScreenStopIndex = selectedIndex;
                   onScreenStartIndex = onScreenStopIndex-visibleRows+1;
                   setRenderY(-1*searchBoxHeight *(onScreenStartIndex+1));
               }
               
               canvas.queueRepaint(); 
            }
        else if (keyCode == -2 || keyCode == Canvas.KEY_NUM8||keyCode==Canvas.DOWN){
        
                selectedIndex++;
                if (selectedIndex > onScreenStopIndex)
                {
                    onScreenStopIndex = selectedIndex;
                    onScreenStartIndex = onScreenStopIndex-visibleRows+1;
                    setRenderY(renderY - searchBoxHeight);
                }
//                if (selectedIndex>=tempContacts.length)
                if (selectedIndex>=4)
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
                    if(selectedIndex==0){

                        tbox = new TextBox("Surname",surname , 64,TextField.ANY );
                        tCommand = new Command("OK", Command.OK, 2);
                        tbox.addCommand(tCommand);
                        tbox.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                    }
                    else if(selectedIndex == 1)
                    {
                        tbox = new TextBox("Other Names",otherNames , 128,TextField.ANY );
                        tCommand = new Command("OK", Command.OK, 2);
                        tbox.addCommand(tCommand);
                        tbox.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tbox);

                    }
                    else if(selectedIndex == 2)
                    {
                        tbox = new TextBox("City",city , 64,TextField.ANY );
                        tCommand = new Command("OK", Command.OK, 2);
                        tbox.addCommand(tCommand);
                        tbox.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                    }
                    else if(selectedIndex==3)
                    {
                        tList = new List("Sex", List.IMPLICIT, new String[]{"Male", "Female"}, null);
                        tList.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tList);
                        
                    }
        }
        
    }
    
    public void pointerPressed(int x, int y)
    {
        if(x>= 2*ContactCanvas.MARGIN && x<= canvas.getWidth()-2*ContactCanvas.MARGIN)
        {
//            if((y-renderY)%searchBoxHeight>= 0 && (y-renderY)%searchBoxHeight<= 8*ContactCanvas.MARGIN )
            {
                selectedIndex = (y-renderY)/searchBoxHeight;
                canvas.queueRepaint();
            }
        }
        
    }
    
    public void pointerReleased(int x, int y)
    {
        if(selectedIndex == (y-renderY)/searchBoxHeight)
            {
               if(x>= 2*ContactCanvas.MARGIN && x<= canvas.getWidth()-2*ContactCanvas.MARGIN)
                    {
                        if((y-renderY)>= 4*ContactCanvas.MARGIN && (y-renderY)<= 8*ContactCanvas.MARGIN )
                        {
                            tbox = new TextBox("Surname",surname , 64,TextField.ANY );
                            tCommand = new Command("OK", Command.OK, 2);
                            tbox.addCommand(tCommand);
                            tbox.setCommandListener(this);
                            canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                        }
                    if((y-renderY)>= 4*ContactCanvas.MARGIN + searchBoxHeight && (y-renderY)<= 8*ContactCanvas.MARGIN+searchBoxHeight )
                        {
                            tbox = new TextBox("Other Names",otherNames , 128,TextField.ANY );
                            tCommand = new Command("OK", Command.OK, 2);
                            tbox.addCommand(tCommand);
                            tbox.setCommandListener(this);
                            canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                        }
                    if((y-renderY)>= 4*ContactCanvas.MARGIN + 2*searchBoxHeight && (y-renderY)<= 8*ContactCanvas.MARGIN+2*searchBoxHeight )
                        {
                            
                            tbox = new TextBox("City",city , 64,TextField.ANY );
                            tCommand = new Command("OK", Command.OK, 2);
                            tbox.addCommand(tCommand);
                            tbox.setCommandListener(this);
                            canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                        }
                    if((y-renderY)>= 4*ContactCanvas.MARGIN + 3*searchBoxHeight && (y-renderY)<= 8*ContactCanvas.MARGIN+3*searchBoxHeight )
                        {
                            tList = new List("Sex", List.IMPLICIT, new String[]{"Male", "Female"}, null);
                            tList.setCommandListener(this);
                            canvas.getContactMIDlet().getDisplay().setCurrent(tList);
                        }
                }
                
            }
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
        visibleRows = height/searchBoxHeight;
        onScreenStopIndex= onScreenStartIndex+visibleRows-1;
        g.setColor(ContactCanvas.GREEN);
        g.fillRect(0, 0, width, height);
        drawSearchBuffer();
        g.drawImage(searchBuffer, 0, this.renderY, Graphics.TOP|Graphics.LEFT);
        contentHeight = searchBuffer.getHeight();
        renderScrollBar(g, contentHeight); 
        System.out.println("height: "+ height);
        System.out.println("width: "+ width);
        System.out.println("renderY: "+ renderY);
        System.out.println("selectedIndex: "+selectedIndex);
    
    
    }
    
    private void drawSearchBuffer()  {
        searchBuffer= Image.createImage(canvas.getWidth(), searchBoxHeight*4);
        Graphics g = searchBuffer.getGraphics();
        int cursorpointer =0;
        g.setColor(ContactCanvas.GREEN);
        g.fillRect(0, 0, searchBuffer.getWidth(), searchBuffer.getHeight());
        g.setFont(ContactCanvas.FONT_DATE);
        String [] fields= {surname, otherNames, city, sex};
        
        for (int i=0;i<4;i++)
        {
            g.setColor(ContactCanvas.DARK_HILITE);
            g.drawLine(0, cursorpointer, canvas.getWidth(), cursorpointer);
            g.setColor(ContactCanvas.LIGHT_HILITE);
            g.drawLine(0, cursorpointer+1, canvas.getWidth(), cursorpointer+1);
            
            if(i==selectedIndex)
                {
                    g.setColor(ContactCanvas.DARK_HILITE);
                    g.fillRect(0, cursorpointer+1, canvas.getWidth(), searchBoxHeight);
                   // g.setColor(ContactCanvas.LIGHT_HILITE);
                    g.setColor(ContactCanvas.PINK);
                    g.fillRect((2*ContactCanvas.MARGIN)-3, cursorpointer+(4*ContactCanvas.MARGIN)-3, canvas.getWidth()-4*ContactCanvas.MARGIN+6, (4*ContactCanvas.MARGIN)+6);
                }
            g.setColor(ContactCanvas.WHITE);
//            g.fillRoundRect(ContactCanvas.MARGIN, cursorpointer+(3*height/32), width-2*ContactCanvas.MARGIN, height/8, height/16, height/16);
            g.drawString(labels[i], 2*ContactCanvas.MARGIN, cursorpointer+ContactCanvas.MARGIN, Graphics.TOP|Graphics.LEFT);
            g.fillRect(2*ContactCanvas.MARGIN, cursorpointer+(4*ContactCanvas.MARGIN), canvas.getWidth()-4*ContactCanvas.MARGIN, 4*ContactCanvas.MARGIN);            
            g.setColor(ContactCanvas.DARK_HILITE);
            g.drawString(StringUtils.truncate(fields[i], ContactCanvas.FONT_DATE, canvas.getWidth()-4*ContactCanvas.MARGIN), 5*ContactCanvas.MARGIN/2, cursorpointer+(5*ContactCanvas.MARGIN), Graphics.TOP|Graphics.LEFT);
                
            cursorpointer += searchBoxHeight;
        }
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(0, cursorpointer-2, canvas.getWidth(), cursorpointer-2);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawLine(0, cursorpointer-1, canvas.getWidth(), cursorpointer-1);
    }

    public Command[] getCommands() {
        return new Command []{backCommand, searchCommand, editCommand, about };
    }

    public void commandAction(Command c, Displayable d) {
        if (c==backCommand)
        {
         //   canvas.setCurrentView(canvas.getContactView());
            canvas.getContactMIDlet().getDisplay().setCurrent(canvas.getCustomForm(canvas.getSavedContacts(), ContactDetailsView.CONTACTMODE));
        }
        else if(c==searchCommand)
        {
            canvas.setCurrentView(canvas.getLoadingView());
            Worker.queue(new WorkableGUI());
        }
        else if (c==editCommand)
        {
            if(selectedIndex==0){

                        tbox = new TextBox("Surname",surname , 64,TextField.ANY );
                        tCommand = new Command("OK", Command.OK, 2);
                        tbox.addCommand(tCommand);
                        tbox.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                    }
                    else if(selectedIndex == 1)
                    {
                        tbox = new TextBox("Other Names",otherNames , 128,TextField.ANY );
                        tCommand = new Command("OK", Command.OK, 2);
                        tbox.addCommand(tCommand);
                        tbox.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tbox);

                    }
                    else if(selectedIndex == 2)
                    {
                        tbox = new TextBox("City",city , 64,TextField.ANY );
                        tCommand = new Command("OK", Command.OK, 2);
                        tbox.addCommand(tCommand);
                        tbox.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                    }
                    else if(selectedIndex==3)
                    {
                        tList = new List("Sex", List.IMPLICIT, new String[]{"Male", "Female"}, null);
                        tList.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tList);
                        
                    }
        }
        else if (c==tCommand)
        {
            if (selectedIndex==0)
            {
                //Display.getDisplay(cxMidlet).setCurrent(this);
                canvas.getContactMIDlet().getDisplay().setCurrent(canvas);
                surname =  tbox.getString();
                canvas.queueRepaint();
            }
            else if(selectedIndex == 1)
            {
                canvas.getContactMIDlet().getDisplay().setCurrent(canvas);
                otherNames =  tbox.getString();
                canvas.queueRepaint();
            }
            else if(selectedIndex == 2)
            {
                canvas.getContactMIDlet().getDisplay().setCurrent(canvas);
                city =  tbox.getString();
                canvas.queueRepaint();
            }
            else if(selectedIndex == 3)
            {
                
            }
        }
        else if (c==List.SELECT_COMMAND)
        {
            canvas.getContactMIDlet().getDisplay().setCurrent(canvas);
            sex = tList.getString(tList.getSelectedIndex());
            canvas.queueRepaint();
        }
        else if(c==about)
        {
            String aboutString = "CoopCUG as a word was derived from the words Cooperative and Closed User Group. It was designed as an on-net voice telecom platform that allows members who are subscribed to the COOpCUG network to make calls for any length of time, 24 hours a day and seven days a week for the whole  month after the payment of the prescribed monthly rental fee for the four GSM providers i.e. MTN, Airtel, Glo and Etisalat."+

                                "What this translates to is that if a member subscribes to CoopCUG, that member will pay a prescribed monthly fee, which is renewable every month, and this allows such member to make voice calls to other members on the CoopCUG platform without any charges as long as such calls terminate on the same network e.g. calls made from an MTN subscriber to another MTN subscriber both on the CoopCUG Service. When a subscriber makes a call from his network to another network e.g. from MTN to Airtel, there is a charge made on such calls. But these charges are usually lower than what one would ordinarily get were such calls made by those who are not subscribed to the CoopCUG."+

                                "A subscriber to the network is issued a CoopCUG PIN known as \"CC PIN\" which is a unique identification number only obtainable to the subscriber after the completion of the registration process. This enables the subscriber after the completion of the registration process. This enables the subscriber to store and share telephone numbers with other subscribers on the CoopCUG network, in the same way that a Blackberry subscriber has a BB PIN."+

                                "To subscribe, logon to www.coopcug.com and follow the instructions or visit any of our dealers/agents within your area. Payments for the CoopCUG service can be made to CoopCUG accounts at First Bank, Zenith Bank, Stanbic IBTC, Skye Bank and mobile payment through e-wallet accounts on MTN MObile Money, First Monie, Glo Cash, Teasly Mobile, Fortis Mobile e.t.c.";
            
            Alert aboutAlert = new Alert("About", aboutString, null, AlertType.INFO);
            aboutAlert.setTimeout(Alert.FOREVER);
            canvas.getContactMIDlet().getDisplay().setCurrent(aboutAlert, canvas);
        }
        
    }
        
    
    private final class WorkableGUI implements Workable,Runnable
    {
        boolean systemOk;
        
        public boolean work() {
            
            systemOk = HttpAPI.search(surname, otherNames, city, sex);
            return true;
        }

        public void run() {
            if (systemOk)
//                canvas.setCurrentView(new SearchDisplayView(canvas, canvas.getSearchView()));
                canvas.getContactMIDlet().getDisplay().setCurrent(canvas.getCustomForm(canvas.getSearchContactfromWeb(), ContactDetailsView.SEARCHMODE));
            else
            {
                Alert loginAlert = new Alert("Error", "Check your Network Settings", null, AlertType.ERROR);
                loginAlert.setTimeout(3000);
                canvas.setCurrentView(canvas.getSearchView());
                canvas.getContactMIDlet().getDisplay().setCurrent(loginAlert,canvas);
            }
            canvas.stopLoading();
        }
        
    }
}
