package view.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import view.dialog.DeleteUpdateEmployeeDialog;
import database.Database;
import model.Constant;
import model.PayrollTableModel;
import model.PayslipComponentInfo;
import model.PayslipDataStorageInfo;
import model.statics.Images;
import model.statics.Utilities;
import model.view.AddCommentsOnAllPayslipDialog;
import model.view.AddEarningOrDeductionDataLayout;
import model.view.AddPayrollDateDialogLayout;
import model.view.DisplayOptionsDialog;
import model.view.Edit15th30thTotalDialog;
import model.view.EditSignatureInfoDialog;
import model.view.OptionViewPanel;
import model.view.PayrollSlipLayout;
import model.view.PayslipDataDialog;
import model.view.ReusableTable;
import model.view.ShowAllEmployeeAddPayrollDataDialogLayout;
import model.view.WithOrWithoutATMDialog;
import model.view.YesOrNoIfDeleteDialogLayout;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

public class PayrollViewPanel extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	private static PayrollViewPanel instance;
	private Images img;
	private JPanel viewPanel,topPanel;
	private int topRightBtnWidth=73, topRightBtnHeight=34;
	private void l__________________________l(){}
	

	public PayrollSlipLayout payrollSlip;
	public ReusableTable fullScreenTable,totalFullScreenTable;
	public JScrollPane fullScreenTableScrollPane,totalTableScrollPane;
	public JButton payrollOptionButton,pdfOptionButton,excelOptionButton,lockOptionButton,
		addBtn,delBtn,showPayslipDialogBtn,netPayCopyModeBtn, cancelNetPayCopyModeBtn,
		showBtn;
	public DisplayOptionsDialog 
		dispEmployeePayrollSummaryOptionPDFEXCEL,dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
	public ArrayList<PayslipDataStorageInfo> payslipDataStorageList;
	public OptionViewPanel optionPanel,pdfPanel,excelPanel;
	public AddPayrollDateDialogLayout addPayrollDateDialog;
	public YesOrNoIfDeleteDialogLayout deletePayrollWarningDialog, lockedWarningDialog;
	public ShowAllEmployeeAddPayrollDataDialogLayout showAllEmpAddPayDaDialog;
	public AddEarningOrDeductionDataLayout addEDDataDialog;
	public PayslipDataDialog payslipDataDialog;
	public AddCommentsOnAllPayslipDialog addCommentsOnAllPayslipDialog;
	public WithOrWithoutATMDialog withOrWithoutATMDialog;
	public JLabel rowCountLabel,payrollDateDataLabel;
	public JPanel bottomPanel,showDisplayPanel,topRightButtonPanel;
	public JComboBox<String>departmentComboBox,payrollDateComboBox;
	public JTextField filterTextField;
	public EditSignatureInfoDialog editSignatureInfoDialog;
	
	private void l________________________________l(){}
	
	
	/**
	 * Create the panel.
	 */
	public PayrollViewPanel() {
		init();
		set();
	}
	
	private void init(){
		dispEmployeePayrollSummaryOptionPDFEXCEL=new DisplayOptionsDialog();
		dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL=new DisplayOptionsDialog();
		
		payslipDataStorageList=new ArrayList<PayslipDataStorageInfo>();
		addPayrollDateDialog=new AddPayrollDateDialogLayout();
		deletePayrollWarningDialog = new YesOrNoIfDeleteDialogLayout();
		lockedWarningDialog = new YesOrNoIfDeleteDialogLayout();
			lockedWarningDialog.setSize(lockedWarningDialog.getWidth(), lockedWarningDialog.getHeight()+10);
		
		showAllEmpAddPayDaDialog = new ShowAllEmployeeAddPayrollDataDialogLayout();
		addEDDataDialog = new AddEarningOrDeductionDataLayout();
		payslipDataDialog = new PayslipDataDialog();
		editSignatureInfoDialog= new EditSignatureInfoDialog();
		withOrWithoutATMDialog= new WithOrWithoutATMDialog();
		addCommentsOnAllPayslipDialog = new AddCommentsOnAllPayslipDialog();
		
		img=Images.getInstance();

	}
	private void set(){
		setThisPanelComponents();
		setOptionDialogComponents();
		setOptionPanelComponents();
		setPDFPanelComponents();
		setExcelPanelComponents();
		setViewPanel();
		setTopPanelComponents();
		setShowDisplayPanel();
		setTopRightButtonPanelComponents();
		setTableComponents();
		setBottomPanelComponents();
		setAddOrDeductionData();
		
		//--> Add background
//		add(payrollViewPanelBg);
		
		repaint();
	}
private void l____________________________l(){}
	
	/**
	 * Set this panel components.
	 */
	private void setThisPanelComponents(){
		setBounds(0, Constant.VIEW_Y, Constant.VIEW_WIDTH, Constant.VIEW_HEIGHT);
		setBackground(Color.GREEN);
		setLayout(null);
		setVisible(false);
		setOpaque(false);	
		
		//--> Temporary add	
		payrollSlip=new PayrollSlipLayout();
		payrollSlip.setLocation(403, 11);
		add(payrollSlip);
		payrollSlip.setVisible(false);
	}
	
	
	
	
	/**
	 * Set the components of all dialog in this view panel.
	 */
	private void setOptionDialogComponents(){
		DisplayOptionsDialog dialog= dispEmployeePayrollSummaryOptionPDFEXCEL;
		dialog.setTitle("Display per Department Payroll PDF Option");
		dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
		dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
		dialog.setSize(dialog.getWidth()+40, dialog.getHeight());
		
		
		dialog=dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		dialog.setTitle("Display Overall Payroll PDF Option");
		dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
		dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
		dialog.lblDepartment.setVisible(false);
		dialog.departmentComboBox.setVisible(false);
		dialog.setSize(dialog.getWidth()+60, dialog.getHeight()-10);

	}
	
	/**
	 * Set component of option panel.
	 */
	private void setOptionPanelComponents(){
		String[] buttonKeyList={
			Constant.VIEW_EARNING_DATA,
			Constant.VIEW_DEDUCTION_DATA,
			Constant.SHOW_PAYROLL_DATE,
			Constant.EDIT_SIGNATURE_INFO,
			Constant.ADD_COMMENTS_ON_PAYSLIP
			
		};
		ImageIcon[] imageList={
				img.viewEarningDataImg,
				img.viewDeductionDataImg,
				img.showPayrollDateImg,
				img.editSignatureInfoImg,
				img.addCommentsToPayslipImg
		};
		ImageIcon[] imageHoverList={
				img.viewEarningDataImgHover,
				img.viewDeductionDataImgHover,
				img.showPayrollDateImgHover,
				img.editSignatureInfoImgHover,
				img.addCommentsToPayslipImgHover
		};
		
		
		optionPanel=new OptionViewPanel(
				buttonKeyList,  img.payrollOptionsBgImg, 
				imageList, imageHoverList, 
				170, 50, 144, 125
		);
		
		optionPanel.buttonList.get("View Earning Data").setToolTipText("Alt+A");
		optionPanel.buttonList.get("View Deduction Data").setToolTipText("Alt+D");
		add(optionPanel);
		
	}
	
	/**
	 * Set PDF Panel Components.
	 */
	private void setPDFPanelComponents(){
		String[] buttonKeyList=null;
		ImageIcon[] imageList=null;
		ImageIcon[] imageHoverList=null;
		
		pdfPanel=new OptionViewPanel(
				buttonKeyList,  img.pdfOptionsContractualBgImg, 
				imageList, imageHoverList, 
				210, 50, 242, 188
		);
		
		add(pdfPanel);
	}
	
	/**
	 * Set necessary components of pdf panel.
	 * @param bgImg
	 * @param width
	 * @param height
	 * @param buttonKeyList
	 * @param imageList
	 * @param imageHoverList
	 */
	private void setNecessaryPDFPanelComponentsWhenExtended(ImageIcon bgImg,int width,int height,
			String[]buttonKeyList, ImageIcon[]imageList, ImageIcon[] imageHoverList){
		
		//--> Set Calculation Panel
		pdfPanel.setLayout(null);
		pdfPanel.setOpaque(false);
		pdfPanel.bg.setIcon(bgImg);
		pdfPanel.setSize(width, height);
		pdfPanel.bg.setBounds(0,0,pdfPanel.getWidth(),pdfPanel.getHeight());
		
		
		pdfPanel.initNewListValues(buttonKeyList, imageList, imageHoverList);
		pdfPanel.addOptionButtons();
		repaint();
		
	}
	/**
	 * Set component of EXCEL panel.
	 */
	private void setExcelPanelComponents(){
		
		String[] buttonKeyList=null;
		ImageIcon[] imageList=null;
		ImageIcon[] imageHoverList=null;
			
			
			excelPanel=new OptionViewPanel(
					buttonKeyList,  img.excelOptionsBgImg	, 
					imageList, imageHoverList, 
					250, 50, 211, 321
			);

			add(excelPanel);
		
	}
	
	/**
	 * Set necessary components of excel panel.
	 * @param bgImg
	 * @param width
	 * @param height
	 * @param buttonKeyList
	 * @param imageList
	 * @param imageHoverList
	 */
	private void setNecessaryExcelPanelComponentsWhenExtended(ImageIcon bgImg,int width,int height,
			String[]buttonKeyList, ImageIcon[]imageList, ImageIcon[] imageHoverList){
		
		//--> Set Calculation Panel
		excelPanel.setLayout(null);
		excelPanel.setOpaque(false);
		excelPanel.bg.setIcon(bgImg);
		excelPanel.setSize(width, height);
		excelPanel.bg.setBounds(0,0,excelPanel.getWidth(),excelPanel.getHeight());
		
		
		excelPanel.initNewListValues(buttonKeyList, imageList, imageHoverList);
		excelPanel.addOptionButtons();
		repaint();
		
	}
	/**
	 * Set view panel where top panel and table panel is added.
	 */
	private void setViewPanel(){
		//--> Set View Panel:
				viewPanel=new JPanel();
				viewPanel.setBounds(10,0,this.getWidth()-20,this.getHeight()-Constant.SCROLL_BAR_WIDTH);
				viewPanel.setBackground(Color.BLUE);
				viewPanel.setOpaque(false);
				viewPanel.setLayout(new BorderLayout());
				add(viewPanel);
				
				
	}
	
	
	/**
	 * Set components of the top panel.
	 */
	private void setTopPanelComponents(){
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0,100));
		topPanel.setBackground(Constant.BILECO_DEFAULT_COLOR_GREEN);
		topPanel.setOpaque(false);
		topPanel.setLayout(null);
		viewPanel.add(topPanel, BorderLayout.NORTH);
		
		//---------------------------------------------
		//--> Add View TITLE Label.
		JLabel lblEmployee = new JLabel(img.payrollViewTitleImg);
		lblEmployee.setBounds(13, 13, 135,26);
		lblEmployee.setFont(Constant.VIEW_TITLE);
		topPanel.add(lblEmployee);
		
		//---------------------------------------------
		//--> Add Option button.
		payrollOptionButton = Utilities.getInstance().initializeNewButton(
				148, 5, 47, 47, img.optionBtnImg, img.optionHoverBtnImg
		);
		payrollOptionButton.setFocusable(false);
		topPanel.add(payrollOptionButton);
		
		//---------------------------------------------
		//--> Add PDF button.
		pdfOptionButton = Utilities.getInstance().initializeNewButton(
				payrollOptionButton.getX()+payrollOptionButton.getWidth()-2,
				payrollOptionButton.getY(),
				42, 42
				, img.pdfImgBtn, img.pdfImgBtnHover
		);
		pdfOptionButton.setFocusable(false);
		topPanel.add(pdfOptionButton);
		
		//---------------------------------------------
		//--> Add EXCEL button.
		excelOptionButton = Utilities.getInstance().initializeNewButton(
				pdfOptionButton.getX()+pdfOptionButton.getWidth()-2,
				pdfOptionButton.getY(),
				42, 42
				, img.excelImgBtn, img.excelImgBtnHover
		);
		excelOptionButton.setFocusable(false);
		topPanel.add(excelOptionButton);
		

		//---------------------------------------------
		//--> Add Lock button.
		lockOptionButton = Utilities.getInstance().initializeNewButton(
				excelOptionButton.getX()+excelOptionButton.getWidth()-2,
				excelOptionButton.getY(),
				37, 39
				, img.lockImgBtn, img.lockImgBtnHover
		);
		lockOptionButton.setFocusable(false);
		topPanel.add(lockOptionButton);
		
		
		//-----------------------------------------------
		//--> Add the TITLE if DATA or DATE option button/label
		int optionSettingTitleBtnWIDTH=127,optionSettingTitleBtnHEIGHT=30;
		payrollDateDataLabel=new JLabel();
		payrollDateDataLabel.setHorizontalAlignment(JLabel.CENTER);
		payrollDateDataLabel.setVerticalAlignment(JLabel.CENTER);
		payrollDateDataLabel.setBounds(
			Constant.VIEW_WIDTH/2-(optionSettingTitleBtnWIDTH/2),
			20,
			optionSettingTitleBtnWIDTH,
			optionSettingTitleBtnHEIGHT
		);
		topPanel.add(payrollDateDataLabel);
		
		//--------------------------------------------------------------
		//--> Add Search bar image.
		int h=52;
		JLabel searchBar = new JLabel(img.searchBarImg);
		searchBar.setBounds(435,h, 376, 43);
		
		//--> Add Filter Textfield, Label and Combobox, search button.
		filterTextField = new JTextField();
		filterTextField.setBounds(442, h, 340, 31);
		filterTextField.setOpaque(false);
		filterTextField.setBorder(BorderFactory.createEmptyBorder());
		filterTextField.setColumns(10);
		filterTextField.setFocusable(true);
		topPanel.add(filterTextField);
		
	
		
		JButton searchBtn = Utilities.getInstance().initializeNewButton(
				782, h+4, 23, 24, img.searchBtnImg, img.searchBtnHoverImg
		);
		topPanel.add(searchBtn);
		
		
		topPanel.add(searchBar);
		
	}
	
	/**
	 * Set the components of top right button panel where
	 * 	add, delete, netpaycopymode, cancelcopymode.
	 */
	private void setTopRightButtonPanelComponents(){
		int y=70;;
		
		//--> Top left panel
		topRightButtonPanel = new JPanel();
		topRightButtonPanel.setBounds(
			showDisplayPanel.getX()+showDisplayPanel.getWidth()-(topRightBtnWidth*3),
			y,
			topRightBtnWidth,
			topRightBtnHeight
		);
		topRightButtonPanel.setBackground(Color.GREEN);
		topRightButtonPanel.setOpaque(false);
		topRightButtonPanel.setLayout(new GridLayout(1, 3, 0, 0));
		topPanel.add(topRightButtonPanel);
		

		//---------------------------------------------
		//--> Add ADD payroll date/data btn
		
		addBtn = Utilities.getInstance().initializeNewButton(
				-1,-1,-1,-1, img.edAddImg, img.edAddImgHover
		);
		topRightButtonPanel.add(addBtn);
		
		//---------------------------------------------
		
		//--> Add DELETE payroll date/data btn
		delBtn = Utilities.getInstance().initializeNewButton(
				-1,-1,-1,-1, img.edDeleteImg, img.edDeleteImgHover
		);
		topRightButtonPanel.add(delBtn);
		
		//---------------------------------------------
		
		//--> Add SHOW PAYSLIP DIALOG  btn
		
		//--> Add DELETE payroll date/data btn
		showPayslipDialogBtn = Utilities.getInstance().initializeNewButton(
				-1,-1,-1,-1, img.showPayslipDialogBtnImg, img.showPayslipDialogBtnImgHover
		);
		topRightButtonPanel.add(showPayslipDialogBtn);
		
		//---------------------------------------------
		
		//--> Add NET PAY COPY MODE  btn
		netPayCopyModeBtn = Utilities.getInstance().initializeNewButton(
				-1,-1,-1,-1, img.netPayCopyModeImg, img.netPayCopyModeImgHover
		);
		topRightButtonPanel.add(netPayCopyModeBtn);
		
		cancelNetPayCopyModeBtn = Utilities.getInstance().initializeNewButton(
				-1,-1,-1,-1, img.cancelNetPayCopyModeImg, img.cancelNetPayCopyModeImgHover
		);
		topRightButtonPanel.add(cancelNetPayCopyModeBtn);
				
				
//		updateTopLeftPanel(3,"");
		
//		leftTopPanel.add(addBtn);
//		leftTopPanel.add(deleteBtn);
//		leftTopPanel.add(editBtn);
//		leftTopPanel.add(calculateBtn);
//		leftTopPanel.add(saveBtn);
//		leftTopPanel.add(cancelBtn);	
	}
	
	/**
	 * Set the component of show display panel.
	 */
	private void setShowDisplayPanel(){
		showDisplayPanel=new JPanel();
		showDisplayPanel.setBounds(950,0,289,65);
		showDisplayPanel.setOpaque(false);
		showDisplayPanel.setLayout(null);
		topPanel.add(showDisplayPanel);
		
		
		//----------------------------
		showBtn = Utilities.getInstance().initializeNewButton(
				214, 32, 73, 34, img.showButtonImg, img.showButtonImgHover
		);
		showBtn.setToolTipText("Alt+W");
		showDisplayPanel.add(showBtn);
		
		payrollDateComboBox = new JComboBox<String>();
		payrollDateComboBox.setBounds(94,6,107,20);
		showDisplayPanel.add(payrollDateComboBox);
		
		departmentComboBox = new JComboBox<String>();
		departmentComboBox.setBounds(94,35,107,20);
		showDisplayPanel.add(departmentComboBox);
		
		
	
		
		//----------------------------
		
		JLabel label = new JLabel(img.showDisplayPanelBG);
		label.setBounds(0, 0, showDisplayPanel.getWidth(),showDisplayPanel.getHeight());
		showDisplayPanel.add(label);
	}
	/**
	 * Set components of the table panel.
	 */
	private void setTableComponents(){
		JPanel tablePanel = new JPanel();
		tablePanel.setOpaque(false);
		tablePanel.setBackground(Color.ORANGE);
		tablePanel.setLayout(new GridLayout(1,0));
		viewPanel.add(tablePanel, BorderLayout.CENTER);
		
		
		//--> Set table
		TableRowSorter<PayrollTableModel> sorter;
		
		//> Employee Table
		PayrollTableModel fullScreenTableModel=new PayrollTableModel();
		sorter= new TableRowSorter<PayrollTableModel>(fullScreenTableModel); 
		fullScreenTable=new ReusableTable(fullScreenTableModel, sorter, false);
//		fullScreenTable.setBackground(new Color(255, 255, 153));
		fullScreenTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		
		
		//---------------------------------------------------------------------------------------------------------------------------------
		//--> Set Scrollpane
		 //--> Create the scroll pane and add the table to it.
		//--> Remember: To show the horizontal scroll bar, 
		//		the main panel layout must be null/absolute and autoresize of table id OFF.
		//		Since the horizontal scroll bar is not shown if the scrollpane is too long.
		//		Height of a horzontak scrollbar is 20;
		
		fullScreenTableScrollPane = new JScrollPane(fullScreenTable);
		fullScreenTableScrollPane.setBounds(
				0, 
				0,
				tablePanel.getWidth(),
				tablePanel.getHeight());
		fullScreenTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		fullScreenTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		fullScreenTableScrollPane.setOpaque(false);
		tablePanel.add(fullScreenTableScrollPane);
	
	}
	
	private void setBottomPanelComponents(){
		bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setBackground(Color.ORANGE);
		bottomPanel.setPreferredSize(new Dimension(0,20));
		bottomPanel.setLayout(null);
		viewPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		//-------------------------------------------------------------
		rowCountLabel = new JLabel("Row Count: 0");
		rowCountLabel.setBounds(10, 0, Constant.ROW_COUNT_LABEL_WIDTH, Constant.ROW_COUNT_LABEL_HEIGHT);
		bottomPanel.add(rowCountLabel);
		
		//-------------------------------------------------------------
		//> Set Total Table
		PayrollTableModel totalTableModel=new PayrollTableModel();
		TableRowSorter<PayrollTableModel>totalSorter= new TableRowSorter<PayrollTableModel>(totalTableModel); 
		totalFullScreenTable=new ReusableTable(totalTableModel,totalSorter,true);
		
		//> Set Total Table Scrollpane.
		totalTableScrollPane = new JScrollPane(totalFullScreenTable);
		totalTableScrollPane.setBounds(
				rowCountLabel.getX(), 
				 rowCountLabel.getY()+rowCountLabel.getHeight()+10,
				Constant.VIEW_WIDTH-Constant.SCROLL_BAR_WIDTH*2,
				60);
		totalTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		totalTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		bottomPanel.add(totalTableScrollPane);
		
		
	}
	
	private void setAddOrDeductionData(){
		addEDDataDialog.setTitle("Choose Payroll Date and Generate ID");
		
		
		addEDDataDialog.cancelButton.setVisible(false);
	}
	
	private void l_____________________________l(){}
	
	/**
	 * Adjust the height of bottom panel depending if payroll date or payroll data are shown.
	 * @param height
	 */
	public void changeTheHeightOfBottomPanel(int height){
		bottomPanel.setPreferredSize(new Dimension(0,height));
	}
	
	/**
	 * Execute the search mechanism of all tables.
	 */
	public void executeSearchMechanismOfAllTables(){
		if(fullScreenTable.getModel().getRowCount()>0){
			fullScreenTable.searchFilterMechanism(filterTextField,rowCountLabel);
		}
		
	}
	
	/**
	 * Return a payslip storage info based from the given earningID or deductionID.
	 * @param db
	 * @param id
	 * @param mode
	 * @return
	 */
	public PayslipDataStorageInfo getPayslipStorageInfoBasedFromDeductionOrEarningID(Database db,String id,int mode){
		for(PayslipDataStorageInfo info:payslipDataStorageList){
			if(mode==Constant.EARNING_MODE && info.earningID.equals(id)){
				return info;
			}
			else if(mode==Constant.DEDUCTION_MODE && info.deductionID.equals(id)){
				return info;
			}
		}
		
		
		return null;
	}
	
	/**
	 * Update the shown buttons in top left button.
	 * @param numoFBtns
	 */
	public void updateTopRightButtonPanel(int numoFBtns){
		//> Remove all
		topRightButtonPanel.remove(addBtn);
		topRightButtonPanel.remove(delBtn);
		topRightButtonPanel.remove(showPayslipDialogBtn);
		topRightButtonPanel.remove(netPayCopyModeBtn);
		topRightButtonPanel.remove(cancelNetPayCopyModeBtn);
		
		
		//--> Manipulations
		
		//--> Set Location
		topRightButtonPanel.setLocation(
			showDisplayPanel.getX()+showDisplayPanel.getWidth()-(topRightBtnWidth*numoFBtns),
			topRightButtonPanel.getY()
		);
		
		
		if(numoFBtns==2){
			//> Add necessary
			topRightButtonPanel.add(addBtn);
			topRightButtonPanel.add(delBtn);
		}
		//--> When calculate, save, cancel buttons are only shown.
		else if(numoFBtns==4){
			//> Add necessary
			topRightButtonPanel.add(addBtn);
			topRightButtonPanel.add(delBtn);
			topRightButtonPanel.add(showPayslipDialogBtn);
			topRightButtonPanel.add((netPayCopyModeBtn.isVisible())?netPayCopyModeBtn:cancelNetPayCopyModeBtn);
			
		}

		
		//> Set and add components
		topRightButtonPanel.setSize(topRightBtnWidth*numoFBtns,topRightBtnHeight);
		topRightButtonPanel.revalidate();
		topRightButtonPanel.repaint();
	}
	
	public void setPDFPanelComponentsContractual(){
		String[] buttonKeyList={
				Constant.DISPLAY_PAYSLIP_PDF,
				Constant.DISPLAY_DEPARTMENT_PAYROLL_DATA_PDF,
				Constant.DISPLAY_SSS_LOAN_DATA_PDF,
				Constant.DISPLAY_PAGIBIG_LOAN_PDF,
				Constant.DISPLAY_MEDICARE_PDF,
				Constant.DISPLAY_SSS_CONT_PDF,
				Constant.DISPLAY_HDMF_PDF
			};
			ImageIcon[] imageList={
					img.displayPayslipPDFImg,
					img.displayDepartmentPayrollDataPDFImg,
					img.displaySSSLoanPayrollDataPDFImg,
					img.displayPagibigLoanPayrollDataPDFImg,
					img.displayMedicarePayrollDataPDFImg,
					img.displaySssContPayrollDataPDFImg,
					img.displayHDMFPayrollDataPDFImg
			};
			ImageIcon[] imageHoverList={
					img.displayPayslipPDFImgHover,
					img.displayDepartmentPayrollDataPDFImgHover,
					img.displaySSSLoanPayrollDataPDFImgHover,
					img.displayPagibigLoanPayrollDataPDFImgHover,
					img.displayMedicarePayrollDataPDFImgHover,
					img.displaySssContPayrollDataPDFImgHover,
					img.displayHDMFPayrollDataPDFImgHover
			};
			
	
			setNecessaryPDFPanelComponentsWhenExtended(
					img.pdfOptionsContractualBgImg,
					 242, 188,
					buttonKeyList, imageList, imageHoverList);
	}
	/**
	 * Set component of pdf panel.
	 */
	public void setPDFPanelComponentsRegular(){
		String[] buttonKeyList={
			Constant.DISPLAY_PAYSLIP_PDF,
			Constant.DISPLAY_EMPLOYEE_PAYROLL_DATA_PDF,
			Constant.DISPLAY_DEPARTMENT_PAYROLL_DATA_PDF,
			Constant.DISPLAY_ASEMCO_DATA_PDF,
			Constant.DISPLAY_BCCI_DATA_PDF,
			Constant.DISPLAY_OCCCI_DATA_PDF,
			Constant.DISPLAY_DBP_DATA_PDF,
			Constant.DISPLAY_CFI_DATA_PDF,
			Constant.DISPLAY_ST_PLAN_PDF,
			Constant.DISPLAY_W_TAX_PDF,
			Constant.DISPLAY_LBP_PDF,
			Constant.DISPLAY_SSS_LOAN_DATA_PDF,
			Constant.DISPLAY_PAGIBIG_LOAN_PDF,
			Constant.DISPLAY_UNION_DUES_PDF,
			Constant.DISPLAY_SSS_CONT_PDF,
			Constant.DISPLAY_HDMF_PDF,
			Constant.DISPLAY_MEDICARE_PDF
		};
		ImageIcon[] imageList={
				img.displayPayslipPDFImg,
				img.displayEmployeePayrollDataPDFImg,
				img.displayDepartmentPayrollDataPDFImg,
				img.displayASEMCOPayrollDataPDFImg,
				img.displayBCCIPayrollDataPDFImg,
				img.displayOCCCIPayrollDataPDFImg,
				img.displayDBPPayrollDataPDFImg,
				img.displayCFIPayrollDataPDFImg,
				img.displayStPlanPayrollDataPDFImg,
				img.displayWTaxPayrollDataPDFImg,
				img.displayLBPPayrollDataPDFImg,
				img.displaySSSLoanPayrollDataPDFImg,
				img.displayPagibigLoanPayrollDataPDFImg,
				img.displayUnionDuesPayrollDataPDFImg,
				img.displaySssContPayrollDataPDFImg,
				img.displayHDMFPayrollDataPDFImg,
				img.displayMedicarePayrollDataPDFImg
		};
		ImageIcon[] imageHoverList={
				img.displayPayslipPDFImgHover,
				img.displayEmployeePayrollDataPDFImgHover,
				img.displayDepartmentPayrollDataPDFImgHover,
				img.displayASEMCOPayrollDataPDFImgHover,
				img.displayBCCIPayrollDataPDFImgHover,
				img.displayOCCCIPayrollDataPDFImgHover,
				img.displayDBPPayrollDataPDFImgHover,
				img.displayCFIPayrollDataPDFImgHover,
				img.displayStPlanPayrollDataPDFImgHover,
				img.displayWTaxPayrollDataPDFImgHover,
				img.displayLBPPayrollDataPDFImgHover,
				img.displaySSSLoanPayrollDataPDFImgHover,
				img.displayPagibigLoanPayrollDataPDFImgHover,
				img.displayUnionDuesPayrollDataPDFImgHover,
				img.displaySssContPayrollDataPDFImgHover,
				img.displayHDMFPayrollDataPDFImgHover,
				img.displayMedicarePayrollDataPDFImgHover
		};
		
		

		
		setNecessaryPDFPanelComponentsWhenExtended(
				img.pdfOptionsBgImg,
				242, 335,
				buttonKeyList, imageList, imageHoverList);
		
		
		
		

	}
	
	/**
	 * Set component of EXCEL panel.
	 */
	public void setExcelPanelComponentsRegular(){
		
		String[] buttonKeyList={
			Constant.EXPORT_EMPLOYEE_PAYROLL_DATA_EXCEL,
			Constant.EXPORT_DEPARTMENT_PAYROLL_DATA_EXCEL,
			Constant.EXPORT_ASEMCO_DATA_EXCEL,
			Constant.EXPORT_BCCI_DATA_EXCEL,
			Constant.EXPORT_OCCCI_DATA_EXCEL,
			Constant.EXPORT_DBP_DATA_EXCEL,
			Constant.EXPORT_CFI_DATA_EXCEL,
			Constant.EXPORT_ST_PLAN_EXCEL,
			Constant.EXPORT_W_TAX_EXCEL,
			Constant.EXPORT_LBP_EXCEL,
			Constant.EXPORT_SSS_LOAN_DATA_EXCEL,
			Constant.EXPORT_PAGIBIG_LOAN_EXCEL,
			Constant.EXPORT_UNION_DUES_EXCEL,
			Constant.EXPORT_SSS_CONT_EXCEL,
			Constant.EXPORT_HDMF_EXCEL,
			Constant.EXPORT_MEDICARE_EXCEL
		};
			ImageIcon[] imageList={
					img.exportEmployeePayrollDataExcelImg,
					img.exportDepartmentPayrollDataExcelImg,
					img.exportASEMCOPayrollDataExcelImg,
					img.exportBCCIPayrollDataExcelImg,
					img.exportOCCCIPayrollDataExcelImg,
					img.exportDBPPayrollDataExcelImg,
					img.exportCFIPayrollDataExcelImg,
					img.exportStPlanPayrollDataExcelImg,
					img.exportWTaxPayrollDataExcelImg,
					img.exportLBPPayrollDataExcelImg,
					img.exportSSSLoanPayrollDataExcelImg,
					img.exportPagibigLoanPayrollDataExcelImg,
					img.exportUnionDuesPayrollDataExcelImg,
					img.exportSssContPayrollDataExcelImg,
					img.exportHDMFPayrollDataExcelImg,
					img.exportMedicarePayrollDataExcelImg
			};
			ImageIcon[] imageHoverList={
				img.exportEmployeePayrollDataExcelImgHover,
				img.exportDepartmentPayrollDataExcelImgHover,
				img.exportASEMCOPayrollDataExcelImgHover,
				img.exportBCCIPayrollDataExcelImgHover,
				img.exportOCCCIPayrollDataExcelImgHover,
				img.exportDBPPayrollDataExcelImgHover,
				img.exportCFIPayrollDataExcelImgHover,
				img.exportStPlanPayrollDataExcelImgHover,
				img.exportWTaxPayrollDataExcelImgHover,
				img.exportLBPPayrollDataExcelImgHover,
				img.exportSSSLoanPayrollDataExcelImgHover,
				img.exportPagibigLoanPayrollDataExcelImgHover,
				img.exportUnionDuesPayrollDataExcelImgHover,
				img.exportSssContPayrollDataExcelImgHover,
				img.exportHDMFPayrollDataExcelImgHover,
				img.exportMedicarePayrollDataExcelImgHover	
			};
			

			setNecessaryExcelPanelComponentsWhenExtended(
					img.excelOptionsBgImg, 
					211, 321, 
					buttonKeyList, imageList, imageHoverList);
		
	}
	
	/**
	 * Set component of EXCEL panel.
	 */
	public void setExcelPanelComponentsContractual(){
		
		String[] buttonKeyList={
			Constant.EXPORT_DEPARTMENT_PAYROLL_DATA_EXCEL,
			Constant.EXPORT_SSS_LOAN_DATA_EXCEL,
			Constant.EXPORT_PAGIBIG_LOAN_EXCEL,
			Constant.EXPORT_MEDICARE_EXCEL,
			Constant.EXPORT_SSS_CONT_EXCEL,
			Constant.EXPORT_HDMF_EXCEL
	
		};
			ImageIcon[] imageList={
					img.exportDepartmentPayrollDataExcelImg,
					img.exportSSSLoanPayrollDataExcelImg,
					img.exportPagibigLoanPayrollDataExcelImg,
					img.exportMedicarePayrollDataExcelImg,
					img.exportSssContPayrollDataExcelImg,
					img.exportHDMFPayrollDataExcelImg
			};
			ImageIcon[] imageHoverList={
				img.exportDepartmentPayrollDataExcelImgHover,
				img.exportSSSLoanPayrollDataExcelImgHover,
				img.exportPagibigLoanPayrollDataExcelImgHover,
				img.exportMedicarePayrollDataExcelImgHover,
				img.exportSssContPayrollDataExcelImgHover,
				img.exportHDMFPayrollDataExcelImgHover
			};
			

			setNecessaryExcelPanelComponentsWhenExtended(
					img.excelOptionsContractualBgImg, 
					211, 153, 
					buttonKeyList, imageList, imageHoverList);
		
	}
	private void l_________________________________l(){}
	
	public static PayrollViewPanel getInstance(){
		if(instance==null)
			instance=new PayrollViewPanel();
		return instance;
	}
	
	public static void setInstanceToNull(){
		instance =null;
	}
	
	
	

}
