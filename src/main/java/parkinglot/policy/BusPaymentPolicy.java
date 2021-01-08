package parkinglot.policy;


import parkinglot.data.Bus;

import java.time.LocalDateTime;

public class BusPaymentPolicy implements PaymentPolicy { // TODO: 2021-01-08 condition
    private static final PaymentPolicy big = PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(60, 4000, 2000, 30);
    private static final PaymentPolicy medium = PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(60, 3000, 1500, 30);
    private static final PaymentPolicy small = PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(60, 2000, 1000, 30);

    private final int seatCapacity;

    public BusPaymentPolicy(Bus bus) {
        this.seatCapacity = bus.getSeatCapacity();
    }

    @Override
    public int calculate(LocalDateTime enterDT, LocalDateTime exitDT) {
        if (seatCapacity >= 40) {
            return big.calculate(enterDT,exitDT);
        } else if (seatCapacity >= 24) {
            return medium.calculate(enterDT,exitDT);
        } else {
            return small.calculate(enterDT,exitDT);
        }
    }
}
