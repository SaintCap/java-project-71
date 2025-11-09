package hexlet.code.formatters;

import hexlet.code.utils.KeyComparisonResult;

import java.util.List;

/**
 * Interface for formatting comparison results into various output formats.
 * Implementations of this interface convert a list of key comparison results
 * into a specific string representation (e.g., JSON, XML, YAML, plain text).
 */
public interface Formatter {
    /**
     * Converts a list of key comparison results into a formatted string representation.
     *
     * @param diff the list of key comparison results to format
     * @return a string representing the comparison results in the specific format
     */
    String format(List<KeyComparisonResult> diff);
}
