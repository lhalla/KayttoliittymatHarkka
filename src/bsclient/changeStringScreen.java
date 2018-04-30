package bsclient;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class changeStringScreen {
	JDialog dialog;
	JPanel mainPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	
	ButtonListener buttonlistener = new ButtonListener();
	private JButton confirmButton = new JButton("VAHVISTA");
	private JButton cancelButton = new JButton("PERU");
	
	private String changedString = "";
	JTextField changer;
	
	public changeStringScreen(Frame frame, String message1) {
		dialog = new JDialog (frame, message1);
		dialog.setSize(500, 500);
	}
	public String changeText(String message){
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setModal (true);
		dialog.setAlwaysOnTop (true);
		dialog.setModalityType (ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.setResizable(false);
		GridLayout grid= new GridLayout(0,1);
		mainPanel.setLayout(grid);
		mainPanel.setBackground(Color.WHITE);
		dialog.add(mainPanel);
		
		JLabel messageLabel= new JLabel();
		messageLabel.setText(message);
		mainPanel.add(messageLabel);
		
		changer=new JTextField();
		mainPanel.add(changer);
		
		confirmButton.setBackground(new Color(50, 200, 45));
		confirmButton.setForeground(Color.WHITE);
		confirmButton.setFocusPainted(false);
		confirmButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        cancelButton.setBackground(new Color(50, 200, 45));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		confirmButton.addActionListener(buttonlistener);
		cancelButton.addActionListener(buttonlistener);
		mainPanel.add(confirmButton);
		mainPanel.add(cancelButton);
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.dispose();
		return changedString;
	}
	private class ButtonListener implements ActionListener{
  	    public void actionPerformed(ActionEvent e) {
  	    if(e.getSource() == confirmButton) {
  	    	changedString=changer.getText();
  	    	dialog.setVisible(false);
  	    }
  	    if(e.getSource() == cancelButton) {
  	    	dialog.setVisible(false);
	    }
  	   }
  	    
	}

}
