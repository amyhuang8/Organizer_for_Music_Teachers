/* Notes:
 * Status: FINISHED
 * Currently displays instructions & FAQs */

import java.awt.*;
import javax.swing.*;

public class Help extends Frame {
	public JFrame fHelp;
	private JPanel panelHelp;
	private JTextArea iText, fText;
	private JTextArea iHeader, fHeader;
	
	public Help() {
		// New frame
		fHelp = new JFrame("Help");
		
		// New panel
		panelHelp = new JPanel();
		panelHelp.setBackground(Color.pink);
		panelHelp.setLayout(new BoxLayout(panelHelp, BoxLayout.Y_AXIS));
		
		// New text area
		iHeader = new JTextArea();
		iText = new JTextArea();
		
		fHeader = new JTextArea();
		fText = new JTextArea();
		
		// New font
		Font headers = new Font("times new roman", Font.BOLD, 18);
		Font text = new Font("times new roman", Font.PLAIN, 16);
		
		// Setting the text
		iHeader.setText("Instructions");
		iHeader.setFont(headers);
		
		iText.setText("Welcome to your personal Organizer for Music Teachers!\n\n"
				+ "For more tab-specific help, click the help button after\n"
				+ "opening a specific tab!\n\n"
				+ "Get started by creating your personal scheduler\n"
				+ "in the Calender tab, or keep track of your students\n"
				+ "with the Student Lists.\n\n"
				+ "You can organize your finances with the\n"
				+ "Financial Records tab, or document your students'\n"
				+ "progress in the Student Records.\n\n"
				+ "Lastly, the Notepad function allows you a\n"
				+ "convenient storage of your notes.\n");
		iText.setFont(text);
		
		fHeader.setText("Frequently Asked Questions (FAQs)");
		fHeader.setFont(headers);
		
		fText.setText("How do I add multiple events to a day?\n\n"
				+ "Unfortunately, this app only allows you to\n"
				+ "add one event per day.\n\n\n"
				+ "If I forget to press the save button,\n"
				+ "will all my records be deleted?\n\n"
				+ "No, this app tries its best to autosave whatever content\n"
				+ "you have entered upon the switching of tabs or closure\n"
				+ "of the app. However, please remember that clicking\n"
				+ "the save button is the best way to prevent data loss.");
		fText.setFont(text);
		
		iHeader.setOpaque(false);
		fHeader.setOpaque(false);
		iHeader.setEditable(false);
		iText.setEditable(false);
		
		// Adding text area to panel
		panelHelp.add(iHeader);
		panelHelp.add(iText);
		panelHelp.add(fHeader);
		panelHelp.add(fText);
		panelHelp.setAlignmentX(LEFT_ALIGNMENT);
		
		// New scrollable content pane
		JScrollPane scrPane = new JScrollPane(panelHelp);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Adding panel to frame
		fHelp.add(scrPane);
		fHelp.setSize(400, 500);
		fHelp.setVisible(true);
		fHelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}