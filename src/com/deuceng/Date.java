package com.deuceng;
public class Date {
    private int hour; // We are using just hour. Thus, we put hour here.
    private int day;
    private int month;
    private int year;

    public Date(int hour, int day, int month, int year) {
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(int day, int month, int year) {
        this.hour = 11;
        this.day = day;
        this.month = month;
        this.year = year;
    }
    //getters
    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    //setters
    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setDay(int inputDay){
        day = inputDay;
    }
    public void setMonth(int inputMonth){
        month = inputMonth;
    }
    public void setYear(int inputYear){
        year = inputYear;
    }
    public void updateDate(){
        if(day>31){
            day=1;
            month+=1;
        }
        if(day>1){
            day+=1;
        }
    }
}
