package service;


import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.Utils;
import vo.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
	private static UserService userService = new UserService();
	public static UserService getInstance() {
		return userService;
	}

	
	private User loginUser;
	private List<User> users = Utils.load("users");
	
	public boolean payFee(int totalFee) {
		switch (Utils.nextInt("결제하실 금액은 " + totalFee + "원입니다\n1. 카드    2. 현금    3. 뒤로가기")) {
			case 1:
				CardPayment(totalFee);
				break;
			case 2:
				CashPayment(totalFee);
				break;
			case 3:
				TaxiCall.getinstance().call();
				return true;

			default:
				System.out.println("올바른 번호를 입력하세요");
				payFee(totalFee);
				break;
			};
		return false;	
	}

	public void register() {
		System.out.println("\n[2.회원가입]");
		System.out.println("-아이디는 영문자로만 입력 가능합니다.\n-비밀번호는 4글자 이상으로 설정해야합니다.\n-이름은 한글로만 입력 가능합니다.\n");
		String id = Utils.nextLine("아이디를 입력하세요 ", t->t.matches("[A-Za-z]+"), " ※ 모든 글자는 영문자로 구성되어야 합니다.");
		if (findBy(id) != null) {
			System.out.println("동일한 아이디를 소유한 사용자가 있습니다");
			return;
		}
		String pw = Utils.nextLine("비밀번호를 입력하세요 ", t->t.matches("\\w{4,}+"), " ※ 최소한 4글자 이상의 문자로 구성되어야\n 합니다.");
		String name = Utils.nextLine("이름을 입력하세요 ", t->t.matches("[가-힣]+"), " ※ 모든 글자는 한글로 구성되어야 합니다.");
		
		insert(User.builder().id(id).pw(pw).name(name).build());
		Utils.save();
		System.out.println("\n    * 회원가입이 완료되었습니다 *\n");
	}

	public void login() {
		System.out.println("\n[1.로그인]");
		String id = Utils.nextLine("아이디를 입력하세요", t->t.matches("[A-Za-z]+"), " ※ 모든 글자는 영문자로 구성되어야 합니다.");
		String pw = Utils.nextLine("비밀번호를 입력하세요", t->t.matches("\\w{4,}+"), " ※ 최소한 4글자 이상의 문자로 구성되어야\n 합니다.");
		User user = findBy(id);
		if (user == null) {
			System.out.println("가입된 사용자의 아이디가 아닙니다");
			return;
		}
		if (!user.getPw().equals(pw)) {
			System.out.println("비밀번호가 일치하지 않습니다");
			return;
		}
		System.out.println("\n            * 로그인 성공 *\n\n"
				+ "=========================================");
		loginUser = user;
	}

	private User findBy(String id) {
		for (int i = 0; i < users.size(); i++) {
			if (id.equals(users.get(i).getId())) {
				return users.get(i);

			}
		}
		return null;
	}

	public void CardPayment(int totalFee) {
		System.out.println("결제 성공");
	}

	public void CashPayment(int totalFee) {
		int cash = Utils.nextInt("현금을 넣어주세요");
		if (cash > totalFee) {
			int change = cash - totalFee;
			System.out.println("결제 성공");
			System.out.println("거스름돈은 " + change + "원입니다");
			if (cash == totalFee) {
				System.out.println("결제 성공");
			}
		} else {
			System.out.println("금액이 부족합니다.");
			payFee(totalFee);
		}
	}
	
	private int insert(User user) {
		return users.add(user) ? 1 : 0;
	}

	public void logout() {
		loginUser = null;
	}

	// 로그인 여부 확인
	public boolean isLogin() {
		return loginUser != null;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
}
