/* Notes:
 * Status: FINISHED
 * Currently checks for validity of login information */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class LoginValidation extends Frame implements ActionListener {
	private JFrame f;
	private JPanel panelUser, panelPw, panelButtons, panelMsg;
	private JPanel emptySpace, emptySpace1, emptySpace2, emptySpace3;
	private JLabel userL, pwL;
	private JLabel blank, blank1, blank2;
	private JTextArea success;
	private JTextField userTF, pwTF;
	private JButton signIn, register;
	private boolean correct = false;
	protected static String username, password;
	
	public void loginValidation() {
		// New frame
		f = new JFrame("Login");
		
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
		
		panelButtons = new JPanel();
		panelButtons.setLayout(new GridLayout(1, 3));
		panelButtons.setBackground(Color.gray);
		
		panelMsg = new JPanel();
		panelMsg.setBackground(Color.gray);
		
		// New labels
		blank = new JLabel("");
		blank.setBackground(Color.gray);
		
		blank1 = new JLabel("");
		blank1.setBackground(Color.gray);
		
		blank2 = new JLabel("");
		blank2.setBackground(Color.gray);
		
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
		
		// New buttons
		register = new JButton("Register");
		register.setBackground(Color.green);
		register.addActionListener(this);
		
		signIn = new JButton("Sign In");
		signIn.setBackground(Color.green);
		signIn.addActionListener(this);
		
		// Adding components to panel
		panelUser.add(userL);
		panelUser.add(userTF);
		
		panelPw.add(pwL);
		panelPw.add(pwTF);
		
		panelButtons.add(blank);
		panelButtons.add(register);
		panelButtons.add(blank1);
		panelButtons.add(signIn);
		panelButtons.add(blank2);
		
		panelMsg.add(success);
		
		// Adding panel to frame
		f.add(emptySpace);
		f.add(panelUser);
		f.add(emptySpace1);
		f.add(panelPw);
		f.add(emptySpace2);
		f.add(panelButtons);
		f.add(panelMsg);
		f.setLayout(new GridLayout(7, 1));
		f.setSize(430, 400);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public String getPassword(FileReader fr) {
		// Variable declaration
		int ch;
		String actualPw = "";
		
		// Process
		try {
			// Reading the username
			while ((ch = fr.read()) != -1 && ((char) ch != '\n')) {
				actualPw += (char) ch;
			}
			
			actualPw = "";
			
			// Reading the password
			while ((ch = fr.read()) != -1) {
				actualPw += (char) ch;
			}
			
			return actualPw;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			
			return null;
		}
	}
	
	public void validation(String username, String password) throws IOException {
		// Variable declaration
		String actualPw;
		
		// Declaring directory
		File directory = new File(username);
		
		if (username.equals("") || password.equals("")) {
			success.setText("Please enter your username and password.");
			success.setVisible(true);
		}
		else {
			// Determining whether the user exists
			if (directory.isDirectory()) {
				// Declaring file reader
				FileReader fr = new FileReader(directory + "/login_info.txt");
				
				// Getting password
				actualPw = getPassword(fr);
				
				if (!(password.equals(actualPw))) { // Incorrect password				
					success.setText("Incorrect password.\n"
							+ "Please try again.");
					success.setVisible(true);
					
					// Closing file
					fr.close();
				}
				else { // Correct user & pw
					correct = true;
					
					// Closing file
					fr.close();
				}
			}
			else {
				// Username incorrect or user does not exist
				success.setText("Your username does not match any existing records.\n"
						+ "Please try again, or create a new account.");
				success.setVisible(true);
			}
		}
	}
	
	public void setLogin(String username, String password) {
		LoginValidation.username = username;
		LoginValidation.password = password;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signIn) {
			try {
				// Calls validation method
				validation(userTF.getText(), pwTF.getText());
				
				if (correct == true) {
					setLogin(userTF.getText(), pwTF.getText());
					f.dispose();
					new MainProgram();
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else {
			new Registration();
		}
	}
}