package main;

import service.TaxiCall;
import service.UserService;
import utils.Utils;

public class Main {
	public static void main(String[] args) {
		
		System.out.println(
				"\n                    ヘ─―ヘ\n          　　　_／　　　　＼\n          　 ／　　　　●　　●丶\n          　｜　CAR피바라 ▼　| \n ．．．．． ｜　　　　　　亠ノ 　\n　　　　 　  O￣O￣￣￣O￣￣O");

		while (true) {
			try {
				if (!UserService.getInstance().isLogin()) {
					switch (Utils.nextInt("=========================================\n" + "     1.로그인   2.회원가입   3.종료\n"
							+ "=========================================\n" + "메뉴를 선택해주세요 >> ")) {
					case 1:
						UserService.getInstance().login();
						TaxiCall.getinstance().call();
						break;
					case 2:
						UserService.getInstance().register();
						break;
					case 3:
						System.out.println("프로그램을 종료합니다.");
						return;
					default:
						System.out.println("지정된 메뉴 번호를 입력해주세요.");
						break;
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력하세요");
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}