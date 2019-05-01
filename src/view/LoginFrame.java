package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import controller.Controller;
import model.Constant;
import model.MainThread;
import model.statics.Images;
import model.statics.Utilities;

import java.awt.Font;
import java.awt.GridLayout;

public class LoginFrame extends JFrame  {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static LoginFrame instance;
	private JPanel contentPanel,bgPanel;
	private JLabel lblUserName, lblPassword,
		lblBilecoPayrollSystem;
	private Images img;
	private int textFieldWidth=199,textFieldHeight=27, textFieldX=177;
	
	
	private void l____________________________________________l(){}
//	public MainThread mainThread=null;
	public JButton loginButton=null;
	public MainThread mainThread=null;
	private void l______________________________________l(){}
	
	public JTextField userNameTextfield;
	public JPasswordField passwordField;

	private void l______________________________________________l(){}
	
	
	/**
	 * Create the dialog.
	 */
	public LoginFrame() {
		System.out.println(">>>>>>>> Login Frame initialize"+CLASS_NAME);
		getContentPane().setLayout(null);
		
		init();
		set();
		
		System.out.println(">>>>>>>> Login Frame DONE"+CLASS_NAME);
	
	}
	
	private void init(){
		img=Images.getInstance();
		mainThread=null;
		Utilities.getInstance().isLogin=false;
		
		
		initBGPanel();
		initContentPanel();
		initButtonPanel();

	}
	
	private void set(){
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		setModal(true);
//		setUndecorated(true);
		setLocationRelativeTo(null);
		setTitle("Login");
		setResizable(false);

		replaceTheCoffeeImageOfJavaApplication();
	}
	private void initBGPanel(){
		bgPanel = new JPanel();
		bgPanel.setBounds(0, 0, 444, 272);
		bgPanel.setBackground(Color.LIGHT_GRAY);
		bgPanel.setOpaque(false);
		getContentPane().add(bgPanel);
		
		JLabel bgLabel = new JLabel(img.loginFrameBg);
		bgLabel.setBounds(0, 0, 444, 272);
		getContentPane().add(bgLabel);
		bgLabel.setIcon(img.loginFrameBg);
		
		
		
	}

	
	
	private void initContentPanel(){
		bgPanel.setLayout(new BorderLayout(0, 0));
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.white);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setOpaque(false);
		bgPanel.add(contentPanel);
		contentPanel.setLayout(null);
		
//		lblUserName = new JLabel("User Name");
//		lblUserName.setBounds(107, 107, 71, 14);
//		contentPanel.add(lblUserName);
		
//		lblPassword = new JLabel("Password");
//		lblPassword.setBounds(107, 132, 71, 14);
//		contentPanel.add(lblPassword);
		
		userNameTextfield = new JTextField("guest");
		userNameTextfield.setBounds(textFieldX, 103, textFieldWidth, textFieldHeight);
		contentPanel.add(userNameTextfield);
		userNameTextfield.setColumns(10);
		userNameTextfield.setBorder(BorderFactory.createEmptyBorder());
		userNameTextfield.setOpaque(false);
		userNameTextfield.setFocusable(true);
		
		passwordField = new JPasswordField("1234");
		passwordField.setBounds(textFieldX, 141,  textFieldWidth, textFieldHeight);
		passwordField.setOpaque(false);
		passwordField.setBorder(BorderFactory.createEmptyBorder());
		contentPanel.add(passwordField);
		
//		lblBilecoPayrollSystem = new JLabel("BILECO Payroll System");
//		lblBilecoPayrollSystem.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
//		lblBilecoPayrollSystem.setHorizontalAlignment(SwingConstants.CENTER);
//		lblBilecoPayrollSystem.setBounds(77, 11, 313, 37);
//		contentPanel.add(lblBilecoPayrollSystem);
		
		
	}
	
	private void initButtonPanel(){
		JPanel buttonPanel = new JPanel();
//		buttonPane.setBackground(Constant.BILECO_DEFAULT_COLOR);
		buttonPanel.setBackground(Color.white);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bgPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setOpaque(false);
		
		
		loginButton = Utilities.getInstance().initializeNewButton(
				-1,-1,-1,-1,
				img.loginBtnImg,img.loginBtnHoverImg
		);
//		loginButton = Utilities.getInstance().initializeNewButton(loginButton);
//		loginButton.setIcon(img.loginBtnImg);
//		loginButton.setRolloverIcon(img.loginBtnHoverImg);
//		loginButton.setCursor(new Cursor((Cursor.HAND_CURSOR)));
		buttonPanel.add(loginButton);
		getRootPane().setDefaultButton(loginButton);
	
		
	}
	
	
	/**
	 * Change the coffee image of Java into the image that you want.
	 */
	private void replaceTheCoffeeImageOfJavaApplication(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                ("images/main/BilecoAppLogo.png")));
	}
	private void l___________________l(){}
	
	public static LoginFrame getInstance(){
		if(instance==null)
			instance=new LoginFrame();
		return instance;
	}
	
	public void setInstanceToNull() {
		mainThread.stopThread();
		instance = null;
	}
	

	
}
