package bsclient;

import bsshared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
	private ClientGUI cgui;
	
	private ObjectInputStream streamIn;
	private ObjectOutputStream streamOut;
	private Socket socket;
	
	private final String server = "localhost";
	private final int port = 8800;
	
	private User user;
	
	public Client(ClientGUI cgui)
	{
		this.cgui = cgui;
		this.user = null;
	}
	
	public boolean start()
	{
		try
		{
			socket = new Socket(server, port);
		}
		catch (Exception e)
		{
			System.err.println("Failed to open socket.");
			return false;
		}
		
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
	
	public boolean authenticate(String username, String password)
	{
		User user = new User(username, password);
		
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
	
	public boolean createNewUser(String username, String password)
	{
		NewUser nuser = new NewUser(username, password);
		
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
}
