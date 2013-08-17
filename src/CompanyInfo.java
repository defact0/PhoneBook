public class CompanyInfo extends PhoneInfo { // 직장 친구
	String company; // 회사명

	public CompanyInfo(String name, String phoneNumber, String company) {
		super(name, phoneNumber);
		this.company = company;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public void showPhoneInfo() {
		// TODO Auto-generated method stub
		super.showPhoneInfo();
		System.out.println("회사명:" + company);
	}

	public void setInfo(String phone, String company) {
		super.setInfo(phone);
		this.company = company;
	}
}
