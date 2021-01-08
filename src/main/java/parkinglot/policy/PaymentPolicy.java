package parkinglot.policy;

import com.google.gson.annotations.JsonAdapter;
import lombok.NonNull;
import parkinglot.configUtil.PaymentPolicyJsonDeserializer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// TODO: 2021-01-06
//  payment가 발생하는 조건 정보(VehicleType, Spec)를 어딘가에 담도록 하기
//  그렇게 하면 PaymentPolicyFactory을 사용하지 않고 조건에 맞는 PaymentPolicy를 모두 실행 할 수 있도록 할 수 있다.
//  update: 2021-01-08
//  PaymentPolicyFactory에서 조건확인을 하고 조건을 성립하는 것들을 composite pattern을 사용해서 하나의 객체로 만들어 반환하기
//  조건 정보는 조건 인터페이스를 만들어 구현하도록 하기

// Strategy Pattern
@JsonAdapter(PaymentPolicyJsonDeserializer.class)
public interface PaymentPolicy {
    int calculate(@NonNull LocalDateTime enterDT, @NonNull LocalDateTime exitDT);

    static PaymentPolicy PaymentPerTimePolicy(int rate, double per) {
        return (enter, exit) -> (rate * (int) Math.ceil(enter.until(exit, ChronoUnit.MINUTES) / per));
    }

    static PaymentPolicy PaymentPerTimeWithMinimumTimePolicy(int minimumTime, int minimumFee, int rate, double per) {
        return (enter, exit)->{
            int time = (int) enter.until(exit, ChronoUnit.MINUTES);
            if (time <= minimumTime) {
                return minimumFee;
            } else {
                return minimumFee + rate * (int) Math.ceil((time-minimumTime) / per);
            }
        };
    }

}
