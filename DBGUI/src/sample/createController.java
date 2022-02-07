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

public class createController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    Boolean included = false;

    @FXML
    Button addButton;
    @FXML
    Button menuButton;
    @FXML
    ListView list;
    @FXML
    TextField attributeBox;
    @FXML
    TextField nameBox;
    @FXML
    ComboBox comboBox;
    @FXML
    Button addAttribute;
    @FXML
    Label Label;

    PreparedStatement pstmt=null;
    Connection con=null;
    String sql;

    public void buttonPressed(ActionEvent event )throws IOException
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); //return to main menu
        if(((Button)event.getSource()).getId().equals("menuButton")) {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if(((Button)event.getSource()).getId().equals("addAttribute") && !attributeBox.getText().equals("")) { //preventing duplicate entries
            included = false;
            for(int i = 0; i < list.getItems().size(); i++)
            {
                if (list.getItems().get(i).equals(attributeBox.getText() + " [VARCHAR(40)]") || list.getItems().get(i).equals(attributeBox.getText() + " [Number]"))
                {
                    included = true;
                }
            }
            if(!included) {
                list.getItems().add(attributeBox.getText() + " [" + comboBox.getValue() + "]");
            }


        }
        if(((Button)event.getSource()).getId().equals("addButton") && !nameBox.getText().equals("")) { //add the table to the database
            try {
                String temp = "";
                con = DBUtil.getConnection();
                for(int i = 0; i < list.getItems().size(); i++) {

                     temp = ((String)list.getItems().get(i)).replace("[", ""); //replace the [] with nothing in order to facilitate command construction
                     temp = temp.replace("]", "");
                    //System.out.println(temp);
                     list.getItems().remove(i);
                     list.getItems().add(i, temp);
                    //System.out.println(list.getItems().get(i));

                }
                sql = "CREATE TABLE " + nameBox.getText() + " (" + list.getItems().get(0) + " PRIMARY KEY";
                for(int i = 1; i < list.getItems().size(); i++) {

                    sql = sql + ", "+ list.getItems().get(i) ;
                }
                sql = sql + ")";
                System.out.println(sql);
                pstmt = con.prepareStatement(sql);
                pstmt.executeUpdate();


            }catch (SQLException e)
            {
                e.printStackTrace();
                Label.setText("Error Detected. Name likely in use.");
            }
        }



    }
    @FXML
    public void initialize() //just initializing the lists and combobox to do things correctly.
    {
        list.getItems().removeAll(list.getItems());
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("VARCHAR(40)", "Number");
        comboBox.getSelectionModel().select("VARCHAR(40)");
    }
}
