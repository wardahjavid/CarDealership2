package com.pluralsight;

public class LeaseContract extends Contract {
    private final double expectedEndingValue;
    private final double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
        this.expectedEndingValue = vehicleSold.getPrice() * 0.50;
        this.leaseFee = vehicleSold.getPrice() * 0.07;
    }

    public double getExpectedEndingValue() { return expectedEndingValue; }
    public double getLeaseFee() { return leaseFee; }

    @Override
    public double getTotalPrice() {
        return (getVehicleSold().getPrice() - expectedEndingValue) + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        int months = 36;
        double rate = 4.0 / 1200;
        double payment = getTotalPrice() * (rate * Math.pow(1 + rate, months)) / (Math.pow(1 + rate, months) - 1);
        return Math.round(payment * 100) / 100.0;
    }
}
