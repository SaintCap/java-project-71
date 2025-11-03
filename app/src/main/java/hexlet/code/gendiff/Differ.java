package hexlet.code.gendiff;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.HashSet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.commons.io.FilenameUtils;

public class Differ {

    private record FileInfo(Path absolutePath, String ext) {
    }

    public static String generate(String filePath1, String filePath2) throws Exception {

        var file1 = getFileInfo(filePath1);
        var file2 = getFileInfo(filePath2);

        var data1 = getData(file1);
        var data2 = getData(file2);

        return compareData(data1, data2);
    }

    private static FileInfo getFileInfo(String filePath) throws NoSuchFileException {
        var path = Paths.get(filePath);
        var file = path.toFile();
        if (!file.exists()) {
            throw new NoSuchFileException("File not found: " + path.toAbsolutePath());
        }

        return new FileInfo(path.toAbsolutePath(), FilenameUtils.getExtension(file.getName()));
    }

    private static Map<String, Object> getData(FileInfo file) throws Exception {
        var content = Files.readString(file.absolutePath());
        var ext = file.ext();

        if (ext.equals("json")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<Map<String, Object>>() {});
        }

        if (ext.equals("yaml")) {
            ObjectMapper mapper = new YAMLMapper();
            return mapper.readValue(content, new TypeReference<Map<String, Object>>() {});
        }

        return Map.of();
    }

    private static String compareData(Map<String, Object> data1, Map<String, Object> data2) {
        var keys = new HashSet<>(data1.keySet());
        keys.addAll(data2.keySet());

        var keysList = keys.stream()
                            .sorted()
                            .toList();

        StringBuilder result = new StringBuilder();

        result.append("{\n");
        for (String key : keysList) {
            if (data1.containsKey(key) && data2.containsKey(key)) {
                var val1 = data1.get(key);
                var val2 = data2.get(key);

                if (val1.equals(val2)) {
                    result.append(String.format("    %s: %s", key, val1)).append("\n");
                    continue;
                }

                result.append(String.format("  - %s: %s", key, val1)).append("\n");
                result.append(String.format("  + %s: %s", key, val2)).append("\n");

            } else if (data1.containsKey(key) && !data2.containsKey(key)) {

                var val1 = data1.get(key);
                result.append(String.format("  - %s: %s", key, val1)).append("\n");

            } else if (!data1.containsKey(key) && data2.containsKey(key)) {

                var val2 = data2.get(key);
                result.append(String.format("  + %s: %s", key, val2)).append("\n");

            }
        }
        result.append("}");

        return result.toString();
    }
}
