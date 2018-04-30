package bsclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import bsshared.*;

public class TrainBookingService {
	
	private User user;
	
	// List of trains and their properties.
	private ArrayList<Train> trainList;
	
	// The train/seat that user is currently trying to book
	private Train chosenTrain;
	private TrainSeat chosenSeat;
	
	// Checks if the user is admin
	private boolean isAdmin;
	
	private Client client;

    private JFrame frame;

    private final int basewidth=200;
    private final int baseheight=200;
    
    // Boolean that determines whether the image is the main menu image or not
    private boolean inSecondScreen=false;
    
    private ButtonListener bl;
    private ArrayList<JButton> ButtonList = new ArrayList<JButton>();
    private ArrayList<JLabel> TextList = new ArrayList<JLabel>();
    private ArrayList<JComboBox<String>> BoxList = new ArrayList<JComboBox<String>>();
    
    // JPanels.
    private JPanel leftPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    
    // JLabel for the middle panel's image.
    private JLabel image = new JLabel();

    // JButtons.
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
    
    private JButton addFunds5 = new JButton("Lisää 5e");
    private JButton addFunds25 = new JButton("Lisää 25e");
    private JButton addFunds100 = new JButton("Lisää 100e");
    
    private JButton hyvaksyVarausButton = new JButton("Hyvaksy");
    private JButton peruVarausButton = new JButton("Peru");
    
  	
    // JComboBoxes for seat reservation.
    private JComboBox<String> paivamaaraBox1;
    private JComboBox<String> paivamaaraBox2;
    private JComboBox<String> reittiBox;
    private JComboBox<String> junanNimiBox;
    private JComboBox<String> junanLahtoPysakkiBox;
    private JComboBox<String> junanSaapumisPysakkiBox;
    private JComboBox<String> junanLahtoAikaBox;
    private JComboBox<Double> junanHintaBox;
    
    private JButton buttonTakaisin = new JButton("TAKAISIN");
    
    public TrainBookingService(JFrame frame)
    {
    	this.frame=frame;
    	
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
    	trainList = client.trainList;
   	}
    
    /**
	 * Makes the frame and layout for TrainBookingService.
	 */
    public JFrame makeWindow() {
    
    frame.getContentPane().setLayout(new BorderLayout());
    
    GridLayout gridLeft = new GridLayout(8,1);
    GridLayout gridTop = new GridLayout(1,5);
    GridLayout gridMid = new GridLayout(0,1);
    frame.setSize(5*basewidth, 5*baseheight-baseheight/2);
    frame.setResizable(false);

    // Left panel.
    leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    leftPanel.setLayout(gridLeft);
    leftPanel.setMaximumSize(new Dimension(basewidth,4*baseheight));
    leftPanel.setMinimumSize(new Dimension(basewidth,4*baseheight));

    // Top panel.
    topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    topPanel.setLayout(gridTop);
    topPanel.setMaximumSize(new Dimension(5*basewidth,baseheight/2));
    topPanel.setMinimumSize(new Dimension(5*basewidth,baseheight/2));

    // Center panel.
    centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    centerPanel.setBackground(Color.WHITE);
    centerPanel.setLayout(gridMid);
    centerPanel.setMaximumSize(new Dimension(4*basewidth,4*baseheight));
    centerPanel.setMinimumSize(new Dimension(4*basewidth,4*baseheight));
    
    // Button initialization
  	bl = new ButtonListener();
                                
    // image
    createImage("https://images.cdn.yle.fi/image/upload//w_1199,h_1307,f_auto,fl_lossy,q_auto/13-3-6716387.jpg");
    
    // add buttons and labels here
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
	 * Stretches image for website X to fit JLabel image
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
	 * Initialises or removes a button in the left panel.
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
    
    /**
	 * Initialises or removes a button in the top panel.
	 */
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
	 * Adds a JComboBox to left panel.
	 */
    private void addBoxLP(JComboBox box){
    	leftPanel.add(box);
    	BoxList.add(box);
    }
    
    /**
	 * Clears buttons from left panel.
	 */
    private void clearButtons(){
		for(int i=0; i<ButtonList.size(); i++){
			ButtonList.get(i).setVisible(false);
			ButtonList.get(i).removeActionListener(bl);
			leftPanel.remove(ButtonList.get(i));
		}
		
		ButtonList.clear();
	}
    
    /**
	 * Clears boxes from left panel.
	 */
    private void clearBoxes(){
    	for(int x=0; x<BoxList.size(); x++){
			leftPanel.remove(BoxList.get(x));
		}
    	
		BoxList.clear();
    }
    
    /**
	 * Creates an image object that is fetched from the given URL.
	 */
    private void createImage(String URLforImage){
    	try
    	{
        	image.setIcon(new ImageIcon(stretchImage(new ImageIcon(new URL(URLforImage)).getImage())));
        }
    	catch(Exception e) {}
    }
    
    protected ArrayList<String> getVaraukset(){
    	return user.getVaraukset();
    }
    
    /**
     * Displays users current bookings on screen.
	 */
    private void varauksetMetodi(){
    	centerPanel.remove(image);
    	for(int i=0;i<user.getVaraukset().size();i++){
    		TextList.add(new JLabel());
    		TextList.get(i).setText("                                                            " + user.getVaraukset().get(i));
    		TextList.get(i).setFont(new Font("Tahoma", Font.BOLD,14));
    		TextList.get(i).setForeground(Color.BLACK);
    		centerPanel.add(TextList.get(i));
    	}
    	
    	centerPanel.setBackground(new Color(127,235,255));
    }
    
    /**
     * Displays users current money on screen, and makes buttons for adding money to it
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
     * this method uses JComboBoxes
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
     * Removes all current JButtons,JComboBoxes and JLabel text in center panel
     * returns user to main menu after
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
     * After user has chosen the seat for a route, a confirmation screen is opened
     * this method will check if user can afford the train ride and will ask if user is sure
	 */
    private boolean confirm(){
    	ConfirmScreen confirmer = new ConfirmScreen(frame);
    	double price = calcPrice();
    	boolean canAfford = user.canAfford(price);
    	boolean confirm = confirmer.confirm((String) reittiBox.getSelectedItem() + " " + (String) paivamaaraBox1.getSelectedItem() + (String) paivamaaraBox2.getSelectedItem() + "ta" + " Paikalla " + chosenSeat.getPaikka() , "Hinta: " + price + "e", canAfford);
    	return confirm;
    }
    
    /**
     * Once user has pressed the button that chooses the route and time for train this will be run
     * this opens up a new screen that will ask which seat would the user like
     * after seat has been chosen, returns a train seat object which tells booking service which seat user wishes to take
	 */
    private TrainSeat varaaPaikka(){
    	SeatReservationScreen paikanVaraaja = new SeatReservationScreen(frame,chosenTrain,user.getUsername());
    	TrainSeat varattuPaikka= paikanVaraaja.varaa();	
    	return varattuPaikka;
    }
    
    /**
     * calculates the price of the train route that is being booked
	 */
    private Double calcPrice(){	
    	return chosenTrain.getCost();
    }
    
    /**
     * returns the train object that user is trying to book
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
  	    	client.logout();
  	    	frame.dispose();
  	    	
	    }
  	  	if(e.getSource() == infoButton) {
  	  		removeTextAndButtonsAndReturn();
	    	clearButtons();
	    	inSecondScreen=true;
	    	centerPanel.remove(image);
	    	
	    	TextList.add(new JLabel());
	    	TextList.add(new JLabel());
	    	TextList.get(0).setText("Train Booking Service versio 1.0");
	    	TextList.get(0).setFont(new Font("Tahoma", Font.BOLD,12));
    		TextList.get(0).setForeground(Color.BLACK);
    		TextList.get(1).setText("Varauksissa siniset paikat ovat erikoispaikkoja esimerkiksi lemmikeille tai liikuntakyvyttömille");
	    	TextList.get(1).setFont(new Font("Tahoma", Font.BOLD,12));
    		TextList.get(1).setForeground(Color.BLACK);
    		centerPanel.add(TextList.get(0));
    		centerPanel.add(TextList.get(1));
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
  			changeStringScreen changer=new changeStringScreen(frame,"Change address");
  			user.setAddress(changer.changeText("Set your new address here"));
  		}
  		if(e.getSource() == buttonMuutaCCNumber){
  			changeStringScreen changer=new changeStringScreen(frame,"Change CCNum");
  			user.setAddress(changer.changeText("Set your new CCNum here"));
  		}
		if(e.getSource() == buttonMuutaSalasana){
			changeStringScreen changer=new changeStringScreen(frame,"Change Password");
  			user.setAddress(changer.changeText("Set your new Password here"));
		}
  	    
  	    if(e.getSource() == hyvaksyVarausButton) {
  	    	chosenTrain=getChosenTrain();
  	    	chosenSeat = varaaPaikka();
  	    	if(chosenSeat.paikkaOnOikeastiVarattu()){
  	    		if(confirm()){
  	    			user.removeMoney(calcPrice());
  	    			user.setVaraus((String) reittiBox.getSelectedItem() + " " + (String) paivamaaraBox1.getSelectedItem() +  " " + (String) paivamaaraBox2.getSelectedItem() + "ta" + " Paikka: " + chosenSeat.getPaikka());
  	    			chosenTrain.seats[chosenSeat.getColumn()][chosenSeat.getRow()]=user.getUsername();
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
