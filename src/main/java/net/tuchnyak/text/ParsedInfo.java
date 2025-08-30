package net.tuchnyak.text;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author tuchnyak (George Shchennikov)
 */
public record ParsedInfo(
        Optional<String> outputData,
        Optional<Map<String, List<String>>> yamlDataMap
) {
    public ParsedInfo() {
        this(
                Optional.empty(),
                Optional.empty()
        );
    }

    public String getTitle() {
        return getSingleParameter("title");
    }

    public String getSlug() {
        return getSingleParameter("slug");
    }

    public String getSingleParameter(String name) {

        return yamlDataMap()
                .map(map -> map.get(name))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .orElse("");
    }

}
