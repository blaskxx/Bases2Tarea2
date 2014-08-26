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
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Map<String, String> map;
    @FXML    private TableView t_file_props;
    @FXML    private Button BT_add;
    @FXML    private Button BT_open;
    @FXML    private Button BT_new;
    @FXML    private TextField TF_opc;
    @FXML    private TextField TF_val;
    @FXML    private TextField TF_path;

    private  FileChooser fileChooser = new FileChooser();
    private File openedFile;
    private boolean isFileOpen = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Init_File ini = new Init_File();
        map = ini.getData();
        //Tabla
        Inicialize_Table(map);



        BT_open.setOnMousePressed(e->{
            fileChooser.setTitle("Open Config File");
            openedFile = fileChooser.showOpenDialog(BT_open.getScene().getWindow());
            if(openedFile != null){
                isFileOpen = true;
                //AQUI va el readfile
                if(readFile(openedFile)){
                    Inicialize_Table(map);
                }
                TF_path.setText(openedFile.getAbsolutePath());
            }
        });
        BT_new.setOnMousePressed(e->{
            fileChooser.setTitle("Guardar Archivo...");
            openedFile = fileChooser.showSaveDialog(BT_open.getScene().getWindow());
            if(openedFile != null){
                isFileOpen = true;
                TF_path.setText(openedFile.getAbsolutePath());
            }
        });
    }

    boolean readFile(File file){
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));

            String text;

            while((text=buffer.readLine()) != null){

                if(!text.startsWith("#")&&!text.isEmpty()){

                    text = text.replaceAll("\\s+","");
                    String[] palabras=text.split("=");
                    System.out.println(palabras[0]);
                    if(map.containsKey(palabras[0])){
                        if(palabras[1].contains("#")){
                            String[] aux=palabras[1].split("#");

                            palabras[1]=aux[0];
                        }
                        map.replace(palabras[0],palabras[1]);

                    }else{

                        if(palabras[1].contains("#")){
                            String[] aux=palabras[1].split("#");

                            palabras[1]=aux[0];
                        }
                        map.put(palabras[0],palabras[1]);

                    }

                }
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void Inicialize_Table(Map<String, String> mapa){
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
            mapa.put(entry.getKey(), e.getNewValue());
        });

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(mapa.entrySet());


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
            items.addAll(mapa.entrySet());
        });

    }

}

