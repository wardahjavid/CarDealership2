package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContractFileManager {

    private static final String FILE_NAME = "contracts_with_headings.csv";

    public void saveContract(Contract c) {
        File file = new File(FILE_NAME);
        boolean exists = file.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (!exists) {
                bw.write("Type|Date|CustomerName|CustomerEmail|VehicleID|Make|Model|Year|Price|Total|Monthly|Finance|AddOns");
                bw.newLine();
            }

            Vehicle v = c.getVehicleSold();

            if (c instanceof SalesContract) {
                SalesContract sc = (SalesContract) c;
                String addOns = "None";
                if (!sc.getAddOns().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (AddOn a : sc.getAddOns()) {
                        sb.append(a.getName()).append(",");
                    }
                    addOns = sb.toString().replaceAll(",$", "");
                }

                bw.write("SALE|" + c.getDate() + "|" + c.getCustomerName() + "|" + c.getCustomerEmail() + "|" +
                        v.getVehicleId() + "|" + v.getMake() + "|" + v.getModel() + "|" + v.getYear() + "|" +
                        v.getPrice() + "|" + c.getTotalPrice() + "|" + c.getMonthlyPayment() + "|" +
                        (sc.isFinanceOption() ? "YES" : "NO") + "|" + addOns);
                bw.newLine();

            } else if (c instanceof LeaseContract) {
                bw.write("LEASE|" + c.getDate() + "|" + c.getCustomerName() + "|" + c.getCustomerEmail() + "|" +
                        v.getVehicleId() + "|" + v.getMake() + "|" + v.getModel() + "|" + v.getYear() + "|" +
                        v.getPrice() + "|" + c.getTotalPrice() + "|" + c.getMonthlyPayment() + "|N/A|None");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing contracts file: " + e.getMessage());
        }
    }

    public List<Contract> loadContracts() {
        List<Contract> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; }
                String[] p = line.split("\\|");

                String type = p[0];
                Vehicle v = new Vehicle(Integer.parseInt(p[4]), Integer.parseInt(p[7]), p[5], p[6], "Car", "N/A", 0, Double.parseDouble(p[8]));

                if (type.equalsIgnoreCase("SALE")) {
                    SalesContract sc = new SalesContract(p[1], p[2], p[3], v, p[11].equalsIgnoreCase("YES"));
                    if (p.length > 12 && !p[12].equalsIgnoreCase("None")) {
                        for (String s : p[12].split(",")) sc.addAddOn(new AddOn(s.trim(), 0));
                    }
                    list.add(sc);
                } else {
                    list.add(new LeaseContract(p[1], p[2], p[3], v));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading contracts file: " + e.getMessage());
        }
        return list;
    }
}
