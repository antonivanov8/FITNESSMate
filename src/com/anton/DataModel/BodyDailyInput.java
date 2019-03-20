package com.anton.DataModel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BodyDailyInput implements Serializable {

    private final long serialVersionUID = 2L;

    private String date;

    private String weight;
    private String upperArm;
    private String chest;
    private String neck;
    private String waist;
    private String hips;
    private String thigh;
    private String calf;

    public BodyDailyInput(String weight, String upperArm, String chest, String neck, String waist, String hips, String thigh, String calf) {
        Date today = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        this.date = dateFormat.format(today);
        this.weight = weight;
        this.upperArm = upperArm;
        this.chest = chest;
        this.neck = neck;
        this.waist = waist;
        this.hips = hips;
        this.thigh = thigh;
        this.calf = calf;
    }

    public BodyDailyInput(String date, String weight, String upperArm, String chest, String neck, String waist, String hips, String thigh, String calf) {
//        Date today = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        this.date = dateFormat.format(today);
        this.date = date;
        this.weight = weight;
        this.upperArm = upperArm;
        this.chest = chest;
        this.neck = neck;
        this.waist = waist;
        this.hips = hips;
        this.thigh = thigh;
        this.calf = calf;
        System.out.println(this.toString());
    }

    public String getDate() {
        return date;
    }

    public String getWeight() {
        return weight;
    }

    public String getUpperArm() {
        return upperArm;
    }

    public String getChest() {
        return chest;
    }

    public String getNeck() {
        return neck;
    }

    public String getWaist() {
        return waist;
    }

    public String getHips() {
        return hips;
    }

    public String getThigh() {
        return thigh;
    }

    public String getCalf() {
        return calf;
    }

    @Override
    public String toString() {
        return "BodyDailyInput{" +
                "date='" + date + '\'' +
                ", weight='" + weight + '\'' +
                ", upperArm='" + upperArm + '\'' +
                ", chest='" + chest + '\'' +
                ", neck='" + neck + '\'' +
                ", waist='" + waist + '\'' +
                ", hips='" + hips + '\'' +
                ", thigh='" + thigh + '\'' +
                ", calf='" + calf + '\'' +
                '}';
    }
}
