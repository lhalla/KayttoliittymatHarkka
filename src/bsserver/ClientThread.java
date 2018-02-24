package bsserver;

import bsshared.*;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

// A thread for each client.
public class ClientThread extends Thread
{
	private Socket socket;	// client's socket
	private ObjectInputStream streamIn;	// object stream for incoming communication
	private ObjectOutputStream streamOut;	// object stream for outgoing communication
	
	private final Server master;	// the master (owner) of this thread
	protected final int id;	// connection id
	
	private User user;	// user logged in
	private String date;	// date to determine which train routes to show
	
	/**
	 * Constructor.
	 * @param master the Server owning of this ClientThread.
	 * @param socket the socket used to communicate with the client.
	 * @param id the unique id of this ClientThread.
	 */
	public ClientThread(Server master, Socket socket, int id)
	{
		this.master = master;
		this.id = id;
		this.socket = socket;
		
		// Establish a connection with the client
		try
		{
			// Start the in/out streams.
			streamOut = new ObjectOutputStream(socket.getOutputStream());
			streamIn = new ObjectInputStream(socket.getInputStream());
			
			// Loop until the client has been verified or the log out (LATTER NOT IMPLEMENTED YET)
			while (true)
			{
				// Read an object from the incoming stream.
				Object incobj = streamIn.readObject();
				
				// If the client tries to create a new user account, check if the username is taken.
				if (incobj instanceof NewUser)
				{
					user = (User) incobj;	// convert the incoming object into a User.
					
					for (User u : master.users)
					{
						// The username is already taken.
						if (u.getUsername().equals(user))
						{
							// Send a negative response.
							streamOut.writeBoolean(false);
							streamOut.flush();
							
							// Set the user to null and break from the loop.
							user = null;
							break;
						}
					}
					
					// If the username wasn't taken, create a new user (NOT IMPLEMENTED YET).
					if (user != null)
					{
						
					}
				}
//				// If the client logs out or closes the login window, end the thread.
//				else if (incobj instanceof Message)
//				{
//					
//				}
				// Otherwise check if the credentials are correct.
				else
				{
					user = (User) incobj;	// convert the incoming object into a User.
					
					// Display a login attempt in the event log.
					master.displayEvent("Incoming login attempt from user '" 
							+ user.getUsername() + "' (" + socket.getInetAddress().toString() + ":" 
							+ socket.getPort() + ").");
					
					// If the user credentials are correct, set the thread's user.
					if (master.users.contains(user))
					{
						// Go through all the users to try to find a match.
						for (User u : master.users)
						{
							// If a match is found, set the user and break out of the loop.
							if (u.equals(user))
							{
								user = u;
								break;
							}
						}
						
						// Send a positive response (valid credentials) and break out of the loop.
						streamOut.writeBoolean(true);
						streamOut.flush();
						break;
					}
					
					// Send a negative response (invalid credentials).
					streamOut.writeBoolean(false);
					streamOut.flush();
				}
			}
			
			// Display a successful login in the event log.
			master.displayEvent("User '" + user.getUsername() + "' connected.");
		}
		catch (IOException ioe)
		{
			return;
		}
		catch (ClassNotFoundException cnfe)	{}
		
		date = new Date().toString() + "\n";
	}
	
	/**
	 * Runs the server (NOT YET IMPLEMENTED).
	 */
	public void run()
	{
		// Keep running (FOR A WHILE LOOP TO BE IMPLEMENTED).
		boolean keepRunning = true;
		
		// Finally remove this thread from the master's ClientThreads and close it.
		master.remove(id);
		close();
	}
	
	/**
	 * Closes the streams and the socket.
	 */
	public void close()
	{
		try
		{
			if (streamOut != null)
				streamOut.close();
		}
		catch (Exception e) {}
		
		try
		{
			if (streamIn != null)
				streamIn.close();
		}
		catch (Exception e) {}
		
		try
		{
			if (socket != null)
				socket.close();
		}
		catch (Exception e) {}
	}
}
