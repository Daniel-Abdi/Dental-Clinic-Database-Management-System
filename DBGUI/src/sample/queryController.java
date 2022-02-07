package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;

import java.io.IOException;
public class queryController {

    @FXML
    TextField columnField;
    @FXML
    TextField tableField;
    @FXML
    TextField condField;
    @FXML
    TextField orderField;
    @FXML
    Button menuButton;
    @FXML
    Button queryButton;
    @FXML
    TableView tableView;
    @FXML
    Label errorLabel;
    @FXML
    TextField groupField;

    private Stage stage;
    private Scene scene;
    private Parent root;
    PreparedStatement pstmt=null;
    Connection con=null;
    String sql = "";
    private ObservableList<ObservableList> data;
    public void buttonPressed(ActionEvent event)throws IOException
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); //return to menu
        if(((Button)event.getSource()).getId().equals("menuButton")) {
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if(((Button)event.getSource()).getId().equals("queryButton") && !columnField.getText().equals("") && !tableField.getText().equals("")) { //if the query button is hit
            sql = "SELECT " + columnField.getText() + " FROM " +tableField.getText();
            data = FXCollections.observableArrayList();

            if(!condField.getText().equals("")) //if the optional condition field is used
            {
                sql = sql + " WHERE " + condField.getText();
            }
            if(!groupField.getText().equals("")) //if the group field is used.
            {
                sql = sql + " GROUP BY " + groupField.getText();
            }
            if(!orderField.getText().equals("")) //if the order field is used.
            {
                sql = sql + " ORDER BY " + orderField.getText();
            }


                try{
                    /* The code below up until the catch section is all for populating the tableview with the values returned from database. */
                con = DBUtil.getConnection();
                pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
                {
                    final int j = i;

                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    tableView.getColumns().addAll(col);
                    //System.out.println("Column [" + i + "] ");
                }
                    while(rs.next()){
                        //Iterate Row
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                            //Iterate Column
                            row.add(rs.getString(i));
                        }
                        //System.out.println("Row [1] added "+row );
                        data.add(row);

                    }
                    tableView.setItems(data);
                }catch (SQLException e) {
                    e.printStackTrace();
                    errorLabel.setText("Error found. Please check your input values.");
                }
        }
    }


    @FXML
    public void initialize()
    {

    }
}
