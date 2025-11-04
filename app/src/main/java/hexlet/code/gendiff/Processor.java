package hexlet.code.gendiff;

import hexlet.code.gendiff.formatters.Formatter;
import hexlet.code.gendiff.formatters.Plain;
import hexlet.code.gendiff.formatters.Stylish;
import hexlet.code.gendiff.utils.KeyComparisonResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {
    private static final Map<String, Formatter> FORMATTERS = new HashMap<>();

    static {
        registerFormatter("stylish", new Stylish());
        registerFormatter("plain", new Plain());
    }

    public static void registerFormatter(String type, Formatter formatter) {
        FORMATTERS.put(type.toLowerCase(), formatter);
    }

    public static String process(String type, List<KeyComparisonResult> diff) {
        Formatter formatter = FORMATTERS.get(type.toLowerCase());
        return formatter.format(diff);
    }
}
