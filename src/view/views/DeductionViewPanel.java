package view.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.Constant;
import model.MultipleUpdateDatabaseModel;
import model.PayrollTableModel;
import model.statics.Utilities;
import model.view.EarningsAndDeductionLayout;
import model.view.ReusableTable;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.table.TableRowSorter;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;

import database.Database;

import javax.swing.DefaultComboBoxModel;


public class DeductionViewPanel extends EarningsAndDeductionLayout {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static DeductionViewPanel instance;
	public DeductionViewPanel(){
	
	}
	
	
	private void l_________________________________________________________l(){}
	
	
	
	
	/** 
	 * Set Calculation Panel on Contractual
	 */
	public void setCalculationPanelContractual(){
		setNecessaryOptionPanelComponentsWhenExtended(174, 26, img.deductionViewTitleImg,
				img.deductionShowAllDataImg,img.deductionShowAllDataImgHover);
		
		
		String[] buttonKeyList={
				Constant.GENERATE_SSS_BTN,
				Constant.GENERATE_PAGIBIG_BTN,
				Constant.GENERATE_PHILHEALTH_BTN
		};
		ImageIcon[] imageList={
				img.generateSSSImg,
				img.generatePagibig,
				img.generatePhilhealthImg
		};
		ImageIcon[] imageHoverList={
				img.generateSSSImgHover,
				img.generatePagibigHover,
				img.generatePhilhealthImgHover
		};
		setNecessaryCalculatePanelComponentsWhenExtended(
				img.deductionContractualCalculateOptionImg, 252, 85,
				buttonKeyList, imageList, imageHoverList);
		
		//--> Match the triangle in calculation button
		calculationPanel.setLocation(8,44);
		
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Set the calculation panel when the mode is regular
	 */
	public void setCalculationPanelRegular(){
		setNecessaryOptionPanelComponentsWhenExtended(174, 26, img.deductionViewTitleImg,
				img.deductionShowAllDataImg,img.deductionShowAllDataImgHover);
		
		
		String[] buttonKeyList={
				Constant.GENERATE_SSS_BTN,
				Constant.GENERATE_PAGIBIG_BTN,
				Constant.GENERATE_PHILHEALTH_BTN,
				Constant.GENERATE_UNION_DUES_BTN,
				Constant.GENERATE_ASEMCO_BTN,
				Constant.GENERATE_ST_PETER_BTN
		};
		ImageIcon[] imageList={
				img.generateSSSImg,
				img.generatePagibig,
				img.generatePhilhealthImg,
				img.generateUnionDues,
				img.generateAsemco,
				img.generateStPeter
		};
		ImageIcon[] imageHoverList={
				img.generateSSSImgHover,
				img.generatePagibigHover,
				img.generatePhilhealthImgHover,
				img.generateUnionDuesHover,
				img.generateAsemcoHover,
				img.generateStPeterHover
		};
		setNecessaryCalculatePanelComponentsWhenExtended(
				img.deductionCalculateOptions, 252, 112,
				buttonKeyList, imageList, imageHoverList);
		
		//--> Match the triangle in calculation button
		calculationPanel.setLocation(8,44);
		
		this.repaint();
		this.revalidate();
	}

	private void l______________________________________________________l(){}
	
	public static DeductionViewPanel getInstance(){
		if(instance==null)
			instance=new DeductionViewPanel();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
