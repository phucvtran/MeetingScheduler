package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * main class
 * @author phuc tran
 *
 */
public class main {
	
	int WINDOWWIDTH = 801; 
	int WINDOWHEIGHT = 500;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenHeight = ((int) screenSize.getHeight()/2) - WINDOWHEIGHT/2;
	int screenWidth = ((int) screenSize.getWidth()/2) - WINDOWWIDTH/2;
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    private JFrame myFrame = new JFrame("Meeting Scheduler");;

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(final String[] args) {
		
		main GUI = new main();

		GUI.start();		
		
	}
	
	/**
	 * setup and start GUI
	 */
	void start(){
		setupFrame();
		HomeScreen home = new HomeScreen(myFrame);
		home.startGUI();
		myFrame.setVisible(true);
	}
	
	
    /**
     * this method will keep the window in a fit size
     * and center screen position.
     */
    private void setupFrame() { 
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setMinimumSize(new Dimension(myFrame.getWidth(), myFrame.getHeight()));
        myFrame.setBounds(0, 0, WINDOWWIDTH, WINDOWHEIGHT);
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                    SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
        
    }

}
