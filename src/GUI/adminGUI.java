package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Room;
import model.admin;

/**
 * Create Admin Panel which display all meeting rooms 
 * and allow user to change information of selected room.
 * @author Phuc Tran
 * @version 14 Mar 2017
 */
public class adminGUI {
	
	private static String welcomeBanner = "Admin Panel";
	private static Font welcome = new Font("Tahoma", Font.BOLD, 22);
	private int WINDOWWIDTH = 800;
	private int WINDOWHEIGHT = 500;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	
	private JFrame myFrame;
	private JPanel contentPane;
	private admin myAdmin;
	private int selectedRow = -1;
	
	private JTable tblItems;
	private String loc;
	
	/**
	 * Table model with default column name
	 */
	private DefaultTableModel tblModel = new DefaultTableModel(new Object[][] {},
			new String[] { "Location", "Capacity", "Equipment"});
	
	public adminGUI(JFrame f, admin a){
		myFrame = f;
		myAdmin = a;
		
	}
	
	/**
	 * start GUI
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


		JLabel label = new JLabel("Meeting Scheduler Admin");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(welcome);
		label.setBounds(0, 0, 788, 24);
		contentPane.add(label);

		JLabel lblWelcome = new JLabel("Welcome to Meeting Schedule Admin Menu");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(welcome);
		lblWelcome.setBounds(0, 29, 788, 24);
		contentPane.add(lblWelcome);

		JLabel lblNewLabel_1 = new JLabel("Logged in as: " + myAdmin.getUserType());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(welcome);
		lblNewLabel_1.setBounds(10, 52, 764, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblHint = new JLabel("Tip: please double click to select a table cell to update,"
				+ " press enter when you are done with the input, then use an appropriate button");
		lblHint.setBounds(10, 350, 764, 24);
		contentPane.add(lblHint);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 100, WINDOWWIDTH, WINDOWHEIGHT-250);
		contentPane.add(scrollPane);

		tblItems = new JTable();
		scrollPane.setViewportView(tblItems);
		tblItems.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectedRow = tblItems.getSelectedRow();
				if (e.getClickCount() >= 2) {
					loc = (String) tblItems.getValueAt(selectedRow, 0);
				}
			}
		});
		tblItems.setShowVerticalLines(true);
		tblItems.setShowHorizontalLines(false);
		tblItems.setShowGrid(true);
		tblItems.setModel(tblModel);
		// set width of column
		tblItems.getColumnModel().getColumn(1).setMaxWidth(70);
		tblItems.getColumnModel().getColumn(2).setPreferredWidth(500);
		tblItems.setBackground(SystemColor.control);
		tblItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		JButton btnAddRoom = new JButton("Add Room");
		btnAddRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField locationField = new JTextField(20);
			    JTextField capacityField = new JTextField(5);
			    JTextField equipmentField = new JTextField(50);

			    JPanel myPanel = new JPanel();
			    myPanel.add(new JLabel("Location:"));
			    myPanel.add(locationField);
			    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			    myPanel.add(new JLabel("capacity:"));
			    myPanel.add(capacityField);
			    myPanel.add(Box.createHorizontalStrut(15));
			    myPanel.add(new JLabel("Equipments"));
			    myPanel.add(equipmentField);

			    // press ok to add room to database and update GUI
			    int result = JOptionPane.showConfirmDialog(null, myPanel, 
		    		  "Please Enter new Room Informations", JOptionPane.OK_CANCEL_OPTION);
			    if (result == JOptionPane.OK_OPTION) {
			    	Room r = new Room(locationField.getText()
			    			, Integer.valueOf(capacityField.getText())
			    			, equipmentField.getText());
			    	try {
						myAdmin.addRoom(r);
						tblModel.addRow(new Object[] { r.getLocation(), r.getCapacity(), r.getEquipment()});
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			    	
			    }
			    
			}
		});
		btnAddRoom.setToolTipText("Click here to Add new Room");
		btnAddRoom.setBounds(31, 411, 154, 23);
		contentPane.add(btnAddRoom);

		JButton btnRemoveRoom = new JButton("Remove Room");
		btnRemoveRoom.addActionListener(new ActionListener() {
			// allow user to change informations in the table
			public void actionPerformed(ActionEvent e) {
				System.out.println(tblItems.getValueAt(selectedRow, 1));
				if (selectedRow != -1){
					int i = (int) tblItems.getValueAt(selectedRow, 1);
					Room r = new Room ((String)tblItems.getValueAt(selectedRow, 0)
							,   i
							, (String) tblItems.getValueAt(selectedRow, 2));
					try {
						myAdmin.removeRoom(r);
						tblModel.removeRow(selectedRow);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					selectedRow = -1;
				}
			}
		});
		btnRemoveRoom.setToolTipText("Click here to remove the last item highlighted in the list.");
		btnRemoveRoom.setBounds(216, 411, 168, 23);
		contentPane.add(btnRemoveRoom);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(600, 411, 154, 23);
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
		btnSignout.setBounds(415, 411, 154, 23);
		contentPane.add(btnSignout);
		
		JButton btnUpdateCap = new JButton("Update Capacity");
		btnUpdateCap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedRow != -1){
					int cap = Integer.valueOf((String)  tblItems.getValueAt(selectedRow, 1));
					try {
						myAdmin.updateRoomCapacity(loc, cap);
					} catch (SQLException e1) {
						System.out.println("error");
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnUpdateCap.setToolTipText("Click here to Update Room Capacity");
		btnUpdateCap.setBounds(415, 376, 154, 23);
		contentPane.add(btnUpdateCap);
		
		JButton btnViewCal = new JButton("View Calendar");
		btnViewCal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CalendarFrame();
			}
		});
		btnViewCal.setToolTipText("Click here to view Calendar");
		btnViewCal.setBounds(600, 376, 154, 23);
		contentPane.add(btnViewCal);
		
		JButton btnUpdateLocation = new JButton("Update Location");
		btnUpdateLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedRow != -1){
					String newlocation = (String) tblItems.getValueAt(selectedRow, 0);
					try {
						myAdmin.updateLocation(loc, newlocation);
					} catch (SQLException e1) {
						System.out.println("error");
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnUpdateLocation.setToolTipText("Click here to update Room Address");
		btnUpdateLocation.setBounds(216, 376, 168, 23);
		contentPane.add(btnUpdateLocation);
		
		JButton btnUpdateEquip = new JButton("Update Equipment");
		btnUpdateEquip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedRow != -1){
					String equip = (String) tblItems.getValueAt(selectedRow, 2);
					try {
						myAdmin.updateEquiqment(loc, equip);
					} catch (SQLException e1) {
						System.out.println("error");
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnUpdateEquip.setToolTipText("Click here to update Equipment");
		btnUpdateEquip.setBounds(31, 376, 154, 23);
		contentPane.add(btnUpdateEquip);
		
		
		
		populateItems();

	}
	
	/**
	 * Loads Room information in table
	 * @throws SQLException 
	 */
	private void populateItems() throws SQLException {
		for (Room i : myAdmin.getRoomList()) {
			tblModel.addRow(new Object[] { i.getLocation(), i.getCapacity(), i.getEquipment() });
		}
	}
	
	

}
