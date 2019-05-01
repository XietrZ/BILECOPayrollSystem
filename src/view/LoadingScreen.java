package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import database.Database;
import main.BilecoPayrollSystemMain;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class LoadingScreen extends JFrame implements Runnable,WindowListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static LoadingScreen instance;
	private JPanel contentPane;
	private JLabel lblLoadingStatus,bg;
	
	
	public boolean isShowLoadingScreen=false;
	public boolean isLogout=false;

	/**
	 * Create the frame.
	 */
	public LoadingScreen() {
		System.out.println(">>>>>>>> Loading Screen initialize"+CLASS_NAME);
		
		this.addWindowListener(this);
		
		//----------------------
		setFrame();
		setComponents();
		//---------------------
		
		System.out.println(">>>>>>>> Loading Screen DONE"+CLASS_NAME);
		
	}
	
	private void l_________________________________________l(){}
	/**
	 * Set frame.
	 */
	private void setFrame(){
		setBounds(100, 100, 450, 155);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setModal(true);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		replaceTheCoffeeImageOfJavaApplication();
	}
	/**
	 * Set the components
	 */
	private void setComponents(){
		contentPane = new JPanel();
		contentPane.setBackground(Constant.BILECO_DEFAULT_COLOR_BLUE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		ImageIcon loadingIcon = new ImageIcon("images/loading/Loader.gif");
		
		JLabel lblLoadingImageLabel = new JLabel("");
		lblLoadingImageLabel.setBounds(142, 60, 121, 55);
		contentPane.add(lblLoadingImageLabel);
		lblLoadingImageLabel.setIcon(loadingIcon);
		
		
		lblLoadingStatus = new JLabel("Sample..");
		lblLoadingStatus.setFont(new Font("Arial", Font.PLAIN, 14));
		lblLoadingStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoadingStatus.setBounds(0, 124, 450, 14);
		contentPane.add(lblLoadingStatus);
		
		
		bg= new JLabel(Images.getInstance().loadingScreenBg);
		bg.setBounds(0,0,this.getWidth(),this.getHeight());
		contentPane.add(bg);
		
		
		
	}
	
	/**
	 * Change the coffee image of Java into the image that you want.
	 */
	private void replaceTheCoffeeImageOfJavaApplication(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                ("images/main/BilecoAppLogo.png")));
	}
	private void l________________________________________l(){}
	/**
	 * Execute the processes and update loading screen status when logging out.
	 */
	public void executeProcessWhenLogout(){
		//--> Set Instance To NULL.
		BilecoPayrollSystemMain.setInstanceToNull();		
		
		//----------------------------------------------------------------------
		//--> Initialize again Images, MainFrame, Controller
		System.out.println(">>>>>>>> BilecoPayrollSystemMain initialize....LOGOUT"+CLASS_NAME);
		BilecoPayrollSystemMain.getInstance().init();
		
		
		this.setVisible(false);
	}
	
	
	/**
	 * Hide laading screen.
	 */
	public void hideLoadingScreen(){
		System.out.println("\tINITIALIZE hide Loading Screen"+CLASS_NAME); 
		isShowLoadingScreen=false;
		notifyThis();
		
	}
	
	/**
	 * Show loading screen.
	 */
	public void showLoadingScreen(){
		System.out.println("\tINITIALIZE show Loading Screen"+CLASS_NAME); 
		updateLoadingStatus("???");
		isShowLoadingScreen=true;
		notifyThis();
		
	}
	
	/**
	 * Use this to add temporary delay
	 */
	public void temporaryDelay(){
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Upload the loading status
	 * @param str
	 */
	public void updateLoadingStatus(String str){
		lblLoadingStatus.setText((isLogout)?
				"Logging out..."+str:
				str
		);
	}
	
	
	
	private void l__________________________________________l(){}
	
	public synchronized void notifyThis(){
		notifyAll();
	}
	
	@Override
	public synchronized void run(){
		// TODO Auto-generated method stub
		
		while(true){
			if(isShowLoadingScreen){
				System.out.println("\tSHOW Loading Screen"+CLASS_NAME);
				this.setVisible(true);
				
				if(isLogout){
					executeProcessWhenLogout();
					isLogout=false;
				}
			}
			else{
				System.out.println("\tHIDE Loading Screen"+CLASS_NAME);
				this.setVisible(false);
			}
			
			//--> This code pauses the thread to minimize usage of too much memory since thread use much memory.
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
	}
	
	
	private void l______________________________________________l(){}
	
	
	
	

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
		
		//--> Loading Screen.
		if(e.getSource()==LoadingScreen.getInstance() && !Utilities.getInstance().isLogin){
			System.out.println("Loading Screen Closing and Program is Closing"+CLASS_NAME);
			System.exit(0);
		}
		else if(e.getSource()==LoadingScreen.getInstance() && Utilities.getInstance().isLogin){
			System.out.println("Loading Screen Closing and Main Frame Visible TRUE"+CLASS_NAME);
			
			hideLoadingScreen();
			MainFrame.getInstance().setVisible(true);
		}
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	private void l_______________________________________________________________l(){}
	public static LoadingScreen getInstance(){
		if(instance==null){
			instance = new LoadingScreen();
		}
		return instance;
	}
//	public void setInstanceToNull(){
//		instance =null;
//	}
	
	

	
}
