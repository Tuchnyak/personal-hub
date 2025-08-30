package net.tuchnyak.text;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tuchnyak (George Shchennikov)
 */
class MarkdownToHtmlConverterTest {

    private final TextConverter underTest = TextConverterFactory.getMarkdownConverter();

    @ParameterizedTest
    @CsvSource({
            "# Заголовок, <h1>Заголовок</h1>",
            "**жирный текст**, <p><strong>жирный текст</strong></p>",
            "*курсив*, <p><em>курсив</em></p>",
            "*курсив*{.highlight}, <p><em class=\"highlight\">курсив</em></p>",
            "~~strikethrough~~, <p><del>strikethrough</del></p>",
            "some text https://google.com and more, <p>some text <a href=\"https://google.com\">https://google.com</a> and more</p>",
            "Some [TEST](https://google.com) and more, <p>Some <a href=\"https://google.com\">TEST</a> and more</p>",
            "- Элемент 1, <ul><li>Элемент 1</li></ul>",
            "* Элемент 1, <ul><li>Элемент 1</li></ul>"
    })
    void convert(String markdown, String expectedHtml) {
        String actualHtml = underTest.convert(markdown).outputData().orElse("").trim().replaceAll("\n", "");
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void getYamlBlockInfo() {
        var actualParsed = underTest.convert(getFullMdText());
        var actualMap = actualParsed.yamlDataMap().orElse(null);

        assertNotNull(actualMap);
        assertEquals(3, actualMap.size());
        assertEquals("Hello world!", actualMap.get("title").get(0));
        assertEquals("java", actualMap.get("tags").get(0));
        assertEquals("spring", actualMap.get("tags").get(1));
        assertEquals("backend", actualMap.get("categories").get(0));
        assertEquals("tutorial", actualMap.get("categories").get(1));
        assertFalse(actualParsed.outputData().orElse("").contains("Hello"));
    }

    private String getFullMdText() {
        return """
                ---
                title: Hello world!
                tags:
                  - java
                  - spring
                categories:
                  - backend
                  - tutorial
                ---
                # Test
                Simple text.
                
                Another row.
                
                ## Sub 2
                Book ref[^1]. More text.
                
                New row.
                
                ### Sub 3
                !!! note "Alert!"
                Be careful with JS!
                
                [^1]: How to
                """.indent(0);
    }
}