package com.example.spendanalyser;
public class Person {
    private String personName;
    private double lend;
    private double borrow;

    public Person() {
    }

    public Person(String name) {
        this.personName = name;
        this.borrow = 0.0;
        this.lend = 0.0;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public double getLend() {
        return lend;
    }

    public void setLend(double lend) {
        this.lend = lend;
    }

    public double getBorrow() {
        return borrow;
    }

    public void setBorrow(double borrow) {
        this.borrow = borrow;
    }
}
