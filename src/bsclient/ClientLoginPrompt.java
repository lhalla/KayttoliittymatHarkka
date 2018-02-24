package bsclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientLoginPrompt extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private final JFrame owner;
	
	private final JLabel labelUser = new JLabel("Username");
    private final JLabel labelPass = new JLabel("Password");

    private final JTextField fieldUser = new JTextField(15);
    private final JPasswordField fieldPass = new JPasswordField();

    private final JButton buttonLogin = new JButton("Login");
    private final JButton buttonNewUser = new JButton("New User");
    private final JButton buttonCancel = new JButton("Cancel");

    private final JLabel labelStatus = new JLabel(" ");
    
    private Client client;

    /**
     * Constructor.
     */
    public ClientLoginPrompt()
    {
        this(null, true);
    }

    /**
     * Constructor.
     * @param owner owner of this prompt.
     * @param modal
     */
    public ClientLoginPrompt(final JFrame owner, boolean modal)
    {
        super(owner, modal);

        this.owner = owner;
        
        // 2x1 JPanel containing the labels for the fields.
        JPanel panelLabels = new JPanel(new GridLayout(2, 1));
        panelLabels.add(labelUser);
        panelLabels.add(labelPass);

        // 2x1 JPanel containing the fields for username and password.
        JPanel panelFields = new JPanel(new GridLayout(2, 1));
        panelFields.add(fieldUser);
        panelFields.add(fieldPass);

        // 2x2 JPanel containing the label and panel fields next to each other. 
        JPanel panelCenter = new JPanel();
        panelCenter.add(panelLabels);
        panelCenter.add(panelFields);

        // 1x3 JPanel containing the buttons.
        JPanel panelButtons = new JPanel();
        panelButtons.add(buttonLogin);
        panelButtons.add(buttonNewUser);
        panelButtons.add(buttonCancel);

        // JPanel containing a status label for informing the user about errors
        // and the buttons.
        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.add(panelButtons, BorderLayout.CENTER);
        panelBottom.add(labelStatus, BorderLayout.NORTH);
        
        // Set the format for the status message.
        labelStatus.setForeground(Color.RED);
        labelStatus.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the center and bottom panels into the frame.
        setLayout(new BorderLayout());
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Exit the program if the window is closed.
        addWindowListener(new WindowAdapter()
        {  
            @Override
            public void windowClosing(WindowEvent e)
            {  
                System.exit(0);  
            }  
        });

        // Create a new client and try to start it.
        client = new Client((ClientGUI) owner);
        if (!client.start())
        	labelStatus.setText("Failed to connect to the server.");

        // Add action listeners to Login, New User and Cancel buttons.
        buttonLogin.addActionListener(this);
        buttonNewUser.addActionListener(this);
        buttonCancel.addActionListener(this);
    }
    
    /**
     * Listens for button presses.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	Object o = e.getSource();
    	
    	// Login button has been pressed.
    	if (o == buttonLogin)
    	{
    		// Get the username and password from their respective fields.
    		String username = String.valueOf(fieldUser.getText());
    		String password = String.valueOf(fieldPass.getPassword());
    		
    		// Authenticate the credentials.
    		boolean res = client.authenticate(username, password);
    		
    		// If successful, open the main frame.
    		if (res)
    		{
    			((ClientGUI) owner).client = client;
    			owner.setVisible(true);
    			setVisible(false);
    		}
    		// Otherwise inform the user of invalid credentials.
    		else
    		{
    			labelStatus.setText("Invalid username or password");
    			fieldUser.setText("");
    			fieldPass.setText("");
    		}
    	}
    	
    	// New User button has been pressed.
    	if (o == buttonNewUser)
    	{
    		// Get the username and password from their respective fields.
    		String username = String.valueOf(fieldUser.getText());
    		String password = String.valueOf(fieldPass.getPassword());
    		
    		// Attempt to create a new user with the credentials.
    		boolean res = client.createNewUser(username, password);
    		
    		// If successful, open the main frame.
    		if (res)
    		{
    			((ClientGUI) owner).client = client;
    			owner.setVisible(true);
    			setVisible(false);
    		}
    		// Otherwise inform the user that the username was taken.
    		else
    		{
    			labelStatus.setText("Username already taken.");
    			fieldUser.setText("");
    			fieldPass.setText("");
    		}
    	}
    	
    	// Cancel button has been pressed.
    	if (o == buttonCancel)
    	{
    		client.logout();
    		
    		// Hide the login prompt.
    		setVisible(false);
    		
    		// Dispose of the main frame.
    		owner.dispose();
    		
    		// Exit.
    		System.exit(0);
    	}
    }
}