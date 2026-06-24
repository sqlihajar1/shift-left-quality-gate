package com.example.ecommerce;

public class DemoDuplication {

    public double calculateInvoiceTotal(double price, double tax, int qty) {
        double subtotal = price * qty;
        double taxAmount = subtotal * tax / 100;
        double total = subtotal + taxAmount;
        if (total < 0) throw new IllegalArgumentException("Total invalide");
        if (qty <= 0) throw new IllegalArgumentException("Quantité invalide");
        if (price < 0) throw new IllegalArgumentException("Prix invalide");
        return Math.round(total * 100.0) / 100.0;
    }

    public double calculateOrderTotal(double price, double tax, int qty) {
        double subtotal = price * qty;
        double taxAmount = subtotal * tax / 100;
        double total = subtotal + taxAmount;
        if (total < 0) throw new IllegalArgumentException("Total invalide");
        if (qty <= 0) throw new IllegalArgumentException("Quantité invalide");
        if (price < 0) throw new IllegalArgumentException("Prix invalide");
        return Math.round(total * 100.0) / 100.0;
    }

    public double calculateCartTotal(double price, double tax, int qty) {
        double subtotal = price * qty;
        double taxAmount = subtotal * tax / 100;
        double total = subtotal + taxAmount;
        if (total < 0) throw new IllegalArgumentException("Total invalide");
        if (qty <= 0) throw new IllegalArgumentException("Quantité invalide");
        if (price < 0) throw new IllegalArgumentException("Prix invalide");
        return Math.round(total * 100.0) / 100.0;
    }
}