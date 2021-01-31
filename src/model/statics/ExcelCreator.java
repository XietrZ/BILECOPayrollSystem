package model.statics;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import database.Database;
import model.Constant;
import model.OrderByInfo;
import model.PayrollPDFTotalValueInfo;
import model.PayslipDataStorageInfo;
import model.SelectConditionInfo;
import model.view.DisplayOptionsDialog;
import model.view.PayrollSlipLayout;
import model.view.WithOrWithoutATMDialog;
import view.MainFrame;
import view.views.PayrollViewPanel;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelCreator {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private WritableCellFormat timesLeft,
				timesCenterRightWithBorder,
				timesCenterLeftWithBorder,
				timesWrapTextCentreWithBorder;
	
	private WritableCellFormat 	timesBoldLeft,timesBoldCenter,
				timesBoldCenterWithBorder,
				timesBoldRightWithBorderMedium,
				timesBoldWrapTextWithBorderMedium;
	private static ExcelCreator instance;
	private boolean isSuccessCreatingEXCEL=false;
	private WritableWorkbook workbook;
	private String[] correctEarnColumnList=null,correctDedsColumnList= null;
	private ArrayList<PayrollPDFTotalValueInfo>totalValueList,departmentTotalValueList;;
	private String netPayColumnName="NetPay", signatureColumnName="Signature";
	private ArrayList<String> payrollHeaderList;
	private Vector<Integer> cellNumberRowSpanTwoList;
	private PayrollViewPanel payrollViewPanel;
	private void l___________________________________________l(){}
	 
	public ExcelCreator(){
		init();
	}
	
	private void init(){
		payrollViewPanel = PayrollViewPanel.getInstance();
		
		payrollHeaderList=new ArrayList<String>();
		cellNumberRowSpanTwoList=new Vector<Integer>();
		totalValueList=new ArrayList<PayrollPDFTotalValueInfo>();
		departmentTotalValueList=new ArrayList<PayrollPDFTotalValueInfo>();
		
		//-------------------------------------------------------------------------
		 
		initFontsCellFormat();
       
	}
	
	
	private void initFontsCellFormat(){
		
		initTimesLeft();
		initTimesCenterRightWithBorderFont();
		initTimesCenterLeftWithBorder();
		initTimesWrapTextCentreWithBorderFont();
       
	   	//-------------------------------------------------------------------------
		initTimesBoldLeftFont();
		initTimesBoldCenterFont();
		initTimesBoldCenterWithBorderFont();
		initTimesBoldRightWithBorderMedium();
		initTimesBoldWrapTextWithBorderMediumFont();
	   
	}
	private void initTimesLeft(){
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		timesLeft= new WritableCellFormat(times10pt);
	}
	
	
	/**
	 * Lets create a TIMESCENTER RIGHT WITH BORDER font
	 */
	private void initTimesCenterRightWithBorderFont(){
		// Lets create a TIMESCENTER RIGHT WITH BORDER font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		NumberFormat nf=new NumberFormat("#,##0.00;-#,##0.00");
		
		// Define the cell format
		timesCenterRightWithBorder = new WritableCellFormat(times10pt,
				nf
		);
		try {
			timesCenterRightWithBorder.setVerticalAlignment(VerticalAlignment.CENTRE);
			timesCenterRightWithBorder.setAlignment(Alignment.RIGHT);
			timesCenterRightWithBorder.setBorder(Border.ALL, BorderLineStyle.THIN,
	   		        Colour.BLACK);
			
		} catch (WriteException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	private void initTimesCenterLeftWithBorder(){
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		timesCenterLeftWithBorder= new WritableCellFormat(times10pt);
		try {
			timesCenterLeftWithBorder.setVerticalAlignment(VerticalAlignment.CENTRE);
			timesCenterLeftWithBorder.setAlignment(Alignment.LEFT);
			timesCenterLeftWithBorder.setBorder(Border.ALL, BorderLineStyle.THIN,
	   		        Colour.BLACK);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
	}
	/**
	 * Lets create a TIMESWRAPTEXT CENTER WITH BORDER font
	 */
	private void initTimesWrapTextCentreWithBorderFont(){
		// Lets create a TIMESWRAPTEXT font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		timesWrapTextCentreWithBorder = new WritableCellFormat(times10pt);
	   	// Lets automatically wrap the cells
	   	try {
			timesWrapTextCentreWithBorder.setWrap(true);
			timesWrapTextCentreWithBorder.setVerticalAlignment(VerticalAlignment.CENTRE);
			timesWrapTextCentreWithBorder.setAlignment(Alignment.CENTRE);
			timesWrapTextCentreWithBorder.setBorder(Border.ALL, BorderLineStyle.THIN,
	   		        Colour.BLACK);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/**
	 * create create a TIMESBOLDLEFT font 
	 */
	private void initTimesBoldLeftFont(){
	 	// create create a TIMESBOLD font 
	   	WritableFont times10ptBold = new WritableFont(
	   			WritableFont.TIMES, 10, WritableFont.BOLD, false);
	   	timesBoldLeft = new WritableCellFormat(times10ptBold);

	}
	
	/**
	 * create create a TIMESBOLDCENTER font 
	 */
	private void initTimesBoldCenterFont(){
	 	// create create a TIMESBOLD font 
	   	WritableFont times10ptBold = new WritableFont(
	   			WritableFont.TIMES, 10, WritableFont.BOLD, false);
	   	timesBoldCenter = new WritableCellFormat(times10ptBold);
		try {
		   	timesBoldCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
		   	timesBoldCenter.setAlignment(Alignment.CENTRE);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * create create a TIMESBOLDCENTER font 
	 */
	private void initTimesBoldCenterWithBorderFont(){
		// create create a TIMESBOLDCENTER font 
	   	WritableFont times10ptBold = new WritableFont(
	   			WritableFont.TIMES, 10, WritableFont.BOLD, false);
	   	timesBoldCenterWithBorder = new WritableCellFormat(times10ptBold);
	   	//Lets automatically wrap the cells
	   	try {
	   		timesBoldCenterWithBorder.setVerticalAlignment(VerticalAlignment.CENTRE);
	   		timesBoldCenterWithBorder.setAlignment(Alignment.CENTRE);
	   		timesBoldCenterWithBorder.setBorder(Border.ALL, BorderLineStyle.THIN,
	   		        Colour.BLACK);
	   		
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void initTimesBoldRightWithBorderMedium(){
		WritableFont times10ptBold = new WritableFont(
	   			WritableFont.TIMES, 10, WritableFont.BOLD, false);
		NumberFormat nf=new NumberFormat("#,##0.00;-#,##0.00");
		timesBoldRightWithBorderMedium= new WritableCellFormat(times10ptBold,nf);
		
		// Lets automatically wrap the cells
	   	try {
	   		timesBoldRightWithBorderMedium.setVerticalAlignment(VerticalAlignment.CENTRE);
	   		timesBoldRightWithBorderMedium.setAlignment(Alignment.RIGHT);
	   		timesBoldRightWithBorderMedium.setBorder(Border.ALL, BorderLineStyle.MEDIUM,
	   				Colour.BLACK);
	   		
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 * create create a bold font with wrap text and border. TIMESBOLDWRAPTEXT
	 */
	private void initTimesBoldWrapTextWithBorderMediumFont(){
		// create create a bold font with wrap text and border. TIMESBOLDWRAPTEXT
	   	WritableFont times10ptBold = new WritableFont(
	   			WritableFont.TIMES, 10, WritableFont.BOLD, false);
	   	timesBoldWrapTextWithBorderMedium = new WritableCellFormat(times10ptBold);
	   	
	   	// Lets automatically wrap the cells
	   	try {
	   		timesBoldWrapTextWithBorderMedium.setVerticalAlignment(VerticalAlignment.CENTRE);
	   		timesBoldWrapTextWithBorderMedium.setAlignment(Alignment.CENTRE);
	   		timesBoldWrapTextWithBorderMedium.setWrap(true);
	   		timesBoldWrapTextWithBorderMedium.setBorder(Border.ALL, BorderLineStyle.MEDIUM,
	   		        Colour.BLACK);
	   		
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void l_______________________________________l(){}
	
	/**
	 * Create excel.
	 * @param filename
	 * @param payslipDataStorageList
	 * @param payslip
	 * @param mode
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws SQLException 
	 */
	public void createExcel(String filename,int mode,MainFrame mainFrame) throws IOException, RowsExceededException, WriteException, SQLException, DocumentException{
		
		
		//--> Name of excel file
		workbook = Workbook.createWorkbook(new File(filename));
	      
		//--> Name of WorkSheet
		WritableSheet wsheet = workbook.createSheet("First Sheet", 0);
	      
		
		//-------------------------------------------------------------------------
		if(mode==Constant.PAYROLL_PER_DEPARTMENT_EXCEL){
			createExcelDepartmenPayrollData(wsheet);
		}
		else if(mode==Constant.PAYROLL_OVERALL_EXCEL){
			createExcelOverallPayrollData(wsheet);
		}
		else if(mode == Constant.PAYROLL_PER_CONTRACTUAL_EMPLOYEE_EXCEL){
			createExcelPerContractualEmployeeData(wsheet);
		}
		else if(mode==Constant.PAYROLL_ASEMCO_EXCEL 		||
				mode==Constant.PAYROLL_BCCI_EXCEL 			|| 
				mode==Constant.PAYROLL_OCCCI_EXCEL 			||
				mode==Constant.PAYROLL_DBP_EXCEL 			||
				mode==Constant.PAYROLL_CFI_EXCEL			||
				mode==Constant.PAYROLL_ST_PETER_PLAN_EXCEL 	||
				mode==Constant.PAYROLL_W_TAX_EXCEL){
			
			String title=null,neededColumnName=null;
			Database db= Database.getInstance();
			Utilities util= Utilities.getInstance();
			int dbIndex = 16; // ASEMCO
			switch(mode){
				case Constant.PAYROLL_ASEMCO_EXCEL:{
					title="ASEMCO";
			 		neededColumnName=db.deductionTableColumnNames[dbIndex];
			 		break;
			 	}
			 	case Constant.PAYROLL_BCCI_EXCEL:{
			 		title="BCCI";
			 		neededColumnName=db.deductionTableColumnNames[dbIndex+1];
			 		break;
			 	}
			 	case Constant.PAYROLL_OCCCI_EXCEL:{
			 		title="OCCCI";
			 		neededColumnName=db.deductionTableColumnNames[dbIndex+2];
			 		break;
			 	}
			 	case Constant.PAYROLL_DBP_EXCEL:{
			 		title="DBP";
			 		neededColumnName=db.deductionTableColumnNames[dbIndex+3];
			 		break;
			 	}
			 	case Constant.PAYROLL_CFI_EXCEL:{
			 		title="CFI";
			 		neededColumnName=db.deductionTableColumnNames[dbIndex+4];
			 		break;
			 	}
			 	case Constant.PAYROLL_ST_PETER_PLAN_EXCEL:{
			 		title="ST. PETER";
			 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[dbIndex+5]);
			 		break;
			 	}
			 	case Constant.PAYROLL_W_TAX_EXCEL:{
			 		title="WITHOLDING TAX";
			 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[dbIndex-1]);
			 		break;
			 	}

			 	default:{
			 		break;
		 		}
			}
			//-------------------------------------------
			createExcelPayrolAsemcoBcciOccciDbpCfiSTPeterWTaxDataSummary(wsheet, title, neededColumnName, mode);
			
		}
		//--> PAYROLL LBP SUMMARY
		else if(mode==Constant.PAYROLL_LBP_EXCEL){

			createExcelPayrollLBPDataSummary(wsheet, mode);
		}
		
		//--> PAYROLL SSS LOAN AND PAGIBIG LOAN SUMMARY PDF
		else if(mode==Constant.PAYROLL_SSS_LOAN_EXCEL	||
				 mode==Constant.PAYROLL_PAGIBIG_LOAN_EXCEL){
			String title=null,neededColumnName=null;
			Database db=Database.getInstance();
			Utilities util=Utilities.getInstance();
			DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
			String payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
			String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
			 
			if(util.isFirstPayOfTheMonth(day)){
				switch(mode){
					case Constant.PAYROLL_SSS_LOAN_EXCEL:{
				 		title="SSS Loan";
				 		neededColumnName=db.deductionTableColumnNames[3];
				 		break;
				 	}
				 	case Constant.PAYROLL_PAGIBIG_LOAN_EXCEL:{
				 		title="PAG-IBIG Loan";
				 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[4]);
				 		break;
				 	}
				 	default:{
				 		break;
				 	}
				}
				 
				createPayrollPDFSSSLoanPagibigLoanDataSummary(wsheet,
						title, payrollDate, util, db,neededColumnName,mode);
			}
			else{
				MainFrame.getInstance().showOptionPaneMessageDialog(
					"No available data. This data is available only on the first pay of the month.",
					JOptionPane.ERROR_MESSAGE
				);	
				//--> Set necessary UI components.
				isSuccessCreatingEXCEL=false;
			}
		}
		//-->PAYROLL UNION DUES SUMMARY EXCEL
		else if(mode==Constant.PAYROLL_UNION_DUES_EXCEL	){
			
			Utilities util=Utilities.getInstance();
			DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
			String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
			String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
			
			
			//--> Makes sure that it will be process at the second payroll of the month
			if(!util.isFirstPayOfTheMonth(day)){
				createPayrollPDFUnionDuesDataSummary(wsheet,payrollDate,util,day,mode);
			}
			else{
				MainFrame.getInstance().showOptionPaneMessageDialog(
						"No available data. This data is available only on the second pay of the month.",
						JOptionPane.ERROR_MESSAGE
				);	
				//--> Set necessary UI components.
				PayrollViewPanel.getInstance().optionPanel.setVisible(true);
				isSuccessCreatingEXCEL=false;
			}
		}
		
		//-->PAYROLL HDMF and MEDICARE SUMMARY PDF
		else if(mode==Constant.PAYROLL_HDMF_EXCEL	|| mode==Constant.PAYROLL_MEDICARE_EXCEL){
			Database db=Database.getInstance();
			Utilities util=Utilities.getInstance();
			DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
			String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
			String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
			String title="???",neededColumnName="???", neededColumnNameER="???";
			
			//--> Set the values depending on the mode.
			switch(mode){
				case Constant.PAYROLL_HDMF_EXCEL:{
			 		title="HDMF";
			 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[6]); // Pag-ibigCont
			 		neededColumnNameER=util.addSlantApostropheToString(db.employerShareTableColumnNames[4]); // Pag-ibigER
			 		break;
			 	}
			 	case Constant.PAYROLL_MEDICARE_EXCEL:{
			 		title="MEDICARE";
			 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[7]); // Medicare
			 		neededColumnNameER=util.addSlantApostropheToString(db.employerShareTableColumnNames[5]); // MedicareER
			 		break;
			 	}
			 	default:{
			 		break;
			 	}
			}
			
			
			// REGULAR
			if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
			
				//--> Makes sure that it will be process at the second payroll of the month
				if(!util.isFirstPayOfTheMonth(day)){
					createPayrollPDFHDMFMedicareDataSummary(wsheet, payrollDate,
							db, util, day,title,
							neededColumnName,neededColumnNameER,mode);
				}
				else{
					MainFrame.getInstance().showOptionPaneMessageDialog(
							"No available data. This data is available only on the second pay of the month.",
							JOptionPane.ERROR_MESSAGE
					);	
					//--> Set necessary UI components.
					PayrollViewPanel.getInstance().optionPanel.setVisible(true);
					isSuccessCreatingEXCEL=false;
				}
			}
			else{  // CONTRACTUAL
				switch(mode){
					case Constant.PAYROLL_MEDICARE_EXCEL:{
						//--> Makes sure that it will be process at the first payroll of the month
						if(util.isFirstPayOfTheMonth(day)){
							createPayrollPDFHDMFMedicareDataSummary(wsheet, payrollDate,
									db, util, day,title,
									neededColumnName,neededColumnNameER,mode);
						}
						else{
							MainFrame.getInstance().showOptionPaneMessageDialog(
									"No available data. This data is available only on the first pay of the month.",
									JOptionPane.ERROR_MESSAGE
							);	
							
						}
				 		break;
				 	}
					case Constant.PAYROLL_HDMF_EXCEL:{
						//--> Makes sure that it will be process at the second payroll of the month
						if(!util.isFirstPayOfTheMonth(day)){
							createPayrollPDFHDMFMedicareDataSummary(wsheet, payrollDate,
									db, util, day,title,
									neededColumnName,neededColumnNameER,mode);
						}
						else{
							MainFrame.getInstance().showOptionPaneMessageDialog(
									"No available data. This data is available only on the second pay of the month.",
									JOptionPane.ERROR_MESSAGE
							);	
							
						}
				 		break;
				 	}
				 	
				 	default:{
				 		break;
				 	}
				}
			}
		}
		
		//-->PAYROLL SSS CONTRIBUTION SUMMARY PDF
		else if(mode==Constant.PAYROLL_SSS_CONT_EXCEL	){
			
			Utilities util=Utilities.getInstance();
			DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
			String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
			String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
			
			
			//--> Makes sure that it will be process at the second payroll of the month
			if(!util.isFirstPayOfTheMonth(day)){
				createPayrollPDFSSSContDataSummary(wsheet, payrollDate, util, day,mode);
			}
			else{
				MainFrame.getInstance().showOptionPaneMessageDialog(
						"No available data. This data is available only on the second pay of the month.",
						JOptionPane.ERROR_MESSAGE
				);	
				//--> Set necessary UI components.
				PayrollViewPanel.getInstance().optionPanel.setVisible(true);
				isSuccessCreatingEXCEL=false;
			}
		}
		//-------------------------------------------------------------------------
		
		if(isSuccessCreatingEXCEL){
			//--> Create the excel file.
			workbook.write();
	      
			//--> Close the workbook, the same with pdf.
			workbook.close();
			
			
//			mainFrame.showOptionPaneMessageDialog("Excel file successfully created!", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("\tUNcomment  mainframe");
		}
	}
	 /**
     * View a excel viewer for payslip and payroll.
     * @param payslipDataStorageList
     * @param payslip
     * @param mode
     */
	public void viewExcel(int mode,MainFrame mainFrame){
    	String fileNamePath=Constant.FILE_PATH_NAME_EXCEL;
		isSuccessCreatingEXCEL=false;
		workbook=null;
		PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL.setButtonsEnableStatus(false);
		PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setButtonsEnableStatus(false);
		
		
		//--> Reuse Code. If you dont reuse this code, it will cause Array Index Exception because of WritableFont.class static variables.
		initFontsCellFormat();
				
		try {
			createExcel(fileNamePath, mode,mainFrame);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			isSuccessCreatingEXCEL=false;
			mainFrame.showOptionPaneMessageDialog(e2.getMessage(), JOptionPane.ERROR_MESSAGE);
		} catch (RowsExceededException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
			isSuccessCreatingEXCEL=false;
			mainFrame.showOptionPaneMessageDialog(e3.getMessage(), JOptionPane.ERROR_MESSAGE);
		} catch (WriteException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
			isSuccessCreatingEXCEL=false;
			mainFrame.showOptionPaneMessageDialog(e4.getMessage(), JOptionPane.ERROR_MESSAGE);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSuccessCreatingEXCEL=false;
			mainFrame.showOptionPaneMessageDialog(e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSuccessCreatingEXCEL=false;
			mainFrame.showOptionPaneMessageDialog(e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		if(isSuccessCreatingEXCEL){
			//--> Open pdf by using the installed pdf viewer of the compute.
			try{
				System.out.println("\t Opening EXCEL Viewer to view the created excel file."+CLASS_NAME);
				Desktop.getDesktop().open(new java.io.File(fileNamePath));
				
				PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL.dispose();
				PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.dispose();
				
				
			}catch(Exception e){
				System.out.println("\t"+e.getMessage()+CLASS_NAME);
				 try {
					workbook.close();
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 workbook=null;
				 
				 MainFrame.getInstance().showOptionPaneMessageDialog("Error in opening the EXCEL File.", JOptionPane.ERROR_MESSAGE);
				 
				
			}
		}
		
		PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL.setButtonsEnableStatus(true);
		PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.setButtonsEnableStatus(true);
		
    }
	
	
	private void l_________________________________________l(){}
	
	/**
	 * Create an excel data where it shows per department data.
	 * @param wsheet
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws SQLException 
	 */
	private void createExcelDepartmenPayrollData(WritableSheet wsheet) throws RowsExceededException, WriteException, SQLException{
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL;
		String payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString())
				,payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		String payrollDatePeriod=util.processPayrollPeriodString(payrollDatePeriodStarts, payrollDate);
		
		
		//----------------------------
		//--> Add Title.
		String str="PAYROLL FOR THE PERIOD "+payrollDatePeriod.toUpperCase();
		wsheet=addLabel(wsheet, 0, 0, str, timesBoldLeft);
		
		//----------------------------
		//--> Add Department
		String deptName=PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL.departmentComboBox.getSelectedItem().toString();
		str= util.convertDeptNameAbbrevToRealName(deptName).toUpperCase();
		wsheet=addLabel(wsheet, 0, 2, str, timesBoldLeft);
		
		//----------------------------
		//--> Add Header
		payrollHeaderList=getEarnDedsPayrollHeaderTexts(db, payrollHeaderList, Constant.PAYROLL_PER_DEPARTMENT_EXCEL);
		int totalColumnsInPDf=payrollHeaderList.size()-((correctDedsColumnList.length/2)+(correctEarnColumnList.length/2));
		wsheet=processComplexPayrollHeaderRegular(wsheet, db, util,totalColumnsInPDf);
		
//		System.out.println("\tAll Columns that has rowspan:"+CLASS_NAME);
//		for(int num:cellNumberRowSpanTwoList){
//			System.out.println("\t\t"+num+CLASS_NAME);
//		}
		
		//----------------------------
		//--> Add Contents
		wsheet=processPayrollContentPerDepartmentSummary(
			wsheet, db, totalColumnsInPDf,
			payrollDate, dialog
		);
		
		
		//----------------------------
		//--> Calculate the Total Values
		totalValueList=calculatePayrollPerDepartmentTotalColumnValues(totalValueList, db);
		
		//----------------------------
		//--> Add Total Values
		wsheet=processPayrollContentPerDepartmentTotal(wsheet, totalColumnsInPDf, db, totalValueList);
	
		//---------------------------
		//--> Add Signature content
		wsheet=processPayrollContentSignature(wsheet, util, Constant.PAYROLL_PER_DEPARTMENT_EXCEL);
		
		isSuccessCreatingEXCEL=true;
	}
	
	/**
	 * Create an excel data where it shows per department data.
	 * @param wsheet
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws SQLException 
	 */
	private void createExcelOverallPayrollData(WritableSheet wsheet) throws RowsExceededException, WriteException, SQLException{
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		String payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString())
				,payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		String payrollDatePeriod=util.processPayrollPeriodString(payrollDatePeriodStarts, payrollDate);
		
		
		//----------------------------
		//--> Add Title.
		wsheet=addLabel(wsheet, 0, 0, "BILIRAN ELECTRIC COOPERATIVE, INC.", timesBoldLeft);
		wsheet=addLabel(wsheet, 0, 1, "BILECO", timesBoldLeft);
		wsheet=addLabel(wsheet, 0, 3, "PAYROLL FOR THE PERIOD "+payrollDatePeriod.toUpperCase(), timesBoldLeft);


		//----------------------------
		//--> Add Header
		payrollHeaderList=getEarnDedsPayrollHeaderTexts(db, payrollHeaderList, Constant.PAYROLL_OVERALL_EXCEL);
		int totalColumnsInExcel=payrollHeaderList.size()-((correctDedsColumnList.length/2)+(correctEarnColumnList.length/2));
		wsheet=processComplexPayrollHeaderRegular(wsheet, db, util,totalColumnsInExcel);
		
//		System.out.println("\tAll Columns that has rowspan:"+CLASS_NAME);
//		for(int num:cellNumberRowSpanTwoList){
//			System.out.println("\t\t"+num+CLASS_NAME);
//		}
		
		//----------------------------
		//--> Add Contents
		wsheet=processPayrollContentOverallSummary(wsheet, db,
				payrollDate, totalColumnsInExcel, dialog);
		

		//--> Add Total Values
		wsheet=processPayrollContentPerDepartmentTotal(wsheet, totalColumnsInExcel, db, departmentTotalValueList);
	
		//---------------------------
		//--> Add Signature content
		wsheet=processPayrollContentSignature(wsheet, util, Constant.PAYROLL_PER_DEPARTMENT_EXCEL);
	
		
		isSuccessCreatingEXCEL=true;
	}
	/**
	 * Create an excel data where it shows per department data.
	 * @param wsheet
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws SQLException 
	 */
	private void createExcelPerContractualEmployeeData(WritableSheet wsheet) throws RowsExceededException, WriteException, SQLException{
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		String payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString())
				,payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		String payrollDatePeriod=util.processPayrollPeriodString(payrollDatePeriodStarts, payrollDate);
		
		
		//----------------------------
		//--> Add Title.
		int totalCols=17;
		wsheet=addLabel(wsheet, 0, 0, "BILIRAN ELECTRIC COOPERATIVE, INC.", timesBoldCenter);
		wsheet=mergeCells(wsheet, 0, 0, totalCols, 0);
		
		wsheet=addLabel(wsheet, 0, 1, "(BILECO)", timesBoldCenter);
		wsheet=mergeCells(wsheet, 0, 1, totalCols, 1);
		
		wsheet=addLabel(wsheet, 0, 2, "Caraycaray, Naval, Biliran",timesBoldCenter);
		wsheet=mergeCells(wsheet, 0, 2, totalCols, 2);
		
		wsheet=addLabel(wsheet, 0, 4, "SKILLED WORKER PAYROLL", timesBoldCenter);
		wsheet=mergeCells(wsheet, 0, 4, totalCols, 4);
		
		wsheet=addLabel(wsheet, 0, 5,  "For the Period "+payrollDatePeriod, timesBoldCenter);
		wsheet=mergeCells(wsheet, 0, 5, totalCols, 5);
		

		//----------------------------
		//--> Add Header
		payrollHeaderList=getPerContractualEmployeePayrollHeaderTexts(db, util, payrollHeaderList);
		int totalColumnsInExcel=payrollHeaderList.size()-2;
		wsheet=processComplexPayrollHeaderContractual(wsheet, db, util, totalColumnsInExcel);

		//----------------------------
		//--> Add Contents
		wsheet=processPayrollContentPerContractualEmployeeSummary(wsheet, db, util, payrollDate, totalColumnsInExcel);
		

		//---------------------------
		//--> Add Signature content
		wsheet=processPayrollContentSignature(wsheet, util, Constant.PAYROLL_PER_DEPARTMENT_EXCEL);
	
		
		isSuccessCreatingEXCEL=true;
	}
	/**
	 * * Create payroll data pdf for ASEMCO/BCCI/OCCCI/DBP/CFI/ST.Peter/WTax and it depends if first pay or second pay of the month.
	 * Why St.Peter and WTax is included? it has the same implementation to the five.
	 * @param wsheet
	 * @param title
	 * @param neededColumnName
	 * @param mode
	 * @throws SQLException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void createExcelPayrolAsemcoBcciOccciDbpCfiSTPeterWTaxDataSummary(WritableSheet wsheet,
			String title, String neededColumnName,int mode)throws  SQLException, RowsExceededException, WriteException{
		
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString()),
				payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		
		String payrollDatePeriod=util.processPayrollPeriodString(payrollDatePeriodStarts, payrollDate);
		String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);

		
		//----------------------------
		//--> Add Title.
		wsheet=addLabel(wsheet, 0, 0, "BILIRAN ELECTRIC COOPERATIVE, INC.", timesBoldLeft);
		wsheet=addLabel(wsheet, 0, 1, "Caraycaray, Naval, Biliran", timesBoldLeft);
		wsheet=addLabel(wsheet, 0, 3, (mode==Constant.PAYROLL_ST_PETER_PLAN_EXCEL)?title+" LIFE PLAN":(mode==Constant.PAYROLL_W_TAX_EXCEL)?title+"":"SCHEDULE OF "+title+" DEDUCTIONS", timesBoldLeft);
		wsheet=addLabel(wsheet, 0, 4,payrollDatePeriod.toUpperCase(), timesBoldLeft);

		
		
		//----------------------------
		//--> Add Header
		payrollHeaderList=getAsemcoBcciOccciDbpCfiStPeterWTaxPayrollHeaderTexts(db, payrollHeaderList, day, util, title, mode);
		int totalColumnsInExcel=payrollHeaderList.size();
		wsheet= processSimplePayrollHeader(wsheet);
		
		
		//----------------------------
		//--> Add Contents
		wsheet=processPayrollContentAsemcoBcciOccciDbpCfiSTPeterWTaxSummary(wsheet, db,
			day, util,
			payrollDate, neededColumnName
		);
		
		
		//---------------------------
		//--> Add Signature content
		wsheet=processPayrollContentSignature(wsheet, util, mode);
		
		isSuccessCreatingEXCEL=true;
	
		
	}
	/**
	 *  Create payroll data pdf for LBP and it depends if first pay or second pay of the month.
	 * LBP is weird since in first payroll duha ka column ang iprint, sa second payroll usa la.
	 * Example: First Payroll: December 31 ,2017  and January  15, 2018 
	 * 			Second Payroll: January 31,2018
	 * In contrast with most payroll.
	 * @param sheet
	 * @param mode
	 * @throws DocumentException
	 * @throws SQLException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void createExcelPayrollLBPDataSummary(WritableSheet sheet,int mode)throws DocumentException, SQLException, RowsExceededException, WriteException{
	
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
		String payrollDateBefore=getBeforePayrollDateBasedFromGivenPayrollDate(db, payrollDate, util);

		
		if(payrollDateBefore!=null){
			String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
				
			//--> Add a TITLE in pdf.
			sheet=addLabel(sheet, 0, 0, "BILIRAN ELECTRIC COOPERATIVE, INC.", timesBoldLeft);
			sheet=addLabel(sheet, 0, 1, "Caraycaray, Naval, Biliran", timesBoldLeft);

			String str1="FUCK1", str2="FUCK2";
			if(util.isFirstPayOfTheMonth(day)){
				str1="LBP LOAN PAYMENTS";
				str2="("+util.convertDateYyyyMmDdToReadableDate(payrollDateBefore)+" and "+util.convertDateYyyyMmDdToReadableDate(payrollDate)+" deduction)";
			}
			else{
				str1="LandBank of the Philippines";
				String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate),
						year=util.getYearFromDateFormatYyyyMmDd(payrollDate);
				month=util.convertThreeLetterMonthIntoCompleteName(month);
				str2=month+" "+day+", "+year;		
			}
			
			sheet=addLabel(sheet, 0, 3, str1, timesBoldLeft);
			sheet=addLabel(sheet, 0, 4, str2, timesBoldLeft);

			
			//----------------------------
			//--> Add Header
			payrollHeaderList=getLBPPayrollHeaderTexts(db, payrollHeaderList, day, payrollDate, payrollDateBefore, util);
			int totalColumnsInExcel=payrollHeaderList.size();
			sheet= processSimplePayrollHeader(sheet);
			
			//----------------------------
			//--> Add Contents
			sheet=processPayrollContentLBPSummary(sheet, db, day,
					util, payrollDate, payrollDateBefore);
			
			
			//---------------------------
			//--> Add Signature content
			sheet=processPayrollContentSignature(sheet, util, mode);
			
			
			isSuccessCreatingEXCEL=true;
		}
		else{
		    MainFrame.getInstance().showOptionPaneMessageDialog("Data is incomplete.", JOptionPane.ERROR_MESSAGE);
		    isSuccessCreatingEXCEL=false;
		}
	}
	
	
	/**
	 * Create the SSS loan and PAGIBIG Loan payroll data to be printed in PDF.
	 * @param document
	 * @param title
	 * @throws DocumentException
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void createPayrollPDFSSSLoanPagibigLoanDataSummary(WritableSheet sheet,
			String title,String payrollDate,Utilities util,
			Database db,String neededColumnName,int mode) throws DocumentException, RowsExceededException, WriteException{
		
		
		String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate);
			month=util.convertThreeLetterMonthIntoCompleteName(month);
		String year= util.getYearFromDateFormatYyyyMmDd(payrollDate);
		
		//----------------------------
		//--> Add Title.
		sheet=addLabel(sheet, 0, 0, "BILIRAN ELECTRIC COOPERATIVE, INC.", timesBoldLeft);
		sheet=addLabel(sheet, 0, 1, "Caraycaray, Naval, Biliran", timesBoldLeft);
		sheet=addLabel(sheet, 0, 3, title+" "+month+" "+year, timesBoldLeft);
		
		
		//----------------------------
		//--> Add Header
		payrollHeaderList=getSssPagibigLoanPayrollHeaderTexts(payrollHeaderList);
		int totalColumnsInExcel=payrollHeaderList.size();
		sheet=processSimplePayrollHeader(sheet);
		
		//----------------------------
		//--> Add Contents
		sheet=processPayrollContentSssPagibigLoanSummary(sheet, db,util, payrollDate, neededColumnName);
		
		//---------------------------
		//--> Add Signature content
		sheet=processPayrollContentSignature(sheet, util, mode);
		
		isSuccessCreatingEXCEL=true;
	}
	
	/**
	 * Create the Union Dues payroll data to be printed in PDF.
	 * @param document
	 * @param title
	 * @throws DocumentException
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void createPayrollPDFUnionDuesDataSummary(WritableSheet sheet,String payrollDate,
			Utilities util,String day, int mode) throws DocumentException, RowsExceededException, WriteException{
		
		Database db=Database.getInstance();
		String payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		
		
		String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate);
			month=util.convertThreeLetterMonthIntoCompleteName(month);
		String year= util.getYearFromDateFormatYyyyMmDd(payrollDate);
		
		//--> Add a TITLE in pdf.
		sheet=addLabel(sheet, 0, 0, "BILIRAN ELECTRIC COOPERATIVE, INC.", timesBoldLeft);
		sheet=addLabel(sheet, 0, 1, "Caraycaray, Naval, Biliran", timesBoldLeft);
		sheet=addLabel(sheet, 0, 3, "UNION DUES", timesBoldLeft);
		sheet=addLabel(sheet, 0, 4,  month+" "+day+", "+year, timesBoldLeft);


		
		//----------------------------
		//--> Add Header
		payrollHeaderList=getUnionDuesPayrollHeaderTexts(payrollHeaderList);
		int totalColumnsInExcel=payrollHeaderList.size();
		sheet=processSimplePayrollHeader(sheet);
		
		
		//----------------------------
		//--> Add Contents
		sheet=processPayrollContentUnionDuesSummary(sheet, db, util, payrollDate);
		
		
		
		//---------------------------
		//--> Add Signature content
		sheet=processPayrollContentSignature(sheet, util, mode);
		
		isSuccessCreatingEXCEL=true;
		
	}
	
	
	/**
	 * Create the HDMF/PAGIBIG CONT payroll data to be printed in PDF.
	 * @param sheet
	 * @param payrollDate
	 * @param db
	 * @param util
	 * @param day
	 * @param title
	 * @param neededColumnName
	 * @param neededColumnNameER
	 * @param mode
	 * @throws DocumentException
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void createPayrollPDFHDMFMedicareDataSummary(WritableSheet sheet,String payrollDate,
			Database db, Utilities util,String day,
			String title, String neededColumnName,String neededColumnNameER,
			int mode) throws DocumentException, RowsExceededException, WriteException{
		
		
		String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate);
			month=util.convertThreeLetterMonthIntoCompleteName(month);
		String year= util.getYearFromDateFormatYyyyMmDd(payrollDate);
		
		
		//--> Add a TITLE in pdf.
		sheet=addLabel(sheet, 0, 0, "BILIRAN ELECTRIC COOPERATIVE, INC.", timesBoldLeft);
		sheet=addLabel(sheet, 0, 1,  "Caraycaray, Naval, Biliran", timesBoldLeft);
		sheet=addLabel(sheet, 0, 3, title+" PREMIUM MASTERLIST", timesBoldLeft);
		sheet=addLabel(sheet, 0, 4, month+" "+day+", "+year, timesBoldLeft);
		

		
		//----------------------------
		//--> Add Header
		payrollHeaderList=getHDMFMedicarePayrollHeaderTexts(payrollHeaderList,mode);
		sheet=processSimplePayrollHeader(sheet);
		
		//----------------------------
		//--> Add Contents
		sheet=processPayrollContentHDMFMedicareSummary(sheet, db, util,
				payrollDate, neededColumnName, neededColumnNameER);
		
				
		//---------------------------
		//--> Add Signature content
		sheet=processPayrollContentSignature(sheet, util, mode);
		
		isSuccessCreatingEXCEL=true;
		
	}
	
	/**Create the SSS Contribution payroll data to be printed in Excel.
	 * 
	 * @param sheet
	 * @param payrollDate
	 * @param util
	 * @param day
	 * @param mode
	 * @throws DocumentException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void createPayrollPDFSSSContDataSummary(WritableSheet sheet,String payrollDate,
			Utilities util,String day,int mode) throws DocumentException, RowsExceededException, WriteException{
		
		Database db=Database.getInstance();
		String payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		
		
		String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate);
			month=util.convertThreeLetterMonthIntoCompleteName(month);
		String year= util.getYearFromDateFormatYyyyMmDd(payrollDate);
		
		//----------------------------
		//--> Add a TITLE in pdf.
		sheet=addLabel(sheet, 0, 0, "BILIRAN ELECTRIC COOPERATIVE, INC.", timesBoldLeft);
		sheet=addLabel(sheet, 0, 1, "Caraycaray, Naval, Biliran", timesBoldLeft);
		sheet=addLabel(sheet, 0, 3, "SSS PREMIUM MASTERLIST", timesBoldLeft);
		sheet=addLabel(sheet, 0, 4, month+" "+day+", "+year, timesBoldLeft);


		
		//----------------------------
		//--> Add Header
		payrollHeaderList=getSSSContributionPayrollHeaderTexts(payrollHeaderList);
		sheet=processSimplePayrollHeader(sheet);
		
		//----------------------------
		//--> Add Contents
		sheet=processPayrollContentSSSContributionSummary(sheet, db, util, payrollDate);
		
		
		
		//---------------------------
		//--> Add Signature content
		sheet=processPayrollContentSignature(sheet, util, mode);
		
		isSuccessCreatingEXCEL=true;
		
	}
	private void l__________Per_DEPARTMENT____________________________________l(){}
	
	/**
	 * Calculate total of each columns of each employee.
	 * @param list
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	private ArrayList<PayrollPDFTotalValueInfo> calculatePayrollPerDepartmentTotalColumnValues(ArrayList<PayrollPDFTotalValueInfo> list,Database db) throws SQLException{
		//--> Assign first all value
		list=assignTotalListValuesToZero(list);
		
		ResultSet rs=db.resultSet;
		ResultSetMetaData md= db.metaData;
		rs.last();
		int totalNumOfRows=rs.getRow();
		rs.beforeFirst();
		
		//--> Calculate total of each columns..
		for(int i=2;i<md.getColumnCount();i++){
			PayrollPDFTotalValueInfo info=null;
			String columnName=md.getColumnName(i+1);
			for(int k=0;k<list.size();k++){
				info=list.get(k);
				if(info.columnName.equals(columnName)){
					break;
				}
			}
			for(int j=0;j<totalNumOfRows;j++){
				rs.absolute(j+1);
//				System.out.println("\tColumn Name from Database: "+columnName+CLASS_NAME);
				
				double val=(rs.getObject(i+1)!=null)?Double.parseDouble(rs.getObject(i+1).toString()):0.0;
				info.value+=val;
				
			}
	
		}
		
		return list;
	}
	
	
	/**
	 * Process the employee payroll data summary in each department on PDF.
	 * @param sheet
	 * @param db
	 * @param totalColumnsInExcel
	 * @param payrollDate
	 * @param dialog
	 * @return
	 * @throws SQLException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private WritableSheet processPayrollContentPerDepartmentSummary(WritableSheet sheet,Database db,
			int totalColumnsInExcel,String payrollDate,DisplayOptionsDialog dialog) throws SQLException, RowsExceededException, WriteException{
		
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		Utilities util= Utilities.getInstance();
		ResultSet rs=null;
		ResultSetMetaData md=null;
		double totalEarningsValue=-1, totalDeductionValue=-1;
		boolean isAddNetPay=false,isTOTALTempBoolean=false;
		
		//--> Process condition.
		conditionColumnAndValueList.add(new SelectConditionInfo(db.departmentTableColumnNames[1], // Department
				dialog.departmentComboBox.getSelectedItem()));
		
		conditionColumnAndValueList.add(new SelectConditionInfo("", // PayrollDate[it could be earning/deduction, doesnt matter], I made empty because in query, it is already modified. see Database class
				payrollDate));

		
		//--> Retrieve Necessary Data from Database.
		db.selectDataInDatabase(null, null, conditionColumnAndValueList, null,null,
				Constant.SELECT_SPECIAL_EMPLOYEE_PAYROLL_SUMMARY_PDF);
		rs=db.resultSet;
		md= db.metaData;
		rs.last();
		int totalNumOfRows=rs.getRow();
		rs.beforeFirst();
		
		PayrollPDFTotalValueInfo netPayTotalInfo=null;
		for(int i=0;i<totalValueList.size();i++){
			netPayTotalInfo=totalValueList.get(i);
			if(netPayTotalInfo.columnName.equals(netPayColumnName))
				break;
		}
		
		//--> Put retrieved data to EXCEL.
		for(int i=0,colIndex=0,rowIndex=5;i<totalNumOfRows;i++,rowIndex++,colIndex=0){
			rs.absolute(i+1);
			
			//> Add Number and Name
			for(int j=0;j<2;j++,colIndex++){
				String str=(j==0)?""+(i+1):rs.getObject(j)+", "+rs.getObject(j+1);
				
				sheet=(j==0)? addNumberInteger(sheet, colIndex, rowIndex, Integer.parseInt(str), timesWrapTextCentreWithBorder)
							: addLabel(sheet, colIndex, rowIndex, str, timesWrapTextCentreWithBorder);
				if(cellNumberRowSpanTwoList.contains(colIndex)){
					sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
				}
				
			}
			
			
			//> Add the Number Values
			//		Since 1 employee data has TWO rows.
			for(int j=2,secondRowIndexOfOneEmployeeData=rowIndex+1;j<md.getColumnCount();j++,colIndex++){
				String data=(rs.getObject(j+1)!=null)?""+rs.getObject(j+1).toString():"0.0";
				
				if(db.earningTableColumnNames[db.earningTableColumnNames.length-1].equals(payrollHeaderList.get(j))){
					totalEarningsValue=Double.parseDouble(data);
					isTOTALTempBoolean=true;
				}
				if(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2].equals(payrollHeaderList.get(j))){
					totalDeductionValue=Double.parseDouble(data);
					isAddNetPay=true;
					isTOTALTempBoolean=true;
				}

				// Add Data on Cells
				if(Double.parseDouble(data)==0){
					data="-";
					sheet=addLabel(sheet, colIndex, rowIndex, data, timesCenterRightWithBorder);
				}
				else{
					sheet=addNumberDouble(sheet, colIndex, rowIndex,
							util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(data)),
							timesCenterRightWithBorder);
				}
				
				
				if(cellNumberRowSpanTwoList.contains(j)){
					sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
				}
				
				if(isTOTALTempBoolean){
					isTOTALTempBoolean=false;
				}
				
				//--> Add the Net Pay Value
				if(isAddNetPay){
					// Increment
					colIndex++;
					
					// Net Pay
					double netPayValue=(totalEarningsValue-totalDeductionValue);
					netPayValue=util.convertRoundToOnlyTwoDecimalPlaces(netPayValue);
					
					sheet=addNumberDouble(sheet, colIndex, rowIndex,
							netPayValue,
							timesCenterRightWithBorder);
					sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
					isAddNetPay=false;
					netPayTotalInfo.value+=netPayValue;
				}
				
				
				
				//--> When the counter reached the total colums not including the net pay, the value of column index will reset and the row index will increment to 1.
				if(j==totalColumnsInExcel-2){
					colIndex=1;
					rowIndex++;
				}
				
				//--> This if statement added/increment 1 to column index so that when the column is equal to Total Earnings, then it will not overwrite or have an error.
				if(colIndex==6 && rowIndex==secondRowIndexOfOneEmployeeData){
					colIndex++;
				}
				
			}
			

			
			
		}
		
		System.out.println("\tRetrieve from database Total COLUMNS: "+md.getColumnCount()
				+"\t Retrieve Total ROWS: "+totalNumOfRows
				+"\t Total Num of Columns: "+totalColumnsInExcel+CLASS_NAME);

		return sheet;
	}
	
	/**
	 * Adding the total summary data of all in the table.
	 * @param sheet
	 * @param totalColumnsInExcel
	 * @param db
	 * @param list
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processPayrollContentPerDepartmentTotal(WritableSheet sheet,
			int totalColumnsInExcel,Database db,ArrayList<PayrollPDFTotalValueInfo>list) throws RowsExceededException, WriteException{
		
		Utilities util = Utilities.getInstance();
		int rowIndex=sheet.getRows();
		int colIndex=0;
		
		//--> Add empty row
		sheet=addLabel(sheet, colIndex, rowIndex, "", timesBoldCenterWithBorder);
		sheet=mergeCells(sheet, colIndex, rowIndex, totalColumnsInExcel-1, rowIndex);
		rowIndex++;
		
		//--> Add the Total string Part of the Table
		sheet=addLabel(sheet, colIndex, rowIndex, "TOTAL", timesBoldCenterWithBorder);
		sheet=mergeCells(sheet, colIndex, rowIndex, 1, rowIndex+1);
	
		
		
		PayrollPDFTotalValueInfo totalEarnPpf=null,totalDedPpf=null;
		colIndex=2;
	    for(int i=0,secondRowIndexOfOneEmployeeData=rowIndex+1;i<list.size();i++,colIndex++){
	    	PayrollPDFTotalValueInfo ppf=list.get(i);
	    	
	    	totalEarnPpf=(ppf.columnName.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]))?ppf:totalEarnPpf;
	    	totalDedPpf=(ppf.columnName.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]))?ppf:totalDedPpf;

	  

	    	if(ppf.columnName.equals(netPayColumnName)){
	    		ppf.value=totalEarnPpf.value-totalDedPpf.value;
	    	}
	    	
	    	String data=""+ppf.value;
	    	// Add Cells
			if(Double.parseDouble(data)==0){
				data="-";
				sheet=addLabel(sheet, colIndex, rowIndex, data, timesCenterRightWithBorder);
			}
			else{
				sheet=addNumberDouble(sheet, colIndex, rowIndex,
						util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(data)),
						timesCenterRightWithBorder);
			}
	    	
			
			if(ppf.columnName.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]) ||
	    			ppf.columnName.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]) ||
	    			ppf.columnName.equals(netPayColumnName)){
	    		sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
	    	}
	    	
	    	//--> When the counter reached the total colums including the net pay, the value of column index will reset and the row index will increment to 1.
			//		Why 1+2? since the Total string covers two columns.
			if((i+2)==totalColumnsInExcel-1){
				colIndex=1;
				rowIndex++;
			}
			
			//--> This if statement added/increment 1 to column index so that when the column is equal to Total Earnings, then it will not overwrite or have an error.
			if(colIndex==6 && rowIndex==secondRowIndexOfOneEmployeeData){
				colIndex++;
			}
			
	    }

		return sheet;
	}
	private void l__________PER_CONTRACTUAL_EMPLOYEE_____________l(){}
	/**
	 * Get the payroll header text for ASEMCO/BCCI/OCCCI/DBP/CFI/StPeter and WTax data
	 * Why St.Peter is included? it has the same implementation to the five.
	 * @param db
	 * @param list
	 * @param day
	 * @param util
	 * @return
	 */
	private ArrayList<String> getPerContractualEmployeePayrollHeaderTexts(
			Database db, Utilities util,
			ArrayList<String> list){
		
		list.clear();
		totalValueList.clear();
		
		
		list.add("#");
		list.add("NAME");
		list.add("Earnings");
		list.add("Deductions");
		list.add(netPayColumnName);
		list.add(signatureColumnName);
		
		for(int i=3;i<db.earningsContractualColumnNames.length;i++){ //5
			list.add(db.earningsContractualColumnNames[i]);
		}
		
		for(int i=3;i<db.deductionsContractualColumnNames.length-1;i++){ //9
			list.add(db.deductionsContractualColumnNames[i]);
		}
		
		
//		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR){
//			
//		}
//		else{
//			
//		}
		
	
		return list;
	}
	
	
	/**
	 * Process Per Contractual Employee Content Summary
	 * @param sheet
	 * @param db
	 * @param util
	 * @param payrollDate
	 * @param neededColumnName
	 * @return
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws SQLException 
	 */
	private WritableSheet processPayrollContentPerContractualEmployeeSummary(
			WritableSheet sheet, Database db,
			Utilities util,
			String payrollDate, int totalColumns) throws RowsExceededException, WriteException, SQLException{
		
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		ResultSet rs=null;
		ResultSetMetaData md=null;
		
		//--> Process condition.
			conditionColumnAndValueList.add(new SelectConditionInfo("", // PayrollDate[it could be earning/deduction, doesnt matter], I made empty because in query, it is already modified. see Database class
					payrollDate));
			
			//--> Add condition if with ATM or NOT only if contractual
			if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
				WithOrWithoutATMDialog wthATMdialog = payrollViewPanel.withOrWithoutATMDialog;
				conditionColumnAndValueList.add(new SelectConditionInfo(
						"WithATM",
						(wthATMdialog.withATMRadioBtn.isSelected())?Constant.STRING_YES:Constant.STRING_NO
					)
				);
			}
	
		//--> Retrieve Necessary Data from Database.
		db.selectDataInDatabase(null, null, conditionColumnAndValueList, null,null,
				Constant.SELECT_SPECIAL_PER_CONTRACTUAL_EMPLOYEE_SUMMARY);
		rs=db.resultSet;
		md= db.metaData;
		rs.last();
		int totalNumOfRows=rs.getRow();
		rs.beforeFirst();
		
		
		HashMap<Integer, Double> totalValueList=new HashMap<Integer,Double>();
		for(int j=4;j<md.getColumnCount();j++){
			totalValueList.put(j, (double) 0);
		}
		
		//--> Put retrieved data to PDF.
		for(int i=0,excelRowIndex=-1;i<totalNumOfRows;i++){
			excelRowIndex=sheet.getRows();
			
			rs.absolute(i+1);
			
			//> Add Number and Name
			for(int j=0;j<2;j++){
				String str=(j==0)?""+(i+1):rs.getObject(j)+", "+rs.getObject(j+1);
				sheet= addLabel(sheet, j, excelRowIndex, str, (j==0)?timesWrapTextCentreWithBorder:timesWrapTextCentreWithBorder);
			}
			
			//> Add the Double Number Values except signature
			for(int j=2;j<md.getColumnCount();j++){
				String data=(rs.getObject(j+1)!=null)?""+rs.getObject(j+1).toString():"0.0";
				
				// Sum total values and store to list.
				if(j>=4){
					double sum=totalValueList.get(j);
					totalValueList.put(j,Double.parseDouble(data)+sum);
				}

				// Add Cells
				if(Double.parseDouble(data)==0){
					sheet=addLabel(sheet, j, excelRowIndex, "-", timesCenterRightWithBorder);	
					
				}
				else{
					sheet=addNumberDouble(sheet, j, excelRowIndex, Double.parseDouble(data), timesCenterRightWithBorder);	
				}

			}
			
			// Add SIGNATURE empty string to avoid overlap
			sheet=addLabel(sheet, totalColumns-1, excelRowIndex, " ", timesCenterRightWithBorder);	
			
		}
		
		// Add long empty string to separate the total values
		int excelRowIndex=sheet.getRows();
		sheet=addLabel(sheet, 0, excelRowIndex, " ", timesCenterRightWithBorder);	
		sheet=mergeCells(sheet, 0, excelRowIndex, totalColumns-1, excelRowIndex);
		
		
		//-----------------------------------------------------------------------------------------------
		//--> Add Total Values
		
		//> Add the TOTAL text
		excelRowIndex=sheet.getRows();
		sheet=addLabel(sheet, 0, excelRowIndex, "Total", timesWrapTextCentreWithBorder);	
		sheet=mergeCells(sheet, 0, excelRowIndex, 3, excelRowIndex);

		
		//> Add Total numbers
		for(int j=4;j<md.getColumnCount();j++){
			double sum=totalValueList.get(j);
			sheet=addNumberDouble(sheet, j, excelRowIndex, sum, timesCenterRightWithBorder);

		}
		// Add SIGNATURE empty string to avoid overlap
		sheet=addLabel(sheet, totalColumns-1, excelRowIndex, " ", timesCenterRightWithBorder);	
		
		
		System.out.println("\tRetrieve from database Total Column: "+md.getColumnCount()+CLASS_NAME);

		return sheet;
	}
	
	private void l__________OVERALL____________________________________l(){}
	/**
	 * Calculate total of each columns of each department.
	 * @param list
	 * @param dependedList
	 * @return
	 */
	private ArrayList<PayrollPDFTotalValueInfo> calculatePayrollOverallTotalColumnValues(ArrayList<PayrollPDFTotalValueInfo> list,ArrayList<PayrollPDFTotalValueInfo> dependedList){
		if(list.size()==0){
			for(PayrollPDFTotalValueInfo dependedInfo:dependedList){
				list.add(new PayrollPDFTotalValueInfo(0,dependedInfo.columnName));
			}
		}
		
		
		for(int i=0;i<list.size();i++){
			PayrollPDFTotalValueInfo info=list.get(i);
			PayrollPDFTotalValueInfo dependedInfo=dependedList.get(i);
			
			info.value+=dependedInfo.value;
		}
		return list;
	}
	
	/**
	 * Process the content of pdf when chosen mode is Department Summary
	 * @param sheet
	 * @param db
	 * @param payrollDate
	 * @param totalColumnsInExcel
	 * @param dialog
	 * @return
	 * @throws SQLException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private WritableSheet processPayrollContentOverallSummary(WritableSheet sheet,
			Database db,String payrollDate, int totalColumnsInExcel,
			DisplayOptionsDialog dialog) throws SQLException, RowsExceededException, WriteException{
		
		Utilities util= Utilities.getInstance();
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		
		int count=0;
		int colIndex=0,rowIndex=sheet.getRows()-1;
		departmentTotalValueList=assignTotalListValuesToZero(departmentTotalValueList);
		
		for(String deptName:db.departmentDataList.values()){
			colIndex=0;
			rowIndex++;
			
			conditionColumnAndValueList.clear();
			//--> Process condition.
			conditionColumnAndValueList.add(new SelectConditionInfo(db.departmentTableColumnNames[1], // Department
					deptName));
			conditionColumnAndValueList.add(new SelectConditionInfo("", // PayrollDate[it could be earning/deduction, doesnt matter], I made empty because in query, it is already modified. see Database class
					payrollDate));
			
			//--> Retrieve Necessary Data from Database.
			db.selectDataInDatabase(null, null, conditionColumnAndValueList, null,null,
					Constant.SELECT_SPECIAL_EMPLOYEE_PAYROLL_SUMMARY_PDF);
			
			//--> Calculate the total values of the given department name.
			totalValueList=calculatePayrollPerDepartmentTotalColumnValues(totalValueList, db);
			
			//------------------------------------------------
			//--> Put necessary data to table;
			// Add number.
			sheet=addNumberInteger(sheet, colIndex, rowIndex,(count+1), timesWrapTextCentreWithBorder);
			sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
			colIndex++;
			
			// Add Department Name.
			sheet=addLabel(sheet, colIndex, rowIndex, deptName, timesWrapTextCentreWithBorder);
			sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
			colIndex++;
			
			//------------------------------------------------
			PayrollPDFTotalValueInfo totalDedPpf=null,totalEarnPpf=null;
			String data=null;
			
		    for(int i=0,secondRowIndexOfOneEmployeeData=rowIndex+1;i<totalValueList.size();i++,colIndex++){
		    	PayrollPDFTotalValueInfo ppf=totalValueList.get(i);
		    	
		    	
	    		totalEarnPpf=(totalEarnPpf==null && ppf.columnName.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]))?ppf:totalEarnPpf;
		    	totalDedPpf=(totalDedPpf==null && ppf.columnName.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]))?ppf:totalDedPpf;

		  

		    	if(ppf.columnName.equals(netPayColumnName)){
		    		ppf.value=totalEarnPpf.value-totalDedPpf.value;	
		    	}
		    	
		    	
		    	//--> Check if zero change to '-'
		    	if(ppf.value==0){
		    		data="-";
		    		sheet=addLabel(sheet, colIndex, rowIndex, data, timesCenterRightWithBorder);
					
		    	}
		    	else{
		    		data=util.insertComma(""+util.convertRoundToOnlyTwoDecimalPlaces(ppf.value));
		    		sheet=addNumberDouble(sheet, colIndex, rowIndex,
							util.convertRoundToOnlyTwoDecimalPlaces(ppf.value),
							timesCenterRightWithBorder);
		    	}
		    	
		    	
				
		    	if(ppf.columnName.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]) ||
		    			ppf.columnName.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]) ||
		    			ppf.columnName.equals(netPayColumnName)){
		    		sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
		    	}
		    	
		    	
		    	//--> When the counter reached the total colums including the net pay, the value of column index will reset and the row index will increment to 1.
				//		Why 1+2? since the Total string covers two columns.
				if((i+2)==totalColumnsInExcel-1){
					colIndex=1;
					rowIndex++;
				}
				
				//--> This if statement added/increment 1 to column index so that when the column is equal to Total Earnings, then it will not overwrite or have an error.
				if(colIndex==6 && rowIndex==secondRowIndexOfOneEmployeeData){
					colIndex++;
				}

		    }
			count++;
			
			departmentTotalValueList=calculatePayrollOverallTotalColumnValues(departmentTotalValueList, totalValueList);
		
	
		}
	
		return sheet;
	}
	private void l__________ASEMCO_BCCI_OCCCI_DBP_CFI_STPETER_WTAX___________________________________l(){}
	/**
	 * Get the payroll header text for ASEMCO/BCCI/OCCCI/DBP/CFI/StPeter and WTax data
	 * Why St.Peter is included? it has the same implementation to the five.
	 * @param db
	 * @param list
	 * @param day
	 * @param util
	 * @return
	 */
	private ArrayList<String> getAsemcoBcciOccciDbpCfiStPeterWTaxPayrollHeaderTexts(
			Database db,ArrayList<String> list,
			String day,Utilities util, String title, int mode){
		
		list.clear();
		
		list.add("#");
		list.add("NAME");
		list.add("15th "+((mode==Constant.PAYROLL_ST_PETER_PLAN_EXCEL || mode==Constant.PAYROLL_W_TAX_EXCEL)?"":title));
		
		if(!util.isFirstPayOfTheMonth(day)){
			list.add("30th "+((mode==Constant.PAYROLL_ST_PETER_PLAN_EXCEL || mode==Constant.PAYROLL_W_TAX_EXCEL)?"":title));
			list.add("Total");
		}
		
		
		return list;
	}
	
	/**
	 * Process the table content when the payroll date is on first Pay or
	 * 		 15th day of Asemco/Bcci/Occci/Dbp/Cfi/ST.Peter/Wtax. 
	 * @param sheet
	 * @param db
	 * @param conditionColumnAndValueList
	 * @param joinColumnCompareList
	 * @param util
	 * @param neededColumnName
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processAsemcoBcciOccciDbpCfiStPeterWTaxFirstPay15thDayTableContent(
			WritableSheet sheet,
			Database db,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList, Utilities util,
			String neededColumnName) throws RowsExceededException, WriteException{

//		private String selectInnerJoinWithCondition(
//		String[] tableNameList,
//		String[]columnNameList, 
//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
//		String[] joinColumnCompareList){
	
		
		
		
		int colIndex=0,rowIndex=sheet.getRows();
		double totalAllAsemcoBcciOccciDbpCfi15thValue=0;
		
		//--> Add aditional condition to NOT retrieve data with all zero values in a row.
		conditionColumnAndValueList.add(new SelectConditionInfo(neededColumnName,0 ));
		conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
		//--> Add condition if regular or contractual
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		//--> Add condition if with ATM or NOT only if contractual
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
			WithOrWithoutATMDialog dialog = payrollViewPanel.withOrWithoutATMDialog;
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"WithATM",
					(dialog.withATMRadioBtn.isSelected())?Constant.STRING_YES:Constant.STRING_NO
				)
			);
		}
			
		//--> Retrieve data from database
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameDeductions},
			new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],neededColumnName}, //FamilyName, FirstName, Asemco/Bcci/Occci/Dbp/Cfi 
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3]},"ASC"), // Sort Ascending order LastName FirstName
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------------------------------
		//--> Process the contents of table
		try {
			db.resultSet.last();
		
			int totalNumOfRows=db.resultSet.getRow();
			db.resultSet.beforeFirst();
			
			for(int i=1;i<=totalNumOfRows;i++,rowIndex++,colIndex=0){
				db.resultSet.absolute(i);
				
				//> Add the number data in cell
				sheet=addNumberInteger(sheet, colIndex, rowIndex, i, timesWrapTextCentreWithBorder);
				colIndex++;

				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++,colIndex++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
							
					sheet=	(j==db.metaData.getColumnCount())?
							addNumberDouble(sheet, colIndex, rowIndex, Double.parseDouble(data), timesCenterRightWithBorder)
							:
							addLabel(sheet, colIndex, rowIndex, data, timesCenterLeftWithBorder);
		
							

					
					if(j==db.metaData.getColumnCount()){
						totalAllAsemcoBcciOccciDbpCfi15thValue+=Double.parseDouble(data);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		colIndex=0;rowIndex=sheet.getRows();
		
		//--> Process ALL Total Asemco 15th Value.
		//> Add empty cell
		sheet=addLabel(sheet, colIndex, rowIndex, "", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		
		//> Total
		sheet=addLabel(sheet, colIndex, rowIndex, "TOTAL", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		

		//> Total Value
		totalAllAsemcoBcciOccciDbpCfi15thValue=
				util.convertRoundToOnlyTwoDecimalPlaces(totalAllAsemcoBcciOccciDbpCfi15thValue);
		sheet=addNumberDouble(sheet, colIndex, rowIndex,
				totalAllAsemcoBcciOccciDbpCfi15thValue,
				timesBoldRightWithBorderMedium
		);
		colIndex++;
	
		
		return sheet;
	}

	/**
	 * Process the table content when the payroll date is
	 * 		on second Pay or 30th day of Asemco/Bcci/Occci/Dbp/Cfi/St.Peter/WTax. 
	 * @param sheet
	 * @param db
	 * @param conditionColumnAndValueList
	 * @param joinColumnCompareList
	 * @param payrollDate
	 * @param util
	 * @param neededColumnName
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processAsemcoBcciOccciDbpCfiSTPeterWTaxSecondPay30thDayTableContent(
			WritableSheet sheet,
			Database db,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,String payrollDate,Utilities util,
			String neededColumnName) throws RowsExceededException, WriteException{
		
		//--> For Debugging Purposes.
//		System.out.println("\t Process ASEMCO,BCCI,OCCCI.DBP,CFI,STPETER,WTAX -> 30th Day"+CLASS_NAME);
		
//		private void selectBasedFromColumns(
//				String[] tableNameList,
//				String[]columnNameList,
//				OrderByInfo orderInfo
//		){
		
		
		
		int colIndex=0,rowIndex=sheet.getRows();
		ArrayList<Double> totalAllAsemcoBcciOccciDbpCfi30thValueList=new ArrayList<Double>();
	
		//-----------------------------------------------------------------------------------------------
		
		
		//--> Get the date before the chosen payroll date.
		String payrollDateBefore=getBeforePayrollDateBasedFromGivenPayrollDate(db, payrollDate, util);
		try{
		
			//--> If there is a payroll date before data found.
			if(payrollDateBefore!=null){
				
				//--> Add aditional condition including this payroll date before
				conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[1],payrollDateBefore ));
				//--> Add aditional condition to NOT retrieve data with all zero values in a row.
				conditionColumnAndValueList.add(new SelectConditionInfo(neededColumnName,0 ));
				conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
				
				//-------------------------------------------------
				
				//--> Retrieve all payroll date data of the chosen payroll date and the payroll date before.
				db.selectDataInDatabase(
					new String[]{db.tableNameEmployee,db.tableNameDeductions},
					new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],neededColumnName,db.payrollDateTableColumnNames[0],db.tableNameEmployee+"."+db.employeeTableColumnNames[0]}, //FamilyName, FirstName, Asemco/Bcci/Occci/Dbp/Cfi, PayrollDate, EmployeeID 
					conditionColumnAndValueList,
					joinColumnCompareList,
					new OrderByInfo(new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3]}, "ASC"),
					Constant.SELECT_SPECIAL_AOBDCSL_30th_PAYROLL_DATA
				);
				
				//-------------------------------------------------
				//--> Set total value List
				for(int i=0;i<3;i++){ // Why 3?: 15th, 30th, Total
					totalAllAsemcoBcciOccciDbpCfi30thValueList.add(new Double(0));
				}
				//-------------------------------------------------
				//--> Process the content of table.
				db.resultSet.last();
				
				int totalNumOfRows=db.resultSet.getRow();
				db.resultSet.beforeFirst();
				
				//--> Get Data each row and put it to pdf.
				for(int i=1,count=1;i<=totalNumOfRows;count++,i++,rowIndex++){
					colIndex=0;
					
					//--> Go to row
					db.resultSet.absolute(i);
					
					//--> Add the number data in cell
					sheet=addNumberInteger(sheet, colIndex, rowIndex, count, timesWrapTextCentreWithBorder);
					colIndex++;
					
					//--> Add the name  to table
					String name=db.resultSet.getObject(1).toString()+", "+db.resultSet.getObject(2).toString();
					sheet=addLabel(sheet, colIndex, rowIndex, name, timesCenterLeftWithBorder);
					colIndex++;
					
				
					
					//--> Get the date and employeID of this row which was retrieved from database
					String dateFromRowTable=db.resultSet.getObject(db.metaData.getColumnCount()-1).toString();
					String employeeIDOfThisRow=db.resultSet.getObject(db.metaData.getColumnCount()).toString();
					
					double totalOf15thAnd30th=0;
					int columnValIndex=db.metaData.getColumnCount()-2;
					
					//--> Process when the payroll date of this row is first pay of the month.
					if(payrollDateBefore.equals(dateFromRowTable)){
						
//						//--> For Debugging Purposes.
//						System.out.println("\t\t Name: "+name
//								+ "\tProcess if Payroll Date BEFORE"+CLASS_NAME);
						
						
						
						//--> Get the employeeID of the next row and return the pointer to this current row.
						String empIDAfterThisRow=null;
						if(i<totalNumOfRows){
							db.resultSet.absolute(i+1);
							empIDAfterThisRow=db.resultSet.getObject(db.metaData.getColumnCount()).toString();
							db.resultSet.absolute(i);
						}
						
						
						//--> Add the value of 15th column cell.
						double value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
						
						sheet=(value==0)?
							addLabel(sheet, colIndex, rowIndex, "-", timesCenterRightWithBorder)
							:
							addNumberDouble(sheet, colIndex, rowIndex, value, timesCenterRightWithBorder
						);
						
						colIndex++;
						totalOf15thAnd30th+=value;
						totalAllAsemcoBcciOccciDbpCfi30thValueList.set(0,totalAllAsemcoBcciOccciDbpCfi30thValueList.get(0)+value);
						
						//--> Add the value of 30th column cell.
						value=0;
						if(empIDAfterThisRow!=null && employeeIDOfThisRow.equals(empIDAfterThisRow)){
							db.resultSet.absolute(i+1);
							value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
							i++;
						}
						sheet=(value==0)?
							addLabel(sheet, colIndex, rowIndex, "-", timesCenterRightWithBorder)
							:
							addNumberDouble(sheet, colIndex, rowIndex, value, timesCenterRightWithBorder
						);
						colIndex++;
						totalOf15thAnd30th+=value;
						totalAllAsemcoBcciOccciDbpCfi30thValueList.set(1,totalAllAsemcoBcciOccciDbpCfi30thValueList.get(1)+value);
						
					}
					//--> Process when the payroll date of this row is second pay of the month.
					else if(payrollDate.equals(dateFromRowTable)){
						
						//--> For Debugging Purposes.
//						System.out.println("\t\t Name: "+name
//								+ "\tProcess if Payroll Date CURRENT"+CLASS_NAME);
						
						
						
						//--> Get the employeeID of the previous row and return the pointer to this current row.
						String empIDBeforeThisRow=null; 
						if(i<totalNumOfRows){
							db.resultSet.absolute(i+1);
							empIDBeforeThisRow=db.resultSet.getObject(db.metaData.getColumnCount()).toString();
							db.resultSet.absolute(i);
						}
						
						
						//--> Add the value of 15th column cell.
						double value=0;
						if(empIDBeforeThisRow!=null && employeeIDOfThisRow.equals(empIDBeforeThisRow) ){
							db.resultSet.absolute(i+1);
							value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
							db.resultSet.absolute(i);
							i++;
						}
						sheet=(value==0)?
							addLabel(sheet, colIndex, rowIndex, "-", timesCenterRightWithBorder)
							:
							addNumberDouble(sheet, colIndex, rowIndex, value, timesCenterRightWithBorder
						);
						colIndex++;
						totalOf15thAnd30th+=value;
						totalAllAsemcoBcciOccciDbpCfi30thValueList.set(0,totalAllAsemcoBcciOccciDbpCfi30thValueList.get(0)+value);
						
						//--> Add the value of 30th column cell.
						value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
						sheet=(value==0)?
							addLabel(sheet, colIndex, rowIndex, "-", timesCenterRightWithBorder)
							:
							addNumberDouble(sheet, colIndex, rowIndex, value, timesCenterRightWithBorder
						);
						colIndex++;
						totalOf15thAnd30th+=value;
						totalAllAsemcoBcciOccciDbpCfi30thValueList.set(1,totalAllAsemcoBcciOccciDbpCfi30thValueList.get(1)+value);
			
					}
					
					
					//--> Add the total value where 15th + 30th value.
					totalOf15thAnd30th=util.convertRoundToOnlyTwoDecimalPlaces(totalOf15thAnd30th);
					
					sheet=(totalOf15thAnd30th==0)?
						addLabel(sheet, colIndex, rowIndex, "-", timesCenterRightWithBorder)
						:
						addNumberDouble(sheet, colIndex, rowIndex, totalOf15thAnd30th, timesCenterRightWithBorder
					);
					colIndex++;
					totalAllAsemcoBcciOccciDbpCfi30thValueList.set(
						2,
						totalAllAsemcoBcciOccciDbpCfi30thValueList.get(2)+totalOf15thAnd30th
					);

				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		colIndex=0;rowIndex=sheet.getRows();
		
		
		//--> Process ALL Total Asemco/Bcci/Occci/Dbp/Cfi 30th Value.

		//> None
		sheet=addLabel(sheet, colIndex, rowIndex,
				" ",
			timesBoldWrapTextWithBorderMedium
		);
		colIndex++;
		
		
		//> Total word
		sheet=addLabel(sheet, colIndex, rowIndex,
				"TOTAL",
			timesBoldWrapTextWithBorderMedium
		);
		colIndex++;

		
		//> Total Value of 15th, 30th and 15th+30th
		for(int i=0;i<totalAllAsemcoBcciOccciDbpCfi30thValueList.size();i++){
			double num= util.convertRoundToOnlyTwoDecimalPlaces(totalAllAsemcoBcciOccciDbpCfi30thValueList.get(i));
			sheet=(num==0)?
				addLabel(sheet, colIndex, rowIndex, "-", timesBoldRightWithBorderMedium)
				:
				addNumberDouble(sheet, colIndex, rowIndex, num, timesBoldRightWithBorderMedium)
			;
			
			colIndex++;
		}
		
		return sheet;
		
	}
	/**
	 *  Process the content of the table of ASEMCO, BCCI, OCCCI, DBP, CFI, St. Peter, and W-Tax
	 * @param sheet
	 * @param db
	 * @param day
	 * @param util
	 * @param payrollDate
	 * @param neededColumnName
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processPayrollContentAsemcoBcciOccciDbpCfiSTPeterWTaxSummary(
			WritableSheet sheet, Database db,
			String day,Utilities util,
			String payrollDate, String neededColumnName) throws RowsExceededException, WriteException{
	
		
		//--> Required Query
//		SELECT  FamilyName, FirstName, Asemco/Bcci/Occci/Dbp/Cfi 
//	    FROM employee
//	    INNER JOIN deductions
//	    ON employee.EmployeeID=deductions.EmployeeID
//	    WHERE PayrollDate='2018-04-30';
		
		//-----------------------------------------------------------------------------------------------
		//--> Set Condition parameters
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(
				new SelectConditionInfo(db.deductionTableColumnNames[1],payrollDate )); // PayrollDate
		
		//-----------------------------------------------------------------------------------------------
		//--> Set inner join paramaters
		String[] joinColumnCompareList= new String[]{
				db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[2]  
		};
		
		//-----------------------------------------------------------------------------------------------
		
		//--> PROCESS IF THE PAYROLL DATE IS AT 15th or FIRST PAY
		if(util.isFirstPayOfTheMonth(day)){	
			sheet= processAsemcoBcciOccciDbpCfiStPeterWTaxFirstPay15thDayTableContent(sheet, db, conditionColumnAndValueList, joinColumnCompareList, util,neededColumnName);
		}
		//-----------------------------------------------------------------------------------------------
		
		//--> PROCESS IF THE PAYROLL DATE IS AT 30th or SECOND PAY
		else{
			
			sheet = processAsemcoBcciOccciDbpCfiSTPeterWTaxSecondPay30thDayTableContent(sheet, db, conditionColumnAndValueList, joinColumnCompareList, payrollDate, util,neededColumnName);
		}
		return sheet;
	}
	private void l__________LBP___________________________________l(){}
	/**
	 * Get the payroll header text for LBP[Landbank of the Philippines] data
	 * @param db
	 * @param list
	 * @param day
	 * @param payrollDate
	 * @param payrollDateBefore
	 * @param util
	 * @return
	 */
	private ArrayList<String> getLBPPayrollHeaderTexts(
			Database db,ArrayList<String> list,
			String day,String payrollDate,String payrollDateBefore,
			Utilities util){
		
		list.clear();
		
		list.add("#");
		list.add("NAME");
		
		if(util.isFirstPayOfTheMonth(day)){
			list.add(util.getDayFromDateFormatYyyyMmDd(payrollDateBefore)+"-"+util.getMonthFromDateFormatYyyyMmDd(payrollDateBefore));
			list.add(day+"-"+util.getMonthFromDateFormatYyyyMmDd(payrollDate));
			list.add("Total");
		}
		else{
			list.add(day+"-"+util.getMonthFromDateFormatYyyyMmDd(payrollDate));
		}
		
		
		
		return list;
	}
	/**
	 * Process the table content when the payroll date is
	 * 		on first Pay or 15th day of LBP. 
	 * @param table
	 * @param db
	 * @param conditionColumnAndValueList
	 * @param joinColumnCompareList
	 * @param payrollDate
	 * @param util
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processLBPFirstPayTableContent_15thDay(
			WritableSheet sheet,
			Database db,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,String payrollDate,
			String payrollDateBefore,Utilities util,int lbpDbIndex) throws RowsExceededException, WriteException{
		
//		private void selectBasedFromColumns(
//				String[] tableNameList,
//				String[]columnNameList,
//				OrderByInfo orderInfo
//		){
		
		int colIndex=0,rowIndex=sheet.getRows();
		ArrayList<Double> totalAllLBP15thValueList=new ArrayList<Double>();
		
		//-----------------------------------------------------------------------------------------------
	
		try{		
			//--> If there is a payroll date before data found.
			if(payrollDateBefore!=null){
				
				//--> Add aditional condition including this payroll date before
				conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[1],payrollDateBefore ));
				//--> Add aditional condition to NOT retrieve data with all zero values in a row.
				conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[lbpDbIndex],0 )); // LBP
				conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
				
				
				
				//--> Retrieve all LBP data from database of the chosen payroll date and the payroll date before.
				db.selectDataInDatabase(
					new String[]{db.tableNameEmployee,db.tableNameDeductions},
					new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],db.deductionTableColumnNames[lbpDbIndex],db.payrollDateTableColumnNames[0],db.tableNameEmployee+"."+db.employeeTableColumnNames[0]}, //FamilyName, FirstName, LBP, PayrollDate, DeductionID 
					conditionColumnAndValueList,
					joinColumnCompareList,
					new OrderByInfo(new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3]}, "ASC"),
					Constant.SELECT_SPECIAL_AOBDCSL_30th_PAYROLL_DATA
				);
				
				//--> Set total value List
				for(int i=0;i<3;i++){ // Why 3?: 15th, 30th, Total
					totalAllLBP15thValueList.add(new Double(0));
				}
				
				//-------------------------------------------------
				//--> Process the content of table.
				db.resultSet.last();
				
				int totalNumOfRows=db.resultSet.getRow();
				db.resultSet.beforeFirst();
				
				for(int i=1,count=1;i<=totalNumOfRows;count++,i++,rowIndex++,colIndex=0){
					
					//--> Go to row
					db.resultSet.absolute(i);
					
					//--> Add the number data in cell
					sheet=addNumberInteger(sheet, colIndex, rowIndex, count, timesWrapTextCentreWithBorder);
					colIndex++;
					
					//--> Add the name  to table
					sheet=addLabel(sheet, colIndex, rowIndex,
						db.resultSet.getObject(1).toString()+", "+db.resultSet.getObject(2).toString(),
						timesCenterLeftWithBorder
					);
					colIndex++;
					
					
				
					
					//--> Get the date and employeID of this row which was retrieved from database
					String dateFromRowTable=db.resultSet.getObject(db.metaData.getColumnCount()-1).toString();
					String employeeIDOfThisRow=db.resultSet.getObject(db.metaData.getColumnCount()).toString();
					
					double totalOf15thAnd30th=0;
					int columnValIndex=db.metaData.getColumnCount()-2;
					
					//--> Process when the payroll date of this row is first pay of the month.
					if(payrollDateBefore.equals(dateFromRowTable)){
						//--> Get the employeeID of the next row and return the pointer to this current row.
						String empIDAfterThisRow=null;
						if(i<totalNumOfRows){
							db.resultSet.absolute(i+1);
							empIDAfterThisRow=db.resultSet.getObject(db.metaData.getColumnCount()).toString();
							db.resultSet.absolute(i);
						}
						
						
						//--> Add the value of 15th column cell.
						double value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
						sheet=(value==0)?
								addLabel(sheet, colIndex, rowIndex, "-", timesWrapTextCentreWithBorder)
								:
								addNumberDouble(sheet, colIndex, rowIndex, value, timesCenterRightWithBorder);
						colIndex++;		
						totalOf15thAnd30th+=value;
						totalAllLBP15thValueList.set(0,totalAllLBP15thValueList.get(0)+value);
						
						//--> Add the value of 30th column cell.
						value=0;
						if(empIDAfterThisRow!=null && employeeIDOfThisRow.equals(empIDAfterThisRow)){
							db.resultSet.absolute(i+1);
							value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
							i++;
						}
						sheet=(value==0)?
								addLabel(sheet, colIndex, rowIndex, "-", timesWrapTextCentreWithBorder)
								:
								addNumberDouble(sheet, colIndex, rowIndex, value, timesCenterRightWithBorder);
						colIndex++;	
						totalOf15thAnd30th+=value;
						totalAllLBP15thValueList.set(1,totalAllLBP15thValueList.get(1)+value);
						
					}
					//--> Process when the payroll date of this row is second pay of the month.
					else if(payrollDate.equals(dateFromRowTable)){
						//--> Get the employeeID of the previous row and return the pointer to this current row.
						String empIDBeforeThisRow=null; 
						if(i<totalNumOfRows){
							db.resultSet.absolute(i+1);
							empIDBeforeThisRow=db.resultSet.getObject(db.metaData.getColumnCount()).toString();
							db.resultSet.absolute(i);
						}
						
						
						//--> Add the value of 15th column cell.
						double value=0;
						if(empIDBeforeThisRow!=null && employeeIDOfThisRow.equals(empIDBeforeThisRow)){
							db.resultSet.absolute(i+1);
							value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
							db.resultSet.absolute(i);
							i++;
						}
						sheet=(value==0)?
								addLabel(sheet, colIndex, rowIndex, "-", timesWrapTextCentreWithBorder)
								:
								addNumberDouble(sheet, colIndex, rowIndex, value, timesCenterRightWithBorder);
						colIndex++;	
						totalOf15thAnd30th+=value;
						totalAllLBP15thValueList.set(0,totalAllLBP15thValueList.get(0)+value);
						
						//--> Add the value of 30th column cell.
						value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
						sheet=(value==0)?
							addLabel(sheet, colIndex, rowIndex, "-", timesWrapTextCentreWithBorder)
							:
							addNumberDouble(sheet, colIndex, rowIndex, value, timesCenterRightWithBorder);
						colIndex++;	
						totalOf15thAnd30th+=value;
						totalAllLBP15thValueList.set(1,totalAllLBP15thValueList.get(1)+value);
								
						
					}
					
					
					//--> Add the total value where 15th + 30th value.
					totalOf15thAnd30th=util.convertRoundToOnlyTwoDecimalPlaces(totalOf15thAnd30th);
					sheet=(totalOf15thAnd30th==0)?
							addLabel(sheet, colIndex, rowIndex, "-", timesWrapTextCentreWithBorder)
							:
							addNumberDouble(sheet, colIndex, rowIndex, totalOf15thAnd30th, timesCenterRightWithBorder);
					colIndex++;	
					totalAllLBP15thValueList.set(
						2,
						totalAllLBP15thValueList.get(2)+totalOf15thAnd30th
					);

				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//---------------------------------------------------------------------------------------
		//--> Process ALL Total Asemco/Bcci/Occci/Dbp/Cfi 30th Value.
		colIndex=0;rowIndex=sheet.getRows();
		
		//> None
		sheet=addLabel(sheet, colIndex, rowIndex, "", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		//> Total word
		sheet=addLabel(sheet, colIndex, rowIndex, "TOTAL", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		
		
		//> Total Value of 15th, 30th and 15th+30th
		for(int i=0;i<totalAllLBP15thValueList.size();i++){
			double num= util.convertRoundToOnlyTwoDecimalPlaces(totalAllLBP15thValueList.get(i));
			
			sheet=(num==0)?
					addLabel(sheet, colIndex, rowIndex, "-", timesBoldWrapTextWithBorderMedium)
					:
					addNumberDouble(sheet, colIndex, rowIndex, num, timesBoldRightWithBorderMedium);
			colIndex++;
		
		}
		
		return sheet;
		
	}	
	/**
	 * Process the table content when the payroll date is
	 * 		on second Pay or 30th day of LBP. . 
	 * In LBP, its the opposite of most like asemco since in second pay, 1 column only
	 * @param table
	 * @param db
	 * @param conditionColumnAndValueList
	 * @param joinColumnCompareList
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processLBPSecondPayTableContent_30thDay(WritableSheet sheet,
			Database db,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList, Utilities util, int lbpDbIndex) throws RowsExceededException, WriteException{

//		private String selectInnerJoinWithCondition(
//		String[] tableNameList,
//		String[]columnNameList, 
//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
//		String[] joinColumnCompareList){
		

		int rowIndex=sheet.getRows(), colIndex=0;
		double totalAllLBP30thValue=0;
		
		//--> Add aditional condition to NOT retrieve data with all zero values in a row.
		conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[lbpDbIndex],0 ));
		conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
		//--> Add condition if regular or contractual
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		//--> Add condition if with ATM or NOT only if contractual
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
			WithOrWithoutATMDialog dialog = payrollViewPanel.withOrWithoutATMDialog;
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"WithATM",
					(dialog.withATMRadioBtn.isSelected())?Constant.STRING_YES:Constant.STRING_NO
				)
			);
		}
		
				
				
		//--> Retrieve data from database
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameDeductions},
			new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],db.deductionTableColumnNames[lbpDbIndex]}, //FamilyName, FirstName, LBP 
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3]},"ASC"), // Sort Ascending order LastName FirstName
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------------------------------
		//--> Process the contents of table
		try {
			db.resultSet.last();
		
			int totalNumOfRows=db.resultSet.getRow();
			db.resultSet.beforeFirst();
			
			for(int i=1;i<=totalNumOfRows;i++,colIndex=0,rowIndex++){
				db.resultSet.absolute(i);
				
				//> Add the number data in cell
				sheet=addNumberInteger(sheet, colIndex, rowIndex,
						i,
						timesWrapTextCentreWithBorder);
				colIndex++;
				
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++,colIndex++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					sheet=(j==db.metaData.getColumnCount())?
							addNumberDouble(sheet, colIndex, rowIndex,Double.parseDouble(data.toString()) , timesCenterRightWithBorder)
							:addLabel(sheet, colIndex, rowIndex, data, timesCenterLeftWithBorder);
					
					
					if(j==db.metaData.getColumnCount()){
						totalAllLBP30thValue+=Double.parseDouble(data);
					}
							
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//------------------------------------------------------------------------------------
		
		//--> Process ALL Total Asemco 30th Value.
		colIndex=0;
		rowIndex=sheet.getRows();
		
		//> None
		sheet=addLabel(sheet, colIndex, rowIndex, "", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		//> Total
		sheet=addLabel(sheet, colIndex, rowIndex, "TOTAL", timesBoldWrapTextWithBorderMedium);
		colIndex++;

		
		//> Total Value
		totalAllLBP30thValue=
				util.convertRoundToOnlyTwoDecimalPlaces(totalAllLBP30thValue);
		
		sheet=addNumberDouble(sheet, colIndex, rowIndex, totalAllLBP30thValue, timesBoldRightWithBorderMedium);
		colIndex++;
		
		return sheet;
	}
	/**
	 * Process the content of the table of LBP[LandBank of the Philippines]
	 * @param sheet
	 * @param db
	 * @param day
	 * @param util
	 * @param payrollDate
	 * @param payrollDateBefore
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processPayrollContentLBPSummary(
			WritableSheet sheet, Database db,
			String day,Utilities util,
			String payrollDate,String payrollDateBefore) throws RowsExceededException, WriteException{
	
		
		//--> Required Query
//		SELECT  FamilyName, FirstName, LBP 
//	    FROM employee
//	    INNER JOIN deductions
//	    ON employee.EmployeeID=deductions.EmployeeID
//	    WHERE PayrollDate='2018-04-30';
		
		//-----------------------------------------------------------------------------------------------
		//--> Set Condition parameters
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[1],payrollDate )); // PayrollDate
		
		//-----------------------------------------------------------------------------------------------
		//--> Set inner join paramaters
		String[] joinColumnCompareList= new String[]{
				db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[2]  
		};
		
		//-----------------------------------------------------------------------------------------------
		int lbpDbIndex = 12;
		//--> PROCESS IF THE PAYROLL DATE IS AT 15th or FIRST PAY
		if(util.isFirstPayOfTheMonth(day)){	
			sheet = processLBPFirstPayTableContent_15thDay(sheet, db,
					conditionColumnAndValueList, joinColumnCompareList,
					payrollDate, payrollDateBefore, util,lbpDbIndex);
		}
		//-----------------------------------------------------------------------------------------------
		
		//--> PROCESS IF THE PAYROLL DATE IS AT 30th or SECOND PAY
		else{
			
			sheet = processLBPSecondPayTableContent_30thDay(sheet,
					db, conditionColumnAndValueList, joinColumnCompareList,
					util,lbpDbIndex
			);
		}
		return sheet;
	}
	
	private void l__________SSS_LOAN_and_PAGIBIG_LOAN___________________________________l(){}
	/**
	 * Get the payroll header text for SSS Loan and PAG-IBIG Loan.
	 * @param db
	 * @param list
	 * @param day
	 * @param util
	 * @return
	 */
	private ArrayList<String> getSssPagibigLoanPayrollHeaderTexts(
			ArrayList<String> list){
		
		list.clear();
		
		list.add("#");
		list.add("NAME");
		list.add("Amount");
		
		return list;
	}
	
	/**
	 * Process the content of the table of SSS Loan and PAG-IBIG Loan.
	 * @param sheet
	 * @param db
	 * @param util
	 * @param payrollDate
	 * @param neededColumnName
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processPayrollContentSssPagibigLoanSummary(
			WritableSheet sheet, Database db,
			Utilities util,
			String payrollDate, String neededColumnName) throws RowsExceededException, WriteException{
	
		
		//--> Required Query
//		SELECT  FamilyName, SSSLoan/PagibigLoan
//	    FROM employee
//	    INNER JOIN deductions
//	    ON employee.EmployeeID=deductions.EmployeeID
//	    WHERE PayrollDate='2018-04-30';
		
		//-----------------------------------------------------------------------------------------------
		//--> Set Condition parameters
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[1],payrollDate )); // PayrollDate
		//--> Add aditional condition to NOT retrieve data with all zero values in a row.
		conditionColumnAndValueList.add(new SelectConditionInfo(neededColumnName,0 ));
		conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
		//--> Add condition if regular or contractual
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		//--> Add condition if with ATM or NOT only if contractual
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
			WithOrWithoutATMDialog dialog = payrollViewPanel.withOrWithoutATMDialog;
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"WithATM",
					(dialog.withATMRadioBtn.isSelected())?Constant.STRING_YES:Constant.STRING_NO
				)
			);
		}
		
		
		
		int colIndex=0,rowIndex=sheet.getRows();
		
		
		//-----------------------------------------------------------------------------------------------
		//--> Set inner join paramaters
		String[] joinColumnCompareList= new String[]{
				db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[2]  
		};
		
		//-----------------------------------------------------------------------------------------------
		
//		//--> PROCESS  THE Contents, since the contents are only present at the first pay of the month.

//		private String selectInnerJoinWithCondition(
//		String[] tableNameList,
//		String[]columnNameList, 
//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
//		String[] joinColumnCompareList){
		
		double totalAllSssPagibigLoanValue=0;
		
		//--> Retrieve data from database
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameDeductions},
			new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],neededColumnName}, //FamilyName, FirstName, Asemco/Bcci/Occci/Dbp/Cfi 
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3]},"ASC"), // Sort Ascending order LastName FirstName
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//-----------------------------------------------------------------------------------------------
		//--> Process the contents of table
		try {
			db.resultSet.last();
		
			int totalNumOfRows=db.resultSet.getRow();
			db.resultSet.beforeFirst();
			
			for(int i=1;i<=totalNumOfRows;i++,rowIndex++,colIndex=0){
				db.resultSet.absolute(i);
				
				//> Add the number data in cell
				sheet=addNumberInteger(sheet, colIndex, rowIndex, i, timesWrapTextCentreWithBorder);
				colIndex++;
				
				
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
						
					sheet=(j==db.metaData.getColumnCount())?
							addNumberDouble(sheet, colIndex, rowIndex, Double.parseDouble(data), timesCenterRightWithBorder)
							:
							addLabel(sheet, colIndex, rowIndex, data, timesCenterLeftWithBorder);
					colIndex++;		
							
					
					
					if(j==db.metaData.getColumnCount()){
						totalAllSssPagibigLoanValue+=Double.parseDouble(data);
					}
							
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//-----------------------------------------------------------------------------------------
		//--> Process ALL TOTAL Asemco 15th Value.
		colIndex=0;
		rowIndex=sheet.getRows();
		
		//> None
		sheet=addLabel(sheet, colIndex, rowIndex, " ", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		//> Total
		sheet=addLabel(sheet, colIndex, rowIndex, "TOTAL", timesBoldWrapTextWithBorderMedium);
		colIndex++;

		//> Total Value
		totalAllSssPagibigLoanValue=
				util.convertRoundToOnlyTwoDecimalPlaces(totalAllSssPagibigLoanValue);
		
		sheet=addNumberDouble(sheet, colIndex, rowIndex, totalAllSssPagibigLoanValue, timesBoldRightWithBorderMedium);
		colIndex++;

		return sheet;
	}
	private void l__________UNION_DUES____________________________________________________l(){}
	/**
	 * Get the payroll header text for Union Dues.
	 * @param db
	 * @param list
	 * @param day
	 * @param util
	 * @return
	 */
	private ArrayList<String> getUnionDuesPayrollHeaderTexts(
			ArrayList<String> list){
		
		list.clear();
		
		list.add("#");
		list.add("NAME OF OFFICERS & EMPLOYEES");
		list.add("EE");
		list.add("TOTAL");
		
		return list;
	}
	
	/**
	 * 
	 * Process the content of the table of Union Dues.
	 * @param sheet
	 * @param db
	 * @param util
	 * @param payrollDate
	 * @return
	 * @throws WriteException 
	 * @throws NumberFormatException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processPayrollContentUnionDuesSummary(
			WritableSheet sheet, Database db,
			Utilities util,
			String payrollDate) throws RowsExceededException, NumberFormatException, WriteException{
	
		
		//--> Required Query
//		SELECT  FamilyName, UnionDues
//	    FROM employee
//	    INNER JOIN deductions
//	    ON employee.EmployeeID=deductions.EmployeeID
//	    WHERE PayrollDate='2018-04-30';
		
		int colIndex=0,rowIndex=sheet.getRows();
		int unionDuesDbIndex = 11;
		
		//-----------------------------------------------------------------------------------------------
		//--> Set Condition parameters
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[1],payrollDate )); // PayrollDate
		//--> Add aditional condition to NOT retrieve data with all zero values in a row.
		conditionColumnAndValueList.add(new SelectConditionInfo(util.addSlantApostropheToString(db.deductionTableColumnNames[unionDuesDbIndex]),0 )); //Un-Dues 
		conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
		//--> Add condition if regular or contractual
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		//--> Add condition if with ATM or NOT only if contractual
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
			WithOrWithoutATMDialog dialog = payrollViewPanel.withOrWithoutATMDialog;
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"WithATM",
					(dialog.withATMRadioBtn.isSelected())?Constant.STRING_YES:Constant.STRING_NO
				)
			);
		}
		
		//-----------------------------------------------------------------------------------------------
		//--> Set inner join paramaters
		String[] joinColumnCompareList= new String[]{
				db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[2]  
		};
		
		//-----------------------------------------------------------------------------------------------
		
//		//--> PROCESS  THE Contents, since the contents are only present at the first pay of the month.

//		private String selectInnerJoinWithCondition(
//		String[] tableNameList,
//		String[]columnNameList, 
//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
//		String[] joinColumnCompareList){
		
		ArrayList<Double> totalAllUnionDues30thValueList=new ArrayList<Double>();
		
		//--> Retrieve data from database
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameDeductions},
			new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],util.addSlantApostropheToString(db.deductionTableColumnNames[unionDuesDbIndex])}, //FamilyName, FirstName, UnionDues
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3]},"ASC"), // Sort Ascending order LastName FirstName
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//--> Set the total value list
		int totalColumnsNeeded=2;// EE and Total
		for(int i=0;i<totalColumnsNeeded;i++){
			totalAllUnionDues30thValueList.add(new Double(0));
		}
		
		//-----------------------------------------------------------------------------------------------
		//--> Process the contents of table
		try {
			db.resultSet.last();
		
			int totalNumOfRows=db.resultSet.getRow();
			db.resultSet.beforeFirst();
			
			for(int i=1;i<=totalNumOfRows;i++,rowIndex++,colIndex=0){
				db.resultSet.absolute(i);
				
				//> Add the number data in cell
				sheet=addNumberInteger(sheet, colIndex, rowIndex, i, timesWrapTextCentreWithBorder);
				colIndex++;
				
				
				double totalUnionDuesValue=0;
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					sheet=(j==db.metaData.getColumnCount())?
							addNumberDouble(sheet, colIndex, rowIndex, Double.parseDouble(data), timesCenterRightWithBorder)
							:
							addLabel(sheet, colIndex, rowIndex, data, timesCenterLeftWithBorder);	
					colIndex++;
					
					if(j==db.metaData.getColumnCount()){
						totalAllUnionDues30thValueList.set(
								0,
								totalAllUnionDues30thValueList.get(0)+Double.parseDouble(data)
						); // Set the totalAll values of EE column
						
						totalUnionDuesValue+=Double.parseDouble(data);
					}
				}
				
				
				//> Add The Total in table
				totalAllUnionDues30thValueList.set(1, 
						totalAllUnionDues30thValueList.get(1)+totalUnionDuesValue); // Set the totalAll values of Total column
				
				totalUnionDuesValue=util.convertRoundToOnlyTwoDecimalPlaces(totalUnionDuesValue);
				sheet=addNumberDouble(sheet, colIndex, rowIndex,
						totalUnionDuesValue,
						timesCenterRightWithBorder);
							
				colIndex++;
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//-----------------------------------------------------------------------------------------
		//--> Process ALL TOTAL Union Dues.
		colIndex=0;
		rowIndex=sheet.getRows();
		
		//> None
		sheet=addLabel(sheet, colIndex, rowIndex, " ", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		//> Total
		sheet=addLabel(sheet, colIndex, rowIndex, "TOTAL", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		
		
		//> Total All Value of EE Column 
		totalAllUnionDues30thValueList.set(0, util.convertRoundToOnlyTwoDecimalPlaces(totalAllUnionDues30thValueList.get(0)));
		sheet=addNumberDouble(sheet, colIndex, rowIndex, totalAllUnionDues30thValueList.get(0), timesBoldRightWithBorderMedium);
		colIndex++;
	
		
		
		//> Total All Value of Total Column 
		totalAllUnionDues30thValueList.set(1, util.convertRoundToOnlyTwoDecimalPlaces(totalAllUnionDues30thValueList.get(1)));
		
		sheet=addNumberDouble(sheet, colIndex, rowIndex, totalAllUnionDues30thValueList.get(1), timesBoldRightWithBorderMedium);
		colIndex++;

	
		return sheet;
	}
	
	private void l__________HDMF_PAGIBIG_CONT_and_MEDICARE_____________________________________l(){}
	/**
	 * Get the payroll header text for HDFM and MEDICARE.
	 * @param db
	 * @param list
	 * @param day
	 * @param util
	 * @return
	 */
	private ArrayList<String> getHDMFMedicarePayrollHeaderTexts(
			ArrayList<String> list,int mode){
		
		list.clear();
		
		list.add("#");
		list.add((mode==Constant.PAYROLL_HDMF_PDF)?"NAME OF EMPLOYEE":"NAME OF OFFICERS & EMPLOYEES");
		list.add("EE");
		list.add("ER");
		list.add("TOTAL");
		
		return list;
	}
	
	/**
	 * Process the content of the table of HDMF and MEDICARE.
	 * @param table
	 * @param db
	 * @param util
	 * @param payrollDate
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processPayrollContentHDMFMedicareSummary(
			WritableSheet sheet, Database db,
			Utilities util,
			String payrollDate,String neededColumnName,String neededColumnNameER) throws RowsExceededException, WriteException{
		
		int colIndex=0,rowIndex=sheet.getRows();
		
		//--> Required Query
//		SELECT FamilyName,FirstName,`Pag-ibigCont`, `Pag-ibigEr`
//	    FROM employee  
//	    INNER JOIN deductions 
//	    ON employee.EmployeeID=deductions.EmployeeID
//	    INNER JOIN employershare
//	    ON deductions.DeductionID=employershare.ID
//	    WHERE deductions.PayrollDate='2018-04-30';
		
		//-----------------------------------------------------------------------------------------------
		//--> Set Condition parameters
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				db.tableNameDeductions+"."+db.deductionTableColumnNames[1],
				payrollDate 
			)
		); // PayrollDate
		
		//--> Add aditional condition to NOT retrieve data with all zero values in a row.
		conditionColumnAndValueList.add(new SelectConditionInfo(neededColumnName,0 )); //Pag-ibig Cont, Medicare 
		conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
		
		conditionColumnAndValueList.add(new SelectConditionInfo(neededColumnNameER,0 )); //PagibigEr, MedicareEr
		conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
		
		
				
		
		//-----------------------------------------------------------------------------------------------
		//--> Set inner join paramaters
		String[] joinColumnCompareList= new String[]{
				db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[2],
				db.tableNameDeductions+"."+db.deductionTableColumnNames[0]+"="+db.tableNameEmployerShare+"."+db.employerShareTableColumnNames[0]
		};
		
		//-----------------------------------------------------------------------------------------------
		
//		//--> PROCESS  THE Contents, since the contents are only present at the first pay of the month.

//		private String selectInnerJoinWithCondition(
//		String[] tableNameList,
//		String[]columnNameList, 
//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
//		String[] joinColumnCompareList){
		
		ArrayList<Double> totalAllHDMFMedicare30thValueList=new ArrayList<Double>();
		
		//--> Retrieve data from database
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameDeductions,db.tableNameEmployerShare},
			new String[]{
					db.employeeTableColumnNames[2],
					db.employeeTableColumnNames[3],
					neededColumnName,
					neededColumnNameER
			}, //FamilyName, FirstName, Pagibig Cont/Medicare, Pag-ibig Er/Medicare 
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3]},"ASC"), // Sort Ascending order LastName FirstName
			Constant.SELECT_SPECIAL_HDMF_MEDICARE_30th_PAYROLL_DATA
		);
		
		//--> Set the total value list
		int totalColumnsNeeded=3;// EE , ER and Total
		for(int i=0;i<totalColumnsNeeded;i++){
			totalAllHDMFMedicare30thValueList.add(new Double(0));
		}
		
		//-----------------------------------------------------------------------------------------------
		//--> Process the contents of table
		try {
			db.resultSet.last();
		
			int totalNumOfRows=db.resultSet.getRow();
			db.resultSet.beforeFirst();
			
			for(int i=1;i<=totalNumOfRows;i++,rowIndex++,colIndex=0){
				db.resultSet.absolute(i);
				//> Add the number data in cell
				sheet=addNumberInteger(sheet, colIndex, rowIndex, i, timesWrapTextCentreWithBorder);
				colIndex++;
				
				double totalHDMFMedicareValue=0;
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					
					sheet=(j>=db.metaData.getColumnCount()-1)?
							addNumberDouble(sheet, colIndex, rowIndex, Double.parseDouble(data), timesCenterRightWithBorder)
							:
							addLabel(sheet, colIndex, rowIndex, data, timesCenterLeftWithBorder);
					colIndex++;
					
					//--> Process if the data is a number and not the name.
					if(j>=db.metaData.getColumnCount()-1){ // if the data is from column EE and ER
						int index=(j==db.metaData.getColumnCount()-1)?0:1; // If the index is for the total of EE[index->0] or ER[index->1]
						
						totalAllHDMFMedicare30thValueList.set(
								index,
								totalAllHDMFMedicare30thValueList.get(index)+Double.parseDouble(data)
						); // Set the totalAll values of EE and ER column
						
					
						//> This add the EE and ER 
						totalHDMFMedicareValue+=Double.parseDouble(data);
					}		
				}
//				
				//> Add The Total in table
				//> WHy 2? since the TOTAL column in the table is at index 2 on the variable totalAllHDMF30thValueList.
				totalAllHDMFMedicare30thValueList.set(2, 
						totalAllHDMFMedicare30thValueList.get(2)+totalHDMFMedicareValue); // Set the totalAll values of Total column
				
				totalHDMFMedicareValue=util.convertRoundToOnlyTwoDecimalPlaces(totalHDMFMedicareValue);
				
				sheet=addNumberDouble(sheet, colIndex, rowIndex, totalHDMFMedicareValue, timesCenterRightWithBorder);
				colIndex++;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//-----------------------------------------------------------------------------------------
		//--> Process ALL TOTAL Union Dues.
		colIndex=0;
		rowIndex=sheet.getRows();
		
		//> None
		sheet=addLabel(sheet, colIndex, rowIndex, " ", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		
		//> Total
		sheet=addLabel(sheet, colIndex, rowIndex, "TOTAL", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		
		//> Total All Value of EE Column 
		totalAllHDMFMedicare30thValueList.set(0, util.convertRoundToOnlyTwoDecimalPlaces(totalAllHDMFMedicare30thValueList.get(0)));
		sheet=addNumberDouble(sheet, colIndex, rowIndex, totalAllHDMFMedicare30thValueList.get(0), timesBoldRightWithBorderMedium);
		colIndex++;
		
		
		
		
		//> Total All Value of ER Column 
		totalAllHDMFMedicare30thValueList.set(1, util.convertRoundToOnlyTwoDecimalPlaces(totalAllHDMFMedicare30thValueList.get(1)));
		sheet=addNumberDouble(sheet, colIndex, rowIndex, totalAllHDMFMedicare30thValueList.get(1), timesBoldRightWithBorderMedium);
		colIndex++;
	
		
		
		//> Total All Value of Total Column 
		totalAllHDMFMedicare30thValueList.set(2, util.convertRoundToOnlyTwoDecimalPlaces(totalAllHDMFMedicare30thValueList.get(2)));
		sheet=addNumberDouble(sheet, colIndex, rowIndex, totalAllHDMFMedicare30thValueList.get(2), timesBoldRightWithBorderMedium);
		colIndex++;
		
		return sheet;
	}
	private void l__________SSS_CONTRIBUTION________________________________________l(){}
	/**
	 * Get the payroll header text for SSS Contribution.
	 * @param db
	 * @param list
	 * @param day
	 * @param util
	 * @return
	 */
	private ArrayList<String> getSSSContributionPayrollHeaderTexts(
			ArrayList<String> list){
		
		list.clear();
		
		list.add("#");
		list.add("NAME OF OFFICERS & EMPLOYEES");
		list.add("EE");
		list.add("ER");
		list.add("EC");
		list.add("TOTAL");
		
		return list;
	}
	
	/**
	 * 
	 * Process the content of the table of SSS CONTRIBUTION.
	 * @param sheet
	 * @param db
	 * @param util
	 * @param payrollDate
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processPayrollContentSSSContributionSummary(
			WritableSheet sheet, Database db,
			Utilities util,
			String payrollDate) throws RowsExceededException, WriteException{
	
		int colIndex=0,rowIndex=sheet.getRows();
		
		//--> Required Query
//		SELECT FamilyName,FirstName,`SSS COnt`, `SSS ER`
//	    FROM employee  
//	    INNER JOIN deductions 
//	    ON employee.EmployeeID=deductions.EmployeeID
//	    INNER JOIN employershare
//	    ON deductions.DeductionID=employershare.ID
//	    WHERE deductions.PayrollDate='2018-04-30';
		
		//-----------------------------------------------------------------------------------------------
		//--> Set Condition parameters
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(
				db.tableNameDeductions+"."+db.deductionTableColumnNames[1],
				payrollDate 
			)
		); // PayrollDate
		
		//--> Add condition if regular or contractual
		conditionColumnAndValueList.add(new SelectConditionInfo(
				"HiredAs",
				(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_REGULAR)?Constant.STRING_REGULAR:Constant.STRING_CONTRACTUAL
			)
		);
		//--> Add condition if with ATM or NOT only if contractual
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL){
			WithOrWithoutATMDialog dialog = payrollViewPanel.withOrWithoutATMDialog;
			conditionColumnAndValueList.add(new SelectConditionInfo(
					"WithATM",
					(dialog.withATMRadioBtn.isSelected())?Constant.STRING_YES:Constant.STRING_NO
				)
			);
		}
		
		//-----------------------------------------------------------------------------------------------
		//--> Set inner join paramaters
		String[] joinColumnCompareList= new String[]{
				db.tableNameEmployee+"."+db.employeeTableColumnNames[0]+"="+db.tableNameDeductions+"."+db.deductionTableColumnNames[2],
				db.tableNameDeductions+"."+db.deductionTableColumnNames[0]+"="+db.tableNameEmployerShare+"."+db.employerShareTableColumnNames[0]
		};
		
		//-----------------------------------------------------------------------------------------------
		
		//--> PROCESS  THE Contents, since the contents are only present at the first pay of the month.

//		private String selectInnerJoinWithCondition(
//		String[] tableNameList,
//		String[]columnNameList, 
//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
//		String[] joinColumnCompareList){
		
		ArrayList<Double> totalAllSSSCont30thValueList=new ArrayList<Double>();
		

		//--> Retrieve data from database
		db.selectDataInDatabase(
			new String[]{db.tableNameEmployee,db.tableNameDeductions,db.tableNameEmployerShare},
			new String[]{
					db.employeeTableColumnNames[2],
					db.employeeTableColumnNames[3],
					db.deductionTableColumnNames[5],
					db.employerShareTableColumnNames[3]
			}, //FamilyName, FirstName, SSSCont, SSS Er 
			conditionColumnAndValueList,
			joinColumnCompareList,
			new OrderByInfo(new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3]},"ASC"), // Sort Ascending order LastName FirstName
			Constant.SELECT_INNER_JOIN_WITH_CONDITION_AND
		);
		
		//--> Set the total value list
		int totalColumnsNeeded=4;// EE , ER, EC and Total
		for(int i=0;i<totalColumnsNeeded;i++){
			totalAllSSSCont30thValueList.add(new Double(0));
		}
		
		//-----------------------------------------------------------------------------------------------
		//--> Process the contents of table
		try {
			db.resultSet.last();
		
			int totalNumOfRows=db.resultSet.getRow();
			db.resultSet.beforeFirst();
			
			for(int i=1;i<=totalNumOfRows;i++,rowIndex++,colIndex=0){
				db.resultSet.absolute(i);
				
				
				//> Add the number data in cell
				sheet=addNumberInteger(sheet, colIndex, rowIndex, i, timesWrapTextCentreWithBorder);
				colIndex++;
				
				
				double totalSSSContValue=0,ee=-1,er=-1;
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					sheet=(j>=db.metaData.getColumnCount()-1)?
							addNumberDouble(sheet, colIndex, rowIndex, Double.parseDouble(data), timesCenterRightWithBorder)
							:
							addLabel(sheet, colIndex, rowIndex, data, timesCenterLeftWithBorder);	
					colIndex++;
					
					
					//--> Process if the data is a number and not the name.
					if(j>=db.metaData.getColumnCount()-1){ // if the data is from column EE and ER
						int index=(j==db.metaData.getColumnCount()-1)?0:1; // If the index is for the total of EE[index->0] or ER[index->1]
						
						totalAllSSSCont30thValueList.set(
								index,
								totalAllSSSCont30thValueList.get(index)+Double.parseDouble(data)
						); // Set the totalAll values of EE and ER column
						
						
						//> This add the EE and ER 
						totalSSSContValue+=Double.parseDouble(data);
						
						
						//Store value of ee and er
						if(j==db.metaData.getColumnCount()-1){
							ee=Double.parseDouble(data);
						}
						else if(j==db.metaData.getColumnCount()){
							er=Double.parseDouble(data);
						}
					}
				
				}
				
				//> Process the EC part of the column
				double ec=db.getECFromSSSDataList(ee, er);
				totalAllSSSCont30thValueList.set(2, 
				totalAllSSSCont30thValueList.get(2)+ec); // Set the totalAll values of Total column
				
				sheet=addNumberDouble(sheet, colIndex, rowIndex,
						ec,
						timesCenterRightWithBorder);
							
				colIndex++;
				totalSSSContValue+=ec;
				
				
				//-------------------------------------
				
				//> Add The Total in table
				//> WHy 2? since the TOTAL column in the table is at index 2 on the variable totalAllHDMF30thValueList.
				totalAllSSSCont30thValueList.set(3, 
						totalAllSSSCont30thValueList.get(3)+totalSSSContValue); // Set the totalAll values of Total column
				
				totalSSSContValue=util.convertRoundToOnlyTwoDecimalPlaces(totalSSSContValue);
				sheet=addNumberDouble(sheet, colIndex, rowIndex,
						totalSSSContValue,
						timesCenterRightWithBorder);
							
				colIndex++;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//-----------------------------------------------------------------------------------------
		//--> Process ALL TOTAL Union Dues.
		colIndex=0;
		rowIndex=sheet.getRows();
		
		//> None
		sheet=addLabel(sheet, colIndex, rowIndex, " ", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		//> Total
		sheet=addLabel(sheet, colIndex, rowIndex, "TOTAL", timesBoldWrapTextWithBorderMedium);
		colIndex++;
		
		
		//------------
		//> Total All Value of EE Column 
		totalAllSSSCont30thValueList.set(0, util.convertRoundToOnlyTwoDecimalPlaces(totalAllSSSCont30thValueList.get(0)));
		sheet=addNumberDouble(sheet, colIndex, rowIndex,
				totalAllSSSCont30thValueList.get(0), timesBoldRightWithBorderMedium
		);
		colIndex++;
		
		
		
		
		
		//------------
		//> Total All Value of ER Column 
		totalAllSSSCont30thValueList.set(1, util.convertRoundToOnlyTwoDecimalPlaces(totalAllSSSCont30thValueList.get(1)));
		sheet=addNumberDouble(sheet, colIndex, rowIndex,
				totalAllSSSCont30thValueList.get(1), timesBoldRightWithBorderMedium
		);
		colIndex++;
		
		
		//------------
		//> Total All Value of EC Column 
		totalAllSSSCont30thValueList.set(2, util.convertRoundToOnlyTwoDecimalPlaces(totalAllSSSCont30thValueList.get(2)));
		sheet=addNumberDouble(sheet, colIndex, rowIndex,
				totalAllSSSCont30thValueList.get(2), timesBoldRightWithBorderMedium
		);
		colIndex++;
		
		
				
		//------------
		//> Total All Value of Total Column 
		totalAllSSSCont30thValueList.set(3, util.convertRoundToOnlyTwoDecimalPlaces(totalAllSSSCont30thValueList.get(3)));
		sheet=addNumberDouble(sheet, colIndex, rowIndex,
				totalAllSSSCont30thValueList.get(3), timesBoldRightWithBorderMedium
		);
		colIndex++;
		
		
	
		return sheet;
	}
	private void l_______________________________________________________________l(){}

    private WritableSheet addNumberInteger(WritableSheet sheet, int column, int row,
            Integer integerNum,WritableCellFormat times) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integerNum, times);
        sheet.addCell(number);
        
        return sheet;
    }

    private WritableSheet addNumberDouble(WritableSheet sheet, int column, int row,
            Double doubleNum,WritableCellFormat times) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, doubleNum, times);
        sheet.addCell(number);
        
        return sheet;
    }
    private WritableSheet addLabel(WritableSheet sheet, int column,
    		int row, String string,WritableCellFormat times)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, string, times);
        sheet.addCell(label);
        
        return sheet;
    }
	
    /**
	 * Assign the values of total value list to zero.
	 * @param list
	 * @return
	 */
	private ArrayList<PayrollPDFTotalValueInfo> assignTotalListValuesToZero(ArrayList<PayrollPDFTotalValueInfo> list){
		for(PayrollPDFTotalValueInfo info:list){
			info.value=0;
		}
		return list;
	}    
    
    /**
     * Merge cells/Rowspan
     * @param sheet
     * @param col1
     * @param row1
     * @param row2
     * @param col2
     * @return
     * @throws RowsExceededException
     * @throws WriteException
     */
    private WritableSheet mergeCells(WritableSheet sheet,int col1,int row1,int col2,int row2) throws RowsExceededException, WriteException{
    	sheet.mergeCells(col1, row1, col2, row2);
    	return sheet;
    }
    
    /**
	 * Get before payroll date based from given payroll date.
	 * Example: payroll date-> May 15,2018
	 * 			before payroll date-> April 30,2018
	 * @param db
	 * @param payrollDate
	 * @param util
	 * @return
	 */
	private String getBeforePayrollDateBasedFromGivenPayrollDate(Database db, String payrollDate,
			Utilities util){
		//--> Retrieve all PayrollDate data
		db.selectDataInDatabase(
			new String[]{db.tableNamePayrollDate},
			new String[]{db.payrollDateTableColumnNames[0]},
			null,
			null,
			null,
			Constant.SELECT_BASED_FROM_COLUMN
		);
				
		//-----------------------------------------------------------------------------------------------
				
		//--> Get the date before the chosen payroll date.
		String payrollDateBefore=null;
		try{
			db.resultSet.last();
			
			int totalNumOfRows=db.resultSet.getRow();
			db.resultSet.beforeFirst();
			
			System.out.println("\t ZZZZTotal Number of Rows: "+totalNumOfRows+CLASS_NAME);
			//> Check if there is available data before this chosen payroll date.
			db.resultSet.absolute(1);
			if(payrollDate.equals(db.resultSet.getObject(1).toString())){
				MainFrame.getInstance().showOptionPaneMessageDialog("There are no available data before the payroll date: "+util.convertDateYyyyMmDdToReadableDate(payrollDate), JOptionPane.ERROR_MESSAGE);
			}
			else{
				for(int i=1;i<=totalNumOfRows && totalNumOfRows>1;i++){
					db.resultSet.absolute(i);
					
					//> If it matched the chosen payroll date, then get the before date data.
					if(db.resultSet.getObject(1).toString().equals(payrollDate)){	//> Only 1 column: PayrollDate
						db.resultSet.absolute(i-1);
						payrollDateBefore=db.resultSet.getObject(1).toString();
						return payrollDateBefore;
			
					}
				}
			
				}	
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}  
	/**
	 * Set the String content of the payroll header list.
	 * @param db
	 * @param list
	 * @return
	 */
	private ArrayList<String> getEarnDedsPayrollHeaderTexts(Database db,ArrayList<String> list,int mode){
		list.clear();
		totalValueList.clear();
		
		list.add("#");
		list.add((mode==Constant.PAYROLL_PER_DEPARTMENT_EXCEL)?"Name of Employee":"Name of Dept");
		int numOfFirstColumnsNotIncluded=3; // ID, Payroll Data, EmployeeID 
		
		//-----------------------------------------------
		
		
		//--> Transfer the correct columns in new array strings
		if(correctDedsColumnList==null && correctEarnColumnList==null){
			
			correctEarnColumnList=new String[db.earningTableColumnNames.length-1-numOfFirstColumnsNotIncluded];
			//--> Added extra 1 for extra empty column header
			correctDedsColumnList= new String[db.deductionTableColumnNames.length-2-numOfFirstColumnsNotIncluded+1]; 
			
			for(int i=numOfFirstColumnsNotIncluded,j=0;i<db.earningTableColumnNames.length-1;i++,j++){
				correctEarnColumnList[j]=db.earningTableColumnNames[i];
			}
			for(int i=numOfFirstColumnsNotIncluded,j=0;i<db.deductionTableColumnNames.length-2;i++,j++){
				correctDedsColumnList[j]=db.deductionTableColumnNames[i];
			}
			//--> Added extra 1 for extra empty column header
			correctDedsColumnList[correctDedsColumnList.length-1]="";
			
			
		}
		
		//-----------------------------------------------
		
		//--> Add to ArrayList the first half EARNING column names;
		for(int i=0;i<correctEarnColumnList.length/2;i++){
			list.add(correctEarnColumnList[i]);
			totalValueList.add(new PayrollPDFTotalValueInfo(0.0, correctEarnColumnList[i]));
		}
		
		
		//--> Add the Total EARNING name
		list.add(db.earningTableColumnNames[db.earningTableColumnNames.length-1]);
		totalValueList.add(new PayrollPDFTotalValueInfo(0.0, db.earningTableColumnNames[db.earningTableColumnNames.length-1]));
		

		//-----------------------------------------------
		
		//--> Add to ArrayList the first half DEDUCTION column names;
		for(int i=0;i<correctDedsColumnList.length/2;i++){
			list.add(correctDedsColumnList[i]);
			totalValueList.add(new PayrollPDFTotalValueInfo(0.0, correctDedsColumnList[i]));
			
		}

		
		//--> Add the Total DEDUCTION name
		list.add(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]);
		totalValueList.add(new PayrollPDFTotalValueInfo(0.0, db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]));
		
		//--> Add NET PAY
		list.add(netPayColumnName);
		totalValueList.add(new PayrollPDFTotalValueInfo(0.0, netPayColumnName));
		
		
		//-----------------------------------------------
		
		//--> Add the remaining half EARNING column names
		for(int i=correctEarnColumnList.length/2;i<correctEarnColumnList.length;i++){
			list.add(correctEarnColumnList[i]);
			totalValueList.add(new PayrollPDFTotalValueInfo(0.0, correctEarnColumnList[i]));
			
		}

		
		//--> Add the remaining half DEDUCTION column names
		for(int i=correctDedsColumnList.length/2;i<correctDedsColumnList.length;i++){
			list.add(correctDedsColumnList[i]);
			totalValueList.add(new PayrollPDFTotalValueInfo(0.0, correctDedsColumnList[i]));
		}
		
		return list;
	}
	
	
	/**
	 * Processing the payroll header. This method is where the headers are created.
	 * This payroll head process is complex since it has rowspan of 2.
	 * @param table
	 * @param db
	 * @param util
	 * @param font
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processComplexPayrollHeaderRegular(WritableSheet sheet,Database db,
			Utilities util,int totalColumns) throws RowsExceededException, WriteException{
		
		cellNumberRowSpanTwoList.clear();
		int counter=0,colIndex=0,rowIndex=sheet.getRows(),secondRowIndex=rowIndex+1,
				earnLastIndex=db.earningTableColumnNames.length-1,
				dedLasiIndex=db.deductionTableColumnNames.length-2;
		for(String str:payrollHeaderList){
			


//			
			if(counter<2  || 											// # and Name of Employee
				str.equals(db.earningTableColumnNames[earnLastIndex]) || 	// Total Earnings[GREEN]
				str.equals(db.deductionTableColumnNames[dedLasiIndex]) || 	// Total Deds[GREEN]
				str.equals(netPayColumnName)){										// Net Pay[GREEN]
				
				
				sheet=addLabel(sheet, colIndex, rowIndex,
						util.convertCamelCaseColumnNamesToReadable(str),
						timesBoldWrapTextWithBorderMedium);
				sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
				
				if(!str.equals(netPayColumnName)){
					cellNumberRowSpanTwoList.add(counter);
				}
			}
			else{
				sheet=addLabel(sheet, colIndex, rowIndex,
						util.convertCamelCaseColumnNamesToReadable(str),
						timesBoldWrapTextWithBorderMedium);
				
			}
			
			//--> When the counter reached the total colums, the value of column index will reset and the row index will increment to 1.
			if(counter==totalColumns-1){
				colIndex=1;
				rowIndex++;
			}
			
			//--> This if statement added 1 to column index so that when the column is equal to Total Earnings, then it will not overwrite or have an error.
			if(colIndex==6 && rowIndex==secondRowIndex){
				colIndex++;
			}
			
			//--> Increment
			colIndex++;
			counter++;
		}
		
	
		
		
		return sheet;
	}
	/**
	 * Processing the payroll header. This method is where the headers are created.
	 * This payroll head process is complex since it has rowspan of 2 inclusive for CONTRACTUAL.
	 * @param table
	 * @param db
	 * @param util
	 * @param font
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processComplexPayrollHeaderContractual(WritableSheet sheet,Database db,
			Utilities util,int totalColumns) throws RowsExceededException, WriteException{
		
		
		for(int i=0,colIndex=0,rowIndex=sheet.getRows()+1,staticRowIndex=rowIndex;i<payrollHeaderList.size();i++,colIndex++){
			String str = payrollHeaderList.get(i);
			sheet=addLabel(sheet, colIndex, rowIndex,
					util.convertCamelCaseColumnNamesToReadable(str),
					timesBoldWrapTextWithBorderMedium);
			
			// When # and Name, NetPay and Signature
			if((i==0 || i==1 || i==4 || i==5) ){
				sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, staticRowIndex+1);
			}
			
			// When Earnings
			if(i==2){
				sheet=mergeCells(sheet, colIndex, rowIndex, 6, rowIndex);
				colIndex=6;
			}
			
			// When Deductions
			if(i==3){
				sheet=mergeCells(sheet, colIndex, rowIndex, 15, rowIndex);
				colIndex=15;
			}
			
			// Change row index
			if(i==5){
				rowIndex++;
				colIndex=1;
			}
		}
		
		
		
//		cellNumberRowSpanTwoList.clear();
//		int counter=0,colIndex=0,rowIndex=sheet.getRows(),secondRowIndex=rowIndex+1,
//				earnLastIndex=db.earningTableColumnNames.length-1,
//				dedLasiIndex=db.deductionTableColumnNames.length-1;
//		for(String str:payrollHeaderList){
//			
//
//
////			
//			if(counter<2  || 											// # and Name of Employee
//				str.equals(db.earningTableColumnNames[earnLastIndex]) || 	// Total Earnings[GREEN]
//				str.equals(db.deductionTableColumnNames[dedLasiIndex]) || 	// Total Deds[GREEN]
//				str.equals(netPayColumnName)){										// Net Pay[GREEN]
//				
//				
//				sheet=addLabel(sheet, colIndex, rowIndex,
//						util.convertCamelCaseColumnNamesToReadable(str),
//						timesBoldWrapTextWithBorderMedium);
//				sheet=mergeCells(sheet, colIndex, rowIndex, colIndex, rowIndex+1);
//				
//				if(!str.equals(netPayColumnName)){
//					cellNumberRowSpanTwoList.add(counter);
//				}
//			}
//			else{
//				sheet=addLabel(sheet, colIndex, rowIndex,
//						util.convertCamelCaseColumnNamesToReadable(str),
//						timesBoldWrapTextWithBorderMedium);
//				
//			}
//			
//			//--> When the counter reached the total colums, the value of column index will reset and the row index will increment to 1.
//			if(counter==totalColumns-1){
//				colIndex=1;
//				rowIndex++;
//			}
//			
//			//--> This if statement added 1 to column index so that when the column is equal to Total Earnings, then it will not overwrite or have an error.
//			if(colIndex==6 && rowIndex==secondRowIndex){
//				colIndex++;
//			}
//			
//			//--> Increment
//			colIndex++;
//			counter++;
//		}
		
	
		
		
		return sheet;
	}
	/**
	 * 
	 * Processing the contents of in payroll where heads will sign their signature.
	 * In my case I made it in a table so that the spacing is unifoirm.
	 * @param sheet
	 * @param util
	 * @param mode
	 * @return
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private WritableSheet processPayrollContentSignature(
			WritableSheet sheet, Utilities util,
			int mode) throws RowsExceededException, WriteException{
		
		Database db = Database.getInstance();
		int rowIndex=sheet.getRows()+6;  // Why 6? to give six spaces from total row to signature content,
		int colIndex=1;
		boolean isLandscapeSignatureLayout =((mode == Constant.PAYROLL_PER_DEPARTMENT_EXCEL ||
				mode == Constant.PAYROLL_OVERALL_EXCEL));
		int margin=(isLandscapeSignatureLayout)?4:3; // The spaces between names.
		
		
		String [][] contentNameList={
				{"Prepared by: ","Checked by: ","Audited by: ","Approved by: "},
				{	
					db.signatureTableDataList.get(db.signatureTableColumnNames[1]),
					db.signatureTableDataList.get(db.signatureTableColumnNames[3]),
					db.signatureTableDataList.get(db.signatureTableColumnNames[5]),
					db.signatureTableDataList.get(db.signatureTableColumnNames[7]),
				},
				{
					db.signatureTableDataList.get(db.signatureTableColumnNames[2]),
					db.signatureTableDataList.get(db.signatureTableColumnNames[4]),
					db.signatureTableDataList.get(db.signatureTableColumnNames[6]),
					db.signatureTableDataList.get(db.signatureTableColumnNames[8]),
				},
		};
		
		
		//----------------------------------------------------------------------
		if(isLandscapeSignatureLayout){
			for(int i=0;i<contentNameList.length;i++,rowIndex++){
				colIndex=1;
				
				WritableCellFormat format=(i==1)?timesBoldLeft:timesLeft;
				rowIndex=(i==1)?rowIndex+2:rowIndex;
				
				for(int j=0;j<contentNameList[i].length;j++,colIndex+=margin){
					String str=contentNameList[i][j];
					sheet=addLabel(sheet, colIndex, rowIndex, str, format);
				}
			}
		}
		else{
			// Only put the first three, leave the last one.
			for(int i=0;i<contentNameList.length;i++,rowIndex++){
				colIndex=0;
				
				WritableCellFormat format=(i==1)?timesBoldLeft:timesLeft;
				rowIndex=(i==1)?rowIndex+2:rowIndex;
				
				for(int j=0;j<contentNameList[i].length-1;j++,colIndex+=margin){
					String str=contentNameList[i][j];
					sheet=addLabel(sheet, colIndex, rowIndex, str, format);
				}
			}
			
			// Add GM below the three above
			colIndex=3;
			rowIndex=sheet.getRows()+2;
			int lastIndex=contentNameList[0].length-1;
			for(int i=0;i<contentNameList.length;i++,rowIndex++){
				WritableCellFormat format=(i==1)?timesBoldLeft:timesLeft;
				rowIndex=(i==1)?rowIndex+2:rowIndex;
				
				
				String str=contentNameList[i][lastIndex];
				sheet=addLabel(sheet, colIndex, rowIndex, str, format);
			}
		}
		
		
		return sheet;
	}
	
	/**
	 * This is where the creation of the headers in table put in pdf.
	 * This is called simple since it has NO row spans.
	 * @param sheet
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private WritableSheet processSimplePayrollHeader(WritableSheet sheet) throws RowsExceededException, WriteException{
		
		int colIndex=0,rowIndex=sheet.getRows()+1;
		
		for(String str:payrollHeaderList){
			sheet=addLabel(sheet, colIndex, rowIndex,str, timesBoldWrapTextWithBorderMedium);
			colIndex++;
		}
		return sheet;
	}
    private void l_________________________________________________l(){}
	
	public static ExcelCreator getInstance(){
		if(instance == null)
			instance = new ExcelCreator();
		
		return instance;
	}
	  
	public static void setInstanceToNull(){
		instance =null;
	}
	
	
	
	
	
}
