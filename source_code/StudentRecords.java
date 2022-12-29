/* Notes:
 * Status: FINISHED
 * Currently displays all students' names
 * Currently has empty, editable textfields under labels for each student
 * Currently saves inputed text
 * Currently has scrollbar for both names and textfields
 * Currently sorts first names A-Z and last names A-Z
 * Currently searches for first occurrence of student name */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class StudentRecords extends MainProgram {
	private JFrame fSearch;
	private JLabel lResult = new JLabel();
	
	private JPanel mainButtons, top, names, editableArea;
	private JButton sortAZ_First, sortAZ_Last, search, save, find;
	private JTextField name;
	private JTextField[] studentNames;
	private JTextArea[][] textbox;
	private int count = 0;
	
	private File directory = new File(username + "/StudentLists");
	private File newDirectory = new File(username + "/StudentRecords");
	
	public void studentRecords() throws IOException {
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
		
		buttonRecords.setEnabled(false);
		buttonRecords.setBackground(new Color(150, 250, 200));
		
		// New buttons
		sortAZ_First = new JButton("Sort A-Z (F/N)");
		sortAZ_First.setBackground(c);
		sortAZ_First.addActionListener(this);
		
		sortAZ_Last = new JButton("Sort A-Z (L/N)");
		sortAZ_Last.setBackground(c);
		sortAZ_Last.addActionListener(this);
		
		search = new JButton("Search");
		search.setBackground(c);
		search.addActionListener(this);
		
		save = new JButton("Save");
		save.setBackground(c);
		save.addActionListener(this);
		
		// New panels
		JPanel sidebar = new JPanel();
		sidebar.setLayout(new GridLayout(1, 1));
		
		names = new JPanel();
		
		JPanel buttonsLabels = new JPanel();
		buttonsLabels.setLayout(new GridLayout(1, 2));
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 4));
		
		JPanel labels = new JPanel();
		labels.setLayout(new GridLayout(1, 4));
		
		// Adding buttons to panel
		buttons.add(sortAZ_First);
		buttons.add(sortAZ_Last);
		buttons.add(search);
		buttons.add(save);
		
		// New labels
		JLabel contactInfo = new JLabel("Contact Information");
		contactInfo.setVisible(true);
		
		JLabel currentRep = new JLabel("Current Repertoire");
		currentRep.setVisible(true);
		
		JLabel lessonDate = new JLabel("Lesson Date");
		lessonDate.setVisible(true);
		
		JLabel notes = new JLabel("Notes");
		notes.setVisible(true);
		
		// Adding labels to panel
		labels.add(contactInfo);
		labels.add(currentRep);
		labels.add(lessonDate);
		labels.add(notes);
		
		// Adding panels to panel
		buttonsLabels.add(buttons);
		buttonsLabels.add(labels);
		
		// New textfields
		try {
			FileReader fr = new FileReader(directory + "/Students_MasterList.txt");
			
			// Determining how many students there are currently
			int ch;
			String studentName = "";
			
			while (fr.ready()) {
				while ((ch = fr.read()) != -1 && ((char) ch != '\n')) {
					studentName += (char) ch;
				}
				
				count++;
				studentName = "";
			}
			
			fr.close();
			
			String[] students = new String[count];
			count = 0;
			studentName = "";
			
			FileReader fr2 = new FileReader(directory + "/Students_MasterList.txt");
			
			while (fr2.ready()) {
				while ((ch = fr2.read()) != -1 && ((char) ch != '\n')) {
					studentName += (char) ch;
				}
				
				if (!(studentName.equals(""))) {
					students[count] = studentName;
					count++;
				}
				studentName = "";
			}
			
			fr2.close();
			
			// Adding names to names panel
			studentNames = new JTextField[count];
			
			for (int i = 0; i < count; i++) {
				studentNames[i] = new JTextField();
				studentNames[i].setEditable(false);
				studentNames[i].setText((i + 1) + ". " + students[i]);
				names.add(studentNames[i]);
			}
			
			names.setLayout(new GridLayout(count, 1));
			
			// New scrollable content pane
			JScrollPane scrPane = new JScrollPane(names);
			scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			sidebar.add(scrPane);
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			
			JLabel na = new JLabel("No students' records available.");
			na.setVisible(true);
			
			names.add(na);
			// Smth like "No students available"
			
			sidebar.add(names);
		}	
		
		// New panel
		editableArea = new JPanel();
		editableArea.setLayout(new GridLayout(count, 4));
		
		// New textfields
		textbox = new JTextArea[count][4];
		
		// Reading info from text files
		for (int rows = 0; rows < count; rows++) {			
			for (int columns = 0; columns < 4; columns++) {
				String fileName = "";
				
				for (int i = 0; i < studentNames[rows].getText().length(); i++) {
					if (studentNames[rows].getText().charAt(i) >= 65) {
						fileName += studentNames[rows].getText().charAt(i);
					}
				}
				
				fileName = fileName.replace(" ", "").replace("\r", "");
				
				if (columns == 0) {
					try {
						// Reading contact info
						FileReader frContact = new FileReader(directory + "/" + fileName + ".txt");
						int chContact;
						String contactInput = "";
						
						while ((chContact = frContact.read()) != -1) {
							contactInput += (char) chContact;
						}
						
						frContact.close();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(contactInput);
						textbox[rows][columns].setEditable(true);
					}
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setEditable(true);
					}
				}
				else if (columns == 1){
					try {
						// Reading other boxes
						FileReader frMain = new FileReader(newDirectory + "/" + fileName + "Rep.txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain.read()) != -1) {
							input += (char) newCh;
						}
							
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(input);
						textbox[rows][columns].setEditable(true);
						
						frMain.close();
					}
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setEditable(true);
					}
				}
				else if (columns == 2){
					try {
						// Reading other boxes
						FileReader frMain2 = new FileReader(newDirectory + "/" + fileName + "LessonDate.txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain2.read()) != -1) {
							input += (char) newCh;
						}
							
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(input);
						textbox[rows][columns].setEditable(true);
						
						frMain2.close();
					}
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setEditable(true);
					}
				}
				else {
					try {
						// Reading other boxes
						FileReader frMain3 = new FileReader(newDirectory + "/" + fileName + "Notes.txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain3.read()) != -1) {
							input += (char) newCh;
						}
							
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(input);
						textbox[rows][columns].setEditable(true);
						
						frMain3.close();
					}
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setEditable(true);
					}
				}
			}
		}
		
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < 4; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						textbox[i][j].setBackground(Color.pink);
					}
				}
				else {
					if (j % 2 != 0) {
						textbox[i][j].setBackground(Color.pink);
					}
				}
				editableArea.add(textbox[i][j]);
			}
		}
		
		// New scrollable content pane
		JScrollPane scrPane2 = new JScrollPane(editableArea);
		scrPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		top = new JPanel();
		top.setLayout(new GridLayout(2, 1));
		top.add(mainButtons);
		top.add(buttonsLabels);
		
		panelRecords = new JPanel();
		panelRecords.setLayout(new GridLayout(1, 2));
		panelRecords.add(sidebar);
		panelRecords.add(scrPane2);
		
		// Adding components frame
		fMain.add(top);
		fMain.add(panelRecords);
		fMain.setLayout(new GridLayout(2, 1));
		fMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fMain.setVisible(true);
		fMain.remove(panelStart);
	}

	public void updateDisplay() throws IOException {
		// Clearing bottom half of the frame
		panelRecords.removeAll();
		names.removeAll();
		editableArea.removeAll();
		fMain.remove(panelRecords);
		
		// New textfields		
		for (int i = 0; i < count; i++) {
			names.add(studentNames[i]);
		}
		
		// New textfields
		textbox = new JTextArea[count][4];
		
		// Reading info from text files
		for (int rows = 0; rows < count; rows++) {			
			for (int columns = 0; columns < 4; columns++) {
				String fileName = "";
				
				for (int i = 0; i < studentNames[rows].getText().length(); i++) {
					if (studentNames[rows].getText().charAt(i) >= 65) {
						fileName += studentNames[rows].getText().charAt(i);
					}
				}
				
				fileName = fileName.replace(" ", "").replace("\r", "");
				
				if (columns == 0) {
					try {
						// Reading contact info
						FileReader frContact = new FileReader(directory + "/" + fileName + ".txt");
						int chContact;
						String contactInput = "";
						
						while ((chContact = frContact.read()) != -1) {
							contactInput += (char) chContact;
						}
						
						frContact.close();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(contactInput);
						textbox[rows][columns].setEditable(true);
					}
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setEditable(true);
					}
				}
				else if (columns == 1){
					try {
						// Reading other boxes
						FileReader frMain = new FileReader(newDirectory + "/" + fileName + "Rep.txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain.read()) != -1) {
							input += (char) newCh;
						}
							
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(input);
						textbox[rows][columns].setEditable(true);
						
						frMain.close();
					}
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setEditable(true);
					}
				}
				else if (columns == 2){
					try {
						// Reading other boxes
						FileReader frMain2 = new FileReader(newDirectory + "/" + fileName + "LessonDate.txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain2.read()) != -1) {
							input += (char) newCh;
						}
							
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(input);
						textbox[rows][columns].setEditable(true);
						
						frMain2.close();
					}
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setEditable(true);
					}
				}
				else {
					try {
						// Reading other boxes
						FileReader frMain3 = new FileReader(newDirectory + "/" + fileName + "Notes.txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain3.read()) != -1) {
							input += (char) newCh;
						}
							
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(input);
						textbox[rows][columns].setEditable(true);
						
						frMain3.close();
					}
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setEditable(true);
					}
				}
			}
		}
		
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < 4; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						textbox[i][j].setBackground(Color.pink);
					}
				}
				else {
					if (j % 2 != 0) {
						textbox[i][j].setBackground(Color.pink);
					}
				}
				editableArea.add(textbox[i][j]);
			}
		}
		
		// New scrollable content pane
		JScrollPane scrPane2 = new JScrollPane(editableArea);
		scrPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Adding panels to panel
		panelRecords.add(names);
		panelRecords.add(editableArea);
		
		// Adding panel to frame
		fMain.add(panelRecords);
		fMain.setLayout(new GridLayout(2, 1));
		fMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fMain.setVisible(true);
		fMain.remove(panelStart);
	}
	
	public void sortFirstNames() throws IOException {
		int min_index;
		String temp;
		String[] array = new String[studentNames.length];
		int ch, count = 0;
		String studentName = "";
		
		// Temporary 1D array
		FileReader fr = new FileReader(directory + "/Students_MasterList.txt");
		
		while (fr.ready()) {
			while ((ch = fr.read()) != -1 && ((char) ch != '\n')) {
				studentName += (char) ch;
			}
			
			if (!(studentName.equals(""))) {
				array[count] = studentName;
				count++;
			}
			studentName = "";
		}
		
		fr.close();
		
		// Sorting process
		// Moving the lower boundary of the unsorted section
		for (int i = 0; i < array.length - 1; i++) {
			// Finding min element in the unsorted section
			min_index = i;

			for (int j = i + 1; j < array.length; j++) {
				if (array[j].compareTo(array[min_index]) < 0) {
					min_index = j;
				}
			}

			// Swapping the found minimum element w/ first element
			temp = array[min_index];
			array[min_index] = array[i];
			array[i] = temp;
		}
		
		// Updating display
		for (int i = 0; i < count; i++) {
			studentNames[i].setText((i + 1) + ". " + array[i]);
		}
	}

	public void sortLastNames() throws IOException {
		int min_index, ch;
		String lastName = "";
		String temp;
		String[] array = new String[40];
		String[] names = new String[40];
		
		try {
			FileReader fr1 = new FileReader(directory + "/Students_MasterList.txt");
			
			// Creating temporary 1D arrays
			for (int i = 0; i < count; i++) {
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
		
		// Updating display
		for (int i = 0; i < count; i++) {
			studentNames[i].setText((i + 1) + ". " + names[i]);
		}
	}
		
	public void save() throws IOException, NullPointerException {
		// Creating new directory
		newDirectory.mkdir();
		
		// Writing info to text files
		for (int rows = 0; rows < studentNames.length; rows++) {
			String original = "";
			String fileName;
			
			for (int k = 0; k < studentNames[rows].getText().length(); k++) {
				if (studentNames[rows].getText().charAt(k) >= 65) {
					original += studentNames[rows].getText().charAt(k);
				}
			}
			
			fileName = original.replace(" ", "").replace("\r", "");
						
			for (int columns = 0; columns < 4; columns++) {
				// Editing student lists to update contact info
				if (columns == 0) {
					FileWriter fwContact = new FileWriter(directory + "/" + fileName + ".txt");
					fwContact.write(textbox[rows][columns].getText());
					fwContact.close();
					
					FileReader master = new FileReader(directory + "/Students_MasterList.txt");
					String s = "", newName = "";
					int ch;
					int num = 0;
					
					// Reading old lists
					while ((ch = master.read()) != -1) {
						s += (char) ch;
					}
					
					master.close();
					
					for (int i = 0; i < textbox[rows][columns].getText().length(); i++) {						
						if (textbox[rows][columns].getText().charAt(i) == '\n') {
							num++;
							
							if (num < 2) {
								newName += " ";
							}
						}
						else {
							newName += textbox[rows][columns].getText().charAt(i);
						}
						
						if (num >= 2) {
							i = textbox[rows][columns].getText().length() - 1;
						}
					}
					
					s = s.replaceAll(original, newName);
					
					FileWriter fwMaster = new FileWriter(directory + "/Students_MasterList.txt");
					
					fwMaster.write(s);
					
					fwMaster.close();
				}
				// Writing other inputed info to file
				else if (columns == 1) {
					FileWriter fw = new FileWriter(newDirectory + "/" + fileName + "Rep.txt");
					
					fw.write(textbox[rows][columns].getText());
					
					fw.close();
				}
				else if (columns == 2) {
					FileWriter fw2 = new FileWriter(newDirectory + "/" + fileName + "LessonDate.txt");
					
					fw2.write(textbox[rows][columns].getText());
					
					fw2.close();
				}
				else {
					FileWriter fw3 = new FileWriter(newDirectory + "/" + fileName + "Notes.txt");
						
					fw3.write(textbox[rows][columns].getText());
					
					fw3.close();
				}
			}
		}
	}

	public void search() {
		// New frame
		fSearch = new JFrame("Search for Student");
		fSearch.setSize(200, 200);
		fSearch.setLayout(new GridLayout(3, 1));
		fSearch.setVisible(true);
		
		// New panel
		JPanel prompt = new JPanel();
		
		JPanel process = new JPanel();
		process.setLayout(new GridLayout(1, 2));
		
		JPanel result = new JPanel();
		
		// New labels
		JLabel lPrompt = new JLabel("Enter the name to search for:");
		
		// New textfield
		name = new JTextField();
		name.setEditable(true);
		
		// New button
		find = new JButton("Search");
		find.addActionListener(this);
		
		// Adding components to panels
		prompt.add(lPrompt);
		process.add(name);
		process.add(find);
		result.add(lResult);
		
		// Adding panels to frame
		fSearch.add(prompt);
		fSearch.add(process);
		fSearch.add(result);
	}

	public void find() {
		int num = 0;
		boolean found = false;
		
		while (found != true && num < count) {
			if ((studentNames[num].getText().toLowerCase()).contains(name.getText().toLowerCase())) {
				lResult.setText(name.getText() + " is number " + (num + 1) + ".");
				found = true;
			}
			else {
				lResult.setText("Student not found.");
			}
			num++;
		}
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
				new Calendar().calendar();
				fMain.dispose();
			}
			catch (NullPointerException npe) {
				npe.fillInStackTrace();
				new Calendar().calendar();
				fMain.dispose();
			}
		}
		else if (e.getSource() == buttonLists) {
			try {
				save();
				
				try {
					new StudentLists().studentLists();
					fMain.dispose();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				
				try {
					new StudentLists().studentLists();
					fMain.dispose();
				}
				catch (IOException ioe2) {
					ioe2.printStackTrace();
				}
			}
			catch (NullPointerException npe) {
				npe.fillInStackTrace();
				
				try {
					new StudentLists().studentLists();
					fMain.dispose();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
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
			new HelpRecords();
		}
		else if (e.getSource() == signOut) {
			try {
				save();
				fMain.dispose();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				fMain.dispose();
			}
			catch (NullPointerException npe) {
				npe.fillInStackTrace();
				fMain.dispose();
			}
		}
		
		// ALL STUDENT RECORDS EVENTS
		if (e.getSource() == sortAZ_First) {
			try {
				sortFirstNames();
				fMain.dispose();
				updateDisplay();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}	
		}
		else if (e.getSource() == sortAZ_Last) {
			try {
				sortLastNames();
				fMain.dispose();
				updateDisplay();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}	
		}
		else if (e.getSource() == save) {
			try {
				save();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == search) {
			search();
		}
		
		// Search button
		if (e.getSource() == find) {
			find();
			fSearch.dispose();
			search();
		}
	}
	
	public void windowClosing(WindowEvent e) {
		try {
			save();
			fMain.dispose();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			fMain.dispose();
		}
		catch (NullPointerException npe) {
			npe.fillInStackTrace();
			fMain.dispose();
		}	
	}
}