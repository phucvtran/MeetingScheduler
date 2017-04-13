package model;

/**
 * Creates an attendee for a meeting.
 * @author Thomas Van Riper
 * @version 14 Mar 2017
 */
public class Attendee
{
	/** The id of the attendee/patient. */
	private String patientId;
	
	/** The status of the attendance of the attendee:
	 * INVITED, ACCEPTED, DECLINED.
	 */
	private String status;
	
	/**
	 * Creates a meeting attendee. 
	 * @param id
	 * @param status
	 */
	public Attendee(String id, String status)
	{
		patientId = id;
		this.status = status;
	}
	
	/**
	 * Returns the attendee/patient ID.
	 * @return patientId
	 */
	public String getId()
	{
		return patientId;
	}
	
	/**
	 * Returns the invitation status.
	 * @return status
	 */
	public String getStatus()
	{
		return status;
	}
}
