package parkinglot.policy;

public class CarPaymentPolicy implements PaymentPolicy {
    public static final PaymentPolicy normal =
            PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(30, 1000, 500, 10);
    @Override
    public int calculate(int timePeriod) {
        return normal.calculate(timePeriod);
    }
}
