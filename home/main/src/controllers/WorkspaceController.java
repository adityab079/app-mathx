package main.src.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.MainClass;
import main.src.controllers.Grades.GradeFive;
import main.src.controllers.Grades.GradeParent;
import main.src.controllers.Grades.GradeTwo;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Karandeep Singh Grewal
 */


public class WorkspaceController implements Initializable {

    @FXML
    private Pane sandBox;
    @FXML
    public StackPane commonPane;
    @FXML
    private Label homeButton;
    @FXML
    private Label submitButton;
    @FXML
    private VBox sidePane;

    @FXML
    private Label resetButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int studentGrade = 2;
        if (LoginController.studentModel.getGrade() != null) {
            studentGrade = Integer.parseInt(LoginController.studentModel.getGrade());
        }

        GradeParent grade = null;
        switch (studentGrade) {
            case 2:
                grade = new GradeTwo();
                break;
            case 5:
                grade = new GradeFive();
                break;
            default:
                System.out.println("Unknown Grade");
                break;

        }

        grade.produceWorkspace(sandBox, sidePane, commonPane);

        homeButton.setOnMouseClicked(mouseEvent -> {
            new MainClass().openHomePageWindow();
            MainClass.workspaceStage.close();
        });

        submitButton.setOnMouseClicked(mouseEvent -> {
            if (commonPane.getChildren().get(0) instanceof StackPane) {
                StackPane questionPane = null;
                if (commonPane.getChildren().get(0) instanceof StackPane) {
                    questionPane = (StackPane) commonPane.getChildren().get(0);
                }
                System.out.println(questionPane.getChildren());

            } else {
                System.out.println("Switch to Result Pane");
            }
        });

    }

}