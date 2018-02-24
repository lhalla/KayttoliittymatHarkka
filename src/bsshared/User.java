package bsshared;

import java.io.Serializable;

public class User implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	protected String username, password;
	protected String address;
	protected String ccNumber;
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.address = "";
		this.ccNumber = "";
	}
	
	// Getters
	public String getUsername()
	{
		return username;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getCCNumber()
	{
		return ccNumber;
	}
	
	// Setters
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void setCCNumber(String ccNumber)
	{
		this.ccNumber = ccNumber;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if ((getClass() != o.getClass()) && !(o instanceof NewUser))
			return false;
		
		User user = (User) o;
		
		return (username.equals(user.username) && password.equals(user.password));
	}
}
