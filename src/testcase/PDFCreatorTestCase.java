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
	public void testPayroll_PerDepartment_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		String strToCompare = "TSD";
		dialog.departmentComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println("\tFuck_1.0: dialog.departmentComboBox: "+dialog.departmentComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_PER_DEPARTMENT_PDF);
		
		assertEquals(strToCompare,dialog.departmentComboBox.getSelectedItem().toString() );
	}
	
	@Test
	public void testPayroll_Overall_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_OVERALL_PDF);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_ASEMCO_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_ASEMCO_PDF);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_BCCI_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_BCCI_PDF);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_OCCCI_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(2); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_OCCCI_PDF);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_DBP_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_DBP_PDF);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_CITY_SAVINGS_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_CITY_SAVINGS_PDF);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_ST_PETER_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_ST_PETER_PLAN_PDF);
		
		assertEquals(true,true );
	}
	
	

	@Test
	public void testPayroll_WTAX_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_W_TAX_PDF);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_LBP_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(2); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_LBP_PDF);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_SSSLOAN_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_SSS_LOAN_PDF);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_PAGIBIGLOAN_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(2); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_PAGIBIG_LOAN_PDF);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_UNIONDUES_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_UNION_DUES_PDF);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_SSSCONT_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\nPAYROLL_OVERALL_PDF"+CLASS_NAME);
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_SSS_CONT_PDF);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_HDMF_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_HDMF_PDF);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_MEDICARE_PDF() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		PDFCreator.getInstance().createPdf(fileNamePath, null,
				null,Constant.PAYROLL_MEDICARE_PDF);
		
		assertEquals(true,true );
	}
	
	
}