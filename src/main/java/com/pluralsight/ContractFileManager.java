package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContractFileManager {

    public void saveContract(Contract c) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("contracts.csv", true))) {
            Vehicle v = c.getVehicleSold();

            if (c instanceof SalesContract sc) {
                String addOns = sc.getAddOns().isEmpty()
                        ? "None"
                        : sc.getAddOns().stream().map(AddOn::getName).collect(Collectors.joining(","));
                String line = String.join("|",
                        "SALE",
                        c.getDate(),
                        c.getCustomerName(),
                        c.getCustomerEmail(),
                        String.valueOf(v.getVehicleId()),
                        v.getMake(),
                        v.getModel(),
                        String.valueOf(v.getYear()),
                        String.format("%.2f", v.getPrice()),
                        String.format("%.2f", c.getTotalPrice()),
                        String.format("%.2f", c.getMonthlyPayment()),
                        sc.isFinanceOption() ? "YES" : "NO",
                        addOns
                );
                bw.write(line);
                bw.newLine();
            } else if (c instanceof LeaseContract) {
                String line = String.join("|",
                        "LEASE",
                        c.getDate(),
                        c.getCustomerName(),
                        c.getCustomerEmail(),
                        String.valueOf(v.getVehicleId()),
                        v.getMake(),
                        v.getModel(),
                        String.valueOf(v.getYear()),
                        String.format("%.2f", v.getPrice()),
                        String.format("%.2f", c.getTotalPrice()),
                        String.format("%.2f", c.getMonthlyPayment())
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contract: " + e.getMessage());
        }
    }

    public List<Contract> loadContracts() {
        List<Contract> list = new ArrayList<>();
        File f = new File("contracts.csv");
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length < 11) continue; // basic sanity

                String type = p[0];
                String date = p[1];
                String name = p[2];
                String email = p[3];

                int vehicleId = Integer.parseInt(p[4]);
                String make = p[5];
                String model = p[6];
                int year = Integer.parseInt(p[7]);
                double price = Double.parseDouble(p[8]);
                Vehicle v = new Vehicle(vehicleId, year, make, model, "Car", "N/A", 0, price);

                if ("SALE".equalsIgnoreCase(type)) {
                    boolean finance = p[11].equalsIgnoreCase("YES");
                    SalesContract sc = new SalesContract(date, name, email, v, finance);
                    if (p.length > 12 && !"None".equalsIgnoreCase(p[12])) {
                        String[] names = p[12].split(",");
                        for (String an : names) sc.addAddOn(new AddOn(an.trim(), 0));
                    }
                    list.add(sc);
                } else if ("LEASE".equalsIgnoreCase(type)) {
                    list.add(new LeaseContract(date, name, email, v));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading contracts: " + e.getMessage());
        }
        return list;
    }
}
