package net.tuchnyak.text;

import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class MarkdownToHtmlConverter implements TextConverter {

    private final Parser parser;
    private final HtmlRenderer renderer;

    MarkdownToHtmlConverter() {
        var options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                AutolinkExtension.create(),
                TaskListExtension.create(),
                YamlFrontMatterExtension.create(),
                AdmonitionExtension.create(),
                AttributesExtension.create(),
                FootnoteExtension.create()
        ));
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options)
                .build();
    }

    @Override
    public ParsedInfo convert(String sourceText) {
        if (sourceText == null || sourceText.trim().isBlank()) {
            return new ParsedInfo();
        }

        var document = parser.parse(sourceText);
        var outputData = renderer.render(document);
        var yamlDataMap = getYamlBlockInfo(document);

        return new ParsedInfo(
                Optional.of(outputData),
                Optional.ofNullable(yamlDataMap)
        );
    }

    Map<String, List<String>> getYamlBlockInfo(Document document) {
        try {
            YamlFrontMatterBlock yamlBlock = (YamlFrontMatterBlock) document.getFirstChild();
            AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor() {
            };
            visitor.visit(yamlBlock);

            return visitor.getData();
        } catch (Exception e) {

            return null;
        }
    }

}
