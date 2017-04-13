package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.JOptionPane;

import model.Patient;
import model.admin;

/**
 * Create Home Screen of application
 * @author Phuc Tran
 * @author Aygun Avazova
 * @version 14 Mar 2017
 */
public class HomeScreen implements ActionListener{
	
	
    private final static String USERNAME = "_450bteam11";
    private final static String PASSWORD ="Nimewg";
   
	private static String welcomeBanner = "Meeting Scheduler";
	private static Font details = new Font("Tahoma", Font.PLAIN, 15);
	private static Font welcome = new Font("Tahoma", Font.BOLD, 22);
	private int baseX = 200;
	private int baseY = 0;
	
	private JFrame myFrame;
	
	private JTextField userText;
	private JPasswordField passText;

	/**
	 * construct that takes in a Jframe to display
	 * @param frame
	 */
	public HomeScreen(JFrame frame){
		myFrame = frame;
	}

	/**
	 * helper method to start application
	 */
	public void startGUI() {

		myFrame.getContentPane().setLayout(null);

		addIdBox();
		addPassBox();
		addIdBoxLabel();
		addPassBoxLabel();
		addTitle();
		addLoginButton();
		addRegisterButton();
	}
	
	/**
	 * add label design for username
	 */
	private void addIdBoxLabel(){
		JLabel userLabel = new JLabel("Username");
		userLabel.setBounds(baseX + 20, baseY + 80, 80, 25);
		userLabel.setFont(details);
		myFrame.getContentPane().add(userLabel);
	}
	
	/**
	 * add Textfield for user_ID
	 */
	private void addIdBox(){
		userText = new JTextField(20);
		userText.setBounds(baseX + 110, baseY + 85, 160, 25);
		myFrame.getContentPane().add(userText);
	}
	
	/**
	 * add label design for password
	 */
	private void addPassBoxLabel(){
		JLabel userLabel = new JLabel("Password");
		userLabel.setBounds(baseX + 20, baseY + 110, 80, 25);
		userLabel.setFont(details);
		myFrame.getContentPane().add(userLabel);
	}
	
	/**
	 * add Password text box to panel
	 */
	private void addPassBox(){
		passText = new JPasswordField(20);
		passText.setBounds(baseX + 110, baseY + 115, 160, 25);	
		myFrame.getContentPane().add(passText);
	}
	
	/**
	 * Panel design
	 */
	private void addTitle(){
		JLabel lblWelcomeBanner = new JLabel(welcomeBanner);
		lblWelcomeBanner.setFont(welcome);
		lblWelcomeBanner.setHorizontalAlignment(SwingConstants.LEFT);
		lblWelcomeBanner.setBounds(baseX + 40, baseY + 10, 400, 25);
		myFrame.getContentPane().add(lblWelcomeBanner);
		
		JLabel lblYouAreSignedAs = new JLabel("Please sign in or register:");
		lblYouAreSignedAs.setFont(details);
		lblYouAreSignedAs.setHorizontalAlignment(SwingConstants.LEFT);
		lblYouAreSignedAs.setBounds(baseX + 20, baseY + 55, 300, 30);
		myFrame.getContentPane().add(lblYouAreSignedAs);
	}
	
	/**
	 * add login Button to the panel
	 */
	private void addLoginButton(){
		JButton loginButton = new JButton("login");
		loginButton.setBounds(baseX + 20, baseY + 145, 150, 25);
		loginButton.addActionListener(this);
		loginButton.setToolTipText("Enter username above and click here to login.");
		myFrame.getContentPane().add(loginButton);
		
	}
	
	/**
	 * add register button to the panel
	 * display register panel when clicked.
	 */
	private void addRegisterButton(){
		JButton registerButton = new JButton("register");
		registerButton.setBounds(baseX + 200, baseY + 145, 150, 25);
		myFrame.getContentPane().add(registerButton);
		registerButton.setToolTipText("Click here to begin registration.");
		registerButton.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	RegisterScreen RegisterPanel = new RegisterScreen();
            	RegisterPanel.run();
            }
        });
	}

	/**
	 * add action listener to login button
	 * display admin panel if user login as admin
	 * display counselor panel if user login as counselor
	 * display patient panel if user login as counselor
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {

		String userId = userText.getText();
		String pass =  passText.getText();
		String userType = "";
		
		try {
			userType = login(userId, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!userType.equals("")){
			System.out.println("You are login as: " + userType);
			userType = userType.toLowerCase();
			
			// take user to admin panel
			if (userType.equals("admin")){
				admin a = new admin (userId, pass, userType);
				myFrame.getContentPane().removeAll();
				adminGUI adminScreen = new adminGUI(myFrame, a);
				try {
					adminScreen.run();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
			
			// take user to counselor panel
			else if (userType.equals("counselor")){
				new CalendarFrame();
			}
			
			// take user to patient panel
			else if (userType.equals("patient")){
				Patient p = null;
				try {
					p = getPatient(userId);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				myFrame.getContentPane().removeAll();
				patientGUI patientScreen = new patientGUI(myFrame, p);
				
				try {
					patientScreen.run();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "wrong username or password");
			userText.setText("");
			passText.setText("");
		}

	}
	
	/**
	 * helper method that reach out Patient table in our database to
	 * get patient informations when user login as patient
	 * @param patientid patientid
	 * @return Patient Object
	 * @throws SQLException
	 */
	private Patient getPatient(String patientid) throws SQLException{
		Patient patient = null;
		Connection con = getConnection();
	    Statement stmt = null;
	    String query = "SELECT * FROM Patient WHERE patient_id = '" + patientid +"';";
	   
	    try {
	   
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        if (rs.next()) {
	            patient = new Patient(patientid
	            		, rs.getString("first_name")
	            		, rs.getString("last_name")
	            		, rs.getString("email"));
	        } else{
	        	System.out.println("this Patient is not found in our database");
	        }
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
		return patient;
	}

	/**
	 * this login method take in a username and password
	 * to check if the user exist on the database
	 * @param theID username
	 * @param thePass password
	 * @return the usertype
	 * @throws SQLException
	 */
	private String login(String theID, String thePass)
		    throws SQLException {
		
		String userType = "";
		Connection con = getConnection();
	    Statement stmt = null;
	    String query = "select type from User where user_id='" + theID + "' and `password`='" + thePass + "';";
	   
	    try {
	   
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        if (rs.next()) {
	            userType = rs.getString("type");
	        } else{
	        	userType = "";
	        }
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	    return userType;
	}
	
	/**
	 * this is project wide method, that create a connection to our database
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}
	    Connection conn = null;
	    
		try {
			conn = DriverManager
			.getConnection("jdbc:mysql://cssgate.insttech.washington.edu:3306/_450bteam11", USERNAME, PASSWORD);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	    
	    return conn;
	}
}