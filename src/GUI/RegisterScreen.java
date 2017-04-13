package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Create registration panel.
 * @author Phuc Tran
 * @author Aygun Avazova
 * @version 14 Mar 2017
 */
public class RegisterScreen implements ActionListener {
	
	private static String welcomeBanner = "Member Registration";
	private static Font details = new Font("Tahoma", Font.PLAIN, 15);
	private static Font welcome = new Font("Tahoma", Font.BOLD, 22);
	private int WINDOWWIDTH = 800;
	private int WINDOWHEIGHT = 500;
	private int baseX = 60;
	private int baseY = 50;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

	private JPanel contentPane;
	private JTextField firstNameText;
	private JTextField lastNameText;
	private JTextField usernameText;
	private JPasswordField passwordText;
	private JPasswordField confirmPassText;
	private JTextField emailText;
	private JButton cancelButton, registerButton;
	private String UserType;
	protected JFrame myFrame;


	/**
	 * Create the frame.
	 */
	public RegisterScreen() {
		
		myFrame = new JFrame("Meeting Scheduler - Registration");
		contentPane = new JPanel();
		contentPane.setLayout(null);
	}
	
	/**
	 * setup and execute the registration panel
	 */
	public void run(){
		
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setBounds(0, 0, WINDOWWIDTH, WINDOWHEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		myFrame.setContentPane(contentPane);
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		contentPane.setLayout(null);
		
		addRegisterOptionButton();
		addRegisterButton();
		addCancelButton();
		myFrame.setVisible(true);
	}
	
	/**
	 * setup panel
	 */
	private void addRegisterOptionButton(){
		JLabel lblWelcomeBanner = new JLabel(welcomeBanner);
		lblWelcomeBanner.setFont(welcome);
		lblWelcomeBanner.setHorizontalAlignment(SwingConstants.LEFT);
		lblWelcomeBanner.setBounds(baseX + 160, baseY - 40, 400, 20);
		myFrame.getContentPane().add(lblWelcomeBanner);
		
		JLabel label = new JLabel("What would you like to register as: ");		
		label.setFont(details);
		label.setBounds(baseX + 125,baseY + 13, 265,20);
		contentPane.add(label);
		
		JButton admin = new JButton("admin");
		admin.setFont(details);
		admin.setBounds(baseX + 80,baseY + 50,154,23);
		admin.setToolTipText("Click here to register as an admin.");
		contentPane.add(admin);
		admin.addActionListener(this);
		
		JButton patient = new JButton("patient");
		patient.setFont(details);
		patient.setBounds(baseX + 260, baseY + 50, 154, 23);
		patient.setToolTipText("Click here to register as a patient.");
		contentPane.add(patient);
		patient.addActionListener(this);
		
		JButton counselor = new JButton("counselor");
		counselor.setFont(details);
		counselor.setBounds(baseX + 440, baseY + 50 , 154, 23);
		counselor.setToolTipText("Click here to register as a counselor.");
		contentPane.add(counselor);
		counselor.addActionListener(this);
		
	}
	
	/**
	 * setup panel
	 */
	private void addRegisterPanel(){
		contentPane.removeAll();
		
		JLabel currentUser = new JLabel("You are registering as " + UserType);
		currentUser.setFont(details);
		currentUser.setBounds(baseX + 360,baseY + 13, 264,20);
		contentPane.add(currentUser);
		
		addRegisterOptionButton();
		addRegisterButton();
		addCancelButton();
		
		JLabel label2 = new JLabel("Please enter the following information:");
		label2.setFont(details);
		label2.setBounds(baseX + 125, baseY + 113, 300, 20);
		contentPane.add(label2);
		
		JLabel nameLabel = new JLabel("First Name: ");
		nameLabel.setFont(details);
		nameLabel.setBounds(baseX + 125, baseY + 144, 90, 14);
		contentPane.add(nameLabel);
		
		JLabel usernameLabel = new JLabel("Last Name: ");
		usernameLabel.setFont(details);
		usernameLabel.setBounds(baseX + 125, baseY + 174, 114, 14);
		contentPane.add(usernameLabel);
		
		firstNameText = new JTextField();
		firstNameText.setBounds(baseX + 230, baseY + 143, 185, 20);
		contentPane.add(firstNameText);
		firstNameText.setColumns(10);
		contentPane.add(firstNameText);
		
		usernameText = new JTextField();
		usernameText.setColumns(10);
		usernameText.setBounds(baseX + 230, baseY + 199, 185, 20);		
		contentPane.add(usernameText);
	
		
		passwordText = new JPasswordField();
		passwordText.setColumns(10);
		passwordText.setBounds(baseX + 230, baseY + 229, 185, 20);
		contentPane.add(passwordText);
	
		JLabel phoneLabel = new JLabel("Password: ");
		phoneLabel.setFont(details);
		phoneLabel.setBounds(baseX + 125, baseY + 230, 104, 14);
		contentPane.add(phoneLabel);
		
		JLabel addressLabel = new JLabel("UserName:");
		addressLabel.setFont(details);
		addressLabel.setBounds(baseX + 125, baseY + 200, 83, 14);
		contentPane.add(addressLabel);
		
		confirmPassText = new JPasswordField();
		confirmPassText.setColumns(10);
		confirmPassText.setBounds(baseX + 230, baseY + 260, 185, 20);	
		contentPane.add(confirmPassText);
		
		lastNameText = new JTextField();
		lastNameText.setColumns(10);
		lastNameText.setBounds(baseX + 230, baseY + 173, 185, 20);
		contentPane.add(lastNameText);
		
		JLabel paymentLabel = new JLabel("Email: ");
		paymentLabel.setFont(details);
		paymentLabel.setBounds(baseX + 126, baseY + 289, 104, 19);
		contentPane.add(paymentLabel);
		
		JLabel emailLabel = new JLabel("Confirm Pass: ");
		emailLabel.setFont(details);
		emailLabel.setBounds(baseX + 125, baseY + 261, 83, 14);
		contentPane.add(emailLabel);
		
		emailText = new JTextField();
		emailText.setColumns(10);
		emailText.setBounds(baseX + 230, baseY + 290, 185, 20);	
		contentPane.add(emailText);
	}
	

	/**
	 * add register button
	 * when user click on this button it will check the database and save the information
	 */
	private void addRegisterButton(){
		registerButton = new JButton("Register");
		registerButton.setBounds(baseX + 124, baseY + 327, 154,23);
		registerButton.setToolTipText("Click here to register.");
		registerButton.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (!usernameText.getText().equals("") 
						&& passwordText.getPassword().length > 0
						&& (passwordText.getText().equals(confirmPassText.getText()))){
					
					try {
						addUser (usernameText.getText(), passwordText.getText());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please check your inputs");
				}

			}
		});
		contentPane.add(registerButton);
	}
	
	/**
	 * add Cancel button
	 * take the user back the home screen when clicked
	 */
	private void addCancelButton(){
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(baseX + 305, baseY + 327, 154,23);
        cancelButton.addActionListener(this);
		contentPane.add(cancelButton);
		cancelButton.setToolTipText("Click here to return to main menu.");
	}
	
	/**
	 * take user back to home screen
	 */
	@Override
	public void actionPerformed(ActionEvent theEvent) {

			UserType = theEvent.getActionCommand();

			addRegisterPanel();

	        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
	                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
			myFrame.repaint();
		
		if (theEvent.getActionCommand().equals("Cancel")){
			myFrame.dispose();
			contentPane.removeAll();
		}
	}
	
	/**
	 * there are 3 tables for user in our data base
	 * when register, this methods will save the user to appropriate user table
	 * @param tableName
	 * @throws SQLException
	 */
	private void addToOtherTable(String tableName)
		    throws SQLException {
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	 
	    String query = "INSERT INTO " + tableName +" VALUES ('" + usernameText.getText() +
	    		"', '" + firstNameText.getText() + 
	    		"', '" + lastNameText.getText() +
	    		"', '" + emailText.getText() + "');";
	    try {
	    	stmt = con.createStatement();
	    	stmt.executeUpdate(query);
	    	
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * save user to database
	 * @param theID username
	 * @param thePass password
	 * @throws SQLException
	 */
	private void addUser(String theID, String thePass)
		    throws SQLException {
		
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	    String queryCheck = "select * from User where user_id='" + theID + "';";
	    String query = "INSERT INTO User VALUES ('" + theID + "' ,'" + thePass + "', '" + UserType + "');";
	    try {
	    	stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery(queryCheck);
	        if (rs.next()) {
	        	JOptionPane.showMessageDialog(null, "UserID already exist, please choose another Username");
	        }else {
	        	JOptionPane.showMessageDialog(null, "Congratulation, You just register as " + UserType);
	        	myFrame.dispose();
	        	stmt.executeUpdate(query);
	        	if (UserType.equals("counselor")){
	        		addToOtherTable("Counselor");
	        	} else {
	        		addToOtherTable("Patient");
	        	}
	        }
	        
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
}
