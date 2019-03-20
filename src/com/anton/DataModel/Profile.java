package com.anton.DataModel;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Profile implements Serializable {

    private final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private final String gender;
    private String weight;
    private String level;

    private final String createdOn;

    private List<BodyDailyInput> bodyList;
    private Map<String, List<Result>> resultsMap;
    private Map<String, List<Nutrition>> nutritionMap;

    public Profile(String firstName, String lastName, String dateOfBirth, String gender, String weight, String level) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.level = level;

        this.bodyList = new LinkedList<>();
        this.resultsMap = new HashMap<>();
        this.nutritionMap = new HashMap<>();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.createdOn = dateFormat.format(date);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getWeight() {
        return weight;
    }

    public String getLevel() {
        return level;
    }

    public List<BodyDailyInput> getBodyList() {
        return bodyList;
    }

    public Map<String, List<Result>> getResultsMap() {
        return resultsMap;
    }

    public Map<String, List<Nutrition>> getNutritionMap() {
        return nutritionMap;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", weight='" + weight + '\'' +
                ", level='" + level + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
