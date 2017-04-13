package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cell represents a date on the calendar.
 * @author Thomas Van Riper
 * @version 14 Mar 2017
 */
public class Cell extends JPanel implements MouseListener, PropertyChangeListener
{

	/** An auto generated serial  UID. */
	private static final long serialVersionUID = -3059067480908071499L;
	
	/** The background color of the cell. */
	private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	
	/** The standard size of a cell. */
	private static final Dimension CELL_SIZE = new Dimension(100, 80);
	
	/** Holds the meetings content. */
	JPanel content;
	
	/** The date the cell represents. */
	int date;
	
	/** Meetings scheduled on the day this cell represents. */
	private List<Meeting> meetings;
	
	/** Maps JLabels to meetings. */
	HashMap<Integer, Meeting> meetingsMap;
	
	/** The month represented by the cell. */
	int month;
	
	/** The year represented by the cell. */
	int year;
	
	/**
	 * Creates a cell representing a date.
	 */
	public Cell()
	{
		addMouseListener(this);
		setBackground(BACKGROUND_COLOR);
		setLayout(new BorderLayout());
		setPreferredSize(CELL_SIZE);
		
		meetings = new ArrayList<Meeting>();
		meetingsMap = new HashMap<Integer, Meeting>();
		displayMeetings();
	}
	
	/**
	 * Adds a meeting to the cell.
	 * @param meeting
	 */
	public void addMeeting(Meeting meeting)
	{
		meetings.add(meeting);
		remove(content);
		displayMeetings();
		invalidate();
		validate();
		repaint();
	}
		
	/**
	 * Deletes a meeting from the cell.
	 * @param meeting
	 */
	public void deleteMeeeting(Meeting meeting)
	{
		meetings.remove(meeting);
		remove(content);
		displayMeetings();
		invalidate();
		validate();
		repaint();
	}
	
	/**
	 * Displays up to two meetings in the cell.
	 * If the number of scheduled meetings exceed two,
	 * display the number hidden.
	 */
	private void displayMeetings()
	{
		Collections.sort(meetings);
		content = new JPanel();
		content.setBackground(BACKGROUND_COLOR);
		if (meetings.size() > 0)
		{
			if (meetings.size() < 3)
			{
				for (int i = 0; i < meetings.size(); i++)
				{
					JLabel title;
					int length = meetings.get(i).getTitle().length();
					if (length < 12)
					{
						title = new JLabel(meetings.get(i).getTitle());
					}
					else
					{
						StringBuilder shortenedString = 
								new StringBuilder(meetings.get(i).getTitle().subSequence(0, 12));
						shortenedString.append("...");
						title = new JLabel(shortenedString.toString());
					}
					
					title.addMouseListener(this);
					meetingsMap.put(title.hashCode(), meetings.get(i));
					content.add(title);
				}			
			}
			else
			{
				int length = meetings.get(0).getTitle().length();
				JLabel title;
				if (length < 12)
				{
					title = new JLabel(meetings.get(0).getTitle());
				}
				else
				{
					StringBuilder shortenedString = 
							new StringBuilder(meetings.get(0).getTitle().subSequence(0, 12));
					shortenedString.append("...");
					title = new JLabel(shortenedString.toString());
				}
				
				title.addMouseListener(this);
				meetingsMap.put(title.hashCode(), meetings.get(0));
				JLabel hiddenMeetings = new JLabel((meetings.size() - 1) + " more...");
				hiddenMeetings.addMouseListener(this);
				content.add(title);
				content.add(hiddenMeetings);
			}
		}
		
		add(content, BorderLayout.CENTER);
	}
	
	/**
	 * Returns the date represented by the cell.
	 * @return date
	 */
	public int getDate()
	{
		return date;
	}
	
	/**
	 * Returns the list of meetings.
	 * @return meetings
	 */
	public List<Meeting> getMeetings()
	{
		return meetings;
	}
	
	/**
	 * Returns a meeting from the meetingsMap.
	 * @param code
	 * @return meeting
	 */
	public Meeting getMeetingsMapValue(Integer code)
	{
		return meetingsMap.get(code);
	}
	
	/**
	 * Returns the month represented by the cell.
	 * @return month
	 */
	public int getMonth()
	{
		return month;
	}
	
	/**
	 * Returns the year represented by the cell.
	 * @return year
	 */
	public int getYear()
	{
		return year;
	}
	
	/**
	 * Adds a key and value to the meetings map.
	 * @param code
	 * @param meeting
	 */
	public void putInMeetingsMap(Integer code, Meeting meeting)
	{
		meetingsMap.put(code, meeting);
	}
	
	/**
	 * Sets the date of the cell.
	 * @param date
	 */
	public void setDate(int date)
	{
		this.date = date;
		add(new JLabel(Integer.toString(date)), BorderLayout.NORTH);
	}
	
	/**
	 * Sets the month of the cell.
	 * @param month
	 */
	public void setMonth(int month)
	{
		this.month = month;
	}
	
	/**
	 * Sets the year of the cell.
	 * @param year
	 */
	public void setYear(int year)
	{
		this.year = year;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getSource().getClass().getSimpleName().equals("JLabel") && e.getClickCount() == 1)
		{
			JLabel selected = (JLabel) e.getSource();
			firePropertyChange("Label", null, selected);
		}
		else if (e.getSource().getClass().getSimpleName().equals("JLabel") && e.getClickCount() == 2)
		{
			JLabel selected = (JLabel) e.getSource();
			if (selected.getText().contains("more..."))
			{
				new LongMeetingList(this);
			}
			else
			{
				firePropertyChange("Label", null, selected);
				new EditMeeting(month, year, date, this, meetingsMap.get(selected.hashCode()));
			}
		}
		else if (e.getSource().equals(this) && date != 0 && e.getClickCount() == 2)
		{
			new AddMeeting(month, year, date, this);
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals("Unselect"))
		{
			JLabel onUnselect = (JLabel) evt.getNewValue();
			onUnselect.setBackground(BACKGROUND_COLOR);
			onUnselect.setForeground(Color.BLACK);
		}
		
	}
}
