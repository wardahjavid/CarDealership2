package com.pluralsight;

import java.util.ArrayList;

public class SalesContract extends Contract {
    private boolean financeOption;
    private ArrayList<AddOn> addOns = new ArrayList<>();

    public SalesContract(String date, String name, String email, Vehicle v, boolean financeOption) {
        super(date, name, email, v);
        this.financeOption = financeOption;
    }

    public boolean isFinanceOption() { return financeOption; }
    public void setFinanceOption(boolean financeOption) { this.financeOption = financeOption; }

    public ArrayList<AddOn> getAddOns() { return addOns; }
    public void addAddOn(AddOn a) { addOns.add(a); }

    @Override
    public double getTotalPrice() {
        double base = getVehicleSold().getPrice();
        double tax = base * 0.05;
        double addOnTotal = 0;
        for (AddOn a : addOns) addOnTotal += a.getPrice();
        return base + tax + addOnTotal;
    }

    @Override
    public double getMonthlyPayment() {
        if (!financeOption) return 0;
        double rate = 0.0425 / 12;
        int months = 48;
        double total = getTotalPrice();
        return (total * rate) / (1 - Math.pow(1 + rate, -months));
    }
}
