package hexlet.code;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Parser {

    public static ObjectMapper getMapper(String extension) {
        return switch (extension) {
                case "json" -> new ObjectMapper();
                case "yaml", "yml" -> new YAMLMapper();
                default -> throw new RuntimeException("Unsupported file extension: " + extension);
        };
    }

}
