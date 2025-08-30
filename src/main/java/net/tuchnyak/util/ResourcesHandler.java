package net.tuchnyak.util;

import rife.resources.ResourceFinderDirectories;
import rife.resources.exceptions.ResourceFinderErrorException;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ResourcesHandler implements Logging {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    private final ResourceFinderDirectories resourceFinder;

    private ResourcesHandler() {
        resourceFinder = new ResourceFinderDirectories(
                new File("src/main/resources")
        );
    }

    private static class SingletonHolder {
        private static final ResourcesHandler INSTANCE = new ResourcesHandler();
    }

    public static ResourcesHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Optional<String> getFileContent(String filePath) {
        try {
            var url = resourceFinder.getResource(filePath);
            return Optional.ofNullable(resourceFinder.getContent(url, UTF_8.displayName()));
        } catch (Exception e) {
            getLogger().warn(">>> Error getting resource: [%s]".formatted(filePath), e);
            return Optional.empty();
        }
    }

}
