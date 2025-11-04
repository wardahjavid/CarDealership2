package com.pluralsight;

import java.io.*;

public class DealershipFileManager {

    public Dealership getDealership() {
        try (BufferedReader br = new BufferedReader(new FileReader("dealership.csv"))) {
            String header = br.readLine(); // name|address|phone
            String[] parts = header.split("\\|");
            Dealership d = new Dealership(parts[0], parts[1], parts[2]);

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                int id = Integer.parseInt(data[0]);
                int year = Integer.parseInt(data[1]);
                String make = data[2];
                String model = data[3];
                String type = data[4];
                String color = data[5];
                int odometer = Integer.parseInt(data[6]);
                double price = Double.parseDouble(data[7]);
                d.addVehicle(new Vehicle(id, year, make, model, type, color, odometer, price));
            }
            return d;
        } catch (IOException e) {
            System.out.println("Error loading dealership.csv: " + e.getMessage());
            return null;
        }
    }

    public void saveDealership(Dealership d) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("dealership.csv"))) {
            bw.write(d.getName() + "|" + d.getAddress() + "|" + d.getPhone());
            bw.newLine();
            for (Vehicle v : d.getAllVehicles()) {
                bw.write(String.format("%d|%d|%s|%s|%s|%s|%d|%.2f",
                        v.getVehicleId(), v.getYear(), v.getMake(), v.getModel(),
                        v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice()));
                bw.newLine();
            }
            System.out.println("The dealership.csv saved successfully.");
        } catch (IOException e) {
            System.out.println("There is an error saving dealership.csv: " + e.getMessage());
        }
    }
}
