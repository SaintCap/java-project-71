package hexlet.code.gendiff;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.HashSet;

import org.apache.commons.io.FilenameUtils;

public class Differ {

    private record FileInfo(Path absolutePath, String ext) {
    }

    public static String generate(String filePath1, String filePath2) throws Exception {

        var file1 = getFileInfo(filePath1);
        var file2 = getFileInfo(filePath2);

        var parser = new Parser();

        var data1 = parser.parse(file1.absolutePath(),file1.ext());
        var data2 = parser.parse(file2.absolutePath(),file2.ext());

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
