package view.views;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.prefs.BackingStoreException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;

import model.PayrollTableModel;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;
import model.view.OptionViewPanel;
import model.view.PayrollSystemModeDialog;
import model.view.PayrollSlipLayout;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import database.Database;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;

public class HomeViewPanel extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	private static HomeViewPanel instance;
	public JLabel homeViewPanelBg,
		connectionStatusLabel, hostNameLabel,portNumberLabel,databaseNameLabel;
	public JButton optionButton;
	public Images img;
	public OptionViewPanel optionPanel;
	public PayrollSystemModeDialog payrollModeDialog;
	public JLabel lblWelcomeToBileco;
	
	private void l____________________________________________________l(){}
	

	/**
	 * Create the panel.
	 */
	public HomeViewPanel() {
		set();
	}
	
	private void set(){
		setThisPanelCompoonents();
		setStatusLabelsComponents();
		setOptionPanelComponents();
		
		//--> Add Background
//		add(homeViewPanelBg);
				
	}
	
	private void l________________________________________________l(){}
	/**
	 * Set the components of this panel.
	 */
	private void setThisPanelCompoonents(){
		img=Images.getInstance();
		payrollModeDialog=new PayrollSystemModeDialog();
		
		
		setBounds(0, Constant.VIEW_Y, Constant.VIEW_WIDTH, Constant.VIEW_HEIGHT);
		setBackground(Constant.BILECO_DEFAULT_COLOR_GREEN);
		setLayout(null);
		setVisible(true);
		setOpaque(false);
		
		//--> Add Welcome Message
			lblWelcomeToBileco = new JLabel(img.homeWelcomeMsgImg);
			lblWelcomeToBileco.setBounds(281, 113, 678, 107);
			add(lblWelcomeToBileco);
				
		//-- ADD View Title.
			JLabel lblHome = new JLabel(img.homeViewTitleImg);
			lblHome.setBounds(19, 16, 93, 26);
			add(lblHome);
				
				
		//--> Add background image label
		homeViewPanelBg = new JLabel(img.homeViewPanelBgImg);
		homeViewPanelBg.setBounds(0, 0, this.getWidth(), this.getHeight());
		
		//--> Add option button.
		optionButton = Utilities.getInstance().initializeNewButton(
				110, 8, 47, 47, img.optionBtnImg, img.optionHoverBtnImg
		);
		optionButton.setFocusable(false);
		add(optionButton);
		
		
		
		
		
		
	
	}
	
	/**
	 * Set the status label components.
	 */
	private void setStatusLabelsComponents(){	
		connectionStatusLabel = new JLabel("Connection Status: OFFLINE");
		connectionStatusLabel.setBounds(10, 31, 166, 14);
		connectionStatusLabel.setVisible(true);
		
		
		
		hostNameLabel = new JLabel("Host Name: null");
		hostNameLabel.setBounds(10, 556, 166, 14);
		add(hostNameLabel);
		
		portNumberLabel = new JLabel("Port Number: null");
		portNumberLabel.setBounds(232, 556, 125, 14);
		add(portNumberLabel);
		
		databaseNameLabel = new JLabel("Database Name:  null ");
		databaseNameLabel.setBounds(422, 555, 188, 14);
		add(databaseNameLabel);
	}
	
	/**
	 * Set component of option panel.
	 */
	private void setOptionPanelComponents(){
		String[] buttonKeyList={
			Constant.CONNECT_TO_SERVER,
			Constant.CONNECTIVITY_PARAMETERS,
			Constant.MANAGE_ACCOUNTS_BTN,
		};
		ImageIcon[] imageList={
				img.connectToServerImg,
				img.connectivityParametersImg,
				img.manageAccountsBtnImg
		};
		ImageIcon[] imageHoverList={
				img.connectToServerImgHover,
				img.connectivityParametersImgHover,
				img.manageAccountsBtnImgHover
		};
		
		
		optionPanel=new OptionViewPanel(
				buttonKeyList,  img.homeOptionPanelBG, 
				imageList, imageHoverList, 
				optionButton.getX()+7,
				optionButton.getY()+(optionButton.getHeight()),
				154, 76
		);
		
		
		

		add(optionPanel);
		
	}
	
	private void l__________________________l(){}
	
	public void updateConnectionStatusField(String msg){
		connectionStatusLabel.setText("Connection Status: "+msg);
	}
	
	public void updateDatabaseLabel(String msg){
		databaseNameLabel.setText("Database Name:  "+msg);
	}
	public void updateHostNameField(String msg){
		switch(msg){
		case "192.168.1.107": {
			msg="XietrZ-PC";
			break;
		}
		case "192.168.1.98": {
			msg="Maam Kath-PC";
			break;
		}
		case "192.168.1.102": {
			msg="XietrZ-PC";
			break;
		}
		case "192.168.1.117": {
				msg="XietrZ-PC";
				break;
			}
			case "192.168.43.117": {
				msg="XietrZ-PC";
				break;
			}
			case "192.168.254.117": {
				msg="XietrZ-PC";
				break;
			}
			case "192.168.1.62": {
				msg="Carl-PC";
				break;
			}
		}
		hostNameLabel.setText("Host Name: "+msg);
	}
	
	public void updatePortNumberLabel(String msg){
		portNumberLabel.setText("Port Number: "+msg);
	}
	
	
	


	private void l____________________________l(){}
	
	
	public static HomeViewPanel getInstance(){
		if(instance==null)
			instance=new HomeViewPanel();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
