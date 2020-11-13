package net.vindalia.jsontranslate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class JsonTranslateTest {

    @Test
    public void testJsonTranslateConstructor() {
        File translationFile = new File("src/test/resources/i18n/de.json");
        Assertions.assertDoesNotThrow(() -> {
            new JsonTranslate(translationFile);
        });
    }

    @Test
    public void testItTranslatesRootLevel() {
        JsonTranslate jsonTranslate = this.get();
        String translation = jsonTranslate.translate("de", "ROOT_LEVEL");
        Assertions.assertEquals("Haupt Level", translation);
    }

    @Test
    public void testItTranslatesFirstLevel() {
        JsonTranslate jsonTranslate = this.get();
        String translation = jsonTranslate.translate("de", "FIRST_LEVEL.EXAMPLE");
        Assertions.assertEquals("Beispiel auf erster Ebene", translation);
    }

    @Test
    public void testItTranslatesSecondLevel() {
        JsonTranslate jsonTranslate = this.get();
        String translation = jsonTranslate.translate("de", "FIRST_LEVEL.SECOND_LEVEL.EXAMPLE");
        Assertions.assertEquals("Beispiel auf zweiter Ebene", translation);
    }

    private JsonTranslate get() {
        File translationFile = new File("src/test/resources/i18n/de.json");
        JsonTranslate jsonTranslate = null;
        try {
            jsonTranslate = new JsonTranslate(translationFile);
        } catch (IOException e) {
            Assertions.fail("Could not create JsonTranslate", e);
        }
        return jsonTranslate;
    }
}
