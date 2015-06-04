/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Oyebc
 */
public class LoadingView extends View{
    
    //public Image offscreenBuffer;
    private final int w,h;
    private Graphics g;
    public int count =0;
    String ellipsis ="";
    private Timer timer = new Timer();
    
    public LoadingView(final ContactCanvas canvas)
    { 
        super(canvas);
        canvas.setFullScreenMode(true);
        w = canvas.getWidth();h =canvas.getHeight();
        
        
        drawOnBuffer();
        timer.scheduleAtFixedRate(new Loading(), 2000, 1000);
    }

    public void render(Graphics g, int width, int height) {
          //g.setColor(0xffffff);
        g.setColor(ContactCanvas.DARK_HILITE);
        g.fillRect(0, 0, w, h);
       // g.setColor(216,24,75);
       // g.setColor(29,60,66);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.drawString("Loading" +ellipsis, w/2, h/2, Graphics.HCENTER|Graphics.TOP);
        g.setColor(0);
    }
    
    private void drawOnBuffer(){

      
    }

    public Command[] getCommands() {
        return new Command[]{};
    }
    
    public void timerElapsed(){

        count++;
        count%=4;
        ellipsis ="";
        for(int i=0;i<count;i++){

            ellipsis+=".";
        }
    }
    
    public void delete()
    {
        timer.cancel();
    }

    public void commandAction(Command c, Displayable d) {
    }
    private class Loading extends TimerTask{

        public void run() {

            timerElapsed();
            drawOnBuffer();
            canvas.queueRepaint();
          //  System.out.println("Loading painting");
            //canvas.serviceRepaints();
        }

    }
    
    
    
}
