package bsserver;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonToggle;
	private JTextArea textAreaLog;
	private Server server;
	
	public ServerGUI()
	{
		super("Train Booking Service - Server");
		server = null;
		
		// JPanel for the ON/OFF button
		JPanel panelToggle = new JPanel();
		buttonToggle = new JButton("Start");
		buttonToggle.addActionListener(this);
		panelToggle.add(buttonToggle);
		add(panelToggle, BorderLayout.NORTH);
		
		// JPanel for the event log
		JPanel panelEventLog = new JPanel(new GridLayout(1,1));
		textAreaLog = new JTextArea(80, 30);
		textAreaLog.setEditable(false);
		
		appendEvent("Server event log.\n");
		
		panelEventLog.add(new JScrollPane(textAreaLog));
		add(panelEventLog);
		
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
		
		setSize(450, 600);
		setVisible(true);
	}
	
	public void appendEvent(String event)
	{
		textAreaLog.append(event);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (server != null)
		{
			server.stop();
			server = null;
			buttonToggle.setText("Start");
			return;
		}
		
		server = new Server(this);
		new ServerThread().start();
		buttonToggle.setText("Stop");
	}
	
	public static void main(String[] args)
	{
		new ServerGUI();
	}
	
	class ServerThread extends Thread
	{
		public void run()
		{
			server.start();
			buttonToggle.setText("Start");
			if (server != null && server.serverOn)
				appendEvent("Server crashed.\n");
			else
				appendEvent("Server shut down.\n");
			server = null;
		}
	}
}
