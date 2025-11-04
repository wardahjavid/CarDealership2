package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AdminUserInterface {
    private final ContractFileManager contractManager = new ContractFileManager();
    private final Scanner scanner = new Scanner(System.in);

    public void display() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\n─── Admin Menu ───");
            System.out.println("1. List ALL contracts");
            System.out.println("2. List LAST 10 contracts");
            System.out.println("3. Export report");
            System.out.println("99. Exit");
            System.out.print("Choice: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> listContracts(false);
                case "2" -> listContracts(true);
                case "3" -> exportReport();
                case "99" -> quit = true;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void listContracts(boolean lastTenOnly) {
        List<Contract> contracts = contractManager.loadContracts();
        if (contracts.isEmpty()) {
            System.out.println("No contracts found.");
            return;
        }

        if (lastTenOnly && contracts.size() > 10) {
            contracts = contracts.subList(contracts.size() - 10, contracts.size());
        }

        System.out.println("\n    Contracts ");
        for (Contract c : contracts) {
            System.out.printf("[%s] %s | %s | $%.2f total | $%.2f/month%n",
                    c instanceof SalesContract ? "SALE" : "LEASE",
                    c.getCustomerName(), c.getVehicleSold().getModel(),
                    c.getTotalPrice(), c.getMonthlyPayment());
        }
        System.out.println("---------------------------------------");
    }

    private void exportReport() {
        List<Contract> contracts = contractManager.loadContracts();
        if (contracts.isEmpty()) {
            System.out.println("No contracts found to export.");
            return;
        }

        int sales = 0, leases = 0;
        double totalRevenue = 0;
        for (Contract c : contracts) {
            if (c instanceof SalesContract) sales++;
            else leases++;
            totalRevenue += c.getTotalPrice();
        }

        String report = String.format("""
                ───────── Dealership Contract Report ─────────
                Total Contracts: %d
                Sales Contracts: %d
                Lease Contracts: %d
                Total Revenue: $%.2f
                Generated: %s
                ─────────────────────────────────────────────
                """, contracts.size(), sales, leases, totalRevenue, java.time.LocalDate.now());

        System.out.println(report);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("contract_report.txt"))) {
            bw.write(report);
            System.out.println("✅ Report saved to contract_report.txt");
        } catch (IOException e) {
            System.out.println("Error exporting report: " + e.getMessage());
        }
    }
}
