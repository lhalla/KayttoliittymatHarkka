package bsshared;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	protected String username, password;
	protected String address;
	protected String ccNumber;
	protected ArrayList<String> varaukset;
	
	public User() { this("",""); }
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.address = "";
		this.ccNumber = "";
		this.varaukset = new ArrayList<String>();
	}
	
	// Getters
	public String getUsername() { return username; }
	
	public String getAddress() { return address; }
	
	public String getCCNumber() { return ccNumber; }
	
	public ArrayList<String> getVaraukset() { return varaukset; }
	
	// Setters
	public void setAddress(String address) { this.address = address; }
	
	public void setCCNumber(String ccNumber) { this.ccNumber = ccNumber; }
	
	public void setVaraukset(ArrayList<String> varaukset) { this.varaukset=varaukset; }
	
	public void setVaraus(String varaus){this.varaukset.add(varaus);}
	
	
	//can't copy into/from a null user
	public void copy(User other)
	{
		this.username = other.username;
		this.password = other.password;
		this.address = other.address;
		this.ccNumber = other.ccNumber;
		
		for (String varaus : other.varaukset)
			this.varaukset.add(varaus);
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
