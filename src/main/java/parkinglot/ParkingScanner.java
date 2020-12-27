package parkinglot;

import parkinglot.data.VehicleType;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingScanner {
    private static final Scanner sc = new Scanner(System.in);

    public VehicleType getVehicleType() {
        while (true) {
            System.out.print("차량 종류를 입력하세요. [승용차(c), 트럭(t), 버스(b)]\n> ");
            String carType = sc.nextLine();
            switch (carType) {
                case "c":
                    return VehicleType.CAR;
                case "t":
                    return VehicleType.TRUCK;
                case "b":
                    return VehicleType.BUS;
                default:
                    System.out.println("잘못된 차량 종류입니다.");
            }
        }
    }

    public int getSpec(VehicleType vehicleType) {
        if (vehicleType == VehicleType.CAR) {
            return getLeftBattery();
        } else if (vehicleType == VehicleType.TRUCK) {
            return getWeight();
        } else { //vehicleType == parkinglot.data.VehicleType.BUS
            return getSeatCapacity();
        }
    }

    public String getLicensePlateNum() {
        System.out.print("차량번호를 입력하세요. (4자리 숫자)\n> ");
        return sc.nextLine();
    }

    public LocalDateTime getEnterDateTime() {
        System.out.println("입차 시간을 입력하세요. (년 월 일 시 분)");
        return getDateTime();
    }

    public LocalDateTime getExitDateTime() {
        System.out.println("출차 시간을 입력하세요. (년 월 일 시 분)");
        return getDateTime();
    }


    private static LocalDateTime getDateTime() {
        while (true) {
            try {
                System.out.print("> ");
                List<Integer> timeList = Arrays.stream(sc.nextLine().split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                return LocalDateTime.of(
                        timeList.get(0),
                        timeList.get(1),
                        timeList.get(2),
                        timeList.get(3),
                        timeList.get(4)
                );
            } catch (IndexOutOfBoundsException e) {
                System.out.println("입력값이 부족해요");
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력하세요.");
            } catch (DateTimeException e) {
                System.out.println("존재하지 않은 날짜입니다.");
            }
        }
    }

    private static int getLeftBattery() {
        while (true) {
            System.out.print("일반차이면 0을, 전기차이면 현재 배터리 잔량을 입력해주세요. (60 >=현재 배터리 잔량 > 0)\n> ");
            int result = Integer.parseInt(sc.nextLine());
            if (result > 60 || result < 0) {
                System.out.println("올바른 배터리 잔량을 입력하세요.");
                continue;
            }
            return result;
        }

    }

    private static int getWeight() {
        while (true) {
            System.out.print("트럭의 중량을 입력해주세요.\n> ");
            int result = Integer.parseInt(sc.nextLine());
            if (result <= 0) {
                System.out.println("양수로 입력하세요.");
                continue;
            }
            return result;
        }
    }

    private static int getSeatCapacity() {
        while (true) {
            System.out.print("버스의 최대 승객수를 입력해주세요.\n> ");
            int result = Integer.parseInt(sc.nextLine());
            if (result <= 0) {
                System.out.println("양수로 입력하세요.");
                continue;
            }
            return result;
        }
    }
}