package bsshared;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class UserlistReader
{
	public static void main(String[] args)
	{
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/data/users.dat")))
		{
			ArrayList<User> al = (ArrayList<User>) ois.readObject();
			al.stream().forEach(usr -> System.out.println("Username: " + usr.getUsername()));
		}
		catch (Exception e)
		{
			System.err.println("Userlist read test failed.");
			e.printStackTrace();
		}
	}
}
