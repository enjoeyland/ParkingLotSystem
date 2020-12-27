package parkinglot.policy;

import parkinglot.data.ElectricCar;

public class ElectricCarPaymentPolicy implements PaymentPolicy {
    private final static CarPaymentPolicy carPaymentPolicy = new CarPaymentPolicy();
    private final ChargingBatteryPaymentPolicy chargingBatteryPolicy;


    public ElectricCarPaymentPolicy(ElectricCar electricCar) {
        chargingBatteryPolicy = new ChargingBatteryPaymentPolicy(electricCar);
    }

    @Override
    public int calculate(int timePeriod) {
        return chargingBatteryPolicy.calculate(timePeriod) + carPaymentPolicy.calculate(timePeriod);
    }
}
