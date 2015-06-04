/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import com.futurice.tantalum2.util.StringUtils;
import java.util.Vector;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * display
 * @author Oyebc
 */
public class ContactDisplayCustomItem extends CustomItem{
    
     private static final Font font1 = Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
     private static final Font font2 = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);
     private final int width;
     private final int preferredWidth;
     final int height;
     private static final int H_SPACE = 3;
     private static final int V_SPACE = 2;
     public static ContactDisplayCustomItem highlightedItem = null;
     private final Vector titlelines, companylines;
     private final Contact currentContact;
     private ContactCanvas canvas;
     
    
    public ContactDisplayCustomItem(final Contact contact, final int preferredWidth )
    {
        super(null);
        titlelines = StringUtils.splitToLines(contact.getSurname()+" "+contact.getOtherNames(), font1, preferredWidth-H_SPACE);
        companylines = StringUtils.splitToLines(contact.getCity(), font2, preferredWidth-60-H_SPACE);
        final int tempHeight = font1.getHeight() *titlelines.size() + font2.getHeight()*companylines.size();
//        System.out.println(tempHeight);
//        System.out.println((2*tempHeight/5)+ percentFont.getHeight());
        this.height = tempHeight  + 3 * V_SPACE;        
        currentContact = contact;
        //this.image = null;
//        this.line1 = job.getJobTitle();
//        this.line2 = job.getCompanyName();
//        percentFit=job.getPercentFit();
        this.width = preferredWidth;
        this.preferredWidth = preferredWidth;
    }

     public ContactDisplayCustomItem(final Contact contact, final int preferredWidth , final ContactCanvas canvas)
    {
        super(null);
        titlelines = StringUtils.splitToLines(contact.getSurname()+" "+contact.getOtherNames(), font1, preferredWidth-H_SPACE);
        companylines = StringUtils.splitToLines(contact.getCity(), font2, preferredWidth-60-H_SPACE);
        final int tempHeight = font1.getHeight() *titlelines.size() + font2.getHeight()*companylines.size();
//        System.out.println(tempHeight);
//        System.out.println((2*tempHeight/5)+ percentFont.getHeight());
        this.height = tempHeight  + 3 * V_SPACE;        
        currentContact = contact;
        //this.image = null;
//        this.line1 = job.getJobTitle();
//        this.line2 = job.getCompanyName();
//        percentFit=job.getPercentFit();
        this.width = preferredWidth;
        this.preferredWidth = preferredWidth;
        this.canvas = canvas;
    }
    protected int getMinContentHeight() {
        return height;
    }

    protected int getMinContentWidth() {
        return width;
    }

    protected int getPrefContentHeight(int width) {
        return height;
    }

    protected int getPrefContentWidth(int height) {
        return preferredWidth;
    }

    public Contact getCurrentContact() {
        return currentContact;
    }
    
    private boolean isHighlighted() {
        return this.equals(ContactDisplayCustomItem.highlightedItem);
    }
    
    protected void paint(Graphics g, int w, int h) {
         
        final boolean highlight = isHighlighted();

      //  g.drawImage(highlight ? getFocusedBackgroundImage(w, h) : getBackgroundImage(w, h), 0, 0, Graphics.TOP | Graphics.LEFT);

        if(highlight)
            {
//                g.setColor(0x00cfeff3);
                g.setColor(ContactCanvas.DARK_HILITE);
            }
        else{
//                g.setColor(0x00ffffff);
            g.setColor(ContactCanvas.GREEN);
        }
        g.fillRect(0, 0, width, height);
        g.setColor(ContactCanvas.DARK_HILITE);
//        g.setColor(ContactCanvas.LIGHT_HILITE);
        g.setColor(ContactCanvas.WHITE);
        int x = H_SPACE;
        int y = (height-(font1.getHeight() *titlelines.size()) - (font2.getHeight()*companylines.size()))/2;
        g.setFont(font1);
        int len = titlelines.size();
          for(int i=0;i<len;i++)
        {   
           // offsetX = ContactCanvas.MARGIN + ((titleBuffer.getWidth()-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, (String)titleLines.elementAt(i)))/2;
            g.drawString((String)titlelines.elementAt(i), x, y , Graphics.TOP|Graphics.LEFT);
            y += font1.getHeight();
        }
        //g.drawString(this.line1, x, y, Graphics.TOP | Graphics.LEFT);
        
        g.setFont(font2);
//        int newX = width-60;          
        g.drawString(currentContact.getGender(), width-64, y, Graphics.TOP|Graphics.LEFT);
        
        len = companylines.size();
          for(int i=0;i<len;i++)
        {   
           // offsetX = ContactCanvas.MARGIN + ((titleBuffer.getWidth()-(2*ContactCanvas.MARGIN))-StringUtils.fastStringWidth(ContactCanvas.FONT_TITLE, (String)titleLines.elementAt(i)))/2;
            g.drawString((String)companylines.elementAt(i), H_SPACE, y , Graphics.TOP|Graphics.LEFT);
            y += font1.getHeight();
        }
          
          
          g.setColor(ContactCanvas.DARK_HILITE);
          g.drawLine(0, height-2, width, height-2);
          g.setColor(ContactCanvas.LIGHT_HILITE);
          g.drawLine(0, height-1, width, height-1);
    }
    
    protected boolean traverse(int dir, int viewportWidth, int viewportHeight, int[] visRect_inout) {
        ContactDisplayCustomItem.highlightedItem = this;

        return false;
    }

    protected void traverseOut() {
        ContactDisplayCustomItem.highlightedItem = null;
    }

    protected void pointerPressed(int x, int y) {
//        super.pointerPressed(x, y);
        if(this.isHighlighted())
        {
            
        }
    }

    protected void pointerReleased(int x, int y) {
//        super.pointerReleased(x, y);
        System.out.println("Status: Pointer Released");
        if(this.isHighlighted())
        {
            canvas.viewContactDetails();         
            System.out.println("Status: Pointer Released on Highlighted");
        }
    }
    
   
}
