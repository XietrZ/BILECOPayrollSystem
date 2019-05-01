package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Constant;
import model.SelectConditionInfo;
import model.statics.Utilities;
import model.view.EmployeeBasicInfoDefaultLayout;
import view.MainFrame;
import view.dialog.AddEmployeeDialog;
import database.Database;

public class ListenerAddEmployee implements ActionListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private AddEmployeeDialog addEmployeeDialog;
	private MainFrame mainFrame;
	private ListenerEmployeeViewPanel employeeViewPanelListener;
	
	
	public ListenerAddEmployee() {
		// TODO Auto-generated constructor stub
		addEmployeeDialog=AddEmployeeDialog.getInstance();
		mainFrame=MainFrame.getInstance();
	}
	private void l_____________________________l(){}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//--> SAVE BUTTON
		if(e.getSource()==addEmployeeDialog.saveButton){
			System.out.println(THIS_CLASS_NAME+"Save Employee to database"+CLASS_NAME);
			
			try {
				processSaveButton();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				mainFrame.showOptionPaneMessageDialog(
						""+e1.getMessage(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//--> CANCEL BUTTON
		else if(e.getSource()==addEmployeeDialog.cancelButton){
			System.out.println(THIS_CLASS_NAME+"Cancel add employee"+CLASS_NAME);
		
			addEmployeeDialog.dispose();
		}
		
		//--> GENERATE EMPLOYEE ID BUTTON
		else if(e.getSource()==addEmployeeDialog.contentPanel.generateEmployeeIDButton){
			System.out.println(THIS_CLASS_NAME+"Generate employee ID.");
			
			processGenerateEmployeeIDButton();
		}
	}
	private void l__________________________l(){}
	
	/**
	 * Process the action that will happen when you click the save button
	 * 		here in add employee.
	 * @throws Exception 
	 */
	private void processSaveButton() throws Exception{
		
		Database db=Database.getInstance();
		AddEmployeeDialog aed=addEmployeeDialog;
		EmployeeBasicInfoDefaultLayout panel=aed.contentPanel;
		aed.contentPanel.storeAllUIComponentsToAHashMap(db);
		
	
		if(aed.isFilledUpNecessaryFields(db, mainFrame)){
			
			//----------------------------------------------------------
			// Insert New Employee data
			Object [][]values={
				{
					aed.contentPanel.employeeIDTextField.getText(),
					(String) panel.nameTitleComboBox.getModel().getSelectedItem(),
					panel.familyNameTextField.getText(),
					panel.firstNameTextField.getText(),
					panel.middleNameTextField.getText(),
					(String)  panel.suffixComboBox.getModel().getSelectedItem(),
					(String)  panel.withATMCombobox.getModel().getSelectedItem(),
					panel.dateHiredDatePicker.getJFormattedTextField().getText(),
					panel.workStartsDatePicker.getJFormattedTextField().getText(),
					Integer.parseInt(panel.yearHiredtextField.getText()),
					(String)  panel.hiresAsComboBox.getModel().getSelectedItem(),
					Double.parseDouble(panel.salaryTextField.getText()),
					(panel.dateLeftDatePicker.getJFormattedTextField().getText().isEmpty())?Constant.STRING_DATE_LEFT_NULL:panel.dateLeftDatePicker.getJFormattedTextField().getText(),
					panel.jobTitleTextField.getText(),
					""+db.getKeyOfDepartmentData(panel.departmentComboBox.getSelectedItem().toString()),
					panel.sssTextField.getText(),
					panel.pagIbigTextField.getText(),
					panel.philhealthTextField.getText()
				}
			};
			
//			db.selectDataInDatabase(new String[]{db.tableNameEmployee}, null, null,null,null,Constant.SELECT_ALL);
			db.insertDataInDatabase(db.tableNameEmployee,db.employeeTableColumnNames,values);
			

			//-------------------------------------------------------------------
			// Insert ASEMCO/BCCCI/OCCCI/DBP/CFI data immediately.

//			db.selectDataInDatabase(new String[]{db.tableNameABODC}, null, null,null,null,Constant.SELECT_ALL);

			int numOfRowsToBeInsert=1;
			values= new Object[numOfRowsToBeInsert][db.abodcTableColumnNames.length];
			for(int r=0;r<numOfRowsToBeInsert;r++){
				for(int i=0;i<db.abodcTableColumnNames.length;i++){
					if(i==0){
						values[r][i]=aed.contentPanel.employeeIDTextField.getText();
					}
					else{
						values[r][i]=Double.parseDouble("0");
					}
				}
			}
			
			db.insertDataInDatabase(db.tableNameABODC,db.abodcTableColumnNames,values);
				
				
			
			//-------------------------------------------------------------------
			// Insert PAGIBIG data immediately.
			
//			db.selectDataInDatabase(new String[]{db.tableNamePagibig}, null, null,null,null,Constant.SELECT_ALL);
			
			numOfRowsToBeInsert=1;
			values= new Object[1][db.pagibigTableColumnNames.length];
			for(int r=0;r<numOfRowsToBeInsert;r++){
				for(int i=0;i<db.pagibigTableColumnNames.length;i++){
					if(i==0){
						values[r][i]=aed.contentPanel.employeeIDTextField.getText();
					}
					else{
						values[r][i]=Double.parseDouble("0");
					}
				}
			}
			
			db.insertDataInDatabase(db.tableNamePagibig,db.pagibigTableColumnNames,values);
				
				
			
			
			//-------------------------------------------------------------------
			// Insert St. Peter data immediately.
			
//			db.selectDataInDatabase(new String[]{db.tableNameStPeter}, null, null,null,null,Constant.SELECT_ALL);
			
			numOfRowsToBeInsert=1;
			values= new Object[numOfRowsToBeInsert][db.stPeterTableColumnNames.length];
			for(int r=0;r<numOfRowsToBeInsert;r++){
				for(int i=0;i<db.stPeterTableColumnNames.length;i++){
					if(i==0){
						values[r][i]=aed.contentPanel.employeeIDTextField.getText();
					}
					else{
						values[r][i]=Double.parseDouble("0");
					}
				}
			}
			
			db.insertDataInDatabase(db.tableNameStPeter,db.stPeterTableColumnNames,values);
				
	
			
			//-------------------------------------------------------------------
			// Insert Union Dues data immediately.
//			db.selectDataInDatabase(new String[]{db.tableNameUnionDues}, null, null,null,null,Constant.SELECT_ALL);

			numOfRowsToBeInsert=1;
			values= new Object[numOfRowsToBeInsert][db.unionDuesTableColumnNames.length];
			
			for(int r=0;r<numOfRowsToBeInsert;r++){
				for(int i=0;i<db.unionDuesTableColumnNames.length;i++){
					if(i==0){
						values[r][i]=aed.contentPanel.employeeIDTextField.getText();
					}
					else{
						values[r][i]=Double.parseDouble("0");
					}
				}
			}
			
			db.insertDataInDatabase(db.tableNameUnionDues,db.unionDuesTableColumnNames,values);
			
			//-------------------------------------------------------------------
			// Insert ECOLA/Laundry Allowance/Longevity/Rice data immediately.
			db.selectDataInDatabase(new String[]{db.tableNameEarningsAutomate}, null, null,null,null,Constant.SELECT_ALL);

			numOfRowsToBeInsert=1;
			values= new Object[numOfRowsToBeInsert][db.earningsAutomateTableColumnNames.length];
			
			for(int r=0;r<numOfRowsToBeInsert;r++){
				for(int i=0;i<db.earningsAutomateTableColumnNames.length;i++){
					if(i==0){
						values[r][i]=aed.contentPanel.employeeIDTextField.getText();
					}
					else{
						values[r][i]=Double.parseDouble("0");
					}
				}
			}
			
			db.insertDataInDatabase(db.tableNameEarningsAutomate,db.earningsAutomateTableColumnNames,values);
			
			
			
			//----------------------------------------------------
			
			if(db.isInsert){
				//--> Popup Inserted Successfully!
				 MainFrame.getInstance().showOptionPaneMessageDialog(
				    		"A new employee was added successfully!",
				    		JOptionPane.INFORMATION_MESSAGE);
				
				System.out.println("\tReuse the code of show employee data summary!!!!! "+CLASS_NAME);
				//--> Reuse the code show employee data summary.
				employeeViewPanelListener.processShowEmployeeDataSummary();
				addEmployeeDialog.dispose();
			}
		}
		
	}
	/**
	 * Process the action that will happen when you click the generate employeeID button.
	 */
	private void processGenerateEmployeeIDButton(){
		EmployeeBasicInfoDefaultLayout panel=addEmployeeDialog.contentPanel;
		Database db= Database.getInstance();
		Utilities util= Utilities.getInstance();
		String dateHired=addEmployeeDialog.contentPanel.dateHiredDatePicker.getJFormattedTextField().getText();
		//------------------------------------------------
		String year=util.getYearFromDateFormatYyyyMmDd(dateHired),
				monthHired=util.getMonthMMFormatFromDateFormatYyyyMmDd(dateHired),
				firstNameFirstLetter=""+panel.firstNameTextField.getText().charAt(0),
				middleNameFirstLetter=""+panel.middleNameTextField.getText().charAt(0),
				lastNameFirstLetter=""+panel.familyNameTextField.getText().charAt(0);
		
		
		//------------------------------------------------
		//--> Get the year +month+ initial letters of names
		String employeeID=""+year+"-";
		employeeID+=""+monthHired+"-";
		employeeID+=firstNameFirstLetter;
		employeeID+=middleNameFirstLetter;
		employeeID+=lastNameFirstLetter;
		
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
			employeeID+="-";
		
			//------------------------------------------------
			//--> Get the inputs for the condition query.
			ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
			conditionColumnAndValueList.add(new SelectConditionInfo(
					db.employeeTableColumnNames[8],
					addEmployeeDialog.contentPanel.yearHiredtextField.getText()));
			
			//------------------------------------------------
			//--> Get the count from database
			db.selectDataInDatabase(
					new String[]{db.tableNameEmployee}, 
					new String[]{db.employeeTableColumnNames[8]}, 
					conditionColumnAndValueList, null,null,
					Constant.SELECT_COUNT_WITH_CONDITION
			);
			String count="";
			try {
				db.resultSet.absolute(1);
				count=db.resultSet.getObject(1).toString();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//> Add zeroes with max 4 digits.
			for(int i=count.length();i<4;i++){
				count="0"+count;
			}
			
			employeeID+=count;
		}
		addEmployeeDialog.contentPanel.employeeIDTextField.setText(employeeID);
	}
	
	
	private void l________________________________________l(){}
	
	public void setters(ListenerEmployeeViewPanel employeeViewPanelListener){
		this.employeeViewPanelListener =  employeeViewPanelListener;
	}
}