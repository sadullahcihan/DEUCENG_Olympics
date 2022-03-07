package com.deuceng;
public class Athlete {
    private String country;
    private String branch;
    private String fullName;
    private String gender;
    private Date birthday;
    private float skill;
    private int point;

    public Athlete(String country, String branch, String fullName, String gender, Date birthday, float skill) {
        this.country = country;
        this.branch = branch;
        this.fullName = fullName;
        this.gender = gender;
        this.birthday = birthday;
        this.skill = skill;
        this.point = 0;
    }

    public String getAttribute(int i) {
        String attr = country + "," + branch + "," + point + "," + gender;
        String[] strSplt = attr.split(",");
        return strSplt[i];
    }

    public int getPoint() {
        return point;
    }

    public String getCountry() {
        return country;
    }

    public String getBranch() {
        return branch;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public float getSkill() {
        return skill;
    }

    public void addPoint() {
        point += 1;
    }
}