package com.anton;

import com.anton.DataModel.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class LoginController {


    @FXML
    private GridPane loginPane;
    @FXML
    private Label user1LoginLabel;
    @FXML
    private ImageView user1LoginImageView;
    @FXML
    private Label user2LoginLabel;
    @FXML
    private ImageView user2LoginImageView;
    @FXML
    private Label user3LoginLabel;
    @FXML
    private ImageView user3LoginImageView;
    @FXML
    private Label user4LoginLabel;
    @FXML
    private ImageView user4LoginImageView;
    @FXML
    private Label user5LoginLabel;
    @FXML
    private ImageView user5LoginImageView;
    @FXML
    private Label loginInfoLabel;

    private String chosenProfile;
    private static List<File> listWithProfiles;
    private ObservableList<ImageView> listOfUserLoginImageViews;
    private ObservableList<Label> listOfUserLoginLabels;

    public void initialize () {
        setProfiles();
    }

    private void setProfiles() {
        listWithProfiles = new ArrayList<>();
        File dataFolder = new File("C:\\Users\\LAPTOP\\Desktop\\JavaPrograms\\FitnessApp\\src\\com\\anton\\Data");
        for (File file : dataFolder.listFiles()) {
            listWithProfiles.add(file);
        }
        listOfUserLoginLabels = FXCollections.observableArrayList();
        listOfUserLoginLabels.addAll(user1LoginLabel,user2LoginLabel,user3LoginLabel,user4LoginLabel,
                user5LoginLabel);

        listOfUserLoginImageViews = FXCollections.observableArrayList();
        listOfUserLoginImageViews.addAll(user1LoginImageView,user2LoginImageView,user3LoginImageView,
                user4LoginImageView,user5LoginImageView);



        if (listWithProfiles.size() > 0) {
            for(int i = 0 ; i < listWithProfiles.size(); i++){
                String profileName = listWithProfiles.get(i).getName().substring(0, listWithProfiles.get(i).getName().length() - 4);
                listOfUserLoginLabels.get(i).setText(profileName);
                listOfUserLoginLabels.get(i).setFont(Font.font("Arial", FontWeight.BOLD,14));
            }
        }
    }



    @FXML
    public void handleChoosingProfile (MouseEvent mouseEvent) {

        for  (int i = 0; i < listWithProfiles.size(); i++) {
            if (mouseEvent.getSource() == listOfUserLoginLabels.get(i) ||
                    mouseEvent.getSource() == listOfUserLoginImageViews.get(i)) {
                chosenProfile = listWithProfiles.get(i).getName();
                System.out.println("Chosen profile: " + chosenProfile);
            }
        }
        if (chosenProfile != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("main_window.fxml"));

            try {
                Parent root = loader.load();

                MainWindowController controller = loader.getController();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Main.class.getResource("css/fitnessmatestyle.css").toExternalForm());
                Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                controller.initialize(chosenProfile);
                stage.show();

                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleNewProfile () {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(loginPane.getScene().getWindow());
        dialog.setTitle("Create Profile");
        dialog.setHeaderText("Welcome to FitnessApp 1.0 .\n You just started your real" +
                " journey in the fitness world.\n Create your profile and track your" +
                " progress." );
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("create_profile.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.FINISH) {
            CreateProfileController controller = loader.getController();
            Profile newProfile = controller.createProfile();
            setProfiles();
            loginInfoLabel.setVisible(true);
            loginInfoLabel.setText("Your profile is ready!");
        }
    }

    public static List<File> getListWithProfiles() {
        return listWithProfiles;
    }
}

