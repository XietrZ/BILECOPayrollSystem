package model.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import model.Constant;
import model.OrderByInfo;
import model.PayslipComponentInfo;
import model.PayslipDataStorageInfo;
import model.statics.Utilities;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import view.MainFrame;
import view.views.HomeViewPanel;
import database.Database;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class PayrollSlipLayout extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private GridLayout deductionDataGridLayout, earningDataGridLayout;
	private JPanel  topPanel,
					deductionDataPanel, earningDataPanel,
					bottomPanel,centerPanel,leftPanel,rightPanel;
	private JTextField nameTextField, periodTextField,departmentTextField,
						grossPayTextField, lessDeductionsTextfield, netPayTextfield;
	private JLabel nameLabel,
		deductionTitleLabel, earningsTitleLabel,
		grossPayLabel, lessDeductionsLabel, netpayLabel;
	private JTextArea commentTextArea, preparedByNameTextArea;
	private boolean isSetPayslipSwingList=true;
	
	

	private Color highLightFieldColor =new Color(255, 255, 153);
	private int borderSpaceVal=50;
	
	private PayslipDataStorageInfo payslipDataValueStorage=null;
	private int fontSize=20;
	private Font textFieldFont = new Font("TimesRoman", Font.PLAIN, fontSize);
	private Font textFieldBoldFont = new Font("TimesRoman", Font.BOLD, fontSize);
	private Font labelFont=new Font("Tahoma", Font.BOLD, 14);
//	private Font smallerLabelFont=new Font("Tahoma", Font.PLAIN, 12);

	private void l__________________________________________________l(){}
	
	public JPanel panelBeforeThisPanel;
	public HashMap<String, PayslipComponentInfo> deductionPayslipSwingList, earningPayslipSwingList;
	public JTextField  idTextField;
	public boolean isCalculationPossible=true;
	public JLabel signatureLabel;
	private void l_____________________________________________l(){}
	
	
	/**
	 * Create the panel.
	 */
	public PayrollSlipLayout() {
		init();
		set();
	}
	
	private void init(){
		deductionDataGridLayout = new GridLayout(0,2);
		deductionDataGridLayout.setVgap(2);
		
		earningDataGridLayout = new GridLayout(0,2);
		earningDataGridLayout.setVgap(2);
		
		deductionPayslipSwingList = new HashMap<String,PayslipComponentInfo>();
		earningPayslipSwingList= new HashMap<String,PayslipComponentInfo>();
	}
	
	private void set(){
		setThisPanelComponents();
		setBeforeThisPanelComponents();
		
		setTopPanelComponents();
		setBottomPanelComponents();
		setCenterPanelComponents();
	}
	private void l_________________________________l(){}
	/**
	 * Set this panel components
	 */
	private void setThisPanelComponents(){
		setBounds(
			0,
			20,
			450+(borderSpaceVal*2-20),
			/*515*/600+(borderSpaceVal)+(borderSpaceVal*2)
		);
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.WHITE);
		
		
	}
	
	/**
	 * This panel adds all the components, as you can see there is a border,
	 * 	The bigger square is this panel, while the smaller and inner square is the panelBeforeThisPanel.
	 */
	private void setBeforeThisPanelComponents(){
		panelBeforeThisPanel = new JPanel();
		
		panelBeforeThisPanel.setBounds(
				0/*+(borderSpaceVal)*/,
				0+(borderSpaceVal), 
				this.getWidth(),
				/*515*/this.getHeight()-(borderSpaceVal)
		);
		panelBeforeThisPanel.setLayout(new BorderLayout());
		panelBeforeThisPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(panelBeforeThisPanel);
	}
	
	
	/**
	 * Set top panel components where name, id and period is located.
	 */
	private void setTopPanelComponents(){

		topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		panelBeforeThisPanel.add(topPanel, BorderLayout.NORTH);
		topPanel.setPreferredSize(new Dimension(0, 70));
		topPanel.setLayout(null);
		
		JLabel paySlipLabel = new JLabel("BILECO PAYSLIP ");
		paySlipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		paySlipLabel.setBounds(211, 4, 130, 14);
		topPanel.add(paySlipLabel);
		{
			nameLabel = new JLabel("Name: ");
			nameLabel.setBounds(10, 24, 46, 14);
			topPanel.add(nameLabel);
		}
		
		nameTextField = new JTextField();
		nameTextField.setText("Paul Alvin V. Sacedor\r\n");
		nameTextField.setEditable(false);
		nameTextField.setBounds(52, 21, 149, 20);
//		nameTextField.setFont(font);
		topPanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel periodLabel = new JLabel("Period:");
		periodLabel.setBounds(340, 21, 74, 14);
		topPanel.add(periodLabel);
		
		periodTextField = new JTextField();
		periodTextField.setText("March 1-15, 2017");
		periodTextField.setEditable(false);
		periodTextField.setColumns(10);
		periodTextField.setBounds(415, 18, 123, 20);
//		periodTextField.setFont(font);
		topPanel.add(periodTextField);
		
		JLabel idLabel = new JLabel("ID: ");
		idLabel.setBounds(10, 52, 46, 14);
		topPanel.add(idLabel);
		
		idTextField = new JTextField();
		idTextField.setText("2018-PVS-0001");
		idTextField.setEditable(false);
		idTextField.setColumns(10);
		idTextField.setBounds(52, 49, 149, 20);
//		idTextField.setFont(font);
		topPanel.add(idTextField);
		
		JLabel departmentLabel = new JLabel("Department:");
		departmentLabel.setBounds(340, 49, 74, 14);
		topPanel.add(departmentLabel);
		
		departmentTextField = new JTextField();
		departmentTextField.setText("OGMMMM");
		departmentTextField.setEditable(false);
		departmentTextField.setColumns(10);
		departmentTextField.setBounds(415, 46, 123, 20);
//		departmentTextField.setFont(font);
		topPanel.add(departmentTextField);
		
		
		
	}
	
	/**
	 * Set Bottom Panel components where Gross pay, less deductions and net pay is located. 
	 */
	private void setBottomPanelComponents(){
		
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setPreferredSize(new Dimension(0, 90)); // When using borderlayout, you can input 0 as width since it will just adjust.
		panelBeforeThisPanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(null);
		
		grossPayLabel = new JLabel("Gross Pay: ");
		grossPayLabel.setFont(labelFont);
		grossPayLabel.setBounds(156, 9, 86, 22);
		bottomPanel.add(grossPayLabel);
		
		grossPayTextField = new JTextField();
		grossPayTextField.setBounds(280, 6, 113, 25);
		grossPayTextField.setEditable(false);
		grossPayTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		grossPayTextField.setFont(textFieldFont);
		bottomPanel.add(grossPayTextField);
		grossPayTextField.setColumns(10);
		
		lessDeductionsTextfield = new JTextField();
		lessDeductionsTextfield.setEditable(false);
		lessDeductionsTextfield.setHorizontalAlignment(SwingConstants.RIGHT);
		lessDeductionsTextfield.setColumns(10);
		lessDeductionsTextfield.setBounds(280, 33, 113, 25);
		lessDeductionsTextfield.setFont(textFieldFont);
		bottomPanel.add(lessDeductionsTextfield);
		
		lessDeductionsLabel = new JLabel("Less Deductions: ");
		lessDeductionsLabel.setFont(labelFont);
		lessDeductionsLabel.setBounds(156, 36, 139, 22);
		bottomPanel.add(lessDeductionsLabel);
		
		netpayLabel = new JLabel("Net Pay: ");
		netpayLabel.setFont(labelFont);
		netpayLabel.setBounds(156, 65, 95, 22);
		bottomPanel.add(netpayLabel);
		
		netPayTextfield = new JTextField();
		netPayTextfield.setBackground(highLightFieldColor);
		netPayTextfield.setEditable(false);
		netPayTextfield.setHorizontalAlignment(SwingConstants.RIGHT);
		netPayTextfield.setColumns(10);
		netPayTextfield.setBounds(280, 62, 113, 25);
		netPayTextfield.setFont(textFieldBoldFont);
		bottomPanel.add(netPayTextfield);
		
		commentTextArea = new JTextArea();
		commentTextArea.setBackground(new Color(255, 255, 255));
		commentTextArea.setFont(new Font("Calibri", Font.PLAIN, 12));
		commentTextArea.setBounds(418, 13, 100, 75);
		bottomPanel.add(commentTextArea);
		
		JLabel lblComment = new JLabel("Note:");
		lblComment.setBounds(403, 0, 73, 14);
		bottomPanel.add(lblComment);
		
		JLabel lblPreparedBy = new JLabel("Prepared by:");
		lblPreparedBy.setBounds(5, 0, 73, 14);
		bottomPanel.add(lblPreparedBy);
		
		signatureLabel = new JLabel("");
		signatureLabel.setBounds(5, 16, 141, Constant.SIGNATURE_IMAGE_HEIGHT);
		signatureLabel.setOpaque(false);
//		signatureLabel.setOpaque(arg0);
		//		signatureLabel.setVisible(false);
				bottomPanel.add(signatureLabel);
		
		preparedByNameTextArea = new JTextArea();
		preparedByNameTextArea.setEditable(false);
		preparedByNameTextArea.setFont(new Font("Calibri", Font.PLAIN, 11));
		preparedByNameTextArea.setBounds(5, 60, 141, 14);
		bottomPanel.add(preparedByNameTextArea);
	}
	/**
	 * Set the compoenents where deductions and earnings was located.
	 */
	private void setCenterPanelComponents(){
		centerPanel = new JPanel();
		panelBeforeThisPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(0, 2));
		
		
	
		setLeftEarningDataPanel();
		setRightDeductionDataPanel();
	}
	
	/**
	 * Set components where earnings data are located
	 */
	private void setLeftEarningDataPanel(){

		leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
		centerPanel.add(leftPanel);
		leftPanel.setLayout(new BorderLayout());
		
		earningsTitleLabel = new JLabel("EARNINGS");
		earningsTitleLabel.setBackground(Color.WHITE);
		earningsTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		earningsTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		leftPanel.add(earningsTitleLabel, BorderLayout.NORTH);
//		setVisible(true);
		
	//----------------------------------------------
		
		earningDataPanel = new JPanel();
		earningDataPanel.setBackground(Color.WHITE);
		leftPanel.add(earningDataPanel);
		earningDataPanel.setLayout(earningDataGridLayout);
	}
	
	/**
	 * Set components where deduction data are located.
	 */
	private void setRightDeductionDataPanel(){
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		centerPanel.add(rightPanel);
		rightPanel.setLayout(new BorderLayout());
		
		deductionTitleLabel = new JLabel("DEDUCTIONS");
		deductionTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		deductionTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		rightPanel.add(deductionTitleLabel, BorderLayout.NORTH);
		
		//----------------------------------------------
		
		deductionDataPanel = new JPanel();
		deductionDataPanel.setBackground(Color.WHITE);
		rightPanel.add(deductionDataPanel, BorderLayout.CENTER);
		deductionDataPanel.setLayout(deductionDataGridLayout);
		
		
	}
	
	private void l____________________________________l(){}
	
	/**
	 * Needed to update to remove in payslip unecessary deduction components.
	 */
	private void addNecessaryComponentDeductionPanel(
			PayslipDataStorageInfo payslipDataStorageInfo,
			String [] notNeededKeys){
		Database db = Database.getInstance();
		
		for(String key:payslipDataStorageInfo.deductionKeys){
			PayslipComponentInfo info= deductionPayslipSwingList.get(key);
			JLabel label = info.label;
			JTextField field = info.textField;
			
			if(!key.equals(notNeededKeys[0]) && !key.equals(notNeededKeys[1])){
				label.setVisible(true);
				Utilities.getInstance().revalidateToMakeSureComponentsAreUpdated(deductionDataPanel);
				revalidate();
				
				field.setVisible(true);
				Utilities.getInstance().revalidateToMakeSureComponentsAreUpdated(deductionDataPanel);
				revalidate();
			}
			else{
				label.setVisible(false);
				Utilities.getInstance().revalidateToMakeSureComponentsAreUpdated(deductionDataPanel);
				revalidate();
				
				field.setVisible(false);
				Utilities.getInstance().revalidateToMakeSureComponentsAreUpdated(deductionDataPanel);
				revalidate();
			}
		}
		
		
		
		
	}


	/**
	 * Calculate the total deduction value in deduction fields.
	 * @param util
	 * @param db
	 * @return
	 * @throws NumberFormatException
	 */
	private double calculateTotalDeductionValue(Utilities util,Database db) throws NumberFormatException{
		double val=0;
		String totalDeductionKey=db.deductionTableColumnNames[db.deductionTableColumnNames.length-2];
		
		for(String key:deductionPayslipSwingList.keySet()){
			JTextField field=deductionPayslipSwingList.get(key).textField;
			String str=util.removeCommaOnNumbers(field.getText());
			double num=Double.parseDouble(str);
			
			val+=num;
			
			
//			field.setText(""+util.removeCommaOnNumbers(util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(num))));
			field.setText(""+util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(num)));
		
			
		}
		String str=util.removeCommaOnNumbers(deductionPayslipSwingList.get(totalDeductionKey).textField.getText());
		
		double num=Double.parseDouble(str);
		return util.convertRoundToOnlyTwoDecimalPlaces(val-num);
	}
	
	/**
	 * Calculate the total earning value in earning fields.
	 * @param util
	 * @param db
	 * @return
	 * @throws NumberFormatException
	 */
	private double calculateTotalEarningValue(Utilities util, Database db) throws NumberFormatException{
		double val=0;
		
		
		//--> If Contractual
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
			double overtime=0;
			
			//--> Add comma when changing
			for(String key:earningPayslipSwingList.keySet()){
				JTextField field=earningPayslipSwingList.get(key).textField;
				String str=util.removeCommaOnNumbers(field.getText());
				double num=Double.parseDouble(str);
				
//				field.setText(""+util.removeCommaOnNumbers(util.insertComma((util.convertRoundToOnlyTwoDecimalPlaces(num)))));
				field.setText(""+util.insertComma((util.convertRoundToOnlyTwoDecimalPlaces(num))));
				
				
				if(key.equals(db.earningsContractualColumnNames[6])){
					overtime=num;
				}
			}	
			
			//--> Update SubTotal
			double numOfDays=Double.parseDouble(earningPayslipSwingList.get(db.earningsContractualColumnNames[3]).textField.getText());
			double ratePerDay=Double.parseDouble(earningPayslipSwingList.get(db.earningsContractualColumnNames[4]).textField.getText());
			double subTotal=util.convertRoundToOnlyTwoDecimalPlaces(numOfDays*ratePerDay);
			
			earningPayslipSwingList.get(db.earningsContractualColumnNames[5]).textField.setText(""+util.removeCommaOnNumbers(util.insertComma(subTotal)));
			
			return util.convertRoundToOnlyTwoDecimalPlaces(overtime+subTotal);
		}
		
		
		//--> If Regular
		for(String key:earningPayslipSwingList.keySet()){
			JTextField field=earningPayslipSwingList.get(key).textField;
			String str=util.removeCommaOnNumbers(field.getText());
			double num=Double.parseDouble(str);
			
			val+=num;
			
//			field.setText(""+util.removeCommaOnNumbers(util.insertComma((util.convertRoundToOnlyTwoDecimalPlaces(num)))));
			field.setText(""+util.insertComma((util.convertRoundToOnlyTwoDecimalPlaces(num))));
			
		}

		String str=util.removeCommaOnNumbers(earningPayslipSwingList.get(db.earningTableColumnNames[db.earningTableColumnNames.length-1]).textField.getText());
		double num=Double.parseDouble(str);
		
		
		return util.convertRoundToOnlyTwoDecimalPlaces(val-num);
	}
	
	
	
	
	
	
	
	
	/**
	 * Add the swing components for both earnings and deduction on the payslip.
	 * @param panel
	 * @param payslipSwingList
	 */
	private void setPayslipSwingList(JPanel panel, HashMap<String,
			PayslipComponentInfo>payslipSwingList,
			String[] columnNameList,
			int earnOrDedMode){
		
		//--> To make sure that this only happens once.
		if(isSetPayslipSwingList){
			Database db= Database.getInstance();
			Utilities util= Utilities.getInstance();
			
			//--> Add the labels needed for deduction. Why 3? since the start column to be included is
			//		the SSSCont and Regular Pay.

			for(int i=3;i<(earnOrDedMode==Constant.EARNING_MODE?columnNameList.length:columnNameList.length-1);i++){
				payslipSwingList.put(
						columnNameList[i],
						new PayslipComponentInfo(
								new JLabel("  "+util.convertCamelCaseColumnNamesToReadable(columnNameList[i])+":"),
								new JTextField()
						)			
				);
				
				JLabel label= payslipSwingList.get(columnNameList[i]).label;
				label.setFont(labelFont);
				JTextField textField= payslipSwingList.get(columnNameList[i]).textField;
				textField.setFont(textFieldFont); 
				
				
//				//--> If SubTotal, Cant be edit
//				if(columnNameList[i].equals(db.earningsContractualColumnNames[5])){
//					textField.setEditable(false);
//				}
				
				
				//--> Only set editable false on total
				if((earnOrDedMode==Constant.EARNING_MODE && i==columnNameList.length-1) ||
						(earnOrDedMode==Constant.DEDUCTION_MODE && i==columnNameList.length-2)){
					textField.setFont(textFieldBoldFont); 
					textField.setEditable(false);
				}
				textField.setHorizontalAlignment(SwingConstants.RIGHT);
				
				panel.add(label);
				panel.add(textField);
				
				if((earnOrDedMode==Constant.EARNING_MODE && i==columnNameList.length-1) ||
						(earnOrDedMode==Constant.DEDUCTION_MODE && i==columnNameList.length-2)){
					textField.setBackground(highLightFieldColor);
				}
			}
			util.revalidateToMakeSureComponentsAreUpdated(panel);

		}
	}
	
	/**
	 * Add the labels and textfields of earnings and deduction based from the column names of earnings
	 *		and deduction data from database.
	 * @param homeViewPanel
	 */
	public void addDeductionAndEarningSwingComponents(Database db,Utilities util){
		
		//--> Add the deduction swing compoinents
		setPayslipSwingList(
			deductionDataPanel,
			deductionPayslipSwingList,
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.deductionTableColumnNames:db.deductionsContractualColumnNames,
			Constant.DEDUCTION_MODE
		);
		
		//------------------------------------------------------------------
		
		//--> Add the earning swing compoinents
		setPayslipSwingList(
			earningDataPanel, 
			earningPayslipSwingList,
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.earningTableColumnNames:db.earningsContractualColumnNames,
			Constant.EARNING_MODE
		);
		
		//--> When adding these swing components, it should only happen once to avoid redundant addition of components.
		isSetPayslipSwingList=false;
	}
	
	
	/**
	 * Add the data on the swing component textfields based from a given data.
	 * @param payslipDataStorageInfo
	 */
	public void addDataToTextFields(PayslipDataStorageInfo payslipDataStorageInfo){
		Utilities util=Utilities.getInstance();
		HashMap<String,String>dataList=payslipDataStorageInfo.dataList;
		String deductionTotalDeductionIndexKey=payslipDataStorageInfo.deductionKeys[payslipDataStorageInfo.deductionKeys.length-2],
				deductionCommentIndexKey=payslipDataStorageInfo.deductionKeys[payslipDataStorageInfo.deductionKeys.length-1],
				earningTotalEarningIndexKey=payslipDataStorageInfo.earningKeys[payslipDataStorageInfo.earningKeys.length-1];
		
		
		//--> Add employee data at the top of payslip
		JTextField []employeeDataTextFieldList={idTextField,nameTextField,periodTextField,departmentTextField};
		for(int i=0;i<employeeDataTextFieldList.length;i++){
			String value=dataList.get(payslipDataStorageInfo.employeeDataKeys[i]);
			employeeDataTextFieldList[i].setText(value);
		}
		
		//--> Add earning data on earning textfields
		for(String key:earningPayslipSwingList.keySet()){
			String value=util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(dataList.get(key))));
//			String value=""+util.removeCommaOnNumbers(util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(dataList.get(key)))));
			
			JTextField textField=earningPayslipSwingList.get(key).textField;
			JLabel label= earningPayslipSwingList.get(key).label;
			
			textField.setText(value);

		}
		
		//--> Add deduction data on deduction textfields
		for(String key:deductionPayslipSwingList.keySet()){

			//--> Debugging Purposes
//			System.out.println("\t POTA Key: "+key
//					+"\t Data: "+dataList.get(key).toString()+CLASS_NAME);
			
			if(!key.equals(deductionCommentIndexKey)){ // Do not include the Comment column which is at the last on deduction columns
				String value=util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(dataList.get(key))));
//				String value=""+util.removeCommaOnNumbers(util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(dataList.get(key)))));
				JTextField textField=deductionPayslipSwingList.get(key).textField;
				
				textField.setText(value.toString());
			}
			
		}
	

		
		
		//--> Add total result data
		
		String value=util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(dataList.get(earningTotalEarningIndexKey))));
		grossPayTextField.setText(value);
		
		value=util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(dataList.get(deductionTotalDeductionIndexKey))));
		lessDeductionsTextfield.setText(value);
		
		
		value=util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces((Double.parseDouble(dataList.get(earningTotalEarningIndexKey)) - Double.parseDouble(dataList.get(deductionTotalDeductionIndexKey)))));
		netPayTextfield.setText(value );
		
		commentTextArea.setText(payslipDataStorageInfo.dataList.get(deductionCommentIndexKey));

		
		this.payslipDataValueStorage=payslipDataStorageInfo;
		
	}
	
	/**
	 *  Extract the earning data from payslip data in order top be saved.
	 * @param db
	 * @param util
	 * @param changesToBeUpdated
	 * @param list
	 * @return
	 */
	public HashMap<String, Object>extractEarningDeductionDataToBeSaved(Database db,Utilities util,
				HashMap<String, Object>changesToBeUpdated,HashMap<String, PayslipComponentInfo>list){
		
		
		changesToBeUpdated.clear();
		
		// SAve earning and deductio data
		for(String key:list.keySet()){
			JTextField field=list.get(key).textField;
			String str=util.removeCommaOnNumbers(field.getText());
			double num=Double.parseDouble(str);
			num=util.convertRoundToOnlyTwoDecimalPlaces(num);
			
			changesToBeUpdated.put(key, num);
		}	
		
		return changesToBeUpdated;
	}
	
	
	public JTextArea getCommentTextArea() {
		return commentTextArea;
	}

	public int getBorderSpaceValue(){
		return borderSpaceVal;
	}
	/**
	 * Update gross pay and dedctions and net pay
	 * @param util
	 * @param db
	 */
	public void updateTotalEarningDeductionNetPay(Utilities util,Database db,MainFrame mainFrame) {
		isCalculationPossible=true;
		String totalDeductionKey=db.deductionTableColumnNames[db.deductionTableColumnNames.length-2];
		
		try{
			double totalEarnings =calculateTotalEarningValue(util,db),
					totalDeductions=calculateTotalDeductionValue(util,db),
					netPay=util.convertRoundToOnlyTwoDecimalPlaces((totalEarnings-totalDeductions));
			
			
			
			
			grossPayTextField.setText(""+util.insertComma(totalEarnings));
//			earningPayslipSwingList.get(db.earningTableColumnNames[db.earningTableColumnNames.length-1]).textField.setText(""+util.removeCommaOnNumbers(util.insertComma(totalEarnings)));
			earningPayslipSwingList.get(db.earningTableColumnNames[db.earningTableColumnNames.length-1]).textField.setText(""+util.insertComma(totalEarnings));
			
			
			lessDeductionsTextfield.setText(""+util.insertComma(totalDeductions));
//			deductionPayslipSwingList.get(totalDeductionKey).textField.setText(""+util.removeCommaOnNumbers(util.insertComma(totalDeductions)));
			deductionPayslipSwingList.get(totalDeductionKey).textField.setText(""+util.insertComma(totalDeductions));
			
			
			netPayTextfield.setText(""+util.insertComma(netPay));
		}catch(Exception e){
			isCalculationPossible=false;
			mainFrame.showOptionPaneMessageDialog("Please input numbers not text on fields.", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	/**
	 * Set all deduction or earning fields editable
	 * @param bool
	 */
	public void setEarningDeductionFieldsEditable(boolean bool,Database db){
		//--> Add earning data on earning textfields
		
		for(String key:earningPayslipSwingList.keySet()){
			earningPayslipSwingList.get(key).textField.setEditable(bool);
			
			//--> Set TotalEarnings and SubTotal and editable to false always
			if(key.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]) 
					|| key.equals(db.earningsContractualColumnNames[5]) ){ //  // Sub Total
				earningPayslipSwingList.get(key).textField.setEditable(false);
			}
			
		}
		
		//--> Add deduction data on deduction textfields
		for(String key:deductionPayslipSwingList.keySet()){
			deductionPayslipSwingList.get(key).textField.setEditable(bool);
			
			//--> Set TotalDeductions editable to false always
			if(key.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2])){
				deductionPayslipSwingList.get(key).textField.setEditable(false);
			}
		}
		
		
		//--> Set the commentTextArea
		commentTextArea.setEditable(bool);
	}
	
	/**
	 * Set the signature and name on payslip.
	 * This method is only executed ONCE.
	 */
	public void setPreparedByNameAndSignature(){
		Database db= Database.getInstance();
		Utilities util= Utilities.getInstance();
		String []temp=null;
		String name=null;
		ImageIcon signatureImg;
		
		
		db.selectDataInDatabase(
				new String[]{db.tableNameSignatureTable}, 
				new String[]{db.signatureTableColumnNames[1]}, 
				null, 
				null, 
				new OrderByInfo(new String[]{db.signatureTableColumnNames[0]}, "ASC"), 
				Constant.SELECT_BASED_FROM_COLUMN
		);
		
		try{
			
			switch(util.payrollSystemMode){
				case Constant.PAYROLL_SYSTEM_MODE_REGULAR:{
					db.resultSet.absolute(1);
//					temp=db.resultSet.getObject(1).toString().split(" ");
					name=db.resultSet.getObject(1).toString();
					break;
				}
				case Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL:{
					db.resultSet.absolute(2);
//					temp=db.resultSet.getObject(1).toString().split(" ");
					name=db.resultSet.getObject(1).toString();
					
					break;
				}
			}
			
			signatureImg = db.getSignatureImageFromDatabase(Constant.SIGNATURE_IMAGE_HEIGHT);
			
		}catch(Exception e){
			signatureImg =null;
			e.printStackTrace();
		
		}
		
	
		//--> Set Name and Signature
		preparedByNameTextArea.setText(name);
		signatureLabel.setIcon(signatureImg);
	}
}
