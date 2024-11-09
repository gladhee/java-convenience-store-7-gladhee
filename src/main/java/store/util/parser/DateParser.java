package store.util.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static LocalDate parse(String dateStr) {
        validateNotEmpty(dateStr);
        return parseToLocalDate(dateStr);
    }

    private static void validateNotEmpty(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 날짜는 필수 값입니다.");
        }
    }

    private static LocalDate parseToLocalDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 올바르지 않습니다.");
        }
    }

}