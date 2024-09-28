package entities;

import java.time.LocalDate;

public class Installment {
    private LocalDate dueDate;
    private Double amount;
    private boolean isPaid;

    public Installment(LocalDate dueDate, Double amount) {
        this.dueDate = dueDate;
        this.amount = amount;
        this.isPaid = false; // Default to unpaid
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    @Override
    public String toString() {
        String status = isPaid ? "Paid" : "Unpaid";
        return "Installment due on " + dueDate + ": " + amount + " (" + status + ")";
    }
}
