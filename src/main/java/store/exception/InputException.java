package store.exception;

public class InputException {

    public static class InvalidYesOrNoException extends IllegalArgumentException {
        public InvalidYesOrNoException() {
            super(ErrorMessages.INVALID_YES_OR_NO.getMessage());
        }
    }

    public static class InvalidInputException extends IllegalArgumentException {
        public InvalidInputException() {
            super(ErrorMessages.INVALID_INPUT.getMessage());
        }
    }

    public static class InvalidFormatException extends IllegalArgumentException {
        public InvalidFormatException() {
            super(ErrorMessages.INVALID_FORMAT.getMessage());
        }
    }

    public static class InvalidOrderQuantityException extends IllegalStateException {
        public InvalidOrderQuantityException() {
            super(ErrorMessages.INVALID_ORDER_QUANTITY.getMessage());
        }
    }

    public static class InvalidProductNameException extends IllegalArgumentException {
        public InvalidProductNameException() {
            super(ErrorMessages.INVALID_PRODUCT_NAME.getMessage());
        }
    }



}

