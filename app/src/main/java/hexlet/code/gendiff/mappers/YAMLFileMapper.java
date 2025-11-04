package hexlet.code.gendiff.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class YAMLFileMapper  implements Mapper {

    @Override
    public boolean supports(String ext) {
        return "yaml".equals(ext);
    }

    @Override
    public Map<String, Object> parse(Path file) throws IOException {
        var content = Files.readString(file);

        ObjectMapper mapper = new YAMLMapper();
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() {});
    }
}
