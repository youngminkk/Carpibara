package vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import vo.Area;
import vo.Car;

public class Call {
	private List<Area> areas;
	private List<Car> cars;

	public static Call getinstance() {
		return new Call();
	}
	
	public Call() {
		areas = new ArrayList<>();
		cars = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			cars.add(new Car(i + 1));
		}
	}

	public List<Area> getAreas() {
		return areas;
	}

	public List<Car> getCars() {
		return cars;
	}

	public Car getCarById(int carId) {
		for (Car car : cars) {
			if (car.getId() == carId) {
				return car;
			}
		}
		return null;
	}
}
