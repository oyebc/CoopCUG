/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import com.futurice.tantalum2.Workable;
import com.futurice.tantalum2.Worker;
import com.futurice.tantalum2.util.StringUtils;
import java.io.IOException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author Oyebc
 */
public class LoginView extends View{
    
    int selectedIndex =0;
    private Command tCommand;
    private TextBox tbox;
    private String ccPin="";
    private Image icon ;
    
    
    public LoginView(final ContactCanvas canvas){
        super(canvas);
        try {
            if(canvas.getWidth()<canvas.getHeight())
                icon = resizeImage(Image.createImage("/org/contact/images/icon.png"),canvas.getWidth()/2,canvas.getWidth()/2);
            else
                icon = resizeImage(Image.createImage("/org/contact/images/icon.png"),canvas.getHeight()/2,canvas.getHeight()/2);
//            icon =Image.createImage("/org/contact/images/icon.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void keyPressed(int keyCode) {
        if (keyCode == -1 || keyCode == Canvas.KEY_NUM2 || keyCode==Canvas.UP) 
            {
               selectedIndex--;
               
               if (selectedIndex<0)
               {
                   selectedIndex=1;
                   
               }
               
               canvas.queueRepaint(); 
            }
        else if (keyCode == -2 || keyCode == Canvas.KEY_NUM8||keyCode==Canvas.DOWN){
        
                selectedIndex++;
               
                if (selectedIndex>=2)
                {
                    selectedIndex=0;                    
                }
                canvas.queueRepaint();
        }
         else if (keyCode == -5 || keyCode == Canvas.KEY_NUM5 || keyCode ==-8) 
        {
                    if(selectedIndex==0){

                        tbox = new TextBox("CCPin",ccPin , 64,TextField.ANY );
                        tCommand = new Command("OK", Command.OK, 2);
                        tbox.addCommand(tCommand);
                        tbox.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                    }
                    else if (selectedIndex==1)
                    {
                        if (!ccPin.equals(""))
                            {
                                canvas.setCurrentView(canvas.getLoadingView());
                                Worker.queue(new WorkableGUI());
                            }
                    }
        }
        
    } 
    
    public void pointerPressed(int x, int y){
    
        if (y>= (7*canvas.getHeight()/10) && y<=(7*canvas.getHeight()/10)+4*ContactCanvas.MARGIN)
                {
                    if (x>=2*ContactCanvas.MARGIN && x<=canvas.getWidth()-2*ContactCanvas.MARGIN)
                    {
                        selectedIndex = 0;
                        canvas.queueRepaint();
                    }
                }
        else if ( y>= ((9*canvas.getHeight()/10)-ContactCanvas.MARGIN/2) && y<=((9*canvas.getHeight()/10)-ContactCanvas.MARGIN/2)+ 3*ContactCanvas.MARGIN)
                {
                    if( x>=(canvas.getWidth()-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login"))/2 -ContactCanvas.MARGIN/2 && x<=((canvas.getWidth()-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login"))/2 -ContactCanvas.MARGIN/2)+(StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login") + ContactCanvas.MARGIN))
                        {
                        selectedIndex = 1;
                        canvas.queueRepaint();
                    }
                }
    }
    
    public void pointerReleased(int x, int y){
        
        if (selectedIndex==0 )
        {
            if (y>= (7*canvas.getHeight()/10) && y<=(7*canvas.getHeight()/10)+4*ContactCanvas.MARGIN)
                {
                    if (x>=2*ContactCanvas.MARGIN && x<=canvas.getWidth()-2*ContactCanvas.MARGIN)
                    {
                        tbox = new TextBox("CCPin",ccPin , 64,TextField.ANY );
                        tCommand = new Command("OK", Command.OK, 2);
                        tbox.addCommand(tCommand);
                        tbox.setCommandListener(this);
                        canvas.getContactMIDlet().getDisplay().setCurrent(tbox);
                   
                    }
                }
        }
        else if(selectedIndex==1)
        {
            if ( y>= ((9*canvas.getHeight()/10)-ContactCanvas.MARGIN/2) && y<=((9*canvas.getHeight()/10)-ContactCanvas.MARGIN/2)+ 3*ContactCanvas.MARGIN)
                {
                    if( x>=(canvas.getWidth()-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login"))/2 -ContactCanvas.MARGIN/2 && x<=((canvas.getWidth()-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login"))/2 -ContactCanvas.MARGIN/2)+(StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login") + ContactCanvas.MARGIN))
                        {
                            if (!ccPin.equals(""))
                            {
                                canvas.setCurrentView(canvas.getLoadingView());
                                Worker.queue(new WorkableGUI());
                            }
                        }
                }
        }
        
    }
    
    public void render(Graphics g, int width, int height) {
        
         canvas.setFullScreenMode(true);
         int cursorpointer =0;
         g.setColor(ContactCanvas.GREEN);
         g.fillRect(0, 0, width, height);
         g.drawImage(icon, (width-icon.getWidth())/2, 2*ContactCanvas.MARGIN, Graphics.TOP|Graphics.LEFT);
         
         cursorpointer=7*height/10;
         if(selectedIndex==0)
         {
             g.setColor(ContactCanvas.PINK);
             g.fillRect(3*ContactCanvas.MARGIN/2, cursorpointer-ContactCanvas.MARGIN/2, width-3*ContactCanvas.MARGIN,ContactCanvas.MARGIN + 4*ContactCanvas.MARGIN);
         } 
         
         g.setColor(ContactCanvas.WHITE);
         g.fillRect(2*ContactCanvas.MARGIN, cursorpointer, width-4*ContactCanvas.MARGIN, 4*ContactCanvas.MARGIN);
         g.setFont(ContactCanvas.FONT_DATE);
         g.drawString("CC Pin", 2*ContactCanvas.MARGIN, cursorpointer-3*ContactCanvas.MARGIN, Graphics.TOP|Graphics.LEFT);
         g.setColor(ContactCanvas.DARK_HILITE);
         g.drawString(ccPin, 5*ContactCanvas.MARGIN/2, cursorpointer+ContactCanvas.MARGIN, Graphics.TOP|Graphics.LEFT);
         
         cursorpointer=9*height/10;
         if (selectedIndex==1)
         {
             g.setColor(ContactCanvas.PINK);
             g.fillRect((width-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login"))/2 -ContactCanvas.MARGIN/2, cursorpointer-ContactCanvas.MARGIN/2, StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login") + ContactCanvas.MARGIN, 3*ContactCanvas.MARGIN);
         }
         
         g.setColor(ContactCanvas.WHITE);
         g.setFont(ContactCanvas.FONT_TITLE);
         g.drawString("Login", (width-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, "Login"))/2, cursorpointer, Graphics.TOP|Graphics.LEFT);
         
        
    }

    public Command[] getCommands() {
        return new Command []{};
    }

    public void commandAction(Command cmnd, Displayable dsplbl) {
        
        if (cmnd==tCommand)
         if(selectedIndex == 0)
            {
                canvas.getContactMIDlet().getDisplay().setCurrent(canvas);
                ccPin =  tbox.getString();
                canvas.queueRepaint();
            }
         

    }
    
    private Image resizeImage(Image src, int newWidth, int newHeight) {

        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        Image tmp = Image.createImage(srcWidth, srcHeight);
        Graphics g = tmp.getGraphics();
        int ratio = (srcWidth << 16) / newWidth;
        int pos = ratio / 2;
        for (int x = 0; x < newWidth; x++) {
            g.setClip(x, 0, 1, srcHeight);
            g.drawImage(src, x - (pos >> 16), 0, 20);
            pos += ratio;
        }

        Image resizedImage = Image.createImage(newWidth, newHeight);
        g = resizedImage.getGraphics();
        ratio = (srcHeight << 16) / newHeight;
        pos = ratio / 2;
        for (int y = 0; y < newHeight; y++) {
            g.setClip(0, y, newWidth, 1);
            g.drawImage(tmp, 0, y - (pos >> 16), 20);
            pos += ratio;
        }

        return resizedImage;
    }
    
    private final class WorkableGUI implements Workable, Runnable
    {

        boolean isRegistered=false;        
        Contact registeredContact;
        String oldusername;
        
        public boolean work() {

            registeredContact = HttpAPI.authenticate(ccPin);
            
                    if (registeredContact!= null)
                        isRegistered=true;                    
            System.out.println("isRegistered: "+ isRegistered);            
            return true;
        }

        public void run() {

                  if (isRegistered) {
                      
                                    Alert loginAlert = new Alert("Login", "Welcome "+registeredContact.getSurname()+" "+registeredContact.getOtherNames(), null, AlertType.ERROR);
                                    loginAlert.setTimeout(3000);   
//                                    canvas.setCurrentView(canvas.getContactView());
                                    canvas.getContactMIDlet().getDisplay().setCurrent(loginAlert,canvas.getCustomForm(canvas.getSavedContacts(), ContactDetailsView.CONTACTMODE));
                      
                        } else if (!isRegistered) {
                                    Alert loginAlert = new Alert("Login Failed", " Incorrect Pin || Check your Network Settings", null, AlertType.ERROR);
                                    loginAlert.setTimeout(3000);
                                    canvas.setCurrentView(canvas.getLoginView());
                                    canvas.getContactMIDlet().getDisplay().setCurrent(loginAlert,canvas);
                        } else {
                             Alert loginAlert = new Alert("Login Failed", " Incorrect Pin || Check your Network Settings", null, AlertType.ERROR);
                                    loginAlert.setTimeout(3000);
                                    canvas.setCurrentView(canvas.getLoginView());
                                    canvas.getContactMIDlet().getDisplay().setCurrent(loginAlert,canvas);
                        }
                                  canvas.stopLoading();
        }
        
    }
}
