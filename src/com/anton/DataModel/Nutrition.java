package com.anton.DataModel;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Nutrition implements Serializable {

    private final long serialVersionUID = 5L;

    private String meal;
    private String food;
    private String quantity;

    public Nutrition(String meal, String food, String quantity) {
        this.meal = meal;
        this.food = food;
        this.quantity = quantity;
    }

    public String getMeal() {
        String mealString = getSSPMeal().get();
        return mealString;
    }


    public SimpleStringProperty getSSPMeal() {
        SimpleStringProperty sSPMeal = new SimpleStringProperty(meal);
        return sSPMeal;
    }

    public String getFood () {
        String foodString = getSSPFood().get();
        return foodString;
    }

    public SimpleStringProperty getSSPFood() {
        SimpleStringProperty sSPFood = new SimpleStringProperty(food);
        return sSPFood;
    }

    public String getQuantity () {
        String quantityString = getSSPQuantity().get();
        return quantityString;
    }

    public SimpleStringProperty getSSPQuantity() {
        SimpleStringProperty sSPQuantity = new SimpleStringProperty(quantity);
        return sSPQuantity;
    }
}
