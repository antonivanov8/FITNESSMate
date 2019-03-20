package com.anton.DataModel;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Result implements Serializable {

    private final long serialVersionUID = 4L;

    private String exercise;
    private String series ;
    private String repeats;
    private String weights ;
    private String time;

    public Result(String exercise, String series, String repeats, String weights, String time) {
        this.exercise = exercise;
        this.series = series;
        this.repeats = repeats;
        this.weights = weights;
        this.time = time;
    }

    public String getExercise() {
        String exerciseString = getSSPExercise().get();
        return exerciseString;
    }


    public SimpleStringProperty getSSPExercise() {
        SimpleStringProperty sSPExercise = new SimpleStringProperty(exercise);
        return sSPExercise;
    }

    public String getSeries () {
        String seriesString = getSSPSeries().get();
        return seriesString;
    }

    public SimpleStringProperty getSSPSeries() {
        SimpleStringProperty sSPSeries = new SimpleStringProperty(series);
        return sSPSeries;
    }

    public String getRepeats () {
        String repeatsString = getSSPRepeats().get();
        return repeatsString;
    }

    public SimpleStringProperty getSSPRepeats() {
        SimpleStringProperty sSPRepeats = new SimpleStringProperty(repeats);
        return sSPRepeats;
    }

    public String getWeights () {
        String weightsString = getSSPWeights().get();
        return  weightsString;
    }

    public SimpleStringProperty getSSPWeights() {
        SimpleStringProperty sSPWeights = new SimpleStringProperty(weights);
        return sSPWeights;
    }

    public String getTime () {
        String timeString = getSSPTime().get();
        return timeString;
    }

    public SimpleStringProperty getSSPTime() {
        SimpleStringProperty sSPTime = new SimpleStringProperty(time);
        return sSPTime;
    }

    @Override
    public String toString() {
        return "Result{" +
                "exercise='" + exercise + '\'' +
                ", series=" + series +
                ", repeats=" + repeats +
                ", weights=" + weights +
                ", time=" + time +
                '}';
    }
}
