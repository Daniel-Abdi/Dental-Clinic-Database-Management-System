package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button create_tables;

    @FXML
    private Button drop_tables;

    @FXML
    private Button populate_tables;

    @FXML
    private Button exit;
    @FXML
    private Button query_tables;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void buttonPressed(ActionEvent event) throws IOException
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); //go to drop tables
        if(((Button)event.getSource()).getId().equals("drop_tables")) {
            root = FXMLLoader.load(getClass().getResource("drop.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if(((Button)event.getSource()).getId().equals("create_tables")) { //go to create tables
            root = FXMLLoader.load(getClass().getResource("create.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if(((Button)event.getSource()).getId().equals("populate_tables")) { //go to populate tables
            root = FXMLLoader.load(getClass().getResource("populate.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if(((Button)event.getSource()).getId().equals("query_tables")) { //go to query tables
            root = FXMLLoader.load(getClass().getResource("query.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if(((Button)event.getSource()).getId().equals("deleteEntry")) { //go to delete an entry
            root = FXMLLoader.load(getClass().getResource("delete.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if(((Button)event.getSource()).getId().equals("exit")) { //exit the program

            stage.close();
        }


    }


}
