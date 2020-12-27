package parkinglot.data;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class Truck extends Vehicle {
    private final int weight;

    public Truck(String licensePlateNum, int weight) {
        super(licensePlateNum);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
