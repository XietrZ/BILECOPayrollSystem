package model.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.Constant;
import model.MultipleUpdateDatabaseModel;
import model.PayrollTableModel;
import model.SelectConditionInfo;
import model.statics.Images;
import model.statics.Utilities;
import model.view.ReusableTable;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.table.TableRowSorter;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;

import database.Database;

import javax.swing.DefaultComboBoxModel;

import view.MainFrame;


import view.views.EarningViewPanel;
import controller.listeners.ListenerEarningDeductionViewPanel;

import java.awt.GridLayout;

public class EarningsAndDeductionLayout extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	private int topLeftBtnWidth=73, topLeftBtnHeight=34;

	private void l_________________________________l(){}
	public JPanel leftTopPanel;
	public ReusableTable dynamicTable,fixedTable,fullScreenTable, totalFullScreenTable;
	public JButton// optionButton,
		backBtn,
		showBtn,
		 editBtn, calculateBtn, saveBtn, cancelBtn,
		 showEmployerShareBtn,hideEmployerShareBtn;
	public ArrayList<MultipleUpdateDatabaseModel>multipleUpdateList, multipleUpdateEmployerShareList;
	public JLabel label,rowCountLabel;
	public JScrollPane dynamicTableScrollPane,fixedTableScrollPane,fullScreenTableScrollPane,totalTableScrollPane;
	public OptionViewPanel calculationPanel,calculationContractualPanel;
	public JPanel showDisplayPanel;
	public boolean isUpdateColumnComboBox=true;
	public JComboBox<String> payrollDateComboBox,departmentComboBox,columnComboBox;
	public JTextField filterTextField;
	public Utilities util;
	public Images img;
	
	
	private void l________________________________l(){}
	/**
	 * Create the panel.
	 */
	public EarningsAndDeductionLayout() {
		init();
		set();
	}
	
	private void l_____________________l(){}
	private void init(){

		
		
		img=Images.getInstance();
		util=Utilities.getInstance();
	}
	private void set(){
		setThisPanelComponents();
		setCalculationPanelComponents();
		setTopLeftComponents();
		setShowDisplayPanel();
		setTableComponents();
	}
	
	/**
	 * Set the needed components of this particular panel.
	 */
	private void setThisPanelComponents(){
		setBounds(0, Constant.VIEW_Y, Constant.VIEW_WIDTH, Constant.VIEW_HEIGHT);
		setBackground(Color.WHITE);
		setVisible(false);
		setOpaque(false);
		setLayout(null);
		
		
		//--> Add VIEW Title
		label = new JLabel("?");
		label.setBounds(10, 30, 156, 14);
		label.setFont(Constant.VIEW_TITLE);
		add(label);
		
		
		
		//-----------------------------------------------------------------------------------
		
		//--> Add back button
		backBtn = Utilities.getInstance().initializeNewButton(
				1165, 5, 73, 34, img.edBackBtnImg, img.edBackBtnImgHover
		);
		add(backBtn);
		
		//----------------------------------------------------------------------------------
		//--> Add Search bar image.
		int h=52;
		JLabel searchBar = new JLabel(img.searchBarImg);
		searchBar.setBounds(435,h, 376, 43);
		
		//--> Add Filter Textfield, Label and Combobox, search button.
		filterTextField = new JTextField();
		filterTextField.setBounds(442, h, 340, 31);
		filterTextField.setOpaque(false);
		filterTextField.setBorder(BorderFactory.createEmptyBorder());
		filterTextField.setColumns(10);
		filterTextField.setFocusable(true);
		add(filterTextField);
		
	
		
		JButton searchBtn = Utilities.getInstance().initializeNewButton(
				782, h+4, 23, 24, img.searchBtnImg, img.searchBtnHoverImg
		);
		add(searchBtn);
		
		
		add(searchBar);
	}
	
	/**
	 * Set the component of show display panel.
	 */
	private void setShowDisplayPanel(){
		showDisplayPanel=new JPanel();
		showDisplayPanel.setBounds(870, 5,289,87);
		showDisplayPanel.setOpaque(false);
		showDisplayPanel.setLayout(null);
		add(showDisplayPanel);
		
		
		//----------------------------
		showBtn = Utilities.getInstance().initializeNewButton(
				213, 50, 73, 34, img.showButtonImg, img.showButtonImgHover
		);
		showBtn.setToolTipText("Alt+W");
		showDisplayPanel.add(showBtn);
		
		payrollDateComboBox = new JComboBox<String>();
		payrollDateComboBox.setBounds(94,6,107,20);
		showDisplayPanel.add(payrollDateComboBox);
		
		departmentComboBox = new JComboBox<String>();
		departmentComboBox.setBounds(94,33,107,20);
		showDisplayPanel.add(departmentComboBox);
		

		columnComboBox = new JComboBox<String>();
		columnComboBox.setBounds(94,61,107,20);
		showDisplayPanel.add(columnComboBox);
		
		
		
	
		
		//----------------------------
		
		JLabel label = new JLabel(img.showDispOptionGroupIIIBgImg);
		label.setBounds(0, 0, showDisplayPanel.getWidth(),showDisplayPanel.getHeight());
		showDisplayPanel.add(label);
	}
	
	
	private void setCalculationPanelComponents(){
		String[] buttonKeyList=null;
		ImageIcon[] imageList=null;
		ImageIcon[] imageHoverList=null;
		
	
		
		calculationPanel=new OptionViewPanel(
				buttonKeyList,  null, 
				imageList, imageHoverList, 
				150, 44, 144, 75
		);
		add(calculationPanel);
	}
	/**
	 * Set the components of top left button.
	 */
	private void setTopLeftComponents(){
		
		//--> Top left panel
		leftTopPanel = new JPanel();
		leftTopPanel.setBounds(34, 11, topLeftBtnWidth, topLeftBtnHeight);
		leftTopPanel.setBackground(Color.GREEN);
		leftTopPanel.setOpaque(false);
		leftTopPanel.setLayout(new GridLayout(1, 3, 0, 0));
		add(leftTopPanel);
		

		//----------------------------------------------
		
		//--> Edit
		editBtn  = util.initializeNewButton(-1,-1,-1,-1, img.edEditImg, img.edEditImgHover);
		
		//-----------------------------------------------
		//--> Show Employer Share
		showEmployerShareBtn  = util.initializeNewButton(-1,-1,-1,-1, img.showEmployerShareImg, img.showEmployerShareImgHover);
		hideEmployerShareBtn  = util.initializeNewButton(-1,-1,-1,-1, img.hideEmployerShareImg, img.hideEmployerShareImgHover);
		
		//-----------------------------------------------
		
		//--> Save
		saveBtn = util.initializeNewButton(-1,-1,-1,-1, img.edSaveImg, img.edSaveImgHover);
		
		//--> Cancel
		cancelBtn  = util.initializeNewButton(-1,-1,-1,-1, img.edCancelImg, img.edCancelImgHover);

		//--> Calculate
		calculateBtn  = util.initializeNewButton(-1,-1,-1,-1, img.edCalcIconImg, img.edCalcIconImgHover);
				
//		updateTopLeftPanel(3,"");
		
//		leftTopPanel.add(addBtn);
//		leftTopPanel.add(deleteBtn);
//		leftTopPanel.add(editBtn);
//		leftTopPanel.add(calculateBtn);
//		leftTopPanel.add(saveBtn);
//		leftTopPanel.add(cancelBtn);	
	}
	
	
	
	/**
	 * Set the table components
	 */
	private void setTableComponents(){
		int tableY=96;
		
		multipleUpdateList=new ArrayList<MultipleUpdateDatabaseModel>();
		multipleUpdateEmployerShareList=new ArrayList<MultipleUpdateDatabaseModel>();
		
		
		//--> Set table
		
		//> Employee Table
		PayrollTableModel fullScreenTableModel=new PayrollTableModel();
		TableRowSorter<PayrollTableModel>sorter= new TableRowSorter<PayrollTableModel>(fullScreenTableModel); 
		fullScreenTable=new ReusableTable(fullScreenTableModel, sorter, false);
		fullScreenTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		//--> ONE Table Model and Sorter for both fixed and dynamic table.
		PayrollTableModel tableModelForBothFixedDynamicTable=new PayrollTableModel();
		sorter= new TableRowSorter<PayrollTableModel>(tableModelForBothFixedDynamicTable);
		
		//> Fixed Table		
		fixedTable=new ReusableTable(tableModelForBothFixedDynamicTable, sorter, false);
		fixedTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
				
				
		//> Dynamic Table		
		dynamicTable=new ReusableTable(tableModelForBothFixedDynamicTable, sorter,false);
		dynamicTable.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		//> Total Table
		PayrollTableModel totalTableModel=new PayrollTableModel();
		TableRowSorter<PayrollTableModel>totalSorter= new TableRowSorter<PayrollTableModel>(totalTableModel); 
		totalFullScreenTable=new ReusableTable(totalTableModel,totalSorter,true);
		
		//-----------------------------------------------------------------------------
		
	    //--> Create the scroll pane and add the table to it.
		//--> Remember: To show the horizontal scroll bar, 
		//		the main panel layout must be null/absolute and autoresize of table id OFF.
		//		Since the horizontal scroll bar is not shown if the scrollpane is too long.
		//		Height of a horzontak scrollbar is 20;
		
		int minimizeHeight=55;
		fullScreenTableScrollPane = new JScrollPane(fullScreenTable);
		fullScreenTableScrollPane.setBounds(
				Constant.SCROLL_BAR_WIDTH, 
				 tableY,
				Constant.VIEW_WIDTH-Constant.SCROLL_BAR_WIDTH*2,
				this.getHeight()- tableY-(Constant.SCROLL_BAR_WIDTH+14)-minimizeHeight);
		fullScreenTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		fullScreenTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(fullScreenTableScrollPane);
	
		
		
		fixedTableScrollPane = new JScrollPane(fixedTable);
		fixedTableScrollPane.setBounds(
				Constant.SCROLL_BAR_WIDTH, 
				 tableY,
				Constant.VIEW_WIDTH-393-Constant.SCROLL_BAR_WIDTH*2,
				this.getHeight()- tableY-(Constant.SCROLL_BAR_WIDTH+14)-minimizeHeight);
		fixedTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		fixedTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		fixedTableScrollPane.getViewport().setBackground(Color.WHITE);
		add(fixedTableScrollPane);
		
		
		
		
		dynamicTableScrollPane = new JScrollPane(dynamicTable);
		dynamicTableScrollPane.setBounds(
				fixedTableScrollPane.getX()+fixedTableScrollPane.getWidth(),
				 tableY,
				Constant.VIEW_WIDTH-fixedTableScrollPane.getWidth()-Constant.SCROLL_BAR_WIDTH*2,
				this.getHeight()- tableY-20-minimizeHeight);
		dynamicTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		dynamicTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dynamicTableScrollPane.getViewport().setBackground(Color.WHITE);
		add(dynamicTableScrollPane);
		
		
		
		totalTableScrollPane = new JScrollPane(totalFullScreenTable);
		totalTableScrollPane.setBounds(
				Constant.SCROLL_BAR_WIDTH, 
				 dynamicTableScrollPane.getY()+dynamicTableScrollPane.getHeight()+10,
				Constant.VIEW_WIDTH-Constant.SCROLL_BAR_WIDTH*2,
				60);
		totalTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		totalTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(totalTableScrollPane);
		
		//--> Set when you scroll down the dynamic table, the fixed table will scroll down too.
		fixedTableScrollPane.getVerticalScrollBar().setModel(dynamicTableScrollPane.getVerticalScrollBar().getModel());
	
		
			
		//-------------------------------------------------------------------------------------------------------------------
		rowCountLabel=new JLabel("Row Count: 0");
		rowCountLabel.setBounds(
			fixedTableScrollPane.getX(),
			fixedTableScrollPane.getY()+fixedTableScrollPane.getHeight(),
			Constant.ROW_COUNT_LABEL_WIDTH,
			Constant.ROW_COUNT_LABEL_HEIGHT
		);
		add(rowCountLabel);
		
	}
		
	private void l____________________________________________________l(){}
	/**
	 * Execute the search mechanism of all tables.
	 */
	public void executeSearchMechanismOfAllTables(){
		if(dynamicTable.getModel().getRowCount()>0){
			dynamicTable.searchFilterMechanism(filterTextField,rowCountLabel);
		}
		
		//----------------------------------------------------
		if(fixedTable.getModel().getRowCount()>0){
			fixedTable.searchFilterMechanism(filterTextField,rowCountLabel);
	
		}
		
		//----------------------------------------------------
		if(fullScreenTable.getModel().getRowCount()>0){
			fullScreenTable.searchFilterMechanism(filterTextField,rowCountLabel);
	
		}
	}
	
	public void setNecessaryCalculatePanelComponentsWhenExtended(ImageIcon bgImg,int width,int height,
			String[]buttonKeyList, ImageIcon[]imageList, ImageIcon[] imageHoverList){
		
		
		
		//--> Set Calculation Panel
		calculationPanel.setLayout(null);
		calculationPanel.setOpaque(false);
		calculationPanel.bg.setIcon(bgImg);
		calculationPanel.setLocation(218-width,44);
		calculationPanel.setSize(width, height);
		calculationPanel.bg.setBounds(0,0,calculationPanel.getWidth(),calculationPanel.getHeight());
		
		
		calculationPanel.initNewListValues(buttonKeyList, imageList, imageHoverList);
		calculationPanel.addOptionButtons();
		repaint();
		
	}
	
	/**
	 * This class is used to update components of these class when this class is extended 
	 * 		by earning ad deduction view panel.
	 * @param labelNewWidth
	 * @param titleLabelNewHeight
	 * @param labelNewImg
	 * @param showAllDataImg
	 * @param showAllDataImgHover
	 */
	public void setNecessaryOptionPanelComponentsWhenExtended(int titleLabelNewWidths, int titleLabelNewHeight, ImageIcon labelNewImg,
			ImageIcon showAllDataImg, ImageIcon showAllDataImgHover){
		
		//--> Set the  Title Text label of the View
		label.setText("");
		label.setIcon(labelNewImg);
		label.setSize(titleLabelNewWidths,titleLabelNewHeight);
		label.setLocation(getWidth()/2-(label.getWidth()/2),13);
		
		
	}
	
	/**
	 * Set the items in show deduction column combo box 
	 * @param db
	 */
	public void setBothDeductionEarningColumnComboBoxItemValues(Database db, String[] preferredColumnNameList){
		
		if(isUpdateColumnComboBox){
			
			
			//--> Set Display Option PANEL Data Column box
			columnComboBox.removeAllItems();
			columnComboBox.addItem(Constant.STRING_ALL);
			for(int i=3;i<preferredColumnNameList.length;i++){
				columnComboBox.addItem(
					Utilities.getInstance().convertCamelCaseColumnNamesToReadable(
						preferredColumnNameList[i]
					)
				);
			}
			
			
			isUpdateColumnComboBox=false;
		}
	}
	
	
	/**
	 * Store value changes done to the table into MultipleUpdateModel to be used for updating the 
	 * 		values in database. MULTIPLE rows and ONE column.
	 * @param db
	 * @param util
	 * @param preferredTableName
	 * @param tableColumnNameList
	 * @param columnNameReadable
	 * @param table
	 * @throws SQLException
	 */
	public void storeONEChangesNeededToUpdate(Database db, Utilities util, ListenerEarningDeductionViewPanel listener,
			int comboboxSelectedColumnIndex, String preferredTableName,  
			String[] tableColumnNameList, String columnNameReadable,ReusableTable table,EarningsAndDeductionLayout both) throws SQLException{
		
	
		//--> WHy? since in the table, the column that was edited is always at the last column.
		//		It depends if there is ER[employer share]. If it has employer share then it is 
		//		not the last column but the last 2 columns.
		int deductionColumnIndexThatWasEdited=(util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,both) )?  // No need for adding  && listener.isEmployerShareDataShown in the condition since if the chosen column is SSSCont, Pag-ibg Cont and Medicare, the overall columns include the ER.
				table.getModel().getColumnCount()-2:table.getModel().getColumnCount()-1,
			employerShareColumnIndexInTable=table.getModel().getColumnCount()-1; 
		
				
		//--> FInd the real column index in database of the given columnNameReadable since when editing once,
		//		the edited table is at column index 6.
		int columnIndexInDatabase=db.getColumnIndexBasedFromTableColumnNameListAndReadableColumnName(tableColumnNameList, columnNameReadable);
		
		//--> Clear first the multiple update list to avoid redundance of data.
		multipleUpdateList.clear();
		multipleUpdateEmployerShareList.clear();
		
		
		
		//--> Store the deduction/earning id which is the primary key and the columns and corresponding values.
		if(table.getModel().isThereAreChanges()){
			for(int i=0;i<table.getModel().getRowCount();i++){
				
				//--> This if statement makes sure that only those that the values changed when edited are updated.
				if(table.getModel().isEditedRowsList[i]){	
					
					//> Makes sure that the cell under the deduction column in fullscreen table is saved when there is a change.
					if(!table.getModel().data[i][deductionColumnIndexThatWasEdited].toString().
							equals(table.getModel().copiedData[i][deductionColumnIndexThatWasEdited].toString())){
						multipleUpdateList.add(new MultipleUpdateDatabaseModel());
						MultipleUpdateDatabaseModel multiUpdate=multipleUpdateList.get(multipleUpdateList.size()-1);
						multiUpdate.primarykey=table.getModel().getValueAt(i, 0).toString();	
						
							
						multiUpdate.changesToBeUpdated.put(
							tableColumnNameList[columnIndexInDatabase],
							util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(table.getModel().getValueAt(i, deductionColumnIndexThatWasEdited).toString()))
						);
					}
					
					
					//---------------------------------------
					//> WHen there is/are Employer Share Data. This means TWO columns are edited.
					if(util.isSelectedColumnComboboxWithEmployerShare(comboboxSelectedColumnIndex,both) && listener.isEmployerShareDataShown){
						
						
						//> Makes sure that the cell under the employer share column in fullscreen table is saved when there is a change.
						if(!table.getModel().data[i][employerShareColumnIndexInTable].toString().
								equals(table.getModel().copiedData[i][employerShareColumnIndexInTable].toString())){
							
							multipleUpdateEmployerShareList.add(new MultipleUpdateDatabaseModel());
							MultipleUpdateDatabaseModel employerShareMultipleUpdate=multipleUpdateEmployerShareList.get(multipleUpdateEmployerShareList.size()-1);
							
							employerShareMultipleUpdate.primarykey=table.getModel().getValueAt(i, 0).toString();
							employerShareMultipleUpdate.changesToBeUpdated.put(
								util.removeSpacesToBeConvertedToCamelCase(table.getModel().getColumnName(employerShareColumnIndexInTable)),
								util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(table.getModel().getValueAt(i, employerShareColumnIndexInTable).toString()))
							);
						}
					}
					
					
				}
			}
				
		}
		
		
		
		//--> Makes sure that only the edited are updated. Process the SUM.
		if(multipleUpdateList.size()>0){
			
			//--> Store the total column and and its total value. WHy tableColumnNameList.length-5 instead of 3?,
			//		because edited column is not included and the total column is also not included.
			String [] desiredColumnNameList=new String[tableColumnNameList.length-5];
			
			
			//> Get the columns that will be calculated for the sum.The column selected in combobox is not included
			//		to avoid the calculation of the value of the edited column before edit.
			for(int i=0,deductionColumnIndex=3;i<desiredColumnNameList.length;deductionColumnIndex++){
				if(deductionColumnIndex!=columnIndexInDatabase){
					desiredColumnNameList[i]=tableColumnNameList[deductionColumnIndex];
					i++;
				}
			}
			
			//> Get the column name of the edited column to be used as  key to access the edited value
			//		in order to be used in calculating the total. 
			String selectedColumnNameInCombobox=tableColumnNameList[columnIndexInDatabase];
					
			//> Add the needed columns[key] and their values for condition.
			ArrayList<SelectConditionInfo>conditionColumnAndValueList=new ArrayList<SelectConditionInfo>();
	
			
//			// If contractual and earnings, only add SubTotal and Overtime column.
			if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL && both instanceof EarningViewPanel){
				desiredColumnNameList=new String[tableColumnNameList.length-5-2];

				for(int i=0,deductionColumnIndex=5;i<desiredColumnNameList.length;deductionColumnIndex++){
					if(deductionColumnIndex!=columnIndexInDatabase){
						desiredColumnNameList[i]=tableColumnNameList[deductionColumnIndex];
						i++;
					}
				}
			}
			
			
			for(MultipleUpdateDatabaseModel update:multipleUpdateList){
				conditionColumnAndValueList.clear();
				conditionColumnAndValueList.add(new SelectConditionInfo(
						util.addSlantApostropheToString(tableColumnNameList[0]), 
						update.primarykey
				));
				
				//> Get the sum of all the columns except the total and the selected column in combobox.
				db.selectDataInDatabase(
						new String[]{preferredTableName}, 
						desiredColumnNameList, 
						conditionColumnAndValueList,
						null,
						null,
						Constant.SELECT_SUM_WITH_CONDITION
				);
				
				//> Why 1? since the retrieve data from select sum is only one.
				db.resultSet.absolute(1);
				//> In this part since the selected column is not included in getting the sum in database,
				//		the stored edited value will be added to the sum to be able to calculate the complete summation.
				update.changesToBeUpdated.put(
						tableColumnNameList[tableColumnNameList.length-1], 
						""+util.convertRoundToOnlyTwoDecimalPlaces((Double.parseDouble(db.resultSet.getObject(1).toString()) 
								+ (Double)update.changesToBeUpdated.get(selectedColumnNameInCombobox)))
				);
			}
		}
		
		
		
		//--> For debugging purposes
		System.out.println("\t Stored multiple changes to be updated"+CLASS_NAME);
		for(MultipleUpdateDatabaseModel multipleUpate:multipleUpdateList){
			System.out.println("\t\tPrimary Key: "+multipleUpate.primarykey);
			for(String keyColumnName:multipleUpate.changesToBeUpdated.keySet()){
				System.out.println("\t\t\tColumn Name: "+keyColumnName
						+"\t Value: "+multipleUpate.changesToBeUpdated.get(keyColumnName));
			}
		}
		System.out.println();
	}
	
	/**
	 * Store the changes that are needed to update when editing MULTIPLE columns and MULTIPLE/ONCE rows.
	 * @param preferredTableName
	 * @param preferredTableColumnNameList
	 * @param listener
	 */
	public void storeMULTIPLEChangesNeededToUpdate(String preferredTableName, 
			String[] preferredTableColumnNameList,ListenerEarningDeductionViewPanel listener,
			EarningsAndDeductionLayout bothEarnDed){
		
		multipleUpdateList.clear();
		multipleUpdateEmployerShareList.clear();
		
		PayrollTableModel dynamicModel=dynamicTable.getModel();
		boolean [] isEditedRowsList=dynamicModel.isEditedRowsList;
		
		
		
		//--> Get the rows changes and store it to a storage before updating the database.
		if(dynamicModel.isThereAreChanges()){
			for(int i=0;i<isEditedRowsList.length;i++){
				if(isEditedRowsList[i]){
					
					//--> If the eduted table data is EMPLOYER SHARE data.
					if(listener.isEmployerShareDataShown){
						multipleUpdateEmployerShareList.add(new MultipleUpdateDatabaseModel());
						MultipleUpdateDatabaseModel multipleEmployerShareUpdate=multipleUpdateEmployerShareList.get(multipleUpdateEmployerShareList.size()-1);
						
						//--> Store the primary key
						multipleEmployerShareUpdate.primarykey=fixedTable.getModel().getValueAt(i, 0).toString();
						
						
						//--> Store the values, do not put !data[][].equals(copiedData[][] since it will cause error. Data truncated error
						//>		Why 7? since the started column SSSEr to be edited is at index 7 of the table column,
						for(int j=7;j<dynamicModel.getColumnCount();j++){
							String columnNameToBeSaved=util.removeSpacesToBeConvertedToCamelCase(dynamicModel.getColumnName(j));
							Double valueToBeSaved=Double.parseDouble(dynamicModel.getValueAt(i, j).toString());
							
							multipleEmployerShareUpdate.changesToBeUpdated.put(columnNameToBeSaved, util.convertRoundToOnlyTwoDecimalPlaces(valueToBeSaved));
					
						}
					}
					//--> If the edited table data is/are DEDUCTION data.
					else{
						multipleUpdateList.add(new MultipleUpdateDatabaseModel());
						MultipleUpdateDatabaseModel multipleUpdate=multipleUpdateList.get(multipleUpdateList.size()-1);
						
						//--> Store the primary key
						multipleUpdate.primarykey=fixedTable.getModel().getValueAt(i, 0).toString();
						
						double sum=0;
						//--> Store ALL column data.
						//		Why 3? since the edited columns start at index 3 in earn/ded column list.
						// Why 7? because the combined column all in all From Earn/Ded ID -> MonthlyFixedSalary is 7.
						//		Why length-1? since the total value is not included in the edit.
						
						
						for(int j=3,k=7;j<preferredTableColumnNameList.length-1;j++,k++){
							multipleUpdate.changesToBeUpdated.put(
									preferredTableColumnNameList[j],
									util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(dynamicModel.data[i][k].toString())));
							
							if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL && bothEarnDed instanceof EarningViewPanel ){
								if(j>=5){
									sum+=Double.parseDouble(dynamicModel.data[i][k].toString());
								}
							}
							else{
								sum+=Double.parseDouble(dynamicModel.data[i][k].toString());
							}
						}
						//--> After calculating the sum, put the value of sum to storage and the last column name.
						multipleUpdate.changesToBeUpdated.put(
								preferredTableColumnNameList[preferredTableColumnNameList.length-1],
								util.convertRoundToOnlyTwoDecimalPlaces(sum));
					}
				}
			}
		
		}
		System.out.println("\t Sizeeeeeeeeeee:"+multipleUpdateList.size()+CLASS_NAME);
	}
	
	
	
	/**
	 * Update the shown buttons in top left button.
	 * @param numoFBtns
	 */
	public void updateTopLeftPanel(int numoFBtns){
		//> Remove all
		leftTopPanel.remove(editBtn);
		leftTopPanel.remove(showEmployerShareBtn);
		leftTopPanel.remove(hideEmployerShareBtn);
		leftTopPanel.remove(calculateBtn);
		leftTopPanel.remove(saveBtn);
		leftTopPanel.remove(cancelBtn);
		
		
		//--> Manipulations
		
		//--> When add button is only shown:
		if(numoFBtns==1){
			//> Add necessary
			leftTopPanel.add(editBtn);
		}
		if(numoFBtns==2){
			//> Add necessary
			leftTopPanel.add(editBtn);
			leftTopPanel.add(showEmployerShareBtn);
			leftTopPanel.add(hideEmployerShareBtn);
		}
		//--> When calculate, save, cancel buttons are only shown.
		else if(numoFBtns==3){
			//> Add necessary
			leftTopPanel.add(saveBtn);
			leftTopPanel.add(cancelBtn);
			leftTopPanel.add(calculateBtn);
		}

		
		//> Set and add components
		leftTopPanel.setSize(topLeftBtnWidth*numoFBtns,topLeftBtnHeight);
		leftTopPanel.revalidate();
		leftTopPanel.repaint();
	}
}


