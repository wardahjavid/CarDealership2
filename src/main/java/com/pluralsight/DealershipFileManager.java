package com.pluralsight;


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
    }
}