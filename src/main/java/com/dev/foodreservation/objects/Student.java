package com.dev.foodreservation.objects;

public class Student {

    public static final int MALE = 0;
    public static final int FEMALE = 1;
    public static String S_MALE = "Man";
    public static String S_FEMALE = "Woman";
    public static String SS_MALE = "Male";
    public static String SS_FEMALE = "Female";

    public final static String STUDENT_TABLE = "MealAutomation.dbo.Student";

    private long rollId,
            id;
    private String firstName,
            lastName;
    private byte gender,
            mealLimit;

    public Student(long rI, long id, String fn, String ln, byte g, byte ml) {
        this.rollId = rI;
        this.id = id;
        this.firstName = fn;
        this.lastName = ln;
        this.gender = g;
        this.mealLimit = ml;
    }

    public void setFirstName(String fn) {
        this.firstName = fn;
    }

    public void setLastName(String ln) {
        this.lastName = ln;
    }

    public void setGender(byte g) {
        this.gender = g;
    }

    public void setMealLimit(byte ml) {
        this.mealLimit = ml;
    }

    public long getRollId() {
        return rollId;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public byte getGender() {
        return gender;
    }

    public byte getMealLimit() {
        return mealLimit;
    }

    public boolean differs(Student student) {
        return !this.firstName.equals(student.firstName)
                || !this.lastName.equals(student.lastName)
                || this.gender != student.getGender()
                || this.mealLimit != student.getMealLimit();
    }

    public String getGenderString() {
        if (this.gender == 0) return SS_MALE;
        return SS_FEMALE;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollId=" + rollId +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", mealLimit=" + mealLimit +
                '}';
    }
}
