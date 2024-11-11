package store.util.parser;

import store.exception.InputException.InvalidInputException;

public class InputParser {

    public static Integer convertToInt(String value) {
        try {
            if (value == null) {
                return null;
            }
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
    }

}
