package parkinglot.data;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public abstract class Vehicle {
    private final String licensePlateNum;

    public Vehicle(String licensePlateNum) {
        this.licensePlateNum = licensePlateNum;
    }

    public String getLicensePlateNum() {
        return this.licensePlateNum;
    }

}
