package me.maxrenner.managers;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

// Static Manager for Managing YAML Files
public class FileManager {
    /**
     * @return A String ArrayList of the contents of the 'words' variable in YAML.
     */
    public static ArrayList<String> readFile(InputStream inputStream) {
        Yaml yaml = new Yaml();
        Map<String, ArrayList<String>> data = yaml.load(inputStream);
        return data.get("words");
    }
}
