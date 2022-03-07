package com.deuceng;

public class Main {

    private static Tournament olympics;

    public static void main(String[] args) throws Exception {
        olympics = new Tournament("input.txt");
        olympics.showTournaments();
    }
}