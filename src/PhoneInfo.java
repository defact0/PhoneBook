public class PhoneInfo { // 일반친구
	String name;
	String phoneNumber;

	public PhoneInfo(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setInfo(String phone) {
		this.phoneNumber = phone;
	}

	@Override
	public boolean equals(Object obj) {
		PhoneInfo cmp = (PhoneInfo) obj;
		if (name.compareTo(cmp.name) == 0)
			return true;
		return false;
	}

	public void showPhoneInfo() {
		System.out.println("=========================");
		System.out.println("이름:" + name);
		System.out.println("전화번호:" + phoneNumber);
	}
}
