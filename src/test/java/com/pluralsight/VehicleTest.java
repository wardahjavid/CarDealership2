package com.pluralsight;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    public void testVehicleGettersAndSetters() {
        Vehicle v = new Vehicle(1, 2022, "Toyota", "Camry", "Sedan", "Blue", 15000, 25000.0);

        assertEquals(1, v.getVehicleId());
        assertEquals(2022, v.getYear());
        assertEquals("Toyota", v.getMake());
        assertEquals("Camry", v.getModel());
        assertEquals("Sedan", v.getVehicleType());
        assertEquals("Blue", v.getColor());
        assertEquals(15000, v.getOdometer());
        assertEquals(25000.0, v.getPrice(), 0.01);

        v.setPrice(19999.99);
        v.setColor("Red");
        assertEquals(19999.99, v.getPrice(), 0.01);
        assertEquals("Red", v.getColor());
    }

    @Test
    public void testToStringFormatting() {
        Vehicle v = new Vehicle(5, 2020, "Honda", "Civic", "Coupe", "White", 30000, 18000.00);
        String output = v.toString();
        assertTrue(output.contains("Honda"));
        assertTrue(output.contains("Civic"));
        assertTrue(output.contains("18000"));
    }
}
