package main.src.controllers;
/**
 * Title:		 Math-X Application
 * Description:  SER 515 Project
 * Copyright:    Copyright (c) 2019
 * Company:      Department of Computer Software Engineering, Arizona State University.
 *
 * @author 		 Mehta Ria
 * @version 	 2.0
 * @since        8/30/2019
 * @modified     11/3/2019
 */
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.MainClass;

import java.io.InputStream;
import java.io.FileInputStream;
import javafx.scene.control.Label;
/**
 * The Class HomepageController.
 */
public class HomepageController {

    /** The assignment elements. */
//    @FXML private javafx.scene.control.ComboBox submittedAssignments;
//    @FXML private javafx.scene.control.ComboBox notSubmittedAssignments;
    @FXML private javafx.scene.chart.BarChart assignmentComparisonChart;
    @FXML private VBox notSubmittedVBox;
    @FXML private VBox submittedVBox;
    private RadioButton radioButtonAssign;
    ToggleGroup toggleGroup = new ToggleGroup();



    /**
     * Initialize.
     *
     */
    @FXML
    public void initialize(){
//        String labelHead=submittedAssignments.getText()+"\n\n\n";
        try{
            displaySubmittedAssignments();
//            submittedAssignments.getSelectionModel().selectFirst();
//            notSubmittedAssignments.getSelectionModel().selectFirst();
            compareAssignmentsOnBarChart();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Display assignments.
     */
    @FXML
    private void displaySubmittedAssignments() throws Exception {

        InputStream serviceAccount = new FileInputStream("/Users/riamehta/IdeaProjects/app-mathx/home/main/src/controllers/ser515-team4-firebase-adminsdk-vb9rb-90250893a1.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setDatabaseUrl("https://ser515-team4.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
        Firestore db= FirestoreClient.getFirestore();

        /**
         * Logic for displaying list of assignments - submitted and not submitted
         */

        String userEmail = "karandeep@gmail.com";
        Iterable<DocumentReference> docRefUpcoming  = db.collection("UserAssignmentStatus").document(userEmail).collection("NotSubmitted").listDocuments();
        ApiFuture<DocumentSnapshot> documentApi;
        DocumentSnapshot documentData;

        notSubmittedVBox.setSpacing(5);
        //Unsubmitted Assignments
        for(DocumentReference doc :docRefUpcoming){
            documentApi = doc.get();             //Gets reference of document
            documentData=documentApi.get();
            radioButtonAssign =new RadioButton();
//            radioButtonAssign.setBorder();
            radioButtonAssign.setText(documentData.getId());
            radioButtonAssign.setToggleGroup(toggleGroup);
            notSubmittedVBox.getChildren().add(radioButtonAssign);
        }

        Iterable<DocumentReference> docRefSolved  = db.collection("UserAssignmentStatus").document(userEmail).collection("Submitted").listDocuments();
        ApiFuture<DocumentSnapshot> documentApiSolved;
        DocumentSnapshot documentDataSolved;


        submittedVBox.setSpacing(5);

        //Submitted assignments
        for(DocumentReference doc :docRefSolved){
            documentApiSolved = doc.get();             //Gets reference of document
            documentDataSolved=documentApiSolved.get();
            radioButtonAssign =new RadioButton();
            radioButtonAssign.setText(documentDataSolved.getId());
            radioButtonAssign.setToggleGroup(toggleGroup);
            submittedVBox.getChildren().addAll(radioButtonAssign);
        }
    }

    /**
     * Comparing grades of assignments on bar charts
     */

    void compareAssignmentsOnBarChart() throws Exception{

        Firestore db= FirestoreClient.getFirestore();

        String userEmail = "karandeep@gmail.com";

        Iterable<DocumentReference> docRefSolved  = db.collection("UserAssignmentStatus").document(userEmail).collection("Submitted").listDocuments();
        ApiFuture<DocumentSnapshot> documentApiSolved;
        DocumentSnapshot documentDataSolved;
        assignmentComparisonChart.setTitle("Comparison of Assignments graded");
        assignmentComparisonChart.getXAxis().setLabel("Assignments");
        assignmentComparisonChart.getYAxis().setLabel("Grades out of 10");
        XYChart.Series<String, Number> assignments = new XYChart.Series<>();
        assignments.setName("Assignments");
        int grade=0;

        for(DocumentReference doc :docRefSolved){
            documentApiSolved = doc.get();             //Gets reference of document
            documentDataSolved=documentApiSolved.get();
            if(documentDataSolved.get("Grade")!=null){
                grade=Integer.parseInt(documentDataSolved.get("Grade").toString());
            }

            assignments.getData().add(new XYChart.Data<>(documentDataSolved.getId(), grade));

            System.out.println(documentDataSolved.getData());

        }


        assignmentComparisonChart.getData().addAll(assignments);
    }




    /**
     * Workspace action.
     *
     * @param actionEvent the action event
     */
    @FXML
    void workspaceAction(ActionEvent actionEvent)
    {

        new MainClass().openWorkSpaceWindow();
//        MainClass.homePageStage.close();
    }

    /**
     * Display.
     *
     * @param event the event
     */
    @FXML
    public void display(ActionEvent event) {
        AlertBox.display();
    }
}
