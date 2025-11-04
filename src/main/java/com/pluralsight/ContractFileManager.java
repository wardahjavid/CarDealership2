package com.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {

    public void saveContract(Contract contract) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("contracts.csv", true))) {

            if (contract instanceof SalesContract sc) {
                StringBuilder addOns = new StringBuilder();
                for (AddOn a : sc.getAddOns())
                    addOns.append(a.getName()).append(",");

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
            } else if (contract instanceof LeaseContract lc) {
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
            System.out.println("Contract saved to contracts.csv");

        } catch (IOException e) {
            System.out.println("There is an error writing the contract: " + e.getMessage());
        }
    }

    public ArrayList<String> readAllContracts() {
        ArrayList<String> contracts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("contracts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) contracts.add(line);
        } catch (IOException e) {
            System.out.println("Error reading contracts.csv: " + e.getMessage());
        }
        return contracts;
    }

    public ArrayList<String> searchContracts(String keyword) {
        ArrayList<String> matches = new ArrayList<>();
        for (String c : readAllContracts())
            if (c.toLowerCase().contains(keyword.toLowerCase()))
                matches.add(c);
        return matches;
    }
}
