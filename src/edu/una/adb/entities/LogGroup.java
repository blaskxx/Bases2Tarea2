package edu.una.adb.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Casa on 28/08/2014.
 */
public class LogGroup {
    private List<String> redo;
    public LogGroup(){
        redo = new ArrayList<String>();
    }
    public void add(String r){
        redo.add(r);
    }
    public String get(int i){
        return  redo.get(i);
    }
    public List<String> getRedo(){
        return redo;
    }
    public String toStrign(){
        if(redo.isEmpty()) return " ";
        String newline = System.getProperty("line.separator");
        final StringBuilder ret = new StringBuilder();
        ret.append("\'");
        for(int i=0; i<redo.size()-1;i++)
           ret.append(redo.get(i)).append(" , ");
        ret.append(redo.get(redo.size()-1)).append("\'");
        return  ret.toString();
    }
}
