package parkinglot.gui;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import parkinglot.ParkingLotManager;
import parkinglot.ParkingLotUtils;
import parkinglot.data.ParkingInfo;
import parkinglot.data.VehicleType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class EnterVehiclePanel extends JPanel {
    private final EnterVehicleScanner scanner = new EnterVehicleScanner();

    private final JTextField licensePlateNumTextField;
    private final JComboBox<String> vehicleTypeCombo;
    private final JTextField specTextField;
    private final JDatePickerImpl datePicker;
    private final TimePicker timePicker;
    private final ParkingSystemFrame parkingSystemFrame;
    private final ParkingLotManager parkingLotManager;


    public EnterVehiclePanel(ParkingSystemFrame parkingSystemFrame, ParkingLotManager parkingLotManager) {
        this.parkingSystemFrame = parkingSystemFrame;
        this.parkingLotManager = parkingLotManager;

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        var licensePlateNumPanel = new JPanel();
        licensePlateNumPanel.add(new JLabel("차량번호"));

        licensePlateNumTextField = new JTextField(10);
        licensePlateNumPanel.add(licensePlateNumTextField);
        inputPanel.add(licensePlateNumPanel);


        var vehicleTypePanel = new JPanel();
        vehicleTypePanel.add(new JLabel("차량종류"));

        vehicleTypeCombo = new JComboBox<>();
        vehicleTypeCombo.addItem("Normal Car");
        vehicleTypeCombo.addItem("Electric Car");
        vehicleTypeCombo.addItem("Bus");
        vehicleTypeCombo.addItem("Truck");
        vehicleTypePanel.add(vehicleTypeCombo);
        inputPanel.add(vehicleTypePanel);


        var specPanel = new JPanel();
        JLabel specLabel = new JLabel("세부정보");
        specLabel.setVisible(false);
        specPanel.add(specLabel);

        specTextField = new JTextField(10);
        specTextField.setText("0");
        specTextField.setVisible(false);
        specPanel.add(specTextField);
        inputPanel.add(specPanel);


        vehicleTypeCombo.addActionListener(event -> {
            String item = vehicleTypeCombo.getItemAt(vehicleTypeCombo.getSelectedIndex());
            switch (item) {
                case "Normal Car" -> {
                    specTextField.setText("0");
                    specLabel.setVisible(false);
                    specTextField.setVisible(false);
                }
                case "Electric Car" -> {
                    specLabel.setText("배터리 잔량");
                    specLabel.setVisible(true);
                    specTextField.setVisible(true);
                }
                case "Bus" -> {
                    specLabel.setText("최대 승객수");
                    specLabel.setVisible(true);
                    specTextField.setVisible(true);
                }
                case "Truck" -> {
                    specLabel.setText("중량");
                    specLabel.setVisible(true);
                    specTextField.setVisible(true);
                }
            }
        });


        var enterDateTimePanel = new JPanel();
        enterDateTimePanel.add(new JLabel("입차시간"));

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
        enterDateTimePanel.add(datePicker);

        timePicker = new TimePicker();
        enterDateTimePanel.add(timePicker);
        inputPanel.add(enterDateTimePanel);

        add(inputPanel);


        var southPanel = new JPanel();

        JButton goBack = new JButton("돌아가기");
        goBack.addActionListener(event-> parkingSystemFrame.showInitPanel());
        southPanel.add(goBack);

        JButton submitBtn = new JButton("입차");
        submitBtn.addActionListener(new SubmitListener());
        southPanel.add(submitBtn);
        add(southPanel, BorderLayout.SOUTH);
    }



    class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                var licensePlateNum = scanner.getLicensePlateNum();
                var vehicleType = scanner.getVehicleType();
                int spec = scanner.getSpec();
                var enterDateTime = scanner.getDateTime();
                ParkingInfo parkingInfo = ParkingLotUtils.createParkingInfo(licensePlateNum, vehicleType, spec, enterDateTime);


                parkingLotManager.enterVehicle(parkingInfo);

                JOptionPane.showMessageDialog(EnterVehiclePanel.this,
                        String.format("%s에 %s %s이(가) 입차합니다.%n",
                                enterDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                                vehicleType,
                                licensePlateNum
                        ), "입차완료", JOptionPane.INFORMATION_MESSAGE);

                parkingSystemFrame.showInitPanel();

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(EnterVehiclePanel.this, e.getMessage(), "입차오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class EnterVehicleScanner {
        public String getLicensePlateNum() {
            String licensePlateNum = licensePlateNumTextField.getText();
            if (licensePlateNum.length() == 0) {
                throw new RuntimeException("차량번호을 입력하세요.");
            }
            return licensePlateNum;
        }

        private VehicleType toVehicleType(String s) {
            if (s.equals("Bus")) {
                return VehicleType.BUS;
            } else if (s.equals("Truck")) {
                return VehicleType.TRUCK;
            } else {
                return VehicleType.CAR;
            }
        }

        public VehicleType getVehicleType() {
            return toVehicleType(vehicleTypeCombo.getItemAt(vehicleTypeCombo.getSelectedIndex()));
        }

        public int getSpec() {
            try {
                return Integer.parseInt(specTextField.getText());
            } catch (NumberFormatException e) {
                throw new RuntimeException("세부사항은 숫자로 입력하세요.");
            }
        }

        public LocalDate getDate() {
            Date d = (Date) datePicker.getModel().getValue();
            if (d == null) {
                throw new RuntimeException("입차 날짜를 선택하세요.");
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
