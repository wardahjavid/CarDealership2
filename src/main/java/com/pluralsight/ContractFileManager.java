package com.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m";

    public void saveContract(Contract c) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contracts.csv", true))) {
            Vehicle v = c.getVehicleSold();

            if (c instanceof SalesContract) {
                SalesContract sc = (SalesContract) c;

                String addOns = "None";
                ArrayList<AddOn> addOnList = sc.getAddOns();
                if (!addOnList.isEmpty()) {
                    addOns = "";
                    for (int i = 0; i < addOnList.size(); i++) {
                        addOns += addOnList.get(i).getName();
                        if (i < addOnList.size() - 1) {
                            addOns += ",";
                        }
                    }
                }

                writer.write("SALE|" + c.getDate() + "|" + c.getCustomerName() + "|" + c.getCustomerEmail() + "|" +
                        v.getVehicleId() + "|" + v.getMake() + "|" + v.getModel() + "|" + v.getYear() + "|" +
                        v.getPrice() + "|" + c.getTotalPrice() + "|" + c.getMonthlyPayment() + "|" +
                        (sc.isFinanceOption() ? "YES" : "NO") + "|" + addOns);
                writer.newLine();

                System.out.println(GREEN + "The sale contract saved successfully!" + RESET);

            } else if (c instanceof LeaseContract) {
                LeaseContract lc = (LeaseContract) c;

                writer.write("LEASE|" + c.getDate() + "|" + c.getCustomerName() + "|" + c.getCustomerEmail() + "|" +
                        v.getVehicleId() + "|" + v.getMake() + "|" + v.getModel() + "|" + v.getYear() + "|" +
                        v.getPrice() + "|" + c.getTotalPrice() + "|" + c.getMonthlyPayment());
                writer.newLine();

                System.out.println(GREEN + "The lease contract was saved successfully!" + RESET);
            }

        } catch (IOException e) {
            System.out.println(RED + "⚠️ Error saving contract: " + e.getMessage() + RESET);
        }
    }

    public ArrayList<Contract> loadContracts() {
        ArrayList<Contract> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("contracts.csv"))) {
            String line;
            System.out.println(CYAN + "\n============================================================================================================================");
            System.out.printf("%-8s %-12s %-20s %-20s %-10s %-10s %-10s %-10s %-12s %-12s %-10s %-15s%n",
                    "TYPE", "DATE", "CUSTOMER", "EMAIL", "VID", "MAKE", "MODEL", "YEAR",
                    "PRICE", "TOTAL", "MONTHLY", "OPTIONS");
            System.out.println("============================================================================================================================" + RESET);

            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|");
                String type = p[0];

                int vehicleId = Integer.parseInt(p[4]);
                String make = p[5];
                String model = p[6];
                int year = Integer.parseInt(p[7]);
                double price = Double.parseDouble(p[8]);

                Vehicle v = new Vehicle(vehicleId, year, make, model, "Car", "N/A", 0, price);

                if (type.equals("SALE")) {
                    boolean finance = p[11].equals("YES");
                    SalesContract sc = new SalesContract(p[1], p[2], p[3], v, finance);

                    if (p.length > 12 && !p[12].equals("None")) {
                        String[] addOnNames = p[12].split(",");
                        for (String name : addOnNames) {
                            sc.addAddOn(new AddOn(name.trim(), 0));
                        }
                    }

                    list.add(sc);

                    // Table row output
                    System.out.printf("%-8s %-12s %-20s %-20s %-10d %-10s %-10s %-10d %-12.2f %-12.2f %-10.2f %-15s%n",
                            "SALE", p[1], p[2], p[3], vehicleId, make, model, year,
                            price, sc.getTotalPrice(), sc.getMonthlyPayment(),
                            finance ? "YES" : "NO");

                } else if (type.equals("LEASE")) {
                    LeaseContract lc = new LeaseContract(p[1], p[2], p[3], v);
                    list.add(lc);

                    // Table row output
                    System.out.printf("%-8s %-12s %-20s %-20s %-10d %-10s %-10s %-10d %-12.2f %-12.2f %-10.2f %-15s%n",
                            "LEASE", p[1], p[2], p[3], vehicleId, make, model, year,
                            price, lc.getTotalPrice(), lc.getMonthlyPayment(), "N/A");
                }
            }

            System.out.println(CYAN + "============================================================================================================================");
            System.out.println("📄 Total Contracts Loaded: " + list.size());
            System.out.println("============================================================================================================================" + RESET);

        } catch (IOException e) {
            System.out.println(RED + "⚠️ Error loading contracts: " + e.getMessage() + RESET);
        }

        return list;
    }
}

        }
    }
}
