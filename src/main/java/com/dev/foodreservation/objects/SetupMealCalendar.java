package com.dev.foodreservation.objects;

import com.dev.foodreservation.database.utilities.DateToPersianDate;

import java.sql.Date;

public class SetupMealCalendar {
    Date date;
    String day;
    String breakfastName, launchName, dinnerName;
    int kitchenId;
    int bmId, breakfastMealId, totalBF,
            lmId, launchMealId, totalL,
            dmId, dinnerMealId, totalD;

    public SetupMealCalendar(Date date, int kitchenId) {
        this.date = date;
        this.kitchenId = kitchenId;
        this.day = new DateToPersianDate().get(date)
                .getDayOfWeek().toString();
        this.breakfastName = "-";
        this.breakfastMealId = -1;
        this.bmId = -1;
        this.launchName = "-";
        this.launchMealId = -1;
        this.lmId = -1;
        this.dinnerName = "-";
        this.dinnerMealId = -1;
        this.dmId = -1;
    }


    public SetupMealCalendar(Date date,
                             int breakfastMealId,
                             int launchMealId,
                             int dinnerMealId,
                             int bmId,
                             String breakfastName,
                             int totalBF,
                             int lmId,
                             String launchName,
                             int totalL,
                             int dmId,
                             String dinnerName,
                             int totalD) {
        this.date = date;
        this.day = new DateToPersianDate().get(date)
                .getDayOfWeek().toString();
        this.breakfastName = breakfastName;
        this.bmId = bmId;
        this.breakfastMealId = breakfastMealId;
        this.totalBF = totalBF;
        this.launchName = launchName;
        this.lmId = lmId;
        this.launchMealId = launchMealId;
        this.totalL = totalL;
        this.dinnerName = dinnerName;
        this.dmId = dmId;
        this.dinnerMealId = dinnerMealId;
        this.totalD = totalD;

        if (breakfastName == null) {
            this.breakfastName = "-";
            this.bmId = -1;
            this.breakfastMealId = -1;
        }
        if (launchName == null) {
            this.launchName = "-";
            this.lmId = -1;
            this.launchMealId = -1;
        }
        if (dinnerName == null) {
            this.dinnerName = "-";
            this.dinnerMealId = -1;
            this.dmId = -1;
        }
    }

    public int getBmId() {
        return bmId;
    }

    public int getLmId() {
        return lmId;
    }

    public int getDmId() {
        return dmId;
    }

    public String getBreakfastName() {
        return breakfastName;
    }

    public String getLaunchName() {
        return launchName;
    }

    public String getDinnerName() {
        return dinnerName;
    }

    public void setKitchenId(int kitchenId) {
        this.kitchenId = kitchenId;
    }

    public void setBreakfastName(String breakfastName) {
        this.breakfastName = breakfastName;
    }

    public void setLaunchName(String launchName) {
        this.launchName = launchName;
    }

    public void setDinnerName(String dinnerName) {
        this.dinnerName = dinnerName;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setBreakfastMealId(int breakfastMealId) {
        this.breakfastMealId = breakfastMealId;
    }

    public void setTotalBF(int totalBF) {
        this.totalBF = totalBF;
    }

    public void setLaunchMealId(int launchMealId) {
        this.launchMealId = launchMealId;
    }

    public void setTotalL(int totalL) {
        this.totalL = totalL;
    }

    public void setDinnerMealId(int dinnerMealId) {
        this.dinnerMealId = dinnerMealId;
    }

    public void setTotalD(int totalD) {
        this.totalD = totalD;
    }

    public Date getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public int getBreakfastMealId() {
        return breakfastMealId;
    }

    public int getTotalBF() {
        return totalBF;
    }

    public int getLaunchMealId() {
        return launchMealId;
    }

    public int getTotalL() {
        return totalL;
    }

    public int getDinnerMealId() {
        return dinnerMealId;
    }

    public int getTotalD() {
        return totalD;
    }

    public int getKitchenId() {
        return kitchenId;
    }

    @Override
    public String toString() {
        return "SetupMealCalendar{" +
                ", date=" + date +
                ", day='" + day + '\'' +
                ", kitchenId=" + kitchenId +
                ", breakfastName='" + breakfastName + '\'' +
                ", launchName='" + launchName + '\'' +
                ", dinnerName='" + dinnerName + '\'' +
                ", breakfastMealId=" + breakfastMealId +
                ", totalBF=" + totalBF +
                ", launchMealId=" + launchMealId +
                ", totalL=" + totalL +
                ", dinnerMealId=" + dinnerMealId +
                ", totalD=" + totalD +
                '}';
    }
}