package com.pluralsight;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private final Scanner scanner = new Scanner(System.in);

    public void display() {
        init();
        boolean quit = false;
        while (!quit) {
            System.out.println("\n---------- Menu ----------");
            System.out.println("1. Get vehicles by price");
            System.out.println("2. Get vehicles by make and model");
            System.out.println("3. Get vehicles by year");
            System.out.println("4. Get vehicles by color");
            System.out.println("5. Get vehicles by mileage");
            System.out.println("6. Get vehicles by type");
            System.out.println("7. Get all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            System.out.println("10. Sell or Lease Vehicle");
            System.out.println("11. Admin Login");
            System.out.println("12. ASCII Preview (All Vehicles)");
            System.out.println("99. Quit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": processGetByPriceRequest(); break;
                case "2": processGetByMakeModelRequest(); break;
                case "3": processGetByYearRequest(); break;
                case "4": processGetByColorRequest(); break;
                case "5": processGetByMileageRequest(); break;
                case "6": processGetByVehicleTypeRequest(); break;
                case "7": processGetAllVehiclesRequest(); break;
                case "8": processAddVehicleRequest(); break;
                case "9": processRemoveVehicleRequest(); break;
                case "10": processSellOrLeaseVehicle(); break;
                case "11": processAdminLogin(); break;
                case "12": processAsciiPreviewAll(); break;
                case "99": quit = true; break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void init() {
        dealership = new DealershipFileManager().getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        System.out.println(DealershipAppGUI.render(
                DealershipAppGUI.vehicleHeaders(),
                DealershipAppGUI.vehiclesToRows(vehicles)
        ));
    }

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
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    private void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = readInt();
        System.out.print("Enter maximum year: ");
        int max = readInt();
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    private void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    private void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = readInt();
        System.out.print("Enter maximum mileage: ");
        int max = readInt();
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }

    private void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();
        displayVehicles(dealership.getVehiclesByType(type));
    }

    private void processGetAllVehiclesRequest() {
        displayVehicles(dealership.getAllVehicles());
    }

    private void processAddVehicleRequest() {
        System.out.print("Enter vehicle ID: ");
        int vehicleId = readInt();
        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();
        System.out.print("Enter vehicle year: ");
        int year = readInt();
        System.out.print("Enter vehicle price: ");
        double price = readDouble();
        System.out.print("Enter vehicle color: ");
        String color = scanner.nextLine();
        System.out.print("Enter vehicle mileage: ");
        int mileage = readInt();
        System.out.print("Enter vehicle type (Car, Truck, SUV, Motorcycle): ");
        String type = scanner.nextLine();

        Vehicle vehicle = new Vehicle(vehicleId, year, make, model, type, color, mileage, price);
        dealership.addVehicle(vehicle);
        new DealershipFileManager().saveDealership(dealership);
        System.out.println("Vehicle added successfully!");
    }

    private void processRemoveVehicleRequest() {
        System.out.print("Enter the Vehicle ID of the vehicle you wish to remove: ");
        int vehicleId = readInt();
        Vehicle toRemove = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVehicleId() == vehicleId) { toRemove = v; break; }
        }
        if (toRemove == null) {
            System.out.println("Vehicle not found. Please try again.");
            return;
        }
        dealership.removeVehicle(toRemove);
        new DealershipFileManager().saveDealership(dealership);
        System.out.println("Vehicle removed successfully!");
    }

    private void processSellOrLeaseVehicle() {
        System.out.print("Enter vehicle ID: ");
        int vehicleId = readInt();
        Vehicle selected = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVehicleId() == vehicleId) { selected = v; break; }
        }
        if (selected == null) {
            System.out.println("Vehicle not found.");
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
            SalesContract saleContract = new SalesContract(LocalDate.now().toString(), name, email, selected, finance);

            // Bonus #2: AddOns selection
            System.out.println("\nAvailable Add-Ons:");
            var addOns = AddOnManager.getAvailableAddOns();
            for (int i = 0; i < addOns.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, addOns.get(i));
            }
            System.out.print("Select add-ons by number (comma separated, or blank for none): ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                String[] parts = input.split(",");
                for (String p : parts) {
                    try {
                        int idx = Integer.parseInt(p.trim()) - 1;
                        if (idx >= 0 && idx < addOns.size()) saleContract.addAddOn(addOns.get(idx));
                    } catch (NumberFormatException ignored) {}
                }
            }
            contract = saleContract;

        } else if ("LEASE".equals(type)) {
            int currentYear = LocalDate.now().getYear();
            if (currentYear - selected.getYear() > 3) {
                System.out.println("This vehicle is older than 3 years and cannot be leased.");
                return;
            }
            contract = new LeaseContract(LocalDate.now().toString(), name, email, selected);

        } else {
            System.out.println("Invalid option. Enter SALE or LEASE.");
            return;
        }

        new ContractFileManager().saveContract(contract);
        dealership.removeVehicle(selected);
        new DealershipFileManager().saveDealership(dealership);
        System.out.printf("✅ Contract saved! Total: $%.2f | Monthly: $%.2f%n",
                contract.getTotalPrice(), contract.getMonthlyPayment());
    }

    private void processAsciiPreviewAll() {
        displayVehicles(dealership.getAllVehicles());
    }

    private void processAdminLogin() {
        System.out.print("Enter admin password: ");
        String pass = scanner.nextLine();
        if ("admin123".equals(pass)) {
            new AdminUserInterface().display();
        } else {
            System.out.println("❌ Incorrect password.");
        }
    }

    // Input helpers
    private int readInt() {
        while (true) {
            String s = scanner.nextLine();
            try { return Integer.parseInt(s.trim()); }
            catch (NumberFormatException e) { System.out.print("Enter a valid integer: "); }
        }
    }

    private double readDouble() {
        while (true) {
            String s = scanner.nextLine();
            try { return Double.parseDouble(s.trim()); }
            catch (NumberFormatException e) { System.out.print("Enter a valid number: "); }
        }
    }
}
