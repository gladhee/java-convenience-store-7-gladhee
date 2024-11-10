package store.domain.promotion;

import java.time.LocalDate;

public class PromotionProduct {

    private final String name;
    private int quantity;
    private final PromotionRule rule;

    private PromotionProduct(Builder builder) {
        this.name = builder.name;
        this.quantity = builder.quantity;
        this.rule = builder.rule;
        validatePromotionProduct();
    }

    public static Builder builder() {
        return new Builder();
    }

    public synchronized int decrease(int requestedQuantity) {
        validateDecrease(requestedQuantity);
        quantity -= requestedQuantity;

        return quantity;
    }

    public synchronized boolean attemptPromotionPurchase(int tryAmount, LocalDate purchaseDate) {
        if (!rule.isApplicable(purchaseDate)) {
            return false;
        }

        int requiredPromotionQuantity = calculateRequiredPromotionQuantity(tryAmount);

        return quantity >= requiredPromotionQuantity;
    }

    private int calculateRequiredPromotionQuantity(int requestedQuantity) {
        int freeItems = rule.calculateFreeItems(requestedQuantity);

        return requestedQuantity + freeItems;
    }

    private void validatePromotionProduct() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 상품명은 빈 값일 수 없습니다.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("[ERROR] 상품 수량은 음수일 수 없습니다.");
        }
    }

    private void validateDecrease(int requestedQuantity) {
        if (requestedQuantity < 0) {
            throw new IllegalArgumentException("[ERROR] 감소할 수량은 음수일 수 없습니다.");
        }
        if (quantity < requestedQuantity) {
            throw new IllegalArgumentException("[ERROR] 재고가 부족합니다.");
        }
    }

    public static class Builder {

        private String name;
        private int quantity;
        private PromotionRule rule;

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;

            return this;
        }

        public Builder rule(PromotionRule rule) {
            this.rule = rule;

            return this;
        }

        public PromotionProduct build() {
            return new PromotionProduct(this);
        }

    }

}
