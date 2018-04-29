package bsclient;

 

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import bsshared.*;
import messages.*;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

 

 

public class TrainBookingService {
	
	private User user;
	
	//List of trains and their properties
	private ArrayList<Train> trainList = new ArrayList<Train>();
	//the train that user is currently trying to book
	private Train chosenTrain;
	
	//cheks if user is admin
	private boolean isAdmin;
	
	private Client client;

    private JFrame frame;

    private final int basewidth=200;
    private final int baseheight=200;
    
    //boolean that tells if image is the main menu image or not
    private boolean inSecondScreen=false;
    
    private ButtonListener bl;
    private ArrayList<JButton> ButtonList = new ArrayList<JButton>();
    private ArrayList<JLabel> TextList = new ArrayList<JLabel>();
    private ArrayList<JComboBox> BoxList = new ArrayList<JComboBox>();
    
    //panels here
    //!paneeleissa ei saa käyttää boxlayout
    private JPanel leftPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    
    //JLabel keskipaneelin kuvalle
    private JLabel image = new JLabel();

    //buttons here
    private JButton varausButton = new JButton("VARAA");
    private JButton varaukseniButton = new JButton("VARAUKSENI");
    private JButton lompakkoButton = new JButton("LOMPAKKO");
    
    private JButton adminJunaAsetuksetButton = new JButton("Juna-ASETUKSET");
    private JButton poistaJunaButton = new JButton("POISTA REITTI");
    private JButton lisaaJunaButton = new JButton("LISAA REITTI");
    private JButton hyvaksyJunanLuominenButton = new JButton("HYVÄKSY");
    private JButton peruJunanLuominenButton = new JButton("PERU");
    private JButton hyvaksyJunanPoistaminenButton = new JButton("HYVÄKSY");
    private JButton peruJunanPoistaminenButton = new JButton("PERU");
    
    private JButton adminAsetuksetButton = new JButton("Admin-ASETUKSET");
    
    private JButton poistuButton = new JButton("POISTU");
    
    private JButton infoButton = new JButton("Tietoja Palvelusta");
    
    private JButton asetuksetButton = new JButton("ASETUKSET");
    private JButton peruVarauksetButton = new JButton("PERU VARAUKSENI");
    private JButton buttonMuutaOsoite = new JButton("MUUTA OSOITE");
    private JButton buttonMuutaCCNumber = new JButton("MUUTA CCNum");
    private JButton buttonMuutaSalasana = new JButton("MUUTA Salasana");
    
    private JButton addFunds5 = new JButton("Lis�� 5e");
    private JButton addFunds25 = new JButton("Lis�� 25e");
    private JButton addFunds100 = new JButton("Lis�� 100e");
    
    private JButton hyvaksyVarausButton = new JButton("Hyvaksy");
    private JButton peruVarausButton = new JButton("Peru");
    
  	
    //JComboBoxit junanvaraukselle/luomiselle/poistamiselle
    private JComboBox<String> paivamaaraBox1;
    private JComboBox<String> paivamaaraBox2;
    private JComboBox<String> reittiBox;
    private JComboBox<String> junanNimiBox;
    private JComboBox<String> junanLahtoPysakkiBox;
    private JComboBox<String> junanSaapumisPysakkiBox;
    private JComboBox<String> junanLahtoAikaBox;
    private JComboBox<Double> junanHintaBox;
    
    private JButton buttonTakaisin = new JButton("TAKAISIN");
    
    //TODO: Pyyt�� parametriksi trainListin, korvaa makeCustomTrains metodi
    public TrainBookingService(JFrame frame)
    {
    	this.frame=frame;
 //   	this.user.copy(user);
    	
    	client = ((ClientGUI)frame).client;
    	
    	this.user = client.user;
    	
    	if(user.getUsername().equals("admin"))
    	{
    		isAdmin=true;
    	}
    	else
    	{
    		isAdmin=false;
    	}
    	
    	
    	
    	client.fetchTrains();
    	trainList = ((ClientGUI)frame).client.trainList;
//    	makeCustomTrains();
   	}
    /**
	 * Makes the frame and layout for trainbookingservice.
	 * no fixes needed, can be ignored.
	 */
    public JFrame makeWindow() {

    
    frame.getContentPane().setLayout(new BorderLayout());
    
    GridLayout gridLeft = new GridLayout(8,1);
    GridLayout gridTop = new GridLayout(1,5);
    GridLayout gridMid = new GridLayout(0,1);
    frame.setSize(5*basewidth, 5*baseheight-baseheight/2);
    frame.setResizable(false);

    //leftpanel
    leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    leftPanel.setLayout(gridLeft);
    leftPanel.setMaximumSize(new Dimension(basewidth,4*baseheight));
    leftPanel.setMinimumSize(new Dimension(basewidth,4*baseheight));

    //toppanel
    topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    topPanel.setLayout(gridTop);
    topPanel.setMaximumSize(new Dimension(5*basewidth,baseheight/2));
    topPanel.setMinimumSize(new Dimension(5*basewidth,baseheight/2));

    //centerpanel
    centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    centerPanel.setBackground(Color.WHITE);
    centerPanel.setLayout(gridMid);
    centerPanel.setMaximumSize(new Dimension(4*basewidth,4*baseheight));
    centerPanel.setMinimumSize(new Dimension(4*basewidth,4*baseheight));
    
    //Button initialization
  	bl = new ButtonListener();
                                
    //image
    createImage("https://images.cdn.yle.fi/image/upload//w_1199,h_1307,f_auto,fl_lossy,q_auto/13-3-6716387.jpg");

    
    //addbuttons and labels here
    centerPanel.add(image);
    makeButtonLP(varausButton);
    makeButtonLP(varaukseniButton);
    makeButtonLP(lompakkoButton);
    //topPanel
    //topPanel buttons depends on user being admin
    if(isAdmin){
    makeButtonTP(adminJunaAsetuksetButton);
    makeButtonTP(adminAsetuksetButton);
    }
    else{
    makeButtonTP(infoButton);
    makeButtonTP(asetuksetButton);	
    }
    makeButtonTP(poistuButton);
    
    
    frame.add(centerPanel, BorderLayout.CENTER);
    frame.add(topPanel, BorderLayout.NORTH);
    frame.add(leftPanel, BorderLayout.WEST);
    
    return frame;

    }
    
    /**
	 * Straches image for website X to fit JLable image
	 * no fixes needed, can be ignored.
	 */
    private Image stretchImage(Image srcImg){
		BufferedImage resizedImg = new BufferedImage(4*basewidth, 4*baseheight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, 4*basewidth, 4*baseheight, null);
	    g2.dispose();
	    return resizedImg;
	}
    
    
    /**
	 * these initialize the buttons or remove them
	 * LP=left panel TP=top panel
	 * no fixes needed, both can be ignored
	 */
    private void makeButtonLP(JButton button){
    	button.setBackground(new Color(50, 200, 45));
    	if(button == buttonTakaisin || button==peruVarausButton)button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.addActionListener(bl);
        button.setPreferredSize(new Dimension(basewidth -20, 30));
        ButtonList.add(button);
        leftPanel.add(button);
        button.setVisible(true);
    }
    private void makeButtonTP(JButton button){
    	button.setBackground(new Color(50, 200, 45));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.addActionListener(bl);
        button.setPreferredSize(new Dimension(basewidth -1, baseheight/2 -10));
        topPanel.add(button);
        button.setVisible(true);
    }
    /**
	 * adds a jcombobox to left panel
	 * no fixes needed, can be ignored.
	 */
    private void addBoxLP(JComboBox box){
    	leftPanel.add(box);
    	BoxList.add(box);
    }
    /**
	 * clears buttons from left panel
	 * no fixes needed, can be ignored.
	 */
    
    private void clearButtons(){
		for(int i=0;i<ButtonList.size();i++){
			ButtonList.get(i).setVisible(false);
		}
		for(int i=0;i<ButtonList.size();i++){
			ButtonList.get(i).removeActionListener(bl);
		}
		for(int i=0;i<ButtonList.size();i++){
			leftPanel.remove(ButtonList.get(i));
		}
		ButtonList.clear();
	}
    /**
	 * clears boxes from left panel
	 * no fixes needed, can be ignored.
	 */
    private void clearBoxes(){
    	for(int x=0;x<BoxList.size();x++){
			leftPanel.remove(BoxList.get(x));
		}
		BoxList.clear();
    }
    /**
	 * no fixes needed, can be ignored.
	 */
    private void createImage(String URLforImage){
    	try {
        	image.setIcon(new ImageIcon(stretchImage(new ImageIcon(new URL(URLforImage)).getImage())));
        	}catch(Exception e) {}
    }
    /**
	 * no fixes needed, can be ignored.
	 */
    protected ArrayList<String> getVaraukset(){
    	return user.getVaraukset();
    }
    
    //koravattava
    private void makeCustomTrains(){
    	trainList.add(new Train());
    	trainList.add(new Train());
    	trainList.add(new Train());
    	trainList.get(0).setRoute("Helsinki", "Tampere", "7-12");
    	trainList.get(0).setCost(11.5);
    	trainList.get(1).setRoute("Turku", "Tampere", "10-12");
    	trainList.get(0).setCost(8.5);
    	trainList.get(2).setRoute("Helsinki", "Turku", "19-23");
    	
    }
    
    /**
     * displays users current bookings on screen
	 * no fixes needed, can be ignored.
	 */
    private void varauksetMetodi(){
    	centerPanel.remove(image);
    	for(int i=0;i<user.getVaraukset().size();i++){
    		TextList.add(new JLabel());
    		TextList.get(i).setText("                                                                      " + user.getVaraukset().get(i));
    		TextList.get(i).setFont(new Font("Tahoma", Font.BOLD,14));
    		TextList.get(i).setForeground(Color.BLACK);
    		centerPanel.add(TextList.get(i));
    	}
    	
    	centerPanel.setBackground(new Color(127,235,255));
    }
    /**
     * displays users current money on screen, and makes buttons for adding money to it
	 * no fixes needed, can be ignored.
	 */
    private void lompakkoMetodi(){
    	centerPanel.remove(image);
    	TextList.add(new JLabel());
    	TextList.get(0).setText("           Wallet: " + user.getLompakko() + "e");
    	TextList.get(0).setFont(new Font("Tahoma", Font.BOLD,40));
    	TextList.get(0).setForeground(Color.BLACK);
    	centerPanel.add(TextList.get(0));
    	centerPanel.setBackground(new Color(255,216,132));
    	makeButtonLP(addFunds5);
    	makeButtonLP(addFunds25);
    	makeButtonLP(addFunds100);
    }
    
    /**
     * Method that gives user the option to choose which route to take and when
     * adds accept route and cancel buttons to left panel
     * this method uses jcomboboxes
	 * no fixes needed, can be ignored.
	 */
    private void varausMetodi(){
    	//Comboboxit reittibox saavat arvonsa t�st�
    	String[] reitti= new String[trainList.size()];
    	for(int a=0;a<trainList.size();a++){
    	reitti[a]=trainList.get(a).getRoute();
    	}
    	String[] paivamaara= new String[]{"1.","2.","3.","4.","5.","6.","7.","8.","9.","10.","11.","12.","13.","14.","15.","16.","17.","18.","19.","20.","21.","22.","23.","24.","25.","26.","27.","28.","29.","30.","31"};
    	String[] kuukausi= new String[]{"tammikuu","helmikuu","maaliskuu","huhtikuu","toukokuu","kes�kuu","hein�kuu","elokuu","syyskuu","lokakuu","marraskuu","joulukuu"};
    	reittiBox= new JComboBox<>(reitti);
    	paivamaaraBox1= new JComboBox<>(paivamaara);
    	paivamaaraBox2= new JComboBox<>(kuukausi);
    	addBoxLP(reittiBox);
    	addBoxLP(paivamaaraBox1);
    	addBoxLP(paivamaaraBox2);
    	makeButtonLP(hyvaksyVarausButton);
    	makeButtonLP(peruVarausButton);
    }
    
    
    /**
     * removes all current jbuttons,jcomboboxes and JLabel text in centerpanel
     * returns user to main menu after
	 * no fixes needed, can be ignored.
	 */
    private void removeTextAndButtonsAndReturn(){
    		clearButtons();
    		clearBoxes();
	    	makeButtonLP(varausButton);
	    	makeButtonLP(varaukseniButton);
	    	makeButtonLP(lompakkoButton);
	    	if(inSecondScreen){
	    		for(int j=0;j<TextList.size();j++){
	    			centerPanel.remove(TextList.get(j));
	    		}
	    		TextList.clear();
	    		centerPanel.add(image);
	    		image.setForeground(Color.WHITE);
	    		centerPanel.setBackground(Color.WHITE);
	    		inSecondScreen=false;
	    	}
	    	createImage("https://images.cdn.yle.fi/image/upload//w_1199,h_1307,f_auto,fl_lossy,q_auto/13-3-6716387.jpg");
	    
    }
    
    /**
     * after user has chosen the seat for a route, a confirmer screen is opened
     * this method will check if user can afford the trainride and will ask if user is sure
	 * no fixes needed, can be ignored.
	 */
    private boolean confirm(){
    	confirmScreen confirmer = new confirmScreen(frame);
    	double price = calcPrice();
    	boolean canAfford = user.canAfford(price);
    	boolean confirm = confirmer.confirm((String) reittiBox.getSelectedItem() + " " + (String) paivamaaraBox1.getSelectedItem() + (String) paivamaaraBox2.getSelectedItem() + "ta", "Hinta: " + price + "e", canAfford);
    	return confirm;
    }
    /**
     * once user has pressed the button that chooses the route and time for train this will be run
     * this opens up a new screen that will ask which seat would the user like
     * after seat has been chosen, returns a trainseat object which tells bookingservice which seat user wishes to take
	 * this method needs no fixes but paikanvarauscreen class requires a couple of tweaks, and hyväksyvarausbutton will update the user.varaukset value if confirmer returns true
	 */
    private TrainSeat varaaPaikka(){
    	paikanVarausScreen paikanVaraaja = new paikanVarausScreen(frame,chosenTrain);
    	TrainSeat varattuPaikka= paikanVaraaja.varaa();	
    	return varattuPaikka;
    }
    
    
    /**
     * calculates the price of the train route that is being booked
	 * no fixes needed, can be ignored.
	 */
    private Double calcPrice(){	
    	return chosenTrain.getCost();
    }
    
    /**
     * returns the train object that user is trying to book
	 * no fixes needed, can be ignored.
	 */
    private Train getChosenTrain(){
    	String junaReitti= (String) reittiBox.getSelectedItem();
    	for(int k=0;k<trainList.size();k++){
    		if(trainList.get(k).matchingReitti(junaReitti)){
    			return trainList.get(k);
    		}
    	}
    	Train noTrain = new Train();
    	noTrain.setAvailability(false);
    	return noTrain;
    }
    
    /**
     * makes Jcomboboxes for making new train routes
	 * no fixes needed, can be ignored.
	 */
    private void lisaaJunaBoxit(){
    		String[] junaNimi= new String[]{"A001","B001"};
	    	String[] LahtoTaiSaapumisPysakki= new String[]{"Helsinki","Turku","Tampere"};
	    	String[] LahtoAikaBox= new String[]{"klo 6-8","klo 7-9","klo 8-10","klo 10-12","klo 12-14","klo 15-17","klo 20-22","klo 22-00"};
	    	Double[] junaHinta= new Double[]{5.0,6.0,8.0};
	    	junanNimiBox =  new JComboBox<>(junaNimi);
	    	junanLahtoPysakkiBox =  new JComboBox<>(LahtoTaiSaapumisPysakki);
	    	junanSaapumisPysakkiBox =  new JComboBox<>(LahtoTaiSaapumisPysakki);
	    	junanLahtoAikaBox =  new JComboBox<>(LahtoAikaBox);
	    	junanHintaBox = new JComboBox<>(junaHinta);
	    	addBoxLP(junanNimiBox);
	    	addBoxLP(junanLahtoPysakkiBox);
	    	addBoxLP(junanSaapumisPysakkiBox);
	    	addBoxLP(junanLahtoAikaBox);
	    	addBoxLP(junanHintaBox);
    }
   
    
    
    
    /**
     * when a button is pressed, buttonlistener does something that is determined by the button
	 */
  	private class ButtonListener implements ActionListener{
  	    public void actionPerformed(ActionEvent e) {
  	    if(e.getSource() == varausButton) {
  	    	//varaa
  	    	removeTextAndButtonsAndReturn();
  	    	clearButtons();
	    	varausMetodi();
  	    }
  	    if(e.getSource() == varaukseniButton) {
  	    	//varaukset
  	    	inSecondScreen=true;
  	    	clearButtons();
  	    	makeButtonLP(buttonTakaisin);
  	    	varauksetMetodi();
  	    }
  	    if(e.getSource() == lompakkoButton) {
  	    	//lompakko
  	    	inSecondScreen=true;
  	    	clearButtons();
  	    	lompakkoMetodi();
  	    	makeButtonLP(buttonTakaisin);
	    }
  	    if(e.getSource() == adminJunaAsetuksetButton) {
  	    	//Juna asetukset
  	    	removeTextAndButtonsAndReturn();
  	    	clearButtons();
  	    	makeButtonLP(lisaaJunaButton);
  	    	makeButtonLP(poistaJunaButton);
  	    	makeButtonLP(buttonTakaisin);	
	    }
  	    if(e.getSource() == lisaaJunaButton) {
  	    	clearButtons();
  	    	lisaaJunaBoxit();
  	    	makeButtonLP(hyvaksyJunanLuominenButton);
  	    	makeButtonLP(peruJunanLuominenButton);
  	  	}
  	  	if(e.getSource() == poistaJunaButton) {
  	  		clearButtons();
	    	String[] reitti= new String[trainList.size()];
	    	for(int a=0;a<trainList.size();a++){
	    	reitti[a]=trainList.get(a).getRoute();
	    	}
	    	reittiBox= new JComboBox<>(reitti);
	    	addBoxLP(reittiBox);
	    	
	    	makeButtonLP(hyvaksyJunanPoistaminenButton);
	    	makeButtonLP(peruJunanPoistaminenButton);
  		}
  	    
  	    
  	    if(e.getSource() == adminAsetuksetButton) {
  	    	//admin asetukset
  	    	removeTextAndButtonsAndReturn();
  	    	clearButtons();
  	    	makeButtonLP(buttonTakaisin); 	    	
	    }
  	    if(e.getSource() == poistuButton) {
  	    	//logout
  	    	
	    }
  	  	if(e.getSource() == infoButton) {
  	  		removeTextAndButtonsAndReturn();
	    	clearButtons();
	    	inSecondScreen=true;
	    	centerPanel.remove(image);
	    	
	    	TextList.add(new JLabel());
	    	TextList.get(0).setText("Train Booking Service versio 1.0 tukee monia toimintoja, mutta poistu näppäin on vielä epäkunnossa, poistu painamalla oikean yläkulman rastia");
	    	TextList.get(0).setFont(new Font("Tahoma", Font.BOLD,12));
    		TextList.get(0).setForeground(Color.BLACK);
    		centerPanel.add(TextList.get(0));
    		centerPanel.setBackground(new Color(127,235,255));
	    	
	    	makeButtonLP(buttonTakaisin);
  		}
  		if(e.getSource() == asetuksetButton) {
	    	//asetukset
  			removeTextAndButtonsAndReturn();
  			clearButtons();
  			makeButtonLP(peruVarauksetButton);
  			makeButtonLP(buttonMuutaOsoite);
  			makeButtonLP(buttonMuutaCCNumber);
  			makeButtonLP(buttonMuutaSalasana);
  			makeButtonLP(buttonTakaisin);
  			
  		}
  		if(e.getSource() == peruVarauksetButton) {
  			//lis�� t�h�n rahantakaisinsaanti
  			for(int j=0;j<user.getVaraukset().size();j++){
  				user.addMoney(5.0);
  			}
	    	user.clearVaraukset();
  		}
  		if(e.getSource() == addFunds5) {
	    	user.addMoney(5.0);
	    	TextList.get(0).setText("           Wallet: " + user.getLompakko() + "e");
  		}
  		if(e.getSource() == addFunds25) {
  			user.addMoney(25.0);
  			TextList.get(0).setText("           Wallet: " + user.getLompakko() + "e");
  		}
  		if(e.getSource() == addFunds100) {
  			user.addMoney(100.0);
  			TextList.get(0).setText("           Wallet: " + user.getLompakko() + "e");
  		}
  		if(e.getSource() == buttonMuutaOsoite){
  			
  		}
  		if(e.getSource() == buttonMuutaCCNumber){
  			
  		}
		if(e.getSource() == buttonMuutaSalasana){
		
		}
  	    
  	    if(e.getSource() == hyvaksyVarausButton) {
  	    	chosenTrain=getChosenTrain();
  	    	TrainSeat chosenSeat = varaaPaikka();
  	    	if(chosenSeat.paikkaOnOikeastiVarattu()){
  	    		if(confirm()){
  	    			user.removeMoney(calcPrice());
  	    			user.setVaraus((String) reittiBox.getSelectedItem() + " " + (String) paivamaaraBox1.getSelectedItem() +  " " + (String) paivamaaraBox2.getSelectedItem() + "ta" + "Paikka: " + chosenSeat.getPaikka());
  	    			removeTextAndButtonsAndReturn();
  	    		}
  	    	}
  	    	
		}
  	  	if(e.getSource() == peruVarausButton) {
	    	removeTextAndButtonsAndReturn();
  		}
  	 	if(e.getSource() == hyvaksyJunanLuominenButton){
  	 		trainList.add(new Train((String) junanNimiBox.getSelectedItem() + ": " + (String) junanLahtoPysakkiBox.getSelectedItem() + "-" + (String) junanSaapumisPysakkiBox.getSelectedItem() + " " + (String) junanLahtoAikaBox.getSelectedItem(), (Double) junanHintaBox.getSelectedItem()));
  	    	removeTextAndButtonsAndReturn();
		}
  	  	if(e.getSource() == peruJunanLuominenButton){
	    	removeTextAndButtonsAndReturn();
		}
  	  	if(e.getSource() == hyvaksyJunanPoistaminenButton){
  	  		chosenTrain=getChosenTrain();
  	  		trainList.remove(chosenTrain);
  	  		removeTextAndButtonsAndReturn();
		}
	  	if(e.getSource() == peruJunanPoistaminenButton){
	  		removeTextAndButtonsAndReturn();
		}
  	    
  	    
  	    if(e.getSource() == buttonTakaisin) {
  	    	removeTextAndButtonsAndReturn();
  	    }
  	   }
  	}
  
  	
}
