package parkinglot.gui;

import parkinglot.ParkingLotManager;
import parkinglot.ParkingLotUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class TotalIncomePanel extends JPanel {

    public TotalIncomePanel(ParkingSystemFrame parkingSystemFrame, ParkingLotManager parkingLotManager) {
        setLayout(new BorderLayout());

        String totalIncomeText = String.format("총 수입은 %d원 입니다.",
                ParkingLotUtils.calculateTotalIncome(parkingLotManager.getPayedVehicle()));
        var totalIncomeLabel = new JLabel(totalIncomeText, SwingConstants.CENTER);
        totalIncomeLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        add(totalIncomeLabel);

        var southPanel = new JPanel();
        JButton okayBtn = new JButton("확인");
        okayBtn.addActionListener(event-> parkingSystemFrame.showInitPanel());
        southPanel.add(okayBtn);
        add(southPanel, BorderLayout.SOUTH);
    }
}
