package com.dev.foodreservation.objects;

public class Wallet {
    private long studentRollId;
    private double balance;

    public Wallet(long studentRollId, double balance) {
        this.studentRollId = studentRollId;
        this.balance = balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getStudentRollId() {
        return studentRollId;
    }

    public double getBalance() {
        return balance;
    }
}