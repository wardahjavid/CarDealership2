package com.pluralsight;

import java.io.*;

public class DealershipFileManager {


    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String RESET = "\u001B[0m";

    private static final String FILE_NAME = "inventory.csv";

    public Dealership getDealership() {
        Dealership dealership = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            // First line: dealership info
            String firstLine = reader.readLine();
            if (firstLine != null) {
                String[] dealerParts = firstLine.split("\\|");
                dealership = new Dealership(dealerParts[0], dealerParts[1], dealerParts[2]);
            }

            System.out.println(CYAN + "\n==================================================================================================================");
            System.out.printf("%-10s %-8s %-12s %-12s %-12s %-10s %-10s %-12s%n",
                    "VEHICLE", "YEAR", "MAKE", "MODEL", "TYPE", "COLOR", "MILEAGE", "PRICE");
            System.out.println("==================================================================================================================" + RESET);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                Vehicle vehicle= new Vehicle(0, 0, "", "", "", "", 0, 0.0);
                vehicle.setVehicleId(Integer.parseInt(data[0]));
                vehicle.setYear(Integer.parseInt(data[1]));
                vehicle.setMake(data[2]);
                vehicle.setModel(data[3]);
                vehicle.setVehicleType(data[4]);
                vehicle.setColor(data[5]);
                vehicle.setOdometer(Integer.parseInt(data[6]));
                vehicle.setPrice(Double.parseDouble(data[7]));

                // Add to dealership
                dealership.addVehicle(vehicle);

                // Print each vehicle row using getters
                System.out.printf("%-10d %-8d %-12s %-12s %-12s %-10s %-10d $%-12.2f%n",
                        vehicle.getVehicleId(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(),
                        vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
            }


            System.out.println(CYAN + "==================================================================================================================");
            System.out.println("Total Vehicles Loaded: " + dealership.getAllVehicles().size());
            System.out.println("==================================================================================================================" + RESET);

        } catch (FileNotFoundException e) {
            System.out.println(RED + "There was an error: inventory.csv file not found." + RESET);
        } catch (IOException e) {
            System.out.println(RED + "There was an error reading inventory file: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + "There was an unexpected error loading dealership: " + e.getMessage() + RESET);
        }

        return dealership;
    }


    public void saveDealership(Dealership dealership) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            writer.newLine();

            for (Vehicle v : dealership.getAllVehicles()) {
                writer.write(v.getVehicleId() + "|" +
                        v.getYear() + "|" +
                        v.getMake() + "|" +
                        v.getModel() + "|" +
                        v.getVehicleType() + "|" +
                        v.getColor() + "|" +
                        v.getOdometer() + "|" +
                        v.getPrice());
                writer.newLine();
            }

            System.out.println(GREEN + "The dealership inventory saved successfully!" + RESET);

        } catch (IOException e) {
            System.out.println(RED + "There was an error saving dealership file: " + e.getMessage() + RESET);
        }
    }
}
