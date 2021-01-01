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
    public final int maximumVehicleNum;
    private int currentVehicleNum = 0;

    private final ParkingInfoDAO parkingInfoDAO;

    public ParkingLotManager(ParkingInfoDAO parkingInfoDAO, int maximumVehicleNum) {
        this.parkingInfoDAO = parkingInfoDAO;
        this.maximumVehicleNum = maximumVehicleNum;
    }

    public void enterVehicle(ParkingInfo pi) {
        // todo : 주차장에 있는 차량과 같은 차량번호인 차량이 입차하려 할 때 - 기존 입차 정보 무시
        //  (실생활에서는 차량번호 인식기에 오류 있을 수 있고 또는 주차장 구조가 이상할 수 도있다.)
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

    /**
     * have potential to throw runtime exception {@see ParkingInfo#getExitDateTime(ParkingInfo) getExitDateTime}
     */
    public ParkingInfo exitVehicle(ParkingInfo pi, LocalDateTime exitDateTime) {
        pi.setExitDateTime(exitDateTime);
        // todo : dao에 저장이 안되어 있는 parkingInfo를 exit하게 되면 currentVehicleNum이 음수가 될 수 있다.
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
