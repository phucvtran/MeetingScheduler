package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Pop up for updating a meeting.
 * @author Thomas Van Riper
 * @version 14 Mar 2017
 */
public class EditMeeting extends JFrame implements ActionListener
{

	/**
	 * Auto generated serial UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** Alternate background color. */
	private static final Color ALTERNATE_COLOR = Color.LIGHT_GRAY;
	
	/** Background color for JPanels. */
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	
	/** Size of the panel. */
	private static final Dimension SIZE = new Dimension(500, 300);
	
	/** Valid times to schedule a meeting. */
	private static final String[] TIMES = new String[] {"7:00", "8:00", "9:00", "10:00", "11:00",
			"12:00", "1:00", "2:00", "3:00", "4:00", "5:00"};	
	
	private MeetingDB data;
	
	/** Lists the attendees to invite. */
	private JList<Patient> attendees;
	
	/** The cell to be updated on adding meeting. */
	Cell cell;
	
	/** Let's user choose which counselor will host the meeting. */
	private JComboBox<Counselor> counselor; 
	
	/** Holds the list of counselors to choose from. */
	private List<Counselor> counselors;
	
	/** Allows the user to choose the end time of the meeting. */
	private JComboBox<String> end;
	
	/** Holds the list of rooms. */
	private JComboBox<Room> location;
	
	/** Holds the list of patients to invite. */
	private List<Patient> patients;
	
	/** The selected meeting to edit. */
	private Meeting meeting;
	
	/** Holds the list of rooms. */
	private List<Room> rooms;
	
	/** Allows the user to choose the start time of the meeting. */
	private JComboBox<String> start;
	
	/** Sets/gets the title for the meeting. */
	private JTextField title;
	
	/** The date of the meeting. */
	int month, year, date;
	
	/**
	 * Pop up window for adding a meeting.
	 * @param month
	 * @param year
	 * @param date
	 */
	public EditMeeting(int month, int year, int date, Cell cell, Meeting meeting)
	{
		this.month = month;
		this.year = year;
		this.date = date;
		this.cell = cell;
		this.meeting = meeting;
		
		data = new MeetingDB();
		
		setup();
		
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Add Meeting");
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Builds the drop down menu for counselors and a list of
	 * possible attendees.
	 * @return counselorPanel
	 */
	private JPanel buildCounselorPanel()
	{
		JPanel counselorPanel = new JPanel();
		counselorPanel.setBackground(BACKGROUND_COLOR);
		counselorPanel.setLayout(new BoxLayout(counselorPanel, BoxLayout.Y_AXIS));
		JLabel counselorLabel = new JLabel("Counselor: ");
		try
		{
			counselors = data.getCounselors();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		Counselor[] counselorArray = new Counselor[counselors.size()];
		int selectedIndex = 0;
		for (int i = 0; i < counselors.size(); i++)
		{
			if (counselors.get(i).getId().equals(meeting.getCounselorId()))
			{
				selectedIndex = i;
			}
			counselorArray[i] = counselors.get(i);
		}
		
		counselor = new JComboBox<Counselor>(counselorArray);
		counselor.setSelectedIndex(selectedIndex);
		
		JLabel attendeesLabel = new JLabel("Attendees: ");
		patients = null;
		try
		{
			patients = data.getPatients();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		Patient[] patientArray = new Patient[patients.size()];
		for (int i = 0; i < patients.size(); i++)
		{
			patientArray[i] = patients.get(i);
		}
		attendees = new JList<Patient>(patientArray);
		JScrollPane scroller = new JScrollPane(attendees);		
		counselorPanel.add(counselorLabel);
		counselorPanel.add(counselor);
		counselorPanel.add(attendeesLabel);
		counselorPanel.add(scroller);
		
		return counselorPanel;
	}
	
	/**
	 * Builds the location, start, and end time drop down menus.
	 * @return detailsPanel
	 */
	private JPanel buildMeetingDetailsPanel()
	{
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBackground(ALTERNATE_COLOR);
		JLabel locationLabel = new JLabel("Location: ");
		rooms = null;
		try
		{
			rooms = data.getLocations();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		Room[] roomArray = new Room[rooms.size()];
		int selectedIndex = 0;
		for (int i = 0; i < rooms.size(); i++)
		{
			if (rooms.get(i).getLocation().equals(meeting.getLocation()))
			{
				selectedIndex = i;
			}
			roomArray[i] = rooms.get(i);
		}
		
		location = new JComboBox<Room>(roomArray);
		location.setSelectedIndex(selectedIndex);
		JLabel startLabel = new JLabel("Start: ");
		start = new JComboBox<String>(TIMES);
		StringTokenizer st = new StringTokenizer(meeting.getStartTime(), " :");
		st.nextToken();
		int selectedTime = Integer.parseInt(st.nextToken());
		if (selectedTime >= 7)
			selectedTime = selectedTime - 7;
		else
			selectedTime = selectedTime + 5;
		start.setSelectedIndex(selectedTime);
		JLabel endLabel = new JLabel("End: ");
		end = new JComboBox<String>(TIMES);
		st = new StringTokenizer(meeting.getEndTime(), " :");
		st.nextToken();
		selectedTime = Integer.parseInt(st.nextToken());
		if (selectedTime >= 7)
			selectedTime = selectedTime - 7;
		else
			selectedTime = selectedTime + 5;
		end.setSelectedIndex(selectedTime);
		detailsPanel.add(locationLabel);
		detailsPanel.add(location);
		detailsPanel.add(startLabel);
		detailsPanel.add(start);
		detailsPanel.add(endLabel);
		detailsPanel.add(end);
		
		return detailsPanel;
	}
	
	/**
	 * Builds a panel for entering the title of the meeting.
	 * @return titlePanel
	 */
	private JPanel buildTitlePanel()
	{
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(BACKGROUND_COLOR);
		JLabel titleLabel = new JLabel("Title: ");
		title = new JTextField(20);
		title.setText(meeting.getTitle());
		titlePanel.add(titleLabel);
		titlePanel.add(title);
		
		return titlePanel;
	}
	
	/**
	 * Brings all the components together in one panel.
	 */
	private void setup()
	{
		JPanel content = new JPanel();
		content.setBackground(Color.WHITE);
		TitledBorder title = BorderFactory.createTitledBorder("Update Meeting");
		title.setTitleFont(new Font("Courier", Font.BOLD, 18));
		content.setBorder(title);
		content.setPreferredSize(SIZE);
		content.setVisible(true);
		content.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(ALTERNATE_COLOR);
		JButton delete = new JButton("Delete Meeting");
		JButton submit = new JButton("Update Meeting");
		delete.addActionListener(this);
		submit.addActionListener(this);
		buttonPanel.add(delete);
		buttonPanel.add(submit);
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2, 0));
		northPanel.add(buildTitlePanel());
		northPanel.add(buildMeetingDetailsPanel());
		
		content.add(northPanel, BorderLayout.NORTH);
		content.add(buildCounselorPanel(), BorderLayout.CENTER);
		content.add(buttonPanel, BorderLayout.SOUTH);
		
		add(content);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Delete Meeting"))
		{
			data.deleteMeeting(meeting.getMeetingId());
			cell.deleteMeeeting(meeting);
		}
		else if (e.getActionCommand().equals("Update Meeting"))
		{
			cell.deleteMeeeting(meeting);
			Counselor c = (Counselor) counselor.getSelectedItem();
			Room newLocation = (Room) location.getSelectedItem();
			String startTime = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + 
					date + " " + start.getSelectedItem() + ":00";
			String endTime = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" +
					date + " " + end.getSelectedItem() + ":00";
			
			meeting.setCounselorId(c.getId());
			meeting.setLocation(newLocation.getLocation());
			meeting.setStartTime(startTime);
			meeting.setEndTime(endTime);
			meeting.setTitle(title.getText());
			
			data.updateMeeting(meeting);
			cell.addMeeting(meeting);
			data.deleteAttendees(meeting);
			for (int i = 0; i < attendees.getSelectedIndices().length; i++)
			{
				Patient p = patients.get(attendees.getSelectedIndices()[i]);
				data.addAttendee(meeting.getMeetingId(), p.getId(), "accepted");
			}
		}
		this.dispose();
	}

}
