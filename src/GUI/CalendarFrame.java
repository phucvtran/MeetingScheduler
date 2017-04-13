package GUI;

import javax.swing.JFrame;

/**
 * A container for a calendar view.
 * @author Thomas Van Riper
 * @version 14 Mar 2017
 */
public class CalendarFrame extends JFrame
{

	/** Auto generated serial UID. */
	private static final long serialVersionUID = 8149494502771663586L;
		
	/**
	 * Instantiates the container and calls a setup method.
	 */
	public CalendarFrame()
	{		
		setup();
		
		setResizable(false);
		setTitle("Monthly View");
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Adds a panel to the container for displaying the calendar.
	 */
	private void setup()
	{
		CalendarPanel calendar = new CalendarPanel();
		this.add(calendar);
	}
}
