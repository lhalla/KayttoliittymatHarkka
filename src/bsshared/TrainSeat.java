package bsshared;


//yksi confirmerille ja yksi train oliolle paikan saatavutta itse varaajaa varten
public class TrainSeat {
	public boolean paikkaOnOikeastiVarattu;
	String paikka;
	int row;
	int column;
	
	//used in hyväksyvaraus button
	//the idea is to do this:
	//user.setVaraus(reittibox + paivamääräboxes + chosenseat)
	
	
	public TrainSeat() {
		paikka="";
	}
	
	public boolean paikkaOnOikeastiVarattu(){
		return paikkaOnOikeastiVarattu;
	}
	public void setPaikanVaraus(boolean onkoVarattu){
		paikkaOnOikeastiVarattu=onkoVarattu;
	}
	
	public String getPaikka(){
		return paikka;
	}
	public int getRow(){
		return row;
	}
	public int getColumn(){
		return column;
	}

	public void setPaikka(String paikka){
		this.paikka=paikka;
	}
	public void setRow(int row){
		this.row=row;
	}
	public void setColumn(int column){
		this.column=column;
	}
}
