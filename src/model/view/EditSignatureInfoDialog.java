package model.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import model.DateLabelFormatter;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;

import java.awt.Font;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;

import com.toedter.calendar.JDateChooser;

import database.Database;

import javax.swing.UIManager;
import javax.xml.crypto.dsig.keyinfo.PGPData;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.GridLayout;

import javax.swing.SwingConstants;

public class EditSignatureInfoDialog extends JDialog {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private Utilities util;
	private Images img;
	private FileNameExtensionFilter filter;
	private void l____________________________________________l(){}
	public String filePath;
	public JFileChooser signatureImgFileChooser;
	
	public JLabel signatureLabel;
	public JButton saveButton,addSignatureImgBtn,clearSignatureImgBtn;
	
	private void l______________________________________l(){}
	private JPanel contentPanel;
	public EditSignatureDataPanel preparedByPanel, checkedByPanel, auditedByPanel, approvedByPanel;
	
	private void l______________________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public EditSignatureInfoDialog() {
		set();
		init();
		
	}
	
	private void set(){
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 358, 490);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
//		setUndecorated(true);
		setLocationRelativeTo(null);
		setTitle("Edit Signature Info");
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
	
	}
	private void init(){
		util = Utilities.getInstance();
		img = Images.getInstance();
		
		initContentPanel();
		initContentNorthPanel();
		initContentBottomPanel();
		initButtonPanel();	
	}
	
	private void l________________________________________________l(){}
	
	/** 
	 * Initialize components of button panel
	 */
	private void initButtonPanel(){
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		//--------------------------------------------------------------

		saveButton = util.initializeNewButton(-1, -1, -1, -1, img.saveBtnDialogImg, img.saveBtnDialogImgHover);
		buttonPane.add(saveButton);
		getRootPane().setDefaultButton(saveButton);
		
	
	

		
	}
	
	
	private void l______________________________________________________l(){}
	
	
	/**
	 * Initialize components of content panel.
	 */
	private void initContentPanel(){
		
		//--> Content Panel
			contentPanel = new JPanel();
			contentPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
//			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPanel.setLayout(new BorderLayout());
			getContentPane().add(contentPanel, BorderLayout.CENTER);
		
			
			
			
			
		//--------
			JPanel contentCENTERPanel = new JPanel();
			contentCENTERPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
//			contentCENTERPanel.setPreferredSize(new Dimension(-1, 10));
			contentPanel.add(contentCENTERPanel, BorderLayout.CENTER);
			
			
		
			
		
			
			


		//--------------------------------------------------------------

//		//--> Change this size if you want to resize the panel. Longer width than scroll pane, the scroll will appear.
//		contentPanel.setPreferredSize(new Dimension(this.getWidth()-30,
//				(contentPanel.namePanel.getHeight()
//						+contentPanel.hiringDetailsPanel.getHeight()
//						+contentPanel.jobDetailsPanel.getHeight()
//						+contentPanel.identificationPanel.getHeight()))); 
//		
	}
	
	
	private void initContentNorthPanel(){
		//--> Content Panel
		JPanel contentNORTHPanel = new JPanel();
		contentNORTHPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		contentNORTHPanel.setLayout(new GridLayout(4, 1, 1, 1));
		contentPanel.add(contentNORTHPanel, BorderLayout.NORTH);
		
		
		
		//-----------------------------------------
		preparedByPanel=new EditSignatureDataPanel();
		contentNORTHPanel.add(preparedByPanel);
		preparedByPanel.getLblPreparedByData().setText("Prepared By Data");
		
		checkedByPanel=new EditSignatureDataPanel();
		contentNORTHPanel.add(checkedByPanel);
		checkedByPanel.getLblPreparedByData().setText("Checked By Data");
		
		auditedByPanel=new EditSignatureDataPanel();
		contentNORTHPanel.add(auditedByPanel);
		auditedByPanel.getLblPreparedByData().setText("Audited By Data");
		
		approvedByPanel=new EditSignatureDataPanel();
		contentNORTHPanel.add(approvedByPanel);
		approvedByPanel.getLblPreparedByData().setText("Approved By Data");
		
	}
	
	private void initContentBottomPanel(){
		JPanel contentBOTTOMPanel = new JPanel();
		contentBOTTOMPanel.setLayout(new BorderLayout());
		contentBOTTOMPanel.setPreferredSize(new Dimension(-1, 130));
		contentBOTTOMPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		contentPanel.add(contentBOTTOMPanel, BorderLayout.SOUTH);
		
		//-------------------------------------
		//> Label
		JLabel label = new JLabel("Signature of person who prepared this payroll:");
		label.setForeground(Color.WHITE);
		contentBOTTOMPanel.add(label,BorderLayout.NORTH);
		
		//-------------------------------------
		//> The signature image mismo
		JPanel centerPanel=new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		contentBOTTOMPanel.add(centerPanel,BorderLayout.CENTER);
		
		
		signatureLabel=new JLabel();
//		signatureLabel.setSize(signatureImgIcon.getIconWidth(), signatureImgIcon.getIconHeight()+100);
		signatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
		centerPanel.add(signatureLabel);
//		System.out.println("POTAASASAAAQQQQ: "+signatureLabel.getWidth()
//				+"\t "+signatureLabel.getHeight()+CLASS_NAME);
		
		//--------------------------------------- 
		//> Ang buttons
		JPanel southPanel=new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.setBackground(Constant.DIALOG_BOX_COLOR_BG);
		contentBOTTOMPanel.add(southPanel,BorderLayout.SOUTH);
		
		addSignatureImgBtn =new JButton("Add Signature Image");
		southPanel.add(addSignatureImgBtn);
		clearSignatureImgBtn =new JButton("Clear");
		southPanel.add(clearSignatureImgBtn);
		
		//---------------------------------------
		signatureImgFileChooser= new JFileChooser();
		filter = new FileNameExtensionFilter("JPG,JPEG and PNG", "png","jpg","jpeg");
		signatureImgFileChooser.setFileFilter(filter);
	}
}
