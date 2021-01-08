package parkinglot.configUtil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import parkinglot.policy.PaymentPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.DoubleToIntFunction;

/**
 * @author 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentPolicyBuilder {
    public static TimeBasePolicyBuilder timeBasePolicy(int rate, int per) {
        return new TimeBasePolicyBuilder(rate, per);
    }

    public static class TimeBasePolicyBuilder {
        private final int rate;
        private final int per;
        private int minimumTime;
        private int minimumFee;
        private boolean minimumTimeAndFeeOption = false;
        private boolean maximumFeePerDayOption = false;
        private int maxFeePerDay;
        private boolean maximumTimePerDayOption;
        private int maxTimePerDay;

        private TimeBasePolicyBuilder(int rate, int per) {
            this.rate = rate;
            this.per = per;
        }

        public TimeBasePolicyBuilder minimumTimeAndFee(int minimumFee, int minimumTime) {
            this.minimumTimeAndFeeOption = true;
            this.minimumTime = minimumTime;
            this.minimumFee = minimumFee;
            return this;
        }

        public TimeBasePolicyBuilder maximumFeePerDay(int maxFeePerDay) {
            this.maximumFeePerDayOption = true;
            this.maxFeePerDay = maxFeePerDay;
            return this;
        }

        public TimeBasePolicyBuilder maximumTimePerDay(int maxTimePerDay) {
            this.maximumTimePerDayOption = true;
            this.maxTimePerDay = maxTimePerDay;
            return this;
        }

        public PaymentPolicy build() {
            PaymentPolicy timeBasePolicy = (enterDT, exitDT) -> {
                int time = (int) enterDT.until(exitDT, ChronoUnit.MINUTES);
                if (maximumTimePerDayOption) {
                    time = Math.min(time, maxTimePerDay);
                }
                int fee = rate * (time / per);
                if (minimumTimeAndFeeOption) {
                    fee -= Math.min(fee, (rate * minimumTime) / per);
                    fee += minimumFee;
                }
                if (maximumFeePerDayOption) {
                    fee = Math.min(fee, maxFeePerDay);
                }
                return fee;
            };
            if (maximumFeePerDayOption || maximumTimePerDayOption) {
                // TODO: 2021-01-09 여기서 dateBasePolicy가 있는 것이 이상하다. 하는 일이 너무 많다.
                PaymentPolicy dateBasePolicy = (enterDT, exitDT) -> {
                    int passedDays = (int) ChronoUnit.DAYS.between(enterDT.toLocalDate(), exitDT);
                    int fee = 0;
                    if (passedDays == 0) {
                        fee += timeBasePolicy.calculate(enterDT, exitDT);
                    } else { // TODO: 2021-01-09 passedDays가 음수 일때 처리 작업 추가
                        fee += timeBasePolicy.calculate(enterDT, enterDT.toLocalDate().plusDays(1).atStartOfDay());
                        fee += (passedDays - 1) * timeBasePolicy.calculate(LocalDateTime.MIN, LocalDateTime.MIN.plusDays(1));

                        int time = (int) ChronoUnit.MINUTES.between(exitDT.toLocalDate().atStartOfDay(), exitDT);
                        if (maximumTimePerDayOption) {
                            time = Math.min(time, maxTimePerDay);
                        }
                        int lastDayFee = rate * (time / per);
                        if (maximumFeePerDayOption) {
                            lastDayFee = Math.min(lastDayFee, maxFeePerDay);
                        }
                        fee += lastDayFee;

                    }
                    return fee;
                };
                return dateBasePolicy;
            } else {
                return timeBasePolicy;
            }
        }
    }
}
