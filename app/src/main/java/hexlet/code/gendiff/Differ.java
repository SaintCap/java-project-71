package hexlet.code.gendiff;

import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Objects;

import hexlet.code.gendiff.utils.FileInfo;
import hexlet.code.gendiff.utils.KeyComparisonResult;

public class Differ {

    public static String generate(String filePath1, String filePath2, String format) throws Exception {

        var fileInfo1 = new FileInfo(filePath1);
        var fileInfo2 = new FileInfo(filePath2);

        var parser = new Parser();

        var data1 = parser.parse(fileInfo1);
        var data2 = parser.parse(fileInfo2);

        var compareResult = compareData(data1, data2);

        return Processor.process(format, compareResult);
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

                if (Objects.equals(val1, val2)) {

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
