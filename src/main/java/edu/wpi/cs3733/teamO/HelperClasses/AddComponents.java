package edu.wpi.cs3733.teamO.HelperClasses;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AddComponents {

    public static ArrayList<JFXTextField> test;
    public static VBox test2;
    public static HBox test3;

    public static ArrayList<JFXTextField> createField (ArrayList<String> labels){
        return test;
    }

    public static VBox addToVBox (){
        return test2;
    }

    public static HBox addToHBox (){
        return test3;
    }


}
