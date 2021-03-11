package model.statics;


import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import view.LoadingScreen;
import view.MainFrame;




public class Images {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	private static Images instance;
	private String imageFolder;
	private void l_____________________________l (){}
	public ImageIcon 
		
		//--> Loading Screen
		loadingScreenBg,
	
		//--> Login Screen
		loginFrameBg,loginBtnImg,loginBtnHoverImg,
		
		//--> Mainframe View Panel Images
		mainFrameBg,bilecoLogoImg,bilecoLogoHoverImg,userDropDownImg,userDropDownHoverImg,
		userNameUser,userNameAdmin,userNameGuest,
	
		menuBg,optionBtnImg,optionHoverBtnImg,
		homeMenuImg,homeMenuHoverImg,
		employeeMenuImg,employeeMenuHoverImg,
		payrollMenuBtnImg,payrollMenuBtnHoverImg,
		settingsMenuBtnImg,settingsMenuHoverBtnImg,
		helpMenuBtnImg,helpMenuHoverBtnImg,
		
		userAccountPanelBg, accountPreferencesBtnImg, accountPreferencesBtnImgHover, logoutBtnImg,logoutBtnImgHover,
		
		payrollModeBg,regularPayrollModeImg, contractualPayrollModeImg, selectBtnDialogImg,selectBtnDialogImgHover,
		
		//--> Home View Panel Images
		homeViewPanelBgImg,homeViewTitleImg,homeWelcomeMsgImg,homeWelcomeMsgRegularImg,homeWelcomeMsgContractualImg,
		homeOptionPanelBG, manageAccountsBtnImg, manageAccountsBtnImgHover,
		connectToServerImg, connectToServerImgHover, connectivityParametersImg, connectivityParametersImgHover,
		
		
		//--> Employee View Panel Images
		employeeViewPanelBgImg,employeeViewTitleImg,searchBarImg,searchBtnImg,
			searchBtnHoverImg,employeeOptionBgImg,addEmployeeImg,addEmployeeHoverImg,
			showEmployeeSummaryImg,showEmployeeSummaryHoverImg,updateDeleteImg,
			updateDeleteHoverImg, saveBtnDialogImg, saveBtnDialogImgHover,
			cancelBtnDialogImg, cancelBtnDialogImgHover,
			editBtnDialogImg, editBtnDialogImgHover,
			backBtnDialogImg, backBtnDialogImgHover,
			updateBtnDialogImg, updateBtnDialogImgHover,
			deleteBtnDialogImg, deleteBtnDialogImgHover,
			yesBtnDialogImg,yesBtnDialogImgHover,
			noBtnDialogImg,noBtnDialogImgHover,
			calculateBtnDialogImg,calculateBtnDialogImgHover,
			addBtnDialogImg,addBtnDialogImgHover,
			generateBtnImg, generateBtnImgHover,
		
		//--> Payroll View Panel Images
		payrollViewPanelBgImg,payrollViewTitleImg,
			payrollOptionsBgImg,pdfOptionsBgImg,pdfOptionsContractualBgImg,excelOptionsBgImg,excelOptionsContractualBgImg,
			netPayCopyModeImg,netPayCopyModeImgHover,
			cancelNetPayCopyModeImg,cancelNetPayCopyModeImgHover,
			showPayslipDialogBtnImg, showPayslipDialogBtnImgHover,
			viewEarningDataImg,viewEarningDataImgHover,
			viewDeductionDataImg,viewDeductionDataImgHover,
			showPayrollDateImg, showPayrollDateImgHover,
			showPayrollSummaryImg,showPayrollSummaryImgHover,
			showPayrollDisplayOptionsImg,showPayrollDisplayOptionsImgHover,
			editSignatureInfoImg, editSignatureInfoImgHover,
			addCommentsToPayslipImg, addCommentsToPayslipImgHover,
			showPDFPayrollImg, showPDFPayrollImgHover,
			showExcelPayrollImg, showExcelPayrollImgHover,
			
			displayPayslipPDFImg,displayPayslipPDFImgHover,
			displayEmployeePayrollDataPDFImg,displayEmployeePayrollDataPDFImgHover,
			displayDepartmentPayrollDataPDFImg,displayDepartmentPayrollDataPDFImgHover,
			displayASEMCOPayrollDataPDFImg, displayASEMCOPayrollDataPDFImgHover,
			displayBCCIPayrollDataPDFImg, displayBCCIPayrollDataPDFImgHover,
			displayOCCCIPayrollDataPDFImg, displayOCCCIPayrollDataPDFImgHover,
			displayDBPPayrollDataPDFImg, displayDBPPayrollDataPDFImgHover,
			displayCFIPayrollDataPDFImg,displayCFIPayrollDataPDFImgHover,
			displaySSSLoanPayrollDataPDFImg,displaySSSLoanPayrollDataPDFImgHover,
			displayPagibigLoanPayrollDataPDFImg, displayPagibigLoanPayrollDataPDFImgHover,
			displayStPlanPayrollDataPDFImg, displayStPlanPayrollDataPDFImgHover,
			displayLBPPayrollDataPDFImg, displayLBPPayrollDataPDFImgHover,
			displayUnionDuesPayrollDataPDFImg, displayUnionDuesPayrollDataPDFImgHover,
			displayHDMFPayrollDataPDFImg,displayHDMFPayrollDataPDFImgHover,
			displaySssContPayrollDataPDFImg, displaySssContPayrollDataPDFImgHover,
			displayMedicarePayrollDataPDFImg, displayMedicarePayrollDataPDFImgHover,
			displayWTaxPayrollDataPDFImg, displayWTaxPayrollDataPDFImgHover,
			
			showDisplayPanelBG,showButtonImg,showButtonImgHover, 
			pdfImgBtn,pdfImgBtnHover,
			excelImgBtn,excelImgBtnHover,
			lockImgBtn, lockImgBtnHover,
			payrollDATELabelImg,payrollDATALabelImg,
			
			exportEmployeePayrollDataExcelImg,exportEmployeePayrollDataExcelImgHover,
			exportDepartmentPayrollDataExcelImg,exportDepartmentPayrollDataExcelImgHover,
			exportASEMCOPayrollDataExcelImg, exportASEMCOPayrollDataExcelImgHover,
			exportBCCIPayrollDataExcelImg, exportBCCIPayrollDataExcelImgHover,
			exportOCCCIPayrollDataExcelImg, exportOCCCIPayrollDataExcelImgHover,
			exportDBPPayrollDataExcelImg, exportDBPPayrollDataExcelImgHover,
			exportCFIPayrollDataExcelImg,exportCFIPayrollDataExcelImgHover,
			exportSSSLoanPayrollDataExcelImg,exportSSSLoanPayrollDataExcelImgHover,
			exportPagibigLoanPayrollDataExcelImg, exportPagibigLoanPayrollDataExcelImgHover,
			exportStPlanPayrollDataExcelImg, exportStPlanPayrollDataExcelImgHover,
			exportLBPPayrollDataExcelImg, exportLBPPayrollDataExcelImgHover,
			exportUnionDuesPayrollDataExcelImg, exportUnionDuesPayrollDataExcelImgHover,
			exportHDMFPayrollDataExcelImg,exportHDMFPayrollDataExcelImgHover,
			exportSssContPayrollDataExcelImg, exportSssContPayrollDataExcelImgHover,
			exportMedicarePayrollDataExcelImg, exportMedicarePayrollDataExcelImgHover,
			exportWTaxPayrollDataExcelImg, exportWTaxPayrollDataExcelImgHover,
		
		//--> BOTH Earning and Deduction Panel Images
		bothDataOptionsPanelImg,showDispOptionGroupIIIBgImg,
//			showAllEmployeeBothDataImg,showAllEmployeeBothDataImgHover,
//			showAllBothDataImg,showAllBothDataImgHover,
//			showDisplayOptionsImg,showDisplayOptionsImgHover,
			edBackBtnImg,edBackBtnImgHover,
			edAddImg,edAddImgHover, edCancelImg,edCancelImgHover, edDeleteImg,edDeleteImgHover, edEditImg, edEditImgHover, edSaveImg, edSaveImgHover,
			edCalcIconImg, edCalcIconImgHover,edRetrievePrevDataIconImg, edRetrievePrevDataIconImgHover,
			
		//--> Earning View Panel Images
		earningViewTitleImg,earningShowAllDataImg,earningShowAllDataImgHover,
			earningRegularCalculateOptionsImg,
			generateRegularPayImg,generateRegularPayImgHover,
			generateOvertimeImg,generateOvertimeImgHover,
			generateEcolaImg, generateEcolaImgHover,
			generateLaundryAllowanceImg, generateLaundryAllowanceImgHover,
			generateLongevityImg, generateLongevityImgHover,
			generateRiceImg, generateRiceImgHover,
			
			earningContractualCalculateOptionsImg,
			generateRatePerDayImg, generateRatePerDayImgHover,
			generateSubTotalImg, generateSubTotalImgHover,
		
		//--> Deduction View Panel Images
		deductionViewTitleImg,deductionShowAllDataImg,deductionShowAllDataImgHover,
			deductionCalculateOptions, deductionContractualCalculateOptionImg,
			showEmployerShareImg, showEmployerShareImgHover,
			hideEmployerShareImg,hideEmployerShareImgHover,
			generateSSSImg,generateSSSImgHover,
			generatePhilhealthImg,generatePhilhealthImgHover,
			generateAsemco, generateAsemcoHover,
			generatePagibig,generatePagibigHover,
			generateStPeter,generateStPeterHover,
			generateUnionDues,generateUnionDuesHover,
					
				
		//--> Settings
		settingViewTitleImg, 
			
			settingsOptionPanelBgImg,
			departmentSettingImg,departmentSettingImgHover,
			phicSalaryRangfeImg,phicSalaryRangfeImgHover,
			phicRateImg,phicRateImgHover,
			sssImg,sssImgHover,
			asemcoOcciBCCDbpImg,asemcoOcciBCCDbpImgHover,
			pagibigImg,pagibigImgHover,
			stPeterImg,stPeterImgHover,
			unionDuesImg, unionDuesImgHover,
			ecolaOptionSettingImg,ecolaOptionSettingImgHover,
			laundryAllowanceOptionSettingImg, laundryAllowanceOptionSettingImgHover,
			longevityOptionSettingImg, longevityOptionSettingImgHover,
			riceOptionSettingImg, riceOptionSettingImgHover,
			asemcoOptionTitleImg,departmentOptionTitleImg, pagibigOptionTitleImg, 
			phicRateOptionTitleImg,phicSalaryRangeOptionTitleImg, sssOptionTitleImg,
			stPeterOptionTitleImg,unionDuesOptionTitleImg,ecolaOptionTitleImg,
			laundryAllowanceOptionTitleImg, longevityOptonTitleImg,
			riceOptionTitleImg, 
			dividerOptionImg,
			
			settingsContractualOptionPanelBgImg,
			contractualRatePerDayImg, contractualRatePerDayImgHover,
			contractualRatePerDayTitleImg,
			
			
		//--> HELP;
		helpViewPanelBG;	
	private void l___________________________l (){}
	
	public Images(){
		imageFolder="images/";
		
		System.out.println(">>>>>>>> Images initialize.."+CLASS_NAME);
		
		initLoadingScreenImages();
		initLoginFrameImages(); 
		initMainFrameImages();
		initHomeViewPanelImages();
		initEmployeeViewPanelImages();
		initPayrollViewPanelImages();
		initBothEarningDeductionImages();
		initEarningViewPanelImages();
		initDeductionViewPanelImages();
		initSettingsViewPanelImages();
		initHelpViewPanelImages();
		
		System.out.println(">>>>>>>> Images initialize..DONE"+CLASS_NAME);
		
	}
	private void l________________________________________l (){}
	
	/**
	 * INitialize loading screen images
	 */
	private void initLoadingScreenImages(){
		String loadingFolder="loading/";
		
		loadingScreenBg=newImageIcon(imageFolder+loadingFolder+"LoardingScreen.png");

	}
	/**
	 * Initialize Login Frame Images.
	 */
	private void initLoginFrameImages(){
		String loginFolder="login/";
		
		loginFrameBg=newImageIcon(imageFolder+loginFolder+"LoginFrameDesign.png");
		loginBtnImg=newImageIcon(imageFolder+loginFolder+"LoginButton.png");
		loginBtnHoverImg=newImageIcon(imageFolder+loginFolder+"LoginButtonHover.png");

	}
	
	private void initMainFrameImages(){
//		homeViewPanelBgImg=newImageIcon(imageFolder+mainFolder+"HomeViewGroup.png");
		String mainFolder="main/";
		
		mainFrameBg=newImageIcon(imageFolder+mainFolder+"MainFrameDesign.png");
		bilecoLogoImg=newImageIcon(imageFolder+mainFolder+"BILECOLogo.png");
		bilecoLogoHoverImg=newImageIcon(imageFolder+mainFolder+"BILECOLogoHover.png");
		
		optionBtnImg=newImageIcon(imageFolder+mainFolder+"OptionIcon.png");
		optionHoverBtnImg=newImageIcon(imageFolder+mainFolder+"OptionIconHover.png");
		userDropDownImg=newImageIcon(imageFolder+mainFolder+"UserDropDownIcon.png");
		userDropDownHoverImg=newImageIcon(imageFolder+mainFolder+"UserDropDownIconHover.png");
		
		userNameAdmin=newImageIcon(imageFolder+mainFolder+"Admin.png");
		userNameUser=newImageIcon(imageFolder+mainFolder+"User.png");
		userNameGuest=newImageIcon(imageFolder+mainFolder+"Guest.png");
		
		
		menuBg=newImageIcon(imageFolder+mainFolder+"LeftMenuGroup.png");
		
		homeMenuImg=newImageIcon(imageFolder+mainFolder+"HomeGroup.png");
		homeMenuHoverImg=newImageIcon(imageFolder+mainFolder+"HomeGroupHover.png");
		employeeMenuImg=newImageIcon(imageFolder+mainFolder+"EmployeeGroup.png");
		employeeMenuHoverImg=newImageIcon(imageFolder+mainFolder+"EmployeeGroupHover.png");
		payrollMenuBtnImg=newImageIcon(imageFolder+mainFolder+"PayrollGroup.png");
		payrollMenuBtnHoverImg=newImageIcon(imageFolder+mainFolder+"PayrollGroupHover.png");
		settingsMenuBtnImg=newImageIcon(imageFolder+mainFolder+"SettingsGroup.png");
		settingsMenuHoverBtnImg=newImageIcon(imageFolder+mainFolder+"SettingsGroupHover.png");
		helpMenuBtnImg=newImageIcon(imageFolder+mainFolder+"HelpMenuGroup.png");
		helpMenuHoverBtnImg=newImageIcon(imageFolder+mainFolder+"HelpMenuGroupHover.png");
		
		
		userAccountPanelBg=newImageIcon(imageFolder+mainFolder+"UserInfoGroup.png");
		accountPreferencesBtnImg=newImageIcon(imageFolder+mainFolder+"AccountPreferences.png");
		accountPreferencesBtnImgHover=newImageIcon(imageFolder+mainFolder+"AccountPreferencesHover.png");
		logoutBtnImg=newImageIcon(imageFolder+mainFolder+"Logout.png");
		logoutBtnImgHover=newImageIcon(imageFolder+mainFolder+"LogoutHover.png");
		
		payrollModeBg=newImageIcon(imageFolder+mainFolder+"PayrollSystemModeDialogBox.png");
		regularPayrollModeImg=newImageIcon(imageFolder+mainFolder+"Regular.png");
		contractualPayrollModeImg=newImageIcon(imageFolder+mainFolder+"Contractual.png");
		selectBtnDialogImg=newImageIcon(imageFolder+mainFolder+"SelectBtnGroup.png");
		selectBtnDialogImgHover=newImageIcon(imageFolder+mainFolder+"SelectBtnGroupHover.png");
	}
	
	private void initHomeViewPanelImages(){
		String homeFolder="home/";
//		homeViewPanelBgImg=newImageIcon(imageFolder+homeFolder+"HomeViewGroup.png");
		
		homeViewTitleImg=newImageIcon(imageFolder+homeFolder+"HomeViewTitle.png");
		homeWelcomeMsgImg=newImageIcon(imageFolder+homeFolder+"HomeWelcomeMsg.png");
		homeWelcomeMsgRegularImg=newImageIcon(imageFolder+homeFolder+"HomeWelcomeMsgRegular.png");
		homeWelcomeMsgContractualImg=newImageIcon(imageFolder+homeFolder+"HomeWelcomeMsgContractual.png");
		
		
		homeOptionPanelBG=newImageIcon(imageFolder+homeFolder+"HomeOptionsGroup.png");
		connectToServerImg=newImageIcon(imageFolder+homeFolder+"ConnectToServer.png");
		connectToServerImgHover=newImageIcon(imageFolder+homeFolder+"ConnectToServerHover.png");
		connectivityParametersImg=newImageIcon(imageFolder+homeFolder+"ConnectivityParameters.png");
		connectivityParametersImgHover=newImageIcon(imageFolder+homeFolder+"ConnectivityParametersHover.png");
		manageAccountsBtnImg=newImageIcon(imageFolder+homeFolder+"ManageAccounts.png");
		manageAccountsBtnImgHover=newImageIcon(imageFolder+homeFolder+"ManageAccountsHover.png");
		
	}
	
	private void initEmployeeViewPanelImages(){
		String empoloyeeFolder="employee/";
		
//		employeeViewPanelBgImg=newImageIcon(imageFolder+employeeFolder+"EmployeeViewGroup.png");
		employeeViewTitleImg=newImageIcon(imageFolder+empoloyeeFolder+"EmployeeViewTitle.png");
		
		searchBarImg=newImageIcon(imageFolder+empoloyeeFolder+"EmployeeSearchBar.png");
		searchBtnImg=newImageIcon(imageFolder+empoloyeeFolder+"SearchIcon.png");
		searchBtnHoverImg=newImageIcon(imageFolder+empoloyeeFolder+"SearchIconHover.png");
		
		
		employeeOptionBgImg=newImageIcon(imageFolder+empoloyeeFolder+"EmployeeOptionsGroup.png");
		
		addEmployeeImg=newImageIcon(imageFolder+empoloyeeFolder+"AddEmployee.png");
		addEmployeeHoverImg=newImageIcon(imageFolder+empoloyeeFolder+"AddEmployeeHover.png");
		showEmployeeSummaryImg=newImageIcon(imageFolder+empoloyeeFolder+"ShowSummary.png");
		showEmployeeSummaryHoverImg=newImageIcon(imageFolder+empoloyeeFolder+"ShowSummaryHover.png");
		updateDeleteImg=newImageIcon(imageFolder+empoloyeeFolder+"UpdateDelete.png");
		updateDeleteHoverImg=newImageIcon(imageFolder+empoloyeeFolder+"UpdateDeleteHover.png");
		
		saveBtnDialogImg=newImageIcon(imageFolder+empoloyeeFolder+"SaveButtonGroup.png");
		saveBtnDialogImgHover=newImageIcon(imageFolder+empoloyeeFolder+"SaveButtonGroupHover.png");
		cancelBtnDialogImg=newImageIcon(imageFolder+empoloyeeFolder+"CancelButtonGroup.png");
		cancelBtnDialogImgHover=newImageIcon(imageFolder+empoloyeeFolder+"CancelButtonGroupHover.png");
		editBtnDialogImg=newImageIcon(imageFolder+empoloyeeFolder+"EditButtonDialogGroup.png");
		editBtnDialogImgHover=newImageIcon(imageFolder+empoloyeeFolder+"EditButtonDialogGroupHover.png");
		backBtnDialogImg=newImageIcon(imageFolder+empoloyeeFolder+"BackButtonDialogGroup.png");
		backBtnDialogImgHover=newImageIcon(imageFolder+empoloyeeFolder+"BackButtonDialogGroupHover.png");
		updateBtnDialogImg=newImageIcon(imageFolder+empoloyeeFolder+"UpdateButtonDialogGroup.png");
		updateBtnDialogImgHover=newImageIcon(imageFolder+empoloyeeFolder+"UpdateButtonDialogGroupHover.png");
		deleteBtnDialogImg=newImageIcon(imageFolder+empoloyeeFolder+"DeleteButtonDialogGroup.png");
		deleteBtnDialogImgHover=newImageIcon(imageFolder+empoloyeeFolder+"DeleteButtonDialogGroupHover.png");
		yesBtnDialogImg=newImageIcon(imageFolder+empoloyeeFolder+"YesButtonDialogGroup.png");
		yesBtnDialogImgHover=newImageIcon(imageFolder+empoloyeeFolder+"YesButtonDialogGroupHover.png");
		noBtnDialogImg=newImageIcon(imageFolder+empoloyeeFolder+"NoButtonDialogGroup.png");
		noBtnDialogImgHover=newImageIcon(imageFolder+empoloyeeFolder+"NoButtonDialogGroupHover.png");
		
		//		generateBtnImg, generateBtnImgHover,
		generateBtnImg=newImageIcon(imageFolder+empoloyeeFolder+"GenerateButtonGroup.png");
		generateBtnImgHover=newImageIcon(imageFolder+empoloyeeFolder+"GenerateButtonGroupHover.png");

		
	}
	
	private void initPayrollViewPanelImages(){
		String payrollFolder="payroll/";
//		payrollViewPanelBgImg=newImageIcon(imageFolder+payrollFolder+"PayrollViewGroup.png");
		
		payrollViewTitleImg=newImageIcon(imageFolder+payrollFolder+"PayrollViewTitle.png");
		payrollOptionsBgImg=newImageIcon(imageFolder+payrollFolder+"PayrollOptionsGroup.png");
		pdfOptionsBgImg=newImageIcon(imageFolder+payrollFolder+"PDFOptionsGroup.png");
		pdfOptionsContractualBgImg=newImageIcon(imageFolder+payrollFolder+"PDFContractualOptionsGroup.png");
		excelOptionsBgImg=newImageIcon(imageFolder+payrollFolder+"ExcelOptionsGroup.png");
		excelOptionsContractualBgImg=newImageIcon(imageFolder+payrollFolder+"ExcelContractualOptionsGroup.png");
		
		
		payrollDATELabelImg=newImageIcon(imageFolder+payrollFolder+"PayrollDateLabel.png");
		payrollDATALabelImg=newImageIcon(imageFolder+payrollFolder+"PayrollDataLabel.png");
		//------------------------
		netPayCopyModeImg=newImageIcon(imageFolder+payrollFolder+"EditNetPayBtnGroup.png");
		netPayCopyModeImgHover=newImageIcon(imageFolder+payrollFolder+"EditNetPayBtnGroupHover.png");
		cancelNetPayCopyModeImg=newImageIcon(imageFolder+payrollFolder+"CancelNetPayBtnGroup.png");
		cancelNetPayCopyModeImgHover=newImageIcon(imageFolder+payrollFolder+"CancelNetPayBtnGroupHover.png");
		
		showPayslipDialogBtnImg=newImageIcon(imageFolder+payrollFolder+"ShowPayslipDialogBtnGroup.png");
		showPayslipDialogBtnImgHover=newImageIcon(imageFolder+payrollFolder+"ShowPayslipDialogBtnGroupHover.png");
		
		
		//------------------------
		viewEarningDataImg=newImageIcon(imageFolder+payrollFolder+"ViewEarningData.png");
		viewEarningDataImgHover=newImageIcon(imageFolder+payrollFolder+"ViewEarningDataHover.png");
		viewDeductionDataImg=newImageIcon(imageFolder+payrollFolder+"ViewDeductionData.png");
		viewDeductionDataImgHover=newImageIcon(imageFolder+payrollFolder+"ViewDeductionDataHover.png");		
		showPayrollDateImg=newImageIcon(imageFolder+payrollFolder+"ShowPayrollDate.png");
		showPayrollDateImgHover=newImageIcon(imageFolder+payrollFolder+"ShowPayrollDateHover.png");
		showPayrollSummaryImg=newImageIcon(imageFolder+payrollFolder+"ShowPayrollSummary.png");
		showPayrollSummaryImgHover=newImageIcon(imageFolder+payrollFolder+"ShowPayrollSummaryHover.png");
		showPayrollDisplayOptionsImg=newImageIcon(imageFolder+payrollFolder+"ShowPayrollDisplayOptions.png");
		showPayrollDisplayOptionsImgHover=newImageIcon(imageFolder+payrollFolder+"ShowPayrollDisplayOptionsHover.png");
		editSignatureInfoImg=newImageIcon(imageFolder+payrollFolder+"EditSignatureInfo.png");
		editSignatureInfoImgHover=newImageIcon(imageFolder+payrollFolder+"EditSignatureInfoHover.png");
		addCommentsToPayslipImg=newImageIcon(imageFolder+payrollFolder+"AddNotes.png");
		addCommentsToPayslipImgHover=newImageIcon(imageFolder+payrollFolder+"AddNotesHover.png");
		showPDFPayrollImg=newImageIcon(imageFolder+payrollFolder+"ShowPDFButtonDialog.png");
		showPDFPayrollImgHover=newImageIcon(imageFolder+payrollFolder+"ShowPDFButtonDialogHover.png");
		showExcelPayrollImg=newImageIcon(imageFolder+payrollFolder+"ShowExcelButtonDialogGroup.png");
		showExcelPayrollImgHover=newImageIcon(imageFolder+payrollFolder+"ShowExcelButtonDialogGroupHover.png");
		
		//------------------------
		displayPayslipPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayPayslipPDF.png");
		displayPayslipPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayPayslipPDFHover.png");
		displayEmployeePayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayEmployeePayrollDataPDF.png");
		displayEmployeePayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayEmployeePayrollDataPDFHover.png");
		displayDepartmentPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayDepartmentPayrollDataPDF.png");
		displayDepartmentPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayDepartmentPayrollDataPDFHover.png");
		displayASEMCOPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayAsemcoPayrollDataPDF.png");
		displayASEMCOPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayAsemcoPayrollDataPDFHover.png");
		displayBCCIPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayBcciPayrollDataPDF.png");
		displayBCCIPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayBcciPayrollDataPDFHover.png");
		displayOCCCIPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayOccciPayrollDataPDF.png");
		displayOCCCIPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayOccciPayrollDataPDFHover.png");
		displayDBPPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayDbpPayrollDataPDF.png");
		displayDBPPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayDbpPayrollDataPDFHover.png");
		displayCFIPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayCfiPayrollDataPDF.png");
		displayCFIPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayCfiPayrollDataPDFHover.png");
		displaySSSLoanPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplaySSSLoanPayrollDataPDF.png");
		displaySSSLoanPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplaySSSLoanPayrollDataPDFHover.png");
		displayPagibigLoanPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayPagibigLoanPayrollDataPDF.png");
		displayPagibigLoanPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayPagibigLoanPayrollDataPDFHover.png");
		displayStPlanPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayStPlanPayrollDataPDF.png");
		displayStPlanPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayStPlanPayrollDataPDFHover.png");
		displayLBPPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayLBPPayrollDataPDF.png");
		displayLBPPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayLBPPayrollDataPDFHover.png");
		displayUnionDuesPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayUnionDuesPayrollDataPDF.png");
		displayUnionDuesPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayUnionDuesPayrollDataPDFHover.png");
		displayHDMFPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayHDMFPayrollDataPDF.png");
		displayHDMFPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayHDMFPayrollDataPDFHover.png");
		displaySssContPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplaySSSContPayrollDataPDF.png");
		displaySssContPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplaySSSContPayrollDataPDFHover.png");
		displayMedicarePayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayMedicarePayrollDataPDF.png");
		displayMedicarePayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayMedicarePayrollDataPDFHover.png");
		displayWTaxPayrollDataPDFImg=newImageIcon(imageFolder+payrollFolder+"DisplayWTaxPayrollDataPDF.png");
		displayWTaxPayrollDataPDFImgHover=newImageIcon(imageFolder+payrollFolder+"DisplayWTaxPayrollDataPDFHover.png");
		
		//------------------------
		showDisplayPanelBG=newImageIcon(imageFolder+payrollFolder+"ShowDisplayOptionPanelBg.png");
		showButtonImg=newImageIcon(imageFolder+payrollFolder+"ShowButtonGroup.png");
		showButtonImgHover=newImageIcon(imageFolder+payrollFolder+"ShowButtonGroupHover.png");
		pdfImgBtn=newImageIcon(imageFolder+payrollFolder+"PdfBtn.png");
		pdfImgBtnHover=newImageIcon(imageFolder+payrollFolder+"PdfBtnHover.png");
		excelImgBtn=newImageIcon(imageFolder+payrollFolder+"ExcelBtn.png");
		excelImgBtnHover=newImageIcon(imageFolder+payrollFolder+"ExcelBtnHover.png");
		lockImgBtn=newImageIcon(imageFolder+payrollFolder+"LockButton.png");
		lockImgBtnHover=newImageIcon(imageFolder+payrollFolder+"LockButtonHover.png");
		
		//------------------------

		exportASEMCOPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportAsemcoPayrollDataEXCEL.png");
		exportASEMCOPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportAsemcoPayrollDataEXCELHover.png");
		exportBCCIPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportBcciPayrollDataEXCEL.png");
		exportBCCIPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportBcciPayrollDataEXCELHover.png");
		exportCFIPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportCfiPayrollDataEXCEL.png");
		exportCFIPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportCfiPayrollDataEXCELHover.png");
		exportDBPPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportDbpPayrollDataEXCEL.png");
		exportDBPPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportDbpPayrollDataEXCELHover.png");
		exportDepartmentPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportDepartmentPayrollDataEXCEL.png");
		exportDepartmentPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportDepartmentPayrollDataEXCELHover.png");
		exportEmployeePayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportEmployeePayrollDataEXCEL.png");
		exportEmployeePayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportEmployeePayrollDataEXCELHover.png");
		exportHDMFPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportHDMFPayrollDataEXCEL.png");
		exportHDMFPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportHDMFPayrollDataEXCELHover.png");
		exportLBPPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportLBPPayrollDataEXCEL.png");
		exportLBPPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportLBPPayrollDataEXCELHover.png");
		exportMedicarePayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportMedicarePayrollDataEXCEL.png");
		exportMedicarePayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportMedicarePayrollDataEXCELHover.png");
		exportOCCCIPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportOccciPayrollDataEXCEL.png");
		exportOCCCIPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportOccciPayrollDataEXCELHover.png");
		exportPagibigLoanPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportPagibigLoanPayrollDataEXCEL.png");
		exportPagibigLoanPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportPagibigLoanPayrollDataEXCELHover.png");
		exportSssContPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportSSSContPayrollDataEXCEL.png");
		exportSssContPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportSSSContPayrollDataEXCELHover.png");
		exportSSSLoanPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportSSSLoanPayrollDataEXCEL.png");
		exportSSSLoanPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportSSSLoanPayrollDataEXCELHover.png");
		exportStPlanPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportStPlanPayrollDataEXCEL.png");
		exportStPlanPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportStPlanPayrollDataEXCELHover.png");
		exportUnionDuesPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportUnionDuesPayrollDataEXCEL.png");
		exportUnionDuesPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportUnionDuesPayrollDataEXCELHover.png");
		exportWTaxPayrollDataExcelImg=newImageIcon(imageFolder+payrollFolder+"ExportWTaxPayrollDataEXCEL.png");
		exportWTaxPayrollDataExcelImgHover=newImageIcon(imageFolder+payrollFolder+"ExportWTaxPayrollDataEXCELHover.png");
		
		

		//------------------------
		calculateBtnDialogImg=newImageIcon(imageFolder+payrollFolder+"CalculateButtonDialogGroup.png");
		calculateBtnDialogImgHover=newImageIcon(imageFolder+payrollFolder+"CalculateButtonDialogGroupHover.png");
		addBtnDialogImg=newImageIcon(imageFolder+payrollFolder+"AddButtonDialogGroup.png");
		addBtnDialogImgHover=newImageIcon(imageFolder+payrollFolder+"AddButtonDialogGroupHover.png");
		
		
	}
	
	private void initBothEarningDeductionImages(){
//		earningViewPanelBgImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"PayrollViewGroup.png");
		String bothEarningDeductionFolder="earningdeduction/";
	
		
		bothDataOptionsPanelImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"BothEarningDeductionOptionsGroup.png");
		showDispOptionGroupIIIBgImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"ShowDisplayOptionsGroupIII.png");
		
		
		
//		showAllEmployeeBothDataImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"ShowAllEmployee.png");
//		showAllEmployeeBothDataImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"ShowAllEmployeeHover.png");
//		showAllBothDataImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"ShowAllBOTHData.png");
//		showAllBothDataImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"ShowAllBOTHDataHover.png");
//		showDisplayOptionsImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"ShowDisplayOptions.png");
//		showDisplayOptionsImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"ShowDisplayOptionsHover.png");
		
		edBackBtnImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"BackButtonGroup.png");
		edBackBtnImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"BackButtonGroupHover.png");
		
		
		edAddImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"AddButtonGroup.png");
		edAddImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"AddButtonGroupHover.png");
		edCancelImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"CancelButtonGroup.png");
		edCancelImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"CancelButtonGroupHover.png");
		edDeleteImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"DeleteButtonGroup.png");
		edDeleteImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"DeleteButtonGroupHover.png");
		edEditImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"EditButtonGroup.png");
		edEditImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"EditButtonGroupHover.png");
		edSaveImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"SaveButtonGroup.png");
		edSaveImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"SaveButtonGroupHover.png");
		edCalcIconImg=newImageIcon(imageFolder+bothEarningDeductionFolder+"CalculateIconGroup.png");
		edCalcIconImgHover=newImageIcon(imageFolder+bothEarningDeductionFolder+"CalculateIconGroupHover.png");
		
		
		
	}
	private void initEarningViewPanelImages(){
//		earningViewPanelBgImg=newImageIcon(imageFolder+earningFolder+"PayrollViewGroup.png");
		String earningFolder="earning/";
		
		earningViewTitleImg=newImageIcon(imageFolder+earningFolder+"EarningViewTitle.png");
		
		earningShowAllDataImg=newImageIcon(imageFolder+earningFolder+"ShowAllEarningData.png");
		earningShowAllDataImgHover=newImageIcon(imageFolder+earningFolder+"ShowAllEarningDataHover.png");
		
		earningRegularCalculateOptionsImg=newImageIcon(imageFolder+earningFolder+"EarningCalculateOptionsGroup.png");
		generateRegularPayImg=newImageIcon(imageFolder+earningFolder+"GenerateRegularPay.png");
		generateRegularPayImgHover=newImageIcon(imageFolder+earningFolder+"GenerateRegularPayHover.png");
		generateOvertimeImg=newImageIcon(imageFolder+earningFolder+"GenerateOvertime.png");
		generateOvertimeImgHover=newImageIcon(imageFolder+earningFolder+"GenerateOvertimeHover.png");
		generateEcolaImg=newImageIcon(imageFolder+earningFolder+"GenerateEcola.png");
		generateEcolaImgHover=newImageIcon(imageFolder+earningFolder+"GenerateEcolaHover.png");
		generateLaundryAllowanceImg=newImageIcon(imageFolder+earningFolder+"GenerateLaundryAllowance.png");
		generateLaundryAllowanceImgHover=newImageIcon(imageFolder+earningFolder+"GenerateLaundryAllowanceHover.png");
		generateLongevityImg=newImageIcon(imageFolder+earningFolder+"GenerateLongevity.png");
		generateLongevityImgHover=newImageIcon(imageFolder+earningFolder+"GenerateLongevityHover.png");
		generateRiceImg=newImageIcon(imageFolder+earningFolder+"GenerateRice.png");
		generateRiceImgHover=newImageIcon(imageFolder+earningFolder+"GenerateRiceHover.png");
		
		
		earningContractualCalculateOptionsImg=newImageIcon(imageFolder+earningFolder+"EarningContractualCalculateOptionsGroup.png");
		generateRatePerDayImg=newImageIcon(imageFolder+earningFolder+"GenerateRatePerDay.png");
		generateRatePerDayImgHover=newImageIcon(imageFolder+earningFolder+"GenerateRatePerDayHover.png");
		generateSubTotalImg=newImageIcon(imageFolder+earningFolder+"GenerateSubTotal.png");
		generateSubTotalImgHover=newImageIcon(imageFolder+earningFolder+"GenerateSubTotalHover.png");
		
	}
	
	private void initDeductionViewPanelImages(){
//		earningViewPanelBgImg=newImageIcon(imageFolder+deductionFolder+"PayrollViewGroup.png");
		String deductionFolder="deduction/";
		
		deductionViewTitleImg=newImageIcon(imageFolder+deductionFolder+"DeductionViewTitle.png");
		
		deductionShowAllDataImg=newImageIcon(imageFolder+deductionFolder+"ShowAllDeductionData.png");
		deductionShowAllDataImgHover=newImageIcon(imageFolder+deductionFolder+"ShowAllDeductionDataHover.png");
		
		showEmployerShareImg=newImageIcon(imageFolder+deductionFolder+"ShowEmployerShareButtonGroup.png");
		showEmployerShareImgHover=newImageIcon(imageFolder+deductionFolder+"ShowEmployerShareButtonGroupHover.png");
		hideEmployerShareImg=newImageIcon(imageFolder+deductionFolder+"HideEmployerShareButtonGroup.png");
		hideEmployerShareImgHover=newImageIcon(imageFolder+deductionFolder+"HideEmployerShareButtonGroupHover.png");
		
		
		
		deductionCalculateOptions=newImageIcon(imageFolder+deductionFolder+"DeductionCalculateOptionsGroup.png");
		deductionContractualCalculateOptionImg=newImageIcon(imageFolder+deductionFolder+"DeductionContractualCalculateOptionsGroup.png");
		generateSSSImg=newImageIcon(imageFolder+deductionFolder+"GenerateSSS.png");
		generateSSSImgHover=newImageIcon(imageFolder+deductionFolder+"GenerateSSSHover.png");
		generatePhilhealthImg=newImageIcon(imageFolder+deductionFolder+"GeneratePhilHealth.png");
		generatePhilhealthImgHover=newImageIcon(imageFolder+deductionFolder+"GeneratePhilHealthHover.png");
		generateAsemco=newImageIcon(imageFolder+deductionFolder+"GenerateAsemco.png");
		generateAsemcoHover=newImageIcon(imageFolder+deductionFolder+"GenerateAsemcoHover.png");
		generatePagibig=newImageIcon(imageFolder+deductionFolder+"GeneratePagibig.png");
		generatePagibigHover=newImageIcon(imageFolder+deductionFolder+"GeneratePagibigHover.png");
		generateStPeter=newImageIcon(imageFolder+deductionFolder+"GenerateStPeter.png");
		generateStPeterHover=newImageIcon(imageFolder+deductionFolder+"GenerateStPeterHover.png");
		generateUnionDues=newImageIcon(imageFolder+deductionFolder+"GenerateUnionDues.png");
		generateUnionDuesHover=newImageIcon(imageFolder+deductionFolder+"GenerateUnionDuesHover.png");
		
		
	}
	
	private void initSettingsViewPanelImages(){
//		earningViewPanelBgImg=newImageIcon(imageFolder+settingsFolder+"PayrollViewGroup.png");
		String settingsFolder="settings/";
		
		settingViewTitleImg=newImageIcon(imageFolder+settingsFolder+"SettingsLabel.png");
		
		
		//-------------------------------
		settingsOptionPanelBgImg=newImageIcon(imageFolder+settingsFolder+"SettingsOptionsGroup.png");
		
		departmentSettingImg=newImageIcon(imageFolder+settingsFolder+"DepartmentSettings.png");
		departmentSettingImgHover=newImageIcon(imageFolder+settingsFolder+"DepartmentSettingsHover.png");
		phicSalaryRangfeImg=newImageIcon(imageFolder+settingsFolder+"PHICSalaryRange.png");
		phicSalaryRangfeImgHover=newImageIcon(imageFolder+settingsFolder+"PHICSalaryRangeHover.png");
		phicRateImg=newImageIcon(imageFolder+settingsFolder+"PHICRate.png");
		phicRateImgHover=newImageIcon(imageFolder+settingsFolder+"PHICRateHover.png");
		sssImg=newImageIcon(imageFolder+settingsFolder+"SSS.png");
		sssImgHover=newImageIcon(imageFolder+settingsFolder+"SSSHover.png");
		asemcoOcciBCCDbpImg=newImageIcon(imageFolder+settingsFolder+"Asemco.png");
		asemcoOcciBCCDbpImgHover=newImageIcon(imageFolder+settingsFolder+"AsemcoHover.png");
		pagibigImg=newImageIcon(imageFolder+settingsFolder+"Pagibig.png");
		pagibigImgHover=newImageIcon(imageFolder+settingsFolder+"PagibigHover.png");
		stPeterImg=newImageIcon(imageFolder+settingsFolder+"StPeter.png");
		stPeterImgHover=newImageIcon(imageFolder+settingsFolder+"StPeterHover.png");
		unionDuesImg=newImageIcon(imageFolder+settingsFolder+"UnionDues.png");
		unionDuesImgHover=newImageIcon(imageFolder+settingsFolder+"UnionDuesHover.png");
		ecolaOptionSettingImg=newImageIcon(imageFolder+settingsFolder+"Ecola.png");
		ecolaOptionSettingImgHover=newImageIcon(imageFolder+settingsFolder+"EcolaHover.png");
		laundryAllowanceOptionSettingImg=newImageIcon(imageFolder+settingsFolder+"LaundryAllowance.png");
		laundryAllowanceOptionSettingImgHover=newImageIcon(imageFolder+settingsFolder+"LaundryAllowanceHover.png");
		longevityOptionSettingImg=newImageIcon(imageFolder+settingsFolder+"Longevity.png");
		longevityOptionSettingImgHover=newImageIcon(imageFolder+settingsFolder+"LongevityHover.png");
		riceOptionSettingImg=newImageIcon(imageFolder+settingsFolder+"Rice.png");
		riceOptionSettingImgHover=newImageIcon(imageFolder+settingsFolder+"RiceHover.png");
		
		//-------------------------------
		settingsContractualOptionPanelBgImg=newImageIcon(imageFolder+settingsFolder+"SettingsContractualOptionsGroup.png");
		contractualRatePerDayImg=newImageIcon(imageFolder+settingsFolder+"RatePerDay.png");
		contractualRatePerDayImgHover=newImageIcon(imageFolder+settingsFolder+"RatePerDayHover.png");		
		
		//------------------------
		asemcoOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"AsemcoOptionTitle.png");
		departmentOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"DepartmentOptionTitle.png");
		pagibigOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"PagibigOptionTitle.png");
		phicRateOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"PhicRateOptionTitle.png");
		phicSalaryRangeOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"PhicSalaryRangeOptionTitle.png");
		sssOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"SssOptionTitle.png");
		stPeterOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"StPeterOptionTitle.png");
		unionDuesOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"UnionDuesOptionTitle.png");
		ecolaOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"EcolaOptionTitle.png");
		laundryAllowanceOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"LaundryAllowanceOptionTitle.png");
		longevityOptonTitleImg=newImageIcon(imageFolder+settingsFolder+"LongevityOptionTitle.png");
		riceOptionTitleImg=newImageIcon(imageFolder+settingsFolder+"RiceOptionTitle.png");
		contractualRatePerDayTitleImg=newImageIcon(imageFolder+settingsFolder+"RatePerDayOptionTitle.png");
		
		//---------------------
		dividerOptionImg=newImageIcon(imageFolder+settingsFolder+"Divider.png");
		
		
	}
	
	private void initHelpViewPanelImages(){
//		earningViewPanelBgImg=newImageIcon(imageFolder+helpFolder+"PayrollViewGroup.png");
		String helpFolder="help/";
		
		helpViewPanelBG=newImageIcon(imageFolder+helpFolder+"HelpViewGroup.png");
		
	}
	
	
	
	private void l_________________________l (){}
	/**
	 * Show or print the error images in case there is a catched error.
	 * @param e
	 */
	private void errorImageLogs(Exception e){
		// TODO Auto-generated catch block
		System.out.println("Image error: "+e.getMessage()+CLASS_NAME);
		MainFrame.getInstance().showOptionPaneMessageDialog(
				"Image error: "+e.getMessage(), JOptionPane.ERROR_MESSAGE
				);
		e.printStackTrace();
	}
	
	/**
	 * Instantiate new image icon.
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private ImageIcon newImageIcon(String path){
		return new ImageIcon(path);
	}
	
	
	private void l_______________________l (){}
	
	public static Images getInstance(){
		if(instance == null)
				instance = new Images();
			
		return instance;
	}
	
	/**
	 * Set the instance to null.
	 */
	public static void setInstanceToNull(){
		instance =null;
	}
}
