package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import GUI.HomeScreen;


/**
 * Admin object
 * @author Phuc Tran
 * @author Aygun Avazova
 * @version 14 Mar 2017
 */
public class admin{
	
	private String username;
	private String password;
	private String userType;


	/**
	 * 
	 * Constructor has admin information
	 * @param username
	 * @param password
	 * @param userType
	 */
	public admin(String username, String password, String userType) {
		this.username = username;
		this.password = password;
		this.userType = userType;

	}
	
	public String getUsername(){ return username;}	
	public String getPassword(){ return password;}
	public String getUserType(){ return userType;}
	


	/**
	 * reach out the database and get list of room
	 * @return list of room
	 * @throws SQLException
	 */
	public ArrayList<Room> getRoomList()throws SQLException{
		ArrayList<Room> roomList= new ArrayList<Room>();
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	    String query = "select * from Room";
	   
	    try {
	   
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	roomList.add(new Room(rs.getString("location")
	        			, rs.getInt("capacity")
	        			, rs.getString("equipment")
	        			));
	        }
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
		return roomList;
	}
	
	/**
	 * add room to database
	 * @param room to add
	 * @throws SQLException
	 */
	public void addRoom(Room r)
		    throws SQLException {
		
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	    String queryCheck = "select * from Room where location='" + r.getLocation() + "';";
	    String query = "INSERT INTO Room VALUES ('" + r.getLocation()
	    				+ "', '" + r.getCapacity() + "', '" + r.getEquipment() + "');";
	    try {
	    	stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery(queryCheck);
	        if (rs.next()) {
	        	System.out.println("this room already in our database");
	        }else {
	        	stmt.executeUpdate(query);
	        	System.out.println("new room is added");
	        }
	        
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * add equipments to a room on database
	 * @param roomLocation the room to add equipment
	 * @param equip equipment add
	 * @throws SQLException
	 */
	public void updateEquiqment (String roomLocation, String equip) throws SQLException{
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	    String query = "";
	    String queryCheck = "select * from Room where location='" + roomLocation + "';";
	    try {
	    	stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery(queryCheck);
	        if (rs.next()) {
	        	query = "UPDATE Room SET equipment='" 
	        			+ equip +"' WHERE location='" + roomLocation + "';";
	        	stmt.executeUpdate(query);
	        }else {
	        	System.out.println("this room is not on our database");
	        }
	        
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * chang the location of the room in our database
	 * @param roomLocation
	 * @param newlocation
	 * @throws SQLException
	 */
	public void updateLocation (String roomLocation, String newlocation) throws SQLException{
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	    String query = "";
	    String queryCheck = "select location from Room where location='" + roomLocation + "';";
	    try {
	    	stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery(queryCheck);
	        if (rs.next()) {
	        	query = "UPDATE Room SET location='" 
	        			+ newlocation +"' WHERE location='" + roomLocation + "';";
	        	stmt.executeUpdate(query);
	        }else {
	        	System.out.println("this room is not on our database");
	        }
	        
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	
	/**
	 * change capacity of the room
	 * @param roomLocation the room we want to change the capacity
	 * @param capacity new capacity
	 * @throws SQLException
	 */
	public void updateRoomCapacity (String roomLocation, int capacity) throws SQLException{
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	    String query = "";
	    String queryCheck = "select * from Room where location='" + roomLocation + "';";
	    try {
	    	stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery(queryCheck);
	        if (rs.next()) {
	        	query = "UPDATE Room SET capacity='" 
	        			+ capacity +"' WHERE location='" + roomLocation + "';";
	        	stmt.executeUpdate(query);
	        	System.out.println("new capacity for this room is : " + capacity);
	        }else {
	        	System.out.println("this room is not on our database");
	        }
	        
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * delete the room from data base
	 * @param r room
	 * @throws SQLException
	 */
	public void removeRoom(Room r)
		    throws SQLException {
		
		Connection con = HomeScreen.getConnection();
	    Statement stmt = null;
	    String queryCheck = "select * from Room where location='" + r.getLocation() + "';";
	    String query = "DELETE FROM Room WHERE location = '" + r.getLocation() + "';";
	    try {
	    	stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery(queryCheck);
	        if (!rs.next()) {
	        	System.out.println("this room isn't in your database");
	        }else {
	        	stmt.executeUpdate(query);
	        	System.out.println("you just remove room: " + r.getLocation());
	        }
	        
	    } catch (SQLException e ) {
	        System.out.println("error");
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
}


