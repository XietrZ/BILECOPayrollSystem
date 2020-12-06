package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * This class is where all CONSTANT variables are stored for easy access and use.
 * @author XietrZ
 *
 */
public class Constant {
	
	public final static String PAYROLL_SYSTEM_VERSION="version 1.6",
			LOCKED_STATUS="Locked",NOT_LOCKED_STATUS="Not Locked";
	
	
	private void l__________________________________________________l(){}
	
	public final static int PAYROLL_SYSTEM_MODE_REGULAR=111, PAYROLL_SYSTEM_MODE_CONTRACTUAL=222;
	
	
	private void l___________________________________________________l(){}
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public final static int FRAME_WIDTH =1260,//screenSize.width-100,
			FRAME_HEIGHT=718,//screenSize.height-50;
			SIGNATURE_IMAGE_HEIGHT=71;
	private void l____________________________l(){}
	
	
	public final static int SCROLL_BAR_WIDTH=20;
	
	private void l_____________________________l(){}
	public final static int TABLE_COLUMN_FIXED_WIDTH=150;
	
	private void l______________________________l(){}
	public final static int VIEW_Y=79,VIEW_WIDTH=1260, VIEW_HEIGHT= 611;
	private void l_______________________________l(){}
	public final static int ROW_COUNT_LABEL_WIDTH=300,ROW_COUNT_LABEL_HEIGHT=20;
	private void l__________________________________________________________________________l(){}
	public final static Color HIGHLIGHT_EDITABLE_ROW=new Color(255, 153, 153),
			HIGHLIGHT_SELECTED_ROW=new  Color(255, 255, 153);
	
	private void l_________________________________l(){}
	public final static String FILE_PATH_NAME_PDF="PDFFile.pdf",
							FILE_PATH_NAME_EXCEL="EXCELFile.xls";
	
	
	//--> Default renderer for values table that is number/double. 
	public final static DecimalFormatRenderer DecimalFormatRenderer= new DecimalFormatRenderer();
	public final static DateFormatRenderer DateFormatRenderer= new DateFormatRenderer();
	
	public final static WordWrapCellRenderer WordWrapCellRenderer=new WordWrapCellRenderer();
		
	private void l____________________________________l(){}
	
	//-->STATIC VALUES for OPTIONS
	public final static int SELECT_ALL=1, // Just select all based from given table.
			SELECT_ALL_WITH_CONDITION_AND=2, // Select ALL based from given table and a condition using AND
			SELECT_ALL_WITH_CONDITION_OR=3,
			SELECT_BASED_FROM_COLUMN=4, // Select based from given columns and table
			SELECT_BASED_FROM_COLUMN_WITH_CONDITION_AND=5, // Select based from given columns,table and condition
			SELECT_BASED_FROM_COLUMN_WITH_CONDITION_OR=6,
			SELECT_DISTINCT=7,
			SELECT_INNER_JOIN=8,
			SELECT_INNER_JOIN_WITH_CONDITION_AND=9,
			SELECT_INNER_JOIN_WITH_CONDITION_OR=10,
			SELECT_SUM_WITH_CONDITION=11,
			SELECT_COUNT_WITH_CONDITION=12,
			SELECT_UNION_BASED_FROM_COLUMN=13,
			SELECT_SPECIAL_UNION_PAYROLL_DATA=14,
			SELECT_SPECIAL_UNION_PAYROLL_DATA_WITH_CONDITION=15,
			SELECT_SPECIAL_EMPLOYEE_PAYROLL_SUMMARY_PDF=16,
			SELECT_SPECIAL_AOBDCSL_30th_PAYROLL_DATA=17,
			SELECT_SPECIAL_HDMF_MEDICARE_30th_PAYROLL_DATA=18,
			SELECT_SPECIAL_PER_CONTRACTUAL_EMPLOYEE_SUMMARY=19;
	
	public final static int CLASS_OBJECT_DOUBLE=1,
			CLASS_OBJECT_STRING=2,
			CLASS_OBJECT_DATE=3,
			CLASS_OBJECT_INTEGER=4;
	
	private void l___________________________l(){}
	
	public final static Color BILECO_DEFAULT_COLOR_GREEN=new Color(143, 188, 143);
	public final static Color BILECO_DEFAULT_COLOR_BLUE=new Color(153, 204, 255);
	public final static Color DIALOG_BOX_COLOR_BG= new Color(48,120,161);
	
	private void l___________________________________l(){}
	public final static Font VIEW_TITLE= new Font("Arial Black", Font.BOLD, 18);
	
	private void l_____________________________________l(){}
	//> PAYROLL_DATE_ONE is when its either you are in Earning/Deduction VIEW, you need to choose ONE since the payroll date is not the same on both views depending in the user.
	//> PAYROLL_DATE_DATA_ALL is used when you are getting the TOTAK payroll dates from both earning and deduction view and it must be distinct.
	public final static int PAYROLL_DATE_DATA_ONE=1, PAYROLL_DATE_DATA_ALL=2;
	
	private void l_______________________________________l(){}
	public final static String STRING_ALL="ALL", STRING_YES="Yes", STRING_NO="No",
			STRING_REGULAR="Regular", STRING_CONTRACTUAL="Contractual",
			STRING_DATE_LEFT_NULL="9999-01-01";
	
	private void l_________________________________________l(){}
	public final static int DEDUCTION_MODE=1,
			EARNING_MODE=2;
	
	private void l___________________________________________l(){}
	public final static int EDIT_ONCE=1, EDIT_MULTIPLE=2; // Edit one column, Edit multiple column
	
	private void l___________________________________________________________l(){}
	public final static int PAYSLIP_PDF=1, PAYROLL_PER_DEPARTMENT_PDF=2, PAYROLL_OVERALL_PDF=3,
			PAYROLL_ASEMCO_PDF=4,PAYROLL_BCCI_PDF=5,PAYROLL_OCCCI_PDF=6,PAYROLL_DBP_PDF=7,
			PAYROLL_CFI_PDF=8,PAYROLL_SSS_LOAN_PDF=9,PAYROLL_PAGIBIG_LOAN_PDF=10,PAYROLL_ST_PETER_PLAN_PDF=11,
			PAYROLL_LBP_PDF=12,PAYROLL_UNION_DUES_PDF=13,PAYROLL_HDMF_PDF=14,PAYROLL_MEDICARE_PDF=15,
			PAYROLL_SSS_CONT_PDF=16,PAYROLL_W_TAX_PDF=17,
			
			PAYROLL_PER_CONTRACTUAL_EMPLOYEE_PDF=18;
	
	private void l_____________________________________________l(){}
	public final static int  PAYROLL_PER_DEPARTMENT_EXCEL=22, PAYROLL_OVERALL_EXCEL=23,
			PAYROLL_ASEMCO_EXCEL=24,PAYROLL_BCCI_EXCEL=25,PAYROLL_OCCCI_EXCEL=26,PAYROLL_DBP_EXCEL=27,
			PAYROLL_CFI_EXCEL=28,PAYROLL_SSS_LOAN_EXCEL=29,PAYROLL_PAGIBIG_LOAN_EXCEL=210,PAYROLL_ST_PETER_PLAN_EXCEL=211,
			PAYROLL_LBP_EXCEL=212,PAYROLL_UNION_DUES_EXCEL=213,PAYROLL_HDMF_EXCEL=214,PAYROLL_MEDICARE_EXCEL=215,
			PAYROLL_SSS_CONT_EXCEL=216,PAYROLL_W_TAX_EXCEL=217,
			
			PAYROLL_PER_CONTRACTUAL_EMPLOYEE_EXCEL=218;
	
	
	
	private void l_______________________________________________l(){}
	public final static int SETTING_DEPARTMENT=1,
			SETTING_PHIC_SALARY=2,
			SETTING_PHIC_RATE=3,
			SETTING_SSS=4,
			SETTING_ASEMCO_BCCI_OCCCI_DBP_CFI=5,
			SETTING_PAGIBIG=6, SETTING_ST_PETER=7, SETTING_UNION_DUES=8,
			SETTING_ECOLA=9, SETTING_LAUNDRY_ALLOWANCE=10,
			SETTING_LONGEVITY=11,SETTING_RICE=12,
			SETTING_CONTRACTUAL_RATE_PER_DAY=13;
	
	private void l_________________________________________________l(){}
	
	public final static String PHIC_STATUS_DYNAMIC="Dynamic",
			PHIC_STATUS_STATIC="Static";
	private void l____________________________________________________l(){}
	
	public final static int 
			CALCULATE_REGULAR_PAY_VALUE=11, 
			CALCULATE_OVERTIME_VALUE=22,
			CALCULATE_ECOLA_VALUE=33,
			CALCULATE_LAUNDRY_ALLOWANCE_VALUE=44,
			CALCULATE_LONGEVITY_VALUE=55,
			CALCULATE_RICE_VALUE=66,
			
			CALCULATE_RATE_PER_DAY_VALUE=77,
			CALCULATE_SUB_TOTAL_VALUE=88;
	
	private void l_______________________________________________________________l(){}
	
	public final static int 
			CALCULATE_SSS_VALUE=1111,
			CALCULATE_MEDICARE_VALUE=2222,
			CALCULATE_ASEMCO_BCCI_OCCCI_DBP_CFI_VALUE=3333,
			CALCULATE_PAGIBIG_VALUE=4444,CALCULATE_ST_PETER_VALUE=5555,
			CALCULATE_UNION_DUES_VALUE=6666;
	
	private void l_____________________________________________________l(){}
	//----------------------------Earning/Deduction COLUMN INDICES----------------------------------------
	public final static int 
			REG_PAY_COLUMN_INDEX=0,
			ECOLA_COLUMN_INDEX=4,
			LAUNDRY_ALLOWANCE_COLUMN_INDEX=6,
			LONGEVITY_COLUMN_INDEX=7,
			RICE_COLUMN_INDEX=8,
			
			CONTRACTUAL_RATE_PER_DAY_INDEX=1,
			CONTRACTUAL_SUB_TOTAL_INDEX=2;
	
	public final static int 
			SSS_CONT_COLUMN_INDEX=2, //<--> SSSEr
			PAGIBIG_COLUMN_INDEX=3, // <--> Pag-ibigEr
			MEDICARE_COLUMN_INDEX=4, // <--> MedicareEr
			UNION_DUES_COLUMN_INDEX=7,
	
	
			ASEMCO_COLUMN_INDEX=12,
			BCCI_COLUMN_INDEC=13,
			OCCCI_COLUMN_INDEX=14,
			DBP_COLUMN_INDEX=15,
			CFI_COLUMN_INDEX=16,
			
			ST_PETER_COLUMN_INDEX=17;
	
	private void l______________________________________________________________________________l(){}
	//-------------------------------BUTTON CONSTANT STRINGS----------------------------------------------------------------------

	public final static String
	
		//--> Option HOME View Panel
		CONNECT_TO_SERVER="Connect To Server", CONNECTIVITY_PARAMETERS="Connectivity Parameters",
		MANAGE_ACCOUNTS_BTN="Manage Accounts",
		
		//--> Option PAYROLL View Panel
		VIEW_EARNING_DATA="View Earning Data", VIEW_DEDUCTION_DATA="View Deduction Data",
		SHOW_PAYROLL_DATE="Show Payroll Date", EDIT_SIGNATURE_INFO="Edit Signature Info",ADD_COMMENTS_ON_PAYSLIP="Add comments on payslip",
		SHOW_PAYROLL_SUMMARY="Show Payroll Summary", SHOW_PAYROLL_DISPLAY_OPTIONS="Show Payroll Display Options",
		DISPLAY_PAYSLIP_PDF="Display Payslip PDF",DISPLAY_EMPLOYEE_PAYROLL_DATA_PDF="Display Employee Payroll Data PDF", DISPLAY_DEPARTMENT_PAYROLL_DATA_PDF="Display Department Payroll Data PDF",
		DISPLAY_ASEMCO_DATA_PDF="Display Asemco Data Pdf",DISPLAY_BCCI_DATA_PDF="Display BCCI Data Pdf",
		DISPLAY_OCCCI_DATA_PDF="Display OCCCI Data Pdf",DISPLAY_DBP_DATA_PDF="Display DBP Data Pdf",
		DISPLAY_CFI_DATA_PDF="Display CFI Data Pdf",DISPLAY_SSS_LOAN_DATA_PDF="Display SSS Loan Data Pdf",
		DISPLAY_PAGIBIG_LOAN_PDF="Display PAG-IBIG LOAN Data Pdf",DISPLAY_ST_PLAN_PDF="Display St. Plan Data Pdf",
		DISPLAY_LBP_PDF="Display Landbank of the Philippines Data Pdf",
		DISPLAY_UNION_DUES_PDF="Display Union Dues Data Pdf",DISPLAY_HDMF_PDF="Display HDMF Pdf",
		DISPLAY_SSS_CONT_PDF="Display SSS Contribution Pdf",DISPLAY_MEDICARE_PDF="Display Medicare Pdf",
		DISPLAY_W_TAX_PDF="Display Witholding Tax Pdf",
		
		EXPORT_EMPLOYEE_PAYROLL_DATA_EXCEL="EXPORT Employee Payroll Data  EXCEL",
		EXPORT_DEPARTMENT_PAYROLL_DATA_EXCEL ="EXPORT Department Payroll Data EXCEL ",
		EXPORT_ASEMCO_DATA_EXCEL ="EXPORT Asemco Data EXCEL ",EXPORT_BCCI_DATA_EXCEL ="EXPORT BCCI Data EXCEL ",
		EXPORT_OCCCI_DATA_EXCEL ="EXPORT OCCCI Data EXCEL ",EXPORT_DBP_DATA_EXCEL ="EXPORT DBP Data EXCEL",
		EXPORT_CFI_DATA_EXCEL ="EXPORT CFI Data EXCEL ",EXPORT_SSS_LOAN_DATA_EXCEL ="EXPORT SSS Loan Data EXCEL",
		EXPORT_PAGIBIG_LOAN_EXCEL ="EXPORT PAG-IBIG LOAN Data  EXCEL",EXPORT_ST_PLAN_EXCEL ="EXPORT St. Plan Data EXCEL ",
		EXPORT_LBP_EXCEL ="EXPORT Landbank of the Philippines Data EXCEL ",
		EXPORT_UNION_DUES_EXCEL ="EXPORT Union Dues Data EXCEL ",EXPORT_HDMF_EXCEL ="EXPORT HDMF EXCEL ",
		EXPORT_SSS_CONT_EXCEL ="EXPORT SSS Contribution EXCEL ",EXPORT_MEDICARE_EXCEL ="EXPORT Medicare EXCEL ",
		EXPORT_W_TAX_EXCEL ="EXPORT Witholding Tax EXCEL",
		
		
		
		//--> Option SETTINGS View Panel
		DEPARTMENT_SETTING_BTN="Department",PHIC_SALARY_RANGE_SETTINGS_BTN="PHIC Salary Range",
		PHIC_RATE_SETTING_BTN="PHIC Rate",SSS_SETTING_BTN="Sss",ASEMCO_SETTING_BTN="Asemco Occi Bcci DBP",
		PAGIBIG_SETTING_BTN="Pag-ibig",ST_PETER_SETTING_BTN="St. Peter", UNION_DUES_SETTING_BTN="Union Dues",
		ECOLA_SETTING_BTN="ECOLA", LAUNDRY_ALLOWANCE_SETTING_BTN="Laundry Allowance", 
		LONGEVITY_SETTING_BTN="Longevity", RICE_SETTING_BTN="Rice",
		DIVIDER_SETTING_BTN="Divider setting option",
		
		//--> Option SETTINGS Contractual View Panel
		RATE_PER_DAY_SETTING_BTN="Rate Per Day setting option",
		
		//--> Calculation DEDUCTION View Panel
		GENERATE_SSS_BTN="Generate SSS", GENERATE_PHILHEALTH_BTN="Generate PhilHealth",
		GENERATE_ASEMCO_BTN="Generate Asemco",GENERATE_PAGIBIG_BTN="Generate Pag-ibig",
		GENERATE_ST_PETER_BTN="Generate St. Peter",GENERATE_UNION_DUES_BTN="Generate Union Dues",
		
		//--> Calculation EARNING View Panel
		GENERATE_REGULAR_PAY_BTN="Generate Regular Pay",GENERATE_OVERTIME_BTN="Generate Overtime",
		GENERATE_ECOLA_BTN="Generate ECOLA",GENERATE_LAUNDRY_ALLOWAMCE_BTN="Generate Laundry Allowance",
		GENERATE_LONGEVITY_BTN="Generate Longevity",GENERATE_RICE_BTN="Generate Rice",
		
		//-> Calculation Contractual EARNING View Panel
		GENERATE_RATE_PER_DAY="Generate Rate Per Day", GENERATE_SUB_TOTAL="Generate Sub-Total",
		
		//--> User Account Option Buttons
		ACCOUNT_PREFEREBCES_BTN="Account Preferences", LOGOUT_BTN="Logout"
		
		;
	
	
	private void l____________________________________________________________l(){}
	//---------------------------------------- USER Authentication Levels----------------------------------------
		public final static int ADMIN_AUTHORIZATION_LEVEL=1,
				USER_AUTHORIZATION_LEVEL=2,
				GUEST_AUTHORIZATION_LEVEL=3;
		
		
	private void l__________________________________________________________________l(){}
	
	
	public Constant(){
	
	}
}
