package store.domain.product;

import java.util.Objects;

public class Product {
    private final String name;
    private final int price;

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        validateProduct();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private int price;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public int calculateTotalPrice(int requestedQuantity) {
        return price * requestedQuantity;
    }

    private void validateProduct() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 상품명은 빈 값일 수 없습니다.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("[ERROR] 상품 가격은 0보다 커야 합니다.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return price == product.price && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

}