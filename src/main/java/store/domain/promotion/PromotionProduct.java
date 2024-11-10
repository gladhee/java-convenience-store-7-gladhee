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

    public boolean decrease(int quantity) {
        if (this.quantity < quantity) {
            return false;
        }

        this.quantity -= quantity;

        return true;
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

    public int getQuantity() {
        return quantity;
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
