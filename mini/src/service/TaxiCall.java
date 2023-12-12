package service;

import utils.Utils;

public class TaxiCall {
	
	public static TaxiCall getinstance() {
		return new TaxiCall();
	}
	
	private TaxiCall () {};
	
	public void call() {
			while (UserService.getInstance().isLogin()) {
				try {
				switch (Utils.nextInt(UserService.getInstance().getLoginUser().getName() + "님 환영합니다!\n\n"
						+ "    1.차량호출   2.리뷰   3.로그아웃\n" + "메뉴를 선택해주세요 >> ")) {
						case 1:
							Reservation.getInstance().makeReservation();
							return;
						case 2:
							ReviewService.getInstance().reviewSwitch();
							return;
						case 3:
							System.out.println("로그아웃 되었습니다.");
							UserService.getInstance().logout();
							break;
						default:
							System.out.println("지정된 메뉴 번호를 입력해주세요.");
							break;
				}
				
				} catch (NumberFormatException e) {
					System.out.println("숫자로 입력하세요");
				} catch (RuntimeException e) {
					System.out.println(e.getMessage());
				}
			}
	}
}
