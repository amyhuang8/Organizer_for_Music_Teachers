/* Notes:
 * Status: FINISHED
 * Currently handles the saving of multiple users & pws */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*; 

public class Registration extends Frame implements ActionListener {
	private JFrame f;
	private JPanel panelUser, panelPw, panelButton;
	private JPanel emptySpace, emptySpace1, emptySpace2, panelMsg;
	private JLabel userL, pwL;
	private JTextArea success;
	private JLabel blank, blank1;
	private JTextField userTF, pwTF;
	private JButton createAccount;
	public String username, password;
	
	public Registration() {		
		// New frame
		f = new JFrame("Registration");
		
		// New panels
		emptySpace = new JPanel();
		emptySpace.setBackground(Color.gray);
		
		panelUser = new JPanel();
		panelUser.setLayout(new GridLayout(1, 2));
		panelUser.setBackground(Color.gray);
		
		emptySpace1 = new JPanel();
		emptySpace1.setBackground(Color.gray);
		
		panelPw = new JPanel();
		panelPw.setLayout(new GridLayout(1, 2));
		panelPw.setBackground(Color.gray);
		
		emptySpace2 = new JPanel();
		emptySpace2.setBackground(Color.gray);
		
		panelButton = new JPanel();
		panelButton.setLayout(new GridLayout(1, 3));
		panelButton.setBackground(Color.gray);
		
		panelMsg = new JPanel();
		panelMsg.setBackground(Color.gray);
		
		// New labels
		blank = new JLabel("");
		blank.setBackground(Color.gray);
		
		blank1 = new JLabel("");
		blank1.setBackground(Color.gray);
		
		userL = new JLabel("Username:");
		userL.setBackground(Color.gray);
		userL.setForeground(Color.white);
		
		pwL = new JLabel("Password:");
		pwL.setBackground(Color.gray);
		pwL.setForeground(Color.white);
		
		success = new JTextArea();
		success.setBackground(Color.gray);
		success.setForeground(Color.white);
		success.setVisible(false);
		success.setEditable(false);
		
		// New textfields
		userTF = new JTextField("");
		
		pwTF = new JTextField("");
		
		// New button
		createAccount = new JButton("Create Account");
		createAccount.setBackground(Color.green);
		createAccount.addActionListener(this);
		
		// Adding components to panel
		panelUser.add(userL);
		panelUser.add(userTF);
		
		panelPw.add(pwL);
		panelPw.add(pwTF);
		
		panelButton.add(blank);
		panelButton.add(createAccount);
		panelButton.add(blank1);
		
		panelMsg.add(success);
		
		// Adding panel to frame
		f.add(emptySpace);
		f.add(panelUser);
		f.add(emptySpace1);
		f.add(panelPw);
		f.add(emptySpace2);
		f.add(panelButton);
		f.add(panelMsg);
		f.setLayout(new GridLayout(7, 1));
		f.setSize(400, 400);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}
	
	public void savingInfo(String u, String p) throws IOException {
		File directory = new File(u);
		
		if (u.equals("") || p.equals("")) {
			success.setText("Please create a valid username and password.");
			success.setVisible(true);
		}
		else if (directory.isDirectory()) {
			success.setText("User already exists.\nPlease sign in, or make a different account.");
			success.setVisible(true);
		}
		else {
			// Creating new directory
			directory.mkdir();
			
			// Creating new file
			FileWriter fw = new FileWriter(directory + "/login_info.txt"); 
			
			// Writing to the file
			for (int i = 0; i < u.length(); i++) {
				fw.write(u.charAt(i));
			}
					
			fw.write("\n");
				
			for (int i = 0; i < p.length(); i++) {
				fw.write(p.charAt(i));
			}
				       
			// Closing the file reader
			fw.close();
			
			// Closes entire frame
			f.dispose();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		username = userTF.getText();
		password = pwTF.getText();
		
		try {
			// Calls saving method
			savingInfo(username, password);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}