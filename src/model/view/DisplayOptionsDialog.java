package model.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import database.Database;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

public class DisplayOptionsDialog extends JDialog {

	private JPanel contentPanel;
	
	public JComboBox<String>departmentComboBox,payrollDateComboBox,columnComboBox;
	public JLabel lblDepartment,lblPayrollDate,lblColumn;
	
	private void l_____________________l(){}
	private String chosenDepartment, chosenPayrollDate;
	
	private void l________________________l(){}
	 
	public JButton  showButton,cancelButton;
	
	private void l_____________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public DisplayOptionsDialog() {
		set();
	}
	
	private void set(){
		setThisDialogComponents();
		setContentPanelComponents();
		setButtonPanelComponents();
	}
	private void setThisDialogComponents(){
		setBounds(100, 100, 225, 157);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Display Options");
		setVisible(false);
		setLocationRelativeTo( null);
		getContentPane().setBackground(Constant.DIALOG_BOX_COLOR_BG);
		setModal(true);
		setResizable(false);
	}
	
	/**
	 * Set the content panel 
	 */
	private void setContentPanelComponents(){
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(0,2,5,5));
		contentPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		lblDepartment = new JLabel("Department: ");
		lblDepartment.setForeground(Color.WHITE);
		lblDepartment.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(lblDepartment);
		
		departmentComboBox = new JComboBox<String>();
		contentPanel.add(departmentComboBox);
		
		lblPayrollDate = new JLabel("Payroll Date: ");
		lblPayrollDate.setForeground(Color.WHITE);
		lblPayrollDate.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(lblPayrollDate);
		
		payrollDateComboBox = new JComboBox<String>();
		contentPanel.add(payrollDateComboBox);
		
		lblColumn = new JLabel("Column:");
		lblColumn.setForeground(Color.WHITE);
		lblColumn.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(lblColumn);
		lblColumn.setVisible(false);
		
		columnComboBox = new JComboBox<String>();
		contentPanel.add(columnComboBox);
		columnComboBox.setVisible(false);
	}
	
	/**
	 * Set the button panel components
	 */
	private void setButtonPanelComponents(){
		Utilities util = Utilities.getInstance();
		Images img = Images.getInstance();
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.setPreferredSize(new Dimension(0,31));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		showButton = util.initializeNewButton(-1,-1, -1, -1, img.showPDFPayrollImg, img.showPDFPayrollImgHover);
		buttonPane.add(showButton);
		
		cancelButton = util.initializeNewButton(-1, -1, -1, -1, img.cancelBtnDialogImg, img.cancelBtnDialogImgHover);
		buttonPane.add(cancelButton);
	
	}
	
	private void l________________________________________l(){}
	/**
	 * Assign values of comboboxed dpending on the given strings
	 * @param d
	 * @param p
	 */
	public void assignValuesOfComboboxed(String d, String p,String c){
		departmentComboBox.setSelectedItem(d);
		payrollDateComboBox.setSelectedItem(p);
		columnComboBox.setSelectedItem(c);
	}
	
	
	public void setButtonsEnableStatus(boolean cond){
		showButton.setEnabled(cond);
		cancelButton.setEnabled(cond);
	}
	
	/**
	 * Set the chosen combobox before edit.
	 */
	public void setTheValuesOfCombooxesBeforeEdit(){
		departmentComboBox.setSelectedItem(chosenDepartment);
		payrollDateComboBox.setSelectedItem(chosenPayrollDate);
	}
	
	/**
	 * Show the third display option which is the column .
	 */
	public void showColumnDisplayOption(){
		lblColumn.setVisible(true);
		columnComboBox.setVisible(true);
	}
	/**
	 * Store the chosen values before editing the comboboxes of the display option dialog box.
	 */
	public void storeChosenItemsFromComboxesBeforeEdit(){
		chosenDepartment=departmentComboBox.getSelectedItem().toString();
		chosenPayrollDate=(payrollDateComboBox.getSelectedObjects().length==0)?"":payrollDateComboBox.getSelectedItem().toString();
	}
	
	
	
	

}
