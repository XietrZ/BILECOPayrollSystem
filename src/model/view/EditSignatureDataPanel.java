package model.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.DropMode;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Constant;

public class EditSignatureDataPanel extends JPanel {
	private JTextField nameTextField;
	private JTextField workTitleTextField;
	private JLabel lblPreparedByData;
	/**
	 * Create the panel.
	 */
	public EditSignatureDataPanel() {
		set();
	}
	
	
	
	private void set(){
		setThisPanel();
		setPartsOfThisPanel();
	}
	
	private void setThisPanel(){
		setLayout(new BorderLayout(10, 10));
		setBackground(Constant.DIALOG_BOX_COLOR_BG);
		setOpaque(false);
	}
	private void setPartsOfThisPanel(){
		lblPreparedByData = new JLabel("TITLE");
		lblPreparedByData.setForeground(Color.WHITE); 
		add(lblPreparedByData, BorderLayout.NORTH);
		
		//--------------------------------
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new GridLayout(0, 2, 0, 5));
		
		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setForeground(Color.WHITE); 
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		centerPanel.add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		centerPanel.add(nameTextField);
		
		JLabel workTitleLabel = new JLabel("Work Title: ");
		workTitleLabel.setForeground(Color.WHITE); 
		workTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		centerPanel.add(workTitleLabel);
		
		workTitleTextField = new JTextField();
		workTitleTextField.setColumns(10);
		centerPanel.add(workTitleTextField);
	}

	
	private void l____________________________l(){}
	
	public JLabel getLblPreparedByData() {
		return lblPreparedByData;
	}



	public JTextField getNameTextField() {
		return nameTextField;
	}



	public JTextField getWorkTitleTextField() {
		return workTitleTextField;
	}
	
	
	
}
