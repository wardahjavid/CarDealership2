package com.pluralsight;

public abstract class Contract {
    private String contractDate;
    private String customerName;
    private String customerEmail;
    private Vehicle vehicleSold;

    public Contract(String date, String name, String email, Vehicle v) {
        this.contractDate = date;
        this.customerName = name;
        this.customerEmail = email;
        this.vehicleSold = v;
    }

    public String getContractDate() { return contractDate; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public Vehicle getVehicleSold() { return vehicleSold; }

    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();
}
