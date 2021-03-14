package controller;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.KeyStroke;

import model.Constant;
import model.statics.Utilities;
import model.view.EarningsAndDeductionLayout;
import controller.listeners.ListenerAddEmployee;
import controller.listeners.ListenerConnectToServerDialog;
import controller.listeners.ListenerDeleteUpdateEmployeeDialog;
import controller.listeners.ListenerEarningDeductionViewPanel;
import controller.listeners.ListenerEmployeeViewPanel;
import controller.listeners.ListenerHelpViewPanel;
import controller.listeners.ListenerHomeViewPanel;
import controller.listeners.ListenerLoginFrame;
import controller.listeners.ListenerMainFrame;
import controller.listeners.ListenerPayrollViewPanel;
import controller.listeners.ListenerSettingsViewPanel;
import controller.listeners.ListenerUpdateEmployeeDialog;
import database.Database;
import view.LoadingScreen;
import view.LoginFrame;
import view.MainFrame;
import view.dialog.AddEmployeeDialog;
import view.dialog.ConnectToServerDialog;
import view.dialog.DeleteUpdateEmployeeDialog;
import view.dialog.UpdateEmployeeDialog;
import view.views.DeductionViewPanel;
import view.views.EarningViewPanel;
import view.views.EmployeeViewPanel;
import view.views.HelpViewPanel;
import view.views.HomeViewPanel;
import view.views.PayrollViewPanel;
import view.views.SettingsViewPanel;

public class Controller implements WindowListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static Controller instance;
	private MainFrame mainFrame;
	private HomeViewPanel homeViewPanel;
	private EmployeeViewPanel employeeViewPanel;
	private ConnectToServerDialog connectToServerDialog;
	private LoginFrame loginDialog;
	private AddEmployeeDialog addEmployeeDialog;
	private DeleteUpdateEmployeeDialog bothDeleteUpdateEmployeeDialog;
	private UpdateEmployeeDialog updateEmployeeDialog;
	private EarningsAndDeductionLayout bothEarningDeductionViewPanel;
	private PayrollViewPanel payrollViewPanel;
	private SettingsViewPanel settingsViewPanel;
	private HelpViewPanel helpViewPanel;
	
	private void l____________________________________________l(){}
	
	private ListenerMainFrame mainFrameListener; 
	private ListenerEmployeeViewPanel employeeViewPanelListener;
	private ListenerConnectToServerDialog connectToServerDialogListener;
	private ListenerLoginFrame loginFrameListener;
	private ListenerHomeViewPanel homeViewPanelListener;
	private ListenerAddEmployee addEmployeeListener;
	private ListenerDeleteUpdateEmployeeDialog deleteUpdateEmployeeListener;
	private ListenerUpdateEmployeeDialog updateEmployeeDialigListener;
	private ListenerEarningDeductionViewPanel earningDeductionViewPanelListener;
	private ListenerPayrollViewPanel payrollViewPanelListener;
	private ListenerSettingsViewPanel settingsViewPanelListener;
	private ListenerHelpViewPanel helpViewPanelListener;
	private void l___________________________________l(){}

	/**
	 * 
	 */
	public Controller() {
		System.out.println(">>>>>>>> Controller Screen initialize.."+CLASS_NAME);
		
		
		// TODO Auto-generated constructor stub
		System.out.println("\tController class new instance."+CLASS_NAME);
		System.out.println("\t\tPOTASASS Width: "+Constant.FRAME_WIDTH
				+"\tHeight: "+Constant.FRAME_HEIGHT+CLASS_NAME);
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Swing Components...");
		
		
		//--> Set swing components
		mainFrame=MainFrame.getInstance();
		homeViewPanel=HomeViewPanel.getInstance();
		employeeViewPanel=EmployeeViewPanel.getInstance();
		connectToServerDialog=ConnectToServerDialog.getInstance();
		loginDialog=LoginFrame.getInstance();
		addEmployeeDialog=AddEmployeeDialog.getInstance();
		bothDeleteUpdateEmployeeDialog=DeleteUpdateEmployeeDialog.getInstance();
		updateEmployeeDialog=UpdateEmployeeDialog.getInstance();
		payrollViewPanel=PayrollViewPanel.getInstance();
		settingsViewPanel=SettingsViewPanel.getInstance();
		helpViewPanel= HelpViewPanel.getInstance();
		
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...");
		
		//--> Set Listeners
		setAllListenetsToNull();
		mainFrameListener=new ListenerMainFrame();
		homeViewPanelListener = new ListenerHomeViewPanel();
		employeeViewPanelListener= new ListenerEmployeeViewPanel();
		connectToServerDialogListener=new ListenerConnectToServerDialog();
		loginFrameListener = new ListenerLoginFrame();
		addEmployeeListener= new ListenerAddEmployee();
		deleteUpdateEmployeeListener= new ListenerDeleteUpdateEmployeeDialog();
		updateEmployeeDialigListener=new ListenerUpdateEmployeeDialog();
		earningDeductionViewPanelListener=new ListenerEarningDeductionViewPanel();
		payrollViewPanelListener = new ListenerPayrollViewPanel();
		settingsViewPanelListener = new ListenerSettingsViewPanel();
		helpViewPanelListener=new ListenerHelpViewPanel();
	
		
		addEmployeeListener.setters(employeeViewPanelListener);
		deleteUpdateEmployeeListener.setters(employeeViewPanelListener);
		updateEmployeeDialigListener.setOutsideListeners(deleteUpdateEmployeeListener);
		payrollViewPanelListener.setOutsideListeners(mainFrameListener);
		earningDeductionViewPanelListener.setOutsideListeners(mainFrameListener);
		mainFrameListener.setters(earningDeductionViewPanelListener);
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Add Listeners..."); 
		
		//--> Add Listeners
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...MainFrame");
		addListenerToMainFrame();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...HomeViewPanel");
		addListenerToHomeViewPanel();
		
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...EmployeeViewPanel");
		addListenerToEmployeeViewPanel();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...ConnectToServerDialog");
		addListenerToConnectionServerDialog();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...LoginFrame");
		addListenerToLoginFrame();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...AddEmployeeDialog");
		addListenerToAddEmployeeDialog();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...DeleteUpdateEmployeeDialog");
		addListenerToDeleteUpdateEmployeeDialog();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...UpdateEmployeeDialog");
		addListenerToUpdateEmployeeDialog();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...EarningDeductionViewPanel");
		addListenerToEarningDeductionViewPanel();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...PayrollViewPanel");
		addListenerToPayrollViewPanel();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...SettingsPanel");
		addListenerToSettingsViewPanel();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...HelpPanel");
		addListenerToHelpViewPanel();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...WindowListener");
		addWindowListenerToComponents();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...KeyboardMnemonics");
		addKeyboardShortcutMnemonicKeyEventsToComponents();
		
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize: Set Listeners...KeyListeners");
		addKeyListenerToComponents();
		
		 
		System.out.println(">>>>>>>> Controller Screen DONE.."+CLASS_NAME);
		System.out.println(">>>>>>>> Loading Screen DONE... Launching Program..."+CLASS_NAME);
		
	}
	
	private void l__________________________________________l(){}
	
	/**
	 * Add listener to necessary components in Main Frame.
	 */
	private void addListenerToMainFrame(){	
		mainFrame.bilecoLogoButton.addActionListener(mainFrameListener);
		mainFrame.connectToServerMenuItem.addActionListener(mainFrameListener);
		mainFrame.checkConnectionMenuItem.addActionListener(mainFrameListener);
		mainFrame.homeMenuItem.addActionListener(mainFrameListener);
		mainFrame.employeeMenuItem.addActionListener(mainFrameListener);
		mainFrame.deductionMenuItem.addActionListener(mainFrameListener);
		mainFrame.earningMenuItem.addActionListener(mainFrameListener);
		mainFrame.payrollMenuItem.addActionListener(mainFrameListener);	
		mainFrame.settingsMenuItem.addActionListener(mainFrameListener);
		
		//-------------------------------------
		
		mainFrame.homeMenuBtn.addActionListener(mainFrameListener);
		mainFrame.employeesMenuBtn.addActionListener(mainFrameListener);
		mainFrame.payrollMenuBtn.addActionListener(mainFrameListener);
		mainFrame.settingsMenuBtn.addActionListener(mainFrameListener);
		mainFrame.helpMenuBtn.addActionListener(mainFrameListener);
		
		
		//--------------------------------------
		mainFrame.userDropDownBtn.addActionListener(mainFrameListener);
		
		for(String key:mainFrame.userAccountOptionPanel.buttonList.keySet()){
			JButton btn=mainFrame.userAccountOptionPanel.buttonList.get(key);
			btn.addActionListener(mainFrameListener);
		}
	}
	
	/**
	 * Add listener to necessary components in Home View Panel
	 */
	private void addListenerToHomeViewPanel(){
		//--------------------------------------
		homeViewPanel.optionButton.addActionListener(homeViewPanelListener);
		//------------------------------------
		homeViewPanel.payrollModeDialog.selectButton.addActionListener(homeViewPanelListener);
		homeViewPanel.payrollModeDialog.regularModeBtn.addActionListener(homeViewPanelListener);
		homeViewPanel.payrollModeDialog.contractualModeBtn.addActionListener(homeViewPanelListener);
		
		//------------------------------------
		//--> Add listener to buttons in options.
		for(String key:homeViewPanel.optionPanel.buttonList.keySet()){
			JButton optionButton=homeViewPanel.optionPanel.buttonList.get(key);
			optionButton.addActionListener(homeViewPanelListener);
		}
	}
	
	/**
	 * Add listener to necessary components in Employee View Panel
	 */
	private void addListenerToEmployeeViewPanel(){
		employeeViewPanel.employeeOptionButton.addActionListener(employeeViewPanelListener);
		
		employeeViewPanel.filterTextField.getDocument().addDocumentListener(employeeViewPanelListener);
		employeeViewPanel.fixedTable.getSelectionModel().addListSelectionListener(employeeViewPanelListener);
		employeeViewPanel.dynamicTable.getSelectionModel().addListSelectionListener(employeeViewPanelListener);
		
		employeeViewPanel.fixedTable.addMouseListener(employeeViewPanelListener);
		employeeViewPanel.dynamicTable.addMouseListener(employeeViewPanelListener);
		
		employeeViewPanel.fixedTable.getTableHeader().addMouseListener(employeeViewPanelListener);
		employeeViewPanel.dynamicTable.getTableHeader().addMouseListener(employeeViewPanelListener);
		

		
		//--> Add listener to buttons in options.
		for(String key:employeeViewPanel.optionPanel.buttonList.keySet()){
			JButton optionButton=employeeViewPanel.optionPanel.buttonList.get(key);
			optionButton.addActionListener(employeeViewPanelListener);
		}

	}
	
	/**
	 * Add listener to necessary components in Connection Server Dialog
	 */
	private void addListenerToConnectionServerDialog(){
		connectToServerDialog.okButton.addActionListener(connectToServerDialogListener);
		connectToServerDialog.cancelButton.addActionListener(connectToServerDialogListener);
	}
	
	/**
	 * Add listener to necessary components in Login Frame
	 */
	private void addListenerToLoginFrame(){
		loginDialog.loginButton.addActionListener(loginFrameListener);
	}
	
	/**
	 * Add listener to necessary components in Add Employee Dialog
	 */
	private void addListenerToAddEmployeeDialog(){
		addEmployeeDialog.saveButton.addActionListener(addEmployeeListener);
		addEmployeeDialog.cancelButton.addActionListener(addEmployeeListener);
		addEmployeeDialog.contentPanel.generateEmployeeIDButton.addActionListener(addEmployeeListener);
		
	}
	
	/**
	 * Add listener to Delete Employee Dialog.
	 */
	private void addListenerToDeleteUpdateEmployeeDialog(){
		bothDeleteUpdateEmployeeDialog.cancelButton.addActionListener(deleteUpdateEmployeeListener);
		bothDeleteUpdateEmployeeDialog.deleteButton.addActionListener(deleteUpdateEmployeeListener);
		bothDeleteUpdateEmployeeDialog.updateButton.addActionListener(deleteUpdateEmployeeListener);
		bothDeleteUpdateEmployeeDialog.departmentComboBox.addActionListener(deleteUpdateEmployeeListener);
		bothDeleteUpdateEmployeeDialog.employeeListTable.getSelectionModel().addListSelectionListener(deleteUpdateEmployeeListener);
		
		bothDeleteUpdateEmployeeDialog.deleteWarningDialog.yesButton.addActionListener(deleteUpdateEmployeeListener);
		bothDeleteUpdateEmployeeDialog.deleteWarningDialog.noButton.addActionListener(deleteUpdateEmployeeListener);
		
	}
	
	/**
	 * Add listener to Update/Delete Employee Dialog components.
	 */
	private void addListenerToUpdateEmployeeDialog(){
		updateEmployeeDialog.saveButton.addActionListener(updateEmployeeDialigListener);
		updateEmployeeDialog.cancelButton.addActionListener(updateEmployeeDialigListener);
		updateEmployeeDialog.editButton.addActionListener(updateEmployeeDialigListener);
		updateEmployeeDialog.backButton.addActionListener(updateEmployeeDialigListener);
		
	}
	
	/**
	 * Add listeners to deduction view panel components.
	 */
	private void addListenerToEarningDeductionViewPanel(){
		EarningsAndDeductionLayout[] edList={EarningViewPanel.getInstance(),DeductionViewPanel.getInstance()};
		for(EarningsAndDeductionLayout ed: edList){
			bothEarningDeductionViewPanel=ed;
			
			bothEarningDeductionViewPanel.filterTextField.getDocument().addDocumentListener(earningDeductionViewPanelListener);
			
			
			bothEarningDeductionViewPanel.editBtn.addActionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.showEmployerShareBtn.addActionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.hideEmployerShareBtn.addActionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.calculateBtn.addActionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.retrievePrevValueBtn.addActionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.saveBtn.addActionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.cancelBtn.addActionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.backBtn.addActionListener(earningDeductionViewPanelListener);
	
			//--> Add listener to calculate buttons.
			for(String key:bothEarningDeductionViewPanel.calculationPanel.buttonList.keySet()){
				JButton calculationPanel=bothEarningDeductionViewPanel.calculationPanel.buttonList.get(key);
				calculationPanel.addActionListener(earningDeductionViewPanelListener);	
			}
			
			//--> Add listener to retrieve prev val buttons.
			for(String key:bothEarningDeductionViewPanel.retrievePrevValOptionalPanel.buttonList.keySet()){
				JButton btn=bothEarningDeductionViewPanel.retrievePrevValOptionalPanel.buttonList.get(key);
				btn.addActionListener(earningDeductionViewPanelListener);
			}
			
			bothEarningDeductionViewPanel.showBtn.addActionListener(earningDeductionViewPanelListener);
			
			
			bothEarningDeductionViewPanel.fullScreenTable.addMouseListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.fixedTable.addMouseListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.dynamicTable.addMouseListener(earningDeductionViewPanelListener);
			
			bothEarningDeductionViewPanel.fullScreenTable.getSelectionModel().addListSelectionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.dynamicTable.getSelectionModel().addListSelectionListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.fixedTable.getSelectionModel().addListSelectionListener(earningDeductionViewPanelListener);
			
			bothEarningDeductionViewPanel.fixedTable.getTableHeader().addMouseListener(earningDeductionViewPanelListener);
			bothEarningDeductionViewPanel.dynamicTable.getTableHeader().addMouseListener(earningDeductionViewPanelListener);
			
			
			bothEarningDeductionViewPanel.fixedTableScrollPane.getViewport().addChangeListener(earningDeductionViewPanelListener);
			
		}
		
		
	}
	
	
	
	
	/**
	 * Settings view panel
	 */
	private void addListenerToSettingsViewPanel(){
		settingsViewPanel.settingsOptionButton.addActionListener(settingsViewPanelListener);
		
		
	
		
		settingsViewPanel.addButton.addActionListener(settingsViewPanelListener);
		settingsViewPanel.editButton.addActionListener(settingsViewPanelListener);
		settingsViewPanel.deleteButton.addActionListener(settingsViewPanelListener);
		settingsViewPanel.saveButton.addActionListener(settingsViewPanelListener);
		settingsViewPanel.cancelButton.addActionListener(settingsViewPanelListener);
		
		
		settingsViewPanel.fullScreenTable.getSelectionModel().addListSelectionListener(settingsViewPanelListener);
		settingsViewPanel.fixedTable.getSelectionModel().addListSelectionListener(settingsViewPanelListener);
		settingsViewPanel.dynamicTable.getSelectionModel().addListSelectionListener(settingsViewPanelListener);
		
		settingsViewPanel.fullScreenTable.addMouseListener(settingsViewPanelListener);
		settingsViewPanel.fixedTable.addMouseListener(settingsViewPanelListener);
		settingsViewPanel.dynamicTable.addMouseListener(settingsViewPanelListener);
		
		settingsViewPanel.fixedTable.getTableHeader().addMouseListener(settingsViewPanelListener);
		settingsViewPanel.dynamicTable.getTableHeader().addMouseListener(settingsViewPanelListener);
		
		
		settingsViewPanel.edit15th30thTotalDialog.edit15th30thRadioBtn.addActionListener(settingsViewPanelListener);
		settingsViewPanel.edit15th30thTotalDialog.editTotalRadioBtn.addActionListener(settingsViewPanelListener);
		settingsViewPanel.edit15th30thTotalDialog.executeButton.addActionListener(settingsViewPanelListener);
		
	}

	/**
	 * Add listeners to components of Payroll View Panel.
	 */
	private void addListenerToPayrollViewPanel(){
		payrollViewPanel.filterTextField.getDocument().addDocumentListener(payrollViewPanelListener);
		payrollViewPanel.payrollOptionButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.pdfOptionButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.excelOptionButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.lockOptionButton.addActionListener(payrollViewPanelListener);
		//----------------------------------------------
		
		payrollViewPanel.addBtn.addActionListener(payrollViewPanelListener);
		payrollViewPanel.delBtn.addActionListener(payrollViewPanelListener);
		payrollViewPanel.showPayslipDialogBtn.addActionListener(payrollViewPanelListener);
		payrollViewPanel.netPayCopyModeBtn.addActionListener(payrollViewPanelListener);
		payrollViewPanel.cancelNetPayCopyModeBtn.addActionListener(payrollViewPanelListener);
		
		
		payrollViewPanel.addPayrollDateDialog.saveButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.addPayrollDateDialog.cancelButton.addActionListener(payrollViewPanelListener);
		//----------------------------------------------
		
		payrollViewPanel.payslipDataDialog.calculateBtn.addActionListener(payrollViewPanelListener);
		payrollViewPanel.payslipDataDialog.saveButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.payslipDataDialog.cancelButton.addActionListener(payrollViewPanelListener);
		
		//----------------------------------------------
		
		payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL.showButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL.cancelButton.addActionListener(payrollViewPanelListener);
		
		//----------------------------------------------
		
		payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.showButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.cancelButton.addActionListener(payrollViewPanelListener);
		
		//----------------------------------------------
		
		
		payrollViewPanel.fullScreenTable.getSelectionModel().addListSelectionListener(payrollViewPanelListener);
		payrollViewPanel.fullScreenTable.getTableHeader().addMouseListener(payrollViewPanelListener);
		payrollViewPanel.fullScreenTable.addMouseListener(payrollViewPanelListener);
		
		//----------------------------------------------
		
		//--> Add listener to options buttons
		for(String key:payrollViewPanel.optionPanel.buttonList.keySet()){
			JButton btn=payrollViewPanel.optionPanel.buttonList.get(key);
			btn.addActionListener(payrollViewPanelListener);
		}
		//----------------------------------------------
		
		
		
		//--> Add listener to EXCEL options buttons
		for(String key:payrollViewPanel.excelPanel.buttonList.keySet()){
			JButton btn=payrollViewPanel.excelPanel.buttonList.get(key);
			btn.addActionListener(payrollViewPanelListener);
		}
		//--------------------------------------------------
		
		payrollViewPanel.deletePayrollWarningDialog.yesButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.deletePayrollWarningDialog.noButton.addActionListener(payrollViewPanelListener);
		
		//----------------------------------------------
		payrollViewPanel.showAllEmpAddPayDaDialog.departmentComboBox.addActionListener(payrollViewPanelListener);
		payrollViewPanel.showAllEmpAddPayDaDialog.addButton.addActionListener(payrollViewPanelListener);
		
		//----------------------------------------------
		payrollViewPanel.addEDDataDialog.saveButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.addEDDataDialog.payrollDateCombobox.addActionListener(payrollViewPanelListener);
		
		//-------------------------------------------------
		
		payrollViewPanel.showBtn.addActionListener(payrollViewPanelListener);
		
		//----------------------------------------------------
		payrollViewPanel.editSignatureInfoDialog.saveButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.editSignatureInfoDialog.addSignatureImgBtn.addActionListener(payrollViewPanelListener);
		payrollViewPanel.editSignatureInfoDialog.clearSignatureImgBtn.addActionListener(payrollViewPanelListener);
		
		//----------------------------------------------------
		payrollViewPanel.lockedWarningDialog.yesButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.lockedWarningDialog.noButton.addActionListener(payrollViewPanelListener);
		
		//----------------------------------------------------
		payrollViewPanel.withOrWithoutATMDialog.selectButton.addActionListener(payrollViewPanelListener);
		payrollViewPanel.withOrWithoutATMDialog.withATMRadioBtn.addActionListener(payrollViewPanelListener);
		payrollViewPanel.withOrWithoutATMDialog.withoutATMRadioBtn.addActionListener(payrollViewPanelListener);
	
		//-----------------------------------------------------
		payrollViewPanel.addCommentsOnAllPayslipDialog.saveButton.addActionListener(payrollViewPanelListener);
	}
	
	
	
	/**
	 * Add listener to UI compionents in Help View Panel.
	 */
	private void addListenerToHelpViewPanel(){
		helpViewPanel.optionButton.addActionListener(helpViewPanelListener);
	}


	/**
	 * Add listener to UI components that needs an action when closed.
	 */
	private void addWindowListenerToComponents(){
		mainFrame.addWindowListener(this);
		loginDialog.addWindowListener(this);	
		homeViewPanel.payrollModeDialog.addWindowListener(this);
		
	}
	
	

	
	/**
	 * Add mnemonic key events to necessary UI components.
	 * This code must be accompany with action listener, meaning when the key are triggered or pressed,
	 * 		it will execute any code under the assign action listener of a certain UI component.
	 */
	private void addKeyboardShortcutMnemonicKeyEventsToComponents(){
		//--> BILECO LOGO MENU
		mainFrame.bilecoLogoButton.setMnemonic(KeyEvent.VK_Q);
		
		//---------------------------------------------------------
		//--> HOME VIEW MENU
		mainFrame.homeMenuItem.setMnemonic(KeyEvent.VK_H);
		KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.ALT_DOWN_MASK);
		mainFrame.homeMenuItem.setAccelerator(keyStrokeToOpen);
		
		HomeViewPanel.getInstance().optionButton.setMnemonic(KeyEvent.VK_X);
		
		//---------------------------------------------------------
		//--> EMPLOYEE VIEW MENU
		mainFrame.employeeMenuItem.setMnemonic(KeyEvent.VK_E);
		keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.ALT_DOWN_MASK);
		mainFrame.employeeMenuItem.setAccelerator(keyStrokeToOpen);
		
		EmployeeViewPanel.getInstance().employeeOptionButton.setMnemonic(KeyEvent.VK_X);

		//---------------------------------------------------------
		//--> DEDUCTION VIEW MENU
		mainFrame.deductionMenuItem.setMnemonic(KeyEvent.VK_D);
		keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.ALT_DOWN_MASK);
		mainFrame.deductionMenuItem.setAccelerator(keyStrokeToOpen);
		
		DeductionViewPanel.getInstance().showBtn.setMnemonic(KeyEvent.VK_W);
		
		//---------------------------------------------------------
		//--> EARNING VIEW MENU
		mainFrame.earningMenuItem.setMnemonic(KeyEvent.VK_A);
		keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK);
		mainFrame.earningMenuItem.setAccelerator(keyStrokeToOpen);
		
		EarningViewPanel.getInstance().showBtn.setMnemonic(KeyEvent.VK_W);
		
		//---------------------------------------------------------
		//--> PAYROLL VIEW MENU
		mainFrame.payrollMenuItem.setMnemonic(KeyEvent.VK_P);
		keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.ALT_DOWN_MASK);
		mainFrame.payrollMenuItem.setAccelerator(keyStrokeToOpen);
		
		payrollViewPanel.showBtn.setMnemonic(KeyEvent.VK_W);
		PayrollViewPanel.getInstance().payrollOptionButton.setMnemonic(KeyEvent.VK_X);
		PayrollViewPanel.getInstance().pdfOptionButton.setMnemonic(KeyEvent.VK_C);
		PayrollViewPanel.getInstance().excelOptionButton.setMnemonic(KeyEvent.VK_V);
		
		//---------------------------------------------------------
		//--> SETTINGS
		mainFrame.settingsMenuItem.setMnemonic(KeyEvent.VK_S);
		keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK);
		mainFrame.settingsMenuItem.setAccelerator(keyStrokeToOpen);
		
		SettingsViewPanel.getInstance().settingsOptionButton.setMnemonic(KeyEvent.VK_X);
	
	
		//---------------------------------------------------------
		//--> HELP VIEW
		HelpViewPanel.getInstance().optionButton.setMnemonic(KeyEvent.VK_X);
		
		
	}
	
	
	/**
	 * Add key listener to UI components that needs an action when certain keys are pressed.
	 */
	private void addKeyListenerToComponents(){
//		mainFrame.addKeyListener(this);
//		homeViewPanel.addKeyListener(this);
	}
	
	
	private void l______________________________________________________l(){}
	
	/**
	 * Add listeners to the UI that are initialized after selecting payroll system mode: Regular or Contractual
	 */
	public void addListenersThatAreNeededAfterSelectingPayrollSystemMode(){
		
		//--> EarningViewPanel: add listener to calculate buttons.
		for(String key:EarningViewPanel.getInstance().calculationPanel.buttonList.keySet()){
			JButton calculationPanel=EarningViewPanel.getInstance().calculationPanel.buttonList.get(key);
			calculationPanel.addActionListener(earningDeductionViewPanelListener);
		}
		
		//--> EarningViewPanel: Add listener to retrieve prev val buttons.
		for(String key:EarningViewPanel.getInstance().retrievePrevValOptionalPanel.buttonList.keySet()){
			JButton btn=EarningViewPanel.getInstance().retrievePrevValOptionalPanel.buttonList.get(key);
			btn.addActionListener(earningDeductionViewPanelListener);
		}
		
		//--> DeductionViewPanel: add listener to calculate buttons.
		for(String key:DeductionViewPanel.getInstance().calculationPanel.buttonList.keySet()){
			JButton calculationPanel=DeductionViewPanel.getInstance().calculationPanel.buttonList.get(key);
			calculationPanel.addActionListener(earningDeductionViewPanelListener);
		}
		
		//-->DeductionViewPanel: Add listener to retrieve prev val buttons.
		for(String key:DeductionViewPanel.getInstance().retrievePrevValOptionalPanel.buttonList.keySet()){
			JButton btn=DeductionViewPanel.getInstance().retrievePrevValOptionalPanel.buttonList.get(key);
			btn.addActionListener(earningDeductionViewPanelListener);
		}
		
		
		
		//--> PayrollViewPanel: add listener to calculate buttons..
			//--> Add listener to PDF options buttons
			for(String key:payrollViewPanel.pdfPanel.buttonList.keySet()){
				JButton btn=payrollViewPanel.pdfPanel.buttonList.get(key);
				btn.addActionListener(payrollViewPanelListener);
			}
			
			//--> Add listener to EXCEL options buttons
			for(String key:payrollViewPanel.excelPanel.buttonList.keySet()){
				JButton btn=payrollViewPanel.excelPanel.buttonList.get(key);
				btn.addActionListener(payrollViewPanelListener);
			}
				
				
		//--> SettingsViewPanel: add listener to option panel buttons.
		for(JButton btn:settingsViewPanel.optionPanel.buttonList.values()){
			if(btn!=settingsViewPanel.optionPanel.buttonList.get(Constant.DIVIDER_SETTING_BTN))
				btn.addActionListener(settingsViewPanelListener);
		}
	}
	
	
	/**
	 * Set all listener variables to null.
	 */
	public void setAllListenetsToNull(){
		mainFrameListener=null;
		homeViewPanelListener=null;
		employeeViewPanelListener=null;
		connectToServerDialogListener=null;
		loginFrameListener =null;
		addEmployeeListener=null;
		deleteUpdateEmployeeListener=null;
		updateEmployeeDialigListener=null;
		earningDeductionViewPanelListener=null;
		payrollViewPanelListener=null;
		settingsViewPanelListener =null;
		employeeViewPanelListener = null;
		helpViewPanelListener= null;
	}
	private void l______________________________________l(){}


	
	
	public ListenerHomeViewPanel getHomeViewPanelListener() {
		return homeViewPanelListener;
	}

	public static Controller getInstance(){
		if(instance==null ){
			instance=new Controller();
		}
		return instance;
	}
	
	
	public static void setInstanceToNull() {
		instance = null;
	}
	

//--------------------------------------------------------------------------------------------------------
	
	
	

	private void l________________________________________________________l(){}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		//--> WHen mainframe is closed
		if(e.getSource()==mainFrame){
			System.out.println("CLOSE FRAME-- Start"+CLASS_NAME);
			
			LoginFrame.getInstance().mainThread.stopThread();
			Database.getInstance().closeALLConnectionToDatabase();
			
			System.out.println("CLOSE FRAME--- Done"+CLASS_NAME);
			
			System.exit(0);
		}
		
		//--> When login frame is closed.
		else if(e.getSource()==loginDialog && !Utilities.getInstance().isLogin){
			System.out.println("Login and Frame is closing!"+CLASS_NAME);
			Database.getInstance().closeALLConnectionToDatabase();
			
			System.exit(0);
		}
		
		//--> When PAYROLL MODE DIALOG is closed.
		else if(e.getSource()==homeViewPanel.payrollModeDialog){
			System.exit(0);
		}
		
		
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

		
	}

}
