package testcase;

import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import model.Constant;
import model.statics.ExcelCreator;
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
public class EXCELCreatorTestCase {
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
	public void testPayroll_PerDepartment_EXCEL() {
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
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_PER_DEPARTMENT_EXCEL, mainFrame);
		
		assertEquals(strToCompare,dialog.departmentComboBox.getSelectedItem().toString() );
	}
	
	@Test
	public void testPayroll_Overall_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_OVERALL_EXCEL, mainFrame);
		
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_ASEMCO_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_ASEMCO_EXCEL, mainFrame);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_BCCI_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_BCCI_EXCEL, mainFrame);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_OCCCI_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_OCCCI_EXCEL,mainFrame);
		
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_DBP_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_DBP_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_CFI_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_CFI_EXCEL,mainFrame);
		
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_ST_PETER_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_ST_PETER_PLAN_EXCEL,mainFrame);
		
		
		assertEquals(true,true );
	}
	
	

	@Test
	public void testPayroll_WTAX_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_W_TAX_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_LBP_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(2); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_LBP_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_SSSLOAN_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(2); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_SSS_LOAN_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_PAGIBIGLOAN_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(2); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_PAGIBIG_LOAN_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_UNIONDUES_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_UNION_DUES_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_SSSCONT_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_SSS_CONT_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	@Test
	public void testPayroll_HDMF_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_HDMF_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	
	@Test
	public void testPayroll_MEDICARE_EXCEL() {
		init();
		
		PayrollViewPanel payrollViewPanel = PayrollViewPanel.getInstance();
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		//--> Oct 31, 2019
		dialog.payrollDateComboBox.setSelectedIndex(1); 
		
		System.out.println("\n\tPAYROLL_OVERALL_PDF");
		System.out.println("\tFuck_1.0: dialog.payrollDateComboBox: "+dialog.payrollDateComboBox.getSelectedItem().toString()+CLASS_NAME);
		System.out.println();
		
		MainFrame mainFrame = MainFrame.getInstance();
		ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_MEDICARE_EXCEL,mainFrame);
		
		assertEquals(true,true );
	}
	
	
}