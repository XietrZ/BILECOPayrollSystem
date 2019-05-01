package model.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

public class PayrollSystemModeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JRadioButton regularModeBtn, contractualModeBtn;
	public JButton selectButton;
	

	/**
	 * Create the dialog.
	 */
	public PayrollSystemModeDialog() {
		setTitle("Payroll System Mode");
		setBounds(100, 100, 258, 128);
		setVisible(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setLocationRelativeTo(null);
		setResizable(false);
		
		replaceTheCoffeeImageOfJavaApplication();
		
		getContentPane().setLayout(new BorderLayout());
		
		//----------------------------------------------------------
		Images img = Images.getInstance();
		Utilities util = Utilities.getInstance();
		Color bg= new Color(48,120,161);
		
		JPanel tempPanel=new JPanel();
		tempPanel.setBackground(bg);
		tempPanel.setPreferredSize(new Dimension(-1,10));
		getContentPane().add(tempPanel, BorderLayout.NORTH);
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBackground(bg);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			regularModeBtn = new JRadioButton();
			regularModeBtn.setFocusable(false);
			regularModeBtn.setBackground(bg);
			contentPanel.add(regularModeBtn);
			
			JLabel lbl = new JLabel(img.regularPayrollModeImg);
			contentPanel.add(lbl);
			
		}
		{
			contractualModeBtn = new JRadioButton();
			contractualModeBtn.setFocusable(false);
			contractualModeBtn.setBackground(bg);
			contentPanel.add(contractualModeBtn);
			
			JLabel lbl = new JLabel(img.contractualPayrollModeImg);
			contentPanel.add(lbl);
		}
		
		
		//---------------------------------------------------------
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bg);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			selectButton = util.initializeNewButton(-1, -1, -1, -1, img.selectBtnDialogImg, img.selectBtnDialogImgHover);
			buttonPane.add(selectButton);
			getRootPane().setDefaultButton(selectButton);
		}
			
	}
	
	
	/**
	 * Change the coffee image of Java into the image that you want.
	 */
	private void replaceTheCoffeeImageOfJavaApplication(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                ("images/main/BilecoAppLogo.png")));
	}

}
