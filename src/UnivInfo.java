public class UnivInfo extends PhoneInfo { // 대학친구

	String major; // 전공
	int year; // 학년

	public UnivInfo(String name, String phoneNumber, String major, int year) {
		super(name, phoneNumber);
		this.major = major;
		this.year = year;
	}

	@Override
	public void showPhoneInfo() {
		// TODO Auto-generated method stub
		super.showPhoneInfo();
		System.out.println("전공:" + major);
		System.out.println("학년:" + year);
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setInfo(String phone, String major, int year) {

		// this.phoneNumber=phone;
		super.setInfo(phone);
		this.major = major;
		this.year = year;

	}

}
