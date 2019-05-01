package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model.statics.Utilities;
import model.view.EarningsAndDeductionLayout;
import model.view.ReusableTable;
import database.Database;

/**
 * This class is used as storage for the data to be inputted in the fields of Payslip.
 * @author XietrZ
 *
 */
public class PayslipDataStorageInfo {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	public HashMap<String,String> dataList;
	public String[] employeeDataKeys,deductionKeys,earningKeys;
	public String deductionID, earningID;
	public String payrollDate;
	
	public PayslipDataStorageInfo(Database db, Utilities util,String earningID, String deductionID,String payrollDate){	
		init(db,util,earningID,deductionID,payrollDate);
		set(db,util);
	}
	
	private void init(Database db,Utilities util, String earningID,String deductionID,String payrollDate){
		this.payrollDate=payrollDate;
		
		dataList= new HashMap<String,String>();
		employeeDataKeys=new String[4];
		deductionKeys=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
				new String[db.deductionTableColumnNames.length-3] : new String[db.deductionsContractualColumnNames.length-3] ;
		
		earningKeys=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
				new String[db.earningTableColumnNames.length-3] : new String[db.earningsContractualColumnNames.length-3];
		
				
		this.earningID=earningID;
		this.deductionID=deductionID;
	}
	
	private void set(Database db,Utilities util){
		setEmployeeData(db);
		setDeductionData(db,util);
		setEarningData(db,util);
	}
	
	private void l______________________________________l(){}
	
	
	private void setEmployeeData(Database db){
		employeeDataKeys[0]=db.employeeTableColumnNames[0]; // EMployeeID
		employeeDataKeys[1]="Name";							// Name
		employeeDataKeys[2]="Period";						// Period/Payroll
		employeeDataKeys[3]="Department";						// Department
		
		
		for(String key:employeeDataKeys){
			dataList.put(key,"");
		}
	}
	
	private void setDeductionData(Database db,Utilities util){
//		String[] neededDeductionContractualColumnnames= new String[db.deductionsContractualColumnNames.length];
//		for(int i=0;i<neededDeductionContractualColumnnames.length;i++){
//			neededDeductionContractualColumnnames[i]="`"+db.deductionsContractualColumnNames[i]+"`";
//		}
		
		for(int i=0,j=3;i<deductionKeys.length;i++,j++){
			deductionKeys[i]=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.deductionTableColumnNames[j]:db.deductionsContractualColumnNames[j];
		}
		for(String key:deductionKeys){
			dataList.put(key,"0");
		}
		
		// Debugging Purposes
//		System.out.println("\t Deduction Data: "+CLASS_NAME);
//		for(String key:deductionKeys){
//			System.out.println("\t\t Key: "+key+CLASS_NAME);
//		}
	}

	private void setEarningData(Database db,Utilities util){
		
		
		for(int i=0,j=3;i<earningKeys.length;i++,j++){
			earningKeys[i]=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.earningTableColumnNames[j] : db.earningsContractualColumnNames[j];
		}
		for(String key:earningKeys){
			dataList.put(key,"0");
		}
		
		
		// Debugging Purposes
//		System.out.println("\t Earning Data: "+CLASS_NAME);
//		for(String key:earningKeys){
//			System.out.println("\t\t Key: "+key);
//		}
	}
	
	
	private void l________________________________________l(){}
	
	/**
	 * Add employee data from table.
	 * @param selectedIndexList
	 * @param table
	 * @param rowIndex
	 */
	public void addEmployeeData(int [] selectedIndexList, ReusableTable table,int rowIndex,String payrollDateStarts,String payrollDateEnd,Utilities util){
		//>Employee ID
		dataList.put(
				employeeDataKeys[0],
				table.getModel().getValueAt(selectedIndexList[rowIndex], 1).toString());
		//>Name
		int nameStartIndex=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?4:3;
		dataList.put(
				employeeDataKeys[1],
				""+table.getModel().getValueAt(selectedIndexList[rowIndex], nameStartIndex)+", "+table.getModel().getValueAt(selectedIndexList[rowIndex], nameStartIndex+1));
		
		//>Period
				dataList.put(
						employeeDataKeys[2],
						util.processPayrollPeriodString(payrollDateStarts, payrollDateEnd));
		
		//>Department
		dataList.put(
				employeeDataKeys[3],
				table.getModel().getValueAt(selectedIndexList[rowIndex], 2).toString());
		
	}
	
	/**
	 * Add DEDUCTION  or EARNING data to this data storage from database.
	 * @param db
	 * @param rowIndex
	 * @param tableColumnNames
	 * @param both
	 * @throws SQLException
	 */
	public void addDeductionOREarningData(Database db,int rowIndex,String[] tableColumnNames,int earnOrDedMode) throws SQLException{
		System.out.println("\t ADD DATA: "+CLASS_NAME);
		
		for(int j=3;j<tableColumnNames.length;j++){
			String key=tableColumnNames[j];
			db.resultSet.absolute(rowIndex+1);
			dataList.put(key,db.resultSet.getObject(j+1).toString());
		}	
	}
	
}
