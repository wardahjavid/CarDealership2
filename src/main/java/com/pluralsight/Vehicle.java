package com.pluralsight;

public class Vehicle {
    private int vehicleId;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;

    public Vehicle(int vehicleId, int year, String make, String model,
                   String vehicleType, String color, int odometer, double price) {
        this.vehicleId = vehicleId;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    public int getVehicleId() { return vehicleId; }
    public int getYear() { return year; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getVehicleType() { return vehicleType; }
    public String getColor() { return color; }
    public int getOdometer() { return odometer; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("%-5d %-6d %-10s %-10s %-8s %-10s %-8d $%.2f",
                vehicleId, year, make, model, vehicleType, color, odometer, price);
    }
}
