package store.domain.product;

import store.exception.ProductException.EmptyProductNameException;
import store.exception.ProductException.PriceShouldBePositiveException;
import store.util.parser.InputParser;

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

    public int calculateTotalPrice(int requestedQuantity) {
        return price * requestedQuantity;
    }

    private void validateProduct() {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyProductNameException();
        }
        if (price <= 0) {
            throw new PriceShouldBePositiveException();
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " " + String.format("%,d", price) + "원";
    }

    public static class Builder {
        private String name;
        private int price;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(String price) {
            this.price = InputParser.convertToInt(price);
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

}