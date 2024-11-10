package store.domain.promotion;

import java.time.LocalDate;
import store.util.parser.DateParser;

public class PromotionRule {

    private final int requiredQuantity;
    private final int freeQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private PromotionRule(Builder builder) {
        this.requiredQuantity = builder.requiredQuantity;
        this.freeQuantity = builder.freeQuantity;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        validate();
    }

    public static Builder builder() {
        return new Builder();
    }

    public int calculateFreeItems(int purchaseQuantity) {
        return (purchaseQuantity / requiredQuantity) * freeQuantity;
    }

    public boolean isApplicable(LocalDate currentDate) {
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    private void validate() {
        if (requiredQuantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 필요 수량은 0보다 커야 합니다.");
        }
        if (freeQuantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 증정 수량은 0보다 커야 합니다.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("[ERROR] 시작일이 종료일보다 늦을 수 없습니다.");
        }
    }

    public static class Builder {

        private int requiredQuantity;
        private int freeQuantity;
        private LocalDate startDate;
        private LocalDate endDate;

        public Builder requiredQuantity(int requiredQuantity) {
            this.requiredQuantity = requiredQuantity;

            return this;
        }

        public Builder freeQuantity(int freeQuantity) {
            this.freeQuantity = freeQuantity;

            return this;
        }

        public Builder startDate(String startDate) {
            this.startDate = DateParser.parse(startDate);

            return this;
        }

        public Builder endDate(String endDate) {
            this.endDate = DateParser.parse(endDate);

            return this;
        }

        public PromotionRule build() {
            return new PromotionRule(this);
        }

    }

}
