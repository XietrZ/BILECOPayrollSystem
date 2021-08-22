package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.itextpdf.text.Image;

import model.Constant;
import model.MultipleUpdateDatabaseModel;
import model.OrderByInfo;
import model.PayrollTableModel;
import model.PayslipDataStorageInfo;
import model.SelectConditionInfo;
import model.SssInfo;
import model.statics.ExcelCreator;
import model.statics.Images;
import model.statics.PDFCreator;
import model.statics.Utilities;
import model.view.AddEarningOrDeductionDataLayout;
import model.view.AddPayrollDateDialogLayout;
import model.view.DisplayOptionsDialog;
import model.view.EditSignatureDataPanel;
import model.view.EditSignatureInfoDialog;
import model.view.PayrollSlipLayout;
import model.view.PayslipDataDialog;
import model.view.ReusableTable;
import model.view.ShowAllEmployeeAddPayrollDataDialogLayout;
import model.view.WithOrWithoutATMDialog;
import model.view.YesOrNoIfDeleteDialogLayout;
import view.MainFrame;
import view.views.EarningViewPanel;
import view.views.EmployeeViewPanel;
import view.views.HomeViewPanel;
import view.views.PayrollViewPanel;
import database.Database;

public class ListenerPayrollViewPanel implements ActionListener,ListSelectionListener,MouseListener,DocumentListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	private ArrayList<SelectConditionInfo>conditionColumnAndValueList,
		conditionColumnAndValueList_Deduction, conditionColumnAndValueList_Earning;
	private boolean canDisplayPayslipPDF=false;
	
	private PayrollViewPanel payrollViewPanel;
	private HomeViewPanel homeViewPanel;
	private MainFrame mainFrame;
	private ListenerMainFrame mainFrameListener=null;
	private String payrollViewMode=null;
	private String payrollDispPDFEXCELBtnClicked=""; // Used in all display PDF excepT the show employee data pdf
	private String alreadyChosenPayrollDateComboboxAddPayrollDATA="";

	private void l__________________________________________________________l(){}
	
	public ListenerPayrollViewPanel() {
		conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList_Earning=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList_Deduction=new ArrayList<SelectConditionInfo>();
		
		payrollViewPanel=PayrollViewPanel.getInstance();
		homeViewPanel=HomeViewPanel.getInstance();
		mainFrame=MainFrame.getInstance();
		
	}
	private void l________________________________________________________l(){}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//--> Check the disconnection status of database.
		Database.getInstance().checkIfDisconnectedToDatabase();
		
		//--> Only Process When Connected.
		if(Database.getInstance().isConnected){
		
			// TODO Auto-generated method stub
			//--> SHOW OPTION PANEL
			if(e.getSource()==payrollViewPanel.payrollOptionButton){
				//--> Set necessary UI components.
				payrollViewPanel.optionPanel.setVisible((payrollViewPanel.optionPanel.isVisible()?false:true));
				
				payrollViewPanel.pdfPanel.setVisible(false);
				payrollViewPanel.excelPanel.setVisible(false);
				mainFrame.menuPanel.setVisible(false);
			}
			
			
			//--> OPTION BUTTONS
			else{
			
				for(String key:payrollViewPanel.optionPanel.buttonList.keySet()){
					JButton btn=payrollViewPanel.optionPanel.buttonList.get(key);
					if(e.getSource()==btn){
						
						//--> Set necessary UI components.
						payrollViewPanel.optionPanel.setVisible(false);
						
						
						switch(key){
							//--> DEDUCTION VIEW
							case Constant.VIEW_DEDUCTION_DATA:{
								mainFrameListener.processDeductionView();
								
								break;
							}
							//--> EARNING VIEW
							case Constant.VIEW_EARNING_DATA:{
								mainFrameListener.processEarningsView();
							
								break;
							}			
							//--> SHOW PAYROLL DATE
							case Constant.SHOW_PAYROLL_DATE:{
								canDisplayPayslipPDF=false;
								processShowPayrollDate();
								break;
							}
							//--> EDIT SIGNATURE INFO 
							case Constant.EDIT_SIGNATURE_INFO:{
								canDisplayPayslipPDF=false;
								processEditSignatureInfo();
								break;
							}
							
							case Constant.ADD_COMMENTS_ON_PAYSLIP:{
								canDisplayPayslipPDF=false;
								processAddCommentsOnPayslip();
								break;
							}
							
							default:
								break;
						}
						
						
						
						break;
					}
				}
			}
			
			
			if(e.getSource()==payrollViewPanel.pdfOptionButton){
				System.out.println(THIS_CLASS_NAME+"PDF Option Button is clicked!"+CLASS_NAME);
				
				//--> Set necessary UI components.
				payrollViewPanel.pdfPanel.setVisible((payrollViewPanel.pdfPanel.isVisible()?false:true));
				
				payrollViewPanel.optionPanel.setVisible(false);
				payrollViewPanel.excelPanel.setVisible(false);
				mainFrame.menuPanel.setVisible(false);
				
			}
			//--> PDF OPTION BUTTONS
			else{
				processOptionButtonsPDF(e);
				
			}
			
			
			if(e.getSource()==payrollViewPanel.excelOptionButton){
				System.out.println(THIS_CLASS_NAME+"EXCEL Option Button is clicked!"+CLASS_NAME);
				
				//--> Set necessary UI components.
				payrollViewPanel.excelPanel.setVisible((payrollViewPanel.excelPanel.isVisible()?false:true));
				
				payrollViewPanel.optionPanel.setVisible(false);
				payrollViewPanel.pdfPanel.setVisible(false);
				mainFrame.menuPanel.setVisible(false);
				
			}
			//--> EXCEL OPTION BUTTONS
			else{
				
				processOptionButtonsEXCEL(e);
				
			}
			
			//--> LOCK OPTION 
			if(e.getSource()==payrollViewPanel.lockOptionButton){
				processLockData();
				
				
			}
			
			//--------------------------------------------------------------------------------------
			
			//--> ADD PAYROLL  DATA/DATE
			if(e.getSource()==payrollViewPanel.addBtn){
				
				Utilities util= Utilities.getInstance();
				if(util.authorizationLevel==Constant.ADMIN_AUTHORIZATION_LEVEL ||
						util.authorizationLevel==Constant.USER_AUTHORIZATION_LEVEL){
					
					// When Payroll DATE
					if(payrollViewMode.equals(Constant.SHOW_PAYROLL_DATE)){
						payrollViewPanel.addPayrollDateDialog.setVisible(true);
					}
					// When Payroll DATA
					else{
						processShowAllEmployeeDialog();
					}
				}
				else{
					MainFrame.getInstance().showOptionPaneMessageDialog("You are not authorized to use this. Please contact your administrator.", JOptionPane.ERROR_MESSAGE);
				}
			}
			//--> DELETE PAYROLL DATA/DATE
			else if(e.getSource()==payrollViewPanel.delBtn){
				Database db = Database.getInstance();
				Utilities util=Utilities.getInstance();
				ReusableTable table=payrollViewPanel.fullScreenTable;
				int [] selectedTableRowIndex=util.convertRowIndexToModel(table, table.getSelectedRows());
				
				JComboBox<String> comboBOx=payrollViewPanel.payrollDateComboBox;
				YesOrNoIfDeleteDialogLayout dialog=payrollViewPanel.deletePayrollWarningDialog;
				
				if(selectedTableRowIndex!=null && selectedTableRowIndex.length>0){
					//--a> Repaint to avoid overlap
					payrollViewPanel.repaint();
					
					boolean isCond=false;
					if(util.authorizationLevel==Constant.ADMIN_AUTHORIZATION_LEVEL ||
							util.authorizationLevel==Constant.USER_AUTHORIZATION_LEVEL){
						isCond=true;
						
						String []payrollDateList=new String[selectedTableRowIndex.length];
						for(int i=0;i<payrollDateList.length;i++){
							int selectedRow=selectedTableRowIndex[i];
							payrollDateList[i]=table.getModel().getValueAt(selectedRow,0).toString();
						}
						
						boolean temp =db.isPayrollDateLocked(payrollDateList);
						System.out.println("\t FFUUCCKK  IsThereASelectedLock: "+temp+CLASS_NAME);
						
						
						if ((util.authorizationLevel==Constant.USER_AUTHORIZATION_LEVEL && temp )){
							isCond=false;
							String msg=payrollViewMode.equals(Constant.SHOW_PAYROLL_DATE)?
									"You cannot delete locked payroll date."
									:
									"You cannot delete payroll data where the payroll date is locked.";
							mainFrame.showOptionPaneMessageDialog(msg, JOptionPane.ERROR_MESSAGE);
						}
						
						if(isCond){
							if(payrollViewMode.equals(Constant.SHOW_PAYROLL_DATE)){
								dialog.lblNewLabel.setText(
									(selectedTableRowIndex.length==1)?
									" Are you sure you want to delete? \n All data under this payroll date will also be \n deleted."
									:
									" Are you sure you want to delete? \n All data under these payroll dates will \n also be deleted."
								);
								dialog.setSize(dialog.getWidth(), dialog.getHeight()+50);
								dialog.setVisible(true);
								dialog.setSize(dialog.getWidth(), dialog.getHeight()-50);
							}
							
							else{
								dialog.lblNewLabel.setText(" Are you sure you want to delete?");
								dialog.setVisible(true);
							}
						}
					}
					
					else{
						MainFrame.getInstance().showOptionPaneMessageDialog("You are not authorized to use this. Please contact your administrator.", JOptionPane.ERROR_MESSAGE);
						
					}
					
				}
				else{
					mainFrame.showOptionPaneMessageDialog(
							"You have not selected anything.", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
			//--> SHOW INDIVIDUAL PAYSLIP DATA DIALOG
			else if(e.getSource()==payrollViewPanel.showPayslipDialogBtn){
				processShowPayslipDialog();
			}
			
			
			//--> NET PAY COPY MODE 
			else if(e.getSource()==payrollViewPanel.netPayCopyModeBtn){
				processNetPayCopyMode();
			}
			
			//--> CANCEL NET PAY COPY MODE 
			else if(e.getSource()==payrollViewPanel.cancelNetPayCopyModeBtn){
				processCancelNetPayCopyMode();
			}
			//--------------------------------------------------------------------------------------
			
			//--> ADD Payroll Date Dialog: SAVE BUTTON
			else if(e.getSource()==payrollViewPanel.addPayrollDateDialog.saveButton){
				processSavePayrollDate();
			}
			//--> ADD Payroll Date Dialog: CANCEL BUTTON
			else if(e.getSource()==payrollViewPanel.addPayrollDateDialog.cancelButton){
				payrollViewPanel.addPayrollDateDialog.dispose();
			}
			
			//--------------------------------------------------------------------------------------
			//--> Department COMBOBOX and ADD button processes of ShowAllEmployeeDialogBox for adding payroll data.
			else if(e.getSource()==payrollViewPanel.showAllEmpAddPayDaDialog.departmentComboBox){
				processDepartmentComboBox();
			}
			else if(e.getSource()==payrollViewPanel.showAllEmpAddPayDaDialog.addButton){
				processAddPayrollDATA();
			}
			
			//--------------------------------------------------------------------------------------
			//--> ADD PAYROLL DATA SAVE BUTTON
			else if(e.getSource()==payrollViewPanel.addEDDataDialog.saveButton){
				processSavePayrollDATA();
			}
	
			//--------------------------------------------------------------------------------------
			
			//--> DISPLAY EMPLOYEE PAYROLL SUMMARY OPTION DIALOG PDF: SHOW BUTTON
			else if(e.getSource()==payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL.showButton){
				
				processDisplayEmployeePayrollDataPDF();
				
			}
			//--> DISPLAY EMPLOYEE PAYROLL SUMMARY OPTION DIALOG PDF: CANCEL BUTTON
			else if(e.getSource()==payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL.cancelButton){
				System.out.println(THIS_CLASS_NAME+"Display employee payroll data PDF option dialog CANCEL Button"+CLASS_NAME);
				payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL.dispose();
				
			
			}
			
			//--------------------------------------------------------------------------------------
			
			//--> DISPLAY PAYROLL DATA SUMMARY OPTION DIALOG  PDF only Payrolll Date Comobox: SHOW BUTTON
			if(e.getSource()==payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.showButton){
				Utilities util = Utilities.getInstance();
				
				switch(payrollDispPDFEXCELBtnClicked){
					case Constant.DISPLAY_DEPARTMENT_PAYROLL_DATA_PDF:{
						
						if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
							System.out.println(THIS_CLASS_NAME+"Display OVERALL payroll data REGULAR."+CLASS_NAME);
							
							processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_OVERALL_PDF);
						}
						else{
							System.out.println(THIS_CLASS_NAME+"Display OVERALL payroll data CONTRACTUAL."+CLASS_NAME);
							
							processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_PER_CONTRACTUAL_EMPLOYEE_PDF);
						}
						break;
					}
					case Constant.DISPLAY_ASEMCO_DATA_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display ASEMCO payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_ASEMCO_PDF);
						break;
					}
					case Constant.DISPLAY_BCCI_DATA_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display BCCI payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_BCCI_PDF);
						break;
					}
					case Constant.DISPLAY_OCCCI_DATA_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display OCCI payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_OCCCI_PDF);
						break;
					}
					case Constant.DISPLAY_DBP_DATA_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display DBP payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_DBP_PDF);
						break;
					}
					case Constant.DISPLAY_CITY_SAVINGS_DATA_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display City Savings payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_CITY_SAVINGS_PDF);
						break;
					}
					case Constant.DISPLAY_ST_PLAN_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display St. Plan payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_ST_PETER_PLAN_PDF);
						break;
					}
					case Constant.DISPLAY_W_TAX_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display W/TAX payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_W_TAX_PDF);
						break;
					}
					case Constant.DISPLAY_LBP_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display LBP Plan payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_LBP_PDF);
						break;
					}
					case Constant.DISPLAY_SSS_LOAN_DATA_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display SSS LOAN payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_SSS_LOAN_PDF);
						break;
					}
					case Constant.DISPLAY_PAGIBIG_LOAN_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display PAGIBIG LOAN payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_PAGIBIG_LOAN_PDF);
						break;
					}
					case Constant.DISPLAY_UNION_DUES_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display Union Dues payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_UNION_DUES_PDF);
						break;
					}
					case Constant.DISPLAY_HDMF_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display HDMF/Pagibig Cont payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_HDMF_PDF);
						break;
					}
					case Constant.DISPLAY_MEDICARE_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display Medicare payroll data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_MEDICARE_PDF);
						break;
					}
					case Constant.DISPLAY_SSS_CONT_PDF:{
						System.out.println(THIS_CLASS_NAME+"Display SSS Contribution payroll data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_SSS_CONT_PDF);
						break;
					}
					
					//----------------------------------------------------------------------------
					case Constant.EXPORT_DEPARTMENT_PAYROLL_DATA_EXCEL:{
						
						if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
							System.out.println(THIS_CLASS_NAME+"Export Overall Department Data REGULAR."+CLASS_NAME);
							
							processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_OVERALL_EXCEL);
							
						}
						else{
							System.out.println(THIS_CLASS_NAME+"Export Overall Department Data CONTRACTUAL."+CLASS_NAME);
							
							processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_PER_CONTRACTUAL_EMPLOYEE_EXCEL);
						}
						
						
						
						break;
					}
					case Constant.EXPORT_ASEMCO_DATA_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export ASEMCO Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_ASEMCO_EXCEL);
						
						break;
					}
					case Constant.EXPORT_BCCI_DATA_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export BCCI Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_BCCI_EXCEL);
						
						break;
					}
					case Constant.EXPORT_OCCCI_DATA_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export OCCCI Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_OCCCI_EXCEL);
						
						break;
					}
					case Constant.EXPORT_DBP_DATA_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export DBP Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_DBP_EXCEL);
						
						break;
					}
					case Constant.EXPORT_CITY_SAVINGS_DATA_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export City Savings Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_CITYSAVINGS_EXCEL);
						
						break;
					}
					case Constant.EXPORT_ST_PLAN_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export St. Peter Plan."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_ST_PETER_PLAN_EXCEL);
						
						break;
					}
					case Constant.EXPORT_W_TAX_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export Witholding Tax Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_W_TAX_EXCEL);
						
						break;
					}
					case Constant.EXPORT_LBP_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export LBP Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_LBP_EXCEL);
						
						break;
					}
					case Constant.EXPORT_SSS_LOAN_DATA_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export SSS Loan Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_SSS_LOAN_EXCEL);
						
						break;
					}
					case Constant.EXPORT_PAGIBIG_LOAN_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export Pag-ibig Loan Data."+CLASS_NAME);
	
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_PAGIBIG_LOAN_EXCEL);
						
						break;
					}
					case Constant.EXPORT_UNION_DUES_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export Union Dues Data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_UNION_DUES_EXCEL);
						
						break;
					}
					
					case Constant.EXPORT_HDMF_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export HDMF Data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_HDMF_EXCEL);
						
						break;
					}
					case Constant.EXPORT_MEDICARE_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export Medicare Data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_MEDICARE_EXCEL);
						
						break;
					}
					case Constant.EXPORT_SSS_CONT_EXCEL:{
						System.out.println(THIS_CLASS_NAME+"Export SSS Cont Excel Data."+CLASS_NAME);
						
						processDisplayPayrollDataDialogPDFExcel(Constant.PAYROLL_SSS_CONT_EXCEL);
						
						break;
					}
					default:
						break;
				}
				
			}
			//--> DISPLAY PAYROLL DATA SUMMARY OPTION DIALOG PDF only Payrolll Date Comobox: CANCEL BUTTON
			else if(e.getSource()==payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.cancelButton){
				
				System.out.println(THIS_CLASS_NAME+"Display payroll data PDF option dialog CANCEL Button"+CLASS_NAME);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.dispose();
				
				//--> Set necessary UI components.
				
				if(isExportExcelButtonsClicked()){
					payrollViewPanel.excelPanel.setVisible(true);
				}
				else{
					payrollViewPanel.pdfPanel.setVisible(true);
				}
				
			
				
			}
			
			//--------------------------------------------------------------------------------------
			
			
			//--> CLICKING SHOWBTN Showing the Payroll Data.
			else if(e.getSource()==payrollViewPanel.showBtn){
				canDisplayPayslipPDF=true;
				processShowPayrollData();
			}
			
	
			
			//--------------------------------------------------------------------------------------
			//--> PAYSLIP DATA DIALOG calculate,save,cancel
			
			else if(e.getSource()==payrollViewPanel.payslipDataDialog.calculateBtn){
				processCalculatePayslipDataDialog();
			}
			else if(e.getSource()==payrollViewPanel.payslipDataDialog.saveButton){
				processSavePayslipDataDialog();
			}
			else if(e.getSource()==payrollViewPanel.payslipDataDialog.cancelButton){
				processCancelPayslipDataDialog();
			}
			
			//--------------------------------------------------------------------------------------
			
			//--> DELETE WARNING DIALOG_YES
			else if(e.getSource()==payrollViewPanel.deletePayrollWarningDialog.yesButton){
				if(payrollViewMode.equals(Constant.SHOW_PAYROLL_DATE)){
					processDeletePayrollDate();
				}
				else{
					processDeletePayrollDATA();
				}
			}
			//--> DELETE WARNING DIALOG_NO
			else if(e.getSource()==payrollViewPanel.deletePayrollWarningDialog.noButton){
				payrollViewPanel.deletePayrollWarningDialog.dispose();
			}
			
			
			//--------------------------------------------------------------------------------------
			//--> ADD,CLEAR, SAVE EDIT SIGNATURE INFO DIALOG
			else if(e.getSource()==payrollViewPanel.editSignatureInfoDialog.saveButton){
				System.out.println(THIS_CLASS_NAME+"SAVE Signature Data Information"+CLASS_NAME);
				EditSignatureInfoDialog dialog = payrollViewPanel.editSignatureInfoDialog;
				Utilities util = Utilities.getInstance();
				Database db = Database.getInstance();
				EditSignatureDataPanel panel=null;
				
				//-----PreparedBy
				panel= payrollViewPanel.editSignatureInfoDialog.preparedByPanel;
				String preparedByName =panel.getNameTextField().getText();
				String preparedByTitle =panel.getWorkTitleTextField().getText();
				
				//-----CheckedBy
				panel= payrollViewPanel.editSignatureInfoDialog.checkedByPanel;
				String checkedByName= panel.getNameTextField().getText();
				String checkedByTitle= panel.getWorkTitleTextField().getText();
				
				//-----AuditedBy
				panel= payrollViewPanel.editSignatureInfoDialog.auditedByPanel;
				String auditedByName= panel.getNameTextField().getText();
				String auditedByTitle= panel.getWorkTitleTextField().getText();
				
				//-----ApprovedBy
				panel= payrollViewPanel.editSignatureInfoDialog.approvedByPanel;
				String approvedByName= panel.getNameTextField().getText();
				String approvedByTitle= panel.getWorkTitleTextField().getText();
				
				
				if(util.isThereAreChangesOnSignatureData(db, util,
						preparedByName, preparedByTitle, 
						checkedByName, checkedByTitle,
						auditedByName, auditedByTitle,
						approvedByName, approvedByTitle)){
					
					ArrayList<MultipleUpdateDatabaseModel>multipleUpdateList = new ArrayList<MultipleUpdateDatabaseModel>();
					multipleUpdateList.add(new MultipleUpdateDatabaseModel());
					MultipleUpdateDatabaseModel multi= multipleUpdateList.get(multipleUpdateList.size()-1);
					multi.primarykey=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?"1":"2";
					
					// Add na names and job
					String[] list={preparedByName, preparedByTitle, checkedByName,checkedByTitle,auditedByName,auditedByTitle,approvedByName,approvedByTitle};
					for(int i=0,j=1;i<list.length;i++,j++){  
						multi.changesToBeUpdated.put(db.signatureTableColumnNames[j],list[i]);
					}
					
					// Add the signature image.
					 ByteArrayInputStream inStream = null;
					 if(dialog.filePath!=null){
							inStream= util.convertImageIconToByteArrayInputStream(dialog.filePath);
					 }
					 multi.changesToBeUpdated.put(
						 db.signatureTableColumnNames[db.signatureTableColumnNames.length-1], // SignatureOfPerson
						 inStream
					 );
					
					// Execute update database
					db.updateDataInDatabase(
						db.tableNameSignatureTable, 
						null,
						db.signatureTableColumnNames[0], 
						null, 
						true, 
						multipleUpdateList
					);
					
					if(db.isUpdate){
						//--------------------------------
						db.initializeSignatureTabletData();
						mainFrame.showOptionPaneMessageDialog("You have successfully saved the data!", JOptionPane.INFORMATION_MESSAGE);
						
						//-----------------------------------
						// Update the signature image of payslip dialog.
						try {
							payrollViewPanel.payslipDataDialog.payslipPanel.signatureLabel.setIcon(
								db.getSignatureImageFromDatabase(Constant.SIGNATURE_IMAGE_HEIGHT)
							);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							payrollViewPanel.payslipDataDialog.payslipPanel.signatureLabel.setIcon(
								null
							);
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							payrollViewPanel.payslipDataDialog.payslipPanel.signatureLabel.setIcon(
								null
							);
							e1.printStackTrace();
						}
						
						
						//-----------------------------------
						dialog.dispose();
					}
					else{
						mainFrame.showOptionPaneMessageDialog("You have failed to save the data.", JOptionPane.ERROR_MESSAGE);
						
					}
					
				}
				else{
					mainFrame.showOptionPaneMessageDialog("You have not edited any new data", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
			else if(e.getSource()==payrollViewPanel.editSignatureInfoDialog.clearSignatureImgBtn){
				System.out.println(THIS_CLASS_NAME+"CLEAR Signature image"+CLASS_NAME);
				
				payrollViewPanel.editSignatureInfoDialog.signatureLabel.setIcon(null);
				payrollViewPanel.editSignatureInfoDialog.filePath=null;
			}
			
			else if(e.getSource()==payrollViewPanel.editSignatureInfoDialog.addSignatureImgBtn){
				System.out.println(THIS_CLASS_NAME+"ADD Signature Image"+CLASS_NAME);
				EditSignatureInfoDialog dialog = payrollViewPanel.editSignatureInfoDialog;
				Utilities util = Utilities.getInstance();
				Database db = Database.getInstance();
				
				
				
				int i=dialog.signatureImgFileChooser.showOpenDialog(dialog);
				
				if(i==JFileChooser.APPROVE_OPTION){
					try{
						File file = dialog.signatureImgFileChooser.getSelectedFile();
						String path = file.getPath();
						System.out.println("\t Signature JFileChooser Path: "+path+CLASS_NAME);
						
						int height=Constant.SIGNATURE_IMAGE_HEIGHT ;
						BufferedImage signatureBufferedImg=util.readBufferedImage(path);
						int imgHeight=signatureBufferedImg.getHeight(), imgWidth=signatureBufferedImg.getWidth();
						signatureBufferedImg=util.resizeImage(
							signatureBufferedImg,
							util.calculateWidthBasedFromGivenMinHeightForScalingImage(imgWidth, imgHeight, height),
							height
						);
						ImageIcon signatureImgIcon=new ImageIcon(signatureBufferedImg);
						dialog.signatureLabel.setIcon(signatureImgIcon);
						dialog.filePath=path;
						
						
						//--> Set directory path the same where you selected the file.
//						dialog.signatureImgFileChooser= new JFileChooser(path);
						dialog.signatureImgFileChooser.setCurrentDirectory(file);
//						dialog.signatureImgFileChooser.setFileFilter(dialog.filter);
						
					} catch(Exception ex){
						ex.printStackTrace();
						mainFrame.showOptionPaneMessageDialog(ex.getMessage(), JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
			
			//---------------------------------------------------------------------------
			//LOCKED WARNING DIALOG YES OR NO
			else if(e.getSource()==payrollViewPanel.lockedWarningDialog.yesButton){
				ReusableTable table = payrollViewPanel.fullScreenTable;
				Database db = Database.getInstance();
				Utilities util = Utilities.getInstance();
				HashMap<String, Object>changesToBeUpdated=new HashMap<String,Object>();
				
				int [] selectedIndexList=Utilities.getInstance().getSelectedIndexListBasedFromTableConvertedToModel(table);
				int payrollDateColumnIndex=0,lockedStatusColumnIndex=2; //Lock Status
				String payrolldate=table.getModel().getValueAt(selectedIndexList[selectedIndexList.length-1], payrollDateColumnIndex).toString();
				
				changesToBeUpdated.put(db.payrollDateTableColumnNames[lockedStatusColumnIndex], Constant.LOCKED_STATUS);
				
				
				db.updateDataInDatabase(
						db.tableNamePayrollDate,
						changesToBeUpdated,
						db.payrollDateTableColumnNames[0],
						payrolldate,
						false,
						null
				);
				
				if(db.isUpdate){
					mainFrame.showOptionPaneMessageDialog("You have successfully locked the payroll date: "+
							util.convertDateYyyyMmDdToReadableDate(payrolldate), JOptionPane.INFORMATION_MESSAGE
					);
					payrollViewPanel.lockedWarningDialog.dispose();
					
					//--> Reuse code
					processShowPayrollDate();
				}
			}
			else if(e.getSource()==payrollViewPanel.lockedWarningDialog.noButton){
				payrollViewPanel.lockedWarningDialog.dispose();
			}
			
			
			
			//---------------------------------------------------------------------------
			//WITH ATM OR NO DIALOG Execute Button.
			else if(e.getSource()==payrollViewPanel.withOrWithoutATMDialog.selectButton){
				WithOrWithoutATMDialog dialog = payrollViewPanel.withOrWithoutATMDialog;
				
				if(dialog.withATMRadioBtn.isSelected() || dialog.withoutATMRadioBtn.isSelected()){
					if(payrollViewPanel.withOrWithoutATMDialog.dispPDFExcelMode >= Constant.PAYROLL_OVERALL_EXCEL){
						dialog.selectButton.setEnabled(false);
						ExcelCreator.getInstance().viewExcel(
							payrollViewPanel.withOrWithoutATMDialog.dispPDFExcelMode,
							mainFrame
						);
					}
					else{
						dialog.selectButton.setEnabled(false);
						PDFCreator.getInstance().viewPDF(
							null,
							payrollViewPanel.payrollSlip,
							payrollViewPanel.withOrWithoutATMDialog.dispPDFExcelMode
						);
					}
					
					payrollViewPanel.withOrWithoutATMDialog.dispose();
				}
				else{
					mainFrame.showOptionPaneMessageDialog("Please select an option.", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getSource()==payrollViewPanel.withOrWithoutATMDialog.withATMRadioBtn){
				payrollViewPanel.withOrWithoutATMDialog.withATMRadioBtn.setSelected(true);
				payrollViewPanel.withOrWithoutATMDialog.withoutATMRadioBtn.setSelected(false);
				
			}
			else if(e.getSource()==payrollViewPanel.withOrWithoutATMDialog.withoutATMRadioBtn){
				payrollViewPanel.withOrWithoutATMDialog.withATMRadioBtn.setSelected(false);
				payrollViewPanel.withOrWithoutATMDialog.withoutATMRadioBtn.setSelected(true);
			}
			
			
			//--------------------------------------------------------
			//--> Add Comment on All Payslip
			else if(e.getSource()==payrollViewPanel.addCommentsOnAllPayslipDialog.saveButton){
				Database db= Database.getInstance();
				Utilities util=Utilities.getInstance();
				String payrollDate=payrollViewPanel.addCommentsOnAllPayslipDialog.payrollDateComboBox.getSelectedItem().toString();
				payrollDate=util.convertDateReadableToYyyyMmDdDate(payrollDate);
				
				
				if(payrollDate.equals("")){
					mainFrame.showOptionPaneMessageDialog("Please select a payroll date.", JOptionPane.ERROR_MESSAGE);	
				}
				else if(db.isPayrollDateLocked(new String[]{payrollDate})){
					mainFrame.showOptionPaneMessageDialog(
							"You cannot add comments when the payroll date is locked.", 
							JOptionPane.ERROR_MESSAGE);
				}
				else{
					String comment=payrollViewPanel.addCommentsOnAllPayslipDialog.commentTextArea.getText();
					
					// Set condition
					ArrayList<SelectConditionInfo>conditionColumnAndValueList = new ArrayList<SelectConditionInfo>();
					conditionColumnAndValueList.add(new SelectConditionInfo(db.payrollDateTableColumnNames[0], payrollDate)); // PayrollDate
					conditionColumnAndValueList.add(
						new SelectConditionInfo(
							db.employeeTableColumnNames[10],
							(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
								Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
						)
					); // HiredAs
					
					// Set Join
					String[] joinColumnCompareList={
						db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+
							"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[2] // employee.EmployeeID=deductions.EmployeeID
					};
					
					
					try {
						//--> Get all deduction/earning ID where we will add comments.
						db.selectDataInDatabase(
								new String[]{db.tableNameDeductions,db.tableNameEmployee},
								new String[]{db.deductionTableColumnNames[0]}, // DeductionID 
								conditionColumnAndValueList, 
								joinColumnCompareList, 
								null, 
								Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND);
					
						
						db.resultSet.last();
						
						int totalNumOfRows=db.resultSet.getRow();
						db.resultSet.beforeFirst();
						String[] idList=new String[totalNumOfRows];
						for(int i=0;i<totalNumOfRows;i++){
							db.resultSet.absolute(i+1);
							idList[i]=db.resultSet.getObject(1).toString();	
						}
						
						//--------------------------------------------------------------------------
						//--> Update to add comments.
						
						ArrayList<MultipleUpdateDatabaseModel>multipleUpdateList=new ArrayList<MultipleUpdateDatabaseModel>();
						for(String id:idList){
							multipleUpdateList.add(new MultipleUpdateDatabaseModel());
							
							MultipleUpdateDatabaseModel mult=multipleUpdateList.get(multipleUpdateList.size()-1);
							mult.primarykey=id;
							mult.changesToBeUpdated.put(db.deductionTableColumnNames[db.deductionTableColumnNames.length-1], comment);
						}
					
						
						
						db.updateDataInDatabase(
								db.tableNameDeductions, 
								null,
								db.deductionTableColumnNames[0], 
								null, 
								true, 
								multipleUpdateList);
						
						if(db.isUpdate){
							mainFrame.showOptionPaneMessageDialog("The comment have been successfully added!", JOptionPane.INFORMATION_MESSAGE);	
							//--> Set necessary UI
							payrollViewPanel.addCommentsOnAllPayslipDialog.dispose();
						}
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						mainFrame.showOptionPaneMessageDialog(""+e1.getMessage(), JOptionPane.ERROR_MESSAGE);
						
					}
					
				}
				
			}
		
				
		
			
			
		}
		
		//--------------------------------------------------------------------------------------
		
		
		//--> Repaint to avoid overlap
		payrollViewPanel.repaint();
		
		
		
	}
	private void l_________________________________________________l(){}
	
	/**
	 * Execute the process of values for total table.
	 * @param db
	 * @param util
	 * @param table
	 * @param tableNameList
	 * @param columnNameID
	 * @param neededColumnsForTotalTable
	 * @param editMode
	 */
	private void executeProcessOfValuesOfTotalTable(Database db, Utilities util,
			ReusableTable table,
			String[] tableNameList,
			String columnNameID,
			String[] neededColumnsForTotalTable,
			String[]joinColumnCompareList){
		
		//		selectBasedFromColumnsWithConditionOR(String[] tableNameList,
		//		String[]columnNameList, 
		//		ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		
		//selectInnerJoinWithConditionOR(String[] tableNameList,String[]columnNameList, 
		//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
		//		String[] joinColumnCompareList,
		//		OrderByInfo orderInfo)
		
		
		//--> This process is executed if there is an existing data.
		if(table.getModel().getRowCount()>0){
			//--> Process needed columns[key] and their values for CONDITION. In this case get the deduction/earning ID
			
			ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
			for(int i=0;i<table.getModel().getRowCount();i++){
				conditionColumnAndValueList.add(
						new SelectConditionInfo(
								columnNameID,
								table.getModel().getValueAt(i, table.getModel().getColumnCount()-1) // Why ? since we get the deduction/earning id column.
						)
				);
			}
					
			
			
			//--> Process Total Table.
			db.selectDataInDatabase(
				tableNameList,
				neededColumnsForTotalTable,
				conditionColumnAndValueList,
				joinColumnCompareList,
				null,
				Constant.SELECT_INNER_JOIN_WITH_CONDITION_OR
			);
			
			//--> Update total table
			payrollViewPanel.totalFullScreenTable.isAutoResize=true;
			payrollViewPanel.totalFullScreenTable.updateTable(db);
			payrollViewPanel.totalFullScreenTable.roundOffAllDataToTwoDecimal();
		}
		else{ // When there are no available data
			payrollViewPanel.totalFullScreenTable.clearAllContentsOfTable();
		}
	}
	
	/**
	 * Execute
	 */
	private void processAddCommentsOnPayslip(){
		System.out.println(THIS_CLASS_NAME+"Add comment on all payslip."+CLASS_NAME);
		
		// Set necessary UI components
		payrollViewPanel.optionPanel.setVisible(false);
		payrollViewPanel.addCommentsOnAllPayslipDialog.setVisible(true);
	}
	
	
	/**
	 * Execute process that will happen when you click edit signature info.
	 */
	private void processEditSignatureInfo(){
		System.out.println(THIS_CLASS_NAME+"Edit Signature Information"+CLASS_NAME);
		EditSignatureInfoDialog dialog = payrollViewPanel.editSignatureInfoDialog;
		Utilities util = Utilities.getInstance();
		Database db = Database.getInstance();
		EditSignatureDataPanel panel=null;
		
		//-----PreparedBy
		panel= dialog.preparedByPanel;
		panel.getNameTextField().setText(db.signatureTableDataList.get(db.signatureTableColumnNames[1]));
		panel.getWorkTitleTextField().setText(db.signatureTableDataList.get(db.signatureTableColumnNames[2]));
		
		//-----CheckedBy
		panel= dialog.checkedByPanel;
		panel.getNameTextField().setText(db.signatureTableDataList.get(db.signatureTableColumnNames[3]));
		panel.getWorkTitleTextField().setText(db.signatureTableDataList.get(db.signatureTableColumnNames[4]));
		
		//-----AuditedBy
		panel= dialog.auditedByPanel;
		panel.getNameTextField().setText(db.signatureTableDataList.get(db.signatureTableColumnNames[5]));
		panel.getWorkTitleTextField().setText(db.signatureTableDataList.get(db.signatureTableColumnNames[6]));
		
		//-----ApprovedBy
		panel= dialog.approvedByPanel;
		panel.getNameTextField().setText(db.signatureTableDataList.get(db.signatureTableColumnNames[7]));
		panel.getWorkTitleTextField().setText(db.signatureTableDataList.get(db.signatureTableColumnNames[8]));
		
		//----Signature Image
		ImageIcon signatureImg=null;
		try {
			signatureImg=db.getSignatureImageFromDatabase(Constant.SIGNATURE_IMAGE_HEIGHT);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialog.signatureLabel.setIcon(signatureImg);
		
		
		
		
		dialog.setVisible(true);
		
	}
	/**
	 * Execute process that will happen when show payroll DATE summary is clicked
	 */
	private void processShowPayrollDate(){
		System.out.println(THIS_CLASS_NAME+"Show Payroll Dates"+CLASS_NAME);
		
		Database db= Database.getInstance();
		
		//-------------------
		
		OrderByInfo orderInfo=new OrderByInfo(
				new String[]{db.payrollDateTableColumnNames[0]},
				"DESC"
		);
		db.selectDataInDatabase(new String[]{db.tableNamePayrollDate}, null, null, null,orderInfo, Constant.SELECT_ALL);
		
		//-------------------
		payrollViewPanel.fullScreenTable.isAutoResize=true;
		payrollViewPanel.fullScreenTable.updateTable(db);
		
		//-------------------
		//--> Update Row Count Label
		payrollViewPanel.rowCountLabel.setText("Row Count: "+payrollViewPanel.fullScreenTable.getModel().getRowCount());
		
		//-------------------
		
		//--> Set necessarry UI compponents
		payrollViewMode=Constant.SHOW_PAYROLL_DATE;
		
		
		payrollViewPanel.topRightButtonPanel.setVisible(true);
		payrollViewPanel.lockOptionButton.setVisible(true);
		payrollViewPanel.updateTopRightButtonPanel(2);
		payrollViewPanel.changeTheHeightOfBottomPanel(20);
		payrollViewPanel.payrollDateDataLabel.setIcon(Images.getInstance().payrollDATELabelImg);
				
	}
	
	/**
	 * When clicking the add button when showing payroll data.
	 */
	private void processShowAllEmployeeDialog(){
		System.out.println(THIS_CLASS_NAME+"SHow all Employee Dialog for Adding Payroll DATA"+CLASS_NAME);
		
		Database db=Database.getInstance();
		Utilities util =Utilities.getInstance();
		ReusableTable table= payrollViewPanel.showAllEmpAddPayDaDialog.employeeListTable;
		
		//-----------------------------------------------------------------------
		//--> Process Condition
		ArrayList<SelectConditionInfo>conditionColumnAndValueList= new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"DateLeft",
				Constant.STRING_DATE_LEFT_NULL // Only include employees that are currently working, dont include those who LEFT.
			)
		);
		
		//-----------------------------------------------------------------------
		//--> Get Data from Database
		db.selectDataInDatabase(
				new String[]{db.tableNameEmployee},
				new String[]{db.employeeTableColumnNames[0],db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],db.employeeTableColumnNames[4]},
				conditionColumnAndValueList,
				null,
				new OrderByInfo(new String[]{db.employeeTableColumnNames[2]}, "ASC"),
				Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND
		);
		
		//--> Update table
		table.updateTable(db);
		
		//--> Set necessary UI components.
		payrollViewPanel.optionPanel.setVisible(false);
		
		payrollViewPanel.showAllEmpAddPayDaDialog.updateRowCountLabel(table.getModel().getRowCount());
		payrollViewPanel.showAllEmpAddPayDaDialog.setVisible(true);
	}
	
	/**
	 * Execute process that will happen when show payroll data summary is clicked
	 */
	private void processShowPayrollDataSummary( ArrayList<SelectConditionInfo>conditionColumnAndValueList){
		System.out.println(THIS_CLASS_NAME+"Show Payroll Data Summary"+CLASS_NAME);
		
		Database db= Database.getInstance();
		Utilities util = Utilities.getInstance();
		
//		private String selectInnerJoin(String[] tableNameList,String[]columnNameList,
//				String[] joinColumnCompareList,OrderByInfo orderInfo){
		
		//----------------------------------------------------------------------------
		//--> Preferred Query
//		SELECT payrolldate.PayrollDate,employee.EmployeeID,FamilyName,FirstName,Department,
//		TotalEarnings,TotalDeduction
//		FROM employee
//		INNER JOIN department 
//		ON `employee`.`DepartmentID`=`department`.`DepartmentID`
//		INNER JOIN deductions
//		ON deductions.EmployeeID = employee.EmployeeID
//		INNER JOIN payrolldate
//		ON payrolldate.PayrollDate = deductions.PayrollDate
//		INNER JOIN earnings
//		ON earnings.EarningID = deductions.DeductionID;
		
		
		//-----------------------------------------------------------------------
		//--> Process the needed columns
		String[] desiredColumnNamesList=db.getDesiredColumnNamesInPayrollViewPanel(util);
		
		//-----------------------------------------------------------------------
		//--> Set the values for joinCompareList.
		String [] joinCompareList={
				db.tableNameEmployee+"."+db.departmentTableColumnNames[0]
					+"="+db.tableNameDepartment+"."+db.departmentTableColumnNames[0],
				
				db.tableNameDeductions+"."+db.employeeTableColumnNames[0]
					+"="+db.tableNameEmployee+"."+db.employeeTableColumnNames[0],
				
				db.tableNamePayrollDate+"."+db.payrollDateTableColumnNames[0]
					+"="+db.tableNameDeductions+"."+db.payrollDateTableColumnNames[0],
				
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.tableNameEarnings+"."+db.earningTableColumnNames[0]
						+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[0]
					:
					db.tableNameEarningsContractual+"."+db.earningsContractualColumnNames[0]
							+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[0]
		};
		
		//-----------------------------------------------------------------------
		//--> Process Order
		OrderByInfo orderInfo=new OrderByInfo(
			new String[]{
					db.payrollDateTableColumnNames[0], 	// Payroll Date
					db.employeeTableColumnNames[2], 	// LastName
					db.employeeTableColumnNames[3]}, 	// FirstName
			"ASC"
		);
		
		//-----------------------------------------------------------------------
		//--> Get Data from Database.
		
		db.selectDataInDatabase(
				new String[]{
						db.tableNameEmployee,
						db.tableNameDepartment,
						db.tableNameDeductions, 
						db.tableNamePayrollDate,
						(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
							db.tableNameEarnings:db.tableNameEarningsContractual
				}, 
				desiredColumnNamesList, 
				conditionColumnAndValueList, 
				joinCompareList, // DepartmentID
				orderInfo,
				Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND);

		
		//----------------------------------------------------------------------------
		//--> Update full screen table.
		payrollViewPanel.fullScreenTable.isAutoResize=true;
		payrollViewPanel.fullScreenTable.updateTable(db);
		
		//----------------------------------------------------------------------------
		//--> Hide Payroll Data Table
		payrollViewPanel.fullScreenTable.hideColumns(new int[]{payrollViewPanel.fullScreenTable.getModel().getColumnCount()-1}); // Why 9? since we hid the last column which is deductionID.
		
		//----------------------------------------------------------------------------
		//--> Update Row Count Label
		payrollViewPanel.rowCountLabel.setText("Row Count: "+payrollViewPanel.fullScreenTable.getModel().getRowCount());
		
		
		//------------------------------------------------------------------------
		//--> Process the retrieving of data for TOTAL TABLE.
		ReusableTable table = payrollViewPanel.fullScreenTable;
		
		//--> Process needed columns for total table.
		String[]neededColumnsForTotalTable=db.getDesiredTotalColumnsInPayrollViewPanel(util, table);
		
		//--> Process Inner Join list. Only when employer share is shown.
		String [] joinColumnCompareList=new String[]{
				db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameDeductions+"."+db.employeeTableColumnNames[0], // 		` employee.EmployeeID=deductions.EmployeeID

				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.tableNameEarnings+"."+db.earningTableColumnNames[0]+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[0] // 		`deductions`.`DeductionID`=`earnigs`.`EarningID`
					:
					db.tableNameEarningsContractual+"."+db.earningsContractualColumnNames[0]+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[0] // 		`deductions`.`DeductionID`=`earnigs`.`EarningID`
									
		};
		//--> Execute processing of total values.
		executeProcessOfValuesOfTotalTable(db, util, table,
			new String[]{
				db.tableNameEmployee,
				db.tableNameDeductions,
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?db.tableNameEarnings:db.tableNameEarningsContractual
			},
			util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(table.getModel().getColumnCount()-1).toString()),
			neededColumnsForTotalTable,
			joinColumnCompareList
		);
				
		
		//----------------------------------------------------------------------------		
		//--> Update immediately with search mechanism.
		PayrollViewPanel.getInstance().executeSearchMechanismOfAllTables();

				
				
		//----------------------------------------------------------------------------		
		//--> Set necessary UI components
		payrollViewMode=Constant.SHOW_PAYROLL_SUMMARY;
		payrollViewPanel.topRightButtonPanel.setVisible(true);
		payrollViewPanel.updateTopRightButtonPanel(4);
		payrollViewPanel.changeTheHeightOfBottomPanel(90);
		
		//----------------------------------------------------------------------------
		//--> No Available Data.
		if(payrollViewPanel.fullScreenTable.getModel().getRowCount()==0){
			mainFrame.showOptionPaneMessageDialog("There are no available data.", JOptionPane.ERROR_MESSAGE);
		}
		
	
	}
	
	/**
	 * Execute the process where you show the option dialog for displaying pdf of employee summary per department data.
	 */
	private void processShowDisplayEmployeePayrollDataPDFEXCELOptionDialog(){
		System.out.println(THIS_CLASS_NAME+"Show Display EMPLOYEE Summary DIALOG"+CLASS_NAME);
		 
		DisplayOptionsDialog dialog=payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL;
		switch(payrollDispPDFEXCELBtnClicked){
			
			case Constant.DISPLAY_EMPLOYEE_PAYROLL_DATA_PDF:{
				dialog.setTitle("Display per Dept. Payroll PDF");
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				break;
			}
			case Constant.EXPORT_EMPLOYEE_PAYROLL_DATA_EXCEL:{
				dialog.setTitle("Export per Dept. Payroll EXCEL");
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				break;
			}
			default:{
				break;
			}
		}
		//--> Set necessary UI components
		payrollViewPanel.topRightButtonPanel.setVisible(false);
		payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL.setVisible(true);
	}
	/**
	 * Execute the process where you show the option dialog where the dualog has payroll date combobox only.
	 */
	private void processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog(){
		System.out.println(THIS_CLASS_NAME+"Show Display DEPARTMENT Summary DIALOG"+CLASS_NAME);
		DisplayOptionsDialog dialog= payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		
		
		//--> Set TITLE option dialog
		switch(payrollDispPDFEXCELBtnClicked){
			case Constant.DISPLAY_DEPARTMENT_PAYROLL_DATA_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display Overall Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_ASEMCO_DATA_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display ASEMCO Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_BCCI_DATA_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display BCCI Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_OCCCI_DATA_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display OCCCI Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_DBP_DATA_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display DBP Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_CITY_SAVINGS_DATA_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display City Savings Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_ST_PLAN_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display ST. PETER Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_W_TAX_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display W/TAX Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_LBP_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display LBP Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_SSS_LOAN_DATA_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display SSS LOAN Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_PAGIBIG_LOAN_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display PAG-IBIG LOAN Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_UNION_DUES_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display Union Dues Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_HDMF_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display HDMF Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_MEDICARE_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display Medicare Payroll PDF Option");
				break;
			}
			case Constant.DISPLAY_SSS_CONT_PDF:{
				dialog.showButton.setIcon(Images.getInstance().showPDFPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showPDFPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Display SSS Cont Payroll PDF Option");
				break;
			}
			
			
			//------------------------------------------------------------
			case Constant.EXPORT_DEPARTMENT_PAYROLL_DATA_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export Overall Summary Data");
				
				break;
			}
			case Constant.EXPORT_ASEMCO_DATA_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export ASEMCO Data");
				
				break;
			}
			case Constant.EXPORT_BCCI_DATA_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export BCCI Data");
				
				break;
			}
			case Constant.EXPORT_OCCCI_DATA_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export OCCCI Data");
				
				break;
			}
			case Constant.EXPORT_DBP_DATA_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export DBP Data");
				
				break;
			}
			case Constant.EXPORT_CITY_SAVINGS_DATA_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export City Savings Data");
				
				break;
			}
			case Constant.EXPORT_ST_PLAN_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export St. Peter Plan Data");
				
				break;
			}
			case Constant.EXPORT_W_TAX_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export Witholding Tax Data");
				
				break;
			}
			case Constant.EXPORT_LBP_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export LBP Data");
				
				break;
			}
			case Constant.EXPORT_SSS_LOAN_DATA_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export SSS Loan Data");
				
				break;
			}
			case Constant.EXPORT_PAGIBIG_LOAN_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export PAG-IBIG Loan Data");
				
				break;
			}
			case Constant.EXPORT_UNION_DUES_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export Union Dues Data");
				
				break;
			}
			
			case Constant.EXPORT_HDMF_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export HDMF Data");
				
				break;
			}
			case Constant.EXPORT_MEDICARE_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export Medicare Data");
				
				break;
			}
			case Constant.EXPORT_SSS_CONT_EXCEL:{
				dialog.showButton.setIcon(Images.getInstance().showExcelPayrollImg);
				dialog.showButton.setRolloverIcon(Images.getInstance().showExcelPayrollImgHover);
				payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setTitle("Export SSS Cont. Data");
				
				break;
			}
			default:
				break;
		}
		
		//--> Set necessary UI components
//		payrollViewPanel.setVisibleAddDeletePayrollDateBtns(false);
		dialog.setVisible(true);
	}
	
	/**
	 * Execute process that will happen when display slip is clicked
	 */
	private void processDisplaySlipPDF(){
		System.out.println(THIS_CLASS_NAME+"Display Payslip"+CLASS_NAME);
		
		//--> Checks first if you can display Payslip PDF.
		if(canDisplayPayslipPDF){
			Database db=Database.getInstance();
			ReusableTable table=payrollViewPanel.fullScreenTable;
			Utilities util= Utilities.getInstance();
			ArrayList<PayslipDataStorageInfo> payslipDataStorageList=payrollViewPanel.payslipDataStorageList;
			String payrollDateEnds="",payrollDateStarts="";
			int [] selectedIndexList=Utilities.getInstance().getSelectedIndexListBasedFromTableConvertedToModel(table);
		
			
			//--> This makes sure that the user select a payroll data.
			if(selectedIndexList!=null && selectedIndexList.length>0){
				payslipDataStorageList.clear();
				conditionColumnAndValueList_Deduction.clear();
				conditionColumnAndValueList_Earning.clear();
				
				//----------------------------------------------------------------------------
//	asasa
				//--> Why earningIndex and deductionIndex is 9? since the deductionId/earningID is at the last column of payroll view table, which was hidden
				int earningIndex=9,deductionIndex=9;
//				if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
//					earningIndex=8;
//					deductionIndex=8;
//				}
				for(int i=0;i<selectedIndexList.length;i++){
					payslipDataStorageList.add(new PayslipDataStorageInfo(
							db,util,
							(table.getModel().getValueAt(selectedIndexList[i], earningIndex)==null)?"":table.getModel().getValueAt(selectedIndexList[i], earningIndex).toString(),
							(table.getModel().getValueAt(selectedIndexList[i], deductionIndex)==null)?"":table.getModel().getValueAt(selectedIndexList[i], deductionIndex).toString(),
							table.getModel().getValueAt(selectedIndexList[i], 0).toString()
						)
							
					);
					
					PayslipDataStorageInfo payslipDataStorageInfo=payslipDataStorageList.get(i);
					
					//----------------------------------------------------------------------------
					//--> Get the payroll start first. This if statement will make suree that the process of finding the value of periodEnd and periodStart is when the value of periodEnd changes in the selected row table
					if(!payrollDateEnds.equals(table.getModel().getValueAt(selectedIndexList[i], 0).toString())){
						payrollDateEnds=table.getModel().getValueAt(selectedIndexList[i], 0).toString();
						payrollDateStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDateEnds);
					}
					
					
					//--> Add employee data located at the top of payslip.
					payslipDataStorageInfo.addEmployeeData(selectedIndexList, table, i,payrollDateStarts,payrollDateEnds,util);
					
					//----------------------------------------------------------------------------
					
					//--> Add conditions for database for EARNING data.
					//			Why 5? since the earningID is at index column 5 in the table.
					if(table.getModel().getValueAt(selectedIndexList[i], earningIndex)!=null){
						//> Get the needed deduction ID and store it to condition.
						conditionColumnAndValueList_Earning.add(new SelectConditionInfo(
								db.earningTableColumnNames[0],
								table.getModel().getValueAt(selectedIndexList[i], earningIndex).toString()
						));
						
					}
					
					//----------------------------------------------------------------------------
					
					//--> Add conditions for database for DEDUCTION data. 
					//			Why 6? since the deductionID is at index column 6 in the table.
					//> Makes sure that deduction data is not null, that there is a deduction data.
					if(table.getModel().getValueAt(selectedIndexList[i], deductionIndex)!=null){
						//> Get the needed deduction ID and store it to condition.
						conditionColumnAndValueList_Deduction.add(new SelectConditionInfo(
								db.deductionTableColumnNames[0],
								table.getModel().getValueAt(selectedIndexList[i], deductionIndex).toString()
						));
					}
					
					//----------------------------------------------------------------------------
					
					
				}
			
				
				//--> For debugging purposes
				System.out.println("\tSelected Index List DEDUCTION ID: "+CLASS_NAME);
				for(int i=0;i<selectedIndexList.length;i++){
					System.out.println("\t\t"+selectedIndexList[i]
							+"\t"+table.getModel().getValueAt(selectedIndexList[i], 6)+CLASS_NAME);
				}
				System.out.println("\tStored Deduction condition:"+CLASS_NAME);
				for(SelectConditionInfo condInfo:conditionColumnAndValueList_Deduction){
					System.out.println("\t\t Key: "+condInfo.getColumnName()
							+"\tValue: "+condInfo.getValue()+CLASS_NAME);
				}
				
				
				try{
					//----------------------------------------------------------------------------
					
					//> Retrieve EARNING data from database based from the given 
					if(conditionColumnAndValueList_Earning.size()>0){
						db.selectDataInDatabase(
								new String[]{(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
										db.tableNameEarnings:db.tableNameEarningsContractual},
								null,
								conditionColumnAndValueList_Earning,
								null,
								null,
								Constant.SELECT_ALL_WITH_CONDITION_OR);
						db.resultSet.last();
						int totalNumOfRows=db.resultSet.getRow();
						db.resultSet.beforeFirst();
						for(int i=0;i<totalNumOfRows;i++){
							db.resultSet.absolute(i+1);
							PayslipDataStorageInfo payslipDataStorageInfo=payrollViewPanel.getPayslipStorageInfoBasedFromDeductionOrEarningID(
									db, db.resultSet.getObject(1).toString(), Constant.EARNING_MODE);
							
							payslipDataStorageInfo.addDeductionOREarningData(
								db, i, 
								(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
									db.earningTableColumnNames:db.earningsContractualColumnNames,
								Constant.EARNING_MODE
							);		
						}
					}
					
					//----------------------------------------------------------------------------
					
					//> Retrieve DEDUCTION data from database based from the given 
					if(conditionColumnAndValueList_Deduction.size()>0){
						
						if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
							String [] neededDeductionColumns=new String[db.deductionTableColumnNames.length];
							for(int i=0;i<neededDeductionColumns.length;i++){
								neededDeductionColumns[i]="`"+db.deductionTableColumnNames[i]+"`";
							}
							db.selectDataInDatabase(
								new String[]{db.tableNameDeductions},
								neededDeductionColumns,
								conditionColumnAndValueList_Deduction,
								null,
								null,
								Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR);
						}
						else{
							String [] neededDeductionContractualColumns=new String[db.deductionsContractualColumnNames.length];
							
							for(int i=0;i<neededDeductionContractualColumns.length;i++){
								neededDeductionContractualColumns[i]="`"+db.deductionsContractualColumnNames[i]+"`";
							}
							db.selectDataInDatabase(
								new String[]{db.tableNameDeductions},
								neededDeductionContractualColumns,
								conditionColumnAndValueList_Deduction,
								null,
								null,
								Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR);
						}
						db.resultSet.last();
						int totalNumOfRows=db.resultSet.getRow();
						db.resultSet.beforeFirst();
						for(int i=0;i<totalNumOfRows;i++){
							db.resultSet.absolute(i+1);
							PayslipDataStorageInfo payslipDataStorageInfo=payrollViewPanel.getPayslipStorageInfoBasedFromDeductionOrEarningID(
									db, db.resultSet.getObject(1).toString(), Constant.DEDUCTION_MODE);
							
							
							payslipDataStorageInfo.addDeductionOREarningData(
								db,i,
								(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
									db.deductionTableColumnNames:db.deductionsContractualColumnNames,
									Constant.DEDUCTION_MODE
							);		
						}
					}
					
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("\t"+e.getMessage()+CLASS_NAME);
					e.printStackTrace();
				}
				
			
				
				//--> Create and show pdf
				PDFCreator.getInstance().viewPDF(
						payslipDataStorageList,
						payrollViewPanel.payrollSlip,
						Constant.PAYSLIP_PDF);
				
				
				//--> Clear this data structure after display to avoid too much use of memory
				payslipDataStorageList.clear();
				conditionColumnAndValueList_Deduction.clear();
				conditionColumnAndValueList_Earning.clear();
				
				
				//--> Garbage collection
				System.gc();
			}
			else{
				mainFrame.showOptionPaneMessageDialog("Please select 1 or more payroll data.", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			mainFrame.showOptionPaneMessageDialog("You can't display payslip PDF. Please select Show Payroll Mode.", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Execute process that will happen when the dislay payroll data dialog show with 
	 * 		payroll date combo box is clicked. 
	 */
	private void processDisplayPayrollDataDialogPDFExcel(int dispPDFExcelMode){
		
		
		DisplayOptionsDialog dialog=payrollViewPanel.dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		Utilities util = Utilities.getInstance();
		if(dialog.payrollDateComboBox.getSelectedObjects().length>0
				&& !dialog.payrollDateComboBox.getSelectedItem().toString().equals("")){

			switch(util.payrollSystemMode){
				case Constant.PAYROLL_SYSTEM_MODE_REGULAR:{
					if(dispPDFExcelMode >= Constant.PAYROLL_OVERALL_EXCEL){
						ExcelCreator.getInstance().viewExcel(dispPDFExcelMode, mainFrame);
					}
					else{
						PDFCreator.getInstance().viewPDF(
								null,
								payrollViewPanel.payrollSlip,
								dispPDFExcelMode);
					}
					break;
				}
				case Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL:{
					payrollViewPanel.withOrWithoutATMDialog.dispPDFExcelMode=dispPDFExcelMode;
					payrollViewPanel.withOrWithoutATMDialog.selectButton.setEnabled(true);
					payrollViewPanel.withOrWithoutATMDialog.setVisible(true);
					break;
				}
			}
			
		}
		else if(dialog.payrollDateComboBox.getSelectedObjects().length==0){
			mainFrame.showOptionPaneMessageDialog("Please add payroll date", JOptionPane.ERROR_MESSAGE);
		}
		else{
			mainFrame.showOptionPaneMessageDialog("Please don't leave any empty fields", JOptionPane.ERROR_MESSAGE);
		}
		
		//--> Set necessary UI components
		payrollViewPanel.topRightButtonPanel.setVisible(false);
	}
	 
	/**
	 * Execute process that will happen when the dislay EMPLOYEE payroll data is clicked. 
	 */
	private void processDisplayEmployeePayrollDataPDF(){
		System.out.println(THIS_CLASS_NAME+"Display EMPLOYEE payroll data."+CLASS_NAME);
		
		DisplayOptionsDialog dialog=payrollViewPanel.dispEmployeePayrollSummaryOptionPDFEXCEL;
		if(dialog.payrollDateComboBox.getSelectedObjects().length>0 &&
				!dialog.payrollDateComboBox.getSelectedItem().toString().isEmpty() &&
				!dialog.departmentComboBox.getSelectedItem().toString().isEmpty()){
		
			
			switch(payrollDispPDFEXCELBtnClicked){
				case Constant.DISPLAY_EMPLOYEE_PAYROLL_DATA_PDF:{
					PDFCreator.getInstance().viewPDF(
							null,
							payrollViewPanel.payrollSlip,
							Constant.PAYROLL_PER_DEPARTMENT_PDF);
					
					break;
				}
				case Constant.EXPORT_EMPLOYEE_PAYROLL_DATA_EXCEL:{
					ExcelCreator.getInstance().viewExcel(Constant.PAYROLL_PER_DEPARTMENT_EXCEL,mainFrame);
					break;
				}
			}
			
			
			
		}
		else if(dialog.payrollDateComboBox.getSelectedObjects().length==0){
			mainFrame.showOptionPaneMessageDialog("Please add payroll date.", JOptionPane.ERROR_MESSAGE);
		}
		else{
			mainFrame.showOptionPaneMessageDialog("Please don't leave any empty fields", JOptionPane.ERROR_MESSAGE);
		}
		
		//--> Set necessary UI components
		payrollViewPanel.topRightButtonPanel.setVisible(false);
	}
	
	/**
	 * Execute process that will happen when the OK button in display
	 * 		payroll option is clicked
	 */
	private void processShowPayrollData(){
		System.out.println(THIS_CLASS_NAME+"Show payroll DATA."+CLASS_NAME);
		Utilities util= Utilities.getInstance();
		Database db= Database.getInstance();
		
		conditionColumnAndValueList.clear();
		
		//--> Checks if there is an existing payroll date.
		if(payrollViewPanel.payrollDateComboBox.getSelectedObjects().length==0){
			mainFrame.showOptionPaneMessageDialog("Please add a payroll date.", JOptionPane.ERROR_MESSAGE);
		}
		else{
			//--> Checks if the selected item in department combobox is NOT! all
			if(!payrollViewPanel.departmentComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
				conditionColumnAndValueList.add(new SelectConditionInfo(
						db.departmentTableColumnNames[1],
						payrollViewPanel.departmentComboBox.getSelectedItem().toString()
				));
			}
			//--> Checks if the selected item in payrolldate combobox is NOT! all
			if(!payrollViewPanel.payrollDateComboBox.getSelectedItem().toString().equals(Constant.STRING_ALL)){
				conditionColumnAndValueList.add(new SelectConditionInfo(
						db.tableNamePayrollDate+"."+db.payrollDateTableColumnNames[0],
						util.convertDateReadableToYyyyMmDdDate(payrollViewPanel.payrollDateComboBox.getSelectedItem().toString())
				));
			}
			
			
			//--> Add condition depending if Regular and Contractual
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"HiredAs",
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
				)
			);
			
			
			//--> If the selected item of all the comboboxed is ALL, then reuse the code for showing all data.
			if(conditionColumnAndValueList.size()==0){
				processShowPayrollDataSummary(conditionColumnAndValueList);
			}
			else{
				processShowPayrollDataSummary(conditionColumnAndValueList);
				
			}
			
			//--> Update Row Count Label
			payrollViewPanel.rowCountLabel.setText("Row Count: "+payrollViewPanel.fullScreenTable.getModel().getRowCount());
			
			
			//--> Update immediately with search mechanism.
			PayrollViewPanel.getInstance().executeSearchMechanismOfAllTables();

			
			//--> Set necessary UI components
			payrollViewMode=Constant.SHOW_PAYROLL_DISPLAY_OPTIONS;
			payrollViewPanel.topRightButtonPanel.setVisible(true);
			payrollViewPanel.updateTopRightButtonPanel(4);
			payrollViewPanel.payrollDateDataLabel.setIcon(Images.getInstance().payrollDATALabelImg);
			payrollViewPanel.lockOptionButton.setVisible(false);
			
			if(payrollViewPanel.fullScreenTable.getModel().getRowCount()==0){
				payrollViewPanel.optionPanel.setVisible(false);
				
				// No need since in processShowPayrollDataSummary, it has already. To avoid two dialog prompts.
//				mainFrame.showOptionPaneMessageDialog("There are no available data.", JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
	}
	
	
	
	/**
	 * Execute process when YES is clicked in the delete warning dialog.
	 */
	private void processDeletePayrollDate(){
		System.out.println(THIS_CLASS_NAME+"Delete Payroll Date"+CLASS_NAME);
		
		Utilities util=Utilities.getInstance();
		ReusableTable table=payrollViewPanel.fullScreenTable;
		int [] selectedTableRowIndex=util.convertRowIndexToModel(table, table.getSelectedRows());
		Database db=Database.getInstance();
		String[]values=new String[selectedTableRowIndex.length];
		boolean isNoError=false;
		
		//---------------------------------------------------------------------------
		
		
		for(int i=0;i<selectedTableRowIndex.length;i++){
			
			values[i] = table.getModel().getValueAt(selectedTableRowIndex[i], 0).toString(); // Get the PayrollDate data
			isNoError=true;
		}
		
		// Continue if no error above.
		if(isNoError){
			db.deleteDataInDatabase(db.tableNamePayrollDate,
				db.payrollDateTableColumnNames[0], //Get the PayrollDate
				values);
			
	
			
			//--> If successfully deleted
			if(db.isDelete){
				// Set necessary UI components
				payrollViewPanel.deletePayrollWarningDialog.dispose();
				
				//--> Popup Successful Delete!
				mainFrame.showOptionPaneMessageDialog(
				    		(values.length==1)?
				    				"A payroll date and all earning and deduction data \nmade during that payroll date were deleted successfully!":
				    				"Payroll Dates and all earning and deduction data \nmade during those payroll dates were deleted successfully! Number of data entries deleted: "+values.length, 
				    		JOptionPane.INFORMATION_MESSAGE);
				
				
				
				System.out.println("\tReuse the code process show employee data summary!!!"+CLASS_NAME);
				
				// Reuse code
				processShowPayrollDate();
				
				// Update all payroll date
				util.updateAllUIPayrollDateComoboxes(db);
				
				
			}
		}
	}
	/**
	 * Execute process when SAVE button is clicked.
	 */
	private void processSavePayrollDate(){
		System.out.println(THIS_CLASS_NAME+"SAVE Payroll Date"+CLASS_NAME);
		
		
		AddPayrollDateDialogLayout ad=payrollViewPanel.addPayrollDateDialog;
		Database db= Database.getInstance();
		Utilities util=Utilities.getInstance();
		
		//----------------------------------
//		//--> To update the resultset and metadata contents which is used in insert query method.
//		OrderByInfo orderInfo=new OrderByInfo(
//				new String[]{db.payrollDateTableColumnNames[0]},
//				"DESC"
//		);
//		
//		db.selectDataInDatabase(new String[]{db.tableNamePayrollDate}, null, null, null,orderInfo, Constant.SELECT_ALL);
		
		//----------------------------------
		
		if(!ad.payrollDatePicker.getJFormattedTextField().getText().isEmpty() && 
				!ad.payrollDateStartPeriodPicker.getJFormattedTextField().getText().isEmpty()){
			db.insertDataInDatabase(
					db.tableNamePayrollDate,
					db.payrollDateTableColumnNames,
					new String[][]{{ad.payrollDatePicker.getJFormattedTextField().getText(),ad.payrollDateStartPeriodPicker.getJFormattedTextField().getText(),Constant.NOT_LOCKED_STATUS}}
			);
			
			if(db.isInsert){
				ad.dispose();
				
				//--> Popup Inserted Successfully!
				 MainFrame.getInstance().showOptionPaneMessageDialog(
				    		"A new payroll date was added successfully!",
				    		JOptionPane.INFORMATION_MESSAGE);
				
				// Reuse code:
				processShowPayrollDate();
				
				// Update all payroll date comboboxes
				util.updateAllUIPayrollDateComoboxes(db);
			}
		}
		else{
			mainFrame.showOptionPaneMessageDialog("Please don't leave empty fields.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Process the action when you click the department combo box.
	 */
	private void processDepartmentComboBox(){
		ShowAllEmployeeAddPayrollDataDialogLayout dialog = payrollViewPanel.showAllEmpAddPayDaDialog;
		
		if(dialog.departmentComboBox.getSelectedItem()!=null){
			String chosenComboBoxItem=dialog.departmentComboBox.getSelectedItem().toString();				
			Database db=Database.getInstance();
			Utilities util = Utilities.getInstance();
			
			//---------------------------------------------------------------------------------
			//--> Process the JoinList for InnerJoin
			String[] joinColumnCompareList={
					(db.tableNameEmployee+"."+db.departmentTableColumnNames[0])+"="+(db.tableNameDepartment+"."+db.departmentTableColumnNames[0])
			};
			
			//---------------------------------------------------------------------------------
			//-->Add the needed columns and values for condition. 
			//		db.employeeTableColumnNames[12]<-> Department.
			ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"HiredAs",
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
				)
			);
			
			
			if(!chosenComboBoxItem.equals(Constant.STRING_ALL)){
				conditionColumnAndValueList.add(new SelectConditionInfo(
						db.departmentTableColumnNames[1],
						chosenComboBoxItem));
			}
			//---------------------------------------------------------------------------------
			//-->Process Order 
			OrderByInfo orderInfo= new OrderByInfo(
				new String[]{
					db.employeeTableColumnNames[2],	// LastName
					db.employeeTableColumnNames[3] // First Name
				}, 
						
				"ASC"
			);
			
			
			//---------------------------------------------------------------------------------
			
			//--> Get data from Database.
			db.selectDataInDatabase(new String[]{db.tableNameEmployee,db.tableNameDepartment}, 
					new String[]{db.employeeTableColumnNames[0],
						db.employeeTableColumnNames[2],
						db.employeeTableColumnNames[3],
						db.employeeTableColumnNames[4]} ,
					conditionColumnAndValueList,
					joinColumnCompareList,
					orderInfo,
					Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND);
			
			
			dialog.employeeListTable.updateTable(db);
			dialog.updateRowCountLabel(dialog.employeeListTable.getModel().getRowCount());
		}	
	}
	
	/**
	 * Execute process when you click the button on ShowEMployeeDialog add button
	 * Execute the process that will happen when add deduction data button is clicked.
	 */
	private void processAddPayrollDATA(){
		System.out.println(THIS_CLASS_NAME+"Add Deduction/Earning Data"+CLASS_NAME);
		
		Utilities util = Utilities.getInstance();
		AddEarningOrDeductionDataLayout addEarningOrDeductionDataDialog=payrollViewPanel.addEDDataDialog;
		//--> Get the table selected and the row. 
		ReusableTable table= payrollViewPanel.showAllEmpAddPayDaDialog.employeeListTable;
		//--> Get an array of selected row indices/index.
		int [] selectedTableRowIndexList=util.getSelectedIndexListBasedFromTableConvertedToModel(table);
		
		
		
		//--------------------------------------------------------------------------------------------
		if(selectedTableRowIndexList==null || selectedTableRowIndexList.length==0){
			mainFrame.showOptionPaneMessageDialog(
					"Please select an employee", 
					JOptionPane.ERROR_MESSAGE
				);
		}
		else if(addEarningOrDeductionDataDialog.payrollDateCombobox.getSelectedItem()==null){
			mainFrame.showOptionPaneMessageDialog(
					"Can't add, please add payroll date data.", 
					JOptionPane.ERROR_MESSAGE
				);
		}
		else{	
			
			if(selectedTableRowIndexList.length==1){
				int viewRow = table.getSelectedRow(); // viewRow is the row in view only.
				int modelRow = 
	                    table.convertRowIndexToModel(viewRow); // modelRow is the REAL row selected not based in view.
				
				//--> Set the important strings when this dialog is clicked.
				addEarningOrDeductionDataDialog.employeeIDTextField.setText(table.getModel().getValueAt(modelRow, 0).toString());
				addEarningOrDeductionDataDialog.employeeNameTextField.setText(table.getModel().getValueAt(modelRow, 2).toString()+", "+table.getModel().getValueAt(modelRow, 3).toString());
			}
			else{
				//--> Set the important strings when this dialog is clicked.
				addEarningOrDeductionDataDialog.employeeIDTextField.setText("Multiple");
				addEarningOrDeductionDataDialog.employeeNameTextField.setText(selectedTableRowIndexList.length+" employees");
			}
	
			
			//--> Set already chosen in payroll date combobox.
			alreadyChosenPayrollDateComboboxAddPayrollDATA=addEarningOrDeductionDataDialog.payrollDateCombobox.getSelectedItem().toString();
			
			
			//-> Set enabled necessary UI components.
			addEarningOrDeductionDataDialog.setVisible(true);
			
			
		}
	}
	
	/**
	 * Execute process that will happen when add deduction data button is clicked.
	 */
	private void processSavePayrollDATA(){
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		AddEarningOrDeductionDataLayout addEarningOrDeductionDataDialog=payrollViewPanel.addEDDataDialog;
		//--> Get the table selected and the row. 
		ReusableTable table= payrollViewPanel.showAllEmpAddPayDaDialog.employeeListTable;
		//--> Get an array of selected row indices/index.
		int [] selectedTableRowIndexList=util.getSelectedIndexListBasedFromTableConvertedToModel(table);
				
				
		
		String payrollDateConvertToYyyyMmDd=(addEarningOrDeductionDataDialog.payrollDateCombobox.getSelectedItem().toString().isEmpty())? 
				null
				:
				util.convertDateReadableToYyyyMmDdDate(addEarningOrDeductionDataDialog.payrollDateCombobox.getSelectedItem().toString())
				;
		

		int i=0;
		for(;i<2;i++){
			
			System.out.println("\n\t Within Loop int I: "+i+CLASS_NAME);
			
			
			String preferredTableName=(i==0)?
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.tableNameEarnings:db.tableNameEarningsContractual
				:
				db.tableNameDeductions;
			String[] preferredColumnNameList=(i==0)?
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.earningTableColumnNames:db.earningsContractualColumnNames
				:
				db.deductionTableColumnNames;
			
			
			if(addEarningOrDeductionDataDialog.payrollDateCombobox.getSelectedObjects().length==0){
				mainFrame.showOptionPaneMessageDialog(
						"Please add a payroll date.", 
						JOptionPane.ERROR_MESSAGE);
				break;
			}
			else if(addEarningOrDeductionDataDialog.payrollDateCombobox.getSelectedItem().toString().isEmpty()){
				mainFrame.showOptionPaneMessageDialog(
						"Please input a payroll date", 
						JOptionPane.ERROR_MESSAGE);
				break;
				
			}
			else{
				System.out.println("\t Set Earning Deduction ID Values: "+CLASS_NAME);
				//--> Add the earning/deduction ID.
				Object[][] values=new Object[selectedTableRowIndexList.length][preferredColumnNameList.length];
				
				for(int r=0;r<selectedTableRowIndexList.length;r++){
					int tableRowIndex=selectedTableRowIndexList[r];
					String employeeID= table.getModel().getValueAt(tableRowIndex, 0).toString();// Why 0, since the EmployeeID column is at index 0.
					String earndedID=employeeID+"-"+payrollDateConvertToYyyyMmDd.replaceAll("-", "");
					
					System.out.println("\t\t EmployeeID: "
							+"\t EarnDedID: "+earndedID
							+"\t PayrollDate: "+payrollDateConvertToYyyyMmDd+employeeID+CLASS_NAME);
					
					values[r][0]=earndedID; // EarningID/DeductionID
					values[r][1]=payrollDateConvertToYyyyMmDd; // Payroll Date
					values[r][2]=employeeID; // EmployeeID
					
					//--> Assign the rest with values null.
					for(int j=3;j<preferredColumnNameList.length;j++){
						values[r][j]=Double.parseDouble("0");
						
						// Default Value of Comment/Note Text Area
						if(preferredColumnNameList[j].equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-1])){
							values[r][j]="-";
						}
					}
				}

		
				//--> Insert Earning, Deduction. Insert the values in database.
				db.insertDataInDatabase(
						preferredTableName,
						preferredColumnNameList,
						values);
				
				
				
				//--> If Insert data fails.
				if(!db.isInsert){
					break;
				}
				
			}	
		
		}
		
	
		
		//--> Insert Employer Share Data in Database.
		//> Makes sure that the insert of deduction data is successful before adding employer share data.
		if(db.isInsert && payrollDateConvertToYyyyMmDd!=null && i>=2){
			System.out.println("\t Set Employer Share ID Values: "+CLASS_NAME);
			
			
			
			//--> Add the earning/deduction ID.
			Object[][] values=new Object[selectedTableRowIndexList.length][db.employerShareTableColumnNames.length];
			for(int r=0;r<selectedTableRowIndexList.length;r++){
				int tableRowIndex=selectedTableRowIndexList[r];
				String employeeID= table.getModel().getValueAt(tableRowIndex, 0).toString();// Why 0, since the EmployeeID column is at index 0.
				String earndedID=employeeID+"-"+payrollDateConvertToYyyyMmDd.replaceAll("-", "");
				
				values[r][0]=earndedID; // EarningID/DeductionID
				values[r][1]=payrollDateConvertToYyyyMmDd; // Payroll Date
				values[r][2]=employeeID; // EmployeeID
				
				//--> Assign the rest with values null.
				for(int j=3;j<db.employerShareTableColumnNames.length;j++){
					values[r][j]=Double.parseDouble("0");
				}
			}
			
			//--> Insert the values in database.
			db.insertDataInDatabase(
					db.tableNameEmployerShare, 
					db.employerShareTableColumnNames,
					values);
		}
		
		
		else{
			
			 MainFrame.getInstance().showOptionPaneMessageDialog(
			    		"Error on adding payroll data!",
			    		JOptionPane.ERROR_MESSAGE);
		}
		
		
		//----------------------------------------------------
		//--> If 1/ALL insert is/are successful!
		if(db.isInsert && i>=2){	
			//--> Dispose the Add Deduction Data dialog.
			addEarningOrDeductionDataDialog.dispose();
			
			//--> Popup Inserted Successfully!
			 MainFrame.getInstance().showOptionPaneMessageDialog(
					(selectedTableRowIndexList.length==1)?
			    		"A new employee payroll data was added successfully!":selectedTableRowIndexList.length+" payroll Data were added successfully!" ,
			    		JOptionPane.INFORMATION_MESSAGE);
			 
			
			//--> Reuse the code.
			switch(payrollViewMode){
				case Constant.SHOW_PAYROLL_SUMMARY:{
					processShowPayrollDataSummary(null);
					break;
				}
				case Constant.SHOW_PAYROLL_DISPLAY_OPTIONS:{
					processShowPayrollData();
					break;
				}
			}
			
			
		}
		
	}

	/**
	 * Execute process when you want to delete a payroll employee data.
	 */
	private void processDeletePayrollDATA(){
		System.out.println(THIS_CLASS_NAME+"Delete Payroll Deduction/Earning DATA"+CLASS_NAME);

		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		ReusableTable table=payrollViewPanel.fullScreenTable;
		//--> Get an array of selected row indices/index.
		int [] selectedTableRowIndexList=util.getSelectedIndexListBasedFromTableConvertedToModel(table);
		int countAllValues=0;
		String[]values=null;;
		
		//--> Loop to delete both earning and deduction data
		for(int z=0;z<2;z++){
			String[] preferredTableColumnNames=(z==0)?
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					db.earningTableColumnNames:db.earningsContractualColumnNames
				:
				db.deductionTableColumnNames;
			String preferredTableName=(z==0)?
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
						db.tableNameEarnings:db.tableNameEarningsContractual
				:
				db.tableNameDeductions;
			
			
				
			values=new String[selectedTableRowIndexList.length];
			
			for(int i=0;i<selectedTableRowIndexList.length;i++){
				//--> We use the index 9 since we want to retrieve the last column data which is the deduction/earning ID.
				values[i] = table.getModel().getValueAt(selectedTableRowIndexList[i], 9).toString();// Why 5? EarningID, does not matter, because EarningID and DeductionID is the same.
			}
			
			//--> Delete data from database.
			db.deleteDataInDatabase(preferredTableName,
				preferredTableColumnNames[0],  
				values);
			
			countAllValues=values.length;
			//--> If error in delete process, break loop.
			if(!db.isDelete){
				
				break;
			}
		}
		
		//--> Making sure that deletion of deduction/earning data is successful, else wont delete employer share data.
		if(db.isDelete && values!=null){
			//--> Delete data from database.
			db.deleteDataInDatabase(db.tableNameEmployerShare,
				db.employerShareTableColumnNames[0],  
				values);
		}
		
		
		//--> When deleting deduction/earning data AND employer share data are successfully deleted.
		if(db.isDelete){	
			//--> Set necessary UI components
			payrollViewPanel.deletePayrollWarningDialog.dispose();
			
			//--> Popup Successful Delete!
			mainFrame.showOptionPaneMessageDialog(
			    		(countAllValues==1)?
			    				"An employee payroll data was deleted successfully!":
			    				"Employee payroll data were deleted successfully! Number of data entries deleted: "+countAllValues, 
			    		JOptionPane.INFORMATION_MESSAGE);
			
			//--> Update UI Table.
			System.out.println("\tREUSE the show payroll data summary code!."+CLASS_NAME);
			
			//--> Reuse the code.
			switch(payrollViewMode){
				case Constant.SHOW_PAYROLL_SUMMARY:{
					processShowPayrollDataSummary(null);
					break;
				}
				case Constant.SHOW_PAYROLL_DISPLAY_OPTIONS:{
					processShowPayrollData();
					break;
				}
			}
			
			
			//--> Make the selectedIndices variable to null to avoid array index out of bounds.
			selectedTableRowIndexList=null;
		}
		else{
			//--> Popup  Delete!
			mainFrame.showOptionPaneMessageDialog("Error in Deleting Payroll Data.", 
			    		JOptionPane.ERROR_MESSAGE);
		}

	}
	
	/**
	 * Execute when showPayslipDialog button is clicked.
	 */
	private void processShowPayslipDialog(){
		Utilities util = Utilities.getInstance();
	
		
		if(util.authorizationLevel==Constant.ADMIN_AUTHORIZATION_LEVEL ||
				util.authorizationLevel==Constant.USER_AUTHORIZATION_LEVEL){
			
			System.out.println(THIS_CLASS_NAME+"Show Payslip Dialog to see individual data."+CLASS_NAME);
			
			PayslipDataDialog dialog=payrollViewPanel.payslipDataDialog;
			Database db= Database.getInstance();
			ReusableTable table = payrollViewPanel.fullScreenTable;
//			int earningDeductionIDIndex=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
//					9:8;
			int earningDeductionIDIndex=9;
			int [] selectedIndexList=Utilities.getInstance().getSelectedIndexListBasedFromTableConvertedToModel(table);
			
			
			if(table.getSelectedRowCount()==0){
				mainFrame.showOptionPaneMessageDialog("Please select a payroll data.", JOptionPane.ERROR_MESSAGE);
			}
			else if(table.getSelectedRowCount()>1){
				mainFrame.showOptionPaneMessageDialog("Please select only 1 employee payroll data.", JOptionPane.ERROR_MESSAGE);
			}
			else{
				String []payrollDateList=new String[selectedIndexList.length];
				for(int i=0;i<payrollDateList.length;i++){
					int selectedRow=selectedIndexList[i];
					payrollDateList[i]=table.getModel().getValueAt(selectedRow,0).toString();
				}
				
				
				dialog.payslipPanel.addDeductionAndEarningSwingComponents(db,util);
				
				int selectedRow= selectedIndexList[0];
				String payrollDateEnds="",payrollDateStarts="";
				//----------------------------------------------------------------
				
				PayslipDataStorageInfo payslipData=new PayslipDataStorageInfo(
					db,Utilities.getInstance(),
					(table.getModel().getValueAt(selectedRow, earningDeductionIDIndex)==null)?"":table.getModel().getValueAt(selectedRow, earningDeductionIDIndex).toString(),
					(table.getModel().getValueAt(selectedRow, earningDeductionIDIndex)==null)?"":table.getModel().getValueAt(selectedRow, earningDeductionIDIndex).toString(),
					table.getModel().getValueAt(selectedRow, 0).toString()
				);
				
				//----------------------------------------------------------------------------
				//--> Get the payroll start first. This if statement will make suree that the process of finding the value of periodEnd and periodStart is when the value of periodEnd changes in the selected row table
				if(!payrollDateEnds.equals(table.getModel().getValueAt(selectedRow, 0).toString())){
					payrollDateEnds=table.getModel().getValueAt(selectedRow, 0).toString();
					payrollDateStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDateEnds);
				}
				
				//--> Add employee data located at the top of payslip.
				payslipData.addEmployeeData(selectedIndexList, table, 0,payrollDateStarts,payrollDateEnds,util);
				
				
				//----------------------------------------------------------------------------
				
				//--> Add conditions for database for EARNING/Deduction data.
				
				ArrayList<SelectConditionInfo>conditionColumnAndValueList_EARNING= new ArrayList<SelectConditionInfo>(),
						conditionColumnAndValueList_DEDUCTION= new ArrayList<SelectConditionInfo>();
					//> Get the needed earning/deduction ID and store it to condition.
				conditionColumnAndValueList_EARNING.add(new SelectConditionInfo(
						db.earningTableColumnNames[0],
						table.getModel().getValueAt(selectedRow, earningDeductionIDIndex).toString()
				));
				
				conditionColumnAndValueList_DEDUCTION.add(new SelectConditionInfo(
						db.deductionTableColumnNames[0],
						table.getModel().getValueAt(selectedRow, earningDeductionIDIndex).toString()
				));
				//----------------------------------------------------------------------------
				
				try{
					//----------------------------------------------------------------------------
					
					//> Retrieve EARNING data from database based from the given 
					if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
						db.selectDataInDatabase(
							new String[]{db.tableNameEarnings},
							null,
							conditionColumnAndValueList_EARNING,
							null,
							null,
							Constant.SELECT_ALL_WITH_CONDITION_OR
						);
					}
					else{
						db.selectDataInDatabase(
								new String[]{db.tableNameEarningsContractual},
								db.earningsContractualColumnNames,
								conditionColumnAndValueList_EARNING,
								null,
								null,
								Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR
							);
					}
					db.resultSet.last();
					int totalNumOfRows=db.resultSet.getRow();
					db.resultSet.beforeFirst();
					for(int i=0;i<totalNumOfRows;i++){
						db.resultSet.absolute(i+1);
					
						
						payslipData.addDeductionOREarningData(
							db, i,
							(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)? db.earningTableColumnNames:db.earningsContractualColumnNames,
							Constant.EARNING_MODE
						);		
					}
					
					//----------------------------------------------------------------------------
					
					//> Retrieve DEDUCTION data from database based from the given 
					if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
						String [] neededColumns=new String[db.deductionTableColumnNames.length];
						for(int i=0;i<neededColumns.length;i++){
							neededColumns[i]="`"+db.deductionTableColumnNames[i]+"`";
						}
						
						db.selectDataInDatabase(
							new String[]{db.tableNameDeductions},
							neededColumns,
							conditionColumnAndValueList_DEDUCTION,
							null,
							null,
							Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR
						);
					}
					else{
						String [] neededColumns=new String[db.deductionsContractualColumnNames.length];
						for(int i=0;i<neededColumns.length;i++){
							neededColumns[i]="`"+db.deductionsContractualColumnNames[i]+"`";
						}
						db.selectDataInDatabase(
							new String[]{db.tableNameDeductions},
							neededColumns,
							conditionColumnAndValueList_DEDUCTION,
							null,
							null,
							Constant.SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR
						);
					}
					db.resultSet.last();
					totalNumOfRows=db.resultSet.getRow();
					db.resultSet.beforeFirst();
					for(int i=0;i<totalNumOfRows;i++){
						db.resultSet.absolute(i+1);
						payslipData.addDeductionOREarningData(
							db,i,
							(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?db.deductionTableColumnNames:db.deductionsContractualColumnNames,
							Constant.DEDUCTION_MODE
						);		
					}
					
					
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("\t"+e.getMessage()+CLASS_NAME);
					e.printStackTrace();
				}
				
				
				//----------------------------------------------------------------------------
				//--> Set Necessary UI Fields.
				dialog.payslipPanel.addDataToTextFields(payslipData);
				payslipData=null;
				conditionColumnAndValueList_DEDUCTION=null;
				conditionColumnAndValueList_EARNING=null;
				
				
				//--> Set payslip fields and buttons editable or clicked depending if locked or not.
				dialog.setIfLockMode(true,db);
				if(db.isPayrollDateLocked(payrollDateList)){
					dialog.setIfLockMode(false,db);
				}
				
				
				dialog.setVisible(true);
			}
				
				
			
		}
		else{
			MainFrame.getInstance().showOptionPaneMessageDialog("You are not authorized to use this. Please contact your administrator.", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	private void  processCalculatePayslipDataDialog(){
		System.out.println(THIS_CLASS_NAME+"Calculate Payslip Data Dialog."+CLASS_NAME);
		
		PayslipDataDialog dialog= payrollViewPanel.payslipDataDialog;
		PayrollSlipLayout payslipData= dialog.payslipPanel;
		Utilities util = Utilities.getInstance();
		Database db = Database.getInstance();
		
		
		payslipData.updateTotalEarningDeductionNetPay(util,db,mainFrame);
		
		
		
	}
	
	private void  processSavePayslipDataDialog(){
		System.out.println(THIS_CLASS_NAME+"Save Payslip Data Dialog."+CLASS_NAME);	
		
		Database db=Database.getInstance();
		Utilities util = Utilities.getInstance();
		PayrollSlipLayout payslipData=payrollViewPanel.payslipDataDialog.payslipPanel;
		
		
//		//--> reuse code
		processCalculatePayslipDataDialog();
		
		
		if(payslipData.isCalculationPossible){
			ReusableTable table=payrollViewPanel.fullScreenTable;
			int earningDeductionIDIndex=(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?
					9:8;
			int [] selectedIndexList=Utilities.getInstance().getSelectedIndexListBasedFromTableConvertedToModel(table);
			String neededID=table.getModel().getValueAt(selectedIndexList[0], earningDeductionIDIndex).toString();
			
			
			HashMap<String, Object>changesToBeUpdated= new HashMap<String,Object>();
			
			
			//--------------------------------------------------------------
			//--> Update Earning Data
			changesToBeUpdated=payslipData.extractEarningDeductionDataToBeSaved(db, util, changesToBeUpdated, payslipData.earningPayslipSwingList);
			
			
			
			db.updateDataInDatabase(
					(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?db.tableNameEarnings:db.tableNameEarningsContractual,
					changesToBeUpdated,
					db.earningTableColumnNames[0],
					neededID,
					false,
					null);
			//--------------------------------------------------------------
			//--> Update Deduction Data
			
			if(db.isUpdate){
				changesToBeUpdated=payslipData.extractEarningDeductionDataToBeSaved(db, util, changesToBeUpdated, payslipData.deductionPayslipSwingList);
				// Save the comment
				int commentColumnIndex=db.deductionTableColumnNames.length-1;
				changesToBeUpdated.put(db.deductionTableColumnNames[commentColumnIndex],payslipData.getCommentTextArea().getText());
				
				
				
				
				db.updateDataInDatabase(db.tableNameDeductions, changesToBeUpdated,
						db.deductionTableColumnNames[0],
						neededID, false,
						null);
			}
			
			
			//-----------------------------------------------------------------
			if(db.isUpdate){
				mainFrame.showOptionPaneMessageDialog("You have updated the payslip data successfully!", JOptionPane.INFORMATION_MESSAGE);
			
				payrollViewPanel.payslipDataDialog.dispose();
				
				//--> Reuse COde. update payroll data
				processShowPayrollData();
			}
		}
		
	}
	
	private void  processCancelPayslipDataDialog(){
		System.out.println(THIS_CLASS_NAME+"Cancel Payslip Data Dialog."+CLASS_NAME);
		
		PayslipDataDialog dialog= payrollViewPanel.payslipDataDialog;
		
		dialog.dispose();
	}
	
	
	
	/**
	 * Execute the net pay copy mode.
	 */
	private void processNetPayCopyMode(){
		System.out.println(THIS_CLASS_NAME+"Execute Net Pay Copy Mode!"+CLASS_NAME);
		
		Utilities util = Utilities.getInstance();
		ReusableTable table = payrollViewPanel.fullScreenTable;
		
		//--> Set Net Pay editable
		String columnName=util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(table.getModel().getColumnCount()-2));
		
		table.setTableColumnsThatAreEditable(new String[]{columnName});
		table.updateTableStateNotContent();
		
		//--> Hide column
		table.hideColumns(new int[]{table.getModel().getColumnCount()-1}); // Why 9? since we hid the last column which is deductionID.
		
		
		//--> Set Necessary UI components
		payrollViewPanel.netPayCopyModeBtn.setVisible(false);
		payrollViewPanel.updateTopRightButtonPanel(4);
	}
	


	
	
	
	/**
	 * Cancel the net pay copy mode.
	 */
	private void processCancelNetPayCopyMode(){
		System.out.println(THIS_CLASS_NAME+"CANCEL Net Pay Copy Mode!"+CLASS_NAME);
		
		Utilities util = Utilities.getInstance();
		ReusableTable table = payrollViewPanel.fullScreenTable;
		
		
		//--> Reset values shown in table if the user accidentally changed the value. This must be for copy mode only.
		for(int i=0,netPayColIndex=table.getModel().getColumnCount()-2;i<table.getModel().getRowCount();i++){
			
			if(!table.getModel().data[i][netPayColIndex].toString().equals(table.getModel().copiedData[i][netPayColIndex].toString())){
				table.getModel().data[i][netPayColIndex]=table.getModel().copiedData[i][netPayColIndex];
			}
		}
		
		
		//--> Set All NOT editable
		table.getModel().columnsToBeEditedList.clear();
		table.updateTableStateNotContent();
		
		//--> Hide column
		table.hideColumns(new int[]{table.getModel().getColumnCount()-1}); // Why 9? since we hid the last column which is deductionID.
				
				
		//--> Set Necessary UI components
		payrollViewPanel.netPayCopyModeBtn.setVisible(true);
		payrollViewPanel.updateTopRightButtonPanel(4);
	}
	
	
	/**
	 * Process when PDF option buttons are clicked.
	 * @param e
	 */
	private void processOptionButtonsPDF(ActionEvent e){
		for(String key:payrollViewPanel.pdfPanel.buttonList.keySet()){
			JButton btn=payrollViewPanel.pdfPanel.buttonList.get(key);
			if(e.getSource()==btn){
				
				//--> Set necessary UI components.
				payrollViewPanel.pdfPanel.setVisible(false);
				
				
				switch(key){
					
					//--> DISPLAY PAYSLIP
					case Constant.DISPLAY_PAYSLIP_PDF:{
						processDisplaySlipPDF();
						break;
					}
					//--> DISPLAY EMPLOYEE PAYROLL DATA Option Dialog first.
					case Constant.DISPLAY_EMPLOYEE_PAYROLL_DATA_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_EMPLOYEE_PAYROLL_DATA_PDF;
						
						processShowDisplayEmployeePayrollDataPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY DEPARTMENT PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_DEPARTMENT_PAYROLL_DATA_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_DEPARTMENT_PAYROLL_DATA_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY ASEMCO PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_ASEMCO_DATA_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_ASEMCO_DATA_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY BCCI PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_BCCI_DATA_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_BCCI_DATA_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY OCCCI PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_OCCCI_DATA_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_OCCCI_DATA_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY DBP PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_DBP_DATA_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_DBP_DATA_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY CFI PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_CITY_SAVINGS_DATA_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_CITY_SAVINGS_DATA_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY ST. PLAN PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_ST_PLAN_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_ST_PLAN_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY WITHOLDING TAX PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_W_TAX_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_W_TAX_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY ST. PLAN PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_LBP_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_LBP_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY SSS LOAN PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_SSS_LOAN_DATA_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_SSS_LOAN_DATA_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY PAG-IBIG LOAN PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_PAGIBIG_LOAN_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_PAGIBIG_LOAN_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY UNION DUES PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_UNION_DUES_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_UNION_DUES_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY HDMF/Pagibig Cont PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_HDMF_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_HDMF_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY MEDICARE PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_MEDICARE_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_MEDICARE_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> DISPLAY SSS CONTRIBUTION PAYROLL DATA is clicked. Show the dialog first
					case Constant.DISPLAY_SSS_CONT_PDF:{
						payrollDispPDFEXCELBtnClicked=Constant.DISPLAY_SSS_CONT_PDF;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					default:
						break;
				}
				
				
				break;
			}
		}
	}
	
	
	/**
	 * Process when EXCEL option buttons are clicked.
	 * @param e
	 */
	private void processOptionButtonsEXCEL(ActionEvent e){
		for(String key:payrollViewPanel.excelPanel.buttonList.keySet()){
			JButton btn=payrollViewPanel.excelPanel.buttonList.get(key);
			if(e.getSource()==btn){
				
				//--> Set necessary UI components.
				payrollViewPanel.excelPanel.setVisible(false);
			
				switch(key){
					
					
					//--> EXPORT EMPLOYEE PAYROLL DATA EXCEL Option Dialog first.
					case Constant.EXPORT_EMPLOYEE_PAYROLL_DATA_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_EMPLOYEE_PAYROLL_DATA_EXCEL;
						
					
						processShowDisplayEmployeePayrollDataPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT DEPARTMENT PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_DEPARTMENT_PAYROLL_DATA_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_DEPARTMENT_PAYROLL_DATA_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						
						break;
					}
					//--> EXPORT ASEMCO PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_ASEMCO_DATA_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_ASEMCO_DATA_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT BCCI PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_BCCI_DATA_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_BCCI_DATA_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;	
					}
					//--> EXPORT OCCCI PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_OCCCI_DATA_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_OCCCI_DATA_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT DBP PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_DBP_DATA_EXCEL:{
						mainFrame.showOptionPaneMessageDialog("Coming Soon!", JOptionPane.ERROR_MESSAGE);
						break;
					}
					//--> EXPORT CFI PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_CITY_SAVINGS_DATA_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_CITY_SAVINGS_DATA_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT ST. PLAN PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_ST_PLAN_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_ST_PLAN_EXCEL;
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT WITHOLDING TAX PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_W_TAX_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_W_TAX_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT ST. PLAN PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_LBP_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_LBP_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT SSS LOAN PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_SSS_LOAN_DATA_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_SSS_LOAN_DATA_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT PAG-IBIG LOAN PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_PAGIBIG_LOAN_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_PAGIBIG_LOAN_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT UNION DUES PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_UNION_DUES_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_UNION_DUES_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//-->EXPORT HDMF/Pagibig Cont PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_HDMF_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_HDMF_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT MEDICARE PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_MEDICARE_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_MEDICARE_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					//--> EXPORT SSS CONTRIBUTION PAYROLL DATA is clicked. Show the dialog first
					case Constant.EXPORT_SSS_CONT_EXCEL:{
						payrollDispPDFEXCELBtnClicked=Constant.EXPORT_SSS_CONT_EXCEL;
						
						processShowDisplayWithPayrollDateComboboxPDFEXCELOptionDialog();
						break;
					}
					default:
						break;
				}
				
				
				break;
			}
		}
	}
	
	/**
	 * What happens when you click the Lock Button.
	 */
	private void processLockData(){
		System.out.println(THIS_CLASS_NAME+"LOCK Option Button is clicked!"+CLASS_NAME);
		
		Utilities  util= Utilities.getInstance();
		ReusableTable table = payrollViewPanel.fullScreenTable;
//		int earningDeductionIDIndex=9;
		int [] selectedIndexList=Utilities.getInstance().getSelectedIndexListBasedFromTableConvertedToModel(table);
		
		if(util.authorizationLevel==Constant.ADMIN_AUTHORIZATION_LEVEL ||
				util.authorizationLevel==Constant.USER_AUTHORIZATION_LEVEL){
			
		
			if(selectedIndexList.length==1){
				int payrollDateColumnIndex=0,lockedStatusColumnIndex=2; //Lock Status
				String lockStatus=table.getModel().getValueAt(selectedIndexList[selectedIndexList.length-1], lockedStatusColumnIndex).toString();
				String payrolldate=table.getModel().getValueAt(selectedIndexList[selectedIndexList.length-1], payrollDateColumnIndex).toString();
				
				
				
				if(lockStatus.equals(Constant.NOT_LOCKED_STATUS)){
					payrollViewPanel.lockedWarningDialog.lblNewLabel.setText("Are you sure you want to lock the payroll date: "+util.convertDateYyyyMmDdToReadableDate(payrolldate));
					payrollViewPanel.lockedWarningDialog.setVisible(true);
				}
				else{
					mainFrame.showOptionPaneMessageDialog("This payroll date is already locked.", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else if(selectedIndexList.length>1){
				mainFrame.showOptionPaneMessageDialog("Please select only 1 payroll date to be locked.", JOptionPane.ERROR_MESSAGE);
			}
	 		else{
				mainFrame.showOptionPaneMessageDialog("Please select a payroll date to be locked.", JOptionPane.ERROR_MESSAGE);
			}
	
		}
		else{
			mainFrame.showOptionPaneMessageDialog("You are not authorized to use this. Please contact your administrator.", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Checks if the button clicked is an export excel buttons
	 * @return
	 */
	private boolean isExportExcelButtonsClicked(){
		if(payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_EMPLOYEE_PAYROLL_DATA_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_DEPARTMENT_PAYROLL_DATA_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_ASEMCO_DATA_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_BCCI_DATA_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_OCCCI_DATA_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_DBP_DATA_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_CITY_SAVINGS_DATA_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_SSS_LOAN_DATA_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_PAGIBIG_LOAN_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_ST_PLAN_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_LBP_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_UNION_DUES_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_SSS_CONT_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_HDMF_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_MEDICARE_EXCEL) ||
				payrollDispPDFEXCELBtnClicked.equals(Constant.EXPORT_W_TAX_EXCEL) ){
			return true;
		}
		return false;
	}
	private void l___________________________________________________________________________l(){}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		ReusableTable table=payrollViewPanel.fullScreenTable;
		int [] selectedIndexList=Utilities.getInstance().getSelectedIndexListBasedFromTableConvertedToModel(table);
	
		//-- For debugging purposes
//		System.out.println("\tSelected Payroll Table Rows: "+CLASS_NAME);
//		for(int i=0;i<selectedIndexList.length;i++){
//			System.out.println("\t\t"+selectedIndexList[i]+CLASS_NAME);
//		}
		
		//--> Set necessary UI components
		payrollViewPanel.optionPanel.setVisible(false);
		payrollViewPanel.pdfPanel.setVisible(false);
		payrollViewPanel.excelPanel.setVisible(false);
		
		//--> Repaint to avoid overlap
		payrollViewPanel.repaint();
	}
	private void l______________________________________________________l(){}
	//--> Default methods in DocumentListener

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument()==PayrollViewPanel.getInstance().filterTextField.getDocument()){
			
			PayrollViewPanel.getInstance().executeSearchMechanismOfAllTables();
			
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument()==PayrollViewPanel.getInstance().filterTextField.getDocument()){
			
			PayrollViewPanel.getInstance().executeSearchMechanismOfAllTables();
			
		}
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument()==PayrollViewPanel.getInstance().filterTextField.getDocument()){
			
			PayrollViewPanel.getInstance().executeSearchMechanismOfAllTables();
			
		}		
		
	}
	private void l________________________________________l(){}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stubf

		//--> Repaint
		payrollViewPanel.repaint();
		
		//--> Set necessary UI components
		payrollViewPanel.optionPanel.setVisible(false);
		
	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void l_______________________________________________________________________________l(){}
	
	/**
	 * Set outside listeners to be used inside this class.
	 * @param mainFrameListener
	 */
	public void setOutsideListeners(ListenerMainFrame mainFrameListener){
		this.mainFrameListener=mainFrameListener;
	}

	
}
