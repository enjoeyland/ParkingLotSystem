package parkinglot;

import parkinglot.data.ParkingInfo;

import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingLotKiosk {

    private final ParkingScanner parkingScanner;
    private final ParkingLotManager parkingLotManager;

    public ParkingLotKiosk(ParkingLotManager parkingLotManager, ParkingScanner parkingScanner) {
        this.parkingScanner = parkingScanner;
        this.parkingLotManager = parkingLotManager;
    }

    public void enterVehicleAndPrintInfo() {
        try {
            var licensePlateNum = parkingScanner.getLicensePlateNum();
            var vehicleType = parkingScanner.getVehicleType();
            var spec = parkingScanner.getSpec(vehicleType);
            var enterDateTime = parkingScanner.getEnterDateTime();
            var parkingInfo = ParkingLotUtils.createParkingInfo(licensePlateNum,vehicleType,spec,enterDateTime);

            parkingLotManager.enterVehicle(parkingInfo);

            System.out.printf("%s에 %s %s이(가) 입차합니다.%n",
                    enterDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                    vehicleType,
                    licensePlateNum
            );
        } catch (ParkingLotManager.FullParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }


    public void exitVehicleAndPrintFeeAndPrintTimePeriod() {
        ParkingInfo exitVehicleInfo = getParkingInfoByLicensePlateNum();
        exitVehicleInfo = exitVehicle(exitVehicleInfo);
        printFee(exitVehicleInfo);
        printTimePeriod(exitVehicleInfo);
    }

    public void printFee(ParkingInfo exitVehicleInfo) {
        System.out.printf("주차요금은 %,d원입니다.%n", ParkingLotUtils.calculateParkingFee(exitVehicleInfo));
    }

    public void printTimePeriod(ParkingInfo exitVehicleInfo) {
        System.out.printf("주차시간은 %s입니다.%n",
                ParkingLotUtils.getParkingTime(exitVehicleInfo).format(DateTimeFormatter.ofPattern("HH시간 mm분")));
    }

    private ParkingInfo getParkingInfoByLicensePlateNum() {
        while (true) {
            var licensePlateNum = parkingScanner.getLicensePlateNum();
            try {
                var parkingInfo = parkingLotManager.findVehicle(licensePlateNum);
                if (!parkingLotManager.inParkingLot(parkingInfo)) {
                    System.out.println("주차장에 없은 차량입니다.");
                    continue;
                }
                return parkingInfo;
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ParkingInfo exitVehicle(ParkingInfo parkingInfo) {
        while (true) {
            var exitDateTime = parkingScanner.getExitDateTime();
            try {
                return parkingLotManager.exitVehicle(parkingInfo,exitDateTime);
            } catch (ParkingInfo.ExitBeforeEnterException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
