/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

import com.futurice.tantalum2.Workable;
import com.futurice.tantalum2.Worker;
import com.futurice.tantalum2.rms.RMSUtils;
import com.futurice.tantalum2.util.UniqueItemVector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Oyebc
 */
public class ContactCanvas extends Canvas implements CommandListener {
    
    public static final int DARK_HILITE =0x000f383e;
    public static final int GREEN = 0x001d6c78;
    public static final int WHITE=0x00ffffff;
    public static final int LIGHT_HILITE=0x00cfeff3;
    public static final int PINK =0x00bb0047;
    public static final Font FONT_TITLE = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
    public static final Font FONT_DESCRIPTION = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    public static final Font FONT_DATE = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
    public static final int MARGIN = FONT_TITLE.getHeight() / 2;
    public static final int boxHeight =FONT_TITLE.getHeight()+2*MARGIN;
    private int mode;
    private static volatile boolean repaintIsCurrentlyQueued = false;
    private ContactSearch contactMIDlet;
    private View currentView;
    private ContactView contactView;
    private SearchView searchView;
    private LoginView loginView;
    private LoadingView loadingView;
    private UniqueItemVector savedContacts;
    private UniqueItemVector searchContacts;
    private final Command exitCommand = new Command("Exit", Command.EXIT, 5);
    private final Command viewChildrenCommand = new Command("Details", Command.ITEM, 0);
    private final Command searchCommand = new Command ("Find Contacts", Command.SCREEN, 1);
    private final Command backCommand = new Command("Back", Command.BACK, 1);
    private final Command about = new Command("About", Command.HELP, 1);
//    private Form contactForm;
    
    
    public ContactCanvas(ContactSearch csearch)
    {
        
        setFullScreenMode(false);
        contactMIDlet=csearch;
//        setCurrentView(getContactView());
//        setCurrentView(getSearchView());
        setCurrentView(getLoginView());
    }

     protected void keyPressed(int keyCode){
    
        currentView.keyPressed(keyCode);
   
    }
   
    protected void pointerPressed(int x, int y) {
         currentView.pointerPressed(x, y);
    }
    
    protected void pointerReleased(int x, int y)
    {
        currentView.pointerReleased(x, y);
    }
    
    protected void paint(Graphics g) {
        repaintIsCurrentlyQueued = false;
        
        currentView.render(g, getWidth(), getHeight());
    }
    
    public final ContactView getContactView() {
        if (contactView == null)
            contactView = new ContactView(this);
       return this.contactView;
    }
    
    public final Form getCustomForm(final UniqueItemVector contacts, int viewMode){
      
        mode = viewMode;
//        if(contactForm ==null){
            Form contactForm;
            if(viewMode==ContactDetailsView.CONTACTMODE)
                contactForm = new Form("Saved Contact");
            else
                contactForm = new Form("Search Results");
            for (int i=0;i<contacts.size();i++)
            {
                contactForm.append(new ContactDisplayCustomItem((Contact)contacts.elementAt(i), this.getWidth(), this));
            }
            if(contacts.isEmpty()&& viewMode==ContactDetailsView.CONTACTMODE)
                contactForm.append("No Saved Contact");
            if(contacts.isEmpty()&& viewMode==ContactDetailsView.SEARCHMODE)
                contactForm.append("No Matching Contact");
            
            if(mode==ContactDetailsView.CONTACTMODE)
            {
                    contactForm.addCommand(exitCommand);
                    if(!contacts.isEmpty())
                        contactForm.addCommand(viewChildrenCommand);
                    contactForm.addCommand(searchCommand);
                    contactForm.addCommand(about);
            }
            else if(mode == ContactDetailsView.SEARCHMODE)
                {
                    contactForm.addCommand(exitCommand);
                    contactForm.addCommand(backCommand);
                    contactForm.addCommand(about);
                    if(!contacts.isEmpty())
                        contactForm.addCommand(viewChildrenCommand);
//                    contactForm.addCommand(searchCommand);
            }
            contactForm.setCommandListener(this);
//       }
        
        return contactForm;
    }
    
     public void queueRepaint() {
        if (!repaintIsCurrentlyQueued) {
            repaintIsCurrentlyQueued = true;
            repaint();
        }
    }
      public void setCurrentView(final View nextView) {
        if (this.currentView != null) {
            for (int i = 0; i < this.currentView.getCommands().length; i++) {
                removeCommand(this.currentView.getCommands()[i]);
            }
        }
        this.currentView = nextView;
        for (int i = 0; i < currentView.getCommands().length; i++) {
            addCommand(currentView.getCommands()[i]);
        }
        setCommandListener(currentView);
        queueRepaint();
    }

      public ContactSearch getContactMIDlet()
      {
          return contactMIDlet;
      }

     public SearchView getSearchView() {
         
         if (searchView==null)
             searchView = new SearchView(this);
         return searchView;         
    }

    public View getLoginView() {
        if (loginView==null)
            loginView = new LoginView(this);
        return loginView;
    }
    
    public LoadingView getLoadingView() {
        if (loadingView==null)
            loadingView = new LoadingView(this);
        return loadingView;
    }
    
    public UniqueItemVector getSavedContacts()
    {
        if (savedContacts==null)
        {
            String savedJSON = "";
            savedJSON = RMSUtils.readString("savedJSON");
            if (!savedJSON.equals(""))
            {
                savedContacts = Response.fromSavedJSON(savedJSON);
            }
            else 
                savedContacts = new UniqueItemVector();
        }
        return savedContacts;
    }

    public UniqueItemVector getSearchContacts(){
       
        if(searchContacts==null)
       {   
           searchContacts= new UniqueItemVector(Response.contacts.length);
            for(int i=0;i<Response.contacts.length;i++)
            {
                searchContacts.addElement(Response.contacts[i]);
            }
       }
       return searchContacts;
    }
    
    public UniqueItemVector getSearchContactfromWeb()
    {
        searchContacts= new UniqueItemVector(Response.contacts.length);
            for(int i=0;i<Response.contacts.length;i++)
            {
                searchContacts.addElement(Response.contacts[i]);
            }
         return searchContacts;   
    }
    void stopLoading() {
        
        loadingView.delete();
        loadingView = null;
    }
    
    public void viewContactDetails()
    {
        if (mode == ContactDetailsView.CONTACTMODE &&!savedContacts.isEmpty())
            {  
                setCurrentView(new ContactDetailsView(this, ContactDisplayCustomItem.highlightedItem.getCurrentContact(), mode));
                getContactMIDlet().getDisplay().setCurrent(this);
            }
            else if(mode == ContactDetailsView.SEARCHMODE && !searchContacts.isEmpty())
            {
                setCurrentView(new ContactDetailsView(this, ContactDisplayCustomItem.highlightedItem.getCurrentContact(), mode));
                getContactMIDlet().getDisplay().setCurrent(this);
            } 
    }
    
    public void commandAction(Command c, Displayable d) {

        if (c==viewChildrenCommand)
        {
           viewContactDetails();
        }
        else if (c == exitCommand )
        {   
            if (savedContacts!= null)
            Worker.queue(new Workable() {

                public boolean work() {
                    Contact []contactArray= new Contact[savedContacts.size()];
                    for (int i=0;i< savedContacts.size();i++)
                    {
                        contactArray[i]=(Contact)savedContacts.elementAt(i);
                    }
                    RMSUtils.write("savedJSON", Response.toJSON(contactArray));

                    return false;
                }
            });
            getContactMIDlet().exitMIDlet(false);
        }
        else if (c==searchCommand)
        {
            setCurrentView(getSearchView());
            getContactMIDlet().getDisplay().setCurrent(this);
        }
        else if (c==backCommand)
        {
            setCurrentView(getSearchView());
            getContactMIDlet().getDisplay().setCurrent(this);
        }
        
        else if(c==about)
        {
            String aboutString = "CoopCUG as a word was derived from the words Cooperative and Closed User Group. It was designed as an on-net voice telecom platform that allows members who are subscribed to the COOpCUG network to make calls for any length of time, 24 hours a day and seven days a week for the whole  month after the payment of the prescribed monthly rental fee for the four GSM providers i.e. MTN, Airtel, Glo and Etisalat."+

                                "What this translates to is that if a member subscribes to CoopCUG, that member will pay a prescribed monthly fee, which is renewable every month, and this allows such member to make voice calls to other members on the CoopCUG platform without any charges as long as such calls terminate on the same network e.g. calls made from an MTN subscriber to another MTN subscriber both on the CoopCUG Service. When a subscriber makes a call from his network to another network e.g. from MTN to Airtel, there is a charge made on such calls. But these charges are usually lower than what one would ordinarily get were such calls made by those who are not subscribed to the CoopCUG."+

                                "A subscriber to the network is issued a CoopCUG PIN known as \"CC PIN\" which is a unique identification number only obtainable to the subscriber after the completion of the registration process. This enables the subscriber after the completion of the registration process. This enables the subscriber to store and share telephone numbers with other subscribers on the CoopCUG network, in the same way that a Blackberry subscriber has a BB PIN."+

                                "To subscribe, logon to www.coopcug.com and follow the instructions or visit any of our dealers/agents within your area. Payments for the CoopCUG service can be made to CoopCUG accounts at First Bank, Zenith Bank, Stanbic IBTC, Skye Bank and mobile payment through e-wallet accounts on MTN MObile Money, First Monie, Glo Cash, Teasly Mobile, Fortis Mobile e.t.c.";
            
            Alert aboutAlert = new Alert("About", aboutString, null, AlertType.INFO);
            aboutAlert.setTimeout(Alert.FOREVER);
            getContactMIDlet().getDisplay().setCurrent(aboutAlert, getCustomForm(getSearchContacts(), mode));
        }
    }
     
}
