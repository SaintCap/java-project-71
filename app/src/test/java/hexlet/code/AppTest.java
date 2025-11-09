package hexlet.code;

import hexlet.code.mappers.JSONFileMapper;
import hexlet.code.mappers.Mapper;
import hexlet.code.mappers.YAMLFileMapper;
import hexlet.code.utils.FileInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class AppTest {
    public static final int TIMEOUT = 50;
    public static final String FILE1 = "src/test/resources/file1.json";
    public static final String FILE2 = "src/test/resources/file2.json";
    public static final String FILE3 = "src/test/resources/file3.yaml";
    public static final String FILE4 = "src/test/resources/file4.yaml";
    public static final String FILE5 = "src/test/resources/file5.json";
    public static final String FILE6 = "src/test/resources/file6.json";
    public static final String FILE7 = "src/test/resources/file7.yaml";
    public static final String FILE8 = "src/test/resources/file8.yaml";

    @ParameterizedTest
    @MethodSource("provideTestDataBasic")
    void generateDiffJson(String file1, String file2, String format, String expectedFilePath) throws Exception {
        var actual = Differ.generate(file1, file2, format);
        var expected = getTestString(expectedFilePath);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataBasicWithoutFormat")
    void generateDiffJsonStylishDefault(String file1, String file2, String expectedFilePath) throws Exception {
        var actual = Differ.generate(file1, file2);
        var expected = getTestString(expectedFilePath);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataParser")
    void testParser(String file, Map<String, Object> expect) throws Exception {
        var parser = new Parser();
        var fileInfo = new FileInfo(file);
        var actual = parser.parse(fileInfo);

        assertEquals(expect, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataMapper")
    void testMappers(String file, Mapper mapper, Map<String, Object> expect) throws Exception {
        var fileInfo = new FileInfo(file);
        var actual = mapper.parse(fileInfo.getFilePath());

        assertEquals(expect, actual);
    }

    private static Stream<Arguments> provideTestDataBasic() throws IOException {
        return Stream.of(
                Arguments.of(FILE1,
                        FILE2,
                        "stylish",
                        "src/test/resources/expected_basic_stylish"
                ),
                Arguments.of(FILE5,
                        FILE6,
                        "plain",
                        "src/test/resources/expected_additionally_plain"
                ),
                Arguments.of(FILE1,
                        FILE2,
                        "json",
                        "src/test/resources/expected_basic_json"
                ),
                Arguments.of(FILE7,
                        FILE8,
                        "plain",
                        "src/test/resources/expected_additionally_yaml_plain"
                ),
                Arguments.of(FILE5,
                        FILE6,
                        "stylish",
                        "src/test/resources/expected_additionally_stylish"
                ),
                Arguments.of(FILE3,
                        FILE4,
                        "stylish",
                        "src/test/resources/expected_basic_stylish"
                ),
                Arguments.of(FILE7,
                        FILE8,
                        "stylish",
                        "src/test/resources/expected_additionally_yaml_stylish"
                ),
                Arguments.of(FILE3,
                        FILE4,
                        "json",
                        "src/test/resources/expected_basic_json"
                )
        );
    }

    private static Stream<Arguments> provideTestDataBasicWithoutFormat() throws IOException {
        return Stream.of(
                Arguments.of(FILE1,
                        FILE2,
                        "src/test/resources/expected_basic_stylish"
                )
        );
    }

    private static Stream<Arguments> provideTestDataParser() throws IOException {
        return Stream.of(
                Arguments.of(FILE1,
                        testParseResult()
                ),
                Arguments.of(FILE3,
                        testParseResult()
                )
        );
    }

    private static Stream<Arguments> provideTestDataMapper() throws IOException {
        return Stream.of(
                Arguments.of(FILE1,
                        new JSONFileMapper(),
                        testParseResult()
                ),
                Arguments.of(FILE3,
                        new YAMLFileMapper(),
                        testParseResult()
                )
        );
    }

    private static Map<String, Object> testParseResult() {
        var expect = new HashMap<String, Object>();

        expect.put("host", "hexlet.io");
        expect.put("timeout", TIMEOUT);
        expect.put("proxy", "123.234.53.22");
        expect.put("follow", false);

        return expect;
    }

    private static String getTestString(String path) throws IOException {
        return Files.readString(Path.of(path)).trim().replace("\r", "");
    }
}
