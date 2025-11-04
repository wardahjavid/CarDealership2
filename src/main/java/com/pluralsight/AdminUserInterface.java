package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AdminUserInterface {

    private final Scanner scanner = new Scanner(System.in);
    private final ContractFileManager contractManager = new ContractFileManager();

    public void display() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\n────────── Admin Menu ──────────");
            System.out.println("1. List ALL contracts");
            System.out.println("2. List LAST 10 contracts");
            System.out.println("3. Export Contract Report");
            System.out.println("99. Exit Admin Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": listContracts(false); break;
                case "2": listContracts(true); break;
                case "3": exportReport(); break;
                case "99": quit = true; break;
                default: System.out.println("Invalid choice.");
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
        String[] headers = TableFormatter.contractHeaders();
        var rows = TableFormatter.contractsToRows(contracts);
        System.out.println(TableFormatter.render(headers, rows));
        System.out.printf("Displayed %d contract(s).%n", contracts.size());
    }

    private void exportReport() {
        List<Contract> contracts = contractManager.loadContracts();
        if (contracts.isEmpty()) {
            System.out.println("No contracts found to analyze.");
            return;
        }

        int totalSales = 0, totalLeases = 0;
        double totalRevenue = 0.0, totalMonthly = 0.0;

        for (Contract c : contracts) {
            if (c instanceof SalesContract) totalSales++;
            else if (c instanceof LeaseContract) totalLeases++;
            totalRevenue += c.getTotalPrice();
            totalMonthly += c.getMonthlyPayment();
        }

        double avgMonthly = totalMonthly / contracts.size();

        String report = String.format("""
                ────────── Dealership Contract Report ──────────
                Total Contracts: %d
                Total Sales Contracts: %d
                Total Lease Contracts: %d

                Total Revenue (from all contracts): $%.2f
                Average Monthly Payment: $%.2f

                Report Generated: %s
                ────────────────────────────────────────────────
                """,
                contracts.size(), totalSales, totalLeases,
                totalRevenue, avgMonthly, java.time.LocalDate.now());

        System.out.println(report);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contracts_report.txt"))) {
            writer.write(report);
            System.out.println("Report exported to contracts_report.txt");
        } catch (IOException e) {
            System.out.println("There is an error writing report: " + e.getMessage());
        }
    }
}
