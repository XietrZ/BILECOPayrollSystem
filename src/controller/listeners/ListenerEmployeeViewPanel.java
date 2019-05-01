package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;






import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import model.Constant;
import model.OrderByInfo;
import model.PayrollTableModel;
import model.SelectConditionInfo;
import model.statics.Utilities;
import model.view.ReusableTable;
import view.MainFrame;
import view.dialog.AddEmployeeDialog;
import view.dialog.DeleteUpdateEmployeeDialog;
import view.views.EmployeeViewPanel;
import database.Database;

public class ListenerEmployeeViewPanel implements ActionListener,DocumentListener,ListSelectionListener,MouseListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private EmployeeViewPanel employeeViewPanel;
	private AddEmployeeDialog addEmployeeDialog;
	private DeleteUpdateEmployeeDialog bothDeleteUpdateEmployeeDialog;
	private ReusableTable fixedTable,dynamicTable;
	private boolean isFixedTableClicked=false, isDynamicTableClicked=false;
	
	public ListenerEmployeeViewPanel() {
		employeeViewPanel=EmployeeViewPanel.getInstance();
		addEmployeeDialog=AddEmployeeDialog.getInstance();
		bothDeleteUpdateEmployeeDialog=DeleteUpdateEmployeeDialog.getInstance();
		
		
		fixedTable=employeeViewPanel.fixedTable;
		dynamicTable=employeeViewPanel.dynamicTable;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//--> Check the disconnection status of database.
		Database.getInstance().checkIfDisconnectedToDatabase();
		
		//--> Only Process When Connected.
		if(Database.getInstance().isConnected){
			// TODO Auto-generated method stub
			//--> SHOW OPTIONS
			if(e.getSource()==employeeViewPanel.employeeOptionButton){
				employeeViewPanel.optionPanel.setVisible((employeeViewPanel.optionPanel.isVisible()?false:true));
			}
			//-->Chosen Options
			else {
	
				for(String key:employeeViewPanel.optionPanel.buttonList.keySet()){
					JButton optionButton=employeeViewPanel.optionPanel.buttonList.get(key);
					
					if(e.getSource()==optionButton){
						//--> SHOW EMPLOYEE SUMMARY
						if(key.equals("Show Summary")){ 
							processShowEmployeeDataSummary();
						}
						//--> ADD EMPLOYEE
						else if(key.equals("Add Employee")){
							processAddEmployee();
						}
						//--> UPDATE/DELETE
						else if(key.equals("Update/Delete")){
							processUpdateDeleteEmployee();
						}
						
						employeeViewPanel.optionPanel.setVisible(false);
						break;
						
					}
	
				}
			}

		}
	}
	private void l______________________________________________________________________l(){}
	/**
	 * Execute process that will happen when you click the show employee data summary button.
	 */
	public void processShowEmployeeDataSummary(){
		System.out.println(THIS_CLASS_NAME+"Show Employee Data Summary"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util= Utilities.getInstance();
		
		//-----------------------------------------------------------------------
		
		
		//--> Select data from database for dynamic table and update
		String[] desiredColumnNamesList=new String[db.employeeTableColumnNames.length];
		for(int  i=0;i<desiredColumnNamesList.length;i++){
			if(i==14){// DepartmentID
				desiredColumnNamesList[i]=db.departmentTableColumnNames[1]; // Departmemt
			}
			else{
				desiredColumnNamesList[i]=db.employeeTableColumnNames[i];
			}
			
		}
		
		//-----------------------------------------------------------------------
		
		
		//--> Set the values for joinCompareList.
		String [] joinCompareList={
				util.addSlantApostropheToString(db.tableNameEmployee)+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[0])
				+"="+util.addSlantApostropheToString(db.tableNameDepartment)+"."+util.addSlantApostropheToString(db.departmentTableColumnNames[0])
		};
		
		//-----------------------------------------------------------------------
		//--> Process Order
		OrderByInfo orderInfo=new OrderByInfo(
			new String[]{
				db.employeeTableColumnNames[2],	// LastName
				db.employeeTableColumnNames[3] // First Name
			}, 
					
			"ASC"
		);
		
		//-----------------------------------------------------------------------
		//--> Process Condition
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		
		
		//-----------------------------------------------------------------------

		
		//--> Get Data from Database.
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameDepartment}, 
			desiredColumnNamesList, 
			conditionColumnAndValueList, 
			joinCompareList, // DepartmentID
			orderInfo,
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_OR
		);
		
		//-----------------------------------------------------------------------
		
		
		//--> Update Dynamic Table
		int []dynamicTableHideColumnList={0,0,0,0,0,0};
		dynamicTable.isAutoResize=false;
		dynamicTable.updateTable(db);
		dynamicTable.hideColumns(dynamicTableHideColumnList);
		
		//-----------------------------------------------------------------------
		
		//--> Update Fixed Table
		int []fixedTableHideColumnList=new int[db.employeeTableColumnNames.length-dynamicTableHideColumnList.length];		
		for(int i=0;i<fixedTableHideColumnList.length;i++){
			fixedTableHideColumnList[i]=dynamicTableHideColumnList.length;
		}
		
		fixedTable.isAutoResize=true;
//		fixedTable.updateTableStateNotContent(); // No need to update since it the model itself was updated in dynamic table
		fixedTable.hideColumns(fixedTableHideColumnList);
		
		//-----------------------------------------------------------------------
		
		//--> Update Row Count Label
		employeeViewPanel.rowCountLabel.setText("Row Count: "+fixedTable.getModel().getRowCount());
		
		//-----------------------------------------------------------------------
		
		//--> Repaint to avoid table overlapping other components
		employeeViewPanel.repaint();
	}
	
	/**
	 * Execute process that will happen when you click the add employee button.
	 */
	private void processAddEmployee(){
		model.statics.Utilities util= model.statics.Utilities.getInstance();
		
		
		if(util.authorizationLevel==Constant.ADMIN_AUTHORIZATION_LEVEL ||
				util.authorizationLevel==Constant.USER_AUTHORIZATION_LEVEL){
			
			System.out.println(THIS_CLASS_NAME+"Add Empoyee"+CLASS_NAME);
			
			Database db = Database.getInstance();
			
			addEmployeeDialog.contentPanel.employeeIDTextField.setText("");
			addEmployeeDialog.setVisible(true);
		}
		else{
			MainFrame.getInstance().showOptionPaneMessageDialog("You are not authorized to use this. Please contact your administrator.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Execute process that will happen when you click the upate/delete employee.
	 */
	private void processUpdateDeleteEmployee(){
		
		Utilities util= Utilities.getInstance();
		if(util.authorizationLevel==Constant.ADMIN_AUTHORIZATION_LEVEL){
			
			System.out.println(THIS_CLASS_NAME+"Add Empoyee"+CLASS_NAME);
			
			Database db= Database.getInstance();
			
			//-----------------------------------------------------------------------
			//--> Process Condition
			//--> Add condition if regular or contractual
			ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"HiredAs",
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
				)
			);
			
			//----------------------------------------------------------------------
			db.selectDataInDatabase(
					new String[]{db.tableNameEmployee}, 
					new String[]{db.employeeTableColumnNames[0],
						db.employeeTableColumnNames[2],
						db.employeeTableColumnNames[3],
						db.employeeTableColumnNames[4]} ,
					conditionColumnAndValueList,
					null,
					new OrderByInfo(
						new String[]{
							db.employeeTableColumnNames[2],	// LastName
							db.employeeTableColumnNames[3] // First Name
						}, 
								
						"ASC"
					),
					Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR);
			
			bothDeleteUpdateEmployeeDialog.employeeListTable.updateTable(db);
			bothDeleteUpdateEmployeeDialog.updateRowCountLabel(bothDeleteUpdateEmployeeDialog.employeeListTable.getModel().getRowCount());
			bothDeleteUpdateEmployeeDialog.setVisible(true);
		}
		else{
			MainFrame.getInstance().showOptionPaneMessageDialog("You are not authorized to use this. Please contact your administrator.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void l______________________________________________________l(){}
	//--> Default methods in DocumentListener

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument()==EmployeeViewPanel.getInstance().filterTextField.getDocument()){
			
			employeeViewPanel.executeSearchMechanismOfAllTables();
			
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument()==EmployeeViewPanel.getInstance().filterTextField.getDocument()){
			
			employeeViewPanel.executeSearchMechanismOfAllTables();
		}
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument()==EmployeeViewPanel.getInstance().filterTextField.getDocument()){
			
			employeeViewPanel.executeSearchMechanismOfAllTables();
		}		
		
	}
	private void l________________________________________________________l(){}
	
	//--> Default methods for ListSelectionListener
	@Override
	public void valueChanged(ListSelectionEvent arg0) {

		ReusableTable fixedTable=employeeViewPanel.fixedTable,
				dynamicTable=employeeViewPanel.dynamicTable;
		
		
		//----------------------------------------------
		//--> Use for Table Highlght
		
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
		employeeViewPanel.repaint();

	}
	
	

	
	private void l_________________________________________________________________l(){}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//--> Set visible false components that are not needed.
		employeeViewPanel.optionPanel.setVisible(false);
		MainFrame.getInstance().menuPanel.setVisible(false);
		
		//------------------------------------------------------
		
		//--> Use for Table Highlight.
		if(e.getSource()==fixedTable){
//			System.out.println("\t Fixed Table was clicked."+CLASS_NAME);
			isFixedTableClicked=true;
			isDynamicTableClicked=false;
		}
		else if(e.getSource()==dynamicTable){
//			System.out.println("\t Dynamic Table was clicked."+CLASS_NAME);
			isDynamicTableClicked=true;
			isFixedTableClicked=false;
		}
		
		
		
		//------------------------------------------------------
		
		//--> Repaint to avoid overlap
		employeeViewPanel.repaint();
				
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==fixedTable){
//			System.out.println("\t Fixed Table was clicked."+CLASS_NAME);
			isFixedTableClicked=true;
			isDynamicTableClicked=false;
		}
		else if(e.getSource()==dynamicTable){
//			System.out.println("\t Dynamic Table was clicked."+CLASS_NAME);
			isDynamicTableClicked=true;
			isFixedTableClicked=false;
		}
		
		//------------------------------------------------------
		
		//--> Repaint to avoid overlap
		employeeViewPanel.repaint();
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
	
	private void l________________________________________l(){}
	public void setters(){
		
	}
	
	
	
}

