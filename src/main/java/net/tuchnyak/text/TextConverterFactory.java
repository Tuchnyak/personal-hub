package net.tuchnyak.text;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class TextConverterFactory {

    private static class MarkdownConverterHolder {
        private static final TextConverter instance = new MarkdownToHtmlConverter();
    }

    public static TextConverter getMarkdownConverter() {
        return MarkdownConverterHolder.instance;
    }
}
