package com.pluralsight;

public class Main {

    public static void main(String[] args) {
        System.out.println("""
                =======================================
                     🚗 Welcome to the Car Dealership App
                =======================================
                1. Run Console Version
                2. Run GUI Version
                ---------------------------------------
                """);

        try {
            new UserInterface().display();

        } catch (Exception e) {
            System.out.println("An unexpected error occured. Please check that dealership.csv and contracts.csv exist and are formatted correctly.Program terminated.");
        }
    }
}
