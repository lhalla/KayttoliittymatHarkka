package bsshared;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UserlistInitialiser
{
	private ArrayList<User> users;
	
	public UserlistInitialiser()
	{
		users = new ArrayList<User>();
	}
	
	public void createAdmin()
	{
		users.add(new User("admin", "admin"));
	}
	
	public void saveList()
	{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/data/users.dat")))
		{
			oos.writeObject(users);
			System.out.println("Userlist successfully initialised.");
		}
		catch (Exception e)
		{
			System.err.println("Userlist initialisation failed.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		UserlistInitialiser uli = new UserlistInitialiser();
		uli.createAdmin();
		uli.saveList();
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/data/users.dat")))
		{
			ArrayList<User> al = (ArrayList<User>) ois.readObject();
			al.stream().forEach(usr -> System.out.println("Username: " + usr.getUsername()));
			System.out.print("Userlist read test successful.");
		}
		catch (Exception e)
		{
			System.err.println("Userlist read test failed.");
			e.printStackTrace();
		}
	}
}
