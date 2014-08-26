package edu.una.adb.entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Casa on 24/08/2014.
 */
public class Init_File{
    private final Map<String,String> data;

    public Init_File() {
        data = new HashMap<String, String>();
        data.put("db_name", "STBC");
        data.put("db_files" , "1024");
        data.put("control_files" , "(,)");
        data.put("db_file_multiblock_read_count" ,  "8");
        data.put("open_cursors","300");
        data.put("SGA_MAX_SIZE" , "3G");
        data.put("SGA_TARGET"   , "2G");
        data.put("large_pool_size"  , "1048576");
        data.put("db_cache_size"    , "50331648");
        data.put("java_pool_size"   , "67108864");
        data.put("shared_pool_size" , "150994945");
        data.put("db_block_size" , "4096");
        data.put("log_checkpoint_interval" , "10000");
        data.put("processes"  , "59");
        data.put("log_buffer" ,  "8192");
        data.put("audit_trail" , "true");
        data.put("timed_statistics" , "true");
        data.put("max_dump_file_size" , "10240");
        data.put("log_archive_start" , "true");
        data.put("log_archive_dest" ,"");
        data.put("log_archive_format" ,"%%ORACLE_SID%%T%TS%S.ARC");
        data.put("global_names" , "TRUE");
    }
    public Init_File(Map<String, String> m){
        data = new HashMap<String, String>();
        data.put("db_name", "STBC");
        data.put("db_files" , "1024");
        data.put("control_files" , "(,)");
        data.put("db_file_multiblock_read_count" ,  "8");
        data.put("open_cursors","300");
        data.put("SGA_MAX_SIZE" , "3G");
        data.put("SGA_TARGET"   , "2G");
        data.put("large_pool_size"  , "1048576");
        data.put("db_cache_size"    , "50331648");
        data.put("java_pool_size"   , "67108864");
        data.put("shared_pool_size" , "150994945");
        data.put("db_block_size" , "4096");
        data.put("log_checkpoint_interval" , "10000");
        data.put("processes"  , "59");
        data.put("log_buffer" ,  "8192");
        data.put("audit_trail" , "true");
        data.put("timed_statistics" , "true");
        data.put("max_dump_file_size" , "10240");
        data.put("log_archive_start" , "true");
        data.put("log_archive_dest" ,"");
        data.put("log_archive_format" ,"%%ORACLE_SID%%T%TS%S.ARC");
        data.put("global_names" , "TRUE");
        data.putAll(m);
    }

    public Map<String, String> getData() {
        return data;
    }

    public void save(File f) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write("#Archivo Creado Con la Herramienta Desarrollada como tare2 DABD UNA 2014");writer.newLine();
            for(Map.Entry me: data.entrySet()) {
                writer.write(me.getKey() + "=" + (me.getValue().equals("")?" ":me.getValue()));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
