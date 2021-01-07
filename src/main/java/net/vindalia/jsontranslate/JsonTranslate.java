package net.vindalia.jsontranslate;

import net.vindalia.jsontranslate.parser.JsonParser;
import net.vindalia.jsontranslate.store.TranslationStore;

import java.io.*;

public class JsonTranslate {

    private final TranslationStore translationStore;

    public JsonTranslate(File translationDirectory) throws IOException {
        this.translationStore = new TranslationStore();

        JsonParser parser = new JsonParser(this.translationStore);
        parser.parse(translationDirectory);
    }

    public JsonTranslate(File translationDirectory, boolean debugMode) throws IOException {
        this.translationStore = new TranslationStore(debugMode);

        JsonParser parser = new JsonParser(this.translationStore);
        parser.parse(translationDirectory);
    }

    public String translate(String language, String key) {
        return this.translationStore.get(language, key);
    }
}
