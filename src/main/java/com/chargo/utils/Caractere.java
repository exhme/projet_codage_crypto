package com.chargo.utils;

public class Caractere {
    private char lettre;
    private int value;

    public Caractere(char lettre, int value) {
        this.lettre = lettre;
        this.value = value;
    }

    public char getLettre() {
        return lettre;
    }

    public void setLettre(char lettre) {
        this.lettre = lettre;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
