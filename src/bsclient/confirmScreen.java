package bsclient;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class confirmScreen {

	private JLabel text = new JLabel();
	private JButton confirmButton = new JButton("VAHVISTA");
	private JButton cancelButton = new JButton("PERU");
	JDialog dialog;
	JPanel mainPanel = new JPanel();
	private boolean accepted = false;
	ButtonListener buttonlistener = new ButtonListener();
	
	public confirmScreen(Frame frame) {
		dialog = new JDialog (frame, "Vahvista varaus");
		dialog.setSize(800, 500);
	}
	public boolean confirm(String ScreenText){
		dialog.setModal (true);
		dialog.setAlwaysOnTop (true);
		dialog.setModalityType (ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.add(mainPanel);
		text.setText(ScreenText);
		mainPanel.add(text);
		confirmButton.addActionListener(buttonlistener);
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
