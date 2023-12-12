package vo;

import vo.Call;

public class Area {
	private Integer[] feeList = {1000, 200, 300, 400, 500, 600, 700, 800};
	
	public Area(int startArea, int destinationArea) {
	}

	public int totalFee(Call system, int startArea, int destinationArea) {
		int totalFee = 0; // 추가부분 출발지 도착지 요금 합계
		if (startArea > destinationArea) {
			for (int i = destinationArea; i <= startArea; i++) {
				totalFee += feeList[i-1];
			}
		} else if(destinationArea > startArea) {
			for (int i = startArea; i <= destinationArea; i++) {
				totalFee += feeList[i-1];
			}
		}
		return totalFee;
	}
}