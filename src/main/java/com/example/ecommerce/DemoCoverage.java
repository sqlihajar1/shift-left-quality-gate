package com.example.ecommerce;

public class DemoCoverage {

    public String processOrder(String orderId) {
        if (orderId == null) return "null";
        if (orderId.isEmpty()) return "empty";
        if (orderId.length() > 50) return "too long";
        if (orderId.startsWith("ORD")) return "valid";
        return "invalid";
    }

    public double calculateDiscount(double price, int quantity) {
        if (quantity >= 100) return price * 0.30;
        else if (quantity >= 50) return price * 0.20;
        else if (quantity >= 20) return price * 0.10;
        else if (quantity >= 10) return price * 0.05;
        return 0;
    }

    public String getStatus(int code) {
        switch (code) {
            case 1: return "PENDING";
            case 2: return "CONFIRMED";
            case 3: return "SHIPPED";
            case 4: return "DELIVERED";
            case 5: return "CANCELLED";
            default: return "UNKNOWN";
        }
    }

    public boolean validateEmail(String email) {
        if (email == null) return false;
        if (!email.contains("@")) return false;
        if (!email.contains(".")) return false;
        if (email.startsWith("@")) return false;
        return true;
    }

    public int computeTotal(int[] items) {
        int total = 0;
        for (int item : items) {
            if (item > 0) total += item;
        }
        return total;
    }
}