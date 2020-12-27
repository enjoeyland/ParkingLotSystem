package parkinglot.data;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public enum VehicleType {
    CAR, BUS, TRUCK;

    public static VehicleType typeOf(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            return CAR;
        } else if (vehicle instanceof Bus) {
            return BUS;
        } else { // vehicle instanceof Data.parkinglot.data.Truck
            return TRUCK;
        }
    }
}
