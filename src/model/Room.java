package model;



/**
 * Room object
 * @author Phuc Tran
 * @author Aygun Avazova
 * @version 14 Mar 2017
 */
public class Room {
	private String location;
	private int capacity;
	private String equipment; 
	
	/**
	 * Constructor Room Object
	 * @param loc location of room
	 * @param cap capacity of room
	 * @param equip equipments in the room
	 */
	public Room (String loc, int cap, String equip){
		location = loc;
		capacity = cap;
		equipment = equip;
	}
	
	/**
	 * get location of the room
	 * @return location of the room
	 */
	public String getLocation(){
		return location;
	}
	
	/**
	 * get capacity of the room
	 * @return capacity of the room
	 */
	public int getCapacity(){
		return capacity;
	}
	
	/**
	 * get equipments in the room
	 * @return equipments in the room
	 */
	public String getEquipment(){
		return equipment;
	}
	
	/**
	 * add equipment to room
	 * @param equip
	 */
	public void addEquipment(String equip){
		equipment = equipment + ", " + equip;
	}
	
	/**
	 * toString method
	 */
	@Override
	public String toString()
	{
		return location;
	}
}
