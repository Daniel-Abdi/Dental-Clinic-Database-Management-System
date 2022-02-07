package sample;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
public class delete
{
    private Stage stage;
    private Scene scene;
    private Parent root;
    String sql;
    PreparedStatement pstmt=null;
    Connection con=null;
    @FXML
    Button menuButton;
    @FXML
    Button deleteButton;
    @FXML
    TextField tableName;
    @FXML
    TextField keyName;
    @FXML
    Label errorLabel;
    @FXML
    TextField keyValue;

    public void buttonPressed(ActionEvent event)throws IOException
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        if(((Button)event.getSource()).getId().equals("menuButton")) { //Returns to main menu
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        if(((Button)event.getSource()).getId().equals("deleteButton") && !tableName.getText().equals("") && !keyName.getText().equals("") && !keyValue.getText().equals("")) { //if the delete button is pressed and none of the fields are empty
            if(isNumeric(keyValue.getText())) {
                sql = "DELETE FROM " + tableName.getText() + " WHERE " + keyName.getText() + "=" + keyValue.getText(); // if a number, do nothing
            }
            else
            {
                sql = "DELETE FROM " + tableName.getText() + " WHERE " + keyName.getText() + "='" +keyValue.getText() + "'" ; //if not a number add quotes around
            }
            try
            {
                System.out.println(sql);
                con = DBUtil.getConnection();
                pstmt = con.prepareStatement(sql);
                pstmt.executeUpdate(); //send query to database
                errorLabel.setText("Success!");
            }catch (SQLException e)
            {
                e.printStackTrace();
                errorLabel.setText("Error Detected. Are you sure your values are correct?");

            }
        }
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
