package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Meeting;
import model.Patient;


/**
 * Create patient Panel which display all the meetings of that patient.
 * @author Phuc Tran
 * @version 14 Mar 2017
 */
public class patientGUI {
	
	private static String welcomeBanner = "Patient Panel";
	private static Font welcome = new Font("Tahoma", Font.BOLD, 22);
	private int WINDOWWIDTH = 800;
	private int WINDOWHEIGHT = 500;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	
	private JFrame myFrame;
	private JPanel contentPane;
	private Patient myPatient;
	
	private JTable tblItems;

	private DefaultTableModel tblModel = new DefaultTableModel(new Object[][] {},
			new String[] { "Title", "Location", "Start Time", " End Time", "Counselor ID"});
	
	public patientGUI(JFrame f, Patient p){
		myFrame = f;
		myPatient = p;
		
	}
	
	/**
	 * fire method.
	 * call this method to display UI
	 * @throws SQLException
	 */
	public void run() throws SQLException{
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setBounds(0, 0, WINDOWWIDTH, WINDOWHEIGHT);
		myFrame.setTitle(welcomeBanner);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		myFrame.setContentPane(contentPane);
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		contentPane.setLayout(null);
		initialize();
		myFrame.setVisible(true);
	}
	
	/**
	 * Initialize the GUI components of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {


		JLabel label = new JLabel("Meeting Scheduler Patient");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(welcome);
		label.setBounds(0, 0, 788, 24);
		contentPane.add(label);

		JLabel lblWelcome = new JLabel("Welcome to Meeting Schedule Patient Menu");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(welcome);
		lblWelcome.setBounds(0, 29, 788, 24);
		contentPane.add(lblWelcome);

		JLabel lblNewLabel_1 = new JLabel("Logged in as: " + myPatient.toString());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(welcome);
		lblNewLabel_1.setBounds(10, 52, 764, 24);
		contentPane.add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 100, WINDOWWIDTH, WINDOWHEIGHT-200);
		contentPane.add(scrollPane);

		tblItems = new JTable();
		scrollPane.setViewportView(tblItems);

		tblItems.setShowVerticalLines(true);
		tblItems.setShowHorizontalLines(false);
		tblItems.setShowGrid(true);
		tblItems.setModel(tblModel);
		tblItems.setBackground(SystemColor.control);
		tblItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(600, 420, 154, 23);
		contentPane.add(btnExit);

		JButton btnSignout = new JButton("SignOut");
		btnSignout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myFrame.dispose();
				main GUI = new main();
				GUI.start();	
			}
		});
		btnSignout.setToolTipText("Click here bring you back to login screen");
		btnSignout.setBounds(415, 420, 154, 23);
		contentPane.add(btnSignout);
		
		populateItems();

	}
	
	/**
	 * Loads Meeting information in table
	 * @throws SQLException 
	 */
	private void populateItems() throws SQLException {
		for (Meeting i : myPatient.getMeetingList()) {
			tblModel.addRow(new Object[] { i.getTitle(), i.getLocation(), i.getStartTime(), i.getEndTime()
					,i.getCounselorId()});
		}
	}
	
	

}
