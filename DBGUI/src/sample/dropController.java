package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import java.io.IOException;
public class dropController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    PreparedStatement pstmt=null;
    Connection con=null;

    @FXML
    private Button menuButton;

    @FXML
    private Button drop_Button;

    public void buttonPressed(ActionEvent event) throws IOException
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); //return to menu
        if(((Button)event.getSource()).getId().equals("menuButton")) {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if(((Button)event.getSource()).getId().equals("drop_button")) { //when the button is hit, drop literally everything
            try
            {
                con = DBUtil.getConnection();


                pstmt = con.prepareStatement("DROP TABLE appointment_history");
                pstmt.executeUpdate();
                pstmt = con.prepareStatement("DROP TABLE medical_history");
                pstmt.executeUpdate();
                pstmt = con.prepareStatement("DROP TABLE dentist_contact");
                pstmt.executeUpdate();
                pstmt = con.prepareStatement("DROP TABLE appointment");
                pstmt.executeUpdate();
                pstmt = con.prepareStatement("DROP TABLE dentist");
                pstmt.executeUpdate();
                pstmt = con.prepareStatement("DROP TABLE problem");
                pstmt.executeUpdate();
                pstmt = con.prepareStatement("DROP TABLE patient_insurance");
                pstmt.executeUpdate();
                pstmt = con.prepareStatement("DROP TABLE patient");
                pstmt.executeUpdate();
                System.out.println("Tables dropped.");
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

    }
}
