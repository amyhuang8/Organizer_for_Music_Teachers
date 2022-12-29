/* Notes:
 * Status: FINISHED
 * Currently functional
 * Currently saves repeating events for up to 1 month
 * Bugged for days that overlap (ex: December 24/31)
 * Missing multiple events function */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

public class Calendar extends MainProgram {
	private JFrame fCalEvents, fDisplay, fDisplayOrAdd;
	private JPanel panelEvent, panelDisplay, panelDisplayOrAdd;
	private JLabel month, monday, tuesday, wednesday, thursday, friday, saturday, sunday;
	private JButton leftArrow, rightArrow, addEvent, cancel, addNewEvent, display, delete;
	private JTextField eventName, description, eventSTime, eventETime;
	private JCheckBox daily = new JCheckBox("Daily", false);
	private JCheckBox weekly = new JCheckBox("Weekly", false);
	private String eventDate, endString = "";
	private Object object;
	
	private File directory = new File(username + "/Calendar");;
	
	private DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
	private DateTimeFormatter monthNum = DateTimeFormatter.ofPattern("MM");
	private DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
	private LocalDate today = LocalDate.now();
	private LocalDate selectedDate;
	private DayOfWeek weekDay;
	private String monthName = "";
	private String dayName;
	private int[][] days;
		
	public void calendar() {
		// New panel
		panelCal = new JPanel();
		panelCal.setLayout(new GridLayout(8, 7));  
		panelCal.setBackground(Color.gray);
		
		calendarDates(today);
		
		// Month label
		month = new JLabel(monthName + ", " + year.format(today));
		month.setOpaque(true);
		month.setBackground(new Color(150, 250, 200));
		month.setForeground(Color.black);
		
		// Days of the week labels
		sunday = new JLabel("Sunday");
		sunday.setForeground(Color.white);
		
		monday = new JLabel("Monday");
		monday.setOpaque(true);
		monday.setBackground(Color.black);
		monday.setForeground(Color.white);
		
		tuesday = new JLabel("Tuesday");
		tuesday.setForeground(Color.white);
		
		wednesday = new JLabel("Wednesday");
		wednesday.setOpaque(true);
		wednesday.setBackground(Color.black);
		wednesday.setForeground(Color.white);
		
		thursday = new JLabel("Thursday");
		thursday.setForeground(Color.white);
		
		friday = new JLabel("Friday");
		friday.setOpaque(true);
		friday.setBackground(Color.black);
		friday.setForeground(Color.white);
		
		saturday = new JLabel("Saturday");
		saturday.setForeground(Color.white);
		
		// New blanks labels for formatting
		JLabel blank1 = new JLabel("");
		blank1.setOpaque(true);
		blank1.setBackground(new Color(150, 250, 200));
		
		JLabel blank2 = new JLabel("");
		blank2.setOpaque(true);
		blank2.setBackground(new Color(150, 250, 200));
		
		JLabel blank3 = new JLabel("");
		blank3.setOpaque(true);
		blank3.setBackground(new Color(150, 250, 200));
		
		JLabel blank4 = new JLabel("");
		blank4.setOpaque(true);
		blank4.setBackground(new Color(150, 250, 200));
		
		// New buttons
		leftArrow = new JButton("<");
		leftArrow.setBackground(new Color(150, 250, 200));
		leftArrow.addActionListener(this);
		
		rightArrow = new JButton(">");
		rightArrow.setBackground(new Color(150, 250, 200));
		rightArrow.addActionListener(this);
		
		// Disable calendar button
		buttonCal.setEnabled(false);
		buttonCal.setBackground(Color.pink);
		
		// Adding components to panel
		panelCal.add(buttonCal);
		panelCal.add(buttonLists);
		panelCal.add(buttonRecords);
		panelCal.add(buttonFinance);
		panelCal.add(buttonNotepad);
		panelCal.add(buttonHelp);
		panelCal.add(signOut);
		
		panelCal.add(blank1);
		panelCal.add(blank2);
		panelCal.add(leftArrow);
		panelCal.add(month);
		panelCal.add(rightArrow);
		panelCal.add(blank3);
		panelCal.add(blank4);
		
		panelCal.add(sunday);
		panelCal.add(monday);
		panelCal.add(tuesday);
		panelCal.add(wednesday);
		panelCal.add(thursday);
		panelCal.add(friday);
		panelCal.add(saturday);
		
		// New textfields
		JTextField[][] calendarDays = new JTextField[5][7];
		
		// Initializing the textfields
		for (int rows = 0; rows < days.length; rows++) {
			for (int columns = 0; columns < 7; columns++) {				
				if (rows == 5) {
					if (dayName.equals("Friday") && days[rows][columns] == 31) {
						calendarDays[rows-1][columns].setText(calendarDays[rows-1][columns].getText() + "/" + Integer.toString(days[rows][columns]));
					}
					else if (dayName.equals("Saturday") && days[rows][columns] == 30) {
						calendarDays[rows-1][columns].setText(calendarDays[rows-1][columns].getText() + "/" + Integer.toString(days[rows][columns]));
					}
					else if (dayName.equals("Saturday") && days[rows][columns] == 31) {
						calendarDays[rows-1][columns].setText(calendarDays[rows-1][columns].getText() + "/" + Integer.toString(days[rows][columns]));
					}
				}
				else {
					calendarDays[rows][columns] = new JTextField();
					calendarDays[rows][columns].setText(Integer.toString(days[rows][columns]));
					
					// Grey out the days that aren't part of the month
					if (calendarDays[rows][columns].getText().equals("0")) {
						calendarDays[rows][columns].setText(null);
					}
				}
			}
		} 
		
		// Adding the textfields to the panel
		for (int rows = 0; rows <= 4; rows++) {
			for (int columns = 0; columns <= 6; columns++) {
				if (!(calendarDays[rows][columns].getText().equals(""))) {
					calendarDays[rows][columns].addMouseListener(this);
				}
				calendarDays[rows][columns].setEditable(false);
				panelCal.add(calendarDays[rows][columns]);
			}
		}
		
		// Adding panel to frame
		fMain.add(panelCal);
		fMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fMain.setVisible(true);
		fMain.remove(panelStart);
	}
	
	public void calendarDates(LocalDate today) {
		int todaysDate = (int) Integer.parseInt(day.format(today));
		int totalDays = 0;
		
		// Determining name of current month & total # of days
		switch (monthNum.format(today)) {
			case "01" :
				monthName = "January";
				totalDays = 31;
				break;
			case "02" :
				monthName = "February";
				if (today.isLeapYear() == true) {
					totalDays = 29;
				}
				else {
					totalDays = 28;
				}
				break;
			case "03" :
				monthName = "March";
				totalDays = 31;
				break;
			case "04" :
				monthName = "April";
				totalDays = 30;
				break;
			case "05" :
				monthName = "May";
				totalDays = 31;
				break;
			case "06" :
				monthName = "June";
				totalDays = 30;
				break;
			case "07" :
				monthName = "July";
				totalDays = 31;
				break;
			case "08" :
				monthName = "August";
				totalDays = 31;
				break;
			case "09" :
				monthName = "September";
				totalDays = 30;
				break;
			case "10" :
				monthName = "October";
				totalDays = 31;
				break;
			case "11" :
				monthName = "November";
				totalDays = 30;
				break;
			case "12" :
				monthName = "December";
				totalDays = 31;
				break;
		}
		
		// First day of the month
		LocalDate firstDay = today.minusDays(todaysDate-1);
		
		// Determining the week day of the first day
		weekDay = DayOfWeek.from(firstDay);
		dayName = weekDay.getDisplayName(TextStyle.FULL, getLocale());
		
		int indexNum = 0;
		int day = 0;
		
		switch (dayName) {
			case "Sunday" :
				indexNum = 7;
				days = new int[5][7];
				day = 1;
				break;
			case "Monday" :
				indexNum = 1;
				days = new int[5][7];
				break;
			case "Tuesday" :
				indexNum = 2;
				days = new int[5][7];
				break;
			case "Wednesday" :
				indexNum = 3;
				days = new int[5][7];
				break;
			case "Thursday" :
				indexNum = 4;
				days = new int[5][7];
				break;
			case "Friday" :
				indexNum = 5;
				if (totalDays == 31) {
					days = new int[6][7];
				}
				else {
					days = new int[5][7];
				}
				break;
			case "Saturday" :
				indexNum = 6;
				if (totalDays == 31 || totalDays == 30) {
					days = new int[6][7];
				}
				else {
					days = new int[5][7];
				}
				break;
		}
		
		// Inputting the days into the calendar
		int count = 0;
		
		for (int rows = 0; rows < days.length; rows++) {
			for (int columns = 0; columns < 7; columns++) {
				if (indexNum != 7) { // not starting on Sunday
					if (!(rows == 0 && columns < indexNum-1)) {
						days[rows][columns] = day;
						if (day < totalDays) {
							day++;
						}
						if (day == totalDays) {
							count++;
							if (count == 2) {
								break;
							}
						}
					}
				}
				else { // starting on Sunday
					days[rows][columns] = day;
					if (day < totalDays) {
						day++;
					}
					if (day == totalDays) {
						count++;
						if (count == 2) {
							break;
						}
					}
				}
			}
		}
	}
	
	public void displayOrAdd() {
		eventDate = monthName + " " + ((JTextField) object).getText() + ", " + year.format(today);
		
		// New frame
		fDisplayOrAdd = new JFrame(eventDate);
		
		// New panel
		panelDisplayOrAdd = new JPanel();
		panelDisplayOrAdd.setLayout(new GridLayout(1, 2));
		panelDisplayOrAdd.setBackground(Color.gray);
		
		// New buttons
		display = new JButton("Display Events");
		display.addActionListener(this);
		
		addNewEvent = new JButton("New Event");
		addNewEvent.addActionListener(this);
		
		// Adding buttons to panel
		panelDisplayOrAdd.add(display);
		panelDisplayOrAdd.add(addNewEvent);
		
		// Adding panel to frame
		fDisplayOrAdd.add(panelDisplayOrAdd);
		fDisplayOrAdd.setSize(400, 200);
		fDisplayOrAdd.setVisible(true);
	}
	
	public void addCalEvent(Object object) {
		int num = 0;
		
		// Determining selected month name
		switch (monthName) {
			case "January" :
				num = 1;
				break;
			case "February" :
				num = 2;
				break;
			case "March" :
				num = 3;
				break;
			case "April" :
				num = 4;
				break;
			case "May" :
				num = 5;
				break;
			case "June" :
				num = 6;
				break;
			case "July" :
				num = 7;
				break;
			case "August" :
				num = 8;
				break;
			case "September" :
				num = 9;
				break;
			case "October" :
				num = 10;
				break;
			case "November" :
				num = 11;
				break;
			case "December" :
				num = 12;
				break;
		}
		
		// Determining selected date
		selectedDate = LocalDate.of(Integer.parseInt(year.format(today)), num, Integer.parseInt(((JTextField) object).getText()));
		
		LocalDate endDate = selectedDate.plusWeeks(4);
		DateTimeFormatter end = DateTimeFormatter.ofPattern("MM");
		
		// Determining name of current month & total # of days
		switch (end.format(endDate)) {
			case "01" :
				endString = "January";
				break;
			case "02" :
				endString = "February";
				break;
			case "03" :
				endString = "March";
				break;
			case "04" :
				endString = "April";
				break;
			case "05" :
				endString = "May";
				break;
			case "06" :
				endString = "June";
				break;
			case "07" :
				endString = "July";
				break;
			case "08" :
				endString = "August";
				break;
			case "09" :
				endString = "September";
				break;
			case "10" :
				endString = "October";
				break;
			case "11" :
				endString = "November";
				break;
			case "12" :
				endString = "December";
				break;
		}
		
		// Determining day
		int date;
		
		switch(day.format(endDate)) {
			case "01":
				date = 1;
				break;
			case "02":
				date = 2;
				break;
			case "03":
				date = 3;
				break;
			case "04":
				date = 4;
				break;
			case "05":
				date = 5;
				break;
			case "06":
				date = 6;
				break;
			case "07":
				date = 7;
				break;
			case "08":
				date = 8;
				break;
			case "09":
				date = 9;
				break;
			default:
				date = Integer.parseInt(day.format(endDate));
				break;
		}
		
		endString += " " + date + ", " + year.format(endDate);
		
		// New frame
		eventDate = monthName + " " + ((JTextField) object).getText() + ", " + year.format(today);
		fCalEvents = new JFrame(eventDate);
		
		// New panel
		panelEvent = new JPanel();
		panelEvent.setLayout(new GridLayout(6, 2));
		panelEvent.setBackground(Color.gray);
		
		// New labels
		JLabel name = new JLabel("Event Name:");
		JLabel des = new JLabel("Description:");
		JLabel sTime = new JLabel("Start Time:");
		JLabel eTime = new JLabel("End Time:");
		
		// New textfields
		eventName = new JTextField();
		description = new JTextField();
		eventSTime = new JTextField("Ex: 9:00 AM");
		eventETime = new JTextField("Ex: 5:00 PM");
		
		// New button
		addEvent = new JButton("Add Event");
		addEvent.addActionListener(this);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		
		// Adding components to panel
		panelEvent.add(name);
		panelEvent.add(eventName);
		panelEvent.add(des);
		panelEvent.add(description);
		panelEvent.add(sTime);
		panelEvent.add(eventSTime);
		panelEvent.add(eTime);
		panelEvent.add(eventETime);
		panelEvent.add(daily);
		panelEvent.add(weekly);
		panelEvent.add(addEvent);
		panelEvent.add(cancel);
		
		// Adding panel to frame
		fCalEvents.add(panelEvent);
		fCalEvents.setSize(400, 400);
		fCalEvents.setVisible(true);
	}
	
	public void saveCalEvent(JTextField eventName, JTextField description,
			JTextField eventSTime, JTextField eventETime, JCheckBox daily,
			JCheckBox weekly) throws IOException {		
		// Creating directory
		directory.mkdir();
		
		// Creating new file
		FileWriter fw = new FileWriter(directory + "/" + eventDate + ".txt"); 
		
		// ensuring empty events aren't saved
		if (!(eventName.getText().equals("") && description.getText().equals("") && eventSTime.getText().equals("") && eventETime.getText().equals(""))) {
			// Writing to the file
			// event name
			for (int i = 0; i < eventName.getText().length(); i++) {
				fw.write(eventName.getText().charAt(i));
			}
					
			fw.write("\n");
			
			// description
			for (int i = 0; i < description.getText().length(); i++) {
				fw.write(description.getText().charAt(i));
			}
			
			fw.write("\n");
			
			// start time
			for (int i = 0; i < eventSTime.getText().length(); i++) {
				fw.write(eventSTime.getText().charAt(i));
			}
			
			fw.write("\n");
			
			// end time
			for (int i = 0; i < eventETime.getText().length(); i++) {
				fw.write(eventETime.getText().charAt(i));
			}
			
			fw.write("\n");
			
			// repeats daily
			if (daily.isSelected()) {
				fw.write("true");
				
				// recursion
				if (!(eventDate.equals(endString))) {
					selectedDate = selectedDate.plusDays(1);
					
					// Determining name of current month
					switch (monthNum.format(selectedDate)) {
						case "01" :
							monthName = "January";
							break;
						case "02" :
							monthName = "February";
							break;
						case "03" :
							monthName = "March";
							break;
						case "04" :
							monthName = "April";
							break;
						case "05" :
							monthName = "May";
							break;
						case "06" :
							monthName = "June";
							break;
						case "07" :
							monthName = "July";
							break;
						case "08" :
							monthName = "August";
							break;
						case "09" :
							monthName = "September";
							break;
						case "10" :
							monthName = "October";
							break;
						case "11" :
							monthName = "November";
							break;
						case "12" :
							monthName = "December";
							break;
					}
					
					// Determining day
					int date;
					
					switch(day.format(selectedDate)) {
						case "01":
							date = 1;
							break;
						case "02":
							date = 2;
							break;
						case "03":
							date = 3;
							break;
						case "04":
							date = 4;
							break;
						case "05":
							date = 5;
							break;
						case "06":
							date = 6;
							break;
						case "07":
							date = 7;
							break;
						case "08":
							date = 8;
							break;
						case "09":
							date = 9;
							break;
						default:
							date = Integer.parseInt(day.format(selectedDate));
							break;
					}
					
					eventDate = monthName + " " + date + ", " + year.format(selectedDate);
					
					saveCalEvent(eventName, description, eventSTime, eventETime, daily, weekly);
				}
			}
			else {
				fw.write("false");
			}
			
			fw.write("\n");
			
			// repeats weekly
			if (weekly.isSelected()) {
				fw.write("true");
				
				// recursion
				if (!(eventDate.equals(endString))) {
					selectedDate = selectedDate.plusWeeks(1);
					
					// Determining name of current month
					switch (monthNum.format(selectedDate)) {
						case "01" :
							monthName = "January";
							break;
						case "02" :
							monthName = "February";
							break;
						case "03" :
							monthName = "March";
							break;
						case "04" :
							monthName = "April";
							break;
						case "05" :
							monthName = "May";
							break;
						case "06" :
							monthName = "June";
							break;
						case "07" :
							monthName = "July";
							break;
						case "08" :
							monthName = "August";
							break;
						case "09" :
							monthName = "September";
							break;
						case "10" :
							monthName = "October";
							break;
						case "11" :
							monthName = "November";
							break;
						case "12" :
							monthName = "December";
							break;
					}
					
					// Determining day
					int date;
					
					switch(day.format(selectedDate)) {
						case "01":
							date = 1;
							break;
						case "02":
							date = 2;
							break;
						case "03":
							date = 3;
							break;
						case "04":
							date = 4;
							break;
						case "05":
							date = 5;
							break;
						case "06":
							date = 6;
							break;
						case "07":
							date = 7;
							break;
						case "08":
							date = 8;
							break;
						case "09":
							date = 9;
							break;
						default:
							date = Integer.parseInt(day.format(selectedDate));
							break;
					}
					
					eventDate = monthName + " " + date + ", " + year.format(selectedDate);
					
					saveCalEvent(eventName, description, eventSTime, eventETime, daily, weekly);
				}
			}
			else {
				fw.write("false");
			}
			
			// Resetting month name
			switch (monthNum.format(today)) {
				case "01" :
					monthName = "January";
					break;
				case "02" :
					monthName = "February";
					break;
				case "03" :
					monthName = "March";
					break;
				case "04" :
					monthName = "April";
					break;
				case "05" :
					monthName = "May";
					break;
				case "06" :
					monthName = "June";
					break;
				case "07" :
					monthName = "July";
					break;
				case "08" :
					monthName = "August";
					break;
				case "09" :
					monthName = "September";
					break;
				case "10" :
					monthName = "October";
					break;
				case "11" :
					monthName = "November";
					break;
				case "12" :
					monthName = "December";
					break;
			}
		}
				       
		// Closing the file writer
		fw.close();
	}
	
	public void displayEvents() throws IOException {
		eventDate = monthName + " " + ((JTextField) object).getText() + ", " + year.format(today);
		
		// Variable declaration
		int ch;
		String eventName = "", description = "", sTime = "", eTime = "";
		JLabel display = new JLabel();
		JLabel name = new JLabel();
		JLabel des = new JLabel();
		JLabel start = new JLabel();
		JLabel end = new JLabel();
		JLabel repeats = new JLabel();;
		delete = new JButton("Delete event");
		
		fDisplay = new JFrame(eventDate);
		panelDisplay = new JPanel();
		panelDisplay.setBackground(Color.gray);
		
		// Creating new file
		FileReader fr;
		File f = new File(directory + "/" + eventDate + ".txt");
		
		try {
			fr = new FileReader(f);
		
			if (f.length() == 0) {
				display.setText("No events found for this day.");
				
				display.setForeground(Color.white);
				panelDisplay.add(display);
				
				panelDisplay.setLayout(new GridLayout(2, 1));
				
				delete.addActionListener(this);
				panelDisplay.add(delete);
				
				// Adding panel to frame
				fDisplay.add(panelDisplay);
				fDisplay.setSize(400, 400);
				fDisplay.setVisible(true);
			}
			else {
				// Reading event name
				while ((ch = fr.read()) != -1 && ((char) ch != '\n')) {
					eventName += (char) ch;
				}
				
				// description
				while ((ch = fr.read()) != -1 && ((char) ch != '\n')) {
					description += (char) ch;
				}
				
				// start time
				while ((ch = fr.read()) != -1 && ((char) ch != '\n')) {
					sTime += (char) ch;
				}
				
				// end time
				while ((ch = fr.read()) != -1 && ((char) ch != '\n')) {
					eTime += (char) ch;
				}
			
				// repeats daily
				if (this.daily.isSelected()) {
					String daily = "Repeats daily";
					
					// New labels
					name.setText("Event Name: " + eventName);
					des.setText("Description: " + description);
					start.setText("Start Time: " + sTime);
					end.setText("End Time: " + eTime);
					repeats.setText(daily);
				}
				else if (this.weekly.isSelected()) {
					String weekly = "Repeats weekly";
					
					// New labels
					name.setText("Event Name: " + eventName);
					des.setText("Description: " + description);
					start.setText("Start Time: " + sTime);
					end.setText("End Time: " + eTime);
					repeats.setText(weekly);
				}
				else {
					// New labels
					name.setText("Event Name: " + eventName);
					des.setText("Description: " + description);
					start.setText("Start Time: " + sTime);
					end.setText("End Time: " + eTime);
				}
				
				name.setForeground(Color.white);
				des.setForeground(Color.white);
				start.setForeground(Color.white);
				end.setForeground(Color.white);
				repeats.setForeground(Color.white);
				
				// Adding components to panel
				panelDisplay.add(name);
				panelDisplay.add(des);
				panelDisplay.add(start);
				panelDisplay.add(end);
				if (!repeats.getText().equals(null)) {
					panelDisplay.add(repeats);
					panelDisplay.setLayout(new GridLayout(6, 1));
				}
				else {
					panelDisplay.setLayout(new GridLayout(5, 1));
				}
				
				delete.addActionListener(this);
				panelDisplay.add(delete);
				
				// Adding panel to frame
				fDisplay.add(panelDisplay);
				fDisplay.setSize(400, 400);
				fDisplay.setVisible(true);
			}
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			display.setText("No events found for this day.");
			
			display.setForeground(Color.white);
			panelDisplay.add(display);
			
			panelDisplay.setLayout(new GridLayout(2, 1));
			
			delete.addActionListener(this);
			panelDisplay.add(delete);
			
			// Adding panel to frame
			fDisplay.add(panelDisplay);
			fDisplay.setSize(400, 400);
			fDisplay.setVisible(true);
		}	
	}
	
	public void deleteEvent() throws IOException {
		// Declaring file name
		eventDate = monthName + " " + ((JTextField) object).getText() + ", " + year.format(today);
		
		// Declaring file writer
		FileWriter fw;
		
		try {
			fw = new FileWriter(directory + "/" + eventDate + ".txt");
			fw.write("");
			
			// Closing file writer
			fw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		// ALL CALENDAR TAB EVENTS
		object = e.getSource();
		displayOrAdd();
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
			new HelpCal();
		}
		else if (e.getSource() == signOut) {
			fMain.dispose();
		}
		
		// ALL CALENDAR TAB EVENTS
		if (e.getSource() == leftArrow) {
			today = today.minusMonths(1);
			fMain.remove(panelCal);
			calendar();
		}
		if (e.getSource() == rightArrow) {
			today = today.plusMonths(1);
			fMain.remove(panelCal);
			calendar();
		}
		
		// displayOrAdd buttons
		if (e.getSource() == addNewEvent) {
			fDisplayOrAdd.dispose();
			addCalEvent(object);
		}
		else if (e.getSource() == display) {
			try {
				fDisplayOrAdd.dispose();
				displayEvents();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == delete) {
			try {
				fDisplay.dispose();
				deleteEvent();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		// addCalEvent buttons
		if (e.getSource() == addEvent) {
			try {
				saveCalEvent(eventName, description, eventSTime, eventETime, daily, weekly);
				fCalEvents.dispose();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else if (e.getSource() == cancel) {
			fCalEvents.dispose();
		}
	}
}