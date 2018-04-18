package bsclient;

import bsshared.*;
import messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
	private ClientGUI cgui;	// the GUI this client is tied to.
	
	protected User user;
	
	private ObjectInputStream streamIn;	// object stream for incoming communication
	private ObjectOutputStream streamOut;	// object stream for outgoing communication
	private Socket socket;	// the socket of the connection
	
	private final String server = "localhost";	// server name
	private final int port = 8800;	// server port
	
	/**
	 * Constructor.
	 * @param cgui the ClientGUI this Client instance is tied to.
	 */
	public Client(ClientGUI cgui)
	{
		this.cgui = cgui;
	}
	
	/**
	 * Starts the client. Opens a socket and in/out object streams with the server.
	 * @return true if the client was started successfully.
	 */
	public boolean start()
	{
		// Try to open a socket.
		try
		{
			socket = new Socket(server, port);
		}
		catch (Exception e)
		{
			System.err.println("Failed to open socket.");
			return false;
		}
		
		// Try to open object streams.
		try
		{
			streamIn = new ObjectInputStream(socket.getInputStream());
			streamOut = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException ioe)
		{
			System.err.println("Failed to open object streams.");
			ioe.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Authenticates the user.
	 * @param username username given through the login prompt.
	 * @param password password given through the login prompt.
	 * @return true if login was successful.
	 */
	public boolean authenticate(String username, String password)
	{
		// Create a User object using the given credentials.
		this.user = new User(username, password);
		
		// Send the User object to the server to check if they were correct.
		try
		{
			streamOut.writeObject(user);
			streamOut.flush();
			boolean res = streamIn.readBoolean();
			return res;
		}
		catch (IOException ioe)
		{
			System.err.println("Exception when trying to send User object.");
		}
		return false;
	}
	
	/**
	 * Creates a new user with the given credentials.
	 * @param username username given through the login prompt.
	 * @param password password given through the login prompt.
	 * @return true if the account creation was successful.
	 */
	public boolean createNewUser(String username, String password)
	{
		// Create a NewUser object using the given credentials.
		NewUser nuser = new NewUser(username, password);
		
		// Send the NewUser object to the server to check if the name was already taken.
		try
		{
			streamOut.writeObject(nuser);
			streamOut.flush();
			boolean res = streamIn.readBoolean();
			return res;
		}
		catch (IOException ioe)
		{
			System.err.println("Exception when trying to send NewUser object.");
		}
		return false;
	}
	
	public void logout()
	{
		if(user != null){
		System.out.println("logging out, user varaukset:" + user.getVaraukset());
		}
		
		if (socket != null && !socket.isClosed())
		{
			try
			{
				streamOut.writeObject(new Logout());
				streamOut.flush();
			}
			catch (IOException ioe)	{}

		}
	}
}
