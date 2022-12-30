/* Notes:
 * Status: FINISHED
 * Currently displays instructions */

import java.awt.*;
import javax.swing.*;

public class HelpLists extends Frame {
	public JFrame fHelp;
	private JPanel panelHelp;
	private JTextArea iText;
	private JTextArea iHeader;
	
	public HelpLists() {
		// New frame
		fHelp = new JFrame("Student Lists Help");
		
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
		
		iText.setText("This is your Student Lists.\n"
				+ "You can:\n\n"
				+ "- Add new students and info by clicking \"Add a Student\"\n"
				+ "- Delete students by clicking \"Delete a Student\"\n"
				+ "(select a name, then press delete)\n"
				+ "- Sort students by their first names\n"
				+ "- Sort students by their last names");
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
		fHelp.setSize(400, 230);
		fHelp.setVisible(true);
		fHelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}