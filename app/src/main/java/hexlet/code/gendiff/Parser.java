package hexlet.code.gendiff;

import hexlet.code.gendiff.mappers.JSONFileMapper;
import hexlet.code.gendiff.mappers.Mapper;
import hexlet.code.gendiff.mappers.YAMLFileMapper;
import hexlet.code.gendiff.utils.FileInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Parser {
    private final List<Mapper> parsers  = new ArrayList<>();

    public Parser() {
        fileParserRegistry();
    }

    public Map<String, Object> parse(FileInfo fileInfo) throws IOException {
        Mapper mapper = getParser(fileInfo.getExt());
        return mapper.parse(fileInfo.getFilePath());
    }

    private Mapper getParser(String extension) throws IOException {
        return parsers.stream()
                .filter(parser -> parser.supports(extension))
                .findFirst()
                .orElseThrow(() -> new IOException(
                        "Unsupported file format: " + extension));
    }

    private void fileParserRegistry() {
        parsers.add(new JSONFileMapper());
        parsers.add(new YAMLFileMapper());
    }

}
