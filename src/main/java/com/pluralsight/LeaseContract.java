package com.pluralsight;

public class LeaseContract extends Contract {

    public LeaseContract(String date, String name, String email, Vehicle v) {
        super(date, name, email, v);
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() * 0.5;
    }

    @Override
    public double getMonthlyPayment() {
        return getTotalPrice() / 36;
    }
}
