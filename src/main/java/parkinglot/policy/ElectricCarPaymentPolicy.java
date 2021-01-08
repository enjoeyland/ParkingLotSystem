package parkinglot.policy;

import parkinglot.data.ElectricCar;

import java.time.LocalDateTime;

public class ElectricCarPaymentPolicy implements PaymentPolicy {
    private final static CarPaymentPolicy carPaymentPolicy = new CarPaymentPolicy();
    private final ChargingBatteryPaymentPolicy chargingBatteryPolicy;


    public ElectricCarPaymentPolicy(ElectricCar electricCar) {
        chargingBatteryPolicy = new ChargingBatteryPaymentPolicy(electricCar);
    }

    @Override
    public int calculate(LocalDateTime enterDT, LocalDateTime exitDT) {
        return chargingBatteryPolicy.calculate(enterDT, exitDT)
                + carPaymentPolicy.calculate(enterDT, exitDT);
    }
}
