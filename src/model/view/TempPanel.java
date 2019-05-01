package model.view;

import java.awt.Color;
import java.awt.Font;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Constant;
import model.DateLabelFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class TempPanel extends JPanel {
	private Font bigFont=new Font("Georgia", Font.BOLD, 24);
	private Font smallFont=new Font("Lucida Sans", Font.PLAIN, 14);
	
	public JPanel namePanel,identificationPanel,hiringDetailsPanel,jobDetailsPanel;

	public JTextField familyNameTextField, firstNameTextField,middleNameTextField,
		yearHiredtextField,
		jobTitleTextField,
		sssTextField, employeeIDTextField, pagIbigTextField, philhealthTextField,
		salaryTextField;
	
	public JDatePickerImpl dateHiredDatePicker,workStartsDatePicker, dateLeftDatePicker;
	
	public JComboBox<String>nameTitleComboBox, suffixComboBox,
		hiresAsComboBox, departmentComboBox;
	public JButton generateEmployeeIDButton;
	
	/**
	 * Create the panel.
	 */
	public TempPanel() {
		setLayout(null);
		setBounds(0,
				0, 
				400, 238);
		setBackground(Color.GREEN);
		
		initHiringDetailsPanel();
	}

	
	
	/**
	 * Initialize and set hiring details components.
	 */
	private void initHiringDetailsPanel(){
		hiringDetailsPanel = new JPanel();
		hiringDetailsPanel.setLayout(null);
		hiringDetailsPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		hiringDetailsPanel.setBounds(0, 0, 
				400, 238);
//		hiringDetailsPanel.setBounds(0, 207, 
//				410, 193);
		
		add(hiringDetailsPanel);
		
		JLabel lblHiringDetails = new JLabel("Hiring Details");
		lblHiringDetails.setFont(bigFont);
		lblHiringDetails.setForeground(Color.WHITE);
		lblHiringDetails.setBounds(10, 11, 177,24);
		hiringDetailsPanel.add(lblHiringDetails);
		
		
		
		//---------------------------------------------------
		
		JLabel lblDateHired = new JLabel("  Date Hired: ");
		lblDateHired.setBounds(0, 55, 170, 16);
		lblDateHired.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateHired.setForeground(Color.WHITE);
		lblDateHired.setFont(smallFont);
		hiringDetailsPanel.add(lblDateHired);
		

		
		UtilDateModel model = new UtilDateModel();
//		model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		dateHiredDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		dateHiredDatePicker.setBounds(173, 52, 107, 29);
		hiringDetailsPanel.add(dateHiredDatePicker);
		dateHiredDatePicker.getJFormattedTextField().setEditable(false);

		//---------------------------------------------------
		
		JLabel lblWorkStarts = new JLabel("Work Starts: ");
		lblWorkStarts.setBounds(0, 89, 170, 16);
		lblWorkStarts.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWorkStarts.setForeground(Color.WHITE);
		lblWorkStarts.setFont(smallFont);
		hiringDetailsPanel.add(lblWorkStarts);
		
		
		
		UtilDateModel model_2 = new UtilDateModel();
//		model.setDate(20,04,2014);
		// Need this...
		Properties p_2 = new Properties();
		p_2.put("text.today", "Today");
		p_2.put("text.month", "Month");
		p_2.put("text.year", "Year");
		JDatePanelImpl datePanel_2 = new JDatePanelImpl(model_2, p_2);
		// Don't know about the formatter, but there it is...
		workStartsDatePicker = new JDatePickerImpl(datePanel_2, new DateLabelFormatter());
		workStartsDatePicker.setBounds(173, 86, 107, 29);
		hiringDetailsPanel.add(workStartsDatePicker);
		workStartsDatePicker.getJFormattedTextField().setEditable(false);
		
		//---------------------------------------------------
		JLabel yearHiredLabel = new JLabel("Year Hired: ");
		yearHiredLabel.setBounds(0, 121, 170, 16);
		yearHiredLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		yearHiredLabel.setForeground(Color.WHITE);
		yearHiredLabel.setFont(smallFont);
		hiringDetailsPanel.add(yearHiredLabel);
		
		yearHiredtextField = new JTextField();
		yearHiredtextField.setEditable(true);
		yearHiredtextField.setColumns(10);
		yearHiredtextField.setBounds(173, 121, 107, 20);
		hiringDetailsPanel.add(yearHiredtextField);
		
		
		//---------------------------------------------------
		JLabel lblHiredAs = new JLabel("  Hired As:");
		lblHiredAs.setBounds(0, 146, 170, 16);
		lblHiredAs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHiredAs.setForeground(Color.WHITE);
		lblHiredAs.setFont(smallFont);
		hiringDetailsPanel.add(lblHiredAs);
		
		hiresAsComboBox = new JComboBox<String>();
		hiresAsComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Regular", "Contractual"}));
		hiresAsComboBox.setBounds(173, 147, 107, 20);
		hiresAsComboBox.setEnabled(false);
		hiringDetailsPanel.add(hiresAsComboBox);
		
		
		//---------------------------------------------------
		JLabel lblSalary = new JLabel("  Salary: ");
		lblSalary.setBounds(0, 173, 170, 16);
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalary.setForeground(Color.WHITE);
		lblSalary.setFont(smallFont);
		hiringDetailsPanel.add(lblSalary);
		
		salaryTextField = new JTextField();
		salaryTextField.setBounds(173, 173, 107, 20);
		hiringDetailsPanel.add(salaryTextField);
		salaryTextField.setColumns(10);
		
		//---------------------------------------------------
		JLabel labelDateLeft = new JLabel("Date Left: ");
		labelDateLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDateLeft.setForeground(Color.WHITE);
		labelDateLeft.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		labelDateLeft.setBounds(0, 201, 170, 16);
		hiringDetailsPanel.add(labelDateLeft);
		
		
		UtilDateModel model_3 = new UtilDateModel();
//		model.setDate(20,04,2014);
		// Need this...
		Properties p_3 = new Properties();
		p_3.put("text.today", "Today");
		p_3.put("text.month", "Month");
		p_3.put("text.year", "Year");
		JDatePanelImpl datePanel_3 = new JDatePanelImpl(model_3, p_3);
		// Don't know about the formatter, but there it is...
		dateLeftDatePicker = new JDatePickerImpl(datePanel_3, new DateLabelFormatter());
		dateLeftDatePicker.setBounds(173, 198, 107, 29);
		hiringDetailsPanel.add(dateLeftDatePicker);
		dateLeftDatePicker.getJFormattedTextField().setEditable(false);
	
	}
}
