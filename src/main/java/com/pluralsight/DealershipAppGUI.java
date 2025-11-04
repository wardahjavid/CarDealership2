package com.pluralsight;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class DealershipAppGUI extends JFrame {
    private final Dealership dealership;
    private final DefaultTableModel model;

    public DealershipAppGUI() {
        dealership = new DealershipFileManager().getDealership();

        setTitle("Car Dealership Management");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        model = new DefaultTableModel(new Object[]{
                "Vehicle ID", "Year", "Make", "Model", "Type", "Color", "Mileage", "Price"
        }, 0);
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        refreshTable(dealership.getAllVehicles());

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton btnSell = new JButton("Sell Vehicle");
        JButton btnLease = new JButton("Lease Vehicle");
        JButton btnAdmin = new JButton("Admin Menu");
        JButton btnReload = new JButton("Reload Inventory");

        btnSell.addActionListener(this::processSale);
        btnLease.addActionListener(this::processLease);
        btnAdmin.addActionListener(e -> new AdminUserInterface().display());
        btnReload.addActionListener(e -> refreshTable(dealership.getAllVehicles()));

        btnPanel.add(btnSell);
        btnPanel.add(btnLease);
        btnPanel.add(btnAdmin);
        btnPanel.add(btnReload);

        panel.add(btnPanel, BorderLayout.SOUTH);
        add(panel);
    }

    public static Object vehicleHeaders() {
        return null;
    }

    private void refreshTable(List<Vehicle> vehicles) {
        model.setRowCount(0);
        for (Vehicle v : vehicles) {
            model.addRow(new Object[]{
                    v.getVehicleId(), v.getYear(), v.getMake(), v.getModel(),
                    v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice()
            });
        }
    }

    private Vehicle findVehicle(String action) {
        String input = JOptionPane.showInputDialog(this, "Enter Vehicle ID to " + action + ":");
        if (input == null || input.isEmpty()) return null;

        try {
            int id = Integer.parseInt(input);
            for (Vehicle v : dealership.getAllVehicles())
                if (v.getVehicleId() == id) return v;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.");
        }

        JOptionPane.showMessageDialog(this, "Vehicle not found!");
        return null;
    }


    private String[] getCustomerInfo() {
        String name = JOptionPane.showInputDialog(this, "Customer name:");
        String email = JOptionPane.showInputDialog(this, "Customer email:");
        if (name == null || email == null || name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Missing customer info!");
            return null;
        }
        return new String[]{name, email};
    }


    private void processSale(ActionEvent e) {
        Vehicle vehicle = findVehicle("sell");
        if (vehicle == null) return;

        String[] info = getCustomerInfo();
        if (info == null) return;

        boolean finance = JOptionPane.showConfirmDialog(this,
                "Finance this sale?", "Finance Option", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;

        SalesContract sc = new SalesContract(LocalDate.now().toString(), info[0], info[1], vehicle, finance);

        // Add-ons
        var addOns = AddOnManager.getAvailableAddOns();
        StringBuilder menu = new StringBuilder("Available Add-ons:\n");
        for (int i = 0; i < addOns.size(); i++)
            menu.append(i + 1).append(". ").append(addOns.get(i)).append("\n");

        String choice = JOptionPane.showInputDialog(this, menu + "\nEnter add-on numbers (comma-separated):");
        if (choice != null && !choice.isEmpty()) {
            for (String num : choice.split(",")) {
                try {
                    int idx = Integer.parseInt(num.trim()) - 1;
                    if (idx >= 0 && idx < addOns.size()) sc.addAddOn(addOns.get(idx));
                } catch (Exception ignored) {}
            }
        }

        new ContractFileManager().saveContract(sc);
        completeTransaction(vehicle);


        showStyledPopup("Sale Complete!", sc.getTotalPrice(), sc.getMonthlyPayment());
    }


    private void processLease(ActionEvent e) {
        Vehicle vehicle = findVehicle("lease");
        if (vehicle == null) return;

        int currentYear = LocalDate.now().getYear();
        if (currentYear - vehicle.getYear() > 3) {
            JOptionPane.showMessageDialog(this, "This vehicle is too old to lease.");
            return;
        }

        String[] info = getCustomerInfo();
        if (info == null) return;

        LeaseContract lc = new LeaseContract(LocalDate.now().toString(), info[0], info[1], vehicle);

        new ContractFileManager().saveContract(lc);
        completeTransaction(vehicle);

        // Styled summary popup
        showStyledPopup("Lease Complete!", lc.getTotalPrice(), lc.getMonthlyPayment());
    }

    private void completeTransaction(Vehicle vehicle) {
        dealership.removeVehicle(vehicle);
        new DealershipFileManager().saveDealership(dealership);
        refreshTable(dealership.getAllVehicles());
    }
    private void showStyledPopup(String title, double total, double monthly) {
        JLabel label = new JLabel("<html><center>" +
                "<h2 style='color:green;'>" + title + "</h2>" +
                "<p style='color:blue;'>Total: $" + String.format("%.2f", total) + "</p>" +
                "<p style='color:#007BFF;'>Monthly: $" + String.format("%.2f", monthly) + "</p>" +
                "</center></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JOptionPane.showMessageDialog(this, label, "Transaction Summary", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DealershipAppGUI().setVisible(true));
    }
}
