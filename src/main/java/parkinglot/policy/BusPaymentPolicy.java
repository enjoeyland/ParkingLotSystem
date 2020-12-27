package parkinglot.policy;


import parkinglot.data.Bus;

public class BusPaymentPolicy implements PaymentPolicy {
    private static final PaymentPolicy big = PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(60, 4000, 2000, 30);
    private static final PaymentPolicy medium = PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(60, 3000, 1500, 30);
    private static final PaymentPolicy small = PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(60, 2000, 1000, 30);

    private final int seatCapacity;

    public BusPaymentPolicy(Bus bus) {
        this.seatCapacity = bus.getSeatCapacity();
    }

    @Override
    public int calculate(int timePeriod) {
        if (seatCapacity >= 40) {
            return big.calculate(timePeriod);
        } else if (seatCapacity >= 24) {
            return medium.calculate(timePeriod);
        } else {
            return small.calculate(timePeriod);
        }
    }
}
