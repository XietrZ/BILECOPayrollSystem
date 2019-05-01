package model.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import model.DateLabelFormatter;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;

import java.awt.Font;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;

import com.toedter.calendar.JDateChooser;

import javax.swing.UIManager;
import javax.xml.crypto.dsig.keyinfo.PGPData;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class AddEarningOrDeductionDataLayout extends JDialog {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private JScrollPane scrollPane;
	
	private void l____________________________________________l(){}
	
	public JButton saveButton,cancelButton;
	private void l______________________________________l(){}
	public JPanel contentPanel;
	public JTextField employeeIDTextField, employeeNameTextField;
	public JComboBox<String>payrollDateCombobox;
	

	

	private void l______________________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public AddEarningOrDeductionDataLayout() {
		setForeground(new Color(0, 0, 0));
		getContentPane().setForeground(Color.WHITE);
			
		set();
		init();
		
	}
	
	private void set(){
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 567, 243);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
//		setUndecorated(true);
		setLocationRelativeTo(null);
		setTitle("?");
		setResizable(false);

	
	}
	private void init(){
		getContentPane().setLayout(new BorderLayout());
		
		
		initContentPanel();
		initButtonPanel();	
	}
	
	private void l________________________________________________l(){}
	
	/** 
	 * Initialize components of button panel
	 */
	private void initButtonPanel(){
		Utilities util = Utilities.getInstance();
		Images img = Images.getInstance();
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		
		saveButton = util.initializeNewButton(-1, -1, -1, -1, img.saveBtnDialogImg, img.saveBtnDialogImgHover);
		buttonPane.add(saveButton);
		
		cancelButton = util.initializeNewButton(-1, -1, -1, -1, img.cancelBtnDialogImg, img.cancelBtnDialogImgHover);
		buttonPane.add(cancelButton);
	

		
	}
	
	
	/**
	 * Initialize components of content panel.
	 */
	private void initContentPanel(){
		
		//--> Content Panel
				contentPanel = new JPanel();
				contentPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
//				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				contentPanel.setLayout(null);
				//--> Change this size if you want to resize the panel. Longer width than scroll pane, the scroll will appear.
				contentPanel.setBounds(0,0,this.getWidth(),this.getHeight());
				
		
		
		//--> ScrollPane Code
		
				scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setViewportView(contentPanel);
				getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		//--> Employee ID
				JLabel EmplpyeeIDLabel = new JLabel("Employee ID:");
				EmplpyeeIDLabel.setForeground(Color.WHITE);
				EmplpyeeIDLabel.setBounds(10, 11, 85, 25);
				contentPanel.add(EmplpyeeIDLabel);
				
				employeeIDTextField = new JTextField();
				employeeIDTextField.setEditable(false);
				employeeIDTextField.setBounds(102, 13, 118, 20);
				contentPanel.add(employeeIDTextField);
				employeeIDTextField.setColumns(10);
				
		//--> Employee Name
				JLabel employeeNameLabel = new JLabel("Employee Name:");
				employeeNameLabel.setBounds(276, 11, 99, 25);
				employeeNameLabel.setForeground(Color.WHITE);
				contentPanel.add(employeeNameLabel);
				
				employeeNameTextField = new JTextField();
				employeeNameTextField.setEditable(false);
				employeeNameTextField.setColumns(10);
				employeeNameTextField.setBounds(385, 13, 164, 20);
				contentPanel.add(employeeNameTextField);

		
		//--> Payroll Date
				JLabel payrollDateLabel = new JLabel("Payroll Date:");
				payrollDateLabel.setForeground(Color.WHITE);
				payrollDateLabel.setBounds(102, 79, 85, 25);
				contentPanel.add(payrollDateLabel);
				
				
				payrollDateCombobox = new JComboBox<String>();
				payrollDateCombobox.setBounds(193, 81, 99, 20);
				contentPanel.add(payrollDateCombobox);
				
				


	
//		//--> Change this size if you want to resize the panel. Longer width than scroll pane, the scroll will appear.
//		contentPanel.setPreferredSize(new Dimension(this.getWidth()-30,
//				(contentPanel.namePanel.getHeight()
//						+contentPanel.hiringDetailsPanel.getHeight()
//						+contentPanel.jobDetailsPanel.getHeight()
//						+contentPanel.identificationPanel.getHeight()))); 
//		
	}
}
