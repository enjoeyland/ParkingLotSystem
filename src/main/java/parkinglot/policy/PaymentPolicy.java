package parkinglot.policy;

import com.google.gson.annotations.JsonAdapter;
import parkinglot.configUtil.PaymentPolicyJsonDeserializer;

// Strategy Pattern
@JsonAdapter(PaymentPolicyJsonDeserializer.class)
public interface PaymentPolicy {
    int calculate(int timePeriod);

    static PaymentPolicy PaymentPerTimePolicy(int rate, double per) {
        return time -> (rate * (int) Math.ceil(time / per));
    }

    static PaymentPolicy PaymentPerTimeWithMinimumTimePolicy(int minimumTime, int minimumFee, int rate, double per) {
        return time->{
            if (time <= minimumTime) {
                return minimumFee;
            } else {
                return minimumFee + rate * (int) Math.ceil((time-minimumTime) / per);
            }
        };
    }

}
