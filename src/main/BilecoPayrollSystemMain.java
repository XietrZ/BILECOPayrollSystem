package main;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import model.Constant;
import model.MainThread;
import model.statics.ExcelCreator;
import model.statics.FileBrowser;
import model.statics.Images;
import model.statics.Logs;
import model.statics.PDFCreator;
import model.statics.Utilities;
import controller.Controller;
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


public class BilecoPayrollSystemMain {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private void l____________________________________l(){}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				
//				
//			}
//		});
		
//			try {
//				UIManager.setLookAndFeel(new MetalLookAndFeel());
//			} catch (ClassNotFoundException | InstantiationException
//					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		
		//--> Start Loading Screen Thread.
		LoadingScreen loadingScreen=LoadingScreen.getInstance();
		Thread t=new Thread(loadingScreen);
		t.start();
		loadingScreen.showLoadingScreen();
		
		//-------------------------------------------
		try {
//		      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		      BilecoPayrollSystemMain.getInstance();
	    
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void l______________________________l(){}
	
	private static BilecoPayrollSystemMain instance=null;
	public BilecoPayrollSystemMain(){
		System.out.println(">>>>>>>> BilecoPayrollSystemMain initialize...."+CLASS_NAME);
		
		init();
	}
	
	public void init(){
		LoadingScreen loadingScreen=LoadingScreen.getInstance();
		
		
		
		// Initialize Images
		LoadingScreen.getInstance().updateLoadingStatus("Images initialize...");
		Images.getInstance();
		
		
		// Initialize Main UI
		LoadingScreen.getInstance().updateLoadingStatus("MainFrame initialize...");
		MainFrame.getInstance();
		
		
		// Initialize Controller
		LoadingScreen.getInstance().updateLoadingStatus("Controller initialize...");
		Controller.getInstance();

		
		// Initialize Database
		LoadingScreen.getInstance().updateLoadingStatus("Database initialize...");
		Database db=Database.getInstance();
		String databaseName=db.getDatabaseName(),//"bileco_db",
				hostName=db.getHostName(),//"localhost",
				portNumber=db.getPortNumber();//"3306";
		
		db.connectToDatabase(hostName, portNumber, databaseName);
		String systemVersionFromDatabase=db.getSystemVersion();
		if(!systemVersionFromDatabase.equals(Constant.PAYROLL_SYSTEM_VERSION)){
			MainFrame.getInstance().showOptionPaneMessageDialog("The system is outdated. Please update the Payroll System App.", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		
		//--> Initialize the user account table
		db.selectDataInDatabase(new String[]{db.tableNameUserAccount}, null,
				null,null, null,Constant.SELECT_ALL);
		
		// INitialize login page
		LoadingScreen.getInstance().updateLoadingStatus("Launching Program..."); 
		LoginFrame.getInstance();
		
		
		
		// Hide Loading screen
		loadingScreen.hideLoadingScreen();
		
		
		// Show Login
		LoginFrame.getInstance().setVisible(true);
	}

	private void l_______________________________________l(){}
	/**
	 * Set all static classes to NULL.
	 */
	private static void setAllStaticClassToNull(){
		Controller.setInstanceToNull();
		
		//-----------------------------------
		
		Database.setInstanceToNull();
		
		//-----------------------------------
		
		ExcelCreator.setInstanceToNull();
		FileBrowser.setInstanceToNull();
		Images.setInstanceToNull();
		PDFCreator.setInstanceToNull();
		Utilities.setInstanceToNull();
		Logs.setInstanceToNull();
		
		//-----------------------------------
		
		LoginFrame.getInstance().setInstanceToNull();
		MainFrame.setInstanceToNull();
		
		//-----------------------------------
		AddEmployeeDialog.setInstanceToNull();
		ConnectToServerDialog.setInstanceToNull();
		DeleteUpdateEmployeeDialog.setInstanceToNull();
		UpdateEmployeeDialog.setInstanceToNull();
		
		//-----------------------------------
		DeductionViewPanel.setInstanceToNull();
		EarningViewPanel.setInstanceToNull();
		EmployeeViewPanel.setInstanceToNull();
		HomeViewPanel.setInstanceToNull();
		PayrollViewPanel.setInstanceToNull();
		SettingsViewPanel.setInstanceToNull();
		HelpViewPanel.setInstanceToNull();
	}
	
	private void l_______________________________________________l(){}
	public static BilecoPayrollSystemMain getInstance(){
		if(instance==null){
			instance=new BilecoPayrollSystemMain();
		}
		return instance;
	}
	
	public static void setInstanceToNull(){
		System.out.println("\t Set ALL Instance To Null.... BEGIN\t\t\t\t BilecoPayrollSystemMain.java");
		
		setAllStaticClassToNull();
		
		System.gc();
		
		System.out.println("\t Set ALL Instance To Null.... DONE\t\t\t\t BilecoPayrollSystemMain.java");
		
		LoadingScreen.getInstance().updateLoadingStatus("Logging Out... Set All Instance To Null");
		
	}
}
