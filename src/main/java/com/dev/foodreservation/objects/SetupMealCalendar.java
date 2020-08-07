package com.dev.foodreservation.objects;

import com.dev.foodreservation.database.utilities.DateToPersianDate;

import java.util.Date;

public class SetupMealCalendar {
    Date date;
    String day;
    String breakfastName, launchName, dinnerName;
    int kitchenId;
    int breakfastMealId, totalBF,
            launchMealId, totalL,
            dinnerMealId, totalD;

    public String getBreakfastName() {
        return breakfastName;
    }

    public String getLaunchName() {
        return launchName;
    }

    public String getDinnerName() {
        return dinnerName;
    }

    public SetupMealCalendar(Date date,
                             int breakfastMealId,
                             int launchMealId,
                             int dinnerMealId,
                             String breakfastName,
                             int totalBF,
                             String launchName,
                             int totalL,
                             String dinnerName,
                             int totalD) {
        this.date = date;
        this.day = new DateToPersianDate().get(date)
                .getDayOfWeek().toString();
        if (breakfastName == null)
            breakfastName = "-";
        if (launchName == null)
            launchName = "-";
        if (dinnerName == null)
            dinnerName = "-";

        this.breakfastName = breakfastName;
        this.breakfastMealId = breakfastMealId;
        this.totalBF = totalBF;
        this.launchName = launchName;
        this.launchMealId = launchMealId;
        this.totalL = totalL;
        this.dinnerName = dinnerName;
        this.dinnerMealId = dinnerMealId;
        this.totalD = totalD;
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