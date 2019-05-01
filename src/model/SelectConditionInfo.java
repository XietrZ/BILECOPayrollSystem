package model;

public class SelectConditionInfo {
	private String columnName;
	private Object value;
	private String sign="="; // Default sign.

	public SelectConditionInfo(String columnName, Object value){
		this.columnName=columnName;
		this.value=value;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	
	
	
}
