package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;

import java.io.IOException;
public class populateController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    ListView list;

    @FXML
    Button addValue;

    @FXML
    Button populateButton;

    @FXML
    TextField tableName;
    @FXML
    TextField valueField;
    @FXML
    Label errorLabel;
    @FXML
    Button menuButton;
    String sql;
    PreparedStatement pstmt=null;
    Connection con=null;
    public void buttonPressed(ActionEvent event) throws IOException
    {

        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); //return to main menu
        if(((Button)event.getSource()).getId().equals("menuButton")) {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if(((Button)event.getSource()).getId().equals("addValue") && !valueField.getText().equals("")) { //add value to list of things to add
            if(isNumeric(valueField.getText()))
            {
                list.getItems().add(valueField.getText());
            }
            else
            {
                list.getItems().add("'"+ valueField.getText() + "'");
            }

        }
        if(((Button)event.getSource()).getId().equals("populateButton") && !tableName.getText().equals("")) { //populate the database with the new entry
            sql = "INSERT INTO " + tableName.getText() + " VALUES (" + list.getItems().get(0);
            for(int i = 1; i< list.getItems().size(); i++)
            {
                sql = sql + ", " + list.getItems().get(i);
            }
            sql = sql + ")";
            //System.out.println(sql);

            try
            {
                con = DBUtil.getConnection();
                pstmt = con.prepareStatement(sql);
                pstmt.executeUpdate();
            }catch(SQLException e)
            {
                e.printStackTrace();
                errorLabel.setText("Error. Either a value is the wrong type or it is duplicate.");
            }

        }
    }


    @FXML
    public void initialize()
    {
        list.getItems().removeAll(list.getItems());
        sql = "";
    }

    public static boolean isNumeric(String string) {
        int intValue;
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
}
