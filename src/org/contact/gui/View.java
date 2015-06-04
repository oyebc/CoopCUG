package org.contact.gui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Graphics;

/**
 * Abstract View class
 * @author ssaa
 */
public abstract class View implements CommandListener {
    public static final int SCROLL_BAR_WIDTH = 4;
    public static final int SCROLL_BAR_COLOR = 0x44000000;
    protected final ContactCanvas canvas;
    protected int renderY;

    public View(final ContactCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Renders the view using the Graphics object
     * @param g
     */
    public abstract void render(Graphics g, int width, int height);

    /**
     * Returns array of Commands related to this view
     * @return Command[]
     */
    public abstract Command[] getCommands();

    public int getRenderY() {
        return renderY;
    }

    public void setRenderY(final int renderY) {
        this.renderY = renderY;
    }
    
    public void keyPressed(int keyCode){}
    
    public void pointerPressed(int x, int y){}
    
    public void pointerReleased(int x, int y){}
    
    public void renderScrollBar(final Graphics g, final int contentHeight) {
        final int visibleAreaHeight = canvas.getHeight();

        if (contentHeight < visibleAreaHeight) {
            //no need for a scrollbar
            return;
        }

        //fill background with transparent color
       // g.setColor(SCROLL_BAR_COLOR);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.fillRect(canvas.getWidth() - SCROLL_BAR_WIDTH, 0, SCROLL_BAR_WIDTH, canvas.getHeight());

        final int barY = -renderY * canvas.getHeight() / contentHeight;
        final int barHeight = Math.max(2, canvas.getHeight() * canvas.getHeight() / contentHeight);

        //fill bar
        //g.setColor(RSSReader.COLOR_HIGHLIGHTED_BACKGROUND);
        g.setColor(ContactCanvas.GREEN);
        g.fillRect(canvas.getWidth() - SCROLL_BAR_WIDTH, barY, SCROLL_BAR_WIDTH, barHeight);
        g.setColor(ContactCanvas.DARK_HILITE);
        g.drawLine(canvas.getWidth()- SCROLL_BAR_WIDTH, 0, canvas.getWidth()- SCROLL_BAR_WIDTH, canvas.getHeight());
    }

    public void renderScrollBar(final Graphics g, final int contentHeight, final int visibleAreaHeight) {
        //final int visibleAreaHeight = canvas.getHeight();

//        Log.l.log("contentHeight: ", String.valueOf(contentHeight));
//        Log.l.log("visibleAreaHeight: ", String.valueOf(visibleAreaHeight));
        if (contentHeight <= visibleAreaHeight) {
            //no need for a scrollbar
            return;
        }

        //fill background with transparent color
        //g.setColor(SCROLL_BAR_COLOR);
        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.fillRect(canvas.getWidth() - SCROLL_BAR_WIDTH, 0, SCROLL_BAR_WIDTH, visibleAreaHeight);

     //   final int barY = -renderY * canvas.getHeight() / contentHeight;
        final int barY = -renderY * visibleAreaHeight / contentHeight;
    //    final int barHeight = Math.max(2, canvas.getHeight() * canvas.getHeight() / contentHeight);
        final int barHeight = Math.max(2, visibleAreaHeight * visibleAreaHeight / contentHeight);

        //fill bar
        //g.setColor(RSSReader.COLOR_HIGHLIGHTED_BACKGROUND);
        g.setColor(ContactCanvas.DARK_HILITE);
        //g.fillRect(canvas.getWidth() - SCROLL_BAR_WIDTH, barY, SCROLL_BAR_WIDTH, barHeight);
        g.fillRect(canvas.getWidth() - SCROLL_BAR_WIDTH, barY, SCROLL_BAR_WIDTH, barHeight);
        
        g.setColor(ContactCanvas.GREEN);
        g.drawLine(0, 0, 0, visibleAreaHeight);
        g.drawLine(canvas.getWidth()- SCROLL_BAR_WIDTH, 0, canvas.getWidth()-SCROLL_BAR_WIDTH, visibleAreaHeight);
    }
}