package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.exception.PromotionException.EmptyPromotionNameException;
import store.exception.PromotionException.QuantityShouldBePositiveException;
import store.exception.PromotionException.StartDateShouldBeEarlierThanEndDateException;
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
        validatePromotionName();
        validateRequiredQuantity();
        validateFreeQuantity();
        validatePromotionDate();
    }

    private void validatePromotionName() {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyPromotionNameException();
        }
    }

    private void validateRequiredQuantity() {
        if (requiredQuantity <= 0) {
            throw new QuantityShouldBePositiveException();
        }
    }

    private void validateFreeQuantity() {
        if (freeQuantity <= 0) {
            throw new QuantityShouldBePositiveException();
        }
    }

    private void validatePromotionDate() {
        if (startDate.isAfter(endDate)) {
            throw new StartDateShouldBeEarlierThanEndDateException();
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
