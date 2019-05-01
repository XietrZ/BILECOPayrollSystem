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
		setNecessaryOptionPanelComponentsWhenExtended(136,26, img.earningViewTitleImg,
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
		
		setNecessaryCalculatePanelComponentsWhenExtended(img.earningContractualCalculateOptionsImg, 154, 58,
				buttonKeyList, imageList, imageHoverList);
		
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Set the calculation panel when the mode is regular
	 */
	public void setCalculationPanelRegular(){
		setNecessaryOptionPanelComponentsWhenExtended(136,26, img.earningViewTitleImg,
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
		setNecessaryCalculatePanelComponentsWhenExtended(img.earningRegularCalculateOptionsImg, 154, 121,
				buttonKeyList, imageList, imageHoverList);
		
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
