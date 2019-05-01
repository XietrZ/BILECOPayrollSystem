package view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import model.DateLabelFormatter;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;
import model.view.EmployeeBasicInfoDefaultLayout;

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

import view.MainFrame;

public class AddEmployeeDialog extends JDialog {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static AddEmployeeDialog instance;
	private JScrollPane scrollPane;
	private Images img;
	private Utilities util;
	private Color colorBG;
	private void l____________________________________________l(){}
	
	public JButton saveButton,cancelButton;
	private void l______________________________________l(){}
	public EmployeeBasicInfoDefaultLayout contentPanel;
	
	

	

	private void l______________________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public AddEmployeeDialog() {
		
		
		set();
		init();
		
		
	}
	
	private void set(){
			getContentPane().setBackground(Constant.DIALOG_BOX_COLOR_BG);
			setBounds(100, 100, 500, 400);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setModal(true);
	//		setUndecorated(true);
			setLocationRelativeTo(null);
			setTitle("Add Employee");
			setResizable(false);
	
		}
	private void init(){
		img=Images.getInstance();
		util= Utilities.getInstance();
		colorBG = new Color(48,120,161);
		
		
		getContentPane().setLayout(new BorderLayout());
		
		
		initContentPanel();
		initButtonPanel();
		
		
		
		
	}
	
	private void l________________________________________________l(){}
	
	private void initButtonPanel(){
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(colorBG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		buttonPane.setPreferredSize(new Dimension(-1,100));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		
		saveButton = util.initializeNewButton(-1, -1, -1, -1, img.saveBtnDialogImg, img.saveBtnDialogImgHover);
		buttonPane.add(saveButton);
//		getRootPane().setDefaultButton(saveButton);
		
		cancelButton= util.initializeNewButton(-1, -1, -1, -1, img.cancelBtnDialogImg, img.cancelBtnDialogImgHover);
		buttonPane.add(cancelButton);
//		getRootPane().setDefaultButton(cancelButton);
	

		
	}
	
	
	
	private void initContentPanel(){
		
		//--> Content Panel
		contentPanel = new EmployeeBasicInfoDefaultLayout(this.getWidth(), this.getHeight());
		contentPanel.setBackground(colorBG);
//		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		
		//--> ScrollPane Code
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(contentPanel);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
	
		
//		
//		//--> Change this size if you want to resize the panel. Longer width than scroll pane, the scroll will appear.
//		contentPanel.setPreferredSize(new Dimension(this.getWidth()-30,
//				(contentPanel.namePanel.getHeight()
//						+contentPanel.hiringDetailsPanel.getHeight()
//						+contentPanel.jobDetailsPanel.getHeight()
//						+contentPanel.identificationPanel.getHeight()))); 
//		
		
		
	}
	private void l___________________________l(){}
	
	
	/**
	 * Checks if all necessary fields have data.
	 * @param db
	 * @param frame
	 * @return
	 */
	public boolean isFilledUpNecessaryFields(Database db,MainFrame frame){
		contentPanel.storeAllUIComponentsToAHashMap(db);
		
		for(String key:contentPanel.componentsList.keySet()){
			JComponent component=contentPanel.componentsList.get(key); 
			if(component instanceof JTextField && ((JTextField) component).getText().isEmpty()){
				frame.showOptionPaneMessageDialog("Please input data to necessary fields", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	private void l_____________________________l(){}
	
	public static AddEmployeeDialog getInstance(){
		if(instance==null)
			instance=new AddEmployeeDialog();
		return instance;
	}
	
	public static void setInstanceToNull(){
		instance =null;
	}
	
}
