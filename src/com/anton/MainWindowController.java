package com.anton;

import com.anton.DataModel.BodyDailyInput;
import com.anton.DataModel.Nutrition;
import com.anton.DataModel.Profile;
import com.anton.DataModel.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MainWindowController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button bodyButton,resultsButton,nutritionsButton,profileButton;
    private Button activeButton;
    private String profileName;
    private Profile currentProfile;

    @FXML
    private GridPane bodyGridPane;
    @FXML
    private Label firstWeekWeightLabel, lastWeekWeightLabel, thisWeekWeightLabel;
    @FXML
    private TextField thisWeekWeightTextField;
    @FXML
    private Label firstWeekUpperArmLabel,lastWeekUpperArmLabel, thisWeekUpperArmLabel;
    @FXML
    private TextField thisWeekUpperArmTextField;
    @FXML
    private Label firstWeekChestLabel, lastWeekChestLabel, thisWeekChestLabel;
    @FXML
    private TextField thisWeekChestTextField;
    @FXML
    private Label firstWeekNeckLabel, lastWeekNeckLabel, thisWeekNeckLabel;
    @FXML
    private TextField thisWeekNeckTextField;
    @FXML
    private Label firstWeekWaistLabel, lastWeekWaistLabel, thisWeekWaistLabel;
    @FXML
    private TextField thisWeekWaistTextField;
    @FXML
    private Label firstWeekHipsLabel, lastWeekHipsLabel, thisWeekHipsLabel;
    @FXML
    private TextField thisWeekHipsTextField;
    @FXML
    private Label firstWeekThighLabel, lastWeekThighLabel, thisWeekThighLabel;
    @FXML
    private TextField thisWeekThighTextField;
    @FXML
    private Label firstWeekCalfLabel, lastWeekCalfLabel, thisWeekCalfLabel;
    @FXML
    private TextField thisWeekCalfTextField;
    private ObservableList<Label> thisWeekLabels;
    private ObservableList<TextField> thisWeekTextFields;
    @FXML
    private ChoiceBox<String> bodySeriesPicker;
    @FXML
    private LineChart bodyProgressLineChart;
    private XYChart.Series<String, Number> weightSeries;
    private XYChart.Series<String, Number> upperArmSeries;
    private XYChart.Series<String, Number> chestSeries;
    private XYChart.Series<String, Number> neckSeries;
    private XYChart.Series<String, Number> waistSeries;
    private XYChart.Series<String, Number> hipsSeries;
    private XYChart.Series<String, Number> thighSeries;
    private XYChart.Series<String, Number> calfSeries;
    private ObservableList<XYChart.Series<String,Number>> bodyLineChartSeriesList;
    @FXML
    private Label bodyInfoLabel;
    @FXML
    private Button submitBodyButton;


    @FXML
    private GridPane resultsGridPane;
    @FXML
    private DatePicker resultsDatePicker;
    @FXML
    private TextField exerciseTextField, seriesTextField, repeatsTextField, weightsTextField, timeTextField;
    @FXML
    private TableView<Result> resultsTableView;
    @FXML
    private Label resultsInfoLabel;
    private ObservableList<Result> resultsData;
    private boolean areThereResults = false;


    @FXML
    private GridPane nutritionsGridPane;
    @FXML
    private TableView<Nutrition> nutritionsTableView;
    @FXML
    private DatePicker nutritionsDatePicker;
    @FXML
    private ChoiceBox mealChoiceBox;
    @FXML
    private TextField foodTextField, quantityTextField;
    @FXML
    private Label nutritionsInfoLabel;
    private ObservableList<Nutrition> nutritionsData;
    private boolean areThereNutritions = false;

    @FXML
    private GridPane profileGridPane;
    @FXML
    private Label profileFirstNameLabel, profileSurnameLabel, profileDateOfBirthLabel, profileWeightLabel,
            profileCreatedOnLabel, profileGenderLabel, profileLevelLabel, profileNumberOfBodyRecordsLabel,
            profileNumberOfResultsRecordsLabel, profileNumberOfNutritionsRecordsLabel,profileInfoLabel;
    @FXML
    private TextField profileFirstNameTextField, profileSurnameTextField;
    @FXML
    private DatePicker profileDateOfBirthDatePicker;
    @FXML
    private ChoiceBox profileLevelChoiceBox;
    @FXML
    private Button profileEditButton;
    private ObservableList<Label> profileLabels;
    private ObservableList<Control> profileOnEditControls;



    void initialize(String profile) {
        profileName = profile;
       //Set the center property content of the mainPane to bodyPane
       mainPane.centerProperty().set(bodyGridPane);
       activeButton = bodyButton;
       activeButton.setId("active-button");
       bodyInfoLabel.setText("Welcome, " + profile.substring(0,profile.length()-4) + " !");

        //Deserialization of the chosen profile object
       try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(
               "C:\\Users\\LAPTOP\\Desktop\\JavaPrograms\\FitnessApp\\src\\com\\anton\\Data\\"
                       + profile))) {
           currentProfile = (Profile)objectInputStream.readObject();
       } catch (IOException | ClassNotFoundException e) {
           e.printStackTrace();
       }

       resultsData = FXCollections.observableArrayList();
       resultsDatePicker.setValue(LocalDate.now());
       resultsDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> setResults());

       nutritionsData = FXCollections.observableArrayList();
       nutritionsDatePicker.setValue(LocalDate.now());
       nutritionsDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> setNutrition());

       setData();

       //Saving the profile changes on exit(Serialization)
       Stage stage = (Stage) mainPane.getScene().getWindow();
       stage.setOnCloseRequest(event -> {
           System.out.println("Saving profile...");
           try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                   "C:\\Users\\LAPTOP\\Desktop\\JavaPrograms\\FitnessApp\\src\\com\\anton\\Data\\"
                   + profile))) {
               objectOutputStream.writeObject(currentProfile);
                System.exit(0);
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               System.out.println(currentProfile.getFirstName() + "has been saved");
           }
       });
    }

    @FXML
    private void setData() {
        setBody();
        setResults();
        setNutrition();
        setProfile();
    }


    @FXML
    public void handleSwitchingBetweenScenes(ActionEvent event) {
        GridPane switchTo;
        activeButton.setId(null);
        if (event.getSource() == bodyButton) {
           switchTo = bodyGridPane;
            activeButton = bodyButton;
        } else if (event.getSource() == resultsButton) {
            switchTo = resultsGridPane;
            activeButton = resultsButton;
        } else if (event.getSource() == nutritionsButton) {
            switchTo = nutritionsGridPane;
            activeButton = nutritionsButton;
        } else {
            switchTo = profileGridPane;
            activeButton = profileButton;
        }
        activeButton.setId("active-button");
        mainPane.centerProperty().set(switchTo);
    }

    private void setBody(){
        setBodyLabelsAndTextFieldsIntoLists();

        bodySeriesPicker.getSelectionModel().selectFirst();
        bodySeriesPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            bodyProgressLineChart.getData().clear();
            switch (newValue) {
                case "Weight":
                    bodyProgressLineChart.getData().add(weightSeries);
                    break;
                case "Upper Arm":
                    bodyProgressLineChart.getData().add(upperArmSeries);
                    break;
                case "Chest":
                    bodyProgressLineChart.getData().add(chestSeries);
                    break;
                case "Neck":
                    bodyProgressLineChart.getData().add(neckSeries);
                    break;
                case "Waist":
                    bodyProgressLineChart.getData().add(waistSeries);
                    break;
                case "Hips":
                    bodyProgressLineChart.getData().add(hipsSeries);
                    break;
                case "Thigh":
                    bodyProgressLineChart.getData().add(thighSeries);
                    break;
                case "Calf":
                    bodyProgressLineChart.getData().add(calfSeries);
                    break;
                default:
                    bodyProgressLineChart.getData().addAll(bodyLineChartSeriesList);
                    break;
            }
        });

        weightSeries = new XYChart.Series<>();
        upperArmSeries = new XYChart.Series<>();
        chestSeries = new XYChart.Series<>();
        neckSeries = new XYChart.Series<>();
        waistSeries = new XYChart.Series<>();
        hipsSeries = new XYChart.Series<>();
        thighSeries = new XYChart.Series<>();
        calfSeries = new XYChart.Series<>();

        weightSeries.setName("Weight");
        upperArmSeries.setName("Upper arm");
        chestSeries.setName("Chest");
        neckSeries.setName("Neck");
        waistSeries.setName("Waist");
        hipsSeries.setName("Hips");
        thighSeries.setName("Thigh");
        calfSeries.setName("Calf");

        if (currentProfile.getBodyList().size() > 0) {

            firstWeekWeightLabel.setText(currentProfile.getBodyList().get(0).getWeight());
            firstWeekUpperArmLabel.setText(currentProfile.getBodyList().get(0).getUpperArm());
            firstWeekChestLabel.setText(currentProfile.getBodyList().get(0).getChest());
            firstWeekNeckLabel.setText(currentProfile.getBodyList().get(0).getNeck());
            firstWeekWaistLabel.setText(currentProfile.getBodyList().get(0).getWaist());
            firstWeekHipsLabel.setText(currentProfile.getBodyList().get(0).getHips());
            firstWeekThighLabel.setText(currentProfile.getBodyList().get(0).getThigh());
            firstWeekCalfLabel.setText(currentProfile.getBodyList().get(0).getCalf());

            lastWeekWeightLabel.setText(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getWeight());
            lastWeekUpperArmLabel.setText(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getUpperArm());
            lastWeekChestLabel.setText(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getChest());
            lastWeekNeckLabel.setText(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getNeck());
            lastWeekWaistLabel.setText(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getWaist());
            lastWeekHipsLabel.setText(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getHips());
            lastWeekThighLabel.setText(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getThigh());
            lastWeekCalfLabel.setText(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getCalf());

            setBodyLineChart();
        }
    }

    @FXML
    private void setBodyLabelsAndTextFieldsIntoLists () {

        ObservableList<Label> firstWeekLabels = FXCollections.observableArrayList();
        firstWeekLabels.addAll(firstWeekWeightLabel,firstWeekUpperArmLabel,firstWeekChestLabel,firstWeekNeckLabel,
                firstWeekWaistLabel,firstWeekHipsLabel,firstWeekThighLabel,firstWeekCalfLabel);


        ObservableList<Label> lastWeekLabels = FXCollections.observableArrayList();
        lastWeekLabels.addAll(lastWeekWeightLabel,lastWeekUpperArmLabel,lastWeekChestLabel,lastWeekNeckLabel,
                lastWeekWaistLabel,lastWeekHipsLabel,lastWeekThighLabel,lastWeekCalfLabel);

        thisWeekLabels = FXCollections.observableArrayList();
        thisWeekLabels.addAll(thisWeekWeightLabel,thisWeekUpperArmLabel,thisWeekChestLabel,thisWeekNeckLabel,
                thisWeekWaistLabel,thisWeekHipsLabel,thisWeekThighLabel,thisWeekCalfLabel);

        thisWeekTextFields = FXCollections.observableArrayList();
        thisWeekTextFields.addAll(thisWeekWeightTextField,thisWeekUpperArmTextField,thisWeekChestTextField,thisWeekNeckTextField,
                thisWeekWaistTextField,thisWeekHipsTextField,thisWeekThighTextField,thisWeekCalfTextField);
    }

    @FXML
    private void setBodyLineChart() {

        bodyLineChartSeriesList = FXCollections.observableArrayList();
        bodyLineChartSeriesList.addAll(weightSeries,upperArmSeries,chestSeries,neckSeries,
                waistSeries,hipsSeries,thighSeries,calfSeries);

        for ( BodyDailyInput bodyDailyInput : currentProfile.getBodyList()) {

            weightSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getWeight())));
            upperArmSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getUpperArm())));
            chestSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getChest())));
            neckSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getNeck())));
            waistSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getWaist())));
            hipsSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getHips())));
            thighSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getThigh())));
            calfSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getCalf())));
        }



        bodyProgressLineChart.getData().addAll(weightSeries,upperArmSeries,chestSeries,neckSeries,
                waistSeries,hipsSeries,thighSeries,calfSeries);
    }

    @FXML
    public void handleSubmitBody(){

        if (submitBodyButton.getText().equals("Submit")) {
            boolean isDataOkay = true;
            for (TextField textField : thisWeekTextFields) {
                if (textField.getText().isBlank()) {
                    isDataOkay = false;
                }
            }
            if(!isDataOkay){
                bodyInfoLabel.setText("Invalid data!");
                return;
            } else {
                BodyDailyInput bodyDailyInput = new BodyDailyInput(thisWeekWeightTextField.getText(),
                        thisWeekUpperArmTextField.getText(), thisWeekChestTextField.getText(),
                        thisWeekNeckTextField.getText(),thisWeekWaistTextField.getText(),
                        thisWeekHipsTextField.getText(),thisWeekThighTextField.getText(),
                        thisWeekCalfTextField.getText());

                if (currentProfile.getBodyList().size() > 0) {
                    if (bodyDailyInput.getDate().equals(currentProfile.getBodyList().get(currentProfile.getBodyList().size()-1).getDate())) {
                        currentProfile.getBodyList().remove(currentProfile.getBodyList().size()-1);
                        for (XYChart.Series<String, Number> series : bodyLineChartSeriesList) {
                            series.getData().remove(series.getData().size()-1);
                        }
                    }
                }

                currentProfile.getBodyList().add(bodyDailyInput);

                weightSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getWeight())));
                upperArmSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getUpperArm())));
                chestSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getChest())));
                neckSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getNeck())));
                waistSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getWaist())));
                hipsSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getHips())));
                thighSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getThigh())));
                calfSeries.getData().add(new XYChart.Data<>(bodyDailyInput.getDate(), Double.parseDouble(bodyDailyInput.getCalf())));


                bodyInfoLabel.setText("Submit successful.");

                for (TextField textField : thisWeekTextFields) {
                    textField.setVisible(false);
                }

                for (int i = 0; i < thisWeekLabels.size(); i++) {
                    thisWeekLabels.get(i).setText(thisWeekTextFields.get(i).getText());
                }

                for (Label label : thisWeekLabels) {
                    label.setVisible(true);
                }
            }
            submitBodyButton.setText("Change");
        } else {
            for (Label label : thisWeekLabels) {
                label.setVisible(false);
            }

            for (TextField thisWeekTextField : thisWeekTextFields) {
                thisWeekTextField.setText(thisWeekTextField.getText());
            }

            for (TextField textField : thisWeekTextFields) {
                textField.setVisible(true);
            }

            submitBodyButton.setText("Submit");
        }

    }

    @FXML
    private void setResults() {
        String datePickerChange = resultsDatePicker.getValue().toString();
        if (currentProfile.getResultsMap().containsKey(datePickerChange)) {
            areThereResults = true;
            resultsData.clear();
            resultsData.addAll(currentProfile.getResultsMap().get(datePickerChange));
            resultsTableView.getItems().clear();
            resultsTableView.getItems().addAll(resultsData);
            resultsInfoLabel.setText(resultsData.size() + " result" + pluralOrNo(resultsData) + " found");
        } else {
            areThereResults = false;
            resultsData.clear();
            resultsTableView.getItems().clear();
            resultsInfoLabel.setText("No results found");
        }
    }

    @FXML
    public void handleAddResult () {
        //Creating new result from the user input
        if (!exerciseTextField.getText().isBlank() && !seriesTextField.getText().isBlank() &&
                !repeatsTextField.getText().isBlank() && !weightsTextField.getText().isBlank() &&
                !timeTextField.getText().isBlank()) {
            Result result;
            // If there is no list of results that they create one
            if (!areThereResults) {
                currentProfile.getResultsMap().put(resultsDatePicker.getValue().toString(), new ArrayList<>());
                areThereResults = true;
            }
            result = new Result(exerciseTextField.getText(), seriesTextField.getText(),
                    repeatsTextField.getText(), weightsTextField.getText(),
                    timeTextField.getText());
            //Add the new result to...
            resultsData.add(result);
            resultsTableView.getItems().addAll(result);
            currentProfile.getResultsMap().get(resultsDatePicker.getValue().toString()).add(result);
            //Clear all the TextFields
            exerciseTextField.clear();
            seriesTextField.clear();
            repeatsTextField.clear();
            weightsTextField.clear();
            timeTextField.clear();
            //Set the Info label
            resultsInfoLabel.setText(resultsData.size() + " nutrition" + pluralOrNo(resultsData) + " found");
        }else {
            resultsInfoLabel.setText("Invalid data!");
        }

    }



    @FXML
    private void setNutrition () {
        String datePickerChange = nutritionsDatePicker.getValue().toString();
        mealChoiceBox.getSelectionModel().selectFirst();
        if (currentProfile.getNutritionMap().containsKey(datePickerChange)) {
            areThereNutritions = true;
            nutritionsData.clear();
            nutritionsTableView.getItems().clear();
            nutritionsData.addAll(currentProfile.getNutritionMap().get(datePickerChange));
            nutritionsTableView.getItems().addAll(nutritionsData);
            nutritionsInfoLabel.setText(nutritionsData.size() + " nutrition" + pluralOrNo(nutritionsData) + " found");
        } else {
            areThereNutritions = false;
            nutritionsData.clear();
            nutritionsTableView.getItems().clear();
            nutritionsInfoLabel.setText("No nutritions found");
        }
    }

    @FXML
    public void handleAddNutrition () {
        if (!foodTextField.getText().isBlank() &&
                !quantityTextField.getText().isBlank()) {
            Nutrition nutrition;
            if (!areThereNutritions) {
                currentProfile.getNutritionMap().put(nutritionsDatePicker.getValue().toString(), new ArrayList<>());
                areThereNutritions = true;
            }
            nutrition = new Nutrition(mealChoiceBox.getSelectionModel().getSelectedItem().toString(),
                    foodTextField.getText(), quantityTextField.getText());
            nutritionsData.add(nutrition);
            nutritionsTableView.getItems().addAll(nutrition);
            currentProfile.getNutritionMap().get(nutritionsDatePicker.getValue().toString()).add(nutrition);
            foodTextField.clear();
            quantityTextField.clear();
            nutritionsInfoLabel.setText(nutritionsData.size() + " result" + pluralOrNo(nutritionsData) + " found");
        } else {
            nutritionsInfoLabel.setText("Invalid data!");
        }
    }

    private String pluralOrNo(ObservableList observableList){
        return (observableList.size() > 1) ? "s" : "";
    }

    private void setProfile () {
        profileFirstNameLabel.setText(currentProfile.getFirstName());
        profileSurnameLabel.setText(currentProfile.getLastName());
        profileDateOfBirthLabel.setText(currentProfile.getDateOfBirth());
        profileGenderLabel.setText(currentProfile.getGender());
        profileLevelLabel.setText(currentProfile.getLevel());
        profileWeightLabel.setText(currentProfile.getWeight());
        profileCreatedOnLabel.setText(currentProfile.getCreatedOn());

        profileNumberOfBodyRecordsLabel.setText(String.valueOf(currentProfile.getBodyList().size()));
        profileNumberOfResultsRecordsLabel.setText(String.valueOf(currentProfile.getResultsMap().size()));
        profileNumberOfNutritionsRecordsLabel.setText(String.valueOf(currentProfile.getNutritionMap().size()));

        profileFirstNameTextField.setText(currentProfile.getFirstName());
        profileSurnameTextField.setText(currentProfile.getLastName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(currentProfile.getDateOfBirth(),formatter);
        profileDateOfBirthDatePicker.setValue(localDate);
        int level = -1;
        switch (currentProfile.getLevel()){
            case "beginner":
                level = 0;
                break;
            case "intermediate":
                level = 1;
                break;
            case "advanced":
                level = 2;
                break;
            case "expert":
                level = 3;
                break;
        }
        profileLevelChoiceBox.getSelectionModel().select(level);

        profileLabels = FXCollections.observableArrayList();
        profileLabels.addAll(profileFirstNameLabel,profileSurnameLabel,profileDateOfBirthLabel,
                profileLevelLabel,profileWeightLabel,profileGenderLabel,profileCreatedOnLabel);

        profileOnEditControls = FXCollections.observableArrayList();
        profileOnEditControls.addAll(profileFirstNameTextField,profileSurnameTextField,profileDateOfBirthDatePicker,
                profileLevelChoiceBox);
    }

    @FXML
    public void handleProfileEdit () {
        if (profileEditButton.getText().equals("Edit")) {
            for (Label label : profileLabels) {
                label.setVisible(false);
            }
            for(Control control: profileOnEditControls){
                control.setVisible(true);
            }
            profileEditButton.setText("Save");
        }else {
            if (!profileFirstNameTextField.getText().isBlank() && !profileSurnameTextField.getText().isBlank()) {
                currentProfile.setFirstName(profileFirstNameTextField.getText());
                currentProfile.setLastName(profileSurnameTextField.getText());
                currentProfile.setDateOfBirth(profileDateOfBirthDatePicker.getValue().toString());
                currentProfile.setLevel(profileLevelChoiceBox.getSelectionModel().getSelectedItem().toString());
                setProfile();
                for (Label label : profileLabels) {
                    label.setVisible(true);
                }
                for(Control control: profileOnEditControls){
                    control.setVisible(false);
                }
                profileInfoLabel.setVisible(true);
                profileInfoLabel.setText("Changes saved.");
                profileEditButton.setText("Edit");
            } else {
                profileInfoLabel.setVisible(true);
                profileInfoLabel.setText("Invalid data.");
            }
        }
    }

    @FXML
    public void handleDeleteRecords(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting records");
        alert.setHeaderText("Are you sure that you want to delete the profile's records");
        alert.setContentText("Records that have been deleted couldn't be recovered!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            currentProfile.getBodyList().clear();
            currentProfile.getResultsMap().clear();
            currentProfile.getNutritionMap().clear();
            setProfile();
        }
    }

    @FXML
    public void handleLogout (ActionEvent event) {
        System.out.println("Saving profile...");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                "C:\\Users\\LAPTOP\\Desktop\\JavaPrograms\\FitnessApp\\src\\com\\anton\\Data\\"
                        + profileName))) {
            objectOutputStream.writeObject(currentProfile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(currentProfile.getFirstName() + "has been saved");
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root,900,584);
            scene.getStylesheets().add(Main.class.getResource("css/fitnessmatestyle.css").toExternalForm());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


