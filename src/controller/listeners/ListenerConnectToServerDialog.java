package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.MainFrame;
import view.dialog.ConnectToServerDialog;
import view.views.HomeViewPanel;
import database.Database;

public class ListenerConnectToServerDialog implements ActionListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private ConnectToServerDialog connectToServerDialog;
	private HomeViewPanel homeViewPanel;
	public ListenerConnectToServerDialog() {
		// TODO Auto-generated constructor stub
		
		connectToServerDialog= ConnectToServerDialog.getInstance();
		homeViewPanel=HomeViewPanel.getInstance();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(THIS_CLASS_NAME+"Connect To Server Dialog OK BUTTON"+CLASS_NAME);
		//--> OK BUTTON
		if(e.getSource()==connectToServerDialog.okButton){
			
			String hostName=connectToServerDialog.hostNameTextfield.getText(),
					portNumber=connectToServerDialog.portNumberTextfield.getText(),
					databaseName=connectToServerDialog.databaseTextfield.getText();
			
			
			if(!Database.getInstance().isConnected){
				Database.getInstance().connectToDatabase(
						hostName,
						portNumber,
						databaseName);
				
				
				if(Database.getInstance().isConnected){
					
					homeViewPanel.updateConnectionStatusField("ONLINE");
					homeViewPanel.updateDatabaseLabel(databaseName);
					homeViewPanel.updateHostNameField(hostName);
					homeViewPanel.updatePortNumberLabel(portNumber);
					
					MainFrame.getInstance().showOptionPaneMessageDialog(
							"Connected to the server!", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			else{
				MainFrame.getInstance().showOptionPaneMessageDialog(
						"Connection to server has already been established!", 
						JOptionPane.INFORMATION_MESSAGE);
			}		
		}
		connectToServerDialog.dispose();	
	}
	

}