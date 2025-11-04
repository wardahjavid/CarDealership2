package com.pluralsight;

import static org.junit.jupiter.api.Assertions.*;

class DealershipTest {
        @Test
        public void addVehicle_AddsToInventory() {
            // Arrange
            Dealership dealer = new Dealership("Potato Crescent Auto", "145 Main St", "888-9999");
            Vehicle car = new Vehicle(1, 2023, "Mazda", "CX-5", "SUV", "Light Blue", 3000, 12000.0);

            // Act
            dealer.addVehicle(car);

            // Assert
            assertEquals(1, dealer.getAllVehicles().size());
        }

        @Test
        public void removeVehicle_RemovesFromInventory() {
            // Arrange
            Dealership dealer = new Dealership("Potato Star Auto", "235 Potato Road", "777-2222");
            Vehicle car = new Vehicle(2, 2019, "Ford", "Focus", "Sedan", "Red", 12000, 15000.0);
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