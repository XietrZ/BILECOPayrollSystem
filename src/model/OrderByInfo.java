package model;

public class OrderByInfo {
	public String []columnNames;
	public String order="";
	
	public OrderByInfo(String[]c,String order){
		this.columnNames=c;
		this.order=order;
	}
}
