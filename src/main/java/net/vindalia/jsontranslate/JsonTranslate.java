package net.vindalia.jsontranslate;

import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonTranslate {

    private final HashMap<String, HashMap<String, String>> translationMap;

    public JsonTranslate(File translationFile) throws IOException {
        this.translationMap = new HashMap<>();
        String json = this.readFromFile(translationFile);

        JSONObject object = new JSONObject(json);
        Map<String, Object> rawMap = object.toMap();
        for (Map.Entry<String, Object> topLevelSet : rawMap.entrySet()) {
            if (topLevelSet.getValue() instanceof String) {
                this.insertSet("de", topLevelSet.getKey(), (String) topLevelSet.getValue());
            } else if (topLevelSet.getValue() instanceof HashMap) {
                this.processTranslations(topLevelSet.getKey(), (HashMap<String, Object>) topLevelSet.getValue());
            }
        }
    }

    public String translate(String language, String key) {
        return this.translationMap.get(key).get(language);
    }

    private void processTranslations(String key, HashMap<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String translationKey = key + "." + entry.getKey();

            if (entry.getValue() instanceof String) {
                this.insertSet("de", translationKey, (String) entry.getValue());
            } else if (entry.getValue() instanceof HashMap) {
                this.processTranslations(translationKey, (HashMap<String, Object>) entry.getValue());
            }
        }
    }

    private void insertSet(String language, String key, String value) {
        if (this.translationMap.containsKey(key)) {
            this.translationMap.get(key).put(language, value);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(language, value);
            this.translationMap.put(key, map);
        }
    }

    private String readFromFile(File translationFile) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(translationFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line).append("\n");
            }
        }
        return resultBuilder.toString();
    }
}
