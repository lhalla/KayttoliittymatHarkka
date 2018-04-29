package bsshared;


//en osaa nimet� muuttujia premminkaan, koska trainseattiin tarvitsee 2 boolean arvoa
//yksi confirmerille ja yksi train oliolle paikan saatavutta itse varaajaa varten
public class TrainSeat {
	public boolean paikkaOnOikeastiVarattu;
	String paikka;
	
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

	public void setPaikka(String paikka){
		this.paikka=paikka;
	}
}
