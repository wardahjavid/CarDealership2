package com.pluralsight;

public class LeaseContract extends Contract {
    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(String date, String name, String email, Vehicle v) {
        super(date, name, email, v);
        this.expectedEndingValue = v.getPrice() * 0.5;
        this.leaseFee = v.getPrice() * 0.07;
    }

    @Override
    public double getTotalPrice() {
        return (getVehicleSold().getPrice() - expectedEndingValue) + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        int months = 36;
        double rate = 4.0 / 1200;
        double payment = getTotalPrice() * (rate * Math.pow(1 + rate, months))
                / (Math.pow(1 + rate, months) - 1);
        return Math.round(payment * 100.0) / 100.0;
    }

    public double getExpectedEndingValue() { return expectedEndingValue; }
    public double getLeaseFee() { return leaseFee; }
}
