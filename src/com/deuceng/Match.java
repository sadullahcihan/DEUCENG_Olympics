package com.deuceng;
import java.util.Random;
public class Match {
    private Athlete athlete1;
    private Athlete athlete2;
    private Athlete winner;
    private Date date;

    public Match(Athlete athlete1, Athlete athlete2) {
        this.athlete1 = athlete1;
        this.athlete2 = athlete2;
        this.date = null;
        this.winner = null;
    }

    public String display() {
        return "" + date.getHour() + ":00  " + date.getDay() + "." + date.getMonth() + "." + date.getYear();
    }
    public Date getDate(){
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Athlete getAthlete1() {
        return athlete1;
    }

    public Athlete getAthlete2() {
        return athlete2;
    }

    public Athlete getWinner() {
        return winner;
    }

    public void setAthlete1(Athlete athlete1) {
        this.athlete1 = athlete1;
    }

    public void setAthlete2(Athlete athlete2) {
        this.athlete2 = athlete2;
    }

    public void setWinner(Athlete winner) {
        this.winner = winner;
    }

    public void compete() {
        Random rand = new Random();
        double totalSkill = this.athlete1.getSkill() + this.athlete2.getSkill();
        double randNumber = rand.nextDouble() * totalSkill;
        Athlete weaker = this.athlete1;
        Athlete stronger = this.athlete2;
        if (stronger.getSkill() < weaker.getSkill()) {
            Athlete temp = weaker;
            weaker = stronger;
            stronger = temp;
        }
        if (randNumber < weaker.getSkill()) {
            winner = weaker;
        } else {
            winner = stronger;
        }
        winner.addPoint();
    }

}
