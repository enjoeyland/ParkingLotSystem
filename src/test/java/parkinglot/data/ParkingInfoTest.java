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
        // Setup

        // Run the test
        assertThrows(RuntimeException.class, pi::getParkingTime);

        // Verify the results
    }

    @Test
    void setExitDateTime_exitBeforeEnter_throwException() {
        // Setup
        LocalDateTime exitDataTime = LocalDateTime.of(2020, 12, 31, 10,0);

        // Run the test
        assertThrows(ParkingInfo.ExitBeforeEnterException.class,
                ()-> pi.setExitDateTime(exitDataTime));

        // Verify the results
        assertNull(pi.getExitDateTime());
    }

    @Test
    void setExitDateTime_wellExit() {
        // Setup
        LocalDateTime exitDataTime = LocalDateTime.of(2020, 12, 31, 10,10);

        // Run the test
        pi.setExitDateTime(exitDataTime);

        // Verify the results
        assertEquals(exitDataTime, pi.getExitDateTime());
    }

    @Test
    void setExitDateTime_alreadyExit_throwException() {
        // Setup
        LocalDateTime exitDataTime1 = LocalDateTime.of(2020, 12, 31, 10,10);
        pi.setExitDateTime(exitDataTime1);
        LocalDateTime exitDataTime2 = LocalDateTime.of(2020, 12, 31, 10,12);

        // Run the test
        Exception e = assertThrows(ParkingInfo.AlreadyExitException.class,
                ()-> pi.setExitDateTime(exitDataTime2));

        // Verify the results
        assertEquals(exitDataTime1, pi.getExitDateTime());
    }

    @Test
    void getParkingTime_afterExit() {
        // Setup
        LocalDateTime exitDataTime = LocalDateTime.of(2021, 1, 1, 1,10);

        // Run the test
        pi.setExitDateTime(exitDataTime);

        // Verify the results
        assertEquals(15*60+(10-8), pi.getParkingTime());
    }
}