package store.exception;

public class ParserException {

    public static class DateIsNotValidException extends IllegalArgumentException {
        public DateIsNotValidException() {
            super(ErrorMessages.DATE_IS_REQUIRED.getMessage());
        }
    }

    public static class DateIsNotValidFormatException extends IllegalArgumentException {
        public DateIsNotValidFormatException() {
            super(ErrorMessages.DATE_IS_NOT_VALID_FORMAT.getMessage());
        }
    }

}
