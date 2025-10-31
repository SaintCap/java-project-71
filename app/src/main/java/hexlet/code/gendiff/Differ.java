package hexlet.code.gendiff;

import java.util.Objects;

public class Differ {

    public static String generate(String fileFirst, String fileSecond) {
        var file1 = Objects.requireNonNull(fileFirst, "fileFirst cannot be null");
        var file2 = Objects.requireNonNull(fileSecond, "fileSecond cannot be null");
        return "Nothing";
    }

}
