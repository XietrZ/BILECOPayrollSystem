package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import model.Constant;
import model.statics.Images;
import model.statics.Utilities;
import model.view.OptionViewPanel;

import java.awt.Color;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import controller.Controller;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import database.Database;

import javax.swing.JList;

import com.itextpdf.text.Image;

import view.views.DeductionViewPanel;
import view.views.EarningViewPanel;
import view.views.EmployeeViewPanel;
import view.views.HelpViewPanel;
import view.views.HomeViewPanel;
import view.views.PayrollViewPanel;
import view.views.SettingsViewPanel;

import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class MainFrame extends JFrame {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	private static MainFrame instance;
	private Images img;
	private JLabel bgLabel,
		menuBg;
	
	private void l___________________________l(){}
	public JPanel menuPanel,
		contentPanel;
	public OptionViewPanel userAccountOptionPanel;
	public JMenu mnSettings;
	public JMenuItem connectToServerMenuItem,checkConnectionMenuItem,
		homeMenuItem,employeeMenuItem,deductionMenuItem,earningMenuItem,payrollMenuItem,settingsMenuItem;
		
	public JButton  bilecoLogoButton,userDropDownBtn,
		homeMenuBtn,employeesMenuBtn,payrollMenuBtn,settingsMenuBtn,exitMenBtn,helpMenuBtn;

	public JLabel userNameLabel;

	
	private void l_______________________________l(){}
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		System.out.println(">>>>>>>> Main Frame initialize"+CLASS_NAME);
		set();
		
		System.out.println(">>>>>>>> Main Frame DONE"+CLASS_NAME);
	}
	
	private void set(){
		img=Images.getInstance();
		
		
		replaceTheCoffeeImageOfJavaApplication();
		
		setFrame();
		setContentPanel();	
		setMenuPanel();
		setUserAccountOptionPanel();
		setComponentsOutsideMainFrame();
		setMenuBar();
		
		
	
	}
	
	
	/**
	 * Change the coffee image of Java into the image that you want.
	 */
	private void replaceTheCoffeeImageOfJavaApplication(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                ("images/main/BilecoAppLogo.png")));
	}
	
	private void l_____________________________l(){}
	
	private void setFrame(){
		setSize(Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("BILECO Payroll System");
		setVisible(false);
		setResizable(false);
	}
	
	private void setContentPanel(){
		//--> Initialize content panel.
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		//--> Initialize and add bileco logo button for showing the left menu.
		bilecoLogoButton = new JButton();
		bilecoLogoButton.setBounds(39, 7, 61, 61);
		bilecoLogoButton =Utilities.getInstance().initializeNewButton(
				39, 7, 61, 61,
				img.bilecoLogoImg, img.bilecoLogoHoverImg
		);
		bilecoLogoButton.setToolTipText("ALt+Q");
		contentPanel.add(bilecoLogoButton);
		
		//--> Add user drop down button.
		userDropDownBtn = Utilities.getInstance().initializeNewButton(
				1212, 6, 31, 24, img.userDropDownImg, img.userDropDownHoverImg
		);
		add(userDropDownBtn);
		
		
		//--> UserName Label:
		userNameLabel = new JLabel();
		userNameLabel.setBounds(1158, 6, 59, 26);
		add(userNameLabel);
		
		
		
		
		//--> Initiate background label
		bgLabel = new JLabel(img.mainFrameBg);
		bgLabel.setBounds(0, 0, 1260, 690);
		
	}
	

	
	
	/**
	 * Set components of the Menu Bar.
	 */
	private void setMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(this.getWidth()/2, 0, this.getWidth()/2, 21);
		contentPanel.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		homeMenuItem = new JMenuItem("Home");
		mnView.add(homeMenuItem);
		
		employeeMenuItem = new JMenuItem("Employee");
		mnView.add(employeeMenuItem);
		
		deductionMenuItem = new JMenuItem("Deduction");
		mnView.add(deductionMenuItem);
		
		earningMenuItem = new JMenuItem("Earning");
		mnView.add(earningMenuItem);
		
		payrollMenuItem = new JMenuItem("Payroll");
		mnView.add(payrollMenuItem);
		
		settingsMenuItem = new JMenuItem("Settings");
		mnView.add(settingsMenuItem);
		
		//---------------------------------------------------------
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		
		JMenu mnUtilities = new JMenu("Utilities");
		menuBar.add(mnUtilities);
		
		connectToServerMenuItem = new JMenuItem("Connect to server");
		mnUtilities.add(connectToServerMenuItem);
		
	
		
		checkConnectionMenuItem = new JMenuItem("Check Connection");
		mnUtilities.add(checkConnectionMenuItem);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		
	}
	
	private void setMenuPanel(){
		Utilities util=Utilities.getInstance();
		
		//--> Set menu components to content panel;
		menuPanel = new JPanel();
		menuPanel.setBounds(0, 79, 176, 611);
		menuPanel.setVisible(false);
		menuPanel.setLayout(null);
		
		
		//--> Set menu buttons
		int x=10,heightGap=27, width=163,height=29;
		homeMenuBtn=util.initializeNewButton(
				x, 25, width, height,
				img.homeMenuImg, img.homeMenuHoverImg
		);
		homeMenuBtn.setToolTipText("Alt+H");

		employeesMenuBtn=util.initializeNewButton(
				x, homeMenuBtn.getY()+height+heightGap, width, height,
				img.employeeMenuImg, img.employeeMenuHoverImg
		);
		employeesMenuBtn.setToolTipText("Alt+E");
		
		payrollMenuBtn=util.initializeNewButton(
				x, employeesMenuBtn.getY()+height+heightGap, width, height,
				img.payrollMenuBtnImg, img.payrollMenuBtnHoverImg
		);
		payrollMenuBtn.setToolTipText("Alt+P");
		
		settingsMenuBtn=util.initializeNewButton(
				x, payrollMenuBtn.getY()+height+heightGap, width, height,
				img.settingsMenuBtnImg, img.settingsMenuHoverBtnImg
		);
		settingsMenuBtn.setToolTipText("Alt+S");
		
		helpMenuBtn=util.initializeNewButton(
				x, settingsMenuBtn.getY()+height+heightGap, width, height,
				img.helpMenuBtnImg, img.helpMenuHoverBtnImg
		);
		helpMenuBtn.setFocusable(false);

		
		//--> Set background of menu panel.
		menuBg = new JLabel(img.menuBg);
		menuBg.setBounds(0, 0, menuPanel.getWidth(), menuPanel.getHeight());		
		
		//--> Add necessary components of menu panel
		contentPanel.add(menuPanel);
		
		menuPanel.add(homeMenuBtn);
		menuPanel.add(employeesMenuBtn);
		menuPanel.add(payrollMenuBtn);
		menuPanel.add(settingsMenuBtn);
		menuPanel.add(helpMenuBtn);
		
		menuPanel.add(menuBg);

	}
	
	private void setUserAccountOptionPanel(){
		String[] buttonKeyList={
				Constant.ACCOUNT_PREFEREBCES_BTN,
				Constant.LOGOUT_BTN
		};
		ImageIcon[] imageList={
				img.accountPreferencesBtnImg,
				img.logoutBtnImg
		};
		ImageIcon[] imageHoverList={
				img.accountPreferencesBtnImgHover,
				img.logoutBtnImgHover
		};
		
		
		userAccountOptionPanel=new OptionViewPanel(
				buttonKeyList,  img.userAccountPanelBg, 
				imageList, imageHoverList, 
				0,userDropDownBtn.getY()+userDropDownBtn.getHeight(), 168,62
		);
//		userAccountOptionPanel.setVisible(true);
		userAccountOptionPanel.setLocation(userDropDownBtn.getX()-userAccountOptionPanel.getWidth()+25,userAccountOptionPanel.getY());
		add(userAccountOptionPanel);
		
	}
	/**
	 * Add necessary panels outside frame.
	 */
	private void setComponentsOutsideMainFrame(){		
		contentPanel.add(HomeViewPanel.getInstance());
		contentPanel.add(EmployeeViewPanel.getInstance());
		contentPanel.add(DeductionViewPanel.getInstance());
		contentPanel.add(EarningViewPanel.getInstance());
		contentPanel.add(PayrollViewPanel.getInstance());
		contentPanel.add(SettingsViewPanel.getInstance());
		contentPanel.add(HelpViewPanel.getInstance());
		
		
		
		//--> Add background image label
		contentPanel.add(bgLabel);
	
	}
	
	
	private void l_____________________l(){}
	
	/**
	 * JDialog. Show error dialog
	 * @param frame
	 */
//	public void showErrorDialog(String msg){
//		
//		JOptionPane jops = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION,null,null,null);
//		JDialog dialog = jops.createDialog(this, "Message");
//		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		dialog.setLocationRelativeTo(null);
//		dialog.setVisible(true);
//		// Cast in a safe manner
//		int input = (int) jops.getValue();
//		
//		if(input == JOptionPane.OK_OPTION)
//		{	
//			dialog.dispose();
//		}
//				
//	}
	
	/**
	 * JDialog. Show message dialog.
	 * @param frame
	 */
//	public void showMessageDialog(String msg){
//		
//		JOptionPane jops = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,null,null,null);
//		JDialog dialog = jops.createDialog(this, "Message");
//		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		dialog.setLocationRelativeTo(null);
//		dialog.setVisible(true);
//		// Cast in a safe manner
//		int input = (int) jops.getValue();
//		
//		if(input == JOptionPane.OK_OPTION){
//		    System.gc();
//		    dialog.dispose();
//		}
//				
//	}
	
	/**
	 * Show option pane dialog when there is a needed message to be prompt.
	 * @param message
	 * @param messageStatus
	 */
	public void showOptionPaneMessageDialog(String message, int messageStatus){
		 JOptionPane.showMessageDialog(null, message, 
					null, messageStatus);
	}
	
	
	
	
	private void l______________________l(){}
	
	
	public static MainFrame getInstance(){
		if (instance==null){
			instance= new MainFrame();
		}
		return instance;
	}
	
	public static void setInstanceToNull() {
		instance = null;
	}
	
	private void l__________________________________________l(){}
	
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
