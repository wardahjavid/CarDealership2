package com.pluralsight;

import java.io.*;

public class ContractFileManager {

    private static final String CONTRACT_FILE = "contracts_with_headings.csv";

    public ContractFileManager() {
        ensureFileHasHeader();
    }

    private void ensureFileHasHeader() {
        File file = new File(CONTRACT_FILE);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONTRACT_FILE))) {
                writer.write("Type|contract date|name|email|car id|year|make|model|vehicle type|color|odometer|price|sales tax|recording fee|processing fee|total cost|finance|monthly payment");
                writer.newLine();
                System.out.println("Created file with headings: " + CONTRACT_FILE);
            } catch (IOException e) {
                System.out.println("Unable to create contracts_with_headings.csv: " + e.getMessage());
            }
        }
    }

    public void saveContract(Contract contract) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONTRACT_FILE, true))) {
            Vehicle v = contract.getVehicleSold();
            StringBuilder sb = new StringBuilder();

            if (contract instanceof SalesContract sc) {
                sb.append("SALE|").append(contract.getDate()).append("|")
                        .append(contract.getCustomerName()).append("|")
                        .append(contract.getCustomerEmail()).append("|")
                        .append(v.getVehicleId()).append("|").append(v.getYear()).append("|")
                        .append(v.getMake()).append("|").append(v.getModel()).append("|")
                        .append(v.getVehicleType()).append("|").append(v.getColor()).append("|")
                        .append(v.getOdometer()).append("|").append(String.format("%.2f", v.getPrice())).append("|")
                        .append(String.format("%.2f", v.getPrice() * 0.05)).append("|")
                        .append("100.00|")
                        .append(String.format("%.2f", v.getPrice() >= 10000 ? 495.00 : 295.00)).append("|")
                        .append(String.format("%.2f", sc.getTotalPrice())).append("|")
                        .append(sc.getMonthlyPayment() > 0 ? "YES" : "NO").append("|")
                        .append(String.format("%.2f", sc.getMonthlyPayment()));
            } else if (contract instanceof LeaseContract lc) {
                sb.append("LEASE|").append(contract.getDate()).append("|")
                        .append(contract.getCustomerName()).append("|")
                        .append(contract.getCustomerEmail()).append("|")
                        .append(v.getVehicleId()).append("|").append(v.getYear()).append("|")
                        .append(v.getMake()).append("|").append(v.getModel()).append("|")
                        .append(v.getVehicleType()).append("|").append(v.getColor()).append("|")
                        .append(v.getOdometer()).append("|").append(String.format("%.2f", v.getPrice())).append("|")
                        .append(String.format("%.2f", v.getPrice() * 0.5)).append("|")
                        .append(String.format("%.2f", v.getPrice() * 0.07)).append("|")
                        .append(String.format("%.2f", lc.getTotalPrice())).append("|")
                        .append("N/A|")
                        .append(String.format("%.2f", lc.getMonthlyPayment()));
            }

            writer.write(sb.toString());
            writer.newLine();
            System.out.println("The contract is saved to 'contracts_with_headings.csv'");

        } catch (IOException e) {
            System.out.println("Error saving contract: " + e.getMessage());
        }
    }
}
