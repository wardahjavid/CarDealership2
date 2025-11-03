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

