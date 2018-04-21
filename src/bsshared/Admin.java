package bsshared;

public class Admin extends User
{
	private static final long serialVersionUID = 1L;

	public Admin()
	{
		super("admin", "admin");
	}
	
	public Admin promoteToAdmin(User user)
	{
		if (!(user instanceof Admin))
		{
			Admin admin = new Admin();
			admin.username = user.username;
			admin.password = user.password;
			admin.address = user.address;
			admin.ccNumber = user.ccNumber;
			
			return admin;
		}
		else
			return (Admin)user;
	}
}
