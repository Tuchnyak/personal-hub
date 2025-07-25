package net.tuchnyak.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReaderUtil {

    public String readFilePathToString(Path path) throws IOException {

        return new String(Files.readAllBytes(path));
    }

}
