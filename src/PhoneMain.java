import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;


class Menu {
	public static void showMenu() {
		System.out.println("==========================");
		System.out.println("메뉴를 선택하세요...");
		System.out.println("I. 데이터 입력");
		System.out.println("S. 데이터 검색");
		System.out.println("D. 데이터 삭제");
		System.out.println("M. 데이터 수정");
		System.out.println("L. 그룹별 출력");
		System.out.println("E. 프로그램 종료");
		System.out.print("선택: ");
	}
}

public class PhoneMain {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		//JFrame
		 JFrame frm = new MainFrame("PhoneBook Manager");
		 
		PhoneBookManager manager = PhoneBookManager.creatManagerInst();
		String choice;
		while (true) {
			try {
				Menu.showMenu(); // 메뉴가 항상 출력되도록....
				choice = sc.next(); // a엔터
				sc.nextLine(); // 버퍼의 엔터처리...
				choice = choice.toUpperCase();

				if (!choice.equals("I") && !choice.equals("S")
						&& !choice.equals("D") && !choice.equals("L")
						&& !choice.equals("E") && !choice.equals("M"))
					throw new MenuInputException(choice);
				MainMenu dest = MainMenu.valueOf(choice);// String.valueOf(choice));
				switch (dest) {
				case I:
					manager.inputData();
					break;
				case S:
					manager.searchData();
					break;
				case D:
					manager.deleteData();
					break;
				case L:
					manager.showList();
					break;
				case M:
					manager.modifyData();
					break;
				case E:
					System.out.println("프로그램을 종료합니다.");
					return;
				}
			} // in try end
			catch (InputMismatchException e) {
				System.out.println("형식이 잘못됐어요");
				sc.nextLine();
			} // out try end
			catch (MenuInputException e) {
				System.out.println(e.getMessage());
				e.showWrongNum();
			}
		}
	}
}
