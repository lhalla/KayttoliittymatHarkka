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
	
	int rivi=-1;
	int column=-1;
	
	Train train;
	TrainSeat trainseat;
	String username;

	public paikanVarausScreen(Frame frame, Train train, String userName) {
		dialog = new JDialog (frame, "Varaa paikka");
		dialog.setSize(800, 450);
		this.train=train;
		this.username=userName;
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
		acceptButtonsPanel.setPreferredSize(new Dimension(800,150));
		dialog.add(acceptButtonsPanel, BorderLayout.SOUTH);
		
		GridLayout gridTop= new GridLayout(2,10);
		chooseSeatPanel.setLayout(gridTop);
		chooseSeatPanel.setBackground(Color.WHITE);
		chooseSeatPanel.setPreferredSize(new Dimension(800,300));
		dialog.add(chooseSeatPanel, BorderLayout.CENTER);
		
		//poista!!
		train.seats[0][0]="remes";
		
		//makes 20 seats to choose from
		for(int seats=0;seats<20;seats++){
			boxList.add(new JCheckBox());
			if(seats==9 || seats==19)boxList.get(seats).setBackground(Color.blue);
			if(train.seats[(seats%10)][seats/10] != null){
				if(!train.seats[(seats%10)][seats/10].equals("")){
					boxList.get(seats).setBackground(Color.red);
					boxList.get(seats).setForeground(Color.white);
					boxList.get(seats).setText(train.seats[(seats%10)][seats/10]);
					boxList.get(seats).setEnabled(false);
				}
			}
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
  			for(int seat=0;seat<20;seat++){
  					if(boxList.get(seat).isSelected()){
  						rivi = (seat/10);
  						column = seat%10;
  						
  					}
  				
  	    	}
  			if(rivi!=-1){
  	    	trainseat.setPaikka("Rivi: " + (rivi+1) + "Paikka: " + (column+1));
  	    	trainseat.setRow(rivi);
  	    	trainseat.setColumn(column);
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
