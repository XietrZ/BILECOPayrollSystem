package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Constant;
import model.OrderByInfo;
import model.PayrollTableModel;
import model.PayslipDataStorageInfo;
import model.SelectConditionInfo;
import model.statics.PDFCreator;
import model.statics.Utilities;
import model.view.AddEarningOrDeductionDataLayout;
import model.view.AddPayrollDateDialogLayout;
import model.view.DisplayOptionsDialog;
import model.view.ReusableTable;
import model.view.ShowAllEmployeeAddPayrollDataDialogLayout;
import model.view.YesOrNoIfDeleteDialogLayout;
import view.MainFrame;
import view.views.EarningViewPanel;
import view.views.HelpViewPanel;
import view.views.HomeViewPanel;
import view.views.PayrollViewPanel;
import database.Database;

public class ListenerHelpViewPanel implements ActionListener{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private final String THIS_CLASS_NAME="\n> "+this.getClass().getSimpleName()+".java: ";
	
	private HelpViewPanel helpViewPanel;
	private MainFrame mainFrame;
	private void l__________________________________________________________l(){}
	
	public ListenerHelpViewPanel() {
		mainFrame= MainFrame.getInstance();
		helpViewPanel= HelpViewPanel.getInstance();
		
	}
	private void l________________________________________________________l(){}
	@Override
	public void actionPerformed(ActionEvent e) {
		//--> Check the disconnection status of database.
		Database.getInstance().checkIfDisconnectedToDatabase();
		
		//--> Only Process When Connected.
		if(Database.getInstance().isConnected){
		
			if(e.getSource()==helpViewPanel.optionButton){
				mainFrame.showOptionPaneMessageDialog("Not Yet Implemented :(", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		//--> Repaint to avoid overlap
		helpViewPanel.repaint();
		
		
		
	}
	

	
}
