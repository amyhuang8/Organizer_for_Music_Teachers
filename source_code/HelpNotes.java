/* Notes:
 * Status: FINISHED
 * Currently displays instructions */

import java.awt.*;
import javax.swing.*;

public class HelpNotes extends Frame {
	public JFrame fHelp;
	private JPanel panelHelp;
	private JTextArea iText;
	private JTextArea iHeader;
	
	public HelpNotes() {
		// New frame
		fHelp = new JFrame("Calendar Help");
		
		// New panel
		panelHelp = new JPanel();
		panelHelp.setBackground(Color.pink);
		panelHelp.setLayout(new BoxLayout(panelHelp, BoxLayout.Y_AXIS));
		
		// New text area
		iHeader = new JTextArea();
		iText = new JTextArea();
		
		// New font
		Font headers = new Font("times new roman", Font.BOLD, 18);
		Font text = new Font("times new roman", Font.PLAIN, 16);
		
		// Setting the text
		iHeader.setText("Instructions");
		iHeader.setFont(headers);
		
		iText.setText("This is your Notepad.\n\n"
				+ "You can store quick notes here, and everything will be\n"
				+ "saved upon switching to another tab or closure of\n"
				+ "the application.");
		iText.setFont(text);
		
		iHeader.setOpaque(false);
		iHeader.setEditable(false);
		iText.setEditable(false);
		
		// Adding text area to panel
		panelHelp.add(iHeader);
		panelHelp.add(iText);
		panelHelp.setAlignmentX(LEFT_ALIGNMENT);
		
		// New scrollable content pane
		JScrollPane scrPane = new JScrollPane(panelHelp);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Adding panel to frame
		fHelp.add(scrPane);
		fHelp.setSize(400, 170);
		fHelp.setVisible(true);
	}
}