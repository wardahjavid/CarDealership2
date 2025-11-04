package com.pluralsight;

import java.util.Arrays;
import java.util.List;

public class AddOnManager {
    public static List<AddOn> getAvailableAddOns() {
        return Arrays.asList(
                new AddOn("Nitrogen Tires", 150),
                new AddOn("Window Tinting", 200),
                new AddOn("All-Season Floor Mats", 100),
                new AddOn("Splash Guards", 85),
                new AddOn("Cargo Tray", 120),
                new AddOn("Wheel Locks", 75)
        );
    }
}
