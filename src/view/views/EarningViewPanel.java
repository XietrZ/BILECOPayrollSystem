package view.views;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Constant;
import model.view.EarningsAndDeductionLayout;
import model.view.OptionViewPanel;

public class EarningViewPanel extends EarningsAndDeductionLayout {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static EarningViewPanel instance;
	
	/**
	 * Create the panel.
	 */
	public EarningViewPanel() {
		
	}
	
	
	private void l_________________________________________________________l(){}
	
	
	
	
	/** 
	 * Set Calculation Panel on Contractual
	 */
	public void setCalculationPanelContractual(){
		setNecessaryLabelComponentsWhenExtended(136,26, img.earningViewTitleImg,
				img.earningShowAllDataImg,img.earningShowAllDataImgHover);	
		

		
		String[] buttonKeyList={
				Constant.GENERATE_RATE_PER_DAY,
				Constant.GENERATE_SUB_TOTAL
				
		};
		ImageIcon[] imageList={
				img.generateRatePerDayImg,
				img.generateSubTotalImg,
		};
		ImageIcon[] imageHoverList={
				img.generateRatePerDayImgHover,
				img.generateSubTotalImgHover,
		};
		
		calculationPanel =setNecessaryOptionViewPanelComponentsWhenExtended(img.earningContractualCalculateOptionsImg, 154, 58,
				buttonKeyList, imageList, imageHoverList, calculationPanel);
		
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Set the calculation panel when the mode is regular
	 */
	public void setCalculationPanelRegular(){
		setNecessaryLabelComponentsWhenExtended(136,26, img.earningViewTitleImg,
				img.earningShowAllDataImg,img.earningShowAllDataImgHover);	
		
		
		String[] buttonKeyList={
				Constant.GENERATE_REGULAR_PAY_BTN,
				Constant.GENERATE_ECOLA_BTN,
				Constant.GENERATE_LAUNDRY_ALLOWAMCE_BTN,
				Constant.GENERATE_LONGEVITY_BTN,
				Constant.GENERATE_RICE_BTN,
				Constant.GENERATE_OVERTIME_BTN
		};
		ImageIcon[] imageList={
				img.generateRegularPayImg,
				img.generateEcolaImg,
				img.generateLaundryAllowanceImg,
				img.generateLongevityImg,
				img.generateRiceImg,
				img.generateOvertimeImg
		};
		ImageIcon[] imageHoverList={
				img.generateRegularPayImgHover,
				img.generateEcolaImgHover,
				img.generateLaundryAllowanceImgHover,
				img.generateLongevityImgHover,
				img.generateRiceImgHover,
				img.generateOvertimeImgHover
		};
		calculationPanel = setNecessaryOptionViewPanelComponentsWhenExtended(img.earningRegularCalculateOptionsImg, 154, 121,
				buttonKeyList, imageList, imageHoverList,calculationPanel);
		
		this.repaint();
		this.revalidate();
	}
	

	/**
	 * Set the retrieve prev value panel when the mode is regular
	 */
	public void setRetrievePrevVaueOptionalViewPanelRegular(){
//		setNecessaryLabelComponentsWhenExtended(174, 26, img.deductionViewTitleImg,
//				img.deductionShowAllDataImg,img.deductionShowAllDataImgHover);
		
		
		String[] buttonKeyList={
				Constant.RETRIEVE_PREV_VALUE_SAL_ADJ_BTN,
				Constant.RETRIEVE_PREV_VALUE_NS_DIFF_BTN,
				Constant.RETRIEVE_PREV_VALUE_RATA_BTN,
				Constant.RETRIEVE_PREV_VALUE_LWPAY_BTN
		};
		ImageIcon[] imageList={
				img.retrievePrevValueSalAdjImg,
				img.retrievePrevValueNsDiffImg,
				img.retrievePrevValueRATAImg,
				img.retrievePrevValueLwPayImg
		};
		ImageIcon[] imageHoverList={
				img.retrievePrevValueSalAdjImgHover,
				img.retrievePrevValueNsDiffImgHover,
				img.retrievePrevValueRATAImgHover,
				img.retrievePrevValueLwPayImgHover
		};
		retrievePrevValOptionalPanel = setNecessaryOptionViewPanelComponentsWhenExtended(
				img.earningRetrievePrevValueOptionImg, 208, 85,
				buttonKeyList, imageList, imageHoverList, retrievePrevValOptionalPanel);
		
		//--> Match the triangle in calculation button
		retrievePrevValOptionalPanel.setLocation(40,44);
		
		this.repaint();
		this.revalidate();
	}
	
	
	private void l______________________________________________________l(){}
	
	public static EarningViewPanel getInstance(){
		if(instance==null)
			instance=new EarningViewPanel();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}

}
