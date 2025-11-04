package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminUserInterface {
    private ContractFileManager manager = new ContractFileManager();
    private Scanner scanner = new Scanner(System.in);

    public void display() {
        boolean quit = false;

        while (!quit) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View All Contracts");
            System.out.println("2. View Last 10 Contracts");
            System.out.println("3. Search Contracts");
            System.out.println("99. Return to Main Menu");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewAll();
                case "2" -> viewLastTen();
                case "3" -> search();
                case "99" -> quit = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewAll() {
        ArrayList<String> contracts = manager.readAllContracts();
        System.out.println("\n--- ALL CONTRACTS ---");
        for (String c : contracts) System.out.println(c);
    }

    private void viewLastTen() {
        ArrayList<String> all = manager.readAllContracts();
        int start = Math.max(0, all.size() - 10);
        System.out.println("\n--- LAST 10 CONTRACTS ---");
        for (int i = start; i < all.size(); i++)
            System.out.println(all.get(i));
    }

    private void search() {
        System.out.print("Enter customer name or SALE/LEASE keyword: ");
        String key = scanner.nextLine();
        ArrayList<String> results = manager.searchContracts(key);
        System.out.println("\n-- SEARCH RESULTS --");
        if (results.isEmpty()) System.out.println("No matches found.");
        else for (String s : results) System.out.println(s);
    }
}
