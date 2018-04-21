package messages;

import java.io.Serializable;

import bsshared.User;

public class UserUpdate implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final User user;
	
	public UserUpdate(User user)
	{
		this.user = new User();
		this.user.copy(user);
	}
	
	public User getUser() { return user; }
}
