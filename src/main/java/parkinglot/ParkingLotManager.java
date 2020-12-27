package parkinglot;

import parkinglot.data.ParkingInfo;
import parkinglot.data.ParkingInfoDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingLotManager {
    public final static int maximumVehicleNum = 100;
    private int currentVehicleNum = 0;

    private final ParkingInfoDAO parkingInfoDAO;

    public ParkingLotManager(ParkingInfoDAO parkingInfoDAO) {
        this.parkingInfoDAO = parkingInfoDAO;
    }

    public void enterVehicle(ParkingInfo pi) {
        if (isFull()) {
            throw new FullParkingLotException("만차입니다.");
        }
        parkingInfoDAO.save(pi);
        currentVehicleNum++;
    }

    public ParkingInfo findVehicle(String licensePlateNum) {
        ParkingInfo parkingInfo = parkingInfoDAO.findByLicensePlateNum(licensePlateNum).orElse(null);
        if (parkingInfo == null) {
            throw new NoSuchElementException("잘못된 차량번호입니다.");
        }
        return parkingInfo;
    }

    public ParkingInfo exitVehicle(ParkingInfo pi, LocalDateTime exitDateTime) {
        pi.setExitDateTime(exitDateTime);
        currentVehicleNum--;
        return pi;
    }

    public boolean inParkingLot(ParkingInfo parkingInfo) {
        return parkingInfo.getExitDateTime() == null;
    }

    public List<ParkingInfo> getVehicleOnParkingLot() {
        return parkingInfoDAO.findByExitDateTimeIsNull();
    }

    public List<ParkingInfo> getPayedVehicle() {
        return parkingInfoDAO.findByExitDateTimeNotNull();
    }


    public boolean isFull() {
        return currentVehicleNum >= maximumVehicleNum;
    }

    public int getCurrentVehicleNum() {
        return this.currentVehicleNum;
    }

    public static class FullParkingLotException extends RuntimeException {
        public FullParkingLotException(String message) {
            super(message);
        }
    }
}
