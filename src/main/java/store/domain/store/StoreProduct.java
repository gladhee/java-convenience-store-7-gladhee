package store.domain.store;

import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.util.parser.InputParser;

public class StoreProduct {

    private final Product product;
    private Promotion promotion;
    private int normalQuantity;
    private int promotionQuantity;

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
        private final Promotion promotion = null;
        private int normalQuantity;
        private final int promotionQuantity = 0;

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder normalQuantity(String normalQuantity) {
            this.normalQuantity = InputParser.convertToInt(normalQuantity);
            return this;
        }

        public StoreProduct build() {
            return new StoreProduct(this);
        }
    }

    public void updatePromotion(String promotionQuantity, Promotion promotion) {
        this.promotionQuantity = InputParser.convertToInt(promotionQuantity);
        this.promotion = promotion;
    }

    public boolean hasSufficientStock(int requestedQuantity) {
        return (normalQuantity + promotionQuantity) >= requestedQuantity;
    }

}
