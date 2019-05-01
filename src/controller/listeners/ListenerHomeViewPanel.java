package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controller.Controller;
import model.Constant;
import model.MainThread;
import model.OrderByInfo;
import model.statics.Images;
import model.statics.Utilities;
import model.view.PayrollSystemModeDialog;
import database.Database;
import view.LoadingScreen;
import view.LoginFrame;
import view.MainFrame;
import view.dialog.AddEmployeeDialog;
import view.dialog.ConnectToServerDialog;
import view.views.DeductionViewPanel;
import view.views.EarningViewPanel;
import view.views.HomeViewPanel;
import view.views.PayrollViewPanel;
import view.views.SettingsViewPanel;

public class ListenerHomeViewPanel implements ActionListener{
private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	private MainFrame mainFrame;
	private HomeViewPanel homeViewPanel;
	private EarningViewPanel earningViewPanel;
	private DeductionViewPanel deductionViewPanel;
	private SettingsViewPanel settingsViewPanel;
	private PayrollViewPanel payrollViewPanel;
	private LoginFrame loginFrame;
	private AddEmployeeDialog addEmployeeDialog;
	private void l_______________________________________________l(){}
	public  ListenerHomeViewPanel() {
		mainFrame=MainFrame.getInstance();
		homeViewPanel= HomeViewPanel.getInstance();
		earningViewPanel= EarningViewPanel.getInstance();
		deductionViewPanel = DeductionViewPanel.getInstance();
		payrollViewPanel= PayrollViewPanel.getInstance();
		settingsViewPanel= SettingsViewPanel.getInstance();
		
		
		loginFrame=LoginFrame.getInstance();
		addEmployeeDialog=AddEmployeeDialog.getInstance();
	}
	
	private void l_____________________________________________l(){}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource()==HomeViewPanel.getInstance().optionButton){
			homeViewPanel.optionPanel.setVisible(homeViewPanel.optionPanel.isVisible()?false:true);
		}
		else if(e.getSource()==homeViewPanel.payrollModeDialog.selectButton){
			
			processWhenPayrollModeSelected();
		}
		else if(e.getSource()==homeViewPanel.payrollModeDialog.regularModeBtn){
			PayrollSystemModeDialog payrollModeDialog=homeViewPanel.payrollModeDialog;
			
			//--> Set necessary UI components
			payrollModeDialog.regularModeBtn.setSelected(true);
			payrollModeDialog.contractualModeBtn.setSelected(false);
		}
		else if(e.getSource()==homeViewPanel.payrollModeDialog.contractualModeBtn){
			PayrollSystemModeDialog payrollModeDialog=homeViewPanel.payrollModeDialog;
			
			//--> Set necessary UI components
			payrollModeDialog.regularModeBtn.setSelected(false);
			payrollModeDialog.contractualModeBtn.setSelected(true);
		}
		//--> OPTION BUTTONS
		else{
		
			for(String key:homeViewPanel.optionPanel.buttonList.keySet()){
				JButton btn=homeViewPanel.optionPanel.buttonList.get(key);
				if(e.getSource()==btn){
					
					//--> Set necessary UI components.
					homeViewPanel.optionPanel.setVisible(false);
					
					
					switch(key){
					
						//--> CONNECT TO SERVER
						case Constant.CONNECT_TO_SERVER:{
							processConnectToServer();
							break;
						}
						
						//--> CONNECTIVITY PARAMETERS
						case Constant.CONNECTIVITY_PARAMETERS:{
							mainFrame.showOptionPaneMessageDialog("Coming Soon!", JOptionPane.ERROR_MESSAGE);
							
//							Database db = Database.getInstance();
//							String table= "temp";
//							Utilities util= Utilities.getInstance();
//							
//							private String selectAll(String[] tableNameList,OrderByInfo orderInfo){
//							db.selectDataInDatabase(
//									new String[]{table},
//									null,
//									null,
//									null,
//									new OrderByInfo(new String[]{"LastName"}, "ASC"),
//									Constant.SELECT_ALL
//							);
//							
//							try {
//								db.resultSet.last();
//								int numOfRows=db.resultSet.getRow();
//								db.resultSet.first();
//								for(int i=1;i<=numOfRows;i++){
//									db.resultSet.absolute(i);
//									
//									System.out.println(
//										util.getContractualEmployeeID(
//											db.resultSet.getObject(5).toString(),
//											db.resultSet.getObject(3).toString(),
//											db.resultSet.getObject(4).toString(),
//											db.resultSet.getObject(2).toString()
//										)
//									);
//								}
//								
//							} catch (SQLException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
							
							
							break;
						}
						
						//--> MANAGE ACCOUNTS
						case Constant.MANAGE_ACCOUNTS_BTN:{
							mainFrame.showOptionPaneMessageDialog("Coming Soon!", JOptionPane.ERROR_MESSAGE);
							
							Database.getInstance().closeALLConnectionToDatabase();
							break;
						}
						
						
						default:
							break;
					}
					
					
					
					break;
				}
			}
		}

				
	}
	private void l__________________________________________________l(){}
	
	/**
	 * Connect to Server Process.
	 */
	private void processConnectToServer(){
		System.out.println(THIS_CLASS_NAME+"Connect To Server Process"+CLASS_NAME);
		
		//--> ReConnecting process.
		LoginFrame.getInstance().mainThread.isReconnect=true;
		
		if(LoginFrame.getInstance().mainThread.isPause){
			LoginFrame.getInstance().mainThread.resumeThread();
		}
	}
	
	/**
	 * Process when select button is clicked
	 */
	private void processWhenPayrollModeSelected(){
		System.out.println(THIS_CLASS_NAME+"Payroll Mode Selected!"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Images img = Images.getInstance();
		Utilities util = Utilities.getInstance();
		
		String databaseName=db.getDatabaseName(),//"bileco_db",
				hostName=db.getHostName(),//"localhost",
				portNumber=db.getPortNumber();//"3306";
		
		//---> REGULAR
		if(homeViewPanel.payrollModeDialog.regularModeBtn.isSelected()){
			mainFrame.setTitle(mainFrame.getTitle()+" Regular v"+util.getVersionNumber());
			homeViewPanel.lblWelcomeToBileco.setIcon(img.homeWelcomeMsgRegularImg);
			AddEmployeeDialog.getInstance().contentPanel.hiresAsComboBox.setSelectedIndex(1);
			util.payrollSystemMode=Constant.PAYROLL_SYSTEM_MODE_REGULAR;
			
			earningViewPanel.setCalculationPanelRegular();
			deductionViewPanel.setCalculationPanelRegular();
			payrollViewPanel.setPDFPanelComponentsRegular();
			payrollViewPanel.setExcelPanelComponentsRegular();
			settingsViewPanel.setRegularOptionPanelComponents();
			
		}
		//---> CONTRACTUAL
		else{
			mainFrame.setTitle(mainFrame.getTitle()+" Contractual v"+util.getVersionNumber());
			homeViewPanel.lblWelcomeToBileco.setIcon(img.homeWelcomeMsgContractualImg);
			AddEmployeeDialog.getInstance().contentPanel.hiresAsComboBox.setSelectedIndex(2);
			util.payrollSystemMode=Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL;
			
			earningViewPanel.setCalculationPanelContractual();
			deductionViewPanel.setCalculationPanelContractual();
			payrollViewPanel.setPDFPanelComponentsContractual();
			payrollViewPanel.setExcelPanelComponentsContractual();
			settingsViewPanel.setContractualOptionPanelComponents();
			
		}
		
		//--> Add listeners to needed UI componnts after selecting payroll system mode: REGULAR or CONTRACTUAL
		Controller.getInstance().addListenersThatAreNeededAfterSelectingPayrollSystemMode();
		
		//--> Dispose Dialog
		homeViewPanel.payrollModeDialog.setVisible(false);
		
		//--> Payroll Slip Set Name and Signature 
		payrollViewPanel.payrollSlip.setPreparedByNameAndSignature();
		payrollViewPanel.payslipDataDialog.payslipPanel.setPreparedByNameAndSignature();
		
		
		//--> Set the signature table data depending if chosen is REGULAR or CONTRACTUAL
		db.initializeSignatureTabletData();
		
		//--> Set resultset null.
		Database.getInstance().resultSet=null;
		
		
		//--> If its isconnected variable is true, update status label.
		if(Database.getInstance().isConnected){
			
			homeViewPanel.updateConnectionStatusField("ONLINE");
			homeViewPanel.updateDatabaseLabel(databaseName);
			homeViewPanel.updateHostNameField(hostName);
			homeViewPanel.updatePortNumberLabel(portNumber);
			
			//---------------------------------------------------------
			
			System.out.println("\t DATABASE STATUS: "+CLASS_NAME);
			System.out.println("\t\tDatabase Name: "+databaseName
					+"\t HostName: "+hostName
					+"\tPort Number: "+portNumber
					+"\t UserName: "+db.getUserName()
					//+"\t Password: "+db.getPassword()
					+"\t Authorization Level: "+Utilities.getInstance().authorizationLevel
					+CLASS_NAME);
		}
		
		//---> Update username label
		Utilities.getInstance().updateUserNameLabel(mainFrame,
				Images.getInstance(),
				loginFrame.userNameTextfield.getText());
		
		
		//--> Initialize main thread.
		loginFrame.mainThread=new MainThread();
		loginFrame.mainThread.start();
		
		
		//--> Add the swing components of payslip layout since we cannot add this before the connection of 
		//		database is established since the data are based on earning and deduction columns from database.
		payrollViewPanel.payrollSlip.addDeductionAndEarningSwingComponents(Database.getInstance(),util);

		
		//--> Update all UI comboboxes
		Utilities.getInstance().updateAllUIDepartmentComboboxes(db);
		Utilities.getInstance().updateAllUIPayrollDateComoboxes(db);
		
		//--> Set sample input in add employee dialog
		addEmployeeDialog.contentPanel.sampleDefaultInput(db);
		

	}
	
}
