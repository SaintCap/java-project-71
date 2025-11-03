package hexlet.code.gendiff.mappers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface Mapper {
    boolean supports(String ext);
    Map<String, Object> parse(Path file) throws IOException;
}
