package store.domain.store;

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

    public void updateNormalQuantity(String normalQuantity) {
        this.normalQuantity = InputParser.convertToInt(normalQuantity);
    }

    public boolean hasSufficientStock(int requestedQuantity) {
        return (normalQuantity + promotionQuantity) >= requestedQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public String toNormalString() {
        if (normalQuantity == null) {
            return "";
        }
        if (normalQuantity == 0) {
            return "- " + product.toString() + " 재고없음\n";
        }
        return "- " + product.toString() + " " + normalQuantity + "개\n";
    }

    public String toPromotionString() {
        if (promotionQuantity == null) {
            return "";
        }
        if (promotionQuantity == 0) {
            return "- " + product.toString() + " 재고없음 " + promotion.getName() + "\n";
        }

        return "- " + product.toString() + " " + promotionQuantity + "개 " + promotion.getName() + "\n";
    }

}
