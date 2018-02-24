package bsclient;

import java.awt.*;
import javax.swing.*;

public class ClientGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private ClientLoginPrompt loginPrompt;
	private Client client;

    public ClientGUI()
    {
    	// Open a login prompt.
        loginPrompt = new ClientLoginPrompt(this, true);
        loginPrompt.setTitle("Train Booking Service - Login");
        loginPrompt.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new ClientGUI();
                frame.getContentPane().setBackground(Color.BLACK);
                frame.setTitle("Train Booking Service");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setSize(500, 500);
            }
        });
    }
}