import java.io.*;
import java.sql.*;
import static db.JdbcUtil.*;

public class PhoneBookManager {
	Connection con;
	static PhoneBookManager inst = null;

	public static PhoneBookManager creatManagerInst() {
		if (inst == null)
			inst = new PhoneBookManager();

		return inst;
	}

	private PhoneInfo readFriendInfo() {
		System.out.print("이름:");
		String name = PhoneMain.sc.nextLine();
		System.out.print("전화번호:");
		String phone = PhoneMain.sc.nextLine();
		return new PhoneInfo(name, phone);

	}

	public void writeCommon() {
		PhoneInfo wrCommon = readFriendInfo();
		con = getConnection();
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO common VALUES(?,?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, wrCommon.getName());
			pstmt.setString(2, wrCommon.getPhoneNumber());

			int count = pstmt.executeUpdate();
			if (count > 0) {
				commit(con);
			} else {
				rollback(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}
	}

	private UnivInfo readUnivInfo() {
		System.out.print("이름:");
		String name = PhoneMain.sc.nextLine();
		System.out.print("전화번호:");
		String phone = PhoneMain.sc.nextLine();
		System.out.print("전공:");
		String major = PhoneMain.sc.nextLine();
		System.out.print("학년:");
		int year = PhoneMain.sc.nextInt();
		return new UnivInfo(name, phone, major, year);
	}

	public void writeUnivInfo() {
		UnivInfo wrUnivInfo = readUnivInfo();
		con = getConnection();
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO UNIVERSITY VALUES(?,?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, wrUnivInfo.getName());
			pstmt.setString(2, wrUnivInfo.getPhoneNumber());
			pstmt.setString(3, wrUnivInfo.getMajor());
			pstmt.setInt(4, wrUnivInfo.getYear());

			int count = pstmt.executeUpdate();
			if (count > 0) {
				commit(con);
			} else {
				rollback(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}
	}

	private CompanyInfo readCompanyInfo() {
		System.out.print("이름:");
		String name = PhoneMain.sc.nextLine();
		System.out.print("전화번호:");
		String phone = PhoneMain.sc.nextLine();
		System.out.print("회사명:");
		String company = PhoneMain.sc.nextLine();
		return new CompanyInfo(name, phone, company);
	}

	public void writeCompanyInfo() {
		CompanyInfo wrCompanyInfo = readCompanyInfo();
		con = getConnection();
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO COMPANY VALUES(?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, wrCompanyInfo.getName());
			pstmt.setString(2, wrCompanyInfo.getPhoneNumber());
			pstmt.setString(3, wrCompanyInfo.getCompany());

			int count = pstmt.executeUpdate();
			if (count > 0) {
				commit(con);
			} else {
				rollback(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}
	}

	public void inputData() throws MenuInputException {
		int choice = subMenu();
		
		switch (choice) {
		case INPUT_MENU.NORMAL:
			writeCommon();
			// info=readFriendInfo();
			break;
		case INPUT_MENU.UNIV:
			writeUnivInfo();
			break;
		case INPUT_MENU.COMPANY:
			writeCompanyInfo();
			break;
		default:
			throw new MenuInputException(String.valueOf(choice));
		}
	}

	// 데이터 검색
	public void searchData() throws MenuInputException {
		int choice = subMenu();
		switch (choice) {
		case 1:
			getData(choice);
			break;
		case 2:
			getData(choice);
			break;
		case 3:
			getData(choice);
			break;
		default:
			throw new MenuInputException(String.valueOf(choice));
		}
	}

	public void getData(int select) {
		System.out.print("이름:");
		String name = PhoneMain.sc.next();

		con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (select == 1) {
				String sql = "SELECT * FROM common WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					String getName = rs.getString("NAME");
					String getPhone = rs.getString("PHONE");
					System.out.println("이름:" + getName + ", 전화번호:" + getPhone);
				}

			} else if (select == 2) {
				String sql = "SELECT * FROM UNIVERSITY WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					String getName = rs.getString("NAME");
					String getPhone = rs.getString("PHONE");
					String getMajor = rs.getString("MAJOR");
					int getYear = rs.getInt("YEAR");
					System.out.println("이름:" + getName + ", 전화번호:" + getPhone
							+ ", 전공:" + getMajor + ", 학년:" + getYear);
				}
			} else {
				String sql = "SELECT * FROM COMPANY WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					String getName = rs.getString("NAME");
					String getPhone = rs.getString("PHONE");
					String getCompany = rs.getString("COMPANY");
					System.out.println("이름:" + getName + ", 전화번호:" + getPhone
							+ ", 회사:" + getCompany);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

	}

	// 데이터 삭제
	public void deleteData() throws MenuInputException {
		int choice = subMenu();

		switch (choice) {
		case 1:
			delData(choice);
			break;
		case 2:
			delData(choice);
			break;
		case 3:
			delData(choice);
			break;
		default:
			throw new MenuInputException(String.valueOf(choice));
		}
	}

	private void delData(int select) {
		System.out.print("이름:");
		String name = PhoneMain.sc.next();

		con = getConnection();
		PreparedStatement pstmt = null;

		try {
			if (select == 1) {
				String sql = "DELETE common WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.executeUpdate();

			} else if (select == 2) {
				String sql = "DELETE UNIVERSITY WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.executeUpdate();
			} else {
				String sql = "DELETE COMPANY WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}

	}

	public void modifyData() throws MenuInputException {
		int choice = subMenu();
		
		switch (choice) {
		case 1:
			modiData(choice);
			break;
		case 2:
			modiData(choice);
			break;
		case 3:
			modiData(choice);
			break;
		default:
			throw new MenuInputException(String.valueOf(choice));
		}
	}

	private void modiData(int select) {
		System.out.print("이름:");
		String name = PhoneMain.sc.next();
		con = getConnection();
		PreparedStatement pstmt = null;

		try {
			if (select == 1) {
				String sql = "UPDATE common SET PHONE = ? WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				System.out.print("전화번호:");
				String phone = PhoneMain.sc.next();
				pstmt.setString(1, phone);
				pstmt.setString(2, name);
				pstmt.executeUpdate();
			} else if (select == 2) {
				String sql = "UPDATE UNIVERSITY SET PHONE = ?, MAJOR = ?, YEAR = ? WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				System.out.print("전화번호:");
				String phone = PhoneMain.sc.next();
				System.out.print("전공:");
				String major = PhoneMain.sc.next();
				System.out.print("학년:");
				int year = PhoneMain.sc.nextInt();
				pstmt.setString(1, phone);
				pstmt.setString(2, major);
				pstmt.setInt(3, year);
				pstmt.setString(4, name);
				pstmt.executeUpdate();
			} else {
				String sql = "UPDATE COMPANY SET PHONE = ? ,COMPANY = ? WHERE name = ?";
				pstmt = con.prepareStatement(sql);
				System.out.print("전화번호:");
				String phone = PhoneMain.sc.next();
				System.out.print("회사:");
				String company = PhoneMain.sc.next();
				pstmt.setString(1, phone);
				pstmt.setString(2, company);
				pstmt.setString(3, name);
				pstmt.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}
	}

	public void showList() throws MenuInputException {
		int choice = subMenu();
		
		switch (choice) {
		case 1:
			showGroupInfo(choice);
			break;
		case 2:
			showGroupInfo(choice);
			break;
		case 3:
			showGroupInfo(choice);
			break;
		default:
			throw new MenuInputException(String.valueOf(choice));
		}
	}

	public void showGroupInfo(int select) {
		con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (select == 1) {
				String sql = "SELECT * FROM common";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					System.out.println("이름:" + rs.getString("NAME") + ", 전화번호:"
							+ rs.getString("PHONE"));
				}

			} else if (select == 2) {
				String sql = "SELECT * FROM UNIVERSITY";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					System.out.println("이름:" + rs.getString("NAME") + ", 전화번호:"
							+ rs.getString("PHONE") + ", 전공"
							+ rs.getString("MAJOR") + ", 학년"
							+ rs.getInt("YEAR"));
				}

			} else {
				String sql = "SELECT * FROM COMPANY";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					System.out.println("이름:" + rs.getString("NAME") + ", 전화번호:"
							+ rs.getString("PHONE") + ", 회사:"
							+ rs.getString("COMPANY"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

	}
	
	
	public int subMenu(){
		System.out.println("===========================");
		System.out.println("1.일반친구, 2.대학친구, 3.회사친구");
		System.out.print("선택:");
		int choice = PhoneMain.sc.nextInt(); // 1엔터
		PhoneMain.sc.nextLine(); // 버퍼의 엔터처리...
		return choice;
	}
}

