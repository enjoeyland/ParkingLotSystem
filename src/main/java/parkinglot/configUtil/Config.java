package parkinglot.configUtil;

import lombok.ToString;
import parkinglot.data.VehicleType;
import parkinglot.policy.PaymentPolicy;

import java.util.List;
import java.util.Map;

/**
 * @author 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
@ToString
// TODO: 2021-01-06
//  rename class
public class Config {
    int capacity;
    List<VehicleType> allowedVehicleType;
    List<VehicleType> forbiddenVehicleType;
    List<PaymentPolicy> feePolicy;
    // TODO: 2021-01-06
    //  replace to
    //  Map<VehicleType,List<PaymentPolicy>> feePolicyMap;
}
