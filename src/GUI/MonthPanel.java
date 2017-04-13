package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Creates a title for the calendar (month and title).
 * @author Thomas Van Riper
 * @version 14 Mar 2016
 */
public class MonthPanel extends JPanel implements ActionListener
{
	/** Auto generated serial UID. */
	private static final long serialVersionUID = 794793676951646386L;
	
	/** Holds an array of months. */
	private String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December"};
	
	/** A Calendar object to get the month and year. */
	private Calendar cal;
	
	/** Gives the JPanel a title with the month and year. */
	JLabel monthTitle;
	
	/** Holds the current month. */
	private int month;
	
	/** Holds the current year. */
	private int year;
	
	/**
	 * Constructs a title panel holding the month and year.
	 */
	public MonthPanel()
	{
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(600, 50));
		cal = Calendar.getInstance();
		
		setup();
	}
	
	/**
	 * Adds buttons for changing the current month and year.
	 */
	private void setup()
	{
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		monthTitle = new JLabel(MONTHS[month] + " " + year);
		monthTitle.setFont(new Font("Courier", Font.BOLD, 36));
		JButton left = new JButton("<");
		left.addActionListener(this);
		JButton right = new JButton(">");
		right.addActionListener(this);
				
		add(left);
		add(monthTitle);
		add(right);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("<"))
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
			firePropertyChange("left", null, null);
		}
		else if (e.getActionCommand().equals(">"))
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
			firePropertyChange("right", null, null);
		}
		
		monthTitle.setText(MONTHS[month] + " " + year);
		this.repaint();
	}
}
