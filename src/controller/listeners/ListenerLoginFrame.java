package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.LoadingScreen;
import view.LoginFrame;
import view.MainFrame;
import view.dialog.AddEmployeeDialog;
import view.views.HomeViewPanel;
import model.Constant;
import model.MainThread;
import model.statics.Images;
import model.statics.Utilities;
import database.Database;

public class ListenerLoginFrame implements ActionListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private MainFrame mainFrame;
	private LoginFrame loginFrame;
	private HomeViewPanel homeViewPanel;
	
//	public boolean isLogin=false;

	public ListenerLoginFrame() {
		mainFrame=MainFrame.getInstance();
		loginFrame=LoginFrame.getInstance();
		homeViewPanel=HomeViewPanel.getInstance();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//--> LOGIN  Button
		if(e.getSource()==loginFrame.loginButton){
			System.out.println(THIS_CLASS_NAME+"Login"+CLASS_NAME);
			
			Database db=Database.getInstance();
			Utilities util=Utilities.getInstance();
			
			
//			
//			db.connectToDatabase(hostName, portNumber, databaseName);
//			db.selectDataInDatabase(new String[]{db.tableNameUserAccount}, null,
//					null,null, null,Constant.SELECT_ALL);
			
			
			//--> Checks if the user account matched!
			if(db.isUserAccountInputMatched(loginFrame.userNameTextfield.getText()
					, loginFrame.passwordField.getText())){
				util.isLogin=true;
				
				//--> Show option message that you have successfully login.
				JOptionPane.showMessageDialog(null, "You have successfully login!");
				loginFrame.dispose();
				
				//--> Set necessary UI components.
				mainFrame.setVisible(true);
				homeViewPanel.payrollModeDialog.regularModeBtn.setSelected(true);
				homeViewPanel.payrollModeDialog.setVisible(true);
				
			}
			else{
				JOptionPane.showMessageDialog(null, "Invalid user name or password", 
						null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

