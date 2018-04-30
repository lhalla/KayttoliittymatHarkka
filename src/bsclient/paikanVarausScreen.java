package bsclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import bsshared.*;

public class paikanVarausScreen {
	JDialog dialog;
	JPanel acceptButtonsPanel = new JPanel();
	JPanel chooseSeatPanel = new JPanel();
	
	//label korvattava junien paikanvarauksella!!!!
	private JLabel seatChooser = new JLabel();
	private ArrayList<JCheckBox> boxList = new ArrayList<JCheckBox>();
	
	private JButton confirmButton = new JButton("VAHVISTA");
	private JButton cancelButton = new JButton("PERU");
	
	ButtonListener buttonlistener = new ButtonListener();
	
	String rivi="invalid";
	String column="invalid";
	
	Train train;
	TrainSeat trainseat;

	public paikanVarausScreen(Frame frame, Train train) {
		dialog = new JDialog (frame, "Varaa paikka");
		dialog.setSize(900, 600);
		this.train=train;
		trainseat=new TrainSeat();
	}
	//hyväksyvarausbutton johtaa tähän metodiin, ja siellä loppujen lopuksi päivitetään 
	public TrainSeat varaa(){
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setModal (true);
		dialog.setAlwaysOnTop (true);
		dialog.setModalityType (ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.setResizable(false);
		
		GridLayout gridBot= new GridLayout(0,1);
		acceptButtonsPanel.setLayout(gridBot);
		acceptButtonsPanel.setBackground(Color.WHITE);
		acceptButtonsPanel.setPreferredSize(new Dimension(900,200));
		dialog.add(acceptButtonsPanel, BorderLayout.SOUTH);
		
		GridLayout gridTop= new GridLayout(2,10);
		chooseSeatPanel.setLayout(gridTop);
		chooseSeatPanel.setBackground(Color.WHITE);
		chooseSeatPanel.setPreferredSize(new Dimension(900,400));
		dialog.add(chooseSeatPanel, BorderLayout.CENTER);
		
		
		//makes 20 seats to choose from
		for(int seats=0;seats<20;seats++){
			boxList.add(new JCheckBox());
			chooseSeatPanel.add(boxList.get(seats));
			
		}
		
		
		confirmButton.setBackground(new Color(50, 200, 45));
		if(!train.areSeatsLeft())confirmButton.setBackground(Color.red);
		confirmButton.setForeground(Color.WHITE);
		confirmButton.setFocusPainted(false);
		confirmButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        cancelButton.setBackground(new Color(50, 200, 45));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		if(train.areSeatsLeft())confirmButton.addActionListener(buttonlistener);
		cancelButton.addActionListener(buttonlistener);
		acceptButtonsPanel.add(confirmButton);
		acceptButtonsPanel.add(cancelButton);
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.dispose();
		return trainseat;
		
		
	}
	
	
	
	private class ButtonListener implements ActionListener{
  	    public void actionPerformed(ActionEvent e) {
  	    if(e.getSource() == confirmButton) {
  	    	//TODO: aseta paikan muut ominaisuudet
  			for(int seat=1;seat<21;seat++){
  					if(boxList.get(seat - 1).isSelected()){
  						rivi = "" + (seat/11 + 1);
  						if(seat < 11)
  						column = "" + seat;
  						else 
  						column = "" + (seat-10);
  					}
  				
  	    	}
  			if(rivi!="invalid"){
  	    	trainseat.setPaikka("Rivi: " + rivi + "Paikka: " + column);
  	    	trainseat.setPaikanVaraus(true);
  	    	dialog.setVisible(false);
  			}
  	    }
  	    if(e.getSource() == cancelButton) {
  	    	trainseat.setPaikanVaraus(false);
  	    	dialog.setVisible(false);
	    }
  	   }
  	    
	}

}
