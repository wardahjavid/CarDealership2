package com.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {

    // Save a contract to CSV safely (handles Windows file lock)
    public void saveContract(Contract contract) {
        String fileName = "contracts_with_headings.csv";  // unified file

        for (int attempt = 1; attempt <= 3; attempt++) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

                if (contract instanceof SalesContract sc) {
                    StringBuilder addOns = new StringBuilder();

                    // Include add-ons if present
                    if (sc.getAddOns() != null && !sc.getAddOns().isEmpty()) {
                        for (AddOn a : sc.getAddOns()) {
                            addOns.append(a.getName()).append(", ");
                        }
                        addOns = new StringBuilder(addOns.substring(0, addOns.length() - 2)); // trim last comma
                    } else {
                        addOns.append("None");
                    }

                    bw.write(String.format(
                            "SALE|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%s|%.2f|%s",
                            sc.getContractDate(), sc.getCustomerName(), sc.getCustomerEmail(),
                            sc.getVehicleSold().getVehicleId(), sc.getVehicleSold().getYear(),
                            sc.getVehicleSold().getMake(), sc.getVehicleSold().getModel(),
                            sc.getVehicleSold().getVehicleType(), sc.getVehicleSold().getColor(),
                            sc.getVehicleSold().getOdometer(), sc.getVehicleSold().getPrice(),
                            sc.getSalesTaxAmount(), sc.getRecordingFee(), sc.getProcessingFee(),
                            sc.getTotalPrice(), sc.isFinanceOption() ? "YES" : "NO",
                            sc.getMonthlyPayment(), addOns.toString()));
                }
                else if (contract instanceof LeaseContract lc) {
                    bw.write(String.format(
                            "LEASE|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f",
                            lc.getContractDate(), lc.getCustomerName(), lc.getCustomerEmail(),
                            lc.getVehicleSold().getVehicleId(), lc.getVehicleSold().getYear(),
                            lc.getVehicleSold().getMake(), lc.getVehicleSold().getModel(),
                            lc.getVehicleSold().getVehicleType(), lc.getVehicleSold().getColor(),
                            lc.getVehicleSold().getOdometer(), lc.getVehicleSold().getPrice(),
                            lc.getExpectedEndingValue(), lc.getLeaseFee(),
                            lc.getTotalPrice(), lc.getMonthlyPayment()));
                }

                bw.newLine();
                System.out.println("\n✅ Contract successfully saved to " + fileName);
                System.out.println("--------------------------------------------");
                System.out.println("Customer: " + contract.getCustomerName());
                System.out.println("Vehicle:  " + contract.getVehicleSold().getMake() + " " +
                        contract.getVehicleSold().getModel());
                System.out.println("Total Cost: $" + String.format("%.2f", contract.getTotalPrice()));
                System.out.println("Monthly Payment: $" + String.format("%.2f", contract.getMonthlyPayment()));
                System.out.println("--------------------------------------------");
                return; // success → exit retry loop

            } catch (IOException e) {
                System.out.println("⚠️  Attempt " + attempt + ": File may be locked, retrying...");
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            }
        }

        System.out.println("❌ Error: Could not save contract after multiple attempts.");
    }

    // Optional: read all contracts
    public ArrayList<String> readAllContracts() {
        ArrayList<String> contracts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("contracts_with_headings.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                contracts.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading contracts_with_headings.csv: " + e.getMessage());
        }
        return contracts;
    }
}
