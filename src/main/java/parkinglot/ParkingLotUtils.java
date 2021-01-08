package parkinglot;

import parkinglot.data.ParkingInfo;
import parkinglot.data.VehicleFactory;
import parkinglot.data.VehicleType;
import parkinglot.policy.PaymentPolicyFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingLotUtils {

    public static int calculateParkingFee(ParkingInfo pi) {
        try {
            return PaymentPolicyFactory.getPolicy(pi.getVehicle()).calculate(pi.getEnterDateTime(), pi.getExitDateTime());
        } catch (RuntimeException e) {
            return 0;
        }
    }

    public static int calculateTotalIncome(List<ParkingInfo> parkingInfoList) {
        int sum = 0;
        for (ParkingInfo pi: parkingInfoList) {
            sum += calculateParkingFee(pi);
        }
        return sum;
    }

    public static LocalTime getParkingTime(ParkingInfo pi) {
        LocalTime time = LocalTime.MIN.plus(Duration.ofMinutes(pi.getParkingTime()));
        time = time.plusMinutes((int) Math.round(time.getMinute()/10.0) * 10 - time.getMinute()); // 4분 이내에 나갈때 0시 0분이 된다.
        return time;
    }

    public static ParkingInfo createParkingInfo(String licensePlateNum, VehicleType vehicleType, int spec, LocalDateTime enterDateTime) {
        var vehicle = VehicleFactory.getVehicle(licensePlateNum, vehicleType, spec);
        return new ParkingInfo(vehicle, enterDateTime);
    }
}
