package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.utils.KeyComparisonResult;

import java.util.List;

public final class Json implements Formatter {

    @Override
    public String format(List<KeyComparisonResult> diff) {

        ObjectMapper objectMapper = new ObjectMapper();

        var result = "";

        try {
            result = objectMapper.writeValueAsString(diff).trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;

    }

}
