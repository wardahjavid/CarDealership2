package com.pluralsight;

import java.time.LocalDate;
import java.util.Scanner;

public class UserInterface {

    private final ContractFileManager fileManager = new ContractFileManager();
    private final Scanner scanner = new Scanner(System.in);

    public void display() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\n--- MAIN DEALERSHIP MENU ---");
            System.out.println("1. Sell vehicle");
            System.out.println("2. Lease vehicle");
            System.out.println("3. Admin view contracts");
            System.out.println("99. Quit");
            System.out.print("Enter option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> sellVehicle();
                case "2" -> leaseVehicle();
                case "3" -> new AdminUserInterface().display();
                case "99" -> quit = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void sellVehicle() {
        System.out.print("Customer name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Vehicle make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Odometer: ");
        int odo = Integer.parseInt(scanner.nextLine());
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Finance? (yes/no): ");
        boolean finance = scanner.nextLine().equalsIgnoreCase("yes");

        Vehicle v = new Vehicle((int)(Math.random()*100000), year, make, model, type, color, odo, price);
        SalesContract sc = new SalesContract(LocalDate.now().toString(), name, email, v, finance);
        fileManager.saveContract(sc);
    }

    private void leaseVehicle() {
        System.out.print("Customer name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Vehicle make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Odometer: ");
        int odo = Integer.parseInt(scanner.nextLine());
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Vehicle v = new Vehicle((int)(Math.random()*100000), year, make, model, type, color, odo, price);
        LeaseContract lc = new LeaseContract(LocalDate.now().toString(), name, email, v);
        fileManager.saveContract(lc);
    }
}
