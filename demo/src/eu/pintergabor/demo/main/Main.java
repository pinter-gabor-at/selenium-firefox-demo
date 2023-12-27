package eu.pintergabor.demo.main;

import eu.pintergabor.demo.web.Browser;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

	/**
	 * Create the GUI and show it.
	 * <p>
	 * For thread safety, this method is invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the main window
		JFrame frame = new JFrame("Selenium - Firefox - automation demo");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Browser.close();
				super.windowClosing(e);
			}
		});
		frame.setContentPane(new MainForm().contentPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// Create and show this application's GUI
		SwingUtilities.invokeLater(Main::createAndShowGUI);
	}
}
