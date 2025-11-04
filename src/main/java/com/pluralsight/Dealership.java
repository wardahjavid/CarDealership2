package com.pluralsight;

import java.util.ArrayList;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public ArrayList<Vehicle> getAllVehicles() { return inventory; }

    public void addVehicle(Vehicle v) { inventory.add(v); }
    public void removeVehicle(Vehicle v) { inventory.remove(v); }

    public Vehicle findById(int id) {
        for (Vehicle v : inventory)
            if (v.getVehicleId() == id) return v;
        return null;
    }
}
