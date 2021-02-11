package database;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;




















import com.mysql.cj.jdbc.Blob;

import model.Constant;
import model.ContractualSalaryRateInfo;
import model.MultipleUpdateDatabaseModel;
import model.OrderByInfo;
import model.PhicInfo;
import model.SelectConditionInfo;
import model.SssInfo;
import model.statics.Logs;
import model.statics.Utilities;
import model.view.ReusableTable;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import view.LoginFrame;
import view.MainFrame;
import view.dialog.AddEmployeeDialog;
import view.dialog.DeleteUpdateEmployeeDialog;

public class Database {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	private static Database instance;
	// JDBC driver name and database URL
	static final String DRIVER =
			/* NEW VERSION:*/ "com.mysql.cj.jdbc.Driver";
//			/* OLD VERSION:*/ "com.mysql.jdbc.Driver";
	
	// Database URL syntax: jdbc:mysql://hostname:portNumber/databaseName
	// Example:"jdbc:mysql://localhost/testdb"
	static final String DATABASE_URL_START = "jdbc:mysql://";
	
	

	// Important Database String
	private String hostName="localhost";//BILECO IP Address: "192.168.1.107 /117" ; 	||  Balay IP: "192.168.254.107/117" ||  Carl Laptop: "192.168.1.62" 
	private String portNumber="3306";		//default
	private String databaseName="bileco_db"
			+ "";	//default --> bileco_db and bileco_db_test
	private String userName="root";// XietrZ_PC: "root"/"XietrZ" ||  Carls_PC: "root"/"Bileco" || MaamKath_PC: "root"/"BILECO"
	private String password="45174517xietrz";// XietrZ_PC: "1234" ||  Carls_PC: "Term1nat0r" || MaamKath_PC: "Term1nat0r"
	
	
	
	
	private void l________________________________l(){}
	
	
	public Connection connection = null; // manages connection
	public Statement statement = null; // query statement
	public ResultSet resultSet = null; // manages results
	public ResultSetMetaData metaData= null;
	public PreparedStatement preparedStatement= null;
	
	//Table Names
	public String tableNameEmployee="employee",
			tableNameEarnings="earnings",
			tableNameEarningsAutomate="earningsautomate",
			tableNameDeductions="deductions",
			tableNamePayrollDate="payrolldate",
			tableNameUserAccount="user_account",
			tableNameDepartment="department",
			tableNamePhic="phic",
			tableNamePhicRate="phicrate",
			tableNameSss="sss",
			tableNameStPeter="stpeter",
			tableNameABODC="abodc",
			tableNamePagibig="pagibig",
			tableNameUnionDues="uniondues",
			tableNameEmployerShare="employershare",
			tableNameEarningsContractual="earningscontractual",
			tableNameContractualSalaryRate="contractualsalaryrate",
			tableNameSystemStatus="systemstatus",
			tableNameSignatureTable="signaturetable";
	
	
	//--> Table Columns:
	public String []employeeTableColumnNames=null,
		deductionTableColumnNames=null,deductionTableColumnNamesNoComment=null,
		earningTableColumnNames=null,earningsAutomateTableColumnNames=null,
		departmentTableColumnNames=null, payrollDateTableColumnNames=null,
		phicTableColumnNames=null,phicRateTableColumnNames=null, sssTableColumnNames=null,
		abodcTableColumnNames=null,pagibigTableColumnNames=null,stPeterTableColumnNames=null,
		unionDuesTableColumnNames=null,employerShareTableColumnNames=null, 
		earningsContractualColumnNames=null,
		deductionsContractualColumnNames=null,deductionsContractualColumnNamesNoComment=null,
		contractualSalaryRateColumnNames=null,signatureTableColumnNames=null;
	
	
	//--> Store data from database that will be used throughout the whole program.
	public HashMap<Integer,String> departmentDataList;
	public String phicRate;
	public HashMap<Integer,PhicInfo> phicSalaryRangeDataList;
	public HashMap<Integer, SssInfo> sssDataList;
	public HashMap<Integer,ContractualSalaryRateInfo> contractualSalaryRateDataList;
	public HashMap<String,String> signatureTableDataList;
	
	public boolean isConnected=false,isInsert=false,isDelete=false,isUpdate=false;
	public int totalUpdates=0;
	
	private void l_____________________________________l(){}
	 
	public Database(){
	
	}
	
	private void l______________________________________l(){}
	
	/**
	 * Store all column names of employee table to an array of string
	 */
	private void initializeEmployeeTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameEmployee}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			employeeTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				employeeTableColumnNames[i]=metaData.getColumnName(i+1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logs.getInstance().printlnLogs(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
			
		
		}
		
	}
	
	/**
	 * Store all column names of deduction table in an array.
	 */
	private void initializeDeductionTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameDeductions}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			deductionTableColumnNames=new String[metaData.getColumnCount()-1];
			deductionTableColumnNamesNoComment= new String[deductionTableColumnNames.length-1];
			for(int i=0,j=0;i<metaData.getColumnCount();i++){
				if(i!=metaData.getColumnCount()-3){ // Do not include column: Others
					deductionTableColumnNames[j]=metaData.getColumnName(i+1);
					
					if(i!=metaData.getColumnCount()-1) // Do not inculude column: Comments
						deductionTableColumnNamesNoComment[j]=metaData.getColumnName(i+1);
					
					j++;
				}
			}
			
			//--> For Debugging Purposes
			Logs.getInstance().printlnLogs("\tTable Name: "+tableNameDeductions+CLASS_NAME);
			for(int i=0;i<deductionTableColumnNames.length;i++){
				Logs.getInstance().printlnLogs("\t\t"+deductionTableColumnNames[i]+CLASS_NAME);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logs.getInstance().printlnLogs(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Store all column names of earning table in an array.
	 */
	private void initializeEarningTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameEarnings}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			earningTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				earningTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			Logs.getInstance().printlnLogs("\tTable Name: "+tableNameEarnings+CLASS_NAME);
			for(int i=0;i<earningTableColumnNames.length;i++){
				Logs.getInstance().printlnLogs("\t\t"+earningTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logs.getInstance().printlnLogs(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void initializePayrollDateTableColumns(){
		selectDataInDatabase(new String[]{tableNamePayrollDate}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			payrollDateTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				payrollDateTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			Logs.getInstance().printlnLogs("\tPayroll Table Name: "+tableNameDepartment+CLASS_NAME);
			for(int i=0;i<payrollDateTableColumnNames.length;i++){
				Logs.getInstance().printlnLogs("\t\t"+payrollDateTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Get all the columns and store it in an array for future use.
	 */
	private void initializeDepartmentTableColumns(){
		selectDataInDatabase(new String[]{tableNameDepartment}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			departmentTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				departmentTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tDeparment Table Name: "+tableNameDepartment+CLASS_NAME);
			for(int i=0;i<departmentTableColumnNames.length;i++){
				System.out.println("\t\t"+departmentTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Get all the columns for phic and phicrate table
	 * 		and store it in an array for future use.
	 */
	private void initializePhicAndPhicRateTableColumns(){
		try {
			
			//--> PHIC Salary range
			
			selectDataInDatabase(
					new String[]{tableNamePhic},
					null, null,null,null,
					Constant.SELECT_ALL);
			
		
			phicTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				phicTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tPHIC Salary Table Name: "+tableNamePhic+CLASS_NAME);
			for(int i=0;i<phicTableColumnNames.length;i++){
				System.out.println("\t\t"+phicTableColumnNames[i]+CLASS_NAME);
			}
			
			//--> PHIC RATE
			selectDataInDatabase(
					new String[]{tableNamePhicRate},
					null, null,null,null,
					Constant.SELECT_ALL);
			
			phicRateTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				phicRateTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tPHIC RATE Table Name: "+tableNamePhicRate+CLASS_NAME);
			for(int i=0;i<phicRateTableColumnNames.length;i++){
				System.out.println("\t\t"+phicRateTableColumnNames[i]+CLASS_NAME);
			}
			
		
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Initialize and store sss column names to an array.
	 */
	private void initializeSssTableColumns(){
	selectDataInDatabase(new String[]{tableNameSss}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			sssTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				sssTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tSSS Table Name: "+tableNameSss+CLASS_NAME);
			for(int i=0;i<sssTableColumnNames.length;i++){
				System.out.println("\t\t"+sssTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Initialize the asemco/bcci/occci/dbp/cfi table column names
	 */
	private void initializeAsemcoBcciOccciDbpCfiTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameABODC}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			abodcTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				abodcTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tASEMCO/BCCI/OCCCI/CFI/DBP Table Name: "+tableNameABODC+CLASS_NAME);
			for(int i=0;i<abodcTableColumnNames.length;i++){
				System.out.println("\t\t"+abodcTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Initialize the st. peter table column names
	 */
	private void initializePagibigTableColumnNames(){
		selectDataInDatabase(new String[]{tableNamePagibig}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			pagibigTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				pagibigTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tPag-ibig Table Name: "+tableNamePagibig+CLASS_NAME);
			for(int i=0;i<pagibigTableColumnNames.length;i++){
				System.out.println("\t\t"+pagibigTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Initialize the pagibig table column names
	 */
	private void initializeStPeterTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameStPeter}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			stPeterTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				stPeterTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tST. Peter Table Name: "+tableNameStPeter+CLASS_NAME);
			for(int i=0;i<stPeterTableColumnNames.length;i++){
				System.out.println("\t\t"+stPeterTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Initialize the Union Dues table column names
	 */
	private void initializeUnionDuesTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameUnionDues}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			unionDuesTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				unionDuesTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tUnion Dues Table Name: "+tableNameUnionDues+CLASS_NAME);
			for(int i=0;i<unionDuesTableColumnNames.length;i++){
				System.out.println("\t\t"+unionDuesTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Initialize the Employer Share table column names
	 */
	private void initializeEmployerShareTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameEmployerShare}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			employerShareTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				employerShareTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tEmployer Share Table Name: "+tableNameEmployerShare+CLASS_NAME);
			for(int i=0;i<employerShareTableColumnNames.length;i++){
				System.out.println("\t\t"+employerShareTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * Initialize the Earnings Automate: ECOLA, Laundry Allowance, Longevity, Rice 
	 * 	table column names
	 */
	private void initializeEarningAutomateTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameEarningsAutomate}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			earningsAutomateTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				earningsAutomateTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tEarning Automate Table Name: "+tableNameEarningsAutomate+CLASS_NAME);
			for(int i=0;i<earningsAutomateTableColumnNames.length;i++){
				System.out.println("\t\t"+earningsAutomateTableColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * Initialize the Earnings Contractual, exclusive only to contractual.
	 * 	table column names
	 */
	private void initializeEarningsContractualTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameEarningsContractual}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			earningsContractualColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				earningsContractualColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tEarnings Contractual Table Name: "+tableNameEarningsContractual+CLASS_NAME);
			for(int i=0;i<earningsContractualColumnNames.length;i++){
				System.out.println("\t\t"+earningsContractualColumnNames[i]+CLASS_NAME);
			}
//			
//			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Initialize the Deductions Contractual, exclusive only to contractual.
	 * 	table column names
	 */
	private void initializeDeductionsContractualTableColumnNames(){
		// 11 -> DeductionID, PayrollDate, EmployeeID, SSSLoan, Pag-ibigLoan, SSSCont, PagibigCont,
		//			Medicare, EMLOAN, A/R, Others,TotalDeductions, Comments
		
		int totalColumns=13;
		
		selectDataInDatabase(new String[]{tableNameDeductions}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			deductionsContractualColumnNames=new String[totalColumns];
			deductionsContractualColumnNamesNoComment=new String[deductionsContractualColumnNames.length-1];
			
			for(int h=0,i=0,j=12;i<metaData.getColumnCount();i++){
				if(i<8){ // DeductionID, PayrollDate, EmployeeID, SSSLoan, Pag-ibigLoan,
							//SSSCont, PagibigCont,Medicare
					deductionsContractualColumnNames[h]=metaData.getColumnName(i+1);
					h++;
				}
				else if(i>=j && i<=13){ // EMLOAN, A/R
					deductionsContractualColumnNames[h]=metaData.getColumnName(i+1);
					h++;
				}
				else if(i>=metaData.getColumnCount()-3 && i<=metaData.getColumnCount()-1){ // Others, TotalDeductions
					deductionsContractualColumnNames[h]=metaData.getColumnName(i+1);
					h++;
				}
			}
			
			for(int i=0;i<deductionsContractualColumnNames.length-1;i++){
				deductionsContractualColumnNamesNoComment[i]=deductionsContractualColumnNames[i];
			}
			
			//--> For Debugging Purposes
			System.out.println("\tDeductions Contractual Table Name: Deduction Contractual"+CLASS_NAME);
			for(int i=0;i<deductionsContractualColumnNames.length;i++){
				System.out.println("\t\t"+deductionsContractualColumnNames[i]+CLASS_NAME);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logs.getInstance().printlnLogs(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * Initialize the Contractual Salary Rate, exclusive only to contractual.
	 * 	table column names
	 */
	private void initializeContractualSalaryRateTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameContractualSalaryRate}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			contractualSalaryRateColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				contractualSalaryRateColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tEarnings Contractual Salary Rate Table Name: "+tableNameContractualSalaryRate+CLASS_NAME);
			for(int i=0;i<contractualSalaryRateColumnNames.length;i++){
				System.out.println("\t\t"+contractualSalaryRateColumnNames[i]+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Initialize the Signature Table
	 * 	table column names
	 */
	private void initializeSignatureTableColumnNames(){
		selectDataInDatabase(new String[]{tableNameSignatureTable}, null, null,null,null,Constant.SELECT_ALL);
		
		try {
			signatureTableColumnNames=new String[metaData.getColumnCount()];
			for(int i=0;i<metaData.getColumnCount();i++){
				signatureTableColumnNames[i]=metaData.getColumnName(i+1);
			}
			
			//--> For Debugging Purposes
			System.out.println("\tSignature Table Name: "+tableNameSignatureTable+CLASS_NAME);
			for(int i=0;i<signatureTableColumnNames.length;i++){
				System.out.println("\t\t"+signatureTableColumnNames[i]+CLASS_NAME);
			}
//			
//			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Generate databaase url.
	 * @return
	 */
	private String generateDatabaseURL(){
//		jdbc:mysql://192.168.43.7:3306/bileco_db_test	
//		jdbc:mysql://localhost:3306/,
//		jdbc:mysql://localhost/testdb"
		
		return DATABASE_URL_START+""+hostName+":"+portNumber+"/"+databaseName;

	}
	
	
	/**
	 * Set the prepared statement based on the class of the object.
	 * @param pstmt
	 * @param parameterIndex
	 * @param objectValue
	 * @return
	 * @throws SQLException 
	 */
	private PreparedStatement setPreparedStatmentBasedOnObjectClass(
			PreparedStatement pstmt, int parameterIndex,
			Object objectValue) throws SQLException{
		
		if(objectValue==null || objectValue.getClass().toString().equals("class java.io.ByteArrayInputStream")){
			pstmt.setBinaryStream(parameterIndex, (InputStream) objectValue); // saving image on database.
		}
		else if(objectValue.getClass().toString().equals("class java.lang.Double")){
			pstmt.setDouble(parameterIndex, (Double)objectValue);
		}
		else if(objectValue.getClass().toString().equals("class java.lang.String")){
			pstmt.setString(parameterIndex, objectValue.toString());
		}
		else if(objectValue.getClass().toString().equals("class java.lang.Date")){
			pstmt.setDate(parameterIndex, (Date)objectValue);
		}
		
		return pstmt;
	}
	
	
	
	
	
	private void l____________________________________________________________________________________l(){}
	/**
	 * Check if the server or connection to database is disconnected.
	 */
	public void checkIfDisconnectedToDatabase(){
		if(!isConnected && LoginFrame.getInstance().mainThread!=null && LoginFrame.getInstance().mainThread.isPause){
			LoginFrame.getInstance().mainThread.resumeThread();
		}
	}
	
	/**
	 * Closes all connection to database
	 * 
	 */
	public void closeALLConnectionToDatabase() {
		try {
			if(resultSet!=null){
				resultSet.close();
				resultSet=null;
			}
			
			if(statement!=null){
				statement.close();
				statement=null;
			}
			
			if(preparedStatement!=null){
				preparedStatement.close();
				preparedStatement=null;
			}
			
			if(connection!=null){
				connection.close();
				connection=null;
				isConnected=false;
			}
			
			System.out.println("\tClose All COnnection!"+CLASS_NAME);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					null, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Close connection and statement.
	 */
	public void closeConnection(){
		try {
			
			if(connection!=null){
				connection.close();
				isConnected=false;
				connection=null;
			}
			
			System.out.println("\tClose connection only!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					null, JOptionPane.ERROR_MESSAGE);
			
			
		}
		
	}
		


	/**
	 * Connect to database
	 * Steps:
	 * 	1. Load driver class
	 *	2. Establish connection to database
	 *	3. Create Statement for querying database
	 *	4. Ensure resultSet, statement and connection are closed
	 */
	public void connectToDatabase(String hostName, String portNumber, String dbName){
		this.hostName=hostName;
		this.portNumber=portNumber;
		this.databaseName=dbName;
		
		if(!isConnected){
			System.out.println("\tDatabase URL: "+generateDatabaseURL()+CLASS_NAME);
			
			try{
				// load the driver class
				Class.forName( DRIVER );
				// establish connection to database
				connection =
						DriverManager.getConnection( generateDatabaseURL(), userName, password );
				
				
					
				isConnected=true;
				
				
				//--> To avoid redundancy when using this code for reconnection process.
				if(employeeTableColumnNames==null){
					// Initialize table column names and store them in an array for future use.
					initializeEmployeeTableColumnNames();
					initializeDeductionTableColumnNames();
					initializeEarningTableColumnNames();
					initializePayrollDateTableColumns();
					initializeDepartmentTableColumns();
					initializePhicAndPhicRateTableColumns();
					initializeSssTableColumns();
					initializeAsemcoBcciOccciDbpCfiTableColumnNames();
					initializePagibigTableColumnNames();
					initializeStPeterTableColumnNames();
					initializeUnionDuesTableColumnNames();
					initializeEmployerShareTableColumnNames();
					initializeEarningAutomateTableColumnNames();
					initializeEarningsContractualTableColumnNames();
					initializeDeductionsContractualTableColumnNames();
					initializeContractualSalaryRateTableColumnNames();
					initializeSignatureTableColumnNames();
					
					//---------------------------------------------------------
					
					initializeDepartmentNametData();
					initializePhicSalaryAndRateData();
					initializeSssData();
					initializeContractualSalaryRateData();
					initializeSignatureTabletData();
				}
				
			}catch(Exception exception){
				exception.printStackTrace();
//				JOptionPane.showMessageDialog(null,""+exception.getMessage(),null,JOptionPane.ERROR_MESSAGE);
				isConnected=false;
				
				//----------------
				closeConnection();
			
				//---------------
				if(!Utilities.getInstance().isLogin){
					JOptionPane.showMessageDialog(null,"Can't Connect to Database.",null,JOptionPane.ERROR_MESSAGE);

					System.exit(0);
				}

				
			}
			
			
		}	
		
		
	}
	
	/**
	 * Get the column index based from given database column name list and a given column name
	 * 		that is NOT camel case.
	 * @param tableColumnNameList
	 * @param columnNameReadable
	 * @return
	 */
	public int getColumnIndexBasedFromTableColumnNameListAndReadableColumnName(String[] tableColumnNameList, String columnNameReadable){
		Utilities util=Utilities.getInstance();
		System.out.println("\t Get column index based from tablo column name and readable column names: "+CLASS_NAME);
		for(int i=0;i<tableColumnNameList.length;i++){
			//--> For debugging purposes
			System.out.println("\t\tChosen Column name Readable: "+columnNameReadable
					+"\tTable Columnn Name NOT converted to Camel Case: "+tableColumnNameList[i]+CLASS_NAME);
			if(columnNameReadable.equals(util.convertCamelCaseColumnNamesToReadable(tableColumnNameList[i])))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Get the contractual salary rate per day based from the given number of years in service.
	 * @param numOfYearsInService
	 * @return
	 */
	public double getContractualSalaryRateBasedYearsOfService(int numOfYearsInService){
		for(int i:contractualSalaryRateDataList.keySet()){
			ContractualSalaryRateInfo info=contractualSalaryRateDataList.get(i);
			if(numOfYearsInService>=info.minimumRange && numOfYearsInService<info.maximumRange){
				return info.salaryPerDay;
			}
		}
		
		return 0;
	}
	
	
	
	
	/**
	 * Get the desired column names in PayrollViewPanel depending if contractual or regular.
	 * @param util
	 * @return
	 */
	public String[] getDesiredColumnNamesInPayrollViewPanel(Utilities util){
		
//		 IF REGULAR
//		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
//			String[] desiredColumnNamesList=new String[10]; // PayrollDate, EmployeeID,FamilyName,FirstName,Department,TotalEarnings,TotalDeductions,DeductionID
//			
//			desiredColumnNamesList[0]=tableNamePayrollDate+"."+payrollDateTableColumnNames[0]; // PayrollDate
//			desiredColumnNamesList[1]=tableNameEmployee+"."+employeeTableColumnNames[0];//EmployeeID
//			desiredColumnNamesList[2]=departmentTableColumnNames[1];//Department
//			desiredColumnNamesList[3]=employeeTableColumnNames[11];//MonthlyBasicSalary
//			
//			for(int  i=4;i<=5;i++){ // FamilyName,FirstName
//				desiredColumnNamesList[i]=employeeTableColumnNames[i-2];
//			}
//			
//			desiredColumnNamesList[6]=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
//				earningTableColumnNames[earningTableColumnNames.length-1]
//				:
//				earningsContractualColumnNames[earningsContractualColumnNames.length-1]
//			;//TotalEarnings
//			desiredColumnNamesList[7]=deductionTableColumnNames[deductionTableColumnNames.length-2];//TotalDeduction
//			desiredColumnNamesList[8]="("+desiredColumnNamesList[6]+"-"+desiredColumnNamesList[7]+") as NetPay";//NetPay
//			desiredColumnNamesList[9]=deductionTableColumnNames[0];//DeductionID
//			
//			return desiredColumnNamesList;
//		}
		
		
//		// If CONTRACTUAL
//		String[] desiredColumnNamesList=new String[9]; // PayrollDate, EmployeeID,FamilyName,FirstName,Department,TotalEarnings,TotalDeduction,DeductionID
//		
//		desiredColumnNamesList[0]=tableNamePayrollDate+"."+payrollDateTableColumnNames[0]; // PayrollDate
//		desiredColumnNamesList[1]=tableNameEmployee+"."+employeeTableColumnNames[0];//EmployeeID
//		desiredColumnNamesList[2]=departmentTableColumnNames[1];//Department
//		
//		for(int  i=3;i<=4;i++){ // FamilyName,FirstName
//			desiredColumnNamesList[i]=employeeTableColumnNames[i-1];
//		}
//		
//		desiredColumnNamesList[5]=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
//			earningTableColumnNames[earningTableColumnNames.length-1]
//			:
//			earningsContractualColumnNames[earningsContractualColumnNames.length-1]
//		;//TotalEarnings
//		desiredColumnNamesList[6]=deductionTableColumnNames[deductionTableColumnNames.length-2];//TotalDeduction
//		desiredColumnNamesList[7]="("+desiredColumnNamesList[5]+"-"+desiredColumnNamesList[6]+") as NetPay";//NetPay
//		desiredColumnNamesList[8]=deductionTableColumnNames[0];//DeductionID
		
//		return desiredColumnNamesList;
		
		
		//------------------------------------------------------------------
		
		String[] desiredColumnNamesList=new String[10]; // PayrollDate, EmployeeID,FamilyName,FirstName,Department,TotalEarnings,TotalDeductions,DeductionID
		
		desiredColumnNamesList[0]=tableNamePayrollDate+"."+payrollDateTableColumnNames[0]; // PayrollDate
		desiredColumnNamesList[1]=tableNameEmployee+"."+employeeTableColumnNames[0];//EmployeeID
		desiredColumnNamesList[2]=departmentTableColumnNames[1];//Department
		desiredColumnNamesList[3]=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
			employeeTableColumnNames[11]//MonthlyBasicSalary
			:
			employeeTableColumnNames[6]//WItHATM
		;
		
		for(int  i=4;i<=5;i++){ // FamilyName,FirstName
			desiredColumnNamesList[i]=employeeTableColumnNames[i-2];
		}
		
		desiredColumnNamesList[6]=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
			earningTableColumnNames[earningTableColumnNames.length-1]
			:
			earningsContractualColumnNames[earningsContractualColumnNames.length-1]
		;//TotalEarnings
		desiredColumnNamesList[7]=deductionTableColumnNames[deductionTableColumnNames.length-2];//TotalDeduction
		desiredColumnNamesList[8]="("+desiredColumnNamesList[6]+"-"+desiredColumnNamesList[7]+") as NetPay";//NetPay
		desiredColumnNamesList[9]=deductionTableColumnNames[0];//DeductionID
		
		return desiredColumnNamesList;
		
	}
	
	/**
	 * Get the desired total columns needed for Payroll View Panel.
	 * @param util
	 * @param table
	 * @return
	 */
	public String [] getDesiredTotalColumnsInPayrollViewPanel(Utilities util, ReusableTable table){
		
		// IF REGULAR
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
			String[]neededColumnsForTotalTable=new String[4]; // TotalSalary/ TotalEarnings/ TotalDeductions/TotalNetPay
			for(int i=0,j=3;i<neededColumnsForTotalTable.length;j++){
				String columnName=util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(j));
				
				if(j!=4 && j!=5){
					if(i==neededColumnsForTotalTable.length-1){ // OverallNetPay
						neededColumnsForTotalTable[i]=
								"SUM("+util.addSlantApostropheToString(util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(j-2)))+"-"+util.addSlantApostropheToString(util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(j-1)))
								+ ") as `Overall"+util.removeSpacesToBeConvertedToCamelCase(columnName)+"`";
					}
					else{
						neededColumnsForTotalTable[i]="SUM("+util.addSlantApostropheToString(columnName)+")as `Overall"+util.removeSpacesToBeConvertedToCamelCase(columnName)+"`";
					}
					i++;
				}
			}
			
			return neededColumnsForTotalTable;	
		}
		
		// IF CONTRACTUAL
		String[]neededColumnsForTotalTable=new String[3]; //  TotalEarnings/ TotalDeductions/TotalNetPay
		for(int i=0,j=5;i<neededColumnsForTotalTable.length;j++,i++){
			String columnName=util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(j));
			
			
			if(i==neededColumnsForTotalTable.length-1){ // OverallNetPay
				neededColumnsForTotalTable[i]=
						"SUM("+util.addSlantApostropheToString(util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(j-2)))+"-"+util.addSlantApostropheToString(util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(j-1)))
						+ ") as `Overall"+util.removeSpacesToBeConvertedToCamelCase(columnName)+"`";
			}
			else{
				neededColumnsForTotalTable[i]="SUM("+util.addSlantApostropheToString(columnName)+")as `Overall"+util.removeSpacesToBeConvertedToCamelCase(columnName)+"`";
			}
			
		}
		
		return neededColumnsForTotalTable;
	}
	/**
	 * Get the EC value from SSS Data List.
	 * @param ec
	 * @param er
	 * @return
	 */
	public double getECFromSSSDataList(double ee, double er){
		
		for(int key:sssDataList.keySet()){
			SssInfo sssInfo=sssDataList.get(key);
			
			if(sssInfo.ee==ee && sssInfo.er==er){
				return sssInfo.ecEr;
			}
		}
		
		return 0;
	}
	/**
	 * Get the departmetID key based from the given departmentName
	 * @param departmentName
	 * @return
	 */
	public int getKeyOfDepartmentData(String departmentName){
		//--> Get the equivalent departmentID.
		for(Integer index:departmentDataList.keySet()){
			if(departmentName
					.equals(departmentDataList.get(index))){
				return index;
			}
		}
		return -1;
	}
	
	/**
	 * Get the Mandatory Provident Fund EE value from SSS Data List.
	 * @param ec
	 * @param er
	 * @return
	 */
	public double getMandaProvFundEEFromSSSDataList(double ee, double er, double salary){
		
		for(int key:sssDataList.keySet()){
			SssInfo sssInfo=sssDataList.get(key);
			
			if(sssInfo.ee==ee && sssInfo.er==er && ( salary>=sssInfo.minimumRange && salary<=sssInfo.maximumRange)){
				return sssInfo.mandaProvFundEe;
			}
		}
		
		return 0;
	}
	
	
	/**
	 * Get the Mandatory Provident Fund ER value from SSS Data List.
	 * @param ec
	 * @param er
	 * @return
	 */
	public double getMandaProvFundERFromSSSDataList(double ee, double er , double salary){
		
		for(int key:sssDataList.keySet()){
			SssInfo sssInfo=sssDataList.get(key);
			
			if(sssInfo.ee==ee && sssInfo.er==er  && ( salary>=sssInfo.minimumRange && salary<=sssInfo.maximumRange) ){
				return sssInfo.mandaProvFundEr;
			}
		}
		
		return 0;
	}
	
	
	
	/**
	 * Get the number of years service based from a given employee ID.
	 * @param employeeID
	 * @return
	 */
	public int getNumberOfYearsServiceBasedFromGivenContractualEmployeeID(String employeeID){
		Utilities util = Utilities.getInstance();
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(employeeTableColumnNames[10], "Contractual")); // HiredAs
		conditionColumnAndValueList.add(new SelectConditionInfo(employeeTableColumnNames[0], employeeID)); // EmployeeID
		
		
		selectDataInDatabase(
			new String[]{tableNameEmployee}, 
			new String[]{employeeTableColumnNames[7]}, // Date Hired 
			conditionColumnAndValueList, 
			null,
			null, 
			Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND
		);
		
		try {
			resultSet.absolute(1);
			String completeYyyyMmDdDate=resultSet.getObject(1).toString();
			String yearHired=util.getYearFromDateFormatYyyyMmDd(completeYyyyMmDdDate);
			Calendar now = Calendar.getInstance();   // Gets the current date and time
			int year = now.get(Calendar.YEAR); 
			
			return year-Integer.parseInt(yearHired);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	/**
	 * Get the payroll date period start value in database based from given payrollDate
	 * @param payrollDate
	 * @return
	 */
	public String getPayrollDateStartBasedFromPayrollDate(String payrollDate){
		String payrollDateStarts="";
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(payrollDateTableColumnNames[0], payrollDate));
		selectDataInDatabase(
				new String[]{tableNamePayrollDate},
				new String[]{payrollDateTableColumnNames[1]},
				conditionColumnAndValueList,
				null,
				null, Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND);
		try {
			resultSet.absolute(1);
			payrollDateStarts=resultSet.getObject(1).toString();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return payrollDateStarts;
	}
	
	/**
	 * Get the system version.
	 * @return
	 */
	public String getSystemVersion(){
		selectDataInDatabase(
				new String[]{tableNameSystemStatus}, 
				null, 
				null, 
				null,
				null,
				Constant.SELECT_ALL);
		
		try {
			resultSet.beforeFirst();
			
			resultSet.absolute(1); // get the row
			System.out.println("\t SYSTEM VERSION FROM DATABASE: "+resultSet.getObject(2).toString()+CLASS_NAME);
			return resultSet.getObject(2).toString(); // get the data from column
			
			
			
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(""+e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(
					e.getMessage(), JOptionPane.ERROR_MESSAGE
			);
			
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Get signature image from signatureTable or of the person that prepared the payroll.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public ImageIcon getSignatureImageFromDatabase(int scaleToThisHeight) throws SQLException, IOException{
		
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(
			new SelectConditionInfo(
				signatureTableColumnNames[0],
				(Utilities.getInstance().payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?1:2
			)
		);
		
		selectDataInDatabase(
			new String[]{tableNameSignatureTable},
			new String[]{signatureTableColumnNames[signatureTableColumnNames.length-1]}, // columnaname: SignatureOfPerson
			conditionColumnAndValueList ,
			null,
			null,
			Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND
		);
		
		
		resultSet.absolute(1);
		
		
		if(resultSet.getObject(1)==null){
			return null;
		}
		InputStream is = resultSet.getBinaryStream(1);
		BufferedImage bufferedImg= ImageIO.read(is);
		bufferedImg=Utilities.getInstance().resizeImage(
				bufferedImg,
				Utilities.getInstance().calculateWidthBasedFromGivenMinHeightForScalingImage(
						bufferedImg.getWidth(),
						bufferedImg.getHeight(),
						scaleToThisHeight
				),
				scaleToThisHeight
		);
		Image image = bufferedImg;
		ImageIcon imageIcon= new ImageIcon(image);
		
		
		return imageIcon;
	}
	/**
	 * Get all department names from database and store to a variable for future use.
	 */
	public void initializeDepartmentNametData(){
		selectDataInDatabase(
				new String[]{tableNameDepartment}, 
				null, 
				null, 
				null,
				null,
				Constant.SELECT_ALL);
		try {
			resultSet.last();
			
			int totalNumOfRows=resultSet.getRow();
			resultSet.beforeFirst();
			
			departmentDataList=new HashMap<Integer,String>();
			for(int i=0;i<totalNumOfRows;i++){
				//--> Put the resulset cursor on the desired row.
				resultSet.absolute(i+1);
				
				//--> Put the resulset cursor on Department column. Why 1? since the resultset only return 1 column
				//			based from the executed select statement.
				departmentDataList.put((Integer) resultSet.getObject(1),resultSet.getObject(2).toString());
			}
			
			//--> For debugging purposes.
			System.out.println("\t Department Names stored DATA : "+CLASS_NAME);
			for(int index:departmentDataList.keySet()){
				System.out.println("\t\t"+departmentDataList.get(index)+CLASS_NAME);
			}
			
			
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(""+e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(
					e.getMessage(), JOptionPane.ERROR_MESSAGE
			);
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Store necessary data on PHIC salary range and rate from database
	 * 		for future use.
	 */
	public void initializePhicSalaryAndRateData(){
		
		try {
			//--> Store PHIC Salary Range
			selectDataInDatabase(
					new String[]{tableNamePhic}, 
					null, 
					null, 
					null,
					null,
					Constant.SELECT_ALL);
			
			resultSet.last();
			
			int totalNumOfRows=resultSet.getRow();
			resultSet.beforeFirst();
			
			phicSalaryRangeDataList=new HashMap<Integer,PhicInfo>();
			for(int i=0;i<totalNumOfRows;i++){
				//--> Put the resulset cursor on the desired row.
				resultSet.absolute(i+1);
				
				phicSalaryRangeDataList.put(
						(Integer) resultSet.getObject(1),
						new PhicInfo(
								(Integer)resultSet.getObject(1),
								(Double)resultSet.getObject(2),
								(Double)resultSet.getObject(3), 
								(String)resultSet.getObject(4)
						)
				);
				
			}
			
			//--> For debugging purposes.
			System.out.println("\t\tPHIC Salary Range stored DATA : "+CLASS_NAME);
			for(int index:phicSalaryRangeDataList.keySet()){
				PhicInfo phicInfo=phicSalaryRangeDataList.get(index);
				System.out.println("\t\t\tID: "+phicInfo.phicID
						+"\tMin: "+phicInfo.monthlyBasicSalaryMin
						+"\tMax: "+phicInfo.monthlyBasicSalaryMax
						+"\tStatus: "+phicInfo.status+CLASS_NAME);
			}
			
			
			//--> Store PHIC Rate
			selectDataInDatabase(
					new String[]{tableNamePhicRate}, 
					null, 
					null, 
					null,
					null,
					Constant.SELECT_ALL);
			
			resultSet.last();
			
			totalNumOfRows=resultSet.getRow();
			resultSet.beforeFirst();
			
			for(int i=0;i<totalNumOfRows;i++){
				//--> Put the resulset cursor on the desired row.
				resultSet.absolute(i+1);
				
				//--> Put the resulset cursor on Department column. Why 1? since the resultset only return 1 column
				//			based from the executed select statement.
				phicRate=resultSet.getObject(2).toString();
			}
			
			//--> For debugging purposes.
			System.out.println("\t\tPHIC Rate stored DATA : "+phicRate+CLASS_NAME);
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Store necessary data on sss data from database
	 * 		for future use.
	 */
	public void initializeSssData(){
		
		try {
			//--> Store SSS
			selectDataInDatabase(
					new String[]{tableNameSss}, 
					null, 
					null, 
					null,
					null,
					Constant.SELECT_ALL);
			
			resultSet.last();
			
			int totalNumOfRows=resultSet.getRow();
			resultSet.beforeFirst();
			
			sssDataList=new HashMap<Integer,SssInfo>();
			for(int i=0;i<totalNumOfRows;i++){
				//--> Put the resulset cursor on the desired row.
				resultSet.absolute(i+1);
				
				sssDataList.put(
						(Integer) resultSet.getObject(1),
						new SssInfo(
								(Integer) resultSet.getObject(1),
								(Double) resultSet.getObject(2),
								(Double) resultSet.getObject(3),
								(Double) resultSet.getObject(4),
								(Double) resultSet.getObject(5),
								(Double) resultSet.getObject(6),
								(Double) resultSet.getObject(7),
								(Double) resultSet.getObject(8),
								(Double) resultSet.getObject(9)
							) 
				);
				
			}
			
			//--> For debugging purposes.
			System.out.println("\t\tSSS stored DATA : "+CLASS_NAME);
			for(int index:sssDataList.keySet()){
				SssInfo sssInfo=sssDataList.get(index);
				System.out.println("\t\t\tID: "+sssInfo.sssID
						+"\tMin: "+sssInfo.minimumRange
						+"\tMax: "+sssInfo.maximumRange
						+"\tMonthly Salary Credit: "+sssInfo.monthlySalaryCredit
						+"\tEe: "+sssInfo.ee
						+"\tEr: "+sssInfo.er
						+"\tEcEr: "+sssInfo.ecEr
						+"\tMandaProvFundEE: "+sssInfo.mandaProvFundEe
						+"\tMandaProvFundER: "+sssInfo.mandaProvFundEr+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Store necessary data on contractual salary rate data from database
	 * 		for future use.
	 */
	public void initializeContractualSalaryRateData(){
		
		try {
			//--> Store Contractual Salary rate
			selectDataInDatabase(
					new String[]{tableNameContractualSalaryRate}, 
					null, 
					null, 
					null,
					null,
					Constant.SELECT_ALL);
			
			resultSet.last();
			
			int totalNumOfRows=resultSet.getRow();
			resultSet.beforeFirst();
			
			contractualSalaryRateDataList=new HashMap<Integer,ContractualSalaryRateInfo>();
			for(int i=0;i<totalNumOfRows;i++){
				//--> Put the resulset cursor on the desired row.
				resultSet.absolute(i+1);
				
				contractualSalaryRateDataList.put(
						(Integer) resultSet.getObject(1),
						new ContractualSalaryRateInfo(
								(Double) resultSet.getObject(2),
								(Double) resultSet.getObject(3),
								(Double) resultSet.getObject(4)
							) 
				);
				
			}
			
			//--> For debugging purposes.
			System.out.println("\t\tContractual Salary Rate stored DATA : "+CLASS_NAME);
			for(int index:contractualSalaryRateDataList.keySet()){
				ContractualSalaryRateInfo info=contractualSalaryRateDataList.get(index);
				System.out.println("\t\t\tID: "+index
						+"\tMin: "+info.minimumRange
						+"\tMax: "+info.maximumRange
						+"\tSalary Per Day: "+info.salaryPerDay+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	

	/**
	 * Get all department names from database and store to a variable for future use.
	 */
	public void initializeSignatureTabletData(){
		Utilities util = Utilities.getInstance();
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(
			new SelectConditionInfo(
				signatureTableColumnNames[0],
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?1:2
			)
		);
		
		selectDataInDatabase(
				new String[]{tableNameSignatureTable}, 
				null, 
				conditionColumnAndValueList, 
				null,
				null,
				Constant.SELECT_ALL_WITH_CONDITION_AND);
		try {
			resultSet.last();
			
			int totalNumOfRows=resultSet.getRow();
			resultSet.beforeFirst();
			
			signatureTableDataList=new HashMap<String,String>();
			for(int i=0;i<totalNumOfRows;i++){
				//--> Put the resulset cursor on the desired row.
				resultSet.absolute(i+1);
				
				for(int j=0;j<metaData.getColumnCount()-1;j++){
					signatureTableDataList.put(signatureTableColumnNames[j],resultSet.getObject(j+1).toString());
				}
			}
			
			//--> For debugging purposes.
			System.out.println("\t Signature Data stored DATA : "+CLASS_NAME);
			for(String index:signatureTableDataList.keySet()){
				System.out.println("\t\tIndex[Column]-> "+index+":"
						+"\t\t"+signatureTableDataList.get(index)+CLASS_NAME);
			}
//			
//			
//		
//			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(""+e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(
					e.getMessage(), JOptionPane.ERROR_MESSAGE
			);
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * CHeck if payroll date is locked
	 * @param payrollDate
	 * @return
	 */
	public boolean isPayrollDateLocked(String[] payrollDateList){
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		
		System.out.println("\t Stored Payroll Date List: "+CLASS_NAME);
		for(String payrollDate:payrollDateList){
			System.out.println("\t\tPayroll Date: "+payrollDate+CLASS_NAME);
			
			conditionColumnAndValueList.add(new SelectConditionInfo(payrollDateTableColumnNames[0], payrollDate));
		}
		
		selectDataInDatabase(
			new String[]{tableNamePayrollDate},
			new String[]{payrollDateTableColumnNames[2]},
			conditionColumnAndValueList,
			null,
			null,
			Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR
		);
		
		
		
		try {
			resultSet.last();
			
			int totalNumOfRows=resultSet.getRow();
			resultSet.beforeFirst();
			
			for(int i=0;i<totalNumOfRows;i++){
				//--> Put the resulset cursor on the desired row.
				resultSet.absolute(i+1);
				
				System.out.println("\t\tPayroll Date: "+payrollDateList[i]
						+"\tLockedStatus: "+resultSet.getObject(1).toString()
						+"\t TotalNumOfRows: "+totalNumOfRows+CLASS_NAME);
				
				if(resultSet.getObject(1).toString().equals(Constant.LOCKED_STATUS)){
					return true;
				}
				
								
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	
	/**
	 * Checks if the user name and password matched!
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean isUserAccountInputMatched(String userName, String password){
		userName=userName.replaceAll(" ","");
		
		System.out.println("\tInputted User Name:"+userName
				//+"\t Password: ?"+password
				+CLASS_NAME);
		try {
			resultSet.last();
			int totalNumOfRows=resultSet.getRow();
			resultSet.beforeFirst();
			
			for(int i=0;i<totalNumOfRows;i++){
				resultSet.absolute(i+1);
				System.out.println("\t\tTable Uname: "+resultSet.getObject(2)
						//+"\t Password: "+resultSet.getObject(3)
						+CLASS_NAME);
				
					if(resultSet.getObject(2).equals(userName) && 
							resultSet.getObject(3).equals(password)){
						
						//--> Save authorization level.
						Utilities.getInstance().authorizationLevel=(int) resultSet.getObject(4);
						return true;
					}
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		return false;
	}
	

	private void l_______________________________________________________________________________________l(){}
	
	/**
	 * Execute delete process in database.
	 * @param tableName
	 * @param columnName
	 * @param value
	 */
	public void deleteDataInDatabase(String tableName, String columnName,String[] values){
		String sql = processDeleteQuery(tableName, columnName, values);
		isDelete=false;
		try {
			connection.setAutoCommit(false);
			
			preparedStatement= connection.prepareStatement(sql);
			
			int rowsDeleted = preparedStatement.executeUpdate();
			if (rowsDeleted > 0) {
			    System.out.println("\tA user/s was deleted successfully!"+CLASS_NAME);
			    
			    
//			    MainFrame.getInstance().showOptionPaneMessageDialog(
//			    		(values.length==1)?
//			    				"A data entry was deleted successfully!":
//			    				"Data Entries were deleted successfully! Number of data entries deleted: "+values.length, 
//			    		JOptionPane.INFORMATION_MESSAGE);
			    
			    isDelete=true;
			    connection.commit();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					null, JOptionPane.ERROR_MESSAGE);
			
			isDelete=false;
			
			try {
				connection.rollback();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			try {
				preparedStatement.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
	
				  MainFrame.getInstance().showOptionPaneMessageDialog(e1.getMessage(), 
				    		JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	/**
	 * Insert query to database.
	 */
	public void insertDataInDatabase(String tableName,String[] columnNameList,Object[][]values){
		
		//--> SAmple Query ONCE
//		INSERT INTO bileco_db.employee 
//			(EMPLOYEE_ID, FAMILY_NAME, FIRST_NAME, MIDDLE_NAME, DESIGNATION, DEPARTMENT, SALARY) 
//		"VALUES ('2018-PVS', 'Sacedor', 'Paul Alvin', 'Vaporoso', 'IT Staff', 'OGM', '0');
		
		//--> Sample Quey EXECUTE BATCH
//		INSERT INTO TableName values (?,?,?,?);
		
		Utilities util = Utilities.getInstance();
		isInsert=false;
		String sqlQuery=processInsertQuery(tableName,columnNameList);
		
		System.out.println("\tInsert SQL Query: "+sqlQuery+CLASS_NAME);
		
		

		try {
			//--> Set Autocommit to false
			connection.setAutoCommit(false);
			
			preparedStatement=connection.prepareStatement(sqlQuery);
			for(int i=0;i<values.length;i++){
				
				for(int j=0,parameterIndex=1;j<values[i].length;j++,parameterIndex++){
					
					Object value=values[i][j];
					
					if(util.isStringANumber(value.toString())){

						if(value.getClass().toString().equals(util.compareClassObject(Constant.CLASS_OBJECT_DOUBLE))){
							preparedStatement.setDouble(parameterIndex, Double.parseDouble(value.toString()));
						}
						else{
							preparedStatement.setInt(parameterIndex, Integer.parseInt(value.toString()));
						}
					}
					else if(util.isStringADateYyyyMmDd(value.toString())){
						preparedStatement.setDate(parameterIndex, util.convertStringToSqlDate(value.toString()));
					}
					else{
						preparedStatement.setString(parameterIndex, value.toString());
					}
					
					
				}
				
				preparedStatement.addBatch();
			}

			int[] rowsInserted = preparedStatement.executeBatch();
			if (rowsInserted.length > 0) {
			    System.out.println("\tA new data was inserted successfully!"+CLASS_NAME); 
			    isInsert=true;
			    
			    //--> Set commit
			    connection.commit();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			isInsert=false;
			
			//--> Set Rollback
			try {
				connection.rollback();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			 MainFrame.getInstance().showOptionPaneMessageDialog(
					 e.getMessage(),
					 JOptionPane.ERROR_MESSAGE);
			try {
				preparedStatement.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				 MainFrame.getInstance().showOptionPaneMessageDialog(
						 e1.getMessage(),
						 JOptionPane.ERROR_MESSAGE);
			}
			e.printStackTrace();
		}
		
	}
	
		/**
		 * Retrieve data from database
		 * Steps:
		 * 		1. Create query that you want to execute in order 
		 * 			to retrieve data from database.
		 * 		2. Query the database.
		 * 		3. Process the query results.
		 * @param tableNameList 	<-> Needed tables
		 * @param columnNameList 	<-> Needed columns
		 * @param conditionColumnAndValueList	<-> Needed column and its value for condition.
		 * @param joinColumnCompareList 
		 * @param mode
		 */
		public void selectDataInDatabase(String[] tableNameList,String[]columnNameList, 
				ArrayList<SelectConditionInfo>conditionColumnAndValueList,
				String[] joinColumnCompareList, OrderByInfo orderInfo, int mode) {
			
			String sqlQuery=processSelectQuery(
					tableNameList, columnNameList,
					conditionColumnAndValueList,joinColumnCompareList, orderInfo,mode
			);
			
			
			//--> Query database
			try {
				
				//--> create Statement for querying database
				statement = connection.createStatement();
				
				//--> Process query results
				resultSet = statement.executeQuery(sqlQuery);
				metaData=resultSet.getMetaData();
				
	
				//--> Put the cursor to the first element of the result set.
				resultSet.beforeFirst();
				
					
			}catch(Exception exception){
				exception.printStackTrace();
				Logs.getInstance().printlnLogs(exception.getMessage());
				JOptionPane.showMessageDialog(null,""+exception.getMessage(),null,JOptionPane.ERROR_MESSAGE);
				isConnected=false;
			
			}
			
			System.out.println();
		}

	/**
	 *  Update data in database.
	 * HashMap<String, String> 
	 * 		<-->  HashMap< Key[ColumnName], Changed value always string bsag integer/double >
	 * @param tableName
	 * @param changesToBeUpdated
	 * @param tablePrimaryIDColumnName
	 * @param neededIDValue
	 * @param isMultiple
	 * @param multipleUpdateList
	 */
	public void updateDataInDatabase(String tableName, HashMap<String, Object>changesToBeUpdated,
			String tablePrimaryIDColumnName, String neededIDValue,
			boolean isMultiple, ArrayList<MultipleUpdateDatabaseModel>multipleUpdateList){
		
		//--> Sample Query Format ONCE:
//			UPDATE employee 
//			SET `Title`='Mrs' 
//			WHERE `EmployeeID`='1984-07-NUA-0009'																					Database.java
				
		
		//--> Sample Query Format EXECUTE BATCH:
//			UPDATE deductions 
//			SET `Pag-ibigLoan`= ?, `Medicare`= ?, `A/R`= ?, `EMLOAN`= ?, `CFI`= ?, `W-Tax`= ?, `OCCCI`= ?, `M-Aid`= ?, `Un-Dues`= ?, `DBP`= ?, `BEMCO`= ?, `Pag-ibigCont`= ?, `St.Peter`= ?, `BCCI`= ?, `LBP`= ?, `ASEMCO`= ?, `SSSCont`= ?, `TotalDeduction`= ?, `SSSLoan`= ? 
//					WHERE `DeductionID`= ?;																					Database.java
				
		
		String sqlQuery="";
		isUpdate=false;	
		
		try {
			connection.setAutoCommit(false);
			
			//--> When updating the database is only once.
			if(!isMultiple){
				updateOnlyOnce(sqlQuery, tableName, changesToBeUpdated, tablePrimaryIDColumnName, neededIDValue);
			}
			//--> When updating multiple ROWS and COLUMNS. WHen updating the database more than once.
			else{
				updateMultipleTimes(sqlQuery, tableName, tablePrimaryIDColumnName, multipleUpdateList);
			}	
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					null, JOptionPane.ERROR_MESSAGE);
			
			isUpdate=false;
			
			//--> Set Rollback
			try {
				connection.rollback();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			try {
				preparedStatement.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), 
						null, JOptionPane.ERROR_MESSAGE);
				isUpdate=false;
			}
			e.printStackTrace();
		}
	}
	private void l___________________________________________________________________________________l(){}
	
	
	
	/**
	 * Process the query used when deleting
	 * @param tableName
	 * @param columnName
	 * @param values
	 * @return
	 */
	private String processDeleteQuery(String tableName, String columnName,String[] values){
//		Query Structure: "DELETE FROM tableName WHERE columnNameID='value';";
		
		
		String sqlQuery="DELETE FROM "+tableName+" WHERE ";
		
		for(int i=0;i<values.length;i++){
			if(i==0){
				sqlQuery=sqlQuery+columnName+"='"+values[i]+"'";
			}
			else{
				sqlQuery=sqlQuery+" OR "+columnName+"='"+values[i]+"'";
			}
		}
		
		
		System.out.println("\tDelete from table query: "+sqlQuery+CLASS_NAME);
		return sqlQuery;
	}
	
	/**
	 * Process query when inserting data in database.
	 * @param tableName
	 * @param values
	 * @return
	 */
	private String processInsertQuery(String tableName,String[]columnNameList){
//		Query Structure: "INSERT INTO tableName(columnName_1,columnName_2,columnName_3) 
//							VALUES ('value_1[string]', 'value_2[string]', value_3[double]);"
		
		
		String sqlQuery= "INSERT INTO "+databaseName+"."+tableName+"(";
		

		// Set the columnb names
		for(int i=0;i<columnNameList.length;i++){ 
			sqlQuery=sqlQuery+"`"+columnNameList[i]+"`";
			
			
			// INsert comma except the last one. that is why columnNameList.length-1.
			if(i<columnNameList.length-1){
				sqlQuery=sqlQuery+",";
			}
		}
		// Set the values which in this case is we used ? since we will use execute batch
		sqlQuery=sqlQuery+") VALUES (";
		for(int i=0;i<columnNameList.length;i++){
			
			sqlQuery=sqlQuery+" ?";
			// INsert comma
			if(i<columnNameList.length-1){
				sqlQuery=sqlQuery+",";
			}
		}
		
		return sqlQuery+");";
	}
	
	/**
	 *  Process query for select data from database.
	 * @param tableName
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @param mode
	 * @return
	 */
	private String processSelectQuery(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,OrderByInfo orderInfo, int mode){
		
		if(mode==Constant.SELECT_ALL){
			return selectAll(tableNameList,orderInfo);
		}
		else if(mode==Constant.SELECT_ALL_WITH_CONDITION_AND){
			return selectAllWithConditionAND(tableNameList, conditionColumnAndValueList);
		}
		else if(mode==Constant.SELECT_ALL_WITH_CONDITION_OR){
			return selectAllWithConditionOR(tableNameList, conditionColumnAndValueList);
		}
		else if(mode==Constant.SELECT_BASED_FROM_COLUMN){
			return selectBasedFromColumns(tableNameList, columnNameList,orderInfo);
		}
		else if(mode==Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND){
			return selectBasedFromColumnsWithConditionAND(tableNameList, columnNameList, conditionColumnAndValueList,orderInfo);
		}
		else if(mode==Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR){
			return selectBasedFromColumnsWithConditionOR(tableNameList, columnNameList, conditionColumnAndValueList,orderInfo);
		}
		else if(mode==Constant.SELECT_DISTINCT){
			return selectDistinct(tableNameList, columnNameList);
		}
		else if(mode==Constant.SELECT_INNER_JOIN){
			return selectInnerJoin(tableNameList, columnNameList,joinColumnCompareList,orderInfo);
		}
		else if(mode==Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND){
			return selectInnerJoinWithConditionAND(tableNameList, columnNameList,
					conditionColumnAndValueList,joinColumnCompareList,orderInfo);
		}
		else if(mode==Constant.SELECT_INNER_JOIN_WITH_CONDITION_OR){
			return selectInnerJoinWithConditionOR(tableNameList, columnNameList,
					conditionColumnAndValueList,joinColumnCompareList,orderInfo);
		}
		else if(mode==Constant.SELECT_SUM_WITH_CONDITION){
			return selectSumWithCondition(tableNameList, columnNameList, conditionColumnAndValueList);
		}
		else if(mode==Constant.SELECT_COUNT_WITH_CONDITION){
			return selectCountWIthCondition(tableNameList, columnNameList, conditionColumnAndValueList);
		}
		else if(mode==Constant.SELECT_UNION_BASED_FROM_COLUMN){
			return selectUnionBasedFromColumn(tableNameList, columnNameList);
		}
		else if(mode==Constant.SELECT_SPECIAL_UNION_PAYROLL_DATA){
			return selectSPECIALAllUnionPayrollData();
		}
		else if(mode==Constant.SELECT_SPECIAL_UNION_PAYROLL_DATA_WITH_CONDITION){
			return selectSPECIALAllUnionWithConditionPayrollData(conditionColumnAndValueList);
		}
		else if(mode==Constant.SELECT_SPECIAL_EMPLOYEE_PAYROLL_SUMMARY_PDF){
			return selectSPECIALEmployeeInDepartmentPayrollSummaryData(conditionColumnAndValueList);
		}
		else if(mode==Constant.SELECT_SPECIAL_PER_CONTRACTUAL_EMPLOYEE_SUMMARY){
			return selectSPECIALPerContractualEmployeePayrollSummaryData(conditionColumnAndValueList);
		}
		else if(mode==Constant.SELECT_SPECIAL_AOBDCSL_30th_PAYROLL_DATA){
			return selectSPECIAL30thABODCSWL(tableNameList, columnNameList, conditionColumnAndValueList, joinColumnCompareList, orderInfo);
		}
		else if(mode==Constant.SELECT_SPECIAL_HDMF_MEDICARE_30th_PAYROLL_DATA){
			return selectSPECIAL30thHDMFMedicareD(tableNameList, columnNameList, conditionColumnAndValueList, joinColumnCompareList, orderInfo);
		}
		
		return null;
	}
	
	
	/**
	 * Process the query used for updating a data in database.
	 * @param columnNames
	 * @param values
	 * @param columnName
	 * @param value
	 * @return
	 */
	private String processUpdateQuery(String tableName, 
			HashMap<String, Object>changesToBeUpdated,
			String tablePrimaryIDColumnName, String neededIDValue,boolean isMultiple){
		
//		Query Structure: UPDATE tableName SET columnName_1='value', columnName_2='value' WHERE `columnNameID`='value';
		
		String sqlQuery="UPDATE "+tableName+" SET ";
		int i=0;
		for(Object key:changesToBeUpdated.keySet()){
			sqlQuery=sqlQuery+"`"+key.toString()+"`"+"=";
			//--> Changes when the update is multiple or not.
			if(!isMultiple){
				sqlQuery=sqlQuery+"'"+changesToBeUpdated.get(key)+"'";
			}
			else{
				sqlQuery=sqlQuery+" ?";
			}
			
			
			//--> Condition when to put comma
			if(i<changesToBeUpdated.size()-1){
				sqlQuery=sqlQuery+", ";
			}
			
			i++;
		}
		
		sqlQuery=sqlQuery+" WHERE `"+tablePrimaryIDColumnName+"`=";
		if(!isMultiple){
			sqlQuery=sqlQuery+"'"+neededIDValue+"'";
		}
		else{
			sqlQuery=sqlQuery+" ?";
		}
		
		System.out.println("\tUpdate Query: "+sqlQuery+CLASS_NAME);
		return sqlQuery;
	}
	
	private void l__________________________________________________________________________________l(){}
	
	/**
	 * Create a query where the mode is only select all.
	 * @param tableNameList
	 * @return
	 */
	private String selectAll(String[] tableNameList,OrderByInfo orderInfo){
//		Query Structure: SELECT * FROM tableName;
		
		String query="SELECT * from "+tableNameList[0];
		
		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ;";
		}
		
		System.out.println("\tSelect all query: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 *  Create a query where the mode is select all with condition using AND.
	 * @param tableNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectAllWithConditionAND(String[] tableNameList,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList){
//		Query Structure: SELECT * from tableName WHERE `columnName`='value';
		
		//--> Process the condition query
		String conditionQuery="WHERE ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//--> Adds AND when the columns and values for condition are more than one.
			if(i>0){
				conditionQuery=conditionQuery+" AND ";
			}
			conditionQuery=conditionQuery+"`"+conditionInfo.getColumnName()
					+"`='"+conditionInfo.getValue().toString()+"'";		 
			
		}
			
		String query="SELECT * from "+tableNameList[0]+" "+conditionQuery;
		System.out.println("\tSelect all with condition Query AND: "+query+CLASS_NAME);
			
		return query;
	}
	/**
	 *  Create a query where the mode is select all with condition using OR.
	 * @param tableNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectAllWithConditionOR(String[] tableNameList,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList){
//		Query Structure: SELECT * from tableName WHERE `columnName`='value';
		
		//--> Process the condition query
		String conditionQuery="WHERE ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//--> Adds AND when the columns and values for condition are more than one.
			if(i>0){
				conditionQuery=conditionQuery+" OR ";
			}
			conditionQuery=conditionQuery+"`"+conditionInfo.getColumnName()
					+"`='"+conditionInfo.getValue().toString()+"'";		 
			
		}
			
		String query="SELECT * from "+tableNameList[0]+" "+conditionQuery;
		System.out.println("\tSelect all with condition Query OR: "+query+CLASS_NAME);
			
		return query;
	}
	
	/**
	 * Create a query where mode is select based from given columns.
	 * @param tableNameList
	 * @param columnNameList
	 * @return
	 */
	private String selectBasedFromColumns(String[] tableNameList,String[]columnNameList,OrderByInfo orderInfo){
//		Query Structure: SELECT `columnName_1`,`columnName_2`,`columnName_3` FROM tableName;
				
		String query="SELECT ";
		for(int i=0;i<columnNameList.length;i++){
			query=query+columnNameList[i];
			
			if(i<columnNameList.length-1){
				query=query+",";
			}
		}
		query=query+" FROM "+tableNameList[0];
		
		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ;";
		}
				
		System.out.println("\tWIth Specific Columns SELECT Query: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create a query where mode is select based from columns and condition.
	 * @param tableNameList
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectBasedFromColumnsWithConditionAND(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList, OrderByInfo orderInfo){
//		Query Structure: SELECT `columnName_1`,`columnName_2`,`columnName_3` FROM tableName WHERE `columnName`='value';																					Database.java

		String query="SELECT ";
		
		//--> Add the needed columns in query
		for(int i=0;i<columnNameList.length;i++){
			query=query+"`"+columnNameList[i]+"`";
			
			if(i<columnNameList.length-1){
				query=query+",";
			}
		}
		
		//--> Process the condition query
		String conditionQuery="WHERE ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//--> Adds AND when the columns and values for condition are more than one.
			if(i>0){
				conditionQuery=conditionQuery+" AND ";
			}
			conditionQuery=conditionQuery+"`"+conditionInfo.getColumnName()
					+"`='"+conditionInfo.getValue().toString()+"'";		 
			
		}
	
		query=query+" FROM "+tableNameList[0]+" "+conditionQuery;
		
		
		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ;";
		}
				
				
		System.out.println("\tWIth Specific Columns AND COndition SELECT Query: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create a query where mode is select based from columns with condition OR.
	 * @param tableNameList
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectBasedFromColumnsWithConditionOR(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList, OrderByInfo orderInfo){
//		Query Structure: SELECT `columnName_1`,`columnName_2`,`columnName_3` FROM tableName WHERE `columnName`='value';																					Database.java

		String query="SELECT ";
		
		//--> Add the needed columns in query
		for(int i=0;i<columnNameList.length;i++){
			query=query+columnNameList[i];
			
			if(i<columnNameList.length-1){
				query=query+",";
			}
		}
		
		//--> Process the condition query
		String conditionQuery=(conditionColumnAndValueList.size()>0)?
				"WHERE ":" ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//--> Adds AND when the columns and values for condition are more than one.
			if(i>0){
				conditionQuery=conditionQuery+" OR";
			}
			conditionQuery=conditionQuery+"`"+conditionInfo.getColumnName()
					+"`='"+conditionInfo.getValue().toString()+"'";		 
			
		}
	
		query=query+" FROM "+tableNameList[0]+" "+conditionQuery;
		
		
		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ;";
		}
		
		System.out.println("\tWIth Specific Columns with COndition OR SELECT Query: "+query+CLASS_NAME);
		return query;
	}
	/**
	 * Create a query where mode is select distinct.
	 * @param columnNameList
	 * @param tableName
	 * @return
	 */
	private String selectDistinct(String[] tableNameList,String[]columnNameList){
//		Query Structure: SELECT DISTINCT columnName FROM tableName ORDER BY columnName DESC
		
		String query="SELECT DISTINCT ";
		for(int i=0;i<columnNameList.length;i++){
			query=query+""+columnNameList[i];
			
			if(i<columnNameList.length-1){
				query=query+",";
			}
		}
		query=query+" FROM "+tableNameList[0]+" ORDER BY "+columnNameList[0]+" DESC";

		System.out.println("\tSELECT DISTINCT query: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create a query where mode is select inner join only.
	 * @param tableNameList
	 * @param columnNameList
	 * @param joinColumnCompareList
	 * @return
	 */
	private String selectInnerJoin(String[] tableNameList,String[]columnNameList,
			String[] joinColumnCompareList,OrderByInfo orderInfo){
//		Query Structure: SELECT `tableName_1`.`columnName_1`,`tableName_2`.`columnName_2`,`tableName_1`.`columnName_3` 
//							FROM tableName_1 
//							INNER JOIN tableName_2 ON tableName_1.columnNameID = tableName_2.columnNameID;																					

		// Content of joinColumnCompareLis--> tableName_1.columnNameID = tableName_2.columnNameID
		
		String query="SELECT ";
		
		for(int i=0;i<columnNameList.length;i++){
			query+=""+columnNameList[i];
			
			if(i<columnNameList.length-1){
				query+=",";
			}
		}
		
//		query+=" FROM "+tableNameList[0]+" INNER JOIN "+tableNameList[1]
//				+" ON "+tableNameList[0]+"."+joinColumnCompareList[0]+" = "+tableNameList[1]+"."+joinColumnCompareList[0];
		
		
		//--> Create query for inner join.
		query+=" FROM "+tableNameList[0]+" ";
		for(int i=0;i<joinColumnCompareList.length;i++){
			query+=" INNER JOIN "+tableNameList[i+1]+" ON "+joinColumnCompareList[i];
		}
		
		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ;";
		}
				
		System.out.println("\tSELECT Inner Join only query: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create a query when the mode is select inner join with condition AND.
	 * @param tableNameList
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectInnerJoinWithConditionAND(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,OrderByInfo orderInfo){
//		 Query Structure: SELECT `tableName_1`.`columnName_1`, `tableName_2`.`columnName_1` 
//							FROM tableName_1 INNER JOIN tableName_2 
//							ON tableName_1.columnNameID = tableName_2.columnNameID 
//							WHERE `tableName`.`columnName_1`='value';																					Database.java
		
		// Content of joinColumnCompareLis--> tableName_1.columnNameID = tableName_2.columnNameID
		
		
		String query="SELECT ";
		
		//--> Add the needed columns in query
		for(int i=0;i<columnNameList.length;i++){
			query+=""+columnNameList[i];
			
			if(i<columnNameList.length-1){
				query+=",";
			}
		}
		
		//--> Process the inner join.
//		query+=" FROM "+tableNameList[0]+" INNER JOIN "+tableNameList[1]
//				+" ON "+tableNameList[0]+".EmployeeID = "+tableNameList[1]+".EmployeeID";
		
		//-->Process the inner join query.
		query+=" FROM "+tableNameList[0]+" ";
		for(int i=0;i<joinColumnCompareList.length;i++){
			query+=" INNER JOIN "+tableNameList[i+1]+" ON "+joinColumnCompareList[i];
		}
		
		//--> Process the condition query
		String conditionQuery="";
		//--> This makes sure that the conditionList is not null
		if(conditionColumnAndValueList!=null){
			//--> To make sure that you add where when the size of condition list is greater than zero.
			if(conditionColumnAndValueList.size()>0){
				conditionQuery=" WHERE ";
			}
			for(int i=0;i<conditionColumnAndValueList.size();i++){
				SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
				
				//--> Adds AND when the columns and values for condition are more than one.
				if(i>0){
					conditionQuery=conditionQuery+" AND ";
				}
				conditionQuery=conditionQuery+conditionInfo.getColumnName()
						+conditionInfo.getSign()+"'"+conditionInfo.getValue().toString()+"'";		 
				
			}
		}
		query+=conditionQuery;
		
		
		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ;";
		}
		
		
		System.out.println("\tSELECT Inner Join with condition query AND: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create a query when the mode is select inner join with condition OR.
	 * @param tableNameList
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectInnerJoinWithConditionOR(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,
			OrderByInfo orderInfo){
//		 Query Structure: SELECT `tableName_1`.`columnName_1`, `tableName_2`.`columnName_1` 
//							FROM tableName_1 INNER JOIN tableName_2 
//							ON tableName_1.columnNameID = tableName_2.columnNameID 
//							WHERE `tableName`.`columnName_1`='value';																					Database.java
		
		// Content of joinColumnCompareLis--> tableName_1.columnNameID = tableName_2.columnNameID
		
		
		String query="SELECT ";
		
		//--> Add the needed columns in query
		for(int i=0;i<columnNameList.length;i++){
			query+=""+columnNameList[i];
			
			if(i<columnNameList.length-1){
				query+=",";
			}
		}
		
		//--> Process the inner join.
//		query+=" FROM "+tableNameList[0]+" INNER JOIN "+tableNameList[1]
//				+" ON "+tableNameList[0]+".EmployeeID = "+tableNameList[1]+".EmployeeID";
		
		//-->Process the inner join query.
		query+=" FROM "+tableNameList[0]+" ";
		for(int i=0;i<joinColumnCompareList.length;i++){
			query+=" INNER JOIN "+tableNameList[i+1]+" ON "+joinColumnCompareList[i];
		}
		
		//--> Process the condition query
		String conditionQuery="";
		//--> This makes sure that the conditionList is not null
		if(conditionColumnAndValueList!=null){
			//--> To make sure that you add where when the size of condition list is greater than zero.
			if(conditionColumnAndValueList.size()>0){
				conditionQuery=" WHERE ";
			}
			for(int i=0;i<conditionColumnAndValueList.size();i++){
				SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
				
				//--> Adds AND when the columns and values for condition are more than one.
				if(i>0){
					conditionQuery=conditionQuery+" OR ";
				}
				conditionQuery=conditionQuery+conditionInfo.getColumnName()
						+conditionInfo.getSign()+"'"+conditionInfo.getValue().toString()+"'";		 
				
			}
		}		
		query+=conditionQuery;
		

		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ;";
		}
		
		System.out.println("\tSELECT Inner Join with condition query OR: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create wuery where mode is select sum with condition.
	 * @param tableNameList
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectSumWithCondition(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList){
//		Query Structure: SELECT SUM(`tableName`.`columnName_1`+`tableName`.`columnName_2`) FROM tableName WHERE `columnNameID`='value';																					

				
		String query="SELECT SUM(";
		Utilities util= Utilities.getInstance();
		
		//--> Get the columns
		for(int i=0;i<columnNameList.length;i++){
			query+=util.addSlantApostropheToString(tableNameList[0])
					+"."+util.addSlantApostropheToString(columnNameList[i]);
			if(i<columnNameList.length-1){
				query+="+";
			}
		}
		query+=") FROM "+tableNameList[0];
		

		//--> Process the condition query
		String conditionQuery=" WHERE ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//--> Adds AND when the columns and values for condition are more than one.
			if(i>0){
				conditionQuery=conditionQuery+" AND ";
			}
			conditionQuery=conditionQuery
					+conditionInfo.getColumnName()
					+"='"+conditionInfo.getValue().toString()+"'";		 
			
		}
		
		query+=conditionQuery;
		System.out.println("\tSELECT SUM query: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create a query where the mode is select count with condition.    
	 * @param tableNameList
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectCountWIthCondition(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList){
//		Query Structure: SELECT COUNT(columnName) FROM tableName WHERE columnName='value'; 
		
		Utilities util= Utilities.getInstance();
		String query="SELECT COUNT(";		
		
		//--> Get the columns
		for(int i=0;i<columnNameList.length;i++){
			query+=util.addSlantApostropheToString(tableNameList[0])
					+"."+util.addSlantApostropheToString(columnNameList[i]);
			if(i<columnNameList.length-1){
				query+="+";
			}
		}
		query+=") FROM "+tableNameList[0];
			
		//--> Process the condition query
		String conditionQuery=" WHERE ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//--> Adds AND when the columns and values for condition are more than one.
			if(i>0){
				conditionQuery=conditionQuery+" AND ";
			}
			conditionQuery=conditionQuery
					+conditionInfo.getColumnName()
					+"='"+conditionInfo.getValue().toString()+"'";		 
			
		}
		
		
		
		query+=conditionQuery;
		System.out.println("\tSELECT COUNT query: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create a query where the mode is select union based from column
	 */
	private String selectUnionBasedFromColumn(String[] tableNameList,String[]columnNameList){
//		Sample query structure: SELECT tableName_1.columnName_1 FROM tableName_1
//								UNION
//								SELECT tableName_2.columnName_2 FROM tableName_2
		
		String query="SELECT ";
		int midIndex=columnNameList.length/2;
		
		for(int i=0;i<midIndex;i++){
			query+=""+columnNameList[i];
			if(i<midIndex-1){
				query+=",";
			}
		}
		query+= " FROM "+tableNameList[0]+" UNION SELECT ";
		for(int i=midIndex;i<columnNameList.length;i++){
			query+=""+columnNameList[i];
			if(i<columnNameList.length-1){
				query+=",";
			}
		}
		query+= " FROM "+tableNameList[1];
		
		System.out.println("\tSelect Union based from columns query: "+query+CLASS_NAME);
		return query;
	}
	/**
	 * A special select query for showing ALL payroll data.
	 * `
	 * @return
	 */
	private String selectSPECIALAllUnionPayrollData(){
		String query="SELECT edTable.PayrollDate,edTable.EmployeeID,FamilyName,FirstName,Department,EarningID,DeductionID FROM "
				+ "(SELECT earnings.EmployeeID,earnings.PayrollDate FROM earnings "
				+ "UNION "
				+ "SELECT deductions.EmployeeID,deductions.PayrollDate FROM deductions"
				+ ") AS edTable"
				+" INNER JOIN employee ON employee.EmployeeID=edTable.EmployeeID"
				+" INNER JOIN department ON department.DepartmentID=employee.DepartmentID"
				+" LEFT JOIN earnings ON earnings.PayrollDate=edTable.PayrollDate AND earnings.EmployeeID=edTable.EmployeeID"
				+" LEFT JOIN deductions ON deductions.PayrollDate=edTable.PayrollDate AND deductions.EmployeeID=edTable.EmployeeID"
				+" ORDER BY edTable.PayrollDate DESC;";
		
		System.out.println("\tSELECT ALL UNION SPECIAL query: "+query+CLASS_NAME);
		return query;
	}
	/**
	 * Create a query where mode is select all union with condition special query for
	 * 		payroll data since it so complicated.
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectSPECIALAllUnionWithConditionPayrollData(ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		String query="SELECT edTable.PayrollDate,edTable.EmployeeID,FamilyName,FirstName,Department,EarningID,DeductionID FROM "
				+ "(SELECT earnings.EmployeeID,earnings.PayrollDate FROM earnings "
				+ "UNION "
				+ "SELECT deductions.EmployeeID,deductions.PayrollDate FROM deductions"
				+ ") AS edTable"
				+" INNER JOIN employee ON employee.EmployeeID=edTable.EmployeeID" 
				+" INNER JOIN department ON department.DepartmentID=employee.DepartmentID"
				+" LEFT JOIN earnings ON earnings.PayrollDate=edTable.PayrollDate AND earnings.EmployeeID=edTable.EmployeeID"
				+" LEFT JOIN deductions ON deductions.PayrollDate=edTable.PayrollDate AND deductions.EmployeeID=edTable.EmployeeID";
		
		//--> Create the query for condition.
		query+=" WHERE ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//--> Adds AND when the columns and values for condition are more than one.
			if(i>0){
				query+=" AND ";
			}
			query+=conditionInfo.getColumnName()
					+"='"+conditionInfo.getValue().toString()+"'";		 
			
		}
		
		//--> Add order for sorting.
		query+=" ORDER BY edTable.PayrollDate DESC;";
		
		System.out.println("\tSELECT ALL UNION SPECIAL with CONDITION query: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Use this select query when retrieving ALL employee earning and deduction data for the payroll PDF printable.
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectSPECIALEmployeeInDepartmentPayrollSummaryData(ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		String query=
				"SELECT FamilyName,FirstName,"
				+"RegPay,SalAdj,Overtime,`NS-DIFF`,ECOLA,TotalEarnings,"	// Earning Column																			
				+"SSSLoan,`Pag-ibigLoan`,SSSCont, `Pag-ibigCont`,Medicare,`M-Aid`,MP2,BEMCO,`Un-Dues`,LBP,TotalDeductions," // Deduction Column
				+"RATA,`L-A`,Longevity,Rice,`L-W/OPay`,"		 // Earning Column																			
				+"EMLOAN, `A/R`,`W-Tax`,ASEMCO,BCCI,OCCCI,DBP,CFI,`St.Peter`" // Deduction Column
				+" FROM ("
				+" SELECT earnings.EmployeeID,earnings.PayrollDate FROM earnings" 
				+" UNION "
				+" SELECT deductions.EmployeeID,deductions.PayrollDate FROM deductions"
				+" )"
				+" AS edTable" 
				+" INNER JOIN employee" 
				+" ON employee.EmployeeID=edTable.EmployeeID" 
				+" INNER JOIN department" 
				+" ON department.DepartmentID=employee.DepartmentID" 
				+" LEFT JOIN earnings"
				+" ON earnings.PayrollDate=edTable.PayrollDate AND earnings.EmployeeID=edTable.EmployeeID"
				+" LEFT JOIN deductions" 
				+" ON deductions.PayrollDate=edTable.PayrollDate AND deductions.EmployeeID=edTable.EmployeeID";
		
		
		//--> Create the query for condition.
		query+=" WHERE ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//--> Adds AND when the columns and values for condition are more than one.
			if(i>0){
				query+=" AND ";
			}
			//Modified to add query earnings.PayrollDate='2018-04-15' OR deductions.PayrollDate='2018-04-15'
			if(i==1){
				query+=" (earnings.PayrollDate='"+conditionInfo.getValue().toString()+"' ";
				query+=" OR ";
				query+=" deductions.PayrollDate='"+conditionInfo.getValue().toString()+"') ";	
				
				break;
			}
			else{
				query+=conditionInfo.getColumnName()
						+"='"+conditionInfo.getValue().toString()+"'";		 
				
			}
			
		}
		
		//--> Add This query to avoid including zero net pay values
		query+= " AND (earnings.TotalEarnings!=0 || deductions.TotalDeductions!=0) ";  
		
		//--> Add condition if regular or contractual
		query+= " AND employee.HiredAs='"
				+((Utilities.getInstance().payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL)
				+"'";  
		
		
		
		//--> Add order for sorting.
		query+=" ORDER BY employee.FamilyName,employee.FirstName ASC;";
		
		System.out.println("\tSELECT SPECIAL Employees Per Department Payroll Data Summary query: "+query+CLASS_NAME);
		return query;
	}
	
	
	/**
	 * Use this select query when retrieving ALL CONTRACTUAL employees earning and deduction data for the payroll PDF and Excel printable.
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectSPECIALPerContractualEmployeePayrollSummaryData(ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		String query=
				"SELECT FamilyName,FirstName,"
				+"NoOfDays,RatePerDay,SubTotal,Overtime,TotalEarnings,"	// EarningContractual Column																			
				+" SSSLoan,`Pag-ibigLoan`,SSSCont, `Pag-ibigCont`,Medicare, EMLOAN,`A/R`,Others,TotalDeductions,"// Deduction Column
				+"(TotalEarnings-TotalDeductions) as NetPay"// Net Pay
				+" FROM ("
				+" SELECT earningscontractual.EmployeeID,earningscontractual.PayrollDate FROM earningscontractual" 
				+" UNION "
				+" SELECT deductions.EmployeeID,deductions.PayrollDate FROM deductions"
				+" )"
				+" AS edTable" 
				+" INNER JOIN employee" 
				+" ON employee.EmployeeID=edTable.EmployeeID" 
				+" LEFT JOIN earningscontractual"
				+" ON earningscontractual.PayrollDate=edTable.PayrollDate AND earningscontractual.EmployeeID=edTable.EmployeeID"
				+" LEFT JOIN deductions" 
				+" ON deductions.PayrollDate=edTable.PayrollDate AND deductions.EmployeeID=edTable.EmployeeID";
		
		
		//--> Create the query for condition.
		query+=" WHERE ";
		for(int i=0;i<conditionColumnAndValueList.size();i++){
			SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
			
			//Modified to add query earnings.PayrollDate='2018-04-15' OR deductions.PayrollDate='2018-04-15'
			if(i==0){
				query+=" (earningscontractual.PayrollDate='"+conditionInfo.getValue().toString()+"' ";
				query+=" OR ";
				query+=" deductions.PayrollDate='"+conditionInfo.getValue().toString()+"') ";	
		
			}
			if(i==1 && Utilities.getInstance().payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
				query+= " AND "+conditionInfo.getColumnName()+"='"+conditionInfo.getValue()+"'";  
				
			}
			
			
		}
		
		//--> Add This query to avoid including zero net pay values
		query+= " AND (earningscontractual.TotalEarnings!=0 || deductions.TotalDeductions!=0) ";  
		
		//--> Add condition if regular or contractual
		query+= " AND employee.HiredAs='"
				+((Utilities.getInstance().payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL)
				+"'";  
		
		
		
		//--> Add order for sorting.
		query+=" ORDER BY employee.FamilyName,employee.FirstName ASC;";
		
		System.out.println("\tSELECT SPECIAL Per CONTRACTUAL Emplpyee Summary query: "+query+CLASS_NAME);
		return query;
	}
	/**
	 * Create a special query for 30th PDF display for asemco,bcci,occci,dbp,cfi,st peter, witholding tax. lbp
	 * @param tableNameList
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectSPECIAL30thABODCSWL(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,
			OrderByInfo orderInfo){
//		 Query Structure: SELECT `tableName_1`.`columnName_1`, `tableName_2`.`columnName_1` 
//							FROM tableName_1 INNER JOIN tableName_2 
//							ON tableName_1.columnNameID = tableName_2.columnNameID 
//							WHERE `tableName`.`columnName_1`='value';																					Database.java
		
		// Content of joinColumnCompareLis--> tableName_1.columnNameID = tableName_2.columnNameID
		
		
		String query="SELECT ";
		
		//--> Add the needed columns in query
		for(int i=0;i<columnNameList.length;i++){
			query+=""+columnNameList[i];
			
			if(i<columnNameList.length-1){
				query+=",";
			}
		}
		
		//--> Process the inner join.
//		query+=" FROM "+tableNameList[0]+" INNER JOIN "+tableNameList[1]
//				+" ON "+tableNameList[0]+".EmployeeID = "+tableNameList[1]+".EmployeeID";
		
		//-->Process the inner join query.
		query+=" FROM "+tableNameList[0]+" ";
		for(int i=0;i<joinColumnCompareList.length;i++){
			query+=" INNER JOIN "+tableNameList[i+1]+" ON "+joinColumnCompareList[i];
		}
		
		//--> Process the condition query
		String conditionQuery="";
		//--> This makes sure that the conditionList is not null
		if(conditionColumnAndValueList!=null){
			//--> To make sure that you add where when the size of condition list is greater than zero.
			if(conditionColumnAndValueList.size()>0){
				conditionQuery=" WHERE ((";
			}
			for(int i=0;i<conditionColumnAndValueList.size();i++){
				SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
				
				//--> Adds AND when the columns and values for condition are more than one.
				if(i==conditionColumnAndValueList.size()-1){
					conditionQuery=conditionQuery+" AND ";
				}
				else if(i>0){
					conditionQuery=conditionQuery+" OR ";
				}
				
				
				conditionQuery=conditionQuery+conditionInfo.getColumnName()
						+conditionInfo.getSign()+"'"+conditionInfo.getValue().toString()+"'";		 
				if(i==1){
					conditionQuery=conditionQuery+" )";	
				}
			}
			

			//--> Add condition if regular or contractual
			conditionQuery+= ") AND employee.HiredAs='"
					+((Utilities.getInstance().payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL)
					+"'";  
		}
		query+=conditionQuery;
		
		

		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ;";
		}
		
		System.out.println("\tSELECT SPECIAL ABODCCSWL30th Inner Join with condition query OR: "+query+CLASS_NAME);
		return query;
	}
	
	/**
	 * Create a special query when the mode is select special dipslay payslip Pag-ibig and Medicare.
	 * @param tableNameList
	 * @param columnNameList
	 * @param conditionColumnAndValueList
	 * @return
	 */
	private String selectSPECIAL30thHDMFMedicareD(String[] tableNameList,String[]columnNameList, 
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,OrderByInfo orderInfo){
//		 Query Structure: SELECT `tableName_1`.`columnName_1`, `tableName_2`.`columnName_1` 
//							FROM tableName_1 INNER JOIN tableName_2 
//							ON tableName_1.columnNameID = tableName_2.columnNameID 
//							WHERE `tableName`.`columnName_1`='value';																					Database.java
		
		// Content of joinColumnCompareLis--> tableName_1.columnNameID = tableName_2.columnNameID
		
		
		String query="SELECT ";
		
		//--> Add the needed columns in query
		for(int i=0;i<columnNameList.length;i++){
			query+=""+columnNameList[i];
			
			if(i<columnNameList.length-1){
				query+=",";
			}
		}
		
		//--> Process the inner join.
//		query+=" FROM "+tableNameList[0]+" INNER JOIN "+tableNameList[1]
//				+" ON "+tableNameList[0]+".EmployeeID = "+tableNameList[1]+".EmployeeID";
		
		//-->Process the inner join query.
		query+=" FROM "+tableNameList[0]+" ";
		for(int i=0;i<joinColumnCompareList.length;i++){
			query+=" INNER JOIN "+tableNameList[i+1]+" ON "+joinColumnCompareList[i];
		}
		
		//--> Process the condition query
		String conditionQuery="";
		//--> This makes sure that the conditionList is not null
		if(conditionColumnAndValueList!=null){
			//--> To make sure that you add where when the size of condition list is greater than zero.
			if(conditionColumnAndValueList.size()>0){
				conditionQuery=" WHERE ";
			}
			for(int i=0;i<conditionColumnAndValueList.size();i++){
				SelectConditionInfo conditionInfo=conditionColumnAndValueList.get(i);
				
				//--> Adds AND when the columns and values for condition are more than one.
				if(i==1){
					conditionQuery=conditionQuery+" AND (";
				}
				else if(i>1){
					conditionQuery=conditionQuery+" OR ";
				}
				conditionQuery=conditionQuery+conditionInfo.getColumnName()
						+conditionInfo.getSign()+"'"+conditionInfo.getValue().toString()+"'";		 
				
				if(i==conditionColumnAndValueList.size()-1){
					conditionQuery+=" ) ";
				}
			}
			//--> Add condition if regular or contractual
			conditionQuery+= " AND employee.HiredAs='"
					+((Utilities.getInstance().payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL)
					+"'";  
		}
		query+=conditionQuery;
		
		
		//--> Process order if not null
		if(orderInfo!=null){
			query+=" ORDER BY ";
			int count=0;
			for(String str:orderInfo.columnNames){
				query+=" "+str+" ";
				if(count<orderInfo.columnNames.length-1){
					query+=",";
				}
				count++;
			}
			query+= " "+orderInfo.order+" ";
		}
		
		query+= " ;";
		System.out.println("\tSELECT Inner Join with condition query AND: "+query+CLASS_NAME);
		return query;
	}
	/**
	 * The process when the update is only once!. One row and can have multiple column updates or one.
	 * @param sqlQuery
	 * @param tableName
	 * @param changesToBeUpdated
	 * @param tablePrimaryIDColumnName
	 * @param neededIDValue
	 * @throws SQLException
	 */
	private void updateOnlyOnce(String sqlQuery,String tableName, HashMap<String, Object>changesToBeUpdated,
			String tablePrimaryIDColumnName, String neededIDValue) throws SQLException{
		//--> For Debugging Purposes
		System.out.println("\t Update: ONCE TIMES"+CLASS_NAME);
		

		//--> Process Query
		sqlQuery= processUpdateQuery(
				tableName,changesToBeUpdated,
				tablePrimaryIDColumnName,neededIDValue,false
		);
		preparedStatement=connection.prepareStatement(sqlQuery);
		int rowsInserted = preparedStatement.executeUpdate();
		if (rowsInserted > 0) {
			isUpdate=true;
			connection.commit();
			
			totalUpdates=rowsInserted;
			
		    System.out.println("\tAn existing data was updated successfully!");
		    
		   
		}
	}
	
	/**
	 * The process that will be executed when the update is multiple. Multiple rows are updated! And can have multiple 
	 * 		or once update in columns.
	 * @param sqlQuery
	 * @param tableName
	 * @param tablePrimaryIDColumnName
	 * @param multipleUpdateList
	 * @throws SQLException
	 */
	private void updateMultipleTimes(String sqlQuery,String tableName,
			String tablePrimaryIDColumnName,
			ArrayList<MultipleUpdateDatabaseModel>multipleUpdateList) throws SQLException{
		
		
		//--> For Debugging Purposes
		System.out.println("\t Update: MULTIPLE TIMES"+CLASS_NAME);
		
		
		//--> Get the first update to identify how many question marks are needed to be set.
		MultipleUpdateDatabaseModel firstUpdate=multipleUpdateList.get(0);
		sqlQuery= processUpdateQuery(
				tableName,firstUpdate.changesToBeUpdated,
				tablePrimaryIDColumnName,firstUpdate.primarykey,true
		);
		preparedStatement=connection.prepareStatement(sqlQuery);
		
		for(int i=0,parameterIndex=1;i<multipleUpdateList.size();i++,parameterIndex=1){
			MultipleUpdateDatabaseModel multipleChange=multipleUpdateList.get(i);
			HashMap<String, Object> changes=multipleChange.changesToBeUpdated;
			for(Object columnKey:changes.keySet()){
				//--> For debugging purposes
				System.out.println("\tColumn Name: "+columnKey
						+"\tObject: "+changes.get(columnKey)
						+"\tObject Class: "+((changes.get(columnKey)!=null)?changes.get(columnKey).getClass():"null")
						+"\t Parameter Index: "+parameterIndex+CLASS_NAME);
				
				preparedStatement=setPreparedStatmentBasedOnObjectClass(preparedStatement, parameterIndex, changes.get(columnKey));
				parameterIndex++;
				
				
			}
			preparedStatement=setPreparedStatmentBasedOnObjectClass(preparedStatement, parameterIndex, multipleChange.primarykey);
			
			
			//--> For debugging purposes
			System.out.println("\tObject: "+multipleChange.primarykey
					+"\tObject Class: "+multipleChange.primarykey.getClass()+CLASS_NAME);
			System.out.println();
			System.out.println();
			
			
			
			preparedStatement.addBatch();
		}
		
		int[] rowsInserted = preparedStatement.executeBatch();
		
		//--> For debugging purposes.
		System.out.println();
		System.out.println("\tUpdate status-> Rows inserted: "+CLASS_NAME);
		for(int i=0;i<rowsInserted.length;i++){
			System.out.println("\t\tIndex: "+i
					+"\tValue: "+rowsInserted[i]+CLASS_NAME);
		}
		
		//--> Checks if the updates are xecuted successfully
		if (rowsInserted.length > 0) {
			 isUpdate=true;
			 connection.commit();
			 
			 totalUpdates=rowsInserted.length;
			 
			System.out.println("\tUpdate/s have been executed successfully!"+CLASS_NAME);
			
		}
	}
	private void l________________________________________________________________________________l(){}
	
	public String getHostName() {
		return hostName;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public Connection getConnection() {
		return connection;
	}
	
	

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	private void l___________________________________________________________________________________________l(){}
	
	public static Database getInstance(){
		if(instance==null)
			instance = new Database();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
