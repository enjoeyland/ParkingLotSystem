package parkinglot.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParkingInfoTest {
    Vehicle car = new Car("1234", CarType.NORMAL);
    LocalDateTime enterDateTime = LocalDateTime.of(2020, 12, 31, 10,8);
    ParkingInfo pi = new ParkingInfo(car, enterDateTime);

    @Test
    void getParkingTime_beforeExit_throwException() {
        Exception e = assertThrows(
                RuntimeException.class, pi::getParkingTime);
    }

    @Test
    void setExitDateTime_exitBeforeEnter_throwException() {
        LocalDateTime exitDataTime = LocalDateTime.of(2020, 12, 31, 10,0);
        Exception e = assertThrows(
                ParkingInfo.ExitBeforeEnterException.class,()-> pi.setExitDateTime(exitDataTime));
        assertNull(pi.getExitDateTime());
    }

    @Test
    void setExitDateTime_wellExit() {
        LocalDateTime exitDataTime = LocalDateTime.of(2020, 12, 31, 10,10);
        pi.setExitDateTime(exitDataTime);
        assertEquals(exitDataTime, pi.getExitDateTime());
    }

    @Test
    void setExitDateTime_alreadyExit_throwException() {
        LocalDateTime exitDataTime1 = LocalDateTime.of(2020, 12, 31, 10,10);
        pi.setExitDateTime(exitDataTime1);

        LocalDateTime exitDataTime2 = LocalDateTime.of(2020, 12, 31, 10,12);
        Exception e = assertThrows(
                ParkingInfo.AlreadyExitException.class,()-> pi.setExitDateTime(exitDataTime2));
        assertEquals(exitDataTime1, pi.getExitDateTime());
    }

    @Test
    void getParkingTime_afterExit() {
        LocalDateTime exitDataTime = LocalDateTime.of(2021, 1, 1, 1,10);
        pi.setExitDateTime(exitDataTime);
        assertEquals(15*60+(10-8), pi.getParkingTime());
    }
}