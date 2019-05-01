package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Constant;
import model.OrderByInfo;
import model.SelectConditionInfo;
import model.statics.Utilities;
import model.view.ReusableTable;
import view.MainFrame;
import view.dialog.DeleteUpdateEmployeeDialog;
import view.dialog.UpdateEmployeeDialog;
import view.views.EmployeeViewPanel;
import database.Database;

public class ListenerDeleteUpdateEmployeeDialog implements ActionListener,ListSelectionListener {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private DeleteUpdateEmployeeDialog bothDeleteUpdateEmployeeDialog;
	private MainFrame mainFrame;
	private EmployeeViewPanel employeeViewPanel;
	private UpdateEmployeeDialog updateEmployeeDialog;
	private ListenerEmployeeViewPanel employeeViewPanelListener;
	
	private int[] selectedTableRowIndex=null;
	
	private void l________________________________l(){}
	
	public ListenerDeleteUpdateEmployeeDialog() {
		// TODO Auto-generated constructor stub
		bothDeleteUpdateEmployeeDialog= DeleteUpdateEmployeeDialog.getInstance();
		mainFrame=MainFrame.getInstance();
		updateEmployeeDialog= UpdateEmployeeDialog.getInstance(); 
		employeeViewPanel=EmployeeViewPanel.getInstance();
	}
	
	private void l_____________________________l(){}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//--> DELETE BUTTON
		if(e.getSource()==bothDeleteUpdateEmployeeDialog.deleteButton){
			System.out.println(THIS_CLASS_NAME+"Delete Employee from database."+CLASS_NAME);
			
			if(selectedTableRowIndex!=null && selectedTableRowIndex.length>0){
				bothDeleteUpdateEmployeeDialog.deleteWarningDialog.lblNewLabel.setText(
						(selectedTableRowIndex.length==1)?
						" Are you sure you want to delete? \n All data under this employee will also be \n deleted."
						:
						" Are you sure you want to delete? \n All data under these employees will \n also be deleted."
					);
				bothDeleteUpdateEmployeeDialog.deleteWarningDialog.setVisible(true);
//				processDeleteButton();
			}
			else{
				mainFrame.showOptionPaneMessageDialog(
						"You have not selected anything.", 
						JOptionPane.ERROR_MESSAGE);
			}

		}
		
		//--> UPDATE BUTTON
		else if(e.getSource()==bothDeleteUpdateEmployeeDialog.updateButton){
			System.out.println(THIS_CLASS_NAME+"Update employee info"+CLASS_NAME);
			
			processUpdateButton();
		}
		
		//--> CANCEL BUTTON
		else if(e.getSource()==bothDeleteUpdateEmployeeDialog.cancelButton){
			System.out.println(THIS_CLASS_NAME+"Cancel delete employee"+CLASS_NAME);
			
			processCancelButton();
		}
		
		//--> DEPARTMENT COMBOBOX
		else if(e.getSource()==bothDeleteUpdateEmployeeDialog.departmentComboBox){
			System.out.println(THIS_CLASS_NAME+"Show Employee depending on chosen department combobox item"+CLASS_NAME);
			
			processDepartmentComboBox();
		}
		
		//---------------------------------------------------------------------
		
		//--> DELETE WARNING DIALOG_YES
		else if(e.getSource()==bothDeleteUpdateEmployeeDialog.deleteWarningDialog.yesButton){
			processDeleteButton();
		}
		//--> DELETE WARNING DIALOG_NO
		else if(e.getSource()==bothDeleteUpdateEmployeeDialog.deleteWarningDialog.noButton){
			bothDeleteUpdateEmployeeDialog.deleteWarningDialog.dispose();
		}
		
	}
	
	private void l_______________________________________________l(){}
	/**
	 * Process the action that will happen when you click the delete button.
	 */
	private void processDeleteButton(){
		Database db=Database.getInstance();
		String[]values=new String[selectedTableRowIndex.length];
		boolean isNoError=false;
		
		for(int i=0;i<selectedTableRowIndex.length;i++){
			try {
				
				db.resultSet.absolute(selectedTableRowIndex[i]+1);
				values[i] = db.resultSet.getObject(1).toString();
				isNoError=true;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Error in Delete employee: "
						+e1.getMessage()
						+CLASS_NAME);
				MainFrame.getInstance().showOptionPaneMessageDialog(
						e1.getMessage(), JOptionPane.ERROR_MESSAGE);
				
				isNoError=false;
				break;
			}
			
		}
		
		// Continue if no error above.
		if(isNoError){
			db.deleteDataInDatabase(db.tableNameEmployee,
				db.employeeTableColumnNames[0], 
				values);
			if(db.isDelete){
				bothDeleteUpdateEmployeeDialog.deleteWarningDialog.dispose();
				
				
				//--> Popup Successful Delete!
				mainFrame.showOptionPaneMessageDialog(
				    		(values.length==1)?
				    				"An employee was deleted successfully!":
				    				"Employees were deleted successfully! Number of data entries deleted: "+values.length, 
				    		JOptionPane.INFORMATION_MESSAGE);
				
				
				System.out.println("\tReuse the code process show employee data summary!!!"+CLASS_NAME);
				//--> After delete, show the updated result in table. Just reuse the code.
				employeeViewPanelListener.processShowEmployeeDataSummary();	
				
				//--> Set necessary UI components
				bothDeleteUpdateEmployeeDialog.deleteWarningDialog.dispose();
				bothDeleteUpdateEmployeeDialog.dispose();
				employeeViewPanel.optionPanel.setVisible(false);
			}
		}

	}
	
	/**
	 * Process the action that will happen when you click the update button.
	 */
	private void processUpdateButton(){
		Database db=Database.getInstance();
		boolean isNoError=false;
			
		if(selectedTableRowIndex!=null && selectedTableRowIndex.length>0){
			if(selectedTableRowIndex.length>1){
				mainFrame.showOptionPaneMessageDialog(
						"Only select 1 employee if you want to edit.",
						JOptionPane.ERROR_MESSAGE);
			}
			else{
				
				try {
					
					//--> Get the table selected and the row. The getSelectedRow is used when only 1 is clicked.
					ReusableTable table= bothDeleteUpdateEmployeeDialog.employeeListTable;
					int viewRow = table.getSelectedRow(); // viewRow is the row in view only.
					int modelRow = 
		                    table.convertRowIndexToModel(viewRow); // modelRow is the REAL row selected not based in view.
					
					
					//--> 
						db.resultSet.absolute(modelRow+1);
					
					//--> Get object is 1 since we want the values of the first column which is the employeeID.
						updateEmployeeDialog.neededIDValue= db.resultSet.getObject(1).toString();
					
					ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
					conditionColumnAndValueList.add(new SelectConditionInfo(
							db.employeeTableColumnNames[0],
							updateEmployeeDialog.neededIDValue));
					
					db.selectDataInDatabase(
						new String[]{db.tableNameEmployee}, 
						null, 
						conditionColumnAndValueList, 
						null,
						null,
						Constant.SELECT_ALL_WITH_CONDITION_AND);
					
					isNoError=true;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					isNoError=false;
					
					mainFrame.showOptionPaneMessageDialog(
							""+e1.getMessage(),
							JOptionPane.ERROR_MESSAGE);
					
				}
				
				// If success Above, then show update dialog.
				if(isNoError){
					bothDeleteUpdateEmployeeDialog.dispose();
					updateEmployeeDialog.updateAllFieldsBasedFromSelectedEmployee(db);
					updateEmployeeDialog.contentPanel.setAllEditable(false);
					updateEmployeeDialog.saveButton.setVisible(false);
					updateEmployeeDialog.cancelButton.setVisible(false);
					
					updateEmployeeDialog.editButton.setVisible(true);
					updateEmployeeDialog.backButton.setVisible(true);
					
					updateEmployeeDialog.setVisible(true);
				}
			}
		}
		else{
			mainFrame.showOptionPaneMessageDialog(
					"You have not selected anything.", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Process the action that will happen when youc click the cancel button.
	 */
	private void processCancelButton(){
		Database db=Database.getInstance();
		
		System.out.println("\t Reuse the Show Employee Data Summary Code!"+CLASS_NAME);
		//--> Show the summary employee data and since this code already does it, just reuse it.
		employeeViewPanelListener.processShowEmployeeDataSummary();
		
		bothDeleteUpdateEmployeeDialog.dispose();
	}
	
	/**
	 * Process the action when you click the department combo box.
	 */
	public void processDepartmentComboBox(){
		if(bothDeleteUpdateEmployeeDialog.departmentComboBox.getSelectedItem()!=null){
			String chosenComboBoxItem=bothDeleteUpdateEmployeeDialog.departmentComboBox.getSelectedItem().toString();				
			Database db=Database.getInstance();
			Utilities util=Utilities.getInstance();
			
	//		WHERE Department='TSD'
			
			
			//---------------------------------------------------------------------------------
			//--> Process the JoinList for InnerJoin
			String[] joinColumnCompareList={
					(db.tableNameEmployee+"."+db.departmentTableColumnNames[0])+"="+(db.tableNameDepartment+"."+db.departmentTableColumnNames[0])
			};
			
			//---------------------------------------------------------------------------------
			//-->Add the needed columns and values for condition. 
			//		db.employeeTableColumnNames[12]<-> Department.
			ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"HiredAs",
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
				)
			);
			
			if(!chosenComboBoxItem.equals(Constant.STRING_ALL)){
				conditionColumnAndValueList.add(new SelectConditionInfo(
						db.departmentTableColumnNames[1],
						chosenComboBoxItem));

			}
			
			//---------------------------------------------------------------------------------
			
			//--> Get data from Database.
			db.selectDataInDatabase(new String[]{db.tableNameEmployee,db.tableNameDepartment}, 
					new String[]{db.employeeTableColumnNames[0],
						db.employeeTableColumnNames[2],
						db.employeeTableColumnNames[3],
						db.employeeTableColumnNames[4]} ,
					conditionColumnAndValueList,
					joinColumnCompareList,
					//--> Process Order
					new OrderByInfo(
						new String[]{
							db.employeeTableColumnNames[2],	// LastName
							db.employeeTableColumnNames[3] // First Name
						}, 
								
						"ASC"
					),
					Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND);
			bothDeleteUpdateEmployeeDialog.employeeListTable.updateTable(db);
			
			bothDeleteUpdateEmployeeDialog.updateRowCountLabel(bothDeleteUpdateEmployeeDialog.employeeListTable.getModel().getRowCount());
		}	
	}
	private void l_________________________________________________l(){}
	
	
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		JTable table=bothDeleteUpdateEmployeeDialog.employeeListTable;
		
		selectedTableRowIndex=table.getSelectedRows();
		selectedTableRowIndex=Utilities.getInstance().convertRowIndexToModel(
				bothDeleteUpdateEmployeeDialog.employeeListTable, selectedTableRowIndex);
		
//		
//		System.out.println("\tSelected Table Row: "+CLASS_NAME);
//		
//		for(int i=0;i<selectedTableRowIndex.length;i++){
//			System.out.println("\t\t"+selectedTableRowIndex[i]+CLASS_NAME);
//		}
	}
	
	
private void l__________________________________________l(){}
	
	public void setters(ListenerEmployeeViewPanel employeeViewPanelListener){
		this.employeeViewPanelListener =  employeeViewPanelListener;
	}
	
}

