package parkinglot.configUtil;

import org.junit.jupiter.api.Test;
import parkinglot.policy.PaymentPolicy;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
class PaymentPolicyBuilderTest {
    @Test
    void testTimeBasePolicy_WithFullOption() {
        // Setup

        // Run the test
        PaymentPolicy policy = PaymentPolicyBuilder
                .timeBasePolicy(200, 10)
                .minimumTimeAndFee(1000, 15)
                .maximumFeePerDay(10000)
                .build();

        // Verify the results
        assertThat(policy.calculate(
                LocalDateTime.of(2021, 1,9,12,32),
                LocalDateTime.of(2021, 1,9,16,42)),
                is(700 + 200*(4*60+10)/10));

        assertThat(policy.calculate(
                LocalDateTime.of(2021, 1,9,10,32),
                LocalDateTime.of(2021, 1,9,22,42)),
                is(10000));

        assertThat(policy.calculate(
                LocalDateTime.of(2021, 1,9,21,32),
                LocalDateTime.of(2021, 1,12,21,0)),
                is(700 + 200*((2*60+28)/10) + 2*10000 + 10000));

        assertThat(policy.calculate(
                LocalDateTime.of(2021, 1,9,21,32),
                LocalDateTime.of(2021, 1,10,7,0)),
                is(700 + 200*((2*60+28)/10) + 200*((7*60)/10)));
    }

}