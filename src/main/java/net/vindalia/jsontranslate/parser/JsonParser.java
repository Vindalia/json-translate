package net.vindalia.jsontranslate.parser;

import net.vindalia.jsontranslate.store.TranslationStore;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    private final TranslationStore translationStore;

    public JsonParser(TranslationStore translationStore) {
        this.translationStore = translationStore;
    }

    public void parse(File translationDirectory) throws IOException {
        this.findTranslationFiles(translationDirectory);
    }

    private void findTranslationFiles(File translationDirectory) throws IOException {
        File[] files = translationDirectory.listFiles();
        if (files == null) {
            throw new FileNotFoundException("No translation files found");
        }
        for (File file : files) {
            String fileName = file.getName();
            if (!fileName.endsWith(".json")) {
                continue;
            }
            this.parseFile(file);
        }
    }

    private void parseFile(File translationFile) throws IOException {
        String languageKey = translationFile.getName().replace(".json", "");
        String json = this.readFromFile(translationFile);

        JSONObject object = new JSONObject(json);
        Map<String, Object> rawMap = object.toMap();
        for (Map.Entry<String, Object> topLevelSet : rawMap.entrySet()) {
            if (topLevelSet.getValue() instanceof String) {
                this.insertSet(languageKey, topLevelSet.getKey(), (String) topLevelSet.getValue());
            } else if (topLevelSet.getValue() instanceof HashMap) {
                this.processTranslations(languageKey, topLevelSet.getKey(), (HashMap<String, Object>) topLevelSet.getValue());
            }
        }
    }

    private void processTranslations(String languageKey, String key, HashMap<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String translationKey = key + "." + entry.getKey();

            if (entry.getValue() instanceof String) {
                this.insertSet(languageKey, translationKey, (String) entry.getValue());
            } else if (entry.getValue() instanceof HashMap) {
                this.processTranslations(languageKey, translationKey, (HashMap<String, Object>) entry.getValue());
            }
        }
    }

    private void insertSet(String language, String key, String value) {
        this.translationStore.add(language, key, value);
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
