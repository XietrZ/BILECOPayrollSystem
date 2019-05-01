package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Controller;
import main.BilecoPayrollSystemMain;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;
import model.view.AddEarningOrDeductionDataLayout;
import model.view.EarningsAndDeductionLayout;
import view.LoadingScreen;
import view.LoginFrame;
import view.MainFrame;
import view.dialog.ConnectToServerDialog;
import view.views.DeductionViewPanel;
import view.views.EarningViewPanel;
import view.views.EmployeeViewPanel;
import view.views.HelpViewPanel;
import view.views.HomeViewPanel;
import view.views.PayrollViewPanel;
import view.views.SettingsViewPanel;
import database.Database;

public class ListenerMainFrame implements ActionListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private MainFrame mainFrame;
	private ConnectToServerDialog connectToServerDialog;
	private EarningsAndDeductionLayout bothEarningDeductionViewPanel;
	
	private ListenerEarningDeductionViewPanel earningDeductionViewPanelListener;
	
	private void l_______________________________________________l(){}
	public  ListenerMainFrame() {
		connectToServerDialog=ConnectToServerDialog.getInstance();
		mainFrame=MainFrame.getInstance();
	}
	
	private void l_____________________________________________l(){}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//--> This checks if the earning./dedcution view is on edit mode, if it is then it cant go to other views
		if(!Utilities.getInstance().isEdit){
			
			// TODO Auto-generated method stub
			//--> SHOW MENU when click BILECO LOGO
			if(e.getSource()==mainFrame.bilecoLogoButton){
				processBilecoLogo();
			}
				
			//--------------------------------------------------------------------------------------------
			
			//--> CONNECT TO SERVER
			else if(e.getSource()==mainFrame.connectToServerMenuItem){
				System.out.println(THIS_CLASS_NAME+"Connect to server menu item."+CLASS_NAME);
				
				connectToServerDialog.setVisible(true);
			}
			
			//--> CHECK CONNECTION
			else if(e.getSource()==mainFrame.checkConnectionMenuItem){
				System.out.println(THIS_CLASS_NAME+"Check Connection."+CLASS_NAME);
				
			}
			
			//--------------------------------------------------------------------------------------------
			
			//--> HOME VIEW
			else if(e.getSource()==mainFrame.homeMenuItem){
				processHomeView();
			}
			else if(e.getSource()==mainFrame.homeMenuBtn){
				processHomeView();
			}
			
			//--> EMPLOYEE VIEW
			else if(e.getSource()==mainFrame.employeeMenuItem){
				processEmployeelView();	
			}
			else if(e.getSource()==mainFrame.employeesMenuBtn){
				processEmployeelView();	
			}
			
			
			//--> DEDUCTION VIEW
			else if(e.getSource()==mainFrame.deductionMenuItem){
					processDeductionView();	
			}
			
			//--> EARNING VIEW
			else if(e.getSource()==mainFrame.earningMenuItem){
					processEarningsView();
			}
			
			
			//--> PAYROLL VIEW
			else if(e.getSource()==mainFrame.payrollMenuItem){
				processPayrollView();
			}
			else if(e.getSource()==mainFrame.payrollMenuBtn){
				processPayrollView();
			}
			
			//--> SETTINGS VIEW
			else if(e.getSource()==mainFrame.settingsMenuItem){
				processSettingsView();
			}
			else if(e.getSource()==mainFrame.settingsMenuBtn){
				processSettingsView();
			}
			
			//--> HELP VIEW
			else if(e.getSource()==mainFrame.helpMenuBtn){
				processHelpView();
			}
			
			
			
			//--------------------------------------------------------------------------------------------
			//--> User Account Drop DoWN Button
			else if(e.getSource()==mainFrame.userDropDownBtn){
				mainFrame.userAccountOptionPanel.setVisible((mainFrame.userAccountOptionPanel.isVisible())?false:true);
			}
			
			else{
				for(String key:mainFrame.userAccountOptionPanel.buttonList.keySet()){
					JButton btn=mainFrame.userAccountOptionPanel.buttonList.get(key);
					
					if(e.getSource()==btn){
						switch(key){
							case Constant.ACCOUNT_PREFEREBCES_BTN:{
								mainFrame.showOptionPaneMessageDialog("Not Yet Implemented. Sorry.", JOptionPane.ERROR_MESSAGE);
								break;
							}
							case Constant.LOGOUT_BTN:{
								processLogoutButton();
								break;
							}
						}
						break;
					}
				}
			}
		}
		else{
			mainFrame.showOptionPaneMessageDialog("You cannot go to other view in edit mode. Please cancel if you want to go.", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		
	}
	
	
	private void l___________________________________________l(){}
	/**
	 * Execute process that will happen when you click BILECO Logo
	 */
	private void processBilecoLogo(){
		mainFrame.menuPanel.setVisible((mainFrame.menuPanel.isVisible()?false:true));
	}
	/**
	 * Execute the process that will happen when you click the Home View
	 */
	public void processHomeView(){
		System.out.println(THIS_CLASS_NAME+"Show Home!"+CLASS_NAME);
	
		
		//--> Set Visible necessary UI components
		boolean isVisible=true;
		HomeViewPanel.getInstance().setVisible(isVisible);
		isVisible=false;
		EmployeeViewPanel.getInstance().setVisible(isVisible);
		DeductionViewPanel.getInstance().setVisible(isVisible);
		EarningViewPanel.getInstance().setVisible(isVisible);
		PayrollViewPanel.getInstance().setVisible(isVisible);
		SettingsViewPanel.getInstance().setVisible(isVisible);
		HelpViewPanel.getInstance().setVisible(isVisible);
		
		mainFrame.menuPanel.setVisible(isVisible);
		HomeViewPanel.getInstance().optionPanel.setVisible(isVisible);
		
	}
	/**
	 * Execute the process that will happen when you click the Employee View
	 */
	private void processEmployeelView(){
		System.out.println(THIS_CLASS_NAME+"Show Employees!"+CLASS_NAME);
		
		//--> Clear table .
		EmployeeViewPanel.getInstance().fixedTable.clearAllContentsOfTable();
		EmployeeViewPanel.getInstance().dynamicTable.clearAllContentsOfTable();
				
		//--> Update Row Count Label
		EmployeeViewPanel.getInstance().rowCountLabel.setText("Row Count: 0");
				
				
		//--> Set Visible necessary UI components
		boolean isVisible=true;
		EmployeeViewPanel.getInstance().setVisible(isVisible);
		isVisible=false;
		HomeViewPanel.getInstance().setVisible(isVisible);
		DeductionViewPanel.getInstance().setVisible(isVisible);
		EarningViewPanel.getInstance().setVisible(isVisible);
		PayrollViewPanel.getInstance().setVisible(isVisible);
		SettingsViewPanel.getInstance().setVisible(isVisible);
		HelpViewPanel.getInstance().setVisible(isVisible);
		
		mainFrame.menuPanel.setVisible(isVisible);
		EmployeeViewPanel.getInstance().optionPanel.setVisible(isVisible);
		
		
		
	
	}
	/**
	 * Execute the process that will happen when you click the Earnings View
	 */
	public void processEarningsView(){
		System.out.println(THIS_CLASS_NAME+"Show Earnings!"+CLASS_NAME);
		//--> Clear table .
		EarningViewPanel.getInstance().fixedTable.clearAllContentsOfTable();
		EarningViewPanel.getInstance().dynamicTable.clearAllContentsOfTable();
		EarningViewPanel.getInstance().fullScreenTable.clearAllContentsOfTable();
		EarningViewPanel.getInstance().totalFullScreenTable.clearAllContentsOfTable();
				
		//--> Update Row Count Label
		EarningViewPanel.getInstance().rowCountLabel.setText("Row Count: 0");
				
				
		//--> Set Visible necessary UI components
		boolean isVisible=true;
		EarningViewPanel.getInstance().setVisible(isVisible);
		
		isVisible=false;
		HomeViewPanel.getInstance().setVisible(isVisible);
		EmployeeViewPanel.getInstance().setVisible(isVisible);
		DeductionViewPanel.getInstance().setVisible(isVisible);
		PayrollViewPanel.getInstance().setVisible(isVisible);
		SettingsViewPanel.getInstance().setVisible(isVisible);
		HelpViewPanel.getInstance().setVisible(isVisible);
		
		
		//--> Assign if earning/deduction
		bothEarningDeductionViewPanel=EarningViewPanel.getInstance();
		earningDeductionViewPanelListener.setIfDeductionOrEarning(bothEarningDeductionViewPanel);
		
		bothEarningDeductionViewPanel.editBtn.setVisible(isVisible);
		
		
		//--> Set/Update the content of comboboxes: earning or deduction ColumnCombobox 
		Database db= Database.getInstance();
		Utilities util= Utilities.getInstance();
		earningDeductionViewPanelListener.table=null;
		bothEarningDeductionViewPanel.setBothDeductionEarningColumnComboBoxItemValues(
				db,
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
						db.earningTableColumnNames:
						db.earningsContractualColumnNames	
		);

	
	}
	/**
	 * Execute the process that will happen when you click the Deduction View
	 */
	public void processDeductionView(){
		System.out.println(THIS_CLASS_NAME+"Show Deductions!"+CLASS_NAME);
		
		//--> Clear table .
		DeductionViewPanel.getInstance().fixedTable.clearAllContentsOfTable();
		DeductionViewPanel.getInstance().dynamicTable.clearAllContentsOfTable();
		DeductionViewPanel.getInstance().fullScreenTable.clearAllContentsOfTable();	
		DeductionViewPanel.getInstance().totalFullScreenTable.clearAllContentsOfTable();	
		
		
		//--> Update Row Count Label
		DeductionViewPanel.getInstance().rowCountLabel.setText("Row Count: 0");
				
		
		
		//--> Set Visible necessary UI components
		boolean isVisible=true;
		DeductionViewPanel.getInstance().setVisible(isVisible);
		
		isVisible=false;
		HomeViewPanel.getInstance().setVisible(isVisible);
		EmployeeViewPanel.getInstance().setVisible(isVisible);
		EarningViewPanel.getInstance().setVisible(isVisible);
		PayrollViewPanel.getInstance().setVisible(isVisible);
		SettingsViewPanel.getInstance().setVisible(isVisible);
		HelpViewPanel.getInstance().setVisible(isVisible);
		
		//--> Assign if earning/deduction
		bothEarningDeductionViewPanel=DeductionViewPanel.getInstance();
		earningDeductionViewPanelListener.setIfDeductionOrEarning(bothEarningDeductionViewPanel);

		bothEarningDeductionViewPanel.editBtn.setVisible(isVisible);
		bothEarningDeductionViewPanel.showEmployerShareBtn.setVisible(isVisible);
		bothEarningDeductionViewPanel.hideEmployerShareBtn.setVisible(isVisible);
		
		//--> Update the earning/deduction three comboboxes.
		
		//--> Set/Update the content of comboboxes: earning or deduction ColumnCombobox 
		Database db= Database.getInstance();
		Utilities util = Utilities.getInstance();
		earningDeductionViewPanelListener.table=null;
		bothEarningDeductionViewPanel.setBothDeductionEarningColumnComboBoxItemValues(
			db,
			(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.deductionTableColumnNamesNoComment:db.deductionsContractualColumnNamesNoComment
		);
			bothEarningDeductionViewPanel.isUpdateColumnComboBox=false;
		
	
	}
	
	/**
	 * Execute the process that will happen when you click the Payroll View
	 */
	public void processPayrollView(){
		System.out.println(THIS_CLASS_NAME+"Payroll View!"+CLASS_NAME);
		
//			FileBrowser.getInstance().savePDFFile();
		
		//--> Clear table .
		PayrollViewPanel.getInstance().fullScreenTable.clearAllContentsOfTable();
		PayrollViewPanel.getInstance().totalFullScreenTable.clearAllContentsOfTable();
		
		//--> Update Row Count Label
		PayrollViewPanel.getInstance().rowCountLabel.setText("Row Count: 0");
		
		//--> Set visible for necessary UI components
		boolean isVisible=true;
		PayrollViewPanel.getInstance().setVisible(isVisible);
		PayrollViewPanel.getInstance().lockOptionButton.setVisible(isVisible);
		
		isVisible=false;
		EarningViewPanel.getInstance().setVisible(isVisible);
		HomeViewPanel.getInstance().setVisible(isVisible);
		EmployeeViewPanel.getInstance().setVisible(isVisible);
		DeductionViewPanel.getInstance().setVisible(isVisible);
		SettingsViewPanel.getInstance().setVisible(isVisible);
		HelpViewPanel.getInstance().setVisible(isVisible);
		
		mainFrame.menuPanel.setVisible(isVisible);
		PayrollViewPanel.getInstance().optionPanel.setVisible(isVisible);
		PayrollViewPanel.getInstance().topRightButtonPanel.setVisible(isVisible);
		PayrollViewPanel.getInstance().payrollDateDataLabel.setIcon(null);
	}
	
	
	
	/**
	 * Execute process that will happen when you click the Settings View
	 */
	private void processSettingsView(){
		System.out.println(THIS_CLASS_NAME+"Settings View!"+CLASS_NAME);
		
		//--> Clear table .
		SettingsViewPanel.getInstance().fixedTable.clearAllContentsOfTable();
		SettingsViewPanel.getInstance().dynamicTable.clearAllContentsOfTable();
		SettingsViewPanel.getInstance().fullScreenTable.clearAllContentsOfTable();		
		
		
		//--> Update Row Count Label
		SettingsViewPanel.getInstance().rowCountLabel.setText("Row Count: 0");
				
		//--> Set Option Title to null
		SettingsViewPanel.getInstance().optionSettingTitleLabel.setIcon(null);
				
		//--> Set Visible necessary UI components
		boolean isVisible=true;
		SettingsViewPanel.getInstance().setVisible(isVisible);

		isVisible=false;
		HomeViewPanel.getInstance().setVisible(isVisible);
		EmployeeViewPanel.getInstance().setVisible(isVisible);
		DeductionViewPanel.getInstance().setVisible(isVisible);
		EarningViewPanel.getInstance().setVisible(isVisible);
		PayrollViewPanel.getInstance().setVisible(isVisible);
		HelpViewPanel.getInstance().setVisible(isVisible);
		
		
		mainFrame.menuPanel.setVisible(isVisible);
		SettingsViewPanel.getInstance().setAddEditDeleteBtnVisible(isVisible);
		SettingsViewPanel.getInstance().setSaveCancelBtnVisible(isVisible);
		SettingsViewPanel.getInstance().optionPanel.setVisible(isVisible);

	}
	
	/**
	 * Execute process that will happen when click the help button.
	 */
	private void processHelpView(){
		System.out.println(THIS_CLASS_NAME+"Show Home!"+CLASS_NAME);
	
		
		//--> Set Visible necessary UI components
		boolean isVisible=true;
		HelpViewPanel.getInstance().setVisible(isVisible);
		
		
		isVisible=false;
		EmployeeViewPanel.getInstance().setVisible(isVisible);
		DeductionViewPanel.getInstance().setVisible(isVisible);
		EarningViewPanel.getInstance().setVisible(isVisible);
		PayrollViewPanel.getInstance().setVisible(isVisible);
		SettingsViewPanel.getInstance().setVisible(isVisible);
		HomeViewPanel.getInstance().setVisible(isVisible);
		
		
		mainFrame.menuPanel.setVisible(isVisible);
	}
	/**
	 * Execute the process that will happen when you click logout button.
	 */
	private void processLogoutButton(){
		System.out.println(THIS_CLASS_NAME+"Logout Account!"+CLASS_NAME);
		
		
		
		LoginFrame.getInstance().mainThread.stopThread();
		
		mainFrame.setVisible(false);
		LoadingScreen.getInstance().isLogout=true;
		LoadingScreen.getInstance().showLoadingScreen();
		LoadingScreen.getInstance().updateLoadingStatus("Logging out..."); 

	}
	
//	private void l________________________________________l(){}
	
	public void setters(ListenerEarningDeductionViewPanel earningDeductionViewPanelListener){
		this.earningDeductionViewPanelListener = earningDeductionViewPanelListener;
	}
}