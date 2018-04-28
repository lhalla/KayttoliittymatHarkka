package bsclient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import bsshared.*;

public class paikanVarausScreen {
	JDialog dialog;
	JPanel mainPanel = new JPanel();
	
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
	
	public TrainSeat varaa(){
		dialog.setModal (true);
		dialog.setAlwaysOnTop (true);
		dialog.setModalityType (ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		GridLayout grid= new GridLayout(0,1);
		mainPanel.setLayout(grid);
		mainPanel.setBackground(Color.WHITE);
		dialog.add(mainPanel);
		
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
		mainPanel.add(confirmButton);
		mainPanel.add(cancelButton);
		
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
