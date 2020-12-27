package parkinglot.gui;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import parkinglot.ParkingLotManager;
import parkinglot.ParkingLotUtils;
import parkinglot.data.ParkingInfo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ExitVehiclePanel extends JPanel {
    private final ExitVehicleScanner scanner = new ExitVehicleScanner();
    private ParkingInfo parkingInfo;

    private final JDatePickerImpl datePicker;
    private final TimePicker timePicker;
    private final JLabel exitVehicleInfo;



    public ExitVehiclePanel(ParkingSystemFrame parkingSystemFrame, ParkingLotManager parkingLotManager) {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JButton findVehicleBtn = new JButton("차량찾기");
        findVehicleBtn.setAlignmentX(SwingConstants.CENTER);
        findVehicleBtn.addActionListener(event -> {
            String licensePlateNum = JOptionPane.showInputDialog(this, "차량번호를 입력하세요", "차량찾기", JOptionPane.PLAIN_MESSAGE);
            if (licensePlateNum != null && licensePlateNum.length()>0) {
                try {
                    this.parkingInfo = parkingLotManager.findVehicle(licensePlateNum);
                    if (!parkingLotManager.inParkingLot(parkingInfo)) {
                        throw new RuntimeException("주차장에 없은 차량입니다.");
                    }
                    showParkingInfo(parkingInfo);
                } catch (RuntimeException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "차량찾기 오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        inputPanel.add(findVehicleBtn);


        exitVehicleInfo = new JLabel("");
        exitVehicleInfo.setVisible(false);
        exitVehicleInfo.setPreferredSize(new Dimension(300,100));
        inputPanel.add(exitVehicleInfo);

        var exitDateTimePanel = new JPanel();
        exitDateTimePanel.add(new JLabel("출차시간"));

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
        exitDateTimePanel.add(datePicker);

        timePicker = new TimePicker();
        exitDateTimePanel.add(timePicker);
        inputPanel.add(exitDateTimePanel);


        add(inputPanel);


        var southPanel = new JPanel();

        JButton goBack = new JButton("돌아가기");
        goBack.addActionListener(event-> parkingSystemFrame.showInitPanel());
        southPanel.add(goBack);

        JButton submitBtn = new JButton("출차");
        submitBtn.addActionListener(event-> {
            try {
                parkingInfo = scanner.getParkingInfo();
                LocalDateTime exitDateTime = scanner.getDateTime();
                this.parkingInfo = parkingLotManager.exitVehicle(parkingInfo,exitDateTime);

                JOptionPane.showMessageDialog(this,
                        String.format("주차요금은 %d원입니다.%n주차시간은 %s입니다.",
                                ParkingLotUtils.calculateParkingFee(parkingInfo),
                                ParkingLotUtils.getParkingTime(parkingInfo).format(DateTimeFormatter.ofPattern("HH시간 mm분"))
                        ), "출차완료", JOptionPane.INFORMATION_MESSAGE);

                parkingSystemFrame.showInitPanel();

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "출차오류", JOptionPane.ERROR_MESSAGE);
            }
        });
        southPanel.add(submitBtn);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void showParkingInfo(ParkingInfo parkingInfo) {
        var text = String.format("<html>차량종류 : %s <br/>차량번호 : %s <br/>입차시간 : %s</html>",
                parkingInfo.getVehicleType(),
                parkingInfo.getVehicle().getLicensePlateNum(),
                parkingInfo.getEnterDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
        );
        exitVehicleInfo.setText(text);
        exitVehicleInfo.setVisible(true);
    }

    class ExitVehicleScanner {
        public ParkingInfo getParkingInfo() {
            if (parkingInfo == null) {
                throw new RuntimeException("출차할 차량을 선택하세요");
            }
            return parkingInfo;
        }

        public LocalDate getDate() {
            Date d = (Date) datePicker.getModel().getValue();
            if (d == null) {
                throw new RuntimeException("출차 날짜를 선택하세요.");
            }
            return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        public LocalTime getTime() {
            return timePicker.getTime();
        }

        public LocalDateTime getDateTime() {
            return getDate().atTime(getTime());
        }
    }
}
