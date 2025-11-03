package com.pluralsight;


import java.io.*;
import java.util.ArrayList;

public class DealershipFileManager {

    // ANSI color codes for console formatting
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";

    public Dealership getDealership() {
        Dealership dealership = null;
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        System.out.println(YELLOW + "\n LOADING DEALERSHIP DATA " + RESET);
        try (BufferedReader br = new BufferedReader(new FileReader("dealership.csv"))) {
            String line;
            int lineNum = 0;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");

                if (lineNum == 0) {
                    // First line: Dealership info
                    dealership = new Dealership(fields[0], fields[1], fields[2]);
                    System.out.println(GREEN + "Loaded dealership info successfully:" + RESET);
                    System.out.printf(CYAN + "%-20s | %-25s | %-15s%n" + RESET, "Name", "Address", "Phone");
                    System.out.printf("%-20s | %-25s | %-15s%n", fields[0], fields[1], fields[2]);
                    System.out.println("--------------------------------------------------------------");
                    System.out.printf(CYAN + "%-8s | %-6s | %-10s | %-10s | %-8s | %-10s | %-8s | %-10s%n" + RESET,
                            "ID", "Year", "Make", "Model", "Type", "Color", "Miles", "Price");
                    System.out.println("-----------------------------------------------------------------------------------------------");
                } else {
                    // Remaining lines: Vehicle info
                    int vehicleId = Integer.parseInt(fields[0]);
                    int year = Integer.parseInt(fields[1]);
                    String make = fields[2];
                    String model = fields[3];
                    String type = fields[4];
                    String color = fields[5];
                    int odometer = Integer.parseInt(fields[6]);
                    double price = Double.parseDouble(fields[7]);

                    vehicles.add(new Vehicle(vehicleId, year, make, model, type, color, odometer, price));

                    // Print each vehicle as table row
                    System.out.printf("%-8d | %-6d | %-10s | %-10s | %-8s | %-10s | %-8d | $%-10.2f%n",
                            vehicleId, year, make, model, type, color, odometer, price);
                }
                lineNum++;
            }

            System.out.println(GREEN + "\n The dealership and vehicle data loaded successfully!" + RESET);

        } catch (IOException e) {
            System.out.println(RED + "\n The file cannot be opened: dealership.csv" + RESET);
            System.out.println(RED + "The technical reason: " + e.getMessage() + RESET);
        }
        if (dealership != null)
            vehicles.forEach(dealership::addVehicle);

        System.out.println(YELLOW + "= \n" + RESET);
        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        System.out.println(YELLOW + "\n SAVING DEALERSHIP DATA " + RESET);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("dealership.csv"))) {
            // Write dealership info
            bw.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            bw.newLine();

            // Write vehicle list
            for (Vehicle v : dealership.getAllVehicles()) {
                bw.write(v.getVehicleId() + "|" + v.getYear() + "|" + v.getMake() + "|" +
                        v.getModel() + "|" + v.getVehicleType() + "|" + v.getColor() + "|" +
                        v.getOdometer() + "|" + v.getPrice());
                bw.newLine();
            }

            System.out.println(GREEN + "The dealership data is saved successfully to dealership.csv" + RESET);

        } catch (IOException e) {
            System.out.println(RED + "\n This could not be saves to file: dealership.csv" + RESET);
            System.out.println(RED + "The technical reason is: " + e.getMessage() + RESET);
        }

        System.out.println(YELLOW + "= \n" + RESET);
    }
}



    }

}