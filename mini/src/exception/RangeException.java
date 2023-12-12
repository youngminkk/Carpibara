
package exception;

import service.Reservation;

@SuppressWarnings("serial")
public class RangeException extends RuntimeException {
	public RangeException() {
		System.out.println("가능한 차량이 아닙니다.");
		Reservation.getInstance().makeReservation();
	}
}