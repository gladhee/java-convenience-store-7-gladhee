package store.exception;

public class ProductException {

    public static class EmptyProductNameException extends IllegalArgumentException {
        public EmptyProductNameException() {
            super(ErrorMessages.PRODUCT_NAME_SHOULD_NOT_BE_EMPTY.getMessage());
        }
    }

    public static class PriceShouldBePositiveException extends IllegalArgumentException {
        public PriceShouldBePositiveException() {
            super(ErrorMessages.PRICE_SHOULD_BE_POSITIVE.getMessage());
        }
    }

    public static class DoesNotExistProductException extends IllegalArgumentException {
        public DoesNotExistProductException() {
            super(ErrorMessages.DOES_NOT_EXIST_PRODUCT.getMessage());
        }
    }

}
