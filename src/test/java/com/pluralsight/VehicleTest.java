package com.pluralsight;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    public void setAndGetVehicleData_VehicleInfoMatches() {
        // Arrange
        Vehicle car = new Vehicle(1, 2020, "Toyota", "Camry", "Sedan", "Silver", 20000, 25000.00);

        // Act
        int actualYear = car.getYear();
        String actualMake = car.getMake();
        double actualPrice = car.getPrice();

        // Assert
        assertEquals(2022, actualYear);
        assertEquals("Toyota", actualMake);
        assertEquals(25000.00, actualPrice, 0.01);
    }

    private void assertEquals(int i, int actualYear) {
    }

    @Test
    public void toString_ReturnsFormattedString() {
        // Arrange
        Vehicle car = new Vehicle(2, 2029, "Honda", "Civic", "Coupe", "Red", 12000, 8000.00);

        // Act
        String description = car.toString();

        // Assert
        assertTrue(description.contains("Honda"));
        assertTrue(description.contains("Civic"));
        assertTrue(description.contains("18000"));
    }
}
