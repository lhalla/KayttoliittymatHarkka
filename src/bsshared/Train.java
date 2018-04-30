package bsshared;

import java.io.Serializable;

public class Train implements Serializable
{
	private static final long serialVersionUID = 1L;
	public boolean Available;
	public double cost;
	public String route;
	public String[][][] seats;


	public Train() {
		this.Available=true;
		this.cost=5.0;
		this.route="A001: Helsinki-Turku klo 8-10";
		this.seats= new String[10][2][372];

	}
	public Train(String route, double cost){
		this.Available=true;
		this.cost=5.0;
		this.route=route;
		this.seats= new String[10][2][372];
	}
	
	public boolean matchingReitti (String reitti){
		if(this.route==reitti)return true;
		else return false;
	}
	
	public double getCost(){
		return cost;
	}
	public String getRoute(){
		return route;
	}
	public boolean getAvailability(){
		return Available;
	}
	

	public boolean areSeatsLeft(){
		for (int row = 0; row < seats.length; row++)
		{
			for (int seat = 0; seat < seats[row].length; seat++)
			{
				if (seats[row][seat] == null || seats[row][seat].equals("")) return true;
			}
		}
		return false;
	}
	
	
	public void setRoute(String from,String to,String when){
		this.route=from + "-" + to + " klo " + when;
	}
	public void setCost(double cost){
		this.cost=cost;
	}
	public void setAvailability(boolean available){
		this.Available=available;
	}

}
