package store.domain.store;

import java.util.Objects;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.util.parser.InputParser;

public class StoreProduct {

    private final Product product;
    private Promotion promotion;
    private Integer normalQuantity;
    private Integer promotionQuantity;

    private StoreProduct(Builder builder) {
        this.product = builder.product;
        this.promotion = builder.promotion;
        this.normalQuantity = builder.normalQuantity;
        this.promotionQuantity = builder.promotionQuantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void updateNormalQuantity(String normalQuantity) {
        this.normalQuantity = InputParser.convertToInt(normalQuantity);
    }

    public void decreaseStock(int quantity) {
        if (isOutOfStock(quantity)) {
            throw new IllegalStateException("[ERROR] 재고가 부족합니다.");
        }
        if (isPromotionApplicable()) {
            decreaseWithPromotion(quantity);
            return;
        }
        decreaseNormalStock(quantity);
    }

    private void decreaseWithPromotion(int quantity) {
        int promotionSets = quantity / promotion.calculateTotalQuantityPerSet();
        int remainingQuantity = quantity % promotion.calculateTotalQuantityPerSet();

        // 프로모션 재고 처리
        int promotionNeeded = promotionSets * promotion.calculateTotalQuantityPerSet();
        if (promotionQuantity >= promotionNeeded) {
            promotionQuantity -= promotionNeeded;
        } else {
            int remainingFromPromotion = promotionNeeded - promotionQuantity;
            promotionQuantity = 0;
            normalQuantity -= remainingFromPromotion;
        }

        // 남은 수량 처리
        normalQuantity -= remainingQuantity;
    }

    private void decreaseNormalStock(int quantity) {
        normalQuantity -= quantity;
    }

    public int calculatePrice(int requestedQuantity) {
        if (!isPromotionApplicable()) {
            return calculateNormalPrice(requestedQuantity);
        }
        return calculatePromotionPrice(requestedQuantity);
    }

    private int calculateNormalPrice(int quantity) {
        return product.calculateTotalPrice(quantity);
    }

    private int calculatePromotionPrice(int quantity) {
        int sets = quantity / promotion.calculateTotalQuantityPerSet();
        int remainder = quantity % promotion.calculateTotalQuantityPerSet();

        int setPrice = product.calculateTotalPrice(sets) * promotion.getRequiredQuantity();
        int remainderPrice = product.calculateTotalPrice(remainder);

        return setPrice + remainderPrice;
    }

    public int calculatePromotionDiscount(int quantity) {
        if (!isPromotionApplicable()) {
            return 0;
        }

        int totalPromotionQuantity = promotionQuantity != null ? promotionQuantity : 0;
        int maxApplicableSets = totalPromotionQuantity / promotion.calculateTotalQuantityPerSet();
        int actualSets = Math.min(quantity / promotion.calculateTotalQuantityPerSet(), maxApplicableSets);

        return actualSets * promotion.calculatePromotionDiscount(product.getPrice());
    }

    public String getName() {
        return product.getName();
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getPromotionQuantity() {
        return Objects.requireNonNullElse(promotionQuantity, 0);
    }

    public int getNormalQuantity() {
        return Objects.requireNonNullElse(normalQuantity, 0);
    }

    public boolean isPromotionApplicable() {
        return promotion != null && promotion.isApplicable();
    }

    public boolean isOutOfStock(int requestedQuantity) {
        if (normalQuantity == null) {
            return promotionQuantity < requestedQuantity;
        }
        if (promotionQuantity == null) {
            return normalQuantity < requestedQuantity;
        }
        return (normalQuantity + promotionQuantity) < requestedQuantity;
    }

    public int calculateNonPromotionQuantity(int requestedQuantity) {
        if (promotion == null) {
            return 0;
        }
        int totalRequiredForPromotion = promotion.getRequiredQuantity();
        int promotionSetCount = promotionQuantity / totalRequiredForPromotion;
        int maxPromotionItems = promotionSetCount * totalRequiredForPromotion;
        if (requestedQuantity <= maxPromotionItems) {
            return 0;
        }
        return requestedQuantity - maxPromotionItems;
    }

    public Product getProduct() {
        return product;
    }

    public String toNormalString() {
        if (normalQuantity == null) {
            return "";
        }
        if (normalQuantity == 0) {
            return "- " + product.toString() + " 재고 없음\n";
        }
        return "- " + product.toString() + " " + normalQuantity + "개\n";
    }

    public String toPromotionString() {
        if (promotionQuantity == null) {
            return "";
        }
        if (promotionQuantity == 0) {
            return "- " + product.toString() + " 재고 없음 " + promotion.getName() + "\n";
        }

        return "- " + product.toString() + " " + promotionQuantity + "개 " + promotion.getName() + "\n";
    }


    public static class Builder {
        private Product product;
        private Promotion promotion;
        private Integer normalQuantity;
        private Integer promotionQuantity;

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder normalQuantity(String normalQuantity) {
            this.normalQuantity = InputParser.convertToInt(normalQuantity);
            return this;
        }

        public Builder promotionQuantity(String promotionQuantity) {
            this.promotionQuantity = InputParser.convertToInt(promotionQuantity);
            return this;
        }

        public Builder promotion(Promotion promotion) {
            this.promotion = promotion;
            return this;
        }

        public StoreProduct build() {
            return new StoreProduct(this);
        }
    }

}
