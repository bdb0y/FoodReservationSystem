package com.dev.foodreservation.objects;

import java.sql.Time;
import java.util.Date;

public class WalletTransaction {
    private long id;
    private long walletId;
    private Date date;
    private Time time;
    private double amount;

    public WalletTransaction(long id, long walletId, Date date, Time time
    ,double amount) {
        this.id = id;
        this.walletId = walletId;
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getWalletId() {
        return walletId;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public double getAmount(){
        return amount;
    }
}
