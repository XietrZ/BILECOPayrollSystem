package model.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

public class WithOrWithoutATMDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JRadioButton withATMRadioBtn, withoutATMRadioBtn;
	public JButton selectButton;
	public int dispPDFExcelMode;
	

	/**
	 * Create the dialog.
	 */
	public WithOrWithoutATMDialog() {
		Utilities util  = Utilities.getInstance();
		Images img = Images.getInstance();
		
		setTitle("With or Without ATM");
		setBounds(100, 100, 258, 128);
		setVisible(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setLocationRelativeTo(null);
		setResizable(false);
		
		replaceTheCoffeeImageOfJavaApplication();
		
		getContentPane().setLayout(new BorderLayout());
		
		//----------------------------------------------------------
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			withATMRadioBtn = new JRadioButton("With ATM");
			withATMRadioBtn.setBackground(Constant.DIALOG_BOX_COLOR_BG);
			withATMRadioBtn.setForeground(Color.WHITE);
			contentPanel.add(withATMRadioBtn);
		}
		{
			withoutATMRadioBtn = new JRadioButton("Without ATM");
			withoutATMRadioBtn.setForeground(Color.WHITE);
			withoutATMRadioBtn.setBackground(Constant.DIALOG_BOX_COLOR_BG);
			contentPanel.add(withoutATMRadioBtn);
		}
		
		
		//---------------------------------------------------------
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			selectButton = util.initializeNewButton(-1, -1, -1, -1, img.selectBtnDialogImg, img.selectBtnDialogImgHover);
			buttonPane.add(selectButton);
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
