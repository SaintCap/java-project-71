package hexlet.code.gendiff;

import hexlet.code.gendiff.mappers.JSONFileMapper;
import hexlet.code.gendiff.mappers.Mapper;
import hexlet.code.gendiff.mappers.YAMLFileMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    private final List<Mapper> parsers  = new ArrayList<>();

    Parser() {
        FileParserRegistry();
    }

    public Map<String, Object> parse(Path file, String extension) throws IOException {
        Mapper mapper = getParser(extension);
        return mapper.parse(file);
    }

    private Mapper getParser(String extension) throws IOException {
        return parsers.stream()
                .filter(parser -> parser.supports(extension))
                .findFirst()
                .orElseThrow(() -> new IOException(
                        "Unsupported file format: " + extension));
    }

    private void FileParserRegistry() {
        parsers.add(new JSONFileMapper());
        parsers.add(new YAMLFileMapper());
    }

}
