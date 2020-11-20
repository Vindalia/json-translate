package net.vindalia.jsontranslate.store;

import java.util.HashMap;

public class TranslationStore {

    private final HashMap<String, HashMap<String, String>> translationMap;

    public TranslationStore() {
        this.translationMap = new HashMap<>();
    }

    public String get(String language, String key) {
        return this.translationMap.get(key).get(language);
    }

    public void add(String language, String key, String value) {
        if (this.translationMap.containsKey(key)) {
            this.translationMap.get(key).put(language, value);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(language, value);
            this.translationMap.put(key, map);
        }
    }
}
