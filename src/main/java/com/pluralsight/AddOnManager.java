package com.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class AddOnManager {

    public static ArrayList<AddOn> getAvailableAddOns() {
        ArrayList<AddOn> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("addons.csv"))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                list.add(new AddOn(p[0], Double.parseDouble(p[1])));
            }
        } catch (IOException e) {
            System.out.println("addons.csv not found, using default add-ons.");
            list.add(new AddOn("Extended Warranty", 999.99));
            list.add(new AddOn("Rust Protection", 499.99));
            list.add(new AddOn("Tire & Wheel Coverage", 299.99));
            list.add(new AddOn("Paint Protection", 249.99));
            list.add(new AddOn("Roadside Assistance", 199.99));
        }

        return list;
    }
}
