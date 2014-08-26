package main;

import edu.una.adb.entities.Init_File;
import edu.una.adb.entities.SQL_File;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import org.controlsfx.dialog.Dialogs;

import java.io.*;
import java.net.URL;
import java.util.*;


import static bases_2.Tarea_2.util.File_Utils.readFile;

public class Controller implements Initializable {

    //private static Map<String, String> map = new HashMap<>();
    private static Init_File ini;

    @FXML    private TableView t_file_props;
    @FXML    private Button BT_add;
    @FXML    private Button BT_open;
    @FXML    private Button BT_new;
    @FXML    private Button BT_saveIni;
    @FXML    private TextField TF_opc;
    @FXML    private TextField TF_val;
    @FXML    private TextField TF_path;
    @FXML   private ComboBox<String> combobox;
    @FXML   private TextField DF_Text;
    @FXML   private TextField DFSize_Text;
    @FXML   private TextField DFAutoExtend_Text;
    @FXML   private TextField DFMAXSIZE_Text;
    ObservableList<String>  l= FXCollections.observableArrayList(
            "SYSTEM","SYSAUX","TEMP","UNDO"
    );
    private  FileChooser fileChooser = new FileChooser();
    private File openedFile;
    private boolean isFileOpen = false;

    public static ObservableList<String> getList(){
        ObservableList<String> list= FXCollections.observableArrayList();
        list.add("SYSTEM");
        list.add("SYSAUX");
        list.add("TEMP");
        list.add("UNDO");
        return list;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combobox.setItems(l);

        //Tabla
        Inicialize_Table();

        BT_open.setOnMousePressed(e->{
            fileChooser.setTitle("Open Config File");
            openedFile = fileChooser.showOpenDialog(BT_open.getScene().getWindow());
            if(openedFile != null){
                isFileOpen = true;
                ini = new Init_File(readFile(openedFile));
                TF_path.setText(openedFile.getAbsolutePath());
                this.restart_table_data();
            }
        });
        BT_new.setOnMousePressed(e->{
            fileChooser.setTitle("Guardar Archivo...");
            openedFile = fileChooser.showSaveDialog(BT_open.getScene().getWindow());
            if(openedFile != null){
                isFileOpen = true;
                TF_path.setText(openedFile.getAbsolutePath());
                ini=new Init_File();
                this.restart_table_data();
            }
        });

    }

    public void Inicialize_Table(){

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
            ini.getData().put(entry.getKey(), e.getNewValue());
        });

        column1.setEditable(false);
        column2.setEditable(true);

        column1.prefWidthProperty().bind(t_file_props.prefWidthProperty().multiply(0.4));
        column2.prefWidthProperty().bind(t_file_props.prefWidthProperty().multiply(0.4));

        column1.setSortType(TableColumn.SortType.ASCENDING);

        t_file_props.setEditable(true);

        t_file_props.getColumns().addAll(column1,column2);


        /***                    BUTTONS                     ***/
        /*--------------------------------------------------**/
        BT_add.setOnMousePressed(e -> {
            if (TF_opc.getText() == null || TF_val.getText() == null) return;
            if (TF_opc.getText().trim().equals("") || TF_val.getText().trim().equals("")) return;
            ini.getData().put(TF_opc.getText(), TF_val.getText());
            t_file_props.getItems().add(new AbstractMap.SimpleEntry<String, String>(TF_opc.getText(), TF_val.getText()));
            TF_opc.clear();TF_val.clear();
            //for(Map.Entry me: ini.getData().entrySet()) System.out.println(me.getKey()+" -> "+ me.getValue());
        });
        BT_saveIni.setOnMousePressed(e->{
                    if(isFileOpen)
                        ini.save(openedFile);
                }
        );


    }
    private void restart_table_data(){
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(ini.getData().entrySet());
        this.t_file_props.getItems().clear();
        this.t_file_props.setItems(items);
        this.t_file_props.getSortOrder().addAll(t_file_props.getColumns().get(0));
    }


    @FXML
    private void handleAbout() {
        Dialogs.create()
                .title("AddressApp")
                .masthead("About")
                .message("Author: José Pablo Madrigal \n Johan Salas \n Kevin Abarca.")
                .showInformation();

    }
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
    // Pone Extención
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Ora files (*.ora)", "*.ora");
        fileChooser.getExtensionFilters().add(extFilter);
    // Muestra la caja de dialogo
        File file = fileChooser.showSaveDialog(BT_open.getScene().getWindow());
        if (file != null) {
    // Valida la extensión
            if (!file.getPath().endsWith(".ora")) {
                file = new File(file.getPath() + ".ora");
            }
            ini.save(file);
        }
    }
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    SQL_File sql;
    @FXML
    private void handleSaveSQL(){

        sql= new SQL_File();
        sql.generarFormato(combobox.getValue(),DF_Text.getText(),DFSize_Text.getText(),DFAutoExtend_Text.getText(),
                DFMAXSIZE_Text.getText());



        FileChooser fileChooser = new FileChooser();
        // Pone Extención
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "SQL files (*.sql)", "*.sql");
        fileChooser.getExtensionFilters().add(extFilter);
        // Muestra la caja de dialogo
        File file = fileChooser.showSaveDialog(BT_open.getScene().getWindow());
        if (file != null) {
            // Valida la extensión
            if (!file.getPath().endsWith(".sql")) {
                file = new File(file.getPath() + ".sql");
            }
            if(sql.getFormato()!=null)
                sql.save(file);
            else{
                Dialogs.create()
                        .title("Alert")
                        .masthead("Alert")
                        .message("No se ha generado ningún archivo el cual guardar.")
                        .showWarning();
            }
        }
    }




}

