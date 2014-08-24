package main;

import edu.una.adb.entities.Init_File;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Map<String, String> map;
    @FXML    private TableView t_file_props;
    @FXML    private Button BT_add;
    @FXML    private TextField TF_opc;
    @FXML    private TextField TF_val;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Init_File ini = new Init_File();
        map = ini.getData();

        //**************************************************************************************************//
        //Table
        // use fully detailed type for Map.Entry<String, String>
        TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Opción");
        column1.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getKey()));


        TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Valor");
        column2.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        column2.setOnEditCommit(e -> {
            Map.Entry<String, String> entry = e.getTableView().getItems().get(e.getTablePosition().getRow());
            map.put(entry.getKey(), e.getNewValue());
        });

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(map.entrySet());
        //ObservableMap<String,String> itemsMap = FXCollections.observableMap(map);

        column2.setEditable(true);
        column1.setEditable(false);
        t_file_props.setEditable(true);

        t_file_props.setItems(items);
        t_file_props.getColumns().setAll(column1, column2);

        /***                    BUTTONS                     ***/
        /*--------------------------------------------------**/
        BT_add.setOnMousePressed(e -> {
            if (TF_opc.getText() == null || TF_val.getText() == null) return;
            if (TF_opc.getText().trim().equals("") || TF_val.getText().trim().equals("")) return;
            map.put(TF_opc.getText(), TF_val.getText());
            items.clear();
            items.addAll(map.entrySet());
        });

    }

}
