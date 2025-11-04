package hexlet.code.gendiff.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.gendiff.utils.KeyComparisonResult;

import java.util.List;

public class Json implements Formatter {

    @Override
    public String format(List<KeyComparisonResult> diff) {

        ObjectMapper objectMapper = new ObjectMapper();

        var result = "";

        try {
            result = objectMapper.writeValueAsString(diff);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;

    }

}
