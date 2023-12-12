package vo;

public class Car {
    private int id;
    private boolean available;
    private int currentLocation;

    public Car(int id) {
        this.id = id;
        this.available = true;
        this.currentLocation = 1; // 차고지 번호가 1 이라서 호출되지 않은 차는 1번위치인 차고지에 항상있음.
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void move(int destination) {
        this.currentLocation = destination;
    }
}

