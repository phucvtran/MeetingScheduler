package model;

import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Represents a meeting with a meeting id, counselor id, location, 
 * start time, end time and title.
 * @author Edie Campbell
 *
 */
public class Meeting implements Comparator<Meeting>, Comparable<Meeting> {
	private String location;
	private String start_time;
	private String end_time;
	private int meeting_id;
	private String counselor_id;
	private String title;
	
	private int year, month, day;
	
	/**
	 * Initialize the meeting parameters.
	 * @param location
	 * @param start_time
	 * @param end_time
	 * @param meeting_id
	 * @param counselor_id
	 * @param title
	 * @throws IllegalArgumentException 
	 */
	public Meeting(String location, String start_time, String end_time, 
			int meeting_id, String counselor_id, String title) {
		this.location = location;
		this.start_time = start_time;
		this.end_time = end_time;
		this.meeting_id = meeting_id;
		this.counselor_id = counselor_id;
		this.title = title;
		setDate();
	}
	
	public Meeting(String location, String start_time, String end_time, 
			String counselor_id, String title) {
		this.location = location;
		this.start_time = start_time;
		this.end_time = end_time;
		this.counselor_id = counselor_id;
		this.title = title;
		setDate();
	}
	
	private void setDate()
	{
		StringTokenizer st = new StringTokenizer(start_time, "- ");
		int count = 0;
		while (count < 3)
		{
			switch (count)
			{
				case 0:
					year = Integer.parseInt(st.nextToken());
					count++;
					break;
					
				case 1:
					month = Integer.parseInt(st.nextToken());
					count ++;
					break;
					
				case 2:
					day = Integer.parseInt(st.nextToken());
					count++;
					break;
			}
		}
	}
	
	@Override
	public String toString() {
		return "Meeting [title=" + title + ", location=" + location 
				+ ", start time=" + start_time + ", end time=" + end_time 
				+ ", counselor id=" + counselor_id
				+ "]";
	}
	
	public int getYear()
	{
		return year;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getStartTime() {
		return start_time;
	}
	
	public String getEndTime() {
		return end_time;
	}
	
	public int getMeetingId() {
		return meeting_id;
	}
	
	public String getCounselorId() {
		return counselor_id;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setStartTime(String startTime)
	{
		start_time = startTime;
	}
	
	public void setEndTime(String endTime)
	{
		end_time = endTime;
	}
	
	public void setCounselorId(String id)
	{
		counselor_id = id;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}

	@Override
	public int compare(Meeting o1, Meeting o2)
	{
		StringTokenizer st = new StringTokenizer(o1.start_time, " :");
		st.nextToken();
		int start1 = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(o2.start_time, " :");
		st.nextToken();
		int start2 = Integer.parseInt(st.nextToken());
		
		return start1 - start2;
	}
	
	@Override
	public int compareTo(Meeting o)
	{
		StringTokenizer st = new StringTokenizer(this.start_time, " :");
		st.nextToken();
		int thisStart = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(o.start_time, " :");
		st.nextToken();
		int oStart = Integer.parseInt(st.nextToken());
		
		return Integer.compare(thisStart, oStart);
	}

}