package sample;

import java.io.IOException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;


public class LoginController  {

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private Label label;

    private Stage stage;
    private Scene scene;
    private Parent root;

    PreparedStatement pstmt=null;
    Connection con=null;

    public void Login(ActionEvent event)throws IOException{
        try {
            ResultSet rs;
            con=DBUtil.getConnection();

            String sql = "SELECT * FROM administrator where admin_id = ? and admin_pass = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,userName.getText());
            pstmt.setString(2, password.getText());
            rs = pstmt.executeQuery();

            //System.out.println(rs.getString(1));
            if (rs.next())
            {
                String s = rs.getString(1);
                System.out.println(s);
                label.setText("Login Success");
                Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                label.setText("Invalid Login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
