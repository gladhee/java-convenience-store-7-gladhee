package store.util.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import store.exception.FileException.FileCannotReadException;
import store.exception.FileException.FileNotFoundException;

public class FileReader {

    public static List<String> readLines(String filename) {
        InputStream inputStream = getInputStream(filename);
        return readLinesFromStream(inputStream, filename);
    }

    private static InputStream getInputStream(String filename) {
        InputStream inputStream = FileReader.class.getClassLoader()
                .getResourceAsStream(filename);
        validateInputStream(inputStream, filename);
        return inputStream;
    }

    private static void validateInputStream(InputStream inputStream, String filename) {
        if (inputStream == null) {
            throw new FileNotFoundException(filename);
        }
    }

    private static List<String> readLinesFromStream(InputStream inputStream, String filename) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileCannotReadException(filename);
        }
    }

}