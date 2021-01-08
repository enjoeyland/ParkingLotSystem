package parkinglot.policy;


import parkinglot.data.Truck;

import java.time.LocalDateTime;

public class TruckPaymentPolicy implements PaymentPolicy {
    public static final PaymentPolicy heavy = PaymentPolicy.PaymentPerTimePolicy(4000, 60);
    public static final PaymentPolicy medium = PaymentPolicy.PaymentPerTimePolicy(3000, 60);
    public static final PaymentPolicy light = PaymentPolicy.PaymentPerTimePolicy(2000, 60);

    private final int weight;

    public TruckPaymentPolicy(Truck truck) {
        this.weight = truck.getWeight();
    }

    @Override
    public int calculate(LocalDateTime enterDT, LocalDateTime exitDT) {
        if (weight >= 10) {
            return heavy.calculate(enterDT, exitDT);
        } else if (weight >= 5) {
            return medium.calculate(enterDT, exitDT);
        } else {
            return light.calculate(enterDT, exitDT);
        }
    }
}
