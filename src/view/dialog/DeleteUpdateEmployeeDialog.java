package view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;





import javax.swing.table.TableRowSorter;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;

import view.MainFrame;
import model.Constant;
import model.PayrollTableModel;
import model.statics.Images;
import model.statics.Utilities;
import model.view.ReusableTable;
import model.view.YesOrNoIfDeleteDialogLayout;
import database.Database;


public class DeleteUpdateEmployeeDialog extends JDialog {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	private static DeleteUpdateEmployeeDialog instance;
	private Utilities util;
	private Images img;
	private Color colorBG;
	private Font smallFont;


	private void l____________________________________________l(){}
	public JLabel rowCountLabel;
	public JButton updateButton,deleteButton,cancelButton;
	
	private void l______________________________________l(){}
	
	
	public JPanel filterPanel;
	public ReusableTable employeeListTable;
	public JComboBox<String> departmentComboBox;
	
	private void l_______________________________________l(){}
	
	public YesOrNoIfDeleteDialogLayout deleteWarningDialog;
	
	/**
	 * Create the dialog.
	 */
	public DeleteUpdateEmployeeDialog() {
		set();
		init();
		
	}
	
	private void init(){
		getContentPane().setLayout(new BorderLayout());
		
		deleteWarningDialog=new YesOrNoIfDeleteDialogLayout();
		deleteWarningDialog.setSize(deleteWarningDialog.getWidth(), deleteWarningDialog.getHeight()+50);
		img=Images.getInstance();
		util= Utilities.getInstance();
		colorBG = new Color(48,120,161);
		smallFont=new Font("Lucida Sans", Font.PLAIN, 14);
		
		
		initContentPanel();
		initButtonPanel();
	}
	
	private void set(){
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 550, 527);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
//		setUndecorated(true);
		setLocationRelativeTo(null);
		setTitle("Update/Delete Employee");
		setResizable(false);
		setVisible(false);

	}
	
	private void l_______________________________________________l(){}
	
	
	private void initButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(colorBG);
		buttonPanel.setLayout(new GridLayout(0,2));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		//-----------------------------------------------------------
		JPanel bottomLeftPanel=new JPanel();
		bottomLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		bottomLeftPanel.setBackground(colorBG);
		buttonPanel.add(bottomLeftPanel);
		
		rowCountLabel=new JLabel("Row Count: -1");
		rowCountLabel.setForeground(Color.WHITE);
		bottomLeftPanel.add(rowCountLabel);
				
				
		//-------------------------------------------------------
		JPanel bottomRightPanel=new JPanel();
		bottomRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottomRightPanel.setBackground(colorBG);
		buttonPanel.add(bottomRightPanel);
		
		
		updateButton = util.initializeNewButton(-1, -1, -1, -1, img.updateBtnDialogImg, img.updateBtnDialogImgHover);
		bottomRightPanel.add(updateButton);
		
		
		deleteButton  = util.initializeNewButton(-1, -1, -1, -1, img.deleteBtnDialogImg, img.deleteBtnDialogImgHover);
		bottomRightPanel.add(deleteButton);
		
		cancelButton  = util.initializeNewButton(-1, -1, -1, -1, img.cancelBtnDialogImg, img.cancelBtnDialogImgHover);
		bottomRightPanel.add(cancelButton);
		
		
		
	}
	
	
	
	private void initContentPanel(){
		
		filterPanel = new JPanel();
		filterPanel.setBackground(colorBG);
		getContentPane().add(filterPanel, BorderLayout.NORTH);
		filterPanel.setPreferredSize(new Dimension(this.getWidth(),50));
		filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(smallFont);
		lblDepartment.setForeground(Color.WHITE);
		filterPanel.add(lblDepartment);
		
		departmentComboBox = new JComboBox<String>();
//		departmentComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"ALL", "TSD", "OGM", "ISD", "FSD"}));
		filterPanel.add(departmentComboBox);
		
		
		//------------------------------------------------
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		PayrollTableModel tableModel=new PayrollTableModel();
		TableRowSorter<PayrollTableModel> sorter = new TableRowSorter<PayrollTableModel>(tableModel);
		employeeListTable = new ReusableTable(tableModel, sorter,true);
		scrollPane.setViewportView(employeeListTable);
		
		
	}
	private void l______________________l(){}
	
	public void updateRowCountLabel(int rowCount){
		rowCountLabel.setText("Row Count: "+rowCount);
	}
	
	
	public static DeleteUpdateEmployeeDialog getInstance(){
		if(instance==null)
			instance=new DeleteUpdateEmployeeDialog();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
