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
    private static File sqlFile;

    @FXML    private TableView t_file_props;
    @FXML    private Button BT_add;
    @FXML    private Button BT_open;
    @FXML    private Button BT_new;
    @FXML    private Button BT_saveIni;
    @FXML    private TextField TF_opc;
    @FXML    private TextField TF_val;
    @FXML    private TextField TF_path;

    @FXML   private TextField DF_Text1;
    @FXML   private TextField DFSize_Text1;
    @FXML   private TextField DFAutoExtend_Text1;
    @FXML   private TextField DFMAXSIZE_Text1;

    @FXML   private TextField DF_Text2;
    @FXML   private TextField DFSize_Text2;
    @FXML   private TextField DFAutoExtend_Text2;
    @FXML   private TextField DFMAXSIZE_Text2;


    @FXML   private TextField DF_Text3;
    @FXML   private TextField DFSize_Text3;
    @FXML   private TextField DFAutoExtend_Text3;
    @FXML   private TextField DFMAXSIZE_Text3;


    @FXML   private TextField DF_Text;
    @FXML   private TextField DFSize_Text;
    @FXML   private TextField DFAutoExtend_Text;
    @FXML   private TextField DFMAXSIZE_Text;

    private  FileChooser fileChooser = new FileChooser();
    private File openedFile;
    private boolean isFileOpen = false;



    private boolean validateQuerys(String df_text,String df_sizeText,String dfautoextend_text,String dfmaxsize_text){
        if((df_text.isEmpty()||df_text==null) && (df_sizeText==null||df_sizeText.isEmpty()) &&
                (dfautoextend_text==null||dfautoextend_text.isEmpty())&&(dfmaxsize_text==null||dfmaxsize_text.isEmpty()))
            return false;
        else return true;
    }

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


        //Tabla
        Inicialize_Table();

        BT_open.setOnMousePressed(e->{
            fileChooser.setTitle("Open Config File");
            openedFile = fileChooser.showOpenDialog(BT_open.getScene().getWindow());
            if(openedFile != null){
                isFileOpen = true;
                ini = new Init_File(readFile(openedFile));
                ini.setPath(openedFile.getAbsolutePath());
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
                ini.setPath(openedFile.getAbsolutePath());
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

        //sql.generarFormato();
        FileChooser fileChooser = new FileChooser();
        boolean system=false,sysaux=false,temp=false,undo=false;

        system=validateQuerys(DF_Text.getText(),DFSize_Text.getText(),DFAutoExtend_Text.getText(),DFMAXSIZE_Text.getText());
        sysaux=validateQuerys(DF_Text1.getText(),DFSize_Text1.getText(),DFAutoExtend_Text1.getText(),DFMAXSIZE_Text1.getText());
        temp=validateQuerys(DF_Text2.getText(),DFSize_Text2.getText(),DFAutoExtend_Text2.getText(),DFMAXSIZE_Text2.getText());
        undo=validateQuerys(DF_Text3.getText(),DFSize_Text3.getText(),DFAutoExtend_Text3.getText(),DFMAXSIZE_Text3.getText());
        sql.generarFormato(system,DF_Text.getText(),DFSize_Text.getText(),DFAutoExtend_Text.getText(),DFMAXSIZE_Text.getText(),
            sysaux,DF_Text1.getText(),DFSize_Text1.getText(),DFAutoExtend_Text1.getText(),DFMAXSIZE_Text1.getText(),
            temp,DF_Text2.getText(),DFSize_Text2.getText(),DFAutoExtend_Text2.getText(),DFMAXSIZE_Text2.getText(),
            undo,DF_Text.getText(),DFSize_Text.getText(),DFAutoExtend_Text.getText(),DFMAXSIZE_Text.getText()
        );

        // Pone Extención
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "SQL files (*.sql)", "*.sql");
        fileChooser.getExtensionFilters().add(extFilter);
        // Muestra la caja de dialogo
        sqlFile = fileChooser.showSaveDialog(BT_open.getScene().getWindow());
        if (sqlFile != null) {
            // Valida la extensión
            if (!sqlFile.getPath().endsWith(".sql")) {
                sqlFile = new File(sqlFile.getPath() + ".sql");
            }
            if(sql.getFormato()!=null)
                sql.save(sqlFile);
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

