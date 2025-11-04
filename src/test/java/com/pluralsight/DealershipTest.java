package com.pluralsight;

import static org.junit.jupiter.api.Assertions.*;

class DealershipTest {
        @Test
        public void addVehicle_AddsToInventory() {
            // Arrange
            Dealership dealer = new Dealership("Auto World", "123 Main St", "555-5555");
            Vehicle car = new Vehicle(1, 2023, "Mazda", "CX-5", "SUV", "Gray", 5000, 28000.0);

            // Act
            dealer.addVehicle(car);

            // Assert
            assertEquals(1, dealer.getAllVehicles().size());
        }

        @Test
        public void removeVehicle_RemovesFromInventory() {
            // Arrange
            Dealership dealer = new Dealership("City Motors", "456 Oak Ave", "555-1212");
            Vehicle car = new Vehicle(2, 2022, "Ford", "Focus", "Sedan", "Blue", 10000, 19000.0);
            dealer.addVehicle(car);

            // Act
            dealer.removeVehicle(car);

            // Assert
            assertTrue(dealer.getAllVehicles().isEmpty());
        }

        @Test
        public void getVehiclesByMakeModel_ReturnsCorrectResults() {
            // Arrange
            Dealership dealer = new Dealership("Motor Plaza", "789 Pine Rd", "555-9090");
            dealer.addVehicle(new Vehicle(3, 2020, "Toyota", "Camry", "Sedan", "Black", 30000, 21000));
            dealer.addVehicle(new Vehicle(4, 2021, "Honda", "Civic", "Coupe", "White", 20000, 18000));

            // Act
            List<Vehicle> result = dealer.getVehiclesByMakeModel("Toyota", "Camry");

            // Assert
            assertEquals(1, result.size());
            assertEquals("Toyota", result.get(0).getMake());
        }
    }


}