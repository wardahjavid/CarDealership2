package com.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class DealershipFileManager {

    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String RESET = "\u001B[0m";

    private static final String FILE_NAME = "inventory.csv";

    public Dealership getDealership() {
        Dealership dealership = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
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
                Vehicle v = new Vehicle(0, 0, "", "", "", "", 0, 0.0);
                v.setVehicleId(Integer.parseInt(data[0]));
                v.setYear(Integer.parseInt(data[1]));
                v.setMake(data[2]);
                v.setModel(data[3]);
                v.setVehicleType(data[4]);
                v.setColor(data[5]);
                v.setOdometer(Integer.parseInt(data[6]));
                v.setPrice(Double.parseDouble(data[7]));

                dealership.addVehicle(v);

                System.out.printf("%-10d %-8d %-12s %-12s %-12s %-10s %-10d $%-12.2f%n",
                        v.getVehicleId(), v.getYear(), v.getMake(), v.getModel(),
                        v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
            }

            System.out.println(CYAN + "==================================================================================================================");
            System.out.println("Total Vehicles Loaded: " + dealership.getAllVehicles().size());
            System.out.println("==================================================================================================================" + RESET);

        } catch (FileNotFoundException e) {
            System.out.println(RED + "There was an error finding the file: inventory.csv file not found." + RESET);
        } catch (IOException e) {
            System.out.println(RED + "There was an error reading the inventory file: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + "There was an unexpected error loading dealership: " + e.getMessage() + RESET);
        }

        return dealership;
    }












