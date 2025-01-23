package org.alphacreative.data;

import java.io.*;
import java.util.Properties;

public class DataManager {

    private Properties properties;
    private File file;

    public DataManager(String fileName) {
        properties = new Properties();
        file = new File(fileName);
        loadData();
    }
    public void loadData() {
        if (!file.exists()) {
            try {
                file.createNewFile();
                initializeDefaultData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeDefaultData() { //Esto lo podemos configurar a nuestro gusto
        properties.setProperty("record", "0");
        properties.setProperty("index", "0");
        properties.setProperty("lastscore", "0");
        saveData();
    }

    public void saveData() {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRecord(int record) {
        properties.setProperty("record", String.valueOf(record));
        saveData();
    }

    public int getRecord() {
        return Integer.parseInt(properties.getProperty("record", "0"));
    }

    public void saveIndex(int index) {
        properties.setProperty("index", String.valueOf(index));
        saveData();
    }

    public int getIndex() {
        return Integer.parseInt(properties.getProperty("index", "0"));
    }

    public void saveLastScore(int lastScore) {
        properties.setProperty("lastscore", String.valueOf(lastScore));
        saveData();
    }
    public int getLastScore() {
        return Integer.parseInt(properties.getProperty("lastscore", "0"));
    }

    public void saveValue(String propertieName, int value)
    {
        properties.setProperty(propertieName, String.valueOf(value));
        saveData();
    }
    public int getValue(String propertieName)
    {
        return Integer.parseInt(properties.getProperty(propertieName, "0"));
    }
}