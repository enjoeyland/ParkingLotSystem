package parkinglot.gui;

import parkinglot.ParkingLotManager;
import parkinglot.data.ParkingInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingLotListPanel extends JPanel {
    public ParkingLotListPanel(ParkingSystemFrame parkingSystemFrame, ParkingLotManager parkingLotManager){
        setLayout(new BorderLayout());

        var ParkingLotListTM = new DefaultTableModel(new Object[0][3], new Object[]{"차량종류", "차량번호", "입차시간"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (ParkingInfo pi: parkingLotManager.getVehicleOnParkingLot()){
            ParkingLotListTM.addRow(new Object[]{
                    pi.getVehicleType(),
                    pi.getVehicle().getLicensePlateNum(),
                    pi.getEnterDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
            });
        }
        JTable parkingLotList = new JTable(ParkingLotListTM);

        JScrollPane parkingLotListPanel = new JScrollPane(parkingLotList);
        add(parkingLotListPanel);

        var southPanel = new JPanel();
        JButton goBack = new JButton("돌아가기");
        goBack.addActionListener(event-> parkingSystemFrame.showInitPanel());
        southPanel.add(goBack);
        add(southPanel, BorderLayout.SOUTH);
    }
}
