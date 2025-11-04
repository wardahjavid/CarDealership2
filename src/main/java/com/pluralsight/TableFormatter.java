package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class TableFormatter {

    public static String render(String[] headers, List<String[]> rows) {
        int cols = headers.length;
        int[] widths = new int[cols];

        for (int c = 0; c < cols; c++) {
            widths[c] = headers[c] == null ? 0 : headers[c].length();
        }
        for (String[] r : rows) {
            for (int c = 0; c < cols; c++) {
                String cell = (c < r.length && r[c] != null) ? r[c] : "";
                widths[c] = Math.max(widths[c], cell.length());
            }
        }

        String horizontal = border(widths);
        StringBuilder sb = new StringBuilder();
        sb.append(horizontal);
        sb.append(row(headers, widths));
        sb.append(horizontal);
        for (String[] r : rows) sb.append(row(r, widths));
        sb.append(horizontal);
        return sb.toString();
    }

    private static String border(int[] widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : widths) {
            for (int i = 0; i < w + 2; i++) sb.append("-");
            sb.append("+");
        }
        sb.append("\n");
        return sb.toString();
    }

    private static String row(String[] cells, int[] widths) {
        StringBuilder sb = new StringBuilder("|");
        for (int c = 0; c < widths.length; c++) {
            String val = (c < cells.length && cells[c] != null) ? cells[c] : "";
            sb.append(" ").append(pad(val, widths[c])).append(" |");
        }
        sb.append("\n");
        return sb.toString();
    }

    private static String pad(String s, int w) {
        if (s.length() >= w) return s;
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < w) sb.append(" ");
        return sb.toString();
    }

    public static String[] vehicleHeaders() {
        return new String[]{"VehicleId","Year","Make","Model","Type","Color","Miles","Price"};
        // (VIN renamed → VehicleId)
    }

    public static List<String[]> vehiclesToRows(List<Vehicle> vehicles) {
        List<String[]> rows = new ArrayList<>();
        for (Vehicle v : vehicles) {
            rows.add(new String[]{
                    String.valueOf(v.getVehicleId()),
                    String.valueOf(v.getYear()),
                    v.getMake(),
                    v.getModel(),
                    v.getVehicleType(),
                    v.getColor(),
                    String.valueOf(v.getOdometer()),
                    String.format("%.2f", v.getPrice())
            });
        }
        return rows;
    }

    public static String[] contractHeaders() {
        return new String[]{"Type","Date","Customer","Vehicle","Year","Total","Monthly","AddOns"};
    }

    public static List<String[]> contractsToRows(List<Contract> contracts) {
        List<String[]> rows = new ArrayList<>();
        for (Contract c : contracts) {
            Vehicle v = c.getVehicleSold();
            String type = (c instanceof SalesContract) ? "SALE" : "LEASE";
            String addOns = "-";
            if (c instanceof SalesContract sc && sc.getAddOns() != null && !sc.getAddOns().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < sc.getAddOns().size(); i++) {
                    sb.append(sc.getAddOns().get(i).getName());
                    if (i < sc.getAddOns().size() - 1) sb.append(",");
                }
                addOns = sb.toString();
            }
            rows.add(new String[]{
                    type,
                    c.getDate(),
                    c.getCustomerName(),
                    v.getMake() + " " + v.getModel(),
                    String.valueOf(v.getYear()),
                    String.format("$%.2f", c.getTotalPrice()),
                    String.format("$%.2f", c.getMonthlyPayment()),
                    addOns
            });
        }
        return rows;
    }
}
