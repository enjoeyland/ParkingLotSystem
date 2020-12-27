package parkinglot.data;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class Car extends Vehicle {
    private final CarType carType;

    public Car(String licensePlateNum, CarType carType) {
        super(licensePlateNum);
        this.carType = carType;
    }

    public CarType getCarType() {
        return carType;
    }
}
