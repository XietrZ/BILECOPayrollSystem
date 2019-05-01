package model.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import database.Database;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

public class AddCommentsOnAllPayslipDialog extends JDialog {

	private JPanel contentPanel;
	private JLabel lblPayrollDate, commentLbl;
	
	public JComboBox<String>payrollDateComboBox;
	public JTextArea commentTextArea;
	
	private void l_____________________l(){}
	private String chosenDepartment, chosenPayrollDate;
	
	private void l________________________l(){}
	 
	public JButton  saveButton;
	
	private void l_____________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public AddCommentsOnAllPayslipDialog() {
		set();
	}
	
	private void set(){
		setThisDialogComponents();
		setContentPanelComponents();
		setButtonPanelComponents();
	}
	private void setThisDialogComponents(){
		setBounds(100, 100, 225, 177);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Add Comments on All Payslip");
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
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		//--------------------------
		JPanel payrollDatePanel= new JPanel();
		payrollDatePanel.setLayout(new GridLayout(0,2,5,5));
		payrollDatePanel.setPreferredSize(new Dimension(0,20));
		payrollDatePanel.setOpaque(false);
		
		lblPayrollDate = new JLabel("Payroll Date: ");
		lblPayrollDate.setForeground(Color.WHITE);
		lblPayrollDate.setHorizontalAlignment(SwingConstants.RIGHT);
		payrollDatePanel.add(lblPayrollDate);
		
		
		payrollDateComboBox = new JComboBox<String>();
		payrollDatePanel.add(payrollDateComboBox);
	
		
		contentPanel.add(payrollDatePanel,BorderLayout.NORTH);
		//--------------------------
		
	
		JPanel textAreaPanel= new JPanel();
		textAreaPanel.setPreferredSize(new Dimension(0, 95));
		textAreaPanel.setLayout(null);
		textAreaPanel.setOpaque(false);
		
		
		contentPanel.add(textAreaPanel,BorderLayout.CENTER);
		
		JLabel lblComments = new JLabel("Comments:");
		lblComments.setForeground(Color.WHITE);
		lblComments.setBounds(10, 11, 75, 14);
		textAreaPanel.add(lblComments);
		
		commentTextArea = new JTextArea();
		commentTextArea.setBounds(87, 14, 100, 75);
		textAreaPanel.add(commentTextArea);
	}
	
	/**
	 * Set the button panel components
	 */
	private void setButtonPanelComponents(){
		JPanel buttonPane = new JPanel();
		buttonPane.setPreferredSize(new Dimension(-1,32));
		buttonPane.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		saveButton = Utilities.getInstance().initializeNewButton(
			-1, -1, -1, -1,
			Images.getInstance().saveBtnDialogImg,
			Images.getInstance().saveBtnDialogImgHover
		);
		buttonPane.add(saveButton);
	

	
	}
	
	
}
