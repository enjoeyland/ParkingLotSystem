package parkinglot.policy;

import parkinglot.data.ElectricCar;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    public int calculate(LocalDateTime enterDT, LocalDateTime exitDT) {
        int time = (int) enterDT.until(exitDT, ChronoUnit.MINUTES);
        if (time >= (batteryCapacity - leftBattery)/chargingSpeed) {
            return rate * (batteryCapacity - leftBattery);
        } else {
            return (int) Math.floor(rate * chargingSpeed * time);
        }
    }
}
