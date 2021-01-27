package testcase;

import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import model.Constant;
import model.statics.PDFCreator;
import model.view.DisplayOptionsDialog;

import org.junit.Test;

import controller.Controller;
import controller.listeners.ListenerHomeViewPanel;
import view.LoadingScreen;
import view.MainFrame;
import view.views.HomeViewPanel;
import view.views.PayrollViewPanel;
import database.Database;
public class PDFCreatorTestCase {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	public void init(){
		// Initialize Main UI
		LoadingScreen.getInstance().updateLoadingStatus("MainFrame initialize...");
		MainFrame.getInstance();
				
				
		// Initialize Controller
		Controller.getInstance();
				
		// Initialize Database
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
		
		
		
		//--> Update all
		ListenerHomeViewPanel listener = Controller.getInstance().getHomeViewPanelListener();
		HomeViewPanel.getInstance().payrollModeDialog.regularModeBtn.setSelected(true);
		listener.processWhenPayrollModeSelected();
		
		
		
	}
	

	@Test
	public void testPayrollOverallPDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		String strToCompare = "TSD";
		dialog.departmentComboBox.setSelectedIndex(1); 
		
		System.out.println("\nFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println("Fuck_1.0: dialog.departmentComboBox: "+dialog.departmentComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_PER_DEPARTMENT_PDF);
		
		assertEquals(strToCompare,dialog.departmentComboBox.getSelectedItem().toString() );
	}
	
	
}