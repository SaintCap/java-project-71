package hexlet.code.gendiff.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class YAMLFileMapper  implements Mapper {

    /**
     * Checks if this parser supports the given file extension.
     * The parser specifically supports JSON files.
     *
     * @param ext the file extension to check (without the dot, e.g. "json", "yml", "yaml")
     * @return true if the extension is "json", false otherwise
     */
    @Override
    public boolean supports(String ext) {
        return "yaml".equals(ext) || "yml".equals(ext);
    }

    /**
     * Parses a JSON file and converts it into a Map representation.
     * Reads the entire file content as string and uses Jackson ObjectMapper
     * to deserialize JSON into a Map of String keys and Object values.
     *
     * @param file the path to the JSON file to parse
     * @return a Map containing the parsed JSON data where keys are strings
     *         and values can be various JSON types (String, Number, Boolean, Array, Object)
     * @throws IOException if an I/O error occurs reading the file
     * @see ObjectMapper
     * @see TypeReference
     */
    @Override
    public Map<String, Object> parse(Path file) throws IOException {
        var content = Files.readString(file);

        ObjectMapper mapper = new YAMLMapper();
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }
}
