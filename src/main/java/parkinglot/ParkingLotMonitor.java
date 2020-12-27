package parkinglot;

import parkinglot.data.ParkingInfo;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingLotMonitor {


    private final ParkingLotManager parkingLotManager;

    public ParkingLotMonitor(ParkingLotManager parkingLotManager) {
        this.parkingLotManager = parkingLotManager;
    }

    public void printTotalIncome() {
        System.out.printf("총 수입은 %,d원입니다.%n",
                ParkingLotUtils.calculateTotalIncome(parkingLotManager.getPayedVehicle()));
    }

    public void printVehicleOnParkingLotList() {
        var vehicleOnParkingLotList = parkingLotManager.getVehicleOnParkingLot();
        if (vehicleOnParkingLotList.size() == 0) {
            System.out.println("주차장에 차량이 없습니다.");
        }
        for (ParkingInfo pi : vehicleOnParkingLotList) {
            System.out.println(pi.toString());
        }
    }
}
