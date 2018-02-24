package bsserver;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonToggle;
	private JTextArea textAreaLog;
	private Server server;
	
	/**
	 * Constructor.
	 */
	public ServerGUI()
	{
		super("Train Booking Service - Server");
		server = null;
		
		// JPanel for the ON/OFF button
		JPanel panelToggle = new JPanel();		// create a new panel for the button
		buttonToggle = new JButton("Start");	// create the button
		buttonToggle.addActionListener(this);	// add a listener to it to check when it's pressed
		panelToggle.add(buttonToggle);			// add the button to the panel
		add(panelToggle, BorderLayout.NORTH);	// add the panel to the frame
		
		// JPanel for the event log
		JPanel panelEventLog = new JPanel(new GridLayout(1,1));	// create a new panel for the event log
		textAreaLog = new JTextArea(80, 30);					// create a new text area to hold the log
		textAreaLog.setEditable(false);							// set the text area as uneditable
		panelEventLog.add(new JScrollPane(textAreaLog));		// add the text area to the panel
		add(panelEventLog);										// add the panel to the frame
		
		appendEvent("Server event log.\n");
		
		// Add a listener to the frame to stop the server if the window is closed.
		addWindowListener(new WindowAdapter() {  
			@Override
			public void windowClosing(WindowEvent e)
			{
				if (server != null)
				{
					try
					{
						server.stop();
					}
					catch (Exception ex)
					{
						server = null;
					}
				}
				
				dispose();
				System.exit(0);
			}  
        });
		
		// Set the size and make the frame visible.
		setSize(450, 600);
		setVisible(true);
	}
	
	/**
	 * Adds an event to the event log.
	 * @param event an event to be added to the log.
	 */
	public void appendEvent(String event)
	{
		textAreaLog.append(event);
	}

	/**
	 * Reacts to button presses.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// If the server is running, turn it off.
		if (server != null)
		{
			server.stop();
			server = null;
			buttonToggle.setText("Start");
			return;
		}
		
		// Otherwise turn it on.
		server = new Server(this);
		new ServerThread().start();
		buttonToggle.setText("Stop");
	}
	
	/**
	 * Starts a new Server GUI.
	 * @param args
	 */
	public static void main(String[] args)
	{
		new ServerGUI();
	}
	
	// A thread for the server to run in.
	class ServerThread extends Thread
	{
		/**
		 * Runs the thread.
		 */
		public void run()
		{
			server.start();
			buttonToggle.setText("Start");
			
			// If the server is still on yet server.start() has finished, it has crashed.
			if (server != null && server.serverOn)
				appendEvent("Server crashed.\n");
			else
				appendEvent("Server shut down.\n");
			server = null;
		}
	}
}
