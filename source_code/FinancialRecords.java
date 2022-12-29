/* Notes:
 * Status: FINISHED
 * Currently displays all students' names
 * Currently has empty, editable textfields under labels for each student
 * Currently saves inputed text
 * Currently has scrollbar for both names and textfields
 * Currently sorts first names A-Z
 * Currently searches for first occurrence of student name
 * Currently has functional calculator */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;

public class FinancialRecords extends MainProgram {
	private JFrame fSearch, fCalculator;
	private JPanel text, numberButtons, operations, buttons;
	private JButton one, two, three, four, five, six, seven, eight, nine, zero, decimal;
	private JButton add, subtract, multiply, divide, equal, ans, leftP, rightP, clear;
	private JLabel displayArea = new JLabel(), result = new JLabel();
	private double ansDouble = 0;
	
	private JPanel mainButtons, top, names, editableArea;
	private JButton sortAZ_First, calculator, search, save, find;
	private JLabel lResult = new JLabel();
	private JCheckBox[][] frequencies;
	private JTextField name;
	private JTextField[] studentNames;
	private JTextArea[][] textbox;
	private int count = 0;
	private File directory = new File(username + "/StudentLists");
	private File newDirectory = new File(username + "/FinancialRecords");
	
	public void finance() throws IOException {
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
		
		buttonFinance.setEnabled(false);
		buttonFinance.setBackground(Color.pink);
		
		// New buttons
		sortAZ_First = new JButton("Sort A-Z (F/N)");
		sortAZ_First.setBackground(c);
		sortAZ_First.addActionListener(this);
		
		calculator = new JButton("Calculator");
		calculator.setBackground(c);
		calculator.addActionListener(this);
		
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
		buttons.add(calculator);
		buttons.add(search);
		buttons.add(save);
		
		// New labels
		JLabel payFreq = new JLabel("Payment Frequency");
		payFreq.setVisible(true);
		
		JLabel payAmount = new JLabel("Payment Amount");
		payAmount.setVisible(true);
		
		JLabel payDay = new JLabel("Next Payment Date");
		payDay.setVisible(true);
		
		JLabel notes = new JLabel("Notes");
		notes.setVisible(true);
		
		// Adding labels to panel
		labels.add(payFreq);
		labels.add(payAmount);
		labels.add(payDay);
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
		
		// Setting new checkbox string array
		frequencies = new JCheckBox[count][3];
		
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 0) {
					frequencies[i][j] = new JCheckBox("upon lesson date", false);
				}
				else if (j == 1) {
					frequencies[i][j] = new JCheckBox("weekly", false);
				}
				else {
					frequencies[i][j] = new JCheckBox("monthly", false);
				}
				
				frequencies[i][j].setOpaque(false);
			}
		}
		
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
					// Setting textbox
					textbox[rows][columns] = new JTextArea();
					textbox[rows][columns].setLayout(new GridLayout(3, 1));
					
					try {
						FileReader frMain = new FileReader(newDirectory + "/" + fileName + ".txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain.read()) != -1) {
							input += (char) newCh;
						}
						
						frMain.close();
						
						if (input.equals("upon lesson date")) {
							frequencies[rows][0].setSelected(true);
							
							for (int i = 0; i < 3; i++) {
								textbox[rows][columns].add(frequencies[rows][i]);
							}
						}
						else if (input.equals("weekly")) {
							frequencies[rows][1].setSelected(true);
							
							for (int i = 0; i < 3; i++) {
								textbox[rows][columns].add(frequencies[rows][i]);
							}
						}
						else if (input.equals("monthly")) {
							frequencies[rows][2].setSelected(true);
							
							for (int i = 0; i < 3; i++) {
								textbox[rows][columns].add(frequencies[rows][i]);
							}
						}
						else {
							for (int i = 0; i < 3; i++) {
								textbox[rows][columns].add(frequencies[rows][i]);
							}
						}
						
						textbox[rows][columns].setEditable(false);
					}
					catch (FileNotFoundException fnfe) {
						for (int i = 0; i < 3; i++) {
							textbox[rows][columns].add(frequencies[rows][i]);
						}
						
						textbox[rows][columns].setEditable(false);
					}
				}
				else if (columns == 1){
					try {
						// Reading other boxes
						FileReader frMain1 = new FileReader(newDirectory + "/" + fileName + "Amount.txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain1.read()) != -1) {
							input += (char) newCh;
						}
							
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(input);
						textbox[rows][columns].setEditable(true);
						
						frMain1.close();
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
						FileReader frMain2 = new FileReader(newDirectory + "/" + fileName + "Date.txt");
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
						textbox[i][j].setBackground(new Color(150, 250, 200));
					}
				}
				else {
					if (j % 2 != 0) {
						textbox[i][j].setBackground(new Color(150, 250, 200));
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
		
		panelFinance = new JPanel();
		panelFinance.setLayout(new GridLayout(1, 2));
		panelFinance.add(sidebar);
		panelFinance.add(scrPane2);
		
		// Adding components frame
		fMain.add(top);
		fMain.add(panelFinance);
		fMain.setLayout(new GridLayout(2, 1));
		fMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fMain.setVisible(true);
		fMain.remove(panelStart);
	}

	public void updateDisplay() throws IOException {
		// Clearing bottom half of the frame
		panelFinance.removeAll();
		names.removeAll();
		editableArea.removeAll();
		fMain.remove(panelFinance);
		
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
					// Setting textbox
					textbox[rows][columns] = new JTextArea();
					textbox[rows][columns].setLayout(new GridLayout(3, 1));
					
					try {
						FileReader frMain = new FileReader(newDirectory + "/" + fileName + ".txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain.read()) != -1) {
							input += (char) newCh;
						}
						
						frMain.close();
						
						if (input.equals("upon lesson date")) {
							frequencies[rows][0].setSelected(true);
							frequencies[rows][1].setSelected(false);
							frequencies[rows][2].setSelected(false);
							
							for (int i = 0; i < 3; i++) {
								textbox[rows][columns].add(frequencies[rows][i]);
							}
						}
						else if (input.equals("weekly")) {
							frequencies[rows][0].setSelected(false);
							frequencies[rows][1].setSelected(true);
							frequencies[rows][2].setSelected(false);
							
							for (int i = 0; i < 3; i++) {
								textbox[rows][columns].add(frequencies[rows][i]);
							}
						}
						else if (input.equals("monthly")) {
							frequencies[rows][0].setSelected(false);
							frequencies[rows][1].setSelected(false);
							frequencies[rows][2].setSelected(true);
							
							for (int i = 0; i < 3; i++) {
								textbox[rows][columns].add(frequencies[rows][i]);
							}
						}
						else {
							for (int i = 0; i < 3; i++) {
								frequencies[rows][0].setSelected(false);
								frequencies[rows][1].setSelected(false);
								frequencies[rows][2].setSelected(false);
								
								textbox[rows][columns].add(frequencies[rows][i]);
							}
						}
						
						textbox[rows][columns].setEditable(false);
					}
					catch (FileNotFoundException fnfe) {
						for (int i = 0; i < 3; i++) {
							textbox[rows][columns].add(frequencies[rows][i]);
						}
						
						textbox[rows][columns].setEditable(false);
					}
				}
				else if (columns == 1){
					try {
						// Reading other boxes
						FileReader frMain1 = new FileReader(newDirectory + "/" + fileName + "Amount.txt");
						int newCh;
						String input = "";
						
						while ((newCh = frMain1.read()) != -1) {
							input += (char) newCh;
						}
							
						// Setting textbox
						textbox[rows][columns] = new JTextArea();
						textbox[rows][columns].setText(input);
						textbox[rows][columns].setEditable(true);
						
						frMain1.close();
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
						FileReader frMain2 = new FileReader(newDirectory + "/" + fileName + "Date.txt");
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
					if (j % 2 != 0) {
						textbox[i][j].setBackground(new Color(150, 250, 200));
					}
				}
				else {
					if (j % 2 == 0) {
						textbox[i][j].setBackground(new Color(150, 250, 200));
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
		panelFinance.add(names);
		panelFinance.add(editableArea);
		
		// Adding panel to frame
		fMain.add(panelFinance);
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

	public void save() throws IOException, NullPointerException {
		// Creating new directory
		newDirectory.mkdir();
		
		// Writing info to text files
		for (int rows = 0; rows < studentNames.length; rows++) {
			String fileName = "";
			
			for (int k = 0; k < studentNames[rows].getText().length(); k++) {
				if (studentNames[rows].getText().charAt(k) >= 65) {
					fileName += studentNames[rows].getText().charAt(k);
				}
			}
			
			fileName = fileName.replace(" ", "").replace("\r", "");
			
			for (int columns = 0; columns < 4; columns++) {				
				// Writing checkbox name selected
				if (columns == 0) {
					FileWriter fw = new FileWriter(newDirectory + "/" + fileName + ".txt");
					
					if (frequencies[rows][0].isSelected()) {
						fw.write("upon lesson date");
					}
					else if (frequencies[rows][1].isSelected()) {
						fw.write("weekly");
					}
					else if (frequencies[rows][2].isSelected()) {
						fw.write("monthly");
					}
					else {
					}
					
					fw.close();
				}
				// Writing other inputed info to file
				else if (columns == 1) {
					FileWriter fw1 = new FileWriter(newDirectory + "/" + fileName + "Amount.txt");
					
					fw1.write(textbox[rows][columns].getText());
					
					fw1.close();
				}
				else if (columns == 2) {
					FileWriter fw2 = new FileWriter(newDirectory + "/" + fileName + "Date.txt");
					
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
	
	public void calculator() {
		// New frame
		fCalculator = new JFrame("Calculator");
		fCalculator.setLayout(new GridLayout(2, 1));
		fCalculator.setSize(500, 500);
		fCalculator.setVisible(true);
		
		// New panels
		text = new JPanel();
		text.setLayout(new GridLayout(2, 1));
		
		numberButtons = new JPanel();
		numberButtons.setLayout(new GridLayout(4, 3));
		
		operations = new JPanel();
		operations.setLayout(new GridLayout(4, 2));
		
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		
		// New labels
		displayArea.setBackground(Color.gray);
		text.add(displayArea);
		
		result.setBackground(Color.white);
		text.add(result);
		
		// New buttons
		// Numbers
		one = new JButton("1");
		one.setEnabled(true);
		one.addActionListener(this);
		numberButtons.add(one);
		
		two = new JButton("2");
		two.setEnabled(true);
		two.addActionListener(this);
		numberButtons.add(two);
		
		three = new JButton("3");
		three.setEnabled(true);
		three.addActionListener(this);
		numberButtons.add(three);
		
		four = new JButton("4");
		four.setEnabled(true);
		four.addActionListener(this);
		numberButtons.add(four);
		
		five = new JButton("5");
		five.setEnabled(true);
		five.addActionListener(this);
		numberButtons.add(five);
		
		six = new JButton("6");
		six.setEnabled(true);
		six.addActionListener(this);
		numberButtons.add(six);
		
		seven = new JButton("7");
		seven.setEnabled(true);
		seven.addActionListener(this);
		numberButtons.add(seven);
		
		eight = new JButton("8");
		eight.setEnabled(true);
		eight.addActionListener(this);
		numberButtons.add(eight);
		
		nine = new JButton("9");
		nine.setEnabled(true);
		nine.addActionListener(this);
		numberButtons.add(nine);
		
		zero = new JButton("0");
		zero.setEnabled(true);
		zero.addActionListener(this);
		numberButtons.add(zero);
				
		decimal = new JButton(".");
		decimal.setEnabled(true);
		decimal.addActionListener(this);
		numberButtons.add(decimal);
		
		clear = new JButton("AC");
		clear.setEnabled(true);
		clear.setBackground(Color.red);
		clear.setForeground(Color.white);
		clear.addActionListener(this);
		numberButtons.add(clear);
		
		// Operations
		leftP = new JButton("(");
		leftP.setEnabled(true);
		leftP.addActionListener(this);
		operations.add(leftP);
		
		rightP = new JButton(")");
		rightP.setEnabled(true);
		rightP.addActionListener(this);
		operations.add(rightP);
		
		multiply = new JButton("×");
		multiply.setEnabled(true);
		multiply.addActionListener(this);
		operations.add(multiply);
		
		divide = new JButton("÷");
		divide.setEnabled(true);
		divide.addActionListener(this);
		operations.add(divide);
		
		add = new JButton("+");
		add.setEnabled(true);
		add.addActionListener(this);
		operations.add(add);
		
		subtract = new JButton("-");
		subtract.setEnabled(true);
		subtract.addActionListener(this);
		operations.add(subtract);
		
		ans = new JButton("Ans");
		ans.setEnabled(true);
		ans.addActionListener(this);
		operations.add(ans);
		
		equal = new JButton("=");
		equal.setEnabled(true);
		equal.addActionListener(this);
		operations.add(equal);
		
		// Adding panels to panel
		buttons.add(numberButtons);
		buttons.add(operations);
		
		// Adding panels to frame
		fCalculator.add(text);
		fCalculator.add(buttons);
	}

	public void updateEquation(String num) {
		displayArea.setText(displayArea.getText() + num);
	}

	public String convert(String equation) {
		equation = displayArea.getText().replaceAll("Ans", "" + ansDouble);
		return equation;
	}
		
	public double calculate(final String str) {
		return new Object() {
			// Variable declaration
	    	int index = -1, ch;
	    	
	    	// Saving next char in equation
	    	void nextChar() {
	    		if (++index < str.length()) {
	        		ch = str.charAt(index);
	    		}
	        	else {
	        		ch = -1;
	        	}
	        }

	        // Determining saved char
	        boolean eat(int charToEat) {
	            while (ch == ' ') {
	            	// Continue reading
	            	nextChar();
	            }
	            if (ch == charToEat) {
	            	// Read the next char
	                nextChar();
	                return true;
	            }
	            else {
	            	return false;
	            }
	        }
	        
	        // Addition & subtraction
	        double parseExpression() {
	        	// Obtaining result from multiplication & division first
	            double x = parseTerm();
	            
	            // While loop repeats process until equation is finished
	            while (true) {
	                if (eat('+') == true) { //plus
	                	x += parseTerm();
	                }
	                else if (eat('-') == true) { //minus
	                	x -= parseTerm();
	                }
	                else {
	                	return x;
	                }
	            }
	        }
	        
	        // Multiplication & division
	        double parseTerm() {
	        	// Obtaining result of addition/subtraction for factors first
	            double x = parseFactor();
	            
	            // While loop repeats process until equation is finished
	            while (true) {
	                if (eat('×') == true) {
	                	x *= parseFactor(); //times
	                }
	                else if (eat('÷') == true) {
	                	x /= parseFactor(); //divide
	                }
	                else {
	                	return x;
	                }
	            }
	        }
	        
	        // Addition/subtraction of factors of multiplication/division
	        double parseFactor() {
	            if (eat('+') == true) {
	            	return parseFactor(); // unary plus
	            }
	            if (eat('-') == true) {
	            	return -parseFactor(); // unary minus
	            }

	            // Variable declaration
	            double x;
	            int startPos = index;
	            
	            // Parentheses
	            if (eat('(') == true) {
	            	// Evaluate inside of parentheses
	                x = parseExpression();
	                
	                // Keep reading until closing parenthesis reached
	                eat(')');
	            }
	            // Numbers & decimal
	            else if ((ch >= '0' && ch <= '9') || ch == '.') {
	                while ((ch >= '0' && ch <= '9') || ch == '.') {
	                	// Keep reading until an operand character is reached
	                	nextChar();
	                }
	                x = Double.parseDouble(str.substring(startPos, index));
	            }
	            else {
	            	result.setText("SYNTAX ERROR");
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            return x;
	        }
	        
	        // Evaluation
	        double parse() {
	        	// Read next char
	            nextChar();
	            
	            // Evaluate current char
	            double x = parseExpression();
	            
	            // Exception catching
	            if (index < str.length()) {
	            	result.setText("SYNTAX ERROR");
	            	throw new RuntimeException("Unexpected: " + (char)ch);
	            }
	            else {
	            	return x;
	            }
	        }
	    }.parse();
	}
	
	public void updateResult(double ans) {
		if (ans == Double.POSITIVE_INFINITY) {
			result.setText("MATH ERROR");
		}
		else if (ans == Double.NEGATIVE_INFINITY) {
			result.setText("MATH ERROR");
		}
		else {
			// Formatting result
			DecimalFormat df = new DecimalFormat("#.#########");
			
			result.setText("" + df.format(ans));
			ansDouble = ans;
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
			new HelpFinance();
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
		
		// ALL FINANCIAL RECORDS EVENTS
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
		else if (e.getSource() == calculator) {
			calculator();
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
		
		// Calculator buttons
		if (e.getSource() == one || e.getSource() == two || e.getSource() == three
				|| e.getSource() == four || e.getSource() == five || e.getSource() == six
				|| e.getSource() == seven || e.getSource() == eight || e.getSource() == nine
				|| e.getSource() == zero || e.getSource() == decimal || e.getSource() == leftP
				|| e.getSource() == rightP || e.getSource() == ans) {
			updateEquation(((JButton) e.getSource()).getText());
			
			fCalculator.dispose();
			calculator();
		}
		else if (e.getSource() == multiply || e.getSource() == divide || e.getSource() == add
				|| e.getSource() == subtract) {
			updateEquation(" " + ((JButton) e.getSource()).getText() + " ");
			
			fCalculator.dispose();
			calculator();
		}
		else if (e.getSource() == equal) {
			updateResult(calculate(convert(displayArea.getText())));
			updateEquation(" " + ((JButton) e.getSource()).getText() + " ");
			
			fCalculator.dispose();
			calculator();
		}
		else if (e.getSource() == clear) {
			displayArea.setText("");
			result.setText("");
			fCalculator.dispose();
			calculator();
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