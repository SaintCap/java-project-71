package hexlet.code.formatters;

import hexlet.code.utils.KeyComparisonResult;

import java.util.List;

public final class Stylish implements Formatter {
    private static final String UNCHANGED = " ";
    private static final String ADD = "+";
    private static final String DELETE = "-";

    @Override
    public String format(List<KeyComparisonResult> diff) {

        var result = new StringBuilder();

        result.append("{\n");
        for (KeyComparisonResult compResult : diff) {
            var key = compResult.getKey();

            if (compResult.isUnchanged()) {
                result.append(formatString(UNCHANGED, key, compResult.getDefaultValue())).append("\n");
                continue;
            }

            if (compResult.isUpdate()) {
                result.append(formatString(DELETE, key, compResult.getDefaultValue())).append("\n");
                result.append(formatString(ADD, key, compResult.getNewValue())).append("\n");
                continue;
            }

            if (compResult.isAdd()) {
                result.append(formatString(ADD, key, compResult.getNewValue())).append("\n");
                continue;
            }

            if (compResult.isDelete()) {
                result.append(formatString(DELETE, key, compResult.getDefaultValue())).append("\n");
            }
        }
        result.append("}");

        return result.toString().trim();
    }

    private String formatString(String changesIndication, String key, Object val) {
        return String.format("  %s %s: %s", changesIndication, key, val);
    }

}
