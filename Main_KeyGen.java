package rsa;
import javax.swing.JFrame;

// Main class
public class Main_KeyGen {
	
	public static void main( String[] args ) {
		
		// Create frame
		JFrame frame = new JFrame();
		
		// Set size of frame
		frame.setSize(750, 500);
		
		// Set frame to Exit on Close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add KeyGen and Encrypt tabs
	    frame.add(new JTabbedPaneTest());
	    
		// Center the frame on the primary screen
		frame.setLocationRelativeTo(null);
		
		// Make visible
		frame.setVisible(true);
		
	}
	
}