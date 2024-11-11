package store.util.parser;

import java.util.Map;

public class InputParser {

    public static Map<String, Integer> parseItems(String input) {
        return null;
    }

    public static Integer convertToInt(String value) {
        try {
            if (value == null) {
                return null;
            }
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자로 변환할 수 없는 값입니다.");
        }
    }

}
