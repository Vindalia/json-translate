package net.vindalia.jsontranslate;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTranslateTest {

    @Test
    public void testJsonTranslateConstructor() {
        File translationFile = new File("src/test/resources/i18n/");
        assertDoesNotThrow(() -> {
            new JsonTranslate(translationFile);
        });
    }

    @Test
    public void testItTranslatesRootLevel() {
        JsonTranslate jsonTranslate = this.get();
        String translation = jsonTranslate.translate("de", "ROOT_LEVEL");
        assertEquals("Haupt Level", translation);

        translation = jsonTranslate.translate("en", "ROOT_LEVEL");
        assertEquals("Root level", translation);
    }

    @Test
    public void testItTranslatesFirstLevel() {
        JsonTranslate jsonTranslate = this.get();
        String translation = jsonTranslate.translate("de", "FIRST_LEVEL.EXAMPLE");
        assertEquals("Beispiel auf erster Ebene", translation);

        translation = jsonTranslate.translate("en", "FIRST_LEVEL.EXAMPLE");
        assertEquals("Example on first level", translation);
    }

    @Test
    public void testItTranslatesSecondLevel() {
        JsonTranslate jsonTranslate = this.get();
        String translation = jsonTranslate.translate("de", "FIRST_LEVEL.SECOND_LEVEL.EXAMPLE");
        assertEquals("Beispiel auf zweiter Ebene", translation);

        translation = jsonTranslate.translate("en", "FIRST_LEVEL.SECOND_LEVEL.EXAMPLE");
        assertEquals("Example on second level", translation);
    }

    @Test
    public void testItGetsNullIfKeyNotExists() {
        JsonTranslate jsonTranslate = this.get();
        String translation = jsonTranslate.translate("de", "INVALID_KEY.LEVEL");

        assertNull(translation);
    }

    @Test
    public void testItGetsKeyWithDebugModeIfKeyNotExists() {
        JsonTranslate jsonTranslate = this.getWithDebugMode();
        String translation = jsonTranslate.translate("de", "INVALID_KEY.LEVEL");

        assertEquals("INVALID_KEY.LEVEL", translation);
    }

    private JsonTranslate get() {
        File translationFile = new File("src/test/resources/i18n");
        JsonTranslate jsonTranslate = null;
        try {
            jsonTranslate = new JsonTranslate(translationFile);
        } catch (IOException e) {
            fail("Could not create JsonTranslate", e);
        }
        return jsonTranslate;
    }

    private JsonTranslate getWithDebugMode() {
        File translationFile = new File("src/test/resources/i18n");
        JsonTranslate jsonTranslate = null;
        try {
            jsonTranslate = new JsonTranslate(translationFile, true);
        } catch (IOException e) {
            fail("Could not create JsonTranslate", e);
        }
        return jsonTranslate;
    }
}
