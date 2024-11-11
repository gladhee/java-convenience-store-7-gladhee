package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.util.parser.DateParser;
import store.util.parser.InputParser;

public class Promotion {

    private final String name;
    private final int requiredQuantity;
    private final int freeQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(Builder builder) {
        this.name = builder.name;
        this.requiredQuantity = builder.requiredQuantity;
        this.freeQuantity = builder.freeQuantity;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        validatePromotionProduct();
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isApplicable() {
        LocalDate currentDate = DateTimes.now().toLocalDate();

        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    public int calculateTotalQuantityPerSet() {
        return requiredQuantity + freeQuantity;
    }

    public int calculatePromotionDiscount(int price) {
        return price * freeQuantity;
    }

    public String getName() {
        return name;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    private void validatePromotionProduct() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 프로모션명은 빈 값일 수 없습니다.");
        }
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

        private String name;
        private int requiredQuantity;
        private int freeQuantity;
        private LocalDate startDate;
        private LocalDate endDate;

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder requiredQuantity(String requiredQuantity) {
            this.requiredQuantity = InputParser.convertToInt(requiredQuantity);

            return this;
        }

        public Builder freeQuantity(String freeQuantity) {
            this.freeQuantity = InputParser.convertToInt(freeQuantity);

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

        public Promotion build() {
            return new Promotion(this);
        }

    }

}
