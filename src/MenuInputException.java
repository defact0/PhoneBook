public class MenuInputException extends Exception {

	String wrongNum;

	public MenuInputException(String num) {
		super("메뉴번호를 잘못 입력했습니다");
		wrongNum = num;
	}

	public void showWrongNum() {
		System.out.println(wrongNum + "메뉴는 존재하지 않습니다.");
	}
}
