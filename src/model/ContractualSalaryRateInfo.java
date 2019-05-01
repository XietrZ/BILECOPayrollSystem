package model;

public class ContractualSalaryRateInfo {
	public double minimumRange,maximumRange,salaryPerDay;
	
	public ContractualSalaryRateInfo( double minimumRange,double maximumRange,double salaryPerDay){
		this.minimumRange=minimumRange;
		this.maximumRange=maximumRange;
		this.salaryPerDay=salaryPerDay;
	}
}
