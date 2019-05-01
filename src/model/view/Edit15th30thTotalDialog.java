package model.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import model.Constant;

public class Edit15th30thTotalDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JRadioButton edit15th30thRadioBtn, editTotalRadioBtn;
	public JButton executeButton;
	

	/**
	 * Create the dialog.
	 */
	public Edit15th30thTotalDialog() {
		setTitle("Edit ");
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
		contentPanel.setBackground(Constant.BILECO_DEFAULT_COLOR_BLUE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			edit15th30thRadioBtn = new JRadioButton("Edit 15th and 30th");
			edit15th30thRadioBtn.setBackground(Constant.BILECO_DEFAULT_COLOR_BLUE);
			contentPanel.add(edit15th30thRadioBtn);
		}
		{
			editTotalRadioBtn = new JRadioButton("Edit Total");
			editTotalRadioBtn.setBackground(Constant.BILECO_DEFAULT_COLOR_BLUE);
			contentPanel.add(editTotalRadioBtn);
		}
		
		
		//---------------------------------------------------------
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Constant.BILECO_DEFAULT_COLOR_GREEN);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			executeButton = new JButton("Execute");
			executeButton.setActionCommand("Execute");
			buttonPane.add(executeButton);
			getRootPane().setDefaultButton(executeButton);
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
