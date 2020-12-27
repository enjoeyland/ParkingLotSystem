package parkinglot.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingMenuPanel extends JPanel {
    public ParkingMenuPanel(ParkingSystemFrame parkingSystemFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 30)));
        addButton("입차", event->parkingSystemFrame.showEnterVehiclePanel());
        addButton("출차", event->parkingSystemFrame.showExitVehiclePanel());
        addButton("주차 목록",event->parkingSystemFrame.showParkingLotListPanel());
        addButton("총 수입", event->parkingSystemFrame.showTotalIncomePanel());
    }

    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.setFont(new Font("Dialog", Font.BOLD, 15));
        button.addActionListener(listener);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createCompoundBorder(
                button.getBorder(),
                BorderFactory.createEmptyBorder(2,20,2,20))
        );
        add(button);
        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}

