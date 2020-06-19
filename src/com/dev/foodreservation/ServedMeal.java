package com.dev.foodreservation;

import java.sql.Timestamp;
import java.util.Date;

public class ServedMeal {
    private long    id,
                    mealReservationId;
    private Date date;
    private Timestamp timestamp;

    public ServedMeal(long id, long mealReservationId, Date date, Timestamp timestamp) {
        this.id = id;
        this.mealReservationId = mealReservationId;
        this.date = date;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public long getMealReservationId() {
        return mealReservationId;
    }

    public Date getDate() {
        return date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
