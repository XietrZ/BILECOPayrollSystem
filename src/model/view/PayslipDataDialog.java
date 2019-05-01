package model.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Image;

import database.Database;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

public class PayslipDataDialog extends JDialog {

	private JPanel contentPanel;
	private JLabel lockLabel;
	
	
	public JButton calculateBtn,saveButton,cancelButton;
	public PayrollSlipLayout payslipPanel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			PayslipDataDialog dialog = new PayslipDataDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public PayslipDataDialog() {
		init();
		set();
	}
	
	private void init(){
		payslipPanel = new PayrollSlipLayout();
	}
	
	
	private void set(){
		setThisDialogComponents();
		setContentPanelComponents();
		setButtonPanelComponents();
	}
	private void setThisDialogComponents(){
		setBounds(100, 100, payslipPanel.panelBeforeThisPanel.getWidth()+20, payslipPanel.getHeight());
		getContentPane().setLayout(new BorderLayout());
		setTitle("Payslip Data");
		setVisible(false);
		setLocationRelativeTo( null);
		getContentPane().setBackground(Constant.BILECO_DEFAULT_COLOR_BLUE);
		setModal(true);
		setResizable(false);
	}
	
	/**
	 * Set the content panel 
	 */
	private void setContentPanelComponents(){
		contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(0,payslipPanel.getHeight()));
		contentPanel.setBackground(Constant.BILECO_DEFAULT_COLOR_BLUE);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		
		
		contentPanel.add(payslipPanel.panelBeforeThisPanel,BorderLayout.CENTER);
		
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
	
	}
	
	/**
	 * Set the button panel components
	 */
	private void setButtonPanelComponents(){
		Utilities util = Utilities.getInstance();
		Images img= Images.getInstance();
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		
		calculateBtn = util.initializeNewButton(-1, -1, -1, -1, img.calculateBtnDialogImg, img.calculateBtnDialogImgHover);
		buttonPane.add(calculateBtn);
	
		
		saveButton = util.initializeNewButton(-1, -1, -1, -1, img.saveBtnDialogImg, img.saveBtnDialogImgHover);
		buttonPane.add(saveButton);
	
		
		cancelButton = util.initializeNewButton(-1, -1, -1, -1, img.cancelBtnDialogImg, img.cancelBtnDialogImgHover);
		buttonPane.add(cancelButton);
		
		//---------------------------------------------
		//--> Add Lock label.
		
		lockLabel= new JLabel(Images.getInstance().lockImgBtn);
		lockLabel.setVisible(false);
		buttonPane.add(lockLabel);
	}
	
	private void l______________________l(){}
	
	public void setIfLockMode(boolean bool,Database db){
		calculateBtn.setVisible(bool);
		saveButton.setVisible(bool);
		cancelButton.setVisible(bool);
		
		lockLabel.setVisible((bool)?false:true);
		
		payslipPanel.setEarningDeductionFieldsEditable(bool,db);
	}
	

}
