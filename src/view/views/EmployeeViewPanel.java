package view.views;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.prefs.BackingStoreException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;

import model.PayrollTableModel;
import model.Constant;
import model.statics.Images;
import model.statics.Utilities;
import model.view.OptionViewPanel;
import model.view.ReusableTable;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import database.Database;

import javax.swing.JLabel;
import javax.swing.SwingConstants;















import java.awt.Font;

import javax.swing.JButton;

public class EmployeeViewPanel extends JPanel {
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	
	private static EmployeeViewPanel instance;
	private JLabel employeeViewPanelBg;
	private Images img;
	
	private void l_________________________________________________________l(){}
	
	public JPanel topPanel;
	public OptionViewPanel optionPanel;
	public JTextField filterTextField;
	public JButton employeeOptionButton,searchBtn;
	public JLabel rowCountLabel;
	
	private void l_____________________________________________________l(){}
	
	public ReusableTable dynamicTable,fixedTable;
	
	private void l______________________________________________________l(){}
	
	/**
	 * Create the panel.
	 */
	public EmployeeViewPanel() {
	
		set();
	}
	
	
	private void set(){
		setThisPanelComponents();
		setOptionPanelComponents();
		setTopPanelComponents();
		setTableComponents();
		
		//--> Add background image, Note!: always add this when all components are added, add always at the last!
//		add(employeeViewPanelBg);
	}
	
	private void l____________________________l(){}
	
	
	private void setThisPanelComponents(){
		setBounds(0, Constant.VIEW_Y, Constant.VIEW_WIDTH, Constant.VIEW_HEIGHT);
		setBackground(Color.WHITE);
		setLayout(null);
		setVisible(false);
		setOpaque(false);
		
		//--> Initialize images class
		img=Images.getInstance();
		
		//--> Add option button.
		employeeOptionButton = Utilities.getInstance().initializeNewButton(
				175, 6, 47, 47, img.optionBtnImg, img.optionHoverBtnImg
		);
		employeeOptionButton.setFocusable(false);
		add(employeeOptionButton);
				
				
		//--> Add background image label. Always add this at the last!
		employeeViewPanelBg = new JLabel(img.employeeViewPanelBgImg);
		employeeViewPanelBg.setBounds(0, 0, this.getWidth(), this.getHeight());
		
		
	}
	
	private void setOptionPanelComponents(){
		String[] buttonKeyList={"Show Summary","Add Employee","Update/Delete"};
		ImageIcon[] imageList={img.showEmployeeSummaryImg,img.addEmployeeImg,img.updateDeleteImg};
		ImageIcon[] imageHoverList={img.showEmployeeSummaryHoverImg,img.addEmployeeHoverImg,img.updateDeleteHoverImg};
		
		
		optionPanel=new OptionViewPanel(
				buttonKeyList,  img.employeeOptionBgImg, 
				imageList, imageHoverList, 
				180, 48, 104, 81
		);
		add(optionPanel);
		
	}
	
	
	private void setTopPanelComponents(){
		
		topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setBounds(0, 0, 1260, 123);
		topPanel.setBackground(Constant.BILECO_DEFAULT_COLOR_GREEN);
		topPanel.setPreferredSize(new Dimension(Constant.VIEW_WIDTH,100));
		topPanel.setLayout(null);
		add(topPanel);
		
		
		//--> Add View TITLE Label.
		JLabel lblEmployee = new JLabel(img.employeeViewTitleImg);
		lblEmployee.setBounds(14, 14, 161, 26);
		topPanel.add(lblEmployee);

		
		//--> Add Search bar image.
		JLabel searchBar = new JLabel(img.searchBarImg);
		searchBar.setBounds(435, 26, 376, 43);
		
		//--> Add Filter Textfield, Label and Combobox, search button.
		filterTextField = new JTextField();
		filterTextField.setBounds(442, 26, 340, 31);
		filterTextField.setOpaque(false);
		filterTextField.setBorder(BorderFactory.createEmptyBorder());
		filterTextField.setColumns(10);
		filterTextField.setFocusable(true);
		topPanel.add(filterTextField);
		
	
		
		searchBtn = Utilities.getInstance().initializeNewButton(
				782, 30, 23, 24, img.searchBtnImg, img.searchBtnHoverImg
		);
		topPanel.add(searchBtn);
		
		
		topPanel.add(searchBar);
	}
	
	

	
	private void setTableComponents(){
		//--> Set table
		PayrollTableModel tableModel=new PayrollTableModel();
		TableRowSorter<PayrollTableModel> tableSorter = new TableRowSorter<PayrollTableModel>(tableModel);
		
		
		//-------------------------------------------------------------
		fixedTable=new ReusableTable(tableModel, tableSorter, false);
		fixedTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	
//		fixedTable.setAutoCreateRowSorter(true);
		
		//------------------------------------------------------------
		
		dynamicTable=new ReusableTable(tableModel, tableSorter,false);
		dynamicTable.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		dynamicTable.setAutoCreateRowSorter(true);
		
		
		
		
	    //--> Create the scroll pane and add the table to it.
		//--> Remember: To show the horizontal scroll bar, 
		//		the main panel layout must be null/absolute and autoresize of table id OFF.
		//		Since the horizontal scroll bar is not shown if the scrollpane is too long.
		//		Height of a horzontak scrollbar is 20;
		
		JScrollPane fixedTableScrollPane = new JScrollPane(fixedTable);
		fixedTableScrollPane.setBounds(
				10, 
				topPanel.getY()+topPanel.getHeight(),
				Constant.VIEW_WIDTH-500-Constant.SCROLL_BAR_WIDTH,
				this.getHeight()-topPanel.getHeight()-(Constant.SCROLL_BAR_WIDTH+14));
		fixedTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		fixedTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(fixedTableScrollPane);
	
		
		
		
		JScrollPane dynamicTableScrollPane = new JScrollPane(dynamicTable);
		dynamicTableScrollPane.setBounds(
				fixedTableScrollPane.getX()+fixedTableScrollPane.getWidth(),
				topPanel.getY()+topPanel.getHeight(),
				Constant.VIEW_WIDTH-(fixedTableScrollPane.getWidth()+fixedTableScrollPane.getX()+10),
				this.getHeight()-topPanel.getHeight()-20);
		dynamicTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		dynamicTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(dynamicTableScrollPane);
		
		
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
	
	
	
	private void l__________________________l(){}
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
	}
	
	
	private void l_____________________________l(){}
	
	public static EmployeeViewPanel getInstance(){
		if(instance==null)
			instance=new EmployeeViewPanel();
		return instance;
	}
	public static void setInstanceToNull(){
		instance =null;
	}
}
