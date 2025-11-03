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

        }
    }
}
