package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Builds the components for displaying the calendar view.
 * @author Thomas Van Riper
 * @version 14 Mar 2017
 */
public class CalendarPanel extends JPanel
{

	/** An auto generated serial UID. */
	private static final long serialVersionUID = -4757106108149737454L;
	
	/** Sets the size of the panel. */
	private static final Dimension panelSize = new Dimension(800, 600);
	
	/** Holds the month layout.*/
	private ContentPanel contentPanel;
	
	/** Holds the current month and year. Sets the title. */
	private MonthPanel monthPanel;
	
	/**
	 * Instantiates the calendar panel and calls a setup method.
	 */
	public CalendarPanel()
	{		
		setup();
		
		setBackground(Color.WHITE);
		setPreferredSize(panelSize);
		setVisible(true);
	}

	/**
	 * Adds panels for the calendar title (month and year) and the content.
	 */
	private void setup()
	{
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		monthPanel = new MonthPanel();
		contentPanel = new ContentPanel();
		monthPanel.addPropertyChangeListener(contentPanel);
		add(monthPanel, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
	}
}
