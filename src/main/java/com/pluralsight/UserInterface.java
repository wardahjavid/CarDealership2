package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private final Scanner scanner = new Scanner(System.in);

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    public void display() {
        init();
        boolean quit = false;

        while (!quit) {
            System.out.println(BLUE + "\n------------------------------------------" + RESET);
            System.out.println(BLUE + "          🚗 DEALERSHIP MAIN MENU 🚗        " + RESET);
            System.out.println(BLUE + "------------------------------------------" + RESET);
            System.out.println(YELLOW + "1" + RESET + " - Get vehicles by price");
            System.out.println(YELLOW + "2" + RESET + " - Get vehicles by make/model");
            System.out.println(YELLOW + "3" + RESET + " - Get vehicles by year");
            System.out.println(YELLOW + "4" + RESET + " - Get vehicles by color");
            System.out.println(YELLOW + "5" + RESET + " - Get vehicles by mileage");
            System.out.println(YELLOW + "6" + RESET + " - Get vehicles by type");
            System.out.println(YELLOW + "7" + RESET + " - List all vehicles");
            System.out.println(YELLOW + "8" + RESET + " - Add a vehicle");
            System.out.println(YELLOW + "9" + RESET + " - Remove a vehicle");
            System.out.println(YELLOW + "10" + RESET + " - Sell or Lease a vehicle");
            System.out.println(YELLOW + "11" + RESET + " - Admin Login");
            System.out.println(RED + "99" + RESET + " - Quit");
            System.out.print(CYAN + "\nEnter your choice: " + RESET);

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": processGetByPriceRequest(); break;
                case "2": processGetByMakeModelRequest(); break;
                case "3": processGetByYearRequest(); break;
                case "4": processGetByColorRequest(); break;
                case "5": processGetByMileageRequest(); break;
                case "6": processGetByVehicleTypeRequest(); break;
                case "7": displayVehicles(dealership.getAllVehicles()); break;
                case "8": processAddVehicleRequest(); break;
                case "9": processRemoveVehicleRequest(); break;
                case "10": processSellOrLeaseVehicle(); break;
                case "11": processAdminLogin(); break;
                case "99":
                    System.out.println(GREEN + "\nThank you for using the Dealership App! Goodbye!" + RESET);
                    quit = true;
                    break;
                default:
                    System.out.println(RED + "Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void init() {
        dealership = new DealershipFileManager().getDealership();
    }

    // ===== MENU METHODS =====
    private void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = readDouble();
        System.out.print("Enter maximum price: ");
        double max = readDouble();
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }

    private void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        displayVehicles((ArrayList<Vehicle>) dealership.getVehiclesByMakeModel(make, model));
    }

    private void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = readInt();
        System.out.print("Enter maximum year: ");
        int max = readInt();
        displayVehicles((ArrayList<Vehicle>) dealership.getVehiclesByYear(min, max));
    }

    private void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        displayVehicles((ArrayList<Vehicle>) dealership.getVehiclesByColor(color));
    }

    private void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = readInt();
        System.out.print("Enter maximum mileage: ");
        int max = readInt();
        displayVehicles((ArrayList<Vehicle>) dealership.getVehiclesByMileage(min, max));
    }

    private void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();
        displayVehicles((ArrayList<Vehicle>) dealership.getVehiclesByType(type));
    }

    private void processAddVehicleRequest() {
        System.out.print("Enter vehicle ID: ");
        int vehicleId = readInt();
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = readInt();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        System.out.print("Enter mileage: ");
        int mileage = readInt();
        System.out.print("Enter price: ");
        double price = readDouble();

        Vehicle vehicle = new Vehicle(vehicleId, year, make, model, type, color, mileage, price);
        dealership.addVehicle(vehicle);
        new DealershipFileManager().saveDealership(dealership);
        System.out.println(GREEN + "✅ Vehicle added successfully!" + RESET);
    }

    private void processRemoveVehicleRequest() {
        System.out.print("Enter Vehicle ID to remove: ");
        int id = readInt();
        Vehicle toRemove = null;

        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVehicleId() == id) {
                toRemove = v;
                break;
            }
        }

        if (toRemove == null) {
            System.out.println(RED + "Vehicle not found." + RESET);
        } else {
            dealership.removeVehicle(toRemove);
            new DealershipFileManager().saveDealership(dealership);
            System.out.println(GREEN + "✅ Vehicle removed successfully!" + RESET);
        }
    }

    private void processSellOrLeaseVehicle() {
        System.out.print("Enter vehicle ID: ");
        int id = readInt();
        Vehicle selected = null;

        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVehicleId() == id) {
                selected = v;
                break;
            }
        }

        if (selected == null) {
            System.out.println(RED + "Vehicle not found." + RESET);
            return;
        }

        System.out.print("Customer name: ");
        String name = scanner.nextLine();
        System.out.print("Customer email: ");
        String email = scanner.nextLine();
        System.out.print("Is this a SALE or LEASE? ");
        String type = scanner.nextLine().trim().toUpperCase();

        Contract contract;

        if ("SALE".equals(type)) {
            System.out.print("Finance? (YES/NO): ");
            boolean finance = scanner.nextLine().trim().equalsIgnoreCase("YES");
            contract = new SalesContract(LocalDate.now().toString(), name, email, selected, finance);
        } else if ("LEASE".equals(type)) {
            int currentYear = LocalDate.now().getYear();
            if (currentYear - selected.getYear() > 3) {
                System.out.println(RED + "❌ This vehicle is older than 3 years and cannot be leased." + RESET);
                return;
            }
            contract = new LeaseContract(LocalDate.now().toString(), name, email, selected);
        } else {
            System.out.println(RED + "Invalid input. Please type SALE or LEASE." + RESET);
            return;
        }

        new ContractFileManager().saveContract(contract);
        dealership.removeVehicle(selected);
        new DealershipFileManager().saveDealership(dealership);

        System.out.printf(GREEN + "\n✅ Contract saved successfully!%nTotal: $%.2f | Monthly: $%.2f%n" + RESET,
                contract.getTotalPrice(), contract.getMonthlyPayment());
    }

    private void processAdminLogin() {
        System.out.print(CYAN + "Enter admin password: " + RESET);
        String pass = scanner.nextLine();
        if ("admin123".equals(pass)) {
            new AdminUserInterface().display();
        } else {
            System.out.println(RED + "❌ Incorrect password." + RESET);
        }
    }

    // ===== DISPLAY TABLE =====
    private void displayVehicles(ArrayList<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println(RED + "No vehicles found." + RESET);
            return;
        }

        System.out.println(CYAN + "\n--------------------------------------------------------------" + RESET);
        System.out.printf(YELLOW + "%-8s %-6s %-10s %-12s %-10s %-10s %-10s %-10s%n" + RESET,
                "ID", "YEAR", "MAKE", "MODEL", "TYPE", "COLOR", "MILES", "PRICE");
        System.out.println(CYAN + "--------------------------------------------------------------" + RESET);

        boolean alternate = true;
        for (Vehicle v : vehicles) {
            String color = alternate ? GREEN : CYAN;
            System.out.printf(color + "%-8d %-6d %-10s %-12s %-10s %-10s %-10d $%-10.2f" + RESET + "\n",
                    v.getVehicleId(), v.getYear(), v.getMake(), v.getModel(),
                    v.getType(), v.getColor(), v.getOdometer(), v.getPrice());
            alternate = !alternate;
        }

        System.out.println(CYAN + "--------------------------------------------------------------" + RESET);
    }

    // ===== INPUT HELPERS =====
    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(RED + "Enter a valid integer: " + RESET);
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(RED + "Enter a valid number: " + RESET);
            }
        }
    }
}
