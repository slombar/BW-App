package edu.wpi.cs3733.teamO.HelperClasses;

import java.awt.*;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/*
  // alignList = new ArrayList<>();
        selectedNode = clickedNode;

       PopUp nodePop = new PopUp();

        // create window event
      EventHandler<WindowEvent> event =
          new EventHandler<WindowEvent>() {
             public void handle(WindowEvent e) {
               // TODO highlight node you are changing
             }
          };

       // add event
      nodePop.setShowingProperties(event);
   }

    draw();
     System.out.println("mapCanvas click");
 */

public class PopUp {
  // create a menu
  ContextMenu contextMenu;

  public PopUp() {

    // create a menu
    contextMenu = new ContextMenu();

    // create menuitems
    MenuItem menuItem1 = new MenuItem("edit");
    MenuItem menuItem2 = new MenuItem("delete");
    MenuItem menuItem3 = new MenuItem("add edge");

    // add menu items to menu
    contextMenu.getItems().add(menuItem1);
    contextMenu.getItems().add(menuItem2);
    contextMenu.getItems().add(menuItem3);
  }

  public void setShowingProperties(EventHandler event) {
    // add event
    contextMenu.setOnShowing(event);
    contextMenu.setOnHiding(event);
  }
}
