package model.statics;


import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AllPermission;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Constant;
import model.OrderByInfo;
import model.PayrollPDFTotalValueInfo;
import model.PayrollTableModel;
import model.PayslipDataStorageInfo;
import model.SelectConditionInfo;
import model.view.DisplayOptionsDialog;
import model.view.PayrollSlipLayout;
import model.view.ReusableTable;
import model.view.WithOrWithoutATMDialog;
import view.MainFrame;
import view.views.HomeViewPanel;
import view.views.PayrollViewPanel;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;

import database.Database;
/**
 * PDF Creator
 * 
 * Tips in creating pdf:
 * 	1. When adding swing components in pdf file, you can use Image
 * 		to put the swing component in an image.
 * @author XietrZ
 *
 */
public class PDFCreator {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static PDFCreator instance;
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
	      Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
	      Font.NORMAL);
	private Rotate rotate;
	private ArrayList<PayrollPDFTotalValueInfo>totalValueList,departmentTotalValueList;
	private ArrayList<String> payrollHeaderList;
	private Vector<Integer> cellNumberRowSpanTwoList;
	private String[] correctEarnColumnList=null,correctDedsColumnList= null;
	private String netPayColumnName="NetPay", signatureColumnName="Signature";
	private Document document;
	private PayrollViewPanel payrollViewPanel;
	
	
	public PdfWriter writer;
	public boolean isSuccessCreatingPDF=false;
	private void l____________________________________l(){}
	
	public PDFCreator(){
		payrollHeaderList=new ArrayList<String>();
		cellNumberRowSpanTwoList=new Vector<Integer>();
		totalValueList=new ArrayList<PayrollPDFTotalValueInfo>();
		departmentTotalValueList=new ArrayList<PayrollPDFTotalValueInfo>();
		
		payrollViewPanel= PayrollViewPanel.getInstance();
	}
	
	private void l________________________________l(){}
	 
	
	/**
	 * Create pdf.
	 * @param filename
	 * @param payslipDataStorageList
	 * @param payslip
	 * @param mode
	 */
	private void createPdf(String filename,ArrayList<PayslipDataStorageInfo> payslipDataStorageList,
			PayrollSlipLayout payslip,int mode){
		System.out.println("\t"
				+ "File path: "+ filename+CLASS_NAME);
		document = null;
		try {
			//--------------------------------------------------------------------------------
			 
			
			
			if(mode==Constant.PAYSLIP_PDF ||
					mode==Constant.PAYROLL_ASEMCO_PDF ||
					mode==Constant.PAYROLL_BCCI_PDF   ||
					mode==Constant.PAYROLL_OCCCI_PDF  ||
					mode==Constant.PAYROLL_DBP_PDF 	  ||
					mode==Constant.PAYROLL_CFI_PDF	  ||
					mode==Constant.PAYROLL_ST_PETER_PLAN_PDF||
					mode==Constant.PAYROLL_W_TAX_PDF||		
					mode==Constant.PAYROLL_LBP_PDF	  ||		
					mode==Constant.PAYROLL_SSS_LOAN_PDF ||
					mode==Constant.PAYROLL_PAGIBIG_LOAN_PDF ||
					mode==Constant.PAYROLL_UNION_DUES_PDF ||
					mode==Constant.PAYROLL_HDMF_PDF ||
					mode==Constant.PAYROLL_MEDICARE_PDF ||
					mode==Constant.PAYROLL_SSS_CONT_PDF){
				
				document = new Document(PageSize.LEGAL);
			}
			else if(mode == Constant.PAYROLL_PER_DEPARTMENT_PDF ||
					mode == Constant.PAYROLL_OVERALL_PDF ||
					mode ==Constant.PAYROLL_PER_CONTRACTUAL_EMPLOYEE_PDF){
				document = new Document(PageSize.LEGAL.rotate());
			}
	
			
			//--------------------------------------------------------------------------------
					 
//			Document document = new Document(PageSize.A4);
			writer = PdfWriter.getInstance(document, new FileOutputStream(filename));			
			 
			document.open();
			 
			//--------------------------------------------------------------------------------
			 
			//--> Add the whole contents of the pdf files.
//			createTheWholeContentOfPDF(document);
			
			//--> PAYSLIP
			if(mode==Constant.PAYSLIP_PDF)
				createPayslipPDF(document, payslipDataStorageList,payslip);
			//--> REGULAR
			else if(mode==Constant.PAYROLL_PER_DEPARTMENT_PDF){
//				createPayrollPDF(document);
				createPayrollPDFPerDepartmentSummary(document,mode);
//				document.newPage();
			}
			//--> PAYROLL DEPARTMENT SUMMARY REGULAR
			else if(mode==Constant.PAYROLL_OVERALL_PDF){
				createPayrollPDFOverallSummary(document,mode);
			}
			//--> PAYROLL PER CONTRACTUAL EMPLOYEE 
			else if(mode==Constant.PAYROLL_PER_CONTRACTUAL_EMPLOYEE_PDF){
				try{
					createPayrollPDFPerContractualEmployeeSummary(document, mode);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			//--> PAYROLL ASEMCO,BCCI,OCCCI,DBP,CFI,STPETER
			else if(mode==Constant.PAYROLL_ASEMCO_PDF 	||
					mode==Constant.PAYROLL_BCCI_PDF 	|| 
					mode==Constant.PAYROLL_OCCCI_PDF 	||
					mode==Constant.PAYROLL_DBP_PDF 		||
					mode==Constant.PAYROLL_CFI_PDF		||
					mode==Constant.PAYROLL_ST_PETER_PLAN_PDF ||
					mode==Constant.PAYROLL_W_TAX_PDF){
				 
				String title=null,neededColumnName=null;
				Database db= Database.getInstance();
				Utilities util= Utilities.getInstance();
				switch(mode){
					case Constant.PAYROLL_ASEMCO_PDF:{
						title="ASEMCO";
				 		neededColumnName=db.deductionTableColumnNames[15];
				 		break;
				 	}
				 	case Constant.PAYROLL_BCCI_PDF:{
				 		title="BCCI";
				 		neededColumnName=db.deductionTableColumnNames[16];
				 		break;
				 	}
				 	case Constant.PAYROLL_OCCCI_PDF:{
				 		title="OCCCI";
				 		neededColumnName=db.deductionTableColumnNames[17];
				 		break;
				 	}
				 	case Constant.PAYROLL_DBP_PDF:{
				 		title="DBP";
				 		neededColumnName=db.deductionTableColumnNames[18];
				 		break;
				 	}
				 	case Constant.PAYROLL_CFI_PDF:{
				 		title="CFI";
				 		neededColumnName=db.deductionTableColumnNames[19];
				 		break;
				 	}
				 	case Constant.PAYROLL_ST_PETER_PLAN_PDF:{
				 		title="ST. PETER";
				 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[20]);
				 		break;
				 	}
				 	case Constant.PAYROLL_W_TAX_PDF:{
				 		title="WITHOLDING TAX";
				 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[14]);
				 		break;
				 	}

				 	default:{
				 		break;
				 	}
				 }
				 //-------------------------------------------
			 	createPayrollPDFAsemcoBcciOccciDbpCfiSTPeterWTaxDataSummary(document, title, neededColumnName,mode);
			}
			//--> PAYROLL LBP SUMMARY
			else if(mode==Constant.PAYROLL_LBP_PDF){
	
				createPayrollPDFLBPDataSummary(document, mode);
			}
			//--> PAYROLL SSS LOAN AND PAGIBIG LOAN SUMMARY PDF
			else if(mode==Constant.PAYROLL_SSS_LOAN_PDF	||
					 mode==Constant.PAYROLL_PAGIBIG_LOAN_PDF){
				
				String title=null,neededColumnName=null;
				Database db=Database.getInstance();
				Utilities util=Utilities.getInstance();
				DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
				String payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
				String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
				 
				if(util.isFirstPayOfTheMonth(day)){
					switch(mode){
						case Constant.PAYROLL_SSS_LOAN_PDF:{
					 		title="SSS Loan";
					 		neededColumnName=db.deductionTableColumnNames[3];
					 		break;
					 	}
					 	case Constant.PAYROLL_PAGIBIG_LOAN_PDF:{
					 		title="PAG-IBIG Loan";
					 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[4]);
					 		break;
					 	}
					 	default:{
					 		break;
					 	}
					}
					 
					createPayrollPDFSSSLoanPagibigLoanDataSummary(document,
							title, payrollDate, util, db,neededColumnName,mode);
				}
				else{
					MainFrame.getInstance().showOptionPaneMessageDialog(
						"No available data. This data is available only on the first pay of the month.",
						JOptionPane.ERROR_MESSAGE
					);	
					//--> Set necessary UI components.
					 document.close();
					 document=null;
					 writer=null;
				}
			}
			//-->PAYROLL UNION DUES SUMMARY PDF
			else if(mode==Constant.PAYROLL_UNION_DUES_PDF	){
				
				Utilities util=Utilities.getInstance();
				DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
				String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
				String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
				
				
				//--> Makes sure that it will be process at the second payroll of the month
				if(!util.isFirstPayOfTheMonth(day)){
					createPayrollPDFUnionDuesDataSummary(document,payrollDate,util,day,mode);
				}
				else{
					MainFrame.getInstance().showOptionPaneMessageDialog(
							"No available data. This data is available only on the second pay of the month.",
							JOptionPane.ERROR_MESSAGE
					);	
					//--> Set necessary UI components.
					PayrollViewPanel.getInstance().optionPanel.setVisible(true);
					 document.close();
					 document=null;
					 writer=null;
				}
			}

			//-->PAYROLL HDMF and MEDICARE SUMMARY PDF
			else if(mode==Constant.PAYROLL_HDMF_PDF	|| mode==Constant.PAYROLL_MEDICARE_PDF){
				Database db=Database.getInstance();
				Utilities util=Utilities.getInstance();
				DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
				String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
				String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
				String title="???",neededColumnName="???", neededColumnNameER="???";
				
				//--> Set the values depending on the mode.
				switch(mode){
					case Constant.PAYROLL_HDMF_PDF:{
				 		title="HDMF";
				 		neededColumnName=util.addSlantApostropheToString(db.deductionTableColumnNames[6]); // Pag-ibigCont
				 		neededColumnNameER=util.addSlantApostropheToString(db.employerShareTableColumnNames[4]); // Pag-ibigER
				 		break;
				 	}
				 	case Constant.PAYROLL_MEDICARE_PDF:{
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
						createPayrollPDFHDMFMedicareDataSummary(document, payrollDate,
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
						 document.close();
						 document=null;
						 writer=null;
					}
				}
				else{  // CONTRACTUAL
					
					switch(mode){
						case Constant.PAYROLL_MEDICARE_PDF:{
							//--> Makes sure that it will be process at the first payroll of the month
							if(util.isFirstPayOfTheMonth(day)){
								createPayrollPDFHDMFMedicareDataSummary(document, payrollDate,
										db, util, day,title,
										neededColumnName,neededColumnNameER,mode);
							}
							else{
								MainFrame.getInstance().showOptionPaneMessageDialog(
										"No available data. This data is available only on the first pay of the month.",
										JOptionPane.ERROR_MESSAGE
								);	
								//--> Set necessary UI components.
								PayrollViewPanel.getInstance().optionPanel.setVisible(true);
								 document.close();
								 document=null;
								 writer=null;
							}
					 		break;
					 	}
						case Constant.PAYROLL_HDMF_PDF:{
							//--> Makes sure that it will be process at the second payroll of the month
							if(!util.isFirstPayOfTheMonth(day)){
								createPayrollPDFHDMFMedicareDataSummary(document, payrollDate,
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
								 document.close();
								 document=null;
								 writer=null;
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
			else if(mode==Constant.PAYROLL_SSS_CONT_PDF	){
				
				Utilities util=Utilities.getInstance();
				DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
				String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
				String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
				
				
				//--> Makes sure that it will be process at the second payroll of the month
				if(!util.isFirstPayOfTheMonth(day)){
					createPayrollPDFSSSContDataSummary(document, payrollDate, util, day,mode);
				}
				else{
					MainFrame.getInstance().showOptionPaneMessageDialog(
							"No available data. This data is available only on the second pay of the month.",
							JOptionPane.ERROR_MESSAGE
					);	
					//--> Set necessary UI components.
					PayrollViewPanel.getInstance().optionPanel.setVisible(true);
					
					 document.close();
					 document=null;
					 writer=null;
				}
			}
			
			
			
//			MainFrame.getInstance().showOptionPaneMessageDialog("BCCI not available", JOptionPane.ERROR_MESSAGE);
//			//--> Set necessary UI components.
//			PayrollViewPanel.getInstance().optionPanel.setVisible(true);
//			writer=null;
			
			 
			//--------------------------------------------------------------------------------
			if(writer!=null){
	 			document.close();
 			}
		
			document=null;
			writer=null;
		} catch (Exception e) {
			 writer=null;
			 
			 document.close();
			 document=null;
			 
			 e.printStackTrace();
			 System.out.println("\tCreate pdf error: "+e.getMessage()+CLASS_NAME);
			 MainFrame.getInstance().showOptionPaneMessageDialog(
					 "Error: "+e.getMessage(),
					 JOptionPane.ERROR_MESSAGE);
		 }
	}
	
	 /**
     * View a pdf viewer for payslip and payroll.
     * @param payslipDataStorageList
     * @param payslip
     * @param mode
     */
    public void viewPDF(ArrayList<PayslipDataStorageInfo> payslipDataStorageList,
			PayrollSlipLayout payslip,int mode){
    	String fileNamePath=Constant.FILE_PATH_NAME_PDF;
		isSuccessCreatingPDF=false;
    	
		//--> Create PDF.
		createPdf(fileNamePath, payslipDataStorageList,
				payslip,mode);
		
		
		if(isSuccessCreatingPDF){
			//--> Open pdf by using the installed pdf viewer of the compute.
			try{
				System.out.println("\t Opening PDF Viewer to view the created pdf file."+CLASS_NAME);
				Desktop.getDesktop().open(new java.io.File(fileNamePath));
				
				PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL.dispose();
				PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL.dispose();
				
				
			}catch(Exception e){
				System.out.println("\t"+e.getMessage()+CLASS_NAME);
				 document.close();
				 document=null;
				 
				 MainFrame.getInstance().showOptionPaneMessageDialog("Error in opening the PDF File.", JOptionPane.ERROR_MESSAGE);
				 
				
			}
		}
		
		
    }
    
  

	private void l____________________________l(){}
	  
	
	/**
	 * Add the payslip images to pdf.
	 * @param document
	 * @param payslipDataStorageList
	 * @param payslipLayoutPanel
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void createPayslipPDF(Document document,
			ArrayList<PayslipDataStorageInfo> payslipDataStorageList,
			PayrollSlipLayout payslipLayoutPanel) throws IOException, DocumentException{
		
		 Utilities util= Utilities.getInstance();
		 
		
		//--> Add a title in pdf.
		 Paragraph title = new Paragraph();
		 Font highlightFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			      Font.BOLD,BaseColor.RED);
		 title=addTextInPDF(title, "Payslip. Please set",titleFont);
		 title=addTextInPDF(title, " PDF Viewer",highlightFont);
		 title=addTextInPDF(title, " Properties to ",titleFont);	 
		 title=addTextInPDF(title, " LEGAL ",highlightFont);	 
		 title=addTextInPDF(title, " then select ",titleFont);	 
		 title=addTextInPDF(title, " Actual Size. ",highlightFont);	 
		 title=addTextInPDF(title, " Please Use ",titleFont);	 
		 title=addTextInPDF(title, "LONG Bondpaper.",highlightFont);	 
 
		 
		 title=addEmptyLineInPDF(title, 1);
		 
		 //-----------------------------------------------------------------------------
			
		 //--> Add payslips in pdf.
		 Paragraph payslips=new Paragraph();
		 PayslipDataStorageInfo info=null;
		 
		 
		 
		 for(int i=0;i<payslipDataStorageList.size();i++){
			 
			 if(i+1 < payslipDataStorageList.size()){
				 //--> Join two payslip panel images.
				 
				 BufferedImage firstImage,secondImage;
				
				 //> Get first image
				 info=payslipDataStorageList.get(i);
				 
				 payslipLayoutPanel.addDataToTextFields(info);
				 firstImage=(BufferedImage) util.getImageFrom(payslipLayoutPanel);
				 
				 
				 //>Get second image
				 info=payslipDataStorageList.get(i+1);
				 payslipLayoutPanel.addDataToTextFields(info);
				 secondImage=(BufferedImage) util.getImageFrom(payslipLayoutPanel);
				 
			
				 //> Join the two
				 BufferedImage joinedImg = util.joinBufferedImage(firstImage,secondImage);
				 i++;
				 
				 //> Add image to pdf
				 payslips=addImageInPdf(payslips, joinedImg, 55,Image.MIDDLE);
			 }
			 else{
				 
				 info=payslipDataStorageList.get(i);
				 payslipLayoutPanel.addDataToTextFields(info);
				 
				 BufferedImage image =(BufferedImage) util.getImageFrom(payslipLayoutPanel);
				 payslips=addImageInPdf(payslips, image, 55,Image.MIDDLE);
			 }
		 }
		
		 //------------------------------------------------------
		 document.add(title);
		 document.add(payslips);
		 
		 
		 System.out.println("\tDone creating the PAYSLIP pdf!"+CLASS_NAME);
		 PayrollViewPanel.getInstance().payslipDataStorageList.clear();
		 
		 isSuccessCreatingPDF=true;
		 MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payslip pdf!", JOptionPane.INFORMATION_MESSAGE);
	
	}
	
//	private void createPayrollPDF(Document document) throws DocumentException, IOException{
//		Utilities util= Utilities.getInstance();
//		
//		//--> Add a TITLE in pdf.
//		 Paragraph title = new Paragraph();
//		 title=addTextInPDF(title, "Payroll",titleFont);
//		 title=addEmptyLineInPDF(title, 1);
//		 
//	
//		 //------------------------------------------------------------------
//		 
//		 //--> Add TABLE in pdf.
//		 PayrollTableModel tableModel=PayrollViewPanel.getInstance().fullScreenTable.getModel();
//		 PdfPTable pdfTable = new PdfPTable(tableModel.getColumnCount()+1);
//        
//		 
//		 //--> Set the font of text in headers. THESE are two types of creating fonts.
//		 Font headerFont =new  Font(FontFamily.HELVETICA, 7, Font.BOLDITALIC, new BaseColor(0, 0, 255));
//		 Font headerFont2 =FontFactory.getFont("Times Roman", 9, BaseColor.BLACK);
//		 //-->Adding table headers
//		 Phrase p=initNewPhrase("#", headerFont);
//		 PdfPCell cell = initNewHeaderCell(p,BaseColor.ORANGE);
//		 cell = mergeRowCell(cell, 2);
//		 
//		 pdfTable.addCell(cell);
//		 pdfTable=addTableHeader(pdfTable, tableModel, headerFont);
//         
//		 
//		 
//		 
//		 //--> Set the font of text in the table cell that are not header
//         Font tempFont =new  Font(FontFamily.HELVETICA, 7, Font.BOLDITALIC, new BaseColor(0, 0, 255));
//         
//         //-->extracting data from the JTable and inserting it to PdfPTable
//         for (int rows = 0; rows < tableModel.getRowCount(); rows++) {
//        	 tempFont.setSize(25);
//        	 cell = initNewNormalCell(""+(rows+1),tempFont);
//        	 pdfTable.addCell(cell);
//        	 for (int cols = 0; cols < tableModel.getColumnCount(); cols++) {
//            	 if(tableModel.getValueAt(rows, cols)!=null){
//            		 pdfTable.addCell(tableModel.getValueAt(rows, cols).toString());
//            	 }
//        		 else{
//            		pdfTable.addCell("null"); 
//            	 }
//             }
//         }
//         
//       
//         //----------------------------------------------------------------------------
//         document.add(title);
//         document.add(pdfTable);
//		 
//		 System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
//		 MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll pdf!", JOptionPane.INFORMATION_MESSAGE);
//	}
	
	/**
	 * Create payroll data pdf for ASEMCO/BCCI/OCCCI/DBP/CFI/ST.Peter/WTax and it depends if first pay or second pay of the month.
	 * Why St.Peter and WTax is included? it has the same implementation to the five.
	 * @param document
	 * @throws DocumentException
	 * @throws SQLException
	 */
	private void createPayrollPDFAsemcoBcciOccciDbpCfiSTPeterWTaxDataSummary(Document document,
			String title, String neededColumnName,int mode)throws DocumentException, SQLException{
		
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString()),
				payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		
		String payrollDatePeriod=util.processPayrollPeriodString(payrollDatePeriodStarts, payrollDate);
		String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
		//-----------------------------------------------------------------------------
		
		//--> Add space
		Paragraph space=new Paragraph(" ");
		//-----------------------------------------------------------------------------
			
		//--> Add a TITLE in pdf.
		Paragraph titleParagraph = new Paragraph();
		titleParagraph=addTextInPDF(titleParagraph, "BILIRAN ELECTRIC COOPERATIVE, INC.\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "Caraycaray, Naval, Biliran\n\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, (mode==Constant.PAYROLL_ST_PETER_PLAN_PDF)?title+" LIFE PLAN\n":(mode==Constant.PAYROLL_W_TAX_PDF)?title+"\n":"SCHEDULE OF "+title+" DEDUCTIONS\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, payrollDatePeriod,titleFont);
		
		
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		
		//----------------------------------------------------------------------------
	
		//--> Add PAYROLL TABLE in pdf.
		payrollHeaderList=getAsemcoBcciOccciDbpCfiStPeterWTaxPayrollHeaderTexts(db, payrollHeaderList, day, util, title, mode);
		int totalColumnsInPDf=payrollHeaderList.size();
		PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
		//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
		pdfTable.setWidthPercentage(100);
		
		//--> Process HEADER
		pdfTable.setHeaderRows(1);	
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		pdfTable=processSimplePayrollHeader(pdfTable, db, headerFont);
		
		//--> Process Content
		try{
		pdfTable=processPayrollContentAsemcoBcciOccciDbpCfiSTPeterWTaxSummary(pdfTable, db, day, util, payrollDate, neededColumnName);
		}catch(Exception e){
			e.printStackTrace();
		}
//		
		//----------------------------------------------------------------------------
		
		//--> Add SIGNATURE TABLE 
		PdfPTable signatureTable=null;
		signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);

		//----------------------------------------------------------------------------
				
		document.add(titleParagraph);
		document.add(space);
		document.add(space);
		document.add(pdfTable);
		document.add(space);
		document.add(space);
		document.add(space);
        document.add(signatureTable);
		        

        System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
        isSuccessCreatingPDF=true;
		
        MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of all "+title+" summary pdf!", JOptionPane.INFORMATION_MESSAGE);

	}
	

	
	/**
	 * Creade Payroll pdf of all department summary values. 
	 * In other words total of all employees each department.
	 * @param document
	 * @throws DocumentException 
	 * @throws SQLException 
	 */
	private void createPayrollPDFOverallSummary(Document document,int mode) throws DocumentException, SQLException{
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		String payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString())
				,payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		String payrollDatePeriod=util.processPayrollPeriodString(payrollDatePeriodStarts, payrollDate);
		
		//-----------------------------------------------------------------------------
		
		//--> Add space
		Paragraph space=new Paragraph(" ");
		//-----------------------------------------------------------------------------
			
		//--> Add a TITLE in pdf.
		Paragraph titleParagraph = new Paragraph();
		titleParagraph=addTextInPDF(titleParagraph, "BILIRAN ELECTRIC COOPERATIVE, INC.\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "BILECO\n\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "PAYROLL FOR THE PERIOD "+payrollDatePeriod,titleFont);
		//------------------------------------------------------------------
		 
		//--> Add PAYROLL TABLE in pdf.
		payrollHeaderList=getPayrollHeaderTexts(db, payrollHeaderList,mode);
		int totalColumnsInPDf=payrollHeaderList.size()-((correctDedsColumnList.length/2)+(correctEarnColumnList.length/2));
		PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
		//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
		pdfTable.setWidthPercentage(100);
		
		//--> Process HEADER
		pdfTable.setHeaderRows(2);
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		pdfTable=processComplexRegularPayrollHeader(pdfTable, db, util, headerFont);
		
		//--> Process Content
		try{
		pdfTable=processPayrollContentOverallSummary(pdfTable,db,payrollDate,dialog);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//--> Process Total and add to table
		pdfTable=processPayrollContentPerDepartmentTotal(pdfTable, totalColumnsInPDf, db,departmentTotalValueList);
				
		//----------------------------------------------------------------------------
		
		//--> Add SIGNATURE TABLE 
		PdfPTable signatureTable=null;
		signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);

		//----------------------------------------------------------------------------
				
		
		document.add(titleParagraph);
		document.add(space);
		document.add(pdfTable);
		document.add(space);
		document.add(space);
		document.add(space);
        document.add(signatureTable);
        

        System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
        isSuccessCreatingPDF=true;
		
        MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of all department summary pdf!", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Create pdf payroll where each employee payroll in each department is shown.
	 * @param document
	 * @throws DocumentException
	 * @throws IOException
	 */
	private void createPayrollPDFPerDepartmentSummary(Document document,int mode) throws DocumentException, IOException,Exception{
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL;
		String payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString())
				,payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		String payrollDatePeriod=util.processPayrollPeriodString(payrollDatePeriodStarts, payrollDate);
		
		//-----------------------------------------------------------------------------
		
		//--> Add space
		Paragraph space=new Paragraph(" ");
		//-----------------------------------------------------------------------------
			
		//--> Add a TITLE in pdf.
		Paragraph titleParagraph = new Paragraph();
		titleParagraph=addTextInPDF(titleParagraph, "PAYROLL FOR THE PERIOD "+payrollDatePeriod,titleFont);
		 
		
		//------------------------------------------------------------------
		//--> Add the DEPARTMENT name in pdf.
		String deptName=PayrollViewPanel.getInstance().dispEmployeePayrollSummaryOptionPDFEXCEL.departmentComboBox.getSelectedItem().toString();
		Paragraph departmentNameParagraph = new Paragraph();
		departmentNameParagraph=addTextInPDF(departmentNameParagraph, util.convertDeptNameAbbrevToRealName(deptName),titleFont);
		
		//------------------------------------------------------------------
		 
		//--> Add PAYROLL TABLE in pdf.
		payrollHeaderList=getPayrollHeaderTexts(db, payrollHeaderList,mode);
		int totalColumnsInPDf=payrollHeaderList.size()-((correctDedsColumnList.length/2)+(correctEarnColumnList.length/2));
		PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
		//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
		pdfTable.setWidthPercentage(100);

		//--> Process HEADER
		pdfTable.setHeaderRows(2);
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		pdfTable=processComplexRegularPayrollHeader(pdfTable, db, util, headerFont);
		
		System.out.println("\tTotal Columns in PDF: "+totalColumnsInPDf
				+ "\tPayrol Header Size; "+payrollHeaderList.size()
				+"\tTotal Value List Size: "+totalValueList.size()+CLASS_NAME);
		
		//--> Process CONTENT
		pdfTable=processPayrollContentPerDepartmentSummary(pdfTable, db,totalColumnsInPDf,payrollDate,dialog);
		
		
		//--> Calculate the TOTAL values
		totalValueList=calculatePayrollPerDepartmentTotalColumnValues(totalValueList, db);
		
		//--> Process Total and add to table
		pdfTable=processPayrollContentPerDepartmentTotal(pdfTable, totalColumnsInPDf, db,totalValueList);
		
		
		//------------------------------------------------------------------
		 
		//--> Add SIGNATURE TABLE 
		PdfPTable signatureTable=null;
		signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);

		//----------------------------------------------------------------------------
       
		document.add(titleParagraph);
		document.add(space);
		document.add(departmentNameParagraph);
		document.add(space);
		document.add(pdfTable);
		document.add(space);
		document.add(space);
		document.add(space);
        document.add(signatureTable);
        
     
        System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
        isSuccessCreatingPDF=true;
		
        MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of employee in each department pdf!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Create pdf payroll where each CONTRACTUAL employees are shown.
	 * @param document
	 * @throws DocumentException
	 * @throws IOException
	 */
	private void createPayrollPDFPerContractualEmployeeSummary(Document document,int mode) throws DocumentException, IOException,Exception{
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		String payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString())
				,payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		String payrollDatePeriod=util.processPayrollPeriodString(payrollDatePeriodStarts, payrollDate);
		
		//-----------------------------------------------------------------------------
		
		//--> Add space
		Paragraph space=new Paragraph(" ");
		//-----------------------------------------------------------------------------
			
		//--> Add a TITLE in pdf.
		Paragraph titleParagraph = new Paragraph();
		titleParagraph=addTextInPDF(titleParagraph, "BILIRAN ELECTRIC COOPERATIVE, INC.\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "(BILECO)\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "Caraycaray, Naval, Biliran\n\n",titleFont);
		
		titleParagraph=addTextInPDF(titleParagraph, "SKILLED WORKER PAYROLL \n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "For the Period "+payrollDatePeriod,titleFont);
		
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		
		
		//------------------------------------------------------------------
		 
		//--> Add PAYROLL TABLE in pdf.
		payrollHeaderList=getPerContractualEmployeePayrollHeaderTexts(db, util, payrollHeaderList);
		int totalColumnsInPDf=payrollHeaderList.size()-2;
		PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
		//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
		pdfTable.setWidthPercentage(100);
		float[] wid= new float[totalColumnsInPDf];
		for(int i=0;i<totalColumnsInPDf;i++){
			wid[i]=1;
			
			if(i==0){ // #
				wid[i]=(float) 0.4;
			}
			else if(i==2 || i==3){ // NoOfDays, RatePerDay
				wid[i]=(float) 0.7;
			}
			else if(i==totalColumnsInPDf-1){ // Signature
				wid[i]=(float) 2;
			}
		}
		pdfTable.setWidths(wid);
		pdfTable.getDefaultCell().setMinimumHeight(10);
		
		//--> Process HEADER
		pdfTable.setHeaderRows(2);
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		pdfTable=processComplexContractualPayrollHeader(pdfTable, db, util, headerFont);
		
		System.out.println("\tTotal Columns in PDF: "+totalColumnsInPDf
				+ "\tPayrol Header Size; "+payrollHeaderList.size()
				+"\tTotal Value List Size: "+totalValueList.size()+CLASS_NAME);
				
		//--> Process CONTENT
		try{
			pdfTable=processPerContractualPayrollContentSummary(pdfTable, db, totalColumnsInPDf, payrollDate, dialog);
		}catch(Exception e){
			e.printStackTrace();
		}

//		//------------------------------------------------------------------
//		 
		//--> Add SIGNATURE TABLE 
		PdfPTable signatureTable=null;
		signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);

//		//----------------------------------------------------------------------------
//       
		document.add(titleParagraph);
		document.add(space);
		document.add(pdfTable);
		document.add(space);
		document.add(space);
		document.add(space);
        document.add(signatureTable);
//        
//     
        System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
        isSuccessCreatingPDF=true;
		
        MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of employee in each department pdf!", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Create the HDMF/PAGIBIG CONT payroll data to be printed in PDF.
	 * @param document
	 * @param title
	 * @throws DocumentException
	 */
	private void createPayrollPDFHDMFMedicareDataSummary(Document document,String payrollDate,
			Database db, Utilities util,String day,
			String title, String neededColumnName,String neededColumnNameER,
			int mode) throws DocumentException{
		
		
		String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate);
			month=util.convertThreeLetterMonthIntoCompleteName(month);
		String year= util.getYearFromDateFormatYyyyMmDd(payrollDate);
		
		//-----------------------------------------------------------------------------
		
		//--> Add space
		Paragraph space=new Paragraph(" ");
		//----------------------------------------------------------------------------
		//--> Add a TITLE in pdf.
		
		Paragraph titleParagraph = new Paragraph();
		titleParagraph=addTextInPDF(titleParagraph, "BILIRAN ELECTRIC COOPERATIVE, INC.\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "Caraycaray, Naval, Biliran\n\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, title+" PREMIUM MASTERLIST\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, month+" "+day+", "+year,titleFont);
		
		
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		
		//----------------------------------------------------------------------------
		
		//--> Add PAYROLL TABLE in pdf.
		payrollHeaderList=getHDMFMedicarePayrollHeaderTexts(payrollHeaderList,mode);
		int totalColumnsInPDf=payrollHeaderList.size();
		PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
		//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
		pdfTable.setWidthPercentage(100);
		
		//--> Process HEADER
		pdfTable.setHeaderRows(1);
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		pdfTable=processSimplePayrollHeader(pdfTable, db, headerFont);
		
		//--> Process Content
		try{
		pdfTable=processPayrollContentHDMFMedicareSummary(pdfTable, db, util,
				payrollDate, neededColumnName, neededColumnNameER);
		}catch(Exception e){
			e.printStackTrace();
		}
			
		//----------------------------------------------------------------------------
		
		//--> Add SIGNATURE TABLE 
		PdfPTable signatureTable=null;
		signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);

		//----------------------------------------------------------------------------
	
		document.add(titleParagraph);
		document.add(space);
		document.add(space);
		document.add(pdfTable);
		document.add(space);
		document.add(space);
		document.add(space);
		document.add(signatureTable);
//		        
//	    //----------------------------------------------------------------------------
//		
		System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
		 isSuccessCreatingPDF=true;
			
		MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of all "+title+" summary pdf!", JOptionPane.INFORMATION_MESSAGE);
	
	}
	/**
	 * Create payroll data pdf for LBP and it depends if first pay or second pay of the month.
	 * LBP is weird since in first payroll duha ka column ang iprint, sa second payroll usa la.
	 * Example: First Payroll: December 31 ,2017  and January  15, 2018 
	 * 			Second Payroll: January 31,2018
	 * In contrast with most payroll.
	 * @param document
	 * @throws DocumentException
	 * @throws SQLException
	 */
	private void createPayrollPDFLBPDataSummary(Document document,int mode)throws DocumentException, SQLException{
	
		Database db=Database.getInstance();
		Utilities util=Utilities.getInstance();
		DisplayOptionsDialog dialog=PayrollViewPanel.getInstance().dispDialogWithPayrolDateComboboxOnlyOptionPDFEXCEL;
		String 	payrollDate=util.convertDateReadableToYyyyMmDdDate(dialog.payrollDateComboBox.getSelectedItem().toString());
		String payrollDateBefore=getBeforePayrollDateBasedFromGivenPayrollDate(db, payrollDate, util);

		
		if(payrollDateBefore!=null){
			String day=util.getDayFromDateFormatYyyyMmDd(payrollDate);
			//-----------------------------------------------------------------------------
			
			//--> Add space
			Paragraph space=new Paragraph(" ");
			//-----------------------------------------------------------------------------
				
			//--> Add a TITLE in pdf.
			Paragraph titleParagraph = new Paragraph();
			titleParagraph=addTextInPDF(titleParagraph, "BILIRAN ELECTRIC COOPERATIVE, INC.\n",titleFont);
			titleParagraph=addTextInPDF(titleParagraph, "Caraycaray, Naval, Biliran\n\n",titleFont);
			
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
			titleParagraph=addTextInPDF(titleParagraph,str1+"\n",titleFont);
			titleParagraph=addTextInPDF(titleParagraph, str2,titleFont);
			
			titleParagraph.setAlignment(Element.ALIGN_CENTER);
			
			//----------------------------------------------------------------------------
		
			//--> Add PAYROLL TABLE in pdf.
			payrollHeaderList=getLBPPayrollHeaderTexts(db, payrollHeaderList, day, payrollDate, payrollDateBefore, util);
			int totalColumnsInPDf=payrollHeaderList.size();
			PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
			//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
			pdfTable.setWidthPercentage(100);
	//		
			//--> Process HEADER
			pdfTable.setHeaderRows(1);
			Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
			pdfTable=processSimplePayrollHeader(pdfTable, db, headerFont);
			
			//--> Process Content
			try{
			pdfTable=processPayrollContentLBPSummary(pdfTable, db, day, util, payrollDate,payrollDateBefore);
			}catch(Exception e){
				e.printStackTrace();
			}
		//	
	//		//----------------------------------------------------------------------------
	//		
			//--> Add SIGNATURE TABLE 
			PdfPTable signatureTable=null;
			signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);
			
			
			//----------------------------------------------------------------------------
					
			document.add(titleParagraph);
			document.add(space);
			document.add(space);
			document.add(pdfTable);
			document.add(space);
			document.add(space);
			document.add(space);
		    document.add(signatureTable);
			        
		
		    System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
		    isSuccessCreatingPDF=true;
			
		    MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of all LBP summary pdf!", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			
			if(document!=null){
				document.close();
			}
			document=null;
			writer=null;
		    MainFrame.getInstance().showOptionPaneMessageDialog("Data is incomplete.", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Create the SSS Contribution payroll data to be printed in PDF.
	 * @param document
	 * @param title
	 * @throws DocumentException
	 */
	private void createPayrollPDFSSSContDataSummary(Document document,String payrollDate,
			Utilities util,String day,int mode) throws DocumentException{
		
		Database db=Database.getInstance();
		
		
		String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate);
			month=util.convertThreeLetterMonthIntoCompleteName(month);
		String year= util.getYearFromDateFormatYyyyMmDd(payrollDate);
		
		//-----------------------------------------------------------------------------
		
		//--> Add space
		Paragraph space=new Paragraph(" ");
		//----------------------------------------------------------------------------
		//--> Add a TITLE in pdf.
		
		Paragraph titleParagraph = new Paragraph();
		titleParagraph=addTextInPDF(titleParagraph, "BILIRAN ELECTRIC COOPERATIVE, INC.\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "Caraycaray, Naval, Biliran\n\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "SSS PREMIUM MASTERLIST\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, month+" "+day+", "+year,titleFont);
		
		
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		
		//----------------------------------------------------------------------------
		
		//--> Add PAYROLL TABLE in pdf.
		payrollHeaderList=getSSSContributionPayrollHeaderTexts(payrollHeaderList);
		int totalColumnsInPDf=payrollHeaderList.size();
		PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
		//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
		pdfTable.setWidthPercentage(100);
		
		//--> Process HEADER
		pdfTable.setHeaderRows(1);
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		pdfTable=processSimplePayrollHeader(pdfTable, db, headerFont);
		
		//--> Process Content
		try{
		pdfTable=processPayrollContentSSSContributionSummary(pdfTable, db, util, payrollDate);
				}catch(Exception e){
			e.printStackTrace();
		}
			
		//----------------------------------------------------------------------------
		
		//--> Add SIGNATURE TABLE 
		PdfPTable signatureTable=null;
		signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);

		//----------------------------------------------------------------------------

		document.add(titleParagraph);
		document.add(space);
		document.add(space);
		document.add(pdfTable);
		document.add(space);
		document.add(space);
		document.add(space);
		document.add(signatureTable);
//		        
//        //----------------------------------------------------------------------------
//		
		System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
		 isSuccessCreatingPDF=true;
			
		MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of all Medicare summary pdf!", JOptionPane.INFORMATION_MESSAGE);

	}
	
	
	/**
	 * Create the SSS loan and PAGIBIG Loan payroll data to be printed in PDF.
	 * @param document
	 * @param title
	 * @throws DocumentException
	 */
	private void createPayrollPDFSSSLoanPagibigLoanDataSummary(Document document,
			String title,String payrollDate,Utilities util,
			Database db,String neededColumnName,int mode) throws DocumentException{
		
		
		String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate);
			month=util.convertThreeLetterMonthIntoCompleteName(month);
		String year= util.getYearFromDateFormatYyyyMmDd(payrollDate);
		
		//-----------------------------------------------------------------------------
		
		//--> Add space
		Paragraph space=new Paragraph(" ");
		//----------------------------------------------------------------------------
		//--> Add a TITLE in pdf.
		
		Paragraph titleParagraph = new Paragraph();
		titleParagraph=addTextInPDF(titleParagraph, "BILIRAN ELECTRIC COOPERATIVE, INC.\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "Caraycaray, Naval, Biliran\n\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, title+" "+month+" "+year,titleFont);
		
		
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		
		//----------------------------------------------------------------------------
		
		//--> Add PAYROLL TABLE in pdf.
		payrollHeaderList=getSssPagibigLoanPayrollHeaderTexts(payrollHeaderList);
		int totalColumnsInPDf=payrollHeaderList.size();
		PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
//		//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
		pdfTable.setWidthPercentage(100);
		
		//--> Process HEADER
		pdfTable.setHeaderRows(1);
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		pdfTable=processSimplePayrollHeader(pdfTable, db, headerFont);
		
		//--> Process Content
		try{
		pdfTable=processPayrollContentSssPagibigLoanSummary(pdfTable, db,util, payrollDate, neededColumnName);
				}catch(Exception e){
			e.printStackTrace();
		}
//			
		//----------------------------------------------------------------------------
		
		//--> Add SIGNATURE TABLE 
		PdfPTable signatureTable=null;
		signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);

		//----------------------------------------------------------------------------

		document.add(titleParagraph);
		document.add(space);
		document.add(space);
		document.add(pdfTable);
		document.add(space);
		document.add(space);
		document.add(space);
		document.add(signatureTable);
		        
        //----------------------------------------------------------------------------
		
        System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
        isSuccessCreatingPDF=true;
		
        MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of all "+title+" summary pdf!", JOptionPane.INFORMATION_MESSAGE);

	}
	/**
	 * Create the Union Dues payroll data to be printed in PDF.
	 * @param document
	 * @param title
	 * @throws DocumentException
	 */
	private void createPayrollPDFUnionDuesDataSummary(Document document,String payrollDate,
			Utilities util,String day, int mode) throws DocumentException{
		
		Database db=Database.getInstance();
		String payrollDatePeriodStarts=db.getPayrollDateStartBasedFromPayrollDate(payrollDate);
		
		
		String month=util.getMonthFromDateFormatYyyyMmDd(payrollDate);
			month=util.convertThreeLetterMonthIntoCompleteName(month);
		String year= util.getYearFromDateFormatYyyyMmDd(payrollDate);
		
		//-----------------------------------------------------------------------------
		
		//--> Add space
		Paragraph space=new Paragraph(" ");
		//----------------------------------------------------------------------------
		//--> Add a TITLE in pdf.
		
		Paragraph titleParagraph = new Paragraph();
		titleParagraph=addTextInPDF(titleParagraph, "BILIRAN ELECTRIC COOPERATIVE, INC.\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "Caraycaray, Naval, Biliran\n\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, "UNION DUES\n",titleFont);
		titleParagraph=addTextInPDF(titleParagraph, month+" "+day+", "+year,titleFont);
		
		
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		
		//----------------------------------------------------------------------------
		
		//--> Add PAYROLL TABLE in pdf.
		payrollHeaderList=getUnionDuesPayrollHeaderTexts(payrollHeaderList);
		int totalColumnsInPDf=payrollHeaderList.size();
		PdfPTable pdfTable = new PdfPTable(totalColumnsInPDf);
		//--> This code stretch out the table by 100 percent withn the pdf file. Without this code, the table will be smaller, try to make it comment and see what happens.
		pdfTable.setWidthPercentage(100);
		
		//--> Process HEADER
		pdfTable.setHeaderRows(1);
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		pdfTable=processSimplePayrollHeader(pdfTable, db, headerFont);
//		
		//--> Process Content
		try{
		pdfTable=processPayrollContentUnionDuesSummary(pdfTable, db, util, payrollDate);
				}catch(Exception e){
			e.printStackTrace();
		}
//			
		//----------------------------------------------------------------------------
		
		//--> Add SIGNATURE TABLE 
		PdfPTable signatureTable=null;
		signatureTable=processPayrollContentSignature(signatureTable,pdfTable,contentFont,mode);

		//----------------------------------------------------------------------------

		document.add(titleParagraph);
		document.add(space);
		document.add(space);
		document.add(pdfTable);
		document.add(space);
		document.add(space);
		document.add(space);
		document.add(signatureTable);
//		        
//        //----------------------------------------------------------------------------
//		
		System.out.println("\tDone creating the PAYROLL pdf!"+CLASS_NAME);
		 isSuccessCreatingPDF=true;
			
		MainFrame.getInstance().showOptionPaneMessageDialog("Done creating the payroll of all Union Dues summary pdf!", JOptionPane.INFORMATION_MESSAGE);

	}
	private void l_________________OVERALL____________________________________l(){}
	
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
	 * @param table
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	private PdfPTable processPayrollContentOverallSummary(PdfPTable table,Database db,String payrollDate,DisplayOptionsDialog dialog) throws SQLException{
		Utilities util= Utilities.getInstance();
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		PdfPCell cell=null;
		
		int count=0;
		departmentTotalValueList=assignTotalListValuesToZero(departmentTotalValueList);
		
		for(String deptName:db.departmentDataList.values()){
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
			
			//--> Put necessary data to table;
			cell=initNewNormalCell(""+(count+1), contentFont);
			cell.setRowspan(2);
			table.addCell(cell);
			
			cell=initNewNormalCell(deptName, contentFont);
			cell.setRowspan(2);
			table.addCell(cell);
			
			PayrollPDFTotalValueInfo totalDedPpf=null,totalEarnPpf=null;
			String data=null;
		    for(int i=0;i<totalValueList.size();i++){
		    	PayrollPDFTotalValueInfo ppf=totalValueList.get(i);
		    	
		    	
	    		totalEarnPpf=(totalEarnPpf==null && ppf.columnName.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]))?ppf:totalEarnPpf;
		    	totalDedPpf=(totalDedPpf==null && ppf.columnName.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]))?ppf:totalDedPpf;

		  

		    	if(ppf.columnName.equals(netPayColumnName)){
		    		ppf.value=totalEarnPpf.value-totalDedPpf.value;	
		    	}
		    	
		    	
		    	//--> Check if zero change to '-'
		    	if(ppf.value==0){
		    		data="-";
		    	}
		    	else{
		    		data=util.insertComma(""+util.convertRoundToOnlyTwoDecimalPlaces(ppf.value));
		    	}
		    	cell=initNewNormalCell(data, contentFont);
				
		    	if(ppf.columnName.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]) ||
		    			ppf.columnName.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]) ||
		    			ppf.columnName.equals(netPayColumnName)){
		    		cell.setRowspan(2);
		    	}
		    	table.addCell(cell);
		    }
			count++;
			
			departmentTotalValueList=calculatePayrollOverallTotalColumnValues(departmentTotalValueList, totalValueList);
		}
		
		
		

		
		return table;
	}
	private void l_________________Per_DEPARTMENT____________________________________l(){}
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
	 * @param table
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	private PdfPTable processPayrollContentPerDepartmentSummary(PdfPTable table,Database db,
			int totalColumnsInPDf,String payrollDate,DisplayOptionsDialog dialog) throws SQLException{
		
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		Utilities util= Utilities.getInstance();
		ResultSet rs=null;
		ResultSetMetaData md=null;
		PdfPCell cell=null;
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
		
		//--> Put retrieved data to PDF.
		for(int i=0;i<totalNumOfRows;i++){
			rs.absolute(i+1);
			
			//> Add Number and Name
			for(int j=0;j<2;j++){
				String str=(j==0)?""+(i+1):rs.getObject(j)+", "+rs.getObject(j+1);
				cell=initNewNormalCell(str, contentFont);
				if(cellNumberRowSpanTwoList.contains(j)){
					cell=mergeRowCell(cell, 2);
				}
				table.addCell(cell);
			}
			//> Add the Values
			for(int j=2;j<md.getColumnCount();j++){
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

				// Add Cells
				if(Double.parseDouble(data)==0){
					data="-";
				}
				else{
					data=util.insertComma(""+util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(data)));
				
				}
				cell=initNewNormalCell(data, contentFont);
				if(cellNumberRowSpanTwoList.contains(j)){
					cell=mergeRowCell(cell, 2);
					if(db.earningTableColumnNames[db.earningTableColumnNames.length-1].equals(payrollHeaderList.get(j)) ||
							db.deductionTableColumnNames[db.deductionTableColumnNames.length-2].equals(payrollHeaderList.get(j))){
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right if numbers
					}
				}
				else{
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right if numbers
				}
				if(isTOTALTempBoolean){
//					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					isTOTALTempBoolean=false;
				}
				table.addCell(cell);

				if(isAddNetPay){
					// Net Pay
					double netPayValue=(totalEarningsValue-totalDeductionValue);
					
					String value=util.insertComma(""+util.convertRoundToOnlyTwoDecimalPlaces(netPayValue));
			
					cell=initNewNormalCell(value, contentFont);
					cell=mergeRowCell(cell, 2);
//					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right if numbers
					table.addCell(cell);
					isAddNetPay=false;
					
					netPayTotalInfo.value+=netPayValue;
				}				
			}
			
			
			
		}
		
		System.out.println("\tRetrieve from database Total Column: "+md.getColumnCount()+CLASS_NAME);

		return table;
	}
	
	/**
	 * Adding the total summary data of all in the table.
	 * @param table
	 * @param totalColumnsInPDf
	 * @param db
	 * @return
	 */
	private PdfPTable processPayrollContentPerDepartmentTotal(PdfPTable table,int totalColumnsInPDf,Database db,ArrayList<PayrollPDFTotalValueInfo>list){
		Utilities util = Utilities.getInstance();
		
		//--> Add empty row
		PdfPCell cell=initNewNormalCell(" ", contentFont);
		cell.setColspan(totalColumnsInPDf);
		table.addCell(cell);
		
		//--> Process the Total Part of the Table
		cell=initNewNormalCell("Total", contentFont);
		cell.setRowspan(2);
		cell.setColspan(2);
		table.addCell(cell);
		
		PayrollPDFTotalValueInfo totalEarnPpf=null,totalDedPpf=null;
	    for(int i=0;i<list.size();i++){
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
			}
			else{
				data=util.insertComma(""+util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(data)));
			}
	    	
			cell=initNewNormalCell(data, contentFont);
			
	    	if(ppf.columnName.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]) ||
	    			ppf.columnName.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]) ||
	    			ppf.columnName.equals(netPayColumnName)){
	    		cell.setRowspan(2);
	    	}
	    	
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right if numbers
	    	table.addCell(cell);
	    }

		return table;
	}
	
	private void l__________________PER_CONTRACTUAL_EMPLOYEE_____________l(){}
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
		
		for(int i=3;i<db.deductionsContractualColumnNames.length-1;i++){ //9 //Comment column not included
			list.add(db.deductionsContractualColumnNames[i]);
		}
		
		

	
		return list;
	}
	
	/**
	 * Process the employee payroll data summary in each department on PDF.
	 * @param table
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	private PdfPTable processPerContractualPayrollContentSummary(PdfPTable table,Database db,
			int totalColumnsInPDf,String payrollDate,DisplayOptionsDialog dialog) throws SQLException{
		
		ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		Utilities util= Utilities.getInstance();
		ResultSet rs=null;
		ResultSetMetaData md=null;
		PdfPCell cell=null;
		
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
		for(int i=0;i<totalNumOfRows;i++){
			rs.absolute(i+1);
			
			//> Add Number and Name
			for(int j=0;j<2;j++){
				String str=(j==0)?""+(i+1):rs.getObject(j)+", "+rs.getObject(j+1);
				cell=initNewNormalCell(str, contentFont);
				
				if(j==0){
					cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center if number before name
				}
				table.addCell(cell);
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
					data="-";
				}
				else{
					data=util.insertComma(""+util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(data)));	
				}
				cell=initNewNormalCell(data, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right if numbers
				table.addCell(cell);
			}
			
			// Add signature empty string to avoid overlap
			cell=initNewNormalCell("", contentFont);
			table.addCell(cell);
		}
		
		// Add long empty string to separate the total values
		cell=initNewNormalCell(" ", contentFont);
		cell.setColspan(totalColumnsInPDf);
		table.addCell(cell);
		
		//-----------------------------------------------------------------------------------------------
		//--> Add Total Values
		
		//> Add the TOTAL text
		cell=initNewNormalCell("Total", contentFont);
		cell.setColspan(4);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center
		table.addCell(cell);
		//> Add Total numbers
		for(int j=4;j<md.getColumnCount();j++){
			double sum=totalValueList.get(j);
			cell=initNewNormalCell(""+util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(sum)), contentFont);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right
			table.addCell(cell);
		}
		//> Add empty string for signature
		cell=initNewNormalCell("", contentFont);
		table.addCell(cell);
		
		System.out.println("\tRetrieve from database Total Column: "+md.getColumnCount()+CLASS_NAME);

		return table;
	}	
	
	private void l__________________ASEMCO_BCCI_OCCCI_DBP_CFI_STPETER_WTAX___________________________________l(){}
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
		list.add("15th "+((mode==Constant.PAYROLL_ST_PETER_PLAN_PDF || mode==Constant.PAYROLL_W_TAX_PDF)?"":title));
		
		if(!util.isFirstPayOfTheMonth(day)){
			list.add("30th "+((mode==Constant.PAYROLL_ST_PETER_PLAN_PDF || mode==Constant.PAYROLL_W_TAX_PDF)?"":title));
			list.add("Total");
		}
		
		
		return list;
	}
	
	/**
	 * Process the table content when the payroll date is on first Pay or
	 * 		 15th day of Asemco/Bcci/Occci/Dbp/Cfi/ST.Peter/Wtax. 
	 * @param table
	 * @param db
	 * @param conditionColumnAndValueList
	 * @param joinColumnCompareList
	 * @return
	 */
	private PdfPTable processAsemcoBcciOccciDbpCfiStPeterWTaxFirstPay15thDayTableContent(PdfPTable table,
			Database db,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList, Utilities util,
			String neededColumnName){

//		private String selectInnerJoinWithCondition(
//		String[] tableNameList,
//		String[]columnNameList, 
//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
//		String[] joinColumnCompareList){
		
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
			
			for(int i=1;i<=totalNumOfRows;i++){
				db.resultSet.absolute(i);
				//> Add the number data in cell
				PdfPCell cell= initNewNormalCell(""+i, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center and number indicator data.
				table.addCell(cell);
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					cell= initNewNormalCell(
							(j==db.metaData.getColumnCount())?
								util.insertComma(data)+"\t":
								data,
							contentFont
					);
					
					if(j==db.metaData.getColumnCount()){
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right and number/double value data.
						totalAllAsemcoBcciOccciDbpCfi15thValue+=Double.parseDouble(data);
					}
					table.addCell(cell);		
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//--> Process ALL Total Asemco 15th Value.
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		BaseColor color=BaseColor.WHITE;
		//> None
		Phrase p= initNewPhrase(
				"",
				headerFont);
		PdfPCell headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total
		p= initNewPhrase(
				"TOTAL",
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total Value
		totalAllAsemcoBcciOccciDbpCfi15thValue=
				util.convertRoundToOnlyTwoDecimalPlaces(totalAllAsemcoBcciOccciDbpCfi15thValue);
		
		p= initNewPhrase(
				""+util.insertComma(totalAllAsemcoBcciOccciDbpCfi15thValue),
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		return table;
	}
	
	/**
	 * Process the table content when the payroll date is
	 * 		on second Pay or 30th day of Asemco/Bcci/Occci/Dbp/Cfi/St.Peter/WTax. 
	 * @param table
	 * @param db
	 * @param conditionColumnAndValueList
	 * @param joinColumnCompareList
	 * @param payrollDate
	 * @param util
	 * @return
	 */
	private PdfPTable processAsemcoBcciOccciDbpCfiSTPeterWTaxSecondPay30thDayTableContent(
			PdfPTable table,
			Database db,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,String payrollDate,Utilities util,
			String neededColumnName){
		
		//--> For Debugging Purposes.
//		System.out.println("\t Process ASEMCO,BCCI,OCCCI.DBP,CFI,STPETER,WTAX -> 30th Day"+CLASS_NAME);
		
		
		
//		private void selectBasedFromColumns(
//				String[] tableNameList,
//				String[]columnNameList,
//				OrderByInfo orderInfo
//		){
		
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
				for(int i=1,count=1;i<=totalNumOfRows;count++,i++){
					
					//--> Go to row
					db.resultSet.absolute(i);
					
					//--> Add the number data in cell
					PdfPCell cell= initNewNormalCell(""+count, contentFont);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center and number indicator data.
					table.addCell(cell);
					
					//--> Add the name  to table
					String name=db.resultSet.getObject(1).toString()+", "+db.resultSet.getObject(2).toString();
					cell= initNewNormalCell(name, contentFont);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left the name
					table.addCell(cell);
				
					
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
						
						
						
						cell= initNewNormalCell((value==0)?"-":util.insertComma(""+value), contentFont);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right the numbers values
						table.addCell(cell);
						totalOf15thAnd30th+=value;
						totalAllAsemcoBcciOccciDbpCfi30thValueList.set(0,totalAllAsemcoBcciOccciDbpCfi30thValueList.get(0)+value);
						
						//--> Add the value of 30th column cell.
						value=0;
						if(empIDAfterThisRow!=null && employeeIDOfThisRow.equals(empIDAfterThisRow)){
							db.resultSet.absolute(i+1);
							value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
							i++;
						}
						cell= initNewNormalCell((value==0)?"-":util.insertComma(""+value), contentFont);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right the numbers values
						table.addCell(cell);
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
						cell= initNewNormalCell((value==0)?"-":util.insertComma(""+value), contentFont);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right the numbers values
						table.addCell(cell);
						totalOf15thAnd30th+=value;
						totalAllAsemcoBcciOccciDbpCfi30thValueList.set(0,totalAllAsemcoBcciOccciDbpCfi30thValueList.get(0)+value);
						
						//--> Add the value of 30th column cell.
						value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
						cell= initNewNormalCell((value==0)?"-":util.insertComma(""+value), contentFont);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right the numbers values
						table.addCell(cell);
						totalOf15thAnd30th+=value;
						totalAllAsemcoBcciOccciDbpCfi30thValueList.set(1,totalAllAsemcoBcciOccciDbpCfi30thValueList.get(1)+value);
								
						
					}
					
					
					//--> Add the total value where 15th + 30th value.
					totalOf15thAnd30th=util.convertRoundToOnlyTwoDecimalPlaces(totalOf15thAnd30th);
					cell= initNewNormalCell((totalOf15thAnd30th==0)?"-":util.insertComma(""+totalOf15thAnd30th), contentFont);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right and number/double value data.
					table.addCell(cell);
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
		
		
		//--> Process ALL Total Asemco/Bcci/Occci/Dbp/Cfi 30th Value.
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		BaseColor color=BaseColor.WHITE;
		//> None
		Phrase p= initNewPhrase(
				"",
				headerFont);
		PdfPCell headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total word
		p= initNewPhrase(
				"TOTAL",
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total Value of 15th, 30th and 15th+30th
		for(int i=0;i<totalAllAsemcoBcciOccciDbpCfi30thValueList.size();i++){
			double num= util.convertRoundToOnlyTwoDecimalPlaces(totalAllAsemcoBcciOccciDbpCfi30thValueList.get(i));
			p= initNewPhrase(
					(num==0)?"-":""+util.insertComma(num),
					headerFont);
			headerCell=initNewHeaderCell(p,color);
			headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			headerCell.setBorderWidth(1);
			table.addCell(headerCell);
		}
		
		return table;
		
	}
	
	/**
	 * Process the content of the table of ASEMCO, BCCI, OCCCI, DBP, CFI, St. Peter, and W-Tax
	 * @param table
	 * @param db
	 * @param day
	 * @param util
	 * @param payrollDate
	 * @return
	 */
	private PdfPTable processPayrollContentAsemcoBcciOccciDbpCfiSTPeterWTaxSummary(
			PdfPTable table, Database db,
			String day,Utilities util,
			String payrollDate, String neededColumnName){
	
		
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
			table = processAsemcoBcciOccciDbpCfiStPeterWTaxFirstPay15thDayTableContent(table, db, conditionColumnAndValueList, joinColumnCompareList, util,neededColumnName);
		}
		//-----------------------------------------------------------------------------------------------
		
		//--> PROCESS IF THE PAYROLL DATE IS AT 30th or SECOND PAY
		else{
			
			table = processAsemcoBcciOccciDbpCfiSTPeterWTaxSecondPay30thDayTableContent(table, db, conditionColumnAndValueList, joinColumnCompareList, payrollDate, util,neededColumnName);
		}
		return table;
	}
	private void l__________________LBP___________________________________l(){}
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
	 */
	private PdfPTable processLBPFirstPayTableContent_15thDay(
			PdfPTable table,
			Database db,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList,String payrollDate,
			String payrollDateBefore,Utilities util){
		
//		private void selectBasedFromColumns(
//				String[] tableNameList,
//				String[]columnNameList,
//				OrderByInfo orderInfo
//		){
		
		ArrayList<Double> totalAllLBP15thValueList=new ArrayList<Double>();
		
		//-----------------------------------------------------------------------------------------------
	
		try{		
			//--> If there is a payroll date before data found.
			if(payrollDateBefore!=null){
				
				//--> Add aditional condition including this payroll date before
				conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[1],payrollDateBefore ));
				//--> Add aditional condition to NOT retrieve data with all zero values in a row.
				conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[11],0 ));
				conditionColumnAndValueList.get(conditionColumnAndValueList.size()-1).setSign("!=");
				
				
				
				//--> Retrieve all LBP data from database of the chosen payroll date and the payroll date before.
				db.selectDataInDatabase(
					new String[]{db.tableNameEmployee,db.tableNameDeductions},
					new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],db.deductionTableColumnNames[11],db.payrollDateTableColumnNames[0],db.tableNameEmployee+"."+db.employeeTableColumnNames[0]}, //FamilyName, FirstName, LBP, PayrollDate, DeductionID 
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
				
				for(int i=1,count=1;i<=totalNumOfRows;count++,i++){
					
					//--> Go to row
					db.resultSet.absolute(i);
					
					//--> Add the number data in cell
					PdfPCell cell= initNewNormalCell(""+count, contentFont);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center and number indicator data.
					table.addCell(cell);
					
					//--> Add the name  to table
					cell= initNewNormalCell(db.resultSet.getObject(1).toString()+", "+db.resultSet.getObject(2).toString(), contentFont);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left the name
					table.addCell(cell);
				
					
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
						cell= initNewNormalCell(
							(value==0)?"-":util.insertComma(""+value),
							contentFont
						);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right the numbers values
						table.addCell(cell);
						totalOf15thAnd30th+=value;
						totalAllLBP15thValueList.set(0,totalAllLBP15thValueList.get(0)+value);
						
						//--> Add the value of 30th column cell.
						value=0;
						if(empIDAfterThisRow!=null && employeeIDOfThisRow.equals(empIDAfterThisRow)){
							db.resultSet.absolute(i+1);
							value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
							i++;
						}
						cell= initNewNormalCell(
							(value==0)?"-":util.insertComma(""+value),
							contentFont
						);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right the numbers values
						table.addCell(cell);
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
						cell= initNewNormalCell(
							(value==0)?"-":util.insertComma(""+value),
							contentFont
						);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right the numbers values
						table.addCell(cell);
						totalOf15thAnd30th+=value;
						totalAllLBP15thValueList.set(0,totalAllLBP15thValueList.get(0)+value);
						
						//--> Add the value of 30th column cell.
						value=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(db.resultSet.getObject(columnValIndex).toString()));
						cell= initNewNormalCell(
							(value==0)?"-":util.insertComma(""+value),
							contentFont
						);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right the numbers values
						table.addCell(cell);
						totalOf15thAnd30th+=value;
						totalAllLBP15thValueList.set(1,totalAllLBP15thValueList.get(1)+value);
								
						
					}
					
					
					//--> Add the total value where 15th + 30th value.
					totalOf15thAnd30th=util.convertRoundToOnlyTwoDecimalPlaces(totalOf15thAnd30th);
					cell= initNewNormalCell(
						(totalOf15thAnd30th==0)?"0":util.insertComma(""+totalOf15thAnd30th),
						contentFont
					);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right and number/double value data.
					table.addCell(cell);
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
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		BaseColor color=BaseColor.WHITE;
		//> None
		Phrase p= initNewPhrase(
				"",
				headerFont);
		PdfPCell headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total word
		p= initNewPhrase(
				"TOTAL",
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total Value of 15th, 30th and 15th+30th
		for(int i=0;i<totalAllLBP15thValueList.size();i++){
			double num= util.convertRoundToOnlyTwoDecimalPlaces(totalAllLBP15thValueList.get(i));
			p= initNewPhrase(
				(num==0)?"-":""+util.insertComma(""+num),
				headerFont
			);
			headerCell=initNewHeaderCell(p,color);
			headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			headerCell.setBorderWidth(1);
			table.addCell(headerCell);
		}
		
		return table;
		
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
	 */
	private PdfPTable processLBPSecondPayTableContent_30thDay(PdfPTable table,
			Database db,
			ArrayList<SelectConditionInfo>conditionColumnAndValueList,
			String[] joinColumnCompareList, Utilities util){

//		private String selectInnerJoinWithCondition(
//		String[] tableNameList,
//		String[]columnNameList, 
//		ArrayList<SelectConditionInfo>conditionColumnAndValueList,
//		String[] joinColumnCompareList){
		
		double totalAllLBP30thValue=0;
		
		//--> Add aditional condition to NOT retrieve data with all zero values in a row.
		conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[11],0 ));
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
			new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],db.deductionTableColumnNames[11]}, //FamilyName, FirstName, LBP 
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
			
			for(int i=1;i<=totalNumOfRows;i++){
				db.resultSet.absolute(i);
				//> Add the number data in cell
				PdfPCell cell= initNewNormalCell(""+i, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center and number indicator data.
				table.addCell(cell);
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					cell= initNewNormalCell(
							(j==db.metaData.getColumnCount())?util.insertComma(data)+"\t":data,
							contentFont
					);
					
					if(j==db.metaData.getColumnCount()){
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right and number/double value data.
						totalAllLBP30thValue+=Double.parseDouble(data);
					}
					table.addCell(cell);		
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//------------------------------------------------------------------------------------
		
		//--> Process ALL Total Asemco 30th Value.
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		BaseColor color=BaseColor.WHITE;
		//> None
		Phrase p= initNewPhrase(
				"",
				headerFont);
		PdfPCell headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total
		p= initNewPhrase(
				"TOTAL",
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total Value
		totalAllLBP30thValue=
				util.convertRoundToOnlyTwoDecimalPlaces(totalAllLBP30thValue);
		
		p= initNewPhrase(
				""+util.insertComma(totalAllLBP30thValue),
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		return table;
	}
	
	/**
	 * Process the content of the table of LBP[LandBank of the Philippines]
	 * @param table
	 * @param db
	 * @param day
	 * @param util
	 * @param payrollDate
	 * @return
	 */
	private PdfPTable processPayrollContentLBPSummary(
			PdfPTable table, Database db,
			String day,Utilities util,
			String payrollDate,String payrollDateBefore){
	
		
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
		
		//--> PROCESS IF THE PAYROLL DATE IS AT 15th or FIRST PAY
		if(util.isFirstPayOfTheMonth(day)){	
			table = processLBPFirstPayTableContent_15thDay(table, db,
					conditionColumnAndValueList, joinColumnCompareList,
					payrollDate, payrollDateBefore, util);
		}
		//-----------------------------------------------------------------------------------------------
		
		//--> PROCESS IF THE PAYROLL DATE IS AT 30th or SECOND PAY
		else{
			
			table = processLBPSecondPayTableContent_30thDay(table,
					db, conditionColumnAndValueList, joinColumnCompareList,
					util
			);
		}
		return table;
	}
	private void l_________________SSS_LOAN_and_PAGIBIG_LOAN___________________________________l(){}
	
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
	 * @param table
	 * @param db
	 * @param day
	 * @param util
	 * @param payrollDate
	 * @return
	 */
	private PdfPTable processPayrollContentSssPagibigLoanSummary(
			PdfPTable table, Database db,
			Utilities util,
			String payrollDate, String neededColumnName){
	
		
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
			
			for(int i=1;i<=totalNumOfRows;i++){
				db.resultSet.absolute(i);
				//> Add the number data in cell
				PdfPCell cell= initNewNormalCell(""+i, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center and number indicator data.
				table.addCell(cell);
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					cell= initNewNormalCell(
							(j==db.metaData.getColumnCount())?util.insertComma(data)+"\t":data,
							contentFont
					);
					
					if(j==db.metaData.getColumnCount()){
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right and number/double value data.
						totalAllSssPagibigLoanValue+=Double.parseDouble(data);
					}
					table.addCell(cell);		
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//-----------------------------------------------------------------------------------------
		//--> Process ALL TOTAL Asemco 15th Value.
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		BaseColor color=BaseColor.WHITE;
		//> None
		Phrase p= initNewPhrase(
				"",
				headerFont);
		PdfPCell headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total
		p= initNewPhrase(
				"TOTAL",
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total Value
		totalAllSssPagibigLoanValue=
				util.convertRoundToOnlyTwoDecimalPlaces(totalAllSssPagibigLoanValue);
		
		p= initNewPhrase(
				""+util.insertComma(totalAllSssPagibigLoanValue),
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);

	
		return table;
	}
	private void l_________________UNION_DUES____________________________________________________l(){}
	
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
	 * Process the content of the table of Union Dues.
	 * @param table
	 * @param db
	 * @param util
	 * @param payrollDate
	 * @return
	 */
	private PdfPTable processPayrollContentUnionDuesSummary(
			PdfPTable table, Database db,
			Utilities util,
			String payrollDate){
	
		
		//--> Required Query
//		SELECT  FamilyName, UnionDues
//	    FROM employee
//	    INNER JOIN deductions
//	    ON employee.EmployeeID=deductions.EmployeeID
//	    WHERE PayrollDate='2018-04-30';
		
		//-----------------------------------------------------------------------------------------------
		//--> Set Condition parameters
		ArrayList<SelectConditionInfo> conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
		conditionColumnAndValueList.add(new SelectConditionInfo(db.deductionTableColumnNames[1],payrollDate )); // PayrollDate
		//--> Add aditional condition to NOT retrieve data with all zero values in a row.
		conditionColumnAndValueList.add(new SelectConditionInfo(util.addSlantApostropheToString(db.deductionTableColumnNames[10]),0 )); //Un-Dues 
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
			new String[]{db.employeeTableColumnNames[2],db.employeeTableColumnNames[3],util.addSlantApostropheToString(db.deductionTableColumnNames[10])}, //FamilyName, FirstName, UnionDues
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
			
			for(int i=1;i<=totalNumOfRows;i++){
				db.resultSet.absolute(i);
				//> Add the number data in cell
				PdfPCell cell= initNewNormalCell(""+i, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center and number indicator data.
				table.addCell(cell);
				
				double totalUnionDuesValue=0;
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					cell= initNewNormalCell(
							(j==db.metaData.getColumnCount())?util.insertComma(data)+"\t":data,
							contentFont
					);
					
					if(j==db.metaData.getColumnCount()){
						totalAllUnionDues30thValueList.set(
								0,
								totalAllUnionDues30thValueList.get(0)+Double.parseDouble(data)
						); // Set the totalAll values of EE column
						
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right and number/double value data.
						totalUnionDuesValue+=Double.parseDouble(data);
					}
					table.addCell(cell);		
				}
				
				//> Add The Total in table
				totalAllUnionDues30thValueList.set(1, 
						totalAllUnionDues30thValueList.get(1)+totalUnionDuesValue); // Set the totalAll values of Total column
				
				String totalValueSTRING=util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(totalUnionDuesValue));
				
				cell= initNewNormalCell(totalValueSTRING, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align center and number indicator data.
				table.addCell(cell);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//-----------------------------------------------------------------------------------------
		//--> Process ALL TOTAL Union Dues.
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		BaseColor color=BaseColor.WHITE;
		//> None
		Phrase p= initNewPhrase(
				"",
				headerFont);
		PdfPCell headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total
		p= initNewPhrase(
				"TOTAL",
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
//		//> Total All Value of EE Column 
		totalAllUnionDues30thValueList.set(0, util.convertRoundToOnlyTwoDecimalPlaces(totalAllUnionDues30thValueList.get(0)));
		p= initNewPhrase(
			""+util.insertComma(totalAllUnionDues30thValueList.get(0)),
			headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		
		//> Total All Value of Total Column 
		totalAllUnionDues30thValueList.set(1, util.convertRoundToOnlyTwoDecimalPlaces(totalAllUnionDues30thValueList.get(1)));
		p= initNewPhrase(
				""+util.insertComma(totalAllUnionDues30thValueList.get(1)),
				headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);

	
		return table;
	}
	private void l_________________HDMF_PAGIBIG_CONT_and_MEDICARE_____________________________________l(){}
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
	 */
	private PdfPTable processPayrollContentHDMFMedicareSummary(
			PdfPTable table, Database db,
			Utilities util,
			String payrollDate,String neededColumnName,String neededColumnNameER){
	
		
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
			
			for(int i=1;i<=totalNumOfRows;i++){
				db.resultSet.absolute(i);
				//> Add the number data in cell
				PdfPCell cell= initNewNormalCell(""+i, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center and number indicator data.
				table.addCell(cell);
				
				double totalHDMFMedicareValue=0;
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					cell= initNewNormalCell(
							(j>=db.metaData.getColumnCount()-1)?util.insertComma(data)+"\t":data,
							contentFont
					);
					
					//--> Process if the data is a number and not the name.
					if(j>=db.metaData.getColumnCount()-1){ // if the data is from column EE and ER
						int index=(j==db.metaData.getColumnCount()-1)?0:1; // If the index is for the total of EE[index->0] or ER[index->1]
						
						totalAllHDMFMedicare30thValueList.set(
								index,
								totalAllHDMFMedicare30thValueList.get(index)+Double.parseDouble(data)
						); // Set the totalAll values of EE and ER column
						
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right and number/double value data.
					
						//> This add the EE and ER 
						totalHDMFMedicareValue+=Double.parseDouble(data);
					}
					table.addCell(cell);		
				}
//				
				//> Add The Total in table
				//> WHy 2? since the TOTAL column in the table is at index 2 on the variable totalAllHDMF30thValueList.
				totalAllHDMFMedicare30thValueList.set(2, 
						totalAllHDMFMedicare30thValueList.get(2)+totalHDMFMedicareValue); // Set the totalAll values of Total column
				
				String totalHDMFMedicareValueSTR=util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(totalHDMFMedicareValue));
				cell= initNewNormalCell(totalHDMFMedicareValueSTR, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align center and number indicator data.
				table.addCell(cell);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//-----------------------------------------------------------------------------------------
		//--> Process ALL TOTAL Union Dues.
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		BaseColor color=BaseColor.WHITE;
		//> None
		Phrase p= initNewPhrase(
				"",
				headerFont);
		PdfPCell headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//> Total
		p= initNewPhrase(
				"TOTAL",
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
//		//> Total All Value of EE Column 
		totalAllHDMFMedicare30thValueList.set(0, util.convertRoundToOnlyTwoDecimalPlaces(totalAllHDMFMedicare30thValueList.get(0)));
		p= initNewPhrase(
			""+util.insertComma(totalAllHDMFMedicare30thValueList.get(0)),
			headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		
//		//> Total All Value of ER Column 
		totalAllHDMFMedicare30thValueList.set(1, util.convertRoundToOnlyTwoDecimalPlaces(totalAllHDMFMedicare30thValueList.get(1)));
		p= initNewPhrase(
			""+util.insertComma(totalAllHDMFMedicare30thValueList.get(1)),
			headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		
		//> Total All Value of Total Column 
		totalAllHDMFMedicare30thValueList.set(2, util.convertRoundToOnlyTwoDecimalPlaces(totalAllHDMFMedicare30thValueList.get(2)));
		p= initNewPhrase(
				""+util.insertComma(totalAllHDMFMedicare30thValueList.get(2)),
				headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);

	
		return table;
	}

	private void l_________________SSS_CONTRIBUTION________________________________________l(){}
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
	 * Process the content of the table of SSS CONTRIBUTION.
	 * @param table
	 * @param db
	 * @param util
	 * @param payrollDate
	 * @return
	 */
	private PdfPTable processPayrollContentSSSContributionSummary(
			PdfPTable table, Database db,
			Utilities util,
			String payrollDate){
	
		
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
			
			for(int i=1;i<=totalNumOfRows;i++){
				db.resultSet.absolute(i);
				//> Add the number data in cell
				PdfPCell cell= initNewNormalCell(""+i, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Align center and number indicator data.
				table.addCell(cell);
				
				double totalSSSContValue=0,ee=0,er=0;
				//> Why 2 since in the name part we combine two columns LastName and FirstName
				for(int j=2;j<=db.metaData.getColumnCount();j++){
					String data=(j==2)?
							db.resultSet.getObject(j-1).toString()+", "+db.resultSet.getObject(j).toString()
							:
							db.resultSet.getObject(j).toString();	
					
					cell= initNewNormalCell(
							(j>=db.metaData.getColumnCount()-1)?util.insertComma(data)+"\t":data,
							contentFont
					);
					
					//--> Process if the data is a number and not the name.
					if(j>=db.metaData.getColumnCount()-1){ // if the data is from column EE and ER
						int index=(j==db.metaData.getColumnCount()-1)?0:1; // If the index is for the total of EE[index->0] or ER[index->1]
						
						totalAllSSSCont30thValueList.set(
								index,
								totalAllSSSCont30thValueList.get(index)+Double.parseDouble(data)
						); // Set the totalAll values of EE and ER column
						
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align right and number/double value data.
					
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
					table.addCell(cell);		
				}
				
				//> Process the EC part of the column
				double ec=db.getECFromSSSDataList(ee, er);
				totalAllSSSCont30thValueList.set(2, 
				totalAllSSSCont30thValueList.get(2)+ec); // Set the totalAll values of Total column
				cell= initNewNormalCell(util.insertComma(""+ec), contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align center and number indicator data.
				table.addCell(cell);
				
				totalSSSContValue+=ec;
				
				
				//-------------------------------------
				
				//> Add The Total in table
				//> WHy 2? since the TOTAL column in the table is at index 2 on the variable totalAllHDMF30thValueList.
				totalAllSSSCont30thValueList.set(3, 
						totalAllSSSCont30thValueList.get(3)+totalSSSContValue); // Set the totalAll values of Total column
				
				String totalValueSTRING=util.insertComma(util.convertRoundToOnlyTwoDecimalPlaces(totalSSSContValue));
				
				cell= initNewNormalCell(totalValueSTRING, contentFont);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align center and number indicator data.
				table.addCell(cell);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//-----------------------------------------------------------------------------------------
		//--> Process ALL TOTAL Union Dues.
		Font headerFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		BaseColor color=BaseColor.WHITE;
		
		//------------
		//> None
		Phrase p= initNewPhrase(
				"",
				headerFont);
		PdfPCell headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//------------
		//> Total
		p= initNewPhrase(
				"TOTAL",
				headerFont);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//------------
		//> Total All Value of EE Column 
		totalAllSSSCont30thValueList.set(0, util.convertRoundToOnlyTwoDecimalPlaces(totalAllSSSCont30thValueList.get(0)));
		p= initNewPhrase(
			""+util.insertComma(totalAllSSSCont30thValueList.get(0)),
			headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		
		//------------
		//> Total All Value of ER Column 
		totalAllSSSCont30thValueList.set(1, util.convertRoundToOnlyTwoDecimalPlaces(totalAllSSSCont30thValueList.get(1)));
		p= initNewPhrase(
			""+util.insertComma(totalAllSSSCont30thValueList.get(1)),
			headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
		
		//------------
		//> Total All Value of EC Column 
		totalAllSSSCont30thValueList.set(2, util.convertRoundToOnlyTwoDecimalPlaces(totalAllSSSCont30thValueList.get(2)));
		p= initNewPhrase(
			""+util.insertComma(totalAllSSSCont30thValueList.get(2)),
			headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);
				
		//------------
		//> Total All Value of Total Column 
		totalAllSSSCont30thValueList.set(3, util.convertRoundToOnlyTwoDecimalPlaces(totalAllSSSCont30thValueList.get(3)));
		p= initNewPhrase(
				""+util.insertComma(totalAllSSSCont30thValueList.get(3)),
				headerFont
		);
		headerCell=initNewHeaderCell(p,color);
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell.setBorderWidth(1);
		table.addCell(headerCell);

	
		return table;
	}
	private void l_________________________________________________l(){}
	
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
	 * Set the String content of the payroll header list.
	 * @param db
	 * @param list
	 * @return
	 */
	private ArrayList<String> getPayrollHeaderTexts(Database db,ArrayList<String> list,int mode){
		list.clear();
		totalValueList.clear();
		
		list.add("#");
		list.add((mode==Constant.PAYROLL_PER_DEPARTMENT_PDF)?"Name of Employee":"Name of Dept");
		int numOfFirstColumnsNotIncluded=3; // ID, Payroll Data, EmployeeID 
		
		//-----------------------------------------------
		
		
		//--> Transfer the correct columns in new array strings
		if(correctDedsColumnList==null && correctEarnColumnList==null){
			correctEarnColumnList=new String[db.earningTableColumnNames.length-1-numOfFirstColumnsNotIncluded];
			correctDedsColumnList= new String[db.deductionTableColumnNames.length-2-numOfFirstColumnsNotIncluded];
			for(int i=numOfFirstColumnsNotIncluded,j=0;i<db.earningTableColumnNames.length-1;i++,j++){
				correctEarnColumnList[j]=db.earningTableColumnNames[i];
			}
			for(int i=numOfFirstColumnsNotIncluded,j=0;i<db.deductionTableColumnNames.length-2;i++,j++){
				correctDedsColumnList[j]=db.deductionTableColumnNames[i];
			}
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
	 * Processing the contents of in payroll where heads will sign their signature.
	 * In my case I made it in a table so that the spacing is unifoirm.
	 * @param signatureTable
	 * @param font
	 * @return
	 */
	private PdfPTable processPayrollContentSignature(PdfPTable signatureTable,
			PdfPTable dataTable,Font font,int mode){
		Database db = Database.getInstance();
		Utilities util = Utilities.getInstance();
		signatureTable=(mode == Constant.PAYROLL_PER_DEPARTMENT_PDF ||
					mode == Constant.PAYROLL_OVERALL_PDF)?
				new PdfPTable(4):new PdfPTable(3);
		signatureTable.setWidthPercentage(100);
		
		//----------------------------------------------------------------------
		
		Font nameFont =new  Font(FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK);
		int numOfTableColumns=signatureTable.getNumberOfColumns();
		
		
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
		//// Add space to avoid the prepared,checked,audited by: be separated from Sir michael,maam leizyl,maam maureen name. 49,50,51 + 2
		if(dataTable.getRows().size()>=51 && dataTable.getRows().size()<=53){
			signatureTable=addEmptyRowSpaceInSignatureTable(signatureTable, nameFont);
		}
		
		
		for(int i=0;i<numOfTableColumns*2;i++){
			PdfPCell cell=initNewNormalCell("", font);
			cell.setBorderColor(BaseColor.WHITE);
			Paragraph p=(Paragraph) cell.getPhrase();
			
			if(i<numOfTableColumns){
				p.add(new Chunk(""+contentNameList[0][i]));
			}
			else{
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				Chunk c=new Chunk("\n\n"+contentNameList[1][i-numOfTableColumns]);
				c.setFont(nameFont);
				p.add(c);
				
				c=new Chunk("\n"+contentNameList[2][i-numOfTableColumns]);
				p.add(c);
			}
			
			
			signatureTable.addCell(cell);
		}
		
		//----------------------------------------------------------------------
		
		
		
		//--> Put the GM info below
		if(!(mode == Constant.PAYROLL_PER_DEPARTMENT_PDF ||
					mode == Constant.PAYROLL_OVERALL_PDF)){
			
			//--> Space
			signatureTable=addEmptyRowSpaceInSignatureTable(signatureTable, nameFont);
			
			// Add space to avoid the approved by: be separated from GM's name.
			if(dataTable.getRows().size()>=45 && dataTable.getRows().size()<=47){
				signatureTable=addEmptyRowSpaceInSignatureTable(signatureTable, nameFont);
			}
			
			
			//---------------------------
			//--> Add the GM strings
			int gmIndex=contentNameList[0].length-1;
		
			PdfPCell cell=null;
			for(int i=0;i<contentNameList.length-1;i++){
				for( int j=0;j<signatureTable.getNumberOfColumns();j++){
					if(j==1){
						cell=initNewNormalCell("", font);
						cell.setBorderColor(BaseColor.WHITE);
						Paragraph p=(Paragraph) cell.getPhrase();
						if(i==0){ // "Approved by: "
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							p.add(new Chunk(""+contentNameList[i][gmIndex])); //"Approved by: "
						}
						else{ // "GERARDO N. OLEDAN,REE" | "General Manager"
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							Chunk c=new Chunk("\n\n"+contentNameList[i][gmIndex]); // "GERARDO N. OLEDAN,REE"
							c.setFont(nameFont);
							p.add(c);
							
							i++; 
							
							c=new Chunk("\n"+contentNameList[i][gmIndex]); // "General Manager"
							p.add(c);
						}
						
						signatureTable.addCell(cell);
					}
					else{
						//--> Empty Space
						cell=initNewNormalCell(" ", font);
						cell.setBorderColor(BaseColor.WHITE);
						signatureTable.addCell(cell);
					}
				}
			}
			
			
		}
		
		//----------------------------------------------------------------------
		return signatureTable;
	}
	
	/**
	 * Processing the payroll header. This method is where the headers are created.
	 * This payroll head process is complex since it has rowspan of 2.
	 * @param table
	 * @param db
	 * @param util
	 * @param font
	 * @return
	 */
	private PdfPTable processComplexRegularPayrollHeader(PdfPTable table,Database db, Utilities util,
			Font font){
		cellNumberRowSpanTwoList.clear();
		int counter=0,
				earnLastIndex=db.earningTableColumnNames.length-1,
				dedLasiIndex=db.deductionTableColumnNames.length-2;
		
		
		for(String str:payrollHeaderList){
			
			Phrase p= initNewPhrase(util.convertCamelCaseColumnNamesToReadable(str),
					font);
			
//			BaseColor color=(counter<2)?BaseColor.BLUE:
//				((str.equals(db.earningTableColumnNames[earnLastIndex]) || // Total Earnings[GREEN]
//					str.equals(db.deductionTableColumnNames[dedLasiIndex]) || // Total Deds[GREEN]
//					str.equals(netPayColumnName))?BaseColor.GREEN:BaseColor.YELLOW); // Net Pay[GREEN]
			BaseColor color=BaseColor.WHITE;
			
			PdfPCell headerCell=initNewHeaderCell(p,color);
			
			if(counter<2  || 											// # and Name of Employee
				str.equals(db.earningTableColumnNames[earnLastIndex]) || 	// Total Earnings[GREEN]
				str.equals(db.deductionTableColumnNames[dedLasiIndex]) || 	// Total Deds[GREEN]
				str.equals(netPayColumnName)){										// Net Pay[GREEN]
					
				headerCell.setRowspan(2); 
				
				if(!str.equals(netPayColumnName)){
					cellNumberRowSpanTwoList.add(counter);
				}
			}
			
			table.addCell(headerCell);
			counter++;
		}
		return table;
	}
	
	/**
	 * Processing the payroll header. This method is where the headers are created.
	 * This payroll head process is complex since it has rowspan of 2.
	 * @param table
	 * @param db
	 * @param util
	 * @param font
	 * @return
	 */
	private PdfPTable processComplexContractualPayrollHeader(PdfPTable table,Database db, Utilities util,
			Font font){
		cellNumberRowSpanTwoList.clear();
		int counter=0;
		
		for(String str:payrollHeaderList){
//			System.out.println("\t\t\t >>>>: "+str+CLASS_NAME);
//			
//			
			Phrase p= initNewPhrase(util.convertCamelCaseColumnNamesToReadable(str),
					font);
			BaseColor color=BaseColor.WHITE;
			
			PdfPCell headerCell=initNewHeaderCell(p,color);
			
			if(counter<2  || 											// # and Name of Employee
				str.equals(netPayColumnName) ||// Net Pay[GREEN]
				str.equals(signatureColumnName)){ // Signature[GREEN]
					
				headerCell=mergeRowCell(headerCell, 2); 
			}
			
			if(str.equals("Earnings")){	// Total Earnings[GREEN]	
				headerCell.setColspan(5);
			}
			
			if(str.equals("Deductions")){// Total Deds[GREEN]
				headerCell.setColspan(9);
			}
			
			table.addCell(headerCell);
			counter++;
		}
		return table;
	}
	
	/**
	 * This is where the creation of the headers in table put in pdf.
	 * This is called simple since it has NO row spans.
	 * @param table
	 * @param db
	 * @param font
	 * @return
	 */
	private PdfPTable processSimplePayrollHeader(PdfPTable table,Database db,
			Font font){
		BaseColor color=BaseColor.WHITE;
		for(String str:payrollHeaderList){
			Phrase p= initNewPhrase(
					str,
					font);
			PdfPCell headerCell=initNewHeaderCell(p,color);
			table.addCell(headerCell);
		}
		return table;
	}
	private void l______________________________________l(){}
	  
	/**
	 * Add an empty whole row space in signature table.
	 * @param table
	 * @param font
	 * @return
	 */
	private PdfPTable addEmptyRowSpaceInSignatureTable(PdfPTable table, Font font){
		PdfPCell cell=initNewNormalCell(" \n\n\n\n", font);
		cell.setBorderColor(BaseColor.WHITE);
		cell.setColspan(table.getNumberOfColumns());
		table.addCell(cell);
		
		return table;
	}
	 /**
	  * Add empty line, the number of lines depends on the value of variable number.
	  * @param paragraph
	  * @param numberOfLines
	  * @return
	  */
	 private Paragraph addEmptyLineInPDF(Paragraph paragraph, int numberOfLines) {
		 for (int i = 0; i < numberOfLines; i++) {
			 paragraph.add(new Paragraph(" "));
		 }
		 return paragraph;
	 }
	 

	 /**
	  * Add text in pdf based from the given string.
	  * @param p
	  * @param str
	  * @return
	  */
	 private Paragraph addTextInPDF(Paragraph p, String str,Font font){
		 Phrase phrase=(new Phrase(str, font));
		 p.add(phrase);
			 
		 return p;
	 }
	  
	 
	 /**
	  * Add swing components panel, etc. as image in pdf file.
	  * @param util
	  * @param paragraph
	  * @param component
	  * @param scalePercent
	  * @param alignment
	  * @return
	  * @throws BadElementException
	  * @throws IOException
	  */
	 
	 private Paragraph addSwingComponentAsImageInPDF(Utilities util,Paragraph paragraph, JComponent component, int scalePercent,int alignment) throws BadElementException, IOException{
		  Image image = Image.getInstance(writer, util.getImageFrom(component), 1);
		  image.scalePercent(scalePercent);
		  image.setAlignment(alignment);
		  paragraph.add(image);
		  
		  return paragraph;
	  }
	 
	 /**
	  * Add image in pdf.
	  * @param paragraph
	  * @param bufferedImage
	  * @param scalePercent
	  * @param alignment
	  * @return
	  * @throws BadElementException
	  * @throws IOException
	  */
	 private Paragraph addImageInPdf(Paragraph paragraph, BufferedImage bufferedImage, int scalePercent, int alignment) throws BadElementException, IOException{
		 Image image = Image.getInstance(writer, bufferedImage, 1);
		 image.scalePercent(scalePercent);
		 image.setAlignment(alignment);
		 paragraph.add(image);
		  
		 return paragraph;
	 }
	 
	 /**
	  * Create new header cell with the style of a header.
	  * 	Set the text with given phrse and color.
	  * @param p
	  * @param backgroundColor
	  * @return
	  */
	 private PdfPCell initNewHeaderCell(Phrase p, BaseColor backgroundColor){
	     
		 PdfPCell header = new PdfPCell();
		 header.setBackgroundColor(backgroundColor);
		 header.setBorderWidth(2);
		 header.setHorizontalAlignment(Element.ALIGN_CENTER);
		 header.setVerticalAlignment(Element.ALIGN_MIDDLE);
		 header.setPhrase(p);
		 
		 return header;
	 }
	 /**
	  * Create a cell based from font and string given.
	  * 	Use this method if you want that the text in each cell is at your desired font size,etc.
	  * @param str
	  * @param font
	  * @return
	  */
	 private PdfPCell initNewNormalCell(String str, Font font){
		 PdfPCell cell=new PdfPCell();
    	 Paragraph p=new Paragraph(str,font);
    	 cell.setPhrase(p);

    	 return cell;
	 }
	 
	 /**
	  * Use this method if you want to add a text in a cell with the desired font.
	  * @param text
	  * @param font
	  * @return
	  */
	 private Phrase initNewPhrase(String text,Font font){
		 Phrase p= new Phrase(text,font);
//		 p.setFont(font); DOES NOT WORK!!
		 return p;
	 }
	 
	 /**
	  * When you want to merge to row cells. 
	  * @param cell
	  * @param numOfRowCellToMerge
	  * @return
	  */
	 private PdfPCell mergeRowCell(PdfPCell cell, int numOfRowCellToMerge){
		 cell.setRowspan(numOfRowCellToMerge);
		 return cell;
	 }
	 private void l_________________________________________l(){}
		 
	  public static PDFCreator getInstance(){
		if(instance == null)
			instance = new PDFCreator();
		
		return instance;
	  }
	  
	  public static void setInstanceToNull(){
			instance =null;
		}
	  
	
	  private void l_______________________________________________________l(){}
	  
	  public class Rotate extends PdfPageEventHelper {
		  protected PdfNumber rotation = PdfPage.PORTRAIT;
		  public void setRotation(PdfNumber rotation) {
			  this.rotation = rotation;
		  }
		  public void onEndPage(PdfWriter writer, Document document) {
			  writer.addPageDictEntry(PdfName.ROTATE, rotation);
		  }
	  }
}
