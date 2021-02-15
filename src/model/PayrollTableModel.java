package model;


import java.awt.Color;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import database.Database;
import model.statics.Utilities;
import model.view.EarningsAndDeductionLayout;
import view.MainFrame;
import view.views.EarningViewPanel;

/**
 * The abstract table model of ALL my tables in the program.
 * @author XietrZ
 *
 */
public class PayrollTableModel extends AbstractTableModel{
	private final String CLASS_NAME="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+this.getClass().getSimpleName()+".java";
	

	private ResultSet tableModelResultSet = null; // manages results
	private ResultSetMetaData tableModelMetaData= null;
	private int totalNumOfRows=0;

	
	
	public Object[][]data,copiedData;
	public boolean[] isEditedRowsList;
	//--> This variable is used when you want to set cells in table editable or not.
	public ArrayList<String>columnsToBeEditedList;

	private void l___________________________l(){}

	public PayrollTableModel() {
		columnsToBeEditedList=new ArrayList<String>();
	}
	
	private void l_____________________________l(){}
	/**
	 * Clear the values of table model;
	 */
	public void clearTableModel(){
		tableModelMetaData=null;
		tableModelResultSet=null;
	}
	
	public double getCalculatedTotalDynamicTableOnly(int row,EarningsAndDeductionLayout bothEarnDed,Utilities util){
		int numOfColumnsNotNeeded=7; // First Columns not needed: {Earning/Deduction ID, PayrollDate, EmployeeID, FamilyName, FirstName, Department, Monthly Fixed Salary}
		
		if(util.payrollSystemMode==Constant.PAYROLL_SYSTEM_MODE_CONTRACTUAL && bothEarnDed instanceof EarningViewPanel){
			numOfColumnsNotNeeded+=2;
		}
		
		double total =0;
		for(int j=numOfColumnsNotNeeded;j<getColumnCount()-1;j++){
			total+=Double.parseDouble(data[row][j].toString());
		}
		return total;
	}
	/**
	 * Checks if there are changes in the data.
	 * @return
	 */
	public boolean isThereAreChanges(){
		boolean isBoolean=false;
		for (int i = 0; i < data.length; i++) {
			if (!Arrays.equals(data[i], copiedData[i])) {
				isBoolean=true;
				isEditedRowsList[i]=true; // If there are changes, put a value TRUE else FALSE.
			}
			else{
				isEditedRowsList[i]=false;
			}
		}
		return isBoolean;
				
	}

	/**
	 * Set the values of resultset and metadata.
	 * @param r
	 * @param m
	 */
	public void setTableModelResultSetAndMetaData(ResultSet r, ResultSetMetaData m){
		this.tableModelMetaData=m;
		this.tableModelResultSet=r;
		
		if(tableModelMetaData!=null && tableModelResultSet!=null){
			Utilities util= Utilities.getInstance();
		    
			
			try {
				tableModelResultSet.last();
				this.totalNumOfRows=tableModelResultSet.getRow();
				tableModelResultSet.beforeFirst();
				
				//--> Copy data from resultset to an array
				data = new Object[this.totalNumOfRows][this.tableModelMetaData.getColumnCount()];
				copiedData=new Object[this.totalNumOfRows][this.tableModelMetaData.getColumnCount()];
				isEditedRowsList=new boolean[this.totalNumOfRows];  // set rows edited value to false;
				
				for(int i=0;i<this.totalNumOfRows;i++){
					tableModelResultSet.absolute(i+1);
					
					isEditedRowsList[i]=false;
					
					for(int j=0;j<this.tableModelMetaData.getColumnCount();j++){
						data[i][j]=tableModelResultSet.getObject(j+1);
						
						
						//--> If the value in the database is string NULL, assign it to null;
						if(tableModelResultSet.getObject(j+1)==null || tableModelResultSet.getObject(j+1).toString().toLowerCase().equals("null")){
							data[i][j]=null;
							
							
						}
						//--> If column name is Date Left
//						else if(getColumnName(j).equals("Date Left") &&
//								tableModelResultSet.getObject(j+1).toString().equals(Constant.STRING_DATE_LEFT_NULL)){ // If date is null or 1900-01-01
//							System.out.println("\t POTAFUCK: "+data[i][j].toString()
//									+"\t Column Name: "+getColumnName(j)+CLASS_NAME);
//							
//							data[i][j]="";
//						}
						
						
							
							
						String columnNameCamelCase=getColumnName(j);
						if(columnNameCamelCase.equals("Net Pay")){
							data[i][j]=util.convertRoundToOnlyTwoDecimalPlaces(Double.parseDouble(data[i][j].toString()));
						}
						copiedData[i][j]=data[i][j];
					}
				}
				
		
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("\tFucking Error: "+CLASS_NAME);
				MainFrame.getInstance().showOptionPaneMessageDialog(e.getMessage(),JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		
		
	}
	
	private void l________________________________l(){}

	//--> Default Methods of AbstractTableModel.
	//		Indices rows and columns always starts in 0 and since accessing data
	//		in sql starts at 1 that is why in every indices in the code
	//		there is +1.
	@Override
	public Class getColumnClass(int column) {
		if(tableModelMetaData!=null){
			try {
				return Class.forName(
					tableModelMetaData.getColumnClassName(column+1));
			} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
					e.printStackTrace();
			}
        }
	        
        return Object.class;
	 }
	 
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		if(tableModelMetaData!=null){
			try {
				return tableModelMetaData.getColumnCount();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	public String getColumnName(int column) {
		try {
			if(tableModelMetaData!=null)
				return Utilities.getInstance().convertCamelCaseColumnNamesToReadable(tableModelMetaData.getColumnName(column+1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
    }


	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub		
		return totalNumOfRows;

	}


	@Override
	public Object getValueAt(int row, int column) {
		
		return data[row][column];
	}
	
	
	/**
	 * Use this method to control the cells that are editable
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
		for(String str:columnsToBeEditedList){
			try {
				if(str.equals(tableModelMetaData.getColumnName(col+1).toString())){
//					System.out.println("\tColumns that are Edited: "+str+CLASS_NAME);
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: "+e.getMessage()+CLASS_NAME);
				MainFrame.getInstance().showOptionPaneMessageDialog(
						"Error: "+e.getMessage(), 
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		
		return false;
    }
	
	
	/**
	 * Use this method in order to update the cell when you try to edit a cell.
	 * 		Without this method, the edited cell will turn back to its original
	 * 		content before editing.
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex){
		if(data[rowIndex][columnIndex].getClass().toString().equals(Utilities.getInstance().compareClassObject(Constant.CLASS_OBJECT_DOUBLE))
				&& aValue==null){
			data[rowIndex][columnIndex]=0.0;
		}
		
		
		else{
			data[rowIndex][columnIndex]=aValue;
		}
		
    }
	
	
//	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
//	{
//         Component c = super.getTableCellRendererCo
//         if (!c.getBackground().equals(getSelectionBackground()))
//         {
//             String type = (String)getModel().getValueAt(row, column);
//             c.setBackground( type.equals(Calculations.smax) ? Color.GREEN :type.equals(Calculations.smin) ? Color.YELLOW:Color.white );
//         }
//         return c;
//	}
	
	
	
}
