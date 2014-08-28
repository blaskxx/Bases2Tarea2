package edu.una.adb.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casa on 28/08/2014.
 */
public class LGroups {
    public List<LogGroup> groups;
    public LGroups(){
        groups = new ArrayList<LogGroup>();
    }
    public void add(LogGroup l){
        groups.add(l);
    }
    public LogGroup get(int i){
        return groups.get(i);
    }
    public List<LogGroup> getGroups(){
        return groups;
    }
}
