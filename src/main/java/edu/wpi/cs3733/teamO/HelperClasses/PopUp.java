package edu.wpi.cs3733.teamO.HelperClasses;

import edu.wpi.cs3733.teamO.Controllers.Revamped.NavController;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import java.awt.event.MouseAdapter;

public class PopUp {

        public PopUp() {

            // create a menu
            ContextMenu contextMenu = new ContextMenu();

            // create menuitems
            MenuItem menuItem1 = new MenuItem("menu item 1");
            MenuItem menuItem2 = new MenuItem("menu item 2");
            MenuItem menuItem3 = new MenuItem("menu item 3");

            // add menu items to menu
            contextMenu.getItems().add(menuItem1);
            contextMenu.getItems().add(menuItem2);
            contextMenu.getItems().add(menuItem3);

            // create window event
            EventHandler<WindowEvent> event = new EventHandler<WindowEvent>() {
                public void handle(WindowEvent e)
                {
                    //TODO highlight node you are changing
                }
            };

            // add event
            contextMenu.setOnShowing(event);
            contextMenu.setOnHiding(event);
        }

}
