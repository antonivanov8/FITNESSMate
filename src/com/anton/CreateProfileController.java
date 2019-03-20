package com.anton;

import com.anton.DataModel.BodyDailyInput;
import com.anton.DataModel.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreateProfileController {


    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private DatePicker dateOfBirthDatePicker;
    @FXML
    private Spinner weightSpinner;
    @FXML
    private ChoiceBox genderChoiceBox;
    @FXML
    private ChoiceBox levelChoiceBox;
    @FXML
    private Label createProfileInfoLabel;

    public void initialize() {
        dateOfBirthDatePicker.setPromptText("Date of Birth");
        dateOfBirthDatePicker.setValue(LocalDate.now());
        genderChoiceBox.getSelectionModel().select(0);
        levelChoiceBox.getSelectionModel().select(0);
    }

    public Profile createProfile () {
        String firstName = null;
        String lastName = null;
        String dateOfBirth = null;
        String weight = null;
        String gender = null;
        String level = null;
        if (firstNameTextField.getText().isBlank() || lastNameTextField.getText().isBlank() ||
            Integer.parseInt(weightSpinner.getValue().toString()) <= 0) {
            createProfileInfoLabel.setText("Invalid date!");
            createProfileInfoLabel.setVisible(true);
        } else {
            firstName = firstNameTextField.getText();
            lastName = lastNameTextField.getText();
            dateOfBirth = dateOfBirthDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            weight = weightSpinner.getValue().toString();
            gender = genderChoiceBox.getSelectionModel().getSelectedItem().toString();
            level = levelChoiceBox.getSelectionModel().getSelectedItem().toString();
        }

        Profile newProfile = new Profile(firstName,lastName,dateOfBirth,gender,weight,level);
        String profileFirstName = newProfile.getFirstName();
        try(ObjectOutputStream locFile = new ObjectOutputStream
                (new BufferedOutputStream(new FileOutputStream
                        ("C:\\Users\\LAPTOP\\Desktop\\JavaPrograms\\FitnessApp\\src\\com\\anton\\Data\\"
                                +profileFirstName +".dat")))){
            locFile.writeObject(newProfile);
        }catch (IOException e) {
            System.out.println("Oooops");
        }
        LoginController.getListWithProfiles().add(new File("C:\\Users\\LAPTOP\\Desktop\\JavaPrograms\\FitnessApp\\src\\com\\anton\\Data\\"
                + newProfile.getFirstName()));
        String profileName = newProfile.getFirstName().substring(0, newProfile.getFirstName().length() - 4);
        System.out.println(newProfile.toString());
        return newProfile;
    }
}
