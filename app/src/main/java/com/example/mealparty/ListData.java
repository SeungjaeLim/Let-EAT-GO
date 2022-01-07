package com.example.mealparty;

import java.io.Serializable;

public class ListData implements Serializable {
    private String date;
    private String[] kama_lunch;
    private String[] kama_dinner;
    private String[] west_breakfast;
    private String[] west_lunch;
    private String[] west_dinner;
    private String[] east_breakfast;
    private String[] east_lunch;
    private String[] east_dinner;

    public ListData(String date, String[] kama_lunch, String[] kama_dinner, String[] west_breakfast, String[] west_lunch, String[] west_dinner, String[] east_breakfast, String[] east_lunch, String[] east_dinner){
        this.date = date;
        this.kama_lunch = kama_lunch;
        this.kama_dinner = kama_dinner;
        this.west_breakfast = west_breakfast;
        this.west_lunch = west_lunch;
        this.west_dinner = west_dinner;
        this.east_breakfast = east_breakfast;
        this.east_lunch = east_lunch;
        this.east_dinner = east_dinner;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public String[] getKama_lunch(){
        return kama_lunch;
    }
    public void setKama_lunch(String[] kama_lunch){
        this.kama_lunch = kama_lunch;
    }

    public String[] getKama_dinner(){
        return kama_dinner;
    }
    public void setKama_dinner(String[] kama_dinner){
        this.kama_dinner = kama_dinner;
    }
    public String[] getWest_breakfast(){
        return west_breakfast;
    }
    public void setWest_breakfast(String[] west_breakfast){
        this.west_breakfast = west_breakfast;
    }

    public String[] getWest_lunch(){
        return west_lunch;
    }
    public void setWest_lunch(String[] west_lunch){
        this.west_lunch = west_lunch;
    }
    public String[] getWest_dinner(){
        return west_dinner;
    }
    public void setWest_dinner(String[] west_dinner){
        this.west_dinner = west_dinner;
    }

    public String[] getEast_breakfast(){
        return east_breakfast;
    }
    public void setEast_breakfast(String[] east_breakfast){
        this.east_breakfast = east_breakfast;
    }

    public String[] getEast_lunch(){
        return east_lunch;
    }
    public void setEast_lunch(String[] east_lunch){
        this.east_lunch = east_lunch;
    }
    public String[] getEast_dinner(){
        return east_dinner;
    }
    public void setEast_dinner(String[] east_dinner){
        this.east_dinner = east_dinner;
    }
}
