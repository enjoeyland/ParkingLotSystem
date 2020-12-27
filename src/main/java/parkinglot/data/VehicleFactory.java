package parkinglot.data;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
// Simple Factory
public class VehicleFactory {
    public static Vehicle getVehicle(String licensePlateNum, VehicleType vehicleType, int spec) {
        if (vehicleType == VehicleType.CAR) {
            if (spec == 0) {
                return new Car(licensePlateNum, CarType.NORMAL);
            } else {
                return new ElectricCar(licensePlateNum, CarType.ELECTRIC, spec);
            }
        } else if (vehicleType == VehicleType.TRUCK) {
            return new Truck(licensePlateNum, spec);
        } else { //vehicleType == parkinglot.data.VehicleType.BUS
            return new Bus(licensePlateNum, spec);
        }

    }
}
