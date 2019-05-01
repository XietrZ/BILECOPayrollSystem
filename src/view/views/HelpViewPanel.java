package view.views;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.prefs.BackingStoreException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;

import model.PayrollTableModel;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;
import model.view.PayrollSlipLayout;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import database.Database;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;

public class HelpViewPanel extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	private static HelpViewPanel instance;
	public JLabel helpViewPanelBg;
	public JButton optionButton;
	public Images img;
	
	private void l____________________________________________________l(){}
	

	/**
	 * Create the panel.
	 */
	public HelpViewPanel() {
		set();
	}
	
	private void set(){
		setThisPanelCompoonents();

	}
	
	private void l________________________________________________l(){}
	/**
	 * Set the components of this panel.
	 */
	private void setThisPanelCompoonents(){
		img=Images.getInstance();
		
		setBounds(0, Constant.VIEW_Y, Constant.VIEW_WIDTH, Constant.VIEW_HEIGHT);
		setBackground(Constant.BILECO_DEFAULT_COLOR_GREEN);
		setLayout(null);
		setVisible(false);
		setOpaque(false);
		
//		//--> Add Welcome Message
//			JLabel lblWelcomeToBileco = new JLabel(img.homeWelcomeMsgImg);
//			lblWelcomeToBileco.setBounds(281, 113, 678, 48);
//			add(lblWelcomeToBileco);
//				
//		//-- ADD View Title.
//			JLabel lblHome = new JLabel(img.homeViewTitleImg);
//			lblHome.setBounds(19, 16, 93, 26);
//			add(lblHome);
//				
//				
//		//--> Add background image label
//		helpViewPanelBg = new JLabel(img.homeViewPanelBgImg);
//		helpViewPanelBg.setBounds(0, 0, this.getWidth(), this.getHeight());
//		
		//--> Add option button.
		optionButton = Utilities.getInstance().initializeNewButton(
				110, 9, 47, 47, img.optionBtnImg, img.optionHoverBtnImg
		);
		optionButton.setFocusable(false);
		add(optionButton);
		
		
		
		//--> Background
		JLabel labelBG=new JLabel(img.helpViewPanelBG);
		labelBG.setBounds(0,0,this.getWidth(),this.getHeight());
		add(labelBG);
		
	
	}
	
	
	


	private void l____________________________l(){}
	
	
	public static HelpViewPanel getInstance(){
		if(instance==null)
			instance=new HelpViewPanel();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
