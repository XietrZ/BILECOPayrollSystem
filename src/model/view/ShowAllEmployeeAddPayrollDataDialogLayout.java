package model.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import model.statics.Images;
import view.dialog.DeleteUpdateEmployeeDialog;




public class ShowAllEmployeeAddPayrollDataDialogLayout extends DeleteUpdateEmployeeDialog{
	public JButton addButton=updateButton;
	
	public ShowAllEmployeeAddPayrollDataDialogLayout(){
		set();
	}
	
	public void set(){
		setTitle("Add Payroll Data");
		
		addButton.setIcon(Images.getInstance().addBtnDialogImg);
		addButton.setRolloverIcon(Images.getInstance().addBtnDialogImgHover);
		deleteButton.setVisible(false);
		cancelButton.setVisible(false);
		employeeListTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
	}
}
