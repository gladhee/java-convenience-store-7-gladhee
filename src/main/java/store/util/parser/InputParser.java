package store.util.parser;

public class InputParser {

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
