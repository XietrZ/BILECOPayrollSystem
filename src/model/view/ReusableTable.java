package model.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.SwingConstants;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import view.MainFrame;
import view.views.EmployeeViewPanel;
import database.Database;
import model.Constant;
import model.DecimalFormatRenderer;
import model.PayrollTableModel;
import model.statics.Utilities;

/**
 * This table is a model used when creating a table.
 * @author XietrZ
 *
 */
public class ReusableTable extends JTable{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	
	private PayrollTableModel model;
	private TableRowSorter<PayrollTableModel> sorter;

	private void l_______________________________________l(){}
	public boolean isAutoResize;
	private void l_____________________________________l(){}
	
	public ReusableTable(PayrollTableModel model,TableRowSorter<PayrollTableModel> sorter,boolean isResize){
		super();
		this.model = model;
		this.sorter=sorter;
		this.isAutoResize=isResize;
		
		
		setModel(this.model);
		if(sorter!=null)
			setRowSorter(this.sorter);
		setFillsViewportHeight(true);
		
		//--> Set the columns drag and drop disable.
		getTableHeader().setReorderingAllowed(false);
		
		//--> Sort the table data when you click the column header, auto sorter.
//	    setAutoCreateRowSorter(true); 
		
	}
	private void l______________________________________________L(){}
	
	
	
	/**
	 * Set renderer for cells in table where values are number/double.
	 * Instead of using the default renderer, we made our own so that we can edit it
	 * 		 and manipulate the renderer whatever we want.
	 * 
	 */
	private void setRendererForValuesThatAreDouble(){
		for(int j=0;j<model.getColumnCount();j++){
			if(model.getColumnClass(j).toString().equals(Utilities.getInstance().compareClassObject(Constant.CLASS_OBJECT_DOUBLE))
					&& getDefaultRenderer(model.getColumnClass(j))!=Constant.DecimalFormatRenderer){
				
				this.setDefaultRenderer(model.getColumnClass(j),Constant.DecimalFormatRenderer);				
			}
			
			
		}
		
		
	}
	
	/**
	 * Set renderer of all dates from database.
	 */
	private void setRendererForValuesThatAreDate(){
		for(int j=0;j<model.getColumnCount();j++){
			
			//--> Debugging Purposes
//			System.out.println("\tZZZFUCKKASAS:"
//					+"\t J: "+j
//					+"\tColumn Class: "+model.getColumnClass(j).toString()
//					+"\tIsDate: "+model.getColumnClass(j).toString().equals(Utilities.getInstance().compareClassObject(Constant.CLASS_OBJECT_DATE))
//					+"\tColumn Renderer: "+getDefaultRenderer(model.getColumnClass(j))+CLASS_NAME);
			
			if(model.getColumnClass(j).toString().equals(Utilities.getInstance().compareClassObject(Constant.CLASS_OBJECT_DATE))
					&& getDefaultRenderer(model.getColumnClass(j))!=Constant.DateFormatRenderer){
				System.out.println("\t\tSULOD---> YYYYYYYYYYYYYY:"
					+"\tColumn Name: "+model.getColumnName(j)+CLASS_NAME);
				this.setDefaultRenderer(model.getColumnClass(j),Constant.DateFormatRenderer);				
			}
			
			
		}
	}
	
	private void l_____________________________________________________________________l(){}
	/**
	 * Clear all contents of this table.
	 */
	public void clearAllContentsOfTable(){
		Database db = Database.getInstance();
		db.resultSet=null;
		db.metaData=null;
		
		updateTable(db);
	}
   /**
    * Hide columns based from given column indices lists.
    * @param columnIndexList
    */
	public void hideColumns(int [] columnIndexList){
		if(columnIndexList!=null){
		
			TableColumnModel columnModel=this.getColumnModel();
			
			for(int index:columnIndexList){
				TableColumn column=columnModel.getColumn(index);
				columnModel.removeColumn(column);
			}
		}
	}
	/**
	 * Round off all data to two decimal places.
	 * This method is used on TOTAL Table.
	 */
	public void roundOffAllDataToTwoDecimal(){
		Utilities util = Utilities.getInstance();
		for(int i=0;i<model.getRowCount();i++){
			for(int j=0;j<model.getColumnCount();j++){
				model.data[i][j]=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(model.data[i][j].toString()));
				model.copiedData[i][j]=model.data[i][j];
			}
		}
		
		updateTableStateNotContent();
	}
	
	/** 
     * Update the row filter regular expression from the expression in
     * 		the text box.
     * The index parameter indicates what column is it now the search
     * 		is based for.
     */
    public void searchFilterMechanism(JTextField textField,JLabel rowCountLabel) {
    	String txtFieldStr=textField.getText();
    	Utilities util= Utilities.getInstance();
//    	System.out.println("\t FILTER Search Mechanism: "+txtFieldStr
//    			+"\t Convert COMPLETE DATE: "+util.convertDateReadableToYyyyMmDdDate(txtFieldStr)
//    			+"\t Is String a three letter month: "+util.isStringAThreeLetterMonth(txtFieldStr)
//    			+"\t Cponvert Three Letter Month: "+util.convertThreeLetterMonthNameToNumber(txtFieldStr)
//    			+CLASS_NAME);
    	
    	if(this.getColumnCount()>0){
    		
	        RowFilter<PayrollTableModel, Object> rf = null;
	  
	        //If current expression doesn't parse, don't update.
	        try {  
	        	txtFieldStr=util.convertThreeLetterMonthNameToNumber(txtFieldStr);
	        	txtFieldStr=util.convertDateReadableToYyyyMmDdDate(txtFieldStr);
	            rf = RowFilter.regexFilter("(?i)"+txtFieldStr); // Adding (?i) makes it insensitive.
	        } catch (java.util.regex.PatternSyntaxException e) {
	            return;
	        }
	            
	        sorter.setRowFilter(rf);
	        
	        //--> Update Row Count Label
	        rowCountLabel.setText("Row Count: "+sorter.getViewRowCount());
	    	 
    	}
    }
    
    /**
     * Set all table cells not editable.
     * 		This method is important to make the code more readable.
     */
    public void setAllTablesNotEditable(Database db,int editMode){
    	model.columnsToBeEditedList.clear();
    	
    	if(editMode==Constant.EDIT_ONCE){
    		updateTable(db);
    	}
    	else if(editMode==Constant.EDIT_MULTIPLE){
    		updateTableStateNotContent();
    	}
    }
    
    /**
     * Add the columns to be edited so to a variable storage in the model
     * 		so that the table will be updated.
     * @param columnList
     */
    public void setTableColumnsThatAreEditable(String[] columnList){
    	model.columnsToBeEditedList.clear();
    	for(String str:columnList){
    		model.columnsToBeEditedList.add(str);
    		
    	}
    }
    
    
   	/**
   	 * Set the column with fixed width. 
   	 * @param fixedWithValue
   	 * @param startColumnIndex
   	 * @param endColumnIndex
   	 */
   	public void setWidthofColumnFixed(int startColumnIndex, int endColumnIndex){

   	    TableColumn column = null;
           for (int i = startColumnIndex; i < (endColumnIndex+1); i++) {
        	   String columnName=getColumnName(i);
        	   column = this.getColumnModel().getColumn(i);
        	   
        	   int width=Constant.TABLE_COLUMN_FIXED_WIDTH;//columnName.length()*10;
               column.setMaxWidth(width);
               column.setMinWidth(width);
           }   
   	}
   	
   	
    
	/** 
	 * Update data on table
	 * @param db
	 */
	public void updateTable(Database db){
		try {
			//--> Put the cursor at the beginning of the table.
			if(db.resultSet!=null)
				db.resultSet.beforeFirst(); 
			
			//--> Update the resultset and metadata values.
			model.setTableModelResultSetAndMetaData(db.resultSet, db.metaData);
			
			//--> Set the renderer of cells in the table where values are number/double
			setRendererForValuesThatAreDouble();
			
			//--> Set the remderer of cells where values are in DATE
			setRendererForValuesThatAreDate();
		
			
			//--> Updates the table
			model.fireTableStructureChanged();
			
		
			//--> Make column NOT resizable.
			this.getTableHeader().setResizingAllowed(false);  // Cannot resize the column header.
			
		
			
			
			//--> How to wrap text, did not include yet for modficiations, uncomment to see what happens!
//			System.out.println("\t\tDUCKKASA: "+this.getColumnModel().getColumnCount()+CLASS_NAME);
//			if(this.getColumnModel().getColumnCount()>0)
//				this.getColumnModel().getColumn(0).setCellRenderer(Constant.WordWrapCellRenderer);
//			

			
			//-> If the columns are autoresize or not.
			if(!isAutoResize && db.resultSet!=null && db.metaData!=null){
				//--> The column width is not auto resize. This code finally stop the table 
					//		to autoresize the columns to fit in the panel and finally shows the
					//		horizontal scrollpane for the table.
				this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
				
				
				//--> Set the column witdh of the table Fixed.
				//		Why 1? since we did not show the ID column as we did above, 
				//		Why db.metaData.getColumnCount()-1-countRemoveColumn? we subtract the total column by the number of column that we did not show.
				setWidthofColumnFixed( 0, db.metaData.getColumnCount()-1);
				
				System.out.println("\tAutoResize Columns: OFF"+CLASS_NAME);
			}
			else{
				this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
				
				System.out.println("\tAutoResize Columns: ON"+CLASS_NAME);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			System.out.println(""+e.getMessage()+CLASS_NAME);
			MainFrame.getInstance().showOptionPaneMessageDialog(
					""+e.getMessage(), 
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			
		}
		
		
	}
	
	/**
	 * Just update the state of the table, NOT the content.
	 */
	public void updateTableStateNotContent(){
		
		model.fireTableStructureChanged();
	
		
		if(!isAutoResize){
			setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
			setWidthofColumnFixed( 0, getColumnCount()-1);
		}
	}
	private void l_________________________________________l(){}
	//--> Default method of JTable, overriden.
	
	@Override
	/**
	 * Use this code when highlighting rows/columns in tables.
	 */
	public Component prepareRenderer(TableCellRenderer renderer, final int row, final int column) {
		int row_new=convertRowIndexToModel(row); // if not convertRowIndexToModel, some columns will not be highlighted when some columns are hidden.
		int column_new=convertColumnIndexToModel(column);
		
	    Component c = super.prepareRenderer(renderer, row, column);
	    Database db= Database.getInstance();
	    
	    //--> Highlight editable cells
	    if(model.isCellEditable(row_new, column_new)){
	    	c.setBackground(Constant.HIGHLIGHT_EDITABLE_ROW);
	    }
	    else{
	    	c.setBackground(Color.WHITE);
	    }
	    
	    
	    //--> Highlight Total Columns for earnings and deduction:
	    String columnNameCamelCase =Utilities.getInstance().removeSpacesToBeConvertedToCamelCase(model.getColumnName(column_new));
	    if(columnNameCamelCase.equals(db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]) // TotalDeduction
	    		|| columnNameCamelCase.equals(db.earningTableColumnNames[db.earningTableColumnNames.length-1]) // TotalEarnings
	    		|| columnNameCamelCase.equals(db.employeeTableColumnNames[11])){ // MonthlyBasicSalary
	    	c.setBackground(new Color(204, 204, 204));
	    }
	    //--> Highlight Overall
	    if(columnNameCamelCase.equals("Overall"+db.deductionTableColumnNames[db.deductionTableColumnNames.length-2]) // TotalDeduction
	    		|| columnNameCamelCase.equals("Overall"+db.earningTableColumnNames[db.earningTableColumnNames.length-1]) // TotalEarnings
	    		|| columnNameCamelCase.equals("Overall"+db.employeeTableColumnNames[11]) || columnNameCamelCase.equals("OverallBasicSalary")){ // MonthlyBasicSalary
	    	c.setBackground(new Color(204, 204, 204));
	    	
	    	
	    }
	    
	    //--> Highlight when column name is NetPay
	    if(columnNameCamelCase.equals("NetPay")||columnNameCamelCase.equals("OverallNetPay")){
	    	if(model.isCellEditable(row_new, column_new)){
		    	c.setBackground(Constant.HIGHLIGHT_EDITABLE_ROW);
		    }
	    	else{
	    		c.setBackground(new Color(205, 207, 241));
	    	}
	    }
	    
	    //--> Highligh FamilyName and FirstName and Middle Name
	    if(columnNameCamelCase.equals(db.employeeTableColumnNames[2]) ||
	    		columnNameCamelCase.equals(db.employeeTableColumnNames[3]) ||
	    		columnNameCamelCase.equals(db.employeeTableColumnNames[4])){
	    	c.setBackground(new Color(242, 184, 79));
	    }
	    
	    //--> Highlight chosen cells
	    int []selectedRowsList=getSelectedRows();
	    for(int i=0;i<selectedRowsList.length;i++){
	    	if(row==selectedRowsList[i])
	    		c.setBackground(Constant.HIGHLIGHT_SELECTED_ROW);
	    }
	    
	    
	    
	   
	    return c;
	}
	
	

	
	
	private void l______________________________________l(){}

	public PayrollTableModel getModel() {
		return model;
	}

	public TableRowSorter<PayrollTableModel> getSorterValue() {
		return sorter;
	}
	
	public void setSorterValue(TableRowSorter<PayrollTableModel> s){
		this.sorter=s;
		setRowSorter(this.sorter);
	}
	
	

	
	

}
