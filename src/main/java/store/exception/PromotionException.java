package store.exception;

public class PromotionException {

    public static class EmptyPromotionNameException extends IllegalArgumentException {
        public EmptyPromotionNameException() {
            super(ErrorMessages.EMPTY_PROMOTION_NAME.getMessage());
        }
    }

    public static class QuantityShouldBePositiveException extends IllegalArgumentException {
        public QuantityShouldBePositiveException() {
            super(ErrorMessages.QUANTITY_SHOULD_BE_POSITIVE.getMessage());
        }
    }

    public static class StartDateShouldBeEarlierThanEndDateException extends IllegalArgumentException {
        public StartDateShouldBeEarlierThanEndDateException() {
            super(ErrorMessages.START_DATE_SHOULD_BE_EARLIER_THAN_END_DATE.getMessage());
        }
    }

    public static class DoesNotExistPromotionException extends IllegalArgumentException {
        public DoesNotExistPromotionException() {
            super(ErrorMessages.DOES_NOT_EXIST_PROMOTION.getMessage());
        }
    }

}
