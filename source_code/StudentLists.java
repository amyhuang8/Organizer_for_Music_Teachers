/* Notes:
 * Status: FINISHED
 * Currently saves multiple students' contact information
 * Currently loads previously saved students' info
 * Currently deletes students' info
 * Currently displays newly added student info
 * Currently sorts first names & last names from A-Z */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class StudentLists extends MainProgram {
	private JFrame fNewStudent, listOfNames;
	private JPanel mainButtons, listsButtons, panelNewStu, buttons;
	private JTextField[][] students = new JTextField[8][5];
	private JTextField[] studentNames;
	private JTextField fName, lName, phone, mail;
	private JButton sortAZ_First, sortAZ_Last, addStudent, deleteStudent, addNew, cancel;
	private JButton delete, cancelDelete;
	private Object object;
	private File directory = new File(username + "/StudentLists");
	
	public void studentLists() throws IOException {
		// New panel
		mainButtons = new JPanel();
		mainButtons.setLayout(new GridLayout(1, 7));  
		mainButtons.setBackground(Color.gray);
		
		listsButtons = new JPanel();
		listsButtons.setLayout(new GridLayout(1, 4));
		
		panelLists = new JPanel();
		panelLists.setLayout(new GridLayout(8, 5));
		
		// New big panel
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(2, 1));
		
		// New buttons
		sortAZ_First = new JButton("Sort A-Z (First Names)");
		sortAZ_First.setBackground(c);
		sortAZ_First.addActionListener(this);
		
		sortAZ_Last = new JButton("Sort A-Z (Last Names)");
		sortAZ_Last.setBackground(c);
		sortAZ_Last.addActionListener(this);
		
		addStudent = new JButton("Add a Student");
		addStudent.setBackground(c);
		addStudent.addActionListener(this);
		
		deleteStudent = new JButton("Delete a Student");
		deleteStudent.setBackground(c);
		deleteStudent.addActionListener(this);
		
		// New textfields
		for (int rows = 0; rows < 8; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				students[rows][columns] = new JTextField();
				students[rows][columns].setEditable(false);
				panelLists.add(students[rows][columns]);
			}
		}
		
		// Updating textfields if there are pre-saved students already
		try {
			FileReader fr = new FileReader(directory + "/Students_MasterList.txt");
			
			int ch;
			int r = 0, c = 0;
			boolean maxCol = false;
			String s = "";
			
			if (fr.ready()) {				
				do {
					while ((ch = fr.read()) != -1 && ((char) ch != '\n')) {
						s += (char) ch;
					}
					
					// ensures that empty lines (deleted students) are not added
					if (!(s.equals(""))) {
						students[r][c].setText(s);
						
						s = "";
						
						if (c == 4) {
							maxCol = true;
						}
						
						if (maxCol == false) {
							c++;
						}
						else {
							if (r == 7) {
								break;
							}
							else {
								r++;
								c = 0;
								maxCol = false;
							}
						}
					}
				} while (fr.ready());
			}
			
			fr.close();
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		// Adding components to main buttons panel
		mainButtons.add(buttonCal);
		mainButtons.add(buttonLists);
		mainButtons.add(buttonRecords);
		mainButtons.add(buttonFinance);
		mainButtons.add(buttonNotepad);
		mainButtons.add(buttonHelp);
		mainButtons.add(signOut);
		
		buttonLists.setEnabled(false);
		buttonLists.setBackground(Color.pink);
		
		// Adding components to lists buttons panel
		listsButtons.add(sortAZ_First);
		listsButtons.add(sortAZ_Last);
		listsButtons.add(addStudent);
		listsButtons.add(deleteStudent);
		
		// Adding all buttons to buttons panel
		buttons.add(mainButtons);
		buttons.add(listsButtons);
		
		// Adding panel to frame
		fMain.add(buttons);
		fMain.add(panelLists);
		fMain.setLayout(new GridLayout(2, 1));
		fMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fMain.setVisible(true);
		fMain.remove(panelStart);
		fMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void studentListsUpdated() {
		// Clearing panels and frame
		panelLists.removeAll();
		fMain.remove(panelLists);
		
		// New textfields		
		for (int rows = 0; rows < 8; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				panelLists.add(students[rows][columns]);
			}
		}
				
		// Adding panel to frame
		fMain.add(panelLists);
		fMain.setLayout(new GridLayout(2, 1));
		fMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fMain.setVisible(true);
		fMain.remove(panelStart);
	}

	public void addAStudent() {
		// New frame
		fNewStudent = new JFrame("Add Student Information");
		
		// New panel
		panelNewStu = new JPanel();
		panelNewStu.setLayout(new GridLayout(5, 2));
		
		// New labels
		JLabel firstName = new JLabel("First Name:");
		JLabel lastName = new JLabel("Last Name:");
		JLabel phoneNum = new JLabel("Phone Number:");
		JLabel email = new JLabel("Email:");
		
		// New textfields
		fName = new JTextField();
		lName = new JTextField();
		phone = new JTextField();
		mail = new JTextField();
		
		// New button
		addNew = new JButton("Add Student");
		addNew.addActionListener(this);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		
		// Adding components to panel
		panelNewStu.add(firstName);
		panelNewStu.add(fName);
		panelNewStu.add(lastName);
		panelNewStu.add(lName);
		panelNewStu.add(phoneNum);
		panelNewStu.add(phone);
		panelNewStu.add(email);
		panelNewStu.add(mail);
		panelNewStu.add(addNew);
		panelNewStu.add(cancel);
		
		// Adding panel to frame
		fNewStudent.add(panelNewStu);
		fNewStudent.setSize(400, 400);
		fNewStudent.setVisible(true);		
	}
	
	public void savingStudent() throws IOException {
		// Creating directory
		directory.mkdir();
		
		// ensure that an empty student will not be added
		if (!(fName.getText().equals("") && lName.getText().equals("") && phone.getText().equals("") && mail.getText().equals(""))) {
			// Declaring file writer
			String s = fName.getText() + lName.getText();
			s = s.replaceAll("\\s", "");
			
			FileWriter fw = new FileWriter(directory + "/" + s + ".txt");
			
			// Writing to the file
			// first name
			for (int i = 0; i < fName.getText().length(); i++) {
				fw.write(fName.getText().charAt(i));
			}
					
			fw.write("\n");
			
			// last name
			for (int i = 0; i < lName.getText().length(); i++) {
				fw.write(lName.getText().charAt(i));
			}
					
			fw.write("\n");
			
			// phone number
			for (int i = 0; i < phone.getText().length(); i++) {
				fw.write(phone.getText().charAt(i));
			}
					
			fw.write("\n");
			
			// email
			for (int i = 0; i < mail.getText().length(); i++) {
				fw.write(mail.getText().charAt(i));
			}
			
			// Closing file writer
			fw.close();
		}
		
		// SAVING ALL STUDENTS
		// Declaring file writer
		FileWriter fw = new FileWriter(directory + "/Students_MasterList.txt", true);
		
		// Writing to the file
		// first name
		for (int i = 0; i < fName.getText().length(); i++) {
			fw.append(fName.getText().charAt(i));
		}
		
		fw.append(' ');
		
		// last name
		for (int i = 0; i < lName.getText().length(); i++) {
			fw.append(lName.getText().charAt(i));
		}
		
		fw.append('\n');
		
		// Closing file writer
		fw.close();
	}
	
	public void deletePopUp() {
		// New frame
		listOfNames = new JFrame("Delete a Student");
		
		// New panels
		JPanel names = new JPanel();
		
		JPanel userPrompt = new JPanel();
		userPrompt.setBackground(Color.gray);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		
		// New label
		JLabel prompt = new JLabel("Please select a student to delete.");
		
		// New buttons
		delete = new JButton("Delete");
		delete.addActionListener(this);
		
		cancelDelete = new JButton("Cancel");
		cancelDelete.addActionListener(this);
		
		// Adding prompt to prompt panel
		userPrompt.add(prompt);
		
		// Determining how many students there are currently
		int count = 0;
		
		for (int rows = 0; rows < 8; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				if (!(students[rows][columns].getText().equals(""))) {
					count++;
				}
			}
		}
		
		// Adding names to names panel
		int newCount = 0;
		
		studentNames = new JTextField[count];
		
		for (int rows = 0; rows < 8; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				if (!(students[rows][columns].getText().equals(""))) {
					studentNames[newCount] = new JTextField();
					studentNames[newCount].setEditable(false);
					studentNames[newCount].setText(students[rows][columns].getText());
					studentNames[newCount].addMouseListener(this);
					names.add(studentNames[newCount]);
					newCount++;
				}
			}
		}
		
		names.setLayout(new GridLayout(count, 1));
		
		// New scrollable content pane
		JScrollPane scrPane = new JScrollPane(names);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Adding buttons to buttons panel
		buttons.add(delete);
		buttons.add(cancelDelete);
		
		// Adding panels to frame
		listOfNames.add(userPrompt);
		listOfNames.add(scrPane);
		listOfNames.add(buttons);
		
		listOfNames.setLayout(new GridLayout(3, 1));
		listOfNames.setSize(400, 400);
		listOfNames.setVisible(true);		
	}
	
	public void deleteStudent() throws IOException {
		// Declaring file writer
		FileWriter fw;
		
		// Determining file name
		String originalName = ((JTextField) object).getText();
		String s = originalName.replaceAll("\\s", "");
		
		try {
			fw = new FileWriter(directory + "/" + s + ".txt");
			fw.write("");
			
			// Determining index of original name
			int indexRows = 0, indexColumns = 0;
			boolean found = false;
			
			for (int rows = 0; rows < 8; rows++) {
				for (int columns = 0; columns < 5; columns++) {
					if (students[rows][columns].getText().equals(originalName) && found == false) {
						indexRows = rows;
						indexColumns = columns;
						found = true;
					}
				}
			}
			
			// Deleting the name from the display
			for (int rows = indexRows; rows < 8; rows++) {
				for (int columns = indexColumns; columns < 5; columns++) {
					if (columns != 4) {
						students[rows][columns].setText(students[rows][columns + 1].getText());
					}
					else if (columns == 4 && rows != 7) {
						students[rows][columns].setText(students[rows + 1][0].getText());
					}
					else if (columns == 4 && rows == 7) {
						students[rows][columns].setText("");
					}
				}
			}
			
			// Closing file writer
			fw.close();
			
			// Editing master list
			FileReader frMaster = new FileReader(directory + "/Students_MasterList.txt");
			
			int ch;
			String nameToDelete = "";
			
			while ((ch = frMaster.read()) != -1) {
				nameToDelete += (char) ch;
			}
			
			frMaster.close();
			
			nameToDelete = nameToDelete.replace(originalName + "\n", "").replace("\r", "");
			
			System.out.print(nameToDelete);
			
			FileWriter fwMaster = new FileWriter(directory + "/Students_MasterList.txt");
			
			fwMaster.write(nameToDelete);
			
			fwMaster.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void display() throws IOException {		
		boolean updated = false;
		
		for (int rows = 0; rows < 8; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				if (!(fName.getText().equals("") && lName.getText().equals("")) && updated == false) {
					if (students[rows][columns].getText().equals("")) {
						students[rows][columns].setText(fName.getText() + " " + lName.getText());
						updated = true;
					}
				}
			}
		}
		
		updated = false;
	}
	
	public void sortFirst() {
		int count = 0, min_index;
		String temp;
		String[] array = new String[40];
		
		// Creating a temporary 1D array
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				array[count] = students[i][j].getText();
				count++;
			}
		}
		
		// Sorting process
		// Moving the lower boundary of the unsorted section
		for (int i = 0; i < array.length - 1; i++) {
			// Finding min element in the unsorted section
			min_index = i;

			for (int j = i + 1; j < array.length; j++) {
				if (!(array[j].equals(""))) {
					if (array[j].compareTo(array[min_index]) < 0) {
						min_index = j;
					}
				}
			}

			// Swapping the found minimum element w/ first element
			temp = array[min_index];
			array[min_index] = array[i];
			array[i] = temp;
		}
		
		// Updating students lists
		count = 0;
	
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				students[i][j].setText(array[count]);
				count++;
			}
		}
	}
	
	public void sortLast() throws IOException {
		int min_index, ch, count = 0;
		String lastName = "";
		String temp;
		String[] array = new String[40];
		String[] names = new String[40];
		
		try {
			FileReader fr = new FileReader(directory + "/Students_MasterList.txt");
			
			// Determining how many students there are currently
			int ch2;
			String studentName = "";
			
			while (fr.ready()) {
				while ((ch2 = fr.read()) != -1 && ((char) ch2 != '\n')) {
					studentName += (char) ch2;
				}
				
				if (!(studentName.equals(""))) {
					count++;
				}
				
				studentName = "";
			}
			
			fr.close();
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		try {
			FileReader fr1 = new FileReader(directory + "/Students_MasterList.txt");
			
			// Creating temporary 1D arrays
			for (int i = 0; i < 40; i++) {
				String fileName = "";
				
				while ((ch = fr1.read()) != -1 && ((char) ch != '\n')) {
					fileName += (char) ch;
				}
				
				if (!(fileName.equals(""))) {
					names[i] = fileName;
					
					lastName = fileName.substring(fileName.indexOf(" ") + 1).toLowerCase();
					
					array[i] = lastName;
				}
			}
			
			fr1.close();
			
			// Sorting process
			// Moving the lower boundary of the unsorted section
			for (int i = 0; i < count - 1; i++) {
				// Finding min element in the unsorted section
				min_index = i;
	
				for (int j = i + 1; j < count; j++) {
					if (array[j].compareTo(array[min_index]) < 0) {
						min_index = j;
					}
					else if (array[j].compareTo(array[min_index]) == 0) {
						if (names[j].compareTo(names[min_index]) < 0) {
							min_index = j;
						}
					}
				}
	
				// Swapping the found minimum element w/ first element
				temp = array[min_index];
				array[min_index] = array[i];
				array[i] = temp;
				
				temp = names[min_index];
				names[min_index] = names[i];
				names[i] = temp;
			}
		}
		catch (FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		
		// Updating students lists
		count = 0;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++) {
				students[i][j].setText(names[count]);
				count++;
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		// ALL STUDENT LISTS EVENTS
		object = e.getSource();
    }
	
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
			new HelpLists();
		}
		else if (e.getSource() == signOut) {
			fMain.dispose();
		}
		
		// ALL STUDENT LISTS EVENTS
		if (e.getSource() == addStudent) {
			addAStudent();
		}
		else if (e.getSource() == deleteStudent) {
			deletePopUp();
		}
		else if (e.getSource() == sortAZ_First) {
			sortFirst();
			fMain.dispose();
			studentListsUpdated();
		}
		else if (e.getSource() == sortAZ_Last){
			try {
				sortLast();
				fMain.dispose();
				studentListsUpdated();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		// Adding new student events
		if (e.getSource() == addNew) {
			try {
				savingStudent();
				display();
				fNewStudent.dispose();
				fMain.dispose();
				studentListsUpdated();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == cancel) {
			fNewStudent.dispose();
		}
		
		// Deleting student events
		if (e.getSource() == delete) {
			try {
				deleteStudent();
				listOfNames.dispose();
				fMain.dispose();
				studentListsUpdated();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == cancelDelete) {
			listOfNames.dispose();
		}
	}
}