package com.dev.foodreservation.objects;

import java.sql.Timestamp;
import java.util.Date;

public class WalletTransaction {
    private long id;
    private long walletId;
    private Date date;
    private Timestamp timestamp;

    public WalletTransaction(long id, long walletId, Date date, Timestamp timestamp) {
        this.id = id;
        this.walletId = walletId;
        this.date = date;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
