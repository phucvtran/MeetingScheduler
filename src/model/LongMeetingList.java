package model;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays the list of meetings when the list grows too long
 * to display on the calendar view.
 * @author Thomas Van Riper
 * @version 14 Mar 2017
 */
public class LongMeetingList extends JFrame implements MouseListener
{

	/** Auto generated serial UID. */
	private static final long serialVersionUID = -4066666409062929282L;
	
	/** The cell representing the date. */
	Cell cell;
	
	/**
	 * Instantiates a list of meetings for the date specified.
	 * @param cell
	 */
	public LongMeetingList(Cell cell)
	{
		this.cell = cell;
		setup();
		
		setAlwaysOnTop(true);
		setTitle("Meetings");
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Displays a list of meetings.
	 */
	private void setup()
	{
		JPanel content = new JPanel();
		content.setBackground(Color.WHITE);
		content.setLayout(new GridLayout(cell.getMeetings().size(), 0));
		for (int i = 0; i < cell.getMeetings().size(); i++)
		{
			JLabel title = new JLabel(cell.getMeetings().get(i).getTitle());
			if (i % 2 == 0)
			{
				title.setOpaque(true);
				title.setBackground(Color.LIGHT_GRAY);
			}
			title.addMouseListener(this);
			cell.putInMeetingsMap(title.hashCode(), cell.getMeetings().get(i));
			content.add(title);
		}
		
		add(content);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 2)
		{
			JLabel selected = (JLabel) e.getSource();
			new EditMeeting(cell.getMonth(), cell.getYear(), cell.getDate(), 
					cell, cell.getMeetingsMapValue(selected.hashCode()));
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
}