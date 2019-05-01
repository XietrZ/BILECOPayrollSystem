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


import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;



import org.jdatepicker.impl.UtilDateModel;

import java.awt.GridLayout;

import javax.swing.SwingConstants;

public class AddPayrollDateDialogLayout extends JDialog {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	

	private void l____________________________________________l(){}
	
	public JButton saveButton,cancelButton;
	private void l______________________________________l(){}
	public JPanel contentPanel;
	public JDatePickerImpl payrollDatePicker,payrollDateStartPeriodPicker;
	
	

	

	private void l______________________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public AddPayrollDateDialogLayout() {
		setForeground(new Color(0, 0, 0));
		getContentPane().setForeground(Color.WHITE);
			
		set();
		init();
		
	}
	
	private void set(){
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 329, 236);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
//		setUndecorated(true);
		setLocationRelativeTo(null);
		setTitle("Add Payroll Date");
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
	
	}
	private void init(){
		
		
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
		
		//--------------------------------------------------------------

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
//					contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPanel.setLayout(null);
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			
			
			UtilDateModel model = new UtilDateModel();
//			model.setDate(20,04,2014);
			// Need this...
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			
			
			UtilDateModel model_2 = new UtilDateModel();
//			model.setDate(20,04,2014);
			// Need this...
			Properties p_2 = new Properties();
			p_2.put("text.today", "Today");
			p_2.put("text.month", "Month");
			p_2.put("text.year", "Year");
			JDatePanelImpl datePanel_2 = new JDatePanelImpl(model_2, p_2);
			
			//--------------------------------------------------------------

			JPanel panel = new JPanel();
			panel.setBounds(10, 49, 303, 57);
			panel.setOpaque(false);
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0,2,5,5));
			
			//--> Payroll Date
				JLabel payrollDateLabel = new JLabel("Payroll Date:");
				payrollDateLabel.setForeground(Color.WHITE);
				payrollDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				panel.add(payrollDateLabel);
				// Don't know about the formatter, but there it is...
				payrollDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
				panel.add(payrollDatePicker);
				payrollDatePicker.getJFormattedTextField().setEditable(false);		
							
			//--> Payroll Date Start Period
				JLabel payrollDateStartPeriodLabel = new JLabel("Payroll Date Start Period:");
				payrollDateStartPeriodLabel.setForeground(Color.WHITE);
				payrollDateStartPeriodLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				panel.add(payrollDateStartPeriodLabel);
				// Don't know about the formatter, but there it is...
				payrollDateStartPeriodPicker = new JDatePickerImpl(datePanel_2, new DateLabelFormatter());
				panel.add(payrollDateStartPeriodPicker);
				payrollDateStartPeriodPicker.getJFormattedTextField().setEditable(false);



		//--------------------------------------------------------------

//		//--> Change this size if you want to resize the panel. Longer width than scroll pane, the scroll will appear.
//		contentPanel.setPreferredSize(new Dimension(this.getWidth()-30,
//				(contentPanel.namePanel.getHeight()
//						+contentPanel.hiringDetailsPanel.getHeight()
//						+contentPanel.jobDetailsPanel.getHeight()
//						+contentPanel.identificationPanel.getHeight()))); 
//		
	}
}
