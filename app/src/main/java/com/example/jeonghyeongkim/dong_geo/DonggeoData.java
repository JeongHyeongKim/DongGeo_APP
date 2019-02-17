package com.example.jeonghyeongkim.dong_geo;

public class DonggeoData {
    private String id;
    private String currency;
    private int amount;
    private int converted;
    private String univertisty;
    //private int id;

    public DonggeoData(String currency, int amount, int converted, String univertisty, String id) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.converted = converted;
        this.univertisty = univertisty;

    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getConverted() {
        return converted;
    }

    public void setConverted(int converted) {
        this.converted = converted;
    }

    public String getUnivertisty() {
        return univertisty;
    }

    public void setUnivertisty(String univertisty) {
        this.univertisty = univertisty;
    }

    public void setId(String id) { this.id = id; }

    public String getId() {return id;}
}
