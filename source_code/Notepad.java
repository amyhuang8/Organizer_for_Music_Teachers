/* Notes:
 * Status: FINISHED
 * Currently saves notes
 * Missing text formatting */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Notepad extends MainProgram {
	private JTextArea note = new JTextArea();
	private JPanel mainButtons, pad, panel;
	private File directory = new File(username + "/Notepad");
	
	public Notepad() throws IOException {
		// Creating new directory
		directory.mkdir();
				
		// New panel
		mainButtons = new JPanel();
		mainButtons.setLayout(new GridLayout(1, 7));
		
		// Adding components to main buttons panel
		mainButtons.add(buttonCal);
		mainButtons.add(buttonLists);
		mainButtons.add(buttonRecords);
		mainButtons.add(buttonFinance);
		mainButtons.add(buttonNotepad);
		mainButtons.add(buttonHelp);
		mainButtons.add(signOut);
		
		buttonNotepad.setEnabled(false);
		buttonNotepad.setBackground(Color.pink);
		
		// New colour
		Color c = new Color(255, 255, 200);
		
		// New panel
		pad = new JPanel();
		pad.setBackground(c);
		
		// New text area
		note.setBackground(c);
		
		try {
			FileReader fr = new FileReader(directory + "/notepad.txt");
			int ch;
			String input = "";
			
			while ((ch = fr.read()) != -1) {
				input += (char) ch;
			}
			
			fr.close();
			
			if (!(input.equals(""))) {
				note.setText(input);
			}
			else {
				note.setText("Type your notes here.");
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			note.setText("Type your notes here.");
		}
		
		note.setEditable(true);
		
		// Adding text area to panel
		pad.add(note);
		
		// New panel
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(mainButtons);
		panel.add(pad);
		
		// Adding components frame
		fMain.add(panel);
		fMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fMain.setVisible(true);
		fMain.remove(panelStart);
		fMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void save() throws IOException {
		FileWriter fw = new FileWriter(directory + "/notepad.txt");
		
		fw.write(note.getText());
		
		fw.close();
	}
	
	public void actionPerformed(ActionEvent e) {
		// ALL MAIN PROGRAM EVENTS
		if (e.getSource() == buttonCal) {
			try {
				save();
				new Calendar().calendar();
				fMain.dispose();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == buttonLists) {
			try {
				save();
				new StudentLists().studentLists();
				fMain.dispose();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == buttonFinance) {
			try {
				new FinancialRecords().finance();
				fMain.dispose();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == buttonRecords) {
			try {
				save();
				new StudentRecords().studentRecords();
				fMain.dispose();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == buttonNotepad) {
			try {
				new Notepad();
				fMain.dispose();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == buttonHelp) {
			new HelpNotes();
		}
		else if (e.getSource() == signOut) {
			try {
				save();
				fMain.dispose();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}		
	}
	
	public void windowClosing(WindowEvent e) {
		try {
			save();
			fMain.dispose();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}