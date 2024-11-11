package store.exception;

public enum ErrorMessages {

    INVALID_YES_OR_NO("Y 또는 N을 입력해 주세요."),
    PRODUCT_NAME_SHOULD_NOT_BE_EMPTY("상품명은 빈 값일 수 없습니다."),
    EMPTY_PROMOTION_NAME("프로모션명은 빈 값일 수 없습니다."),
    QUANTITY_SHOULD_BE_POSITIVE("필요 수량은 0보다 커야 합니다."),
    PRICE_SHOULD_BE_POSITIVE("상품 가격은 0보다 커야 합니다."),
    START_DATE_SHOULD_BE_EARLIER_THAN_END_DATE("시작일이 종료일보다 늦을 수 없습니다."),
    OUT_OF_STOCK("재고가 부족합니다."),
    ORDER_QUANTITY_SHOULD_BE_POSITIVE("주문 수량은 0보다 커야 합니다."),
    DOES_NOT_EXIST_PRODUCT("존재하지 않는 상품입니다."),
    DOES_NOT_EXIST_PROMOTION("존재하지 않는 프로모션입니다."),
    ORDER_IS_CANCELED("주문이 취소되었습니다."),
    ORDER_ITEM_SHOULD_BE_REQUIRED("주문 항목은 필수입니다."),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다"),
    FILE_CANNOT_READ("파일을 읽을 수 없습니다"),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_ORDER_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_PRODUCT_NAME("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    DATE_IS_REQUIRED("날짜는 필수 값입니다."),
    DATE_IS_NOT_VALID_FORMAT("날짜 형식이 올바르지 않습니다.");

    private String message = "[ERROR] ";

    ErrorMessages(String message) {
        this.message += message;
    }

    public String getMessage() {
        return message;
    }

}
