package model;

/**
 * Creates a counselor.
 * @author Thomas Van Riper
 * @version 14 Mar 2016
 */
public class Counselor
{
	private String counselor_id;
	private String firstName;
	private String lastName;
	private String email;
	
	/**
	 * Instantiates a counselor.
	 * @param id
	 * @param first
	 * @param last
	 * @param email
	 */
	public Counselor(String id, String first, String last, String email)
	{
		counselor_id = id;
		firstName = first;
		lastName = last;
		this.email = email;
	}
	
	/**
	 * Returns the counselor's ID.
	 * @return counselor_id
	 */
	public String getId()
	{
		return counselor_id;
	}
	
	/**
	 * Returns the counselor's first name.
	 * @return firstName
	 */
	public String getFirst()
	{
		return firstName;
	}
	
	/**
	 * Returns the counselor's last name.
	 * @return lastName
	 */
	public String getLast()
	{
		return lastName;
	}
	
	/**
	 * Returns the counselor's e-mail.
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
}
