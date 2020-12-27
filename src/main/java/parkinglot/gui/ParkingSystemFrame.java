package parkinglot.gui;

import parkinglot.ParkingLotManager;

import javax.swing.*;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class ParkingSystemFrame extends JFrame {
    private final ParkingLotManager parkingLotManager;
    ParkingMenuPanel initPanel;

    public ParkingSystemFrame(ParkingLotManager parkingLotManager) {
        this.parkingLotManager = parkingLotManager;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,300);
//        setLocationRelativeTo(null);

        initPanel = new ParkingMenuPanel(this);
        showInitPanel();
    }

    public void showInitPanel() {
        getContentPane().removeAll();
        getContentPane().add(initPanel);
        revalidate();
        repaint();
    }

    public void showEnterVehiclePanel() {
        getContentPane().removeAll();
        getContentPane().add(new EnterVehiclePanel(this, parkingLotManager));
        revalidate();
        repaint();
    }

    public void showExitVehiclePanel() {
        getContentPane().removeAll();
        getContentPane().add(new ExitVehiclePanel(this, parkingLotManager));
        revalidate();
        repaint();
    }

    public void showParkingLotListPanel() {
        getContentPane().removeAll();
        getContentPane().add(new ParkingLotListPanel(this, parkingLotManager));
        revalidate();
        repaint();
    }

    public void showTotalIncomePanel() {
        getContentPane().removeAll();
        getContentPane().add(new TotalIncomePanel(this, parkingLotManager));
        revalidate();
        repaint();
    }

//    public static void main(String[] args) {
//        EventQueue.invokeLater(ParkingSystemFrame::new);
//    }
}
