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

import javax.swing.JButton;
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
	private JLabel SeatText = new JLabel();
	
	private JButton confirmButton = new JButton("VAHVISTA");
	private JButton cancelButton = new JButton("PERU");
	
	ButtonListener buttonlistener = new ButtonListener();
	
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
		
		GridLayout gridTop= new GridLayout(0,1);
		chooseSeatPanel.setLayout(gridTop);
		chooseSeatPanel.setBackground(Color.WHITE);
		chooseSeatPanel.setPreferredSize(new Dimension(900,400));
		dialog.add(chooseSeatPanel, BorderLayout.CENTER);
		
		SeatText.setText("Junan paikanvaraus tähän, Eli paikanVarausScreenin pitää visualisoida paikan varaus, varata paikka junasta ja palautetussa trainseat oliossa olla varattu paikka");
		SeatText.setFont(new Font("Tahoma", Font.BOLD, 12));
		chooseSeatPanel.add(SeatText);
		
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
  	    	
  	    	trainseat.setPaikanVaraus(true);
  	    	dialog.setVisible(false);
  	    }
  	    if(e.getSource() == cancelButton) {
  	    	trainseat.setPaikanVaraus(false);
  	    	dialog.setVisible(false);
	    }
  	   }
  	    
	}

}
