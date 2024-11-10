package store.util.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {

    private static final String RESOURCE_PATH = "src/main/resources/";

    public static List<String> readLines(String filename) {
        try {
            Path path = Paths.get(RESOURCE_PATH + filename);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 파일을 읽을 수 없습니다.", e);
        }
    }

}
