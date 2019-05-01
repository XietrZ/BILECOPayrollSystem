package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;

import model.Constant;
import model.OrderByInfo;
import model.PayrollTableModel;
import model.PhicInfo;
import model.SelectConditionInfo;
import model.SssInfo;
import model.statics.Utilities;
import model.view.AddEarningOrDeductionDataLayout;
import model.view.EarningsAndDeductionLayout;
import model.view.ReusableTable;
import view.MainFrame;
import view.views.DeductionViewPanel;
import view.views.EarningViewPanel;
import view.views.EmployeeViewPanel;
import view.views.PayrollViewPanel;
import database.Database;

public class ListenerEarningDeductionViewPanel implements ActionListener,DocumentListener,ListSelectionListener,MouseListener,ChangeListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	

	private boolean isFixedTableClicked=false, isDynamicTableClicked=false,isFullScreenTableClicked=false;
	private int editMode=-1;
	
	private EarningsAndDeductionLayout bothEarningDeductionViewPanel;
	private MainFrame mainFrame;
	
	private ListenerMainFrame mainFrameListener;
	private String[] neededColumnsForFixedDynamicTable=null;
	private boolean isShowDataBasedOnCombobox=false;
	private double tempCalculatedValue=-1; // This variable is only used during EDIT ONCE mode and only in calculation is SSSCont,Pag-ibigCont and Medicare value.
	
	
	public boolean isEmployerShareDataShown=false;
	public ReusableTable table=null;
	
	private void l___________________________________l(){}
	
	public ListenerEarningDeductionViewPanel() {
		mainFrame=MainFrame.getInstance();
	}
	private void l_________________________________l(){}
	@Override
	public void actionPerformed(ActionEvent e) {
		//--> Check the disconnection status of database.
		Database.getInstance().checkIfDisconnectedToDatabase();
		
		
		//--> Only Process When Connected.
		if(Database.getInstance().isConnected){
		
			
			
			//-------------------------------------------------------------------
			//--> SHOW CALCULATION PANEL
			if(e.getSource()==bothEarningDeductionViewPanel.calculateBtn){
					System.out.println(THIS_CLASS_NAME+"Calculation Button clicked!"+CLASS_NAME);
				bothEarningDeductionViewPanel.calculationPanel.setVisible((bothEarningDeductionViewPanel.calculationPanel.isVisible())?false:true);
			}
			else{
				//--> Add listener to calculation buttons.
				for(String key:bothEarningDeductionViewPanel.calculationPanel.buttonList.keySet()){
					JButton generateButton=bothEarningDeductionViewPanel.calculationPanel.buttonList.get(key);
					
					if(e.getSource()==generateButton){
						switch(key){
							//-->GENERATE REGULAR PAY
							case Constant.GENERATE_REGULAR_PAY_BTN:{
								processCalculate(Constant.CALCULATE_REGULAR_PAY_VALUE);
								break;
							}
							//-->GENERATE ECOLA
							case Constant.GENERATE_ECOLA_BTN:{
								processCalculate(Constant.CALCULATE_ECOLA_VALUE);
								break;
							}
							//-->GENERATE LAUNDRY ALLOWANCE
							case Constant.GENERATE_LAUNDRY_ALLOWAMCE_BTN:{
								processCalculate(Constant.CALCULATE_LAUNDRY_ALLOWANCE_VALUE);
								break;
							}
							//-->GENERATE LONGEVITY
							case Constant.GENERATE_LONGEVITY_BTN:{
								processCalculate(Constant.CALCULATE_LONGEVITY_VALUE);
								break;
							}
							//-->GENERATE RICE
							case Constant.GENERATE_RICE_BTN:{
								processCalculate(Constant.CALCULATE_RICE_VALUE);
								break;
							}
							//-->GENERATE OVERTIME
							case Constant.GENERATE_OVERTIME_BTN:{
								mainFrame.showOptionPaneMessageDialog("Generate Overtime Not Yet Available", JOptionPane.ERROR_MESSAGE);
								break;
							}
							
							//----- Contractual -------
							//-->GENERATE RATE PER DAY
							case Constant.GENERATE_RATE_PER_DAY:{
								processCalculate(Constant.CALCULATE_RATE_PER_DAY_VALUE);
								break;
							}
							//-->GENERATE SUB TOTAL
							case Constant.GENERATE_SUB_TOTAL:{
								processCalculate(Constant.CALCULATE_SUB_TOTAL_VALUE);
								break;
							}
							
							//---------------------------------------------------------------------
							
							//--> GENERATE SSS data.
							case Constant.GENERATE_SSS_BTN:{
								processCalculate(Constant.CALCULATE_SSS_VALUE);
								break;
							}
							//--> GENERATE Philhealth data.
							case Constant.GENERATE_PHILHEALTH_BTN:{
								processCalculate(Constant.CALCULATE_MEDICARE_VALUE);
								break;
							}
							//--> GENERATE ASEMCO/BCCI/DBP/OCCI
							case Constant.GENERATE_ASEMCO_BTN:{
								processCalculate(Constant.CALCULATE_ASEMCO_BCCI_OCCCI_DBP_CFI_VALUE);
								
								break;
							}
							//--> GENERATE PAGIBIG
							case Constant.GENERATE_PAGIBIG_BTN:{
								processCalculate(Constant.CALCULATE_PAGIBIG_VALUE);
								
								break;
							}
							//--> GENERATE ST. PETER
							case Constant.GENERATE_ST_PETER_BTN:{
								processCalculate(Constant.CALCULATE_ST_PETER_VALUE);
								break;
							}
							//--> GENERATE UNION DUES
							case Constant.GENERATE_UNION_DUES_BTN:{
								processCalculate(Constant.CALCULATE_UNION_DUES_VALUE);
								break;
							}
						}
					}
				}
			}
			
			//-------------------------------------------------------------------
	
			
			
			//--> EDIT EARNING/DEDUCTION DATA
			if(e.getSource()==bothEarningDeductionViewPanel.editBtn){
				processEditDataButton();
			}
			//--> SHOW EMPLOYER SHARE DIALOG
			else if(e.getSource()==bothEarningDeductionViewPanel.showEmployerShareBtn){
				processShowEmployerShareDialog();
			}
			//--> SHOW EMPLOYER SHARE DIALOG
			else if(e.getSource()==bothEarningDeductionViewPanel.hideEmployerShareBtn){
				processHideEmployerShareDialog();
			}
			//--> SAVE EARNING/DEDUCTION DATA
			else if(e.getSource()==bothEarningDeductionViewPanel.saveBtn){
				processsSaveButton();
			}
			
			//--> CANCEL EARNING/DEDUCTION DATA
			else if(e.getSource()==bothEarningDeductionViewPanel.cancelBtn){
				processsCancelButton();
			}
			
			//-------------------------------------------------------------------
			
			//--> CLICKING SHOWBTN Showing the Payroll Data.  SHOW DATA IN Table Depending on chosen data in combo boxes from Show Display PANEL.
			else if(e.getSource()==bothEarningDeductionViewPanel.showBtn){
				if(Utilities.getInstance().isEdit){
					mainFrame.showOptionPaneMessageDialog("You cannot click this button during edit. Please cancel edit mode.", JOptionPane.ERROR_MESSAGE);
				}
				else{
					isEmployerShareDataShown=false;
					processShowDataBasedFromThreeColumnCombobox();
				}
			}
			
		
			//--> BACK to Payroll View
			else if(e.getSource()==bothEarningDeductionViewPanel.backBtn){
				if(Utilities.getInstance().isEdit){
					mainFrame.showOptionPaneMessageDialog("You cannot click this button during edit. Please cancel edit mode.", JOptionPane.ERROR_MESSAGE);
				}
				else{
					mainFrameListener.processPayrollView();
				}
			}
		}
		
		//-------------------------------------------------------------------
		
		
		//--> Repaint to avoid overlap
		bothEarningDeductionViewPanel.repaint();
		
		
		
	}
	private void l__________________________________l(){}
	
	
	/**
	 *  Execute the process that will happen when the show all employee deduction data button is clicked.
	 * 	Why is it that there is a variable called conditionColumnAndValueList , so that this method can be reused when basing the show all columns in dialog box. In other words, the column combobox in showDisplayOption dialog box is ALL.
	 * 	If it is not used by combobox, then just put 'null'.
	 * 
	 * @param conditionColumnAndValueList
	 */
	private void processShowAllEmployeeDeductionOrEarningDataButton(ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		System.out.println();
		System.out.println(THIS_CLASS_NAME+"Show all employee earning/deduction data."+CLASS_NAME);
		
		Database db= Database.getInstance();
		Utilities util=Utilities.getInstance();
		String[] preferredTableColumnNamesList=(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
				db.earningTableColumnNames:db.earningsContractualColumnNames
			:
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.deductionTableColumnNamesNoComment:db.deductionsContractualColumnNamesNoComment;
		String preferredTableName=(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
				db.tableNameEarnings:db.tableNameEarningsContractual
			:
			db.tableNameDeductions;
		
		//--> Assign what table
		table=bothEarningDeductionViewPanel.fixedTable;
		//------------------------------------------------------------------------
		//--> Get necessary values for inner join/join.
		String tableNameEmployeeWithApostrophe=util.addSlantApostropheToString(db.tableNameEmployee), // Employee Table
				tableNamePrefferedWithApostrophe=util.addSlantApostropheToString(preferredTableName), // Eaning/Deduction Table
				tableNameDepartmentWithApostrophe=util.addSlantApostropheToString(db.tableNameDepartment); // Department Table
		String[] joinColumnCompareList={
				tableNameEmployeeWithApostrophe+"."+util.addSlantApostropheToString(db.employeeTableColumnNames[0]) // EmployeeID
					+"="+tableNamePrefferedWithApostrophe+"."+util.addSlantApostropheToString(db.employeeTableColumnNames[0]), // EmployeeID
				
				tableNameEmployeeWithApostrophe+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[0])// DepartmentID
				+"="+tableNameDepartmentWithApostrophe+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[0]), // DepartmentID
				
			};

		//-----------------------------------------------------------------------
		//--> Process necessary columns
		//> Get desired columns from employee table
		String[] desiredColumnsFromEmployeeTable={
				db.departmentTableColumnNames[1],// Department
				((util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.employeeTableColumnNames[11]// MonthlyFixedSalary
					:
					db.employeeTableColumnNames[6]//WItHATM
				),
				db.employeeTableColumnNames[2], // FamilyName
				db.employeeTableColumnNames[3], // FirstName
				
		};
		
		neededColumnsForFixedDynamicTable=null;
		neededColumnsForFixedDynamicTable=new String[preferredTableColumnNamesList.length+desiredColumnsFromEmployeeTable.length];
		for(int i=0,employeeTableIndex=0,earndedTableIndex=0;i<neededColumnsForFixedDynamicTable.length;i++){
			if(i>2 && i<desiredColumnsFromEmployeeTable.length+3){
				if(employeeTableIndex==0){// Department
					neededColumnsForFixedDynamicTable[i]=tableNameDepartmentWithApostrophe+"."+util.addSlantApostropheToString(desiredColumnsFromEmployeeTable[employeeTableIndex]);
					
				}
				else{
					neededColumnsForFixedDynamicTable[i]=tableNameEmployeeWithApostrophe+"."+util.addSlantApostropheToString(desiredColumnsFromEmployeeTable[employeeTableIndex]);
				}
				employeeTableIndex++;
			}
			else{
				neededColumnsForFixedDynamicTable[i]=tableNamePrefferedWithApostrophe+"."+util.addSlantApostropheToString(preferredTableColumnNamesList[earndedTableIndex]);
				earndedTableIndex++;
			}
			
		}
		//-----------------------------------------------------------------------
		//--> Get data from database for fixed table and update.
		db.selectDataInDatabase(
				new String[]{preferredTableName,db.tableNameEmployee,db.tableNameDepartment}, 
				neededColumnsForFixedDynamicTable, 
				conditionColumnAndValueList, 
				joinColumnCompareList,
				new OrderByInfo(
					new String[]{
							db.payrollDateTableColumnNames[0], // Payroll Date
							db.employeeTableColumnNames[2], // LastName
							db.employeeTableColumnNames[3]}, // FirstName
					"ASC"
				),
				Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
				);
			
		//-----------------------------------------------------------------------
		
		//--> Update Dynamic Table
		int []dynamicTableHideColumnList={0,0,0,0,0,0,0};
		bothEarningDeductionViewPanel.dynamicTable.isAutoResize=false;
		bothEarningDeductionViewPanel.dynamicTable.updateTable(db);
		System.out.println("\t HIIIDEEEE MODE"+CLASS_NAME);
		bothEarningDeductionViewPanel.dynamicTable.hideColumns(dynamicTableHideColumnList);
		
		bothEarningDeductionViewPanel.dynamicTable.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		//-----------------------------------------------------------------------
		
		//--> Update Fixed Table, No updateTable in fixedtable since fixedtable and dynamic table has the same table model.
		int []fixedTableHideColumnList=new int[neededColumnsForFixedDynamicTable.length-dynamicTableHideColumnList.length];		
		for(int i=0;i<fixedTableHideColumnList.length;i++){
			fixedTableHideColumnList[i]=dynamicTableHideColumnList.length;
		}
		bothEarningDeductionViewPanel.fixedTable.hideColumns(fixedTableHideColumnList);
//		bothEarningDeductionViewPanel.fixedTable.hideColumns(
//			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
//				new int[]{0}
//				:
//				new int[]{0,3}	
//		);
		bothEarningDeductionViewPanel.fixedTable.hideColumns(new int[]{0});
		
		
		bothEarningDeductionViewPanel.fixedTable.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		//------------------------------------------------------------------------

		
		
		//--> Update Row Count Label
		bothEarningDeductionViewPanel.rowCountLabel.setText("Row Count: "+bothEarningDeductionViewPanel.fixedTable.getModel().getRowCount());
				
		//------------------------------------------------------------------------
		
		//--> Set EDIT Mode.
		editMode=Constant.EDIT_MULTIPLE;
		
		
		//------------------------------------------------------------------------
		//--> Process the retrieving of data for TOTAL TABLE.
		ReusableTable table = bothEarningDeductionViewPanel.fixedTable;
		
		//--> Process needed columns for total table.
		String[]neededColumnsForTotalTable=new String[preferredTableColumnNamesList.length-3+1];
		for(int i=0,j=3;i<neededColumnsForTotalTable.length;i++,j++){
			if(i==neededColumnsForTotalTable.length-1){ // OverallMonthlyBasicSalary
				neededColumnsForTotalTable[i]="SUM("+util.addSlantApostropheToString(db.employeeTableColumnNames[11])+")as `OverallBasicSalary`";
			}
			else{
				neededColumnsForTotalTable[i]="SUM("+util.addSlantApostropheToString(preferredTableColumnNamesList[j])+")as `Total"+preferredTableColumnNamesList[j]+"`";
				if(i==neededColumnsForTotalTable.length-2){ // OverallTotalDeductions/ OverallTotalEarnings
					neededColumnsForTotalTable[i]="SUM("+util.addSlantApostropheToString(preferredTableColumnNamesList[j])+")as `Overall"+preferredTableColumnNamesList[j]+"`";
				}
			}
			
		}
		
	
				
		//--> Process Inner Join list. Only when employer share is shown.
		joinColumnCompareList=new String[]{
				db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+preferredTableName+"."+db.employeeTableColumnNames[0], // 		` employee.EmployeeID=deductions.EmployeeID
		};
		
		//--> Execute the values of total table.
		executeProcessOfValuesOfTotalTable(
			db, util, table,
			new String[]{preferredTableName,db.tableNameEmployee},
			preferredTableColumnNamesList[0],
			neededColumnsForTotalTable, joinColumnCompareList,editMode
		);

		
		
		//--> Update immediately with search mechanism.
		bothEarningDeductionViewPanel.executeSearchMechanismOfAllTables();


				
		//--> Set enabled necessary UI components
		boolean isBoolean=false;
		bothEarningDeductionViewPanel.fullScreenTableScrollPane.setVisible(isBoolean);
		bothEarningDeductionViewPanel.calculationPanel.setVisible(isBoolean);
		isShowDataBasedOnCombobox=false;
		
		
		isBoolean=true;
		bothEarningDeductionViewPanel.dynamicTableScrollPane.setVisible(isBoolean);
		bothEarningDeductionViewPanel.fixedTableScrollPane.setVisible(isBoolean);
		
		
		bothEarningDeductionViewPanel.dynamicTable.setVisible(isBoolean);
		bothEarningDeductionViewPanel.fixedTable.setVisible(isBoolean);
		
		bothEarningDeductionViewPanel.dynamicTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		bothEarningDeductionViewPanel.dynamicTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		bothEarningDeductionViewPanel.editBtn.setVisible(isBoolean);
		bothEarningDeductionViewPanel.showEmployerShareBtn.setVisible(isBoolean);
		
		//--> Update Top Left Panel
		bothEarningDeductionViewPanel.updateTopLeftPanel((bothEarningDeductionViewPanel instanceof EarningViewPanel)?1:2);
		bothEarningDeductionViewPanel.leftTopPanel.remove(bothEarningDeductionViewPanel.hideEmployerShareBtn);
		
		
	}
	
	/**
	 * Execute processes that will happen when you click the show deduction column combobox.
	 * Or when the table shown is only ONE.
	 */
	private void processShowDataBasedFromThreeColumnCombobox(){
		System.out.println(THIS_CLASS_NAME+" SHOW the data  based from the parameters of three comboboxes"+CLASS_NAME);
		
		Database db = Database.getInstance();
		Utilities util=Utilities.getInstance();
		String[] preferredTableColumnNames=(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.earningTableColumnNames:db.earningsContractualColumnNames
				:
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
						db.deductionTableColumnNamesNoComment:db.deductionsContractualColumnNamesNoComment;
		String preferredTableName=(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.tableNameEarnings:db.tableNameEarningsContractual
				:
				db.tableNameDeductions;
		
			
		
		
		//--> Make sure that the there are payrolldates
		if(bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedObjects().length==0){
			mainFrame.showOptionPaneMessageDialog(
					"Please add payroll date.", 
					JOptionPane.ERROR_MESSAGE
			);
		}
		else{
			executeShowDataBasedFromThreeColumnCombobox(db, util, preferredTableName, preferredTableColumnNames);
		}
		
		
		//--> Update immediately with search mechanism.
		bothEarningDeductionViewPanel.executeSearchMechanismOfAllTables();


	}
	

	
	
		
		
	
	/**
	 * Execute processes that will happen when clicking the edit deduction data button. 
	 */
	private void processEditDataButton(){
		
		Utilities util=Utilities.getInstance();
		
		if(util.authorizationLevel==Constant.ADMIN_AUTHORIZATION_LEVEL ||
				util.authorizationLevel==Constant.USER_AUTHORIZATION_LEVEL){
			
			System.out.println();
			System.out.println(THIS_CLASS_NAME+"Edit Deduction/Earning Data."+CLASS_NAME);
			
			
			Database db = Database.getInstance();
			String[] preferredTableColumnNames=(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
						db.earningTableColumnNames:db.earningsContractualColumnNames
					:
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
							db.deductionTableColumnNamesNoComment:db.deductionsContractualColumnNamesNoComment;
			String selectedPayrollDateCombobox=bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString();
			
			//---------------------------------------------------------------------------------------------
			if(table==null || table.getModel().getRowCount()==0){
				MainFrame.getInstance().showOptionPaneMessageDialog(
						"You cannot edit this table. Please add/show some data in the table.", 
						JOptionPane.ERROR_MESSAGE);
			}
			//--> You can't edit if the chosen colum is about total value in combobox.
			else if(editMode==Constant.EDIT_ONCE && bothEarningDeductionViewPanel.columnComboBox.getSelectedItem().toString().
						equals(util.convertCamelCaseColumnNamesToReadable(preferredTableColumnNames[preferredTableColumnNames.length-1]))){
				MainFrame.getInstance().showOptionPaneMessageDialog(
						"You cannot edit the total value column", 
						JOptionPane.ERROR_MESSAGE);
			}
			else if(selectedPayrollDateCombobox.equals(Constant.STRING_ALL) || db.isPayrollDateLocked(new String[]{util.convertDateReadableToYyyyMmDdDate(selectedPayrollDateCombobox)})){
				mainFrame.showOptionPaneMessageDialog(
						"You cannot edit the payroll data where the payroll date is locked.", 
						JOptionPane.ERROR_MESSAGE);
			}
			
			else{
				
				
				if(editMode==Constant.EDIT_ONCE){
					executeEditOnce( util, db);
				}
				else if(editMode==Constant.EDIT_MULTIPLE){
					executeEditMultiple(db, util);
				}
				
				//--> Set edit boolean, so that you can't click any buttons when you are in edit mode.
				util.isEdit=true;
				
				//--> Set enabled UI components
				boolean isBoolean=false;
				
				bothEarningDeductionViewPanel.columnComboBox.setEnabled(isBoolean);
				bothEarningDeductionViewPanel.payrollDateComboBox.setEnabled(isBoolean);
				bothEarningDeductionViewPanel.departmentComboBox.setEnabled(isBoolean);
				
				bothEarningDeductionViewPanel.updateTopLeftPanel(3);
			}
		}
		else{
			mainFrame.showOptionPaneMessageDialog("You are not authorized to use this feature.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Execute the process that will happen when you click the save button.
	 */
	private void processsSaveButton(){
		System.out.println();
		System.out.println(THIS_CLASS_NAME+": Save edit."+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		String[] preferredTableColumnNames=(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
				db.earningTableColumnNames:db.earningsContractualColumnNames
			:
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.deductionTableColumnNamesNoComment:db.deductionsContractualColumnNamesNoComment;
		String preferredTableName=(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.tableNameEarnings:db.tableNameEarningsContractual
			:
			db.tableNameDeductions;
		
		//--> The selected column index in column combobox.
		int comboboxSelectedColumnIndex= bothEarningDeductionViewPanel.columnComboBox.getSelectedIndex(); // Selected index in dialog combobox column
		
		
		//--------------------------------------------------------------------------------------------
		//--> Store the changes that are needed to be updated.
		try {
			if(editMode==Constant.EDIT_ONCE){
				bothEarningDeductionViewPanel.storeONEChangesNeededToUpdate(db,util, this,
						comboboxSelectedColumnIndex,
						preferredTableName,
						preferredTableColumnNames, 
						bothEarningDeductionViewPanel.columnComboBox.getSelectedItem().toString(),
						bothEarningDeductionViewPanel.fullScreenTable,bothEarningDeductionViewPanel
				);
			}
			else if(editMode==Constant.EDIT_MULTIPLE){
				bothEarningDeductionViewPanel.storeMULTIPLEChangesNeededToUpdate(
						preferredTableName, preferredTableColumnNames,this,bothEarningDeductionViewPanel);
			}
		} catch (SQLException e) {
			System.out.println(THIS_CLASS_NAME+""+e.getMessage()+CLASS_NAME);
			mainFrame.showOptionPaneMessageDialog(
					e.getMessage(), 
					JOptionPane.ERROR_MESSAGE
			);
			e.printStackTrace();
		}
		

		
		//--------------------------------------------------------------------------------------------
		//--> Update/save only in database when there are changes that happens
		if(bothEarningDeductionViewPanel.multipleUpdateList.size()>0 || bothEarningDeductionViewPanel.multipleUpdateEmployerShareList.size()>0){
			
			//--> Update content in database in deduction table when there are changes DEDUCTIONS Data.
			if(bothEarningDeductionViewPanel.multipleUpdateList.size()>0){
				db.updateDataInDatabase(
						preferredTableName, 
						null,
						preferredTableColumnNames[0], 
						null, 
						true, 
						bothEarningDeductionViewPanel.multipleUpdateList);
				
				
			}
			
			
			//--> Update/save only in database when there are changes that happens of the EMPLOYER SHARE Data. Remember this code is executed during in EDIT ONCE and EDIT MULTIPLE mode.
			if(	(editMode==Constant.EDIT_ONCE && bothEarningDeductionViewPanel.multipleUpdateEmployerShareList.size()>0 && util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel) && isEmployerShareDataShown) 
					||
					(editMode==Constant.EDIT_MULTIPLE && bothEarningDeductionViewPanel.multipleUpdateEmployerShareList.size()>0)){
				
				db.updateDataInDatabase(
					db.tableNameEmployerShare, 
					null,
					db.employerShareTableColumnNames[0], 
					null, 
					true, 
					bothEarningDeductionViewPanel.multipleUpdateEmployerShareList
				);
			
			}
			
			//--> Pop up Dialog if update is successful
			if(db.isUpdate){
				 JOptionPane.showMessageDialog(null, (db.totalUpdates==1)?"An existing data was updated successfully!":
						 "Updates have been executed successfully!", 
							null, JOptionPane.INFORMATION_MESSAGE);
			}
			
			
			
			//-->Update Table UI.
			if(editMode==Constant.EDIT_ONCE){
				System.out.println("\tReuse the processShowDataBasedFromThreeColumnCombobox() method for update!"+CLASS_NAME);
			
				processShowDataBasedFromThreeColumnCombobox();

			
			}
			else if(editMode==Constant.EDIT_MULTIPLE){
				System.out.println("\tReuse the processShowAllEmployeeDeductionOrEarningDataButton() method for update!"+CLASS_NAME);
				
			
				//-> Set All cells in table NOT editable
				bothEarningDeductionViewPanel.dynamicTable.setAllTablesNotEditable(db,editMode);
				
				//-----------------------------------------------------------------------
				//--> Show Data
				
				if(isEmployerShareDataShown && editMode==Constant.EDIT_MULTIPLE){
					//-> Reuse code
					processShowEmployerShareDialog();
					
					//--> Set necessary UI components
					bothEarningDeductionViewPanel.calculationPanel.setVisible(false);
					
				}
				else{
					//--> Add the needed columns[key] and their values for CONDITION.
					ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
					
					//--> Add condition if regular or contractual
					conditionColumnAndValueList.add(new SelectConditionInfo(
							"HiredAs",
							(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
						)
					);
					
					//--> CHECKS IF the selected item of both payroll and department is NOT ALL.
					if(isShowDataBasedOnCombobox && !bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
						conditionColumnAndValueList.add(
								new SelectConditionInfo
								(
									util.addSlantApostropheToString(preferredTableName)+"."+util.addSlantApostropheToString(db.payrollDateTableColumnNames[0]), 
									util.convertDateReadableToYyyyMmDdDate(bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString())
								)
						);// Table Name: deductions/earnings	Column Name: PayrollDate
						
					}
					if(isShowDataBasedOnCombobox && !bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
						conditionColumnAndValueList.add(new SelectConditionInfo(
								util.addSlantApostropheToString(db.tableNameDepartment)+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[1]), 
								bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString()
						));// Table Name: employee		Column Name: Department
					}
					//-----------------------------------------------------------------------
					
					//--> Why use temp, since when reusing the code of process Show All, is showDataBAsedOnCombobox boolean will be set to false.
					boolean isTempBoolean=isShowDataBasedOnCombobox;
					//--> Reuse code
					processShowAllEmployeeDeductionOrEarningDataButton(conditionColumnAndValueList);
					
					isShowDataBasedOnCombobox=isTempBoolean;
				}
			}
			
			//--> Set edit boolean
			Utilities.getInstance().isEdit=false;
			
			
			
			//--> Set necessary UI components
			bothEarningDeductionViewPanel.columnComboBox.setEnabled(true);
			bothEarningDeductionViewPanel.payrollDateComboBox.setEnabled(true);
			bothEarningDeductionViewPanel.departmentComboBox.setEnabled(true);
	
		}
		
		
		//--------------------------------------------------------------------------------------------
		//--> Pop up dialog when there are no edited.
		else{
			mainFrame.showOptionPaneMessageDialog(
					"You have not edited anything", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	/**
	 * Execute the process that will happen when you click the cancel button.
	 */
	private void processsCancelButton(){
		System.out.println();
		System.out.println(THIS_CLASS_NAME+": Cancel edit."+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		String preferredTableName=(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.tableNameEarnings:db.tableNameEarningsContractual
				:
				db.tableNameDeductions;
		
		
		if(editMode==Constant.EDIT_ONCE){
			System.out.println("\tReuse CODEDDEEE!!!---> CANCEL: EDIT ONCE"+CLASS_NAME);
			
			
			//-> Set All cells in table NOT editable
			bothEarningDeductionViewPanel.fullScreenTable.setAllTablesNotEditable(db,editMode);
			
			//--> Update Top Left Panel
			bothEarningDeductionViewPanel.updateTopLeftPanel((bothEarningDeductionViewPanel instanceof EarningViewPanel)?1:2);

			//--> Set visible calculate panel
			bothEarningDeductionViewPanel.calculationPanel.setVisible(false);
			
			//--> Reuse Code
			processShowDataBasedFromThreeColumnCombobox();
			
			//--> Show the hideEMplyerShare btn when the emplpoyer share data is shown when edit is cicked
			if(isEmployerShareDataShown){
				bothEarningDeductionViewPanel.updateTopLeftPanel(2);
				bothEarningDeductionViewPanel.leftTopPanel.remove(bothEarningDeductionViewPanel.showEmployerShareBtn);
						
			}
			
			
		}
		else if(editMode==Constant.EDIT_MULTIPLE){
			//-> Set All cells in table NOT editable
			bothEarningDeductionViewPanel.dynamicTable.setAllTablesNotEditable(db,editMode);
			
			System.out.println("\tReuse CODEDDEEE!!!: Cancel Edit Multiple: Is SHow Data based from Combobox? ->"+isShowDataBasedOnCombobox+CLASS_NAME);
			
			
			
			//-----------------------------------------------------------------------
			
			//--> When you click the cancel and what is shown is the EMPLOYER SHARE DATA.
			if(isEmployerShareDataShown){
				
				// Reuse code
				processShowEmployerShareDialog();
				//-------------------------
				
				//--> Set necessary UI components
				bothEarningDeductionViewPanel.calculationPanel.setVisible(false);
				
				//--> Update Top Left Panel
				bothEarningDeductionViewPanel.updateTopLeftPanel((bothEarningDeductionViewPanel instanceof EarningViewPanel)?1:2);
				bothEarningDeductionViewPanel.leftTopPanel.remove(bothEarningDeductionViewPanel.showEmployerShareBtn);
				
			}
//			//--> When you click the cancel and what is shown is the DEDUCTION DATA.
			else{

				//--> Add the needed columns[key] and their values for CONDITION.
				ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
				//--> Add condition if regular or contractual
				conditionColumnAndValueList.add(new SelectConditionInfo(
						"HiredAs",
						(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
					)
				);
				
				
				//--> CHECKS IF the selected item of both payroll and department is NOT ALL.
				if(isShowDataBasedOnCombobox && !bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
					conditionColumnAndValueList.add(
							new SelectConditionInfo
							(
								util.addSlantApostropheToString(preferredTableName)+"."+util.addSlantApostropheToString(db.payrollDateTableColumnNames[0]), 
								util.convertDateReadableToYyyyMmDdDate(bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString())
							)
					);// Table Name: deductions/earnings	Column Name: PayrollDate
					
				}
				if(isShowDataBasedOnCombobox && !bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
					conditionColumnAndValueList.add(new SelectConditionInfo(
							util.addSlantApostropheToString(db.tableNameDepartment)+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[1]), 
							bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString()
					));// Table Name: employee		Column Name: Department
				}
				//-------------------------
				
				//--> Why use temp, since when reusing the code of process Show All, is showDataBAsedOnCombobox boolean will be set to false.
				boolean isTempBoolean=isShowDataBasedOnCombobox;
				//--> Reuse code
				processShowAllEmployeeDeductionOrEarningDataButton(conditionColumnAndValueList);
				
				isShowDataBasedOnCombobox=isTempBoolean;

				//-------------------------
				
				//--> Set necessary UI components
				//--> Update Top Left Panel
				bothEarningDeductionViewPanel.updateTopLeftPanel((bothEarningDeductionViewPanel instanceof EarningViewPanel)?1:2);
				bothEarningDeductionViewPanel.leftTopPanel.remove(bothEarningDeductionViewPanel.hideEmployerShareBtn);

			}
			
		}
	
		//--> Set edit boolean
		Utilities.getInstance().isEdit=false;
		
		//--> Set necessary UI components
		bothEarningDeductionViewPanel.columnComboBox.setEnabled(true);
		bothEarningDeductionViewPanel.payrollDateComboBox.setEnabled(true);
		bothEarningDeductionViewPanel.departmentComboBox.setEnabled(true);
		

		
	}
	
	/**
	 * Execute calculation/generation of automative values.
	 * @param calculationMode
	 */
	private void processCalculate(int calculationMode){
		//--> Get an array of selected row indices/index.
		int [] selectedTableRowIndexList=Utilities.getInstance().getSelectedIndexListBasedFromTableConvertedToModel(table);
				
		if(selectedTableRowIndexList!=null && selectedTableRowIndexList.length>0){
			Database db= Database.getInstance();
			Utilities util = Utilities.getInstance();
			ReusableTable dynamicTable=bothEarningDeductionViewPanel.dynamicTable;
			ReusableTable fixTable=null;
			ReusableTable fullScreenTable=bothEarningDeductionViewPanel.fullScreenTable;
			ReusableTable tableToBeCalculated=null;
			
			//-------------------------------------

			int []columnIndexToBeCalculated=null; 
			int columnIndexTotal=dynamicTable.getModel().getColumnCount()-1; // dynamic Table column index; Total Deduction.
			int columnIndexSalary=-1; // fixed table coliumn index: Salary.
			boolean isCalculate=true;
			int numOfColumnsNotNeeded=7; // First Columns not needed: {Earning/Deduction ID, PayrollDate, EmployeeID, FamilyName, FirstName, Department, Monthly Fixed Salary}
			
			//-------------------------------------
		
			switch(editMode){
				//--> When Editing only One Column.
				case Constant.EDIT_ONCE:{
					fixTable=fullScreenTable;
					tableToBeCalculated=fullScreenTable;
					//--> Fullscreen table coliumn index: Salary. Depends if the employer share data is shown or deduction data is shown.
					columnIndexSalary=4; // Column Index Salary
					columnIndexToBeCalculated=new int[1];
					columnIndexToBeCalculated[0]=tableToBeCalculated.getColumnCount();  // full screen column index to be calculated
					
					if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
						columnIndexToBeCalculated[0]+=1;
					}
					
					// Note: tableToBeCalculated.getColumnCount() and tableToBeCalculated.getModel().getColumnCount()
					break;
				}
				//--> When Editing Multiple Column.
				case Constant.EDIT_MULTIPLE:{
					
					
					fixTable=bothEarningDeductionViewPanel.fixedTable;
					tableToBeCalculated=dynamicTable;
					columnIndexSalary=4; // fixed table coliumn index: Salary.
					
					//---------------------------------------------------------------------------------
					//--> EARNING Calculation/Generation
					if(calculationMode==Constant.CALCULATE_REGULAR_PAY_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate Regular Pay."+CLASS_NAME);
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.REG_PAY_COLUMN_INDEX+numOfColumnsNotNeeded;
						}
					}
					else if(calculationMode==Constant.CALCULATE_ECOLA_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate ECOLA."+CLASS_NAME);
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.ECOLA_COLUMN_INDEX+numOfColumnsNotNeeded;
						}
					}
					else if(calculationMode==Constant.CALCULATE_LAUNDRY_ALLOWANCE_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate LAUNDRY ALLOWANCE."+CLASS_NAME);
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.LAUNDRY_ALLOWANCE_COLUMN_INDEX+numOfColumnsNotNeeded;
						}
					}
					else if(calculationMode==Constant.CALCULATE_LONGEVITY_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate LONGEVITY ALlowance."+CLASS_NAME);
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.LONGEVITY_COLUMN_INDEX+numOfColumnsNotNeeded;
						}
					}
					
					else if(calculationMode==Constant.CALCULATE_RICE_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate RICE."+CLASS_NAME);
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.RICE_COLUMN_INDEX+numOfColumnsNotNeeded;
						}
					}
					else if(calculationMode==Constant.CALCULATE_RATE_PER_DAY_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate SALARY RATE PER DAY."+CLASS_NAME);
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.CONTRACTUAL_RATE_PER_DAY_INDEX+numOfColumnsNotNeeded;
						}
					}
					
					else if(calculationMode==Constant.CALCULATE_SUB_TOTAL_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate Sub Total."+CLASS_NAME);
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.CONTRACTUAL_SUB_TOTAL_INDEX+numOfColumnsNotNeeded;
						}
					}
					
					//-----------------------------------------------------------------------------------
					//--> DEDUCTION Calculation/Generation
					else if(calculationMode==Constant.CALCULATE_SSS_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate SSS Cont."+CLASS_NAME);
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.SSS_CONT_COLUMN_INDEX+numOfColumnsNotNeeded;
							if(isEmployerShareDataShown){
								columnIndexToBeCalculated[i]=0+numOfColumnsNotNeeded;
							}
						}
					}
					else if(calculationMode==Constant.CALCULATE_MEDICARE_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate Medicare/PhilHealth."+CLASS_NAME);
						
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.MEDICARE_COLUMN_INDEX+numOfColumnsNotNeeded;
							if(isEmployerShareDataShown){
								columnIndexToBeCalculated[i]=2+numOfColumnsNotNeeded;
							}
						}
					}
					else if(calculationMode==Constant.CALCULATE_ASEMCO_BCCI_OCCCI_DBP_CFI_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate Asemco/BCCI/OCCCI/DBP/CFI."+CLASS_NAME);
						
						int [] asemcoOcciBcciDbpListIndex={Constant.ASEMCO_COLUMN_INDEX,
								Constant.OCCCI_COLUMN_INDEX,
								Constant.DBP_COLUMN_INDEX,
								Constant.BCCI_COLUMN_INDEC,
								Constant.CFI_COLUMN_INDEX}; //Asemco, Occi, Dbp, BccLoan, CFI
						
						columnIndexToBeCalculated=new int[asemcoOcciBcciDbpListIndex.length];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=asemcoOcciBcciDbpListIndex[i]+numOfColumnsNotNeeded;
						}
					}
					else if(calculationMode==Constant.CALCULATE_PAGIBIG_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate Pagibig/HDMF."+CLASS_NAME);
						
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.PAGIBIG_COLUMN_INDEX+numOfColumnsNotNeeded;
							if(isEmployerShareDataShown){
								columnIndexToBeCalculated[i]=1+numOfColumnsNotNeeded;
							}
						}
					}
					
					else if(calculationMode==Constant.CALCULATE_ST_PETER_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate St. Peter."+CLASS_NAME);
						
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.ST_PETER_COLUMN_INDEX+numOfColumnsNotNeeded;
						}
					}
					else if(calculationMode==Constant.CALCULATE_UNION_DUES_VALUE){
						System.out.println(THIS_CLASS_NAME+"Calculate Union Dues."+CLASS_NAME);
						
						
						columnIndexToBeCalculated=new int[1];
						for(int i=0;i<columnIndexToBeCalculated.length;i++){
							columnIndexToBeCalculated[i]=Constant.UNION_DUES_COLUMN_INDEX+numOfColumnsNotNeeded;
						}
					}


					break;
				}
				default:{
					break;
				}
			}
			
			//-------------------------------------
			
			//--> Checks if the clicked calculation matched chosen column to edit. If edit mode is ONCE.
			if(editMode==Constant.EDIT_ONCE && !isChosenCalculationMatchedTheChosenColumnToBeEdited(calculationMode)){
				isCalculate=false;
			}
			//--> Checks that if what is shown in table is employershare data, only 3 calculation is allowed[SSS Cont, Pag-ibig Cont, Medicare,]. If edit mode is MULTIPLE.
			else if(editMode==Constant.EDIT_MULTIPLE &&
					isEmployerShareDataShown &&
					!(calculationMode==Constant.CALCULATE_SSS_VALUE || calculationMode==Constant.CALCULATE_PAGIBIG_VALUE || calculationMode==Constant.CALCULATE_MEDICARE_VALUE)){
				isCalculate=false;
			}
			
			
			//-------------------------------------
			
			if(isCalculate){
				//--> Calculate the needed value and assign it to the field of phic in table.
				tableToBeCalculated=calculation(
						db, calculationMode,
						tableToBeCalculated, fixTable, columnIndexSalary,
						columnIndexToBeCalculated, columnIndexTotal,numOfColumnsNotNeeded,
						editMode
				);
			}
			else{
				mainFrame.showOptionPaneMessageDialog("You cannot use this method of calculation.", JOptionPane.ERROR_MESSAGE);
			}
			
		
		}
		else{
			mainFrame.showOptionPaneMessageDialog("You have not selected anything!", JOptionPane.ERROR_MESSAGE);
		}
	}
//	/**
//	 * Execute the process that will happen when add calculate sss button is clicked.
//	 */
//	private void processCalculateSSS(){
//		System.out.println(THIS_CLASS_NAME+"Calculate SSS."+CLASS_NAME);
//		
//		if(selectedTableRowIndexList!=null && selectedTableRowIndexList.length>0){
//			Database db= Database.getInstance();
//			Utilities util= Utilities.getInstance();
//			ReusableTable dynamicTable=bothEarningDeductionViewPanel.dynamicTable;
//			ReusableTable fixTable=null;
//			ReusableTable fullScreenTable=bothEarningDeductionViewPanel.fullScreenTable;
//			ReusableTable table=null;
//			
//
//			int columnIndexSSS=-1; ////[NEED TO CHANGE,NOT THE SAME IN ALL CALCULATION!] dynamic table column index: SSS.
//			int columnIndexTotal=dynamicTable.getModel().getColumnCount()-1; // dynamic Table column index; Total Deduction.
//			int columnIndexSalary=-1; // fixed table coliumn index: Salary.
//
//			
//			switch(editMode){
//				case Constant.EDIT_ONCE:{
//					fixTable=fullScreenTable;
//					table=fullScreenTable;
//					columnIndexSSS=table.getColumnCount()-1; //[NEED TO CHANGE,NOT THE SAME IN ALL CALCULATION!] fullscreen table column index: SSS.
//					columnIndexSalary=table.getColumnCount()-2; // fullscreen table coliumn index: Salary.
//					break;
//				}
//				case Constant.EDIT_MULTIPLE:{
//					fixTable=bothEarningDeductionViewPanel.fixedTable;
//					table=dynamicTable;
//					columnIndexSSS=0; //[NEED TO CHANGE,NOT THE SAME IN ALL CALCULATION!] dynamic table column index: SSS.
//					columnIndexSalary=5; // fixed table coliumn index: Salary.
//					break;
//				}
//				default:{
//					break;
//				}
//			}
//			
////			//--> Calculate the SSS and assign it to the field of phic in table.
//			table=calculation(db, Constant.CALCULATE_SSS,
//					table, fixTable, columnIndexSalary,
//					columnIndexSSS, columnIndexTotal);
//		
//		}
//		else{
//			mainFrame.showOptionPaneMessageDialog("You have not selected anything!", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//	/**
//	 * Execute the process that will happen when calculate PHIC/Philhealth button is clicked.
//	 */
//	private void processCalculatePHIC(){
//		System.out.println(THIS_CLASS_NAME+"Calculate PHIC/PhilHealth."+CLASS_NAME);
//		
//		if(selectedTableRowIndexList!=null && selectedTableRowIndexList.length>0){
//			Database db= Database.getInstance();
//			Utilities util= Utilities.getInstance();
//			ReusableTable dynamicTable=bothEarningDeductionViewPanel.dynamicTable;
//			ReusableTable fixTable=null;
//			ReusableTable fullScreenTable=bothEarningDeductionViewPanel.fullScreenTable;
//			ReusableTable table=null;
//			
//			int columnIndexPHIC=-1;  //[NEED TO CHANGE,NOT THE SAME IN ALL CALCULATION]
//			int columnIndexTotal=dynamicTable.getModel().getColumnCount()-1; // dynamic Table column index; Total Deduction.
//			int columnIndexSalary=-1; 
//
//			
//			switch(editMode){
//				case Constant.EDIT_ONCE:{
//					fixTable=fullScreenTable;
//					table=fullScreenTable;
//					columnIndexPHIC=table.getColumnCount()-1; //[NEED TO CHANGE,NOT THE SAME IN ALL CALCULATION!] fullscreen table column index: SSS.
//					columnIndexSalary=table.getColumnCount()-2; // fullscreen table coliumn index: Salary.
//					break;
//				}
//				case Constant.EDIT_MULTIPLE:{
//					fixTable=bothEarningDeductionViewPanel.fixedTable;
//					table=dynamicTable;
//					columnIndexPHIC=1; //[NEED TO CHANGE,NOT THE SAME IN ALL CALCULATION!] dynamic table column index: Medicare.
//					columnIndexSalary=5; // fixed table coliumn index: Salary.
//					break;
//				}
//				default:{
//					break;
//				}
//			}
//			
//			//--> Calculate the phic and assign it to the field of phic in table.
//			table=calculation(db, Constant.CALCULATE_PHIC,
//					table, fixTable, columnIndexSalary,
//					columnIndexPHIC, columnIndexTotal);
//		
//			
//		}
//		else{
//			mainFrame.showOptionPaneMessageDialog("You have not selected anything!", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//	
	
	/**
	 * Process what will happen when SHOW employer share button is clicked.
	 */
	private void processShowEmployerShareDialog(){
		System.out.println(THIS_CLASS_NAME+"SHOW Employer Share Data."+CLASS_NAME);
		
		Database db= Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		//> Show employer share when the it is based on three combo boxes and EDIT ONCE.
		if(editMode==Constant.EDIT_ONCE){
			System.out.println("\t EDIT ONCE, show employer share data");
			
			isEmployerShareDataShown=true; // assign true BEFORE all process when edit mode is ONCE.
			
			
			// Reuse code
			processShowDataBasedFromThreeColumnCombobox();
			
		}
		//> Show employer share when it is based on show ALL and EDIT MULTIPLE.
		else if(editMode==Constant.EDIT_MULTIPLE){
			System.out.println("\t EDIT MULTIPLE, show employer share data");
			
			
			//----------------------------------------------------------------------------------
			
			//--> Add the needed columns[key] and their values for CONDITION.
			ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
			if(isShowDataBasedOnCombobox){
				//--> CHECKS IF the selected item of both payroll and department is NOT ALL.
				if(!bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
					conditionColumnAndValueList.add(
							new SelectConditionInfo
							(
								util.addSlantApostropheToString(db.tableNameDeductions)+"."+util.addSlantApostropheToString(db.payrollDateTableColumnNames[0]), 
								util.convertDateReadableToYyyyMmDdDate(bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString())
							)
					);// Table Name: deductions/earnings	Column Name: PayrollDate
					
				}
				if(!bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
					conditionColumnAndValueList.add(new SelectConditionInfo(
							util.addSlantApostropheToString(db.tableNameDepartment)+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[1]), 
							bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString()
					));// Table Name: employee		Column Name: Department
				}
			}
			else{
				conditionColumnAndValueList=null;
			}
			
			
			//----------------------------------------------------------------------------------
			//--> Store needed values for inner join.Sample: deductions.EmployeeID = employee.EmployeeID 
			String tableNameDeductionApos=util.addSlantApostropheToString(db.tableNameDeductions),
					tableNameEmployeeApos=util.addSlantApostropheToString(db.tableNameEmployee),
					tableNameDepartmentApos=util.addSlantApostropheToString(db.tableNameDepartment),
					tableNameEmployerShareApos=util.addSlantApostropheToString(db.tableNameEmployerShare),
					
					employeeIDApos=util.addSlantApostropheToString(db.employeeTableColumnNames[0]),
					departmentIDApos=util.addSlantApostropheToString(db.departmentTableColumnNames[0]),
					employerShareIDApos=util.addSlantApostropheToString(db.employerShareTableColumnNames[0]),
					deductionIDApos=util.addSlantApostropheToString(db.deductionTableColumnNames[0]);
			
			String[] joinColumnCompareList={
						tableNameDeductionApos+"."+employeeIDApos+"="+tableNameEmployeeApos+"."+employeeIDApos, // 		`deductions`.`EmployeeID`=`employee`.`EmployeeID`
						tableNameDepartmentApos+"."+departmentIDApos+"="+tableNameEmployeeApos+"."+departmentIDApos,// 	`department`.`DepartmentID`=`employee`.`DepartmentID`
						tableNameDeductionApos+"."+deductionIDApos+"="+tableNameEmployerShareApos+"."+employerShareIDApos // 		`deductions`.`DeductionID`=`employershare`.`ID`
				};
						
						
			//----------------------------------------------------------------------------------
			//--> Process necessary columns
			
			//> Get desired columns for deduction table
			String [] desiredColumnsFromDeductionTable={
				db.deductionTableColumnNames[0], // DeductionID
				db.tableNameDeductions+"."+db.deductionTableColumnNames[1],//PayrollDate,
				db.tableNameDeductions+"."+db.deductionTableColumnNames[2], // EmployeeID
			};
			
			
			//> Get desired columns from employee table
			String[] desiredColumnsFromEmployeeTable={
				db.departmentTableColumnNames[1],// Department
				db.employeeTableColumnNames[11],// MonthlyBasicSalary
				db.employeeTableColumnNames[2], // FamilyName
				db.employeeTableColumnNames[3], // FirstName
				
			};
			
			//> Get desired columns for employershare table.
			String[] desiredColumnsFromEmployerShareTable={
					db.employerShareTableColumnNames[3], // SSSEr
					util.addSlantApostropheToString(db.employerShareTableColumnNames[4]), // Pag-ibigEr
					db.employerShareTableColumnNames[5], // MedicareEr
			};
			//-------------------------
			neededColumnsForFixedDynamicTable=new String[
                 desiredColumnsFromDeductionTable.length +
                 desiredColumnsFromEmployeeTable.length  +
                 desiredColumnsFromEmployerShareTable.length
             ];
			
			for(int i=0,dedIndex=0,emIndex=0,emShareIndex=0;i<neededColumnsForFixedDynamicTable.length;i++){
				if(i>=0 && i<desiredColumnsFromDeductionTable.length){
					neededColumnsForFixedDynamicTable[i]=desiredColumnsFromDeductionTable[dedIndex];
					dedIndex++;
				}
				else if(i>=desiredColumnsFromDeductionTable.length && i<(desiredColumnsFromDeductionTable.length+desiredColumnsFromEmployeeTable.length)){
					neededColumnsForFixedDynamicTable[i]=desiredColumnsFromEmployeeTable[emIndex];
					emIndex++;
				}
				else if(i>=(desiredColumnsFromDeductionTable.length+desiredColumnsFromEmployeeTable.length) && i<(desiredColumnsFromDeductionTable.length+desiredColumnsFromEmployeeTable.length+desiredColumnsFromEmployerShareTable.length)){
					neededColumnsForFixedDynamicTable[i]=desiredColumnsFromEmployerShareTable[emShareIndex];
					emShareIndex++;
				}
			}
			
			//----------------------------------------------------------------------------------
			//--> Add condition if regular or contractual
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"HiredAs",
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
				)
			);
			
			//--> Get data from database for fixed table and update.
			db.selectDataInDatabase(
					new String[]{db.tableNameDeductions,db.tableNameEmployee,db.tableNameDepartment,db.tableNameEmployerShare}, 
					neededColumnsForFixedDynamicTable, 
					conditionColumnAndValueList, 
					joinColumnCompareList,
					new OrderByInfo(
						new String[]{
								db.payrollDateTableColumnNames[0], // Payroll Date
								db.employeeTableColumnNames[2], // LastName
								db.employeeTableColumnNames[3]}, // FirstName
						"ASC"
					),
					Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
					);
				
			//----------------------------------------------------------------------------------
			
			//--> Update Dynamic Table
			int []dynamicTableHideColumnList={0,0,0,0,0,0,0};
			bothEarningDeductionViewPanel.dynamicTable.isAutoResize=false;
			bothEarningDeductionViewPanel.dynamicTable.updateTable(db);
			System.out.println("\t HIIIDEEEE MODE"+CLASS_NAME);
			bothEarningDeductionViewPanel.dynamicTable.hideColumns(dynamicTableHideColumnList);
			
			bothEarningDeductionViewPanel.dynamicTable.setSelectionMode(
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			
			//----------------------------------------------------------------------------------
			
			//--> Update Fixed Table, No updateTable in fixedtable since fixedtable and dynamic table has the same table model.
			int []fixedTableHideColumnList=new int[neededColumnsForFixedDynamicTable.length-dynamicTableHideColumnList.length];		
			for(int i=0;i<fixedTableHideColumnList.length;i++){
				fixedTableHideColumnList[i]=dynamicTableHideColumnList.length;
			}
			bothEarningDeductionViewPanel.fixedTable.hideColumns(fixedTableHideColumnList);
			bothEarningDeductionViewPanel.fixedTable.hideColumns(new int[]{0});
			if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
				bothEarningDeductionViewPanel.fixedTable.hideColumns(new int[]{3});
			}
			
			bothEarningDeductionViewPanel.fixedTable.setSelectionMode(
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			//------------------------------------------------------------------------

			isEmployerShareDataShown=true; // assign true after all process when edit mode is MULTIPLE.
			
			
			//------------------------------------------------------------------------
			//--> Process needed columns for TOTAL TABLE
			String[]neededColumnsForTotalTable=new String[db.employerShareTableColumnNames.length-3+1];
			for(int i=0,j=3;i<neededColumnsForTotalTable.length;i++){
				if(i==0){
					neededColumnsForTotalTable[i]="SUM("+util.addSlantApostropheToString(db.employeeTableColumnNames[11])+")as `OverallBasicSalary`";
				}
				else{
					neededColumnsForTotalTable[i]="SUM("+util.addSlantApostropheToString(db.employerShareTableColumnNames[j])+")as `Total"+db.employerShareTableColumnNames[j]+"`";
					j++;
				}
			}
			
			
			//--> Process Inner Join list. Only when employer share is shown.
			joinColumnCompareList=new String[]{
					db.tableNameEmployerShare+"."+db.employeeTableColumnNames[0]+"="+db.tableNameEmployee+"."+db.employeeTableColumnNames[0], // 		` employee.EmployeeID=deductions.EmployeeID
			};
			
			//--> Execute process of total values.
			executeProcessOfValuesOfTotalTable(
				db, util, table,
				new String[]{db.tableNameEmployerShare,db.tableNameEmployee}, db.employerShareTableColumnNames[0],
				neededColumnsForTotalTable,
				joinColumnCompareList, editMode
			);
			
			
			
			
		}
		
		
		//--> Set necessary UI components
		boolean isBoolean=true;
		bothEarningDeductionViewPanel.hideEmployerShareBtn.setVisible(isBoolean);
		
		
		bothEarningDeductionViewPanel.updateTopLeftPanel(2);
		bothEarningDeductionViewPanel.leftTopPanel.remove(bothEarningDeductionViewPanel.showEmployerShareBtn);
		
	}
	
	/**
	 * Process what will happen when HIDE employer share button is clicked.
	 */
	private void processHideEmployerShareDialog(){
		System.out.println(THIS_CLASS_NAME+"HIDE Employer Share Data."+CLASS_NAME);
		
		//--> Set Employer Share Data Shown to false.
		isEmployerShareDataShown=false;
		Utilities util =Utilities.getInstance();
		
		if(isShowDataBasedOnCombobox){
			System.out.println("\t IS SHOW BASED ON COMBOBOX-> TRUE, hideemployer share data");
			
			if(editMode==Constant.EDIT_ONCE){
				System.out.println("\t\t EDIT ONCE, hide employer share data");
				
				if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
					bothEarningDeductionViewPanel.fullScreenTable.hideColumns(new int[]{7}); // Hide the EmployerShare column.
					bothEarningDeductionViewPanel.totalFullScreenTable.hideColumns(new int[]{2});// Hide TotalEmoloyerShareColumn.
				}
				else{
					bothEarningDeductionViewPanel.fullScreenTable.hideColumns(new int[]{6}); // Hide the EmployerShare column.
					bothEarningDeductionViewPanel.totalFullScreenTable.hideColumns(new int[]{1});// Hide TotalEmoloyerShareColumn.
	
				}
				

				
			}
			else if(editMode==Constant.EDIT_MULTIPLE){
				System.out.println("\t\t EDIT MULTIPLE, hide employer share data");
				Database db= Database.getInstance();
				
				//-----------------------------------------
				//--> Add the needed columns[key] and their values for CONDITION.
				ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
				
				//--> Add condition if regular or contractual
				conditionColumnAndValueList.add(new SelectConditionInfo(
						"HiredAs",
						(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
					)
				);
				
				
				//--> CHECKS IF the selected item of both payroll and department is NOT ALL.
				if(isShowDataBasedOnCombobox && !bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
					conditionColumnAndValueList.add(
							new SelectConditionInfo
							(
								util.addSlantApostropheToString(db.tableNameDeductions)+"."+util.addSlantApostropheToString(db.payrollDateTableColumnNames[0]), 
								util.convertDateReadableToYyyyMmDdDate(bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString())
							)
					);// Table Name: deductions/earnings	Column Name: PayrollDate
					
				}
				if(isShowDataBasedOnCombobox && !bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
					conditionColumnAndValueList.add(new SelectConditionInfo(
							util.addSlantApostropheToString(db.tableNameDepartment)+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[1]), 
							bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString()
					));// Table Name: employee		Column Name: Department
				}
				
				//-----------------------------------------
				//--> Why use temp, since when reusing the code of process Show All, the showDataBAsedOnCombobox boolean will be set to false.
				boolean isTempBoolean=isShowDataBasedOnCombobox;
				//--> Reuse code
				processShowAllEmployeeDeductionOrEarningDataButton(conditionColumnAndValueList);
				
				isShowDataBasedOnCombobox=isTempBoolean;
			}
		}
		else{
//			System.out.println("\t EDIT MULTIPLE, hide employer share data");
			System.out.println("\t IS SHOW BASED ON COMBOBOX-> FALSE, hideemployer share data");
			
			//-----------------------------------------
			//--> Add the needed columns[key] and their values for CONDITION.
			ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
			//--> Add condition if regular or contractual
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"HiredAs",
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
				)
			);
			
			
			// Reuse code
			processShowAllEmployeeDeductionOrEarningDataButton(conditionColumnAndValueList);
		}
		
		
		
		//--> Set necessary UI components
		bothEarningDeductionViewPanel.updateTopLeftPanel(2);
		bothEarningDeductionViewPanel.leftTopPanel.remove(bothEarningDeductionViewPanel.hideEmployerShareBtn);
		
		
		
	}
	private void l______________________________________l(){}
	
	/**
	 * Execute calculation.
	 * @param db
	 * @param calculationMode
	 * @param table
	 * @param fixTable
	 * @param columnIndexSalary
	 * @param columnIndexCalculated
	 * @param columnIndexTotal
	 * @return
	 */
	private ReusableTable calculation(Database db,int calculationMode,ReusableTable table,
			ReusableTable fixTable,int columnIndexSalary,int[] columnIndexCalculated,
			int columnIndexTotal,int numOfColumnsNotNeeded,int editMode){
		
		double calculatedValue=-1;
		//--> Get an array of selected row indices/index.
		int [] selectedTableRowIndexList=Utilities.getInstance().getSelectedIndexListBasedFromTableConvertedToModel(table);
				
		
		for(int i=0,row=-1;i<selectedTableRowIndexList.length;i++){
			//--> Get the row
			row=selectedTableRowIndexList[i];
			double salary=Double.parseDouble(fixTable.getModel().getValueAt(row, columnIndexSalary).toString());
			Date payrollDate=(Date) fixTable.getModel().getValueAt(row, 1);	// PayrollDate
			
			//--> Assign in column to be edited the value.
			//--> Get the column
			for(int colIndex:columnIndexCalculated){
				
				//--> Check if what calculation to use
				//--------------------------------------------------------
				//--> EARNING View Calculation
				if(calculationMode==Constant.CALCULATE_REGULAR_PAY_VALUE){
					calculatedValue=calcuateRegularPayValue(salary,""+payrollDate);
				}
				else if(calculationMode==Constant.CALCULATE_ECOLA_VALUE){
					calculatedValue=calcuateEcolaLALongevityRiceValue(
						db,
						(String) table.getModel().getValueAt(row, 2), // EmployeeID
						1// ECOLA
					);
				}
				else if(calculationMode==Constant.CALCULATE_LAUNDRY_ALLOWANCE_VALUE){
					calculatedValue=calcuateEcolaLALongevityRiceValue(
						db,
						(String) table.getModel().getValueAt(row, 2), // EmployeeID
						2// LAUNDRY ALLOWANCE
					);
				}
				else if(calculationMode==Constant.CALCULATE_LONGEVITY_VALUE){
					calculatedValue=calcuateEcolaLALongevityRiceValue(
						db,
						(String) table.getModel().getValueAt(row, 2), // EmployeeID
						3// LONGEVITY
					);
				}
				else if(calculationMode==Constant.CALCULATE_RICE_VALUE){
					calculatedValue=calcuateEcolaLALongevityRiceValue(
						db,
						(String) table.getModel().getValueAt(row, 2), // EmployeeID
						4// RICE
					);
				}
				
				
				
				else if(calculationMode==Constant.CALCULATE_OVERTIME_VALUE){
					
				}
				
				else if(calculationMode==Constant.CALCULATE_RATE_PER_DAY_VALUE){
					calculatedValue=calculateContractualSalaryRatePerDay(
							db,
							(String) table.getModel().getValueAt(row, 2) // EmployeeID
					); 
				}
				else if(calculationMode==Constant.CALCULATE_SUB_TOTAL_VALUE){
					calculatedValue=calculateContractualSubTotal(
							db,
							row,
							0
					); 
				}
				//--------------------------------------------------------
				//--> DEDUCTION View Calculation
				else if(calculationMode==Constant.CALCULATE_SSS_VALUE){
					calculatedValue=calculateSSSValue(
						db,
						salary,
						(String) table.getModel().getValueAt(row, 2) // EmployeeID
					);
				}
				
				else if(calculationMode==Constant.CALCULATE_MEDICARE_VALUE){
					calculatedValue=calculateMedicareValue(db, salary,payrollDate);
				}
				else if(calculationMode==Constant.CALCULATE_ASEMCO_BCCI_OCCCI_DBP_CFI_VALUE){
					calculatedValue=calculateAsemcoBcciOccciDbpCfi(
						db,
						(String) fixTable.getModel().getValueAt(row, 2), // EmployeeID
						colIndex,
						payrollDate,	// PayrollDate
						numOfColumnsNotNeeded,
						editMode
					);
				}
				else if(calculationMode==Constant.CALCULATE_PAGIBIG_VALUE){
					calculatedValue=calculatePagibigValue(
						db,
						(String) table.getModel().getValueAt(row, 2) // EmployeeID
					);
				}
				else if(calculationMode==Constant.CALCULATE_ST_PETER_VALUE){
					calculatedValue=calculateStPeterValue(
							db,
							(String) table.getModel().getValueAt(row, 2), // EmployeeID
							payrollDate
					);
				}
				else if(calculationMode==Constant.CALCULATE_UNION_DUES_VALUE){
					calculatedValue=calculateUnionDuesValue(
							db,
							(String) table.getModel().getValueAt(row, 2) // EmployeeID
					);
				}
				
				
				
				//--> Debugging Purposes
				System.out.println("\t\tCalculated/Generated Value: "+calculatedValue+CLASS_NAME);
				table.getModel().data[row][colIndex]=calculatedValue;
				
				//--> This assignent process only happens when the employer share data is shown and edit mode is EDIT ONCE since there are two columns to be edited and calculated.
				if(isEmployerShareDataShown && editMode==Constant.EDIT_ONCE){
					table.getModel().data[row][colIndex-1]=tempCalculatedValue;
				}
			}
			
			//--> Assign the total table with the total value after calculating.. Remember: this total process is not incuded when data shown is employer share data.
			if(editMode == Constant.EDIT_MULTIPLE && !isEmployerShareDataShown){
				bothEarningDeductionViewPanel.dynamicTable.getModel().data[row][columnIndexTotal]=
						table.getModel().getCalculatedTotalDynamicTableOnly(row,bothEarningDeductionViewPanel,Utilities.getInstance());
			}
		}
		
		
		//--> Update the state/appearance of table only, not in the database.
//		table.updateTableStateNotContent();
		
		
		
		return table;
	}
	
	/**
	 * This process is executed when the mode for editing is edit ONCE.
	 * @param preferredTableColumnNames
	 * @param util
	 * @param db
	 */
	private void executeEditOnce(Utilities util,Database db){
		System.out.println("\tEDIT ONCE"+CLASS_NAME);
		ReusableTable table=bothEarningDeductionViewPanel.fullScreenTable;
		

		String columnToBeEdited="",columnToBeEditedER="";
		
		//-----------------------------------------------------------------------------
		//--> Get the necessary column names to be edited for.
		for(int i=0;i<table.getModel().getColumnCount();i++){
			if(bothEarningDeductionViewPanel.columnComboBox.getSelectedItem().toString().
					equals(table.getModel().getColumnName(i))){
				columnToBeEdited=util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(i));
				
				break;
			}
		}
		System.out.println("\tEdited: "+columnToBeEdited);
		
		//-----------------------------------------------------------------------------
		//--> Add the Employer Share[ER] Column to be edited when shown.
		int comboboxSelectedColumnIndex= bothEarningDeductionViewPanel.columnComboBox.getSelectedIndex(); // Selected index in dialog combobox column
		
		//> Include the ER column to be edited if the show er data is clicked.
		if(util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel) && isEmployerShareDataShown){
			columnToBeEditedER=util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(table.getModel().getColumnCount()-1));
			
		}
		
		//-----------------------------------------------------------------------------
		//--> Set the table columns that are editable
		table.setTableColumnsThatAreEditable(
			(util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel) && isEmployerShareDataShown)?
					new String[]{columnToBeEdited,columnToBeEditedER}
					:
					new String[]{columnToBeEdited}
		);
		
		//-----------------------------------------------------------------------------
		//--> Update Table
		table.updateTableStateNotContent();
		
		//-----------------------------------------------------------------------------
		//--> Hide
		table.hideColumns(new int[]{0});
//		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
//			table.hideColumns(new int[]{3});
//		}
		
		//--> Hide the ER column when show employer share button is not clicked and selected column is SSS Cont, Pag-ibig Cont and Medicare
		if(util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel) && !isEmployerShareDataShown){ 
			if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
				table.hideColumns(new int[]{7});
			}
			else{
				table.hideColumns(new int[]{6});
			}
		}
		
		//--> Update immediately with search mechanism.
		bothEarningDeductionViewPanel.executeSearchMechanismOfAllTables();
		
		
		System.out.println("\t ISEmployerShareDataShown: "+isEmployerShareDataShown+CLASS_NAME);
	}
	
	/**
	 * This process is executed when the mode for editing is edit MUTLIPLE.
	 * @param db
	 * @param util
	 */
	private void executeEditMultiple(Database db,Utilities util){
		System.out.println("\tEDIT MULTIPLE"+CLASS_NAME);
		
		//--> Process the columns that you want to be edited.
		ReusableTable dynamicTable=bothEarningDeductionViewPanel.dynamicTable;
		int numOfColumnsNotNeeded=7; // First Columns not needed: {Earning/Deduction ID, PayrollDate, EmployeeID, FamilyName, FirstName, Department, Monthly Fixed Salary}
		int totalLengthOfColumnsToBeEdited=
			(isEmployerShareDataShown)?
				dynamicTable.getModel().getColumnCount()-numOfColumnsNotNeeded
				:
				dynamicTable.getModel().getColumnCount()-numOfColumnsNotNeeded-1
		;// This states that all columns in dynamic table is to be edited when employer share data is shown, else all columns in dynamic table is editable except the total when deduction data is shown.
				
		String []columnToBeEditedList=new String[totalLengthOfColumnsToBeEdited];
		for(int i=0,j=numOfColumnsNotNeeded;i<columnToBeEditedList.length;i++,j++){
			String columnName=util.removeSpacesToBeConvertedToCamelCase(dynamicTable.getModel().getColumnName(j));
			columnToBeEditedList[i]=columnName;
//			System.out.println("\tCCCS: "+columnName+CLASS_NAME);
		}
		
		
		//-----------------------------------------------------------------------
		
		//--> Set the table columns that are editable
		dynamicTable.setTableColumnsThatAreEditable(
				columnToBeEditedList
		);
		dynamicTable.updateTableStateNotContent();
		
		//-----------------------------------------------------------------------
		
		//--> Update Dynamic Table
		int []dynamicTableHideColumnList={0,0,0,0,0,0,0};
		bothEarningDeductionViewPanel.dynamicTable.isAutoResize=false;
		bothEarningDeductionViewPanel.dynamicTable.hideColumns(dynamicTableHideColumnList);
//		bothEarningDeductionViewPanel.dynamicTable.setSelectionMode(
//				ListSelectionModel.SINGLE_SELECTION);

		
		//-----------------------------------------------------------------------
		
		//--> Update Fixed Table
		int []fixedTableHideColumnList=new int[neededColumnsForFixedDynamicTable.length-dynamicTableHideColumnList.length];		
		for(int i=0;i<fixedTableHideColumnList.length;i++){
			fixedTableHideColumnList[i]=dynamicTableHideColumnList.length;
		}
		bothEarningDeductionViewPanel.fixedTable.isAutoResize=true;
		bothEarningDeductionViewPanel.fixedTable.hideColumns(fixedTableHideColumnList);
		bothEarningDeductionViewPanel.fixedTable.hideColumns(new int[]{0});	
//		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
//			bothEarningDeductionViewPanel.fixedTable.hideColumns(new int[]{3});
//		}
//		bothEarningDeductionViewPanel.fixedTable.setSelectionMode(
//				ListSelectionModel.SINGLE_SELECTION);

		
		//-----------------------------------------------------------------------
		//--> Update immediately with search mechanism.
		bothEarningDeductionViewPanel.executeSearchMechanismOfAllTables();
				
	}
	
	/**
	 * Execute the show data based from column boxes process .
	 * @param db
	 * @param util
	 * @param preferredTableName
	 * @param preferredTableColumnNames
	 */
	private void executeShowDataBasedFromThreeColumnCombobox(Database db,Utilities util,
			String preferredTableName, String[]preferredTableColumnNames){
		
		//--> Add the needed columns[key] and their values for CONDITION.
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		
		//--> Add condition if regular or contractual
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		//--> CHECKS IF the selected item of both payroll and department is NOT ALL.
		if(!bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
			conditionColumnAndValueList.add(
					new SelectConditionInfo
					(
						util.addSlantApostropheToString(preferredTableName)+"."+util.addSlantApostropheToString(db.payrollDateTableColumnNames[0]), 
						util.convertDateReadableToYyyyMmDdDate(bothEarningDeductionViewPanel.payrollDateComboBox.getSelectedItem().toString())
					)
			);// Table Name: deductions/earnings	Column Name: PayrollDate
			
		}
		if(!bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
			conditionColumnAndValueList.add(new SelectConditionInfo(
					util.addSlantApostropheToString(db.tableNameDepartment)+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[1]), 
					bothEarningDeductionViewPanel.departmentComboBox.getSelectedItem().toString()
			));// Table Name: employee		Column Name: Department
		}
		
		//----------------------------------------------------------------------------------------
		
	
		//--> Checks if the column combobox of dialog box is ALL
		if(bothEarningDeductionViewPanel.columnComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
		
			//--> Reuse code.
			processShowAllEmployeeDeductionOrEarningDataButton(conditionColumnAndValueList);
			
			isShowDataBasedOnCombobox=true;
			if(bothEarningDeductionViewPanel.fixedTable.getModel().getRowCount()==0){
				mainFrame.showOptionPaneMessageDialog("There are no available data.", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else{
			//----------------------------------------------------------------------------------------
			//--> Assign what table
			table=bothEarningDeductionViewPanel.fullScreenTable;
			int comboboxSelectedColumnIndex= bothEarningDeductionViewPanel.columnComboBox.getSelectedIndex(); // Selected index in dialog combobox column
			
			//----------------------------------------------------------------------------------------
			
			//--> Get the NECESSARY COLUMNS for the data to be retrieve to.
			//			Why 5, since we want to retrieve the first 5 columns from the preferred table.
			int index=0,
				totalNumberofDesiredColumns=
					(util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel))?9:8; // 8 when the selected column has no ER, 9 when it has ER
			String[]columnNames=new String[totalNumberofDesiredColumns];
			
			//> Get the first 4 columns of deductions table.
			for(;index<3;index++){
				columnNames[index]=util.addSlantApostropheToString(preferredTableName)+"."
						+util.addSlantApostropheToString(preferredTableColumnNames[index]);
			}
			
			//> Get LastName,FirstName and Department,Salary columns, from employee table
			String [] desiredEmplopyeeTableColumns={
					db.employeeTableColumnNames[13],
					((util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
						db.employeeTableColumnNames[11] // MonthlyBasicSalary
						:
						db.employeeTableColumnNames[6] //  WithATM
					),
					db.employeeTableColumnNames[2],
					db.employeeTableColumnNames[3]};
			for(int j=0;j<desiredEmplopyeeTableColumns.length;j++,index++){
				// If DepartmentID , change to Department
				if(desiredEmplopyeeTableColumns[j].equals(db.employeeTableColumnNames[13])){
					columnNames[index]=util.addSlantApostropheToString(db.tableNameDepartment)+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[1]);
				}
				else{
					columnNames[index]=util.addSlantApostropheToString(db.tableNameEmployee)+"."+util.addSlantApostropheToString(desiredEmplopyeeTableColumns[j]);
					
				}
			}
			//> This makes sure that the retrieved last column is the same with the 
			//			selected item in deduction data column combo box.
			for(int j=0;j<preferredTableColumnNames.length;j++){
				if(bothEarningDeductionViewPanel.columnComboBox.getSelectedItem().toString().
						equals(util.convertCamelCaseColumnNamesToReadable(preferredTableColumnNames[j]))){
					
					//> Why tempindex is totalNumberofDesiredColumns-2? since it has ER the ER part is the last column.
					//> Why tempindex is totalNumberofDesiredColumns-1? since it has NO ER, the selected column is the last column.
					int tempIndex=util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel)?totalNumberofDesiredColumns-2:totalNumberofDesiredColumns-1;
					
					columnNames[tempIndex]=util.addSlantApostropheToString(preferredTableName)+"."
									+util.addSlantApostropheToString(preferredTableColumnNames[j]);
					break;
				}
			}	
			//----------------------------------------------------------------------------------------
			
			//> Set the ER column when chosen column index is SSS Cont, Pag-ibig Cont and Medicare.
			int erIndex=-1;
			if(bothEarningDeductionViewPanel instanceof DeductionViewPanel && util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel)){
				switch(comboboxSelectedColumnIndex){
					case Constant.SSS_CONT_COLUMN_INDEX+1:{
						erIndex=3;
						break;
					}
					case Constant.PAGIBIG_COLUMN_INDEX+1:{
						erIndex=4;
						break;
					}
					case Constant.MEDICARE_COLUMN_INDEX+1:{
						erIndex=5;
						break;
					}
					default:
						break;				
				}
				columnNames[columnNames.length-1]=util.addSlantApostropheToString(db.tableNameEmployerShare)+"."
						+util.addSlantApostropheToString(db.employerShareTableColumnNames[erIndex]);
		
			}
			
			
			//--> For Debugging purposes
			System.out.println("\tSelected Column Names to be shown:"+CLASS_NAME);
			for(int i=0;i<columnNames.length;i++){
				System.out.println("\t\t"+columnNames[i]+CLASS_NAME);
			}
			
			//----------------------------------------------------------------------------------------
			//--> Store needed values for inner join.Sample: deductions.EmployeeID = employee.EmployeeID
			
			String tableNameDeductionEarningApos=util.addSlantApostropheToString(preferredTableName),
					tableNameEmployeeApos=util.addSlantApostropheToString(db.tableNameEmployee),
					tableNameDepartmentApos=util.addSlantApostropheToString(db.tableNameDepartment),
					tableNameEmployerShareApos=util.addSlantApostropheToString(db.tableNameEmployerShare),
					
					employeeIDApos=util.addSlantApostropheToString(db.employeeTableColumnNames[0]),
					departmentIDApos=util.addSlantApostropheToString(db.departmentTableColumnNames[0]),
					employerShareIDApos=util.addSlantApostropheToString(db.employerShareTableColumnNames[0]),
					deductionIDApos=util.addSlantApostropheToString(db.deductionTableColumnNames[0]);
			
			String[] joinColumnCompareList=
				(bothEarningDeductionViewPanel instanceof DeductionViewPanel && util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel))?		
					new String[]{
						tableNameDeductionEarningApos+"."+employeeIDApos+"="+tableNameEmployeeApos+"."+employeeIDApos, // 		`deductions`.`EmployeeID`=`employee`.`EmployeeID`
						tableNameDepartmentApos+"."+departmentIDApos+"="+tableNameEmployeeApos+"."+departmentIDApos,// 	`department`.`DepartmentID`=`employee`.`DepartmentID`
						tableNameDeductionEarningApos+"."+deductionIDApos+"="+tableNameEmployerShareApos+"."+employerShareIDApos // 		`deductions`.`DeductionID`=`employershare`.`ID`
					}
					:
					new String[]{
						tableNameDeductionEarningApos+"."+employeeIDApos+"="+tableNameEmployeeApos+"."+employeeIDApos, // 		`deductions`.`EmployeeID`=`employee`.`EmployeeID`
						tableNameDepartmentApos+"."+departmentIDApos+"="+tableNameEmployeeApos+"."+departmentIDApos// 	`department`.`DepartmentID`=`employee`.`DepartmentID`	
					}
			
			;
			//----------------------------------------------------------------------------------------
			//--> Get the needed table name list, may differ when employer share is shown
			String []neededTableNameList=
					(bothEarningDeductionViewPanel instanceof DeductionViewPanel && util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel))?
							new String[]{preferredTableName,db.tableNameEmployee,db.tableNameDepartment,db.tableNameEmployerShare}
							:
							new String[]{preferredTableName,db.tableNameEmployee,db.tableNameDepartment};
		
			//----------------------------------------------------------------------------------------					
			//--> Get the data from database
							
			db.selectDataInDatabase(
					neededTableNameList, 
					columnNames, 
					conditionColumnAndValueList, 
					joinColumnCompareList,
					new OrderByInfo(
						new String[]{
								db.payrollDateTableColumnNames[0], // Payroll Date
								db.employeeTableColumnNames[2], // LastName
								db.employeeTableColumnNames[3]}, // FirstName
						"ASC"
					),
					Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND);
			
			//--> Update table
			bothEarningDeductionViewPanel.fullScreenTable.isAutoResize=true;
			bothEarningDeductionViewPanel.fullScreenTable.updateTable(db);
//			bothEarningDeductionViewPanel.fullScreenTable.setSelectionMode(
//					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			bothEarningDeductionViewPanel.fullScreenTable.setSelectionMode(
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			
//			//-> Set All cells in table NOT editable
			bothEarningDeductionViewPanel.fullScreenTable.setAllTablesNotEditable(db,editMode);
			
			
			//--> Hide
			bothEarningDeductionViewPanel.fullScreenTable.hideColumns(new int[]{0});
//			if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
//				bothEarningDeductionViewPanel.fullScreenTable.hideColumns(new int[]{3});
//			}
			
			//-->The ER column is included in the table. Hide the ER column if the show employer share is not clicked.
			if(util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel) && !isEmployerShareDataShown){
				if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
					bothEarningDeductionViewPanel.fullScreenTable.hideColumns(new int[]{7}); 
				}
				else{
					bothEarningDeductionViewPanel.fullScreenTable.hideColumns(new int[]{6}); 
				}
			}

			//----------------------------------------------------------------------------------------					
			//--> Update Row Count Label
			
			bothEarningDeductionViewPanel.rowCountLabel.setText("Row Count: "+bothEarningDeductionViewPanel.fullScreenTable.getModel().getRowCount());
			
			//----------------------------------------------------------------------------------------					
			//--> Set EDIT Mode.
			
			editMode=Constant.EDIT_ONCE;
			
			//----------------------------------------------------------------------------------------					
			//--> Process the retrieving of data for TOTAL TABLE.
			
			ReusableTable table = bothEarningDeductionViewPanel.fullScreenTable;
			String selectedColumnNameFromCombobox=bothEarningDeductionViewPanel.columnComboBox.getSelectedItem().toString();
				selectedColumnNameFromCombobox=util.removeSpacesToBeConvertedToCamelCase(selectedColumnNameFromCombobox);
			
			//--> Process needed columns for total table.
			String[]neededColumnsForTotalTable=new String[(isEmployerShareDataShown)?3:2];
			neededColumnsForTotalTable[0]="SUM("+db.employeeTableColumnNames[11]+")as `Overall"+db.employeeTableColumnNames[11]+"`";
			neededColumnsForTotalTable[1]="SUM("+util.addSlantApostropheToString(selectedColumnNameFromCombobox)+")as `Total"+selectedColumnNameFromCombobox+"`";
			
			if(isEmployerShareDataShown){
				neededColumnsForTotalTable[2]="SUM("+util.addSlantApostropheToString(db.employerShareTableColumnNames[erIndex])+")as `Total"+db.employerShareTableColumnNames[erIndex]+"`";	
			}
			
			//--> Process Inner Join list. Only when employer share is shown.
			joinColumnCompareList=null;
			if(isEmployerShareDataShown){
				joinColumnCompareList=new String[]{
								tableNameDeductionEarningApos+"."+deductionIDApos+"="+tableNameEmployerShareApos+"."+employerShareIDApos, // 		`deductions`.`DeductionID`=`employershare`.`ID`
								db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+preferredTableName+"."+db.employeeTableColumnNames[0], // 		` employee.EmployeeID=deductions.EmployeeID
				};
			}
			else{
				joinColumnCompareList=new String[]{
						db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+preferredTableName+"."+db.employeeTableColumnNames[0], // 		` employee.EmployeeID=deductions.EmployeeID
				};
			}
								
			//--> Execute the values of total table.
			executeProcessOfValuesOfTotalTable(
				db, util, table,
				(isEmployerShareDataShown)?new String[]{preferredTableName,db.tableNameEmployerShare,db.tableNameEmployee}:new String[]{preferredTableName,db.tableNameEmployee},
				preferredTableColumnNames[0],
				neededColumnsForTotalTable,
				joinColumnCompareList,
				editMode
			);

			
			//----------------------------------------------------------------------------------------					
			//--> Set enabled necessary UI components
			
			boolean isBoolean=true;
			bothEarningDeductionViewPanel.fullScreenTableScrollPane.setVisible(isBoolean);
			
			bothEarningDeductionViewPanel.dynamicTable.setVisible(isBoolean);
			bothEarningDeductionViewPanel.fixedTable.setVisible(isBoolean);
			
			bothEarningDeductionViewPanel.dynamicTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			bothEarningDeductionViewPanel.dynamicTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			bothEarningDeductionViewPanel.editBtn.setVisible(isBoolean);
			bothEarningDeductionViewPanel.showEmployerShareBtn.setVisible(isBoolean);
			
			isShowDataBasedOnCombobox=isBoolean;

			
			
			isBoolean=false;
			bothEarningDeductionViewPanel.dynamicTableScrollPane.setVisible(isBoolean);
			bothEarningDeductionViewPanel.fixedTableScrollPane.setVisible(isBoolean);
			bothEarningDeductionViewPanel.calculationPanel.setVisible(isBoolean);
			
			//--> Update Top Left Panel
			bothEarningDeductionViewPanel.updateTopLeftPanel((bothEarningDeductionViewPanel instanceof EarningViewPanel)?1:2);
			bothEarningDeductionViewPanel.leftTopPanel.remove(bothEarningDeductionViewPanel.hideEmployerShareBtn);
			if(!util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,bothEarningDeductionViewPanel)){
				bothEarningDeductionViewPanel.showEmployerShareBtn.setVisible(false);
			}

			
			
			//--> Checks if there are available data.
			if(bothEarningDeductionViewPanel.fullScreenTable.getModel().getRowCount()==0){
				mainFrame.showOptionPaneMessageDialog("There are no available data.", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	/**
	 * Execute the process of values for total table.
	 * @param db
	 * @param util
	 * @param table
	 * @param tableNameList
	 * @param columnNameID
	 * @param neededColumnsForTotalTable
	 * @param editMode
	 */
	private void executeProcessOfValuesOfTotalTable(Database db, Utilities util,
			ReusableTable table,
			String[] tableNameList,
			String columnNameID,
			String[] neededColumnsForTotalTable,
			String[]joinColumnCompareList,
			int editMode){
		
		//		selectBasedFromColumnsWithConditionOR(String[] tableNameList,
		//		String[]columnNameList, 
		//		ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		
		//selectInnerJoinWithConditionOR(String[] tableNameList,String[]columnNameList, 
		//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
		//		String[] joinColumnCompareList,
		//		OrderByInfo orderInfo)
		
		
		//--> This process is executed if there is an existing data.
		if(table.getModel().getRowCount()>0){
			//--> Process needed columns[key] and their values for CONDITION. In this case get the deduction/earning ID
			
			ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
			for(int i=0;i<table.getModel().getRowCount();i++){
				conditionColumnAndValueList.add(
						new SelectConditionInfo(
								columnNameID,
								table.getModel().getValueAt(i, 0) // Why 0? since we get the deduction/earning id column.
						)
				);
			}
					
			
			
			//--> Process Total Table.
			db.selectDataInDatabase(
				tableNameList,
				neededColumnsForTotalTable,
				conditionColumnAndValueList,
				joinColumnCompareList,
				null,
				Constant.SELECT_INNER_JOIN_WITH_CONDITION_OR
			);
			
			//--> Update total table
			bothEarningDeductionViewPanel.totalFullScreenTable.isAutoResize=
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					(editMode==Constant.EDIT_MULTIPLE && !isEmployerShareDataShown)?false:true
					:
					(bothEarningDeductionViewPanel instanceof EarningViewPanel)?
						true
						:
						(editMode==Constant.EDIT_MULTIPLE && !isEmployerShareDataShown)?false:true
			;
			bothEarningDeductionViewPanel.totalFullScreenTable.updateTable(db);
			bothEarningDeductionViewPanel.totalFullScreenTable.roundOffAllDataToTwoDecimal();
			
			if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
				if(editMode==Constant.EDIT_MULTIPLE){
					if(isEmployerShareDataShown){
						bothEarningDeductionViewPanel.totalFullScreenTable.hideColumns(
								new int[]{0}
						);
					}
					else{
						bothEarningDeductionViewPanel.totalFullScreenTable.hideColumns(
								new int[]{bothEarningDeductionViewPanel.totalFullScreenTable.getModel().getColumnCount()-1}
						);
					}
					
					
					// Hide the TotalNumOfDays, and TotalRatePerDay
					if(bothEarningDeductionViewPanel instanceof EarningViewPanel){
						bothEarningDeductionViewPanel.totalFullScreenTable.hideColumns(
								new int[]{0,0}
						);
					}
				}
				else{
					bothEarningDeductionViewPanel.totalFullScreenTable.hideColumns(
							new int[]{0}
					);
					
					// There must no TotalRatePerDay and TotalNumOfDays when single column.
					if(bothEarningDeductionViewPanel instanceof EarningViewPanel &&
							(bothEarningDeductionViewPanel.columnComboBox.getSelectedIndex()==1 || bothEarningDeductionViewPanel.columnComboBox.getSelectedIndex()==2)){
						bothEarningDeductionViewPanel.totalFullScreenTable.clearAllContentsOfTable();
					}
				}
			}
			
		}
		else{ // When there are no available data
			bothEarningDeductionViewPanel.totalFullScreenTable.clearAllContentsOfTable();
		}
	}
	
	/**
	 * Checks if the chosen calculation/generation button matched the chosen single column to be edit.
	 * @param buttonKey
	 * @return
	 */
	private boolean isChosenCalculationMatchedTheChosenColumnToBeEdited(int calculationMode){
		int selectedComboboxIndex=bothEarningDeductionViewPanel.columnComboBox.getSelectedIndex();
		
		// Why +1? because in the combobox there is one extra which is the empty/ALL string .

		
		//--> EARNING
		if(calculationMode==Constant.CALCULATE_REGULAR_PAY_VALUE && selectedComboboxIndex==Constant.REG_PAY_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate Regular Pay."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_ECOLA_VALUE && selectedComboboxIndex==Constant.ECOLA_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate ECOLA."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_LAUNDRY_ALLOWANCE_VALUE && selectedComboboxIndex==Constant.LAUNDRY_ALLOWANCE_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate LAUNDRY ALLOWANCE."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_LONGEVITY_VALUE && selectedComboboxIndex==Constant.LONGEVITY_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate LONGEVITY."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_RICE_VALUE && selectedComboboxIndex==Constant.RICE_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate RICE."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_RATE_PER_DAY_VALUE && selectedComboboxIndex==Constant.CONTRACTUAL_RATE_PER_DAY_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate Contractual Salary Rate Per Day."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_SUB_TOTAL_VALUE && selectedComboboxIndex==Constant.CONTRACTUAL_SUB_TOTAL_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate Contractual Sub Total."+CLASS_NAME);
			return true;
		}
		
		
		//-------------------------------------------------------------------------------------
		//--> DEDUCTION 
		else if(calculationMode==Constant.CALCULATE_SSS_VALUE && selectedComboboxIndex==Constant.SSS_CONT_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate SSS."+CLASS_NAME);
			return true;
		}
		
		else if(calculationMode==Constant.CALCULATE_MEDICARE_VALUE && selectedComboboxIndex==Constant.MEDICARE_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate PhilHealth."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_ASEMCO_BCCI_OCCCI_DBP_CFI_VALUE && 
			(
				selectedComboboxIndex==(Constant.ASEMCO_COLUMN_INDEX+1) // Why +1? because in the combobox there is one extra which is the empty/ALL string .
				|| selectedComboboxIndex==(Constant.BCCI_COLUMN_INDEC+1) 
				|| selectedComboboxIndex==(Constant.OCCCI_COLUMN_INDEX+1) 
				|| selectedComboboxIndex==(Constant.DBP_COLUMN_INDEX+1)
				|| selectedComboboxIndex==(Constant.CFI_COLUMN_INDEX+1)
			)
		){ 
			System.out.println(THIS_CLASS_NAME+"Calculate Asemco/OCCI/BCCI/DBP."
					+ "\tSelected Combo Index: "+selectedComboboxIndex+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_PAGIBIG_VALUE && selectedComboboxIndex==Constant.PAGIBIG_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate Pag-ibig."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_ST_PETER_VALUE && selectedComboboxIndex==Constant.ST_PETER_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate St. Peter."+CLASS_NAME);
			return true;
		}
		else if(calculationMode==Constant.CALCULATE_UNION_DUES_VALUE && selectedComboboxIndex==Constant.UNION_DUES_COLUMN_INDEX+1){
			System.out.println(THIS_CLASS_NAME+"Calculate Union Dues."+CLASS_NAME);
			return true;
		}
		
		
		System.out.println(THIS_CLASS_NAME+"Calculate FAILED!."
				+"\tSelected Combo Index: "+selectedComboboxIndex+CLASS_NAME);
		return false;
	}
	private void l_________________________________________________________________l(){}
	
	/**
	 * Calculate Regular Pay.
	 * @param salary
	 * @return
	 */
	private double calcuateRegularPayValue(double salary ,String payrollDate){
		Utilities util=Utilities.getInstance();
		String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
		double firstPayValue=util.calculateFirstHalfKinsenasPayValue(salary);
	
		return (util.isFirstPayOfTheMonth(day))? 
			firstPayValue
			:
			util.calculateSecondHalfKinsenasPayValue(salary, firstPayValue)
		;
			
	}
	
	/**
	 *  Calculate ECOLA/LaundryAllowance/Longevity/Allowance.
	 * @param db
	 * @param employeeID
	 * @param earningsAutomateDatabaseColumnIndex
	 * @return
	 */
	private double calcuateEcolaLALongevityRiceValue(Database db ,String employeeID,
			int earningsAutomateDatabaseColumnIndex){
		
		//--------------------------------------------------------------------------------		
		double calculateValue=0;
		System.out.println("\t Chosen employee ID calculate ECOLA: "+employeeID+CLASS_NAME);
		
		//--------------------------------------------------------------------------------
		//--> Process consitionValueList
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.earningsAutomateTableColumnNames[0], employeeID));
		
		//--------------------------------------------------------------------------------
		//--> Get data from database.
		db.selectDataInDatabase(
				new String[]{db.tableNameEarningsAutomate},
				new String[]{db.earningsAutomateTableColumnNames[earningsAutomateDatabaseColumnIndex]},// Ecola column names
				conditionColumnAndValueList,
				null,
				null,
				Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND
		);
		
		//--------------------------------------------------------------------------------
		//--> Get the value from resultset.
		
		int columnIndex=1; // Since the resultset return only 1 column values.  
		try {
			db.resultSet.last();
			int totalRows=db.resultSet.getRow();
			if(totalRows>0){
				db.resultSet.first();
				calculateValue=(double) db.resultSet.getObject(columnIndex);			
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//--------------------------------------------------------------------------------
		
		return calculateValue;

			
	}
	
	
	
	private double calculateOvertimeValue(){
		return -1;
	}

	/**
	 * Calculate contract salary rate per day.
	 * @param db
	 * @param employeeID
	 * @return
	 */
	private double calculateContractualSalaryRatePerDay(Database db,String employeeID){
		int numOfYearsInService=db.getNumberOfYearsServiceBasedFromGivenContractualEmployeeID(employeeID);
		System.out.println("\t\t Num of Years in Service: "+numOfYearsInService+CLASS_NAME);
		double salaryPerDay=db.getContractualSalaryRateBasedYearsOfService(numOfYearsInService);
		
		for(String id:Utilities.getInstance().getSpecialContractualList()){
			if(id.equals(employeeID)){
				salaryPerDay+=100;
				break;
			}
		}
		return salaryPerDay;
	}
	
	
//	private String selectBasedFromColumnsWithConditionAND(String[] tableNameList,String[]columnNameList, 
//			ArrayList<SelectConditionInfo>conditionColumnAndValueList, OrderByInfo orderInfo){
	private double calculateContractualSubTotal(Database db,int row, int column){
	
		switch(editMode){
		
			//--> When Editing only One Column.
			case Constant.EDIT_ONCE:{
				String earningID=(String) table.getModel().getValueAt(row, 0); // EarningID

				ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
				conditionColumnAndValueList.add(new SelectConditionInfo(db.earningsContractualColumnNames[0], earningID)); // EarningID
				
				
				
				db.selectDataInDatabase(
					new String[]{db.tableNameEarningsContractual},
					new String[]{db.earningsContractualColumnNames[3],db.earningsContractualColumnNames[4]}, 
					conditionColumnAndValueList, 
					null, 
					null, 
					Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND
				);
				
				
				try {
					db.resultSet.absolute(1);
					return Double.parseDouble(db.resultSet.getObject(1).toString()) * Double.parseDouble(db.resultSet.getObject(2).toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			//--> When Editing Multiple Column.
			case Constant.EDIT_MULTIPLE:{
				double numOfDays=Double.parseDouble(table.getModel().getValueAt(row, 7).toString()); // NumOfDays
				double ratePerDay=Double.parseDouble(table.getModel().getValueAt(row, 8).toString()); // Rate Per Day

				return numOfDays*ratePerDay;
			}
		}
		
		
		
		return 0;
	}
	
	
	private void l____________________________________________l(){}
	
	/**
	 * Calculated SSS value.
	 * @param db
	 * @param salary
	 * @return
	 */
	private double calculateSSSValue(Database db,double salary, String employeeID){
		Utilities util = Utilities.getInstance();
		
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
			salary=util.getContractualEmpployeeSalaryPerMonth(db, employeeID);
		}
		
		
		int counter=0;
		double calculatedValue=-1;
		for(Integer index:db.sssDataList.keySet()){
			SssInfo sssInfo=db.sssDataList.get(index);
			if(salary>=sssInfo.minimumRange && salary<=sssInfo.maximumRange){
				calculatedValue=sssInfo.ee;
				
				//--> If the employer share data is shown, show the er data.
				if(isEmployerShareDataShown){ 
					calculatedValue=sssInfo.er;	
					
					//> To be used only in edit mode EDIT ONCE.
					tempCalculatedValue=sssInfo.ee;
				}
				break;
			}
			counter++;
		}
		if(counter==db.sssDataList.size()){
			calculatedValue=0;
		}
		
		return calculatedValue;
	}
	
	/**
	 * Calculate the value for PHIC.
	 * @param db
	 * @param salary
	 * @return
	 */
	private double calculateMedicareValue(Database db,Double salary,Date payrollDate){
		Utilities util=Utilities.getInstance();
		String day=util.getDayFromDateFormatYyyyMmDd(""+payrollDate);
		double medicareValue=-1;
		double phicRate=Double.parseDouble(db.phicRate);
		
		for(Integer index:db.phicSalaryRangeDataList.keySet()){
			PhicInfo phicInfo=db.phicSalaryRangeDataList.get(index);
			if(salary>=phicInfo.monthlyBasicSalaryMin && salary<=phicInfo.monthlyBasicSalaryMax){
				if(phicInfo.status.equals(Constant.PHIC_STATUS_STATIC)){
					if(phicInfo.phicID==1){ // Lowest
						medicareValue=(phicRate*phicInfo.monthlyBasicSalaryMax);///2;
					}
					else if(phicInfo.phicID==3){// highest
						medicareValue=(phicRate*phicInfo.monthlyBasicSalaryMin);///2;
					}
				}
				else if(phicInfo.status.equals(Constant.PHIC_STATUS_DYNAMIC)){ // middle part
					medicareValue=salary*phicRate;
					//medicareValue/=2;
				}
				
				double value=util.convertRoundToOnlyTwoDecimalPlaces(medicareValue);
				double firstPayVal=util.calculateFirstHalfKinsenasPayValue(value);
				medicareValue=util.calculateSecondHalfKinsenasPayValue(value, firstPayVal);
				
				//--> Depends on what data is shown. When Deduction Data is shown, the value is the second half[lowest/equal value]. When Employer Share Data is shown, the value is the first half[highest/equal value].
				if(isEmployerShareDataShown){
					tempCalculatedValue=medicareValue;
					medicareValue=firstPayVal;
				}
				
				System.out.println("\t\t\t POTA: "+value
						+"\tFirst Pay: "+firstPayVal
						+"\tSecond Pay: "+medicareValue+CLASS_NAME);
				break;
			}
		}
		return medicareValue;
	}

	/**
	 *  Calculate ASEMCO, BCCI, OCCCI, DBP, CFI
	 * @param db
	 * @param employeeID
	 * @param colIndex
	 * @return
	 */
	private double calculateAsemcoBcciOccciDbpCfi(Database db,String employeeID,
			int colIndex,Date payrollDate,int numOfColumnsNotNeededs,int editMode){
		 // ASEMCO, OCCCI, DBP, BCCI,  CFI
		Utilities util=Utilities.getInstance();
		double calculateValue=-0;
		int conditionNum=(editMode==Constant.EDIT_MULTIPLE)?
				colIndex-numOfColumnsNotNeededs
				:
				bothEarningDeductionViewPanel.columnComboBox.getSelectedIndex()-1
		;
		String day=util.getDayFromDateFormatYyyyMmDd(""+payrollDate);
		String tableColumnName=null;
		int firstPayColumnNameIndex=-999,secondPayColumnNameIndex=-999;
		//-----------------------
		System.out.println("\tASWEDS EMployeeID: "+employeeID
				+"\tPayroll Date: "+payrollDate+CLASS_NAME);
		System.out.println("\t List index: "+CLASS_NAME);
		System.out.println("\tDay: "+day+CLASS_NAME); // Mar 2016
		//-----------------------

		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.abodcTableColumnNames[0], employeeID));
		
		//----------------------
		switch(conditionNum){
			case Constant.ASEMCO_COLUMN_INDEX:{
				firstPayColumnNameIndex=1; //ASEMCOFirstPay
				secondPayColumnNameIndex=2; // ASEMCOSecondPay
				tableColumnName=(util.isFirstPayOfTheMonth(day))?db.abodcTableColumnNames[firstPayColumnNameIndex]:db.abodcTableColumnNames[secondPayColumnNameIndex];
				
				break;

			}
			case Constant.BCCI_COLUMN_INDEC:{
				firstPayColumnNameIndex=4; //BCCIFirstPay
				secondPayColumnNameIndex=5; // BCCISecondPay
				tableColumnName=(util.isFirstPayOfTheMonth(day))?db.abodcTableColumnNames[firstPayColumnNameIndex]:db.abodcTableColumnNames[secondPayColumnNameIndex];
				
				break;
			}
			case Constant.OCCCI_COLUMN_INDEX:{
				firstPayColumnNameIndex=7; //OCCCIFirstPay
				secondPayColumnNameIndex=8; // OCCCISecondPay
				tableColumnName=(util.isFirstPayOfTheMonth(day))?db.abodcTableColumnNames[firstPayColumnNameIndex]:db.abodcTableColumnNames[secondPayColumnNameIndex];
				
				break;
			}
			case Constant.DBP_COLUMN_INDEX:{
				firstPayColumnNameIndex=10; //DBPFirstPay
				secondPayColumnNameIndex=11; // DBPSecondPay
				tableColumnName=(util.isFirstPayOfTheMonth(day))?db.abodcTableColumnNames[firstPayColumnNameIndex]:db.abodcTableColumnNames[secondPayColumnNameIndex];
				
				break;
			}
			case Constant.CFI_COLUMN_INDEX:{
				firstPayColumnNameIndex=13; //CFIFirstPay
				secondPayColumnNameIndex=14; //CFISecondPay
				tableColumnName=(util.isFirstPayOfTheMonth(day))?db.abodcTableColumnNames[firstPayColumnNameIndex]:db.abodcTableColumnNames[secondPayColumnNameIndex];
			
				break;
			}
			default:
				break;
		}
	
		//--------------------------------
		int indexUsed=1; // WHy 1? Since we only get 1 column. See query. 
		db.selectDataInDatabase(
				new String[]{db.tableNameABODC},
				new String[]{tableColumnName},
				conditionColumnAndValueList,
				null, null,Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND);
		

		//--------------------------------
		try {
			db.resultSet.last();
			int totalRows=db.resultSet.getRow();
			if(totalRows>0){
				db.resultSet.first();
				calculateValue=(double) db.resultSet.getObject(indexUsed);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return calculateValue;
	}
	
	/**
	 * Calculate pagibig value.
	 * @param db
	 * @param employeeID
	 * @return
	 */
	private double calculatePagibigValue(Database db,String employeeID){
		double calculateValue=0;
		
//		 selectBasedFromColumnsAndCondition(String[] tableNameList,String[]columnNameList, 
//					ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		
		System.out.println("\t Chosen employee ID calculate PAGIBIG: "+employeeID+CLASS_NAME);
		//--------------------------------------------------------------------------------
		//--> Process consitionValueList
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.pagibigTableColumnNames[0], employeeID));
		
		//--------------------------------------------------------------------------------
		// Depends on what data is shown. When Deduction Data is shown, the pagibig column EE is assigned. When Employer Share Data is shown, the pagibig column ER is assigned.
		String[] neededTableColumn=new String []{db.pagibigTableColumnNames[1]};
				
		if(isEmployerShareDataShown){
			 neededTableColumn=new String []{db.pagibigTableColumnNames[2]};
			 
			 if(editMode==Constant.EDIT_ONCE){
				 neededTableColumn=new String []{db.pagibigTableColumnNames[2],db.pagibigTableColumnNames[1]};
				 	 
			 }
		}
		
		//--> Get data from database.
		db.selectDataInDatabase(
				new String[]{db.tableNamePagibig},
				neededTableColumn	,
				conditionColumnAndValueList,
				null,
				null,
				Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND
		);
		
		//--------------------------------------------------------------------------------
		//--> Get the value from resultset.
		
		int indexUsed=1; // WHy 1? Since we only get the first column whether the return column is 1 or 2. In our case there are times that it returns 2 when edit mode is EDIT ONCE and employer share data is shown. See query. 
		try {
			db.resultSet.last();
			int totalRows=db.resultSet.getRow();
			if(totalRows>0){
				db.resultSet.first();
				calculateValue=(double) db.resultSet.getObject(indexUsed);
				
				if(isEmployerShareDataShown && editMode==Constant.EDIT_ONCE){
					tempCalculatedValue=(double) db.resultSet.getObject(indexUsed+1);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//--------------------------------------------------------------------------------
		
		return calculateValue;
	}
	
	
	/**
	 * Calculate st. peter value.
	 * @param db
	 * @param employeeID
	 * @return
	 */
	private double calculateStPeterValue(Database db,String employeeID, Date payrollDate){

//		 selectBasedFromColumnsAndCondition(String[] tableNameList,String[]columnNameList, 
//					ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		
		//--------------------------------------------------------------------------------		
		Utilities util = Utilities.getInstance();
		double calculateValue=0;
		String day=util.getDayFromDateFormatYyyyMmDd(""+payrollDate);
		System.out.println("\t Chosen employee ID calculate St. Peter: "+employeeID+CLASS_NAME);
		
		//--------------------------------------------------------------------------------
		//--> Process consitionValueList
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.stPeterTableColumnNames[0], employeeID));
		
		//--------------------------------------------------------------------------------
		//--> Get data from database.
		db.selectDataInDatabase(
				new String[]{db.tableNameStPeter},
				new String[]{db.stPeterTableColumnNames[1],db.stPeterTableColumnNames[2]},// 15th and 30th column names
				conditionColumnAndValueList,
				null,
				null,
				Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND
		);
		
		//--------------------------------------------------------------------------------
		//--> Get the value from resultset.
		
		int columnIndex_15th=1, columnIndex_30th=columnIndex_15th+1; // Since the resultset only return two values from 15th and 30th column  
		try {
			db.resultSet.last();
			int totalRows=db.resultSet.getRow();
			if(totalRows>0){
				db.resultSet.first();
				calculateValue=(util.isFirstPayOfTheMonth(day))?
						(double) db.resultSet.getObject(columnIndex_15th)
						:
						(double) db.resultSet.getObject(columnIndex_30th)	;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//--------------------------------------------------------------------------------
		
		return calculateValue;
	}
	
	
	/**
	 * Calculate union dues value.
	 * @param db
	 * @param employeeID
	 * @return
	 */
	private double calculateUnionDuesValue(Database db,String employeeID){

//		 selectBasedFromColumnsAndCondition(String[] tableNameList,String[]columnNameList, 
//					ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		
		//--------------------------------------------------------------------------------		
		Utilities util = Utilities.getInstance();
		double calculateValue=0;
		System.out.println("\t Chosen employee ID calculate Union Dues: "+employeeID+CLASS_NAME);
		
		//--------------------------------------------------------------------------------
		//--> Process consitionValueList
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.unionDuesTableColumnNames[0], employeeID));
		
		//--------------------------------------------------------------------------------
		//--> Get data from database.
		db.selectDataInDatabase(
				new String[]{db.tableNameUnionDues},
				new String[]{db.unionDuesTableColumnNames[1]},// EE column names
				conditionColumnAndValueList,
				null,
				null,
				Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND
		);
		
		//--------------------------------------------------------------------------------
		//--> Get the value from resultset.
		
		int eeColumnIndex=1; // Since the resultset return only 1 column values.  
		try {
			db.resultSet.last();
			int totalRows=db.resultSet.getRow();
			if(totalRows>0){
				db.resultSet.first();
				calculateValue=(double) db.resultSet.getObject(eeColumnIndex);			
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		//--------------------------------------------------------------------------------
		
		return calculateValue;
	}
	
	private void l________________________________________________l(){}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
		
		ReusableTable fixedTable=bothEarningDeductionViewPanel.fixedTable,
				dynamicTable=bothEarningDeductionViewPanel.dynamicTable,
				employeeTable=bothEarningDeductionViewPanel.fullScreenTable;
		Utilities util=Utilities.getInstance();

		
		//----------------------------------------------
		
		
		//--> Highlight the rows selected. Makes sure that the highlight feature for both tables
		//			only happens when the shown are only earning/deduction data. Not all employees.
		if(isFixedTableClicked){ // When Fixed Table is clicked!
			 //> When you select the fixed table, the corresponding dynamuc table row is also clicked.
			if(fixedTable.getSelectedRows().length>0){
				dynamicTable.getSelectionModel().setSelectionInterval(
						fixedTable.getSelectedRows()[0], fixedTable.getSelectedRows()[fixedTable.getSelectedRows().length-1]);
			}
			//> When there are no/zero selection in fixed table, unselect also the dynamic table.
			else{
				dynamicTable.clearSelection();
			}
		}
		
		else if(isDynamicTableClicked ){// When Dynamic Table is clicked!
			 //> When you select the dynamic table, the corresponding fixed table row is also clicked.
			if(dynamicTable.getSelectedRows().length>0){
				fixedTable.getSelectionModel().setSelectionInterval(
						dynamicTable.getSelectedRows()[0], dynamicTable.getSelectedRows()[dynamicTable.getSelectedRows().length-1]);
			}
			//> When there are no/zero selection in dynamic table, unselect also the fixed table table.
			else{
				fixedTable.clearSelection();
			}
		}
		
		
	
		
		//----------------------------------------------
		
		//--> Repaint to avoid overlap
		bothEarningDeductionViewPanel.repaint();
	}
	private void l______________________________________________________l(){}
	//--> Default methods in DocumentListener

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(bothEarningDeductionViewPanel!=null && e.getDocument()==bothEarningDeductionViewPanel.filterTextField.getDocument()){
			
			bothEarningDeductionViewPanel.executeSearchMechanismOfAllTables();
			
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(bothEarningDeductionViewPanel!=null &&e.getDocument()==bothEarningDeductionViewPanel.filterTextField.getDocument()){
			
			bothEarningDeductionViewPanel.executeSearchMechanismOfAllTables();
			
		}
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(bothEarningDeductionViewPanel!=null &&e.getDocument()==bothEarningDeductionViewPanel.filterTextField.getDocument()){
			
			bothEarningDeductionViewPanel.executeSearchMechanismOfAllTables();
			
		}	
		
	}
	private void l___________________________________________l(){}
	//--> Default methods for MouseListener
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		ReusableTable fixedTable=bothEarningDeductionViewPanel.fixedTable,
				dynamicTable=bothEarningDeductionViewPanel.dynamicTable,
				employeeTable=bothEarningDeductionViewPanel.fullScreenTable;
		
		//------------------------------------------------------
		
		// TODO Auto-generated method stub

		if(e.getSource()==fixedTable){
//			System.out.println("\t Fixed Table was clicked."+CLASS_NAME);
			isFixedTableClicked=true;
			
			isDynamicTableClicked=false;
			isFullScreenTableClicked=false;
		}
		else if(e.getSource()==dynamicTable){
//			System.out.println("\t Dynamic Table was clicked."+CLASS_NAME);
			
			isDynamicTableClicked=true;
			
			isFullScreenTableClicked=false;
			isFixedTableClicked=false;
		}
		else if(e.getSource()==employeeTable){
			System.out.println("\t Employee Table was clicked."+CLASS_NAME);
			
			isFullScreenTableClicked=true;
			
			isDynamicTableClicked=false;
			isFixedTableClicked=false;
		}
		
//		//------------------------------------------------------
//		//--> Use for Table Sorting
//		if(e.getSource() instanceof JTableHeader){
//			if( ((JTableHeader) e.getSource()).getTable() == fixedTable ){
//				System.out.println("\t Fixed Table Column HEADER is clicked MouseListener:mouseClicked."+CLASS_NAME);
//				
//				fixedTable.setRowSorter(bothEarningDeductionViewPanel.fixedTableSorter);
//				dynamicTable.setRowSorter(bothEarningDeductionViewPanel.fixedTableSorter);
//				
//			}
//			else if( ((JTableHeader) e.getSource()).getTable() == dynamicTable){
//				System.out.println("\t Dynamic Table Column HEADER is clicked MouseListener:mouseClicked."+CLASS_NAME);
//				
//				dynamicTable.setRowSorter(bothEarningDeductionViewPanel.dynamicTableSorter);
//				fixedTable.setRowSorter(bothEarningDeductionViewPanel.dynamicTableSorter);
//			}
//			
//		}
				
		//-----------------------------------------------------
		//--> Repaint to avoid overlap
		bothEarningDeductionViewPanel.repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		ReusableTable fixedTable=bothEarningDeductionViewPanel.fixedTable,
				dynamicTable=bothEarningDeductionViewPanel.dynamicTable,
				fullScreenTable=bothEarningDeductionViewPanel.fullScreenTable;
				
		
		if(e.getSource()==fixedTable){
//			System.out.println("\t Fixed Table was clicked."+CLASS_NAME);
			isFixedTableClicked=true;
			
			isDynamicTableClicked=false;
			isFullScreenTableClicked=false;
		}
		else if(e.getSource()==dynamicTable){
//			System.out.println("\t Dynamic Table was clicked."+CLASS_NAME);
			
			isDynamicTableClicked=true;
			
			isFullScreenTableClicked=false;
			isFixedTableClicked=false;
		}
		else if(e.getSource()==fullScreenTable){
//			System.out.println("\t Employee Table was clicked."+CLASS_NAME);
			
			isFullScreenTableClicked=true;
			
			isDynamicTableClicked=false;
			isFixedTableClicked=false;
		}
		
	
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void l______________________________________________________________l(){}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
		//--> Repaint. To make sure that when scrollbars are scrolled by the user, the option boxes dili matapakan
		if(bothEarningDeductionViewPanel!=null)
			bothEarningDeductionViewPanel.repaint();
	}
	private void l_______________________________________________l(){}
	
	

	public void setIfDeductionOrEarning(EarningsAndDeductionLayout bothEarningDeductionViewPanell){
		
		this.bothEarningDeductionViewPanel=bothEarningDeductionViewPanell;
		 
		 
//		//--> Set enabled necessary UI components
		boolean isBoolean=false;
		bothEarningDeductionViewPanel.calculationPanel.setVisible(isBoolean);
		
		isBoolean=true;
		bothEarningDeductionViewPanel.dynamicTableScrollPane.setVisible(isBoolean);
		bothEarningDeductionViewPanel.fixedTableScrollPane.setVisible(isBoolean);
		
		
		//--> Update Top Left Panel
		bothEarningDeductionViewPanel.updateTopLeftPanel((bothEarningDeductionViewPanel instanceof EarningViewPanel)?1:2);

 	}
	
	 /**
	 * Set outside listeners to be used inside this class.
	 * @param mainFrameListener
	 */
	 public void setOutsideListeners(ListenerMainFrame mainFrameListener){
		 this.mainFrameListener=mainFrameListener;
	 }

	

	
	
}
