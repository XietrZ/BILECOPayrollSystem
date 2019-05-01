package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;




import model.Constant;
import model.MultipleUpdateDatabaseModel;
import model.OrderByInfo;
import model.PayrollTableModel;
import model.SelectConditionInfo;
import model.statics.Images;
import model.statics.Utilities;
import model.view.Edit15th30thTotalDialog;
import model.view.ReusableTable;
import database.Database;
import view.MainFrame;
import view.views.SettingsViewPanel;

public class ListenerSettingsViewPanel implements ActionListener,ListSelectionListener,MouseListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private Database db;
	private ReusableTable table;
	private Utilities util;
	
	private SettingsViewPanel settingsViewPanel;
	private MainFrame mainFrame;
	
	private boolean isFixedTableClicked=false, isDynamicTableClicked=false,
			isFullScreenTableClicked=false, isEdit15th30thColumn=false;
	private int[] selectedTableRowIndexList=null;
	private String []neededColumnsForAsemcoSettng=null;
	
	private ListenerMainFrame mainFrameListener;

	public  ListenerSettingsViewPanel() {
		settingsViewPanel=SettingsViewPanel.getInstance();
		mainFrame=MainFrame.getInstance();
		db=Database.getInstance();
		util= Utilities.getInstance();
		table=null;
	}
	
	private void l________________________________________l(){}
	@Override
	public void actionPerformed(ActionEvent e) {
		//--> Check the disconnection status of database.
		Database.getInstance().checkIfDisconnectedToDatabase();
		
		
		//--> Only Process When Connected.
		if(Database.getInstance().isConnected){
			
			//--> Settings Option Button. 
			if(e.getSource()==settingsViewPanel.settingsOptionButton){
				if(Utilities.getInstance().isEdit){
					mainFrame.showOptionPaneMessageDialog("You cannot click this button during edit. Please cancel edit mode.", JOptionPane.ERROR_MESSAGE);
				}
				else{
					settingsViewPanel.optionPanel.setVisible((settingsViewPanel.optionPanel.isVisible())?false:true);
				}
			}
			
	
			else{
				//--> Option Panel Buttons
				for(String key:settingsViewPanel.optionPanel.buttonList.keySet()){
					JButton btn=settingsViewPanel.optionPanel.buttonList.get(key);
					if(e.getSource()==btn){
						switch(key){
							case Constant.DEPARTMENT_SETTING_BTN:{
								processDepartmentSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.PHIC_SALARY_RANGE_SETTINGS_BTN:{
								processPhicSalaryRange();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.PHIC_RATE_SETTING_BTN:{
								processPhicRate();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.SSS_SETTING_BTN:{
								processSSSSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.ASEMCO_SETTING_BTN:{
								processAsemcoBcciOccciDbpCfiSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fixedTable.getModel().getRowCount());
								break;
							}
							case Constant.PAGIBIG_SETTING_BTN:{
								processPagibigSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.ST_PETER_SETTING_BTN:{
								processStPeterSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.UNION_DUES_SETTING_BTN:{
								processUnionDuesSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.ECOLA_SETTING_BTN:{
								processEcolaSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.LAUNDRY_ALLOWANCE_SETTING_BTN:{
								processLaundryAllowanceSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.LONGEVITY_SETTING_BTN:{
								processLongevitySetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							case Constant.RICE_SETTING_BTN:{
								processRiceSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							
							case Constant.RATE_PER_DAY_SETTING_BTN:{
								processContractualSalaryRateSetting();
								//--> Update Row Count Label
								settingsViewPanel.rowCountLabel.setText("Row Count: "+settingsViewPanel.fullScreenTable.getModel().getRowCount());
								break;
							}
							
							default:
								break;
						}
						
						settingsViewPanel.setAddEditDeleteBtnVisible(true);
						settingsViewPanel.optionPanel.setVisible(false);
						
						break;
					}
				}
			}
			
			//-------------------------------------------
			
			//--> ADD BUTTON
			if(e.getSource()==settingsViewPanel.addButton){
				processAddButton();
			}
			//--> EDIT BUTTON
			else if(e.getSource()==settingsViewPanel.editButton){
				processEditButton();
			}
			//--> DELETE BUTTON
			else if(e.getSource()==settingsViewPanel.deleteButton){
				processDeleteButton();
			}
			//--> SAVE BUTTON
			else if(e.getSource()==settingsViewPanel.saveButton){
				processSaveButton();
			}
			//--> CANCEL BUTTON
			else if(e.getSource()==settingsViewPanel.cancelButton){
				processCancelButton();
			}
			//----------------------------------------------------------------------
			
			//--> EDIT 15th 30th RADIO BUTTON
			else if(e.getSource()==settingsViewPanel.edit15th30thTotalDialog.edit15th30thRadioBtn){
				settingsViewPanel.edit15th30thTotalDialog.editTotalRadioBtn.setSelected(false);
				isEdit15th30thColumn=true;
			}
			//--> EDIT TOTAL RADIO BUTTON
			else if(e.getSource()==settingsViewPanel.edit15th30thTotalDialog.editTotalRadioBtn){
				settingsViewPanel.edit15th30thTotalDialog.edit15th30thRadioBtn.setSelected(false);
				isEdit15th30thColumn=false;
			}
			//--> EXECUTE BUTTON
			else if(e.getSource()==settingsViewPanel.edit15th30thTotalDialog.executeButton){
				processExecuteEditButtonAsemcoStPeter();
			}
			
		}
		
		
		//----------------------------------------------------------------------
		
		//--> To avoid overlaps
		settingsViewPanel.repaint();
		
		
		
	}
	
	
	private void l____________________________________________l(){}
	/**
	 * Execute process where the Department menu item setting is clicked.
	 */
	private void processDepartmentSetting(){
		System.out.println(THIS_CLASS_NAME+"Show Department settings"+CLASS_NAME);
		
		Database db=Database.getInstance();

		settingsViewPanel.mode=Constant.SETTING_DEPARTMENT;
		table=settingsViewPanel.fullScreenTable;
		
		db.selectDataInDatabase(
				new String[]{db.tableNameDepartment}, 
				null,
				null,
				null,
				new OrderByInfo(
					new String[]{ 
							db.departmentTableColumnNames[1]}, // LastName/FamilyName
					"ASC"
				),
				Constant.SELECT_ALL);
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		settingsViewPanel.fullScreenTable.hideColumns(new int[]{0});
		
		
//		settingsDialog.settingComponentMode=Constant.SETTING_COMPONENT_SHOWTABLE;
//		
//		settingsDialog.setVisibleButtonsWhenShowTable();
//		settingsDialog.setVisible(true);
		
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().departmentOptionTitleImg);
		
		
		//--> Setting UI components
		boolean isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
		
		isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
	}
	
	/**
	 * Execute process that will happen when you click the salary range phic.
	 */
	private void processPhicSalaryRange(){
		System.out.println(THIS_CLASS_NAME+"Show salary range table."+CLASS_NAME);
		
		Database db=Database.getInstance();
	
		settingsViewPanel.mode=Constant.SETTING_PHIC_SALARY;
		table=settingsViewPanel.fullScreenTable;
		
		
		db.selectDataInDatabase(
				new String[]{db.tableNamePhic}, 
				null,
				null,
				null,
				null,
				Constant.SELECT_ALL);
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		settingsViewPanel.fullScreenTable.hideColumns(new int[]{0});
	
//		settingsDialog.settingComponentMode=Constant.SETTING_COMPONENT_SHOWTABLE;
//		
//		settingsDialog.setVisibleButtonsWhenShowTable();
//		settingsDialog.setVisible(true);
		
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().phicSalaryRangeOptionTitleImg);
				
				
		//--> Setting UI components
		boolean isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
		
		isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
	}
	
	/**
	 * Execute process that will happen when you click the phic rate.
	 */
	private void processPhicRate(){
		System.out.println(THIS_CLASS_NAME+"Show phic rate table."+CLASS_NAME);
		
		Database db=Database.getInstance();

		settingsViewPanel.mode=Constant.SETTING_PHIC_RATE;
		table=settingsViewPanel.fullScreenTable;
		
		
		db.selectDataInDatabase(
				new String[]{db.tableNamePhicRate}, 
				null,
				null,
				null,
				null,
				Constant.SELECT_ALL);
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		settingsViewPanel.fullScreenTable.hideColumns(new int[]{0});
		
		
//		settingsDialog.settingComponentMode=Constant.SETTING_COMPONENT_SHOWTABLE;
//		
//		settingsDialog.setVisibleButtonsWhenShowTable();
//		settingsDialog.setVisible(true);
		
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().phicRateOptionTitleImg);
				
				
		//--> Setting UI components
		boolean isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
		
		isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
	
	}
	
	
	
	/**
	 * Execute process that will happen when you click sss settings.
	 */
	private void processSSSSetting(){
		System.out.println(THIS_CLASS_NAME+"Show SSS settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_SSS;
		table=settingsViewPanel.fullScreenTable;
		

		db.selectDataInDatabase(
				new String[]{db.tableNameSss}, 
				null,
				null,
				null,
				null,
				Constant.SELECT_ALL);
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		settingsViewPanel.fullScreenTable.hideColumns(new int[]{0});
//		
//		settingsViewPanel.settingComponentMode=Constant.SETTING_COMPONENT_SHOWTABLE;
//		
//		settingsDialog.setVisibleButtonsWhenShowTable();
//		settingsDialog.setVisible(true);
		
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().sssOptionTitleImg);
		
				
				
		//--> Setting UI components
		boolean isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
		
		isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
	}
	
	/**
	 * Execute process when clicking the setting button ASEMCO/BCCI/OCCCI/DBP/CFI
	 */
	private void processAsemcoBcciOccciDbpCfiSetting(){
		System.out.println(THIS_CLASS_NAME+"Show asemco/bcci/occci/dbp/cfi settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI;
		table=settingsViewPanel.dynamicTable;
		
		//--> Desired query, DO NOT UNCOMMENT
//		SELECT employee.EmployeeID,FamilyName, FirstName, ASEMCOFirstPay, ASEMCOSecondPay, ASEMCOMonthlyPay,
//			BCCIFirstPay, BCCISecondPay, BCCIMonthlyPay,
//	        OCCCIFirstPay, OCCCISecondPay,OCCCIMonthlyPay,
//	        DBPFirstPay,DBPSecondPay,DBPMonthlyPay,
//	        CFIFirstPay,CFISecondPay,CFIMonthlyPay
//	        
//        FROM employee
//        INNER JOIN abodc
//        ON employee.EmployeeID=abodc.EmployeeID;
		
		//-----------------------------------------------------------------------
		//--> Process needed columnlist
		neededColumnsForAsemcoSettng=null;
		neededColumnsForAsemcoSettng=new String[2+(db.abodcTableColumnNames.length)]; // 2->LastName and FirstName. 
		for(int i=0,employeeIndex=2,aobdcIndex=0;i<neededColumnsForAsemcoSettng.length;i++){
			if(i==0){
				neededColumnsForAsemcoSettng[i]=db.tableNameABODC+"."+db.abodcTableColumnNames[aobdcIndex];
				aobdcIndex++;
			}
			else if(i>=1 && i<=2){
				neededColumnsForAsemcoSettng[i]=db.employeeTableColumnNames[employeeIndex];
				employeeIndex++;
			}
			else{
				neededColumnsForAsemcoSettng[i]=db.tableNameABODC+"."+db.abodcTableColumnNames[aobdcIndex];
				aobdcIndex++;
			}
		}
		
		//-----------------------------------------------------------------------
		
		//--> Process joinColumnList;
		String []joinColumnCompareList={
			db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameABODC+"."+db.abodcTableColumnNames[0]	
		};
		
		//-----------------------------------------------------------------------
		//--> Process Condition
		//--> Add condition if regular or contractual
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		//-----------------------------------------------------------------------
		//--> Get data from database
		db.selectDataInDatabase(
				new String[]{db.tableNameEmployee,db.tableNameABODC},
				neededColumnsForAsemcoSettng,
				conditionColumnAndValueList,
				joinColumnCompareList,
				new OrderByInfo(
					new String[]{ 
							db.employeeTableColumnNames[2]}, // LastName/FamilyName
					"ASC"
				),
				Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		//-----------------------------------------------------------------------
		
		//--> Update Dynamic Table
		int []dynamicTableHideColumnList={0,0,0};
		settingsViewPanel.dynamicTable.isAutoResize=false;
		settingsViewPanel.dynamicTable.updateTable(db);
		settingsViewPanel.dynamicTable.hideColumns(dynamicTableHideColumnList);
		
		//-----------------------------------------------------------------------
		
		//--> Update Fixed Table
		int []fixedTableHideColumnList=new int[neededColumnsForAsemcoSettng.length-dynamicTableHideColumnList.length];		
		for(int i=0;i<fixedTableHideColumnList.length;i++){
			fixedTableHideColumnList[i]=dynamicTableHideColumnList.length;
		}
		settingsViewPanel.fixedTable.isAutoResize=true;
//		fixedTable.updateTableStateNotContent(); // No need to update since it the model itself was updated in dynamic table
		settingsViewPanel.fixedTable.hideColumns(fixedTableHideColumnList);

		//-------------------------------------------------------------------------------------------------------------
		
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().asemcoOptionTitleImg);
				
				
				
		//--> Setting UI components
		boolean isVisible=true;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
		isVisible=false;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
		
	}
	/**
	 * Execute when pagibig buttion in option panel is clicked. Show 
	 */
	private void processPagibigSetting(){
		//--> Desired query.. Do not UNCOMMENT.
//		SELECT employee.EmployeeID,FamilyName,FirstName,EE,ER,Total
//	    FROM employee
//	    INNER JOIN
//	    pagibig
//	    ON employee.EmployeeID=pagibig.EmployeeID;
		
		//-----------------------------------------------------------------------
		//--> Initialize
		System.out.println(THIS_CLASS_NAME+"Show PAGIBIG settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_PAGIBIG;
		table=settingsViewPanel.fullScreenTable;
		
		//-----------------------------------------------------------------------
		//--> Process needed column names
		String[] neededColumnNameForPagibigSettingList=new String[2+(db.pagibigTableColumnNames.length)]; // 2->LastName and FirstName. 
		System.out.println("\t\t Total needed PAGIBIG setting columnList: "+neededColumnNameForPagibigSettingList.length+CLASS_NAME);
		for(int i=0,employeeIndex=2,pagibigIndex=0;i<neededColumnNameForPagibigSettingList.length;i++){
			if(i>=1 && i<=2){
				neededColumnNameForPagibigSettingList[i]=db.employeeTableColumnNames[employeeIndex];
				employeeIndex++;
			}
			else{
				neededColumnNameForPagibigSettingList[i]=
						util.addSlantApostropheToString(db.tableNamePagibig)+"."
						+util.addSlantApostropheToString(db.pagibigTableColumnNames[pagibigIndex]);
				pagibigIndex++;
			}
		}
		
		//-----------------------------------------------------------------------
		
		//--> Process joinColumnList;
		String []joinColumnCompareList={
			db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNamePagibig+"."+db.abodcTableColumnNames[0]	
		};
				
		//-----------------------------------------------------------------------
		//--> Process Condition
		//--> Add condition if regular or contractual
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		
		//-----------------------------------------------------------------------
		//--> Get data from database.
		
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNamePagibig},
			neededColumnNameForPagibigSettingList,
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(
				new String[]{ 
						db.employeeTableColumnNames[2]}, // LastName/FamilyName
				"ASC"
			),
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------
		//--> Update Table
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		
		
		//-----------------------------------------------------------------------
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().pagibigOptionTitleImg);
				
				
		//--> Setting UI components
		
		boolean isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
		isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
	    
	}
	
	/**
	 * Execute when st. peter buttion in option panel is clicked. Show 
	 */
	private void processStPeterSetting(){
		//--> Desired query.. Do not UNCOMMENT.
//		SELECT employee.EmployeeID,FamilyName,FirstName,15th,30th,Total
//	    FROM employee
//	    INNER JOIN
//	    stpeter
//	    ON employee.EmployeeID=stpeter.EmployeeID;
		
		//-----------------------------------------------------------------------
		//--> Initialize
		System.out.println(THIS_CLASS_NAME+"Show ST. Peter settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_ST_PETER;
		table=settingsViewPanel.fullScreenTable;
		
		//-----------------------------------------------------------------------
		//--> Process needed column names
		String[] neededColumnNameForStPeterSettingList=new String[2+(db.stPeterTableColumnNames.length)]; // 2->LastName and FirstName. 
		System.out.println("\t\t Total needed ST PETER setting columnList: "+neededColumnNameForStPeterSettingList.length+CLASS_NAME);
		for(int i=0,employeeIndex=2,stPeterIndex=0;i<neededColumnNameForStPeterSettingList.length;i++){
			if(i>=1 && i<=2){
				neededColumnNameForStPeterSettingList[i]=db.employeeTableColumnNames[employeeIndex];
				employeeIndex++;
			}
			else{
				neededColumnNameForStPeterSettingList[i]=
						util.addSlantApostropheToString(db.tableNameStPeter)+"."
						+util.addSlantApostropheToString(db.stPeterTableColumnNames[stPeterIndex]);
				stPeterIndex++;
			}
		}
		
		//-----------------------------------------------------------------------
		
		//--> Process joinColumnList;
		String []joinColumnCompareList={
			db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameStPeter+"."+db.stPeterTableColumnNames[0]	
		};
		
		//-----------------------------------------------------------------------
		//--> Process Condition
		//--> Add condition if regular or contractual
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
					
//		//-----------------------------------------------------------------------
		//--> Get data from database.
		
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameStPeter},
			neededColumnNameForStPeterSettingList,
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(
				new String[]{ 
						db.employeeTableColumnNames[2]}, // LastName/FamilyName
				"ASC"
			),
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------
		//--> Update Table
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		
		
		//-----------------------------------------------------------------------
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().stPeterOptionTitleImg);
				
				
		//--> Setting UI components
		
		boolean isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
		isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
	    
	}
	
	/**
	 * Execute when st. peter buttion in option panel is clicked. Show 
	 */
	private void processUnionDuesSetting(){
		//--> Desired query.. Do not UNCOMMENT.
//		SELECT employee.EmployeeID,FamilyName,FirstName,EE,Total
//	    FROM employee
//	    INNER JOIN
//	    uniondues
//	    ON employee.EmployeeID=stpeter.EmployeeID;
		
		//-----------------------------------------------------------------------
		//--> Initialize
		System.out.println(THIS_CLASS_NAME+"Show Union Dues settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_UNION_DUES;
		table=settingsViewPanel.fullScreenTable;
		
		//-----------------------------------------------------------------------
		//--> Process needed column names
		String[] neededColumnNameForUnionDuesSettingList=new String[2+(db.unionDuesTableColumnNames.length)]; // 2->LastName and FirstName. 
		System.out.println("\t\t Total needed UNION DUES setting columnList: "+neededColumnNameForUnionDuesSettingList.length+CLASS_NAME);
		for(int i=0,employeeIndex=2,unionDuesIndex=0;i<neededColumnNameForUnionDuesSettingList.length;i++){
			if(i>=1 && i<=2){
				neededColumnNameForUnionDuesSettingList[i]=db.employeeTableColumnNames[employeeIndex];
				employeeIndex++;
			}
			else{
				neededColumnNameForUnionDuesSettingList[i]=
						util.addSlantApostropheToString(db.tableNameUnionDues)+"."
						+util.addSlantApostropheToString(db.unionDuesTableColumnNames[unionDuesIndex]);
				unionDuesIndex++;
			}
		}
		
		//-----------------------------------------------------------------------
		
		//--> Process joinColumnList;
		String []joinColumnCompareList={
			db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameUnionDues+"."+db.unionDuesTableColumnNames[0]	
		};
					
		//-----------------------------------------------------------------------
		//--> Process Condition
		//--> Add condition if regular or contractual
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		
		//-----------------------------------------------------------------------
		//--> Get data from database.
		
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameUnionDues},
			neededColumnNameForUnionDuesSettingList,
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(
				new String[]{ 
						db.employeeTableColumnNames[2]}, // LastName/FamilyName
				"ASC"
			),
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------
		//--> Update Table
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		
		
		//-----------------------------------------------------------------------
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().unionDuesOptionTitleImg);
				
				
		//--> Setting UI components
		
		boolean isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
		isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
	    
	}
	
	/**
	 * Execute when ecola setting button in option panel is clicked. 
	 */
	private void processEcolaSetting(){
		//--> Desired query.. Do not UNCOMMENT.
//		SELECT employee.EmployeeID,FamilyName,FirstName,EE,Total
//	    FROM employee
//	    INNER JOIN
//	    uniondues
//	    ON employee.EmployeeID=stpeter.EmployeeID;
		
		//-----------------------------------------------------------------------
		//--> Initialize
		System.out.println(THIS_CLASS_NAME+"Show ECOLA settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_ECOLA;
		table=settingsViewPanel.fullScreenTable;
		
		//-----------------------------------------------------------------------
		//--> Process needed column names
		String[] neededColumnNameForEcolaSettingList=new String[3+(1)]; // 2->EmployeeID, LastName and FirstName | 1-> ECOLAValue. 
		System.out.println("\t\t Total needed ECOLA setting columnList: "+neededColumnNameForEcolaSettingList.length+CLASS_NAME);
		for(int i=0,employeeIndex=0,ecolaIndex=1;i<neededColumnNameForEcolaSettingList.length;i++){
			if(i<=2){ // EmployeeID, LastName, FirstName
				neededColumnNameForEcolaSettingList[i]=
						util.addSlantApostropheToString(db.tableNameEmployee)+"."
						+util.addSlantApostropheToString(db.employeeTableColumnNames[employeeIndex]);
						
						
				
				employeeIndex=(i==0)?employeeIndex+2:employeeIndex+1;
			}
			else{ // Ecola
				neededColumnNameForEcolaSettingList[i]=
						util.addSlantApostropheToString(db.tableNameEarningsAutomate)+"."
						+util.addSlantApostropheToString(db.earningsAutomateTableColumnNames[ecolaIndex]);
			}
		}
		
		//-----------------------------------------------------------------------
		
		//--> Process joinColumnList;
		String []joinColumnCompareList={
			db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameEarningsAutomate+"."+db.earningsAutomateTableColumnNames[0]	
		};
					
		
		//-----------------------------------------------------------------------
		//--> Process Condition
		//--> Add condition if regular or contractual
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		//-----------------------------------------------------------------------
		//--> Get data from database.
		
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameEarningsAutomate},
			neededColumnNameForEcolaSettingList,
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(
				new String[]{ 
						db.employeeTableColumnNames[2]}, // LastName/FamilyName
				"ASC"
			),
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------
		//--> Update Table
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		
		
		//-----------------------------------------------------------------------
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().ecolaOptionTitleImg);
				
				
		//--> Setting UI components
		
		boolean isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
		isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
	}
	
	/**
	 * Execute when you click the Laundry Allowanceon options at setting view,
	 */
	private void processLaundryAllowanceSetting(){
		
		//-----------------------------------------------------------------------
		//--> Initialize
		System.out.println(THIS_CLASS_NAME+"Show LAUNDRY ALLOWANCE settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_LAUNDRY_ALLOWANCE;
		table=settingsViewPanel.fullScreenTable;
		
		//-----------------------------------------------------------------------
		//--> Process needed column names
		String[] neededColumnNameForLaundryAllowanceSettingList=new String[3+(1)]; // 3->EmployeeID, LastName and FirstName | 1-> LaundryAllowanceValue. 
		System.out.println("\t\t Total needed LAUNDRY ALLOWANCE setting columnList: "+neededColumnNameForLaundryAllowanceSettingList.length+CLASS_NAME);
		for(int i=0,employeeIndex=0,laundryAllowanceIndex=2;i<neededColumnNameForLaundryAllowanceSettingList.length;i++){
			if(i<=2){ // EmployeeID, LastName, FirstName
				neededColumnNameForLaundryAllowanceSettingList[i]=
						util.addSlantApostropheToString(db.tableNameEmployee)+"."
						+util.addSlantApostropheToString(db.employeeTableColumnNames[employeeIndex]);
						
						
				
				employeeIndex=(i==0)?employeeIndex+2:employeeIndex+1;
			}
			else{ // LaundyAllowance
				neededColumnNameForLaundryAllowanceSettingList[i]=
						util.addSlantApostropheToString(db.tableNameEarningsAutomate)+"."
						+util.addSlantApostropheToString(db.earningsAutomateTableColumnNames[laundryAllowanceIndex]);
			}
		}
		
		//-----------------------------------------------------------------------
		
		//--> Process joinColumnList;
		String []joinColumnCompareList={
			db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameEarningsAutomate+"."+db.earningsAutomateTableColumnNames[0]	
		};
					
		
		//-----------------------------------------------------------------------
		//--> Process Condition
		//--> Add condition if regular or contractual
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		
		//-----------------------------------------------------------------------
		//--> Get data from database.
		
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameEarningsAutomate},
			neededColumnNameForLaundryAllowanceSettingList,
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(
				new String[]{ 
						db.employeeTableColumnNames[2]}, // LastName/FamilyName
				"ASC"
			),
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------
		//--> Update Table
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		
		
		//-----------------------------------------------------------------------
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().laundryAllowanceOptionTitleImg);
				
				
		//--> Setting UI components
		
		boolean isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
		isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
	}
	
	/**
	 * Execute when you click the Longevity on options at setting view,
	 */
	private void processLongevitySetting(){
		//-----------------------------------------------------------------------
		//--> Initialize
		System.out.println(THIS_CLASS_NAME+"Show LONGEVITY settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_LONGEVITY;
		table=settingsViewPanel.fullScreenTable;
		
		//-----------------------------------------------------------------------
		//--> Process needed column names
		String[] neededColumnNameForLongevitySettingList=new String[3+(1)]; // 3->EmployeeID, LastName and FirstName | 1-> LongevityValue. 
		System.out.println("\t\t Total needed LONGEVITY setting columnList: "+neededColumnNameForLongevitySettingList.length+CLASS_NAME);
		for(int i=0,employeeIndex=0,longevityIndex=3;i<neededColumnNameForLongevitySettingList.length;i++){
			if(i<=2){ // EmployeeID, LastName, FirstName
				neededColumnNameForLongevitySettingList[i]=
						util.addSlantApostropheToString(db.tableNameEmployee)+"."
						+util.addSlantApostropheToString(db.employeeTableColumnNames[employeeIndex]);
						
						
				
				employeeIndex=(i==0)?employeeIndex+2:employeeIndex+1;
			}
			else{ // Longevity
				neededColumnNameForLongevitySettingList[i]=
						util.addSlantApostropheToString(db.tableNameEarningsAutomate)+"."
						+util.addSlantApostropheToString(db.earningsAutomateTableColumnNames[longevityIndex]);
			}
		}
		
		//-----------------------------------------------------------------------
		
		//--> Process joinColumnList;
		String []joinColumnCompareList={
			db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameEarningsAutomate+"."+db.earningsAutomateTableColumnNames[0]	
		};
			
		
		//-----------------------------------------------------------------------
		//--> Process Condition
		//--> Add condition if regular or contractual
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		
		//-----------------------------------------------------------------------
		//--> Get data from database.
		
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameEarningsAutomate},
			neededColumnNameForLongevitySettingList,
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(
				new String[]{ 
						db.employeeTableColumnNames[2]}, // LastName/FamilyName
				"ASC"
			),
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------
		//--> Update Table
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		
		
		//-----------------------------------------------------------------------
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().longevityOptonTitleImg);
				
				
		//--> Setting UI components
		
		boolean isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
		isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
	}
	
	/**
	 * Execute when you click the Rice on options at setting view,
	 */
	private void processRiceSetting(){
		
		//-----------------------------------------------------------------------
		//--> Initialize
		System.out.println(THIS_CLASS_NAME+"Show RICE settings"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		settingsViewPanel.mode=Constant.SETTING_RICE;
		table=settingsViewPanel.fullScreenTable;
		
		//-----------------------------------------------------------------------
		//--> Process needed column names
		String[] neededColumnNameForRiceSettingList=new String[3+(1)]; // 3->EmployeeID, LastName and FirstName | 1-> RICEValue. 
		System.out.println("\t\t Total needed RICE setting columnList: "+neededColumnNameForRiceSettingList.length+CLASS_NAME);
		for(int i=0,employeeIndex=0,riceIndex=4;i<neededColumnNameForRiceSettingList.length;i++){
			if(i<=2){ // EmployeeID, LastName, FirstName
				neededColumnNameForRiceSettingList[i]=
						util.addSlantApostropheToString(db.tableNameEmployee)+"."
						+util.addSlantApostropheToString(db.employeeTableColumnNames[employeeIndex]);
						
						
				
				employeeIndex=(i==0)?employeeIndex+2:employeeIndex+1;
			}
			else{ // Longevity
				neededColumnNameForRiceSettingList[i]=
						util.addSlantApostropheToString(db.tableNameEarningsAutomate)+"."
						+util.addSlantApostropheToString(db.earningsAutomateTableColumnNames[riceIndex]);
			}
		}
		
		//-----------------------------------------------------------------------
		
		//--> Process joinColumnList;
		String []joinColumnCompareList={
			db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameEarningsAutomate+"."+db.earningsAutomateTableColumnNames[0]	
		};
					
		
		//-----------------------------------------------------------------------
		//--> Process Condition
		//--> Add condition if regular or contractual
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		
		//-----------------------------------------------------------------------
		//--> Get data from database.
		
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameEarningsAutomate},
			neededColumnNameForRiceSettingList,
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(
				new String[]{ 
						db.employeeTableColumnNames[2]}, // LastName/FamilyName
				"ASC"
			),
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------
		//--> Update Table
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		
		
		//-----------------------------------------------------------------------
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().riceOptionTitleImg);
				
				
		//--> Setting UI components
		
		boolean isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
		isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
		
		
	}
	
	/**
	 * Execute process where the Department menu item setting is clicked.
	 */
	private void processContractualSalaryRateSetting(){
		System.out.println(THIS_CLASS_NAME+"Show Contractual Rate Per Day settings"+CLASS_NAME);
//		
		Database db=Database.getInstance();

		settingsViewPanel.mode=Constant.SETTING_CONTRACTUAL_RATE_PER_DAY;
		table=settingsViewPanel.fullScreenTable;
		
		db.selectDataInDatabase(
				new String[]{db.tableNameContractualSalaryRate}, 
				null,
				null,
				null,
				new OrderByInfo(
					new String[]{ 
							db.contractualSalaryRateColumnNames[1]}, 
					"ASC"
				),
				Constant.SELECT_ALL);
		settingsViewPanel.fullScreenTable.isAutoResize=true;
		settingsViewPanel.fullScreenTable.updateTable(db);
		settingsViewPanel.fullScreenTable.hideColumns(new int[]{0});
		
	
		
		//--> Set Option Title Image
		settingsViewPanel.optionSettingTitleLabel.setIcon(Images.getInstance().contractualRatePerDayTitleImg);
		
		
		//--> Setting UI components
		boolean isVisible=true;
		settingsViewPanel.fullScreenTableScrollPane.setVisible(isVisible);
		
		isVisible=false;
		settingsViewPanel.fixedTableScrollPane.setVisible(isVisible);
		settingsViewPanel.dynamicTableScrollPane.setVisible(isVisible);
		
	}
	private void l______________________________________________l(){}
	
	/**
	 * Execute process that will happen when you click the ADD button.
	 */
	private void processAddButton(){
		System.out.println(THIS_CLASS_NAME+"ADD settings content!"+CLASS_NAME);
		boolean canAdd=true;
		
		switch(settingsViewPanel.mode){
			case Constant.SETTING_PHIC_SALARY:{
				canAdd=false;
				break;
			}
			case Constant.SETTING_PHIC_RATE:{
				canAdd=false;
				break;
			}
			case Constant.SETTING_SSS:{
				canAdd=false;
				break;
			}
			default:{
				break;
			}
		}
		
		
		
//		if(canAdd){
//			settingsDialog.addDepartmentTextfield.setText("");
//			
//			//--> Set visible  of necessary UI components.
//			settingsDialog.setVisibleButtonsWhenAdd();
//			System.out.println("\tCan Add"+CLASS_NAME);
//		}
//		else{
			mainFrame.showOptionPaneMessageDialog("You cannot add another data.", JOptionPane.ERROR_MESSAGE);
//		}
	}
	
	
	/**
	 * Execute process that will happen when you click the EDIT button.
	 */
	private void processEditButton(){
		
		Utilities util= Utilities.getInstance();
		if(util.authorizationLevel==Constant.ADMIN_AUTHORIZATION_LEVEL ||
				util.authorizationLevel==Constant.USER_AUTHORIZATION_LEVEL){
			
			System.out.println(THIS_CLASS_NAME+"EDIT settings content!"+CLASS_NAME);
			
			if(table.getModel().getRowCount()>0){
				String[] columnsToBeEditableList = null;
				
				System.out.println("\tSettings ASASAS Mode: "+settingsViewPanel.mode+CLASS_NAME);
				
				//--> Settings Mode
				switch(settingsViewPanel.mode){
					case Constant.SETTING_DEPARTMENT:{
						columnsToBeEditableList=new String[]{db.departmentTableColumnNames[1]};
						break;
					}
					case Constant.SETTING_PHIC_SALARY:{
						columnsToBeEditableList=new String[]{db.phicTableColumnNames[1],db.phicTableColumnNames[2],db.phicTableColumnNames[3]};
						
						//--> PHIc salary range change to combobox editor.
						JComboBox<String> status = new JComboBox<String>();
						status.addItem("Dynamic");
						status.addItem("Static");
						table.setDefaultEditor(table.getModel().getColumnClass(3), new DefaultCellEditor(status));
						
						break;
					}
					case Constant.SETTING_PHIC_RATE:{
						columnsToBeEditableList=new String[]{db.phicRateTableColumnNames[1]};
						break;
					}
					case Constant.SETTING_SSS:{
						columnsToBeEditableList=new String[db.sssTableColumnNames.length-1];
						for(int i=0,j=1;i<columnsToBeEditableList.length;i++,j++){
							columnsToBeEditableList[i]=db.sssTableColumnNames[j];
						}
						break;
					}
					case Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI:{
						settingsViewPanel.edit15th30thTotalDialog.setVisible(true);
						break;
					}
					case Constant.SETTING_PAGIBIG:{
						
						columnsToBeEditableList=new String[db.pagibigTableColumnNames.length-2]; // Not inculded the employeeID
						
						for(int i=0,pagibigIndex=1;i<columnsToBeEditableList.length;i++,pagibigIndex++){
							columnsToBeEditableList[i]=db.pagibigTableColumnNames[pagibigIndex];
						}
						break;
					}
					case Constant.SETTING_ST_PETER:{
						settingsViewPanel.edit15th30thTotalDialog.setVisible(true);
						break;
					}
					case Constant.SETTING_UNION_DUES:{
						columnsToBeEditableList=new String[1]; // Only the Total
						columnsToBeEditableList[0]=db.unionDuesTableColumnNames[db.unionDuesTableColumnNames.length-1];
						
						break;
					}
					case Constant.SETTING_ECOLA:{
						columnsToBeEditableList=new String[1]; // Only the Total
						columnsToBeEditableList[0]=db.earningsAutomateTableColumnNames[1];
						
						break;
					}
					case Constant.SETTING_LAUNDRY_ALLOWANCE:{
						columnsToBeEditableList=new String[1]; // Only the Total
						columnsToBeEditableList[0]=db.earningsAutomateTableColumnNames[2];
						
						break;
					}
					case Constant.SETTING_LONGEVITY:{
						columnsToBeEditableList=new String[1]; // Only the Total
						columnsToBeEditableList[0]=db.earningsAutomateTableColumnNames[3];
						
						break;
					}
					case Constant.SETTING_RICE:{
						columnsToBeEditableList=new String[1]; // Only the Total
						columnsToBeEditableList[0]=db.earningsAutomateTableColumnNames[4];
						
						break;
					}
					case Constant.SETTING_CONTRACTUAL_RATE_PER_DAY:{
						columnsToBeEditableList=new String[db.contractualSalaryRateColumnNames.length-1]; 
						
						for(int i=1;i<db.contractualSalaryRateColumnNames.length;i++){
							columnsToBeEditableList[i-1]=db.contractualSalaryRateColumnNames[i];
						}
						break;
					}
					default:{
						break;
					}
				}
	
				
				//--> Set desired column to be editable except asemco and set peter since they have different/TWO modes.
				if(settingsViewPanel.mode!=Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI &&
						settingsViewPanel.mode!=Constant.SETTING_ST_PETER){
				
					table.setTableColumnsThatAreEditable(columnsToBeEditableList);
					table.updateTableStateNotContent();
				
				
					//--> Make sure that in pagibig and nd uniondues mode, DO NOT hide the first column
					if(settingsViewPanel.mode!=Constant.SETTING_PAGIBIG &&
							settingsViewPanel.mode!=Constant.SETTING_UNION_DUES &&
							settingsViewPanel.mode!=Constant.SETTING_ECOLA &&
							settingsViewPanel.mode!=Constant.SETTING_LAUNDRY_ALLOWANCE &&
							settingsViewPanel.mode!=Constant.SETTING_LONGEVITY &&
							settingsViewPanel.mode!=Constant.SETTING_RICE){
						
						settingsViewPanel.fullScreenTable.hideColumns(new int[]{0});
					}
					
					
					//-----------------------------------------------------------------------
					
					//--> Set Edit into true;
					Utilities.getInstance().isEdit=true;
					
					//--> Set visible  of necessary UI components.
					settingsViewPanel.setSaveCancelBtnVisible(true);
					settingsViewPanel.setAddEditDeleteBtnVisible(false);
					
				}
				
			}
			else{
				mainFrame.showOptionPaneMessageDialog("Please select a setting first.", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			mainFrame.showOptionPaneMessageDialog("You are not authorized to use this. Please contact your administrator.", JOptionPane.ERROR_MESSAGE);
		}
	}

	
	/**
	 * Execute process that will happen when you click the EDIT button ASEMCO and ST. PETER.
	 * It has separate implementation since asmeco and st peter can be edit 15th and 30th or
	 * 	Total.
	 */
	private void processExecuteEditButtonAsemcoStPeter(){
		System.out.println(THIS_CLASS_NAME+"EDIT ASEMCO/ST PETER settings content!"+CLASS_NAME);
		Edit15th30thTotalDialog dialog= settingsViewPanel.edit15th30thTotalDialog;
		
		if(!dialog.edit15th30thRadioBtn.isSelected() && !dialog.editTotalRadioBtn.isSelected()){
			mainFrame.showOptionPaneMessageDialog("Please select an edit button first.", JOptionPane.ERROR_MESSAGE);
		}
		
		else if(table.getModel().getRowCount()>0){
			String[] columnsToBeEditableList = null;
			
			System.out.println("\tSettings ASASAS Mode: "+settingsViewPanel.mode+CLASS_NAME);
			
			//--> Settings Mode
			switch(settingsViewPanel.mode){
				
				case Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI:{
					
					if(isEdit15th30thColumn){
						columnsToBeEditableList=new String[(db.abodcTableColumnNames.length-1)-5]; // 10 columns to be edited.
						
						for(int i=0,j=1;i<columnsToBeEditableList.length;j++){
							if((!(j%3==0 ))){
								columnsToBeEditableList[i]=db.abodcTableColumnNames[j];
								i++;
							}
						}
					}
					else{
						columnsToBeEditableList=new String[(db.abodcTableColumnNames.length-1)/3];
						
						for(int i=0,j=1;i<columnsToBeEditableList.length;j++){
							// Only edit the Monthly Pay
							if(j%3==0 ){
								columnsToBeEditableList[i]=db.abodcTableColumnNames[j];
								i++;
							}
						}
					}
					break;
				}
				
				case Constant.SETTING_ST_PETER:{
					if(isEdit15th30thColumn){
						columnsToBeEditableList=new String[2]; // Only the Total
						columnsToBeEditableList[0]=db.stPeterTableColumnNames[db.stPeterTableColumnNames.length-3]; //15th
						columnsToBeEditableList[1]=db.stPeterTableColumnNames[db.stPeterTableColumnNames.length-2]; // 30th
					}
					else{
						columnsToBeEditableList=new String[1]; // Only the Total
						columnsToBeEditableList[0]=db.stPeterTableColumnNames[db.stPeterTableColumnNames.length-1];
					}
					
					break;
				}
				
				default:{
					break;
				}
			}

			
			//--> Set desired column to be editable
			table.setTableColumnsThatAreEditable(columnsToBeEditableList);
			table.updateTableStateNotContent();
			
			
			//--> When hiding columns with a setting using two tables.
			switch(settingsViewPanel.mode){
				
				case Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI:{
					
					//-----------------------------------------------------------------------
					
//					//--> Update Dynamic Table
					int []dynamicTableHideColumnList={0,0,0};
					settingsViewPanel.dynamicTable.isAutoResize=false;
					settingsViewPanel.dynamicTable.hideColumns(dynamicTableHideColumnList);
					
					//-----------------------------------------------------------------------
					
					//--> Update Fixed Table
					int []fixedTableHideColumnList=new int[neededColumnsForAsemcoSettng.length-dynamicTableHideColumnList.length];		
					for(int i=0;i<fixedTableHideColumnList.length;i++){
						fixedTableHideColumnList[i]=dynamicTableHideColumnList.length;
					}
					settingsViewPanel.fixedTable.isAutoResize=true;
					settingsViewPanel.fixedTable.hideColumns(fixedTableHideColumnList);
					
					//-----------------------------------------------------------------------
					break;
				}
				default:{
					//--> Make sure that stpeter, do not hide the first column
					if(settingsViewPanel.mode!=Constant.SETTING_ST_PETER ){
						settingsViewPanel.fullScreenTable.hideColumns(new int[]{0});
					}
					
					break;
				}
			}
			
			
			//-----------------------------------------------------------------------
			
			//--> Set Edit into true;
			Utilities.getInstance().isEdit=true;
			
			//--> Set visible  of necessary UI components.
			settingsViewPanel.setSaveCancelBtnVisible(true);
			settingsViewPanel.setAddEditDeleteBtnVisible(false);
			
			settingsViewPanel.edit15th30thTotalDialog.dispose();
			
		}
		else{
			mainFrame.showOptionPaneMessageDialog("Please select a setting first.", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 *  Execute process that will happen when you click the back button.
	 */
	private void processCancelButton(){
		System.out.println(THIS_CLASS_NAME+"CANCEL settings content!"+CLASS_NAME);
		//--> Make table editable
		table.setAllTablesNotEditable(db, Constant.EDIT_ONCE);
		table.updateTableStateNotContent();
		
		
		//--> Settings mode
		switch(settingsViewPanel.mode){
			
			case Constant.SETTING_PHIC_SALARY:{
				//--> Return cell editor to default.
				table.setDefaultEditor(table.getModel().getColumnClass(3), new DefaultCellEditor(new JTextField()));
				
				break;
			}
			case Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI:{
				//-----------------------------------------------------------------------
				
//				//--> Update Dynamic Table
				int []dynamicTableHideColumnList={0,0,0};
				settingsViewPanel.dynamicTable.isAutoResize=false;
				settingsViewPanel.dynamicTable.hideColumns(dynamicTableHideColumnList);
				
				//-----------------------------------------------------------------------
				
				//--> Update Fixed Table
				int []fixedTableHideColumnList=new int[neededColumnsForAsemcoSettng.length-dynamicTableHideColumnList.length];		
				for(int i=0;i<fixedTableHideColumnList.length;i++){
					fixedTableHideColumnList[i]=dynamicTableHideColumnList.length;
				}
				settingsViewPanel.fixedTable.isAutoResize=true;
				settingsViewPanel.fixedTable.hideColumns(fixedTableHideColumnList);
				
				break;
			}
			default:{
				//--> Make sure that in pagibig and stpeter mode, do not hide the first column
				if(settingsViewPanel.mode!=Constant.SETTING_PAGIBIG &&
						settingsViewPanel.mode!=Constant.SETTING_ST_PETER &&
						settingsViewPanel.mode!=Constant.SETTING_UNION_DUES &&
						settingsViewPanel.mode!=Constant.SETTING_ECOLA &&
						settingsViewPanel.mode!=Constant.SETTING_LAUNDRY_ALLOWANCE &&
						settingsViewPanel.mode!=Constant.SETTING_LONGEVITY &&
						settingsViewPanel.mode!=Constant.SETTING_RICE ){
					
					settingsViewPanel.fullScreenTable.hideColumns(new int[]{0});
				}
				break;
			}
		}
				
		//--> Set Edit to false
		Utilities.getInstance().isEdit=false;
		
		//--> Set visible  of necessary UI components.
		settingsViewPanel.setSaveCancelBtnVisible(false);
		settingsViewPanel.setAddEditDeleteBtnVisible(true);
	}
	
	
	/**
	 * Execute process that will happen when you click the SAVE button.
	 */
	private void processSaveButton(){
		System.out.println(THIS_CLASS_NAME+"SAVE settings content!"+CLASS_NAME);
	
		String tableName=null,columnNameID=null;
		//--> Settings mode
		switch(settingsViewPanel.mode){
			case Constant.SETTING_DEPARTMENT:{
				tableName=db.tableNameDepartment;
				columnNameID=db.departmentTableColumnNames[0];
				break;
			}
			case Constant.SETTING_PHIC_SALARY:{
				tableName=db.tableNamePhic;
				columnNameID=db.phicTableColumnNames[0];
				break;
			}
			case Constant.SETTING_PHIC_RATE:{
				tableName=db.tableNamePhicRate;
				columnNameID=db.phicRateTableColumnNames[0];
				break;
			}
			case Constant.SETTING_SSS:{
				tableName=db.tableNameSss;
				columnNameID=db.sssTableColumnNames[0];
				break;
			}
			case Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI:{
				tableName=db.tableNameABODC;
				columnNameID=db.abodcTableColumnNames[0];
				break;
			}
			case Constant.SETTING_PAGIBIG:{
				tableName=db.tableNamePagibig;
				columnNameID=db.pagibigTableColumnNames[0];
				break;
			}
			case Constant.SETTING_ST_PETER:{
				tableName=db.tableNameStPeter;
				columnNameID=db.stPeterTableColumnNames[0];
				break;
			}
			case Constant.SETTING_UNION_DUES:{
				tableName=db.tableNameUnionDues;
				columnNameID=db.unionDuesTableColumnNames[0];
				break;
			}
			//-----
			case Constant.SETTING_ECOLA:{
				tableName=db.tableNameEarningsAutomate;
				columnNameID=db.earningsAutomateTableColumnNames[0];
				break;
			}
			case Constant.SETTING_LAUNDRY_ALLOWANCE:{
				tableName=db.tableNameEarningsAutomate;
				columnNameID=db.earningsAutomateTableColumnNames[0];
				break;
			}
			case Constant.SETTING_LONGEVITY:{
				tableName=db.tableNameEarningsAutomate;
				columnNameID=db.earningsAutomateTableColumnNames[0];
				break;
			}
			case Constant.SETTING_RICE:{
				tableName=db.tableNameEarningsAutomate;
				columnNameID=db.earningsAutomateTableColumnNames[0];
				break;
			}
			//---
			case Constant.SETTING_CONTRACTUAL_RATE_PER_DAY:{
				tableName=db.tableNameContractualSalaryRate;
				columnNameID=db.contractualSalaryRateColumnNames[0];
				break;
			}
			default:{
				break;
			}
		}
		
		//--> Save if there are changes.
		if(table.getModel().isThereAreChanges()){
			settingsViewPanel.multipleUpdateList.clear();
			ArrayList<MultipleUpdateDatabaseModel> multipleUpdateList=settingsViewPanel.multipleUpdateList;
			PayrollTableModel tableModel=table.getModel();
			
			for(int i=0;i<table.getModel().isEditedRowsList.length;i++){
				//--> Check if the row is edited.
				if(table.getModel().isEditedRowsList[i]){
					multipleUpdateList.add(new MultipleUpdateDatabaseModel());
					
					MultipleUpdateDatabaseModel update=multipleUpdateList.get(multipleUpdateList.size()-1);
					//--> Add the primary key of the edited row.
					// Why the asemco part is different? Since the asemco setting used fixed table and dynamic table
					if(settingsViewPanel.mode==Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI){
						update.primarykey=settingsViewPanel.fixedTable.getModel().getValueAt(i, 0).toString();
					}
					else{
						update.primarykey=table.getModel().getValueAt(i, 0).toString();
					}
					
					//------------------------------------------------------------
					//--> Why 3 since the ASEMCOFirstPay column is at index 3.
					int j=3;
					if(	settingsViewPanel.mode==Constant.SETTING_DEPARTMENT ||
						settingsViewPanel.mode==Constant.SETTING_PHIC_SALARY ||
						settingsViewPanel.mode==Constant.SETTING_PHIC_RATE ||
						settingsViewPanel.mode==Constant.SETTING_SSS ||
						settingsViewPanel.mode==Constant.SETTING_CONTRACTUAL_RATE_PER_DAY
						
						){
						j=1;
					}
					//--> When the row was edited, store all columns with corresponding values to avoid truncate error/bunkeg
					for(;j<table.getModel().getColumnCount();j++){
						
						String columnName=util.removeSpacesToBeConvertedToCamelCase(tableModel.getColumnName(j));
						Object value=(util.isStringANumber(tableModel.getValueAt(i, j).toString()))?
							util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(tableModel.getValueAt(i, j).toString()))
							:
							tableModel.getValueAt(i, j).toString()
						;
						update.changesToBeUpdated.put(
								columnName,
								value);
					}
					
					
					//----------------------------------------------------------------------------------
					
					//--> Why 3 since the ASEMCOFirstPay column is at index 3.
					//>		Here process the AUTOMATIC addition or something.  AUTOMATION
					for(j=3;j<table.getModel().getColumnCount() && 
							(	settingsViewPanel.mode==Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI ||
								settingsViewPanel.mode==Constant.SETTING_PAGIBIG ||
								settingsViewPanel.mode==Constant.SETTING_ST_PETER);
						j++){
						
						
						//--> Get all column and check if each column is edited then change the necessaru values.
						if(tableModel.data[i][j]!=tableModel.copiedData[i][j]){
							
							
							//--> Calculate the half value automatically when editing ASEMCO/BCCI/OCCCI/DBP/CFI Setting
							if(settingsViewPanel.mode==Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI){
								
								if(isEdit15th30thColumn){
									int totalIndex=0;
									for(int k=1;k<=5;k++){ // 5
										if(j<(k*3)){
											totalIndex=(k*3);
											break;
										}
									}
									
									totalIndex--;
									System.out.println("\n\tFUCK Total Index: "+totalIndex
											+"\tJ: "+j
											+"\t(totalIndex-2)==j: "+((totalIndex-2)==j)
											+"\t(totalIndex-1)==j: "+((totalIndex-1)==j)+CLASS_NAME);
									if((totalIndex-2)==j){ // 15th
										double totalValue=
												Double.parseDouble(tableModel.getValueAt(i, j).toString())
												+Double.parseDouble(tableModel.getValueAt(i, (j+1)).toString());
										
										System.out.println("\t\t (totalIndex-2)==j) TOTAL VALUE: "+totalValue+CLASS_NAME);
										
										//> Update for Total column
										update.changesToBeUpdated.put(
											util.removeSpacesToBeConvertedToCamelCase(
												tableModel.getColumnName(totalIndex)),
												util.convertRoundToOnlyTwoDecimalPlaces(totalValue)
											);
									}
									else if((totalIndex-1)==j){ //30th
										double totalValue=
												Double.parseDouble(tableModel.getValueAt(i, j).toString())
												+Double.parseDouble(tableModel.getValueAt(i, (j-1)).toString());
										
										System.out.println("\t\t (totalIndex-1)==j) TOTAL VALUE: "+totalValue+CLASS_NAME);
										
										//> Update for Total column
										update.changesToBeUpdated.put(
											util.removeSpacesToBeConvertedToCamelCase(
												tableModel.getColumnName(totalIndex)),
												util.convertRoundToOnlyTwoDecimalPlaces(totalValue)
											);
									}
									
									
									
								}
								else{
					
									Double monthlyBasicPayValue=Double.parseDouble(tableModel.getValueAt(i, j).toString());
									Double secondPayValue=-1.0;
									Double firstPayValue=-1.0;
									
									
									// First Pay Value
									firstPayValue=util.calculateFirstHalfKinsenasPayValue(monthlyBasicPayValue);
									//Second Pay Value
									secondPayValue=util.calculateSecondHalfKinsenasPayValue(monthlyBasicPayValue, firstPayValue);
									
									
									
									//-Second Pay---------------------------
									int secondPayIndex=j-1;
									update.changesToBeUpdated.put(
											util.removeSpacesToBeConvertedToCamelCase(tableModel.getColumnName(secondPayIndex)),
											util.convertRoundToOnlyTwoDecimalPlaces(secondPayValue));
									//-First Pay---------------------------
									int firstPayIndex=j-2;
									update.changesToBeUpdated.put(
											util.removeSpacesToBeConvertedToCamelCase(tableModel.getColumnName(firstPayIndex)),
											util.convertRoundToOnlyTwoDecimalPlaces(firstPayValue));
									
								}
							}
							//--> Calculate the total in PAGIBIG Mode.
							else if(settingsViewPanel.mode==Constant.SETTING_PAGIBIG){
								int eeIndex=3,totalIndex=5; // index of EE and total column in  pagibig table 
								double totalValue=Double.parseDouble(tableModel.getValueAt(i, eeIndex).toString())+
										Double.parseDouble(tableModel.getValueAt(i, eeIndex+1).toString());
								
								//--> To avoid redundant process. Kay you can edut the EE and ER column. dapat kausa la iupdate ang total column.
								if((j==eeIndex || j==eeIndex+1) && totalValue!=Double.parseDouble(tableModel.getValueAt(i, eeIndex+2).toString())){
									update.changesToBeUpdated.put(
											util.removeSpacesToBeConvertedToCamelCase(tableModel.getColumnName(totalIndex)),
											util.convertRoundToOnlyTwoDecimalPlaces(totalValue));
								}
							}
							//--> Calculate the automatic division into two of Total for 15th and 30th column. 
							else if(settingsViewPanel.mode==Constant.SETTING_ST_PETER){
								
								if(isEdit15th30thColumn){
									int totalIndex=table.getModel().getColumnCount()-1;
									double totalValue=
											Double.parseDouble(tableModel.getValueAt(i, totalIndex-2).toString())
											+Double.parseDouble(tableModel.getValueAt(i, totalIndex-1).toString());
									
									//> Update for Total column
									update.changesToBeUpdated.put(
											util.removeSpacesToBeConvertedToCamelCase(tableModel.getColumnName(tableModel.getColumnCount()-1)),
											util.convertRoundToOnlyTwoDecimalPlaces(totalValue));
								}
								else{
									double totalValue=Double.parseDouble(tableModel.getValueAt(i, j).toString());
	
									//> Update for 30th column
									update.changesToBeUpdated.put(
											util.removeSpacesToBeConvertedToCamelCase(tableModel.getColumnName(j-1)),
											util.convertRoundToOnlyTwoDecimalPlaces(totalValue/2));
									//> Update for 15th column
									update.changesToBeUpdated.put(
											util.removeSpacesToBeConvertedToCamelCase(tableModel.getColumnName(j-2)),
											util.convertRoundToOnlyTwoDecimalPlaces(totalValue/2));
								}
							}
					
						}
						
					}
				}
			}
			
			db.updateDataInDatabase(
					tableName, 
					null,
					columnNameID, 
					null, 
					true, 
					multipleUpdateList);
			
			//--> Pop up Dialog if update is successful
			if(db.isUpdate){
				 JOptionPane.showMessageDialog(null, (db.totalUpdates==1)?"An existing data was updated successfully!":
						 "Updates have been executed successfully!", 
							null, JOptionPane.INFORMATION_MESSAGE);
			}

			
			//--> Reuse code. Show the updated table when the insert is successfully.
			if(db.isUpdate){
				//--> Make table NOT editable
				table.setAllTablesNotEditable(db, Constant.EDIT_ONCE);
				table.updateTableStateNotContent();
				
				//--> Settings mode
				switch(settingsViewPanel.mode){
					case Constant.SETTING_DEPARTMENT:{
						//-->Reuse code.  Show updated tables.
						processDepartmentSetting();
						
						//-->Update department data list
						db.initializeDepartmentNametData();
	
						//--> Update ALL UI departmentComboBoxes
						Utilities.getInstance().updateAllUIDepartmentComboboxes(db);
						break;
					}
					case Constant.SETTING_PHIC_SALARY:{
						//-->Reuse code.  Show updated tables.
						processPhicSalaryRange();
						
						//-->Update phic salary data list
						db.initializePhicSalaryAndRateData();
						
						//--> Return cell editor to default.
						table.setDefaultEditor(table.getModel().getColumnClass(3), new DefaultCellEditor(new JTextField()));
						
						break;
					}
					case Constant.SETTING_PHIC_RATE:{
						//-->Reuse code.  Show updated tables.
						processPhicRate();
						
						//-->Update phic rate data list
						db.initializePhicSalaryAndRateData();
						break;
					}
					case Constant.SETTING_SSS:{
						//-->Reuse code.  Show updated tables.
						processSSSSetting();
						
						//-->Update phic rate data list
						db.initializeSssData();
						break;
					}
					case Constant.SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI:{
						//-->Reuse code.  Show updated tables.
						processAsemcoBcciOccciDbpCfiSetting();
						break;
					}
					case Constant.SETTING_PAGIBIG:{
						//--> Reuse code
						processPagibigSetting();
						break;
					}
					case Constant.SETTING_ST_PETER:{
						//--> Reuse code
						processStPeterSetting();
						break;
					}
					case Constant.SETTING_UNION_DUES:{
						//--> Reuse code
						processUnionDuesSetting();
						break;
					}
					case Constant.SETTING_ECOLA:{
						//--> Reuse code
						processEcolaSetting();
						break;
					}
					case Constant.SETTING_LAUNDRY_ALLOWANCE:{
						//--> Reuse code
						processLaundryAllowanceSetting();
						break;
					}
					case Constant.SETTING_LONGEVITY:{
						//--> Reuse code
						processLongevitySetting();
						break;
					}
					case Constant.SETTING_RICE:{
						//--> Reuse code
						processRiceSetting();
						break;
					}
						
					case Constant.SETTING_CONTRACTUAL_RATE_PER_DAY:{
						//-->Reuse code.  Show updated tables.
						processContractualSalaryRateSetting();
						
						//-->Update department data list
						db.initializeContractualSalaryRateData();
	
						break;
					}
	
					default:{
						break;
					}
				}
			}
			
			
			Utilities.getInstance().isEdit=false;
			//--> Set visible  of necessary UI components.
			settingsViewPanel.setSaveCancelBtnVisible(false);
			settingsViewPanel.setAddEditDeleteBtnVisible(true);
		}
		else{
			mainFrame.showOptionPaneMessageDialog("You have not edited anything", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	/**
	 * Execute process that will happen when you click the DELETE button.
	 */
	private void processDeleteButton(){
		System.out.println(THIS_CLASS_NAME+"DELETE settings content!"+CLASS_NAME);
		
		mainFrame.showOptionPaneMessageDialog("You cannot delete the follwong data.", JOptionPane.ERROR_MESSAGE);
		
	}
	private void l_______________________________________________l(){}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
		ReusableTable fixedTable=settingsViewPanel.fixedTable,
				dynamicTable=settingsViewPanel.dynamicTable,
				employeeTable=settingsViewPanel.fullScreenTable;
		Utilities util=Utilities.getInstance();
		
		ReusableTable table=null;
		
		//--> Highlight the rows selected. Makes sure that the highlight feature for both tables
		//			only happens when the shown are only earning/deduction data. Not all employees.
		if(isFixedTableClicked){
			 table=fixedTable;
			 //> When you select the fixed table, the corresponding dynamuc table row is also clicked.
			if(fixedTable.getSelectedRows().length>0){
				dynamicTable.getSelectionModel().setSelectionInterval(
						fixedTable.getSelectedRows()[0], fixedTable.getSelectedRows()[fixedTable.getSelectedRows().length-1]);
			}
			//> When there are no/zero selection in fixed table, unselect also the dynamic table.
			else{
				dynamicTable.clearSelection();
				selectedTableRowIndexList=null;
			}
		}
		
		else if(isDynamicTableClicked ){
			table=dynamicTable;	
			 //> When you select the dynamic table, the corresponding fixed table row is also clicked.
			if(dynamicTable.getSelectedRows().length>0){
				fixedTable.getSelectionModel().setSelectionInterval(
						dynamicTable.getSelectedRows()[0], dynamicTable.getSelectedRows()[dynamicTable.getSelectedRows().length-1]);
			}
			//> When there are no/zero selection in dynamic table, unselect also the fixed table table.
			else{
				fixedTable.clearSelection();
				selectedTableRowIndexList=null;
			}
		}
		else if(isFullScreenTableClicked && employeeTable.getSelectedRows().length>0){
			table=employeeTable;
		}
		
		
		//--> Makes sure that a table was clicked.
		if((table!=null) && table.getSelectedRows().length>0){
			//--> Get an array of selected row indices/index.
			selectedTableRowIndexList=table.getSelectedRows();
			
			//--> Convert view index to model since the rows are interchanged in view when sorted.
			selectedTableRowIndexList=util.convertRowIndexToModel(table, selectedTableRowIndexList);
			
			//-- For debugging purposes
//			System.out.println("\t\tSelectedddd Table Row: "+CLASS_NAME);
//			for(int i=0;i<selectedTableRowIndexList.length;i++){
//				System.out.println("\t\t\t"+selectedTableRowIndexList[i]+CLASS_NAME);
//			}
		}
		
		//--> Reset value of variable table.
		table=null;
				
				

		
		
		//--> To avoid overlaps
		settingsViewPanel.repaint();
	}
	
	
	private void l__________________________________________l(){}

	//--> Default methods for MouseListener
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		ReusableTable fixedTable=settingsViewPanel.fixedTable,
				dynamicTable=settingsViewPanel.dynamicTable,
				fullScreenTable=settingsViewPanel.fullScreenTable;
		
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
		else if(e.getSource()==fullScreenTable){
//			System.out.println("\t Employee Table was clicked."+CLASS_NAME);
			
			isFullScreenTableClicked=true;
			
			isDynamicTableClicked=false;
			isFixedTableClicked=false;
		}
		

		//-------------------------------------------------------
				
		//--> Repaint to avoid overlap
		settingsViewPanel.repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		ReusableTable fixedTable=settingsViewPanel.fixedTable,
				dynamicTable=settingsViewPanel.dynamicTable,
				fullScreenTable=settingsViewPanel.fullScreenTable;
				
		
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
	
	private void l______________________________________________________l(){}
	
	 /**
	 * Set outside listeners to be used inside this class.
	 * @param mainFrameListener
	 */
	 public void setOutsideListeners(ListenerMainFrame mainFrameListener){
		 this.mainFrameListener=mainFrameListener;
	 }
}
