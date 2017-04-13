package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import GUI.HomeScreen;

/**
 * Creates a patient.
 * @author Thomas Van Riper
 * Modified by Phuc Tran
 * @version 14 Mar 2017
 */
public class Patient
{
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	
	/**
	 * Instantiates a patient.
	 * @param id
	 * @param first
	 * @param last
	 * @param email
	 */
	public Patient(String id, String first, String last, String email)
	{
		this.id = id;
		firstName = first;
		lastName = last;
		this.email = email;
	}
	

	/**
	 * Returns the patient's ID.
	 * @return id
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * Returns the patient's first name.
	 * @return firstName
	 */
	public String getFirst()
	{
		return firstName;
	}
	
	/**
	 * Returns the patient's last name.
	 * @return lastName
	 */
	public String getLast()
	{
		return lastName;
	}
	
	/**
	 * Returns the patient's e-mail.
	 * @return email
	 */
	public String getEmail()
	{
		return email;
	}
	
	@Override
	public String toString()
	{
		String name = firstName + " " + lastName;
		return name;
	}
	
	/**
	 * reach out the database and get list of meeting
	 * @return list of meeting
	 * @throws SQLException
	 */
	public ArrayList<Meeting> getMeetingList()throws SQLException{
		ArrayList<Meeting> List= new ArrayList<Meeting>();
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	    String query = "SELECT * FROM Meeting NATURAL JOIN Attendees WHERE patient_id = '" + id + "';";	   
	    try {
	   
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	List.add(new Meeting(rs.getString("location")
	        			, rs.getString("start_time")
	        			, rs.getString("end_time")
	        			, rs.getInt("meeting_id")
	        			, rs.getString("counselor_id")
	        			, rs.getString("title")
	        			));
	        }
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
		return List;
	}
	
}
