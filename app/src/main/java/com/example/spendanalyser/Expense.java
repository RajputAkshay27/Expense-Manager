package com.example.spendanalyser;

public class Expense {
    private String amount;
    private String type;
    private String description;
    private String paidBy;

    public Expense() {
    }

    public Expense(String amount, String type, String description, String paidBy) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.paidBy = paidBy;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }
}
