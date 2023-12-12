package service;

import static utils.Utils.chkAreaRange;

import static utils.Utils.chkCarRange2;
import static utils.Utils.nextInt;
import static utils.Utils.nextLine;

import java.util.Scanner;

import vo.Call;
import vo.Area;
import vo.Car;


public class Reservation {
	private int id;
	public Car car = new Car(id);
	private int startArea;
	private int destinationArea;
	int cnt = 5;
	Scanner scanner = new Scanner(System.in);

	public static Reservation getInstance() {
		return new Reservation();
	}

	private Reservation() {
	}

	public void makeReservation() {
		// 호출가능 차 출력
		
		System.out.println("=========================================\n" + "[1.택시호출]");
		if (cnt == 0) {
			System.out.println("현재 호출가능한 차량이 없습니다.");
			return;
		} else {
			System.out.println("호출가능한 차 번호입니다.");
			for (Car car : Call.getinstance().getCars()) {
				if (car.isAvailable()) {
					System.out.print("[" + car.getId() + "번]  ");
				}
			}
		}
		System.out.println();

		// 차 번호 선택
		int selectedCar = chkCarRange2(nextInt("호출하실 차 번호를 입력해주세요 : "));
		if (Call.getinstance().getCarById(selectedCar).isAvailable() != true) {
			System.out.println("이미 운행중인 차량입니다.");
			return;
		}

		Car car = Call.getinstance().getCarById(selectedCar);
		cnt--;
		car.setAvailable(false);
		System.out.println("\n1번 ~ 8번의 정류장이 있습니다.");
		System.out.println("호출 할 정류장의 번호를 입력하세요 : ");
		
		startArea = chkAreaRange(scanner.nextInt());
		
		while (startArea > 8 || 1 > startArea) {
			System.out.println("잘못된 정류장입니다. 다시 입력하세요. ");
			startArea = scanner.nextInt();
			}
		System.out.println("도착 정류장의 번호를 입력하세요 : ");
		destinationArea = scanner.nextInt();
		while (destinationArea > 8 || 1 > destinationArea) {
			System.out.println("잘못된 정류장입니다. 다시 입력하세요. ");
			destinationArea = scanner.nextInt();
		} 
//		
        
		System.out.println("목적지 설정이 완료되었습니다.");
		Area area = new Area(startArea, destinationArea);
		int totalFee = area.totalFee(Call.getinstance(), startArea, destinationArea); // 요금

		// 호출 정류장 도착 예정시간
		int startAreaTime = Math.abs(1 - startArea) * 5;

		// 목적지 도착예정시간
		int travelTime = Math.abs(startArea - destinationArea) * 5;

		int time = Math.abs(1 - destinationArea) * 5;
		// 도착정류장-출발정류장만큼 이동
		car.move(destinationArea);

		if (UserService.getInstance().payFee(totalFee)) {
			return;
		}

		// 사용자 인증
		String userCode = (int) (Math.random() * 9999 + 1) + ""; // 랜덤으로 부여할 인증번호
		System.out.println("호출한 차량의 인증번호는 [" + userCode + "]입니다.");

		new Thread(() -> {
			try {
				System.out.println();
				System.out.println(startAreaTime + " 분 후 도착합니다.");
				System.out.println(
						"\n                   _ヘ─―ヘ_\n          　　　_／　　　　＼\n          　 ／　　　　3　　3丶\n          　｜　   가는중 ▼　| \n ．．．．． ｜　　　　　　亠ノ 　\n　　　　 　  O￣O￣￣￣O￣￣O");
				Thread.sleep(startAreaTime * 1000);
				System.out.println(car.getId() + "번 택시가 호출 정류장에 도착했습니다.");
				nextLine("인증번호를 입력하세요 : ", s -> s.equals(userCode), "인증번호가 일치하지 않습니다");
				System.out.println("인증번호가 일치합니다. 운행 시작!");
				Thread.sleep(1000);
				System.out.println(travelTime + "분 후 목적지에 도착합니다.");
				Thread.sleep(travelTime * 1000);
				System.out.println(car.getId() + "번 택시가 목적지에 도착했습니다.");
				Thread.sleep(1000);
				if (startArea == 1) {
					System.out.println(car.getId() + "번 택시가 차고지에 도착했습니다."); // 차고지에 도착해야 차량이 예약가능하다.
				} else {
					if (destinationArea < 5) {
						System.out.println(car.getId() + "번 택시가 차고지에 " + time + "분 후 도착 합니다.");
						Thread.sleep(time * 1000);
						System.out.println(
								"\n                   _ヘ─―ヘ_\n          　　　_／　　　　＼\n          　 ／　　　　Z　　Z丶\n          　｜차고지 도착 ▼  | \n ．．．．． ｜　　　　　　亠ノ 　\n　　　　 　  O￣O￣￣￣O￣￣O");
						System.out.println(car.getId() + "번 택시가 차고지에 도착했습니다."); // 차고지에 도착해야 차량이 예약가능하다.
					} else {
						System.out.println(car.getId() + "번 택시가 차고지에 " + (9 - destinationArea) * 5 + "분 후 도착 합니다.");
						Thread.sleep((9 - destinationArea) * 5 * 1000);
						System.out.println(car.getId() + "번 택시가 차고지에 도착했습니다."); // 차고지에 도착해야 차량이 예약가능하다.
					}
				}
				car.setAvailable(true);
				cnt++;
				TaxiCall.getinstance().call();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
