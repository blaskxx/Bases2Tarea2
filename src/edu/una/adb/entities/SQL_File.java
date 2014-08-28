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

    public void generarFormato(boolean system,String datafile,String TSSize,String autoextendSize,String MAXSIZE,
            boolean sysaux,String datafile1,String TSSize1,String autoextendSize1, String MAXSIZE1,
            boolean temp,String datafile2,String TSSize2,String autoextendSize2, String MAXSIZE2,
            boolean undo,String datafile3,String TSSize3,String autoextendSize3, String MAXSIZE3
                               ){
        StringBuilder sb= new StringBuilder();
        System.out.println(system);
        if(system){

            sb.append("CREATE TABLESPACE SYSTEM");

            if(!datafile.isEmpty()){

                sb.append(" DATAFILE "+datafile);

            }
            if(!TSSize.isEmpty()){
                sb.append(" SIZE "+TSSize);
                sb.append("M ");
            }
            if(!autoextendSize.isEmpty()){
                sb.append(" AUTOEXTEND ON NEXT ");
                sb.append(autoextendSize);
                sb.append("M ");
            }
            if(!MAXSIZE.isEmpty()){
                sb.append(" MAXSIZE ");
                sb.append(MAXSIZE);
                sb.append("M ");
            }
            sb.append(";\n");

        }
        if(sysaux){

            sb.append("CREATE TABLESPACE SYSAUX");

            if(!datafile1.isEmpty()){

                sb.append(" DATAFILE "+datafile1);

            }
            if(!TSSize1.isEmpty()){
                sb.append(" SIZE "+TSSize1);
                sb.append("M ");
            }
            if(!autoextendSize1.isEmpty()){
                sb.append(" AUTOEXTEND ON NEXT ");
                sb.append(autoextendSize1);
                sb.append("M ");
            }
            if(!MAXSIZE1.isEmpty()){
                sb.append(" MAXSIZE ");
                sb.append(MAXSIZE1);
                sb.append("M ");
            }
            sb.append(";\n");

        }
        if(temp){

            sb.append("CREATE TABLESPACE TEMP");

            if(!datafile2.isEmpty()){

                sb.append(" DATAFILE "+datafile2);

            }
            if(!TSSize2.isEmpty()){
                sb.append(" SIZE "+TSSize2);
                sb.append("M ");
            }
            if(!autoextendSize2.isEmpty()){
                sb.append(" AUTOEXTEND ON NEXT ");
                sb.append(autoextendSize2);
                sb.append("M ");
            }
            if(!MAXSIZE2.isEmpty()){
                sb.append(" MAXSIZE ");
                sb.append(MAXSIZE2);
                sb.append("M ");
            }
            sb.append(";\n");
        }
        if(undo){

            sb.append("CREATE TABLESPACE UNDO");

            if(!datafile3.isEmpty()){

                sb.append(" DATAFILE "+datafile3);

            }
            if(!TSSize3.isEmpty()){
                sb.append(" SIZE "+TSSize3);
                sb.append("M ");
            }
            if(!autoextendSize3.isEmpty()){
                sb.append(" AUTOEXTEND ON NEXT ");
                sb.append(autoextendSize3);
                sb.append("M ");
            }
            if(!MAXSIZE3.isEmpty()){
                sb.append(" MAXSIZE ");
                sb.append(MAXSIZE3);
                sb.append("M ");
            }
            sb.append(";\n");
        }


        formato=sb.toString();
    }

    public String getFormato(){
        return formato;
    }

    public void save(File f) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write("--Archivo Creado Con la Herramienta Desarrollada como tare2 DABD UNA 2014");writer.newLine();

                writer.write(formato);
                writer.newLine();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
