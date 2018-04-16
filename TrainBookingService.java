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


 

 

public class TrainBookingService {

	private boolean isAdmin;

    private JFrame frame;

    private final int basewidth=200;
    private final int baseheight=200;
    
    private boolean inVaraukset=false;
    
    private ButtonListener bl;
    private ArrayList<JButton> ButtonList = new ArrayList<JButton>();
    
    
    //panels here
    //!paneeleissa ei saa k‰ytt‰‰ boxlayout

    private JPanel leftPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JLabel image = new JLabel();

    //buttons here
    private JButton button1 = new JButton("VARAA");
    private JButton button2 = new JButton("VARAUKSENI");
    private JButton button3 = new JButton("POISTU");
    
    private JButton button4 = new JButton("adminbutton 1");
    private JButton button5 = new JButton("adminbutton 2");
    private JButton button6 = new JButton("adminbutton 3");
    
    private JButton buttonVaraa1 = new JButton("matka helsinkiin");
    private JButton buttonVaraa2 = new JButton("matka turkuun");
    private JButton buttonVaraa3 = new JButton("matka tampereelle");
    
    private JButton buttonTakaisin = new JButton("TAKAISIN");
    
    
    public TrainBookingService(JFrame frame) {

    	this.frame=frame;
      	isAdmin=false;
    	}

    public JFrame makeWindow() {

    frame.getContentPane().setLayout(new BorderLayout());
    
    GridLayout gridLeft = new GridLayout(8,1);
    GridLayout gridTop = new GridLayout(1,5);
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
    centerPanel.setMaximumSize(new Dimension(4*basewidth,4*baseheight));
    centerPanel.setMinimumSize(new Dimension(4*basewidth,4*baseheight));
    
    //Button initialization
  	bl = new ButtonListener();
                                

    //image
    createImage("https://images.cdn.yle.fi/image/upload//w_1199,h_1307,f_auto,fl_lossy,q_auto/13-3-6716387.jpg");

    
    //addbuttons and labels here
    centerPanel.add(image);
    makeButtonLP(button1);
    makeButtonLP(button2);
    makeButtonLP(button3);
    //topPanel
    makeButtonTP(button4);
    makeButtonTP(button5);
    makeButtonTP(button6);
    
    
    
    frame.add(centerPanel, BorderLayout.CENTER);
    frame.add(topPanel, BorderLayout.NORTH);
    frame.add(leftPanel, BorderLayout.WEST);
    
    return frame;

    }
    
    
    private Image stretchImage(Image srcImg){
		BufferedImage resizedImg = new BufferedImage(4*basewidth, 4*baseheight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, 4*basewidth, 4*baseheight, null);
	    g2.dispose();
	    return resizedImg;
	}
    
    
    //these initialize the buttons
    private void makeButtonLP(JButton button){
    	button.setBackground(new Color(50, 200, 45));
    	if(button == buttonTakaisin)button.setBackground(Color.RED);
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
    private void createImage(String URLforImage){
    	try {
        	image.setIcon(new ImageIcon(stretchImage(new ImageIcon(new URL(URLforImage)).getImage())));
        	}catch(Exception e) {}
    }
    
    
    
    private void varauksetMetodi(){
    	image.setIcon(null);
    	image.setText("Varauksesi lukevat t‰ss‰: ");
    	
    	//varaukset here
    	
    	centerPanel.setBackground(Color.BLACK);
    	image.setForeground (Color.green);
    }
    
    
   
    
    
    
  //each button press creates an action
  	private class ButtonListener implements ActionListener{
  	    public void actionPerformed(ActionEvent e) {
  	    if(e.getSource() == button1) {
  	    	//varaa
  	    	clearButtons();
  	    	makeButtonLP(buttonVaraa1);
  	    	makeButtonLP(buttonVaraa2);
  	    	makeButtonLP(buttonVaraa3);
  	    	makeButtonLP(buttonTakaisin);
  	    }
  	    if(e.getSource() == button2) {
  	    	//varaukset
  	    	inVaraukset=true;
  	    	clearButtons();
  	    	makeButtonLP(buttonTakaisin);
  	    	varauksetMetodi();
  	    }
  	    if(e.getSource() == button3) {
  	    	//logout
  	    	frame.dispose();
	    }
  	    if(e.getSource() == button4) {
  	    	//doStuff()
	    }
  	    if(e.getSource() == button5) {
  	    	//doStuff()
	    }
  	    if(e.getSource() == button6) {
  	    	//doStuff()
	    }
  	    if(e.getSource() == buttonVaraa1) {
	    	//doStuff()
	    }
  	    if(e.getSource() == buttonVaraa2) {
	    	//doStuff()
  	    }
  	    if(e.getSource() == buttonVaraa3) {
	    	//doStuff()
  	    }
  	    if(e.getSource() == buttonTakaisin) {
	    	clearButtons();
  	    	makeButtonLP(button1);
  	    	makeButtonLP(button2);
  	    	makeButtonLP(button3);
  	    	if(inVaraukset){
  	    		image.setText(null);
  	    		image.setForeground(Color.WHITE);
  	    		centerPanel.setBackground(Color.WHITE);
  	    	}
  	    	createImage("https://images.cdn.yle.fi/image/upload//w_1199,h_1307,f_auto,fl_lossy,q_auto/13-3-6716387.jpg");
  	    }  	  
  	}
  	}
}
