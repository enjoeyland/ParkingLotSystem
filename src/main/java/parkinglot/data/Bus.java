package parkinglot.data;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class Bus extends Vehicle{
    private final int seatCapacity;

    public Bus(String licensePlateNum, int seatCapacity) {
        super(licensePlateNum);
        this.seatCapacity = seatCapacity;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }
}
