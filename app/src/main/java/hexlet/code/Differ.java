package hexlet.code;

import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Objects;

import hexlet.code.gendiff.Parser;
import hexlet.code.gendiff.Processor;
import hexlet.code.gendiff.utils.FileInfo;
import hexlet.code.gendiff.utils.KeyComparisonResult;

public class Differ {
    private static final String FORMAT = "stylish";

    /**
     * Generates a formatted comparison report by processing and comparing data from two files.
     * The method reads the files, parses their contents, performs a comparison, and returns
     * the result in the specified format.
     *
     * @param filePath1 the path to the first file for comparison
     * @param filePath2 the path to the second file for comparison
     * @param format the desired output format for the comparison result (e.g., "json", "stylish", "plain")
     * @return a string containing the comparison result in the requested format
     * @throws Exception if file reading, parsing, or processing fails
     * @see Parser
     * @see Processor
     */
    public static String generate(String filePath1, String filePath2, String format) throws Exception {

        var fileInfo1 = new FileInfo(filePath1);
        var fileInfo2 = new FileInfo(filePath2);

        var parser = new Parser();

        var data1 = parser.parse(fileInfo1);
        var data2 = parser.parse(fileInfo2);

        var compareResult = compareData(data1, data2);

        return Processor.process(format, compareResult);
    }

    /**
     * Generates a formatted comparison report by processing and comparing data from two files.
     * The method reads the files, parses their contents, performs a comparison, and returns
     * the result in the specified format.
     *
     * @param filePath1 the path to the first file for comparison
     * @param filePath2 the path to the second file for comparison
     * @return a string containing the comparison result in the requested format
     * @throws Exception if file reading, parsing, or processing fails
     * @see Parser
     * @see Processor
     */
    public static String generate(String filePath1, String filePath2) throws Exception {

        var fileInfo1 = new FileInfo(filePath1);
        var fileInfo2 = new FileInfo(filePath2);

        var parser = new Parser();

        var data1 = parser.parse(fileInfo1);
        var data2 = parser.parse(fileInfo2);

        var compareResult = compareData(data1, data2);

        return Processor.process(FORMAT, compareResult);
    }

    private static List<KeyComparisonResult> compareData(Map<String, Object> data1, Map<String, Object> data2) {
        var keys = new HashSet<>(data1.keySet());
        keys.addAll(data2.keySet());

        var keysList = keys.stream()
                            .sorted()
                            .toList();

        List<KeyComparisonResult> result = new ArrayList<>();

        for (String key : keysList) {
            var inFirst = data1.containsKey(key);
            var inSecond = data2.containsKey(key);

            var val1 = data1.get(key);
            var val2 = data2.get(key);

            KeyComparisonResult compResult = new KeyComparisonResult();

            if (inFirst && inSecond) {

                if (Objects.deepEquals(val1, val2)) {

                    compResult.setIsUnchanged(key, val1);

                } else {

                    compResult.setIsUpdate(key, val1, val2);

                }

            } else if (!inSecond) {

                compResult.setIsDelete(key, val1);

            } else {

                compResult.setIsAdd(key, val2);

            }

            result.add(compResult);

        }

        return result;
    }

}
