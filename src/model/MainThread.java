package model;

import javax.swing.JOptionPane;

import main.BilecoPayrollSystemMain;
import view.LoadingScreen;
import view.LoginFrame;
import view.MainFrame;
import database.Database;

public class MainThread extends Thread{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	private long counter=0,resetValue=100000, //100,000 ms->100 s // This means reset value of counter every 100 seconds/100000 milliseconds. 
			byOneSecond=1000,//1000 ms->1 s	
			byTenSecond=10000 //10,000 ms->10 s
	;
	private boolean isRun=true;
	
	public boolean isPause=false,isReconnect=false;
	private void l____________________________________________l(){}
	
	/**
	 * Stop thread.
	 */
	public void stopThread(){
		resumeThread();
		LoginFrame.getInstance().dispose();
		isRun=false;
	}
	
	/**
	 * Resume thread when paused.
	 */
	public void resumeThread(){
		if(isPause){
			notifyThis();
		}
		
	}
	private void l_____________________________________________________l(){}
	
	public synchronized void notifyThis(){
		notifyAll();
	}
	
	@Override
	public synchronized void run(){
		while(isRun){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			isPause=false; 
			
			
			//-----------------------------------------------------------------------------------
			//--> Check if there is a connection to database. 
			if(Database.getInstance().connection==null ||
					Database.getInstance().isConnected==false ){
				isReconnect=false;
				
				System.out.println("\tThere is no connection to database."
						+CLASS_NAME);
				
				MainFrame.getInstance().showOptionPaneMessageDialog(
						"There is no connection to database, Please connect again.",
						JOptionPane.ERROR_MESSAGE);
				
				
				isPause=true;
				//--> This code pauses the thread to minimize usage of too much memory since thread use much memory.
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//-----------------------------------------------------------------------------------
			//--> Check if it is time ro reconnect.
			if(isReconnect){
				isReconnect=false;
				
				//--> ReConnecting process.
				LoadingScreen loadingScreen= LoadingScreen.getInstance();
				loadingScreen.showLoadingScreen();
				MainFrame.getInstance().setVisible(false);
				loadingScreen.updateLoadingStatus("Reconnecting to database.. Please wait...");
				
				Database db=Database.getInstance();
				String databaseName=db.getDatabaseName(),//"bileco_db",
						hostName=db.getHostName(),//"localhost",
						portNumber=db.getPortNumber();//"3306";
				while(!db.isConnected && LoadingScreen.getInstance().isShowLoadingScreen){
					db.connectToDatabase(hostName, portNumber, databaseName);
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				//--> If CONNECTED!
				if(db.isConnected){
					loadingScreen.hideLoadingScreen();
					MainFrame.getInstance().showOptionPaneMessageDialog(
							"Reconnection successful!",
							JOptionPane.INFORMATION_MESSAGE);
					
					MainFrame.getInstance().setVisible(true);
				}
			}
			
			
			
			//-----------------------------------------------------------------
			//--> Counter values.
			counter++;
			if(counter>=resetValue){ //
				counter=0;
			}
		}
	}
	


}
