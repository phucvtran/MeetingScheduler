package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Cell;
import model.Meeting;
import model.MeetingDB;

/**
 * Holds the cells that represent dates on the calendar.
 * @author Thomas Van Riper
 * @version 14 Mar 2017
 */
public class ContentPanel extends JPanel implements PropertyChangeListener
{

	/** An auto generated serial UID. */
	private static final long serialVersionUID = 4230073288966947304L;
	
	/** The background color for JPanels. */
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	
	/** The highlight color for a selected meeting */
	private static final Color HIGHLIGHT_COLOR = new Color(0, 165, 253);
		
	/** The number of cells to display on a monthly calendar view. */
	private static final int NUMBER_OF_CELLS = 42;
	
	/** The days of the week. */
	private static final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday"};
	
	/** A calendar to build the display. */
	private Calendar cal;
	
	/** The cell panel to refresh on month/year change. */
	private JPanel cellPanel;
	
	/** Cells for holding each date's meetings. */
	private Cell[] cells;
	
	/** A connection to get data. */
	private MeetingDB data;
	
	/** All the meetings visible to the user. */
	private List<Meeting> meetings;
	
	/** The month the calendar display is set to. */
	private int month;
	
	/** Holds the previously highlighted JLabel to be un-highlighted on click. */
	private JLabel onUnselect;
	
	/** The year the calendar display is set to. */
	private int year;
	
	/**
	 * Constructs a panel holding a grid of cells representing dates.
	 */
	public ContentPanel()
	{
		data = new MeetingDB();
		try
		{
			meetings = data.getMeetings();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		cal = Calendar.getInstance();
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		buildDayOfWeekLabels();
		buildCells();
		addMeetings();
	}
	
	/**
	 * Adds any meetings that correspond to dates on the calendar.
	 */
	private void addMeetings()
	{
		for (Meeting m : meetings)
		{
			for (int i = 0; i < cells.length; i++)
			{
				if (m.getDay() == cells[i].getDate() 
						&& m.getMonth() - 1 == cells[i].getMonth()
						&& m.getYear() == cells[i].getYear())
				{
					cells[i].addMeeting(m);
				}
			}
		}
	}
	
	/**
	 * Builds the grid that represents a calendar.
	 */
	private void buildCells()
	{
		cal = new GregorianCalendar(year, month, 1);
		int firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK) - 1;
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
		
		boolean startCounting = false;
		int count = 2;
		
		cellPanel = new JPanel();
		cellPanel.setBackground(BACKGROUND_COLOR);
		cells = new Cell[NUMBER_OF_CELLS];
		for (int i = 0; i < NUMBER_OF_CELLS; i++)
		{
			cells[i] = new Cell();
			if (i == firstDayOfMonth)
			{
				cells[i].setDate(1);
				startCounting = true;
			}
			else if (startCounting && count < daysInMonth)
			{
				cells[i].setDate(count);
				count++;
			}
			
			cells[i].addPropertyChangeListener(this);
			addPropertyChangeListener(cells[i]);
			cells[i].setMonth(month);
			cells[i].setYear(year);
			cellPanel.add(cells[i]);
		}
		
		add(cellPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Adds the labels for each day of the week.
	 */
	private void buildDayOfWeekLabels()
	{
		JPanel daysOfWeek = new JPanel();
		daysOfWeek.setBackground(BACKGROUND_COLOR);
		for (int i = 0; i < DAYS_OF_WEEK.length; i++)
		{
			JPanel dayOfWeek = new JPanel();
			dayOfWeek.setBackground(BACKGROUND_COLOR);
			dayOfWeek.setPreferredSize(new Dimension(100, 20));
			dayOfWeek.add(new JLabel(DAYS_OF_WEEK[i]));
			daysOfWeek.add(dayOfWeek);
		}
		
		add(daysOfWeek, BorderLayout.NORTH);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals("left"))
		{
			if (month == 0)
			{
				year = year - 1;
				month = 11;
			}
			else
			{
				month = month - 1;
			}

			remove(cellPanel);
			buildCells();
			addMeetings();
			repaint();
		}
		else if (evt.getPropertyName().equals("right"))
		{
			if (month == 11)
			{
				year = year + 1;
				month = 0;
			}
			else
			{
				month = month + 1;
			}
			
			remove(cellPanel);
			buildCells();
			addMeetings();
			repaint();
		}
		else if (evt.getPropertyName().equals("Label"))
		{	
			if (onUnselect != null)
			{
				firePropertyChange("Unselect", null, onUnselect);
			}
			JLabel selected = (JLabel) evt.getNewValue();
			selected.setOpaque(true);
			selected.setBackground(HIGHLIGHT_COLOR);
			selected.setForeground(BACKGROUND_COLOR);
			onUnselect = selected;
		}
	}
}
