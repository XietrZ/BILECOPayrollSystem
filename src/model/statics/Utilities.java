package model.statics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import view.MainFrame;
import view.dialog.AddEmployeeDialog;
import view.dialog.DeleteUpdateEmployeeDialog;
import view.dialog.UpdateEmployeeDialog;
import view.views.DeductionViewPanel;
import view.views.EarningViewPanel;
import view.views.PayrollViewPanel;
import database.Database;
import model.Constant;
import model.OrderByInfo;
import model.view.AddCommentsOnAllPayslipDialog;
import model.view.AddEarningOrDeductionDataLayout;
import model.view.DisplayOptionsDialog;
import model.view.EarningsAndDeductionLayout;
import model.view.PayrollSlipLayout;
import model.view.ReusableTable;
import model.view.ShowAllEmployeeAddPayrollDataDialogLayout;



public class Utilities {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	public boolean isEdit=false,isLogin=false;
	public int authorizationLevel=-1;
	public int payrollSystemMode=-1;

//	public String preparedByName="KATHERINE S. MADRONIO",preparedByTitle="Personnel Officer",
//					checkedByName="MA. LEIZYL Q. GARCIA",checkedByTitle="FSD Manager",
//					auditedByName="MAUREEN D. NIERRA,CPA",auditedByTitle="Intenal Auditor",
//					approvedByName="GERARDO N. OLEDAN,REE", approvedByTitle="General Manager";
	

	
	private static Utilities instance;
	
	public Utilities(){
		isLogin=false;
	}
	
	private void l__________________________________________l(){}
	
	/**
	 * Add a slant apostrophe to a given string.
	 * @param str
	 * @return
	 */
	public String addSlantApostropheToString(String str){
		return "`"+str+"`";
	}
	
	
	
	public int calculateWidthBasedFromGivenMinHeightForScalingImage(int toBeScaledWidth,
			int toBeScaledHeight, int height){
		
		int width=(height*toBeScaledWidth)/toBeScaledHeight;
		return width+1;
		
	}
	
	/**
	 * Calculate first pay/kinsenas value based from given monthly basic pay value.
	 * @param monthlyBasicPayValue
	 * @return
	 */
	public double calculateFirstHalfKinsenasPayValue(double monthlyBasicPayValue){
		Double halfMonthlyPayValue=monthlyBasicPayValue/2;
		//--> Checks if the monthly pay value*100 is not divisible by 2.
		double conditionNum=(monthlyBasicPayValue*100)%2;
		// First Pay Value
		
		return (conditionNum!=0)?
				convertRoundToOnlyTwoDecimalPlaces(halfMonthlyPayValue)
				:
				halfMonthlyPayValue;
	}
	
	/**
	 * Calculate the second kinsenas half pay value
	 * @param monthlyBasicPayValue
	 * @param firstPayValue
	 * @return
	 */
	public double calculateSecondHalfKinsenasPayValue(double monthlyBasicPayValue,double firstPayValue){
		Double halfMonthlyPayValue=monthlyBasicPayValue/2;
		//--> Checks if the monthly pay value*100 is not divisible by 2.
		double conditionNum=(monthlyBasicPayValue*100)%2;
		
		return (conditionNum!=0)?
				processLowerHalfValueTwoDecimal(firstPayValue)
				:
				halfMonthlyPayValue;
	}
	/**
	 * Process column names from database so that the camel case
	 * 		will be gone.
	 * @param columnName
	 * @return
	 */
	public String convertCamelCaseColumnNamesToReadable(String columnName){
		if(columnName.equals("MandaProvFundEe") || columnName.equals("MandaProvFundEr")){
			return columnName;
		}
		
		columnName=columnName.replaceAll("([A-Z][a-z]+)", " $1") // Words beginning with UC
	             .replaceAll("([A-Z][A-Z]+)", " $1") // "Words" of only UC
	            // .replaceAll("([^A-Za-z ]+)", " $1") // "Words" of non-letters
	             .trim();
		return columnName;
	}
	/**
	 * This method convert the selected rows in table into the actual rows in database.
	 * 		If this method is not used and the user sort the table inputs, the return index
	 * 		will not be the exact index in the database.
	 * @param table
	 * @param selectedIndexList
	 * @return
	 */
	public int[] convertRowIndexToModel(JTable table, int[] selectedIndexList){
		for(int i=0;i<selectedIndexList.length;i++){
			selectedIndexList[i]=table.convertRowIndexToModel(selectedIndexList[i]);
		}
		return selectedIndexList;
	}
	
	/**
	 * Convert the double number to only two decimal places.
	 * 
	 * 		if TWO Places: Math.round(num * 100.0) / 100.0,
	 * 		THREE Places:Math.round(num * 1000.0) / 1000.0,
	 * 		FOUR Places:Math.round(num * 10000.0) / 10000.0  and so on..
	 * 		
	 * @param num
	 * @return
	 */
	public double convertRoundToOnlyTwoDecimalPlaces(double num){
		double roundOff =Math.round(num * 100.0) / 100.0;   
		return roundOff;
	}
	
	/**
	 * Compare class objects depending on what preferred class/mode.
	 * @param mode
	 * @return
	 */
	public String compareClassObject(int mode){
		if(mode==Constant.CLASS_OBJECT_DOUBLE){
			return "class java.lang.Double";
		}
		else if(mode==Constant.CLASS_OBJECT_INTEGER){
			return "class java.lang.Integer";
		}
		else if(mode==Constant.CLASS_OBJECT_STRING){
			return "class java.lang.String";
		}
		else if(mode==Constant.CLASS_OBJECT_DATE){
			return "class java.sql.Date";
		}
		return null;
	}
	/**
	 * Count number of decimal places based from a given number.
	 * Ex: 0.0275 -> Answer: 4.
	 * @param value
	 * @return
	 */
	public int countNumberOfDecimalPlaces(Object value){
		if((Double.parseDouble(value.toString())*10)%10==0 || 
				(Double.parseDouble(value.toString())*100)%10==0 ||
				(Double.parseDouble(value.toString())*1000)%10==0 ){
			return 2;
		}
		
		
		return String.valueOf(value).split("\\.")[1].length();
	}
	/**
	 * Convert the date format yyy-mm-dd to a readable date.
	 * 	Example: 2018-04-30 -> April 30,2018.
	 * @return
	 */
	public String convertDateYyyyMmDdToReadableDate(String payrollDate ){
//		// Java 8
//		
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
//		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.ENGLISH);
//		LocalDate ld = LocalDate.parse(payrollDate, dtf);
//		String str = dtf2.format(ld);
		
//		-------------------------------------------------
		// Java 7
		 String[] temp = new String[3];
		 payrollDate=payrollDate.replaceAll("-", " ");
		 StringTokenizer st = new StringTokenizer(payrollDate);
	     
		 for (int i=0;st.hasMoreTokens();i++) {
	         temp[i]=st.nextToken();
	     }
		 String str=convertEquivalentMonthNumberToText(Integer.parseInt(temp[1]))
				 		+" "+temp[2]+", "+temp[0];
	 
		return str;
	}
	
	/**
	 * Convert the readable date format to yyyy-mm-dd.
	 * 	Example: April 30,2018 -> 2018-04-30;
	 * @return
	 */
	public String convertDateReadableToYyyyMmDdDate(String readablePayrollDate){
		//Java 8
		String str = null;
		try{
			DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.ENGLISH);
			DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			LocalDate ld = LocalDate.parse(readablePayrollDate, dtf);
			str = dtf2.format(ld);
		}catch(Exception e){
			return readablePayrollDate;
		}
		
//		-------------------------------------------------
		// Java 7
//		String[] temp = new String[3];
//		String str = null;
//		try{
//			readablePayrollDate=readablePayrollDate.replaceAll(",", " ");
//			System.out.println("FUCKSSDS: "+readablePayrollDate+CLASS_NAME);
//			StringTokenizer st = new StringTokenizer(readablePayrollDate);
//		 
//			for (int i=0;st.hasMoreTokens();i++) {
//				temp[i]=st.nextToken();
//		     
//				System.out.println("\tshit: "+temp[i]+CLASS_NAME);
//			
//			}
//			str=temp[2]+"-"
//					+convertEquivalentMonthTextToNumber(temp[0])+"-"
//					 +temp[1];
//	 	}catch(Exception e){
//			e.printStackTrace(); 
//			return readablePayrollDate;
//	 	}

		return str;
	}
	
	/**
	 * Convert the abbreviated department name to its real full name.
	 * @param abbreviatedDeptName
	 * @return
	 */
	public String convertDeptNameAbbrevToRealName(String abbreviatedDeptName){
		switch(abbreviatedDeptName){
			case "TSD":
				return "TECHNICAL SERVICES DEPARTMENT";
			case "OGM":
				return "OFFICE OF THE GENERAL MANAGER";
			case "FSD":
				return "FINANCIAL SERVICES DEPARTMENT";
			case "ISD":
				return "INSTITUTIONAL SERVICES DEPARTMENT";
		}
		return null;
	}
	
	/***
	 * Return the three letter equivalent of month number
	 * 	Ex: 02 - Feb
	 * @param monthNumber
	 * @return
	 */
	public String convertEquivalentMonthNumberToText(int monthNumber){
		switch(monthNumber){
			case 1:{
				return "Jan";
			}
			case 2:{
				return "Feb";
			}
			case 3:{
				return "Mar";
			}
			case 4:{
				return "Apr";
			}
			case 5:{
				return "May";
			}
			case 6:{
				return "Jun";
			}
			case 7:{
				return "Jul";
			}
			case 8:{
				return "Aug";
			}
			case 9:{
				return "Sep";
			}
			case 10:{
				return "Oct";
			}
			case 11:{
				return "Nov";
			}
			case 12:{
				return "Dec";
			}
			default:{
				break;
			}
		}
		return "?error->"+monthNumber;
		
	}
	
	/***
	 * Return the three letter equivalent of month number
	 * 	Ex: 02 - Feb
	 * @param monthNumber
	 * @return
	 */
	public String convertEquivalentMonthTextToNumber(String monthText){
		switch(monthText){
			case "Jan":{
				return "01";
			}
			case "Feb":{
				return "02";
			}
			case "Mar":{
				return "03";
			}
			case "Apr":{
				return "04";
			}
			case "May":{
				return "05";
			}
			case "Jun":{
				return "06";
			}
			case "Jul":{
				return "07";
			}
			case "Aug":{
				return "08";
			}
			case "Sep":{
				return "09";
			}
			case "Oct":{
				return "10";
			}
			case "Nov":{
				return "11";
			}
			case "Dec":{
				return "12";
			}
			default:{
				break;
			}
		}
		return "error->"+monthText;
		
	}
	/**
	 * Convert the three letter format of month into a complete name.
	 * Example: Jan->January , Feb-> February
	 * @param threeLetterMonth
	 * @return
	 */
	public String convertThreeLetterMonthIntoCompleteName(String threeLetterMonth){
		switch(threeLetterMonth){
			case "Jan":{
				return "January";
			}
			case "Feb":{
				return "February";
			}
			case "Mar":{
				return "March";
			}
			case "Apr":{
				return "April";
			}
			case "May":{
				return "May";
			}
			case "Jun":{
				return "June";
			}
			case "Jul":{
				return "July";
			}
			case "Aug":{
				return "August";
			}
			case "Sep":{
				return "September";
			}
			case "Oct":{
				return "October";
			}
			case "Nov":{
				return "November";
			}
			case "Dec":{
				return "December";
			}
			default:{
				break;
			}
		}
		return "???error";
	}
	
	public String convertThreeLetterMonthNameToNumber(String str){
		String convertedString=str;
		if(isStringAThreeLetterMonth(str)){
			// Java 8
			try{
				 convertedString=""+ Month.valueOf(convertThreeLetterMonthIntoCompleteName(str).toUpperCase()).getValue();
				    
				System.out.println("\t FUCKSASASSWS: "+convertedString+CLASS_NAME);
			}catch(Exception e){
				System.out.println("\t FUCKSASASSWS: ERROR!: "+str
						+"\t : "+e.getMessage()+CLASS_NAME);
				return str;
			}
			
//			-----------------------------------
			// Java 7
//			convertedString=convertEquivalentMonthTextToNumber(str);
		}
		return convertedString;
	}
	/**
	 * Convert string with yyy-MM-dd format to sql date.
	 * @param str
	 * @return
	 */
	public java.sql.Date convertStringToSqlDate(String str){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed;
		try {
			parsed = format.parse(str);
			java.sql.Date sql = new java.sql.Date(parsed.getTime());
			return sql;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
	}
	
	
	/**
	 * Get the contractual employee salary per month.
	 * @param db
	 * @param employeeID
	 * @return
	 */
	public double getContractualEmpployeeSalaryPerMonth(Database db,String employeeID){
		int numOfYearsService=db.getNumberOfYearsServiceBasedFromGivenContractualEmployeeID(employeeID);
		double contractualSalaryPerDay=db.getContractualSalaryRateBasedYearsOfService(numOfYearsService);
		double salary=contractualSalaryPerDay*22;
		
		System.out.println("\t\t EmployeeID: "+employeeID
				+"\t Salary Per Day Contractual: "+contractualSalaryPerDay
				+"\t Salary Per Month Contractual: "+salary+CLASS_NAME);
		return salary;
	}
	
	/**
	 * Get the contractual employee ID, based from given last name
	 * 		first name, middle name, date entry. 
	 * @param lastName
	 * @param firstName
	 * @param middleName
	 * @param dateEntry
	 * @return
	 */
	public String getContractualEmployeeID(String lastName, String firstName, 
			String middleName,String dateEntry){
		
		lastName=lastName.replaceAll("\\s","");
		firstName=firstName.replaceAll("\\s","");
		middleName=middleName.replaceAll("\\s","");
		dateEntry=dateEntry.replaceAll("\\s","");
		
		//------------------------------------------------
		String year=getYearFromDateFormatYyyyMmDd(dateEntry),
				monthHired=getMonthMMFormatFromDateFormatYyyyMmDd(dateEntry),
				firstNameFirstLetter=""+firstName.charAt(0),
				middleNameFirstLetter=""+middleName.charAt(0),
				lastNameFirstLetter=""+lastName.charAt(0);
		
		
		//------------------------------------------------
		//--> Get the year +month+ initial letters of names
		String employeeID=""+year+"-";
		employeeID+=""+monthHired+"-";
		employeeID+=firstNameFirstLetter;
		employeeID+=middleNameFirstLetter;
		employeeID+=lastNameFirstLetter;
		
		return employeeID;
	}
	
	/**
	 * Get the day from the Date where the format is yyyy-MM-dd.
	 * 	Example: 2016-02-15
	 * 	Output: 15
	 * @param payrollDate
	 * @return
	 */
	public String getDayFromDateFormatYyyyMmDd(String payrollDate){
		// Java 8
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd", Locale.ENGLISH);
		LocalDate ld = LocalDate.parse(payrollDate, dtf);
		String day = dtf2.format(ld);
		
//		-------------------------------------------------
		// Java 7
//		 String[] temp = new String[3];
//		 payrollDate=payrollDate.replaceAll("-", " ");
//		 StringTokenizer st = new StringTokenizer(payrollDate);
//	     
//		 for (int i=0;st.hasMoreTokens();i++) {
//	         temp[i]=st.nextToken();
//	     }
//		 String day=temp[2];
		 
		return day;
	}
	/**
	 * Get the month from the Date where the format is yyyy-MM-dd.
	 * Example: 2016-02-15
	 * 	Output: Feb
	 * @param payrollDate
	 * @return
	 */
	public String getMonthFromDateFormatYyyyMmDd(String payrollDate){
		
		// Java 8
		payrollDate=convertDateYyyyMmDdToReadableDate(payrollDate);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.ENGLISH);
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
		LocalDate ld = LocalDate.parse(payrollDate, dtf);
		String month = dtf2.format(ld);
		
//		-------------------------------------------------
		// Java 7
//		 String[] temp = new String[3];
//		 payrollDate=payrollDate.replaceAll("-", " ");
//		 StringTokenizer st = new StringTokenizer(payrollDate);
//	     
//		 for (int i=0;st.hasMoreTokens();i++) {
//	         temp[i]=st.nextToken();
//	     }
//		 String month=convertEquivalentMonthNumberToText(Integer.parseInt(temp[1]));
		
		return month;
	}

	/**
	 * Get the month from the Date where the format is yyyy-MM-dd.
	 * Example: 2016-02-15
	 * 	Output: 02
	 * @param payrollDate
	 * @return
	 */
	public String getMonthMMFormatFromDateFormatYyyyMmDd(String payrollDate){
		
		// Java 8
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MM", Locale.ENGLISH);
		LocalDate ld = LocalDate.parse(payrollDate, dtf);
		String month = dtf2.format(ld);
		
//		-------------------------------------------------
		// Java 7
//		String[] temp = new String[3];
//		payrollDate=payrollDate.replaceAll("-", " ");
//		StringTokenizer st = new StringTokenizer(payrollDate);
//	    
//		for (int i=0;st.hasMoreTokens();i++) {
//	        temp[i]=st.nextToken();
//	    }
//		String month=temp[1];
		
		return month;
	}
	
	/**
	 * Get the year from the Date where the format is yyyy-MM-dd.
	 * 	Example: 2016-02-15
	 * 	Output: 2016
	 * @param payrollDate
	 * @return
	 */
	public String getYearFromDateFormatYyyyMmDd(String payrollDate){
		
		// Java 8
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH);
		LocalDate ld = LocalDate.parse(payrollDate, dtf);
		String year = dtf2.format(ld);
		
//		-------------------------------------------------
		// Java 7
//		String[] temp = new String[3];
//		payrollDate=payrollDate.replaceAll("-", " ");
//		StringTokenizer st = new StringTokenizer(payrollDate);
//	    
//		for (int i=0;st.hasMoreTokens();i++) {
//	        temp[i]=st.nextToken();
//	    }
//		String year=temp[0];
		
		return year;
	}
	
	
	/**
	 * Get the selected indices list based from the table and convert automatically to the model.
	 * @param table
	 * @return
	 */
	public int[] getSelectedIndexListBasedFromTableConvertedToModel(ReusableTable table){
		if(table==null)
			return null;
		
		int [] selectedIndexList=table.getSelectedRows();
		selectedIndexList=convertRowIndexToModel(
				table, selectedIndexList);
		
		return selectedIndexList;
	}
	
	/**
	 * These are special contractual
	 * @return
	 */
	public String[] getSpecialContractualList(){
		return new String[]{"2012-06-ACS", "2013-03-NAS", "2015-06-NAS"};
	}
	/**
	 * Get version number
	 * @return
	 */
	public double getVersionNumber(){
		String version = Constant.PAYROLL_SYSTEM_VERSION;
		
		String[] tokens = version.split( " " );
		return Double.parseDouble(tokens[tokens.length-1]);
	}
	/**
	 * Insert comma to a given number when the number is greater than 999.
	 * Ex: 10000.025 -> Ans.: 10,000.025;
	 * @param value
	 * @return
	 */
	public String insertComma(Object value){
		double doubleNum=Double.parseDouble(value.toString());
		
//		doubleNum=convertRoundToOnlyTwoDecimalPlaces(doubleNum);
		int numberOfDecimalPlaces=countNumberOfDecimalPlaces(doubleNum);
		 String numberAsString = String.format("%,."+numberOfDecimalPlaces+"f", doubleNum);

		return numberAsString;
	}
	
	
	
	/**
	 * Checks if the given day is the first pay of the month pr what we called
	 * 		first kinsenas.
	 * @param day
	 * @return
	 */
	public boolean isFirstPayOfTheMonth(String day){
		int dayInteger=Integer.parseInt(day);
		if(dayInteger<22){
			return true;
		}
		return false;
	}
	/**
	 * This method checks if the chosen combobox for column in dialog box has corresponding
	 * 		employer share like SSSEr,PAGIBIGEr,MedicareEr
	 * @param selectedColumnComboboxiNdex
	 * @return
	 */
	public boolean isSelectedColumnComboboxWithEmployerShare(int selectedColumnComboboxiNdex, EarningsAndDeductionLayout both){
		if(both instanceof DeductionViewPanel &&
				(selectedColumnComboboxiNdex==Constant.SSS_CONT_COLUMN_INDEX+1 || 
				selectedColumnComboboxiNdex==Constant.PAGIBIG_COLUMN_INDEX+1 || 
				selectedColumnComboboxiNdex==Constant.MEDICARE_COLUMN_INDEX+1 
				) 
		){
			return true;
		}
		return false;
	}
	
	/**
	 * Check if a string is a date with format yyyy-MM-dd.
	 * @param str
	 * @return
	 */
	public boolean isStringADateYyyyMmDd(String str){
		
		if(str.matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if a string is a number
	 * @param str
	 * @return
	 */
	public boolean isStringAThreeLetterMonth(String str){
		if(str.equals("Jan") ||
				str.equals("Feb") ||
				str.equals("Mar") ||
				str.equals("Apr") ||
				str.equals("May") ||
				str.equals("Jun") ||
				str.equals("Jul") ||
				str.equals("Aug") ||
				str.equals("Sep") ||
				str.equals("Oct") ||
				str.equals("Nov") ||
				str.equals("Dec") ){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a string is a number
	 * @param str
	 * @return
	 */
	public boolean isStringANumber(String str){
		if(str.matches("\\d+")){
			return true;
		}
		return false;
	}

	/**
	 * Checked if there are changes on signature data fields.
	 * @param db
	 * @param util
	 * @param preparedByName
	 * @param preparedByTitle
	 * @param checkedByName
	 * @param checkedByTitle
	 * @param auditedByName
	 * @param auditedByTitle
	 * @param approvedByName
	 * @param approvedByTitle
	 * @return
	 */
	public boolean isThereAreChangesOnSignatureData(Database db,Utilities util, String preparedByName,String preparedByTitle,
			String checkedByName, String checkedByTitle, String auditedByName, String auditedByTitle,
			String approvedByName, String approvedByTitle){
		
		String tempName=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
				db.signatureTableDataList.get(db.signatureTableColumnNames[1]): db.signatureTableDataList.get(db.signatureTableColumnNames[3])
		;
		String tempTitle=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
						db.signatureTableDataList.get(db.signatureTableColumnNames[2]): db.signatureTableDataList.get(db.signatureTableColumnNames[4]);	
		
				
		
		if(preparedByName.equals(tempName) && preparedByTitle.equals(tempTitle) &&
			checkedByName.equals(db.signatureTableDataList.get(db.signatureTableColumnNames[5])) && checkedByTitle.equals(db.signatureTableDataList.get(db.signatureTableColumnNames[6])) &&
			auditedByName.equals(db.signatureTableDataList.get(db.signatureTableColumnNames[7])) && auditedByTitle.equals(db.signatureTableDataList.get(db.signatureTableColumnNames[8])) &&
			approvedByName.equals(db.signatureTableDataList.get(db.signatureTableColumnNames[9])) && approvedByTitle.equals(db.signatureTableDataList.get(db.signatureTableColumnNames[10]))){
			return false;
		}
		
		return true;
	}
	public void logs(){
		
	}
	
	/**
	 * Process the lower half value when the original value when divided by 2 is not divisible or bungkeg.
	 * 	After the process the lower half + bigger half should equate to the original value .
	 * 
	 * Example: Original Value->1896.89
	 * 		Correct Result-> Bigger Value: 984.45 | Lower Value: 984.44
	 * @param biggerValue
	 * @return
	 */
	public double processLowerHalfValueTwoDecimal(double biggerValue){
		int integerValue=(int) biggerValue;
		double decimalValue=(biggerValue-integerValue);
		
		decimalValue=convertRoundToOnlyTwoDecimalPlaces(decimalValue);
		System.out.println("\t Decimal value rounded to two decimals: "+decimalValue+CLASS_NAME);
		
		String str=""+decimalValue;
		int numOfDecimals=str.length()-1;
		
		return (numOfDecimals==1)?
				convertRoundToOnlyTwoDecimalPlaces(biggerValue-0.1)
				:
				convertRoundToOnlyTwoDecimalPlaces(biggerValue-0.01);
	}
	
	/**
	 * Remove comma on numbers.
	 * @param str
	 * @return
	 */
	public String removeCommaOnNumbers(String str){
		str= str.replaceAll(",", "");		
		return str;
	}
	/**
	 * Remove all spaces to a string to convert it to a camelcase.
	 * Example: SSS Er -> SSSEr
	 * @param str
	 * @return
	 */
	public String removeSpacesToBeConvertedToCamelCase(String str){
		str=str.replaceAll(" ", "");
		return str;
	}
	
	/**
	 * Thread sleep/delay
	 * @param num
	 */
	public void threadSleep(long num){
		
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Convert the two given dates into period form.
	 * 	Example: 2018-04-15  ,  2018-04-30
	 * Output: Apr 15-30, 2018
	 * @param payrollDateStart
	 * @param payrollDateEnd
	 * @return
	 */
	public String processPayrollPeriodString(String payrollDateStart, String payrollDateEnd){
		String startDay=getDayFromDateFormatYyyyMmDd(payrollDateStart);
		String endDay=getDayFromDateFormatYyyyMmDd(payrollDateEnd);
		String year=getYearFromDateFormatYyyyMmDd(payrollDateEnd);
		String month=getMonthFromDateFormatYyyyMmDd(payrollDateEnd);
				//convertThreeLetterMonthIntoCompleteName(getMonthFromDateFormatYyyyMmDd(payrollDateEnd));
		
		
		return month+" "+startDay+"-"+endDay+", "+year;
	}
	
	
	private void l_______________________________________l(){}
	
	/**
	 * Adjust image
	 * @param icon
	 * @param c
	 * @return
	 */
//	public BufferedImage adjustImage(BufferedImage icon,CPUPanelImageModel c){
//		 
//		BufferedImage image = new BufferedImage( icon.getWidth() , icon.getHeight(),icon.getType());
//	     
//	        System.out.println("FUCK: "+icon.getRGB(0, 12));
//	        
//	        for( int y = 0 ; y< icon.getHeight(); y++ ){
//	            for( int x = c.getX() ; x<(c.getWidth()+c.getX()) ; x++ ){
//	                image.setRGB(x, y, icon.getRGB(x, y));
//	            }
//	        }
//	        
//	        image=image.getSubimage(c.getX(), 0, c.getWidth(), icon.getHeight());
//	        
//	        return image;
//	        
//	}
	
	/**
	 * Set the item values of the department combo box.
	 * isAll indicates that if it is true, then there must be an 'ALL' in combobox,
	 * 		otherwise there are no 'ALL'.
	 * @param db
	 * @param departmentCombobox
	 * @param isALl
	 * @return
	 */
	private JComboBox<String> setDepartmentComboboxItemValues(Database db,JComboBox<String>departmentCombobox,boolean isALl){
		departmentCombobox.removeAllItems();
		departmentCombobox.addItem((isALl)?"ALL":"");
		for(Integer index: db.departmentDataList.keySet()){
			departmentCombobox.addItem(db.departmentDataList.get(index));
		}
	
		return departmentCombobox;
	}
	
	/**
	 * Convert imageicon to bytearrayinputstream
	 * @param path
	 * @return
	 */
	public ByteArrayInputStream convertImageIconToByteArrayInputStream(String path){
		ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        BufferedImage bImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bImage.getGraphics();
        bg.drawImage(image,0,0,null);
        bg.dispose();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
			ImageIO.write(bImage,"png",out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] buf = out.toByteArray();
        // setup stream for blob
        ByteArrayInputStream inStream = new ByteArrayInputStream(buf);
        
        return inStream;
	}
	
	/**
	 * Initialize new button in a way that the image is integrated well.
	 * Remember: Make sure the size of the button is the same with the size of the
	 * 		image to avoid showing square lines.
	 * Why -1,-1,-1,-1? since the layout that button is added to is NOT null.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param icon
	 * @param hover
	 * @return
	 */
	public JButton initializeNewButton(int x, int y, int width, int height,
			ImageIcon icon, ImageIcon hover){
		
		JButton btn = new JButton();
		btn.setBounds(x,y,width,height);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if(hover!=null){
			btn.setIcon(icon);
			btn.setRolloverIcon(hover);
			btn.setOpaque(false);
			btn.setContentAreaFilled(false);
			btn.setBorderPainted(false);
			btn.setBorder(BorderFactory.createEmptyBorder());
		}
		return btn;
	}
	
	/**
	 * Join two images into one.
	 * @param img1
	 * @param img2
	 * @return
	 */
	 public BufferedImage joinBufferedImage(BufferedImage img1,
		      BufferedImage img2) {
		    int offset = 2;
		    int width = img1.getWidth() + img2.getWidth() + offset;
		    int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
		    BufferedImage newImage = new BufferedImage(width, height,
		        BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2 = newImage.createGraphics();
		    Color oldColor = g2.getColor();
		    g2.setPaint(Color.BLACK);
		    g2.fillRect(0, 0, width, height);
		    g2.setColor(oldColor);
		    g2.drawImage(img1, null, 0, 0);
		    g2.drawImage(img2, null, img1.getWidth() + offset, 0);
		    g2.dispose();
		    return newImage;
		  }
	 
	 /**
		 * Get string content based from a component type.
		 * Set method-> setComponentStringValue
		 * @param component
		 * @return
		 */
		public String getComponentStringValue(JComponent component){
			if(component instanceof JTextField){
				return ((JTextField) component).getText();
			}
			else if(component instanceof JComboBox<?>){
				return ((JComboBox<?>) component).getModel().getSelectedItem().toString();
			}else if(component instanceof JDatePickerImpl){
				return ((JDatePickerImpl) component).getJFormattedTextField().getText();
			}
			
			return null;
		}
		
		
	 
	/**
	 * Convert any jcomponent into an image.
	 * @param component
	 * @return
	 */
	public java.awt.Image getImageFrom(Component component) {
        BufferedImage image = new BufferedImage(component.getWidth(),
                component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image;
    }
	
	
	
	/**
	 * Based from path, read buffered image.
	 * @param image
	 * @return
	 */
	public BufferedImage readBufferedImage(String image) {
		try {
			return ImageIO.read(new File(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *  WHen adding another components in a panel after initializations are done, be sure to invoke this code!
	 * 
	 * @param component
	 * @return
	 */
	public JComponent revalidateToMakeSureComponentsAreUpdated(JComponent component){
		//--> WHen adding another components in a panel after initializations are done, be sure to invoke this code!
				component.revalidate();
				return component;
	}
	
	/**
	 * Render image, used in rotating the image method.
	 * @param g
	 * @return
	 */
	public  Graphics2D render(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_DEFAULT);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DEFAULT);
		return g;
	}
	
	public BufferedImage resizeImage(BufferedImage origImg, int IMG_WIDTH, int IMG_HEIGHT){
        if(origImg == null)
            return null;
        try{
            int type = origImg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : origImg.getType();
            BufferedImage resizedImg = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
            Graphics2D g = resizedImg.createGraphics();
            g = render(g);
            g.drawImage(origImg, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
            g.setComposite(AlphaComposite.Src);
            g.dispose();
            return resizedImg;
        }catch(Exception e){
            System.err.println("resizeImage method error: "+e.getLocalizedMessage());
        }
        return origImg;
	}
	/**
	 * Rotate image based from given angle
	 * @param origImg
	 * @param angle
	 * @return
	 */
	public  BufferedImage rotateImage(BufferedImage origImg, double angle){
		try{
			int type = origImg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : origImg.getType();
			double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
			int width = origImg.getWidth(), height = origImg.getHeight();
		    int newWidth = (int)Math.round(width*cos+height*sin), newHeight = (int)Math.round(height*cos+width*sin);
		    BufferedImage rotatedImg = new BufferedImage(newWidth, newHeight, type);
			Graphics2D g = rotatedImg.createGraphics();
			g.translate((newWidth-width)/2, (newHeight-height)/2);
			g = render(g);
		    g.rotate(angle, width/2, height/2);
			g.drawRenderedImage(origImg, null);
			g.setComposite(AlphaComposite.Src);
			g.dispose();
			return rotatedImg;
		}catch(Exception e){
			System.err.println("rotateImage method error: "+e.getLocalizedMessage());
		}
		return origImg;
	}
	
	
	/**
	 * Set/Assign values of string to a component.
	 * Get method-> getComponentStringValue
	 * @param component
	 * @param value
	 * @return
	 */
	public JComponent setComponentStringValue(JComponent component,String value){
		if(component instanceof JTextField){
			((JTextField) component).setText(value);
		}
		else if(component instanceof JComboBox<?>){
			((JComboBox) component).getModel().setSelectedItem(value);
		}else if(component instanceof JDatePickerImpl){
			((JDatePickerImpl) component).getJFormattedTextField().setText(value);
		}
		return component;
	}
	
	
	/**
	 * Set the items in show  column combo box 
	 * @param db
	 */
	public JComboBox<String> setColumnComboBoxItemValues(Database db, String[] preferredColumnNameList,JComboBox<String>dataColumnComboBox){
		dataColumnComboBox.removeAllItems();
		dataColumnComboBox.addItem("");
		for(int i=0;i<preferredColumnNameList.length;i++){
			dataColumnComboBox.addItem(
				Utilities.getInstance().convertCamelCaseColumnNamesToReadable(
					preferredColumnNameList[i]
				)
			);
		}
		
		return dataColumnComboBox;
	}
	
	

	
	/**
	 * Update all UI departmentBoxes in the program.
	 * @param db
	 */
	public void updateAllUIDepartmentComboboxes(Database db){
		//--> Update UI departmentComboBoxes
		db.initializeDepartmentNametData();
		
		
		//-----------------------------------------------------------------------------------
		// EMPLOYEE VIEW
		
		//--> Add employee dialog combobox
		AddEmployeeDialog addEmployeeDialog=AddEmployeeDialog.getInstance();
		addEmployeeDialog.contentPanel.departmentComboBox=setDepartmentComboboxItemValues(
				db, addEmployeeDialog.contentPanel.departmentComboBox,false
		);
		
		//--> Update employee dialog combobox
		UpdateEmployeeDialog updateEmployeeDialog=UpdateEmployeeDialog.getInstance();
		updateEmployeeDialog.contentPanel.departmentComboBox=setDepartmentComboboxItemValues(
				db, updateEmployeeDialog.contentPanel.departmentComboBox,false
		);
		
		
		//--> Delete/Update dialog combobox
		DeleteUpdateEmployeeDialog bothDeleteUpdateEmployeeDialog=DeleteUpdateEmployeeDialog.getInstance();
		bothDeleteUpdateEmployeeDialog.departmentComboBox=setDepartmentComboboxItemValues(
				db, bothDeleteUpdateEmployeeDialog.departmentComboBox,true
		);
		
		//-----------------------------------------------------------------------------------
		// PAYROLL VIEW
		
		
		//--> Show all employee for adding payroll data combobox
		ShowAllEmployeeAddPayrollDataDialogLayout showEmpAddPayDataDialog=PayrollViewPanel.getInstance().showAllEmpAddPayDaDialog;
		showEmpAddPayDataDialog.departmentComboBox=setDepartmentComboboxItemValues(
				db, showEmpAddPayDataDialog.departmentComboBox, true);
		
		
		
		//--> Payrill display EMPLOYEE payroll pdf data option dialog combobox
		DisplayOptionsDialog displayOptionDialog=PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL;
		displayOptionDialog.departmentComboBox=setDepartmentComboboxItemValues(
				db, displayOptionDialog.departmentComboBox,false
		);
		
		//--> Show Display payroll data option PANEL.
		PayrollViewPanel panel=PayrollViewPanel.getInstance();
		panel.departmentComboBox=setDepartmentComboboxItemValues(
				db, panel.departmentComboBox,true
		);
		
		
		
		
		//-----------------------------------------------------------------------------------
		// EARNING VIEW
		
	
		
		//--> Earning display PANEL combobox
		EarningViewPanel.getInstance().departmentComboBox=setDepartmentComboboxItemValues(
				db, EarningViewPanel.getInstance().departmentComboBox,true
		);
		
		//---------------------------------------
		// DEDUCTION VIEW
		
		
		
		//--> Deduction display PANEL combobox
		DeductionViewPanel.getInstance().departmentComboBox=setDepartmentComboboxItemValues(
				db, DeductionViewPanel.getInstance().departmentComboBox,true
		);
	}
	
	public void updateAllUIPayrollDateComoboxes(Database db){

		//--> Get Data from Database
		//> Process order
		OrderByInfo orderInfo=new OrderByInfo(
				new String[]{db.payrollDateTableColumnNames[0]},
				"DESC"
		);
		
		//> Get the data
		db.selectDataInDatabase(new String[]{db.tableNamePayrollDate}, new String[]{db.payrollDateTableColumnNames[0]}, null, null,orderInfo, Constant.SELECT_BASED_FROM_COLUMN);
		ResultSet rs=db.resultSet;
		String []payrollDateList=null;
		
		//> Transfer data to string array
		try {
			rs.last();
			int totalRows=rs.getRow();
			rs.beforeFirst();
			payrollDateList=new String[totalRows];
			
			for(int i=0;i<payrollDateList.length;i++){
				rs.absolute(i+1);
				payrollDateList[i]=rs.getObject(1).toString(); // Why 1? Since the result data return only ONE column.
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//--> This makes sure that you will update the combo boxes if there are existing payroll dates in payrollDateTable.
		if(payrollDateList.length>0){
			//------------------------------------------------------------------
			//--> PAYROLL VIEW
			PayrollViewPanel payrollViewPanel=PayrollViewPanel.getInstance();
			
			JComboBox<String> displayEmployeePDFDialogPayrollDateCombobox=payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL.payrollDateComboBox,
					displayDepartmentPDFDialogPayrollDateCombobox=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.payrollDateComboBox,
					showPayrollDataPayrollDateCombobox=payrollViewPanel.payrollDateComboBox,
					addCommentOnPayslipPayrollDateCombobox=PayrollViewPanel.getInstance().addCommentsOnAllPayslipDialog.payrollDateComboBox; 
					

			
		
			
			showPayrollDataPayrollDateCombobox.removeAllItems();
			showPayrollDataPayrollDateCombobox.addItem(Constant.STRING_ALL);
			
			displayEmployeePDFDialogPayrollDateCombobox.removeAllItems();
			displayEmployeePDFDialogPayrollDateCombobox.addItem("");
			
			displayDepartmentPDFDialogPayrollDateCombobox.removeAllItems();
			displayDepartmentPDFDialogPayrollDateCombobox.addItem("");
			
			
			addCommentOnPayslipPayrollDateCombobox.removeAllItems();
			addCommentOnPayslipPayrollDateCombobox.addItem("");
			
			for(String str:payrollDateList){
				str=convertDateYyyyMmDdToReadableDate(str);
				

				
				//> Show Payroll Data PANEL
				showPayrollDataPayrollDateCombobox.addItem(str);
				
				//> Display Employee Payroll Data PDF
				displayEmployeePDFDialogPayrollDateCombobox.addItem(str);
				
				//> Display Department Payroll Data PDF
				displayDepartmentPDFDialogPayrollDateCombobox.addItem(str);
	
				
				//> Add Comment on all Payslip.
				addCommentOnPayslipPayrollDateCombobox.addItem(str);
			}
			
			//------------------------------------------------------------------
			//--> EARNING/DEDUCTION VIEW
			EarningsAndDeductionLayout [] earndedList={EarningViewPanel.getInstance(),DeductionViewPanel.getInstance()};
		
			
			//> Show Payroll Display PANEL EARNING/DEDUCTION View Panel
			for(EarningsAndDeductionLayout earnded:earndedList){
				
				earnded.payrollDateComboBox.removeAllItems();
				earnded.payrollDateComboBox.addItem(Constant.STRING_ALL);
				
				for(String str:payrollDateList){
					str=convertDateYyyyMmDdToReadableDate(str);
					earnded.payrollDateComboBox.addItem(str);
				}
			}
			
			
			//------------------------------------------------------------------
			//--> PAYROLL VIEW | AddEarningOrDeductionDataLayout
			
			//> Show display option DIALOG
			AddEarningOrDeductionDataLayout addEDDatadialog=PayrollViewPanel.getInstance().addEDDataDialog;
			JComboBox<String> addPayrollViewPayrollDateCombobox=addEDDatadialog.payrollDateCombobox;
			
			addPayrollViewPayrollDateCombobox.removeAllItems();
			addPayrollViewPayrollDateCombobox.addItem("");
			
			for(String str:payrollDateList){
				str=convertDateYyyyMmDdToReadableDate(str);
				addPayrollViewPayrollDateCombobox.addItem(str);
			}
			
			
			
		}
		else{
			//------------------------------------------------------------------
			//--> PAYROLL VIEW
			PayrollViewPanel payrollViewPanel=PayrollViewPanel.getInstance();
			
			JComboBox<String> displayEmployeePDFDialogPayrollDateCombobox=payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL.payrollDateComboBox,
					displayDepartmentPDFDialogPayrollDateCombobox=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.payrollDateComboBox,
					showPayrollDataPayrollDateCombobox=payrollViewPanel.payrollDateComboBox;
			

			showPayrollDataPayrollDateCombobox.removeAllItems();
			displayEmployeePDFDialogPayrollDateCombobox.removeAllItems();
			displayDepartmentPDFDialogPayrollDateCombobox.removeAllItems();
			
			
			//------------------------------------------------------------------
			//--> EARNING/DEDUCTION VIEW
			EarningsAndDeductionLayout [] earndedList={EarningViewPanel.getInstance(),DeductionViewPanel.getInstance()};
			
			
			
			//> Show Payroll Display Options PANEL EARNING/DEDUCTION View Panel
			for(EarningsAndDeductionLayout earnded:earndedList){
				JComboBox<String> displayOptionDialogPayrollDateCombobox=earnded.payrollDateComboBox;
				displayOptionDialogPayrollDateCombobox.removeAllItems();
			}
			
			
			//------------------------------------------------------------------
			//--> PAYROLL VIEW
			AddEarningOrDeductionDataLayout addEDDatadialog=PayrollViewPanel.getInstance().addEDDataDialog;
			JComboBox<String> addEarnDedDataPayrollDateCombobox=addEDDatadialog.payrollDateCombobox;
			
			addEarnDedDataPayrollDateCombobox.removeAllItems();
			
		}
	}
	
	/**
	 * Update username label, depending on the username.
	 * @param mainFrame
	 * @param img
	 * @param userName
	 */
	public void updateUserNameLabel(MainFrame mainFrame, Images img,String userName){
		userName=userName.replaceAll(" ", "");
		
		if(userName.equals("admin")){
			mainFrame.userNameLabel.setIcon(img.userNameAdmin);
		}
		else if(userName.equals("user")){
			mainFrame.userNameLabel.setIcon(img.userNameUser);
		}
		else if(userName.equals("guest")){
			mainFrame.userNameLabel.setIcon(img.userNameGuest);
		}
	}
	
	private void l__________________________________________________l(){}
	
	public static Utilities getInstance(){
		if(instance==null)
			instance=new Utilities();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
