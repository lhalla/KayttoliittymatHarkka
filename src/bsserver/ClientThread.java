package bsserver;

import bsshared.*;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

public class ClientThread extends Thread
{
	private Socket socket;	// client's socket
	private ObjectInputStream streamIn;
	private ObjectOutputStream streamOut;
	
	private final Server master;
	protected final int id;	// connection id
	
	private User user;	// user logged in
	private String date;
	
	public ClientThread(Server master, Socket socket, int id)
	{
		this.master = master;
		this.id = id;
		this.socket = socket;
		
		try
		{
			streamOut = new ObjectOutputStream(socket.getOutputStream());
			streamIn = new ObjectInputStream(socket.getInputStream());
			
			while (true)
			{
				user = (User) streamIn.readObject();
				if (user instanceof NewUser)
				{
					for (User u : master.users)
					{
						if (u.getUsername().equals(user))
						{
							streamOut.writeBoolean(false);
							streamOut.flush();
						}
					}
				}
				else
				{
					master.displayEvent("Incoming login attempt from user '" 
							+ user.getUsername() + "' (" + socket.getInetAddress().toString() + ":" 
							+ socket.getPort() + ").");
					if (master.users.contains(user))
					{
						for (User u : master.users)
						{
							if (u.equals(user))
								user = u;
						}
						streamOut.writeBoolean(true);
						streamOut.flush();
						break;
					}
					streamOut.writeBoolean(false);
					streamOut.flush();
				}
			}
			
			master.displayEvent("User '" + user.getUsername() + "' connected.");
		}
		catch (IOException ioe)
		{
			return;
		}
		catch (ClassNotFoundException cnfe)	{}
		
		date = new Date().toString() + "\n";
	}
	
	public void run()
	{
		boolean keepRunning = true;
		
		master.remove(id);
		close();
	}
	
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
