package hexlet.code.gendiff.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class JSONFileMapper implements Mapper {

    @Override
    public boolean supports(String ext) {
        return "json".equals(ext);
    }

    @Override
    public Map<String, Object> parse(Path file) throws IOException {
        var content = Files.readString(file);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }
}
