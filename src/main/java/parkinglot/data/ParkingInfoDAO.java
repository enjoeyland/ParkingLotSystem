package parkinglot.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingInfoDAO {
    private final List<ParkingInfo> parkingInfoList;

    public ParkingInfoDAO() {
        this.parkingInfoList = new ArrayList<>();
    }

    public ParkingInfo save(ParkingInfo parkingInfo) {
        parkingInfoList.add(parkingInfo);
        return parkingInfo;
    }

    public Optional<ParkingInfo> findByLicensePlateNum(String licensePlateNum) {
        for (ParkingInfo pi : parkingInfoList) {
            if (pi.getVehicle().getLicensePlateNum().equals(licensePlateNum)) {
                return Optional.of(pi);
            }
        }
        return Optional.empty();
    }

    public List<ParkingInfo> findAll() {
        return parkingInfoList;
    }

    public List<ParkingInfo> findByExitDateTimeNotNull() {
        List<ParkingInfo> result = new ArrayList<>();
        for (ParkingInfo pi : parkingInfoList) {
            if (pi.getExitDateTime() != null) {
                result.add(pi);
            }
        }
        return result;
    }

    public List<ParkingInfo> findByExitDateTimeIsNull() {
        List<ParkingInfo> result = new ArrayList<>();
        for (ParkingInfo pi : parkingInfoList) {
            if (pi.getExitDateTime() == null) {
                result.add(pi);
            }
        }
        return result;
    }

}