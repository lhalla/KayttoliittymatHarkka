package bsshared;

import java.util.ArrayList;

public class Train {
	public boolean Available;
	public double cost;
	public String name;
	public String route;
	public ArrayList<String> seats;

	public Train() {
		this.Available=true;
		this.cost=5.0;
		this.name="A001";
		this.route="Helsinki-Turku klo 8-10";
		this.seats=new ArrayList<String>();
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
	//TODO: korjaa antamaan false jos paika t�ynn�
	public boolean areSeatsLeft(){
		return true;
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
