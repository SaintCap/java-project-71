package hexlet.code.gendiff.formatters;

import hexlet.code.gendiff.utils.KeyComparisonResult;

import java.util.List;

public final class Plain implements Formatter {

    @Override
    public String format(List<KeyComparisonResult> diff) {

        var result = new StringBuilder();

        result.append("\n");
        for (KeyComparisonResult compResult : diff) {
            if (compResult.isUnchanged()) {
                continue;
            }

            var basicString = keyFormatString(compResult.getKey());
            var defaultValue = compResult.getDefaultValue();
            var newValue = compResult.getNewValue();

            if (compResult.isUpdate()) {
                var resultString = updatedFormatString(basicString, defaultValue, newValue);
                result.append(resultString).append("\n");
                continue;
            }

            if (compResult.isAdd()) {
                result.append(addFormatString(basicString, newValue)).append("\n");
                continue;
            }

            if (compResult.isDelete()) {
                result.append(removeFormatString(basicString)).append("\n");
            }
        }

        return result.toString().trim();
    }

    private String keyFormatString(String key) {
        return String.format("Property '%s' was", key);
    }

    private String updatedFormatString(String basicString, Object val1, Object val2) {
        var formVal1 = formatValue(val1);
        var formVal2 = formatValue(val2);

        return String.format("%s updated. From %s to %s", basicString, formVal1, formVal2);
    }

    private String addFormatString(String basicString, Object val) {
        var formVal = formatValue(val);

        return String.format("%s added with value: %s", basicString, formVal);
    }

    private String removeFormatString(String basicString) {
        return String.format("%s removed", basicString);
    }

    private  String formatValue(Object val) {
        if (val instanceof String) {
            return String.format("'%s'", val);
        }

        if (val instanceof Number || val instanceof Boolean || val == null) {
            return String.format("%s", val);
        }

        return "[complex value]";
    }

}
