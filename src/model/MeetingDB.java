package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MeetingDB
{
	private static String userName = "_450bteam11";	
	private static String password = "Nimewg";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection conn;
	private List<Meeting> list;
	
	/**
	 * Creates a sql connection to MySQL using the properties for
	 * userid, password and server information.
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		conn =  DriverManager
				.getConnection("jdbc:mysql://" + serverName + "/" + userName + "?user=" + userName + "&password=" + password);
		
		System.out.println("Connected to database");
	}
	
	/**
	 * Adds an attendee to a meeting.
	 * @param meeting_id
	 * @param patient_id
	 * @param status
	 */
	public void addAttendee(int meeting_id, String patient_id, String status) {
		String sql = "insert into Attendees" 
				+ " (meeting_id, patient_id, status) values " 
				+ "(?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, meeting_id);
			preparedStatement.setString(2, patient_id);
			preparedStatement.setString(3, status);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
		finally {
			if (preparedStatement != null) {
				try
				{
					preparedStatement.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Adds a new meeting to the table.
	 * @param meeting 
	 */
	public void addMeeting(Meeting meeting) {
		String sql = "insert into Meeting" 
				+ " (counselor_id, location, start_time, end_time, title) values " 
				+ "(?, ?, ?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, meeting.getCounselorId());
			preparedStatement.setString(2, meeting.getLocation());
			preparedStatement.setString(3, meeting.getStartTime());
			preparedStatement.setString(4, meeting.getEndTime());
			preparedStatement.setString(5,  meeting.getTitle());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try
				{
					preparedStatement.close();
				} catch (SQLException e)
				{
										e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Deletes attendees on meeting update or delete.
	 * @param meeting
	 */
	public void deleteAttendees(Meeting meeting)
	{
		if (conn == null) {
			try
			{
				createConnection();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
			
		String sql = "delete from Attendees where meeting_id = ? ";
		
		//System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1,  meeting.getMeetingId());
		preparedStatement.executeUpdate();		
		} catch (SQLException e) {
		System.out.println(e);
		e.printStackTrace();
		}
		finally {
			if (preparedStatement != null) {
				try
				{
					preparedStatement.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Deletes a meeting from the database.
	 * @param meetingId
	 */
	public void deleteMeeting(int meetingId) {
		String sql = "delete from Meeting where meeting_id = ?; ";
		String sql2 = "delete from Attendees where meeting_id = ?; ";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, meetingId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			preparedStatement = conn.prepareStatement(sql2);
			preparedStatement.setInt(1,  meetingId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try
				{
					preparedStatement.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Retreives all attendees for a meeting.
	 * @param meetingId
	 * @return list of attendees
	 * @throws SQLException
	 */
	public List<Attendee> getAttendees(int meetingId) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		String query = "SELECT patient_id, status"
				+ " from Attendees where meeting_id = ?";

		List<Attendee> attendees = new ArrayList<Attendee>();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, meetingId);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String patientId = rs.getString("patient_id");
				String status = rs.getString("status");
				Attendee attendee = new Attendee(patientId, status);
				attendees.add(attendee);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
		return attendees;
	}
	
	/**
	 * Returns a list of counselors.
	 * @return counselors
	 * @throws SQLException
	 */
	public List<Counselor> getCounselors() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT counselor_id, first_name, last_name, email"
				+ " from Counselor";

		List<Counselor> counselors = new ArrayList<Counselor>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String counselor_id = rs.getString("counselor_id");
				String first = rs.getString("first_name");
				String last = rs.getString("last_name");
				String email = rs.getString("email");
				Counselor counselor = new Counselor(counselor_id, first, last, 
						email);
				counselors.add(counselor);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return counselors;
	}
	
	/**
	 * Returns a list of locations.
	 * @return rooms
	 * @throws SQLException
	 */
	public List<Room> getLocations() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT location, capacity, equipment"
				+ " from Room";

		List<Room> rooms = new ArrayList<Room>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String location = rs.getString("location");
				int capacity = rs.getInt("capacity");
				String equipment = rs.getString("equipment");
				Room room = new Room(location, capacity, equipment);
				rooms.add(room);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return rooms;
	}
	
	/**
	 * Filters the meeting list to find the given title. Returns a list with the
	 * meeting objects that match the title provided.
	 * @param title
	 * @return list of meetings that contain the title.
	 */
	public List<Meeting> getMeeting(String title) {
		List<Meeting> filterList = new ArrayList<Meeting>();
		try {
			list = getMeetings();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Meeting meeting : list) {
			if (meeting.getTitle().toLowerCase().contains(title.toLowerCase())) {
				filterList.add(meeting);
			}
		}
		return filterList;
	}
	
	/**
	 * Returns a list of meeting objects from the database.
	 * @return list of meeting
	 * @throws SQLException
	 */
	public List<Meeting> getMeetings() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT meeting_id, counselor_id, location, start_time, "
				+ "end_time, title from " + "Meeting";

		list = new ArrayList<Meeting>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int meeting_id = rs.getInt("meeting_id");
				String counselor_id = rs.getString("counselor_id");
				String location = rs.getString("location");
				String start_time = rs.getString("start_time");
				String end_time = rs.getString("end_time");
				String title = rs.getString("title");
				Meeting meeting = new Meeting(location, start_time, end_time, 
						meeting_id, counselor_id, title);
				list.add(meeting);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}
	
	/**
	 * Returns a list of patients.
	 * @return patients
	 * @throws SQLException
	 */
	public List<Patient> getPatients() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT patient_id, first_name, last_name, email"
				+ " from Patient";

		List<Patient> patients = new ArrayList<Patient>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String patient_id = rs.getString("patient_id");
				String first = rs.getString("first_name");
				String last = rs.getString("last_name");
				String email = rs.getString("email");
				Patient patient = new Patient(patient_id, first, last, 
						email);
				patients.add(patient);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return patients;
	}
	
	/**
	 * Modifies the meeting information corresponding to the index in the list.
	 * @param row index of the element in the list
	 * @param columnName attribute to modify
	 * @param data value to supply
	 */
	public void updateMeeting(Meeting meeting) {
		if (conn == null) {
			try
			{
				createConnection();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		String sql = "update Meeting set counselor_id = ?, location = ?, start_time = ?,"
				+ " end_time = ?, title = ? where meeting_id = ? ";
				
		//System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, meeting.getCounselorId());
			preparedStatement.setString(2, meeting.getLocation());
			preparedStatement.setString(3, meeting.getStartTime());
			preparedStatement.setString(4, meeting.getEndTime());
			preparedStatement.setString(5,  meeting.getTitle());
			preparedStatement.setInt(6,  meeting.getMeetingId());
			preparedStatement.executeUpdate();
				
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try
				{
					preparedStatement.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}
}
