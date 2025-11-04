package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner = new Scanner(System.in);
    private DealershipFileManager dfm = new DealershipFileManager();
    private ContractFileManager cfm = new ContractFileManager();

    public void display() {
        dealership = dfm.getDealership();
        boolean quit = false;

        while (!quit) {
            System.out.println("\n-- DEALERSHIP MENU --");
            System.out.println("1. View All Vehicles");
            System.out.println("2. Sell or Lease Vehicle");
            System.out.println("3. Admin Login");
            System.out.println("99. Quit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showAll();
                case "2" -> sellOrLease();
                case "3" -> adminLogin();
                case "99" -> quit = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void showAll() {
        for (Vehicle v : dealership.getAllVehicles())
            System.out.println(v);
    }

    private void sellOrLease() {
        System.out.print("Enter vehicle ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Vehicle v = dealership.findById(id);
        if (v == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Customer name: ");
        String name = scanner.nextLine();
        System.out.print("Customer email: ");
        String email = scanner.nextLine();
        System.out.print("Sale or Lease (S/L): ");
        String type = scanner.nextLine().toUpperCase();

        Contract contract = null;
        if (type.equals("S")) {
            System.out.print("Finance (Y/N): ");
            boolean finance = scanner.nextLine().equalsIgnoreCase("Y");
            SalesContract sc = new SalesContract(LocalDate.now().toString(), name, email, v, finance);
            chooseAddOns(sc);
            contract = sc;
        } else if (type.equals("L")) {
            contract = new LeaseContract(LocalDate.now().toString(), name, email, v);
        }

        if (contract != null) {
            cfm.saveContract(contract);
            dealership.removeVehicle(v);
            dfm.saveDealership(dealership);
        }
    }

    private void chooseAddOns(SalesContract sc) {
        ArrayList<AddOn> available = new ArrayList<>();
        available.add(new AddOn("Nitrogen Tires", 75.00));
        available.add(new AddOn("Window Tinting", 125.00));
        available.add(new AddOn("All-Season Mats", 95.00));
        available.add(new AddOn("Splash Guards", 65.00));
        available.add(new AddOn("Cargo Tray", 85.00));
        available.add(new AddOn("Wheel Locks", 60.00));

        System.out.println("\nAvailable Add-Ons:");
        for (int i = 0; i < available.size(); i++)
            System.out.println((i + 1) + ". " + available.get(i));

        System.out.println("Enter add-on numbers separated by commas, or press Enter to skip:");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            String[] choices = input.split(",");
            for (String c : choices) {
                try {
                    int index = Integer.parseInt(c.trim()) - 1;
                    if (index >= 0 && index < available.size())
                        sc.addAddOn(available.get(index));
                } catch (NumberFormatException ignored) {}
            }
        }
    }

    private void adminLogin() {
        System.out.print("Enter admin password: ");
        if (scanner.nextLine().equals("admin123"))
            new AdminUserInterface().display();
        else
            System.out.println("Incorrect password.");
    }
}
