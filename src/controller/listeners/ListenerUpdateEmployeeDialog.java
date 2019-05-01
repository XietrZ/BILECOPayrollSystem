package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Constant;
import model.OrderByInfo;
import model.SelectConditionInfo;
import model.statics.Utilities;
import view.MainFrame;
import view.dialog.DeleteUpdateEmployeeDialog;
import view.dialog.UpdateEmployeeDialog;
import database.Database;

public class ListenerUpdateEmployeeDialog implements ActionListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private UpdateEmployeeDialog updateEmployeeDialog;
	private MainFrame mainFrame;
	private DeleteUpdateEmployeeDialog bothDeleteUpdateEmployeeDialog;
	
	private ListenerDeleteUpdateEmployeeDialog delUpdateEmployeeListener;
	private void l__________________________________l(){}
	
	public ListenerUpdateEmployeeDialog() {
		// TODO Auto-generated constructor stub
		updateEmployeeDialog= UpdateEmployeeDialog.getInstance();
		mainFrame=MainFrame.getInstance();
		bothDeleteUpdateEmployeeDialog= DeleteUpdateEmployeeDialog.getInstance();
	}

	private void l_____________________________l(){}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		UpdateEmployeeDialog ued=updateEmployeeDialog;
		Database db= Database.getInstance();
		
		//--> SAVE BUTTON
		if(e.getSource()==ued.saveButton){
			System.out.println(THIS_CLASS_NAME+"Save update"+CLASS_NAME);
			processSaveButton(db, ued);
		}
		
		//--> BACK BUTTON
		else if(e.getSource()==ued.backButton){
			System.out.println(THIS_CLASS_NAME+"Back to show all employees"+CLASS_NAME);
			processBackButton(db, ued);	
		}
		
		//--> EDIT BUTTON
		else if(e.getSource()==ued.editButton){
			System.out.println(THIS_CLASS_NAME+"Edit info"+CLASS_NAME);
			processEditButton(ued);
			
		}
		
		//--> CANCEL BUTTON
		else if(e.getSource()==ued.cancelButton){
			System.out.println(THIS_CLASS_NAME+"Cancel update"+CLASS_NAME);
			processCancelButton(ued);
		}
	}
	
	private void l__________________________________________l(){}
	/**
	 * Process the action that will happen when you click save.
	 * @param db
	 * @param ued
	 */
	private void processSaveButton(Database db,UpdateEmployeeDialog ued){
		
		if(!ued.isThereChangesInComponentsContent(db)){
			mainFrame.showOptionPaneMessageDialog(
					"There are no changes!", 
					JOptionPane.ERROR_MESSAGE);
		}
		else{
			db.updateDataInDatabase(
					db.tableNameEmployee, 
					ued.componentContentChangesList,
					db.employeeTableColumnNames[0], ued.neededIDValue,
					false,null);
			
			//--> Pop up Dialog if update is successful
			if(db.isUpdate){
				 JOptionPane.showMessageDialog(null, (db.totalUpdates==1)?"An existing data was updated successfully!":
						 "Updates have been executed successfully!", 
							null, JOptionPane.INFORMATION_MESSAGE);
			}
			
			if(db.isUpdate){
				ued.dispose();
			
				//-----------------------------------------------------------------------
				//--> Process Condition
				//--> Add condition if regular or contractual
				ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
				conditionColumnAndValueList.add(new SelectConditionInfo(
						"HiredAs",
						(Utilities.getInstance().payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
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
					Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR
				);
				
				
				bothDeleteUpdateEmployeeDialog.employeeListTable.updateTable(db);
				
				
				
				bothDeleteUpdateEmployeeDialog.setVisible(true);
			}
			
		}
	
	}
	
	/**
	 * Process the action that will happen when you click the back button
	 * @param db
	 * @param ued
	 */
	private void processBackButton(Database db,UpdateEmployeeDialog ued){
		//Reuse Code
		delUpdateEmployeeListener.processDepartmentComboBox();
		
		ued.dispose();
		bothDeleteUpdateEmployeeDialog.setVisible(true);
	}
	
	/**
	 * Process the action that will happen when you click the edit button
	 * @param ued
	 */
	private void processEditButton(UpdateEmployeeDialog ued){
		ued.contentPanel.setAllEditable(true);
		
		ued.saveButton.setVisible(true);
		ued.cancelButton.setVisible(true);
		
		ued.editButton.setVisible(false);
		ued.backButton.setVisible(false);
		
	}
	/**
	 * Process the action tht will happen when you click cancel button.
	 * @param ued
	 */
	private void processCancelButton(UpdateEmployeeDialog ued){
		Database db=Database.getInstance();
		
		ued.updateAllFieldsBasedFromSelectedEmployee(db);
		
		ued.contentPanel.setAllEditable(false);
		ued.saveButton.setVisible(false);
		ued.cancelButton.setVisible(false);
		
		ued.editButton.setVisible(true);
		ued.backButton.setVisible(true);

	}
	
	private void l________________________________________l(){}
	
	/**
	 * Set outside listeners to be used inside this class.
	 * @param mainFrameListener
	 */
	public void setOutsideListeners(ListenerDeleteUpdateEmployeeDialog delUpdateEmployeDialogListener){
		this.delUpdateEmployeeListener=delUpdateEmployeDialogListener;
	}
}

