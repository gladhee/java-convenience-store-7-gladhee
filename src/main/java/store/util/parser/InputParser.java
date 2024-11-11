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
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

}
