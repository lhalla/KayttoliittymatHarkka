package bsclient;

import java.util.ArrayList;
import bsshared.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class ClientGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private ClientLoginPrompt loginPrompt;	// login prompt used at startup
	protected Client client;	// the client this clientGUI is tied to.
	protected User user;
	protected static String username;
	protected static ArrayList<String> varaukset;
	/**
	 * Constructor.
	 */
    public ClientGUI()
    {
    	// Open a login prompt.
        loginPrompt = new ClientLoginPrompt(this, true);
        loginPrompt.setTitle("Train Booking Service - Login");
        loginPrompt.setVisible(true);
        
     // Exit the program if the window is closed.
        addWindowListener(new WindowAdapter()
        {  
            @Override
            public void windowClosing(WindowEvent e)
            {  
            	client.logout();
                System.exit(0);  
            }  
        });
    }

    /**
     * Starts a new ClientGUI.
     * @param args
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {            	
                JFrame frame = new ClientGUI();
                TrainBookingService service= new TrainBookingService(frame,username,varaukset);
                
                frame = service.makeWindow();
                frame.setTitle("Train Booking Service");
                frame.setLocationRelativeTo(null);
                
            }
        });
    }
}
