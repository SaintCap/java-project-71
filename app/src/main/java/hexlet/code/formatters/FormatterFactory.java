package hexlet.code.formatters;

public class FormatterFactory {
    public static Formatter create(String format) {
        return switch (format.toLowerCase()) {
            case "stylish" -> new Stylish();
            case "plain" -> new Plain();
            case "json" -> new Json();
            default -> throw new IllegalArgumentException("Unknown formatter type: " + format);
        };
    }
}
