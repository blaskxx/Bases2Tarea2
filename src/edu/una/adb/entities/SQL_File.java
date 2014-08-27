package edu.una.adb.entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by jose on 26/08/14.
 */
public class SQL_File {

    private String formato;

    public void generarFormato(String tablespace, String datafile,String TSSize,String autoextendSize, String MAXSIZE){
        StringBuilder sb= new StringBuilder();
        System.out.println(tablespace);
        if(tablespace!=null){

            sb.append("CREATE TABLESPACE "+tablespace);

            if(!datafile.isEmpty()){

                sb.append(" DATAFILE "+datafile);

            }
            if(!TSSize.isEmpty()){
                sb.append(" SIZE "+TSSize);
                sb.append(" M ");
            }
            if(!autoextendSize.isEmpty()){
                sb.append(" AUTOEXTEND ON NEXT ");
                sb.append(autoextendSize);
                sb.append(" M ");
            }
            if(!MAXSIZE.isEmpty()){
                sb.append(" MAXSIZE ");
                sb.append(MAXSIZE);
                sb.append(" M ");
            }
            sb.append(";");
            formato=sb.toString();
        }

    }

    public String getFormato(){
        return formato;
    }

    public void save(File f) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write("#Archivo Creado Con la Herramienta Desarrollada como tare2 DABD UNA 2014");writer.newLine();

                writer.write(formato);
                writer.newLine();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
