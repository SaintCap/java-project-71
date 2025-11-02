package hexlet.code.gendiff;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Objects;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {

    public static String generate(String filePath1, String filePath2) throws Exception {

        var file1 = checkPathToFile(filePath1);
        var file2 = checkPathToFile(filePath2);

        var data1 = getData(Files.readString(file1));
        var data2 = getData(Files.readString(file2));

        return compareData(data1, data2);
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

    private static String compareData(Map<String, Object> data1, Map<String, Object> data2) {
        var keys = new HashSet<>(data1.keySet());
        keys.addAll(data2.keySet());

        var keysList = keys.stream()
                            .sorted()
                            .toList();

        var resultList = new ArrayList<String>();
        for (String key : keysList) {

            if (data1.containsKey(key) && data2.containsKey(key)) {
                var val1 = data1.get(key);
                var val2 = data2.get(key);

                if (val1.equals(val2)) {
                    resultList.add(String.format("    %s: %s", key, val1));
                    continue;
                }

                resultList.add(String.format("  - %s: %s", key, val1));
                resultList.add(String.format("  + %s: %s", key, val2));

            } else if (data1.containsKey(key) && !data2.containsKey(key)) {

                var val1 = data1.get(key);
                resultList.add(String.format("  - %s: %s", key, val1));

            } else if (!data1.containsKey(key) && data2.containsKey(key)) {

                var val2 = data2.get(key);
                resultList.add(String.format("  + %s: %s", key, val2));

            }

        }

        return resultCompareToString(resultList);
    }

    private static String resultCompareToString(List<String> resultCompare) {
        StringBuilder result = new StringBuilder();

        for (String element : resultCompare) {
            result.append(element).append("\n");
        }

        result.insert(0,"{\n");
        result.append("}");

        return result.toString();
    }
}
