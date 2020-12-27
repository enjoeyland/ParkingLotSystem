package parkinglot.policy;

import parkinglot.data.*;

public class PaymentPolicyFactory {
    public static PaymentPolicy getPolicy(Vehicle vehicle) {
        VehicleType type = VehicleType.typeOf(vehicle);

        if (type == VehicleType.BUS) {
            return new BusPaymentPolicy((Bus) vehicle);
        } else if (type == VehicleType.TRUCK){
            return new TruckPaymentPolicy((Truck) vehicle);
        } else if (type == VehicleType.CAR && ((Car) vehicle).getCarType() == CarType.ELECTRIC) {
            return new ElectricCarPaymentPolicy((ElectricCar) vehicle);
        } else { //type == parkinglot.data.VehicleType.CAR
            return new CarPaymentPolicy();
        }
    }
}
