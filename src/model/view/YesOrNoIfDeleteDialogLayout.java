package model.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import database.Database;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

import java.awt.Font;

public class YesOrNoIfDeleteDialogLayout extends JDialog {

	private JPanel contentPanel;
	
	private void l________________________l(){}
	 
	public JButton  yesButton,noButton;
	public JTextArea lblNewLabel;
	
	private void l_____________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public YesOrNoIfDeleteDialogLayout() {
		set();
	}
	
	private void set(){
		setThisDialogComponents();
		setContentPanelComponents();
		setButtonPanelComponents();
	}
	private void setThisDialogComponents(){
		setBounds(100, 100, 376, 110);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Warning");
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
		contentPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JTextArea(" Are you sure you want to delete?");
		lblNewLabel.setFont(new Font("Kalinga", Font.PLAIN, 18));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setLineWrap(true);
		lblNewLabel.setWrapStyleWord(true);
		lblNewLabel.setOpaque(false);
		lblNewLabel.setEditable(false);
		lblNewLabel.setHighlighter(null);
//		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblNewLabel, BorderLayout.CENTER);
		
	
	}
	
	/**
	 * Set the button panel components
	 */
	private void setButtonPanelComponents(){
		Utilities util =  Utilities.getInstance();
		Images img =  Images.getInstance();
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		yesButton = util.initializeNewButton(-1, -1, -1, -1, img.yesBtnDialogImg,img.yesBtnDialogImgHover);
		buttonPane.add(yesButton);
	
		
		noButton = util.initializeNewButton(-1, -1, -1, -1, img.noBtnDialogImg,img.noBtnDialogImgHover);
		buttonPane.add(noButton);
	
	}
	
	private void l________________________________________l(){}
//	public void setMsg(String [] msgList){
//		int width=lblNewLabel.getColumns();
//		
//		for(int i=0;i<msgList.length;i++){
//			String str=msgList[i];
//			lblNewLabel.append(" ");
//		}
//	}
	
	
	
	

}
