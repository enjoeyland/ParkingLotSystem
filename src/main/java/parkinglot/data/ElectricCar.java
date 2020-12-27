package parkinglot.data;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ElectricCar extends Car {

    private final int leftBattery;
    private final int batteryCapacity = 60;

    public ElectricCar(String licensePlateNum, CarType carType, int leftBattery) {
        super(licensePlateNum, carType);
        this.leftBattery = leftBattery;
    }

    public int getLeftBattery() {
        return this.leftBattery;
    }

    public int getBatteryCapacity() {
        return this.batteryCapacity;
    }
}
