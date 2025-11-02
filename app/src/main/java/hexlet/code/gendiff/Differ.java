package hexlet.code.gendiff;

import java.nio.file.*;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Differ {

    public static String generate(String filePath1, String filePath2) throws Exception {

        var file1 = checkPathToFile(filePath1);
        var file2 = checkPathToFile(filePath2);

        var data1 = getData(Files.readString(file1));
        var data2 = getData(Files.readString(file2));

        return "Nothing";
    }

    private static Path checkPathToFile(String filePath) throws NoSuchFileException {
        var path = Objects.requireNonNull(filePath, "file path cannot be null");
        var pathToFile = Paths.get(path);
        if (!pathToFile.toFile().exists()) {
            throw new NoSuchFileException("File not found: " + pathToFile.toAbsolutePath());
        }
        return pathToFile.toAbsolutePath();
    }

    private static Map<String, Object> getData(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() {});
    }
}
