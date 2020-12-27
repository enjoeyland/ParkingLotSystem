package parkinglot;

import parkinglot.data.ParkingInfoDAO;
import parkinglot.gui.ParkingSystemFrame;

import java.awt.*;
import java.util.Scanner;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingLot {
    public static final boolean USE_GUI = true;

    public static void main(String[] args) {
        // create objects
        var parkingInfoDAO = new ParkingInfoDAO();
        var parkingLotManager = new ParkingLotManager(parkingInfoDAO);

        if (USE_GUI) {
            EventQueue.invokeLater(()->new ParkingSystemFrame(parkingLotManager));
        } else {
            interactWithTerminal(parkingLotManager);
        }
    }

    private static void interactWithTerminal(ParkingLotManager parkingLotManager) {
        var parkingScanner = new ParkingScanner();

        var parkingLotMonitor = new ParkingLotMonitor(parkingLotManager);
        var parkingLotKiosk = new ParkingLotKiosk(parkingLotManager, parkingScanner);

        // select option from menu
        Scanner sc = new Scanner(System.in);
        String option = "";
        while (!option.equals("5")) {
            System.out.print("\n1. 입차\n2. 출차\n3. 주차차량 보기\n4. 총 수입 보기\n5. 종료\n> ");
            option = sc.nextLine();
            switch (option) {
                case "1":
                    parkingLotKiosk.enterVehicleAndPrintInfo();
                    break;
                case "2":
                    parkingLotKiosk.exitVehicleAndPrintFeeAndPrintTimePeriod();
                    break;
                case "3":
                    parkingLotMonitor.printVehicleOnParkingLotList();
                    break;
                case "4":
                    parkingLotMonitor.printTotalIncome();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("잘못된 옵션값입니다.");
                    break;
            }
        }
    }
}
