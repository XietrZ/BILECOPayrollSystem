package model.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import main.BilecoPayrollSystemMain;
import model.DateLabelFormatter;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;

import java.awt.Font;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;

import com.toedter.calendar.JDateChooser;

import database.Database;

import javax.swing.UIManager;
import javax.xml.crypto.dsig.keyinfo.PGPData;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
/**
 * The layout used to show and edit employee basic info as shown in
 * 		add employee and update employee.
 * @author XietrZ
 *
 */
public class EmployeeBasicInfoDefaultLayout extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	private Font bigFont=new Font("Georgia", Font.BOLD, 24);
	private Font smallFont=new Font("Lucida Sans", Font.PLAIN, 14);
	
	private void l______________________________________l(){}
	public JPanel namePanel,identificationPanel,hiringDetailsPanel,jobDetailsPanel;

	public JTextField familyNameTextField, firstNameTextField,middleNameTextField,
		yearHiredtextField,
		jobTitleTextField,
		sssTextField, employeeIDTextField, pagIbigTextField, philhealthTextField,
		salaryTextField;
	public JDatePickerImpl dateHiredDatePicker,workStartsDatePicker,dateLeftDatePicker;
	
	public JComboBox<String>nameTitleComboBox, suffixComboBox,
		hiresAsComboBox, departmentComboBox,withATMCombobox;
	public JButton generateEmployeeIDButton;
	public HashMap<String,JComponent> componentsList;

	private void l________________________________________l(){}
	private int width,height;
	

	private void l______________________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public EmployeeBasicInfoDefaultLayout(int width,int height) {
		this.width=width;
		this.height=height;
		
		set();
		init();
	}
	
	private void set(){
		setBackground(Constant.BILECO_DEFAULT_COLOR_BLUE);
		setLayout(null);
		
		//--> Change this size if you want to resize the panel. Longer width than scroll pane, the scroll will appear.
		setBounds(0,0,width,height);
		
		
	}
	private void init(){
		componentsList=new HashMap<String,JComponent>();
		
		initNamePanel(); 
		initHiringDetailsPanel();
		initJobDetails();
		initIdentificationPanel();
		
		
		//--> Change this size if you want to resize the panel. Longer width than scroll pane, the scroll will appear.
		setPreferredSize(new Dimension(420,
				(namePanel.getHeight()+hiringDetailsPanel.getHeight()+jobDetailsPanel.getHeight()+identificationPanel.getHeight()))); 
		
		
	}
	

	
	
	
	
	private void l___________________________l(){}
	
	/**
	 * Initialize and set components of name panel.
	 */
	private void initNamePanel(){
		namePanel = new JPanel();
		namePanel.setForeground(Color.WHITE);
		namePanel.setLayout(null);
		namePanel.setBounds(0, 0,width, 207);
		namePanel.setOpaque(false);
		add(namePanel);
		
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(bigFont);
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(10, 11, 75, 24);
		namePanel.add(lblName);
		
		
		
		//-------------------------------------------------------
		
		nameTitleComboBox = new JComboBox<String>();
		nameTitleComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Dr", "Miss ", "Mr", "Mrs", "Ms", "Prof"}));
		nameTitleComboBox.setBounds(173, 47, 79, 20);
		namePanel.add(nameTitleComboBox);
		
		
		JLabel lblFamilyName = new JLabel("Family Name:");
		lblFamilyName.setBounds(0, 74, 170, 16);
		lblFamilyName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFamilyName.setForeground(Color.WHITE);
		lblFamilyName.setFont(smallFont);
		namePanel.add(lblFamilyName);
		
		familyNameTextField = new JTextField();
		familyNameTextField.setColumns(10);
		familyNameTextField.setBounds(173, 71, 165, 20);
		namePanel.add(familyNameTextField);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(0, 100, 170, 16);
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setFont(smallFont);
		namePanel.add(lblFirstName);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setColumns(10);
		firstNameTextField.setBounds(173, 97, 165, 20);
		namePanel.add(firstNameTextField);
		
		JLabel lblMiddleName = new JLabel("Middle Name:");
		lblMiddleName.setBounds(0, 128, 170, 16);
		lblMiddleName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMiddleName.setForeground(Color.WHITE);
		lblMiddleName.setFont(smallFont);
		namePanel.add(lblMiddleName);
		
		middleNameTextField = new JTextField();
		middleNameTextField.setColumns(10);
		middleNameTextField.setBounds(173, 125, 165, 20);
		namePanel.add(middleNameTextField);
	
	
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(0, 49, 170, 16);
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(smallFont);
		namePanel.add(lblTitle);
		
		JLabel lblSuffix = new JLabel("Suffix:");
		lblSuffix.setBounds(0, 153, 170, 16);
		lblSuffix.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSuffix.setForeground(Color.WHITE);
		lblSuffix.setFont(smallFont);
		namePanel.add(lblSuffix);
		
		suffixComboBox = new JComboBox<String>();
		suffixComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Esq", "II", "III", "IV", "Jr", "MD", "PhD", "Sr"}));
		suffixComboBox.setBounds(173, 150, 79, 20);
		namePanel.add(suffixComboBox);
		
		
		JLabel lblWithAtm = new JLabel("With ATM:");
		lblWithAtm.setBounds(0, 182, 170, 16);
		lblWithAtm.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWithAtm.setForeground(Color.WHITE);
		lblWithAtm.setFont(smallFont);
		namePanel.add(lblWithAtm);
		
		withATMCombobox = new JComboBox<String>();
		withATMCombobox.setModel(new DefaultComboBoxModel<String>(new String[] {"Yes", "No"}));
		withATMCombobox.setBounds(173, 176, 79, 20);
		withATMCombobox.setSelectedIndex(1);
		namePanel.add(withATMCombobox);
		
	}
	
	
	/**
	 * Initialize and set hiring details components.
	 */
	private void initHiringDetailsPanel(){
		hiringDetailsPanel = new JPanel();
		hiringDetailsPanel.setLayout(null);
		hiringDetailsPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		hiringDetailsPanel.setBounds(
				0,
				 namePanel.getY()+namePanel.getHeight(), 
				400,
				238);
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
		
	
	
	/**
	 * Initialize Job details components.
	 */
	private void initJobDetails(){
		jobDetailsPanel = new JPanel();
		jobDetailsPanel.setLayout(null);
		jobDetailsPanel.setOpaque(false);
		jobDetailsPanel.setBounds(0,  hiringDetailsPanel.getY()+hiringDetailsPanel.getHeight(), 
				this.getWidth(), 130);
		add(jobDetailsPanel);
		
		JLabel lblJobDetails = new JLabel("Job Details");
		lblJobDetails.setFont(bigFont);
		lblJobDetails.setForeground(Color.WHITE);
		lblJobDetails.setBounds(10, 11, 140,24);
		jobDetailsPanel.add(lblJobDetails);
		
		
		//----------------------------------------------
		JLabel lblJobTitle = new JLabel("Job Title:");
		lblJobTitle.setBounds(0, 57, 170, 16);
		lblJobTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJobTitle.setForeground(Color.WHITE);
		lblJobTitle.setFont(smallFont);
		jobDetailsPanel.add(lblJobTitle);
		
		
		jobTitleTextField = new JTextField();
		jobTitleTextField.setColumns(10);
		jobTitleTextField.setBounds(173, 57, 220, 20);
		jobDetailsPanel.add(jobTitleTextField);

		
		
		JLabel lblDepartment = new JLabel("Department: ");
		lblDepartment.setBounds(0, 82, 170, 16);
		lblDepartment.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDepartment.setForeground(Color.WHITE);
		lblDepartment.setFont(smallFont);
		jobDetailsPanel.add(lblDepartment);
		
		departmentComboBox = new JComboBox();
//		departmentComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "TSD", "OGM", "ISD", "FSD"}));
		departmentComboBox.setBounds(173, 79, 104, 20);
		jobDetailsPanel.add(departmentComboBox);
		
		
		
		//---------------------------------------------------
	}
	
	/**
	 * Initialize and set components of identification panel.
	 */
	private void initIdentificationPanel(){
		identificationPanel = new JPanel();
		identificationPanel.setLayout(null);
		identificationPanel.setOpaque(false);
		identificationPanel.setBounds(0, jobDetailsPanel.getY()+jobDetailsPanel.getHeight(), 
				this.getWidth(), 193);
		
		add(identificationPanel);
		
		JLabel lblIdentification = new JLabel("Identification Numbers");
		lblIdentification.setFont(bigFont);
		lblIdentification.setForeground(Color.WHITE);
		lblIdentification.setBounds(10, 11,291, 24);
		identificationPanel.add(lblIdentification);
		
		
		//---------------------------------------------
		JLabel lblSss = new JLabel("Social Security Number:");
		lblSss.setBounds(0, 67, 170, 16);
		lblSss.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSss.setForeground(Color.WHITE);
		lblSss.setFont(smallFont);
		identificationPanel.add(lblSss);
		
		sssTextField = new JTextField();
		sssTextField.setColumns(10);
		sssTextField.setBounds(173, 64, 143, 20);
		identificationPanel.add(sssTextField);
		
		
		
		JLabel lblPagIbig = new JLabel("Pag-ibig: ");
		lblPagIbig.setBounds(0, 95, 170, 16);
		lblPagIbig.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPagIbig.setForeground(Color.WHITE);
		lblPagIbig.setFont(smallFont);
		identificationPanel.add(lblPagIbig);
		
		pagIbigTextField = new JTextField();
		pagIbigTextField.setColumns(10);
		pagIbigTextField.setBounds(173, 92, 143, 20);
		identificationPanel.add(pagIbigTextField);
		
		JLabel lblPhilhealth = new JLabel("Philhealth: ");
		lblPhilhealth.setBounds(0, 120, 170, 16);
		lblPhilhealth.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPhilhealth.setForeground(Color.WHITE);
		lblPhilhealth.setFont(smallFont);
		identificationPanel.add(lblPhilhealth);
		
		philhealthTextField = new JTextField();
		philhealthTextField.setColumns(10);
		philhealthTextField.setBounds(173, 117, 143, 20);
		identificationPanel.add(philhealthTextField);
		
		JLabel lblEmpkoyeeID = new JLabel("Employee ID:");
		lblEmpkoyeeID.setBounds(0, 149, 170, 16);
		lblEmpkoyeeID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmpkoyeeID.setForeground(Color.WHITE);
		lblEmpkoyeeID.setFont(smallFont);
		identificationPanel.add(lblEmpkoyeeID);
		
		employeeIDTextField = new JTextField();
		employeeIDTextField.setColumns(10);
		employeeIDTextField.setBounds(173, 146, 143, 20);
		identificationPanel.add(employeeIDTextField);
//		employeeIDTextField.setEditable(false);
		
		generateEmployeeIDButton = Utilities.getInstance().initializeNewButton(
			326, 145, 82, 31, 
			Images.getInstance().generateBtnImg,
			Images.getInstance().generateBtnImgHover
		);
	
		identificationPanel.add(generateEmployeeIDButton);
	
	}
	private void l________________________________l(){}
	
	/**
	 * Clear all fields
	 */
	public void clearAllFields(){
		nameTitleComboBox.getModel().setSelectedItem(nameTitleComboBox.getModel().getElementAt(0));
		familyNameTextField.setText("");
		firstNameTextField.setText("");
		middleNameTextField.setText("");
		suffixComboBox.getModel().setSelectedItem(suffixComboBox.getModel().getElementAt(0));
		
		
		dateHiredDatePicker.getJFormattedTextField().setText("");
		workStartsDatePicker.getJFormattedTextField().setText("");
		hiresAsComboBox.getModel().setSelectedItem(hiresAsComboBox.getModel().getElementAt(0));
		salaryTextField.setText("");
		dateLeftDatePicker.getJFormattedTextField().setText("");
		
		jobTitleTextField.setText("");
		departmentComboBox.getModel().setSelectedItem(departmentComboBox.getModel().getElementAt(0));
		
		sssTextField.setText("");
		pagIbigTextField.setText("");
		philhealthTextField.setText("");
		employeeIDTextField.setText("");
	}
	/**
	 * Sample input.
	 */
	public void sampleDefaultInput(Database db){
		nameTitleComboBox.getModel().setSelectedItem(nameTitleComboBox.getModel().getElementAt(3));
		familyNameTextField.setText("Sacedor");
		firstNameTextField.setText("Paul ALvin");
		middleNameTextField.setText("Vaporoso");
		suffixComboBox.getModel().setSelectedItem(suffixComboBox.getModel().getElementAt(1));
		
		dateHiredDatePicker.getJFormattedTextField().setText("2018-01-01");
		workStartsDatePicker.getJFormattedTextField().setText("2018-01-01");
		yearHiredtextField.setText("2018");
//		hiresAsComboBox.getModel().setSelectedItem(hiresAsComboBox.getModel().getElementAt(1));
		salaryTextField.setText("0");
		dateLeftDatePicker.getJFormattedTextField().setText("");
		
		jobTitleTextField.setText("IT Staff");
		departmentComboBox.getModel().setSelectedItem(db.departmentDataList.get(1));
		
		sssTextField.setText("sss-1");
		pagIbigTextField.setText("pagibig-1");
		philhealthTextField.setText("philhealth-1");
		employeeIDTextField.setText("2018-PVS-0001");
		
		
	}
	
	
	/**
	 * Set all components not editable or editable depending on the boolean value.
	 */
	public void setAllEditable(boolean cond){
		nameTitleComboBox.setEnabled(cond);
		familyNameTextField.setEditable(cond);
		firstNameTextField.setEditable(cond);
		middleNameTextField.setEditable(cond);
		suffixComboBox.setEnabled(cond);
		withATMCombobox.setEnabled(cond);
		
		//----------------------------------------
		
//		dateHiredDatePicker.getJFormattedTextField().setEditable(cond);
		dateHiredDatePicker.getComponents()[1].setEnabled(cond);
//		workStartsDatePicker.getJFormattedTextField().setEditable(cond);
		workStartsDatePicker.getComponents()[1].setEnabled(cond);
		hiresAsComboBox.setEnabled(cond);
		yearHiredtextField.setEditable(cond);
		salaryTextField.setEditable(cond);
		dateLeftDatePicker.getComponents()[1].setEnabled(cond);
		
		//----------------------------------------
		
		jobTitleTextField.setEditable(cond);
		departmentComboBox.setEnabled(cond);
		
		//----------------------------------------
		sssTextField.setEditable(cond);
		pagIbigTextField.setEditable(cond);
		philhealthTextField.setEditable(cond);
		
		employeeIDTextField.setEditable(cond);
	}
	
	private boolean isSToredDone=false;
	/**
	 * Store all UI to a hashmap just ONCE!.
	 * @param db
	 */
	public void storeAllUIComponentsToAHashMap(Database db){
		if(!isSToredDone){
			isSToredDone=true;
			
			componentsList.put(db.employeeTableColumnNames[0],employeeIDTextField);
			componentsList.put(db.employeeTableColumnNames[1],nameTitleComboBox);
			componentsList.put(db.employeeTableColumnNames[2],familyNameTextField);
			componentsList.put(db.employeeTableColumnNames[3],firstNameTextField);
			componentsList.put(db.employeeTableColumnNames[4],middleNameTextField);
			componentsList.put(db.employeeTableColumnNames[5],suffixComboBox);
			componentsList.put(db.employeeTableColumnNames[6],withATMCombobox);
			componentsList.put(db.employeeTableColumnNames[7],dateHiredDatePicker);
			componentsList.put(db.employeeTableColumnNames[8],workStartsDatePicker);
			componentsList.put(db.employeeTableColumnNames[9],yearHiredtextField);
			componentsList.put(db.employeeTableColumnNames[10],hiresAsComboBox);
			componentsList.put(db.employeeTableColumnNames[11],salaryTextField);
			componentsList.put(db.employeeTableColumnNames[12],dateLeftDatePicker);
			componentsList.put(db.employeeTableColumnNames[13],jobTitleTextField);
			componentsList.put(db.employeeTableColumnNames[14],departmentComboBox);
			componentsList.put(db.employeeTableColumnNames[15],sssTextField);
			componentsList.put(db.employeeTableColumnNames[16],pagIbigTextField);
			componentsList.put(db.employeeTableColumnNames[17],philhealthTextField);
	
		}
	}
	private void l_____________________________l(){}
}
