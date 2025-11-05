package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AdminUserInterface {

    public void display() {
        String fileName = "contracts_with_headings.csv";
        System.out.println("\n--- ADMIN CONTRACT LIST (" + fileName + ") ---");

        int count = 0;
        double totalSales = 0;
        double totalMonthlyPayments = 0;
        boolean firstLine = true;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {  // skip heading row
                    System.out.println(line);
                    firstLine = false;
                    continue;
                }

                System.out.println(line);
                count++;

                String[] parts = line.split("\\|");
                if (parts.length >= 17) {
                    try {
                        totalSales += Double.parseDouble(parts[15]);
                        totalMonthlyPayments += Double.parseDouble(parts[17]);
                    } catch (NumberFormatException ignored) {}
                }
            }

            // 🧾 Print summary after listing all lines
            System.out.println("\n--- SUMMARY REPORT ---");
            System.out.printf("Total Contracts: %d%n", count);
            System.out.printf("Total Sales Value: $%.2f%n", totalSales);
            System.out.printf("Average Monthly Payment: $%.2f%n",
                    count > 0 ? totalMonthlyPayments / count : 0);

        } catch (IOException e) {
            System.out.println("⚠️ No contracts found: " + e.getMessage());
        }
    }
}
