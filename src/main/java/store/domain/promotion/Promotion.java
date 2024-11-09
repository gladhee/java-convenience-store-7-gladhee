package store.domain.promotion;

import java.time.LocalDate;

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
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isApplicable(LocalDate currentDate) {
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
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

        public Builder requiredQuantity(int requiredQuantity) {
            this.requiredQuantity = requiredQuantity;

            return this;
        }

        public Builder freeQuantity(int freeQuantity) {
            this.freeQuantity = freeQuantity;

            return this;
        }

        public Builder startDate(String startDate) {
            this.startDate = LocalDate.parse(startDate);

            return this;
        }

        public Builder endDate(String endDate) {
            this.endDate = LocalDate.parse(endDate);

            return this;
        }

        public Promotion build() {
            validate();

            return new Promotion(this);
        }

        // @todo refactor
        private void validate() {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("[ERROR] 프로모션명은 빈 값일 수 없습니다.");
            }
            if (requiredQuantity <= 0) {
                throw new IllegalArgumentException("[ERROR] 필요 수량은 0보다 커야 합니다.");
            }
            if (freeQuantity <= 0) {
                throw new IllegalArgumentException("[ERROR] 증정 수량은 0보다 커야 합니다.");
            }
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("[ERROR] 프로모션 기간은 필수입니다.");
            }
            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("[ERROR] 시작일이 종료일보다 늦을 수 없습니다.");
            }
        }

    }

    public String getName() {
        return name;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

}