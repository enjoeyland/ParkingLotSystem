package parkinglot.policy;

import java.time.LocalDateTime;

public class CarPaymentPolicy implements PaymentPolicy {
    public static final PaymentPolicy normal =
            PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(30, 1000, 500, 10);

    @Override
    public int calculate(LocalDateTime enterDT, LocalDateTime exitDT) {
        return normal.calculate(enterDT, exitDT);
    }
}
