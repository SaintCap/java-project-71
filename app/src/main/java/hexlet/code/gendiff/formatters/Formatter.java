package hexlet.code.gendiff.formatters;

import hexlet.code.gendiff.utils.KeyComparisonResult;

import java.util.List;

public interface Formatter {
    String format(List<KeyComparisonResult> diff);
}
