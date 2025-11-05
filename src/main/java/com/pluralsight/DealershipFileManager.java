package com.pluralsight;

import java.io.*;


public class DealershipFileManager {

    private static final String INVENTORY_FILE = "inventory.csv";


    public Dealership getDealership() {
        Dealership dealership = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {

            String firstLine = reader.readLine();
            if (firstLine == null) return null;
            String[] dealerParts = firstLine.split("\\|");
            dealership = new Dealership(dealerParts[0], dealerParts[1], dealerParts[2]);

            // remaining lines: vehicle info
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int vehicleId = Integer.parseInt(parts[0]);
                int year = Integer.parseInt(parts[1]);
                String make = parts[2];
                String model = parts[3];
                String type = parts[4];
                String color = parts[5];
                int odometer = Integer.parseInt(parts[6]);
                double price = Double.parseDouble(parts[7]);
                Vehicle v = new Vehicle(vehicleId, year, make, model, type, color, odometer, price);
                dealership.addVehicle(v);
            }

        } catch (IOException e) {
            System.out.println("⚠️ Error loading dealership file: " + e.getMessage());
        }

        return dealership;
    }
    public void saveDealership(Dealership dealership) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {

            writer.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            writer.newLine();


            for (Vehicle v : dealership.getAllVehicles()) {
                writer.write(v.getVehicleId() + "|" + v.getYear() + "|" + v.getMake() + "|" +
                        v.getModel() + "|" + v.getVehicleType() + "|" + v.getColor() + "|" +
                        v.getOdometer() + "|" + v.getPrice());
                writer.newLine();
            }

            System.out.println("Dealership saved successfully to inventory.csv");

        } catch (IOException e) {
            System.out.println("There is an error saving the dealership: " + e.getMessage());
        }
    }
}
