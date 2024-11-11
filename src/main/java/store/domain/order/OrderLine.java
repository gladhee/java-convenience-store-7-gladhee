package store.domain.order;

import store.domain.store.StoreProduct;

public class OrderLine {

    private final StoreProduct product;
    private final int quantity;

    public OrderLine(StoreProduct product, int quantity) {
        validateQuantity(quantity);
        this.product = product;
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 0보다 커야 합니다.");
        }
    }

    public int calculatePrice() {
        return product.calculatePrice(quantity);
    }

    public int calculatePromotionDiscount() {
        return product.calculatePromotionDiscount(quantity);
    }

    public StoreProduct getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

}