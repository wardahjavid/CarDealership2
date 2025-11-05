package com.pluralsight;

public class SalesContract extends Contract {
    private final double salesTaxAmount;
    private final double recordingFee = 100.00;
    private final double processingFee;
    private final boolean financeOption;

    public SalesContract(String date, String customerName, String customerEmail,
                         Vehicle vehicleSold, boolean financeOption) {
        super(date, customerName, customerEmail, vehicleSold);
        this.financeOption = financeOption;
        this.salesTaxAmount = vehicleSold.getPrice() * 0.05;
        this.processingFee = vehicleSold.getPrice() >= 10000 ? 495.00 : 295.00;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + salesTaxAmount + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (!financeOption) return 0.0;

        int numberOfPayments;
        double interestRate;
        if (getVehicleSold().getPrice() >= 10000) {
            numberOfPayments = 48;
            interestRate = 4.25 / 1200;
        } else {
            numberOfPayments = 24;
            interestRate = 5.25 / 1200;
        }

        double monthly = getTotalPrice() *
                (interestRate * Math.pow(1 + interestRate, numberOfPayments)) /
                (Math.pow(1 + interestRate, numberOfPayments) - 1);
        monthly = Math.round(monthly * 100) / 100.0;
        return monthly;
    }
}
