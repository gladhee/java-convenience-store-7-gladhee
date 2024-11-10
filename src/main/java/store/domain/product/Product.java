package store.domain.product;

import store.domain.stock.Stock.Builder;

public class Product {

    private final String name;
    private final int price;
    private int quantity;

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        validateProduct();
    }

    private void validateProduct() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 상품명은 빈 값일 수 없습니다.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("[ERROR] 상품 가격은 0보다 커야 합니다.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("[ERROR] 상품 수량은 음수일 수 없습니다.");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public static class Builder {

        private String name;
        private int price;
        private int quantity;

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder price(int price) {
            this.price = price;

            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;

            return this;
        }

        public Product build() {
            return new Product(this);
        }

    }

}
