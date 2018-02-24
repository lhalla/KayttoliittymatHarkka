package bsserver;

import bsshared.*;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server
{
	private static int uid;	// each connection has a unique identifier
	private ArrayList<ClientThread> ctl; // each client has their own thread
	private ServerGUI sgui;	// the GUI this server is tied to
	private SimpleDateFormat sdf;
	private final int port = 8800;	// the port this server runs on
	protected boolean serverOn;	// is this server running?
	protected ArrayList<User> users;	// list of users
	
	/**
	 * Constructor.
	 * @param sgui the ServerGUI this Server instance is tied to.
	 */
	@SuppressWarnings("unchecked")
	public Server(ServerGUI sgui)
	{
		this.sgui = sgui;
		sdf = new SimpleDateFormat("HH:mm:ss");
		ctl = new ArrayList<ClientThread>();
		
		// Load a list of all users from file.
		try (ObjectInputStream streamFile = new ObjectInputStream(new FileInputStream("src/data/users.dat")))
		{
			users = (ArrayList<User>) streamFile.readObject();
		}
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Starts the server.
	 */
	public void start()
	{
		// The server is on.
		serverOn = true;
		
		// Display the starting event in the log.
		displayEvent("Server started on port " + port + ".");
		
		// Create a server socket and wait for incoming connections.
		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			while (serverOn)
			{
				// Accept an incoming connection
				Socket socket = serverSocket.accept();
				
				// If the server has been turned off, break out of the loop.
				if (!serverOn)
					break;
				
				// Start a new thread for the new client.
				ClientThread ct = new ClientThread(this, socket, ++uid);
				ctl.add(ct);
				ct.start();
			}
			
			// Once the server has been told to turn off, close all the clien threads.
			try
			{
				// Close all ClientThreads.
				for (ClientThread ct : ctl)
					ct.close();
			}
			catch (Exception e)
			{
				displayEvent("An exception encountered while shutting down the server and clients: " + e);
			}
		}
		catch (IOException ioe)
		{
			displayEvent(sdf.format(new Date()) + " Exception on new ServerSocket: " + ioe + "\n");
		}
	}
	
	/**
	 * Stops the server.
	 */
	protected void stop()
	{
		serverOn = false;
		
		// Connect to itself as a client -> serverOn is detected to be false.
		try (Socket sock = new Socket("localhost", port)) {}
		catch (Exception e) {}
	}
	
	/**
	 * Removes a ClienThread with a given id from the list once the connection ends.
	 * @param id ClientThread's unique id.
	 */
	synchronized void remove(int id)
	{
		// Go through all of the threads until one with the correct id is found.
		for (int i = 0; i < ctl.size(); i++)
		{
			ClientThread ct = ctl.get(i);
			
			// Remove it.
			if (ct.id == id)
			{
				ctl.remove(i);
				return;
			}
		}
	}
	
	/**
	 * Adds a new user to the user list.
	 * @param newUser
	 * @return
	 */
	synchronized boolean addNewUser(User newUser)
	{
		// If the user list doesn't contain a user with the same name, add it.
		if (!users.contains(newUser))
		{
			// Add the user.
			users.add(newUser);
			
			// Save the new user list.
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/data/users.dat")))
			{
				oos.writeObject(users);
				displayEvent("A new user '" + newUser.getUsername() + "' added.");
				return true;
			}
			catch (Exception e)
			{
				displayEvent("Addition of a new user '" + newUser.getUsername() + "' failed.");
				users.remove(newUser);
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Displays an event in the event log.
	 * @param message event to be displayed.
	 */
	protected void displayEvent(String message)
	{
		String event = sdf.format(new Date()) + " " + message;
		sgui.appendEvent(event + "\n");
	}
}
