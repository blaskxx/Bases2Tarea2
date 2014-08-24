package main;

import edu.una.adb.entities.Init_File;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;


import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TableView t_file_props;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Init_File ini = new Init_File();
        Map<String, String> map = ini.getData();


        // use fully detailed type for Map.Entry<String, String>
        TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Opción");
        column1.setCellValueFactory((p)-> new SimpleStringProperty(p.getValue().getKey()));

        TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Valor");
        column2.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(map.entrySet());
        t_file_props.setItems(items);
        column2.setEditable(true);
        column1.setEditable(true);
        t_file_props.setEditable(true);

        t_file_props.getColumns().setAll(column1, column2);
    }
}
