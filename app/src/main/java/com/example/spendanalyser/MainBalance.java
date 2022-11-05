package com.example.spendanalyser;

public class MainBalance {
    private Double mainBalance;
    private Double totalExpense;

    public MainBalance() {
        this.mainBalance = 0.0;
        this.totalExpense = 0.0;
    }

    public Double getMainBalance() {
        return mainBalance;
    }

    public void setMainBalance(Double mainBalance) {
        this.mainBalance = mainBalance;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
