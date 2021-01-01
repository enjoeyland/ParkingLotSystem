package parkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import parkinglot.data.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ParkingLotManagerTest {

    @Mock
    private ParkingInfoDAO mockParkingInfoDAO;

    private ParkingLotManager parkingLotManagerUnderTest;

    Vehicle car = new Car("1234", CarType.NORMAL);
    int maximumVehicleNum = 1;
    @BeforeEach
    void setUp() {
        initMocks(this);

        parkingLotManagerUnderTest = new ParkingLotManager(mockParkingInfoDAO, maximumVehicleNum);
    }

    @Test
    void testEnterVehicle() {
        // Setup
        final ParkingInfo pi = new ParkingInfo(car, LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        int preVehicleNum = parkingLotManagerUnderTest.getCurrentVehicleNum();

        // Configure ParkingInfoDAO.save(...).
        when(mockParkingInfoDAO.save(any(ParkingInfo.class))).thenReturn(pi);

        // Run the test
        parkingLotManagerUnderTest.enterVehicle(pi);

        // Verify the results
        assertThat(parkingLotManagerUnderTest.getCurrentVehicleNum(), is(preVehicleNum+1));
    }



    @Test
    void testEnterVehicle_throwFullParkingLotException() {
        // Configure ParkingInfoDAO.save(...).
        final ParkingInfo pi = new ParkingInfo(car, LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockParkingInfoDAO.save(any(ParkingInfo.class))).thenReturn(pi);

        // Setup
        parkingLotManagerUnderTest.enterVehicle(pi);
        final ParkingInfo pi2 = new ParkingInfo(car, LocalDateTime.of(2020, 1, 2, 0, 0, 0));

        // Run the test
        // Verify the results
        assertThrows(ParkingLotManager.FullParkingLotException.class,()->parkingLotManagerUnderTest.enterVehicle(pi2));
        assertThat(parkingLotManagerUnderTest.getCurrentVehicleNum(), is(maximumVehicleNum));
    }

    @Test
    void testFindVehicle() {
        // Setup
        Vehicle car = new Car("1234", CarType.NORMAL);

        // Configure ParkingInfoDAO.findByLicensePlateNum(...).
        final Optional<ParkingInfo> parkingInfo = Optional.of(new ParkingInfo(car, LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockParkingInfoDAO.findByLicensePlateNum("1234")).thenReturn(parkingInfo);

        // Run the test
        final ParkingInfo result = parkingLotManagerUnderTest.findVehicle("1234");

        // Verify the results
        assertThat(result.getVehicle().getLicensePlateNum(), is("1234"));
    }

    @Test
    void testFindVehicle_ParkingInfoDAOReturnsAbsent() {
        // Setup
        when(mockParkingInfoDAO.findByLicensePlateNum(any(String.class))).thenReturn(Optional.empty());

        // Run the test
        // Verify the results
        assertThrows(NoSuchElementException.class,
                ()-> parkingLotManagerUnderTest.findVehicle("2345"));
    }

    @Test
    void testExitVehicle() {
        // Setup
        final ParkingInfo pi = new ParkingInfo(car, LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        parkingLotManagerUnderTest.enterVehicle(pi);
        final int preVehicleNum = parkingLotManagerUnderTest.getCurrentVehicleNum();

        // Run the test
        final ParkingInfo result = parkingLotManagerUnderTest.exitVehicle(pi, LocalDateTime.of(2020, 1, 1, 3, 0, 0));

        // Verify the results
        assertThat(result.getParkingTime(), is(3*60));
        assertThat(parkingLotManagerUnderTest.getCurrentVehicleNum(), allOf(greaterThanOrEqualTo(0),is(preVehicleNum-1)));
    }

    @Test
    void testGetVehicleOnParkingLot() {
        // Setup

        // Configure ParkingInfoDAO.findByExitDateTimeIsNull(...).
        final List<ParkingInfo> parkingInfos = List.of(new ParkingInfo(car, LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockParkingInfoDAO.findByExitDateTimeIsNull()).thenReturn(parkingInfos);

        // Run the test
        final List<ParkingInfo> result = parkingLotManagerUnderTest.getVehicleOnParkingLot();

        // Verify the results
        for (ParkingInfo pi : result) {
            assertTrue(parkingLotManagerUnderTest.inParkingLot(pi));
        }
    }

    @Test
    void testGetPayedVehicle() {
        // Setup
        var pi1 = new ParkingInfo(car, LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        pi1.setExitDateTime(LocalDateTime.of(2020, 1, 1, 10, 0, 0));

        // Configure ParkingInfoDAO.findByExitDateTimeNotNull(...).
        final List<ParkingInfo> parkingInfos = List.of(pi1);
        when(mockParkingInfoDAO.findByExitDateTimeNotNull()).thenReturn(parkingInfos);

        // Run the test
        final List<ParkingInfo> result = parkingLotManagerUnderTest.getPayedVehicle();

        // Verify the results
        for (ParkingInfo pi : result) {
            assertFalse(parkingLotManagerUnderTest.inParkingLot(pi));
        }
    }

}
