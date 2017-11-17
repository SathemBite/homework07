package task2;

import java.io.*;
import java.util.*;

/**
 * Created by anton on 03.11.17.
 */
public class SynchAccessToProp {
    private HashMap<String, String> data;
    private static HashSet<String> busyFiles;
    private static Object synchObj = new Object();
    private static boolean isThreadIn = false;

    public SynchAccessToProp(String fileName){
        if (takeFile(fileName)){
            reading(fileName);
        } else{
            synchReading(fileName);
        }
    }

    private static synchronized HashMap<String, String> synchReading(String fileName){
        return reading(fileName);
    }

    private static HashMap<String, String> reading(String fileName){
        HashMap<String, String> props = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader("resources/" + fileName + ".properties"))){
            br.lines().
                    forEach(str ->
                            props.put(
                                    str.substring(0, str.indexOf('=')),
                                    str.substring(str.indexOf('=') + 1, str.length())
                            )
                    );
        } catch (NullPointerException ex){
            System.out.println("Error! Properties file name or locale is null!");
        } catch (MissingResourceException ex){
            System.out.println("Error! The specified properties file not exist!");
        } catch (IOException ex){
            System.out.println("Error! The specified file is opened by another process!");
        }

        return props;
    }

    public static synchronized boolean takeFile(String fileName){
        if (busyFiles.contains(fileName)){
            return false;
        }else{
            busyFiles.add(fileName);
            return true;
        }
    }

    public static synchronized void freeFile(String fileName){
        busyFiles.remove(fileName);
    }


    public String get(String key){
        try {
            return data.get(key);
        } catch (NullPointerException ex){
            System.out.println("Property not found!");
            return "";
        }

    }
}

