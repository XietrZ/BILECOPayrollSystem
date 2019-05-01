package model;

public class PhicInfo {
	
	public int phicID=-1;
	public double monthlyBasicSalaryMin=-1,
			monthlyBasicSalaryMax=-1;
	public String status="";
	
	public PhicInfo(int phicID, double monthlyBasicSalaryMin,
			double monthlyBasicSalaryMax, String status) {
		// TODO Auto-generated constructor stub
		this.phicID=phicID;
		this.monthlyBasicSalaryMax=monthlyBasicSalaryMax;
		this.monthlyBasicSalaryMin=monthlyBasicSalaryMin;
		this.status=status;
	}
}
