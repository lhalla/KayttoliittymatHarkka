package bsshared;


//en osaa nimetä muuttujia premminkaan, koska trainseattiin tarvitsee 2 boolean arvoa
//yksi confirmerille ja yksi train oliolle paikan saatavutta itse varaajaa varten
public class TrainSeat {
	public boolean paikkaOnOikeastiVarattu;
	
	public TrainSeat() {
		
	}
	
	public boolean paikkaOnOikeastiVarattu(){
		return paikkaOnOikeastiVarattu;
	}
	public void setPaikanVaraus(boolean onkoVarattu){
		paikkaOnOikeastiVarattu=onkoVarattu;
	}

}
