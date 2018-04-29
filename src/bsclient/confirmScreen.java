package bsclient;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class confirmScreen {

	private JLabel RouteText = new JLabel();
	private JLabel PriceText = new JLabel();
	private JLabel textSeats = new JLabel();
	private JButton confirmButton = new JButton("VAHVISTA");
	private JButton cancelButton = new JButton("PERU");
	JDialog dialog;
	JPanel mainPanel = new JPanel();
	private boolean accepted = false;
	ButtonListener buttonlistener = new ButtonListener();
	
	
	public confirmScreen(Frame frame) {
		dialog = new JDialog (frame, "Vahvista varaus");
		dialog.setSize(500, 500);
	}
	/**
	 * Return true if user clicks confirm otherwise false
	 * user cant click confirm if he/she doesnt have enough money for the trip
	 * no fixes needed.
	 */
	public boolean confirm(String ScreenRouteText,String ScreenPriceText, boolean canAfford){
		dialog.setModal (true);
		dialog.setAlwaysOnTop (true);
		dialog.setModalityType (ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		GridLayout grid= new GridLayout(0,1);
		mainPanel.setLayout(grid);
		mainPanel.setBackground(Color.WHITE);
		dialog.add(mainPanel);
		
		RouteText.setText(ScreenRouteText);
		RouteText.setFont(new Font("Tahoma", Font.BOLD, 12));
		mainPanel.add(RouteText);
		PriceText.setText(ScreenPriceText);
		PriceText.setFont(new Font("Tahoma", Font.BOLD, 12));
		if(!canAfford)PriceText.setForeground(Color.red);
		mainPanel.add(PriceText);
		
		confirmButton.setBackground(new Color(50, 200, 45));
		if(!canAfford)confirmButton.setBackground(Color.red);
		confirmButton.setForeground(Color.WHITE);
		confirmButton.setFocusPainted(false);
		confirmButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        cancelButton.setBackground(new Color(50, 200, 45));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		if(canAfford)confirmButton.addActionListener(buttonlistener);
		cancelButton.addActionListener(buttonlistener);
		mainPanel.add(confirmButton);
		mainPanel.add(cancelButton);
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.dispose();
		return accepted;
	}
	private class ButtonListener implements ActionListener{
  	    public void actionPerformed(ActionEvent e) {
  	    if(e.getSource() == confirmButton) {
  	    	accepted=true;
  	    	dialog.setVisible(false);
  	    }
  	    if(e.getSource() == cancelButton) {
  	    	accepted=false;
  	    	dialog.setVisible(false);
	    }
  	   }
  	    
	}

}
