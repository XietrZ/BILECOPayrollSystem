package model;

public class SssInfo {
	public int sssID;
	public double minimumRange,maximumRange,monthlySalaryCredit,
		ee,er,ecEr,mandaProvFundEe,mandaProvFundEr;
	
	public SssInfo(int id,double minimumRange, double maximumRange, double monthlySalaryCredit,
			double ee,double er,double ecEr,double mandaEe,double mandaEr){
		this.sssID=id;
		this.minimumRange=minimumRange;
		this.maximumRange=maximumRange;
		this.monthlySalaryCredit=monthlySalaryCredit;
		this.ee=ee;
		this.er=er;
		this.ecEr=ecEr;
		this.mandaProvFundEe=mandaEe;
		this.mandaProvFundEr=mandaEr;
		
	}
}
