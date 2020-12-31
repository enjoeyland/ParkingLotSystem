package parkinglot.data;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
@Getter
public class ParkingInfo {
    private final VehicleType vehicleType;
    private final Vehicle vehicle;

    private final LocalDateTime enterDateTime;

    // 주차장 내에 있는 차량들은 null로 설정
    // 주차장 밖에 있는 차량들은 null이 아님
    private LocalDateTime exitDateTime;

    public ParkingInfo(Vehicle vehicle, LocalDateTime enterDateTime) {
        this.vehicle = vehicle;
        this.vehicleType = VehicleType.typeOf(vehicle);
        this.enterDateTime = enterDateTime;
        this.exitDateTime = null;
    }

    public int getParkingTime() {
        if (exitDateTime == null) {
            throw new RuntimeException("아칙 출차하지 않은 차량이여서 주차시간을 계산할 수 없습니다.");
        }
        return (int) enterDateTime.until(exitDateTime, ChronoUnit.MINUTES);
    }

    public void setExitDateTime(LocalDateTime exitDateTime) {
        if (this.exitDateTime != null) {
            throw new AlreadyExitException("벌써 출차한 차량입니다.");
        } else if (exitDateTime.isBefore(enterDateTime)) {
            throw new ExitBeforeEnterException("출차시간이 입차시간보다 앞입니다.");
        }

        this.exitDateTime = exitDateTime;
    }

    public static class ExitBeforeEnterException extends RuntimeException {
        public ExitBeforeEnterException(String message) {
            super(message);
        }
    }

    public static class AlreadyExitException extends RuntimeException {
        public AlreadyExitException(String message) {
            super(message);
        }
    }

    @Override
    public String toString() {
        return String.format("%-5s %-5s %s", vehicleType, vehicle.getLicensePlateNum(), enterDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
    }
}
