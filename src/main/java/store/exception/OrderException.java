package store.exception;

public class OrderException {

    public static class OutOfStockException extends IllegalStateException {
        public OutOfStockException() {
            super(ErrorMessages.OUT_OF_STOCK.getMessage());
        }
    }

    public static class OrderQuantityShouldBePositiveException extends IllegalArgumentException {
        public OrderQuantityShouldBePositiveException() {
            super(ErrorMessages.ORDER_QUANTITY_SHOULD_BE_POSITIVE.getMessage());
        }
    }

    public static class OrderItemShouldBeRequiredException extends IllegalArgumentException {
        public OrderItemShouldBeRequiredException() {
            super(ErrorMessages.ORDER_ITEM_SHOULD_BE_REQUIRED.getMessage());
        }
    }

    public static class OrderIsCanceledException extends IllegalStateException {
        public OrderIsCanceledException() {
            super(ErrorMessages.ORDER_IS_CANCELED.getMessage());
        }
    }

}
