package bases_2.Tarea_2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Casa on 25/08/2014.
 */
public class File_Utils {
    public static Map<String,String> readFile(File f){
        Map<String,String> map = new HashMap<>();
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(f));
            //List<String> lst =
            buffer.lines().filter(s->s.matches("(?!#).*[ ]?=[ ]?.*")).forEach(s->{  String k[] = s.split("=");
                                                                                    k[1]=k[1].split("#")[0].trim();
                                                                                    map.put(k[0].trim(),k[1]);  }
                                                                             );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
}
