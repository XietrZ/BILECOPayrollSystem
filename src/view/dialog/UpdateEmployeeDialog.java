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
import javax.swing.text.StyleContext.SmallAttributeSet;

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
import model.view.YesOrNoIfDeleteDialogLayout;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;

import java.awt.Font;
import java.sql.SQLException;
import java.util.HashMap;
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

public class UpdateEmployeeDialog extends JDialog {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private static UpdateEmployeeDialog instance;
	private JScrollPane scrollPane;
	private Utilities util;
	private Images img;
	private Color colorBG;
	
	private void l____________________________________________l(){}
	public EmployeeBasicInfoDefaultLayout contentPanel;
	
	public JButton saveButton,cancelButton,editButton,backButton;
	

	private void l__________________________________________________l(){}
	public HashMap<String,Object> beforeEditContentStorage;
	public HashMap<String,Object> componentContentChangesList;
	private void l______________________________________________l(){}
	public String neededIDValue;
	private void l___________________________________________________l(){}
	/**
	 * Create the dialog.
	 */
	public UpdateEmployeeDialog() {
		
		
		set();
		init();
		
		
	}
	
	private void set(){
			getContentPane().setBackground(Color.WHITE);
			setBounds(100, 100, 500, 400);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setModal(true);
	//		setUndecorated(true);
			setLocationRelativeTo(null);
			setTitle("Employee Basic Info");
			setResizable(false);
			setLayout(new BorderLayout());
	
		}
	private void init(){
//		employeeTableStorageComponent=new HashMap<String,JComponent>();
		beforeEditContentStorage=new HashMap<String,Object>();
		componentContentChangesList=new HashMap<String,Object>();
		img=Images.getInstance();
		util= Utilities.getInstance();
		colorBG = new Color(48,120,161);
		
		//--> Initialize swing components
		initContentPanel();
		initButtonPanel();
		
		
		
		
	}
	
	private void l________________________________________________l(){}
	
	private void initButtonPanel(){
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(colorBG);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.NORTH);
		
		
		saveButton = util.initializeNewButton(-1,-1, -1, -1, img.saveBtnDialogImg, img.saveBtnDialogImgHover);
		buttonPane.add(saveButton);
		saveButton.setVisible(false);
		
		cancelButton  = util.initializeNewButton(-1,-1, -1, -1, img.cancelBtnDialogImg, img.cancelBtnDialogImgHover);
		buttonPane.add(cancelButton);
		cancelButton.setVisible(false);
		
		
		editButton  = util.initializeNewButton(-1,-1, -1, -1, img.editBtnDialogImg, img.editBtnDialogImgHover);
		buttonPane.add(editButton);
		
		backButton = util.initializeNewButton(-1,-1, -1, -1, img.backBtnDialogImg, img.backBtnDialogImgHover);
		buttonPane.add(backButton);
	

		
	}
	
	
	
	private void initContentPanel(){
		
		//--> Content Panel
		contentPanel = new EmployeeBasicInfoDefaultLayout(this.getWidth(), this.getHeight());
		contentPanel.setBackground(colorBG);
//		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		contentPanel.generateEmployeeIDButton.setVisible(false);
		
	
		
		//--> ScrollPane Code
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(contentPanel);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		
			
	}
	private void l__________________________________l(){}
	
	
	
	/**
	 * Store all contents to a hashmap to be used for comparison later.
	 * @param db
	 */
	private void storeAllContentsBeforeEdit(Database db){
		beforeEditContentStorage.clear();
		for(int i=0;i<db.employeeTableColumnNames.length;i++){
			beforeEditContentStorage.put(
					db.employeeTableColumnNames[i], 
					util.getComponentStringValue(contentPanel.componentsList.get(db.employeeTableColumnNames[i])));
		}
	}
	
	
	
	private void l______________________________l(){}
	
	/**
	 * Checks if there are changes when updating.
	 * @param db
	 * @return
	 */
	public boolean isThereChangesInComponentsContent(Database db){
		int i=0,counter=0;
		componentContentChangesList.clear();
		for(i=0;i<contentPanel.componentsList.size();i++){
			
			
			if(!((util.getComponentStringValue(contentPanel.componentsList.get(db.employeeTableColumnNames[i])))
					.equals(beforeEditContentStorage.get(db.employeeTableColumnNames[i])))){
				
					componentContentChangesList.put(
							db.employeeTableColumnNames[i], // Key/ColumnName
							((""+db.departmentTableColumnNames[0]).equals(db.employeeTableColumnNames[i]))?
									db.getKeyOfDepartmentData(util.getComponentStringValue(contentPanel.componentsList.get(db.employeeTableColumnNames[i]))) // Values
									: util.getComponentStringValue(contentPanel.componentsList.get(db.employeeTableColumnNames[i]))
					);
				
				
				counter++;
			}
		}
		
		if(counter==0){
			componentContentChangesList.clear();
			return false;
		}
		
		// Set the DateLeft where if it is empty, change to 1900-01-01
		String colName="DateLeft";
		componentContentChangesList.put(
			colName,
			( util.getComponentStringValue(contentPanel.componentsList.get(colName)).isEmpty())?
					Constant.STRING_DATE_LEFT_NULL
					:
					util.getComponentStringValue(contentPanel.componentsList.get(colName))
		);
		return true;
	}
	
	
	/**
	 * Update the necessary fields based from the selected employee to be edit.
	 * @param db
	 */
	public void updateAllFieldsBasedFromSelectedEmployee(Database db){
		EmployeeBasicInfoDefaultLayout cp=contentPanel;
		
	//--> Store all of swing components into a hashmap for easier use.
		contentPanel.storeAllUIComponentsToAHashMap(db);
				
		try {
			db.resultSet.first();
			
			
			for(int i=0;i<db.metaData.getColumnCount();i++){
				contentPanel.componentsList.put(
						db.employeeTableColumnNames[i], 
						util.setComponentStringValue(
								contentPanel.componentsList.get(db.employeeTableColumnNames[i]), // jcomponent
								((""+db.departmentTableColumnNames[0])).equals(db.employeeTableColumnNames[i])? // If column is DepartmentID
										db.departmentDataList.get(db.resultSet.getObject(i+1))
										:(db.resultSet.getObject(i+1)==null)?""	// value, if null just put empty string
											:(db.resultSet.getObject(i+1).toString().equals(Constant.STRING_DATE_LEFT_NULL))?"":db.resultSet.getObject(i+1).toString() // if the retrieve date is 1900-01-01 which means null version of date 
											
						)
				);
			}
			
			
			
			//--> This method is here to makae sure the hashmap that stores the swing JComponents has beed stored
			storeAllContentsBeforeEdit(db);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainFrame.getInstance().showOptionPaneMessageDialog(
					e.getMessage(), 
					JOptionPane.ERROR_MESSAGE);
			cp.clearAllFields();
		}



	}
	
	private void l_____________________________l(){}
	
	public static UpdateEmployeeDialog getInstance(){
		if(instance==null)
			instance=new UpdateEmployeeDialog();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
