package com.pluralsight;

import java.util.ArrayList;

public class SalesContract extends Contract {
    private double salesTaxAmount;
    private final double recordingFee = 100.00;
    private double processingFee;
    private boolean financeOption;
    private ArrayList<AddOn> addOns = new ArrayList<>();

    public SalesContract(String date, String name, String email,
                         Vehicle v, boolean financeOption) {
        super(date, name, email, v);
        this.financeOption = financeOption;
        this.salesTaxAmount = v.getPrice() * 0.05;
        this.processingFee = (v.getPrice() < 10000) ? 295.00 : 495.00;
    }

    public void addAddOn(AddOn addOn) {
        addOns.add(addOn);
    }

    public ArrayList<AddOn> getAddOns() {
        return addOns;
    }

    @Override
    public double getTotalPrice() {
        double addOnTotal = 0;
        for (AddOn a : addOns)
            addOnTotal += a.getPrice();

        return getVehicleSold().getPrice() + addOnTotal +
                salesTaxAmount + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (!financeOption) return 0.0;

        int months = (getVehicleSold().getPrice() >= 10000) ? 48 : 24;
        double rate = (getVehicleSold().getPrice() >= 10000 ? 4.25 : 5.25) / 1200;

        double payment = getTotalPrice() * (rate * Math.pow(1 + rate, months))
                / (Math.pow(1 + rate, months) - 1);
        return Math.round(payment * 100.0) / 100.0;
    }

    public double getSalesTaxAmount() { return salesTaxAmount; }
    public double getRecordingFee() { return recordingFee; }
    public double getProcessingFee() { return processingFee; }
    public boolean isFinanceOption() { return financeOption; }
}
