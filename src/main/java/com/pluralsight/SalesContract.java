package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class SalesContract extends Contract {
    private final double salesTaxAmount;
    private final double recordingFee = 100.0;
    private final double processingFee;
    private final boolean financeOption;
    private final List<AddOn> addOns = new ArrayList<>();

    public SalesContract(String date, String customerName, String customerEmail,
                         Vehicle vehicleSold, boolean financeOption) {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTaxAmount = vehicleSold.getPrice() * 0.05;
        this.processingFee = (vehicleSold.getPrice() < 10000) ? 295.0 : 495.0;
        this.financeOption = financeOption;
    }

    public void addAddOn(AddOn addOn) { addOns.add(addOn); }
    public List<AddOn> getAddOns() { return addOns; }
    public boolean isFinanceOption() { return financeOption; }

    @Override
    public double getTotalPrice() {
        double addOnTotal = addOns.stream().mapToDouble(AddOn::getPrice).sum();
        return getVehicleSold().getPrice() + salesTaxAmount + recordingFee + processingFee + addOnTotal;
    }

    @Override
    public double getMonthlyPayment() {
        if (!financeOption) return 0.0;
        double price = getVehicleSold().getPrice();
        int months = (price >= 10000) ? 48 : 24;
        double rate = (price >= 10000) ? 4.25 / 1200 : 5.25 / 1200;

        double principal = getTotalPrice();
        double payment = principal * (rate * Math.pow(1 + rate, months)) / (Math.pow(1 + rate, months) - 1);
        return Math.round(payment * 100) / 100.0;
    }
}
