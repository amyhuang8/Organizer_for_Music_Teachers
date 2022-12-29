/* Notes:
 * Status: FINISHED */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MainProgram extends LoginValidation implements MouseListener {
	// ALL MAIN PROGRAM VARIABLES BELOW
	protected JFrame fMain;
	protected JPanel panelStart, panelCal, panelLists, panelRecords, panelFinance;
	protected JButton buttonCal, buttonFinance, buttonLists, buttonRecords, buttonNotepad, buttonHelp, signOut;
	protected Color c = new Color(180, 200, 255);
	
	public MainProgram() {
		// New frame
		fMain = new JFrame("Organizer for Music Teachers");
		
		// New panels
		panelStart = new JPanel();
		panelStart.setLayout(new GridLayout(1, 7));
		panelStart.setBackground(Color.gray);
		
		// Calendar button
		buttonCal = new JButton("Calendar");
		buttonCal.setBackground(c);
		buttonCal.addActionListener(this);
		
		buttonLists = new JButton("Student Lists");
		buttonLists.setBackground(c);
		buttonLists.addActionListener(this);
		
		buttonFinance = new JButton("Financial Records");
		buttonFinance.setBackground(c);
		buttonFinance.addActionListener(this);
		
		buttonRecords = new JButton("Student Records");
		buttonRecords.setBackground(c);
		buttonRecords.addActionListener(this);
		
		buttonNotepad = new JButton("Notepad");
		buttonNotepad.setBackground(c);
		buttonNotepad.addActionListener(this);
		
		buttonHelp = new JButton("Help");
		buttonHelp.setBackground(c);
		buttonHelp.addActionListener(this);
		
		signOut = new JButton("Sign Out");
		signOut.setBackground(c);
		signOut.addActionListener(this);
		
		// Adding components to panel
		panelStart.add(buttonCal);
		panelStart.add(buttonLists);
		panelStart.add(buttonRecords);
		panelStart.add(buttonFinance);
		panelStart.add(buttonNotepad);
		panelStart.add(buttonHelp);
		panelStart.add(signOut);
		
		// Adding panel to frame
		fMain.add(panelStart);
		fMain.setSize(1200, 200);
		fMain.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent e) {}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void actionPerformed(ActionEvent e) {
		// ALL MAIN PROGRAM EVENTS
		if (e.getSource() == buttonCal) {
			new Calendar().calendar();
			fMain.dispose();
		}
		else if (e.getSource() == buttonLists) {
			try {
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
			new Help();
		}
		else if (e.getSource() == signOut) {
			fMain.dispose();
		}
	}

}