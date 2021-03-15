package edu.wpi.cs3733.teamO.Controllers.Revamped;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.teamO.Database.RequestHandling;
import edu.wpi.cs3733.teamO.Database.UserHandling;
import edu.wpi.cs3733.teamO.HelperClasses.PopupMaker;
import edu.wpi.cs3733.teamO.HelperClasses.SwitchScene;
import edu.wpi.cs3733.teamO.SRequest.DisplayRequest;
import edu.wpi.cs3733.teamO.SRequest.Request;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static edu.wpi.cs3733.teamO.Controllers.ServiceRequest.RequestPageController.getReqType;

public class EntryRequestController implements Initializable {
    public ScrollPane scrollPane;
    @FXML
    private JFXButton assignButton;
    @FXML private JFXButton updateButton;
    @FXML private JFXButton addButton;
    @FXML private VBox reqBox;
    @FXML private StackPane popUpPane;
    private static ObservableList<Request> reqList;
    private static String typeOfRequest;

    public static ArrayList<JFXTextField> createFields(ArrayList<String> labels) {
        ArrayList<JFXTextField> listOfFields = new ArrayList<>();
        for (String label : labels) {
            JFXTextField text = new JFXTextField();
            text.setPromptText(label);
            listOfFields.add(text);
        }
        return listOfFields;
    }

    /** Display a single request from the request list */
    public void displayOneRequest(Request r) {

        int reqID = r.getRequestID();
        String requestedBy = r.getRequestedBy();
        String fulfilledBy = r.getFulfilledBy();
        Date dateNeeded = r.getDateNeeded();
        Date dateRequested = r.getDateRequested();
        String location = r.getLocationNodeID();
        String par1 = r.getPara1();
        String par2 = r.getPara2();
        String par3 = r.getPara3();

        HBox addBox = new HBox();
        addBox.setSpacing(30);
        addBox.setPrefWidth(1400);
        addBox.setBackground(
                new Background(
                        new BackgroundFill(Color.color(.95, .95, .95), new CornerRadii(5), Insets.EMPTY)));
        Label id = new Label(String.valueOf(reqID));
        id.setStyle("-fx-max-width: 50; -fx-min-width: 50; " + id.getStyle());

        Label reqBy = new Label(requestedBy);
        reqBy.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + reqBy.getStyle());

        Label filledBy = new Label(fulfilledBy);
        filledBy.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + filledBy.getStyle());

        Label dReq = new Label(dateRequested.toString());
        dReq.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + dReq.getStyle());

        Label dNeed = new Label(dateNeeded.toString());
        dNeed.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + dNeed.getStyle());

        Label loc = new Label(location);
        loc.setStyle("-fx-max-width: 150; -fx-min-width: 150; " + loc.getStyle());

        Label p1 = new Label(par1);
        p1.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + p1.getStyle());

        Label p2 = new Label(par2);
        p2.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + p2.getStyle());

        Label p3 = new Label(par3);
        p3.setStyle("-fx-max-width: 100; -fx-min-width: 100; " + p3.getStyle());

        JFXButton markDone = new JFXButton();

        reqBox.setSpacing(15);

        String status = "";

        try {
            status = RequestHandling.getStatus(reqID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        markDone.setOnAction(
                e -> {
                    // mark the thing as done
                    try {
                        RequestHandling.setStatus(reqID, "Complete");
                        SwitchScene.goToParent("/Views/ServiceRequests/RequestList.fxml");
                    } catch (SQLException throwables) {
                        // TODO @sam add input scrubbing / verification?
                        throwables.printStackTrace();
                    }
                });

        markDone.setText("Mark Complete");
        markDone.setStyle(
                "-fx-background-color: #CFE2F3; -fx-text-fill: #3a5369; -fx-border-radius: 5px; -fx-font-family: 'Leelawadee UI'; -fx-font-size: 10pt; -fx-font-weight: BOLD;");

        addBox.getChildren().add(id);
        addBox.getChildren().add(reqBy);
        addBox.getChildren().add(filledBy);
        addBox.getChildren().add(dReq);
        addBox.getChildren().add(dNeed);
        addBox.getChildren().add(loc);

        if (par1 != null && !par1.equals("null")) {
            addBox.getChildren().add(p1);
        }
        if (par2 != null && !par2.equals("null")) {
            addBox.getChildren().add(p2);
        }
        if (par3 != null && !par3.equals("null")) {
            addBox.getChildren().add(p3);
        }

        switch (status) {
            case "Not Assigned":
                addBox.setStyle("-fx-border-color:  #ffaca4; -fx-border-width: 5px;");

                break;
            case "Assigned":
                addBox.setStyle("-fx-border-color:  #fec107; -fx-border-width: 5px;");

                break;
            case "Complete":
                addBox.setStyle("-fx-border-color:  #72db8e; -fx-border-width: 5px;");
                break;
        }
        // add button
        addBox.getChildren().add(markDone);

        for (Node n : addBox.getChildren()) {
            if (!n.getClass().equals(JFXButton.class)) {
                String temp = n.getStyle();
                n.setStyle("-fx-text-fill:  #3A5369; -fx-font-size: 14pt;" + temp);
            }
        }
        addBox
                .onMouseClickedProperty()
                .set(
                        e -> {
                            PopupMaker.serviceReqPopup(
                                    popUpPane, ((Label) addBox.getChildren().get(0)).getText());
                        });
        reqBox.getChildren().add(addBox);
    }

    /**
     * Displays the list on the page by going through obs. list of requests
     *
     * @param requests
     */
    public void displayList(ObservableList<Request> requests) {

        for (Request r : requests) {
            displayOneRequest(r);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfRequest = getReqType();
        System.out.println("RequestType: " + typeOfRequest);
        if (typeOfRequest != null) {
            reqList = DisplayRequest.getSpecificReqList(typeOfRequest);
            displayList(reqList);
        }
        JFXScrollPane.smoothScrolling(scrollPane);

        assignButton.setDisable(!UserHandling.getAdmin());
        assignButton.setVisible(UserHandling.getAdmin());

        updateButton.setDisable(!typeOfRequest.equals("CV19"));
        updateButton.setVisible(typeOfRequest.equals("CV19"));
        // addButton.setDisable(typeOfRequest.equals("CV19"));
        // addButton.setVisible(!typeOfRequest.equals("CV19"));
    }




}
