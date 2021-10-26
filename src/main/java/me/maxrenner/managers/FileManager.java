package me.maxrenner.managers;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

// Static Manager for Managing YAML Files
public class FileManager {

    private static Map<String, ArrayList<String>> data;

    public static void loadFile(InputStream inputStream){
        Yaml yaml = new Yaml();
        data = yaml.load(inputStream);
    }

    /**
     * @return A String ArrayList of the contents of the 'words' variable in YAML.
     */
    public static ArrayList<String> readFile() {
        return data.get("words");
    }
}
