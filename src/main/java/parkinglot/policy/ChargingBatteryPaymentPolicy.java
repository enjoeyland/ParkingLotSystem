package parkinglot.policy;

import parkinglot.data.ElectricCar;

public class ChargingBatteryPaymentPolicy implements PaymentPolicy {
    private static final double chargingSpeed = 0.2; // 분당
    private static final int rate = 300; // 1kw당 300원

    private final int leftBattery;
    private final int batteryCapacity;

    public ChargingBatteryPaymentPolicy(ElectricCar electricCar) {
        this.leftBattery = electricCar.getLeftBattery();
        this.batteryCapacity = electricCar.getBatteryCapacity();
    }

    @Override
    public int calculate(int timePeriod) {
        if (timePeriod >= (batteryCapacity - leftBattery)/chargingSpeed) {
            return rate * (batteryCapacity - leftBattery);
        } else {
            return (int) Math.floor(rate * chargingSpeed * timePeriod);
        }
    }
}
