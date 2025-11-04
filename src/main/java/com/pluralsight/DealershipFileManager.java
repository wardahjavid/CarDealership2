package com.pluralsight;

import java.io.*;


public class DealershipFileManager {

    private static final String FILE_NAME = "inventory.csv";

    public Dealership getDealership() {
        Dealership dealership = null;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String firstLine = br.readLine();
            String[] dealerInfo = firstLine.split("\\|");
            dealership = new Dealership(dealerInfo[0], dealerInfo[1], dealerInfo[2]);

            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                Vehicle v = new Vehicle(
                        Integer.parseInt(p[0]),
                        Integer.parseInt(p[1]),
                        p[2], p[3], p[4], p[5],
                        Integer.parseInt(p[6]),
                        Double.parseDouble(p[7])
                );
                dealership.addVehicle(v);
            }

        } catch (IOException e) {
            System.out.println("Error reading inventory.csv: " + e.getMessage());
        }
        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            bw.newLine();
            for (Vehicle v : dealership.getAllVehicles()) {
                bw.write(v.getVehicleId() + "|" + v.getYear() + "|" + v.getMake() + "|" + v.getModel() + "|" +
                        v.getVehicleType() + "|" + v.getColor() + "|" + v.getOdometer() + "|" + v.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory.csv: " + e.getMessage());
        }
    }
}
